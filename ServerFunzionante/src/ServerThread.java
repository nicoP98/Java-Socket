import java.io.*;
import java.net.*;
import java.util.ArrayList;

class ServerThread extends Thread {
    private static int counter = 0;
    private int id = ++counter;
    private Socket socket;
    private ArrayList<String> nomi;
    private ArrayList<String> indirizzi;
    private BufferedReader in;
    private PrintWriter out;
    public ServerThread(Socket s,ArrayList<String> nomia,ArrayList<String> indirizzia) throws IOException {
        nomi=nomia;
        indirizzi=indirizzia;
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        out = new PrintWriter(new BufferedWriter(osw), true);
        start();
        System.out.println("ServerThread "+id+": started");
    }
    public void run() {
        try {
            while (true) {
                boolean trovato=false;
                String str = in.readLine();
                for(int i=0;i<nomi.size();i++){
                    if(str.equals(nomi.get(i))){
                        System.out.println("ServerThread "+id+": echoing -> " + indirizzi.get(i));
                        out.println(indirizzi.get(i));
                        trovato=true;
                    }
                }
                if (trovato==false){
                    out.println("dominio non trovato");
                }
                if (str.equals("END")) break;

            }
            System.out.println("ServerThread "+id+": closing...");
        } catch (IOException e) {}
        try {
            socket.close();
        } catch(IOException e) {}
    }
}

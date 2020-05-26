import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoMultiServer {
    static final int PORT = 1070;
    public static void main(String[] args) throws IOException {


        ArrayList<String> nomi = new ArrayList<String>();
        ArrayList<String> indirizzi = new ArrayList<String>();

        String csvFile = "C:/Users/Nico/Desktop/n.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] eccl = line.split(cvsSplitBy);
                nomi.add(eccl[0]);
                indirizzi.add(eccl[1]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("EchoMultiServer: started");
        System.out.println("Server Socket: " + serverSocket);

        try {
            while(true) {
                // bloccante finchè non avviene una connessione:
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted: "+ clientSocket);
                try {
                    new ServerThread(clientSocket,nomi,indirizzi);
                } catch(IOException e) {
                    clientSocket.close();
                }
            }
        }
        catch (IOException e) {
            System.err.println("Accept failed");
            System.exit(1);
        }
        System.out.println("EchoMultiServer: closing...");
        serverSocket.close();
    }
}

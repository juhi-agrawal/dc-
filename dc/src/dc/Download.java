/*
 * This class will provide dowload facility to other peers each in seperate thread
 */
package dc;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author JUHI AGRAWAL
 */
public class Download extends Thread{
    static ServerSocket downSocket,nameSocket;
    static Socket clientSocket,nClientSocket;
    String loc;
    
    //Constructor -- establishes socket to enable download
    Download(){
        try {
            downSocket = new ServerSocket(5100);
            nameSocket=new ServerSocket(5112);
            System.out.println("Server started.");
        } catch (Exception e) {
            System.err.println("Port already in use.");
            System.exit(1);
        }
    }
    
    //Eastablishes connection with peer
    synchronized public void run(){
        while (true) {
            try {
                nClientSocket = nameSocket.accept();
                
                DataInputStream din = new DataInputStream(nClientSocket.getInputStream());
                loc = din.readUTF();
                
                din.close();
                nClientSocket.close();
                        
                        
                        
                clientSocket = downSocket.accept();
                System.out.println("Accepted connection : " + clientSocket);

                //Start download in new thread
                Thread t = new Thread(new DownConnection(clientSocket,loc));

                t.start();

            } catch (Exception e) {
                System.err.println("Error in connection attempt.");
            }
        }
    
    }
    
}


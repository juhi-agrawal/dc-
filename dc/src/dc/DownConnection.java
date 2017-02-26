/*
 * This class performs the download operation by sending file to the peer which requires it
*/
package dc;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DownConnection extends Thread{
    Socket clientSocket;
        InputStream in = null;
        OutputStream out = null;
        String loc;
    public DownConnection(Socket client,String l) {
        this.clientSocket = client;
        loc=l;
    }
    
    public void run(){
        try{
          /*  DataInputStream din = new DataInputStream(clientSocket.getInputStream());
            loc = din.readUTF();
            din.close();*/
        //  loc="C:\\Users\\JUHI AGRAWAL\\Documents\\acadcal_2015_16.pdf";
            System.out.println(loc);
        File file = new File(loc);
        // Get the size of the file
        long length = file.length();
        System.out.println("length of file is "+ length);
        byte[] bytes = new byte[16 * 1024];
        in = new FileInputStream(file);
        out = clientSocket.getOutputStream();

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }

        out.close();
        in.close();
        
        clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(downloadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

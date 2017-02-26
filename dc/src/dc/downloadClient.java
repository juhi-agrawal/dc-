/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import static java.lang.System.out;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JUHI AGRAWAL
 */
public class downloadClient extends Thread{
    
    
    static Socket socket,nClientSocket;
    static ServerSocket nameSocket;
    String ip, location, filename;
    InputStream in ;
    OutputStream out ;
    DataOutputStream dout;
    
    public downloadClient(String i,String l){
        ip=i;
        location=l;
        filename=l.substring(l.lastIndexOf('\\'));
    }
    
    @Override
    synchronized public void run(){
     
        try {
            socket = new Socket(ip, 5100);
            
            
            nClientSocket = new Socket(ip,5112);
            
            dout = new DataOutputStream(nClientSocket.getOutputStream());
            dout.writeUTF(location);
            dout.flush();
            dout.close();
            nClientSocket.close();
            
            in = socket.getInputStream();
            out = new FileOutputStream("E:\\Downloads\\"+filename);
            

        byte[] bytes = new byte[16*1024];

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }

        out.close();
        in.close();	

        
        socket.close();
        } catch (IOException ex) {
            Logger.getLogger(downloadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("khatam program client ka ");
    }
}

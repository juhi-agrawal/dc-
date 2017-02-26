/*
 * This class performs the upload function from peer to hub
 */
package dc;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.naming.Context;
import javax.swing.JOptionPane;


public class Upload extends Thread{


    //Socket variables
    Socket sock;
    BufferedReader stdin;
    
    //Details to be shared
    String ip,username,about;
    String name,path;
    long size;
    
    //Constructor -- initialises the details to be uploaded
    public Upload(String u,String i,String p,long s,String a){
        username=u;
        ip=i;
        path=p;
        size=s;
        about=a;
    }
    
    @Override
    synchronized public void run(){
        
        //Eastablishing socket connection
        System.out.println(username);
         try {
            sock = new Socket(ip, 4444);
            stdin = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
            System.err.println("Cannot connect to the server, try again later.");
            System.exit(1);
        }
        System.out.println("Connected");
        
        //Writing in Hub database
        DataOutputStream dout;  
        try {
            dout = new DataOutputStream(sock.getOutputStream());
            dout.writeUTF("#$#"+username+"#$#"+path+"#$#"+size+"#$#"+about);  
            dout.flush(); 
            sock.close();
          
        } catch (IOException ex) {
          //  Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}

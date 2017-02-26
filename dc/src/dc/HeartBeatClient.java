/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JUHI AGRAWAL
 */
public class HeartBeatClient extends Thread{
    
    private Socket HbClient;
    private DataInputStream din;
    private DataOutputStream dout;
    private String ip;
    private int SLEEP_TIME = 1000;
    
    public HeartBeatClient(String i){
        ip=i;
    }
    
    public void run(){
        try {
            HbClient = new Socket(ip,4480);
            
            din = new DataInputStream(HbClient.getInputStream());
            dout = new DataOutputStream(HbClient.getOutputStream());
            String str;
            long time=System.currentTimeMillis();
            while(true){
                if(System.currentTimeMillis() - time >3000)
                    break;
                dout.writeUTF("connected");
                if(din.available()>0){
                    str=(String)din.readUTF();
                    //System.out.println(str+" "+HbClient.isConnected());
                    time = System.currentTimeMillis();
                }
                this.sleep(SLEEP_TIME);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(HeartBeatClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(HeartBeatClient.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                System.out.println("close");
                din.close();
                dout.close();
                HbClient.close();
            } catch (IOException ex) {
                Logger.getLogger(HeartBeatClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}

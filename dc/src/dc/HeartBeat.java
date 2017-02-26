/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dc;

import static dc.CLIENTConnection.conn;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JUHI AGRAWAL
 */
public class HeartBeat extends Thread{
    
    private Socket HbClient;
    private DataInputStream din;
    private DataOutputStream dout;
    private String ip;
    final private int SLEEP_TIME=1000;
    
    //Database details
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/peertopeer"; // jdbc:mysql://server_address/Database_name
    
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
   
    static Connection conn = null;
    static Statement stmt = null;
    
    public HeartBeat(Socket client,String i) {
        this.HbClient = client;
        ip=i;
    }
    
    public void run(){
        
        try{
            din = new DataInputStream(HbClient.getInputStream());
            dout = new DataOutputStream(HbClient.getOutputStream());
            long time=System.currentTimeMillis();
            while(true){
                if(System.currentTimeMillis() - time >3000)
                    break;
                if(din.available()>0){
                    String  str=(String)din.readUTF();
                    //System.out.println(str+" "+HbClient.isConnected());
                    time = System.currentTimeMillis();
                }
                dout.writeUTF("connected");
                
                this.sleep(SLEEP_TIME);
                
            } 
        
        
            System.out.println("Close");
            
        }catch(Exception e){}
        finally{
            try {
                System.out.println("close");
                din.close();
                dout.close();
                HbClient.close();
                Class.forName("com.mysql.jdbc.Driver");

                    System.out.println("Connecting to database...");
                    conn = DriverManager.getConnection(DB_URL,USER,PASS);
                    stmt = conn.createStatement();
                    ip=ip.substring(0,ip.lastIndexOf(':'));
                    String sql = "DELETE FROM `activeUser` WHERE `ip` LIKE '"+ip+"%'";
                    System.out.println(sql);
                    stmt.executeUpdate(sql);
                    stmt.close();
                    conn.close(); 
            } catch (IOException ex) {
                Logger.getLogger(HeartBeatClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HeartBeat.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(HeartBeat.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}

/*
 * This class represents the thread class created in Hub class to handle each client seperately.
 * It performs all the functions of search and upload
 */

package dc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

class CLIENTConnection extends Thread{

    //Scoket Variables
    private Socket clientSocket;
    private BufferedReader in = null;
    DataOutputStream dout;
    
    //Variables to stare data to be passed to the client
    String name,path,about,ip,filename,ext;
    String criteria;
    long size;
    
    //Database details
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/peertopeer"; // jdbc:mysql://server_address/Database_name
    
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
   
    static Connection conn = null;
    static Statement stmt = null;
    
    //Constructor -- initalises socket
    public CLIENTConnection(Socket client) {
        this.clientSocket = client;
    }

    @Override
    public void run() {
        
        System.out.println("new Client Connected");
        System.out.println(clientSocket.getRemoteSocketAddress().toString());
        ip=clientSocket.getRemoteSocketAddress().toString();
       
        
        DataInputStream dis;  
        
        try {
            while(true)
            {        
                dis = new DataInputStream(clientSocket.getInputStream());
                dout = new DataOutputStream(clientSocket.getOutputStream());
             
                String  str=(String)dis.readUTF();
                System.out.println(str);
                
                //Updating Active User table
                if(str.charAt(0)=='#' && str.charAt(1)=='@' && str.charAt(2)=='#')
                {
                    name=str.substring(3);
                    Class.forName("com.mysql.jdbc.Driver");

                    System.out.println("Connecting to database...");
                    conn = DriverManager.getConnection(DB_URL,USER,PASS);
                    
                    String sql;
                    sql = "INSERT INTO `peertopeer`.`activeUser`"
                            + " (`username`, `ip`, `load`)"
                            + " VALUES(?,?,?)";
                    PreparedStatement ps;
                    ps = conn.prepareStatement(sql);
                    ps.setString(1,name);
                    ps.setString(2,ip);
                    ps.setInt(3,0);
                    ps.executeUpdate();
     
                    //stmt.close();
                    conn.close(); 
                    
                }
                
                //Upload request
                if(str.charAt(0)=='#' && str.charAt(1)=='$' && str.charAt(2)=='#')
                {
                    StringTokenizer st = new StringTokenizer(str,"#$#");
                    name=st.nextToken();
                    path=st.nextToken();
                    
                    filename=path.substring(path.lastIndexOf('\\'));
                    ext=filename.substring(filename.lastIndexOf('.'));
                    size=Long.valueOf(st.nextToken()).longValue();
                    if(st.hasMoreTokens())
                    {
                       about=st.nextToken();
                    }
                    else
                    {
                        about="";
                    }
                
                    //Entering file details in database
                    Class.forName("com.mysql.jdbc.Driver");

                    System.out.println("Connecting to database...");
                    conn = DriverManager.getConnection(DB_URL,USER,PASS);
                    //stmt = conn.createStatement();
                    System.out.println("Path : "+path);
                    String sql;
                    sql = "INSERT INTO `peertopeer`.`filelist`"
                            + " (`username`, `filename`, `ip`, `location`, `about`, `filesize`, `format`)"
                            + " VALUES(?,?,?,?,?,?,?)";
                            //+ " VALUES ( '"+name+"', '"+filename+"', '"+ip+"', \""+path+"\", '"+about+"', '"+size+"', '"+ext+"');";
                    PreparedStatement ps;
                    ps = conn.prepareStatement(sql);
                    ps.setString(1,name);
                    ps.setString(2,filename);
                    ps.setString(3,ip);
                    ps.setString(4,path);
                    ps.setString(5,about);
                    ps.setLong(6,Long.valueOf(size));
                    ps.setString(7,ext);
                    ps.executeUpdate();
     
                    //stmt.close();
                    conn.close(); 
                }//Upload request finishes
               
                //Search request
                if(str.charAt(0)=='#' && str.charAt(1)=='%' && str.charAt(2)=='#')
                {
                    System.out.println("Connecting to database...");
                    conn = DriverManager.getConnection(DB_URL,USER,PASS);
                    stmt = conn.createStatement();
                    System.out.println("db");
                    String sql;
                 
                    Class.forName("com.mysql.jdbc.Driver");
                        
                    sql=str.substring(3);
                    System.out.println(sql);
                       ResultSet rs;
        
                    rs = stmt.executeQuery(sql);
    
                    //Sending the requested details back to the client
                    while(rs.next()){
                        System.out.println(rs.getString("location"));
                        String res = "#%#1"+rs.getString("filename")+"#%#2"+rs.getString("username")+"#%#3"
                                        +rs.getString("filesize")+"#%#4"+rs.getString("about")
                                        +"#%#5"+rs.getString("location")+"#%#"+rs.getString("ip");
                        System.out.println(res);
                        dout.writeUTF(res);
                        dout.flush();
                    }
                    dout.writeUTF("END");
                    dout.flush();
  
                    rs.close();
                    stmt.close();
                    conn.close();
                   
                }
                
                if(str.charAt(0)=='#' && str.charAt(1)=='!' && str.charAt(2)=='#')
                {
                    System.out.println("1Connecting to database...");
                    conn = DriverManager.getConnection(DB_URL,USER,PASS);
                    stmt = conn.createStatement();
                    System.out.println("db");
                    String sql;
                 
                    Class.forName("com.mysql.jdbc.Driver");
                        
                    sql=str.substring(3);
                    System.out.println(sql);
                       ResultSet rs;
        
                    rs = stmt.executeQuery(sql);
    
                    //Sending the requested details back to the client
                    while(rs.next()){
                       // System.out.println(rs.getString("location"));
                        String res = rs.getString("username");
                        System.out.println(res);
                        dout.writeUTF(res);
                        dout.flush();
                    }
                    dout.writeUTF("END");
                    dout.flush();
  
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }finally{
           //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}

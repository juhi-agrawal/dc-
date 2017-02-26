/*
 * This class represents the hub's gui.This shows up when the system containing the hub runs the aaplication.
 * It shows two tables - list of all the files uploaded in the hub and the list of clients currently connected to the hub.
 */

package dc;

import static dc.HeartBeat.conn;
import static dc.Hub.stmt;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Hub extends javax.swing.JFrame {

    /**
     * Creates new Hub GUI
     */
    private static ServerSocket serverSocket; 
    private static Socket clientSocket = null;
    
    private static ServerSocket HbServerSocket;
    private static Socket HbclientSocket;
    
    // JDBC details
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
    static final String DB_URL = "jdbc:mysql://localhost/peertopeer"; // jdbc:mysql://server_address/Database_name 

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
  
    static Connection conn = null;
    static Statement stmt = null;
  
    //Default constructor
    public Hub() {
        initComponents();
        
        //Esablishing Server Socket
        try {
            serverSocket = new ServerSocket(4444);
            HbServerSocket = new ServerSocket(4480);
            System.out.println("Server started.");
        } catch (Exception e) {
            System.err.println("Port already in use.");
            System.exit(1);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel3");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 199, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(94, 94, 94))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        
        //GUI Related function
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Hub().setVisible(true);
            }
        });
        
        System.out.println("Creating statement...");
        try{
         
            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();

            String sql;
            sql = "SELECT * FROM filelist";
            ResultSet rs;
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                int id;
                id = rs.getInt("id");
                String name = rs.getString("username");

                 //Display values
                 System.out.print("ID: " + id);
                 //System.out.print(", Age: " + age);
                 System.out.println(", Name: " + name+"\n");
            }
  
            rs.close();
            stmt.close();
            
            
            
                    stmt = conn.createStatement();
                     sql = "DELETE FROM `activeUser`";
                    System.out.println(sql);
                    stmt.executeUpdate(sql);
                    stmt.close();
                    conn.close(); 
            
            
            
        } catch(SQLException se){
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
            }
            catch(SQLException se2){}// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }
            catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
   //   
        try{
        //Establishing connection with client
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted connection : " + clientSocket);

                Thread t = new Thread(new CLIENTConnection(clientSocket));
                t.start();
                
                HbclientSocket = HbServerSocket.accept();
                Thread t1 = new Thread(new HeartBeat(HbclientSocket,clientSocket.getRemoteSocketAddress().toString()));
                t1.start();

            } catch (Exception e) {
                System.err.println("Error in connection attempt.");
            }
        }
        }
        finally{
            try{
            clientSocket.close();
            HbclientSocket.close();
            serverSocket.close();
            HbServerSocket.close();
            Class.forName("com.mysql.jdbc.Driver");

                    System.out.println("Connecting to database...");
                    conn = DriverManager.getConnection(DB_URL,USER,PASS);
                    stmt = conn.createStatement();
                    String sql = "DELETE FROM `activeUser`";
                    System.out.println(sql);
                    stmt.executeUpdate(sql);
                    stmt.close();
                    conn.close(); 
            }
            catch(Exception e){}
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dc;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JUHI AGRAWAL
 */
public class ActiveUserPage1 extends javax.swing.JFrame {

    /**
     * Creates new form ActiveUserPage
     */
    
    static Socket sock;
    static DataInputStream stdin;
    static DataOutputStream dout;
    static String ip,criteria,str,downloadIp;
    
    public ActiveUserPage1() {
        initComponents();
    }

    public ActiveUserPage1(String i) {
        initComponents();
        ip=i;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        userList = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        userList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Active Users"
            }
        ));
        jScrollPane2.setViewportView(userList);

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S. No.", "File Name", "User Name", "File Size", "About", "Location"
            }
        ));
        jScrollPane1.setViewportView(Table);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel t=(DefaultTableModel)userList.getModel();
        t.setRowCount(0);
        DefaultTableModel t1=(DefaultTableModel)Table.getModel();
        t1.setRowCount(0);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Table.setRowSelectionAllowed(true);
        //Table.setColumnSelectionAllowed(false);
        try {
            sock = new Socket(ip, 4444);
            stdin = new DataInputStream(sock.getInputStream());
            dout = new DataOutputStream(sock.getOutputStream());
            System.out.println("enter");
        } catch (Exception e) {
            System.err.println("Cannot connect to the server, try again later.");
            System.exit(1);
        }
        System.out.println("Connected");
        try {
            
            
            criteria="#!#SELECT * FROM `activeUser`";
            System.out.println(criteria);
            dout.writeUTF(criteria);
            dout.flush(); 
            
            int count=1;
            //str=(String)stdin.readUTF();
            while(!((str=(String)stdin.readUTF()).equals("END"))){
                t.addRow(new String[]{});
                System.out.println(str);
                
               
                userList.getModel().setValueAt(str,0, 1);
        }
            
           // JOptionPane.showMessageDialog((Component) context,"File Successfully Uploaded!!");
        } catch (IOException ex) {
          //  Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } 
        userList.addMouseListener(new MouseAdapter(){
           
           public void mouseClicked(MouseEvent me)
           {
               if(me.getClickCount()==2)
               {
                   JTable target = (JTable)me.getSource();
                   int row = target.getSelectedRow();
                   
                   String user = (String) userList.getValueAt(row, 1);
                   System.out.println(user);
                   criteria="#%#SELECT * FROM `filelist` WHERE `username` LIKE "+user;
                   try{
                       
                   dout.writeUTF(criteria);
            dout.flush(); 
            
            int count=1;
            //str=(String)stdin.readUTF();
            String a;
            while(!((str=(String)stdin.readUTF()).equals("END"))){
                t.addRow(new String[]{});
                System.out.println(str);
                
                StringTokenizer st = new StringTokenizer(str,"#%#");
                System.out.println(st.countTokens());
                Table.getModel().setValueAt(String.valueOf(count), count-1, 0);
                if(st.hasMoreTokens() && !((a=st.nextToken()).equals("1")))
                Table.getModel().setValueAt(a.substring(1), count-1, 1);
                if(st.hasMoreTokens() && !((a=st.nextToken()).equals("2")))
                Table.getModel().setValueAt(a.substring(1), count-1, 2);
                if(st.hasMoreTokens() && !((a=st.nextToken()).equals("3")))
                Table.getModel().setValueAt(a.substring(1), count-1, 3);
                if(st.hasMoreTokens() && !((a=st.nextToken()).equals("4")))
                Table.getModel().setValueAt(a.substring(1), count-1, 4);
                if(st.hasMoreTokens() && !((a=st.nextToken()).equals("5")))
                Table.getModel().setValueAt(a.substring(1), count-1, 5);
                if(st.hasMoreTokens()){
                    
                    String b=st.nextToken();
                    downloadIp=(b).substring(1,b.lastIndexOf(':'));
                }
                count++;
                
            
        }
            sock.close();
            
           // JOptionPane.showMessageDialog((Component) context,"File Successfully Uploaded!!");
        } catch (IOException ex) {
          //  Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Table.addMouseListener(new MouseAdapter(){
           
           public void mouseClicked(MouseEvent me)
           {
               if(me.getClickCount()==2)
               {
                   JTable target = (JTable)me.getSource();
                   int row = target.getSelectedRow();
                   System.out.println("test   "+Table.getValueAt(row,0).toString());
                   System.out.println("test   "+Table.getValueAt(row,1).toString());
                   System.out.println("test   "+Table.getValueAt(row,2).toString());
                   System.out.println("test   "+Table.getValueAt(row,3).toString());
                   System.out.println("test   "+Table.getValueAt(row,4).toString());
                   System.out.println("test   "+Table.getValueAt(row,5).toString());
                   
                   //New thread to start the download
                   Thread t = new Thread(new downloadClient(downloadIp,Table.getValueAt(row,5).toString()));
                   t.start();
                   
               }
               
               
           }
           
       });
      
               }
               
               
           }
           
       });
      
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ActiveUserPage1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ActiveUserPage1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ActiveUserPage1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ActiveUserPage1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ActiveUserPage1().setVisible(true);
            }
        });
        System.out.println("Active");
        
        
      
    }

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {                                   
        // TODO add your handling code here:
    }
    
    private void userListMouseClicked(java.awt.event.MouseEvent evt) {                                   
        // TODO add your handling code here:
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable userList;
    // End of variables declaration//GEN-END:variables
}

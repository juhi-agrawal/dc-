/*
 * This class is the second pane the peer sees.
 * It will contain option for upload and search. It will also show the list of online users
 */
package dc;

import java.io.File;
import javax.naming.Context;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class secPage extends javax.swing.JFrame {

    //Variables for c=sharing info with hub
    firstPage parentPage;
    String name, path,username,ip,abt;
    long size;
    
    //Default constructor
    public secPage() {
        initComponents();
    }
    
    //Constructor -- initializes ip and username of the peer
    public secPage(firstPage p,String u,String i) {
        initComponents();
        username=u;
        ip=i;
        parentPage=p;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        search = new javax.swing.JButton();
        upload = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        filename = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        about = new javax.swing.JTextArea();
        activeUser = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        search.setText("Search");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        upload.setText("Upload");
        upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadActionPerformed(evt);
            }
        });

        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        about.setColumns(20);
        about.setRows(5);
        jScrollPane2.setViewportView(about);

        activeUser.setText("View Active Users");
        activeUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activeUserActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(search)
                    .addComponent(activeUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(filename, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(upload)
                    .addComponent(jButton1))
                .addGap(36, 36, 36))
            .addGroup(layout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(backButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(filename, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(search)
                            .addComponent(upload))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(activeUser))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(backButton)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //This button will start the upload of given file in new thread named Upload
    private void uploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadActionPerformed
        abt=about.getText();
        
        Thread t = new Thread(new Upload(username,ip,path,size,abt));
        t.start();
        
        JOptionPane.showMessageDialog(this,"File Successfully Uploaded!!");
    }//GEN-LAST:event_uploadActionPerformed

    //Browse button -- for selecting file from computer file list
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        JFileChooser browseFile = new JFileChooser();
        browseFile.showOpenDialog(null);
        File selected = browseFile.getSelectedFile();
        System.out.println(selected.getName()+" "+selected.getAbsolutePath()+" "+selected.length());
        
        filename.setText(selected.getName());
        path=selected.getAbsolutePath();
        size=selected.length();
       
    }//GEN-LAST:event_jButton1ActionPerformed
    //Search button -- opens a new pane for search options
    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed

        searchPage ob=new searchPage(this,ip);
        this.setVisible(false);
        ob.setVisible(true);
    }//GEN-LAST:event_searchActionPerformed

    private void activeUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activeUserActionPerformed
        // TODO add your handling code here:
        ActiveUserPage ob = new ActiveUserPage(this,ip);
        this.setVisible(false);
        ob.setVisible(true);
    }//GEN-LAST:event_activeUserActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        parentPage.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_backButtonActionPerformed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new secPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea about;
    private javax.swing.JButton activeUser;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel filename;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton search;
    private javax.swing.JButton upload;
    // End of variables declaration//GEN-END:variables
}

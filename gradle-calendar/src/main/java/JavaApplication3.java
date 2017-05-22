/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.util.ServiceException;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class JavaApplication3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Arsti2().init();
    }
    
}
class Arsti2 {
JFrame main = new JFrame("Ārst");
JPanel jPanel = new JPanel();
JPanel bP = new JPanel();
private javax.swing.JButton addButton;
private javax.swing.JButton refreshButton;
JButton logoutButton = new JButton("Logout");

JTable jTable1 = new JTable();
DefaultTableModel model;
Vector columnNames = new Vector();
Vector data = new Vector();


void init()
{
   
        jTable1 = new javax.swing.JTable();
        
        addButton = new javax.swing.JButton("Add Contact");
        refreshButton = new  javax.swing.JButton("Refresh");
        logoutButton.setVisible(true);
        
        main = new javax.swing.JFrame();
        main.setVisible(true);
        main.setSize(1000,800);
        main.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        main.setTitle("Cotact Book");
        main.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

      jPanel = new javax.swing.JPanel();
        
     jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new String [][] {
                {"Name", "Mail", "Phone"}
                
            },
            new String [] {
                "Name", "Mail", "Phone no"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(300);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(200);
        
            addButton.setVisible(true);
            refreshButton.setVisible(true);
            jPanel.add(addButton);

            jPanel.add(jTable1);

            jPanel.add(refreshButton);
            jPanel.add(logoutButton);
            

            main.add(jPanel);

   

addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             try {
            // TODO add your handling code here:
            AddContact addContact=new AddContact();
            
            ContactsService myService =
            ContactsQuickstart.getContactsService();
            addContact.setMyService(myService);
            
        } catch (IOException ex) {
                 System.out.println(ex);
        } catch (GeneralSecurityException ex) {
                 System.out.println(ex);
        }
        
            
            }


});
logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("file delete");
                File file = new File("C:\\Users\\user\\.credentials\\contact-java-quickstart.json\\StoredCredential");
                  file.delete();
                  
                  
                
            }}
            );
refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 System.out.print("afsa");
        // TODO add your handling code here:
       DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
tableModel.setRowCount(0);   
  //tableModel = (DefaultTableModel) jTable1.getModel();
  String[] row = { "sho", "data2" ,"asfa"};
  
    ContactsService myService ;
                try {
                    myService = ContactsQuickstart.getContactsService();                
                    //ContactsQuickstart.printAllContacts(myService);
                tableModel = ContactsQuickstart.getAllContacts(myService, tableModel);
                } catch (ServiceException ex) {
                    Logger.getLogger(Arsti2.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Arsti2.class.getName()).log(Level.SEVERE, null, ex);
                } catch (GeneralSecurityException ex) {
                    Logger.getLogger(Arsti2.class.getName()).log(Level.SEVERE, null, ex);
                }
   
     
jTable1.setModel(tableModel);
tableModel.fireTableDataChanged();
jTable1.repaint();
            }

        });

return;
}
  

}
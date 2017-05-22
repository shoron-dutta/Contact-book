/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.util.ServiceException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author user
 */
public class AddContact {

    JFrame jframe;
    private JPanel addContactPanel;

    ContactsQuickstart cqs = new ContactsQuickstart();

    JTextField JTfullName, JTgivenName, JTfamilyName, JTemail, JTdisplayName, JTphoneNumber;
    JTextField JTaddressStreet, JTaddressCity, JTaddressRegion, JTaddressPostCode;
    JTextField JTaddressCountryFullName, JTaddressCountryShortName;
    String fullName = "FN", givenName, familyName, email, displayName, phoneNumber;
    String addressStreet, addressCity, addressRegion, addressPostCode, addressCountryFullName, addressCountryShortName;

    JButton doneButton, cancelButton;
    //ContactsService myService;
    
        ContactsService myService =
            ContactsQuickstart.getContactsService();;

    JPanel getAddContactPanel() {
        return addContactPanel;
    }

    void setMyService(ContactsService myService) {
        this.myService = myService;
    }

    AddContact() throws IOException, GeneralSecurityException {
        jframe = new JFrame();
        jframe.setVisible(true);
        jframe.setSize(600, 600);
        addContactPanel = new JPanel();

        BoxLayout boxLayout1 = new BoxLayout(addContactPanel, BoxLayout.Y_AXIS);
        addContactPanel.setLayout(boxLayout1);
        addContactPanel.add(Box.createVerticalGlue());

        JTfullName = new JTextField(20);
        JTfullName.setUI(new HintTextFieldUI("Full Name", true));
        JTfullName.setVisible(true);

        JTgivenName = new JTextField(20);
        JTgivenName.setUI(new HintTextFieldUI("Given Name", true));
        JTgivenName.setVisible(true);

        JTfamilyName = new JTextField(10);
        JTfamilyName.setUI(new HintTextFieldUI("Family Name", true));
        JTfamilyName.setSize(50, 20);
        JTfamilyName.setVisible(true);

        JTdisplayName = new JTextField(20);
        JTdisplayName.setUI(new HintTextFieldUI("Display Name", true));
        JTdisplayName.setVisible(true);

        JTemail = new JTextField(25);
        JTemail.setUI(new HintTextFieldUI("Email", true));
        JTemail.setVisible(true);

        JTphoneNumber = new JTextField(25);
        JTphoneNumber.setUI(new HintTextFieldUI("Phone number", true));
        JTphoneNumber.setVisible(true);

        JTaddressStreet = new JTextField(20);
        JTaddressStreet.setUI(new HintTextFieldUI("Address Street", true));
        JTaddressStreet.setVisible(true);

        JTaddressCity = new JTextField(25);
        JTaddressCity.setUI(new HintTextFieldUI("Address City", true));
        JTaddressCity.setVisible(true);

        JTaddressRegion = new JTextField(20);
        JTaddressRegion.setUI(new HintTextFieldUI("Address Region", true));
        JTaddressRegion.setVisible(true);

        JTaddressPostCode = new JTextField(25);
        JTaddressPostCode.setUI(new HintTextFieldUI("Address Post Code", true));
        JTaddressPostCode.setVisible(true);

        JTaddressCountryFullName = new JTextField(20);
        JTaddressCountryFullName.setUI(new HintTextFieldUI("Address Country Full Name", true));
        JTaddressCountryFullName.setVisible(true);

        JTaddressCountryShortName = new JTextField(25);
        JTaddressCountryShortName.setUI(new HintTextFieldUI("Address Country Short Name", true));
        JTaddressCountryShortName.setVisible(true);

        cancelButton = new JButton("Cancel");
        cancelButton.setVisible(true);

        doneButton = new JButton("Done");
        doneButton.setVisible(true);

        addContactPanel.add(JTfullName);
        addContactPanel.add(JTgivenName);
        addContactPanel.add(JTfamilyName);
        addContactPanel.add(JTemail);
        addContactPanel.add(JTdisplayName);
        addContactPanel.add(JTphoneNumber);
        addContactPanel.add(JTaddressStreet);
        addContactPanel.add(JTaddressCity);
        addContactPanel.add(JTaddressRegion);
        addContactPanel.add(JTaddressPostCode);
        addContactPanel.add(JTaddressCountryFullName);
        addContactPanel.add(JTaddressCountryShortName);

        addContactPanel.add(cancelButton);
        addContactPanel.add(doneButton);
        jframe.add(addContactPanel);

        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jframe.setVisible(false);
                jframe.dispose();
            }
        });
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        try{
                fullName = JTfullName.getText();
                if (fullName.length()==0) {
                    fullName = "no fullname";
                }
                System.out.println(JTfullName.getText());

                givenName = JTgivenName.getText();
                if (givenName.length()==0) {
                    givenName = "no given name";
                }
                System.out.println(givenName);

                familyName = JTfamilyName.getText();
                if (familyName.length()==0) {
                    familyName = "no family name";
                }
                System.out.println(familyName);
                email = JTemail.getText();
                if (email.length()==0) {
                    email = "default email";
                }
                System.out.println(email);
                displayName = JTdisplayName.getText();
                if (displayName.length()==0) {
                    displayName = "no display name";
                }
                System.out.println(displayName);
                phoneNumber = JTphoneNumber.getText();
                if (phoneNumber.length()==0) {
                    phoneNumber = "no phone number";
                }
                System.out.println(phoneNumber);

                addressStreet = JTaddressStreet.getText();
                if (addressStreet.length()==0) {
                    addressStreet = "no address street";
                }
                System.out.println(addressStreet);
                addressCity = JTaddressCity.getText();
                if (addressCity.length()==0) {
                    addressCity = "no address city";
                }
                System.out.println(addressCity);
                addressRegion = JTaddressRegion.getText();
                if (addressRegion.length()==0) {
                    addressRegion = "no address region";
                }
                System.out.println(addressRegion);
                addressPostCode = JTaddressPostCode.getText();
                if (addressPostCode.length()==0) {
                    addressPostCode = "no address post code";
                }
                System.out.println(addressPostCode);
                addressCountryFullName = JTaddressCountryFullName.getText();
                if (addressCountryFullName.length()==0) {
                    addressCountryFullName = "no address country full name";
                }
                System.out.println(addressCountryFullName);
                addressCountryShortName = JTaddressCountryShortName.getText();
                if (addressCountryShortName.length()==0) {
                    addressCountryShortName = "no address country short name";
                }
                System.out.println(addressCountryShortName);

                
                    
//                    System.out.println("Try "+myService+ fullName+ givenName+
//                            familyName
//                            +email+ displayName+ phoneNumber+
//                            addressStreet+
//                            addressCity+ addressRegion+addressPostCode+
//                            addressCountryFullName +" Catch");
                    
//                    ContactsQuickstart.createNewContact(myService, JTfullName.getText(), JTgivenName.getText(),
//                            JTfamilyName.getText(),
//                            JTemail.getText(), JTdisplayName.getText(), JTphoneNumber.getText(),
//                            JTaddressStreet.getText(),
//                            JTaddressCity.getText(), JTaddressRegion.getText(), JTaddressPostCode.getText(),
//                            JTaddressCountryFullName.getText(), "BN");
//                    
                    ContactsQuickstart.createNewContact(myService, fullName, givenName,
                            familyName,
                            email, displayName, phoneNumber,
                            addressStreet,
                            addressCity, addressRegion, addressPostCode,
                            addressCountryFullName, "BN");
                } catch (IOException ex) {
                    Logger.getLogger(AddContact.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServiceException ex) {
                    Logger.getLogger(AddContact.class.getName()).log(Level.SEVERE, null, ex);
                }

                //System.out.println("contact added !!");
                jframe.setVisible(false);
                jframe.dispose();

            }

        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jframe.setVisible(false);
                jframe.dispose();
            }

        });

    }

}

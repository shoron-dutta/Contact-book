
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.util.ServiceException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class MainFrame {

    static void showMessageDialog(Object object, String username_Field_is_empty) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    JFrame mainFrame;
    //AddContact addContact=new AddContact();
    JButton addButton, syncButton, loginButton, searchButton;
    JPanel menuPanel;//always shows the menu on the screen
    JPanel mainPanel;//changes according 
    JLabel labelEmail;
    AddContact addContact;
    ContactsService myService;
                    
    //constructor
    public MainFrame() throws IOException, GeneralSecurityException, ServiceException {
        mainFrame = new JFrame("Contact Book");
        mainFrame.setSize(600, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        addContact=new AddContact();
        addContact.setMyService(myService);
        initialize();
    }

    void initialize() throws IOException, GeneralSecurityException, ServiceException {
        
        

        menuPanel = new JPanel();
        menuPanel.setBackground(Color.darkGray);

        mainPanel = new JPanel();
        mainFrame.add(menuPanel, BorderLayout.NORTH);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        BoxLayout boxLayout1 = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(boxLayout1);
        mainPanel.add(Box.createVerticalGlue());
        myService=new ContactsQuickstart().getContactsService();
        //set up the main frame
        initializeButtons();

        showContacts(myService);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addContact.setMyService(myService);
                JPanel addContactPanel=addContact.getAddContactPanel();
                mainPanel.removeAll();
                mainPanel.add(addContactPanel);
                mainPanel.revalidate();
                mainPanel.repaint();

            }
        });

    }

    void showContacts(ContactsService myService)
            throws ServiceException, IOException {
        // Request the feed
        URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);
        //System.out.println("xml string: "+resultFeed.toString());

    // Print the results
        //System.out.println("this is a test"+resultFeed.getTitle().getPlainText());
        for (ContactEntry entry : resultFeed.getEntries()) {
            String contactId = entry.getId();
            //System.out.println(contactId);
            ContactEntry contact = ContactsQuickstart.retrieveContact(myService, contactId);

            for (Email email : contact.getEmailAddresses()) {
                
                System.out.print(" " + email.getAddress());
                labelEmail = new JLabel(" Email:" + email.getAddress());
                labelEmail.setVisible(true);
                labelEmail.setSize(100, 20);
                
                
                mainPanel.add(labelEmail);
                System.out.print("\n");
            }

        }
        labelEmail = new JLabel("End of list");
        mainPanel.add(labelEmail);
        labelEmail.setVisible(true);

    }

    void initializeButtons() {

        //initialize buttons
        addButton = new JButton("Add");
        syncButton = new JButton("Sync");
        loginButton = new JButton("Login");
        searchButton = new JButton("Search");

        menuPanel.add(addButton);
        menuPanel.add(syncButton);
        menuPanel.add(loginButton);
        menuPanel.add(searchButton);

        addButton.setVisible(true);
        syncButton.setVisible(true);
        loginButton.setVisible(true);
        searchButton.setVisible(true);

    }
}

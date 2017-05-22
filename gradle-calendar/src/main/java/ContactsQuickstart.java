import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.gdata.client.contacts.*;
import com.google.gdata.data.*;
import com.google.gdata.data.contacts.*;
import com.google.gdata.data.extensions.City;
import com.google.gdata.data.extensions.Country;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.FormattedAddress;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Im;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.data.extensions.PostCode;
import com.google.gdata.data.extensions.Region;
import com.google.gdata.data.extensions.Street;
import com.google.gdata.data.extensions.StructuredPostalAddress;
//import com.google.gdata.data.extensions.*;
import com.google.gdata.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.DefaultTableModel;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * if net connect generates any error it will be UnknownHostException
 */

/**
 *
 * @author nowshad
 */
class ContactsQuickstart {
    
    /** Application name. */
    private static final String APPLICATION_NAME =
        "contacttest";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/contact-java-quickstart.json");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart.json
     */
    private static final List<String> SCOPES =
        Arrays.asList("https://www.google.com/m8/feeds");

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
    
   public static Credential authorize() throws IOException, GeneralSecurityException {
        // Load client secrets.
        InputStream in =
            ContactsQuickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

                    
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        if (!credential.refreshToken()) {
                        throw new RuntimeException("Failed OAuth to refresh the token");
                    }
        // System.out.println(
        //        "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    } 
   
    public static void printAllContacts(ContactsService myService)
            throws ServiceException, IOException {
    // Request the feed
    URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
    ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);
        //// System.out.println("xml string: "+resultFeed.toString());
   
    // Print the results
    // System.out.println(resultFeed.getTitle().getPlainText());
    for (ContactEntry entry : resultFeed.getEntries()) {
        // System.out.println("Start");
        FileWriterTest.write("data.txt", "Start");
        // System.out.println("contactId: " + entry.getId());
        FileWriterTest.write("data.txt", "contactId: " + entry.getId());
      if (entry.hasName()) {
        Name name = entry.getName();
        if (name.hasFullName()) {
          String fullNameToDisplay = name.getFullName().getValue();
          if (name.getFullName().hasYomi()) {
            fullNameToDisplay += " (" + name.getFullName().getYomi() + ")";
          }
        
        // System.out.println("\t\t" + fullNameToDisplay);
        FileWriterTest.write("data.txt", fullNameToDisplay);
        } else {
          // System.out.println("\t\t (no full name found)");
          FileWriterTest.write("data.txt", "(no full name found)");
        }
        if (name.hasNamePrefix()) {
          // System.out.println("\t\t" + name.getNamePrefix().getValue());
          FileWriterTest.write("data.txt",name.getNamePrefix().getValue() );
        } else {
          // System.out.println("\t\t (no name prefix found)");
          FileWriterTest.write("data.txt","(no name prefix found)");
        }
        if (name.hasGivenName()) {
          String givenNameToDisplay = name.getGivenName().getValue();
          if (name.getGivenName().hasYomi()) {
            givenNameToDisplay += " (" + name.getGivenName().getYomi() + ")";
          }
          // System.out.println("\t\t" + givenNameToDisplay);
          FileWriterTest.write("data.txt",givenNameToDisplay);
        } else {
          // System.out.println("\t\t (no given name found)");
          FileWriterTest.write("data.txt","(no given name found)");
        }
        if (name.hasAdditionalName()) {
          String additionalNameToDisplay = name.getAdditionalName().getValue();
          if (name.getAdditionalName().hasYomi()) {
            additionalNameToDisplay += " (" + name.getAdditionalName().getYomi() + ")";
          }
          // System.out.println("\t\t" + additionalNameToDisplay);
          FileWriterTest.write("data.txt",additionalNameToDisplay);
        } else {
          // System.out.println("\t\t (no additional name found)");
          FileWriterTest.write("data.txt","(no additional name found)");
        }
        if (name.hasFamilyName()) {
          String familyNameToDisplay = name.getFamilyName().getValue();
          if (name.getFamilyName().hasYomi()) {
            familyNameToDisplay += " (" + name.getFamilyName().getYomi() + ")";
          }
          // System.out.println("\t\t" + familyNameToDisplay);
          FileWriterTest.write("data.txt",familyNameToDisplay);
        } else {
          // System.out.println("\t\t (no family name found)");
          FileWriterTest.write("data.txt","(no family name found)");
        }
        if (name.hasNameSuffix()) {
          // System.out.println("\t\t" + name.getNameSuffix().getValue());
          FileWriterTest.write("data.txt",name.getNameSuffix().getValue());
        } else {
          // System.out.println("\t\t (no name suffix found)");
          FileWriterTest.write("data.txt","(no name suffix found)");
        }
      } else {
        // System.out.println("\t (no name found)");
        FileWriterTest.write("data.txt","(no name found)");
      }
      // System.out.println("Email addresses:");
      FileWriterTest.write("data.txt","Email addresses:");
      for (Email email : entry.getEmailAddresses()) {
        System.out.print(" " + email.getAddress());
        FileWriterTest.write("data.text", " "+email.getAddress());
        if (email.getRel() != null) {
          System.out.print(" rel:" + email.getRel());
          FileWriterTest.write("data.text"," rel:" + email.getRel());
        }
        if (email.getLabel() != null) {
          System.out.print(" label:" + email.getLabel());
          FileWriterTest.write("data.text"," label:" + email.getLabel());
        }
        if (email.getPrimary()) {
          System.out.print(" (primary) ");
         FileWriterTest.write("data.text"," (primary) ");
        }
        System.out.print("\n");
      }
      // System.out.println("IM addresses:");
      FileWriterTest.write("data.text","IM addresses:");
      for (Im im : entry.getImAddresses()) {
        System.out.print(" " + im.getAddress());
        FileWriterTest.write("data.txt", " " + im.getAddress());
        if (im.getLabel() != null) {
          System.out.print(" label:" + im.getLabel());
          FileWriterTest.write("data.txt"," label:" + im.getLabel());
        }
        if (im.getRel() != null) {
          System.out.print(" rel:" + im.getRel());
          FileWriterTest.write("data.txt"," rel:" + im.getRel());
        }
        if (im.getProtocol() != null) {
          System.out.print(" protocol:" + im.getProtocol());
          FileWriterTest.write("data.txt"," protocol:" + im.getProtocol());
        }
        if (im.getPrimary()) {
          System.out.print(" (primary) ");
          FileWriterTest.write("data.txt"," (primary) ");
        }
        System.out.print("\n");
      }
      // System.out.println("Groups:");
      FileWriterTest.write("data.txt","Groups:");
      for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
        String groupHref = group.getHref();
        // System.out.println("  Id: " + groupHref);
        FileWriterTest.write("data.txt","  Id: " + groupHref);
      }
      // System.out.println("Extended Properties:");
      FileWriterTest.write("data.txt","Extended Properties:");
      for (ExtendedProperty property : entry.getExtendedProperties()) {
        if (property.getValue() != null) {
          // System.out.println("  " + property.getName() + "(value) = " +
           //   property.getValue());
          FileWriterTest.write("data.txt","  " + property.getName() + "(value) = " +
              property.getValue());
        } else if (property.getXmlBlob() != null) {
          // System.out.println("  " + property.getName() + "(xmlBlob)= " +
           //   property.getXmlBlob().getBlob());
          FileWriterTest.write("data.txt","  " + property.getName() + "(xmlBlob)= " +
              property.getXmlBlob().getBlob());
        }
      }
      Link photoLink = entry.getContactPhotoLink();
      String photoLinkHref = photoLink.getHref();
      // System.out.println("Photo Link: " + photoLinkHref);
      FileWriterTest.write("data.txt","Photo Link: " + photoLinkHref);
      
      if (photoLink.getEtag() != null) {
        // System.out.println("Contact Photo's ETag: " + photoLink.getEtag());
        FileWriterTest.write("data.txt","Contact Photo's ETag: " + photoLink.getEtag());
      }
      // System.out.println("Contact's ETag: " + entry.getEtag());
      FileWriterTest.write("data.txt","Contact's ETag: " + entry.getEtag());
      // System.out.println("END");
      FileWriterTest.write("data.txt","END");
      
    }
   
}
  public static DefaultTableModel getAllContacts(ContactsService myService, DefaultTableModel tableModel)
            throws ServiceException, IOException {
      
     String row[] = {"Name", "Email","Phone No"};
      String row2[] = new String[3];
      //row2[2]="phn";
    // Request the feed
    URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
    ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);
        //// System.out.println("xml string: "+resultFeed.toString());
   
   tableModel.setRowCount(0);   
   tableModel.addRow(row);
    // Print the results
    // System.out.println(resultFeed.getTitle().getPlainText());
    for (ContactEntry entry : resultFeed.getEntries()) {
        // System.out.println("Start");
        FileWriterTest.write("data.txt", "Start");
        // System.out.println("contactId: " + entry.getId());
        FileWriterTest.write("data.txt", "contactId: " + entry.getId());
      if (entry.hasName()) {
        Name name = entry.getName();
        if (name.hasFullName()) {
          String fullNameToDisplay = name.getFullName().getValue();
          if (name.getFullName().hasYomi()) {
            fullNameToDisplay += " (" + name.getFullName().getYomi() + ")";
          }
        row2[0]=fullNameToDisplay;
        // System.out.println("\t\t" + fullNameToDisplay);
        } else {
            row2[0]="no full name found";
          // System.out.println("\t\t (no full name found)");
          }
        if (name.hasNamePrefix()) {
          // System.out.println("\t\t" + name.getNamePrefix().getValue());
          FileWriterTest.write("data.txt",name.getNamePrefix().getValue() );
        } else {
          // System.out.println("\t\t (no name prefix found)");
          FileWriterTest.write("data.txt","(no name prefix found)");
        }
        if (name.hasGivenName()) {
          String givenNameToDisplay = name.getGivenName().getValue();
          if (name.getGivenName().hasYomi()) {
            givenNameToDisplay += " (" + name.getGivenName().getYomi() + ")";
          }
          // System.out.println("\t\t" + givenNameToDisplay);
          FileWriterTest.write("data.txt",givenNameToDisplay);
        } else {
          // System.out.println("\t\t (no given name found)");
          FileWriterTest.write("data.txt","(no given name found)");
        }
        if (name.hasAdditionalName()) {
          String additionalNameToDisplay = name.getAdditionalName().getValue();
          if (name.getAdditionalName().hasYomi()) {
            additionalNameToDisplay += " (" + name.getAdditionalName().getYomi() + ")";
          }
          // System.out.println("\t\t" + additionalNameToDisplay);
          FileWriterTest.write("data.txt",additionalNameToDisplay);
        } else {
          // System.out.println("\t\t (no additional name found)");
          FileWriterTest.write("data.txt","(no additional name found)");
        }
        if (name.hasFamilyName()) {
          String familyNameToDisplay = name.getFamilyName().getValue();
          if (name.getFamilyName().hasYomi()) {
            familyNameToDisplay += " (" + name.getFamilyName().getYomi() + ")";
          }
          // System.out.println("\t\t" + familyNameToDisplay);
          FileWriterTest.write("data.txt",familyNameToDisplay);
        } else {
          // System.out.println("\t\t (no family name found)");
          FileWriterTest.write("data.txt","(no family name found)");
        }
        if (name.hasNameSuffix()) {
          // System.out.println("\t\t" + name.getNameSuffix().getValue());
          FileWriterTest.write("data.txt",name.getNameSuffix().getValue());
        } else {
          // System.out.println("\t\t (no name suffix found)");
          FileWriterTest.write("data.txt","(no name suffix found)");
        }
      } else {
          row2[0]="no name IS FOUND";
        // System.out.println("\t (no name found)");
        FileWriterTest.write("data.txt","(no name found)");
      }
      // System.out.println("Email addresses:");
      FileWriterTest.write("data.txt","Email addresses:");
      
      for (Email email : entry.getEmailAddresses()) {
          row2[1]=email.getAddress();
        //  tableModel.addRow(row2);
          
      //  System.out.print(" " + email.getAddress());
        if (email.getRel() != null) {
       //   System.out.print(" rel:" + email.getRel());
          FileWriterTest.write("data.text"," rel:" + email.getRel());
        }
        if (email.getLabel() != null) {
        //  System.out.print(" label:" + email.getLabel());
          FileWriterTest.write("data.text"," label:" + email.getLabel());
        }
        if (email.getPrimary()) {
      //    System.out.print(" (primary) ");
         FileWriterTest.write("data.text"," (primary) ");
        }
        System.out.print("\n");
      }
      for (Email email : entry.getEmailAddresses()) {
          int CT=0;
          for(PhoneNumber phoneNumber: entry.getPhoneNumbers())
          {
              CT++;
              System.out.println("phone ------" +phoneNumber.toString());
          if(phoneNumber.getPhoneNumber().toString().length()>0 ){
          row2[2]=phoneNumber.getPhoneNumber().toString();
           System.out.println("ound"+ row2[2]);
          //tableModel.addRow(row2);
          }
          
          
          }
          if (CT ==0)
          {
              row2[2]="No phone";
           System.out.println("ound"+ row2[2]);
          //tableModel.addRow(row2);
          
          
          }    
              
          
          System.out.println(row2[2]+ "outside");
          tableModel.addRow(row2);
        System.out.print("\n");
      }
      
      // System.out.println("IM addresses:");
      FileWriterTest.write("data.text","IM addresses:");
      for (Im im : entry.getImAddresses()) {
        System.out.print(" " + im.getAddress());
        FileWriterTest.write("data.txt", " " + im.getAddress());
        if (im.getLabel() != null) {
          System.out.print(" label:" + im.getLabel());
          FileWriterTest.write("data.txt"," label:" + im.getLabel());
        }
        if (im.getRel() != null) {
          System.out.print(" rel:" + im.getRel());
          FileWriterTest.write("data.txt"," rel:" + im.getRel());
        }
        if (im.getProtocol() != null) {
          System.out.print(" protocol:" + im.getProtocol());
          FileWriterTest.write("data.txt"," protocol:" + im.getProtocol());
        }
        if (im.getPrimary()) {
          System.out.print(" (primary) ");
          FileWriterTest.write("data.txt"," (primary) ");
        }
        System.out.print("\n");
      }
      
      // System.out.println("Groups:");
      FileWriterTest.write("data.txt","Groups:");
      for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
        String groupHref = group.getHref();
        // System.out.println("  Id: " + groupHref);
        FileWriterTest.write("data.txt","  Id: " + groupHref);
      }
      // System.out.println("Extended Properties:");
      FileWriterTest.write("data.txt","Extended Properties:");
      for (ExtendedProperty property : entry.getExtendedProperties()) {
        if (property.getValue() != null) {
          // System.out.println("  " + property.getName() + "(value) = " +
          //    property.getValue());
          FileWriterTest.write("data.txt","  " + property.getName() + "(value) = " +
              property.getValue());
        } else if (property.getXmlBlob() != null) {
          // System.out.println("  " + property.getName() + "(xmlBlob)= " +
           //   property.getXmlBlob().getBlob());
          FileWriterTest.write("data.txt","  " + property.getName() + "(xmlBlob)= " +
              property.getXmlBlob().getBlob());
        }
      }
      Link photoLink = entry.getContactPhotoLink();
      String photoLinkHref = photoLink.getHref();
      // System.out.println("Photo Link: " + photoLinkHref);
      FileWriterTest.write("data.txt","Photo Link: " + photoLinkHref);
      
      if (photoLink.getEtag() != null) {
        // System.out.println("Contact Photo's ETag: " + photoLink.getEtag());
        FileWriterTest.write("data.txt","Contact Photo's ETag: " + photoLink.getEtag());
      }
      // System.out.println("Contact's ETag: " + entry.getEtag());
      FileWriterTest.write("data.txt","Contact's ETag: " + entry.getEtag());
      // System.out.println("END");
      FileWriterTest.write("data.txt","END");
      
    }
return tableModel;
}
    public static com.google.gdata.data.contacts.ContactEntry retrieveContact(ContactsService myService, String contactId)
            throws IOException, ServiceException {
        
        String delims = "[/]";
        String[] tokens = contactId.split(delims);
        
        String baseId = tokens[tokens.length-1];
        
        // System.out.println("Base ID: " + baseId);
        
        com.google.gdata.data.contacts.ContactEntry contact =
            myService.getEntry(new URL("https://www.google.com/m8/feeds/contacts/default/full/"+baseId),
                com.google.gdata.data.contacts.ContactEntry.class);
        // Do something with the contact.
        // System.out.println("Id: " + contact.getId());
        
        
        if (contact.hasName()) {
            Name name = contact.getName();
            if (name.hasFullName()) {
              String fullNameToDisplay = name.getFullName().getValue();
              if (name.getFullName().hasYomi()) {
                fullNameToDisplay += " (" + name.getFullName().getYomi() + ")";
              }

            // System.out.println("\t\t" + fullNameToDisplay);
            } else {
              // System.out.println("\t\t (no full name found)");
            }
            if (name.hasNamePrefix()) {
              // System.out.println("\t\t" + name.getNamePrefix().getValue());
            } else {
              // System.out.println("\t\t (no name prefix found)");
            }
            if (name.hasGivenName()) {
              String givenNameToDisplay = name.getGivenName().getValue();
              if (name.getGivenName().hasYomi()) {
                givenNameToDisplay += " (" + name.getGivenName().getYomi() + ")";
              }
              // System.out.println("\t\t" + givenNameToDisplay);
            } else {
              // System.out.println("\t\t (no given name found)");
            }
            if (name.hasAdditionalName()) {
              String additionalNameToDisplay = name.getAdditionalName().getValue();
              if (name.getAdditionalName().hasYomi()) {
                additionalNameToDisplay += " (" + name.getAdditionalName().getYomi() + ")";
              }
              // System.out.println("\t\t" + additionalNameToDisplay);
            } else {
              // System.out.println("\t\t (no additional name found)");
            }
            if (name.hasFamilyName()) {
              String familyNameToDisplay = name.getFamilyName().getValue();
              if (name.getFamilyName().hasYomi()) {
                familyNameToDisplay += " (" + name.getFamilyName().getYomi() + ")";
              }
              // System.out.println("\t\t" + familyNameToDisplay);
            } else {
              // System.out.println("\t\t (no family name found)");
            }
            if (name.hasNameSuffix()) {
              // System.out.println("\t\t" + name.getNameSuffix().getValue());
            } else {
              // System.out.println("\t\t (no name suffix found)");
            }
          } else {
            // System.out.println("\t (no name found)");
          }
            // System.out.println("Email addresses:");
          for (Email email : contact.getEmailAddresses()) {
            System.out.print(" " + email.getAddress());
            if (email.getRel() != null) {
              System.out.print(" rel:" + email.getRel());
            }
            if (email.getLabel() != null) {
              System.out.print(" label:" + email.getLabel());
            }
            if (email.getPrimary()) {
              System.out.print(" (primary) ");
            }
            System.out.print("\n");
          }
          // System.out.println("IM addresses:");
          for (Im im : contact.getImAddresses()) {
            System.out.print(" " + im.getAddress());
            if (im.getLabel() != null) {
              System.out.print(" label:" + im.getLabel());
            }
            if (im.getRel() != null) {
              System.out.print(" rel:" + im.getRel());
            }
            if (im.getProtocol() != null) {
              System.out.print(" protocol:" + im.getProtocol());
            }
            if (im.getPrimary()) {
              System.out.print(" (primary) ");
            }
            System.out.print("\n");
          }
          // System.out.println("Groups:");
          for (GroupMembershipInfo group : contact.getGroupMembershipInfos()) {
            String groupHref = group.getHref();
            // System.out.println("  Id: " + groupHref);
          }
          // System.out.println("Extended Properties:");
          for (ExtendedProperty property : contact.getExtendedProperties()) {
            if (property.getValue() != null) {
              // System.out.println("  " + property.getName() + "(value) = " +
               //   property.getValue());
            } else if (property.getXmlBlob() != null) {
              // System.out.println("  " + property.getName() + "(xmlBlob)= " +
               //   property.getXmlBlob().getBlob());
            }
          }
//          // System.out.println(contact.getStructuredPostalAddresses().get(0).getCity());
//
//          // System.out.println(contact.getStructuredPostalAddresses().get(0).getCountry().getCode());
//          
//          // System.out.println(contact.getStructuredPostalAddresses().get(0).getCountry().getValue());
//          
//          // System.out.println(contact.getStructuredPostalAddresses().get(0).getPostcode());
//          
//          // System.out.println(contact.getStructuredPostalAddresses().get(0).getRegion());
//          
//          // System.out.println(contact.getStructuredPostalAddresses().get(0).getStreet());
          
          
                    
          Link photoLink = contact.getContactPhotoLink();
          String photoLinkHref = photoLink.getHref();
          // System.out.println("Photo Link: " + photoLinkHref);
          if (photoLink.getEtag() != null) {
            // System.out.println("Contact Photo's ETag: " + photoLink.getEtag());
          }
          // System.out.println("Contact's ETag: " + contact.getEtag());
          // System.out.println("END");

        return contact;
    }
    
    public static com.google.gdata.data.contacts.ContactEntry createContact(ContactsService myService) 
            throws MalformedURLException, IOException, ServiceException {
        // Create the entry to insert.
        com.google.gdata.data.contacts.ContactEntry contact = 
                new com.google.gdata.data.contacts.ContactEntry();
        // Set the contact's name.
        Name name = new Name();
        final String NO_YOMI = null;
        name.setFullName(new FullName("Elizabeth Bennet", NO_YOMI));
        name.setGivenName(new GivenName("Elizabeth", NO_YOMI));
        name.setFamilyName(new FamilyName("Bennet", NO_YOMI));
        contact.setName(name);
       

        contact.setContent(new PlainTextConstruct("Notes"));
        // Set contact's e-mail addresses.
        Email primaryMail = new Email();
        primaryMail.setAddress("liz@gmail.com");
        primaryMail.setDisplayName("E. Bennet");
        primaryMail.setRel("http://schemas.google.com/g/2005#home");
        primaryMail.setPrimary(true);
        contact.addEmailAddress(primaryMail);
        Email secondaryMail = new Email();
        secondaryMail.setAddress("liz@example.com");
        secondaryMail.setRel("http://schemas.google.com/g/2005#work");
        secondaryMail.setPrimary(false);
        contact.addEmailAddress(secondaryMail);
        // Set contact's phone numbers.
        PhoneNumber primaryPhoneNumber = new PhoneNumber();
        primaryPhoneNumber.setPhoneNumber("(206)555-1212");
        primaryPhoneNumber.setRel("http://schemas.google.com/g/2005#work");
        primaryPhoneNumber.setPrimary(true);
        contact.addPhoneNumber(primaryPhoneNumber);
        PhoneNumber secondaryPhoneNumber = new PhoneNumber();
        secondaryPhoneNumber.setPhoneNumber("(206)555-1213");
        secondaryPhoneNumber.setRel("http://schemas.google.com/g/2005#home");
        contact.addPhoneNumber(secondaryPhoneNumber);
        // Set contact's IM information.
        Im imAddress = new Im();
        imAddress.setAddress("liz@gmail.com");
        imAddress.setRel("http://schemas.google.com/g/2005#home");
        imAddress.setProtocol("http://schemas.google.com/g/2005#GOOGLE_TALK");
        imAddress.setPrimary(true);
        contact.addImAddress(imAddress);
        // Set contact's postal address.
        StructuredPostalAddress postalAddress = new StructuredPostalAddress();
        postalAddress.setStreet(new Street("1600 Amphitheatre Pkwy"));
        postalAddress.setCity(new City("Mountain View"));
        postalAddress.setRegion(new Region("CA"));
        postalAddress.setPostcode(new PostCode("94043"));
        postalAddress.setCountry(new Country("US", "United States"));
        postalAddress.setFormattedAddress(new FormattedAddress("1600 Amphitheatre Pkwy Mountain View"));
        postalAddress.setRel("http://schemas.google.com/g/2005#work");
        postalAddress.setPrimary(true);
        contact.addStructuredPostalAddress(postalAddress);
        // Ask the service to insert the new entry
        URL postUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
                                
        com.google.gdata.data.contacts.ContactEntry createdContact = myService.insert(postUrl, contact);
        // System.out.println("Created Contact's ID: " + createdContact.getId());
        return createdContact;
                
    }
    
    public static com.google.gdata.data.contacts.ContactEntry createNewContact(
                ContactsService myService,
                String fullName,
                String givenName,
                String familyName,
                String email,
                String displayName,
                String phoneNumber,
                String addressStreet,
                String addressCity,
                String addressRegion,
                String addressPostCode,
                String addressCountryFullName,
                String addressCountryShortName
    
    ) throws MalformedURLException, IOException, ServiceException {
        // Create the entry to insert.
        com.google.gdata.data.contacts.ContactEntry contact = 
                new com.google.gdata.data.contacts.ContactEntry();
        // Set the contact's name.
        Name name = new Name();
        final String NO_YOMI = null;
        name.setFullName(new FullName(fullName, NO_YOMI));
        name.setGivenName(new GivenName(givenName, NO_YOMI));
        name.setFamilyName(new FamilyName(familyName, NO_YOMI));
        contact.setName(name);
       

        contact.setContent(new PlainTextConstruct("Notes"));
        // Set contact's e-mail addresses.
        Email primaryMail = new Email();
        primaryMail.setAddress(email);
        primaryMail.setDisplayName(displayName);
        primaryMail.setRel("http://schemas.google.com/g/2005#home");
        primaryMail.setPrimary(true);
        contact.addEmailAddress(primaryMail);
        /*
        Email secondaryMail = new Email();
        secondaryMail.setAddress("liz@example.com");
        secondaryMail.setRel("http://schemas.google.com/g/2005#work");
        secondaryMail.setPrimary(false);
        contact.addEmailAddress(secondaryMail);
        */
        // Set contact's phone numbers.
        PhoneNumber primaryPhoneNumber = new PhoneNumber();
        primaryPhoneNumber.setPhoneNumber(phoneNumber);
        primaryPhoneNumber.setRel("http://schemas.google.com/g/2005#work");
        primaryPhoneNumber.setPrimary(true);
        contact.addPhoneNumber(primaryPhoneNumber);
        /*
        PhoneNumber secondaryPhoneNumber = new PhoneNumber();
        secondaryPhoneNumber.setPhoneNumber("(206)555-1213");
        secondaryPhoneNumber.setRel("http://schemas.google.com/g/2005#home");
        contact.addPhoneNumber(secondaryPhoneNumber);
        */
        // Set contact's IM information.
        /*
        Im imAddress = new Im();
        imAddress.setAddress("liz@gmail.com");
        imAddress.setRel("http://schemas.google.com/g/2005#home");
        imAddress.setProtocol("http://schemas.google.com/g/2005#GOOGLE_TALK");
        imAddress.setPrimary(true);
        contact.addImAddress(imAddress);
        */
        // Set contact's postal address.
        StructuredPostalAddress postalAddress = new StructuredPostalAddress();
        postalAddress.setStreet(new Street(addressStreet));
        postalAddress.setCity(new City(addressCity));
        postalAddress.setRegion(new Region(addressRegion));
        postalAddress.setPostcode(new PostCode(addressPostCode));
        postalAddress.setCountry(new Country(addressCountryShortName, addressCountryFullName));
        //postalAddress.setCountry(new Country());
        //postalAddress.setFormattedAddress(new FormattedAddress("1600 Amphitheatre Pkwy Mountain View"));
        postalAddress.setRel("http://schemas.google.com/g/2005#work");
        postalAddress.setPrimary(true);
        contact.addStructuredPostalAddress(postalAddress);
        // Ask the service to insert the new entry
        URL postUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        
        com.google.gdata.data.contacts.ContactEntry createdContact = myService.insert(postUrl, contact);
        // System.out.println("Created Contact's ID: " + createdContact.getId());
        return createdContact;
        
        
        
                
    }
    
    public static com.google.gdata.data.contacts.ContactEntry updateContactName(
        ContactsService myService, URL contactURL)
        throws ServiceException, IOException {
        //pass the etag with the service
      // First retrieve the contact to updated.
      com.google.gdata.data.contacts.ContactEntry entryToUpdate = 
              myService.getEntry(contactURL, com.google.gdata.data.contacts.ContactEntry.class);
      entryToUpdate.getName().getFullName().setValue("New Name");
      entryToUpdate.getName().getGivenName().setValue("New");
      entryToUpdate.getName().getFamilyName().setValue("Name");
      URL editUrl = new URL(entryToUpdate.getEditLink().getHref());
      try {
        com.google.gdata.data.contacts.ContactEntry contactEntry = myService.update(editUrl, entryToUpdate);
        // System.out.println("Updated: " + contactEntry.getUpdated().toString());
        return contactEntry;
      } catch (PreconditionFailedException e) {
        // Etags mismatch: handle the exception.
      }
      return null;
    }
    
    public static com.google.gdata.data.contacts.ContactEntry updateContact(
        ContactsService myService, 
        URL contactURL,
                String fullName,
                String givenName,
                String familyName,
                String email,
                String displayName,
                String phoneNumber,
                String addressStreet,
                String addressCity,
                String addressRegion,
                String addressPostCode,
                String addressCountryFullName,
                String addressCountryShortName)
        throws ServiceException, IOException {
        //pass the etag with the service
      // First retrieve the contact to updated.
      com.google.gdata.data.contacts.ContactEntry entryToUpdate = 
              myService.getEntry(contactURL, com.google.gdata.data.contacts.ContactEntry.class);
      entryToUpdate.getName().getFullName().setValue(fullName);
      entryToUpdate.getName().getGivenName().setValue(givenName);
      entryToUpdate.getName().getFamilyName().setValue(familyName);
      
      Email EmailAddress = new Email();
      EmailAddress.setAddress(email);
      EmailAddress.setDisplayName(displayName);
      EmailAddress.setRel("http://schemas.google.com/g/2005#home");
      EmailAddress.setPrimary(true);
        
      entryToUpdate.getEmailAddresses().set(0, EmailAddress);
      
      StructuredPostalAddress postalAddress = new StructuredPostalAddress();
        postalAddress.setStreet(new Street(addressStreet));
        postalAddress.setCity(new City(addressCity));
        postalAddress.setRegion(new Region(addressRegion));
        postalAddress.setPostcode(new PostCode(addressPostCode));
        postalAddress.setCountry(new Country(addressCountryShortName, addressCountryFullName));
        //postalAddress.setCountry(new Country());
        //postalAddress.setFormattedAddress(new FormattedAddress("1600 Amphitheatre Pkwy Mountain View"));
        postalAddress.setRel("http://schemas.google.com/g/2005#work");
        postalAddress.setPrimary(true);
      entryToUpdate.getStructuredPostalAddresses().set(0, postalAddress);
      
      URL editUrl = new URL(entryToUpdate.getEditLink().getHref());
      
      try {
        com.google.gdata.data.contacts.ContactEntry contactEntry = myService.update(editUrl, entryToUpdate);
        // System.out.println("Updated: " + contactEntry.getUpdated().toString());
        return contactEntry;
      } catch (PreconditionFailedException e) {
        // Etags mismatch: handle the exception.
      }
      return null;
    }
    
    public static void deleteContact(ContactsService myService, URL contactURL)
        throws ServiceException, IOException {
        //etag has to be set with service
      // Retrieving the contact is required in order to get the Etag.
      com.google.gdata.data.contacts.ContactEntry contact = 
              myService.getEntry(contactURL, com.google.gdata.data.contacts.ContactEntry.class);

      try {
        contact.delete();
        // System.out.println("Contact Deleted successfully");
      } catch (PreconditionFailedException e) {
        // Etags mismatch: handle the exception.
      }
    }
    
    
    public static com.google.gdata.client.contacts.ContactsService
        getContactsService() throws IOException, GeneralSecurityException {
        Credential credential = authorize();
        
        com.google.gdata.client.contacts.ContactsService service = 
                new com.google.gdata.client.contacts.ContactsService(APPLICATION_NAME);
        
        service.setOAuth2Credentials(credential);
        // System.out.println("accesstoken: "+credential.getAccessToken());
        service.setHeader("Authorization", "Bearer "+credential.getAccessToken());
        return service;
    }
        
        public static void main(String[] args)
                throws ServiceException, IOException, GeneralSecurityException{
            
        
        ContactsService myService =
            ContactsQuickstart.getContactsService();
        
        
        //check allContacts
        ContactsQuickstart.printAllContacts(myService);
        
        //check single contact
        /*
        String testContact = "http://www.google.com/m8/feeds/contacts/shoron.dutta321%40gmail.com/base/3504c6c788d4e0b4";
        com.google.gdata.data.contacts.ContactEntry singleContact = 
                ContactsQuickstart.retrieveContact(myService, testContact);
        */
        //delete a contact
        
        /*URL contactURL = new URL("http://www.google.com/m8/feeds/contacts/shoron.dutta321%40gmail.com/base/5b77a170087aa467");
        ContactsQuickstart.deleteContact(myService, contactURL);
        */
     
        
        //create contact example
        
        /*ContactsQuickstart.createNewContact(myService,
                "tara", "given name", "family name", "tara@gmail.com",
                "DisplayName", "09812231313", "Street", "Sylhet", "Asia", "1215", "Bangladesh", "BN");
        */
        //update an existing contact
        /*
        URL contactURL = new URL("http://www.google.com/m8/feeds/contacts/shoron.dutta321%40gmail.com/base/60c23efd0ea4b955");
        ContactsQuickstart.updateContact(myService, contactURL,
        "arab", "given name up", "family name", "shoron.dutta@yahoo.com",
                "DisplayName", "09812231313", "Street", "Sylhet up", "Asia up ", "1215", "Bangladesh", "BN");
        */
        
}
    
}

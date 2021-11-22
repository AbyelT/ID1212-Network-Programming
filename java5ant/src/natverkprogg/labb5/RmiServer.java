package natverkprogg.labb5;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;
import java.util.Properties;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart; 
import javax.mail.*;
import java.io.*;

public class RmiServer extends UnicastRemoteObject implements RmiServerInf {

    public RmiServer() throws RemoteException {
        super(0); // required to avoid the 'rmic' step, see below
    }

    public String getMessage() {
        try{
            String host = "webmail.kth.se";
            String username = "abyel";
            String password = "";
            
            File file = new File("C:\\Users\\Abyel Tesfay\\Desktop\\pass.txt"); //Super safe way of storing password :)
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            password = br.readLine();
            //System.out.println(password);
            
            Properties props = new Properties();
            props.setProperty("mail.imap.ssl.enable", "true");

            Session session = javax.mail.Session.getInstance(props);
            Store store = session.getStore("imap");
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            Message firstMsg = messages[messages.length-1];
            String result = "";
            if(firstMsg.isMimeType("text/plain")) {
               result = firstMsg.getContent().toString();
            }
            else if (firstMsg.isMimeType("multipart/*")) {
                MimeMultipart mimeMultipart = (MimeMultipart) firstMsg.getContent();
                int numberOfParts = mimeMultipart.getCount();
                for (int partCount = 0; partCount < numberOfParts; partCount++) {
                    MimeBodyPart part = (MimeBodyPart) mimeMultipart.getBodyPart(partCount);
                       result += part.getContent().toString();
               }
                inbox.close(false);
                store.close();
            }
            return result;  
        } catch (IOException e) {
            System.out.println(e);
        } catch (NoSuchProviderException ex) {
            System.out.println(ex);
        } catch (MessagingException ex) {
            System.out.println(ex);
        } 
        return "failed";
    }
    
    public static void main(String args[]) throws Exception {
        System.out.println("RMI server started, port 1099");

        try { //special exception handler for registry creation
            LocateRegistry.createRegistry(1099);                //skapa registry i localhost
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }
           
        //Instantiate RmiServer
        RmiServer server = new RmiServer();                     //skapa ny instans av RmiServer

        // Bind this object instance to the name "RmiServer"
        Naming.rebind("//localhost/RmiServer", server);
        System.out.println("PeerServer bound in registry");
    }
}
package extraassign;
import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

class AndroidHandler implements Runnable {
    private Socket clientSoc;
    
    public AndroidHandler(Socket s) {
        this.clientSoc = s;	
    }    
    
    public void run() {
        try {
            //Part 1: get the message
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
            
            //Part 2: write to client
            PrintWriter outp = new PrintWriter(clientSoc.getOutputStream(), true);
            BufferedReader inp = new BufferedReader(new InputStreamReader(clientSoc.getInputStream()));
            //outp.println("Incomming message!\n");
            outp.println("Server: " + result);
            System.out.println("Server: " + result);
            clientSoc.close();
        } catch (Exception e) {
            System.out.println("IO error " + e);
        }
    }
}


public class androidServer {
     public static void main(String[] args) throws IOException{
        System.out.println("Creating Serversocket: 2233");
	ServerSocket ss = new ServerSocket(2233);
         
        for(;;) {
            Socket socket = ss.accept();
            System.out.println("new client");
            AndroidHandler h = new AndroidHandler(socket);
            new Thread(h).start();
        }
    }
}

package lecture4;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Base64;
import javax.net.ssl.*;

public class ClientSmtp {
    public static void main(String[] args) throws IOException{
        SSLSocketFactory sf = (SSLSocketFactory)SSLSocketFactory.getDefault();  //Returnera default SSL Socket Factory, en klass som skapar sockets  
        Socket socket = null;
        String host = "<the smtp server of kth here>";    
        int port = "<the port number>"; 
        PrintWriter writer = null;                         
        BufferedReader reader = null; 
         
        try{
            socket = new Socket(host, port);
            writer = new PrintWriter(socket.getOutputStream(), true);                             //läsa
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //skriva
        }
        catch(MalformedURLException e){         
            System.out.println(e.getMessage());            //Fel url?
        }
        catch(IOException e){
            System.out.println(e.getMessage());            //Någon IO fel
        }
        
        writer.println("EHLO smtp.kth.se");
        writer.flush();   
        writer.println("STARTTLS");
        writer.flush();
        String str = "";
        try{ 
            while((str=reader.readLine()) != null) {
                System.out.println(str); 
                if (str.equals("220 2.0.0 Ready to start TLS")) {
                    break;
                }
            }   
            SSLSocket sslsocket = (SSLSocket)sf.createSocket(socket, socket.getInetAddress().getHostAddress(), socket.getPort(), true);
            writer = new PrintWriter(sslsocket.getOutputStream(), true);                             //läsa
            reader = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));    //skriva
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        System.out.println("\nSkapar ny connection:"); 
        writer.println("EHLO smtp.kth.se");
        writer.flush();   
        writer.println("AUTH LOGIN");
        String user = "<username here>";
        String pass = "<password here>";
        String user64 = Base64.getEncoder().encodeToString(user.getBytes());
        String pass64 = Base64.getEncoder().encodeToString(pass.getBytes());
        writer.flush();   
        //System.out.println("\n" + user64); 
        //System.out.println("\n" + pass64);      
        //byte[] decodedBytes1 = Base64.getDecoder().decode(user64);
        //byte[] decodedBytes2 = Base64.getDecoder().decode(pass64);

	//String deo1= new String(decodedBytes1);
        //String deo2 = new String(decodedBytes2);
        
        //System.out.println(deo1 + ", " + deo2); 

        str = "";
        try {
            while((str=reader.readLine()) != null) {
                System.out.println(str); 
                if (str.substring(0,3).equals("334")) {
                    writer.println(user64);
                    writer.flush();   
                    writer.println(pass64);
                    writer.flush();  
                    System.out.println(reader.readLine());  
                    System.out.println(reader.readLine());     
                    writer.println("MAIL FROM:<>");
                    writer.flush();  
                    System.out.println(reader.readLine());     

                    writer.println("RCPT TO:<>");
                    writer.flush();  
                    System.out.println(reader.readLine());     

                    writer.println("RCPT TO:<>");
                    writer.flush();  
                    System.out.println(reader.readLine());     

                    writer.println("DATA");
                    //writer.flush(); 
                    System.out.println(reader.readLine());     

                    writer.println("This is a test message for labb 4, smtp works!");
                    writer.println(".");
                    writer.flush(); 
                    System.out.println(reader.readLine());     

                    writer.println("QUIT");
                    writer.flush();   

                }
                if(str.equals("250 OK")) {
                    if(str.equals("221 BYE")) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());  
        }
        
        writer.close();
        reader.close();
        socket.close();
        System.out.println("JOB FINISHED!");
    }
}

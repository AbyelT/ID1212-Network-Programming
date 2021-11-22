package lecture4;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class Client{
    public static void main(String[] args){
        SSLSocketFactory sf = (SSLSocketFactory)SSLSocketFactory.getDefault();  //Returnera default SSL Socket Factory, en klass som skapar sockets  
        for(int i = 0; i < sf.getSupportedCipherSuites().length; i++) {}           //Skriv ut alla stödda krypteringsalgoritmer
            //System.out.println("SF " + i + ":" + sf.getSupportedCipherSuites()[i]);    
        HttpsURLConnection.setDefaultSSLSocketFactory(sf);                      //Sätt default socket factory till en httpurlcon.. klass
        SSLSocket socket = null;
        //String host = "www.lu.se";
        String host = "webmail.kth.se";             //host url
        //String host = "localhost";
        try{
            socket = (SSLSocket)sf.createSocket(host,993); //Skapa SSL socket till host med port, 443 förr
        }
        catch(MalformedURLException e){         
            System.out.println(e.getMessage());            //Fel url?
        }
        catch(IOException e){
            System.out.println(e.getMessage());            //Någon IO fel
        }
        for(int i = 0; i < socket.getSupportedCipherSuites().length; i++) {}
            //System.out.println("SS " + i + ": " + socket.getSupportedCipherSuites()[i]);        //Printa alla stödda kryptering av Socket
        //String[] cipher = {"SSL_DH_anon_WITH_RC4_128_MD5"};
        //String[] cipher = {"TLS_AES_128_GCM_SHA256"};
        String[] cipher = {"TLS_RSA_WITH_AES_128_CBC_SHA"};
        socket.setEnabledCipherSuites(cipher);             //Sätt socket till vald
        
        for(int i = 0; i < socket.getEnabledCipherSuites().length; i++) {}
            //System.out.println("SE" + i + ":" + socket.getEnabledCipherSuites()[i]);
        
        PrintWriter writer = null;                         
        BufferedReader reader = null;                      
        try{
            socket.startHandshake();                       //Utför TCP handshake (connection börjar)
        }
        catch(IOException e){
            System.out.println("*************" + e.getMessage());
        }
        try{
            //writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
            writer = new PrintWriter(socket.getOutputStream());                             //läsa
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //skriva
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        //writer.println("a004 fetch 1 body[header]");
        //writer.println("a005 uid fetch 1:1 flags");
        //System.out.println("Login sent");
        //writer.println("Host: " + host);
        //writer.println("");
        //writer.println("a003 fetch 17 all");
        //writer.println("a004 fetch 17 body[header]");
        //writer.println("a005 fetch 17 body[text]");
        
        String pass = "";
        writer.println("a001 login abyel " + pass);
        writer.println("a002 examine inbox");
        writer.println("a003 search all");
        writer.flush();

        try{
            String str;
            String[] numbers;
            int firstMsg = 1;
            while((str=reader.readLine()) != null) {
                System.out.println(str);
                if(str.substring(2,8).equals("SEARCH")) {
                    numbers = str.substring(9).split(" ");
                    System.out.println(reader.readLine());
                    firstMsg = Integer.parseInt(numbers[numbers.length-1]);
                    break;
                }
                //System.out.println(str);
            }
            writer.println("a004 fetch " + firstMsg + " body[text]");
            //writer.println("a005 fetch " + firstMsg + " body");
            writer.println("a006 logout");

            writer.flush();
               
            while((str=reader.readLine()) != null) {
                System.out.println(str);
            }
            
            writer.close();
            reader.close();
            socket.close();
            System.out.println("JOB FINISHED!");
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}

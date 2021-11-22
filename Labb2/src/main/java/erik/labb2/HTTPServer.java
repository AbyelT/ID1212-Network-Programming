package erik.labb2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.LinkedList;
import java.util.StringTokenizer;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

class HTTPHandler implements Runnable{
	private SSLSocket clientSoc;
	private LinkedList<GuessGame> games;
	private int cookie;   
        
	public HTTPHandler(SSLSocket s, LinkedList<GuessGame> list, int cookie) {
		this.clientSoc = s;
		this.cookie = cookie;
		this.games = list;
	}

	public void run() {
		//Undersök första request
		try {
            //skapa input/output streams
            BufferedReader Reader = new BufferedReader(new InputStreamReader(clientSoc.getInputStream()));
            //new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(clientSoc.getOutputStream(), true);	
            String str = "";
            try { //FEL HÄR
                //analysera request header
                str = Reader.readLine();
            }catch(IOException e) {
                //Will get error here because our certificate sucks.
//                System.out.println("Fel!");
//                System.out.println(str);
//                e.printStackTrace();
            }
            StringTokenizer s = new StringTokenizer(str, " =");
            
            //om request har en favicon så ingorera, annrs kör normalt
            if(s.nextToken().equals("GET")) {
                if(s.nextToken().equals("/favicon.ico")){
                    out.close();
                    clientSoc.close();
                    return;
                }
                else { 
                    //http response
                    String httpOK = "HTTP/1.1 200 OK\r\n";
                    httpOK += "Server: Cookies 1.1\r\n";
                    httpOK += "Content-Type: text/html; Charset=utf-8\r\n";

                    try {
                            String response = "";
                            String foundCookie = "";
                            String numberOfGuesses = "";

                            //Find the number and convert to integer
                            int numb = Integer.parseInt(s.nextToken());
                            while((str=Reader.readLine()) != null && str.length() > 0) {

                                    //If we find a cookie in the HTTP headers
                                    if (str.substring(0, 6).equals("Cookie")) {
                                        //Get cookie
                                        foundCookie = str.split("[ ]")[1];
                                    }
                            }

                            //Go through list of games
                            for (GuessGame game : this.games) {

                                if (game.getGameID().equals(foundCookie)){
                                    response = game.PlayGame(numb);
                                    numberOfGuesses = " Number of guesses: " + game.getAmountOfTries();
                                }
                            }

                            //String response = game.PlayGame(numb);
                            httpOK += "Connection: Keep-Alive\r\n\r\n";
                            httpOK += response + numberOfGuesses + "\n"
                                            + "<form>"
                                            + "<input type=text name='number'>"
                                            + "	<input type=submit>"
                                            + "</form>"
                                            + "\r\n";

                            //Write response to client
                            out.println(httpOK);
                    } catch(NumberFormatException e) {
                        //First time a client connects
                        //Generate cookie
                        String theCookie = "ClientId=" + this.cookie;
                        httpOK += "Set-Cookie: " + theCookie + "\r\n";
                        httpOK += "Connection: Keep-Alive\r\n\r\n";
                        httpOK += "Welcome to the number guessing game. " 
                                        + "I'm thinking of a number between 1 and 100. Whats your guess?\n"
                                        + "<form>"
                                        + "<input type=text name='number'>"
                                        + "	<input type=submit>"
                                        + "</form>"
                                        + "\r\n";
                        //Write to client
                        out.println(httpOK);
                        GuessGame game = new GuessGame(theCookie);
                        games.add(game);
                    }
					Reader.close();
					out.close();
					clientSoc.close();						
				}
			}
		} 
		catch (IOException e) {
                    System.out.println("IOException caught!");
                } //the connections is unresponsive = ignore n
	}
}

/**
 * HTTPServer listens after incoming connection from clients and creates
 * new threads for every successful connection with a client.
 */
public class HTTPServer {
    
	public static void main(String[] args) throws IOException {
        SSLServerSocketFactory ssf; // = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
        SSLServerSocket server = null;              //servern
        try{
            KeyStore ks = null;
            ks = KeyStore.getInstance("JKS", "SUN");
            //ks = KeyStore.getInstance("jks");
            InputStream is = null;
            //is = new FileInputStream(new File("C:/Program Files/Java/jdk1.8.0_271/jre/lib/security/cacerts"));
            is = new FileInputStream(new File("C:/users/erikb/.keystore"));             //hämta keystore till inputstream
            char[] pwd = "rootroot".toCharArray();
            ks.load(is,pwd);                                                            //ladda nycklar till keystore    
            SSLContext ctx = SSLContext.getInstance("TLS");                             //?
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());     //?
            kmf.init(ks, pwd);
            ctx.init(kmf.getKeyManagers(), null, null);
            //ctx.init(null, null, null);
            ssf = ctx.getServerSocketFactory();

            //System.out.println("Supported:");
            //for(int i = 0; i < ssf.getSupportedCipherSuites().length; i++) {
            //    System.out.println(ssf.getSupportedCipherSuites()[i]);  
            //}
            //SSLServerSocket ss = null;
            
            server = (SSLServerSocket)ssf.createServerSocket(443);
            //String[] cipher = {"SSL_DH_anon_WITH_RC4_128_MD5"};
            String[] cipher = {"TLS_RSA_WITH_AES_128_CBC_SHA"};
            server.setEnabledCipherSuites(cipher);
            //System.out.println("Choosen:");  
            //Just to check what is enabled, unnecessary
//            for(int i = 0; i < server.getEnabledCipherSuites().length; i++)
//                System.out.println(server.getEnabledCipherSuites()[i]);     


            //socket = (SSLSocket)server.accept();
            //BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //String row = null;
            //while( (row=reader.readLine()) != null)
            //    System.out.println(row);
             //reader.close();

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
       
            LinkedList<GuessGame> list = new LinkedList<GuessGame>();
            //Helper variable used to create a unique cookie
            int Cookie = 0;
            //SSLSocket socket = null;                    //clientern som connectar
            System.out.println("Guessing Game online, port: 443\n");
            for(;;) {
                SSLSocket socket = (SSLSocket)server.accept();
                HTTPHandler h = new HTTPHandler(socket, list, Cookie++);
                System.out.println("new client");
                new Thread(h).start();
            }
	}
}

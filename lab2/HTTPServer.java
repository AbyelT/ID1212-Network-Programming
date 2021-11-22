package lab2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.StringTokenizer;

class HTTPHandler implements Runnable{
	private Socket clientSoc;
	private LinkedList<Integer> cookies;
	private int amountCookie;

	public HTTPHandler(Socket s) {
		clientSoc = s;
		amountCookie = 0;
		cookies = new LinkedList<Integer>();
	}

	public void run() {
		//Undersök först request
		try {
			//skapa input/output streams
			BufferedReader Reader = new BufferedReader(new InputStreamReader(clientSoc.getInputStream()));
			PrintStream out = new PrintStream(clientSoc.getOutputStream());
			GuessGame game = new GuessGame();
			String httpOK = "HTTP/1.1 200 OK\r\n";
			httpOK += "Server: Cookies 1.1\r\n";
			httpOK += "Content-Type: text/html; Charset=utf-8\r\n";
			httpOK += "Set-Cookie: " + cookieGenerate() + "\r\n";
			httpOK += "Connection: Keep-Alive\r\n\r\n";
			
			//analysera request header
			String str = Reader.readLine();
			StringTokenizer s = new StringTokenizer(str, " =");

			//om request har en favicon så ingorera, annrs kör normalt
			if(s.nextToken().equals("GET")) {
				if(s.nextToken().equals("/favicon.ico")){
					out.close();
					clientSoc.close();
					return;
				}
				else { 
					try {
						String u = s.nextToken();
						int numb = Integer.parseInt(u);
						while((str=Reader.readLine()) != null && str.length() > 0) {
							System.out.println(str);
						}
						String response = game.PlayGame(numb);
						httpOK += response + "\n"
								+ "<form>"
								+ "<input type=text name='number'>"
								+ "	<input type=submit>"
								+ "</form>"
								+ "\r\n";
			
						out.println(httpOK);
					} catch(NumberFormatException e) {
						System.out.println(str);
						while((str=Reader.readLine()) != null && str.length() > 0) {
							System.out.println(str);
						}	
						httpOK += "Welcome to the number guess game" 
								+ "I'm thinking of a number between 1 and 100. Whats your guess?\n"
								+ "<form>"
								+ "<input type=text name='number'>"
								+ "	<input type=submit>"
								+ "</form>"
								+ "\r\n";
						out.println(httpOK);
						
					}
					Reader.close();
					out.close();
					clientSoc.close();						
				}
			}
		} 
		catch (IOException e) {} //the connections is unresponsive = ignore n
	}
	
	//generate a cookie
	public String cookieGenerate() {
		int cookieId = amountCookie + 1;
		cookies.add(cookieId);
		return "ClientId=" + cookieId;
	}
}



/**
 * HTTPServer listens after incoming connection from clients and creates
 * new threads for every successful connection with a client.
 */
public class HTTPServer {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(1331);
		System.out.println("Guessing Game online, port: 1331\n");
		for(;;) {
			HTTPHandler h = new HTTPHandler(server.accept());
		    new Thread(h).start();
		}
	}

}

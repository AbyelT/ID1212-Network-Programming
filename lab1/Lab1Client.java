package lab1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
/**
 * Lab1Client lets the user send messages to a chat server connected
 * through an active socket, the class also starts a separate thread
 * for receiving user messages from the server.
 */
public class Lab1Client {
	//Starts the client
	public static void main(String[] args) throws Exception{
        Socket s = new Socket("localhost", 1234);
        PrintStream out = new PrintStream(s.getOutputStream());
        BufferedReader indata = new BufferedReader(new InputStreamReader(System.in));				//from terminal
		BufferedReader chatdata = new BufferedReader(new InputStreamReader(s.getInputStream()));	//from server

		//New thread - for receiving incoming messages
        ClientReceive receive = new ClientReceive(chatdata);
        new Thread(receive).start();
        System.out.print("Enter your username: ");
        String username = indata.readLine();
        String text;
        System.out.print("Enter text to send: ");
        while((text = indata.readLine()) != null){
            out.println(username + ": " + text);
            System.out.print("Enter text to send: ");
        }
        s.shutdownOutput();
        s.close();
    }
}

/**
 * ClientReceive reads incoming messages from the connected socket
 */
class ClientReceive implements Runnable {
	private BufferedReader fromServer;
	
	public ClientReceive(BufferedReader c) {
		fromServer = c;
	}
	
	//runs the thread
	public void run() {
        try {
	        String text;
	        while((text = fromServer.readLine()) != null){
	            System.out.print("\n" + text);
	        }	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}




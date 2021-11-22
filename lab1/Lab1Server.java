package lab1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
/**
 * Lab1Server listens after incoming connections from new client 
 * and creates a new thread for every socket connection established 
 * with a new client. 
 */
public class Lab1Server {
	public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(1234);
        LinkedList<ConnectedClient> clients = new LinkedList<ConnectedClient>();
		System.out.println("Chat Server online, port: 20");
        while(true){
			ConnectedClient c = new ConnectedClient(ss.accept(), clients);
			clients.add(c);
            new Thread(c).start();
        }
    }
}

/*
 * ConnectedClient implements a Runnable interface and forwards all received 
 * messages from a client to all other clients
 */
class ConnectedClient implements Runnable{
	private Socket clientSoc;
	private LinkedList<ConnectedClient> clients;
	
	public ConnectedClient(Socket s, LinkedList<ConnectedClient> l) {
		clientSoc = s;
		clients = l;
	}

	//runs the thread
	public void run() {
        String text = "";
        try {
        	BufferedReader indata = new BufferedReader(new InputStreamReader(clientSoc.getInputStream()));
        	PrintStream output;
        	//listening after new messages
			while((text = indata.readLine()) != null){
			    System.out.println("Received - " + text);
			    //forward the message to all connected clients except the one that sent it
			    for(ConnectedClient client : clients) {
			    	if(client.getClient() != this.clientSoc) {
			    		output = new PrintStream(client.getClient().getOutputStream());
			    		output.println(text);
			    	}
			    }
			} 
			clientSoc.shutdownInput();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//return the connected socket
	public Socket getClient() {
		return clientSoc;
	}
}

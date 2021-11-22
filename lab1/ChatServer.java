package lab1;
import java.net.*;
import java.io.*;

/*
 * ChatServer skickar inkommande meddelanden från en klient till
 * alla uppkopplade klienter. 
 * 
 * ChatServer startar upp en socket som inkommande klienter kan koppla sig till
 * 
 * därefter kör den en tråd som lyssnar på inkommande kopplingar från nya 
 * klienter, för varje klient skapas en egen tråd som tar emot samt skickar
 * meddelanden till klienten.
 */
public class ChatServer {

	//startar ChattServern
	public static void main(String[] args) throws IOException {
		
		String name = "abbe";
		String val = "24x";
		
		HttpCookie test = new HttpCookie(name, val);
		System.out.println("set-cookie: " + test);
	}

}



package lab2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * GuessGame handles the game logic of the guessing game, for every 
 * client it will generate a new random number in which the client 
 * will attempt to guess. if the game ends then a new game can 
 * be started by generating a new number.
 */
public class GuessGame {
	private int number;
	private int amountTry;
	
	public GuessGame() {
		amountTry = 0;
		number = (int) (Math.random() * (101));
	}
	
	public String PlayGame(int guess) {
		String response = "";		
		//kanske ha nån sorts restart när spelet är över t.ex number -1
		else {
			//alla möjliga utfall
			if(guess < number) {
				response = "That's too low. Please guess higher: ";
				amountTry++;
			}
			else if(guess > number) {
				response = "That's too high. Please guess lower: ";
				amountTry++;
			}
			else {
				response = "You made it in " + amountTry + " guess(es)";
				number = -1;
			}
		}
		return response;
	}
	
	//tesing purpouses
	public static void main(String args[]) throws NumberFormatException, IOException {
		System.out.println("Game start\n");
        BufferedReader indata = new BufferedReader(new InputStreamReader(System.in));				//from terminal
		Socket s = new Socket();	
		//GuessGame g = new GuessGame(s);
		
		while(true) {
			String resp = g.PlayGame(Integer.parseInt(indata.readLine()));
			System.out.println(resp);
		}
	}
}




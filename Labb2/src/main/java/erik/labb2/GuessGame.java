package erik.labb2;
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
	private String gameId;
        
	public GuessGame(String gameId) {
		amountTry = 0;
		number = (int) (Math.random() * (101));
                this.gameId = gameId;
	}
        public int getRandomNumber () {
            return this.number;
        }
        
        public String getGameID(){
            return this.gameId;
        }
        
        public int getAmountOfTries() {
            return this.amountTry;
        }
        
	public String PlayGame(int guess) {
            String response = "";

            //kanske ha nån sorts restart när spelet är över t.ex number -1
            //alla möjliga utfall
            if(guess < number) {
                    response = "That's too low. Please guess higher: " + "Cheat: (" + number + ")";
                    amountTry++;
            }
            else if(guess > number) {
                    response = "That's too high. Please guess lower: " + "Cheat: (" + number + ")";
                    amountTry++;
            }
            else {
                    amountTry++;
                    response = "You won! The number was: " + number + ". ";
            }
            
            return response;
	}
}




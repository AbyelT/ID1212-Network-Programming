package erik.labb2;

import java.net.URL;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.io.*;

public class HttpURLConnectionClient{
    
    public static void main(String[] args){
        
        int total = 0;
        int number;
        int roof;
        int floor;
        
        for (int i = 0; i < 100; i++) {
            number = 50;
            roof = 101;
            floor = 0;
            URL url = null;
            HttpURLConnection con = null;
            String Cookie = "";
            BufferedReader infile = null;
            String row = null;
            try{
                url = new URL("http","localhost", 1331, "/");
                con = (HttpURLConnection)url.openConnection();
                con.setRequestProperty("User-Agent","Mozilla");
                con.connect();
                Cookie = con.getHeaderField("Set-Cookie");
                
                infile = new BufferedReader(new InputStreamReader(con.getInputStream()));
                
                while( (row=infile.readLine()) != null){
                    System.out.println(row);
                }
            }
            catch(MalformedURLException e){
                System.out.println("#1 " + e.getMessage());
            }
            catch(IOException e){
                System.out.println("#2 " + e.getMessage());
            }
            for (int j = 1; j <= 20; j++) {
                con = null;
                infile = null;
                row = null;
                try{
                    url = new URL("http","localhost", 1331, "/?number=" + number);
                    con = (HttpURLConnection)url.openConnection();
                    con.setRequestProperty("Cookie", Cookie);
                    con.connect();
                    
                    infile = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    
                    while( (row=infile.readLine()) != null){
                        //System.out.println(row);
                        if (row.contains("That's too high")) {
                            roof = number;
                            if(roof == 1) {
                                number = 0;
                            }
                            else {
                                number = number - ((int) Math.ceil((number-floor)/2));
                            }
                        }
                        else if (row.contains("That's too low")) {
                            floor = number;
                            number = (int) (number + Math.ceil((roof-number)/2));

                        }
                        else if (row.contains("won")){
                            number = -1;
                        }

                    }
                    if (number == -1) { 
                        total += j;
                        break;
                    }
                    else {
                        System.out.println(number);
                    }
                }
                catch(MalformedURLException e){
                    System.out.println("#1 " + e.getMessage());
                }
                catch(IOException e){
                    System.out.println("#2 " + e.getMessage());
                }
            }
        }
        System.out.println("Total number of guesses: " + total);
        System.out.println("Average number of guesses: " + total/100);
    }
}

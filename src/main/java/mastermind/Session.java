package mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Session {
    
    private BufferedReader bufferedReader;

    public Game currentGame;
    private LinkedList<Integer> scoreHistory;
    private int gameSize;
    private boolean duplicatesAllowed;
    //add timer for each game

    

    public Session() throws Exception {
        this.scoreHistory = new LinkedList<>();
        InputStreamReader inputReader = new InputStreamReader(System.in);
        this.bufferedReader = new BufferedReader(inputReader);

        // this.requestSettings();

        // int[] newPattern = PatternGenerator.generatePattern(this.gameSize, this.duplicatesAllowed);
        int[] newPattern = new int[] {0,1,3,3};
        this.currentGame = new Game(newPattern);


    }

    public void startNewGame() throws Exception {
        // get game settings from user input in terminal, provide promp;
        // int gameSize;
        // boolean duplicatesAllowed;

        System.out.print("Would you like to use the settings from your previous game? (y/n): ");
        boolean newSettings = "y".equals(this.bufferedReader.readLine());
        if (newSettings) {
            this.requestSettings();
        }
        
        int[] newPattern = PatternGenerator.generatePattern(this.gameSize, this.duplicatesAllowed);
        Game newGame = new Game(newPattern);

    }

    private void requestSettings() throws IOException {
        System.out.print("How many numbers?: ");
        //ADD ERROR CHECK
        this.gameSize = Integer.parseInt(this.bufferedReader.readLine());
        //ADD ERROR CHECK
        System.out.print("\nDuplicates allowed? (y/n): ");
        this.duplicatesAllowed = "y".equals(this.bufferedReader.readLine());
    }
    
    public static void main(String[] args) throws Exception {
        try {
            Session session = new Session();
            int[] guess = new int[] {0,3,3,1};
            int[] response = session.currentGame.guess(guess);

            for (int i = 0; i < response.length; i++) {
                System.out.print(session.currentGame.PATTERN[i] + ", ");
            }

            System.out.println();
            for (int i = 0; i < response.length; i++) {
                System.out.print(guess[i] + ", ");
            }

            System.out.println();
            for (int i = 0; i < response.length; i++) {
                System.out.print(response[i] + ", ");
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}

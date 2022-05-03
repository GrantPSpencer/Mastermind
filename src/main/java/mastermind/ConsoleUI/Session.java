package mastermind.ConsoleUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import mastermind.Game.Game;
import mastermind.Game.PatternGenerator;
import mastermind.Game.StaticCachedPatternGenerator;

public class Session {
    
    private BufferedReader bufferedReader;

    public Game currentGame;
    private int currentGameSize;
    private boolean currentDuplicatesAllowed;
    private LinkedList<Integer> winningScoreHistory;
    private int gamesPlayedCount;

    public Session() throws Exception {
        //escape code to clear console
        System.out.println("\033[H\033[2J");
        
        this.winningScoreHistory = new LinkedList<>();
        this.gamesPlayedCount = 0;
        InputStreamReader inputReader = new InputStreamReader(System.in);
        this.bufferedReader = new BufferedReader(inputReader);

        //Prompting user input, continuously prompts until valid string is given
        String line = promptUserInput("Start a new game? (y/n): ");
        while (!checkYesNoString(line)) {
            System.out.println("Error, please respond with a \"y\" for yes ,or a \"n\" for no");
            line = promptUserInput("Start a new game? (y/n): ");
        }

        
        //Loop process: Starts new game, has them play that game, then prompts user if they'd like to stop or play another
        //User stays in this loop until they respond "no" to start new game
        while (line.equals("y") || line.equals("yes")) {
            startNewGame();
            line = promptUserInput("Start a new game? (y/n): "); 
            while (!checkYesNoString(line)) {
                System.out.println("Error, please respond with a \"y\" for yes ,or a \"n\" for no");
                line = promptUserInput("Start a new game? (y/n): ");
            }
        }

        //When exiting a session, if the user has played at least 1 game, print the 
            //# of games won and played and the average guess count for games won
        if (this.gamesPlayedCount > 0) {
            if (this.winningScoreHistory.size() == 0) {
                System.out.println("You won 0 of the " + this.gamesPlayedCount + " games you played.");
            } else {
                int winningScoreSum = 0;
                for (int score : this.winningScoreHistory) {
                    winningScoreSum += score;
                }
                System.out.println("You won " + this.winningScoreHistory.size() + " of the " + this.gamesPlayedCount + " games you played, with an average winning guess count of " 
                + (double)(winningScoreSum/this.winningScoreHistory.size()) + " guesses." );
            }
        }


        System.out.println("\nThanks for playing!");
        

    }

    public void startNewGame() throws Exception {
        
        // get game settings from user input in terminal if already played a game, else if first game, prompt for settings;
        boolean newSettingsRequested;
        if (currentGame != null) {
            System.out.print("Would you like to use the settings from your previous game? (y/n): ");
            String line = this.bufferedReader.readLine();
            newSettingsRequested = (line.equals("n") || line.equals("no"));
        } else {
            newSettingsRequested = true;
        }

        if (newSettingsRequested) {
            this.requestSettings();
        }
        
        // int[] newPattern = PatternGenerator.generatePattern(this.currentGameSize, this.currentDuplicatesAllowed);
        int[] newPattern = StaticCachedPatternGenerator.getPattern(this.currentGameSize, this.currentDuplicatesAllowed);
        Game newGame = new Game(newPattern);
        this.playGame(newGame);

    }

    // Potential optimizimation where checkguessString passes back the guessArray, since I'm already converting to numeric value in the check function?
    private void playGame(Game game) throws IOException {        
        this.currentGame = game;
        int[] guessArray;
        String guessString;
        int[] responseArray;
        System.out.println("\nIf you need a hint, just type \"hint\" as your response");

        //Plays until game is over (win or loss)
        while (!game.gameOver) {

            System.out.println("\nYou have " + game.remainingGuesses + " guesses left");
            guessString = promptUserInput("Make your guess (" + game.LENGTH +" numbers): ");

            //Continuously prompts user input until valid guess is inputed
            while (!checkValidGuessString(guessString)) {
                System.out.print("Error: Length of guess must be " + game.LENGTH + " digits long and only contain digits from 0 to 7, please guess again\n");
                guessString = promptUserInput("\nMake your guess (" + game.LENGTH +" numbers): ");
            }

            // if user input == "hint", then give hint and print to terminal
            if (guessString.toLowerCase().equals("hint")) {
                int hint[] = game.giveHint();
                System.out.println("Digit " + hint[1] + " is located at index " + hint[0]);
                continue;
            }


            //Convert guess string to guess array for compatibility with game functions
            guessArray = new int[guessString.length()];
            for (int i = 0; i < guessString.length(); i++) {
                guessArray[i] = Character.getNumericValue(guessString.charAt(i));
            }

            responseArray = game.guess(guessArray);            
            
            System.out.println("\nYour guess: " + Arrays.toString(guessArray));
            System.out.println("Bulls: " + responseArray[0] + ", Cows: " + responseArray[1]);

        }

        //After game finished, add game score to scoreHistory
        int guessCount = 10-game.remainingGuesses;

        if (game.gameWon) {
            System.out.print("\n");
            if (guessCount == 1) {
                System.out.println("Congrats you won in 1 guess!\n");
            } else {
                System.out.println("Congrats you won in " + guessCount + " guesses!\n");
            }
            this.winningScoreHistory.addLast(guessCount);
        } else {
            System.out.print("\n");
            System.out.println("Uh oh, looks like you lost! :(\n");
        }
        this.gamesPlayedCount++;
    }


    private String promptUserInput(String message) throws IOException {
        System.out.print(message);
 
        return bufferedReader.readLine().toLowerCase();
    }

    private boolean checkYesNoString(String line) {
        if (line.equals("y") || line.equals("yes") || line.equals("n") || line.equals("no")) {
            return true;
        }
        return false;
    }

    private boolean checkSingleNumberString(String line) {
        if (line.length() > 1) {
            return false;
        }
        char c = line.charAt(0);
        if (Character.getNumericValue(c) < 4 || Character.getNumericValue(c) > 8) {
            return false;
        }


        return true;

    }



    private boolean checkValidGuessString(String guessString) {
        //Check if asking for hint
        if (guessString.toLowerCase().equals("hint")) {
            return true;
        }

        //Check if correct size
        if (guessString.length() != this.currentGameSize) {
            return false;
        }
        //Check if all letters are digits (0-7)
        for (int i = 0; i < guessString.length(); i++) {
            if (Character.getNumericValue(guessString.charAt(i)) > 8) {
                return false;
            }
        }
        return true;

    }


    //Prompt user for settings and then assign accordingly
    private void requestSettings() throws IOException {
        System.out.print("\nHow many numbers?: ");
        String line = this.bufferedReader.readLine(); 
        while (!checkSingleNumberString(line)) {
            System.out.println("Number of digits must be between 4 and 8, inclusive");
            System.out.print("\nHow many digits?: ");
            line =this.bufferedReader.readLine(); 
        }

        this.currentGameSize = Integer.parseInt(line);
        System.out.print("\nDuplicates allowed? (y/n): ");
        line = this.bufferedReader.readLine();
        while (!checkYesNoString(line)) {
            System.out.println("Error, please respond with a \"y\" for yes ,or a \"n\" for no");
            line = promptUserInput("Duplicates allowed? (y/n): ");
        }
        this.currentDuplicatesAllowed = (line.equals("y") || line.equals("yes"));
        

    }
    
   

    public static void main(String[] args) throws Exception {
        try {
            Session session = new Session();
            // session.startNewGame();
            


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}

package mastermind.ConsoleUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import mastermind.Game.CachedPatternGenerator;
import mastermind.Game.Game;
import mastermind.Game.PatternGenerator;
// import mastermind.Game.StaticCachedPatternGenerator;
import mastermind.Game.Session;

public class ConsoleUI {
    
    private BufferedReader bufferedReader;

    private Session session;
    private int requestedGameSize;
    private boolean requestedDuplicatesAllowed;
    
    // public Game currentGame;
    // private long gameStartTime;
    // private LinkedList<Integer> winningScoreHistory;
    // private int gamesPlayedCount;
    
    
    public ConsoleUI() throws Exception {
        
        //Escape code to clear console
        System.out.println("\033[H\033[2J");

        this.session = new Session();

        InputStreamReader inputReader = new InputStreamReader(System.in);
        this.bufferedReader = new BufferedReader(inputReader);

        //Explaining rules in terminal
        System.out.println("Rules: The computer will set a code of the specified length with the numbers 0 through 7, you have to 10 guesses to break the code!");
        System.out.println("\nWith each guess, the computer will respond with the number of bulls (correctly placed digits) and cows (digits that are in the code, but in the wrong place).");
        
        //Prompting user input, continuously prompts until valid string is given
        String line = promptUserInput("\nStart a new game? (y/n): ");
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

        String sessionStatsString = session.getEndOfSessionStats();
        if (sessionStatsString.length() > 0) {
            System.out.println(sessionStatsString);
        }

        System.out.println("\nThanks for playing!");
        

    }

    public void startNewGame() throws Exception {
        
        // get game settings from user input in terminal if already played a game, else if first game, prompt for settings;
        boolean newSettingsRequested;
        if (session.currentGame != null) {
            System.out.print("Would you like to use the settings from your previous game? (y/n): ");
            String line = this.bufferedReader.readLine();
            newSettingsRequested = (line.equals("n") || line.equals("no"));
        } else {
            newSettingsRequested = true;
        }

        if (newSettingsRequested) {
            this.requestSettings();
        }
               
        session.startNewGame(requestedGameSize, requestedDuplicatesAllowed);

        this.playGame(session.currentGame);
    }

    // Potential optimizimation where checkguessString passes back the guessArray, since I'm already converting to numeric value in the check function?
    private void playGame(Game game) throws IOException {        


        int[] guessArray;
        String guessString;
        int[] responseArray;
        System.out.println("When you make a guess, please enter the numbers without any commas or spaces in between them (Example: 0123 or 4567).");
        System.out.println("\nIf you need a hint, just type \"hint\" as your response.");

        //Plays until game is over (win or loss)
        while (!session.currentGame.gameOver) {
            System.out.println("\nYou have " + session.currentGame.remainingGuesses + " guesses left");
            guessString = promptUserInput("Make your guess (" + session.currentGameSize +" numbers): ");

            //Continuously prompts user input until valid guess is inputed
            while (!checkValidGuessString(guessString)) {
                System.out.print("Error: Length of guess must be " + session.currentGameSize + " digits long and only contain digits from 0 to 7, please guess again\n");
                guessString = promptUserInput("\nMake your guess (" + session.currentGameSize +" numbers): ");
            }

            // if user input == "hint", then give hint and print to terminal
            if (guessString.toLowerCase().equals("hint")) {
                int hint[] = session.currentGame.giveHint();
                System.out.println("Digit " + hint[1] + " is located at index " + hint[0]);
                continue;
            }


            //Convert guess string to guess array for compatibility with game functions
            guessArray = new int[guessString.length()];
            for (int i = 0; i < guessString.length(); i++) {
                guessArray[i] = Character.getNumericValue(guessString.charAt(i));
            }

            if (session.currentGame.gameOver) {
                break;
            }
            responseArray = session.currentGame.guess(guessArray);            
            
            System.out.println("\nYour guess: " + Arrays.toString(guessArray));
            System.out.println("Bulls: " + responseArray[0] + ", Cows: " + responseArray[1]);

        }

        //After game finished, add game score to scoreHistory
        int guessCount = 10-session.currentGame.remainingGuesses;

        if (session.currentGame.gameWon) {
            System.out.print("\n");
            if (guessCount == 1) {
                System.out.println("Congrats you won in 1 guess!\n");
            } else {
                System.out.println("Congrats you won in " + guessCount + " guesses!\n");
            }
        } else {
            System.out.print("\n");
            System.out.println("Uh oh, looks like you lost! :(\n");
        }
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
        if (guessString.length() != session.currentGameSize) {
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
        System.out.print("\nHow many digits? (4-8): ");
        String line = this.bufferedReader.readLine(); 
        while (!checkSingleNumberString(line)) {
            System.out.println("Number of digits must be between 4 and 8, inclusive");
            System.out.print("\nHow many digits?(4-8): ");
            line =this.bufferedReader.readLine(); 
        }

        requestedGameSize = Integer.parseInt(line);
        System.out.print("\nDuplicates allowed? (y/n): ");
        line = this.bufferedReader.readLine();
        while (!checkYesNoString(line)) {
            System.out.println("Error, please respond with a \"y\" for yes ,or a \"n\" for no");
            line = promptUserInput("Duplicates allowed? (y/n): ");
        }
        requestedDuplicatesAllowed = (line.equals("y") || line.equals("yes"));
        

    }
    
}

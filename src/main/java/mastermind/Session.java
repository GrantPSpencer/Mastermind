package mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

public class Session {
    
    private BufferedReader bufferedReader;

    public Game currentGame;
    private int gameSize;
    private boolean duplicatesAllowed;
    private LinkedList<Integer> scoreHistory;

    //add timer for each game

    

    public Session() throws Exception {
        //escape code to clear console
        System.out.println("\033[H\033[2J");
        
        this.scoreHistory = new LinkedList<>();
        InputStreamReader inputReader = new InputStreamReader(System.in);
        this.bufferedReader = new BufferedReader(inputReader);

        // this.requestSettings();

        // int[] newPattern = PatternGenerator.generatePattern(this.gameSize, this.duplicatesAllowed);
        // int[] newPattern = new int[] {0,1,3,3};
        // this.currentGame = new Game(newPattern);
        String line = promptUserInput("Start a new game? (y/n): ");
        while (!checkYesNoString(line)) {
            System.out.println("Error, please respond with a \"y\" for yes ,or a \"n\" for no");
            line = promptUserInput("Start a new game? (y/n): ");
        }
        while (line.equals("y") || line.equals("yes")) {
            this.startNewGame();
            line = promptUserInput("Start a new game? (y/n): "); 
            while (!checkYesNoString(line)) {
                System.out.println("Error, please respond with a \"y\" for yes ,or a \"n\" for no");
                line = promptUserInput("Start a new game? (y/n): ");
            }
        }

        if (scoreHistory.size() > 0) {
            int scoreHistorySum = 0;
            for (int score : scoreHistory) {
                scoreHistorySum += score;
            }
            System.out.println("\nSession over! You played " + scoreHistory.size() + " games, with an average of " + 
            (double)(scoreHistorySum/scoreHistory.size()) + " guesses per game");
        }

        System.out.println("\nThanks for playing!");
        

    }

    public void startNewGame() throws Exception {
        // get game settings from user input in terminal, provide promp;
        // int gameSize;
        // boolean duplicatesAllowed;
        
        boolean newSettings;
        if (currentGame != null) {
            
            System.out.print("Would you like to use the settings from your previous game? (y/n): ");
            String line = this.bufferedReader.readLine();
            newSettings = (line.equals("n") || line.equals("no"));
        } else {
            newSettings = true;
        }
        
        if (newSettings) {
            this.requestSettings();
        }
        
        int[] newPattern = PatternGenerator.generatePattern(this.gameSize, this.duplicatesAllowed);
        Game newGame = new Game(newPattern);
        this.playGame(newGame);

    }

    // can optimize where checkguessString passes back the guessArray, since I'm already converting to numeric value in the check function
    private void playGame(Game game) throws IOException {
        //for testing purpose only
        System.out.println("Correct Pattern: " + Arrays.toString(game.PATTERN));
        System.out.println("If you need a hint, just type \"hint\" as your response");
        //for testing purpose only

        //Move checkguess function to game class?
        int[] guessArray;
        String guessString;
        int[] responseArray;
        while (!game.gameOver) {
            // String guessString = promptGuess(game.LENGTH);
            System.out.println("\nYou have " + game.remainingGuesses + " guesses left");
            guessString = promptUserInput("Make your guess (" + game.LENGTH +" numbers): ");

            // if (guessString.toLowerCase().equals("hint")) {
            //     //give hint;
                
            //     continue;
            // }

            guessArray = new int[guessString.length()];
            for (int i = 0; i < guessString.length(); i++) {
                guessArray[i] = Character.getNumericValue(guessString.charAt(i));
            }


             responseArray = game.guess(guessArray);

            while (responseArray.length == 0) {
                System.out.print("Error: Length of guess must be " + game.LENGTH + " digits long and only contain digits from 0 to 7, please guess again\n");
                guessString = promptUserInput("\nMake your guess (" + game.LENGTH +" numbers): ");
                guessArray = new int[guessString.length()];
                for (int i = 0; i < guessString.length(); i++) {
                    guessArray[i] = Character.getNumericValue(guessString.charAt(i));
                }
                responseArray = game.guess(guessArray);
            }

            if (responseArray.length == 1) {
                continue;
            }

            
           
            

            System.out.println("Your guess: " + Arrays.toString(guessArray));
            
            System.out.println("Response: " + Arrays.toString(responseArray));

            // for (int num : responseArray) {
            //     System.out.print(num + ", ");
            // }
            // System.out.print("\n");
        }

        //add game score
        scoreHistory.addLast(10 - game.remainingGuesses);
    }

    // private String promptGuess(int patternSize) throws IOException {
    //     System.out.print("\nMake your guess (" + patternSize +" numbers): ");
    //     String line = bufferedReader.readLine();
    //     // while (line.length() != patternSize) {
    //     //     System.out.print("\nError: Length of guess must be +" + patternSize + " digits long, please guess again");
    //     //     System.out.print("\nMake your guess (" + patternSize +" numbers): ");
    //     //     line = bufferedReader.readLine();
    //     // }



    //     return line;

    // }

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



    // private boolean checkGuessString(String guessString, int patternSize) {
    //     // incorrect size
    //     if (guessString.length() != patternSize) {
    //         return false;
    //     }
    //     // not all letters are digits (0-7)
    //     for (int i = 0; i < guessString.length(); i++) {
    //         if (Character.getNumericValue(guessString.charAt(i)) > 8) {
    //             return false;
    //         }
    //     }
    //     return true;


    // }

    private void requestSettings() throws IOException {
        System.out.print("\nHow many numbers?: ");
        //ADD ERROR CHECK
        String line = this.bufferedReader.readLine(); 
        while (!checkSingleNumberString(line)) {
            System.out.println("Number of digits must be between 4 and 8, inclusive");
            System.out.print("\nHow many digits?: ");
            line =this.bufferedReader.readLine(); 
        }
        // while (lineVal < 0 || lineVal > 8) {
        //     System.out.println("Number of digits must be between 4 and 8, inclusive");
        //     System.out.print("\nHow many digits?: ");
        //     line =this.bufferedReader.readLine(); 
        //     lineVal = Integer.parseInt(line);
        // }
        this.gameSize = Integer.parseInt(line);
        //ADD ERROR CHECK
        System.out.print("\nDuplicates allowed? (y/n): ");
        line = this.bufferedReader.readLine();
        while (!checkYesNoString(line)) {
            System.out.println("Error, please respond with a \"y\" for yes ,or a \"n\" for no");
            line = promptUserInput("Duplicates allowed? (y/n): ");
        }
        this.duplicatesAllowed = (line.equals("y") || line.equals("yes"));
        

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

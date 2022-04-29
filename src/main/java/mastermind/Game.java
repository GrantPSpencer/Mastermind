package mastermind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Game 
{

    public int[] PATTERN;
    public int LENGTH; 
    public int remainingGuesses;
    private LinkedList<int[]> guessHistory;
    //correct placements array used for always giving useful hint
    private int[] correctPlacementsArray;
    // private HashMap<Integer, Integer> patternMap = new HashMap<>();
    public boolean gameOver;
    public boolean gameWon;
    

    public Game(int[] pattern) {
        this.PATTERN = pattern;
        this.LENGTH = PATTERN.length;
        this.remainingGuesses = 10;
        this.guessHistory = new LinkedList<>();
        this.correctPlacementsArray = new int[this.LENGTH];
        this.gameOver = false;
        this.gameWon = false;

        //instantiate pattern hashmap
        // for (int num : PATTERN) {
        //     patternMap.put(num, patternMap.getOrDefault(num, 0)+1);
        // }
    }

    public int[] guess(int[] guessArray) {

        if (this.gameOver || this.remainingGuesses == 0) {
            throw new Error("Game is already over");
        }

        if (!checkGuessArray(guessArray)) {
            if (guessArray.length == 4 && 'h' == Character.forDigit(guessArray[0], 36) && 'i' == Character.forDigit(guessArray[1], 36)
             && 'n' == Character.forDigit(guessArray[2], 36) && 't' == Character.forDigit(guessArray[3], 36)) {
                this.giveHint();
                return new int[] {1};
            }
            return new int[] {};
        }

        int[] feedback = this.judgeGuess(guessArray);
        this.remainingGuesses--;
        this.guessHistory.addLast(guessArray);
        
        // List<Integer> tempList = new ArrayList<>(LENGTH);
        // for (int num : feedback) {
        //     tempList.add(num);
        // }
        // Collections.shuffle(tempList);
        
        // budget shuffle
        // int i = 0;
        // for (int num : tempList) {
        //     feedback[i++] = (int) num;
        // }

        //check if no more guesses
        if (remainingGuesses == 0) {
            System.out.print("\n");
            System.out.println("Uh oh, looks like you lost you friggin loser!\n");
            this.gameOver = true;
        }


        
        return feedback;

    }

    private boolean checkGuessArray(int[] guessArray) {
        // incorrect size
        if (guessArray.length != this.LENGTH) {
            return false;
        }
        // not all letters are digits (0-7)
        for (int i = 0; i < guessArray.length; i++) {
            if (guessArray[i] > 8 || guessArray[i] < 0) {
                return false;
            }
        }
        return true;


    }


    private int[] judgeGuess(int[] guessArray) {
        int[] responseArray = new int[2];

        HashMap<Integer, Integer> patternMap = new HashMap<>();

        //first pass
        //use sum to determine if game winning guess
        // int winnerSumCheck = 0;
        int bulls = 0;
        int cows = 0;
        HashSet<Integer> usedIndexSet = new HashSet<>();

        for (int i = 0; i < LENGTH; i++) {

            //check if correct color and placement
            // if not, add to patternMap for second pass checking for correct color but incorrect placement
            if (guessArray[i] == PATTERN[i]) {
                // responseArray[i] = 1;
                usedIndexSet.add(i);
                this.correctPlacementsArray[i] = 1;
                bulls++;
                // winnerSumCheck++;
            } else {
                // responseArray[i] = -1;
                patternMap.put(PATTERN[i], patternMap.getOrDefault(PATTERN[i], 0)+1);
            }
        }

        responseArray[0] = bulls;
        //check if game was won, if yes then mark game done + won and return early
        if (bulls == LENGTH) {
            this.gameOver = true;
            this.gameWon = true;

            System.out.print("\n");
            System.out.println("Congrats you won!\n");

            return responseArray;
        }

        //second pass

        for (int i = 0; i < LENGTH; i++) {
            //skip correctly placed colors
            if (usedIndexSet.contains(i)) {
                continue;
            }

            //check if color is in the pattern
            // if it is, set response value to 0
            // if it is not, set response value to -1
            if (patternMap.containsKey(guessArray[i]) && patternMap.get(guessArray[i]) > 0) {
                patternMap.put(guessArray[i], patternMap.get(guessArray[i])-1);
                // responseArray[i] = 0;
                cows++;
            } else {
                // responseArray[i] = -1;
            }
        }

        responseArray[1] = cows;
        return responseArray;


    }

    private void resetGame() {
        this.remainingGuesses = 10;
        this.guessHistory = new LinkedList<>();
    }

    public void giveHint() {
        //tells user location of undiscovered digit (not yet correctly placed in any guesses)
        for (int i = 0; i < this.LENGTH; i++) {
            if (correctPlacementsArray[i] != 1) {
                System.out.println("Digit " + this.PATTERN[i] + " is located at index " + i);
                correctPlacementsArray[i] = 1;
                return;
            }
        }

        //if player has correctly placed each digit across all guesses (but not won), then picks pseudo random number
        int randomInt = Math.round((int) (Math.random()*(this.LENGTH)));
        System.out.println("Digit " + this.PATTERN[randomInt] + " is located at index " + randomInt);
    }




}

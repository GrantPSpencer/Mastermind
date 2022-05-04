package mastermind.Game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Hello world!
 *
 */
public class Game {

    private final int[] PATTERN;
    public final int LENGTH; 

    public int remainingGuesses;
    private LinkedList<int[]> guessHistory;

    public boolean gameOver;
    public boolean gameWon;

    //Correct placements array used for always giving useful hint
    //Hint queue used to add ordering to the hints that are given
    private int[] correctPlacementsArray;
    private Queue<Integer> hintQueue;

    

    public Game(int[] pattern) {
        this.PATTERN = pattern;
        this.LENGTH = PATTERN.length;

        this.remainingGuesses = 10;
        this.guessHistory = new LinkedList<>();

        this.gameOver = false;
        this.gameWon = false;

        this.correctPlacementsArray = new int[this.LENGTH];
        this.hintQueue = new LinkedList<>();



        //For testing purposes only
        System.out.println("\nCorrect pattern: " + Arrays.toString(this.PATTERN) + "\n");

    }

    //Judges the passed guessArray, decrements remaining guess count, and returns # of bulls and cows
    public int[] guess(int[] guessArray) {

        if (this.gameOver || this.remainingGuesses == 0) {
            throw new Error("Game is already over");
        }

        int[] feedback = judgeGuess(guessArray);
        this.remainingGuesses--;
        this.guessHistory.addLast(guessArray);

        //Check if no more guesses (game over)
        if (remainingGuesses == 0) {
            this.gameOver = true;
        }

        return feedback;

    }
    //Returns the # of bulls (correct digit and placement) and cows (correct digit, incorrect placement)
    private int[] judgeGuess(int[] guessArray) {
        
        //Used to correctly detect cows
        HashMap<Integer, Integer> patternMap = new HashMap<>();

        //Used to ensure we do not counts bulls as cows
        HashSet<Integer> usedIndexSet = new HashSet<>();


        int bulls = 0;
        int cows = 0;
        
        //First pass
        for (int i = 0; i < LENGTH; i++) {

            //Check if bull
            //If not, add to patternMap and incremement value for second pass checking for cows
            //Mark as correctly placed in correctPlacementsArray for hint giving function
            if (guessArray[i] == PATTERN[i]) {
                usedIndexSet.add(i);

                //If not correctly placed before, add to hintQueue
                if (this.correctPlacementsArray[i] != 1) {
                    this.hintQueue.offer(i);
                }

                this.correctPlacementsArray[i] = 1;
                bulls++;
            } else {
                patternMap.put(PATTERN[i], patternMap.getOrDefault(PATTERN[i], 0)+1);
            }
        }

        //Check if game was won, if yes then mark game done + won and return early
        if (bulls == LENGTH) {
            this.gameOver = true;
            this.gameWon = true;
            return new int[] {bulls, cows};
        }

        //Second pass
        for (int i = 0; i < LENGTH; i++) {
            //Skip bulls (already counted for in first pass)
            if (usedIndexSet.contains(i)) {
                continue;
            }

            //Check if digit is in the code pattern
            //If it is, then decrement value to properly count cows
            if (patternMap.containsKey(guessArray[i]) && patternMap.get(guessArray[i]) > 0) {
                patternMap.put(guessArray[i], patternMap.get(guessArray[i])-1);
                cows++;
            }
        }

        return new int[] {bulls, cows};


    }

    //Unimplemented functionality (Add to GUI?)
    private void resetGame() {
        this.remainingGuesses = 10;
        this.guessHistory = new LinkedList<>();
    }

    //Tells user location of undiscovered digit (not yet correctly placed in any guesses)
    //If all digits have been correctly placed (across all guesses), then it returns previously revealed info in
        // the order it was originally revealed
    public int[] giveHint() {
        
        //If all positions have been revealed either through guesses or hints,
            // then loop through and return hints in the order in which they were revealed
        if (this.hintQueue.size() == this.LENGTH) {
            int hintIndex = this.hintQueue.poll();
            this.hintQueue.offer(hintIndex);

            return new int[] {hintIndex, this.PATTERN[hintIndex]};
        }

        //Loop over correctPlacementsArray until finding index that has not been correctly placed
        for (int i = 0; i < this.LENGTH; i++) {
            if (this.correctPlacementsArray[i] != 1) {
                this.hintQueue.offer(i);
                this.correctPlacementsArray[i] = 1;
                return new int[] {i, this.PATTERN[i]};
            }
        }

        return new int[] {-1,-1};

    }

}

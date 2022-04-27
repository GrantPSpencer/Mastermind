package mastermind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Game 
{

    public int[] PATTERN;
    private int LENGTH; 
    private int remainingGuesses;
    private LinkedList<int[]> guessHistory;
    // private HashMap<Integer, Integer> patternMap = new HashMap<>();
    

    public Game(int[] pattern) {
        this.PATTERN = pattern;
        this.LENGTH = PATTERN.length;
        this.remainingGuesses = 10;
        this.guessHistory = new LinkedList<>();

        //instantiate pattern hashmap
        // for (int num : PATTERN) {
        //     patternMap.put(num, patternMap.getOrDefault(num, 0)+1);
        // }
    }

    public int[] guess(int[] guessArray) {

        if (remainingGuesses == 0) {
            throw new Error("Game is already over");
        }

        int[] feedback = this.judgeGuess(guessArray);
        this.remainingGuesses--;
        this.guessHistory.addLast(guessArray);
        
        List<Integer> tempList = new ArrayList<>(LENGTH);
        for (int num : feedback) {
            tempList.add(num);
        }
        Collections.shuffle(tempList);
        
        // int i = 0;
        // for (int num : tempList) {
        //     feedback[i++] = (int) num;
        // }
        
        return feedback;

    }


    private int[] judgeGuess(int[] guessArray) {
        int[] responseArray = new int[this.LENGTH];
        HashMap<Integer, Integer> patternMap = new HashMap<>();

        //first pass
        for (int i = 0; i < LENGTH; i++) {


            //check if correct color and placement
            // if not, add to patternMap for second pass checking for correct color but incorrect placement
            if (guessArray[i] == PATTERN[i]) {
                responseArray[i] = 1;
            } else {
                // responseArray[i] = -1;
                patternMap.put(PATTERN[i], patternMap.getOrDefault(PATTERN[i], 0)+1);
            }


        }

        //second pass

        for (int i = 0; i < LENGTH; i++) {
            //skip correctly placed colors
            if (responseArray[i] == 1) {
                continue;
            }

            //check if color is in the pattern
            // if it is, set response value to 0
            // if it is not, set response value to -1
            if (patternMap.containsKey(guessArray[i]) && patternMap.get(guessArray[i]) > 0) {
                patternMap.put(guessArray[i], patternMap.get(guessArray[i] -1));
                responseArray[i] = 0;
            } else {
                responseArray[i] = -1;
            }

        }
        return responseArray;


    }

    private void resetGame() {
        this.remainingGuesses = 10;
        this.guessHistory = new LinkedList<>();
    }




}

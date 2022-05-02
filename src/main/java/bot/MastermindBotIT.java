package bot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import mastermind.Game;
import mastermind.PatternGenerator;

public class MastermindBotIT {
    

// THIS ONLY PLAYS GAMES OF LENGTH 4 (8 POSSIBLE DIGITS, DUPLICATES ALLOWED)
    private final int LENGTH = 4;
    private HashSet<int[]> possibleAnswerSet;

    // public MastermindBotIT() {

        


    // }



    public int playGame(Game game) {
        this.possibleAnswerSet = getAnswerSet();
        
        //guess best guess
        System.out.println("possible guess size is: " + possibleAnswerSet.size());
        System.out.println("Guess #:" + (11-game.remainingGuesses));
        int[] bestGuess = new int[] {0,1,3,5};
        System.out.println(Arrays.toString(bestGuess));
        int[] feedback = game.guess(bestGuess);

        

        while (!game.gameOver) {
            
            this.possibleAnswerSet = filterAnswersByGuess(bestGuess, feedback);

            System.out.println("possible guess size is: " + possibleAnswerSet.size());
            System.out.println("Guess #:" + (11-game.remainingGuesses));

            bestGuess = findBestGuess();

            System.out.println(Arrays.toString(bestGuess));
            feedback = game.guess(bestGuess);
            

        }
        if (game.gameWon) {
            return (10-game.remainingGuesses);
        } else {
            return (11);
        }

       
    }


    // calculate bits over all possible guesses + answers
    // store average of expected bits from each one, take one with highest expected bits

    private int[] findBestGuess() {

        // HashMap<int[], Double> map = new HashMap<>();
        double maxSum = Integer.MIN_VALUE;
        int[] bestGuess = new int[] {0,0,0,0};
        System.out.print("\n");
        int i = 0;
        for (int[] possibleGuess : possibleAnswerSet) {
            double sum = 0;
            for (int[] possibleAnswer : possibleAnswerSet) {
                sum += getBits(possibleGuess, possibleAnswer);
            }
            if (sum > maxSum) {
                maxSum = sum;
                bestGuess = possibleGuess;
            }
            System.out.print("\rProgress: " + (i++) + " / " + possibleAnswerSet.size());
            
        }
        
        return bestGuess;
    }


    //gets feedback from guess to answer
    //reduces possible answer size according to the given feedback
    // calculates bits based off new answer size and current answer size
    
    private double getBits(int[] guess, int[] answer) {
        int[] feedback = judgeGuessToAnswer(guess, answer);
        HashSet<int[]> newAnswerSet = filterAnswersByGuess(guess, feedback); 
        
        double probability = ((double) newAnswerSet.size()) / ((double) this.possibleAnswerSet.size());

        double bits = Math.log(1/probability) / Math.log(2);
        return bits;
    }



    
    
    // returns # of bulls and cows for given guess to given answer
    private int[] judgeGuessToAnswer(int[] guess, int[] answer) {
        // System.out.println("judge guess called, guess: " + Arrays.toString(guess) + " and answer: " + Arrays.toString(answer));
        
        int[] responseArray = new int[2];
        
        HashMap<Integer, Integer> patternMap = new HashMap<>();

        //first pass
        int bulls = 0;
        int cows = 0;
        HashSet<Integer> usedIndexSet = new HashSet<>();

        for (int i = 0; i < this.LENGTH; i++) {

            //check if correct color and placement (bulls)
            // if not, add to patternMap for second pass checking for correct color but incorrect placement
            if (guess[i] == answer[i]) {
                usedIndexSet.add(i);
                bulls++;
                //  patternMap.put(answer[i], patternMap.getOrDefault(answer[i], 0)+1);
            } else {
                patternMap.put(answer[i], patternMap.getOrDefault(answer[i], 0)+1);
            }
        }

        responseArray[0] = bulls;

        //second pass
        for (int i = 0; i < LENGTH; i++) {
            //skip correctly placed colors
            if (usedIndexSet.contains(i)) {
                continue;
            }

            //check if color is in the pattern (cows)
            if (patternMap.containsKey(guess[i]) && patternMap.get(guess[i]) > 0) {
                patternMap.put(guess[i], patternMap.get(guess[i]) - 1);
                cows++;
            } 

            
        }

        responseArray[1] = cows;
        return responseArray;

    }


    private HashSet<int[]> filterAnswersByGuess(int[] guess, int[] originalFeedback) {
        HashSet<int[]> newAnswerSet = new HashSet<>();

        for (int[] possibleAnswer : this.possibleAnswerSet) {
            int[] feedback = judgeGuessToAnswer(guess, possibleAnswer);
            if (feedback[0] == originalFeedback[0] && feedback[1] == originalFeedback[1] ) {
                newAnswerSet.add(possibleAnswer);
            }
        }
        
        
        return newAnswerSet;
    }



    private HashSet<int[]> getAnswerSet() {
        
        HashSet<int[]> answerSet = new HashSet<>();

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {
                

                for (int k = 0; k < 8; k++) {

                    for (int l = 0; l < 8; l++) {
                        answerSet.add(new int[] {i, j, k, l});
                    }
                }
            }
        }

        return answerSet;
    }


    //stores average bits and worst case bits from each guess across all possible games
    public void findBestFirstGuess() throws IOException {
        
        System.out.println("finding best first guess");
        long startTime = System.currentTimeMillis();
        this.possibleAnswerSet = getAnswerSet();
        
        File avgFile = new File("src/main/java/bot/first_guess_performance_averagecase_test.txt");
        File worstFile = new File("src/main/java/bot/first_guess_performance_worstcase_test.txt");
        FileWriter avgWriter = new FileWriter(avgFile);
        FileWriter worstWriter = new FileWriter(worstFile);

        int i = 0;
        System.out.print("\n");
        String arrayStr;
        for (int[] possibleGuess : possibleAnswerSet) {
            double sum = 0;
            double minBits = Double.MAX_VALUE;
            System.out.println("\rProgress: " + i++ + " / " + possibleAnswerSet.size());
            for (int[] possibleAnswer : possibleAnswerSet) {
                // sum += getBits(possibleGuess, possibleAnswer);
                double toAddBits = getBits(possibleGuess, possibleAnswer);
                sum += toAddBits;
                minBits = Math.min(toAddBits, minBits);
            }
            arrayStr = Arrays.toString(possibleGuess);
            avgWriter.write(arrayStr + ", " + (sum/possibleAnswerSet.size()) + "\n");
            worstWriter.write(arrayStr + ", " + minBits + "\n");
        }
        


        Long endTime = System.currentTimeMillis();
        long timeInMilliseconds = endTime-startTime;
        long hours = TimeUnit.MILLISECONDS.toHours(timeInMilliseconds);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMilliseconds - TimeUnit.HOURS.toMillis(hours));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMilliseconds - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes));
        long milliseconds = timeInMilliseconds - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes) - TimeUnit.SECONDS.toMillis(seconds);



        avgWriter.write("\nStarted at: " + new Date(startTime) + "\n");
        avgWriter.write("Finished at: " + new Date(endTime)+"\n");
        avgWriter.write("Time to completion: " +String.format("%02d:%02d:%02d:%d", hours, minutes, seconds, milliseconds)+"\n");

        
        avgWriter.flush();
        avgWriter.close();

        worstWriter.flush();
        worstWriter.close();

    }


    public static void main(String[] args) {
        MastermindBotIT bot = new MastermindBotIT();
        // try {
        //     bot.playGame(new Game(PatternGenerator.generatePattern(4, true)));
        // } catch (Exception e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        
        // bot.playGame(new Game(new int[] {1,0,1,0}));

        try {
            bot.findBestFirstGuess();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
}

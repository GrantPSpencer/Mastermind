package bot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import mastermind.Game.Game;
import mastermind.Game.PatternGenerator;

public class MastermindBotIT {
    

// THIS IS ONLY INTENDED TO PLAY GAMES OF LENGTH 4 (8 POSSIBLE DIGITS, DUPLICATES ALLOWED)
// The time complexity for the getBits algorithm is n^2 and it requires a bit of time to run. 
// Increasing the code length would cause a SIGNIFICANT increase on runtime

    private final int LENGTH = 4;
    private HashSet<int[]> possibleAnswerSet;


    //pass a game for the bot to play
    //the bot will make guesses until the game is over (loss or win)

    public int playGame(Game game) {
        long startTime = System.currentTimeMillis();
        this.possibleAnswerSet = getAnswerSet();
        
        // System.out.println("possible guess size is: " + possibleAnswerSet.size());
        // System.out.println("Guess #:" + (11-game.remainingGuesses));

        // pre-coded best first guess to save computation time
        // refer to the readme for analysis of best first guess
        int[] bestGuess = new int[] {0,1,3,5};
        int[] feedback = game.guess(bestGuess);
        System.out.println("\nGuessed: " + Arrays.toString(bestGuess));

        while (!game.gameOver) {
            
            //filters the possible answer set according to result from 
            this.possibleAnswerSet = filterAnswersByGuess(bestGuess, feedback);

            // System.out.println("possible guess size is: " + possibleAnswerSet.size());
            // System.out.println("Guess #:" + (11-game.remainingGuesses));

            bestGuess = findBestGuess();

            System.out.println("\nGuessed: " + Arrays.toString(bestGuess));
            feedback = game.guess(bestGuess);
            

        }

        long endTime = System.currentTimeMillis();
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(endTime-startTime));
        if (game.gameWon) {
            System.out.println("\nGame won in " + (10-game.remainingGuesses) + " guesses");
            return (10-game.remainingGuesses);
        } else {
            System.out.println("\nGame lost");
            return (11);
        }

       
    }


    // Calculate bits over all possible guesses + answers
    // Store the bits gained each time that pattern is guessed (sum)
    // Take the guess with the greatest bits sum
    private int[] findBestGuess() {


        //instantiating dummy array for bestGuess
        int[] bestGuess = new int[] {-1,-1,-1,-1};
        double maxSum = Double.MIN_VALUE;
        double maxWorstCase = Double.MIN_VALUE;
        int i = 0;
        
        System.out.print("\n");

        //Loops over all possible guesses
        //Then within each possible guess, it loops over all possible answers,
            //and gets the bits if that guess was made for that answer
        //Records the guess pattern with the greatest cumulative bits
        //Uses worst case bits to break ties on average bits
        for (int[] possibleGuess : possibleAnswerSet) {
            double sum = 0;
            double worstCaseBits = Double.MAX_VALUE;
            for (int[] possibleAnswer : possibleAnswerSet) {
                double toAddBits = getBits(possibleGuess, possibleAnswer);
                sum += toAddBits;
                worstCaseBits = Math.min(toAddBits, worstCaseBits);
            }
            if (sum > maxSum) {
                bestGuess = possibleGuess;
                maxSum = sum;
                maxWorstCase = worstCaseBits;
            } else if (sum == maxSum && worstCaseBits > maxWorstCase) {
                bestGuess = possibleGuess;
                maxSum = sum;
                maxWorstCase = worstCaseBits;
            }
            System.out.print("\rProgress: " + (i++) + " / " + possibleAnswerSet.size());
        }
        
        return bestGuess;
    }



    //Calculates bits based off of the size of the new possible answer set
    //The smaller the new answer set (i.e., the greater the original set is reduced), the more bits are gained
    //Our program doesn't need to know the actual bits formula, it can just use the newAnswer size as an approximation
        // since all guesses are using the same original possible answer set (original formula commented out below)

    private double getBits(int[] guess, int[] answer) {
        int[] feedback = judgeGuessToAnswer(guess, answer); 
        
        int newAnswerSetSize = getFilteredAnswerSetSize(guess, feedback);
        return (1/(double)newAnswerSetSize);
        
        // HashSet<int[]> newAnswerSet = filterAnswersByGuess(guess, feedback);
        // double probability = ((double) newAnswerSet.size()) / ((double) this.possibleAnswerSet.size());
        // double bits = Math.log(1/probability) / Math.log(2);
        // return bits;
        
    }



    
    
 


    //Returns the # of bulls (correct digit and placement) and cows (correct digit, incorrect placement)
    private int[] judgeGuessToAnswer(int[] guess, int[] answer) {
        
        

        //Used to correctly detect cows
        HashMap<Integer, Integer> patternMap = new HashMap<>();

        //Used to ensure we do not counts bulls as cows
        HashSet<Integer> usedIndexSet = new HashSet<>();
        
        int bulls = 0;
        int cows = 0;

        //First Pass
        for (int i = 0; i < this.LENGTH; i++) {

            //Check if bull
            //If not, add to patternMap and incremement value for second pass checking for cows
            if (guess[i] == answer[i]) {
                usedIndexSet.add(i);
                bulls++;
            } else {
                patternMap.put(answer[i], patternMap.getOrDefault(answer[i], 0)+1);
            }
        }

    

        //Second pass
        for (int i = 0; i < LENGTH; i++) {
            //Skip bulls (already counted for in first pass)
            if (usedIndexSet.contains(i)) {
                continue;
            }

            //Check if digit is in the code pattern
            //If it is, then decrement value to properly count cows
            if (patternMap.containsKey(guess[i]) && patternMap.get(guess[i]) > 0) {
                patternMap.put(guess[i], patternMap.get(guess[i]) - 1);
                cows++;
            } 
            
        }
        
        //Response array
        return new int[] {bulls, cows};

    }




    //Given a # of bulls and cows from a guess, the possible answers in the answer set must be ones that return the same
        //# of bulls and cows when that guess is made against them

    //Creates new possible answer set (from current one, this.possibleAnswerSet) by filtering according to feedback from guess
    private HashSet<int[]> filterAnswersByGuess(int[] guess, int[] originalFeedback) {
        HashSet<int[]> newAnswerSet = new HashSet<>();

        for (int[] possibleAnswer : this.possibleAnswerSet) {
            
            //Get # bulls and cows from guess against answer
            int[] feedback = judgeGuessToAnswer(guess, possibleAnswer);

            //Check if same # bulls and cows from original guess, if so then it is a possible answer
            if (feedback[0] == originalFeedback[0] && feedback[1] == originalFeedback[1] ) {
                newAnswerSet.add(possibleAnswer);
            }
        }
        
        return newAnswerSet;
    }

    //Iterates through current answer set, does not create new answer set, simply returns what its size would be
    //Use this instead to save memory and unecessary creation of answer sets when all that is needed is the size
    private int getFilteredAnswerSetSize(int[] guess, int[] originalFeedback) {
     
        int count = 0;
        for (int[] possibleAnswer : this.possibleAnswerSet) {
                int[] feedback = judgeGuessToAnswer(guess, possibleAnswer);
                if (feedback[0] == originalFeedback[0] && feedback[1] == originalFeedback[1] ) {
                    count++;
                }
        }
        return count;
    }

    //Produces answer set (0000-9999) on command... faster than loading from a txt file?
    private HashSet<int[]> getAnswerSet() {
        
        HashSet<int[]> answerSet = new HashSet<>();

        //Digit 1
        for (int i = 0; i < 8; i++) {
            //Digit 2
            for (int j = 0; j < 8; j++) {
                //Digit 3
                for (int k = 0; k < 8; k++) {
                    //Digit 4
                    for (int l = 0; l < 8; l++) {
                        answerSet.add(new int[] {i, j, k, l});
                    }
                }
            }
        }

        return answerSet;
    }


    //Stores average bits and worst case bits from each guess across all possible games
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
        int[] pattern = new int[] {5, 1, 3, 2};
        try {
            // bot.playGame(new Game(PatternGenerator.generatePattern(4, true)));

            bot.playGame(new Game(pattern));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // bot.playGame(new Game(new int[] {1,0,1,0}));

        // try {
        //     bot.findBestFirstGuess();
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
    }

    
}

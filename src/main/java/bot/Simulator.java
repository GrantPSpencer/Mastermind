package bot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import mastermind.Game.Game;

public class Simulator {
    
    private HashSet<int[]> possibleAnswerSet;

    public Simulator() {
        this.possibleAnswerSet = getAnswerSet();
    }

    //generates answer set
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


    //Plays the bot across all possible games, prints out results to text files
    public void simulateGames() throws IOException {

        long startTime = System.currentTimeMillis();
        MastermindBotIT bot = new MastermindBotIT();
        
        File file = new File("src/main/java/bot/simulation_results/game_simulations/performance_simulation_results.txt");
        FileWriter writer = new FileWriter(file);

        File timeFile = new File("src/main/java/bot/simulation_results/game_simulations/performance_simulation_times.txt");
        FileWriter timeWriter = new FileWriter(timeFile);

        int score; 
        int sum = 0;
        HashMap<Integer, Integer> scoreMap = new HashMap<>();
        int i = 0;
        long gameStartTime;
        for (int[] pattern : this.possibleAnswerSet) {
            gameStartTime = System.currentTimeMillis();
            System.out.println("Progress: " + (++i) + " / " + possibleAnswerSet.size());

            score = bot.playGame(new Game(pattern));
            sum += score;
            scoreMap.put(score, scoreMap.getOrDefault(score, 0)+1);
            System.out.println("Average is: " +  (double)sum/(double)i);
            
            timeWriter.write(Arrays.toString(pattern) + ", " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-gameStartTime)+"\n");

        }
        timeWriter.flush();
        timeWriter.close();

       


        Long endTime = System.currentTimeMillis();
        long timeInMilliseconds = endTime-startTime;
        long hours = TimeUnit.MILLISECONDS.toHours(timeInMilliseconds);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMilliseconds - TimeUnit.HOURS.toMillis(hours));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMilliseconds - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes));
        long milliseconds = timeInMilliseconds - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes) - TimeUnit.SECONDS.toMillis(seconds);


        writer.write("Sum is: " + sum + "\n");
        writer.write("Length is: " + this.possibleAnswerSet.size() + "\n");
        writer.write("Average performance is: " + ((double)sum / (double)i)+"\n");
        writer.write("Score Distribution is: \n");
        writer.write("\n");
        for (Map.Entry entry : scoreMap.entrySet()) {
            writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
        }

        writer.write("\n");
        
        writer.write("Started at: " + new Date(startTime) + "\n");
        writer.write("Finished at: " + new Date(endTime)+"\n");
        writer.write("Time to completion: " +String.format("%02d:%02d:%02d:%d", hours, minutes, seconds, milliseconds)+"\n");

   
        writer.flush();
        writer.close();


    }
    public static void main(String[] args) {
        
        Simulator sim = new Simulator();
        try {
            sim.simulateGames();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}

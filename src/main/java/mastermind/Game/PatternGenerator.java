package mastermind.Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashSet;


public class PatternGenerator {
    
    //Creates connection to API and generates a pattern according to constraints of passed variables
    public static int[] generatePattern(int size, boolean duplicatesAllowed) throws Exception {


        long startTime = System.currentTimeMillis();
        int[] pattern = new int[size];
        StringBuilder urlSB = new StringBuilder();

        //Example API URL String:
        //https://www.random.org/integers/?num=4&min=1&max=6&col=1&base=10&format=plain&rnd=new

        urlSB.append("https://www.random.org/integers/?num=");
        urlSB.append(size);
        urlSB.append("&min=0&max=7&col=1&base=10&format=plain&rnd=new");

        URL url = new URL(urlSB.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputReader);

        //Converting API response to array of digits
        int i = 0;
        for (String line;  (line = bufferedReader.readLine()) != null; i++) {
            pattern[i] = Integer.parseInt(line);

        }
        
        //Removes duplicates (in non-random way) from array
        if (!duplicatesAllowed && size <= 8) {
            return removeDuplicates(pattern);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Call took " + (endTime-startTime) + " ms for " + size + " numbers.");

        return pattern;
    }

    //Removes duplicates by continuously creating new number less than and greater than (-1/+1) 
        // the duplicate value. Replaces duplicate once new value that satisfies conditions has been found
    //Limitation: Inherently favors replacing with adjacent values, therefore not random
    private static int[] removeDuplicates(int[] pattern) {

        //Keep track of values that are already in array, so we do not create any new duplicates
        HashSet<Integer> usedIntegerSet = new HashSet<>();
        HashSet<Integer> duplicatesIndexSet = new HashSet<>();

        //First pass, add all values to used integer set, if already in set, then add index to duplicates set
        for (int i = 0; i < pattern.length; i++) {
            if (usedIntegerSet.contains(pattern[i])) {
                duplicatesIndexSet.add(i);
            }
            usedIntegerSet.add(pattern[i]);
        }


        //Favors replacing duplicates with adjacent values
        //Use collections.shuffle to get around this?
        for (int index : duplicatesIndexSet)  {

            //Iterate left (-1) and right (+1) from original value
            int left = pattern[index] - 1;
            int right = pattern[index] + 1;

            //Check if either satisfies condition, if not then continue iterating and rechecking
            while (true) {
                if (left >= 0 && !usedIntegerSet.contains(left)) {
                    pattern[index] = left;
                    usedIntegerSet.add(left);
                    break;
                }
                if (right <= 7 && !usedIntegerSet.contains(right)) {
                    pattern[index] = right;
                    usedIntegerSet.add(right);
                    break;
                }
                left--;
                right++;

            }

        }

        return pattern;
    }

    public static void main(String[] args) throws Exception {

    }
}

package mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashSet;


public class PatternGenerator {
    
    public static int[] generatePattern(int size, boolean duplicatesAllowed) throws Exception {


        int[] pattern = new int[size];

        StringBuilder urlSB = new StringBuilder();

        //https://www.random.org/integers/?num=4&min=1&max=6&col=1&base=10&format=plain&rnd=new
        urlSB.append("https://www.random.org/integers/?num=");
        urlSB.append(size);
        urlSB.append("&min=0&max=7&col=1&base=10&format=plain&rnd=new");

        URL url = new URL(urlSB.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputReader);

        int i = 0;
        for (String line;  (line = bufferedReader.readLine()) != null; i++) {
            pattern[i] = Integer.parseInt(line);

        }

        if (!duplicatesAllowed) {
            return removeDuplicates(pattern);
        }

        return pattern;
    }

    private static int[] removeDuplicates(int[] pattern) {


        HashSet<Integer> usedIntegerSet = new HashSet<>();
        HashSet<Integer> duplicatesIndexSet = new HashSet<>();
        for (int i = 0; i < pattern.length; i++) {
            if (usedIntegerSet.contains(pattern[i])) {
                duplicatesIndexSet.add(i);
            }
            usedIntegerSet.add(pattern[i]);
        }

        
        for (int index : duplicatesIndexSet)  {
            int left = pattern[index] - 1;
            int right = pattern[index] + 1;

            while (true) {
                if (left >= 0 && !usedIntegerSet.contains(left)) {
                    pattern[index] = left;
                    usedIntegerSet.add(left);
                    break;
                }
                if (right < pattern.length && !usedIntegerSet.contains(right)) {
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
}

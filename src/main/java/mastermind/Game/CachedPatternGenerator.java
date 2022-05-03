package mastermind.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CachedPatternGenerator {
    
    private int[] patternsArray;
    private int nextIndex;

    public CachedPatternGenerator() throws Exception {
        this.patternsArray = getPatternsFromAPI();
        this.nextIndex = 0;
    }


    public int[] getPattern(int size) throws Exception {
        int[] pattern = new int[size];

        for (int i = 0; i < size; i++) {
            pattern[i] = this.patternsArray[nextIndex+i];
        }

        nextIndex = nextIndex + size;

        if(nextIndex > (1000-8)) {
            resetPatternsArray();
        }

        return pattern;
    }

    private void resetPatternsArray() throws Exception {
        this.patternsArray = getPatternsFromAPI();
        this.nextIndex = 0;
    }



    private int[] getPatternsFromAPI() throws Exception {
        
        int[] pattern = new int[1000];
        //Example API URL String:
        //https://www.random.org/integers/?num=4&min=1&max=6&col=1&base=10&format=plain&rnd=new

        URL url = new URL("https://www.random.org/integers/?num=1000&min=0&max=7&col=1&base=10&format=plain&rnd=new");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputReader);

        //Converting API response to array of digits
        int i = 0;
        for (String line;  (line = bufferedReader.readLine()) != null; i++) {
            pattern[i] = Integer.parseInt(line);

        }

        return pattern;
    }

    
}

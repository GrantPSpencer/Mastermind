package mastermind.Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;



public class CachedPatternGenerator {
    private int[] patternsArray;
    private int nextIndex;
    private final int CACHE_SIZE = 1000;

    public CachedPatternGenerator() throws Exception {
        createPatternsArray();
    }

    public int[] getPattern(int size, boolean duplicatesAllowed) throws Exception {

        //Index over larger patterns array to produce subaray of specified length = size
        int[] pattern = new int[size];
        for (int i = 0; i < size; i++) {
            pattern[i] = patternsArray[nextIndex+i];
        }

        //Move next index up for next call
        nextIndex = nextIndex + size;
        
        //If we are only 2 possible new patterns (max codelength = 8) away from indexing out, 
            // then call the async refresh patterns array function
        if(nextIndex > (CACHE_SIZE-16)) {
            refreshPatternsArray();
        }

        //If duplicates not allowed, then remove duplicates and return subarray
        if (!duplicatesAllowed) {
           return removeDuplicates(pattern);
        }

        return pattern;
    }


    //Initial API call, cannot be async as it must block until API call completes on startup
    public void createPatternsArray() throws Exception {
        patternsArray = getPatternsFromAPI();
        nextIndex = 0;
    }
    
    //Async API call, doesn't need to block as we already have a larger patterns array loaded
    public void refreshPatternsArray() throws Exception {
        CompletableFuture.runAsync(() -> {
            try {
                createPatternsArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    //Connects to API and requests 1000 digits, reads them into an array and returns the array
    private int[] getPatternsFromAPI() throws Exception {
        

       
        int[] patternsArray = new int[CACHE_SIZE];
        //Example API URL String:
        //https://www.random.org/integers/?num=4&min=1&max=6&col=1&base=10&format=plain&rnd=new

        
        URL url = new URL("https://www.random.org/integers/?num="+CACHE_SIZE+"&min=0&max=7&col=1&base=10&format=plain&rnd=new");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputReader);

        //Converting API response to array of digits
        int i = 0;
        for (String line;  (line = bufferedReader.readLine()) != null; i++) {
            patternsArray[i] = Integer.parseInt(line);

        }

        return patternsArray;
    }


    //Removes duplicates by continuously creating new number less than and greater than (-1/+1) 
        // the duplicate value. Replaces duplicate once new value that satisfies conditions has been found
    //Limitation: Inherently favors replacing with adjacent values, therefore not random
    private int[] removeDuplicates(int[] pattern) {

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

    //Previous attempt at async http connection for async new pattern array function
        // private static void asyncGetPatternsFromAPI() {

    //     int[] pattern = new int[1000];
    //     HttpClient client = HttpClient.newHttpClient();
    //     HttpRequest request = HttpRequest.newBuilder()
    //         .uri(URI.create("https://www.random.org/integers/?num=100&min=0&max=7&col=1&base=10&format=plain&rnd=new"))
    //         .build();

    //     AtomicInteger i = new AtomicInteger();
    //     client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
    //         .thenApply(HttpResponse::body)
    //         .thenAccept((x)-> pattern[i.getAndIncrement()] = Integer.parseInt(x));


    // }
}

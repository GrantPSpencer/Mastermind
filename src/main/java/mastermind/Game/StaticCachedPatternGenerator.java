package mastermind.Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;


public class StaticCachedPatternGenerator {
    private static int[] patternsArray;
    private static int nextIndex;


    public static int[] getPattern(int size) throws Exception {
        if (patternsArray == null) {
            createPatternsArray();
        }
        int[] pattern = new int[size];

        for (int i = 0; i < size; i++) {
            pattern[i] = patternsArray[nextIndex+i];
        }

        nextIndex = nextIndex + size;

        if(nextIndex > (1000-16)) {
            resetPatternsArray();
        }
        System.out.println("Index  " + nextIndex + " / 1000");
        return pattern;
    }


    public static void createPatternsArray() throws Exception {
        patternsArray = getPatternsFromAPI();
        nextIndex = 0;
    }
    
    public static void resetPatternsArray() throws Exception {
        CompletableFuture.runAsync(() -> {
            try {
                createPatternsArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static int[] getPatternsFromAPI() throws Exception {
        
        long startTime = System.currentTimeMillis();

       
        int[] pattern = new int[100];
        //Example API URL String:
        //https://www.random.org/integers/?num=4&min=1&max=6&col=1&base=10&format=plain&rnd=new

        URL url = new URL("https://www.random.org/integers/?num=100&min=0&max=7&col=1&base=10&format=plain&rnd=new");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputReader);

        //Converting API response to array of digits
        int i = 0;
        for (String line;  (line = bufferedReader.readLine()) != null; i++) {
            pattern[i] = Integer.parseInt(line);

        }

        long endTime = System.currentTimeMillis();
        System.out.println("Call took " + (endTime-startTime) + " ms for 1000 numbers.");

        return pattern;
    }


    //Prevoius attempt at async http connection for async new pattern array function
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

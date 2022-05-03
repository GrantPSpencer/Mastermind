package mastermind;

import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static void main(String[] args) throws Exception {
            
        // int[] pattern = PatternGenerator.generatePattern(8, false);
        // Game game = new Game(new int[] {0,1,1,3});

        // int[] responseArray = game.guess(new int[] {3,1,1,1});

        // for (int i = 0; i < responseArray.length; i++) {
        //     System.out.println(responseArray[i]);
        // }

        // int[] arr = new int[] {0,1,2,3,4,5};
        // System.out.println(Arrays.toString(arr));

        // int[] arr = new int[] {Character.getNumericValue('h'), Character.getNumericValue('i'), Character.getNumericValue('n'), Character.getNumericValue('t')};
        // int a = 65;
        // System.out.println((char) a);
        // System.out.println(arr[0]);
        // System.out.println((char)(a+1));
        // System.out.println((char)27)
        // int a = 17;
        // System.out.println((char)a);
        // Character.forDig


        // int randomInt = Math.round((int) Math.random()*7);

        // for (int i = 0; i < 10; i++) {
        //     System.out.println(Math.round((int) (Math.random()*7)));
            
        // }

        // int[] pattern = new int[100];
        // HttpClient client = HttpClient.newHttpClient();
        // HttpRequest request = HttpRequest.newBuilder()
        //     .uri(URI.create("https://www.random.org/integers/?num=100&min=0&max=7&col=1&base=10&format=plain&rnd=new"))
        //     .build();

        // AtomicInteger i = new AtomicInteger();
        // client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        //     .thenApply(HttpResponse::body)
        //     .thenAccept((x)-> pattern[i.getAndIncrement()] = Integer.parseInt(x));


        // Thread.sleep(5000);
        // System.out.println(Arrays.toString(pattern));


    }
}

package mastermind;

public class Test {
    public static void main(String[] args) throws Exception {
            
        // int[] pattern = PatternGenerator.generatePattern(8, false);
        Game game = new Game(new int[] {0,1,1,3});

        int[] responseArray = game.guess(new int[] {3,1,1,1});

        for (int i = 0; i < responseArray.length; i++) {
            System.out.println(responseArray[i]);
        }

    

        
        




    }
}

package mastermind;

public class Test {
    public static void main(String[] args) {
        try {
            int[] pattern = PatternGenerator.generatePattern(8, false);
            for (int i = 0; i < pattern.length; i++) {
                System.out.println(pattern[i] + " ... " + i);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

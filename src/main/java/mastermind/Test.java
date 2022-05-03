package mastermind;

import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.*;

public class Test {
    public static void main(String[] args) throws Exception {
            
        int gamesPlayedCount = 5;
        LinkedList<Integer> winningScoreHistory = new LinkedList<>();
        winningScoreHistory.add(1);
        winningScoreHistory.add(3);
        JFrame statsFrame = new JFrame();
        
        if (gamesPlayedCount > 0) {
            if (winningScoreHistory.size() == 0) {
                String noWinsString = "You won 0 of the " + gamesPlayedCount + " games you played.";
                JLabel noWinsLabel = new JLabel("<html>" + noWinsString + "<html>");
                noWinsLabel.setVisible(true);
                noWinsLabel.setBounds(10, 0, 400, 50);
                statsFrame.add(noWinsLabel);
            } else {
                int winningScoreSum = 0;
                for (int score : winningScoreHistory) {
                    winningScoreSum += score;
                }
                String winsString = "You won " + winningScoreHistory.size() + " of the " + gamesPlayedCount + " games you played, with an average winning guess count of " 
                + (double)(winningScoreSum/winningScoreHistory.size()) + " guesses.";

                JLabel winsLabel = new JLabel("<html>" + winsString + "<html>");
                winsLabel.setVisible(true);
                winsLabel.setBounds(10, 0, 300, 50);
                statsFrame.add(winsLabel);


            }

        }
        statsFrame.setLayout(null);
        statsFrame.getContentPane().setPreferredSize(new Dimension(320, 50));
        statsFrame.setVisible(true);
        statsFrame.pack();
    }
}

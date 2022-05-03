package mastermind.GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.event.*;
import java.util.LinkedList;

import mastermind.Game.Game;
// import mastermind.Game.PatternGenerator;
import mastermind.Game.StaticCachedPatternGenerator;

public class GameGUI {

    private SettingsPanel settingsPanel;
    private GameRows gameRows;
    private Game currGame;
    private JFrame frame;


    private LinkedList<Integer> winningScoreHistory;
    private int gamesPlayedCount;


    //When instantiated, creates game with default code length = 4 and duplicates allowed = true
    public GameGUI() {
        this.winningScoreHistory = new LinkedList<>();
        this.gamesPlayedCount = 0;

        try {
            // this.currGame = new Game(PatternGenerator.generatePattern(4, true));
            this.currGame = new Game(StaticCachedPatternGenerator.getPattern(4, true));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        createAndShowGUI();

    }

    public void createAndShowGUI() {
        this.frame = new JFrame();
        int codeLength = currGame.LENGTH;
        
        //Getting width of components based off # of columns (code length)
        int width = codeLength*75;
        if (codeLength == 4) {
            width = 400+180;
        } else if (codeLength == 5){
            width = 520+120;
        } else if (codeLength == 6) {
            width = 620+120;
        } else if (codeLength == 7) {
            width = 720+120;
        } else {
            width = 820+120;
        }

        //Game row includes the 0-7 dropdowns, the bulls and cows display, and submit guess butotn
        this.gameRows = new GameRows(codeLength, currGame);
        gameRows.setSize(width, 350);
        frame.add(gameRows);

        //Code length and duplicates allowed options
        this.settingsPanel = new SettingsPanel();
        settingsPanel.setBounds(0, 400, width, 300);
        frame.add(settingsPanel);

        //New Game button, with action listener that calls the startNewGame function when pressed
        JButton newGameButton = new JButton("Start New Game");
        newGameButton.setBounds((width/2)-75,350,150, 50);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startNewGame();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        frame.add(newGameButton);

        //add function to confirm exit
        //update session stats
        //show user session stats on exit
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener( new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                
                JFrame frame = (JFrame)e.getSource();
                int confirmed = JOptionPane.showConfirmDialog(frame, 
                "Are you sure you want to stop playing Mindbreaker? All progress will be lost",
                "Exit Mastermind",JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    updateSessionStatistics();
                    String sessionStatsString = getSessionsStatisticsString();
                    if (sessionStatsString.length() > 0) {
                        JOptionPane.showMessageDialog(frame, sessionStatsString);
                    }
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // showSessionStatisticsFrame();
                }
                
            }

        });

        //Configuring display settings for game GUI (frame)
        frame.setLayout(null);
        frame.getContentPane().setPreferredSize(new Dimension(950, 500));
        frame.setVisible(true);
        frame.pack();

        

    }

    public void startNewGame() throws Exception {

        updateSessionStatistics();


        //Gets the currently selected options within the settings panel
        boolean duplicatesAllowed = settingsPanel.getDuplicatesAllowed();
        int codeLength = settingsPanel.getCodeLength();

        //Width based off # of columns (code length)
        int width = codeLength*75;
        if (codeLength == 4) {
            width = 400+180;
        } else if (codeLength == 5){
            width = 520+120;
        } else if (codeLength == 6) {
            width = 620+120;
        } else if (codeLength == 7) {
            width = 720+120;
        } else {
            width = 820+120;
        }

        //Creating new game object
        // this.currGame = new Game(PatternGenerator.generatePattern(codeLength, duplicatesAllowed));
        this.currGame = new Game(StaticCachedPatternGenerator.getPattern(codeLength, duplicatesAllowed));
        
        //Removing previous game rows
        // this.gameRows.setVisible(false);
        frame.remove(this.gameRows);

        //Creating new game rows with new game object and adding to frame
        this.gameRows = new GameRows(codeLength, this.currGame);
        gameRows.setSize(width, 350);
        frame.add(gameRows);

        //Render removal of previous game rows
        frame.repaint();

        //Render addition of new game rows
        frame.revalidate();
        
        
    }

    private void updateSessionStatistics() {
        //Check if user has guessed at least once, if so then count as played game
        if (this.currGame.remainingGuesses < 10) {
            this.gamesPlayedCount++;
        }
        
        //Check if user has won, then add to score history
        if (this.currGame.gameWon) {
            this.winningScoreHistory.add(10-this.currGame.remainingGuesses);
        }
    }

    private String getSessionsStatisticsString() {

        String statsString = "";
        if (this.gamesPlayedCount > 0) {
            if (this.winningScoreHistory.size() == 0) {
                statsString = "<html>You won 0 of the " + this.gamesPlayedCount + " games you played.<html>";
                
            } else {
                int winningScoreSum = 0;
                for (int score : winningScoreHistory) {
                    winningScoreSum += score;
                }
                statsString = "<html>You won " + winningScoreHistory.size() + " of the " + gamesPlayedCount + " games you played, with an average winning guess count of " 
                + (double)(winningScoreSum/winningScoreHistory.size()) + " guesses.<html>";
            }
        }

        return statsString;
    }

 
    
}

package mastermind.GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.*;
import mastermind.Game.Game;
import mastermind.Game.PatternGenerator;
import mastermind.Game.StaticCachedPatternGenerator;

public class GameGUI {

    private SettingsPanel settingsPanel;
    private GameRows gameRows;
    private Game currGame;
    private JFrame frame;

    //When instantiated, creates game with default code length = 4 and duplicates allowed = true
    public GameGUI() {

        try {
            // this.currGame = new Game(PatternGenerator.generatePattern(4, true));
            this.currGame = new Game(StaticCachedPatternGenerator.getPattern(4));
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

        //Configuring display settings for game GUI (frame)
        frame.setLayout(null);
        frame.getContentPane().setPreferredSize(new Dimension(950, 500));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

    public void startNewGame() throws Exception {

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
        this.currGame = new Game(StaticCachedPatternGenerator.getPattern(codeLength));
        
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

 
    
}

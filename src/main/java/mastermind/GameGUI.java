package mastermind;

import javax.print.attribute.SetOfIntegerSyntax;

// import javax.swing.BoxLayout;
// import javax.swing.JComboBox;
// import java.awt.GridLayout;
// import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.*;



public class GameGUI {

    SettingsPanel settingsPanel;
    GameRows gameRows;
    Game currGame;
    JFrame frame;

    public GameGUI(Game game) {

        this.currGame = game;
        createAndShowGUI();

    }

    public void createAndShowGUI() {
        this.frame = new JFrame();
        int cols = currGame.LENGTH;
        
        int width = cols*75;
        if (cols == 4) {
            width = 400+180;
        } else if (cols == 5){
            width = 520+120;
        } else if (cols == 6) {
            width = 620+120;
        } else if (cols == 7) {
            width = 720+120;
        } else {
            width = 820+120;
        }

        this.gameRows = new GameRows(cols, currGame);
        gameRows.setSize(width, 350);
        frame.add(gameRows);

        this.settingsPanel = new SettingsPanel();
        settingsPanel.setBounds(0, 400, width, 300);
        frame.add(settingsPanel);

        JButton newGameButton = new JButton("Start New Game");
        newGameButton.setBounds((width/2)-75,350,150, 50);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    startNewGame();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                // System.out.println(settingsPanel.getDuplicatesAllowed());
                // System.out.println(settingsPanel.getCodeLength());
            }
        });


        frame.add(newGameButton);



        // frame.add()
        frame.setLayout(null);
        // frame.setSize(350, 250);
        frame.getContentPane().setPreferredSize(new Dimension(950, 500));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

    public void startNewGame() throws Exception {
        // System.out.println(settingsPanel.getDuplicatesAllowed());
        // System.out.println(settingsPanel.getCodeLength());

        boolean duplicatesAllowed = settingsPanel.getDuplicatesAllowed();
        int codeLength = settingsPanel.getCodeLength();

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

        Game game = new Game(PatternGenerator.generatePattern(codeLength, duplicatesAllowed));
        this.gameRows.setVisible(false);
        frame.remove(this.gameRows);
        this.gameRows = new GameRows(codeLength, game);
        gameRows.setSize(width, 350);
        frame.add(gameRows);
        frame.revalidate();
        
        
    }

 
    
}

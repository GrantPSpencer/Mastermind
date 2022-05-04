package mastermind.GUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mastermind.Game.Game;
import java.awt.event.*;
import java.util.Arrays;

public class GiveHintPanel extends JPanel {

    private Game currGame;
    private JButton hintButton;
    public JLabel hintLabel;

    public GiveHintPanel(Game game) {
        this.currGame = game;
        this.hintLabel = drawHintLabel();
        this.hintButton = drawHintButton();

        //TODO set bounds

        this.hintButton.setBounds(10,10,150,50);
        this.hintLabel.setBounds(10,70,150, 50);

        this.add(hintButton);
        this.add(hintLabel);
        

    }

    private JButton drawHintButton() {

        JButton button = new JButton("Give hint");
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] hintArray = currGame.giveHint();
                int hintIndex = hintArray[0];
                int hintValue = hintArray[1];
                
                hintLabel.setText("Digit " + hintValue + " is in column " +  (hintIndex+1));
            }
        });

        
        return button;
    }

    private JLabel drawHintLabel() {

        JLabel label = new JLabel();
        return label;
        
    }




    


}

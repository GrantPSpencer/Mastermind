package mastermind.GUI;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.Arrays;
import mastermind.Game.Game;


public class GameRows extends JPanel {
    
    private final int COLS;
    private JLabel[][] responseLabels;
    private JComboBox[][] dropdownBoxes;
    private JButton[] submitGuessButtons;
    private Game game;
    // private JPanel panel;

    public GameRows(int columns, Game game) {
        this.COLS = columns;
        this.game = game;
        dropdownBoxes = new JComboBox[10][this.COLS];
        responseLabels = new JLabel[10][this.COLS];
        submitGuessButtons = new JButton[10];

        drawRow();
        
    }




    private void drawRow() {
        String[] optionsToChoose = {"0","1","2","3","4","5","6","7"};
        JComboBox<String> jComboBox;
        for (int i =0; i < 10; i++) {
            for (int j = 0; j < this.COLS; j++) {
                jComboBox = new JComboBox<>(optionsToChoose);
                jComboBox.setBounds((i*50), (j*20), 50, 20);
                if (i != 9) {
                    jComboBox.setEnabled(false);
                }
                dropdownBoxes[9-i][j] = jComboBox;
                this.add(jComboBox);
            }
            for (int j = 0; j < this.COLS; j++) {
                JLabel jLabel = new JLabel("     ");
                // jLabel.setSize(new Dimension(50, 25));
                jLabel.setOpaque(true);
                jLabel.setBackground(Color.white);
                responseLabels[9-i][j] = jLabel;
                this.add(jLabel);

            }
            
            JButton submitGuessButton = new JButton("Submit Guess " + (10-i));
            if (i != 9) {
                submitGuessButton.setEnabled(false);
            }

            submitGuessButtons[9-i] = submitGuessButton;
            submitGuessButton.setActionCommand(Integer.toString(9-i));
                submitGuessButton.addActionListener(new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String actionCommand = button.getActionCommand();
                    Integer buttonIndex = Integer.parseInt(actionCommand);
                    int[] guessArray = new int[COLS];
                    int i = 0;
                    for (JComboBox dropdownBox : dropdownBoxes[buttonIndex]) {
                        guessArray[i++] = dropdownBox.getSelectedIndex();
                        dropdownBox.setEnabled(false);
                    }

                
                    submitGuessButtons[buttonIndex].setEnabled(false);

                    

                    //create guess array
                    // System.out.println(Arrays.toString(guessArray));
                    int[] feedback = game.guess(guessArray);

                    int bulls = feedback[0];
                    int cows = feedback[1];
                    int j = 0;
                    // System.out.println("Bulls: " + bulls + ", Cows: " + cows);
                    while (bulls > 0) {
                        responseLabels[buttonIndex][j].setBackground(Color.red);
                        bulls--;
                        j++;
                    }

                    while (cows > 0) {
                        responseLabels[buttonIndex][j].setBackground(Color.yellow);
                        cows--;
                        j++;
                    }


                    if (buttonIndex != 9 && feedback[0] != 4) {
                        for (JComboBox dropdownBox : dropdownBoxes[buttonIndex+1]) {
                            dropdownBox.setEnabled(true);
                        }
                        submitGuessButtons[buttonIndex+1].setEnabled(true);
                    }

                    if (feedback[0] == 4) {
                        submitGuessButtons[buttonIndex].setText("Congrats, you won!");
                    } else if (buttonIndex == 9){
                        submitGuessButtons[buttonIndex].setText("Sorry, you lost! :( ");
                    }





                }
            });

            

            this.add(submitGuessButton);
            
        }
 
    }


    public JLabel[][] getResponseLabels() {
        return this.responseLabels;
    }
    
    public JButton[] getSubmitGuessButtons() {
        return this.submitGuessButtons;
    }

    public JComboBox[][] getDropdownBoxes() {
        return this.dropdownBoxes;
    }


    



}

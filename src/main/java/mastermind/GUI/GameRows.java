package mastermind.GUI;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Color;
import mastermind.Game.Game;


public class GameRows extends JPanel {
    
    private final int COLS;
    private JLabel[][] responseLabels;
    private JComboBox[][] dropdownBoxes;
    private JButton[] submitGuessButtons;
    private Game game;

    //Storing an array of the dropdown boxes, response labels, and submit guess buttons will allow
        // us to modify each row according to the index given to us when the user submits their guess
    public GameRows(int columns, Game game) {
        this.COLS = columns;
        this.game = game;
        dropdownBoxes = new JComboBox[10][this.COLS];
        responseLabels = new JLabel[10][this.COLS];
        submitGuessButtons = new JButton[10];

        drawRow();
        
    }




    private void drawRow() {
        
        //Creating the dropdown boxes, all are disabled except for the first row "Guess 1"
        //Indices are reversed, index 9 = first row, index 0 = 10th row
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

            //Creating response labels, where we display # of bulls (red squares) and cows (yellow squares)
            for (int j = 0; j < this.COLS; j++) {
                JLabel jLabel = new JLabel("     ");
                jLabel.setOpaque(true);
                jLabel.setBackground(Color.white);
                responseLabels[9-i][j] = jLabel;
                this.add(jLabel);

            }
            
            //Creating submit guess buttons, all but first row is disabled
            JButton submitGuessButton = new JButton("Submit Guess " + (10-i));
            if (i != 9) {
                submitGuessButton.setEnabled(false);
            }
            
            submitGuessButtons[9-i] = submitGuessButton;

            
            //Setting action command to string of row index, 
            // allowing us to retrieve later when button is pressed
            submitGuessButton.setActionCommand(Integer.toString(9-i));
            
            //Adding event trigger to button press
            submitGuessButton.addActionListener(new ActionListener() {
                
                @Override 
                public void actionPerformed(ActionEvent e) {
                    
                    //Getting row index from previously set action command
                    JButton button = (JButton) e.getSource();
                    String actionCommand = button.getActionCommand();
                    Integer buttonIndex = Integer.parseInt(actionCommand);
                    

                    //Iterate over dropdown boxes in current row, adding values to the guess array
                    // and disabling the dropdown boxes to prevent future modification
                    int[] guessArray = new int[COLS];
                    int i = 0;
                    for (JComboBox dropdownBox : dropdownBoxes[buttonIndex]) {
                        guessArray[i++] = dropdownBox.getSelectedIndex();
                        dropdownBox.setEnabled(false);
                    }

                    //Disabling submit guess buttons to prevent future use
                    submitGuessButtons[buttonIndex].setEnabled(false);

   
                    //Getting # of bulls and cows from the Game.guess method
                    int[] feedback = game.guess(guessArray);
                    int bulls = feedback[0];
                    int cows = feedback[1];

                    //Iterate over response labels, set to color red to reflect # of bulls
                    int j = 0;
                    while (bulls > 0) {
                        responseLabels[buttonIndex][j].setBackground(Color.red);
                        bulls--;
                        j++;
                    }

                    //Iterate over response labels, set to color yellow to reflect # of cows
                    while (cows > 0) {
                        responseLabels[buttonIndex][j].setBackground(Color.yellow);
                        cows--;
                        j++;
                    }

                    //Iterate over next row, enabling the dropdown boxes and submit guess button
                    if (buttonIndex != 9 && feedback[0] != 4) {
                        for (JComboBox dropdownBox : dropdownBoxes[buttonIndex+1]) {
                            dropdownBox.setEnabled(true);
                        }
                        submitGuessButtons[buttonIndex+1].setEnabled(true);
                    }

                    //Check if won (bulls == 0), else check if that was the last guess (loss)
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


    //Public methods to access component arrays, if needed in future
    // public JLabel[][] getResponseLabels() {
    //     return this.responseLabels;
    // }
    
    // public JButton[] getSubmitGuessButtons() {
    //     return this.submitGuessButtons;
    // }

    // public JComboBox[][] getDropdownBoxes() {
    //     return this.dropdownBoxes;
    // }


    



}

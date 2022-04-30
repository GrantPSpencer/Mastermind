package mastermind;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

import java.awt.GridLayout;
// import java.awt.addActionListener;
// import java.awt.ActionEvent;
import java.awt.event.*;

public class Main {
    

    // public JPanel drawRows() {
    //     JPanel panel = new JPanel();
    //     String[] optionsToChoose = {"0","1","2","3","4","5","6","7"};
    //     for (int i = 0; i < 4; i++) {
    //         JComboBox<String> jComboBox;
    //         jComboBox = new JComboBox<>(optionsToChoose);
    //         jComboBox.setBounds((i*50), 10, 50, 20);
    //         panel.add(jComboBox);
    //         panel.setLayout(new GridLayout(0, 1, 0, 3));
    //     }
    //     return panel;
        
    // }

    // private frame drawDropdown() {
    //     String[] optionsToChoose = {"0","1","2","3","4","5","6","7"};
    //     JComboBox<String> jComboBox;

    //     jComboBox = new JComboBox<>(optionsToChoose);
    //     jComboBox.setBounds((i*50), 10, 50, 20);
            
    // }

    public static void main(String[] args) {

        try {
            GameGUI gameGUI = new GameGUI(new Game(PatternGenerator.generatePattern(4, true)));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        // Main main = new Main();
        // JFrame frame = new JFrame();
        // int cols = 8;
        
        // int width = cols*75;
        // if (cols == 4) {
        //     width = 400+120;
        // } else if (cols == 5){
        //     width = 520+120;
        // } else if (cols == 6) {
        //     width = 620+120;
        // } else if (cols == 7) {
        //     width = 720+120;
        // } else {
        //     width = 820+120;
        // }

        // GameRow gameRow = new GameRow(4);
        // frame.getContentPane().add(gameRow);
        // gameRow.setVisible(true);
        // frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));

        
        // frame.add(main.drawRows());


        
        // GameRows gameRows = new GameRows(cols);
        // gameRows.setSize(width, 350);
        // frame.add(gameRows);

        // SettingsPanel settingsPanel = new SettingsPanel();
        // // settingsPanel.setSize(300, 300);
        // settingsPanel.setBounds(0, 400, width, 300);
        // frame.add(settingsPanel);

        // JButton newGameButton = new JButton("Start New Game");
        // newGameButton.setBounds((width/2)-75,350,150, 50);
        // newGameButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
                
                
        //         // System.out.println(settingsPanel.getDuplicatesAllowed());
        //         // System.out.println(settingsPanel.getCodeLength());
        //     }
        // });


        // frame.add(newGameButton);



        // // frame.add()
        // frame.setLayout(null);
        // // frame.setSize(350, 250);
        // frame.getContentPane().setPreferredSize(new Dimension(900, 500));
        // frame.setVisible(true);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.pack();
        
    }

}

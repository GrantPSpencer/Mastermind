package mastermind;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mastermind.GUI.GameGUI;
import mastermind.Game.Game;
import mastermind.Game.PatternGenerator;

import java.awt.Dimension;

import java.awt.GridLayout;
// import java.awt.addActionListener;
// import java.awt.ActionEvent;
import java.awt.event.*;

public class Main {
    
    public static void main(String[] args) {

        try {
            GameGUI gameGUI = new GameGUI();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

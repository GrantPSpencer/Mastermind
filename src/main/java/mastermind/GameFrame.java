package mastermind;
import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame {
    
    public GameFrame() {
        setSize( 500, 500 );
        setVisible( true ); 

        GridPanel gridPanel = new GridPanel();

        gridPanel.setVisible(true);
        this.getContentPane().add(gridPanel,BorderLayout.CENTER);
        

    }    

    // private void paint(Graphics g) {
    //     for ( int x = 30; x <= 180; x += 30 ) {
    //         for ( int y = 30; y <= 180; y += 30 ) {
    //             g.drawRect( x, y, 30, 30 );
    //         }
    //     }
    // }



    public static void main(String[] args) {
        GameFrame gframe = new GameFrame();
        gframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

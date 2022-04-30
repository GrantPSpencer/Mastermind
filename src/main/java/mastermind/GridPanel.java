package mastermind;
import java.awt.*;
import javax.swing.*;

public class GridPanel extends JPanel {
   

        public GridPanel() {       
            setSize( 500, 500 );
            setVisible( true );   
        } 
       
        public void paint( Graphics g )    
        {  
            for ( int x = 30; x <= 180; x += 30 ) {
                for ( int y = 30; y <= 180; y += 30 ) {
                    g.drawRect( x, y, 30, 30 );
                }
            }
            
            
       
        } 
        public static void main( String args[] ) 
        {
            GridPanel application = new GridPanel();
            // application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );   
    }
}

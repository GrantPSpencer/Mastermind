package mastermind;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;

public class SettingsPanel extends JPanel {
    
    private JCheckBox jCheckBox;
    private JComboBox jComboBox;
    private String[] options;


    public SettingsPanel() {

        this.jCheckBox = new JCheckBox("Allow Duplicates?");
        this.jCheckBox.setSelected(true);
        // this.jCheckBox.setPreferredSize(new Dimension(100, 100));
        add(this.jCheckBox,BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        JLabel jLabel = new JLabel("Code Length:");
        this.options = new String[] {"4","5","6","7","8"};
        this.jComboBox = new JComboBox<>(options);
        
        panel2.add(jLabel);
        panel2.add(this.jComboBox);
        add(panel2);


    }

    public boolean getDuplicatesAllowed() {
        return this.jCheckBox.isSelected();
    }

    public int getCodeLength() {
        return Integer.valueOf(this.options[this.jComboBox.getSelectedIndex()]);
    }


    
    


}

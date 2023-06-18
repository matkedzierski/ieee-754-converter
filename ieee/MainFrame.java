package ieee;

import javax.swing.*;
import java.awt.*;
import org.kerbaya.ieee754lib.IEEE754;

public class MainFrame extends JFrame {

    public MainFrame()
    {
        setTitle("Konwerter liczb zmiennoprzecinkowych");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
 
        JPanel mainPanel = new JPanel();
        setMinimumSize(new Dimension(400, 0));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        
        addLabel(mainPanel, "Wejście (bin/hex):");
        JTextField input = addInput(mainPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        
        JRadioButton radio1 = addRadioButton(mainPanel, "Kod wewnętrzny -> IEEE-754", true);
        JRadioButton radio2 = addRadioButton(mainPanel, "IEEE-754 -> Kod wewnętrzny", false);
        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);

        mainPanel.add(Box.createRigidArea(new Dimension(10, 10)))   ;
        JButton convertButton = addButton(mainPanel);
    

        mainPanel.add(Box.createRigidArea(new Dimension(10, 20)));

        addLabel(mainPanel, "Wyjście (bin/hex):");
        JTextField output = addOutput(mainPanel);
    
        convertButton.addActionListener((event)->{
            String b = input.getText();
            String out;
            if(radio1.isSelected()){
                IEEE754 in = FloatingPointUtils.parseInternal(b);
                out = FloatingPointUtils.toIEEE(in);
            } else {
                IEEE754 in = FloatingPointUtils.parseIEEE754(b);
                out = FloatingPointUtils.toInternal(in);
            }
            output.setText(FloatingPointUtils.divideInFours(out));
        });
        
        
        setContentPane(mainPanel);
    }

    public JRadioButton addRadioButton(Container c, String label, boolean selected){
        JRadioButton radio1 = new JRadioButton(label, selected);
        radio1.setAlignmentX(0.5f);
        c.add(radio1);
        return radio1;
    }

    public JTextField addOutput(Container c){
        JTextField tf = new JTextField();
        tf.setAlignmentX(0.5f);
        tf.setEditable(false);
        tf.setHorizontalAlignment(SwingConstants.CENTER);
        c.add(tf);
        return tf;
    }

    public JButton addButton(Container c){
        JButton button = new JButton("Konwertuj");
        button.setAlignmentX(0.5f);
        c.add(button);
        return button;
    }
    public JTextField addInput(Container c){
        JTextField tf = new JTextField();
        tf.setAlignmentX(0.5f);
        tf.setHorizontalAlignment(SwingConstants.CENTER);
        c.add(tf);
        return tf;
    }
    public void addLabel(Container c, String text){
        JLabel label = new JLabel(text);
        label.setAlignmentX(0.5f);
        c.add(label);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
        frame.pack();
        frame.setVisible(true);
    }
}
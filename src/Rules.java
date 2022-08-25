import javax.swing.JFrame;
import javax.swing.JLabel;

public class Rules extends JFrame{

    //private JFrame frame = new JFrame();
    private final JLabel label1 = new JLabel("There should be rules here");
    private final JLabel label2 = new JLabel("There should be rules here");
    private final JLabel label3 = new JLabel("There should be rules here");
    private final JLabel label4 = new JLabel("There should be rules here");

    Rules(){
        super("Instructions");
        setResizable(false);
        setLayout(null);
        setSize(500, 150);
        label1.setBounds(0, 0, 500, 500);
        label1.setText("The numbers on the board represent how many bombs are adjacent to a square.");
        label1.setVerticalAlignment(JLabel.TOP);
        add(label1);

        label2.setBounds(0, 30, 500, 500);
        label2.setText("For example, if a square has a \"3\" on it, then there are 3 bombs next to that square.");
        label2.setVerticalAlignment(JLabel.TOP);
        add(label2);

        label3.setBounds(0, 60, 500, 500);
        label3.setText("The bombs could be above, below, right left, or diagonal to the square.");
        label3.setVerticalAlignment(JLabel.TOP);
        add(label3);

        label4.setBounds(0, 90, 500, 500);
        label4.setText("Avoid all the bombs and expose all the empty spaces to win Minesweeper.");
        label4.setVerticalAlignment(JLabel.TOP);
        add(label4);

        setVisible(true);
    }
}

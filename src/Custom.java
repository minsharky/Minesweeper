import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.*;

public class Custom extends JFrame implements ChangeListener {
    private final JPanel panel = new JPanel();
    private final JLabel label1 = new JLabel();
    private final JLabel label2 = new JLabel();
    private final JLabel label3 = new JLabel();
    public static JSlider bombs = new JSlider(0,90,20);
    public static JSlider frameX = new JSlider(5,30,15);
    public static JSlider frameY = new JSlider(5,30,15);

    Custom(){
        super("Customize");
        setResizable(false);
        setLayout(null);
        setSize(500, 500);

        bombs.setPreferredSize(new Dimension(400,100));
        frameX.setPreferredSize(new Dimension(400,100));
        frameY.setPreferredSize(new Dimension(400,100));
        //this.repaint();

        bombs.setPaintTicks(true);
        bombs.setMinorTickSpacing(5);
        bombs.setPaintTrack(true);
        bombs.setMajorTickSpacing(10);
        bombs.setPaintLabels(true);
        bombs.addChangeListener(this);
        label1.setText(bombs.getValue()+ "% of Bombs Randomized");

        frameX.setPaintTicks(true);
        frameX.setMinorTickSpacing(1);
        frameX.setPaintTrack(true);
        frameX.setMajorTickSpacing(5);
        frameX.setPaintLabels(true);
        frameX.addChangeListener(this);
        label2.setBounds(100,200,10,10);
        label2.setText("Number of Rows: " +frameX.getValue());

        frameY.setPaintTicks(true);
        frameY.setMinorTickSpacing(1);
        frameY.setPaintTrack(true);
        frameY.setMajorTickSpacing(5);
        frameY.setPaintLabels(true);
        frameY.addChangeListener(this);
        label3.setBounds(100,400,10,10);
        label3.setText("Number of Columns: " +frameY.getValue());

        panel.add(label1);
        panel.add(bombs);

        /*panel.add(label2);
        panel.add(frameX);
        panel.add(label3);
        panel.add(frameY);*/

        panel.setBounds(0,0,500,500);
        add(panel);

        setVisible(true);
    }

    public void stateChanged(ChangeEvent e) {
        label1.setText(bombs.getValue()+ "% of Bombs Randomized");
        label2.setText("Number of Rows: " +frameX.getValue());
        label3.setText("Number of Columns: " +frameY.getValue());
    }
}

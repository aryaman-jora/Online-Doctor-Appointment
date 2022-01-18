import javax.swing.*;
import java.awt.*;

public class FeedBackStars extends JApplet
    {

        public void init()
            {
                setVisible(true);
                setSize(700, 700);
                setLayout(new BorderLayout());

                JPanel p = new JPanel();
                p.setLayout(new GridLayout(1, 5));

                JButton[] bt = new JButton[5]; // creating ref
                for (int i = 0; i < bt.length; i++)
                    {

                        bt[i] = new JButton(new ImageIcon("download.jpg")); // Memory Allocation
                        // bt[i] = new Button( Integer.toString(i+1));
                    }

                for (JButton jButton : bt)
                    {
                        jButton.setRolloverIcon(new ImageIcon("download.jpg"));
                        p.add(jButton);
                    }

                add(p, BorderLayout.NORTH);
            }
    }

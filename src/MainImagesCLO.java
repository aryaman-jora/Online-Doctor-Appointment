import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class MainImagesCLO extends JPanel implements ActionListener
    {

        private final JPanel pmain;
        private final JButton bnext;
        private final JButton bprev;
        private final CardLayout clo;

        JLabel lb2;


        public MainImagesCLO()
            {
                JPanel p1 = new JPanel();
                clo = new CardLayout();
                bnext = new JButton("-->");
                bprev = new JButton("<--");
                bnext.addActionListener(this);
                bprev.addActionListener(this);

                //body
                setLayout(new BorderLayout());

                // p2 = new JPanel();
                //p2.add(new JLabel("ONLINE DOCTOR APPOINTMENT"));
                //add(p2,BorderLayout.NORTH);

                p1.add(bprev);
                p1.add(bnext);
                add(p1, BorderLayout.SOUTH);

// if panel in the card layout are not lenghty then do need to create another panel class
// make panel in the function itself and use it

                // For CardLayout
                JPanel pimage1 = new JPanel();
                JLabel lb1 = new JLabel();
                lb1.setIcon(new ImageIcon("medical-appointment.png"));

                pimage1.add(lb1);


                JPanel pimage2 = new JPanel();
                JLabel lb2;
                lb2 = new JLabel();
                lb2.setIcon(new ImageIcon("calendar.jpg"));

                pimage2.add(lb2);

                JPanel pimage3 = new JPanel();
                JLabel lb3 = new JLabel();
                lb3.setIcon(new ImageIcon("contact-us.jpg"));

                pimage3.add(lb3);


                pmain = new JPanel();
                pmain.setSize(new Dimension(200, 200));
                pmain.setPreferredSize(new Dimension(200, 200));

                pmain.setLayout(clo);
                pmain.setBackground(Color.DARK_GRAY);
                pmain.add("image1", pimage1);
                pmain.add("image2", pimage2);
                pmain.add("image3", pimage3);

                add(pmain, BorderLayout.CENTER);
            }

        @Override
        public void actionPerformed(ActionEvent ae)
            {
                Object src = ae.getSource();

                if (src == bnext)
                    {
                        clo.next(pmain);
                    } else if (src == bprev)
                    {
                        clo.previous(pmain);
                    }

            }
    }


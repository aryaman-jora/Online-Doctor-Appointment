import javax.swing.*;
import java.awt.*;

class AdminWelcome extends JPanel
    {

        private final JLabel lbwelcome;
        private JLabel lbpic;

        AdminWelcome()
            {
                JLabel lbimg = new JLabel(new ImageIcon("medical-appointment.jpg"));
                lbwelcome = new JLabel("Welcome to online Doctor Appointment, Admin");

                JPanel p1 = new JPanel();
                JPanel p2 = new JPanel();
                JPanel p3 = new JPanel();

                p1.add(lbimg);
                p2.add(lbwelcome);


                add(p1, BorderLayout.CENTER);
                add(p2, BorderLayout.SOUTH);
                add(p3, BorderLayout.NORTH);

                style();
            }

        private void style()
            {
                Font flb = new Font("comic sans", Font.ITALIC + Font.BOLD, 40);
                lbwelcome.setFont(flb);
            }
    }



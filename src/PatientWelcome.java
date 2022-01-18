import javax.swing.*;
import java.awt.*;

class PatientWelcome extends JPanel
    {

        private final JLabel lbwelcome;

        PatientWelcome(String userp)
            {
                JLabel lbimg = new JLabel(new ImageIcon("medical-appointment.jpg"));
                lbwelcome = new JLabel("Welcome to online Doctor Appointment, Patient " + userp);

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






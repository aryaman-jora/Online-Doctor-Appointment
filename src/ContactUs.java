import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class ContactUs extends JPanel
    {

        private final JLabel lbaddress;
        private final JLabel lbemail;
        private final JLabel lbcontact;

        ContactUs()
            {
                setVisible(true);
                setLayout(new BorderLayout());
                JLabel lbimg = new JLabel(new ImageIcon("contact-us.jpg"));
                lbaddress = new JLabel("Address : " + "3-D-32 JNV Colony,Bikaner,Rajasthan");
                lbemail = new JLabel("Mail us at:  " + "  onlinedoct24@gmail.com");
                lbcontact = new JLabel(":: Contact Us ::");

                JPanel p1 = new JPanel();
                JPanel p2 = new JPanel();
                JPanel p3 = new JPanel();

                p1.add(lbimg);
                p2.add(lbaddress);
                p2.add(new JLabel("\t"));
                p2.add(lbemail);
                p3.add(lbcontact);

                //p1.setSize(new Dimension(400,600));
                //p1.setPreferredSize(new Dimension(400,600));

                add(p3, BorderLayout.NORTH);
                add(p1, BorderLayout.CENTER);
                add(p2, BorderLayout.SOUTH);

                style();
            }


        private void style()
            {

                Color clb = new Color(20, 110, 140);
                Font flb = new Font("comic sans", Font.ITALIC + Font.BOLD, 40);
                Border loweredbevel = BorderFactory.createLoweredBevelBorder();

                lbaddress.setFont(flb);
                lbemail.setFont(flb);
                lbcontact.setFont(flb);

                lbaddress.setForeground(clb);
                lbemail.setForeground(clb);
                lbcontact.setForeground(Color.red);
            }
    }


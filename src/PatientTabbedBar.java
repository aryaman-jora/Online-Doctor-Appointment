import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PatientTabbedBar extends JFrame implements ActionListener
    {

        private JPanel logout;
        private JButton btlogout;
        private String userp;

        PatientTabbedBar(String userp)
            {
                this.userp = userp;

                setVisible(true);
                setSize(new Dimension(3000, 1000));

                setLayout(new BorderLayout());
                // This is JFrame called in PatientLoginHome // see logout also dispose
                JTabbedPane jtp = new JTabbedPane();
                jtp.addTab("Home", new PatientWelcome(userp));
                jtp.addTab("Search Doctor", new Patient_SearchDoctor(userp));
                jtp.addTab("About Me", new PatientInfo(userp));
                jtp.addTab("Manage Password", new Patient_ChangePassword(userp));
                userlogout();
                jtp.addTab("Log Out", logout);
                jtp.addTab("Contact Us", new ContactUs());


                jtp.setBackground(Color.black);
                jtp.setForeground(Color.white);

                jtp.setBackgroundAt(3, Color.red);
                jtp.setBackgroundAt(4, Color.red);
                add(jtp);
            }

        private void userlogout()
            {
                logout = new JPanel();
                btlogout = new JButton("Log out");
                logout.add(btlogout);
                btlogout.addActionListener(this);
            }

        @Override
        public void actionPerformed(ActionEvent e)
            {

                Object src = e.getSource();
                if (src == btlogout)
                    {
                        int ans = JOptionPane.showConfirmDialog(null, "Do you really want to logout ,Patient " + userp, "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (ans == JOptionPane.YES_OPTION)
                            this.dispose(); // when it is frame

                    }
            }
    }



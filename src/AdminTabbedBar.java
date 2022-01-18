import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AdminTabbedBar extends JFrame implements ActionListener
    {

        private JPanel logout;
        private JButton btlogout;

        AdminTabbedBar()
            {
                setVisible(true);
                setLayout(new BorderLayout());
                setSize(new Dimension(3000, 1000));

                userlogout();
                // This is JFrame called in AdminLoginHome
                JTabbedPane jtp = new JTabbedPane();
                jtp.addTab(" Home ", new AdminWelcome());
                jtp.addTab("See Doctor", new AdminShowAllDoctor());
                jtp.addTab("See Patients", new AdminShowAllPatient());
                jtp.addTab("Log Out", logout);
                jtp.addTab(" Contact Us ", new ContactUs());


                jtp.setBackground(Color.black);
                jtp.setForeground(Color.white);

                jtp.setBackgroundAt(3, Color.red);
                // For logout
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
                        int ans = JOptionPane.showConfirmDialog(null, "Do you really want to logout ,Admin ", "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (ans == JOptionPane.YES_OPTION)
                            this.dispose(); // when it is frame

                    }
            }

    }



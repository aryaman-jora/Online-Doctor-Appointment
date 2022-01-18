import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class Patient_ChangePassword extends JPanel implements ActionListener
    {

        private final JLabel lbuser;
        private final JLabel lboldpass;
        private final JLabel lbnewpass;
        private final JLabel lbconfpass;
        private final JTextField txuser;
        private final JPasswordField txoldpss;
        private final JPasswordField txnewpass;
        private final JPasswordField txconfpass;
        private final JPanel P;
        private final JButton btsubmit;
        private final JButton btrefresh;
        private final String userp;

        Patient_ChangePassword(String userp)
            {
                this.userp = userp;

                setVisible(true);
                setLayout(new BorderLayout());
                setSize(700, 700);

                lbuser = new JLabel("Patient Username");
                lboldpass = new JLabel("Old Password");
                lbnewpass = new JLabel("New Password");
                lbconfpass = new JLabel("Confirm Password");

                txuser = new JTextField();
                txuser.setEnabled(false);
                txuser.setText(userp);
                txoldpss = new JPasswordField();
                txnewpass = new JPasswordField();
                txconfpass = new JPasswordField();

                btsubmit = new JButton("Submit");
                btrefresh = new JButton("Refresh");

                P = new JPanel();

                P.add(lbuser);
                P.add(txuser);

                P.add(lboldpass);
                P.add(txoldpss);

                P.add(lbnewpass);
                P.add(txnewpass);

                P.add(lbconfpass);
                P.add(txconfpass);

                P.add(btsubmit);
                P.add(btrefresh);

                P.setLayout(new GridLayout(5, 2, 20, 20));

                btsubmit.addActionListener(this);
                btrefresh.addActionListener(this);

                add(P);
                style();

            }

        private void style()

            {

                Font ftitle = new Font("comic sans", Font.ITALIC + Font.BOLD, 40);
                Border loweredbevel = BorderFactory.createLoweredBevelBorder();
                Border h = BorderFactory.createTitledBorder(loweredbevel, ":: Change Password ::", TitledBorder.CENTER, TitledBorder.TOP, ftitle, Color.red);
                Border k = BorderFactory.createMatteBorder(0, 10, 0, 0, Color.red);
                P.setBorder(BorderFactory.createCompoundBorder(h, k));

                Font flb = new Font("Arial", Font.BOLD, 20);
                lbuser.setFont(flb);
                lboldpass.setFont(flb);
                lbnewpass.setFont(flb);
                lbconfpass.setFont(flb);

                txuser.setFont(flb);
                txnewpass.setFont(flb);
                txoldpss.setFont(flb);
                txconfpass.setFont(flb);

                btsubmit.setFont(flb);
                btrefresh.setFont(flb);

                btsubmit.setBackground(Color.green);
                btrefresh.setBackground(Color.red);

                Color clb = new Color(20, 110, 140);
                lbuser.setForeground(clb);
                lboldpass.setForeground(clb);
                lbnewpass.setForeground(clb);
                lbconfpass.setForeground(clb);

            }

        @Override
        public void actionPerformed(ActionEvent e)
            {

                Object src = e.getSource();
                if (src == btsubmit)
                    {
                        String newpass = new String(txnewpass.getPassword());
                        String confpass = new String(txconfpass.getPassword());
                        if (!newpass.equals(confpass))
                            {
                                JOptionPane.showMessageDialog(null, "Both Password must be same", "Different Password", JOptionPane.ERROR_MESSAGE);
                                txnewpass.requestFocus();
                            } else
                            {

                                checkpass();
                            }

                    } else if (src == btrefresh)
                    {
                        txoldpss.setText("");
                        txnewpass.setText("");
                        txconfpass.setText("");
                        txoldpss.requestFocus();
                    }

            }

        private void checkpass()
            {
                try
                    {


                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  patienttb( username varchar(100),"
                                + "password varchar(100),"
                                + "email varchar(100),"
                                + "phone varchar(100),"
                                + "gender int,"
                                + "material int,"
                                + "dob date ,"
                                + "location varchar(100),"
                                + "address varchar(100),"
                                + "city varchar(100),"
                                + "state varchar(100),"
                                + "disease varchar(100),"
                                + "past varchar(100),"
                                + "primary key(username))");

                        PreparedStatement pres = con.prepareStatement("select count(*) from patienttb where username=? and password=?");
                        pres.setString(1, userp);
                        pres.setString(2, txoldpss.getText());

                        ResultSet rs = pres.executeQuery();
                        rs.next();
                        int c = rs.getInt(1);

                        if (c == 1)
                            {
                                // want to change pass
                                // JOptionPane.showInputDialog(message)
                                int ans = JOptionPane.showConfirmDialog(null, "Do you really want to change your password", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (ans == JOptionPane.YES_OPTION)
                                    {
                                        PreparedStatement prstmt = con.prepareStatement("update patienttb set password=? where username=?");
                                        prstmt.setString(1, txnewpass.getText());
                                        prstmt.setString(2, userp);
                                        prstmt.executeUpdate();
                                        JOptionPane.showMessageDialog(null, "Password Changed", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                                        txoldpss.setText("");
                                        txnewpass.setText("");
                                        txconfpass.setText("");
                                    }
                            } else
                            {
                                JOptionPane.showMessageDialog(null, "Password is Incorrect", "Invalid Password", JOptionPane.ERROR_MESSAGE);
                                txoldpss.requestFocus();
                            }
                        con.close();

                    } catch (ClassNotFoundException | SQLException ae)
                    {

                        ae.printStackTrace();
                    }

            }

    }


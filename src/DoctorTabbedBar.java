import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class DoctorTabbedBar extends JDialog implements ActionListener
    {

        private int c;
        private JPanel logout;
        private JButton btlogout;
        private String userd;

        DoctorTabbedBar(String userd)
            {
                this.userd = userd; // taking username from previous login form


                setVisible(true);
                setSize(new Dimension(3000, 1000));
                setLayout(new BorderLayout());
                // This is JFrame called in DoctorLoginHome // see logout also dispose
                JTabbedPane jtp = new JTabbedPane();

                userlogout();
                jtp.addTab("Home", new DoctorWelcome(userd));
                if (checkschedule())
                    {
                        jtp.addTab("MyAppointment", new DoctorMyAppointment(userd));
                        // Jtp.addTab("MyAppointment", new TodayDate(userd));
                        jtp.addTab("About Me", new DoctorInfo(userd));
                        jtp.addTab("Manage Password", new Doctor_ChangePassword(userd));
                        jtp.addTab("Manage Schedule", new Doctor_Manageschedule(userd));
                        jtp.addTab("Log Out", logout);
                        jtp.addTab("Contact Us", new ContactUs());

                        jtp.setBackgroundAt(3, Color.red);
                        jtp.setBackgroundAt(4, Color.red);
                        jtp.setBackgroundAt(5, Color.red);
                    } else
                    {
                        jtp.addTab("Set Schedule", new Doctor_Setschedule(userd));
                        jtp.addTab("Log Out", logout);
                        jtp.addTab("Contact Us", new ContactUs());

                        jtp.setBackgroundAt(1, Color.red);
                        jtp.setBackgroundAt(2, Color.red);
                    }


                jtp.setBackground(Color.black);
                jtp.setForeground(Color.white);

                //  Jtp.setBackgroundAt(3, Color.red);
                add(jtp);

            }

        private boolean checkschedule()
            {
                try
                    {


                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  DoctorscheduleTb(username varchar(100),days  varchar(100),morningtimestart varchar(100),morningtimeend varchar(100),eveningtimestart varchar(100),eveningtimeend varchar(100),primary key(username))");


                        PreparedStatement pstmt = con.prepareStatement("select count(*) from DoctorscheduleTb where username=?");
                        pstmt.setString(1, userd);
                        ResultSet rs = pstmt.executeQuery();

                        rs.next();
                        c = rs.getInt(1);

                    } catch (ClassNotFoundException | SQLException e)
                    {
                        e.printStackTrace();
                    }

                // it will c=1 for home and c=0 for setschedule
                // setschedule
                return c != 0; // home
                // it will open home in case exceptional c > 1 which is not possible as it will be executed once
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
                        int ans = JOptionPane.showConfirmDialog(null, "Do you really want to logout ,Doctor " + userd, "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (ans == JOptionPane.YES_OPTION)
                            this.dispose();
                    }
            }
    }



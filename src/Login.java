import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JApplet implements ActionListener
    {
        private JLabel lbtype;
        private JLabel lbuser;
        private JLabel lbpass;
        private JTextField txuser;
        private JPasswordField txpass;
        private JButton btsubmit;
        private JButton btcancel;
        private JButton btregister;
        private JPanel p1;
        private JPanel p2;
        private JPanel p3;
        private JPanel p4;
        private JPanel p5;
        private JPanel pmain;
        private JRadioButton admin, patient, doctor;

        public void init()
            {


                setVisible(true);
                setPreferredSize(new Dimension(1000, 800));
                setSize(new Dimension(1000, 800));
                setSize(1000, 800);

                admin = new JRadioButton("Admin");
                patient = new JRadioButton("Patient");
                doctor = new JRadioButton("Doctor");

                admin.setSelected(true);

                ButtonGroup type = new ButtonGroup();
                type.add(admin);
                type.add(patient);
                type.add(doctor);

                p1 = new JPanel();
                p1.setLayout(new GridLayout(1, 4, 100, 100));

                p2 = new JPanel();
                p2.setLayout(new GridLayout(1, 2));
                p3 = new JPanel();
                p3.setLayout(new GridLayout(1, 2));
                p4 = new JPanel();
                p4.setLayout(new GridLayout(1, 2));
                p5 = new JPanel();

                pmain = new JPanel();

                lbtype = new JLabel("Type");
                lbuser = new JLabel("Username");
                lbpass = new JLabel("Password");


                txuser = new JTextField(20);
                txpass = new JPasswordField(20);


                btsubmit = new JButton("Submit");
                btcancel = new JButton("Cancel");
                btregister = new JButton("Create New Account");

                style();

                print();

                admin.addActionListener(this);
                btsubmit.addActionListener(this);
                btcancel.addActionListener(this);
                btregister.addActionListener(this);
            }


        @Override
        public void actionPerformed(ActionEvent e)
            {

                Object src = e.getSource();
                if (src == btcancel)
                    {
                        System.exit(-1);
                    }
                if (src == btsubmit)
                    {
                        if (admin.isSelected())
                            {
                                checkAdmin();
                            }
                        if (patient.isSelected())
                            {
                                checkPatient();
                            }
                        if (doctor.isSelected())
                            {
                                checkDoctor();
                            }
                        refresh();
                    }
                if (src == btregister)
                    {
                        if (patient.isSelected())
                            new PatientID1();
                        if (doctor.isSelected())
                            new DoctorID1();
                    }
            }

        private void checkAdmin()
            {
                String pass = new String(txpass.getPassword());
                if (txuser.getText().equals("admin") && pass.equals("admin"))
                    {
                        JOptionPane.showMessageDialog(null, "You are now Login as Admin, " + txuser.getText(), "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                        new AdminTabbedBar();
                    } else
                    JOptionPane.showMessageDialog(null, "Admin, " + txuser.getText() + " does not exists.", "Error in Login", JOptionPane.ERROR_MESSAGE);
            }

        private void checkPatient()
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
                        pres.setString(1, txuser.getText());
                        String pass = new String(txpass.getPassword());
                        pres.setString(2, pass);

                        ResultSet rs = pres.executeQuery();
                        rs.next();
                        int c = rs.getInt(1);

                        if (c == 1)
                            {

                                JOptionPane.showMessageDialog(null, "You are now Login as Patient, " + txuser.getText(), "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                                String userp = txuser.getText();
                                new PatientTabbedBar(userp);
                            } else
                            JOptionPane.showMessageDialog(null, "Patient, " + txuser.getText() + " does not exists.", "Error in Login", JOptionPane.ERROR_MESSAGE);
                        con.close();

                    } catch (ClassNotFoundException | SQLException ae)
                    {
                        ae.printStackTrace();
                    }
            }

        private void checkDoctor()
            {
                try
                    {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  doctortb( username varchar(100),"
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
                                + "spec varchar(100),"
                                + "qual varchar(100),"
                                + "lang varchar(100),"
                                + "hname varchar(100),"
                                + "hloc varchar(100),"
                                + "cname varchar(100),"
                                + "cloc varchar(100),"
                                + "primary key(username))");

                        PreparedStatement pres = con.prepareStatement("select count(*) from doctortb where username=? and password=?");
                        pres.setString(1, txuser.getText());
                        String pass = new String(txpass.getPassword());
                        pres.setString(2, pass);

                        ResultSet rs = pres.executeQuery();
                        rs.next();
                        int c = rs.getInt(1);

                        if (c == 1)
                            {
                                JOptionPane.showMessageDialog(null, "You are now Login as " + txuser.getText(), "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                                String userd = txuser.getText();
                                new DoctorTabbedBar(userd);

                            } else
                            {
                                JOptionPane.showMessageDialog(null, " Doctor " + txuser.getText() + " does not exists.", "Error in Login", JOptionPane.ERROR_MESSAGE);
                            }
                        con.close();

                    } catch (ClassNotFoundException | SQLException ae)
                    {

                        ae.printStackTrace();
                    }
            }

        private void refresh()
            {
                txuser.setText("");
                txpass.setText("");
            }

        private void style()
            {

                Font ftitle = new Font("comic sans", Font.ITALIC + Font.BOLD, 40);
                Border loweredbevel = BorderFactory.createLoweredBevelBorder();
                Border h = BorderFactory.createTitledBorder(loweredbevel, ":: LOGIN HOME::", TitledBorder.CENTER, TitledBorder.TOP, ftitle, Color.red);
                Border k = BorderFactory.createMatteBorder(0, 10, 0, 0, Color.red);
                pmain.setBorder(BorderFactory.createCompoundBorder(h, k));

                btsubmit.setBackground(Color.green);
                btcancel.setBackground(Color.red);
                btregister.setBackground(Color.green);

                //Foreground color
                Color clb = new Color(20, 110, 140);
                lbtype.setForeground(clb);
                lbuser.setForeground(clb);
                lbpass.setForeground(clb);
                admin.setForeground(clb);
                patient.setForeground(clb);
                doctor.setForeground(clb);
                // Font
                Font flb = new Font("Comic Sans MS", Font.BOLD, 30);
                lbtype.setFont(flb);
                lbuser.setFont(flb);
                lbpass.setFont(flb);
                btsubmit.setFont(flb);
                btcancel.setFont(flb);
                btregister.setFont(flb);
                admin.setFont(flb);
                patient.setFont(flb);
                doctor.setFont(flb);

                Font ftx = new Font("Arial", Font.BOLD, 20);
                txuser.setFont(ftx);
                txpass.setFont(ftx);
            }

        private void print()
            {
                GridLayout g = new GridLayout(5, 1, 100, 100);
                pmain.setLayout(g);

                p1.add(lbtype);
                p1.add(admin);
                p1.add(patient);
                p1.add(doctor);

                p2.add(lbuser);
                p2.add(txuser);

                p3.add(lbpass);
                p3.add(txpass);

                p4.add(btsubmit);
                p4.add(btcancel);

                p5.add(btregister);

                pmain.add(p1);
                pmain.add(p2);
                pmain.add(p3);
                pmain.add(p4);
                pmain.add(p5);

                add(pmain);
            }


    }

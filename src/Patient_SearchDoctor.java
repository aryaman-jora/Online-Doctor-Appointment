import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.Objects;

class Patient_SearchDoctor extends JPanel implements ItemListener, ActionListener
    {

        private final JLabel lbstate;
        private final JLabel lbcity;
        private final JLabel lbspec;
        private final JLabel lbhos;
        private final JComboBox<String> cbcity;
        private final JComboBox<String> cbstate;
        private final JComboBox<String> chhospital;
        private final JComboBox<String> chspecilization;
        private final JButton btsearch;
        private final JButton btchoose;
        private final JPanel Pfield;

        private JTable tb;
        private int jspvis = 0;
        private int c = 0;
        private String condition = "";

        private float finalrate;
        private int finalappoint;
        private int finalpatient;
        private String userd;
        private String userp;

        Patient_SearchDoctor(String userp)
            {
                this.userp = userp;
                userd = ""; // always empty
                // Doctor whose appointment is to be booked

                setVisible(true);
                setLayout(new BorderLayout());
                setSize(new Dimension(700, 700));
                lbstate = new JLabel("State");
                lbcity = new JLabel("City");
                lbspec = new JLabel("Specilization");
                lbhos = new JLabel("Hospital");

                cbcity = new JComboBox<>();
                cbcity.addItem("Select City");
                cbstate = new JComboBox<String>();
                cbstate.addItem("Select State");
                fillcbstate();

                chhospital = new JComboBox<>();
                chspecilization = new JComboBox<String>();

                chhospital.addItem("Select Hospital");
                chspecilization.addItem("Select Specilization");

                btsearch = new JButton("Search");
                btsearch.setEnabled(false);
                btchoose = new JButton("Choose");
                btchoose.setEnabled(false);

                Pfield = new JPanel();
                JPanel p1 = new JPanel();
                Pfield.setLayout(new GridLayout(1, 11));

                Pfield.add(lbstate);
                Pfield.add(cbstate);
                Pfield.add(new JLabel());
                Pfield.add(lbcity);
                Pfield.add(cbcity);
                Pfield.add(new JLabel());
                Pfield.add(lbspec);
                Pfield.add(chspecilization);
                Pfield.add(new JLabel());
                chspecilization.setEnabled(false);
                Pfield.add(lbhos);
                Pfield.add(chhospital);
                chhospital.setEnabled(false);

                p1.setLayout(new FlowLayout());
                p1.add(btsearch);
                p1.add(btchoose);


                add(Pfield, BorderLayout.NORTH);

                add(p1, BorderLayout.SOUTH);

                cbstate.addItemListener(this);
                cbcity.addItemListener(this);

                chspecilization.addItemListener(this);
                chhospital.addItemListener(this);

                btsearch.addActionListener(this);
                btchoose.addActionListener(this);
                style();
            }

        private void fillcbstate()
            {
                try
                    {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists state");
                        stmt.execute("Use state");
                        // table exist already

                        ResultSet rs = stmt.executeQuery("select distinct city_state from cities");

                        while (rs.next())
                            {
                                cbstate.addItem("" + rs.getString("city_state"));
                            }
                        con.close();

                    } catch (ClassNotFoundException | SQLException ae)
                    {

                        ae.printStackTrace();
                    }
            }

        private void fillcbcity()
            {

                cbcity.removeAllItems();
                cbcity.addItem("Select City");


                String s = Objects.requireNonNull(cbstate.getSelectedItem()).toString();

                try
                    {


                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists state");
                        stmt.execute("Use state");
                        // table exist already

                        PreparedStatement pstmt = con.prepareStatement("select distinct city_name from cities where city_state=?");
                        pstmt.setString(1, s);
                        ResultSet rs = pstmt.executeQuery();

                        while (rs.next())
                            {
                                cbcity.addItem("" + rs.getString("city_name"));
                            }

                        con.close();

                    } catch (ClassNotFoundException | SQLException ae)
                    {

                        ae.printStackTrace();
                    }
            }

        private void style()
            {

                Font ftitle = new Font("comic sans", Font.ITALIC + Font.BOLD, 30);
                Border loweredbevel = BorderFactory.createLoweredBevelBorder();
                Border h = BorderFactory.createTitledBorder(loweredbevel, ":: Search a Doctor ::", TitledBorder.CENTER, TitledBorder.TOP, ftitle, Color.red);
                Border k = BorderFactory.createMatteBorder(0, 10, 0, 0, Color.red);
                Pfield.setBorder(BorderFactory.createCompoundBorder(h, k));

                Font flb = new Font("Arial", Font.BOLD, 25);
                lbstate.setFont(flb);
                lbcity.setFont(flb);
                lbspec.setFont(flb);
                lbhos.setFont(flb);

                Color clb = new Color(20, 110, 140);
                lbstate.setForeground(clb);
                lbcity.setForeground(clb);
                lbspec.setForeground(clb);
                lbhos.setForeground(clb);

                btsearch.setForeground(clb);
                btsearch.setFont(flb);

                btchoose.setForeground(clb);
                btchoose.setFont(flb);
            }

        private void tablestyle()
            {
                Color ctable = new Color(20, 110, 140);
                Font ftable_head = new Font(null, Font.BOLD, 20);
                JTableHeader header = tb.getTableHeader();
                header.setBackground(ctable);
                header.setFont(ftable_head);
                header.setForeground(Color.white);
                header.setPreferredSize(new Dimension(getSize().width, 100));
                ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

                tb.setForeground(ctable);
                Font ftable = new Font("comic sans", Font.BOLD, 15);
                tb.setFont(ftable);
                //JTable.CENTER_ALIGNMENT
                tb.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


                TableColumn column = null;
                for (int i = 0; i < 7; i++)
                    {
                        column = tb.getColumnModel().getColumn(i);
                        if (i == 1 || i == 2 || i == 3 || i == 5)
                            {
                                column.setPreferredWidth(125);
                            } else
                            {
                                column.setPreferredWidth(75);
                            }

                        for (int r = 0; r < c; r++)
                            {
                                tb.setRowHeight(r, 50);
                            }
                        tb.setEnabled(false);
                    }
            }

        @Override
        public void itemStateChanged(ItemEvent e)
            {

                if (e.getStateChange() == ItemEvent.SELECTED)
                    {
                        Object src = e.getSource();
                        condition = "";
                        if (src == cbstate)
                            {
                                btsearch.setEnabled(false);
                                cbcity.removeItemListener(this);
                                chspecilization.removeItemListener(this);
                                chhospital.removeItemListener(this);

                                chhospital.setEnabled(false);
                                chspecilization.setEnabled(false);
                                chhospital.removeAllItems();
                                chspecilization.removeAllItems();
                                chhospital.addItem("Select Hospital");
                                chspecilization.addItem("Select Specilization");

                                fillcbcity();

                                cbcity.addItemListener(this);
                                chspecilization.addItemListener(this);
                                chhospital.addItemListener(this);
                            } else if (src == cbcity)
                            {
                                btsearch.setEnabled(true);
                                chspecilization.removeItemListener(this);
                                chhospital.removeItemListener(this);
                                fillspec();
                                fillhosp();
                                chspecilization.addItemListener(this);
                                chhospital.addItemListener(this);
                            } else if (src == chspecilization || src == chhospital)
                            {
                                String a = Objects.requireNonNull(chspecilization.getSelectedItem()).toString();
                                String b = Objects.requireNonNull(chhospital.getSelectedItem()).toString();

                                if (!a.equalsIgnoreCase(":: All ::") && !b.equalsIgnoreCase(":: All ::"))
                                    condition = "and spec='" + a + "' and hname='" + b + "' ";

                                if (!a.equalsIgnoreCase(":: All ::") && b.equalsIgnoreCase(":: All ::"))
                                    condition = "and spec='" + a + "' ";
                                if (!b.equalsIgnoreCase(":: All ::") && a.equalsIgnoreCase(":: All ::"))
                                    condition = "and hname='" + b + "' ";
                            }

                        // it will be called when cbstate or cbcity is filled
                        if ((cbstate.getSelectedIndex() != 0 && cbcity.getSelectedIndex() != 0))
                            {
                                chspecilization.setEnabled(true);
                                chhospital.setEnabled(true);
                            }
                        if (jspvis != 0)
                            {
                                //tb.setRowHeight(1);
                                tb.setVisible(false);
                                btchoose.setEnabled(false);
                            }
                        validate();
                    }
            }

        private void fillspec()
            {
                chspecilization.removeAllItems();
                //  chspecilization.addItem("Select Specilization");
                chspecilization.addItem(":: All ::");

                try
                    {
                        Class.forName("com.mysql.jdbc.Driver"); // imported external jar mysql JConnector

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

                        PreparedStatement pstmt = con.prepareStatement("select distinct spec from doctortb where state=? and city=?");
                        pstmt.setString(1, Objects.requireNonNull(cbstate.getSelectedItem()).toString());
                        pstmt.setString(2, Objects.requireNonNull(cbcity.getSelectedItem()).toString());

                        ResultSet rs = pstmt.executeQuery();
                        while (rs.next())
                            {
                                chspecilization.addItem("" + rs.getString("spec"));
                            }
                        con.close();
                    } catch (SQLException | ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }
            }

        private void fillhosp()
            {
                chhospital.removeAllItems();
                //	chhospital.addItem("Select Hospital");
                chhospital.addItem(":: All ::");


                try
                    {
                        Class.forName("com.mysql.jdbc.Driver"); // imported external jar mysql JConnector

                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

                        // server and default mysql port address , username , password

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

                        PreparedStatement pstmt = con.prepareStatement("select distinct  hname from doctortb where state=? and city=?");
                        pstmt.setString(1, Objects.requireNonNull(cbstate.getSelectedItem()).toString());
                        pstmt.setString(2, Objects.requireNonNull(cbcity.getSelectedItem()).toString());

                        ResultSet rs = pstmt.executeQuery();
                        while (rs.next())
                            {
                                chhospital.addItem("" + rs.getString("hname"));
                            }
                        con.close();
                    } catch (SQLException | ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }
            }

        @Override
        public void actionPerformed(ActionEvent e)
            {

                Object src = e.getSource();
                if (src == btsearch)
                    {
                        filltable();
                        if (c != 0)
                            btchoose.setEnabled(true);
                    } else if (src == btchoose)
                    {
                        bookdoctor();
                    }
            }

        private void bookdoctor()
            {
                try
                    {
                        Class.forName("com.mysql.jdbc.Driver"); // imported external jar mysql JConnector

                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

                        // server and default mysql port address , username , password

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

                        PreparedStatement pstmt = con.prepareStatement("select username from doctortb where state=? and city=? " + condition + " ");
                        pstmt.setString(1, Objects.requireNonNull(cbstate.getSelectedItem()).toString());
                        pstmt.setString(2, Objects.requireNonNull(cbcity.getSelectedItem()).toString());
                        ResultSet rs = pstmt.executeQuery();

                        String[] userdoctor = new String[c];
                        int i = 0;
                        while (rs.next())
                            {
                                userdoctor[i] = rs.getString("username");
                                //System.out.println(userdoctor[i]);
                                i++;
                            }

                        Object[] doctorArray = new Object[userdoctor.length];
                        System.arraycopy(userdoctor, 0, doctorArray, 0, userdoctor.length);

                        Object doctor = JOptionPane.showInputDialog(null, "Choose your doctor", "Choose Doctor", JOptionPane.QUESTION_MESSAGE, null, doctorArray, 0);

                        if (doctor != null)
                            {
                                userd = doctor.toString();
                                int a = JOptionPane.showConfirmDialog(null, "Press Yes to View Doctor Info and Book Appointment", "Choose Your Requirement", JOptionPane.YES_NO_OPTION);
                                if (a == JOptionPane.YES_OPTION)
                                    new Patient_Viewdoctorinfo(userp, userd);
                            }

                        //System.out.println("Number of records: "+ c);

                    } catch (SQLException | ClassNotFoundException ae)
                    {
                        ae.printStackTrace();
                    }
            }


        private void filltable()
            {
                Object[] col = {"Doctor Name", "Patient Under", "Appointment Taken", "Rating", "Email", "Location", "Specilization", "Hospital Name", "Hospital Location"};
                try
                    {
                        Class.forName("com.mysql.jdbc.Driver"); // imported external jar mysql JConnector

                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

                        // server and default mysql port address , username , password

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

                        PreparedStatement pstmt = con.prepareStatement("select count(*) from doctortb where state=? and city=? " + condition + " ");
                        pstmt.setString(1, Objects.requireNonNull(cbstate.getSelectedItem()).toString());
                        pstmt.setString(2, Objects.requireNonNull(cbcity.getSelectedItem()).toString());
                        ResultSet rs = pstmt.executeQuery();
                        rs.next();

                        c = rs.getInt(1);
                        //System.out.println("Number of records: "+ c);

                        if (c != 0)
                            {
                                Object[][] row = new Object[c][9];


                                PreparedStatement pstmt1 = con.prepareStatement("select username,email,location,spec,hname,hloc from doctortb where state=? and city=? " + condition + " ");
                                pstmt1.setString(1, cbstate.getSelectedItem().toString());
                                pstmt1.setString(2, cbcity.getSelectedItem().toString());

                                ResultSet rs1 = pstmt1.executeQuery();

                                for (int i = 0; i < c && rs1.next(); i++)
                                    {
                                        row[i][0] = rs1.getString("username"); // these username are for doctors
                                        row[i][1] = filldoctorpatient(row[i][0].toString());
                                        row[i][2] = filldoctorfrequency(row[i][0].toString());
                                        if (filldoctorrating(row[i][0].toString()) == -1)
                                            row[i][3] = "no rating";
                                        else
                                            row[i][3] = filldoctorrating(row[i][0].toString());
                                        row[i][4] = rs1.getString("email");
                                        row[i][5] = rs1.getString("location");
                                        row[i][6] = rs1.getString("spec");
                                        row[i][7] = rs1.getString("hname");
                                        row[i][8] = rs1.getString("hloc");

                                    }
                                tb = new JTable(row, col);
                                JScrollPane jsp = new JScrollPane(tb);

                                jspvis = 1;
                                add(jsp, BorderLayout.CENTER);
                                tablestyle();
                                validate();
                            } else
                            {
                                //Joption no doctor found
                                JOptionPane.showMessageDialog(null, "No Doctor is found according to requirement", "Search Failed", JOptionPane.ERROR_MESSAGE);

                            }

                        con.close();

                    } catch (SQLException | ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }

                style();
            }


        // all the entries are taken from main table
        private float filldoctorrating(String doctor)
            {
                try
                    {


                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  DoctorFinalTb(username varchar(100),patient int,appointment int,rating float)");

                        PreparedStatement precount = con.prepareStatement("select count(*) from DoctorFinalTb where username=?");
                        precount.setString(1, doctor);
                        ResultSet rcount = precount.executeQuery();
                        rcount.next();
                        int c = rcount.getInt(1);
                        // but in that case when doctor have not setschedule but patient search for it
                        if (c == 0) // insert  / it will not be executed here it will be in doctor_setschedule only once
                            {
                                PreparedStatement pinsert = con.prepareStatement("insert into DoctorFinalTb values(?,?,?,?)");
                                pinsert.setString(1, doctor);
                                pinsert.setInt(2, 0);
                                pinsert.setInt(3, 0);
                                pinsert.setFloat(4, -1);
                                pinsert.execute();
                            } else if (c == 1) // that doctor exist in rating table set that rating as final
                            {
                                //System.out.println(c);
                                PreparedStatement pfinalrate = con.prepareStatement("select rating from DoctorFinalTb where username=?");
                                pfinalrate.setString(1, doctor);
                                ResultSet rfinal = pfinalrate.executeQuery();
                                rfinal.next();
                                finalrate = rfinal.getFloat("rating");
                            }
                    } catch (ClassNotFoundException | SQLException ce)
                    {

                        ce.printStackTrace();
                    }
                //if(finalrate == -1)
                //return (-1);
                return (finalrate);
            }

        private int filldoctorfrequency(String doctor)
            {// for all the doctors
                try
                    {


                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  DoctorFinalTb(username varchar(100),patient int,appointment int,rating float)");

                        PreparedStatement precount = con.prepareStatement("select count(*) from DoctorFinalTb where username=?");
                        precount.setString(1, doctor);
                        ResultSet rcount = precount.executeQuery();
                        rcount.next();
                        int c = rcount.getInt(1);
                        // but in that case when doctor have not setschedule but patient search for it
                        if (c == 0) // insert  / it will not be executed here it will be in doctor_setschedule only once
                            {
                                PreparedStatement pinsert = con.prepareStatement("insert into DoctorFinalTb values(?,?,?,?)");
                                pinsert.setString(1, doctor);
                                pinsert.setInt(2, 0);
                                pinsert.setInt(3, 0);
                                pinsert.setFloat(4, -1);
                                pinsert.execute();
                            } else if (c == 1) // that doctor exist in rating table set that rating as final
                            {
                                //System.out.println(c);
                                PreparedStatement pfinalappoint = con.prepareStatement("select appointment from DoctorFinalTb where username=?");
                                pfinalappoint.setString(1, doctor);
                                ResultSet rfinal = pfinalappoint.executeQuery();
                                rfinal.next();
                                finalappoint = rfinal.getInt("appointment");
                            }
                    } catch (ClassNotFoundException | SQLException ce)
                    {

                        ce.printStackTrace();
                    }
                return (finalappoint);
            }

        private int filldoctorpatient(String doctor)
            {// for all the doctors
                try
                    {


                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  DoctorFinalTb(username varchar(100),patient int,appointment int,rating float)");

                        PreparedStatement precount = con.prepareStatement("select count(*) from DoctorFinalTb where username=?");
                        precount.setString(1, doctor);
                        ResultSet rcount = precount.executeQuery();
                        rcount.next();
                        int c = rcount.getInt(1);
                        // but in that case when doctor have not setschedule but patient search for it
                        if (c == 0) // insert  / it will not be executed here it will be in doctor_setschedule only once
                            {
                                PreparedStatement pinsert = con.prepareStatement("insert into DoctorFinalTb values(?,?,?,?)");
                                pinsert.setString(1, doctor);
                                pinsert.setInt(2, 0);
                                pinsert.setInt(3, 0);
                                pinsert.setFloat(4, -1);
                                pinsert.execute();
                            } else if (c == 1) // that doctor exist in rating table set that rating as final
                            {
                                //System.out.println(c);
                                PreparedStatement pfinalpatient = con.prepareStatement("select patient from DoctorFinalTb where username=?");
                                pfinalpatient.setString(1, doctor);
                                ResultSet rfinal = pfinalpatient.executeQuery();
                                rfinal.next();
                                finalpatient = rfinal.getInt("patient");
                            }
                    } catch (ClassNotFoundException | SQLException ce)
                    {

                        ce.printStackTrace();
                    }
                return (finalpatient);
            }

    }



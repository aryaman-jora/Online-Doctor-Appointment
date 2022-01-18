import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Objects;

class Patient_Viewdoctorinfo extends JDialog implements ActionListener, ItemListener, FocusListener, KeyListener
    {
        private final JButton btcancel;
        private final JButton btbook;
        private final JTextField txuser;
        private final JTextArea taddress;
        private final JTextField txmobile;
        private final JTextField txemail;
        private final JTextField txlocation;
        private final JComboBox<String> cbcity;
        private final JComboBox<String> cbstate;
        private final JComboBox<String> chspecilization;
        private final JComboBox<String> chqualification;
        private final JTextField txHospitalName;
        private final JTextField txHospitalLocation;
        private final JTextField txclinicname;
        private final JTextField txcliniclocation;
        private final JRadioButton ckmale;
        private final JRadioButton ckfemale;
        private final JCheckBox chenglish;
        private final JCheckBox chhindi;
        private final JCheckBox chothers;
        private final JComboBox<String> chyy;
        private final JComboBox<String> chmm;
        private final JComboBox<String> chdd;
        private final JComboBox<String> cMs;
        private final JLabel lbuser;
        private final JLabel lbemail;
        private final JLabel lbmobile;
        private final JLabel lbgender;
        private final JLabel lbstatus;
        private final JLabel lbdob;
        private final JLabel lblocation;
        private final JLabel lbaddress;
        private final JLabel lbstate;
        private final JLabel lbcity;
        private final JLabel lbspeci;
        private final JLabel lbqualification;
        private final JLabel lblang;
        private final JLabel lbhosn;
        private final JLabel lbhosl;
        private final JLabel lbclin;
        private final JLabel lbclinl;
        private JPanel p;
        private String trial;
        private String userp;
        private String userd;

        Patient_Viewdoctorinfo(String userp, String userd)
            {
                this.userp = userp;
                this.userd = userd;

                trial = "";
                setVisible(true);
                setSize(new Dimension(700, 700));
                setPreferredSize(new Dimension(700, 700));
                setLayout(new BorderLayout());
                txuser = new JTextField("User Name");
                txuser.setEnabled(false);
                txemail = new JTextField("Email ID");

                cMs = new JComboBox<String>();
                cMs.addItem("Select status");
                cMs.addItem("Single");
                cMs.addItem("Married");

                txlocation = new JTextField("Location");
                cbcity = new JComboBox<>();
                cbcity.addItem("Select City");
                cbstate = new JComboBox<>();
                cbstate.addItem("Select State");
                fillcbstate();
                ButtonGroup gr = new ButtonGroup();
                ckmale = new JRadioButton("Male");
                ckfemale = new JRadioButton("Female");
                JRadioButton ckhidden = new JRadioButton("Hidden"); // dont add this

                gr.add(ckmale);
                gr.add(ckfemale);
                gr.add(ckhidden);

                chspecilization = new JComboBox<String>();
                chspecilization.addItem("Select Specilization");
                fillspec();
                chqualification = new JComboBox<String>();
                chqualification.addItem("Select Qualification");
                fillqual();

                chenglish = new JCheckBox("English");
                chhindi = new JCheckBox("Hindi");
                chothers = new JCheckBox("Others"); // dont add this

                txHospitalName = new JTextField("Hospital Name");
                txHospitalLocation = new JTextField("Hospital Location");
                txclinicname = new JTextField("Clinic Name");
                txcliniclocation = new JTextField("Clinic Location");

                chyy = new JComboBox<String>();
                chmm = new JComboBox<String>();
                chdd = new JComboBox<>();


                taddress = new JTextArea("Address");
                txmobile = new JTextField("Personal Number");

                btcancel = new JButton("Cancel");
                btcancel.addActionListener(this);

                btbook = new JButton("Book");
                btbook.addActionListener(this);
                cbstate.addItemListener(this);

                lbuser = new JLabel("USER NAME : ");
                lbemail = new JLabel("EMAIL : ");
                lbmobile = new JLabel("PERSONAL NO :");
                lbgender = new JLabel("GENDER : ");
                lbstatus = new JLabel("MATERIAL STATUS : ");
                lbdob = new JLabel("DOB : ");
                lblocation = new JLabel("LOCATION : ");
                lbaddress = new JLabel("ADDRESS : ");
                lbstate = new JLabel("STATE : ");
                lbcity = new JLabel("CITY : ");
                lbspeci = new JLabel("SPECILIZATION : ");
                lbqualification = new JLabel("QUALIFICATION : ");
                lblang = new JLabel("LANGUAGE : ");
                lbhosn = new JLabel("HOSPITAL NAME : ");
                lbhosl = new JLabel("HOSPITAL LOCATION: ");
                lbclin = new JLabel("CLINIC NAME : ");
                lbclinl = new JLabel("CLINIC LOCATION : ");

                chyy.addItemListener(this);
                chmm.addItemListener(this);
                txmobile.addKeyListener(this);
                print();

                style();
                enableall();
                fillform(); // overwriting


            }

        private void fillspec()
            {


                try
                    {


                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  DoctorspecTb(specilisation varchar(150))");

                        ResultSet rs = stmt.executeQuery("select distinct specilisation from DoctorspecTb");

                        while (rs.next())
                            {
                                chspecilization.addItem("" + rs.getString("specilisation"));
                            }

                        con.close();
                    } catch (ClassNotFoundException | SQLException e)
                    {

                        e.printStackTrace();
                    }


            }

        private void fillqual()
            {


                try
                    {


                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  DoctorqualTb(qualification varchar(150))");

                        ResultSet rs = stmt.executeQuery("select distinct qualification from DoctorqualTb");

                        while (rs.next())
                            {
                                chqualification.addItem("" + rs.getString("qualification"));
                            }

                        con.close();
                    } catch (ClassNotFoundException | SQLException e)
                    {

                        e.printStackTrace();
                    }
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

        private void fillform()
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

                        PreparedStatement pstmt = con.prepareStatement("select * from doctortb where username = ?");
                        pstmt.setString(1, userd);
                        ResultSet rs = pstmt.executeQuery();
                        rs.next();

                        txuser.setText(rs.getString("username"));
                        txemail.setText(rs.getString("email"));
                        txmobile.setText(rs.getString("phone"));
                        txlocation.setText(rs.getString("location"));
                        taddress.setText(rs.getString("address"));

                        cbstate.setSelectedItem(rs.getString("state"));
                        cbcity.setSelectedItem(rs.getString("city"));

                        chspecilization.setSelectedItem(rs.getString("spec"));
                        chqualification.setSelectedItem(rs.getString("qual"));
                        txHospitalName.setText(rs.getString("hname"));
                        txHospitalLocation.setText(rs.getString("hloc"));
                        txclinicname.setText(rs.getString("cname"));
                        txcliniclocation.setText(rs.getString("cloc"));


                        int g = rs.getInt("gender");
                        if (g == 1)
                            ckmale.setSelected(true);
                        else if (g == 0)
                            ckfemale.setSelected(true);

                        String l = rs.getString("lang");
                        if (l.charAt(0) == '1')
                            chenglish.setSelected(true);
                        else
                            chenglish.setSelected(false);
                        if (l.charAt(1) == '1')
                            chhindi.setSelected(true);
                        else
                            chhindi.setSelected(false);
                        if (l.charAt(2) == '1')
                            chothers.setSelected(true);
                        else
                            chothers.setSelected(false);

                        cMs.setSelectedIndex(rs.getInt("material"));

                        Date dob = rs.getDate("dob");
                        int dd = dob.getDate();
                        int mm = dob.getMonth();
                        int yy = dob.getYear();

                        chyy.setSelectedItem(Integer.toString(1900 + yy));
                        chmm.setSelectedItem(Integer.toString(1 + mm));
                        chdd.setSelectedItem(Integer.toString(dd));


                        con.close();
                    } catch (ClassNotFoundException | SQLException e)
                    {

                        e.printStackTrace();
                    }


            }

        private void print()
            {

                chyy.addItem("Year");
                for (int i = 1981; i <= 2000; i++)
                    chyy.addItem(i + "");

                chmm.addItem("Month");
                for (int i = 1; i <= 12; i++)
                    chmm.addItem(i + "");

                chdd.addItem("Date");


                JPanel pgender = new JPanel();
                pgender.setLayout(new GridLayout(1, 2));
                JPanel pdob = new JPanel();
                pdob.setLayout(new GridLayout(1, 3));

                pgender.add(ckmale);
                pgender.add(ckfemale);
                // dont add this hidden check box

                pdob.add(chyy);
                pdob.add(chmm);
                pdob.add(chdd);

                JPanel PLang = new JPanel();
                PLang.setLayout(new GridLayout(1, 3));
                PLang.add(chenglish);
                PLang.add(chhindi);
                PLang.add(chothers);


                p = new JPanel();
                p.setLayout(new GridLayout(17, 2));
                p.add(lbuser);
                p.add(txuser);
                p.add(lbemail);
                p.add(txemail);
                p.add(lbmobile);
                p.add(txmobile);
                p.add(lbgender);
                p.add(pgender);
                p.add(lbstatus);
                p.add(cMs);
                p.add(lbdob);
                p.add(pdob);
                p.add(lblocation);
                p.add(txlocation);
                p.add(lbaddress);
                p.add(taddress);
                p.add(lbstate);
                p.add(cbstate);
                p.add(lbcity);
                p.add(cbcity);
                p.add(lbspeci);
                p.add(chspecilization);
                p.add(lbqualification);
                p.add(chqualification);
                p.add(lblang);
                p.add(PLang);
                p.add(lbhosn);
                p.add(txHospitalName);
                p.add(lbhosl);
                p.add(txHospitalLocation);
                p.add(lbclin);
                p.add(txclinicname);
                p.add(lbclinl);
                p.add(txcliniclocation);

                add(p, BorderLayout.CENTER);
                p.setBorder(BorderFactory.createTitledBorder(null, "::DOCTOR INFO::"));
                JPanel pbutton = new JPanel();
                pbutton.add(btbook);
                pbutton.add(btcancel);
                add(pbutton, BorderLayout.SOUTH);
                // enableall(false);
            }

        @Override
        public void itemStateChanged(ItemEvent ie)
            {

                Object src = ie.getSource();
                if (src == cbstate)
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
                    } else if (chyy.getSelectedIndex() != 0 && chmm.getSelectedIndex() != 0)
                    {


                        int yy = Integer.parseInt(Objects.requireNonNull(chyy.getSelectedItem()).toString());
                        int mm = Integer.parseInt(Objects.requireNonNull(chmm.getSelectedItem()).toString());
                        chdd.removeAllItems();
                        chdd.addItem("Date");

                        int days = 0;

                        if (mm == 2)
                            {
                                if (yy % 4 == 0)

                                    days = 29;
                                else
                                    days = 28;
                            } else
                            {
                                if (mm == 4 || mm == 6 || mm == 9 || mm == 11)
                                    days = 30;
                                else
                                    days = 31;
                            }

                        for (int i = 1; i <= days; i++)
                            chdd.addItem(i + "");
                    }
            }

        @Override
        public void focusGained(FocusEvent e)
            {


            }

        @Override
        public void focusLost(FocusEvent e)
            {


            }


        @Override
        public void actionPerformed(ActionEvent ae)
            {

                Object src = ae.getSource();
                if (src == btcancel)
                    {
                        this.dispose();
                    } else if (src == btbook)
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
                                int c = rs.getInt(1);

                                if (c == 0)
                                    JOptionPane.showMessageDialog(null, "Dr " + userd + " have not set schedule", "Doctor is currently unavailable for some time", JOptionPane.INFORMATION_MESSAGE);
                                else if (c == 1) // as there is only one doctor in schedule
                                    {

                                        new Patient_BookingDoctor(userp, userd);
                                        this.dispose();
                                    }

                                con.close();
                            } catch (ClassNotFoundException | SQLException ce)
                            {

                                ce.printStackTrace();
                            }

                    }

            }

        private void enableall()
            {

                txemail.setEnabled(false);

                cMs.setEnabled(false);

                txlocation.setEnabled(false);
                cbcity.setEnabled(false);
                cbstate.setEnabled(false);

                ckmale.setEnabled(false);
                ckfemale.setEnabled(false);

                chspecilization.setEnabled(false);
                chqualification.setEnabled(false);

                chenglish.setEnabled(false);
                chhindi.setEnabled(false);
                chothers.setEnabled(false);

                txHospitalName.setEnabled(false);
                txHospitalLocation.setEnabled(false);
                txclinicname.setEnabled(false);
                txcliniclocation.setEnabled(false);

                chyy.setEnabled(false);
                chmm.setEnabled(false);
                chdd.setEnabled(false);

                taddress.setEnabled(false);
                txmobile.setEnabled(false);
            }

        private void style()

            {

                Color clb = new Color(20, 110, 140);
                Font ftitle = new Font("comic sans", Font.ITALIC + Font.BOLD, 40);
                Border loweredbevel = BorderFactory.createLoweredBevelBorder();
                Border h = BorderFactory.createTitledBorder(loweredbevel, ":: ABOUT DOCTOR " + userd + " ::", TitledBorder.CENTER, TitledBorder.TOP, ftitle, Color.red);
                Border k = BorderFactory.createMatteBorder(0, 10, 0, 0, Color.red);
                p.setBorder(BorderFactory.createCompoundBorder(h, k));

                Font flb = new Font("comic sans", Font.ITALIC + Font.BOLD, 20);
                lbuser.setFont(flb);
                lbemail.setFont(flb);
                lbmobile.setFont(flb);
                lbgender.setFont(flb);
                lbstatus.setFont(flb);
                lbdob.setFont(flb);
                lblocation.setFont(flb);
                lbaddress.setFont(flb);
                lbstate.setFont(flb);
                lbcity.setFont(flb);
                lbqualification.setFont(flb);
                lbspeci.setFont(flb);
                lblang.setFont(flb);
                lbhosn.setFont(flb);
                lbhosl.setFont(flb);
                lbclin.setFont(flb);
                lbclinl.setFont(flb);

                lbuser.setForeground(clb);
                lbemail.setForeground(clb);
                lbmobile.setForeground(clb);
                lbgender.setForeground(clb);
                lbstatus.setForeground(clb);
                lbdob.setForeground(clb);
                lblocation.setForeground(clb);
                lbaddress.setForeground(clb);
                lbstate.setForeground(clb);
                lbcity.setForeground(clb);
                lbqualification.setForeground(clb);
                lbspeci.setForeground(clb);
                lblang.setForeground(clb);
                lbhosn.setForeground(clb);
                lbhosl.setForeground(clb);
                lbclin.setForeground(clb);
                lbclinl.setForeground(clb);


            }

        @Override
        public void keyTyped(KeyEvent e)
            {
            }

        @Override
        public void keyPressed(KeyEvent e)
            {
            }

        @Override
        public void keyReleased(KeyEvent e)
            {


                int code = e.getKeyCode();
                //System.out.println(code);
                if (txmobile.getText().length() <= 10)
                    {
                        if (!(code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9)
                                && code != KeyEvent.VK_BACK_SPACE
                                && code != KeyEvent.VK_LEFT
                                && code != KeyEvent.VK_RIGHT
                                && code != KeyEvent.VK_DELETE)
                            {
                                //e.consume();
                                txmobile.setText(trial);
                                getToolkit().beep();

                            }
                        trial = txmobile.getText();
                    } else
                    {
                        txmobile.setText(trial);
                        getToolkit().beep();
                        trial = txmobile.getText();
                    }


            }


    }







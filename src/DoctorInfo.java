import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Objects;

class DoctorInfo extends JPanel implements ActionListener, ItemListener, FocusListener, KeyListener
    {
        private JPanel p;
        JPanel phead;
        private final JButton btedit;
        private final JButton btsubmit;
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
        JCheckBox chlanghidden;
        private final JComboBox<String> chyy;
        private final JComboBox<String> chmm;
        private final JComboBox<String> chdd;
        private final JComboBox<String> cMs;
        private String trial;
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
        private final String userd;

        DoctorInfo(String userd)
            {

                this.userd = userd; // taking username from previous login form
                trial = "";
                setVisible(true);
                setSize(new Dimension(700, 700));
                setPreferredSize(new Dimension(700, 700));
                setLayout(new BorderLayout());
                txuser = new JTextField("User Name");
                txuser.setEnabled(false);
                txemail = new JTextField("Email ID");

                cMs = new JComboBox<>();
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

                chspecilization = new JComboBox<>();
                chspecilization.addItem("Select Specilization");
                fillspec();
                chqualification = new JComboBox<>();
                chqualification.addItem("Select Qualification");
                fillqual();

                chenglish = new JCheckBox("English");
                chhindi = new JCheckBox("Hindi");
                chothers = new JCheckBox("Others"); // dont add this

                txHospitalName = new JTextField("Hospital Name");
                txHospitalLocation = new JTextField("Hospital Location");
                txclinicname = new JTextField("Clinic Name");
                txcliniclocation = new JTextField("Clinic Location");

                chyy = new JComboBox<>();
                chmm = new JComboBox<>();
                chdd = new JComboBox<>();


                taddress = new JTextArea("Address");
                txmobile = new JTextField("Personal Number");

                btedit = new JButton("Edit");
                btedit.addActionListener(this);

                btsubmit = new JButton("Submit");
                btsubmit.setEnabled(false);
                btsubmit.addActionListener(this);
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
                enableall(false);
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
                pbutton.add(btedit);
                pbutton.add(btsubmit);
                add(pbutton, BorderLayout.SOUTH);
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

                        // every time it come here we will remove previous entered item
                        // and then it will add automatically

                        //  for(int i=1;i<chdd.getItemCount();i++)
                        // chdd.remove(1);

                        chdd.removeAllItems();
                        chdd.addItem("Date");

                        int days;

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
                if (src == btedit)
                    {
                        btsubmit.setEnabled(true);
                        btedit.setEnabled(false);
                        enableall(true);
                    } else if (src == btsubmit)
                    {

                        updateform();

                    }

            }

        private void updateform()
            {
                if (txuser.getText().equals("")
                        || txemail.getText().equals("")
                        || txmobile.getText().equals("")
                        || !(ckmale.isSelected() || ckfemale.isSelected())
                        || txlocation.getText().equals("")
                        || taddress.getText().equals("")
                        || chspecilization.getSelectedIndex() == 0
                        || chqualification.getSelectedIndex() == 0
                        || txHospitalName.getText().equals("")
                        || txHospitalLocation.getText().equals("")
                        || cMs.getSelectedIndex() == 0
                        || chyy.getSelectedIndex() == 0
                        || chmm.getSelectedIndex() == 0
                        || chdd.getSelectedIndex() == 0
                        || cbstate.getSelectedIndex() == 0
                        || cbcity.getSelectedIndex() == 0
                        || !(chenglish.isSelected() || chhindi.isSelected() || chothers.isSelected())
                        || txmobile.getText().length() != 10
                )
                    // insert lang
                    // no need of clinicname and cliniclocation
                    {
                        JOptionPane.showMessageDialog(null, "Kindly fill all the required entries of Form", "Unfilled Form", JOptionPane.ERROR_MESSAGE);
                    } else
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

                                PreparedStatement pre = con.prepareStatement("update  doctortb set email=?,phone=?,gender=?,material=?,dob=?,location=?,address=?,city=?,state=?,spec=?,qual=?,lang=?,hname=?,hloc=?,cname=?,cloc=? where username=?");

                                pre.setString(1, txemail.getText());
                                pre.setString(2, txmobile.getText());

                                if (ckmale.isSelected())
                                    pre.setInt(3, 0);
                                else if (ckfemale.isSelected())
                                    pre.setInt(3, 1);
                                // 0 for female
                                // 1 for male

                                pre.setInt(4, cMs.getSelectedIndex());
                                // 1 for single
                                // 2 for married

                                int yy = Integer.parseInt(Objects.requireNonNull(chyy.getSelectedItem()).toString());
                                int mm = Integer.parseInt(Objects.requireNonNull(chmm.getSelectedItem()).toString());
                                int dd = Integer.parseInt(Objects.requireNonNull(chdd.getSelectedItem()).toString());
                                Date dob = new Date(yy - 1900, mm - 1, dd);
                                /*
                                 * year the year minus 1900; must be 0 to 8099. (Note that8099 is 9999 minus 1900.)
                                 * month 0 to 11
                                 * day 1 to 31
                                 */

                                pre.setDate(5, dob);

                                pre.setString(6, txlocation.getText());
                                pre.setString(7, taddress.getText());
                                pre.setString(8, Objects.requireNonNull(cbcity.getSelectedItem()).toString());
                                pre.setString(9, Objects.requireNonNull(cbstate.getSelectedItem()).toString());

                                pre.setString(10, Objects.requireNonNull(chspecilization.getSelectedItem()).toString());
                                pre.setString(11, Objects.requireNonNull(chqualification.getSelectedItem()).toString());


                                String lan = "";
                                if (chenglish.isSelected())
                                    lan += "1";
                                else
                                    lan += "0";
                                if (chhindi.isSelected())
                                    lan += "1";
                                else
                                    lan += "0";
                                if (chothers.isSelected())
                                    lan += "1";
                                else
                                    lan += "0";

                                pre.setString(12, lan);

                                pre.setString(13, txHospitalName.getText());
                                pre.setString(14, txHospitalLocation.getText());
                                pre.setString(15, txclinicname.getText());
                                pre.setString(16, txcliniclocation.getText());

                                pre.setString(17, userd);
                                pre.executeUpdate();
                                JOptionPane.showMessageDialog(null, "You have successfully changed your details", "Insertion successful", JOptionPane.INFORMATION_MESSAGE);

                                btsubmit.setEnabled(false);
                                btedit.setEnabled(true);
                                enableall(false);

                                con.close();
                            } catch (ClassNotFoundException | SQLException e)
                            {

                                e.printStackTrace();
                            }
                    }
            }

        private void enableall(boolean status)
            {

                txemail.setEnabled(status);

                cMs.setEnabled(status);

                txlocation.setEnabled(status);
                cbcity.setEnabled(status);
                cbstate.setEnabled(status);

                ckmale.setEnabled(status);
                ckfemale.setEnabled(status);

                chspecilization.setEnabled(status);
                chqualification.setEnabled(status);

                chenglish.setEnabled(status);
                chhindi.setEnabled(status);
                chothers.setEnabled(status);

                txHospitalName.setEnabled(status);
                txHospitalLocation.setEnabled(status);
                txclinicname.setEnabled(status);
                txcliniclocation.setEnabled(status);

                chyy.setEnabled(status);
                chmm.setEnabled(status);
                chdd.setEnabled(status);

                taddress.setEnabled(status);
                txmobile.setEnabled(status);
            }

        private void style()

            {

                Font ftitle = new Font("comic sans", Font.ITALIC + Font.BOLD, 40);
                Border loweredbevel = BorderFactory.createLoweredBevelBorder();
                Border h = BorderFactory.createTitledBorder(loweredbevel, ":: ABOUT ME ::", TitledBorder.CENTER, TitledBorder.TOP, ftitle, Color.red);
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

                Color clb = new Color(20, 110, 140);
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







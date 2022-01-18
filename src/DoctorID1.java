import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Objects;


class DoctorID1 extends JDialog implements ActionListener, ItemListener, FocusListener, KeyListener
    {

        private final JLabel lbuser;
        private final JLabel lbpass;
        private final JLabel lbgender;
        private final JLabel lbdob;
        private final JLabel lbaddress;
        private final JLabel lbmobile;
        private final JLabel lbconfirmpass;
        private final JLabel lbemail;
        private final JLabel lbMaterialstatus;
        private final JLabel lblocation;
        private final JLabel lbcity;
        private final JLabel lbstate;
        private final JLabel lbspecilization;
        private final JLabel lbqualification;
        private final JLabel lblanguage;
        private final JLabel lbHospitalName;
        private final JLabel lbHospitalLocation;
        private final JLabel lbclinicname;
        private final JLabel lbcliniclocation;
        private final JButton btregister;
        private final JButton btrefresh;
        private final JButton btclose;
        private final JTextField txuser;
        private final JTextField txmobile;
        private final JTextField txemail;
        private final JTextField txlocation;
        private final JComboBox<String> cbcity;
        private final JComboBox<String> cbstate;
        private final JTextField txHospitalName;
        private final JTextField txHospitalLocation;
        private final JTextField txclinicname;
        private final JTextField txcliniclocation;
        private final JComboBox<String> chspecilization;
        private final JComboBox<String> chqualification;
        private final JTextArea taddress;
        private final JPasswordField txpass;
        private final JPasswordField txconfirmpass;
        private final JRadioButton ckmale;
        private final JRadioButton ckfemale;
        private final JRadioButton ckhidden;
        private final JCheckBox chenglish;
        private final JCheckBox chhindi;
        private final JCheckBox chothers;
        private final JCheckBox chshowpass;
        private final JComboBox<String> chyy;
        private final JComboBox<String> chmm;
        private final JComboBox<String> chdd;
        private final JComboBox<String> cMs;
        CardLayout CLO;
        private String trialmobile, trialuser;
        private final String place;
        private final GridLayout g11;
        private final GridLayout g12;
        private final GridLayout g13;

        DoctorID1()
            {

                setVisible(true);
                setSize(new Dimension(1400, 1000));
                setPreferredSize(new Dimension(1400, 1000));
                setSize(1400, 1000);

                g11 = new GridLayout(1, 1);
                g12 = new GridLayout(1, 2);
                g13 = new GridLayout(1, 3);

                trialmobile = "";
                trialuser = "";
                place = "Example123@gmail.com";
                String need = "*";
                lbuser = new JLabel("User Name" + need);
                lbpass = new JLabel("New Password" + need);
                lbconfirmpass = new JLabel("Confirm New Password" + need);
                lbemail = new JLabel("Email ID" + need);
                lbMaterialstatus = new JLabel("Material Status" + need);
                lblocation = new JLabel("Location" + need);
                lbcity = new JLabel("City" + need);
                lbstate = new JLabel("State" + need);
                lbgender = new JLabel("Gender" + need);

                lbspecilization = new JLabel("Specilization" + need);
                lbqualification = new JLabel("Qualification" + need);
                lblanguage = new JLabel("Language" + need);

                lbHospitalName = new JLabel("Hospital Name" + need);
                lbHospitalLocation = new JLabel("Hospital Location" + need);
                lbclinicname = new JLabel("Clinic Name");
                lbcliniclocation = new JLabel("Clinic Location");

                ButtonGroup gr = new ButtonGroup();
                ckmale = new JRadioButton("Male");
                ckfemale = new JRadioButton("Female");
                ckhidden = new JRadioButton("Hidden"); // Don't add this
                // Used in refreshing of radio button

                gr.add(ckmale);
                gr.add(ckfemale);
                gr.add(ckhidden);

                chenglish = new JCheckBox("English");
                chenglish.setSelected(true);
                chhindi = new JCheckBox("Hindi");
                chhindi.setSelected(true);
                chothers = new JCheckBox("Others"); // Don't add this
                // Used in refreshing of radio button

                lbdob = new JLabel("DOB" + need);

                chyy = new JComboBox<String>();
                chmm = new JComboBox<String>();
                chdd = new JComboBox<String>();

                cMs = new JComboBox<String>();
                cMs.addItem("Select status");
                cMs.addItem("Single");
                cMs.addItem("Married");

                lbaddress = new JLabel("Address" + need);
                lbmobile = new JLabel("Personal Number" + need);

                btregister = new JButton("Register");
                btrefresh = new JButton("Refresh");
                btclose = new JButton("Close");

                txuser = new JTextField();
                txpass = new JPasswordField();
                // txpass.setEchoChar('*');
                // Used in case of Applet only
                txconfirmpass = new JPasswordField();
                // txconfirmpass.setEnabled(false);
                // txconfirmpass.setEchoChar('*');
                chshowpass = new JCheckBox("Show Password");
                txmobile = new JTextField();
                txemail = new JTextField();
                txemail.setText(place);
                txlocation = new JTextField();

                cbcity = new JComboBox<String>();
                cbcity.addItem("Select City");
                cbstate = new JComboBox<String>();
                cbstate.addItem("Select State");

                CreatingCombo.fillcbstate(cbstate);

                taddress = new JTextArea();
                chspecilization = new JComboBox<String>();
                chspecilization.addItem("Select Specilization");
                CreatingCombo.fillSpecilization(chspecilization);
                chqualification = new JComboBox<String>();
                chqualification.addItem("Select Qualification");
                CreatingCombo.fillQualification(chqualification);
                txHospitalName = new JTextField();
                txHospitalLocation = new JTextField();
                txclinicname = new JTextField();
                txcliniclocation = new JTextField();

                print();

                chyy.addItemListener(this);
                chmm.addItemListener(this);
                cbstate.addItemListener(this);
                chshowpass.addItemListener(this);
                btregister.addActionListener(this);
                btrefresh.addActionListener(this);
                btclose.addActionListener(this);

                txmobile.addKeyListener(this);
                txemail.addFocusListener(this);
                txpass.addFocusListener(this);
                txconfirmpass.addFocusListener(this);
                txuser.addFocusListener(this);
                txuser.addKeyListener(this);

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

                // according to month and year date must change

                JPanel pgender = new JPanel();
                pgender.setLayout(g12);
                JPanel pdob = new JPanel();
                pdob.setLayout(g13);
                JPanel pbutton1 = new JPanel();
                pbutton1.setLayout(g11);
                JPanel pbutton2 = new JPanel();
                pbutton2.setLayout(g12);

                pgender.add(ckmale);
                pgender.add(ckfemale);
                // Don't add this hidden check box

                JPanel PLang = new JPanel();
                PLang.setLayout(g13);
                PLang.add(chenglish);
                PLang.add(chhindi);
                PLang.add(chothers);

                pdob.add(chyy);
                pdob.add(chmm);
                pdob.add(chdd);

                JPanel ppass = new JPanel();
                ppass.setLayout(g12);
                ppass.add(txpass);
                ppass.add(chshowpass);

                pbutton1.add(btregister);
                pbutton2.add(btrefresh);
                pbutton2.add(btclose);

                JPanel pcity = new JPanel();
                pcity.setLayout(g12);
                pcity.add(lbcity);
                pcity.add(cbcity);

                JPanel pstate = new JPanel();
                pstate.setLayout(g12);
                pstate.add(lbstate);
                pstate.add(cbstate);

                JPanel phos = new JPanel();
                phos.setLayout(g12);
                phos.add(lbHospitalName);
                phos.add(txHospitalName);

                JPanel phosl = new JPanel();
                phosl.setLayout(g12);
                phosl.add(lbHospitalLocation);
                phosl.add(txHospitalLocation);

                JPanel pclinic = new JPanel();
                pclinic.setLayout(g12);
                pclinic.add(lbclinicname);
                pclinic.add(txclinicname);

                JPanel pclinicl = new JPanel();
                pclinicl.setLayout(g12);
                pclinicl.add(lbcliniclocation);
                pclinicl.add(txcliniclocation);

                GridLayout goverall = new GridLayout(17, 2);
                JPanel p = new JPanel();
                p.setLayout(goverall);
                p.add(lbuser);
                p.add(txuser);
                p.add(lbpass);
                p.add(ppass);
                p.add(lbconfirmpass);
                p.add(txconfirmpass);
                p.add(lbmobile);
                p.add(txmobile);
                p.add(lbemail);
                p.add(txemail);
                p.add(lbgender);
                p.add(pgender);
                p.add(lbMaterialstatus);
                p.add(cMs);
                p.add(lbdob);
                p.add(pdob);
                p.add(lbaddress);
                p.add(taddress);
                p.add(lblocation);
                p.add(txlocation);
                p.add(pstate);
                p.add(pcity);
                p.add(lbspecilization);
                p.add(chspecilization);
                p.add(lbqualification);
                p.add(chqualification);
                p.add(lblanguage);
                p.add(PLang);
                p.add(phos);
                p.add(phosl);
                p.add(pclinic);
                p.add(pclinicl);
                p.add(pbutton1);
                p.add(pbutton2);

                setLayout(new BorderLayout());
                add(p, BorderLayout.CENTER); // default of JApplet
                Font ftitle = new Font("comic sans", Font.ITALIC + Font.BOLD, 30);
                @SuppressWarnings("unused")
                Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
                @SuppressWarnings("unused")
                Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
                Border loweredbevel = BorderFactory.createLoweredBevelBorder();
                @SuppressWarnings("unused")
                Border raisedbevel = BorderFactory.createRaisedBevelBorder();
                Border h = BorderFactory.createTitledBorder(loweredbevel, ":: DOCTOR SIGNUP ::", TitledBorder.CENTER,
                        TitledBorder.TOP, ftitle, Color.red);
                Border k = BorderFactory.createMatteBorder(0, 10, 0, 0, Color.red);
                p.setBorder(BorderFactory.createCompoundBorder(h, k));
                p.setSize(new Dimension(1000, 800));
                p.setPreferredSize(new Dimension(1000, 800));

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
                        s = s.replace(" ", "_");
                        s = s.replace("&", "and");
                        try
                            {
                                Class.forName("com.mysql.jdbc.Driver");
                                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                                Statement stmt = con.createStatement();
                                stmt.executeUpdate("create database if not exists states");
                                stmt.execute("Use states");
                                // table exist already

                                ResultSet rs = stmt.executeQuery("select cities_names from " + s + "tb");
                                while (rs.next())
                                    {
                                        cbcity.addItem("" + rs.getString("cities_names"));
                                    }
                                con.close();

                            } catch (ClassNotFoundException ae)
                            {

                                ae.printStackTrace();
                            } catch (SQLException ae)
                            {

                                ae.printStackTrace();
                            }
                    } else
                    // yymmdd
                    if (chyy.getSelectedIndex() != 0 && chmm.getSelectedIndex() != 0)
                        {

                            int yy = Integer.parseInt(Objects.requireNonNull(chyy.getSelectedItem()).toString());
                            int mm = Integer.parseInt(Objects.requireNonNull(chmm.getSelectedItem()).toString());

                            // every time it come here we will remove previous entered item
                            // and then it will add automatically

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
                if (chshowpass.isSelected())
                    {
                        txpass.setEchoChar((char) 0);
                    } else
                    {
                        txpass.setEchoChar('*');
                    }

            }

        @Override
        public void actionPerformed(ActionEvent ae)
            {

                Object src = ae.getSource();
                // in refresh radio JButton will remain as it
                // making hidden radio JButton

                if (src == btregister)
                    {
                        String pass = new String(txpass.getPassword());
                        String confirmpass = new String(txconfirmpass.getPassword());
                        if (txuser.getText().equals("") || pass.equals("") || confirmpass.equals("")
                                || txemail.getText().equals("") || txmobile.getText().equals("")
                                || !(ckmale.isSelected() || ckfemale.isSelected()) || txlocation.getText().equals("")
                                || taddress.getText().equals("") || chspecilization.getSelectedIndex() == 0
                                || chqualification.getSelectedIndex() == 0 || txHospitalName.getText().equals("")
                                || txHospitalLocation.getText().equals("") || cMs.getSelectedIndex() == 0
                                || chyy.getSelectedIndex() == 0 || chmm.getSelectedIndex() == 0 || chdd.getSelectedIndex() == 0
                                || cbstate.getSelectedIndex() == 0 || cbcity.getSelectedIndex() == 0
                                || !(chenglish.isSelected() || chhindi.isSelected() || chothers.isSelected())
                                || txmobile.getText().length() != 10)
                            // No need of clinic name and clinic location
                            JOptionPane.showMessageDialog(null, "Kindly fill all the required entries of Form", "Unfilled Form",
                                    JOptionPane.ERROR_MESSAGE);
                        else
                            createDoctor();

                    } else if (src == btrefresh)
                    {
                        txuser.setText("");
                        txpass.setText("");

                        // Radio JButton
                        ckhidden.setSelected(true);
                        // if it is true then other will automatically will get off

                        chyy.setSelectedIndex(0);
                        chmm.setSelectedIndex(0);
                        chdd.setSelectedIndex(0);
                        taddress.setText("");
                        txconfirmpass.setText("");

                        txmobile.setText("");
                        cbcity.setSelectedIndex(0);
                        cbstate.removeItemListener(this);
                        cbstate.setSelectedIndex(0);
                        cbstate.addItemListener(this);
                        txlocation.setText("");
                        txemail.setText("");
                        cMs.setSelectedIndex(0);
                        chspecilization.setSelectedIndex(0);
                        chqualification.setSelectedIndex(0);
                        txHospitalName.setText("");
                        txHospitalLocation.setText("");
                        txclinicname.setText("");
                        txcliniclocation.setText("");
                        chenglish.setSelected(true);
                        chhindi.setSelected(true);
                        chothers.setSelected(false);

                    } else if (src == btclose)
                    {
                        this.dispose();
                    }
            }

        private void createDoctor()
            {

                try
                    {

                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  doctortb( username varchar(100),"
                                + "password varchar(100)," + "email varchar(100)," + "phone varchar(100)," + "gender int,"
                                + "material int," + "dob date ," + "location varchar(100)," + "address varchar(100),"
                                + "city varchar(100)," + "state varchar(100)," + "spec varchar(100)," + "qual varchar(100),"
                                + "lang varchar(100)," + "hname varchar(100)," + "hloc varchar(100),"
                                + "cname varchar(100)," + "cloc varchar(100)," + "primary key(username))");

                        PreparedStatement pres = con.prepareStatement("select count(*) from doctortb where username=?");
                        pres.setString(1, txuser.getText());

                        ResultSet rs = pres.executeQuery();
                        rs.next();
                        int c = rs.getInt(1);

                        if (c == 0)
                            {
                                PreparedStatement pre = con.prepareStatement(
                                        "insert into doctortb (username,password,email,phone,gender,material,dob,location,address,city,state,spec,qual,lang,hname,hloc,cname,cloc) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                                pre.setString(1, txuser.getText());
                                String pass = new String(txpass.getPassword());
                                pre.setString(2, pass);
                                pre.setString(3, txemail.getText());
                                pre.setString(4, txmobile.getText());

                                if (ckmale.isSelected())
                                    pre.setInt(5, 1);
                                else if (ckfemale.isSelected())
                                    pre.setInt(5, 0);
                                // 0 for female
                                // 1 for male

                                pre.setInt(6, cMs.getSelectedIndex());
                                // 1 for single
                                // 2 for married

                                int yy = Integer.parseInt(Objects.requireNonNull(chyy.getSelectedItem()).toString());
                                int mm = Integer.parseInt(Objects.requireNonNull(chmm.getSelectedItem()).toString());
                                int dd = Integer.parseInt(Objects.requireNonNull(chdd.getSelectedItem()).toString());

                                Date dob = new Date(yy - 1900, mm - 1, dd);
                                /*
                                 * year the year minus 1900; must be 0 to 8099. (Note that8099 is 9999 minus
                                 * 1900.) month 0 to 11 day 1 to 31
                                 */

                                pre.setDate(7, dob);

                                pre.setString(8, txlocation.getText());
                                pre.setString(9, taddress.getText());
                                pre.setString(10, Objects.requireNonNull(cbcity.getSelectedItem()).toString());
                                pre.setString(11, Objects.requireNonNull(cbstate.getSelectedItem()).toString());

                                pre.setString(12, Objects.requireNonNull(chspecilization.getSelectedItem()).toString());
                                pre.setString(13, Objects.requireNonNull(chqualification.getSelectedItem()).toString());

                                String lan = "";
                                lan += chenglish.isSelected() ? "1" : "0";
                                lan += chhindi.isSelected() ? "1" : "0";
                                lan += chothers.isSelected() ? "1" : "0";

                                pre.setString(14, lan);

                                pre.setString(15, txHospitalName.getText());
                                pre.setString(16, txHospitalLocation.getText());
                                pre.setString(17, txclinicname.getText());
                                pre.setString(18, txcliniclocation.getText());

                                pre.executeUpdate();
                                System.out.println("Insertion Sucessfully");
                                // this.dispose();

                            } else if (c == 1)
                            {
                                JOptionPane.showMessageDialog(null, txuser.getText() + " already exists.Try other username",
                                        "Incorrect Username", JOptionPane.ERROR_MESSAGE);
                                txuser.requestFocus();
                            }
                        con.close();
                    } catch (ClassNotFoundException e)
                    {

                        e.printStackTrace();
                    } catch (SQLException e)
                    {

                        e.printStackTrace();
                    }
            }


        @Override
        public void focusGained(FocusEvent e)
            {

                Object src = e.getSource();

                if (src == txemail)
                    {
                        if (txemail.getText().equalsIgnoreCase(place))
                            txemail.setText("");
                    }
            }

        @Override
        public void focusLost(FocusEvent e)
            {
                Object src = e.getSource();
                String pass = new String(txpass.getPassword());
                String confirmpass = new String(txconfirmpass.getPassword());
                if (src == txconfirmpass)
                    {
                        int b = pass.compareTo(confirmpass);
                        // b will be zero if they are same
                        if (b != 0)
                            {
                                JOptionPane.showMessageDialog(null, "Both Passwords Must Be Same", "Incorrect Password",
                                        JOptionPane.ERROR_MESSAGE);
                                chshowpass.setSelected(true);
                                txconfirmpass.requestFocus();
                            } else
                            chshowpass.setSelected(false);
                    } else if (src == txuser)
                    {
                        checkuser();

                    } else if (src == txpass)
                    {
                        if (!pass.equals(confirmpass))
                            txconfirmpass.requestFocus();
                    }
            }

        private void checkuser()
            {

                try
                    {

                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  doctortb( username varchar(100)," + "password varchar(100),"
                                + "email varchar(100)," + "phone varchar(100)," + "gender int," + "material int," + "dob date ,"
                                + "location varchar(100)," + "address varchar(100)," + "city varchar(100)," + "state varchar(100),"
                                + "spec varchar(100)," + "qual varchar(100)," + "lang varchar(100)," + "hname varchar(100),"
                                + "hloc varchar(100)," + "cname varchar(100)," + "cloc varchar(100)," + "primary key(username))");

                        PreparedStatement pres = con.prepareStatement("select count(*) from doctortb where username=?");
                        pres.setString(1, txuser.getText());

                        ResultSet rs = pres.executeQuery();
                        rs.next();
                        int c = rs.getInt(1);

                        if (c != 0)
                            {

                                JOptionPane.showMessageDialog(null, txuser.getText() + " already exists.Try other username",
                                        "Incorrect Username", JOptionPane.ERROR_MESSAGE);
                                txuser.requestFocus();
                            }
                        con.close();

                    } catch (ClassNotFoundException ae)
                    {

                        ae.printStackTrace();
                    } catch (SQLException ae)
                    {

                        ae.printStackTrace();
                    }
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

                Object src = e.getSource();
                // System.out.println(code);
                if (src == txmobile)
                    {
                        if (txmobile.getText().length() <= 10)
                            {
                                int code = e.getKeyCode();
                                if (!(code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9) && code != KeyEvent.VK_BACK_SPACE
                                        && code != KeyEvent.VK_LEFT && code != KeyEvent.VK_RIGHT && code != KeyEvent.VK_DELETE)
                                    {
                                        // e.consume();
                                        txmobile.setText(trialmobile);
                                        getToolkit().beep();

                                    }
                                trialmobile = txmobile.getText();
                            } else
                            {
                                txmobile.setText(trialmobile);
                                getToolkit().beep();
                                trialmobile = txmobile.getText();
                            }
                    } else if (src == txuser)
                    {
                        int code = e.getKeyCode();
                        if (!(code >= KeyEvent.VK_A && code <= KeyEvent.VK_Z) && code != KeyEvent.VK_BACK_SPACE
                                && code != KeyEvent.VK_LEFT && code != KeyEvent.VK_RIGHT && code != KeyEvent.VK_DELETE)
                            {
                                // e.consume();
                                txuser.setText(trialuser);
                                getToolkit().beep();

                            }
                        trialuser = txuser.getText();
                    }
            }
    }

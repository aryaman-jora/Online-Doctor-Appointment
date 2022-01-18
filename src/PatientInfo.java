import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Objects;

class PatientInfo extends JPanel implements ActionListener, ItemListener, FocusListener, KeyListener
    {

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
        private final JLabel lbdisease;
        private final JLabel lbpast;
        private final JTextField txuser;
        private final JTextField txmobile;
        private final JTextField txpassrecords;
        private final JTextArea taddress;
        private final JTextField txemail;
        private final JTextField txlocation;
        private final JComboBox<String> cbstate;
        private final JComboBox<String> cbcity;
        private JPanel P;
        private final JButton btedit;
        private final JButton btsubmit;
        private final JRadioButton ckmale;
        private final JRadioButton ckfemale;
        private final JComboBox<String> chyy;
        private final JComboBox<String> chmm;
        private final JComboBox<String> chdd;
        private final JComboBox<String> cMs;
        private JPanel Pclass;
        private final JButton bnext;
        private final JButton bprevious;
        private JCheckBox[] ch;
        private CardLayout CLO;
        private String trial;
        private String userp;

        PatientInfo(String userp)
            {

                this.userp = userp; // taking username from previous login form

                String need = "*";
                trial = "";
                setVisible(true);
                setSize(800, 800);
                txuser = new JTextField("Patient Name");
                txuser.setEditable(false);

                txemail = new JTextField("Email ID");
                txlocation = new JTextField("Location");
                taddress = new JTextArea("Address");
                txmobile = new JTextField("Personal Number");
                txpassrecords = new JTextField("Past Records");

                ButtonGroup gr = new ButtonGroup();
                ckmale = new JRadioButton("Male");
                ckfemale = new JRadioButton("Female");
                JRadioButton ckhidden = new JRadioButton("Hidden"); // dont add this

                gr.add(ckmale);
                gr.add(ckfemale);
                gr.add(ckhidden);
                chyy = new JComboBox<>();
                chmm = new JComboBox<String>();
                chdd = new JComboBox<String>();

                cMs = new JComboBox<>();
                cMs.addItem("Select status");
                cMs.addItem("Single");
                cMs.addItem("Married");

                cbcity = new JComboBox<>();
                cbcity.addItem("Select City");
                cbstate = new JComboBox<String>();
                cbstate.addItem("Select State");
                fillcbstate();

                btedit = new JButton("Edit");
                btedit.addActionListener(this);

                btsubmit = new JButton("Submit");
                btsubmit.addActionListener(this);
                btsubmit.setEnabled(false);
                bnext = new JButton("Next");
                bprevious = new JButton("Previous");

                chyy.addItemListener(this);
                chmm.addItemListener(this);
                cbstate.addItemListener(this);
                bnext.addActionListener(this);
                bprevious.addActionListener(this);

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
                lbdisease = new JLabel("RELATED DISEASE : ");
                lbpast = new JLabel("PAST RECORDS : ");

                txmobile.addKeyListener(this);
                print();
                enableall(false);
                fillform(); // overwriting
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

        private void fillform()
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

                        PreparedStatement pstmt = con.prepareStatement("select * from patienttb where username = ?");
                        pstmt.setString(1, userp);
                        ResultSet rs = pstmt.executeQuery();
                        rs.next();

                        txuser.setText(rs.getString("username"));
                        txemail.setText(rs.getString("email"));
                        txmobile.setText(rs.getString("phone"));
                        txlocation.setText(rs.getString("location"));
                        taddress.setText(rs.getString("address"));
                        txpassrecords.setText(rs.getString("past"));

                        int g = rs.getInt("gender");
                        if (g == 1)
                            ckmale.setSelected(true);
                        else if (g == 0)
                            ckfemale.setSelected(true);

                        cMs.setSelectedIndex(rs.getInt("material"));
                        cbstate.setSelectedItem(rs.getString("state"));
                        cbcity.setSelectedItem(rs.getString("city"));

                        Date dob = rs.getDate("dob");
                        int dd = dob.getDate();
                        int mm = dob.getMonth();
                        int yy = dob.getYear();

                        chyy.setSelectedItem(Integer.toString(1900 + yy));
                        chmm.setSelectedItem(Integer.toString(1 + mm));
                        chdd.setSelectedItem(Integer.toString(dd));
                        String d = rs.getString("disease");
                        for (int i = 0; i < ch.length; i++)
                            {
                                if (d.charAt(i) == '1')
                                    ch[i].setSelected(true);

                            }


                        con.close();
                    } catch (ClassNotFoundException | SQLException e)
                    {

                        e.printStackTrace();
                    }


            }

        private void print()
            {

                P = new JPanel();
                JPanel phead = new JPanel();

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

                JPanel[] pdis = new JPanel[3];
                ch = new JCheckBox[9];

                for (int i = 0; i < pdis.length; i++)
                    {
                        pdis[i] = new JPanel();
                        pdis[i].setLayout(new GridLayout(1, 3));
                    }
                String[] disname = {"Diabetes", "High Blood Pressure", "Respiratory Diseases", "Heart Diseases", "Digestive Diseases", "High Cholesterol ", "Stroke", "Cancer", "Alzheimer�s disease"};

                for (int i = 0; i < ch.length; i++)
                    ch[i] = new JCheckBox(disname[i]);

                pdis[0].add(ch[0]);
                pdis[0].add(ch[1]);
                pdis[0].add(ch[2]);

                pdis[1].add(ch[3]);
                pdis[1].add(ch[4]);
                pdis[1].add(ch[5]);

                pdis[2].add(ch[6]);
                pdis[2].add(ch[7]);
                pdis[2].add(ch[8]);


                JPanel pdisease = new JPanel();
                pdisease.setLayout(new BorderLayout());
                JPanel pbut = new JPanel();
                pbut.add(bnext);
                pbut.add(bprevious);
                pdisease.add(pbut, BorderLayout.NORTH);

                Pclass = new JPanel();
                CLO = new CardLayout();
                Pclass.setLayout(CLO);
                Pclass.add("Type1", pdis[0]); // adding panels
                Pclass.add("Type2", pdis[1]);
                Pclass.add("Type3", pdis[2]);
                pdisease.add(Pclass, BorderLayout.CENTER);


                pdob.add(chyy);
                pdob.add(chmm);
                pdob.add(chdd);
                P.setLayout(new GridLayout(12, 2));
                P.add(lbuser);
                P.add(txuser);
                P.add(lbemail);
                P.add(txemail);
                P.add(lbmobile);
                P.add(txmobile);
                P.add(lbgender);
                P.add(pgender);
                P.add(lbstatus);
                P.add(cMs);
                P.add(lbdob);
                P.add(pdob);
                P.add(lblocation);
                P.add(txlocation);
                P.add(lbaddress);
                P.add(taddress);
                P.add(lbstate);
                P.add(cbstate);
                P.add(lbcity);
                P.add(cbcity);
                P.add(lbdisease);
                P.add(pdisease);
                P.add(lbpast);
                P.add(txpassrecords);

                setLayout(new BorderLayout());
                add(P, BorderLayout.CENTER);
                JPanel pbutton = new JPanel();
                pbutton.add(btedit);
                pbutton.add(btsubmit);
                add(pbutton, BorderLayout.SOUTH);
                P.setBorder(BorderFactory.createTitledBorder(null, ":: PATIENT INFO ::"));
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

                    } else if (src == bnext)
                    {
                        CLO.next(Pclass);
                    } else if (src == bprevious)
                    {
                        CLO.previous(Pclass);
                    }

            }

        private void enableall(boolean status)
            {
                txemail.setEnabled(status);
                txlocation.setEnabled(status);
                cMs.setEnabled(status);
                chyy.setEnabled(status);
                chmm.setEnabled(status);
                chdd.setEnabled(status);
                ckmale.setEnabled(status);
                ckfemale.setEnabled(status);
                cbcity.setEnabled(status);
                cbstate.setEnabled(status);
                taddress.setEnabled(status);
                txmobile.setEnabled(status);

                for (JCheckBox jCheckBox : ch)
                    jCheckBox.setEnabled(status);
                bnext.setEnabled(status);
                bprevious.setEnabled(status);
                txpassrecords.setEnabled(status);

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

        @Override
        public void focusGained(FocusEvent e)
            {


            }

        @Override
        public void focusLost(FocusEvent e)
            {


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

        private void updateform()
            {


                if (txuser.getText().equals("")
                        || txemail.getText().equals("")
                        || txmobile.getText().equals("")
                        || !(ckmale.isSelected() || ckfemale.isSelected())
                        || txlocation.getText().equals("")
                        || taddress.getText().equals("")
                        || cMs.getSelectedIndex() == 0
                        || chyy.getSelectedIndex() == 0
                        || chmm.getSelectedIndex() == 0
                        || chdd.getSelectedIndex() == 0
                        || cbstate.getSelectedIndex() == 0
                        || cbcity.getSelectedIndex() == 0

                )
                    // no related disease and past records
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

                                PreparedStatement pre = con.prepareStatement("update patienttb set email=?,phone=?,gender=?,material=?,dob=?,location=?,address=?,city=?,state=?,disease=?,past=? where username=?");

                                pre.setString(1, txemail.getText());
                                pre.setString(2, txmobile.getText());

                                if (ckmale.isSelected())
                                    pre.setInt(3, 1);
                                else if (ckfemale.isSelected())
                                    pre.setInt(3, 0);
                                // 1 for male
                                // 0 for female

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


                                StringBuilder res = new StringBuilder();
                                for (JCheckBox jCheckBox : ch)
                                    res.append(jCheckBox.isSelected() ? "1" : "0");
                                pre.setString(10, res.toString());
                                pre.setString(11, txpassrecords.getText());

                                pre.setString(12, userp);

                                pre.executeUpdate();
                                JOptionPane.showMessageDialog(null, "You have successfully changed your details", "Insertion successful", JOptionPane.INFORMATION_MESSAGE);

                                btsubmit.setEnabled(false);
                                btedit.setEnabled(true);
                                enableall(false);

                                btsubmit.setEnabled(false);
                                btedit.setEnabled(true);
                                enableall(false);
                                //this.dispose();
                                // using jdialog box

                                con.close();

                            } catch (ClassNotFoundException | SQLException e)
                            {

                                e.printStackTrace();
                            }
                    }

            }

        private void style()

            {

                Font ftitle = new Font("comic sans", Font.ITALIC + Font.BOLD, 40);
                Border loweredbevel = BorderFactory.createLoweredBevelBorder();
                Border h = BorderFactory.createTitledBorder(loweredbevel, ":: ABOUT ME ::", TitledBorder.CENTER, TitledBorder.TOP, ftitle, Color.red);
                Border k = BorderFactory.createMatteBorder(0, 10, 0, 0, Color.red);
                P.setBorder(BorderFactory.createCompoundBorder(h, k));

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
                lbdisease.setFont(flb);
                lbpast.setFont(flb);

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
                lbdisease.setForeground(clb);
                lbpast.setForeground(clb);


            }
    }



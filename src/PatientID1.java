import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;


class PatientID1 extends JDialog
        implements ActionListener, ItemListener, FocusListener, KeyListener, ChangeListener, DocumentListener
    {

        private final JLabel lbuser;
        private final JLabel lbpass;
        private final JLabel lbgender;
        private final JLabel lbdob;
        private final JLabel lbaddress;
        private final JLabel lbmobile;
        private final JLabel lbconfirmpass;
        private final JLabel lbpastrecord;
        private final JLabel lbdisease;
        private final JLabel lbemail;
        private final JLabel lbMaterialstatus;
        private final JLabel lblocation;
        private final JLabel lbcity;
        private final JLabel lbstate;
        private final JButton btregister;
        private final JButton btrefresh;
        private final JButton btclose;
        private final JTextField txuser;
        private final JTextField txmobile;
        private final JTextField txpassrecords;
        private final JTextField txemail;
        private final JTextField txlocation;
        private final JPasswordField txpass;
        private final JPasswordField txconfirmpass;
        private final JComboBox<String> cbcity;
        private final JComboBox<String> cbstate;
        private final JTextArea taddress;
        private final JPanel P;
        private JCheckBox[] ch;
        private final JCheckBox chshowpass;
        private JPanel Pclass;
        private final JButton bnext;
        private final JButton bprevious;
        private final JRadioButton ckmale;
        private final JRadioButton ckfemale;
        private final JRadioButton ckhidden;
        private final JComboBox<String> chyy;
        private final JComboBox<String> chmm;
        private final JComboBox<String> chdd;
        private final JComboBox<String> cMs;
        private CardLayout CLO;
        private String trialmobile, trialuser;
        private final String place;
        private final GridLayout g11;
        private final GridLayout g12;
        private final GridLayout g13;
        private final String gmail_user;
        private final String gmail_password;

        PatientID1()
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
                gmail_user = "onlinedoct24@gmail.com";
                gmail_password = "0nline_d@ct24";

                lbuser = new JLabel("User Name" + need);
                lbpass = new JLabel("New Password" + need);
                lbconfirmpass = new JLabel("Confirm New Password" + need);
                lbemail = new JLabel("Email ID" + need);
                lbMaterialstatus = new JLabel("Material Status" + need);
                lblocation = new JLabel("Location" + need);
                lbcity = new JLabel("City" + need);
                lbstate = new JLabel("State" + need);
                lbdisease = new JLabel("Related Disease");
                lbgender = new JLabel("Gender" + need);

                ButtonGroup gr = new ButtonGroup();
                ckmale = new JRadioButton("Male");
                ckfemale = new JRadioButton("Female");
                ckhidden = new JRadioButton("Hidden"); // Don't add this
                // Used in refreshing of radio button

                gr.add(ckmale);
                gr.add(ckfemale);
                gr.add(ckhidden);

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

                lbpastrecord = new JLabel("Past Records");

                btregister = new JButton("Register");
                btrefresh = new JButton("Refresh");
                btclose = new JButton("Close");
                bnext = new JButton("Next");
                bprevious = new JButton("Previous");

                txuser = new JTextField();
                txpass = new JPasswordField(200);
                // txpass.setEchoChar('*');
                // Used in case of Applet only
                chshowpass = new JCheckBox("Show Password");
                txconfirmpass = new JPasswordField();
                // txconfirmpass.setEnabled(false);
                // no need of txconfirmpass.setEchoChar('*');
                txmobile = new JTextField();
                txpassrecords = new JTextField();
                txemail = new JTextField();
                txemail.setText(place);
                txlocation = new JTextField();
                cbcity = new JComboBox<String>();
                cbcity.addItem("Select City");
                cbstate = new JComboBox<String>();
                cbstate.addItem("Select State");

                CreatingCombo.fillcbstate(cbstate);

                taddress = new JTextArea();
                P = new JPanel();

                print();

                chyy.addItemListener(this);
                chmm.addItemListener(this);
                cbstate.addItemListener(this);
                chshowpass.addItemListener(this);

                btregister.addActionListener(this);
                btrefresh.addActionListener(this);
                btclose.addActionListener(this);
                bnext.addActionListener(this);
                bprevious.addActionListener(this);

                txpass.addFocusListener(this);
                txconfirmpass.addFocusListener(this);
                txuser.addFocusListener(this);
                txemail.addFocusListener(this);
                txmobile.addKeyListener(this);
                txuser.addKeyListener(this);
                // txmobile.getDocument().addDocumentListener(this);
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

                pdob.add(chyy);
                pdob.add(chmm);
                pdob.add(chdd);

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

                JPanel ppass = new JPanel();
                ppass.setLayout(g12);
                ppass.add(txpass);
                ppass.add(chshowpass);

                JPanel[] pdis = new JPanel[3];
                ch = new JCheckBox[9];

                for (int i = 0; i < pdis.length; i++)
                    {
                        pdis[i] = new JPanel();
                        pdis[i].setLayout(g13);
                    }
                String[] disname = {"Diabetes", "High Blood Pressure", "Respiratory Diseases", "Heart Diseases",
                        "Digestive Diseases", "High Cholesterol ", "Stroke", "Cancer", "Alzheimer�s disease"};

                // Disease in Database

                for (int i = 0; i < ch.length; i++)
                    ch[i] = new JCheckBox(disname[i]);

                // Create Loop
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

                JPanel pbutton = new JPanel();
                pbutton.add(bnext);
                pbutton.add(bprevious);
                pdisease.add(pbutton, BorderLayout.NORTH);

                Pclass = new JPanel();
                CLO = new CardLayout();
                Pclass.setLayout(CLO);
                for (int i = 0; i < pdis.length; i++)
                    Pclass.add("Type " + (i + 1), pdis[i]);

                pdisease.add(Pclass, BorderLayout.CENTER);

                GridLayout goverall = new GridLayout(14, 2);
                P.setLayout(goverall);

                P.add(lbuser);
                P.add(txuser);
                P.add(lbpass);
                P.add(ppass);
                P.add(lbconfirmpass);
                P.add(txconfirmpass);
                P.add(lbmobile);
                P.add(txmobile);
                P.add(lbemail);
                P.add(txemail);
                P.add(lbgender);
                P.add(pgender);
                P.add(lbMaterialstatus);
                P.add(cMs);
                P.add(lbdob);
                P.add(pdob);
                P.add(lbaddress);
                P.add(taddress);
                P.add(lblocation);
                P.add(txlocation);
                P.add(pstate);
                P.add(pcity);
                P.add(lbdisease);
                P.add(pdisease);
                P.add(lbpastrecord);
                P.add(txpassrecords);
                P.add(pbutton1);
                P.add(pbutton2);

                setLayout(new BorderLayout());
                add(P, BorderLayout.CENTER); // default is border layout
                Font ftitle = new Font("comic sans", Font.ITALIC + Font.BOLD, 30);
                @SuppressWarnings("unused")
                Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
                @SuppressWarnings("unused")
                Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
                @SuppressWarnings("unused")
                Border raisedbevel = BorderFactory.createRaisedBevelBorder();
                Border loweredbevel = BorderFactory.createLoweredBevelBorder();
                Border h = BorderFactory.createTitledBorder(loweredbevel, ":: PATIENT SIGNUP ::", TitledBorder.CENTER,
                        TitledBorder.TOP, ftitle, Color.red);
                Border k = BorderFactory.createMatteBorder(0, 10, 0, 0, Color.red);
                P.setBorder(BorderFactory.createCompoundBorder(h, k));
                P.setSize(new Dimension(800, 800));
                P.setPreferredSize(new Dimension(800, 800));
                P.setSize(800, 800);
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
                    }

                // yymmdd
                else if (chyy.getSelectedIndex() != 0 && chmm.getSelectedIndex() != 0)
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

                // Show Password
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

                        if (txuser.getText().equals("") || pass.equals("") || confirmpass.equals("") || txemail.getText().equals("")
                                || txmobile.getText().equals("") || !(ckmale.isSelected() || ckfemale.isSelected())
                                || txlocation.getText().equals("") || taddress.getText().equals("") || cMs.getSelectedIndex() == 0
                                || chyy.getSelectedIndex() == 0 || chmm.getSelectedIndex() == 0 || chdd.getSelectedIndex() == 0
                                || cbstate.getSelectedIndex() == 0 || cbcity.getSelectedIndex() == 0
                                || txmobile.getText().length() != 10)
                            // no related disease and past records
                            JOptionPane.showMessageDialog(null, "Kindly fill all the required entries of Form", "Unfilled Form",
                                    JOptionPane.ERROR_MESSAGE);
                        else
                            createPatient();
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

                        for (int i = 0; i < ch.length; i++)
                            ch[i].setSelected(false);

                        chshowpass.setSelected(false);
                        txmobile.setText("");
                        txpassrecords.setText("");
                        cbcity.setSelectedIndex(0);
                        cbstate.removeItemListener(this);
                        cbstate.setSelectedIndex(0);
                        cbstate.addItemListener(this);
                        txlocation.setText("");
                        txemail.setText("Example123@gmail.com");
                        cMs.setSelectedIndex(0);
                    } else if (src == bnext)
                    {
                        CLO.next(Pclass);
                    } else if (src == bprevious)
                    {
                        CLO.previous(Pclass);
                    } else if (src == btclose)
                    {
                        this.dispose();
                    }
            }

        private void createPatient()
            {

                try
                    {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  patienttb( username varchar(100),"
                                + "password varchar(100)," + "email varchar(100)," + "phone varchar(100)," + "gender int,"
                                + "material int," + "dob date ," + "location varchar(100)," + "address varchar(100),"
                                + "city varchar(100)," + "state varchar(100)," + "disease varchar(100)," + "past varchar(100),"
                                + "primary key(username))");

                        PreparedStatement pres = con.prepareStatement("select count(*) from patienttb where username=?");
                        pres.setString(1, txuser.getText());

                        ResultSet rs = pres.executeQuery();
                        rs.next();
                        int c = rs.getInt(1);

                        if (c == 0)
                            {
                                PreparedStatement pre = con.prepareStatement(
                                        "insert into patienttb (username,password,email,phone,gender,material,dob,location,address,city,state,disease,past) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");

                                pre.setString(1, txuser.getText());
                                String pass = new String(txpass.getPassword());
                                pre.setString(2, pass);
                                pre.setString(3, txemail.getText());
                                pre.setString(4, txmobile.getText());

                                if (ckmale.isSelected())
                                    pre.setInt(5, 1);
                                else if (ckfemale.isSelected())
                                    pre.setInt(5, 0);
                                // 1 for male
                                // 0 for female

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

                                StringBuilder res = new StringBuilder();
                                for (int i = 0; i < ch.length; i++)
                                    res.append(ch[i].isSelected() ? "1" : "0");
                                pre.setString(12, res.toString());
                                pre.setString(13, txpassrecords.getText());

                                pre.executeUpdate();
                                System.out.println("Insertion Sucessfully");
                                // this.dispose();

                                // Mail to patient about new account begin created

                                String TO = txemail.getText();
                                String gmail_subject = "Welcome to Online Doctor Appointment App";
                                // Add attachment in mail
                                // Giving some information about best doctors
                                String gmail_message = "<h1>Hello! Welcome!!! </h1>"
                                        + "Ohhh my goodness, we're so thrilled you decided to join our family. Hats off making an execllent decision."
                                        + "You are officially in the loop to hear all about our most experienced doctors all around globe."
                                        + "<pre style='color:green'>It is health that is real wealth and not pieces of gold and silver.</pre>";

                                gmail_message = "<!--Download - https://github.com/lime7/responsive-html-template.git-->\r\n" +
                                        "<html lang=\"en\">\r\n" +
                                        "<head>\r\n" +
                                        "	<meta charset=\"UTF-8\">\r\n" +
                                        "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n" +
                                        "	<title>One Letter</title>\r\n" +
                                        "\r\n" +
                                        "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\r\n" +
                                        "\r\n" +
                                        "	<style>\r\n" +
                                        "		.ReadMsgBody {width: 100%; background-color: #ffffff;}\r\n" +
                                        "		.ExternalClass {width: 100%; background-color: #ffffff;}\r\n" +
                                        "\r\n" +
                                        "				/* Windows Phone Viewport Fix */\r\n" +
                                        "		@-ms-viewport { \r\n" +
                                        "		    width: device-width; \r\n" +
                                        "		}\r\n" +
                                        "	</style>\r\n" +
                                        "\r\n" +
                                        "	<!--[if (gte mso 9)|(IE)]>\r\n" +
                                        "	    <style type=\"text/css\">\r\n" +
                                        "	        table {border-collapse: collapse;}\r\n" +
                                        "	        .mso {display:block !important;} \r\n" +
                                        "	    </style>\r\n" +
                                        "	<![endif]-->\r\n" +
                                        "\r\n" +
                                        "</head>\r\n" +
                                        "<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" style=\"background: #e7e7e7; width: 100%; height: 100%; margin: 0; padding: 0;\">\r\n" +
                                        "	<!-- Mail.ru Wrapper -->\r\n" +
                                        "	<div id=\"mailsub\">\r\n" +
                                        "		<!-- Wrapper -->\r\n" +
                                        "		<center class=\"wrapper\" style=\"table-layout: fixed; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; padding: 0; margin: 0 auto; width: 100%; max-width: 960px;\">\r\n" +
                                        "			<!-- Old wrap -->\r\n" +
                                        "	        <div class=\"webkit\">\r\n" +
                                        "				<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#ffffff\" style=\"padding: 0; margin: 0 auto; width: 100%; max-width: 960px;\">\r\n" +
                                        "					<tbody>\r\n" +
                                        "						<tr>\r\n" +
                                        "							<td align=\"center\">\r\n" +
                                        "								<!-- Start Section (1 column) -->\r\n" +
                                        "								<table id=\"intro\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#4F6331\" align=\"center\" style=\"width: 100%; padding: 0; margin: 0; background-image: url(https://github.com/lime7/responsive-html-template/blob/master/index/intro__bg.png?raw=true); background-size: auto 102%; background-position: center center; background-repeat: no-repeat; background-color: #080e02\">\r\n" +
                                        "									<tbody >\r\n" +
                                        "										<tr><td colspan=\"3\" height=\"20\"></td></tr>\r\n" +
                                        "										<tr>\r\n" +
                                        "											<td width=\"330\" style=\"width: 33%;\"></td>\r\n" +
                                        "											<!-- Logo -->\r\n" +
                                        "											<td width=\"300\" style=\"width: 30%;\" align=\"center\">\r\n" +
                                        "												<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; display: block; outline: none; text-decoration: none; line-height: 60px; height: 60px; color: #ffffff; font-family: Verdana, Geneva, sans-serif;  -webkit-text-size-adjust:none;\">\r\n" +
                                        "													<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/logo.png?raw=true\" alt=\"One Letter\" width=\"193\" height=\"43\" border=\"0\" style=\"border: none; display: block; -ms-interpolation-mode: bicubic;\">\r\n" +
                                        "												</a>\r\n" +
                                        "											</td>\r\n" +
                                        "											<!-- Social Button -->\r\n" +
                                        "											<td width=\"330\" style=\"width: 33%;\" align=\"right\">\r\n" +
                                        "												<div style=\"text-align: center; max-width: 150px; width: 100%;\">\r\n" +
                                        "													<span>&nbsp;</span>\r\n" +
                                        "													<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; outline: none; text-decoration: none; line-height: 60px; color: #ffffff; font-family: Verdana, Geneva, sans-serif;  -webkit-text-size-adjust:none\">\r\n" +
                                        "														<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/f.png?raw=true\" alt=\"facebook.com\" border=\"0\" width=\"11\" height=\"23\" style=\"border: none; outline: none; -ms-interpolation-mode: bicubic;\">\r\n" +
                                        "													</a>\r\n" +
                                        "													<span>&nbsp;</span>\r\n" +
                                        "													<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; outline: none; text-decoration: none; line-height: 60px; color: #ffffff; font-family: Verdana, Geneva, sans-serif; -webkit-text-size-adjust:none\">\r\n" +
                                        "														<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/vk.png?raw=true\" alt=\"vk.com\" border=\"0\" width=\"39\" height=\"23\" style=\"border: none; outline: none; -ms-interpolation-mode: bicubic;\">\r\n" +
                                        "													</a>\r\n" +
                                        "													<span>&nbsp;</span>\r\n" +
                                        "													<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; outline: none; text-decoration: none; line-height: 60px; color: #ffffff; font-family: Verdana, Geneva, sans-serif; -webkit-text-size-adjust:none;\">\r\n" +
                                        "														<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/g+.png?raw=true\" alt=\"google.com\" border=\"0\" width=\"23\" height=\"23\" style=\"border: none; outline: none; -ms-interpolation-mode: bicubic;\">\r\n" +
                                        "													</a>\r\n" +
                                        "													<span>&nbsp;</span>\r\n" +
                                        "												</div>\r\n" +
                                        "											</td>\r\n" +
                                        "										</tr>\r\n" +
                                        "										<tr><td colspan=\"3\" height=\"100\"></td></tr>\r\n" +
                                        "										<!-- Main Title -->\r\n" +
                                        "										<tr>\r\n" +
                                        "											<td colspan=\"3\" height=\"60\" align=\"center\">\r\n" +
                                        "												<div border=\"0\" style=\"border: none; line-height: 60px; color: #ffffff; font-family: Verdana, Geneva, sans-serif; font-size: 52px; text-transform: uppercase; font-weight: bolder;\">HELLO, WORLD!</div>\r\n" +
                                        "											</td>\r\n" +
                                        "										</tr>\r\n" +
                                        "										<!-- Line 1 -->\r\n" +
                                        "										<tr>\r\n" +
                                        "											<td colspan=\"3\" height=\"20\" valign=\"bottom\" align=\"center\">\r\n" +
                                        "												<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/line-1.png?raw=true\" alt=\"line\" border=\"0\" width=\"464\" height=\"5\" style=\"border: none; outline: none; max-width: 464px; width: 100%; -ms-interpolation-mode: bicubic;\" >\r\n" +
                                        "											</td>\r\n" +
                                        "										</tr>\r\n" +
                                        "										<!-- Meta title -->\r\n" +
                                        "										<tr>\r\n" +
                                        "											<td colspan=\"3\">\r\n" +
                                        "												<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"padding: 0; margin: 0; width: 100%;\">\r\n" +
                                        "													<tbody>\r\n" +
                                        "														<tr>\r\n" +
                                        "															<td width=\"90\" style=\"width: 9%;\"></td>\r\n" +
                                        "															<td align=\"center\">\r\n" +
                                        "																<div border=\"0\" style=\"border: none; height: 60px;\">\r\n" +
                                        "																	<p style=\"font-size: 18px; line-height: 24px; font-family: Verdana, Geneva, sans-serif; color: #ffffff; text-align: center; mso-table-lspace:0;mso-table-rspace:0;\">\r\n" +
                                        "																		This is Photoshop's version  of Lorem Ipsum. Proin gravida nibh vel velit auctor aliquet. Aenean sollicitudin, lorem quis bibendum auctor, nisi elit consequat ipsum.\r\n" +
                                        "																	</p>\r\n" +
                                        "																</div>\r\n" +
                                        "															</td>\r\n" +
                                        "															<td width=\"90\" style=\"width: 9%;\"></td>\r\n" +
                                        "														</tr>\r\n" +
                                        "													</tbody>\r\n" +
                                        "												</table>\r\n" +
                                        "											</td>\r\n" +
                                        "										</tr>\r\n" +
                                        "										<tr><td colspan=\"3\" height=\"160\"></td></tr>\r\n" +
                                        "										<tr>\r\n" +
                                        "											<td width=\"330\"></td>\r\n" +
                                        "											<!-- Button Start -->\r\n" +
                                        "											<td width=\"300\" align=\"center\" height=\"52\">\r\n" +
                                        "												<div style=\"background-image: url(https://github.com/lime7/responsive-html-template/blob/master/index/intro__btn.png?raw=true); background-size: 100% 100%; background-position: center center; width: 225px;\">\r\n" +
                                        "													<a href=\"#\" target=\"_blank\" width=\"160\" height=\"52\" border=\"0\" bgcolor=\"#009789\" style=\"border: none; outline: none; display: block; width:160px; height: 52px; text-transform: uppercase; text-decoration: none; font-size: 17px; line-height: 52px; color: #ffffff; font-family: Verdana, Geneva, sans-serif; text-align: center; background-color: #009789;  -webkit-text-size-adjust:none;\">\r\n" +
                                        "														Get it now\r\n" +
                                        "													</a>\r\n" +
                                        "												</div>\r\n" +
                                        "											</td>\r\n" +
                                        "											<td width=\"330\"></td>\r\n" +
                                        "										</tr>\r\n" +
                                        "										<tr><td colspan=\"3\" height=\"85\"></td></tr>\r\n" +
                                        "									</tbody>\r\n" +
                                        "								</table><!-- End Start Section -->\r\n" +
                                        "								<!-- Icon articles (4 columns) -->\r\n" +
                                        "								<table id=\"icon__article\" class=\"device\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"width: 100%; padding: 0; margin: 0; background-color: #ffffff\">\r\n" +
                                        "									<tbody>								\r\n" +
                                        "										<tr>\r\n" +
                                        "											<td align=\"center\">\r\n" +
                                        "												<div style=\"display: inline-block;\">\r\n" +
                                        "													<table width=\"240\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"padding: 0; margin: 0; mso-table-lspace:0pt; mso-table-rspace:0pt;\"  class=\"article\">\r\n" +
                                        "													<tbody>\r\n" +
                                        "														<tr> <td colspan=\"3\" height=\"40\"></td> </tr>\r\n" +
                                        "														<tr>\r\n" +
                                        "															<td width=\"80\" style=\"width: 8%;\"></td>\r\n" +
                                        "															<td align=\"center\">\r\n" +
                                        "																<div class=\"imgs\">\r\n" +
                                        "																	<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; display: block; outline: none; text-decoration: none; line-height: 60px; height: 60px; color: #ffffff; font-family: Verdana, Geneva, sans-serif; margin: 0 30px; -webkit-text-size-adjust:none;\">\r\n" +
                                        "																		<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/ico-1.png?raw=true\" alt=\"icon\" border=\"0\" width=\"60\" height=\"60\" style=\"border: none; display: block; outline: none; -ms-interpolation-mode: bicubic;\">\r\n" +
                                        "																	</a>\r\n" +
                                        "																</div>\r\n" +
                                        "																<h3 border=\"0\" style=\"border: none; line-height: 14px; color: #212121; font-family: Verdana, Geneva, sans-serif; font-size: 16px; text-transform: uppercase; font-weight: normal; overflow: hidden; margin:17px 0 0px 0;\">Lorem Ipsum\r\n" +
                                        "																</h3>\r\n" +
                                        "																<p style=\"line-height: 20px; color: #727272; font-family: Verdana, Geneva, sans-serif; font-size: 16px; text-align: center; overflow: hidden; margin: 10px 0; mso-table-lspace:0;mso-table-rspace:0;\"> This is Photoshop's version  of Lorem\r\n" +
                                        "																</p>\r\n" +
                                        "															</td>\r\n" +
                                        "															<td width=\"80\" style=\"width: 8%;\"></td>\r\n" +
                                        "														</tr>\r\n" +
                                        "														<tr><td colspan=\"3\" height=\"10\"></td></tr>					\r\n" +
                                        "														<tr>\r\n" +
                                        "															<td colspan=\"3\" height=\"5\" valign=\"top\" align=\"center\">\r\n" +
                                        "																<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/line-2.png?raw=true\" alt=\"line\" border=\"0\" width=\"960\" height=\"5\" style=\"border: none; outline: none; max-width: 960px; width: 100%; -ms-interpolation-mode: bicubic;\" >\r\n" +
                                        "															</td>\r\n" +
                                        "														</tr>						\r\n" +
                                        "													</tbody>\r\n" +
                                        "													</table>\r\n" +
                                        "												</div>\r\n" +
                                        "\r\n" +
                                        "												<div style=\"display: inline-block; margin-left: -4px;\">\r\n" +
                                        "													<table width=\"240\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"padding: 0; margin: 0; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"article\">\r\n" +
                                        "													<tbody>\r\n" +
                                        "														<tr> <td colspan=\"3\" height=\"40\"></td> </tr>\r\n" +
                                        "														<tr>\r\n" +
                                        "															<td width=\"80\" style=\"width: 8%;\"></td>\r\n" +
                                        "															<td align=\"center\">\r\n" +
                                        "																<div class=\"imgs\">\r\n" +
                                        "																	<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; display: block; outline: none; text-decoration: none; line-height: 60px; height: 60px; color: #ffffff; font-family: Verdana, Geneva, sans-serif; margin: 0 30px; -webkit-text-size-adjust:none;\">\r\n" +
                                        "																		<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/ico-2.png?raw=true\" alt=\"icon\" border=\"0\" width=\"60\" height=\"60\" style=\"border: none; display: block; outline: none; -ms-interpolation-mode: bicubic;\">\r\n" +
                                        "																	</a>\r\n" +
                                        "																</div>\r\n" +
                                        "																<h3 border=\"0\" style=\"border: none; line-height: 14px; color: #212121; font-family: Verdana, Geneva, sans-serif; font-size: 16px; text-transform: uppercase; font-weight: normal; overflow: hidden; margin:17px 0 0px 0;\">Lorem Ipsum\r\n" +
                                        "																</h3>\r\n" +
                                        "																<p style=\"line-height: 20px; color: #727272; font-family: Verdana, Geneva, sans-serif; font-size: 16px; text-align: center; overflow: hidden; margin: 10px 0; mso-table-lspace:0;mso-table-rspace:0;\"> This is Photoshop's version  of Lorem\r\n" +
                                        "																</p>\r\n" +
                                        "															</td>\r\n" +
                                        "															<td width=\"80\" style=\"width: 8%;\"></td>\r\n" +
                                        "														</tr>\r\n" +
                                        "														<tr><td colspan=\"3\" height=\"10\"></td></tr>					\r\n" +
                                        "														<tr>\r\n" +
                                        "															<td colspan=\"3\" height=\"5\" valign=\"top\" align=\"center\">\r\n" +
                                        "																<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/line-2.png?raw=true\" alt=\"line\" border=\"0\" width=\"960\" height=\"5\" style=\"border: none; outline: none; max-width: 960px; width: 100%; -ms-interpolation-mode: bicubic;\" >\r\n" +
                                        "															</td>\r\n" +
                                        "														</tr>					\r\n" +
                                        "													</tbody>\r\n" +
                                        "													</table>\r\n" +
                                        "												</div>\r\n" +
                                        "\r\n" +
                                        "												<div style=\"display: inline-block; margin-left: -4px;\">\r\n" +
                                        "													<table width=\"240\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"padding: 0; margin: 0; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"article\">\r\n" +
                                        "													<tbody>\r\n" +
                                        "														<tr> <td colspan=\"3\" height=\"40\"></td> </tr>\r\n" +
                                        "														<tr>\r\n" +
                                        "															<td width=\"80\" style=\"width: 8%;\"></td>\r\n" +
                                        "															<td align=\"center\">\r\n" +
                                        "																<div class=\"imgs\">\r\n" +
                                        "																	<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; display: block; outline: none; text-decoration: none; line-height: 60px; height: 60px; color: #ffffff; font-family: Verdana, Geneva, sans-serif; margin: 0 30px; -webkit-text-size-adjust:none;\">\r\n" +
                                        "																		<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/ico-3.png?raw=true\" alt=\"icon\" border=\"0\" width=\"60\" height=\"60\" style=\"border: none; display: block; outline: none; -ms-interpolation-mode: bicubic;\">\r\n" +
                                        "																	</a>\r\n" +
                                        "																</div>\r\n" +
                                        "																<h3 border=\"0\" style=\"border: none; line-height: 14px; color: #212121; font-family: Verdana, Geneva, sans-serif; font-size: 16px; text-transform: uppercase; font-weight: normal; overflow: hidden; margin:17px 0 0px 0;\">Lorem Ipsum\r\n" +
                                        "																</h3>\r\n" +
                                        "																<p style=\"line-height: 20px; color: #727272; font-family: Verdana, Geneva, sans-serif; font-size: 16px; text-align: center; overflow: hidden; margin: 10px 0; mso-table-lspace:0;mso-table-rspace:0;\"> This is Photoshop's version  of Lorem\r\n" +
                                        "																</p>\r\n" +
                                        "															</td>\r\n" +
                                        "															<td width=\"80\" style=\"width: 8%;\"></td>\r\n" +
                                        "														</tr>\r\n" +
                                        "														<tr><td colspan=\"3\" height=\"10\"></td></tr>					\r\n" +
                                        "														<tr>\r\n" +
                                        "															<td colspan=\"3\" height=\"5\" valign=\"top\" align=\"center\">\r\n" +
                                        "																<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/line-2.png?raw=true\" alt=\"line\" border=\"0\" width=\"960\" height=\"5\" style=\"border: none; outline: none; max-width: 960px; width: 100%; -ms-interpolation-mode: bicubic;\" >\r\n" +
                                        "															</td>\r\n" +
                                        "														</tr>					\r\n" +
                                        "													</tbody>\r\n" +
                                        "													</table>\r\n" +
                                        "												</div>\r\n" +
                                        "\r\n" +
                                        "												<div style=\"display: inline-block; margin-left: -4px;\">\r\n" +
                                        "													<table width=\"240\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"padding: 0; margin: 0; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"article\">\r\n" +
                                        "													<tbody>\r\n" +
                                        "														<tr> <td colspan=\"3\" height=\"40\"></td> </tr>\r\n" +
                                        "														<tr>\r\n" +
                                        "															<td width=\"80\" style=\"width: 8%;\"></td>\r\n" +
                                        "															<td align=\"center\">\r\n" +
                                        "																<div class=\"imgs\">\r\n" +
                                        "																	<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; display: block; outline: none; text-decoration: none; line-height: 60px; height: 60px; color: #ffffff; font-family: Verdana, Geneva, sans-serif; margin: 0 30px; -webkit-text-size-adjust:none;\">\r\n" +
                                        "																		<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/ico-4.png?raw=true\" alt=\"icon\" border=\"0\" width=\"60\" height=\"60\" style=\"border: none; display: block; outline: none; -ms-interpolation-mode: bicubic;\">\r\n" +
                                        "																	</a>\r\n" +
                                        "																</div>\r\n" +
                                        "																<h3 border=\"0\" style=\"border: none; line-height: 14px; color: #212121; font-family: Verdana, Geneva, sans-serif; font-size: 16px; text-transform: uppercase; font-weight: normal; overflow: hidden; margin:17px 0 0px 0;\">Lorem Ipsum\r\n" +
                                        "										̥						</h3>\r\n" +
                                        "																<p style=\"line-height: 20px; color: #727272; font-family: Verdana, Geneva, sans-serif; font-size: 16px; text-align: center; overflow: hidden; margin: 10px 0; mso-table-lspace:0;mso-table-rspace:0;\"> This is Photoshop's version  of Lorem\r\n" +
                                        "																</p>\r\n" +
                                        "															</td>\r\n" +
                                        "															<td width=\"80\" style=\"width: 8%;\"></td>\r\n" +
                                        "														</tr>\r\n" +
                                        "														<tr><td colspan=\"3\" height=\"10\"></td></tr>					\r\n" +
                                        "														<tr>\r\n" +
                                        "															<td colspan=\"3\" height=\"5\" valign=\"top\" align=\"center\">\r\n" +
                                        "																<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/line-2.png?raw=true\" alt=\"line\" border=\"0\" width=\"960\" height=\"5\" style=\"border: none; outline: none; max-width: 960px; width: 100%; -ms-interpolation-mode: bicubic; \" >\r\n" +
                                        "															</td>\r\n" +
                                        "														</tr>					\r\n" +
                                        "													</tbody>\r\n" +
                                        "													</table>\r\n" +
                                        "												</div>										\r\n" +
                                        "											</td>\r\n" +
                                        "										</tr>\r\n" +
                                        "										<tr> <td colspan=\"5\" height=\"40\"></td> </tr>\r\n" +
                                        "									</tbody>\r\n" +
                                        "								</table> <!-- End Icon articles -->						\r\n" +
                                        "								<!-- News article (2 columns) -->\r\n" +
                                        "								<table id=\"news__article\" class=\"device\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"width: 100%; padding: 0; margin: 0; background-color: #ffffff\">\r\n" +
                                        "									<tbody>\r\n" +
                                        "										<tr>\r\n" +
                                        "											<td width=\"90\" style=\"width: 9.3%;\"></td>\r\n" +
                                        "											<td align=\"center\">\r\n" +
                                        "												<div style=\"display: inline-block;\">\r\n" +
                                        "													<table class=\"news__art\" align=\"center\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" bgcolor=\"#ffffff\" style=\"padding: 0; margin: 0; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\r\n" +
                                        "														<tbody>\r\n" +
                                        "															<tr>\r\n" +
                                        "																<td width=\"80\" style=\"width:10%;\"></td>\r\n" +
                                        "																<td colspan=\"2\" align=\"center\" width=\"300\">\r\n" +
                                        "																	<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; display: block; outline: none; text-decoration: none; line-height: 220px; height: 220px; color: #ffffff; font-family: Verdana, Geneva, sans-serif; -webkit-text-size-adjust:none;\">\r\n" +
                                        "																		<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/pic-1.png?raw=true\" alt=\"icon\" border=\"0\" width=\"300\" height=\"220\" style=\"border: none; display: block; outline: none; -ms-interpolation-mode: bicubic;\">\r\n" +
                                        "																	</a>\r\n" +
                                        "																</td>\r\n" +
                                        "																<td width=\"80\" style=\"width:10%;\"></td>\r\n" +
                                        "															</tr>\r\n" +
                                        "															<tr> <td colspan=\"4\" height=\"15\"></td> </tr>\r\n" +
                                        "															<tr>\r\n" +
                                        "																<td width=\"80\" style=\"width:10%;\"></td>\r\n" +
                                        "																<td align=\"left\">\r\n" +
                                        "																	<div border=\"0\" style=\"border: none; line-height: 22px; color: #212121; font-family: Verdana, Geneva, sans-serif; font-size: 16px;\">\r\n" +
                                        "																		Lorem Ipsum\r\n" +
                                        "																	</div>\r\n" +
                                        "																</td>\r\n" +
                                        "																<td align=\"right\">\r\n" +
                                        "																	<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; outline: none; text-decoration: none; line-height: 18px; font-size: 12px; color: #b7b7b7; font-family: Verdana, Geneva, sans-serif; -webkit-text-size-adjust:none;\">21 aug</a>\r\n" +
                                        "																	<span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>\r\n" +
                                        "																	<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; outline: none; text-decoration: none; line-height: 18px; font-size: 12px; color: #b7b7b7; font-family: Verdana, Geneva, sans-serif; -webkit-text-size-adjust:none;\">Author</a>\r\n" +
                                        "																</td>\r\n" +
                                        "																<td width=\"80\" style=\"width:10%;\"></td>\r\n" +
                                        "															</tr>\r\n" +
                                        "															\r\n" +
                                        "															<tr>\r\n" +
                                        "																<td width=\"80\" style=\"width:10%;\"></td>\r\n" +
                                        "																<td colspan=\"2\" width=\"300\">\r\n" +
                                        "																	<div border=\"0\" style=\"border: none; line-height: 18px; color: #727272; font-family: Verdana, Geneva, sans-serif; font-size: 13px;\">\r\n" +
                                        "																		This is Photoshop's version  of Lorem Ipsum. Proin <a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; outline: none; line-height: 18px; font-size: 13px; color: #31796e; font-family: Verdana, Geneva, sans-serif; -webkit-text-size-adjust:none;\">more ... </a>\r\n" +
                                        "																	</div>\r\n" +
                                        "																</td>\r\n" +
                                        "																<td width=\"80\" style=\"width:10%;\"></td>\r\n" +
                                        "															</tr>\r\n" +
                                        "															<tr> <td colspan=\"4\" height=\"40\"></td> </tr>\r\n" +
                                        "														</tbody>\r\n" +
                                        "													</table>\r\n" +
                                        "												</div>\r\n" +
                                        "\r\n" +
                                        "												<div style=\"display: inline-block;\">\r\n" +
                                        "													<table class=\"news__art\" align=\"center\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" bgcolor=\"#ffffff\" style=\"padding: 0; margin: 0; mso-table-lspace:0pt; mso-table-rspace:0pt; \">\r\n" +
                                        "														<tbody>\r\n" +
                                        "															<tr>\r\n" +
                                        "																<td width=\"80\" style=\"width:10%;\"></td>\r\n" +
                                        "																<td colspan=\"2\" align=\"center\" width=\"300\">\r\n" +
                                        "																	<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; display: block; outline: none; text-decoration: none; line-height: 220px; height: 220px; color: #ffffff; font-family: Verdana, Geneva, sans-serif; -webkit-text-size-adjust:none;\">\r\n" +
                                        "																		<img src=\"https://github.com/lime7/responsive-html-template/blob/master/index/pic-2.png?raw=true\" alt=\"icon\" border=\"0\" width=\"300\" height=\"220\" style=\"border: none; display: block; outline: none; -ms-interpolation-mode: bicubic;\">\r\n" +
                                        "																	</a>\r\n" +
                                        "																</td>\r\n" +
                                        "																<td width=\"80\" style=\"width:10%;\"></td>\r\n" +
                                        "															</tr>\r\n" +
                                        "															<tr> <td colspan=\"4\" height=\"15\"></td> </tr>\r\n" +
                                        "															<tr>\r\n" +
                                        "																<td width=\"80\" style=\"width:10%;\"></td>\r\n" +
                                        "																<td align=\"left\">\r\n" +
                                        "																	<div border=\"0\" style=\"border: none; line-height: 22px; color: #212121; font-family: Verdana, Geneva, sans-serif; font-size: 16px;\">\r\n" +
                                        "																		Lorem Ipsum\r\n" +
                                        "																	</div>\r\n" +
                                        "																</td>\r\n" +
                                        "																<td align=\"right\">\r\n" +
                                        "																	<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; outline: none; text-decoration: none; line-height: 18px; font-size: 12px; color: #b7b7b7; font-family: Verdana, Geneva, sans-serif; -webkit-text-size-adjust:none;\">21 aug</a>\r\n" +
                                        "																	<span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>\r\n" +
                                        "																	<a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; outline: none; text-decoration: none; line-height: 18px; font-size: 12px; color: #b7b7b7; font-family: Verdana, Geneva, sans-serif; -webkit-text-size-adjust:none;\">Author</a>\r\n" +
                                        "																</td>\r\n" +
                                        "																<td width=\"80\" style=\"width:10%;\"></td>\r\n" +
                                        "															</tr>\r\n" +
                                        "															\r\n" +
                                        "															<tr>\r\n" +
                                        "																<td width=\"80\" style=\"width:10%;\"></td>\r\n" +
                                        "																<td colspan=\"2\" width=\"300\">\r\n" +
                                        "																	<div border=\"0\" style=\"border: none; line-height: 18px; color: #727272; font-family: Verdana, Geneva, sans-serif; font-size: 13px;\">\r\n" +
                                        "																		This is Photoshop's version  of Lorem Ipsum. Proin <a href=\"#\" target=\"_blank\" border=\"0\" style=\"border: none; outline: none; line-height: 18px; font-size: 13px; color: #31796e; font-family: Verdana, Geneva, sans-serif; -webkit-text-size-adjust:none;\">more ... </a>\r\n" +
                                        "																	</div>\r\n" +
                                        "																</td>\r\n" +
                                        "																<td width=\"80\" style=\"width:10%;\"></td>\r\n" +
                                        "															</tr>\r\n" +
                                        "															<tr> <td colspan=\"4\" height=\"40\"></td> </tr>\r\n" +
                                        "														</tbody>\r\n" +
                                        "													</table>\r\n" +
                                        "												</div>\r\n" +
                                        "											</td>\r\n" +
                                        "											<td width=\"90\" style=\"width: 9.3%;\"></td>\r\n" +
                                        "										</tr>\r\n" +
                                        "									</tbody>\r\n" +
                                        "								</table> <!-- End News article -->						\r\n" +
                                        "								<!-- One art (2 columns) -->\r\n" +
                                        "								<table id=\"one__art\" class=\"device\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"width: 100%; padding: 0; margin: 0; background-image: url(https://github.com/lime7/responsive-html-template/blob/master/index/one__bg.png?raw=true); background-size: 100% 100%; background-position: center center; background-repeat: no-repeat; background-color: #ECECED;\">\r\n" +
                                        "									<tbody>\r\n" +
                                        "										<tr>											\r\n" +
                                        "											<td align=\"center\">\r\n" +
                                        "												<table align=\"right\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#ffffff\" style=\"padding: 0; margin: 0; background-image: url(ohttps://github.com/lime7/responsive-html-template/blob/master/index/one__art-mask.png?raw=true); background-size: 100% 100%; background-position: center center; background-repeat: no-repeat; background-color: rgba(255,255,255,.85); max-width: 480px; width:100%;\">\r\n" +
                                        "													<tbody>\r\n" +
                                        "														<tr><td colspan=\"3\" height=\"15\"></td></tr>\r\n" +
                                        "														<tr>\r\n" +
                                        "															<td width=\"20\" style=\"width:4%;\"></td>\r\n" +
                                        "															<td align=\"left\">\r\n" +
                                        "																<div border=\"0\" style=\"border: none; line-height: 18px; color: #212121; font-family: Verdana, Geneva, sans-serif; font-size: 20px;\">\r\n" +
                                        "																	Lorem Ipsum\r\n" +
                                        "																</div>\r\n" +
                                        "															</td>\r\n" +
                                        "															<td width=\"20\" style=\"width:4%;\"></td>\r\n" +
                                        "														</tr>\r\n" +
                                        "														<tr>\r\n" +
                                        "															<td width=\"20\" style=\"width:4%;\"></td>\r\n" +
                                        "															<td align=\"left\">\r\n" +
                                        "																<div border=\"0\" style=\"border: none;\">\r\n" +
                                        "																	<p style=\"line-height: 22px; color: #727272; font-family: Verdana, Geneva, sans-serif; font-size: 16px; text-align: left; mso-table-lspace:0;mso-table-rspace:0;\">\r\n" +
                                        "																		This is Photoshop's version  of Lorem Ipsum. Proin gravida nibh vel velit auctor aliquet. Aenean sollicitudin, lorem quis bibendum auctor, nisi elit consequat ipsum, nec sagittis sem nibh id elit. \r\n" +
                                        "																	</p>\r\n" +
                                        "																</div>\r\n" +
                                        "															</td>\r\n" +
                                        "															<td width=\"20\" style=\"width:4%;\"></td>\r\n" +
                                        "														</tr>\r\n" +
                                        "														<tr>\r\n" +
                                        "															<td width=\"20\" style=\"width:4%;\"></td>\r\n" +
                                        "															<td align=\"left\" height=\"34\">\r\n" +
                                        "																<div style=\"background-image: url(https://github.com/lime7/responsive-html-template/blob/master/index/one__art-btn.png?raw=true); background-size: 100% 100%; background-position: center center; width: 125px;\">\r\n" +
                                        "																	<a href=\"#\" target=\"_blank\" width=\"90\" height=\"34\" align=\"center\" bgcolor=\"#9de0dc\" border=\"0\" style=\"border: none; outline: none; display: block; height: 34px; text-decoration: none; font-size: 16px; line-height: 34px; color: #ffffff; font-family: Verdana, Geneva, sans-serif; text-align: center;  -webkit-text-size-adjust:none; background-color:#9de0dc; margin: 0 auto; width: 90px;\">\r\n" +
                                        "																		view more\r\n" +
                                        "																	</a>\r\n" +
                                        "																</div>\r\n" +
                                        "															</td>\r\n" +
                                        "															<td width=\"20\" style=\"width:4%;\"></td>\r\n" +
                                        "														</tr>\r\n" +
                                        "														<tr><td colspan=\"3\" height=\"13\"></td></tr>\r\n" +
                                        "													</tbody>\r\n" +
                                        "												</table>\r\n" +
                                        "											</td>\r\n" +
                                        "										</tr>\r\n" +
                                        "									</tbody>\r\n" +
                                        "								</table> <!-- End One art -->\r\n" +
                                        "								<!-- Footer -->\r\n" +
                                        "								<table id=\"news__article\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"width: 100%; padding: 0; margin: 0; background-color: #ffffff\">\r\n" +
                                        "									<tbody>\r\n" +
                                        "										<tr><td colspan=\"3\" height=\"23\"></td></tr>\r\n" +
                                        "										<tr>\r\n" +
                                        "											<td align=\"center\">\r\n" +
                                        "												<div border=\"0\" style=\"border: none; line-height: 14px; color: #727272; font-family: Verdana, Geneva, sans-serif; font-size: 16px;\">\r\n" +
                                        "													2015 � <a href=\"https://semenchenkov.github.io/\" target=\"_blank\" border=\"0\" style=\"border: none; outline: none; text-decoration: none; line-height: 14px; font-size: 16px; color: #727272; font-family: Verdana, Geneva, sans-serif; -webkit-text-size-adjust:none;\">Semenchenko V. Helen</a>\r\n" +
                                        "												</div>\r\n" +
                                        "											</td>\r\n" +
                                        "										</tr>\r\n" +
                                        "										<tr><td colspan=\"3\" height=\"23\"></td></tr>\r\n" +
                                        "									</tbody>\r\n" +
                                        "								</table> <!-- End Footer -->\r\n" +
                                        "							</td>\r\n" +
                                        "						</tr>\r\n" +
                                        "					</tbody>\r\n" +
                                        "				</table>\r\n" +
                                        "			</div> <!-- End Old wrap -->\r\n" +
                                        "		</center> <!-- End Wrapper -->\r\n" +
                                        "	</div> <!-- End Mail.ru Wrapper -->\r\n" +
                                        "</body>\r\n" +
                                        "\r\n" +
                                        "</html>";
                                send(gmail_user, gmail_password, TO, gmail_subject, gmail_message);

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
                                // Showing password
                                chshowpass.setSelected(true);
                                txconfirmpass.requestFocus();
                            } else
                            chshowpass.setSelected(false);
                    } else if (src == txuser)
                    {
                        checkUser();
                    } else if (src == txpass)
                    {
                        if (!pass.equals(confirmpass))
                            txconfirmpass.requestFocus();
                    }
            }

        private void checkUser()
            {
                try
                    {

                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  patienttb( username varchar(100),"
                                + "password varchar(100)," + "email varchar(100)," + "phone varchar(100)," + "gender int,"
                                + "material int," + "dob date ," + "location varchar(100)," + "address varchar(100),"
                                + "city varchar(100)," + "state varchar(100)," + "disease varchar(100)," + "past varchar(100),"
                                + "primary key(username))");

                        PreparedStatement pres = con.prepareStatement("select count(*) from patienttb where username=?");
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

        @Override
        public void stateChanged(ChangeEvent e)
            {


            }

        @Override
        public void insertUpdate(DocumentEvent e)
            {


            }

        @Override
        public void removeUpdate(DocumentEvent e)
            {


            }

        @Override
        public void changedUpdate(DocumentEvent e)
            {


            }

        private void send(String from, String password, String to, String sub, String msg)
            {
                // Get properties object
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");
                // get Session
                Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator()
                    {
                        protected PasswordAuthentication getPasswordAuthentication()
                            {
                                return new PasswordAuthentication(from, password);
                            }
                    });
                // compose message
                try
                    {
                        // Create a default MimeMessage object.
                        Message message = new MimeMessage(session);

                        // Set From: header field of the header.
                        message.setFrom(new InternetAddress(from));


                        // message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                        // Set To: header field of the header.
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

                        // Set Subject: header field
                        message.setSubject(sub);

                        // Create the message part
                        BodyPart messageBodyPart = new MimeBodyPart();
                        // Now set the actual message
                        messageBodyPart.setContent(msg, "text/html");

                        // Creating the image part
                        BodyPart imageBodyPart = new MimeBodyPart();
                        String filename = "F:\\Intellj Idea\\Projects\\Doctor-Appointment\\src\\attach.png";
                        DataSource source = new FileDataSource(filename);
                        imageBodyPart.setDataHandler(new DataHandler(source));
                        imageBodyPart.setFileName("Prior Health");
                        imageBodyPart.setHeader("Health", "<image>");

                        // Create a multiple parts
                        Multipart multipart = new MimeMultipart();

                        // Set message part
                        multipart.addBodyPart(messageBodyPart);
                        // Set image part
                        multipart.addBodyPart(imageBodyPart);


                        // Send the complete message parts
                        message.setContent(multipart);


                        // send message
                        Transport.send(message);
                        System.out.println("Mail sent successfully");
                    } catch (MessagingException e)
                    {
                        throw new RuntimeException(e);
                    }
            }
    }

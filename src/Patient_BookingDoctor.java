import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Objects;

class Patient_BookingDoctor extends JDialog implements ActionListener, ItemListener
    {

        // This is JDialog
        // Called by Patient_Viewdoctorinfo(); // also dispose it

        private final JLabel lbdoc;
        private final JLabel lbpat;
        private final JLabel lbslot;
        private final JLabel lbdate;
        private final JLabel lbday;
        private final JTextField txdoctor;
        private final JTextField txpatient;
        private final JComboBox<String> chyy;
        private final JComboBox<String> chmm;
        private final JComboBox<String> chdd;
        private final JComboBox<String> chday;
        private final JRadioButton chmslot;
        private final JRadioButton cheslot;
        private final JComboBox<String> cbms;
        private final JComboBox<String> cbme;
        private final JComboBox<String> cbes;
        private final JComboBox<String> cbee;
        private final JPanel P;
        private final JButton btbook;
        private final JButton btcancel;
        private String mstart;
        private String mend;
        private String estart;
        private String eend;
        private final String userp;
        private final String userd;

        Patient_BookingDoctor(String userp, String userd)
            {
                this.userp = userp;
                this.userd = userd;
                setVisible(true);
                setSize(new Dimension(1000, 1000));
                setPreferredSize(new Dimension(1000, 1000));
                setLayout(new BorderLayout());
                lbdoc = new JLabel("Doctor Name");
                lbpat = new JLabel("Patient Name");
                lbslot = new JLabel("SLOTS:");
                lbday = new JLabel("DAY:");
                lbdate = new JLabel("DATE:");

                txdoctor = new JTextField();
                txpatient = new JTextField();

                chyy = new JComboBox<>();
                chmm = new JComboBox<>();
                chdd = new JComboBox<>();


                chyy.addItem("Year");
                for (int i = 1981; i <= 2000; i++)
                    chyy.addItem(i + "");

                chmm.addItem("Month");
                for (int i = 1; i <= 12; i++)
                    chmm.addItem(i + "");

                chdd.addItem("Date");

                chday = new JComboBox<String>();
                filldays(); // it will fill days

                JPanel pdob = new JPanel();
                pdob.setLayout(new GridLayout(1, 3));
                // according to month and year date must change
                pdob.add(chyy);
                pdob.add(chmm);
                pdob.add(chdd);

                txdoctor.setText(userd);
                txpatient.setText(userp);

                chmslot = new JRadioButton("Morning time");
                cheslot = new JRadioButton("Evening time");

                ButtonGroup time = new ButtonGroup();
                time.add(chmslot);
                time.add(cheslot);

                cbms = new JComboBox<>();
                cbme = new JComboBox<>();
                cbme.setEnabled(false);
                //fillmslottime();


                cbes = new JComboBox<String>();
                cbee = new JComboBox<String>();
                cbee.setEnabled(false);
                //filleslottime();

                btbook = new JButton("Confirm Booking");
                btcancel = new JButton("Cancel");

                JPanel pbtn = new JPanel();
                pbtn.setLayout(new FlowLayout());
                pbtn.add(btbook);
                pbtn.add(btcancel);

                P = new JPanel();
                JPanel pslot = new JPanel();
                pslot.add(chmslot);
                pslot.add(cheslot);

                P.setLayout(new GridLayout(7, 2, 50, 50));
                P.add(lbdoc);
                P.add(txdoctor);
                P.add(lbpat);
                P.add(txpatient);
                P.add(lbdate);
                P.add(pdob);
                P.add(lbday);
                P.add(chday);
                P.add(lbslot);
                P.add(pslot);
                P.add(cbms);
                P.add(cbes);
                P.add(cbme);
                P.add(cbee);
                add(P, BorderLayout.CENTER);
                add(pbtn, BorderLayout.SOUTH);

                enableevening(false);
                enablemorning(false);

                btbook.addActionListener(this);
                btcancel.addActionListener(this);

                txdoctor.setEnabled(false);
                txpatient.setEnabled(false);

                // slots
                chmslot.addItemListener(this);
                cheslot.addItemListener(this);

                chyy.addItemListener(this);
                chmm.addItemListener(this);
                chdd.addItemListener(this);

                cbms.addItemListener(this);
                cbes.addItemListener(this);
                style();


            }

        private void filldays()
            {
                try
                    {

                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  DoctorscheduleTb(username varchar(100),days  varchar(100),morningtimestart varchar(100),morningtimeend varchar(100),eveningtimestart varchar(100),eveningtimeend varchar(100),primary key(username))");


                        PreparedStatement pstmt = con.prepareStatement("select days from DoctorscheduleTb where username=?");
                        pstmt.setString(1, userd);

                        ResultSet rs = pstmt.executeQuery();
                        rs.next(); // as only one doctor is there
                        String q = rs.getString("days");
                        // getting 01010101
                        String[] d = {":: Days ::", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                        // dont take zero index
                        chday.addItem(d[0]);
                        for (int i = 0; i < q.length(); i++)
                            {
                                if (q.charAt(i) == '1')
                                    chday.addItem(d[i + 1]);
                            }

                    } catch (ClassNotFoundException | SQLException ce)
                    {

                        ce.printStackTrace();
                    }

            }

        private void fillmslottime()
            {
                try
                    {


                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  DoctorscheduleTb(username varchar(100),days  varchar(100),morningtimestart varchar(100),morningtimeend varchar(100),eveningtimestart varchar(100),eveningtimeend varchar(100),primary key(username))");


                        PreparedStatement pstmt = con.prepareStatement("select morningtimestart,morningtimeend from DoctorscheduleTb where username=?");
                        pstmt.setString(1, userd);

                        ResultSet rs = pstmt.executeQuery();

                        rs.next(); // as only one doctor is there
                        mstart = rs.getString("morningtimestart");
                        mend = rs.getString("morningtimeend");

                        //System.out.println(mstart);
                        //System.out.println(mend);

                        fillmslottimeinterval();

                    } catch (ClassNotFoundException | SQLException ce)
                    {

                        ce.printStackTrace();
                    }
            }


        private void fillmslottimeinterval()
            {
                cbms.removeAllItems();
                cbme.removeAllItems();

                for (int i = 6; i <= 12; i++)
                    {
                        if (i <= 9)
                            cbms.addItem("0" + i + ":00 AM");

                        else
                            cbms.addItem(i + ":00 AM");
                    }

                cbms.addItem("01:00 PM");
                // fill all the entries of chms

                while (true)
                    {

                        if (!cbms.getItemAt(cbms.getItemCount() - 1).equals(mend))
                            cbms.removeItemAt(cbms.getItemCount() - 1);
                        else
                            break;
                    }

                while (true)
                    {
                        if (!cbms.getItemAt(0).equals(mstart))
                            cbms.removeItemAt(0);
                        else
                            break;
                    }

                cbms.removeItemAt(cbms.getItemCount() - 1);
                cbme.addItem(cbms.getItemAt(1));

            }

        private void filleslottime()
            {
                try
                    {


                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  DoctorscheduleTb(username varchar(100),days  varchar(100),morningtimestart varchar(100),morningtimeend varchar(100),eveningtimestart varchar(100),eveningtimeend varchar(100),primary key(username))");


                        PreparedStatement pstmt = con.prepareStatement("select eveningtimestart,eveningtimeend from DoctorscheduleTb where username=?");
                        pstmt.setString(1, userd);

                        ResultSet rs = pstmt.executeQuery();

                        rs.next(); // as only one doctor is there
                        estart = rs.getString("eveningtimestart");
                        eend = rs.getString("eveningtimeend");


                        //System.out.println(estart);
                        //System.out.println(eend);

                        filleslottimeinterval();

                    } catch (ClassNotFoundException | SQLException ce)
                    {

                        ce.printStackTrace();
                    }
            }

        private void filleslottimeinterval()
            {
                cbes.removeAllItems();
                cbee.removeAllItems();


                for (int i = 2; i <= 9; i++)
                    cbes.addItem("0" + i + ":00 PM");
                cbes.addItem("10:00 PM");
                // filling all entries of ches

                while (true)
                    {
                        if (!cbes.getItemAt(cbes.getItemCount() - 1).equals(eend))
                            cbes.removeItemAt(cbes.getItemCount() - 1);
                        else
                            break;
                    }

                while (true)
                    {
                        if (!cbes.getItemAt(0).equals(estart))
                            cbes.removeItemAt(0);
                        else
                            break;
                    }


                cbes.removeItemAt(cbes.getItemCount() - 1);
                cbee.addItem(cbes.getItemAt(1));
            }

        private void enablemorning(boolean status)
            {
                cbms.setVisible(status);
                cbme.setVisible(status);
            }

        private void enableevening(boolean status)
            {
                cbes.setVisible(status);
                cbee.setVisible(status);
            }


        private void style()
            {

                Font ftitle = new Font("comic sans", Font.ITALIC + Font.BOLD, 40);
                Border loweredbevel = BorderFactory.createLoweredBevelBorder();
                Border h = BorderFactory.createTitledBorder(loweredbevel, ":: BOOK YOUR DOCTOR ::", TitledBorder.CENTER, TitledBorder.TOP, ftitle, Color.red);
                Border k = BorderFactory.createMatteBorder(0, 10, 0, 0, Color.red);
                P.setBorder(BorderFactory.createCompoundBorder(h, k));

                Color clb = new Color(20, 110, 140);
                lbdoc.setForeground(clb);
                lbpat.setForeground(clb);
                lbslot.setForeground(clb);
                lbday.setForeground(clb);
                lbdate.setForeground(clb);
                txdoctor.setForeground(clb);
                txpatient.setForeground(clb);

                btbook.setForeground(clb);
                btcancel.setForeground(clb);

                Font flb = new Font("comic sans", Font.BOLD, 25);
                lbdoc.setFont(flb);
                lbpat.setFont(flb);
                lbday.setFont(flb);
                lbdate.setFont(flb);
                lbslot.setFont(flb);
                txdoctor.setFont(flb);
                txpatient.setFont(flb);


            }

        @Override
        public void actionPerformed(ActionEvent e)
            {

                Object src = e.getSource();
                if (src == btbook)
                    {
                        if (chyy.getSelectedIndex() == 0
                                || chmm.getSelectedIndex() == 0
                                || chdd.getSelectedIndex() == 0
                                || chday.getSelectedIndex() == 0
                                || !(chmslot.isSelected() || cheslot.isSelected())
                        )
                            JOptionPane.showMessageDialog(null, "Kindly fill all the entries of the form", "Form Unfilled", JOptionPane.ERROR_MESSAGE);
                        else
                            {
                                bookdoctor();
                                this.dispose();
                            }
                    } else if (src == btcancel)
                    {
                        this.dispose();
                    }
            }

        private void bookdoctor()
            {

                try
                    {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  dr" + userd + "Tb( patient varchar(100),"
                                + "dob date ,"
                                + "day varchar(100),"
                                + "slot varchar(100),"
                                + "timestart varchar(100),"
                                + "timeend varchar(100),"
                                + "frequency int,"
                                + "rating int,"
                                + "primary key(patient))");


                        // finding if he has already booked an appointment before
                        PreparedStatement prstmt = con.prepareStatement("select count(*) from dr" + userd + "Tb where patient=?");
                        prstmt.setString(1, userp);
                        ResultSet rs = prstmt.executeQuery();

                        rs.next();
                        int c = rs.getInt(1);
                        if (c == 0) // no booking before is set
                            {
                                PreparedStatement pres = con.prepareStatement("insert into dr" + userd + "Tb values(?,?,?,?,?,?,?,?)");

                                pres.setString(1, userp);

                                int yy = Integer.parseInt(Objects.requireNonNull(chyy.getSelectedItem()).toString());
                                int mm = Integer.parseInt(Objects.requireNonNull(chmm.getSelectedItem()).toString());
                                int dd = Integer.parseInt(Objects.requireNonNull(chdd.getSelectedItem()).toString());
                                Date dob = new Date(yy - 1900, mm - 1, dd);
                                /*
                                 * year the year minus 1900; must be 0 to 8099. (Note that8099 is 9999 minus 1900.)
                                 * month 0 to 11
                                 * day 1 to 31
                                 */
                                pres.setDate(2, dob);
                                pres.setString(3, Objects.requireNonNull(chday.getSelectedItem()).toString());

                                String x = "";
                                if (chmslot.isSelected())
                                    x = "Morning";
                                else if (cheslot.isSelected())
                                    x = "Evening";

                                pres.setString(4, x);

                                if (x.equalsIgnoreCase("Morning"))
                                    {
                                        pres.setString(5, Objects.requireNonNull(cbms.getSelectedItem()).toString());
                                        pres.setString(6, Objects.requireNonNull(cbme.getSelectedItem()).toString());
                                    } else if (x.equalsIgnoreCase("Evening"))
                                    {
                                        pres.setString(5, Objects.requireNonNull(cbes.getSelectedItem()).toString());
                                        pres.setString(6, Objects.requireNonNull(cbee.getSelectedItem()).toString());
                                    }
                                pres.setInt(7, 1); // as its patient first time
                                pres.setInt(8, -1); // at first appointment no rating is done
                                pres.executeUpdate();
                                JOptionPane.showMessageDialog(null, "You have booked appointment for dr " + userd + "", "Thanks for Booking", JOptionPane.PLAIN_MESSAGE);
                                // first booking of that patient
                                int app;
                                int pat;

                                PreparedStatement premain = con.prepareStatement("select patient,appointment from DoctorFinalTb where username=?");
                                premain.setString(1, userd);
                                ResultSet rremain = premain.executeQuery();

                                rremain.next();
                                pat = rremain.getInt("patient");
                                app = rremain.getInt("appointment");

                                // if previously rated
                                PreparedStatement p = con.prepareStatement("update DoctorFinalTb set patient=?,appointment=? where username=?");
                                p.setString(3, userd);
                                p.setInt(1, pat + 1);
                                p.setInt(2, app + 1);
                                p.executeUpdate();
                            } else if (c == 1) // already booked before need to update as it will contain that 1 patient
                            {
                                // getting booking time
                                // updating frequency and rating
                                PreparedStatement p = con.prepareStatement("select frequency from dr" + userd + "Tb where patient=?");
                                p.setString(1, userp);
                                ResultSet r = p.executeQuery();
                                r.next();
                                int freq = r.getInt("frequency"); // for total appointment

                                JOptionPane.showMessageDialog(null, "You have booked appointment for dr " + userd + " " + freq + " times before", "Thanks for Booking Again", JOptionPane.PLAIN_MESSAGE);

                                Object[] s = {"0", "1", "2", "3", "4", "5"};

                                Object doc = JOptionPane.showInputDialog(null, "Please rate dr " + userd + " based upon your previous meeting", "Rating Previous Meeting", JOptionPane.QUESTION_MESSAGE, null, s, 0);
                                int rate;
                                if (doc != null)
                                    {
                                        rate = Integer.parseInt(doc.toString());
                                    } else
                                    rate = -1; // if pressed cancel
                                // dont press cancel

                                freq++; // increasing frequency

                                PreparedStatement pres = con.prepareStatement("update dr" + userd + "Tb set dob=?,day=?,slot=?,timestart=?,timeend=?,frequency=?,rating=? where patient=?");
                                pres.setString(8, userp);

                                pres.setInt(6, freq); // setting new frequency


                                int yy = Integer.parseInt(Objects.requireNonNull(chyy.getSelectedItem()).toString());
                                int mm = Integer.parseInt(Objects.requireNonNull(chmm.getSelectedItem()).toString());
                                int dd = Integer.parseInt(Objects.requireNonNull(chdd.getSelectedItem()).toString());
                                Date dob = new Date(yy - 1900, mm - 1, dd);
                                /*
                                 * year the year minus 1900; must be 0 to 8099. (Note that8099 is 9999 minus 1900.)
                                 * month 0 to 11
                                 * day 1 to 31
                                 */

                                pres.setDate(1, dob);

                                pres.setString(2, Objects.requireNonNull(chday.getSelectedItem()).toString());

                                String x = "";
                                if (chmslot.isSelected())
                                    x = "Morning";
                                else if (cheslot.isSelected())
                                    x = "Evening";

                                if (rate != -1) // change it
                                    pres.setInt(7, rate);

                                else // remain it
                                    {
                                        PreparedStatement prerate = con.prepareStatement("select rating from dr" + userd + "Tb where patient=?");
                                        prerate.setString(1, userp);
                                        ResultSet rsetprerate = prerate.executeQuery();
                                        rsetprerate.next();
                                        int rpre = rsetprerate.getInt(1);

                                        pres.setInt(7, rpre);
                                    }

                                pres.setString(3, x);

                                if (x.equalsIgnoreCase("Morning"))
                                    {
                                        pres.setString(4, Objects.requireNonNull(cbms.getSelectedItem()).toString());
                                        pres.setString(5, Objects.requireNonNull(cbme.getSelectedItem()).toString());
                                    } else if (x.equalsIgnoreCase("Evening"))
                                    {
                                        pres.setString(4, Objects.requireNonNull(cbes.getSelectedItem()).toString());
                                        pres.setString(5, Objects.requireNonNull(cbee.getSelectedItem()).toString());
                                    }

                                pres.executeUpdate();
                                updatefinaltable(); // in doctorfinaltb
                            } else
                            con.close();
                    } catch (ClassNotFoundException | SQLException e)
                    {

                        e.printStackTrace();
                    }
            }

        // final rating will be from that all rating
        // final appointment is from frequency
        private void updatefinaltable()
            {
                try
                    {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");

                        // for final rating
                        PreparedStatement prate = con.prepareStatement("select rating from dr" + userd + "Tb");
                        ResultSet rrate = prate.executeQuery();
                        int no = 0;
                        float finalrate = 0;
                        while (rrate.next())
                            {

                                int rone = (rrate.getInt(1));
                                if (rone != -1)
                                    {
                                        finalrate += rone;
                                        no++;
                                    }
                            }

                        float avgsum = finalrate / no;
                        DecimalFormat df = new DecimalFormat("0.0");

                        // for final appoint
                        PreparedStatement pappoint = con.prepareStatement("select frequency from dr" + userd + "Tb");
                        ResultSet rappoint = pappoint.executeQuery();
                        int finalappoint = 0;
                        while (rappoint.next())
                            {
                                int rone = (rappoint.getInt(1));
                                finalappoint += rone;
                            }

                        // for final patient
                        PreparedStatement prstmt = con.prepareStatement("select count(*) from dr" + userd + "Tb");
                        ResultSet rs = prstmt.executeQuery();
                        rs.next();
                        int finalpatient = rs.getInt(1);

                        PreparedStatement p = con.prepareStatement("update DoctorFinalTb set patient=?,appointment=?,rating=? where username=?");

                        p.setString(4, userd);
                        p.setFloat(3, Float.parseFloat(df.format(avgsum)));
                        p.setInt(2, finalappoint);
                        p.setInt(1, finalpatient);
                        p.executeUpdate();

                        con.close();
                    } catch (ClassNotFoundException | SQLException ce)
                    {

                        ce.printStackTrace();
                    }
            }

        @Override
        public void itemStateChanged(ItemEvent e)
            {

                Object src = e.getSource();
                if (e.getStateChange() == ItemEvent.SELECTED)
                    {

                        if (src == chyy || src == chmm)
                            {
                                chdd.removeItemListener(this);
                                if (chyy.getSelectedIndex() != 0 && chmm.getSelectedIndex() != 0)
                                    {


                                        int yy = Integer.parseInt(Objects.requireNonNull(chyy.getSelectedItem()).toString());
                                        int mm = Integer.parseInt(Objects.requireNonNull(chmm.getSelectedItem()).toString());

                                        // every time it come here we will remove previous entered item
                                        // and then it will add automatically

                                        //  for(int i=1;i<chdd.getItemCount();i++)
                                        // chdd.remove(1);

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
                                chdd.addItemListener(this);

                            } else if (src == chmslot)
                            {
                                cbms.removeItemListener(this);
                                enablemorning(true);
                                enableevening(false);
                                fillmslottime();
                                cbms.addItemListener(this);
                            } else if (src == cheslot)
                            {
                                cbes.removeItemListener(this);
                                enableevening(true);
                                enablemorning(false);
                                filleslottime();
                                cbes.addItemListener(this);
                            } else if (src == cbms)
                            {
                                cbme.removeAllItems();
                                if (cbms.getSelectedIndex() == cbms.getItemCount() - 1)
                                    cbme.addItem(mend);
                                else
                                    cbme.addItem((cbms.getItemAt(cbms.getSelectedIndex() + 1)));

                            } else if (src == cbes)
                            {
                                cbee.removeAllItems();
                                if (cbes.getSelectedIndex() == cbes.getItemCount() - 1)
                                    cbee.addItem(eend);
                                else
                                    cbee.addItem((cbes.getItemAt(cbes.getSelectedIndex() + 1)));

                            }
                    }
            }
    }


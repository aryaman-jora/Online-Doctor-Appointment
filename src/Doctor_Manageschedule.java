import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.Objects;

class Doctor_Manageschedule extends JPanel implements ItemListener, ActionListener
    {
        private final JCheckBox[] ch;
        private final JButton btupdate;
        private final JButton btedit;
        private final JPanel Pmain;
        private String k = "";
        private final JComboBox<String> cbms;
        private final JComboBox<String> cbme;
        private final JComboBox<String> cbes;
        private final JComboBox<String> cbee;
        private final JLabel lbms;
        private final JLabel lbme;
        private final JLabel lbes;
        private final JLabel lbee;
        private final JLabel lbset;
        // ms morning start
        // me morning end
        // es evening start
        // ee evening end
        private final String userd;

        Doctor_Manageschedule(String userd)
            {
                this.userd = userd;

                setVisible(true);
                setSize(700, 700);
                setLayout(new BorderLayout());
                btupdate = new JButton("Update Schedule");
                btedit = new JButton("Edit Schedule");
                JPanel p = new JPanel();
                p.setLayout(new GridLayout(1, 7));
                ch = new JCheckBox[7];
                String[] d = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                for (int i = 0; i < ch.length; i++)
                    {
                        ch[i] = new JCheckBox(d[i]);
                        p.add(ch[i]);
                        ch[i].addItemListener(this);
                    }
                add(p, BorderLayout.NORTH);

                cbms = new JComboBox<>();
                cbme = new JComboBox<>();
                cbes = new JComboBox<>();
                cbee = new JComboBox<>();

                //cbms.addItem("Morning Start Time");
                //cbme.addItem("Morning End Time");
                //cbes.addItem("Evening Start Time");
                //cbee.addItem("Evening End Time");


                // morning hours from 6:00 AM to 1:00 PM
                for (int i = 6; i <= 12; i++)
                    {
                        if (i <= 9)
                            {
                                cbms.addItem("0" + i + ":00 AM");
                                if (i == 9)
                                    cbme.addItem((i + 1) + ":00 AM");
                                else
                                    cbme.addItem("0" + (i + 1) + ":00 AM");
                            } else
                            {
                                cbms.addItem(i + ":00 AM");
                                if (i == 12)
                                    cbme.addItem("01:00 PM");
                                else
                                    cbme.addItem((i + 1) + ":00 AM");
                            }
                    }
                cbme.setSelectedItem("01:00 PM");


                // morning end in item state changed

                // evening hours from 2:00 PM to 10:00 PM
                // evening hours from 2:00 PM to 10:00 PM
                for (int i = 2; i <= 9; i++)
                    {
                        cbes.addItem("0" + i + ":00 PM");
                        if (i == 9)
                            cbee.addItem((i + 1) + ":00 PM");
                        else
                            cbee.addItem("0" + (i + 1) + ":00 PM");
                    }
                // evening end in item state changed
                cbee.setSelectedItem("10:00 PM");

                cbms.addItemListener(this);
                cbes.addItemListener(this);

                lbms = new JLabel("Morning Start");
                lbme = new JLabel("Morning End");
                lbes = new JLabel("Evening Start");
                lbee = new JLabel("Evening End");

                lbset = new JLabel("Select Working Days");
                JPanel pset = new JPanel();
                pset.add(lbset);

                JPanel pm = new JPanel();
                pm.setLayout(new GridLayout(1, 4));
                pm.add(lbms);
                pm.add(cbms);
                pm.add(lbme);
                pm.add(cbme);

                JPanel pe = new JPanel();
                pe.setLayout(new GridLayout(1, 4));
                pe.add(lbes);
                pe.add(cbes);
                pe.add(lbee);
                pe.add(cbee);

                JPanel pbtn = new JPanel();
                pbtn.setLayout(new FlowLayout());
                pbtn.add(btupdate);
                btupdate.setEnabled(false);
                pbtn.add(btedit);

                Pmain = new JPanel();
                Pmain.setLayout(new GridLayout(5, 1, 100, 100));
                Pmain.add(pset);
                Pmain.add(p);
                Pmain.add(pm);
                Pmain.add(pe);
                Pmain.add(pbtn);
                add(Pmain, BorderLayout.CENTER);

                btupdate.addActionListener(this);
                btedit.addActionListener(this);
                style();
                fillform();
                enableall(false); // initialisally all false
            }

        private void enableall(boolean status)
            {
                cbms.setEnabled(status);
                cbme.setEnabled(status);
                cbes.setEnabled(status);
                cbee.setEnabled(status);
                for (JCheckBox jCheckBox : ch)
                    jCheckBox.setEnabled(status);
            }

        @Override
        public void itemStateChanged(ItemEvent e)
            {

                Object src = e.getSource();
                for (JCheckBox checkBox : ch)
                    {
                        if (src == checkBox)
                            {
                                k = "";
                                for (JCheckBox jCheckBox : ch)
                                    k += jCheckBox.isSelected() ? "1" : "0";
                            }
                    }

                if (src == cbms) // morning start will fill morning end
                    {
                        cbme.removeAllItems();

                        int m = cbms.getSelectedIndex();
                        for (int j = m + 1; j < cbms.getItemCount(); j++)
                            cbme.addItem(cbms.getItemAt(j));

                        cbme.setEnabled(true);

                        cbme.addItem("01:00 PM");
                        cbme.setSelectedItem("01:00 PM");
                    } else if (src == cbes) // evening start will fill evening end
                    {
                        cbee.removeAllItems();

                        int m = cbes.getSelectedIndex();
                        for (int j = m + 1; j < cbes.getItemCount(); j++)
                            cbee.addItem(cbes.getItemAt(j));

                        cbee.addItem("10:00 PM");
                        cbee.setSelectedItem("10:00 PM");
                    }

            }

        @Override
        public void actionPerformed(ActionEvent e)
            {

                Object src = e.getSource();
                if (src == btupdate)
                    {

                        if (!(k.equals("0000000") || k.equals("")))
                            {
                                // if we dont set this button disable
                                // if once pressed anything then k = 0000000 otherwise k = ""  !(k.equals("0000000") || k.equals(""))
                                updateform();
                                JOptionPane.showMessageDialog(null, "Your Details have been successfull updated", "Updation Successfull", JOptionPane.PLAIN_MESSAGE);
                                enableall(false);
                                btedit.setEnabled(true);
                                btupdate.setEnabled(false);
                            } else
                            JOptionPane.showMessageDialog(null, "Please fill all the required information.", "Form Incomplete", JOptionPane.ERROR_MESSAGE);
                    } else if (src == btedit)
                    {
                        btupdate.setEnabled(true);
                        btedit.setEnabled(false);
                        enableall(true);
                    }
            }

        private void updateform()
            {
                try
                    {


                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("create database if not exists ManagementDb");
                        stmt.execute("Use ManagementDb");
                        stmt.executeUpdate("create table if not exists  DoctorscheduleTb(username varchar(100),days  varchar(100),morningtimestart varchar(100),morningtimeend varchar(100),eveningtimestart varchar(100),eveningtimeend varchar(100))");


                        PreparedStatement pstmt = con.prepareStatement("update DoctorscheduleTb set days=?,morningtimestart=?,morningtimeend=?,eveningtimestart=?,eveningtimeend=? where username=?");

                        pstmt.setString(1, k);
                        pstmt.setString(2, Objects.requireNonNull(cbms.getSelectedItem()).toString());
                        pstmt.setString(3, Objects.requireNonNull(cbme.getSelectedItem()).toString());
                        pstmt.setString(4, Objects.requireNonNull(cbes.getSelectedItem()).toString());
                        pstmt.setString(5, Objects.requireNonNull(cbee.getSelectedItem()).toString());
                        pstmt.setString(6, userd);
                        pstmt.executeUpdate();


                    } catch (ClassNotFoundException | SQLException ce)
                    {

                        ce.printStackTrace();
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
                        stmt.executeUpdate("create table if not exists  DoctorscheduleTb(username varchar(100),days  varchar(100),morningtimestart varchar(100),morningtimeend varchar(100),eveningtimestart varchar(100),eveningtimeend varchar(100))");


                        PreparedStatement pstmt = con.prepareStatement("select days,morningtimestart,morningtimeend,eveningtimestart,eveningtimeend from DoctorscheduleTb where username=?");

                        pstmt.setString(1, userd);
                        // there is only one user
                        ResultSet rs = pstmt.executeQuery();
                        rs.next();

                        String d = rs.getString("days");
                        for (int i = 0; i < d.length(); i++)
                            {
                                if (d.charAt(i) == '1')
                                    ch[i].setSelected(true);
                            }

                        cbms.setSelectedItem(rs.getString("morningtimestart"));
                        cbme.setSelectedItem(rs.getString("morningtimeend"));
                        cbes.setSelectedItem(rs.getString("eveningtimestart"));
                        cbee.setSelectedItem(rs.getString("eveningtimeend"));


                    } catch (ClassNotFoundException | SQLException ce)
                    {

                        ce.printStackTrace();
                    }
            }

        private void style()
            {
                Font ftitle = new Font("comic sans", Font.ITALIC + Font.BOLD, 40);
                Border loweredbevel = BorderFactory.createLoweredBevelBorder();
                Border h = BorderFactory.createTitledBorder(loweredbevel, ":: DOCTOR SCHEDULE ::", TitledBorder.CENTER, TitledBorder.TOP, ftitle, Color.red);
                Border k = BorderFactory.createMatteBorder(0, 10, 0, 0, Color.red);
                Pmain.setBorder(BorderFactory.createCompoundBorder(h, k));

                Font flb = new Font("comic sans", Font.ITALIC + Font.BOLD, 20);
                lbset.setFont(flb);
                lbms.setFont(flb);
                lbme.setFont(flb);
                lbes.setFont(flb);
                lbee.setFont(flb);

                Color clb = new Color(20, 110, 140);
                lbset.setForeground(clb);
                lbms.setForeground(clb);
                lbme.setForeground(clb);
                lbes.setForeground(clb);
                lbee.setForeground(clb);
                btupdate.setForeground(clb);
                btedit.setForeground(clb);

            }

    }

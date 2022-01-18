import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class TodayDate extends JPanel implements ActionListener
    {


        private int c = 0;
        private final JPanel P;
        private final JTable tb;
        private final JButton bttoday;
        private final JButton btall;
        private boolean condition;

        public TodayDate(String userd)
            {
                condition = false;
                // convert them into label
                String need = "*";
                P = new JPanel();


                JPanel pbtn = new JPanel();
                bttoday = new JButton("Today");
                btall = new JButton("All");
                pbtn.add(bttoday);
                pbtn.add(btall);

                P.add(pbtn, BorderLayout.NORTH);


                bttoday.addActionListener(this);
                btall.addActionListener(this);

                Object[] col = {"Patient Name", "Appointment Date", "Appointment Day", "Slot", "Timimg", "Previous Visits", "Rating"};
                try
                    {
                        Class.forName("com.mysql.jdbc.Driver"); // imported external jar mysql JConnector

                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

                        // server and default mysql port address , username , password

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
                                + "rating varchar(100),"
                                + "primary key(patient))");


                        // finding if he has already booked an appointment before
                        PreparedStatement prstmt;
                        if (condition)

                            prstmt = con.prepareStatement("select count(*) from dr" + userd + "Tb");
                        else
                            {
                                LocalDate localDate = LocalDate.now();
                                String today = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
                                prstmt = con.prepareStatement("select count(*) from dr" + userd + "Tb where dob = ? ");
                                prstmt.setString(1, today);
                            }
                        ResultSet rs = prstmt.executeQuery();
                        rs.next();
                        c = rs.getInt(1);


                        // it will give all the patient and one patient is only once no use od distint
                        //System.out.println("Number of records: "+ c);
                    } catch (SQLException | ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }

                Object[][] row = new Object[c][7];

                try
                    {
                        Class.forName("com.mysql.jdbc.Driver"); // imported external jar mysql JConnector


                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

                        // server and default mysql port address , username , password

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
                                + "rating varchar(100),"
                                + "primary key(patient))");


                        // finding if he has already booked an appointment before
                        PreparedStatement prstmt;
                        if (condition)
                            {
                                prstmt = con.prepareStatement("select * from dr" + userd + "Tb");

                            } else
                            {
                                LocalDate localDate = LocalDate.now();
                                String today = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
                                prstmt = con.prepareStatement("select * from dr" + userd + "Tb where dob = ? ");
                                prstmt.setString(1, today);

                            }
                        ResultSet rs = prstmt.executeQuery();
                        rs.next();
                        c = rs.getInt(1);


                        for (int i = 0; i < c && rs.next(); i++)
                            {
                                row[i][0] = rs.getString("patient");
                                row[i][1] = rs.getDate("dob");
                                row[i][2] = rs.getString("day");
                                row[i][3] = rs.getString("slot");

                                row[i][4] = rs.getString("timestart") + " - " + rs.getString("timestart");

                                row[i][5] = rs.getInt("frequency");
                                row[i][6] = rs.getString("rating");

                            }

                        con.close();

                    } catch (SQLException | ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }


                tb = new JTable(row, col);
                JScrollPane jsp = new JScrollPane(tb);
                P.setLayout(new BorderLayout());
                P.add(jsp, BorderLayout.CENTER);
                setLayout(new BorderLayout());
                add(P, BorderLayout.CENTER);

                style();
                validate();
            }

        private void style()
            {
                Font ftitle = new Font("comic sans", Font.ITALIC + Font.BOLD, 40);
                Border loweredbevel = BorderFactory.createLoweredBevelBorder();
                Border h = BorderFactory.createTitledBorder(loweredbevel, ":: PATIENTS ::", TitledBorder.CENTER, TitledBorder.TOP, ftitle, Color.red);
                Border k = BorderFactory.createMatteBorder(0, 10, 0, 0, Color.red);
                P.setBorder(BorderFactory.createCompoundBorder(h, k));

                Color ctable = new Color(20, 110, 140);
                Font ftable_head = new Font(null, Font.BOLD, 20);
                JTableHeader header = tb.getTableHeader();
                header.setBackground(ctable);
                header.setFont(ftable_head);
                header.setForeground(Color.white);
                header.setPreferredSize(new Dimension(getSize().width, 100));
                ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

                tb.setForeground(ctable);
                Font ftable = new Font("comic sans", Font.BOLD, 12);
                tb.setFont(ftable);
                //JTable.CENTER_ALIGNMENT
                tb.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


                TableColumn column = null;
                for (int i = 0; i < 7; i++)
                    {
                        column = tb.getColumnModel().getColumn(i);
                        if (i == 1 || i == 5)
                            {
                                column.setPreferredWidth(100);
                            } else
                            {
                                column.setPreferredWidth(50);
                            }

                        TableRow rows = null;
                        for (int r = 0; r < c; r++)
                            {
                                tb.setRowHeight(r, 50);
                            }
                        tb.setEnabled(false);
                    }
            }

        @Override
        public void actionPerformed(ActionEvent e)
            {

                Object src = e.getSource();

                if (src == btall)
                    {
                        condition = false;
                    } else if (src == bttoday)
                    {

                        condition = true;
                    }
            }
    }



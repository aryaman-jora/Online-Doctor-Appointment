import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;
import java.awt.*;
import java.sql.*;

class AdminShowAllPatient extends JPanel
    {

        private final JTable tb;
        private final JPanel P;

        private int c = 0;

        AdminShowAllPatient()
            {

                // convert them into label
                String need = "*";
                P = new JPanel();
                JPanel ps = new JPanel();
                Object[] col = {"UserName" + need, "Password" + need, "Email" + need, "PhoneNumber" + need, "Gender" + need, "Material" + need, "DOB" + need, "Location" + need, "Address" + need, "City" + need, "State" + need, "Diseases!", "PastRecords"};
                try
                    {
                        Class.forName("com.mysql.jdbc.Driver"); // imported external jar mysql JConnector

                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

                        // server and default mysql port address , username , password

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

                        ResultSet rs = stmt.executeQuery("select count(*) from patienttb");
                        rs.next();

                        c = rs.getInt(1);
                        //System.out.println("Number of records: "+ c);
                    } catch (SQLException | ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }

                Object[][] row = new Object[c][13];

                try
                    {
                        Class.forName("com.mysql.jdbc.Driver"); // imported external jar mysql JConnector


                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

                        // server and default mysql port address , username , password

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

                        ResultSet rs = stmt.executeQuery("select * from patienttb");


                        for (int i = 0; i < c && rs.next(); i++)
                            {
                                row[i][0] = rs.getString("username");
                                row[i][1] = rs.getString("password");
                                row[i][2] = rs.getString("email");
                                row[i][3] = rs.getString("phone");

                                int g = rs.getInt("gender");
                                if (g == 1)
                                    row[i][4] = "Male";
                                else if (g == 0)
                                    row[i][4] = "Female";

                                int m = rs.getInt("material");
                                if (m == 1)
                                    row[i][5] = "Single";
                                else if (m == 2)
                                    row[i][5] = "Married";

                                row[i][6] = rs.getString("dob");

                                row[i][7] = rs.getString("location");
                                row[i][8] = rs.getString("address");

                                row[i][9] = rs.getString("city");
                                row[i][10] = rs.getString("state");

                                row[i][11] = rs.getString("disease");
                                row[i][12] = rs.getString("past");
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
                ps.setLayout(new GridLayout(2, 1));
                JLabel lb1, lb2;
                lb1 = new JLabel("         :: Here 1001100100 stands for Diabetes,High Blood Pressure,Respiratory Diseases,Heart Diseases,Digestive Diseases,High Cholesterol ,Stroke,Cancer,Alzheimer???s disease ::");
                lb2 = new JLabel("         :: 0 for Disease Absence And 1 for Disease Presence ::");

                ps.add(lb1);
                ps.add(lb2);
                add(ps, BorderLayout.SOUTH);
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
                for (int i = 0; i < 13; i++)
                    {
                        column = tb.getColumnModel().getColumn(i);
                        if (i == 2 || i == 7 || i == 12)
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

    }

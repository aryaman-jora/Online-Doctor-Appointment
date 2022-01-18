import java.sql.*;

class StatesDatabase
    {

        // Make it main in case of creating states database
        public StatesDatabase()
            {
                try
                    {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Statement istmt = con.createStatement();
                        istmt.executeUpdate("create database if not exists state");
                        istmt.execute("Use state");
                        // table exist already from file cities.sql
                        ResultSet irs = istmt.executeQuery("select count(distinct city_state) as count from cities");
                        irs.next();
                        int c = irs.getInt("count");

                        String[] originalstates = new String[c];
                        String[] manipulatestates = new String[c];

                        irs = istmt.executeQuery("select distinct city_state from cities");
                        int i = 0;
                        while (irs.next())
                            {
                                String state_nm = irs.getString("city_state");
                                originalstates[i] = state_nm;
                                state_nm = state_nm.replace(" ", "_");
                                state_nm = state_nm.replace("&", "and");
                                manipulatestates[i] = state_nm;
                                i++;
                            }

                        for (int j = 0; j < c; j++)
                            {
                                System.out.println(originalstates[j]);
                                System.out.println(manipulatestates[j]);
                            }


                        for (int j = 0; j < c; j++)
                            {
                                PreparedStatement ipstmt = con.prepareStatement("select distinct city_name from cities where city_state=?");
                                ipstmt.setString(1, originalstates[j]);

                                ResultSet frs = ipstmt.executeQuery(); // Cities of particular state
                                // No Need of state database
                                Statement fstmt = con.createStatement();
                                fstmt.executeUpdate("create database if not exists states");
                                fstmt.execute("Use states");
                                fstmt.executeUpdate("create table if not exists " + manipulatestates[j] + "Tb(cities_names varchar(150) primary key)");
                                while (frs.next()) // Every city of particular state
                                    {
                                        PreparedStatement fpstmt = con.prepareStatement("insert into " + manipulatestates[j] + "Tb(cities_names) values(?)");
                                        fpstmt.setString(1, frs.getString("city_name")); // Using previous result set
                                        fpstmt.executeUpdate();
                                    }
                                // Every iteration one city is inserted to that particular state table

                                fstmt.execute("Use state"); // Again reverting previous database
                            }

                        con.close();

                    } catch (ClassNotFoundException | SQLException ae)
                    {

                        ae.printStackTrace();
                    }

            }
    }

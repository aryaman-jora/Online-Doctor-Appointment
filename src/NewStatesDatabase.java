import java.sql.*;


class NewStatesDatabase
    {

        public NewStatesDatabase()
            {
                try
                    {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection icon = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
                        Connection fcon = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

                        Statement istmt = icon.createStatement();
                        istmt.executeUpdate("create database if not exists state");
                        istmt.execute("use state");
                        Statement fstmt = fcon.createStatement();
                        fstmt.executeUpdate("create database if not exists states");
                        fstmt.execute("use states");

                        ResultSet irs = istmt.executeQuery("select distinct city_state from cities");
                        while (irs.next())
                            {
                                String state_nm = irs.getString("city_state");
                                System.out.println(state_nm);
                                PreparedStatement ipstmt = icon.prepareStatement("select distinct city_name from cities where city_state = ?");
                                ipstmt.setString(1, state_nm);
                                ResultSet frs = ipstmt.executeQuery();

                                state_nm = state_nm.replace(" ", "_");
                                state_nm = state_nm.replace("&", "and");

                                fstmt.execute("create table if not exists " + state_nm + "tb(cities_names varchar(150) primary key)");

                                while (frs.next())
                                    {
                                        PreparedStatement fpstmt = fcon.prepareStatement("insert into " + state_nm + "tb values(?)");
                                        fpstmt.setString(1, frs.getString("city_name"));
                                        System.out.println("\t" + frs.getString("city_name"));
                                        fpstmt.execute();
                                    }
                            }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            }
    }

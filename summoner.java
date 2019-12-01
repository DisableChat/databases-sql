/******************************************************************************
 * File:        lol.py
 * Author:      Wesley Ryder
 * Description: Databases project - prime machines + oracleplus
 ******************************************************************************
 */

import java.sql.*; 
import java.io.*; 

class summoner {

    void print_menu() {
        System.out.println("\n      LEAGUE OF LEGENDS\n" +
                           "=========================================\n");
        System.out.println("(p) Display Summoners!!! (NA = Garbage)"    );
        System.out.println("(1) Add Summoner"                           );
        System.out.println("(d) Remove Summoner (Tribunal Goodbye XD)"  );
        System.out.println("(2) Add Rune Page"                          );
        System.out.println("(3) Add Play"                               );
        System.out.println("(4) Update Summoner W/L"                    );
        System.out.println("(5) Show Challenger Players and champion's" );
        System.out.println("(6) Show Champion's by Possition"           );
        System.out.println("(q) Quit\n"                                 );
    }

    void add_summoner(Connection conn) 
        throws SQLException, IOException {

        String query = " insert into summoner (Summoner_name, MMR, Division,"
                        + " Honor_level, Mastery_score, wins, losses)"
                        + " values (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(query);
        
        String sname    = readEntry("Summoner Name: ");
        String mmr      = readEntry("MMR: ");
        String div      = readEntry("Division: ");
        String honor    = readEntry("Honor Level: ");
        String mastery  = readEntry("Mastery Level: ");
        String wins     = readEntry("Wins: ");
        String losses   = readEntry("Losses: ");

        // create the mysql insert preparedstatement
        stmt.setString  (1, sname);
        stmt.setInt     (2, Integer.parseInt(mmr));
        stmt.setString  (3, div);
        stmt.setInt     (4, Integer.parseInt(honor));
        stmt.setInt     (5, Integer.parseInt(mastery));
        stmt.setInt     (6, Integer.parseInt(wins));
        stmt.setInt     (7, Integer.parseInt(losses));

        try {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error Adding Summoner Entry");
            while (e != null) {
                System.out.println("Message     : " + e.getMessage());
                e = e.getNextException();
            }
            return;
        }
        stmt.close();
        System.out.println("\nAdded " + sname + " Summoner Entry!\n");
    }

    void add_rune_page(Connection conn) 
        throws SQLException, IOException {

        String query = " insert into rune_page (Rune_id_no, name, keystone,"
                            + " adapt_keystone, Sname)"
                            + " values (?, ?, ?, ?, ?)";

        PreparedStatement stmt  = conn.prepareStatement(query);

        String Rune_id_no       = readEntry("Rune_id_no.: ");
        String name             = readEntry("name (of pg): ");
        String keystone         = readEntry("Keystone: ");
        String adapt_keystone   = readEntry("adapt_keystone: ");
        String Sname            = readEntry("Summoner Name: ");

        // create the mysql insert preparedstatement
        stmt.setString  (1, Rune_id_no);
        stmt.setString  (2, name);
        stmt.setString  (3, keystone);
        stmt.setString  (4, adapt_keystone);
        stmt.setString  (5, Sname);

        try {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error Adding Rune Page Entry");
            while (e != null) {
                System.out.println("Message     : " + e.getMessage());
                e = e.getNextException();
            }
            return;
        }
        stmt.close();
        System.out.println("\n" + Rune_id_no + " Rune page was added!");
    }

    void add_play(Connection conn) 
        throws SQLException, IOException {

        String query = " insert into play (Summoner_n, Champion_n)"
                            + " values (?, ?)";

        PreparedStatement stmt = conn.prepareStatement(query);

        String Summoner_n = readEntry("Summoner Name: ");
        String Champion_n = readEntry("Name of Champ: ");

        // create the mysql insert preparedstatement
        stmt.setString (1, Summoner_n);
        stmt.setString (2, Champion_n);

        try {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding summoner champ pool Entry");
            while (e != null) {
                System.out.println("Message     : " + e.getMessage());
                e = e.getNextException();
            }
            return;
        }
        stmt.close();
        System.out.println("\n" + Champion_n + " was added to" + Summoner_n + "'s champ-pool!");
    }

    void show_chally(Connection conn) 
        throws SQLException, IOException {

        String query = " select Summoner_name, Champion_n, Mastery_score "
                        + " from summoner, riot_reward, play"
                        + " where Division = 'Challenger' AND Summoner_name = Summoner_n"
                        + " AND Summoner_name = SumName";

        String query1 = " select Champion_name, possition, role "
                        + " from champion, summoner, play"
                        + " where Division = 'Challenger' AND Champion_name = Champion_n"
                        + " AND Summoner_name = Summoner_n";

        String query2 = " select Name_of_champ, Rune_id_no, Sname, keystone, adapt_keystone"
                        + " from summoner, play, equip, rune_page"
                        + " where Division = 'Challenger'"
                        + " AND Name_of_champ = Champion_n AND Sname = Summoner_n"
                        + " AND Summoner_name = Summoner_n AND rune_id_num = RUne_id_no";

        String query_space   = "15";
        String query_header  = "\nChallenger Players Picks & Mastery Score\n"
                                + "=========================================\n";

        String query1_space  = "10";
        String query1_header = "\nCorrosponding Champs Posstions & Roles\n"
                                + "=========================================\n";

        String query2_space  = "13";
        String query2_header =  "\nCorrosponding Player's Champion Runepages, ID,"
                                + " Primary & secondary keystones!\n"
                                + "========================================="
                                + "=======================================\n";

        Statement stmt = conn.createStatement ();

        print_chally_report(stmt, query,  query_header,  query_space);
        print_chally_report(stmt, query1, query1_header, query1_space); 
        print_chally_report(stmt, query2, query2_header, query2_space);

    }

    void update_summoner(Connection conn)
        throws SQLException, IOException {

            String query = "update summoner set wins=?, losses=?"
                            + "where Summoner_name=?"; 


            PreparedStatement stmt = conn.prepareStatement(query);

            String Summoner_name = readEntry("Summoner Name: ");
            String wins          = readEntry("# of win total: ");
            String losses        = readEntry("# of losses total: ");

            stmt.setInt     (1, Integer.parseInt(wins));
            stmt.setInt     (2, Integer.parseInt(losses));
            stmt.setString  (3, Summoner_name);

            // create the mysql insert preparedstatement
            try {
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error");
                while (e != null) {
                    System.out.println("Message     : " + e.getMessage());
                    e = e.getNextException();
                }
                return;
            }
            stmt.close();
            System.out.println("\n" + Summoner_name + "'s wins/losses were updated!");
        }


    void remove_summoner(Connection conn) 
        throws SQLException, IOException {

        String Summoner_n = readEntry("Summoner Name *DELETING*: ");

        String query  = " delete from rune_page where Sname='"          + Summoner_n + "'";
        String query1 = " delete from summoner where Summoner_name='"   + Summoner_n +"'";
        String query2 = " delete from play  where Summoner_n='"         + Summoner_n +"'";
        String query3 = " delete from riot_reward where SumName='"      + Summoner_n +"'";

        conn.setAutoCommit(false);
        Statement stmt = conn.createStatement (); 
        int rows;

        try {
            rows = stmt.executeUpdate(query);
            rows = stmt.executeUpdate(query1);
            rows = stmt.executeUpdate(query2);
            rows = stmt.executeUpdate(query3);
        } catch (SQLException e) {
            System.out.println("Error Deleting Summoner");
            while (e != null) {
                System.out.println("Message     : " + e.getMessage());
                e = e.getNextException();
            }
            return;
        }
        conn.commit();
        conn.setAutoCommit(true); 
        stmt.close();
        System.out.println("\n" + Summoner_n + " Was Permabanned from the database!");
    }

    void display_summoners(Connection conn) 
        throws SQLException, IOException {

        String query = " select * from summoner";

        Statement stmt = conn.createStatement ();

        ResultSet rset0;

        try {
            rset0 = stmt.executeQuery(query);
        
            System.out.println("\nLeague Of Legends NA (AKA Garbage) Summonor's\n"
                                + "================================================="
                                + "=================================================\n");

            // Handles problems with various variable types in printing result
            if (rset0 != null) {
                ResultSetMetaData tmp = rset0.getMetaData();
                for (int i = 1; i <= tmp.getColumnCount(); i++){
                    System.out.printf("%-15s", tmp.getColumnName(i));
                }
                System.out.println("\n================================================="
                                 + "=================================================\n");

                while (rset0.next()) {
                    ResultSetMetaData rsmd = rset0.getMetaData();
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        if (i > 1) {
                        System.out.print(" ");
                        }

                        int type = rsmd.getColumnType(i);
                        if (type == Types.VARCHAR || type == Types.CHAR) {
                            System.out.printf("%-14s", rset0.getString(i));
                        } else {
                           System.out.printf("%-14d",rset0.getLong(i));
                        }
                    }

                    System.out.println();
                }
            }
        } catch (SQLException e) {
                System.out.println("Problem reading Summoners Tables Boiiii");
            while (e != null) {
                System.out.println("Message     : " + e.getMessage());
                e = e.getNextException();
            }
             return;
        }
    }

    void show_champ_by_role(Connection conn) 
        throws SQLException, IOException {

        String top = "TOP";
        String jng = "JNG";
        String mid = "MID";
        String adc = "ADC";
        String sup = "SUP";

        Statement stmt = conn.createStatement ();
         
        String space = "15";
        print_champ_by_possition_report(stmt, top, space); 
        print_champ_by_possition_report(stmt, jng, space); 
        print_champ_by_possition_report(stmt, mid, space); 
        print_champ_by_possition_report(stmt, adc, space); 
        print_champ_by_possition_report(stmt, sup, space); 
    }

    void print_champ_by_possition_report(Statement stmt, String role, String space) {

        System.out.println( "\n            "+ role + "\n"
                            + "==============================");

        String query = " select Champion_name, role"
                     + " from champion where possition = '" + role + "'";
        try {
            ResultSet rset0 = stmt.executeQuery(query);
        
            ResultSetMetaData rsmd = rset0.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rset0.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(" ");
                        String columnValue = rset0.getString(i);
                            System.out.printf("%-"+space+"s", columnValue);
                }
                System.out.println("");
            }
        } catch (SQLException e) {
            System.out.println("Problem reading Summoners Tables Boiiii");
            while (e != null) {
                System.out.println("Message     : " + e.getMessage());
                e = e.getNextException();
            }
            return;
        }

    }

    void print_chally_report(Statement stmt, String query,
                             String header, String space) {

        try {
            ResultSet rset = stmt.executeQuery(query);
        
            ResultSetMetaData rsmd = rset.getMetaData();
            System.out.println(header);

            int columnsNumber = rsmd.getColumnCount();
            while (rset.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(" ");
                        String columnValue = rset.getString(i);
                            System.out.printf("%-"+space+"s", columnValue);
                }
                System.out.println("");
            }
        } catch (SQLException e) {
            System.out.println("Problem reading Summoners Tables Boiiii");
            while (e != null) {
                System.out.println("Message     : " + e.getMessage());
                e = e.getNextException();
            }
            return;
        }

    }

    //readEntry function -- to read input string
    static String readEntry(String prompt) {
    try {
        StringBuffer buffer = new StringBuffer();
        System.out.print(prompt);
        System.out.flush();
        int c = System.in.read();
        while(c != '\n' && c != -1) {
            buffer.append((char)c);
            c = System.in.read();
        }
        return buffer.toString().trim();
        } catch (IOException e) {
        return "";
        }
    }
} 

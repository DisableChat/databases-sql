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
        System.out.println("(1) Add Summoner"                           );
        System.out.println("(d) Remove Summoner (Tribunal Goodbye XD)"  );
        System.out.println("(2) Add Rune Page"                          );
        System.out.println("(3) Add Play"                               );
        System.out.println("(4) Show Challenger Players and champion's" );
        System.out.println("(5) Update Summoner W/L"                    );
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

        // Dude make a function for the repeat print statments *TODO* rip...
        Statement stmt = conn.createStatement ();

        ResultSet rset0;
        ResultSet rset1;
        ResultSet rset2;

        try {
            rset0 = stmt.executeQuery(query);
        
            ResultSetMetaData rsmd = rset0.getMetaData();
            System.out.println("\nChallenger Players Picks & Mastery Score\n"
                                + "=========================================\n");
            int columnsNumber = rsmd.getColumnCount();
            while (rset0.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(" ");
                        String columnValue = rset0.getString(i);
                            System.out.printf("%-15s", columnValue);
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
        
        try {
            rset1 = stmt.executeQuery(query1);
        
            ResultSetMetaData rsmd1 = rset1.getMetaData();
            System.out.println("\nCorrosponding Champs Posstions & Roles\n"
                                + "=========================================\n");
            int columnsNumber = rsmd1.getColumnCount();
            while (rset1.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(" ");
                        String columnValue = rset1.getString(i);
                            System.out.printf("%-10s", columnValue);
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

        try {
            rset2 = stmt.executeQuery(query2);
        
            ResultSetMetaData rsmd = rset2.getMetaData();
            System.out.println("\nCorrosponding Player's Champion Runepages, ID,"
                                + " Primary & secondary keystones!\n"
                                + "========================================="
                                + "=======================================\n");
            int columnsNumber = rsmd.getColumnCount();
            while (rset2.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(" ");
                        String columnValue = rset2.getString(i);
                            System.out.printf("%-13s", columnValue);
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

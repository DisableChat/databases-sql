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
                           "==============================\n"           );
        System.out.println("(1) Add Summoner"                           );
        System.out.println("(d) Remove Summoner (Tribunal Goodbye XD)"  );
        System.out.println("(2) Add Rune Page"                          );
        System.out.println("(3) Add Play"                               );
        System.out.println("(4) Select Champ Pool"                      );
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

    void select_summoner(Connection conn) 
        throws SQLException, IOException {
        }

    void update_summoner(Connection conn)
        throws SQLException, IOException {

            String query = "update summoner set wins=?, losses=?"
                            + "where Summoner_name=?"; 


            PreparedStatement stmt = conn.prepareStatement(query);

            String Summoner_name = readEntry("Summoner Name: ");
            String wins = readEntry("# of win total: ");
            String losses = readEntry("# of losses total: ");

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
        //PreparedStatement stmt = conn.prepareStatement(query);

        //stmt.setString (1, Summoner_n);
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

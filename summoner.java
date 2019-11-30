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
        System.out.println("\n      LEAGUE OF LEGENDS\n"
                            + "==============================\n");
        System.out.println("(1) Add Summoner");
        System.out.println("(2) Add Rune Page");
        System.out.println("(3) Add Play");
        System.out.println("(4) Select Champ Pool");
        System.out.println("(5) Update Summoner W/L");
        System.out.println("(q) Quit\n");
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
            /*String query = " update summoner set wins = '" + Integer.parseInt(wins)
                            + "', losses = '" + Integer.parseInt(losses)
                            + "', where summoner.Summoner_name = '"
                            + Summoner_name + "'"; 
            */
        
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
      /*  

      void select_course(Connection conn) 
        throws SQLException, IOException {

        String query1 = "select distinct lineno,courses.cno,ctitle " +
                        "from courses,catalog " +
                        "where courses.cno = catalog.cno and term = '";
        String query;
        String term_in = readEntry("Term: ");
        query = query1 + term_in + "'";
         
        Statement stmt = conn.createStatement (); 
        ResultSet rset = stmt.executeQuery(query);
        System.out.println("");
        while (rset.next ()) { 
          System.out.println(rset.getString(1) + "   " +
                             rset.getString(2) + "   " +
                             rset.getString(3));
        } 
        System.out.println("");
        String ls = readEntry("Select a course line number: ");
        
        grade2 g2 = new grade2();
        boolean done;
        char ch,ch1;

        done = false;
        do {
          g2.print_menu();
          System.out.print("Type in your option:");
          System.out.flush();
          ch = (char) System.in.read();
          ch1 = (char) System.in.read();
          switch (ch) {
            case '1' : g2.add_enrolls(conn,term_in,ls);
                       break;
            case '2' : g2.add_course_component(conn,term_in,ls);
                       break;
            case '3' : g2.add_scores(conn,term_in,ls);
                       break;
            case '4' : g2.modify_score(conn,term_in,ls);
                       break;
            case '5' : g2.drop_student(conn,term_in,ls);
                       break;
            case '6' : g2.print_report(conn,term_in,ls);
                       break;
            case 'q' : done = true;
                       break;
            default  : System.out.println("Type in option again");
          }
        } while (!done);

      }
      */
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

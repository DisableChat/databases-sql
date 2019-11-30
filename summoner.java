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
    System.out.println("      LEAGUE OF LEGENDS\n");
    System.out.println("(1) Add Summoner");
    System.out.println("(2) Add Rune Page");
    System.out.println("(3) Add Play");
    System.out.println("(4) Select Champ Pool");
    System.out.println("(q) Quit\n");
  }

  void add_summoner(Connection conn) 
    throws SQLException, IOException {
 
    String query = " insert into summoner (Summoner_name, MMR, Division,"
                        + " Honor_level, Mastery_score, wins, losses)"
                        + " values (?, ?, ?, ?, ?, ?, ?)";

    PreparedStatement stmt = conn.prepareStatement(query);
    //Statement stmt  = conn.createStatement();
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
    System.out.println("Added Summoner Entry");
  }
  
    void add_rune_page(Connection conn) 
        throws SQLException, IOException {
        }

    void add_play(Connection conn) 
        throws SQLException, IOException {
        }

    void select_summoner(Connection conn) 
        throws SQLException, IOException {
        }

  /*  
  void add_course(Connection conn) 
        throws SQLException, IOException {

    String term_in = readEntry("Term         : ");
    String ls      = readEntry("Line Number  : ");
    String cnum    = readEntry("Course Number: ");
    String as      = readEntry("A Cutoff     : ");
    String bs      = readEntry("B Cutoff     : ");
    String cs      = readEntry("C Cutoff     : ");
    String ds      = readEntry("D Cutoff     : ");

    String query = "insert into courses values (" +
            "'" + term_in + "'," + ls + "," + 
            "'" + cnum + "'," + as + "," + 
            bs + "," + cs + "," + ds + ")";
           
    Statement stmt = conn.createStatement (); 
    try {
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.out.println("Course was not added! Failure!");
      return;
    }
    System.out.println("Course was added! Success!");
    stmt.close();
  }

  void add_students(Connection conn) 
      throws SQLException, IOException {

    String id, ln, fn, mi;
    PreparedStatement stmt = conn.prepareStatement(
      "insert into students values (?, ?, ?, ?)"  ); 
    do {
      id = readEntry("ID (0 to stop): ");
      if (id.equals("0"))
        break;
      ln = readEntry("Last  Name    : ");
      fn = readEntry("First Name    : ");
      mi = readEntry("Middle Initial: ");
      try {
        stmt.setString(1,id);
        stmt.setString(2,fn);
        stmt.setString(3,ln);
        stmt.setString(4,mi);
        stmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println("Student was not added! Error!");
      }
    } while (true);  
    stmt.close();
  }

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

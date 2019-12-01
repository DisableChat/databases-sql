/******************************************************************************
 * File:        lol.py
 * Author:      Wesley Ryder
 * Description: Databases project - prime machines + oracleplus
 ******************************************************************************
 */

import java.io.*; 
import java.sql.*;

class lol { 

  public static void main (String args []) 
      throws SQLException, IOException { 

    summoner s1 = new summoner();
    boolean done;
    char ch,ch1;
    byte num = 0;

    try {
      Class.forName ("oracle.jdbc.driver.OracleDriver");
    } catch (ClassNotFoundException e) {
        System.out.println ("Could not load the driver");
        return;
      }
    String user, pass;
    user = readEntry("userid  : ");
    pass = readEntry("password: ");

    //  The following line was modified by Prof. Marling to work on prime

    Connection conn = DriverManager.getConnection
       ("jdbc:oracle:thin:@deuce.cs.ohio.edu:1521:class", user, pass);

    done = false;
    do {
      s1.print_menu();
      System.out.print("Type in your option:");
      System.out.flush();
      ch = (char) System.in.read();
      ch1 = (char) System.in.read();
      switch (ch) {
        case '1' : s1.add_summoner(conn);
                   break;
        case '2' : s1.add_rune_page(conn);
                   break;
        case '3' : s1.add_play(conn);
                   break;
        case '4' : s1.show_chally(conn);
                   break;
        case '5' : s1.update_summoner(conn);
                   break;
        case 'q' : done = true;
                   break;
        case 'd' : s1.remove_summoner(conn);
                   break;
        default  : System.out.println("Type in option again");
      }
    } while (!done);

    conn.close();
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

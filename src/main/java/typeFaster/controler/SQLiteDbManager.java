/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package typeFaster.controler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author M_Zahi
 */
public class SQLiteDbManager {

    public static Connection connection() {
        Connection con = null;
        try {
            System.out.println("connection");
            Class.forName("org.sqlite.JDBC");
            System.out.println("connection1");
            con = DriverManager.getConnection("jdbc:sqlite::resource:TP.db");
            System.out.println("connection2");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("sdfsdfsdfsdfsdf");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return con;
    }

    public static String getWord(String lang) {
        String query = "";
        System.out.println("getWord");
        if (lang.equals("AR")) {
            query = "SELECT text FROM ar_words ORDER BY RANDOM() LIMIT 1";
        } else if (lang.equals("FR")) {
            query = "SELECT text FROM fr_words ORDER BY RANDOM() LIMIT 1";
        } else if (lang.equals("EN")) {
            query = "SELECT text FROM en_words ORDER BY RANDOM() LIMIT 1";
        }
        Connection con = null;
        Statement stmt = null;
        con = connection();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String word = rs.getString("text");
                return word;
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteDbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static void storePlayerScore(String nom, int score, String lang) {
        Connection con;
        Statement stmt;
        String query = "insert into player(name,score,lang) values('" + nom + "'," + score + ",'" + lang + "')";
        con = connection();
        try {
            stmt = con.createStatement();
            boolean bool = stmt.execute(query);
            if (bool == false) {
                System.out.println("player inserted");
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteDbManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String[][] top10(String lang) {
        String[][] scores = new String[10][3];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                scores[i][j] = "";
            }
        }
        Connection con;
        Statement stmt;
        String query = "Select * from player where lang='" + lang + "' ORDER BY score DESC limit 10";
        con = connection();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                String name = rs.getString("name");
                String score = rs.getString("score");
                System.out.println(name + ":" + score);
                scores[i][0] = Integer.toString(i + 1);
                scores[i][1] = name;
                scores[i][2] = score;
                i++;
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteDbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;
    }   
}

package portablewiki;

// jdbc: jdbc:derby://localhost:1527/portablewiki [root on ROOT] root : root

import java.sql.*;

/**
 *
 * @author YuriPilshikov
 */
public class Portablewiki {

    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/portablewiki", "root", "root");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM notes")) {

            while(rs.next()) {
                for(int i = 1; i <= 5; i++) {
                    System.out.println(rs.getObject(i));
                    System.out.println(" ");
                }
                System.out.println("");
            }

        } catch (SQLException ex) {
            System.out.println("Error connecting database: " + ex.getMessage());
        }
    }

}

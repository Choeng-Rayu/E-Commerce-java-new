import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    // Database credentials
    private static final String USERNAME = "root";
    private static final String PASSWORD = "rayu$@mySQL";
    // Assuming default MySQL port 3306 and localhost
    private static final String URL = "jdbc:mysql://localhost:3306/databasejava";

    public static void main(String[] args) {
        Connection connection = null;
        
        try {
            // Register JDBC driver (Not needed for newer versions of JDBC)
            // Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            // If connection is successful
            System.out.println("Successfully connected to MySQL database!");
            
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        } finally {
            // Close the connection
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    System.out.println("Database connection closed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

import java.sql.*;

public class MySQLDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "rayu@";

    // Function to get a database connection
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Function to insert data into MySQL
    public static void insertData(String name, String email, String password) {
        Connection conn = getConnection();
        if (conn == null) {
            System.out.println("Failed to connect!");
            return;
        }

        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully!");
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to fetch data from MySQL
    public static void fetchData() {
        Connection conn = getConnection();
        if (conn == null) {
            System.out.println("Failed to connect!");
            return;
        }

        String sql = "SELECT * FROM users";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main function
    public static void main(String[] args) {
        // Insert sample data
        insertData("John Doe", "john@example.com");

        // Fetch and display all users
        fetchData();
    }
}

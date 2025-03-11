import java.sql.Connection;         // Helps us connect to the database
import java.sql.DriverManager;     // Manages the connection
import java.sql.PreparedStatement; // Used to send data to the database safely
import java.sql.ResultSet;         // Holds data we get from the database
import java.sql.Statement;         // Used to ask the database for data
import java.util.Scanner;          // Lets us get input from the user

public class ConnectorDataBase {

    public static void main(String[] args) {
        // Create a scanner to read what the user types
        Scanner scanner = new Scanner(System.in);

        // Information to connect to the database
        String url = "jdbc:mysql://localhost:3306/ecom_db"; // Where the database lives
        String username = "root";                           // Your database username
        String password = "rayu$@mySQL";                    // Your database password

        try {
            // Step 1: Connect to the database (like opening a door)
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Yay! We connected to the database!");

            // Step 2: Ask the user for information to add to the database
            System.out.println("Please enter an ID number:");
            int id = scanner.nextInt();      // Get the number the user types
            scanner.nextLine();              // Clear the leftover "enter" key

            System.out.println("Please enter a name:");
            String name = scanner.nextLine(); // Get the name the user types

            // Step 3: Add the user's data to the "info" table
            // This is like filling out a form and sending it
            String insertCommand = "INSERT INTO info (id, name) VALUES (?, ?)";
            PreparedStatement insertTool = conn.prepareStatement(insertCommand);
            insertTool.setInt(1, id);        // Put the ID in the first spot
            insertTool.setString(2, name);   // Put the name in the second spot
            insertTool.executeUpdate();      // Send it to the database
            System.out.println("Data added successfully!");

            // Step 4: Get all the data from the "info" table and show it
            String getCommand = "SELECT * FROM info";
            Statement getTool = conn.createStatement(); // Tool to ask for data
            ResultSet results = getTool.executeQuery(getCommand); // Get the data

            System.out.println("Here’s everything in the database:");
            while (results.next()) {         // Loop through all the rows
                int dbId = results.getInt("id");      // Get the ID from the row
                String dbName = results.getString("name"); // Get the name from the row
                System.out.println("ID: " + dbId + ", Name: " + dbName); // Show it
            }

            // Step 5: Close the connection (like closing the door)
            conn.close();
            System.out.println("Connection closed.");

        } catch (Exception e) {
            // If something goes wrong, tell the user
            System.out.println("Oops! Something went wrong with the database.");
            e.printStackTrace(); // Show details about the problem
        }

        // Close the scanner to clean up
        scanner.close();
    }
}
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/E_COMMERCE_PROJECT_RAYU_JAVA";
    private static final String USER = "root";
    private static final String PASSWORD = "rayu$@mySQL";

    // Connection management
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Customer Operations

    // Insert new customer
    public static String insertCustomer(CustomerData customer) {
        String sql = "INSERT INTO customers (id, first_name, last_name, email, password, date_registered) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customer.id);
            stmt.setString(2, customer.firstName);
            stmt.setString(3, customer.lastName);
            stmt.setString(4, customer.email);
            stmt.setString(5, customer.getPassword());
            stmt.setTimestamp(6, Timestamp.valueOf(customer.dateRegister));
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Customer inserted successfully!" : "Failed to insert customer.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Fetch customer by email
    public static CustomerData fetchCustomerByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";
        
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new CustomerData(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update customer information
    public static String updateCustomer(CustomerData customer) {
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, email = ?, password = ? WHERE email = ?";
        
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customer.firstName);
            stmt.setString(2, customer.lastName);
            stmt.setString(3, customer.email);
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.email);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Customer updated successfully!" : "Failed to update customer.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Delete customer
    public static String deleteCustomer(String email) {
        String sql = "DELETE FROM customers WHERE email = ?";
        
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Customer deleted successfully!" : "Failed to delete customer.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Seller Operations

    // Insert new seller
    public static String insertSeller(SellerData seller) {
        String sql = "INSERT INTO sellers (id, first_name, last_name, email, password, date_registered) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, seller.id);
            stmt.setString(2, seller.firstName);
            stmt.setString(3, seller.lastName);
            stmt.setString(4, seller.email);
            stmt.setString(5, seller.getPassword());
            stmt.setTimestamp(6, Timestamp.valueOf(seller.dateRegister));
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Seller inserted successfully!" : "Failed to insert seller.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Fetch seller by email
    public static SellerData fetchSellerByEmail(String email) {
        String sql = "SELECT * FROM sellers WHERE email = ?";
        
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new SellerData(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update seller information
    public static String updateSeller(SellerData seller) {
        String sql = "UPDATE sellers SET first_name = ?, last_name = ?, email = ?, password = ? WHERE email = ?";
        
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, seller.firstName);
            stmt.setString(2, seller.lastName);
            stmt.setString(3, seller.email);
            stmt.setString(4, seller.getPassword());
            stmt.setString(5, seller.email);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Seller updated successfully!" : "Failed to update seller.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Delete seller
    public static String deleteSeller(String email) {
        String sql = "DELETE FROM sellers WHERE email = ?";
        
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Seller deleted successfully!" : "Failed to delete seller.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Product Operations

    // Insert new product
    public static String insertProduct(Product product) {
        String sql = "INSERT INTO products (id, name, price, quantity, push_by, date_pushed) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, product.id);
            stmt.setString(2, product.name);
            stmt.setDouble(3, product.price);
            stmt.setInt(4, product.quantity);
            stmt.setString(5, product.pushBy);
            stmt.setTimestamp(6, Timestamp.valueOf(product.datePush));
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Product inserted successfully!" : "Failed to insert product.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Fetch all products
    public static List<Product> fetchAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("push_by")
                    //rs.getTimestamp("date_pushed")
                    //rs.getString("date_pushed")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Update product
    public static String updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, quantity = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, product.name);
            stmt.setDouble(2, product.price);
            stmt.setInt(3, product.quantity);
            stmt.setInt(4, product.id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Product updated successfully!" : "Failed to update product.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Delete product
    public static String deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE id = ?";
        
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, productId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Product deleted successfully!" : "Failed to delete product.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public static String insertProductSold(AddToCart.ProductSold productSold) {
        String sql = "INSERT INTO sold_products (product_id, product_name, price, quantity, sold_by, buyer_name, date_bought) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set parameters based on ProductSold object
            stmt.setInt(1, productSold.productId);          // product_id
            stmt.setString(2, productSold.productName);     // product_name
            stmt.setDouble(3, productSold.price);           // price
            stmt.setInt(4, productSold.quantity);           // quantity
            stmt.setString(5, productSold.boughtName);      // sold_by
            stmt.setString(6, productSold.buyerName);       // buyer_name
            stmt.setString(7, productSold.dateBought);      // date_bought
            
            // Execute the query
            int rowsAffected = stmt.executeUpdate();
            
            // Return success or failure message
            return rowsAffected > 0 ? "Product sold recorded successfully!" : "Failed to record product sale.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

}
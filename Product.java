import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseEvent;
import java.awt.BorderLayout;

//import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Product {
    public int id;
    public String name;
    public double price;
    public int quantity;
    public String datePush;
    public String pushBy;
    public static int totalProductAddedCart = 0;
    public double totalMoneyForPay = 0;
    
    
    public static ArrayList<Product> products = new ArrayList<>();
    //public static ArrayList<Product> cart = new ArrayList<>();
    SellerData seller = new SellerData("", "", "", "");

    // Constructor
    public Product(int id, String name, double price, int quantity, String pushBy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.pushBy = pushBy;
        this.datePush = getCurrentDateTimeInCambodia();
    }
    public void DeleteProductByAdmin() {
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Delete Product by Admin");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create a label and text field for index input
        JLabel indexLabel = new JLabel("Select Index to Delete:");
        JTextField indexField = new JTextField(10);

        // Create a button to confirm deletion
        JButton deleteButton = new JButton("Delete");

        // Add components to the panel
        panel.add(indexLabel);
        panel.add(indexField);
        panel.add(deleteButton);

        // Add action listener to the delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String iString = indexField.getText();
                    NumberOnlyException.validateNumber(iString, "^[0-9]+$");
                    int i = Integer.parseInt(iString);

                    // Verify admin password
                    AdminExtends ad = new AdminExtends();
                    if (ad.verifyPassword()) {
                        products.remove(i); // Remove the product at the specified index
                        JOptionPane.showMessageDialog(frame, "Product was deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Password! Product was not deleted.");
                    }
                } catch (NumberOnlyException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Index. Product was not deleted.");
                }
            }
        });

        // Add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }

    public void displayAllProduct() {
    if (products.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No Products Available!");
        return;
    }

    // Create a JFrame for the GUI
    JFrame frame = new JFrame("Product List");
    frame.setSize(800, 400);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    // Create a table model with columns
    String[] columns = {"No", "ID", "Name", "Price ($)", "Quantity", "Push By", "Date"};
    DefaultTableModel model = new DefaultTableModel(columns, 0);

    // Populate the table model with product data
    int i = 0;
    for (Product p : products) {
        Object[] row = {i, p.id, p.name, p.price, p.quantity, p.pushBy, p.datePush};
        model.addRow(row);
        i++;
    }

    // Create a JTable with the populated model
    JTable table = new JTable(model);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only one row to be selected
    table.setRowHeight(25); // Set row height for better visibility

    // Add a mouse listener to handle row clicks
    table.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int selectedRow = table.getSelectedRow(); // Get the selected row index
            if (selectedRow != -1) { // Ensure a valid row is selected
                // Retrieve product details from the selected row
                int id = (int) table.getValueAt(selectedRow, 1);
                String name = (String) table.getValueAt(selectedRow, 2);
                double price = (double) table.getValueAt(selectedRow, 3);
                int quantity = (int) table.getValueAt(selectedRow, 4);
                String pushBy = (String) table.getValueAt(selectedRow, 5);
                String datePush = (String) table.getValueAt(selectedRow, 6);

                // Display product details in a dialog
                String details = "Product Details:\n" +
                        "ID: " + id + "\n" +
                        "Name: " + name + "\n" +
                        "Price: $" + price + "\n" +
                        "Quantity: " + quantity + "\n" +
                        "Push By: " + pushBy + "\n" +
                        "Date: " + datePush;
                JOptionPane.showMessageDialog(frame, details, "Product Details", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    });

    // Add the table to a scroll pane
    JScrollPane scrollPane = new JScrollPane(table);
    frame.add(scrollPane, BorderLayout.CENTER);

    // Make the frame visible
    frame.setVisible(true);
}

    // Method to add a product
    public void addProduct(int id, String name, double price, int quantity, String pushBy) {
        Product p = new Product(id, name, price, quantity, pushBy);
        DatabaseHandler.insertProduct(p);
        products.add(p);
    }

    public void modifiedProduct(int id) {
        // Iterate through the list of products
        for (Product product : products) {
            if (product.id == id) {
                // Create input dialogs for new product details
                String newName = JOptionPane.showInputDialog(null, "Enter new product name:");
                String newPriceInput = JOptionPane.showInputDialog(null, "Enter new product price:");
                String newQuantityInput = JOptionPane.showInputDialog(null, "Enter new product quantity:");

                try {
                    // Validate and parse the new price
                    NumberOnlyException.validateNumber(newPriceInput, "^[0-9]+(\\.[0-9]+)?$"); // Allow decimals
                    double newPrice = Double.parseDouble(newPriceInput);

                    // Validate and parse the new quantity
                    NumberOnlyException.validateNumber(newQuantityInput, "^[0-9]+$");
                    int newQuantity = Integer.parseInt(newQuantityInput);
                    DuplicateArgumentException.validateQuantity(newQuantity);

                    // Update product details
                    product.name = newName;
                    product.price = newPrice;
                    product.quantity = newQuantity;

                    // Show success message
                    JOptionPane.showMessageDialog(null, "Product was modified successfully!");
                    return; // Exit the method after modifying the product
                } catch (NumberOnlyException | DuplicateArgumentException e) {
                    // Show error message if validation fails
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                    return;
                }
            }
        }
        // If no matching product is found, show an error message
        JOptionPane.showMessageDialog(null, "Product not found!");
    }

    public String deleteProduct(int idDelete) {
        // Iterate through the list of products
        for (Product product : products) {
            if (product.id == idDelete) {
                // Check if the product was pushed by the current seller
                if (SellerData.pushByName.equals(product.pushBy)) {
                    // Prompt for password
                    String passString = JOptionPane.showInputDialog(null, "Enter password to delete:");
    
                    // Validate password
                    if (passString != null && passString.equals(seller.getCurrentPw())) {
                        products.remove(product); // Remove the product
                        return "Product Name: " + product.name + " was deleted successfully";
                    } else {
                        return "Invalid password! Product was not deleted.";
                    }
                } else {
                    return "You can only delete the product you pushed!";
                }
            }
        }
        return "Product not found.";
    }
    @Override
    public String toString() {
        return "Product ID: " + id + "\nProduct Name: " + name + "\nProduct Price$: " + price + "\nProduct Quantity: " + quantity + "\nDate: " + datePush;
    }

    public static int getTotalProduct() {
        return products.size();
    }

    public String searchProductByID(int id) {
        for (Product product : products) {
            if (product.id == id) {
                return product.toString();
            }
        }
        return "Product not found";
    }

    public String searchProductByName(String name) {
        for (Product product : products) {
            if (product.name.equalsIgnoreCase(name)) {
                return product.toString();
            }
        }
        return "Product not found";
    }

    public String searchProductByPrice(double price) {
        for (Product product : products) {
            if (product.price == price) {
                return product.toString();
            }
        }
        return "Product not found";
    }
    public static int generateIDByProductSize() { 
        return products.size();
    }
    public void productManagement() {
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Product Management System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Create a JPanel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create buttons for each option
        JButton exitButton = new JButton("0. Exit");
        JButton addProductButton = new JButton("1. Add Products");
        JButton modifyProductButton = new JButton("2. Modify Products");
        JButton deleteProductButton = new JButton("3. Delete Products");
        JButton displayProductsButton = new JButton("4. Display Products");
        JButton searchProductsButton = new JButton("5. Search Products");
        JButton checkSoldProductsButton = new JButton("6. Check Products Sold");
        JButton backButton = new JButton("7. Back to Main Menu");

        // Add buttons to the panel
        panel.add(exitButton);
        panel.add(addProductButton);
        panel.add(modifyProductButton);
        panel.add(deleteProductButton);
        panel.add(displayProductsButton);
        panel.add(searchProductsButton);
        panel.add(checkSoldProductsButton);
        panel.add(backButton);

        // Add action listeners to buttons
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Exiting...");
                System.exit(0);
            }
        });

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameInput = JOptionPane.showInputDialog(frame, "Enter product name:");
                String priceInput = JOptionPane.showInputDialog(frame, "Enter product price:");
                String quantityInput = JOptionPane.showInputDialog(frame, "Enter product quantity:");

                try {
                    NumberOnlyException.validateNumber(priceInput, "^[0-9]+(\\.[0-9]+)?$"); // Allow decimals
                    double priceInputDouble = Double.parseDouble(priceInput);

                    NumberOnlyException.validateNumber(quantityInput, "^[0-9]+$");
                    int quantityInputInt = Integer.parseInt(quantityInput);

                    addProduct(generateIDByProductSize(), nameInput, priceInputDouble, quantityInputInt, SellerData.pushByName);
                    JOptionPane.showMessageDialog(frame, "Product added successfully!");
                } catch (NumberOnlyException | DuplicateArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        modifyProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (products.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Products!");
                    return;
                }

                String idInput = JOptionPane.showInputDialog(frame, "Enter product ID to modify:");
                try {
                    NumberOnlyException.validateNumber(idInput, "^[0-9]+$");
                    int idInputInt = Integer.parseInt(idInput);
                    modifiedProduct(idInputInt);
                } catch (NumberOnlyException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (products.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Products!");
                    return;
                }

                String idInput = JOptionPane.showInputDialog(frame, "Enter product ID to delete:");
                try {
                    NumberOnlyException.validateNumber(idInput, "^[0-9]+$");
                    int idInputInt = Integer.parseInt(idInput);
                    String result = deleteProduct(idInputInt);
                    JOptionPane.showMessageDialog(frame, result);
                } catch (NumberOnlyException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        displayProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (products.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Products!");
                    return;
                }

                displayAllProduct();
                JOptionPane.showMessageDialog(frame, "Total Products: " + Product.getTotalProduct());
            }
        });

        searchProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMenu(); // Open the search menu
            }
        });

        checkSoldProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddToCart cart = new AddToCart(0, "", 0, 0, "");
                cart.displaySoldProducts();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
            }
        });

        // Add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }
    public void searchMenu() {
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Search Menu");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    
        // Create a JPanel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        // Create buttons for each option
        JButton exitButton = new JButton("0. Exit");
        JButton searchByIdButton = new JButton("1. Search Product by ID");
        JButton searchByNameButton = new JButton("2. Search Product by Name");
        JButton searchByPriceButton = new JButton("3. Search Product by Price");
        JButton backButton = new JButton("4. Back to Main Menu");
    
        // Add buttons to the panel
        panel.add(exitButton);
        panel.add(searchByIdButton);
        panel.add(searchByNameButton);
        panel.add(searchByPriceButton);
        panel.add(backButton);
    
        // Add action listeners to buttons
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Exiting...");
                System.exit(0);
            }
        });
    
        searchByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idInput = JOptionPane.showInputDialog(frame, "Enter product ID:");
                try {
                    NumberOnlyException.validateNumber(idInput, "^[0-9]+$");
                    int idInputInt = Integer.parseInt(idInput);
                    String result = searchProductByID(idInputInt);
                    JOptionPane.showMessageDialog(frame, result);
                } catch (NumberOnlyException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });
    
        searchByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameInput = JOptionPane.showInputDialog(frame, "Enter product name:");
                if (nameInput != null) {
                    String result = searchProductByName(nameInput);
                    JOptionPane.showMessageDialog(frame, result);
                }
            }
        });
    
        searchByPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String priceInput = JOptionPane.showInputDialog(frame, "Enter product price:");
                try {
                    NumberOnlyException.validateNumber(priceInput, "^[0-9]+(\\.[0-9]+)?$");
                    double priceInputDouble = Double.parseDouble(priceInput);
                    String result = searchProductByPrice(priceInputDouble);
                    JOptionPane.showMessageDialog(frame, result);
                } catch (NumberOnlyException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });
    
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
            }
        });
    
        // Add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }

    public static String getCurrentDateTimeInCambodia() {
        ZoneId cambodiaZoneId = ZoneId.of("Asia/Phnom_Penh");
        LocalDateTime currentDateTime = LocalDateTime.now(cambodiaZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }
    public static void main(String[] args){
        Product c = new Product(1, "book", 123.56, 10, "rayu");
        Product newProduct2 = new Product(2, "pen", 234.56, 20, "rayu");
        Product newProduct3 = new Product(3, "phone", 345.67, 30, "rayu");
        products.add(newProduct2);
        products.add(newProduct3);
        products.add(c);
        //c.displayAllProduct();
        //c.addProductToCart(1, 2);
        //c.displayCart();
        c.productManagement();
        //c.menuShopping();
        //c.productManagement();  
        //c.searchMenu();
    }
}
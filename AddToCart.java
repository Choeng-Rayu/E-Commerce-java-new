
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class AddToCart extends Product {
    public static int totalProductAdded = 0;
    public String currentDateAddToCart;
    public static ArrayList<Product> cart = new ArrayList<>();
    private static ArrayList<ProductSold> soldProducts = new ArrayList<>();

    public AddToCart(int id, String name, double price, int quantity, String pushBy) {
        super(id, name, price, quantity, pushBy);
        totalProductAdded += quantity;
    }
    public class ProductSold {
        public String buyerName;
        public String dateBought; // Changed to match constructor
        public int productId;
        public String productName;
        public double price;
        public int quantity;
        public String boughtName;

        public ProductSold(int productId, String productName, double price, int quantity, 
                         String boughtName, String buyerName, String dateBought) {
            this.productId = productId;
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
            this.boughtName = boughtName;
            this.buyerName = buyerName;
            this.dateBought = dateBought;
        }

        // Method to add a sold product to the list
        public void recordSale() {
            soldProducts.add(this);
            DatabaseHandler.insertProductSold(this);
        }

        // // Getter methods
        // public String getBuyerName() { return buyerName; }
        // public String getDateBought() { return dateBought; }
        // public int getProductId() { return productId; }
        // public double getPrice() { return price; }
        // public int getQuantity() { return quantity; }
    }

    // Method to display all sold products
    public void displaySoldProducts() {
        if (soldProducts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No products have been sold yet.");
            return;
        }
    
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Sold Products");
        frame.setSize(900, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    
        // Create a table model with columns
        String[] columns = {"ID", "Name", "Price ($)", "Quantity", "Sold By", "Buyer", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
    
        // Populate the table model with sold product data
        for (ProductSold sold : soldProducts) {
            Object[] row = {
                sold.productId,
                sold.productName,
                sold.price,
                sold.quantity,
                sold.boughtName,
                sold.buyerName,
                sold.dateBought
            };
            model.addRow(row);
        }
    
        // Create a JTable with the populated model
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only one row to be selected
        table.setRowHeight(25); // Set row height for better visibility
    
        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
    
        // Make the frame visible
        frame.setVisible(true);
    }
    public void addProductToCart(int id, int quantityAdded) {
        for (Product productAdded : products) {
            if (productAdded.id == id) {
                if (productAdded.quantity >= quantityAdded && quantityAdded > 0) {
                    Product cartProduct = new Product(productAdded.id, productAdded.name, productAdded.price, quantityAdded, productAdded.pushBy);
                    cart.add(cartProduct);
                    productAdded.quantity -= quantityAdded;
                    totalProductAddedCart += quantityAdded;
                    //System.out.println("Product: " + cartProduct.name + " " + cartProduct.price + " " + cartProduct.pushBy + " " + cartProduct.datePush);
                   //System.out.println("Product added to cart successfully");
                    //System.out.println("WARNING: Your cart will be remove after you logout!");   
                    JOptionPane.showMessageDialog(null,"WARNING: Your cart will be remove after you logout!"); 
                } else {
                    System.out.println("Insufficient quantity");
                }
                return;
            }
        }
        System.out.println("Product not found");
    }
    public void displayCart() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Your cart is empty!");
            return;
        }

        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Cart");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Create a table model with columns
        String[] columns = {"No", "ID", "Name", "Price ($)", "Quantity", "Push By", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Populate the table model with cart data
        int i = 0;
        totalMoneyForPay = 0; // Reset total price
        for (Product productAdded : cart) {
            Object[] row = {i, productAdded.id, productAdded.name, productAdded.price, productAdded.quantity, productAdded.pushBy, productAdded.datePush};
            model.addRow(row);
            i++;
            totalMoneyForPay += productAdded.price * productAdded.quantity; // Calculate total price
        }

        // Create a JTable with the populated model
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only one row to be selected
        table.setRowHeight(25); // Set row height for better visibility

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the total quantity and price
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new GridLayout(2, 1)); // Two rows for total quantity and price

        // Add total quantity label
        JLabel totalQuantityLabel = new JLabel("Total Quantity: " + totalProductAddedCart);
        totalQuantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalPanel.add(totalQuantityLabel);

        // Add total price label
        JLabel totalPriceLabel = new JLabel("Total Price: " + totalMoneyForPay + "$");
        totalPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalPanel.add(totalPriceLabel);

        // Add the total panel to the frame
        frame.add(totalPanel, BorderLayout.SOUTH);

        // Make the frame visible
        frame.setVisible(true);
    }
   
    public void Payment() {
        // Display the cart
        displayCart();
    
        // Check if the cart is empty
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Products in Cart!");
            return;
        }
    
        // Prompt the user to enter their password for verification
        String pwInput = JOptionPane.showInputDialog(null, "Please enter your password to verify payment:");
    
        // Validate the password
        CustomerData c = new CustomerData("", "", "", "");
        if (pwInput != null && c.getCurrentPw().equals(pwInput)) {
            // Record each product in the cart as sold
            for (Product p : cart) {
                ProductSold sold = new ProductSold(
                    p.id, 
                    p.name, 
                    p.price, 
                    p.quantity, 
                    p.pushBy, 
                    CustomerData.CurrentNameLogin, 
                    getCurrentDateTimeInCambodia() // Using current date as example
                );
                sold.recordSale();
            }
    
            // Clear the cart and reset totals
            cart.clear();
            totalProductAddedCart = 0;
            totalMoneyForPay = 0;
    
            // Show success message
            JOptionPane.showMessageDialog(null, "Payment successfully processed!");
        } else {
            // Show error message if the password is invalid
            JOptionPane.showMessageDialog(null, "Invalid Password! Payment was not successful.");
        }
    }
    public static boolean CartisEmpty(){
        if(cart.isEmpty()){
            return true;
        }
        return false;
    }
    public static boolean logoutClearCart(String input){
        if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")){
            cart.clear();
            totalProductAddedCart = 0;
            //menuRole = false;
            return true;
        }
        return false;
                        
    }
    public String removeProductFromCart(int index) {
        for (Product productRemove : cart) {
            if (productRemove.id == index) {
                cart.remove(productRemove);
                totalProductAddedCart -= productRemove.quantity;
          
                return "Product removed from cart successfully";
            }
        }
        return "Product not found";
    }
    public void menuShopping() {
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Shopping Menu");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Create a JPanel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create buttons for each option
        JButton exitButton = new JButton("0. Exit");
        JButton addToCartButton = new JButton("1. Add to Cart");
        JButton removeFromCartButton = new JButton("2. Remove from Cart");
        JButton checkOutButton = new JButton("3. Check Out");
        JButton paymentButton = new JButton("4. Payment");
        JButton searchProductButton = new JButton("5. Search Product");
        JButton backButton = new JButton("6. Back to Previous Menu");

        // Add buttons to the panel
        panel.add(exitButton);
        panel.add(addToCartButton);
        panel.add(removeFromCartButton);
        panel.add(checkOutButton);
        panel.add(paymentButton);
        panel.add(searchProductButton);
        panel.add(backButton);

        // Add action listeners to buttons
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Exiting...");
                System.exit(0);
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (products.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Products!");
                    return;
                }

                displayAllProduct(); // Display all products

                String idInputStr = JOptionPane.showInputDialog(frame, "Enter product ID to add to cart:");
                String quantityInputStr = JOptionPane.showInputDialog(frame, "Enter quantity:");

                try {
                    NumberOnlyException.validateNumber(idInputStr, "^[0-9]+$");
                    int idInput = Integer.parseInt(idInputStr);

                    NumberOnlyException.validateNumber(quantityInputStr, "^[0-9]+$");
                    int quantityInput = Integer.parseInt(quantityInputStr);

                    addProductToCart(idInput, quantityInput);
                    JOptionPane.showMessageDialog(frame, "Product added to cart successfully!");
                } catch (NumberOnlyException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        removeFromCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCart(); // Display the cart

                if (cart.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Products in Cart!");
                    return;
                }

                String idInputStr = JOptionPane.showInputDialog(frame, "Enter product ID to remove:");
                try {
                    NumberOnlyException.validateNumber(idInputStr, "^[0-9]+$");
                    int idInput = Integer.parseInt(idInputStr);

                    removeProductFromCart(idInput);
                    JOptionPane.showMessageDialog(frame, "Product removed from cart successfully!");
                } catch (NumberOnlyException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCart(); // Display the cart

                if (cart.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Products in Cart!");
                    return;
                }

                // Implement checkout logic here
                JOptionPane.showMessageDialog(frame, "Checkout functionality not yet implemented.");
            }
        });

        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cart.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Products in Cart!");
                    return;
                }

                Payment(); // Call the payment method
            }
        });

        searchProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMenu(); // Open the search menu
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
}

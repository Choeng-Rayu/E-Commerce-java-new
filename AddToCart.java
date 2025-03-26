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

    // Inner class for ProductSold
    public class ProductSold {
        public String buyerName;
        public String dateBought; // Date of purchase
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

        // Record sale by adding to local list and inserting into database
        public void recordSale() {
            soldProducts.add(this);
            DatabaseHandler.insertProductSold(this);
        }
    }

    // Method to display all sold products from the local list
    public void displaySoldProducts() {
        if (soldProducts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No products have been sold yet.");
            return;
        }

        JFrame frame = new JFrame("Sold Products");
        frame.setSize(900, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        String[] columns = {"ID", "Name", "Price ($)", "Quantity", "Sold By", "Buyer", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

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

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Add product to cart (update local product quantity)
    public void addProductToCart(int id, int quantityAdded) {
        for (Product productAdded : products) {
            if (productAdded.id == id) {
                if (productAdded.quantity >= quantityAdded && quantityAdded > 0) {
                    Product cartProduct = new Product(productAdded.id, productAdded.name, productAdded.price, quantityAdded, productAdded.pushBy);
                    cart.add(cartProduct);
                    productAdded.quantity -= quantityAdded;
                    totalProductAddedCart += quantityAdded;
                    JOptionPane.showMessageDialog(null, "WARNING: Your cart will be removed after you logout!");
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient quantity");
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Product not found");
    }

    // Display current cart contents and total price
    public void displayCart() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Your cart is empty!");
            return;
        }

        JFrame frame = new JFrame("Cart");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        String[] columns = {"No", "ID", "Name", "Price ($)", "Quantity", "Push By", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        int i = 0;
        totalMoneyForPay = 0; // Reset total price
        for (Product productAdded : cart) {
            Object[] row = {i, productAdded.id, productAdded.name, productAdded.price, productAdded.quantity, productAdded.pushBy, productAdded.datePush};
            model.addRow(row);
            i++;
            totalMoneyForPay += productAdded.price * productAdded.quantity;
        }

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new GridLayout(2, 1));

        JLabel totalQuantityLabel = new JLabel("Total Quantity: " + totalProductAddedCart);
        totalQuantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalPanel.add(totalQuantityLabel);

        JLabel totalPriceLabel = new JLabel("Total Price: " + totalMoneyForPay + "$");
        totalPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalPanel.add(totalPriceLabel);

        frame.add(totalPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Process payment: verify password, record sales, clear cart
    public void Payment() {
        displayCart();
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Products in Cart!");
            return;
        }
        String pwInput = JOptionPane.showInputDialog(null, "Please enter your password to verify payment:");
        CustomerData c = new CustomerData("", "", "", "");
        if (pwInput != null && c.getCurrentPw().equals(pwInput)) {
            for (Product p : cart) {
                ProductSold sold = new ProductSold(
                    p.id, 
                    p.name, 
                    p.price, 
                    p.quantity, 
                    p.pushBy, 
                    CustomerData.CurrentNameLogin, 
                    getCurrentDateTimeInCambodia()
                );
                sold.recordSale();
            }
            cart.clear();
            totalProductAddedCart = 0;
            totalMoneyForPay = 0;
            JOptionPane.showMessageDialog(null, "Payment successfully processed!");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Password! Payment was not successful.");
        }
    }

    public static boolean CartisEmpty() {
        return cart.isEmpty();
    }

    public static boolean logoutClearCart(String input) {
        if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")){
            cart.clear();
            totalProductAddedCart = 0;
            return true;
        }
        return false;
    }

    // Remove a product from the cart by product ID
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

    // Shopping menu GUI
    public void menuShopping() {
        JFrame frame = new JFrame("Shopping Menu");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton exitButton = new JButton("0. Exit");
        JButton addToCartButton = new JButton("1. Add to Cart");
        JButton removeFromCartButton = new JButton("2. Remove from Cart");
        JButton checkOutButton = new JButton("3. Check Out");
        JButton paymentButton = new JButton("4. Payment");
        JButton searchProductButton = new JButton("5. Search Product");
        JButton backButton = new JButton("6. Back to Previous Menu");

        panel.add(exitButton);
        panel.add(addToCartButton);
        panel.add(removeFromCartButton);
        panel.add(checkOutButton);
        panel.add(paymentButton);
        panel.add(searchProductButton);
        panel.add(backButton);

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
                displayAllProduct();
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
                displayCart();
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
                displayCart();
                if (cart.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Products in Cart!");
                    return;
                }
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
                Payment();
            }
        });

        searchProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMenu();
            }
        });

        backButton.addActionListener(e -> frame.dispose());

        frame.add(panel);
        frame.setVisible(true);
    }
}

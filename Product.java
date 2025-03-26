import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import java.awt.BorderLayout;
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
    // Associated seller instance (for accessing current password, etc.)
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
    
    // Admin deletion via GUI with no DB update here (assumed deletion only from local list)
    public void DeleteProductByAdmin() {
        JFrame frame = new JFrame("Delete Product by Admin");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel indexLabel = new JLabel("Select Index to Delete:");
        JTextField indexField = new JTextField(10);
        JButton deleteButton = new JButton("Delete");

        panel.add(indexLabel);
        panel.add(indexField);
        panel.add(deleteButton);

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
                        Product productToRemove = products.get(i);
                        products.remove(i);
                        // Remove product from database as well
                        String result = DatabaseHandler.deleteProduct(productToRemove.id);
                        JOptionPane.showMessageDialog(frame, "Product was deleted successfully!\n" + result);
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

        frame.add(panel);
        frame.setVisible(true);
    }

    public void displayAllProduct() {
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Products Available!");
            return;
        }

        JFrame frame = new JFrame("Product List");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        String[] columns = {"No", "ID", "Name", "Price ($)", "Quantity", "Push By", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        int i = 0;
        for (Product p : products) {
            Object[] row = {i, p.id, p.name, p.price, p.quantity, p.pushBy, p.datePush};
            model.addRow(row);
            i++;
        }

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) table.getValueAt(selectedRow, 1);
                    String name = (String) table.getValueAt(selectedRow, 2);
                    double price = (double) table.getValueAt(selectedRow, 3);
                    int quantity = (int) table.getValueAt(selectedRow, 4);
                    String pushBy = (String) table.getValueAt(selectedRow, 5);
                    String datePush = (String) table.getValueAt(selectedRow, 6);
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

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Method to add a product (insert into DB and local list)
    public void addProduct(int id, String name, double price, int quantity, String pushBy) {
        Product p = new Product(id, name, price, quantity, pushBy);
        String result = DatabaseHandler.insertProduct(p);
        products.add(p);
        JOptionPane.showMessageDialog(null, result);
    }

    // Modify product details locally; ideally, you'd update the DB afterwards.
    public void modifiedProduct(int id) {
        for (Product product : products) {
            if (product.id == id) {
                String newName = JOptionPane.showInputDialog(null, "Enter new product name:");
                String newPriceInput = JOptionPane.showInputDialog(null, "Enter new product price:");
                String newQuantityInput = JOptionPane.showInputDialog(null, "Enter new product quantity:");

                try {
                    NumberOnlyException.validateNumber(newPriceInput, "^[0-9]+(\\.[0-9]+)?$");
                    double newPrice = Double.parseDouble(newPriceInput);
                    NumberOnlyException.validateNumber(newQuantityInput, "^[0-9]+$");
                    int newQuantity = Integer.parseInt(newQuantityInput);
                    DuplicateArgumentException.validateQuantity(newQuantity);

                    product.name = newName;
                    product.price = newPrice;
                    product.quantity = newQuantity;
                    
                    // Update the product in the database
                    String result = DatabaseHandler.updateProduct(product);
                    JOptionPane.showMessageDialog(null, "Product was modified successfully!\n" + result);
                    return;
                } catch (NumberOnlyException | DuplicateArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Product not found!");
    }

    // Delete a product by ID with seller verification
    public String deleteProduct(int idDelete) {
        for (Product product : products) {
            if (product.id == idDelete) {
                if (SellerData.pushByName.equals(product.pushBy)) {
                    String passString = JOptionPane.showInputDialog(null, "Enter password to delete:");
                    if (passString != null && passString.equals(seller.getCurrentPw())) {
                        products.remove(product);
                        String result = DatabaseHandler.deleteProduct(product.id);
                        return "Product Name: " + product.name + " was deleted successfully\n" + result;
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
        return "Product ID: " + id + "\nProduct Name: " + name + "\nProduct Price$: " + price +
               "\nProduct Quantity: " + quantity + "\nDate: " + datePush;
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

    // Main product management GUI with database integration
    public void productManagement() {
        JFrame frame = new JFrame("Product Management System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton exitButton = new JButton("0. Exit");
        JButton addProductButton = new JButton("1. Add Products");
        JButton modifyProductButton = new JButton("2. Modify Products");
        JButton deleteProductButton = new JButton("3. Delete Products");
        JButton displayProductsButton = new JButton("4. Display Products");
        JButton searchProductsButton = new JButton("5. Search Products");
        JButton checkSoldProductsButton = new JButton("6. Check Products Sold");
        JButton backButton = new JButton("7. Back to Main Menu");

        panel.add(exitButton);
        panel.add(addProductButton);
        panel.add(modifyProductButton);
        panel.add(deleteProductButton);
        panel.add(displayProductsButton);
        panel.add(searchProductsButton);
        panel.add(checkSoldProductsButton);
        panel.add(backButton);

        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Exiting...");
            System.exit(0);
        });

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameInput = JOptionPane.showInputDialog(frame, "Enter product name:");
                String priceInput = JOptionPane.showInputDialog(frame, "Enter product price:");
                String quantityInput = JOptionPane.showInputDialog(frame, "Enter product quantity:");

                try {
                    NumberOnlyException.validateNumber(priceInput, "^[0-9]+(\\.[0-9]+)?$");
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
                searchMenu();
            }
        });

        checkSoldProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddToCart cart = new AddToCart(0, "", 0, 0, "");
                cart.displaySoldProducts();
            }
        });

        backButton.addActionListener(e -> frame.dispose());

        frame.add(panel);
        frame.setVisible(true);
    }

    // Search menu for products
    public void searchMenu() {
        JFrame frame = new JFrame("Search Menu");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        JButton exitButton = new JButton("0. Exit");
        JButton searchByIdButton = new JButton("1. Search Product by ID");
        JButton searchByNameButton = new JButton("2. Search Product by Name");
        JButton searchByPriceButton = new JButton("3. Search Product by Price");
        JButton backButton = new JButton("4. Back to Main Menu");
    
        panel.add(exitButton);
        panel.add(searchByIdButton);
        panel.add(searchByNameButton);
        panel.add(searchByPriceButton);
        panel.add(backButton);
    
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Exiting...");
            System.exit(0);
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
    
        backButton.addActionListener(e -> frame.dispose());
    
        frame.add(panel);
        frame.setVisible(true);
    }

    public static String getCurrentDateTimeInCambodia() {
        ZoneId cambodiaZoneId = ZoneId.of("Asia/Phnom_Penh");
        LocalDateTime currentDateTime = LocalDateTime.now(cambodiaZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }
}

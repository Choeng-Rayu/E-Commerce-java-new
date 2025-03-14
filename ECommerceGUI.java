import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class ECommerceGUI {
    private JFrame mainFrame;
    private UserData currentUser;
    private ArrayList<UserData> users = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();

    public ECommerceGUI() {
        initializeMainFrame();
    }

    private void initializeMainFrame() {
        mainFrame = new JFrame("E-commerce System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        mainFrame.setLayout(new CardLayout());
        
        // Login Panel
        JPanel loginPanel = createLoginPanel();
        mainFrame.add(loginPanel, "Login");
        
        // Show frame
        mainFrame.setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(loginButton, gbc);
        gbc.gridx = 1;
        panel.add(registerButton, gbc);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            for (UserData user : users) {
                if (user.login(email, password)) {
                    currentUser = user;
                    showUserDashboard();
                    return;
                }
            }
            JOptionPane.showMessageDialog(mainFrame, "Invalid credentials!");
        });

        registerButton.addActionListener(e -> showRegisterDialog());

        return panel;
    }

    private void showRegisterDialog() {
        JDialog registerDialog = new JDialog(mainFrame, "Register", true);
        registerDialog.setSize(300, 400);
        registerDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Customer", "Seller"});
        JButton submitButton = new JButton("Register");

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;
        registerDialog.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        registerDialog.add(firstNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        registerDialog.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        registerDialog.add(lastNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        registerDialog.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        registerDialog.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        registerDialog.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        registerDialog.add(phoneField, gbc);
        gbc.gridx = 0; gbc.gridy = 4;
        registerDialog.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        registerDialog.add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy = 5;
        registerDialog.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        registerDialog.add(typeCombo, gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        registerDialog.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            // try {
            //     int phone = Integer.parseInt(phoneField.getText());
            //     UserData newUser = typeCombo.getSelectedItem().equals("Seller") ?
            //         // new SellerData(firstNameField.getText(), lastNameField.getText(),
            //         //     emailField.getText(), String.valueOf(phone), new String(passwordField.getPassword())) :
            //         // new CustomerData(firstNameField.getText(), lastNameField.getText(),
            //         //     emailField.getText(), phone, new String(passwordField.getPassword()));
            //     // users.add(newUser);
            //     //registerDialog.dispose();
            //     //JOptionPane.showMessageDialog(mainFrame, "Registration successful!");
            // } catch (NumberFormatException ex) {
            //     JOptionPane.showMessageDialog(mainFrame, "Phone must be a number!");
            // }
        });

        registerDialog.setVisible(true);
    }

    private void showUserDashboard() {
        JPanel dashboard = new JPanel(new BorderLayout());
        
        if (currentUser instanceof CustomerData) {
            dashboard.add(createCustomerPanel(), BorderLayout.CENTER);
        } else if (currentUser instanceof SellerData) {
            dashboard.add(createSellerPanel(), BorderLayout.CENTER);
        }

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            currentUser = null;
            CardLayout cl = (CardLayout)mainFrame.getContentPane().getLayout();
            cl.show(mainFrame.getContentPane(), "Login");
        });
        dashboard.add(logoutButton, BorderLayout.SOUTH);

        mainFrame.add(dashboard, "Dashboard");
        CardLayout cl = (CardLayout)mainFrame.getContentPane().getLayout();
        cl.show(mainFrame.getContentPane(), "Dashboard");
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea productList = new JTextArea(10, 30);
        JButton addToCartButton = new JButton("Add to Cart");
        
        panel.add(new JLabel("Welcome Customer!"), BorderLayout.NORTH);
        panel.add(new JScrollPane(productList), BorderLayout.CENTER);
        panel.add(addToCartButton, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createSellerPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JTextField productNameField = new JTextField(20);
        JTextField priceField = new JTextField(10);
        JTextField quantityField = new JTextField(10);
        JButton addProductButton = new JButton("Add Product");

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Product Name:"), gbc);
        gbc.gridx = 1;
        panel.add(productNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        panel.add(priceField, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        panel.add(quantityField, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(addProductButton, gbc);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ECommerceGUI::new);
    }
}

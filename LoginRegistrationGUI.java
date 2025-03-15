import java.awt.*;
import javax.swing.*;

public class LoginRegistrationGUI {
    private JFrame frame;
    private JPanel loginPanel;
    private JPanel registerPanel;

    // Login components
    private JTextField loginEmailField;
    private JPasswordField loginPasswordField;

    // Registration components
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField registerEmailField;
    private JPasswordField registerPasswordField;
    SellerData seller = new SellerData(null, null, null, null);
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginRegistrationGUI gui = new LoginRegistrationGUI();
            //gui.createAndShowGUI();
            gui.createAndShowGUI();
            //gui.createFrame();
        });
    }

    public void createAndShowGUI() {
        createFrame();
        createLoginPanel();
        createRegisterPanel();
        
        // Add login panel as default
        frame.add(loginPanel);
        frame.setVisible(true);
    }

    public void createFrame() {
        frame = new JFrame("User Authentication");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
    }

    public void createLoginPanel() {
        loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Email label and field
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Enter Email:"), gbc);

        gbc.gridx = 1;
        loginEmailField = new JTextField(20);
        loginPanel.add(loginEmailField, gbc);

        // Password label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Enter Password:"), gbc);

        gbc.gridx = 1;
        loginPasswordField = new JPasswordField(20);
        loginPanel.add(loginPasswordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("Login");
        JButton switchToRegisterButton = new JButton("Register");

        buttonPanel.add(loginButton);
        buttonPanel.add(switchToRegisterButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(buttonPanel, gbc);

        // Action listeners
        loginButton.addActionListener(e -> handleLogin());
        switchToRegisterButton.addActionListener(e -> switchToRegisterPanel());
    }

    public void createRegisterPanel() {
        registerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // First Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        registerPanel.add(new JLabel("Enter First Name:"), gbc);

        gbc.gridx = 1;
        firstNameField = new JTextField(20);
        registerPanel.add(firstNameField, gbc);

        // Last Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        registerPanel.add(new JLabel("Enter Last Name:"), gbc);

        gbc.gridx = 1;
        lastNameField = new JTextField(20);
        registerPanel.add(lastNameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        registerPanel.add(new JLabel("Enter Email:"), gbc);

        gbc.gridx = 1;
        registerEmailField = new JTextField(20);
        registerPanel.add(registerEmailField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        registerPanel.add(new JLabel("Enter Password:"), gbc);

        gbc.gridx = 1;
        registerPasswordField = new JPasswordField(20);
        registerPanel.add(registerPasswordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton registerButton = new JButton("Register");
        JButton switchToLoginButton = new JButton("Back to Login");

        buttonPanel.add(registerButton);
        buttonPanel.add(switchToLoginButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        registerPanel.add(buttonPanel, gbc);

        // Action listeners
        registerButton.addActionListener(e -> handleRegistration());
        switchToLoginButton.addActionListener(e -> switchToLoginPanel());
    }

    public void switchToRegisterPanel() {
        frame.getContentPane().removeAll();
        frame.add(registerPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void switchToLoginPanel() {
        frame.getContentPane().removeAll();
        frame.add(loginPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void handleLogin() {
        String email = loginEmailField.getText();
        String password = new String(loginPasswordField.getPassword());
        
        // Add your login validation logic here
        // JOptionPane.showMessageDialog(frame, 
        //     "Login attempt with:\nEmail: " + email + "\nPassword: " + password,
        //     "Login", JOptionPane.INFORMATION_MESSAGE);
    }

    public void handleRegistration() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = registerEmailField.getText();
        String password = new String(registerPasswordField.getPassword());
        
        // Add your registration validation logic here
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(frame,
            "Registration attempt with:\nFirst Name: " + firstName +
            "\nLast Name: " + lastName + "\nEmail: " + email +
            "\nPassword: " + password,
            "Registration", JOptionPane.INFORMATION_MESSAGE);
        }else{
            seller.register(firstName, lastName, email, password);
            //firstNameField = "";
            //registerMessageLabel.setText("Registration successful!");
        }
    }
}
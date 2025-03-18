import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Customer {
    private JFrame frame;
    private JPanel mainPanel, loginPanel, registerPanel;
    private JTextField loginEmailField, firstNameField, lastNameField, phoneField, emailField;
    private JPasswordField loginPasswordField, passwordField;
    private JLabel loginMessageLabel, registerMessageLabel;
    private CardLayout cardLayout;

    public static void main(String[] args) {
        new Customer();
    }

    public Customer() {
        createFrame();
        createPanels();
        createLoginComponents();
        createRegisterComponents();
        addPanelsToMain();
        addActionListeners();
        finalizeFrame();
    }

    private void createFrame() {
        frame = new JFrame("Customer Authentication");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new CardLayout());
        frame.setLocationRelativeTo(null);
    }

    private void createPanels() {
        mainPanel = new JPanel(new CardLayout());
        loginPanel = new JPanel(null);
        registerPanel = new JPanel(null);
        cardLayout = (CardLayout) mainPanel.getLayout();
    }

    private void createLoginComponents() {
        JLabel loginEmailLabel = new JLabel("Enter Email:");
        loginEmailField = new JTextField(20);
        JLabel loginPasswordLabel = new JLabel("Enter Password:");
        loginPasswordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        loginMessageLabel = new JLabel("");
        JButton switchToRegister = new JButton("Go to Register");

        loginEmailLabel.setBounds(50, 30, 100, 25);
        loginEmailField.setBounds(180, 30, 150, 25);
        loginPasswordLabel.setBounds(50, 70, 100, 25);
        loginPasswordField.setBounds(180, 70, 150, 25);
        loginButton.setBounds(150, 110, 100, 30);
        loginMessageLabel.setBounds(100, 150, 250, 25);
        switchToRegister.setBounds(130, 190, 140, 30);

        loginPanel.add(loginEmailLabel);
        loginPanel.add(loginEmailField);
        loginPanel.add(loginPasswordLabel);
        loginPanel.add(loginPasswordField);
        loginPanel.add(loginButton);
        loginPanel.add(loginMessageLabel);
        loginPanel.add(switchToRegister);

        switchToRegister.addActionListener(e -> cardLayout.show(mainPanel, "Register"));
        loginButton.addActionListener(this::handleLogin);
    }

    private void createRegisterComponents() {
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(20);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField(20);
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JButton registerButton = new JButton("Register");
        registerMessageLabel = new JLabel("");
        JButton switchToLogin = new JButton("Go to Login");

        firstNameLabel.setBounds(50, 30, 100, 25);
        firstNameField.setBounds(180, 30, 150, 25);
        lastNameLabel.setBounds(50, 70, 100, 25);
        lastNameField.setBounds(180, 70, 150, 25);
        phoneLabel.setBounds(50, 110, 100, 25);
        phoneField.setBounds(180, 110, 150, 25);
        emailLabel.setBounds(50, 150, 100, 25);
        emailField.setBounds(180, 150, 150, 25);
        passwordLabel.setBounds(50, 190, 100, 25);
        passwordField.setBounds(180, 190, 150, 25);
        registerButton.setBounds(150, 230, 100, 30);
        registerMessageLabel.setBounds(100, 270, 250, 25);
        switchToLogin.setBounds(130, 310, 140, 30);

        registerPanel.add(firstNameLabel);
        registerPanel.add(firstNameField);
        registerPanel.add(lastNameLabel);
        registerPanel.add(lastNameField);
        registerPanel.add(phoneLabel);
        registerPanel.add(phoneField);
        registerPanel.add(emailLabel);
        registerPanel.add(emailField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(registerButton);
        registerPanel.add(registerMessageLabel);
        registerPanel.add(switchToLogin);

        switchToLogin.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        registerButton.addActionListener(this::handleRegistration);
    }

    private void addPanelsToMain() {
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");
        frame.add(mainPanel);
    }

    private void addActionListeners() {
        // Additional action listeners if needed
    }

    private void handleLogin(ActionEvent e) {
        String email = loginEmailField.getText();
        String password = new String(loginPasswordField.getPassword());
        if (email.equals("customer@example.com") && password.equals("password123")) {
            loginMessageLabel.setText("Login successful!");
        } else {
            loginMessageLabel.setText("Invalid email or password.");
        }
    }

    private void handleRegistration(ActionEvent e) {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
            phoneField.getText().isEmpty() || emailField.getText().isEmpty() ||
            passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            registerMessageLabel.setText("Registration successful!");
            clearRegistrationFields();
        }
    }

    private void clearRegistrationFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    private void finalizeFrame() {
        frame.setVisible(true);
    }
}

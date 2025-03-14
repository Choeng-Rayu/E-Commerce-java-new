// import java.awt.GridBagConstraints;
// import java.awt.GridBagLayout;
// import java.awt.Insets;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import javax.swing.JButton;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JOptionPane;
// import javax.swing.JPanel;
// import javax.swing.JPasswordField;
// import javax.swing.JTextField;

// public class GUI {
//     // public static void LoginGUI() {
//     //     // Create JFrame
//     //     JFrame frame = new JFrame("Customer Login");
//     //     frame.setSize(400, 250);
//     //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//     //     frame.setLayout(new GridBagLayout());
        
//     //     GridBagConstraints gbc = new GridBagConstraints();
//     //     gbc.insets = new Insets(5, 5, 5, 5);
//     //     gbc.fill = GridBagConstraints.HORIZONTAL;

//     //     // Email Label and Field
//     //     JLabel emailLabel = new JLabel("Enter Email:");
//     //     gbc.gridx = 0;
//     //     gbc.gridy = 0;
//     //     frame.add(emailLabel, gbc);
        
//     //     JTextField emailField = new JTextField(20);
//     //     gbc.gridx = 1;
//     //     frame.add(emailField, gbc);

//     //     // Password Label and Field
//     //     JLabel passwordLabel = new JLabel("Enter Password:");
//     //     gbc.gridx = 0;
//     //     gbc.gridy = 1;
//     //     frame.add(passwordLabel, gbc);
        
//     //     JPasswordField passwordField = new JPasswordField(20);
//     //     gbc.gridx = 1;
//     //     frame.add(passwordField, gbc);
        
//     //     // Login Button
//     //     JButton loginButton = new JButton("Login");
//     //     gbc.gridx = 1;
//     //     gbc.gridy = 2;
//     //     frame.add(loginButton, gbc);
        
//     //     // Message Label
//     //     JLabel messageLabel = new JLabel("");
//     //     gbc.gridx = 1;
//     //     gbc.gridy = 3;
//     //     frame.add(messageLabel, gbc);
        
//     //     // Button Action
//     //     loginButton.addActionListener(new ActionListener() {
//     //         @Override
//     //         public void actionPerformed(ActionEvent e) {
//     //             String email = emailField.getText();
//     //             String password = new String(passwordField.getPassword());
//     //             UserData seller = new SellerData("", "", "", "");
                
//     //             // Placeholder login validation (replace with real validation)
//     //             if (seller.login(email, password)) {
//     //                 messageLabel.setText("Login successful!");
//     //             } else {
//     //                 messageLabel.setText("Invalid email or password.");
//     //             }
                
//     //         }
//     //     });
        
//     //     // Show Frame
//     //     frame.setLocationRelativeTo(null);
//     //     frame.setVisible(true);
//     // }

    
// }





import java.awt.*;
import javax.swing.*;

public class CustomerAuthGUI {
    public static void main(String[] args) {
        new CustomerAuthGUI();
    }

    public CustomerAuthGUI() {
        SellerData s = new SellerData(null, null, null, null);
        JFrame frame = new JFrame("Customer Authentication");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new CardLayout());
        
        JPanel mainPanel = new JPanel(new CardLayout());
        JPanel loginPanel = new JPanel(null);
        JPanel registerPanel = new JPanel(null);
        
        // Login Components
        JLabel loginEmailLabel = new JLabel("Enter Email:");
        JTextField loginEmailField = new JTextField(20);
        JLabel loginPasswordLabel = new JLabel("Enter Password:");
        JPasswordField loginPasswordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JLabel loginMessageLabel = new JLabel("");
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

        // Register Components
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(20);
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField(20);
        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton registerButton = new JButton("Register");
        JLabel registerMessageLabel = new JLabel("");
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
        
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");
        frame.add(mainPanel);
        
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        
        loginButton.addActionListener(e -> {
            String email = loginEmailField.getText();
            String password = new String(loginPasswordField.getPassword());

            if (s.login(email, password)) {
                loginMessageLabel.setText("Login successful!");
            } else {
                loginMessageLabel.setText("Invalid email or password.");
            }
        });
        
        registerButton.addActionListener(e -> {
            if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || 
                phoneField.getText().isEmpty() || emailField.getText().isEmpty() ||
                passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            }//else {
            //     registerMessageLabel.setText("Registration successful!");
            // }
            s.register(firstNameField.getText(), lastNameField.getText(), emailField.getText(), new String(passwordField.getPassword()));
            
        });
        
        switchToRegister.addActionListener(e -> cl.show(mainPanel, "Register"));
        switchToLogin.addActionListener(e -> cl.show(mainPanel, "Login"));
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
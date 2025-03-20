import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminExtends extends UserData {
	public AdminExtends() {
        super("Choeng", "Rayu", "choengrayu@gmail.com","12345");
	}    
    public static void main(String[] args) {
        AdminExtends a = new AdminExtends();
        a.adminMenu();
    }

    public void adminMenu() {
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Admin Menu");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    
        // Create a JPanel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        // Create buttons for each option
        JButton exitButton = new JButton("0. Exit");
        JButton logoutButton = new JButton("1. Log out");
        JButton checkProfileButton = new JButton("2. Check Profile Account");
        JButton modifyAccountButton = new JButton("3. Modify Account");
        JButton modifyPasswordButton = new JButton("4. Modify Password");
        JButton productManagementButton = new JButton("5. Product Management");
        JButton sellerManagementButton = new JButton("6. Seller Management");
        JButton customerManagementButton = new JButton("8. Customer Management");
    
        // Add buttons to the panel
        panel.add(exitButton);
        panel.add(logoutButton);
        panel.add(checkProfileButton);
        panel.add(modifyAccountButton);
        panel.add(modifyPasswordButton);
        panel.add(productManagementButton);
        panel.add(sellerManagementButton);
        panel.add(customerManagementButton);
    
        // Add action listeners to buttons
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
            }
        });
    
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window (log out)
            }
        });
    
        checkProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailInput = JOptionPane.showInputDialog(frame, "Enter Admin's email to verify:");
                if (emailInput != null) {
                    AdminExtends ad = new AdminExtends();
                    ad.checkProfile(emailInput);
                }
            }
        });
    
        modifyAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailInput = JOptionPane.showInputDialog(frame, "Enter Admin's email:");
                String passwordInput = JOptionPane.showInputDialog(frame, "Enter Admin's password:");
                if (emailInput != null && passwordInput != null) {
                    AdminExtends ad = new AdminExtends();
                    ad.modifyAccount(emailInput, passwordInput);
                }
            }
        });
    
        modifyPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailInput = JOptionPane.showInputDialog(frame, "Enter Admin's email:");
                String passwordInput = JOptionPane.showInputDialog(frame, "Enter Admin's password:");
                if (emailInput != null && passwordInput != null) {
                    AdminExtends ad = new AdminExtends();
                    ad.modifyPasswordString(emailInput, passwordInput);
                }
            }
        });
    
        productManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product p = new Product(0, "", 0, 0, "");
                p.productManagement(); // Open the product management menu
            }
        });
    
        sellerManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SellerData sd = new SellerData(firstName, lastName, email, email);
                sd.SellerManagement(); // Open the seller management menu
            }
        });
    
        customerManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerData cd = new CustomerData("", "", "", "");
                cd.customerManagement(); // Open the customer management menu
            }
        });
    
        // Add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }
    @Override
    public boolean login(String email, String password) {
        
            if (email.equals(this.email) && password.equals(this.getPassword())) {
                //System.out.println("Login Successful! Welcome, " + this.firstName + " " + this.lastName + "!");
                JOptionPane.showMessageDialog(null, "Login Successful! Welcome, " + this.firstName + " " + this.lastName + "!");
                return true;
            }
        JOptionPane.showMessageDialog(null, "Login failed. Incorrect email or password.");
        //        System.out.println("Login failed. Incorrect email or password.");
        return false;
    }
    public void adminLogIn() {
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Admin Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    
        // Create a JPanel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        // Create buttons for each option
        JButton exitButton = new JButton("0. Exit");
        JButton backButton = new JButton("1. Back to Previous Menu");
        JButton loginButton = new JButton("2. Log In");
    
        // Add buttons to the panel
        panel.add(exitButton);
        panel.add(backButton);
        panel.add(loginButton);
    
        // Add action listeners to buttons
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Exiting...");
                System.exit(0);
            }
        });
    
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
            }
        });
    
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Input fields for login
                String emailInputAdmin = JOptionPane.showInputDialog(frame, "Enter email:");
                String passwordInput = JOptionPane.showInputDialog(frame, "Enter password:");
    
                if (emailInputAdmin != null && passwordInput != null) {
                    if (login(emailInputAdmin, passwordInput)) {
                        frame.dispose(); // Close the login window
                        adminMenu(); // Open the admin menu
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid email or password. Please try again.");
                    }
                }
            }
        });
    
        // Add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }
}
// Purpose: This file contains the SellerData class which extends UserData and implements the authetication interface
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

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

public class SellerData extends UserData{
    public static int totalseller = 0; // total should be static
    public static ArrayList<SellerData> sellers = new ArrayList<>();
    int id;
    public static String pushByName;
    // Constructor
    public SellerData(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.id = ++totalseller; // Correctly increment static total
    }
    @Override
    public void modifyAccount(String email, String passwordInputString) {
        // Iterate through the list of sellers
        for (SellerData seller : sellers) {
            if (seller.email.equals(email) && seller.getPassword().equals(passwordInputString)) {
                // Create input dialogs for new account details
                String newFirstName = JOptionPane.showInputDialog(null, "Enter new first name:");
                String newLastName = JOptionPane.showInputDialog(null, "Enter new last name:");
                String newEmail = JOptionPane.showInputDialog(null, "Enter new email:");
                String newPassword = JOptionPane.showInputDialog(null, "Enter new password:");

                // Check if the user provided all inputs (did not cancel the dialogs)
                if (newFirstName != null && newLastName != null && newEmail != null && newPassword != null) {
                    // Update seller details
                    seller.firstName = newFirstName;
                    seller.lastName = newLastName;
                    seller.email = newEmail;
                    seller.setPassword(newPassword);
                    setCurrentPw(newPassword);
                    pushByName = newFirstName + " " + newLastName;

                    // Show success message
                    JOptionPane.showMessageDialog(null, "Account was modified successfully!");
                    return; // Exit the method after modifying the account
                } else {
                    // If the user cancels any input dialog, show a message
                    JOptionPane.showMessageDialog(null, "Modification canceled.");
                    return;
                }
            }
        }
        // If no matching seller is found, show an error message
        JOptionPane.showMessageDialog(null, "Incorrect email or password!");
    }

    @Override
    public void modifyPasswordString(String email, String oldPassword) {
        for (SellerData seller : sellers) {
            if (seller.email.equals(email) && seller.getPassword().equals(oldPassword)) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter new password: ");
                String newPassword = scanner.nextLine();
                seller.setPassword(newPassword);
                setCurrentPw(newPassword);
                System.out.println("Password was changed successfully");
                return;
            }
        }
        System.out.println("Incorrect email or password");
    }
    @Override
    public boolean login(String email, String password) {
        for (SellerData seller : sellers) {
            if (seller.email.equals(email) && seller.getPassword().equals(password)) {
                System.out.println("Login Successful! Welcome, " + seller.firstName + " " + seller.lastName + "!");
                setCurrentPw(password);
                //SellerData.passwordCurrentLogin = getCurrentPw();
                pushByName = seller.firstName + " " + seller.lastName;
                return true;
            }
        }
        System.out.println("Login failed. Incorrect email or password.");
        return false;
    }

    @Override
    public void register(String firstName, String lastName, String email, String password) {
        for (SellerData seller : sellers) {
            if (seller.email.equals(email)) { 
                JOptionPane.showMessageDialog(null, "Email already exists. Please use a different email.");
                return; // Exit the method if email already exists
            }
        }
        SellerData newSeller = new SellerData(firstName, lastName, email, password);
        DatabaseHandler.insertSeller(newSeller);
        sellers.add(newSeller);
        JOptionPane.showMessageDialog(null, "Sign-up successful! Please login again for this account, " + newSeller.firstName + " " + newSeller.lastName + "!");
    }
    @Override
    public String toString() {
        return "Seller ID: " + id + "\nFirst Name: " + firstName + "\nLast Name: " + lastName + "\nEmail: " + email + "\nRegistered date: " + dateRegister;
    }

    @Override
    public String checkProfile(String email) {
        for (SellerData seller : sellers) {
            if (seller.email.equals(email)) {
                System.out.println("==== PROFILE ====");
                return seller.toString();
            }
        }
        return "Incorrect email or password";
    }
    public static int getTotalSeller() {
        return totalseller;
    }
    
    public void menuSeller() {
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Seller Menu");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Create a JPanel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create buttons for each option
        JButton exitButton = new JButton("0. Exit");
        JButton loginButton = new JButton("1. Login");
        JButton registerButton = new JButton("2. Register");
        JButton backButton = new JButton("3. Back to Previous Menu");

        // Add buttons to the panel
        panel.add(exitButton);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(backButton);

        // Add action listeners to buttons
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Exiting...");
                System.exit(0);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Input fields for login
                String emailInput = JOptionPane.showInputDialog(frame, "Enter email:");
                String passwordInput = JOptionPane.showInputDialog(frame, "Enter password:");

                if (emailInput != null && passwordInput != null) {
                    if (login(emailInput, passwordInput)) {
                        menuSellerAccount(); // Call the seller account menu if login is successful
                    } else {
                        JOptionPane.showMessageDialog(frame, "Login failed. Please try again.");
                    }
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Input fields for registration
                String firstNameInput = JOptionPane.showInputDialog(frame, "Enter first name:");
                String lastNameInput = JOptionPane.showInputDialog(frame, "Enter last name:");
                String emailInput = JOptionPane.showInputDialog(frame, "Enter email:");
                String passwordInput = JOptionPane.showInputDialog(frame, "Enter password:");

                if (firstNameInput != null && lastNameInput != null && emailInput != null && passwordInput != null) {
                    register(firstNameInput, lastNameInput, emailInput, passwordInput);
                    //JOptionPane.showMessageDialog(frame, "Registration successful!");
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
    public void menuSellerAccount() {
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Seller Account Menu");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    
        // Create a JPanel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        // Create buttons for each option
        JButton exitButton = new JButton("0. Exit");
        JButton checkProfileButton = new JButton("1. Check Profile Account");
        JButton modifyAccountButton = new JButton("2. Modify Account");
        JButton modifyPasswordButton = new JButton("3. Modify Password");
        JButton productManagementButton = new JButton("4. Product Management");
        JButton logoutButton = new JButton("5. Log out");
    
        // Add buttons to the panel
        panel.add(exitButton);
        panel.add(checkProfileButton);
        panel.add(modifyAccountButton);
        panel.add(modifyPasswordButton);
        panel.add(productManagementButton);
        panel.add(logoutButton);
    
        // Add action listeners to buttons
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Exiting...");
                System.exit(0);
            }
        });
    
        checkProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailInput = JOptionPane.showInputDialog(frame, "Please enter email again to verify:");
                if (emailInput != null) {
                    String profileInfo = checkProfile(emailInput);
                    JOptionPane.showMessageDialog(frame, profileInfo);
                }
            }
        });
    
        modifyAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailInput = JOptionPane.showInputDialog(frame, "Enter email:");
                String passwordInput = JOptionPane.showInputDialog(frame, "Enter password:");
                if (emailInput != null && passwordInput != null) {
                    modifyAccount(emailInput, passwordInput);
                    JOptionPane.showMessageDialog(frame, "Account modified successfully!");
                }
            }
        });
    
        modifyPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailInput = JOptionPane.showInputDialog(frame, "Enter email:");
                String passwordInput = JOptionPane.showInputDialog(frame, "Enter password:");
                if (emailInput != null && passwordInput != null) {
                    modifyPasswordString(emailInput, passwordInput);
                    JOptionPane.showMessageDialog(frame, "Password modified successfully!");
                }
            }
        });
    
        productManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product p = new Product(0, "", 0.0, 0, pushByName);
                p.productManagement(); // Open the product management menu
            }
        });
    
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
            }
        });
    
        // Add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }

    public void deleteSeller() {
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Delete Seller");
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
                    String input = indexField.getText();
                    NumberOnlyException.validateNumber(input, "^[0-9]+$");
                    int i = Integer.parseInt(input);

                    // Verify admin credentials
                    AdminExtends ad = new AdminExtends();
                    if (ad.login(email, getPassword())) {
                        sellers.remove(i); // Remove the seller at the specified index
                        JOptionPane.showMessageDialog(frame, "Account was deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Password! Account was not deleted.");
                    }
                } catch (NumberOnlyException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Index. Account was not deleted.");
                }
            }
        });

        // Add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }
    public void displayAllSeller() {
    if (sellers.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No Seller Accounts!");
        return;
    }

    // Create a JFrame for the GUI
    JFrame frame = new JFrame("Seller List");
    frame.setSize(800, 400);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    // Create a table model with columns
    String[] columns = {"No", "ID", "First Name", "Last Name", "Password", "Email"};
    DefaultTableModel model = new DefaultTableModel(columns, 0);

    // Populate the table model with seller data
    int i = 0;
    for (SellerData s : sellers) {
        Object[] row = {i, s.id, s.firstName, s.lastName, s.getPassword(), s.email};
        model.addRow(row);
        i++;
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
    public static void main(String[] args) {
        SellerData c = new SellerData(null, null, null, null);
        SellerData newSeller = new SellerData("choeng", "rayu12356", "choengrayu@gmail.com", "123");
        SellerData newSeller1SellerData = new SellerData("choeng1", "rayu", "email2", "1234");
        SellerData newSeller2SellerData = new SellerData("choeng1", "rayu", "email3", "1236");
        sellers.add(newSeller);
        sellers.add(newSeller1SellerData);
        sellers.add(newSeller2SellerData);
        c.displayAllSeller();
        c.deleteSeller();
        c.displayAllSeller();
    }
    public void SellerManagement() {
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Seller Management");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    
        // Create a JPanel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        // Create buttons for each option
        JButton exitButton = new JButton("0. Exit");
        JButton backButton = new JButton("1. Back to Previous Menu");
        JButton showAllButton = new JButton("2. Show all Sellers");
        JButton searchButton = new JButton("3. Search Sellers");
        JButton deleteButton = new JButton("4. Delete Seller");
        JButton modifyAccountButton = new JButton("5. Modify Account");
        JButton modifyPasswordButton = new JButton("6. Modify Password");
        JButton getReportButton = new JButton("7. Get Report");
    
        // Add buttons to the panel
        panel.add(exitButton);
        panel.add(backButton);
        panel.add(showAllButton);
        panel.add(searchButton);
        panel.add(deleteButton);
        panel.add(modifyAccountButton);
        panel.add(modifyPasswordButton);
        panel.add(getReportButton);
    
        // Add action listeners to buttons
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You have exited the program!");
                System.exit(0);
            }
        });
    
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
            }
        });
    
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sellers.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Seller Accounts!");
                } else {
                    displayAllSeller();
                }
            }
        });
    
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sellers.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Seller Accounts!");
                } else {
                    String emailInput = JOptionPane.showInputDialog(frame, "Enter Email to search:");
                    if (emailInput != null) {
                        checkProfile(emailInput);
                    }
                }
            }
        });
    
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sellers.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Seller Accounts!");
                } else {
                    displayAllSeller();
                    deleteSeller();
                }
            }
        });
    
        modifyAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sellers.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Seller Accounts!");
                } else {
                    String emailInput = JOptionPane.showInputDialog(frame, "Enter email:");
                    String passwordInput = JOptionPane.showInputDialog(frame, "Enter password:");
                    if (emailInput != null && passwordInput != null) {
                        modifyAccount(emailInput, passwordInput);
                    }
                }
            }
        });
    
        modifyPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sellers.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Seller Accounts!");
                } else {
                    String emailInput = JOptionPane.showInputDialog(frame, "Enter email:");
                    String passwordInput = JOptionPane.showInputDialog(frame, "Enter password:");
                    if (emailInput != null && passwordInput != null) {
                        modifyPasswordString(emailInput, passwordInput);
                    }
                }
            }
        });
    
        getReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Total Sellers: " + getTotalSeller());
            }
        });
    
        // Add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }
}
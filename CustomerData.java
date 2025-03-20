import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

public class CustomerData extends UserData {
    int id;
    private static int totalcd = 0;
    public static String CurrentNameLogin;
    //private String currentPassword;
    //private ArrayList<Product> cart = new ArrayList<>();
    // List to store registered cd
    public static ArrayList<CustomerData> cd = new ArrayList<>();

    // Constructor (Only initializes the object, does not add to the list)
    public CustomerData(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.id = ++totalcd;
        
    }
    @Override
    public void register(String firstName, String lastName, String email, String password) {
        CustomerData newc = new CustomerData(firstName, lastName, email, password);
        for (CustomerData c : cd) {
            if (c.email.equals(email)) {
                //System.out.println("Sign-up failed. Email already exists.");
                JOptionPane.showMessageDialog(null, "Sign-up failed. Email already exists.");
                return; // Exit the method if email already exists
            }
        }
        cd.add(newc);
        DatabaseHandler.insertCustomer(newc);
        JOptionPane.showMessageDialog(null, "Registration successful!");
        //System.out.println("Please login again for this new account " + firstName + " " + lastName + "!");
    }

    @Override
    public boolean login(String email, String passwordInput) {
        for (CustomerData c : cd) {
            if (c.email.equals(email) && c.getPassword().equals(passwordInput)) {
                System.out.println("Login Successful! Welcome, " + c.firstName + " " + c.lastName + "!");
                CurrentNameLogin = c.firstName + " " + c.lastName;
                setCurrentPw(c.getPassword());
                return true;
            }
        }
        System.out.println("Login failed. Incorrect email or password.");
        return false;
    }
    @Override
    public String toString() {
        return "ID: " + id + "\nFirst Name: " + firstName + "\nLast Name: " + lastName + "\nEmail: " + email + "Date Registerd: " + dateRegister;
    }

    public void deleteCustomer() {
        Scanner s = new Scanner(System.in);
        try  {
            System.out.println("Select Index to Delete: ");
            String iString = s.nextLine();
            NumberOnlyException.validateNumber(iString, "^[0-9]+$");
            int i = Integer.parseInt(iString);
            AdminExtends ad = new AdminExtends();
            if (ad.verifyPassword()) {
                cd.remove(i);
                System.out.println("Account was deleted Successfully!");
            } else {
                System.out.println("Invalid Password!, Account was not deleted");
            }
        } catch (NumberOnlyException e) {
            System.err.println(e.getMessage());
        }
    }

    public void displayAllCustomers() {
        if (cd.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Customer Accounts!");
            return;
        }

        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Customer List");
        frame.setSize(1000, 400); // Adjusted size to accommodate more columns
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Create a table model with columns
        String[] columns = {"No", "ID", "First Name", "Last Name", "Password", "Email", "Date of Registered"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Populate the table model with customer data
        int i = 0;
        for (CustomerData c : cd) {
            Object[] row = {i, c.id, c.firstName, c.lastName, c.getPassword(), c.email, c.dateRegister};
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

    public static int getTotalCustomer() {
        return cd.size();
    }

    @Override
    public String checkProfile(String email) {
        for (CustomerData c : cd) {
            if (c.email.equals(email)) {
                //System.out.println("");
                System.out.println("==== PROFILE ====");
                return c.toString();
            }
        }
        return "Not Found";
    }

  

    public void customerManagement() {
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Customer Management");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Create a JPanel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create buttons for each option
        JButton exitButton = new JButton("0. Exit");
        JButton backButton = new JButton("1. Back to Previous Menu");
        JButton showAllButton = new JButton("2. Show all Customers");
        JButton searchButton = new JButton("3. Search Customers");
        JButton deleteButton = new JButton("4. Delete Customer");
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
                if (cd.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Customer Accounts!");
                } else {
                    displayAllCustomers();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cd.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Customer Accounts!");
                } else {
                    String emailInput = JOptionPane.showInputDialog(frame, "Enter Email to verify:");
                    checkProfile(emailInput);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cd.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Customer Accounts!");
                } else {
                    displayAllCustomers();
                    deleteCustomer();
                }
            }
        });

        modifyAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cd.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Customer Accounts!");
                } else {
                    String emailInput = JOptionPane.showInputDialog(frame, "Enter email:");
                    String passwordInput = JOptionPane.showInputDialog(frame, "Enter password:");
                    modifyAccount(emailInput, passwordInput);
                }
            }
        });

        modifyPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cd.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Customer Accounts!");
                } else {
                    String emailInput = JOptionPane.showInputDialog(frame, "Enter email:");
                    String passwordInput = JOptionPane.showInputDialog(frame, "Enter password:");
                    modifyPasswordString(emailInput, passwordInput);
                }
            }
        });

        getReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Total Customers: " + getTotalCustomer());
            }
        });

        // Add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        CustomerData c = new CustomerData("1", "1", "1", "1");
        c.register("1", "1", "1", "1");
        
    }

    public void menuCustomer() {
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Customer Menu");
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
                        menu(); // Call the menu function if login is successful
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
    
    public void menu() {
        // Create a JFrame for the GUI
        JFrame frame = new JFrame("Customer Menu");
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
        JButton shoppingButton = new JButton("4. Shopping");
        JButton logoutButton = new JButton("5. Log out");

        // Add buttons to the panel
        panel.add(exitButton);
        panel.add(checkProfileButton);
        panel.add(modifyAccountButton);
        panel.add(modifyPasswordButton);
        panel.add(shoppingButton);
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

        shoppingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddToCart cart = new AddToCart(0, "", 0, 0, "");
                cart.menuShopping(); // Open the shopping menu
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AddToCart.CartisEmpty()) {
                    frame.dispose(); // Close the current window
                } else {
                    int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "Warning! Your cart will not save when you log out of the account!\nAre you sure?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        AddToCart.logoutClearCart("yes"); // Clear the cart
                        JOptionPane.showMessageDialog(frame, "Your cart was cleared!");
                        frame.dispose(); // Close the current window
                    } else {
                        JOptionPane.showMessageDialog(frame, "Your cart was not cleared!");
                    }
                }
            }
        });

        // Add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }
}
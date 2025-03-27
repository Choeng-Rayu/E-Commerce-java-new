import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.awt.BorderLayout;

public class CustomerData extends UserData {
    int id;
    private static int totalcd = 0;
    public static String CurrentNameLogin;
    public static ArrayList<CustomerData> cd = new ArrayList<>();

    // Default constructor
    public CustomerData() {
    }

    public CustomerData(int id, String firstName, String lastName, String email, String password, String dateRegistered) {
        super(firstName, lastName, email, password);
        this.id = cd.size()+1;
        this.dateRegister = dateRegistered;
    }
    // Constructor (initializes the object)
    public CustomerData(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.id = cd.size() + 1;
    }


    @Override
    public void register(String firstName, String lastName, String email, String password) {
        CustomerData newCustomer = new CustomerData(firstName, lastName, email, password);
        // Check if email already exists in the local list
        for (CustomerData c : cd) {
            if (c.email.equals(email)) {
                JOptionPane.showMessageDialog(null, "Sign-up failed. Email already exists.");
                return;
            }
        }
        cd.add(newCustomer);
        // Insert into the database
        String result = DatabaseHandler.insertCustomer(newCustomer);
        JOptionPane.showMessageDialog(null, result);
    }
    // Add a new constructor to match the required arguments
    public CustomerData(int id, String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.id = cd.size() + 1;    
    }
    public static void loadCustomersIntoStaticList() {
        List<CustomerData> customers = DatabaseHandler.loadAllCustomer();
        cd.clear(); // Clear existing entries (if any)
        cd.addAll(customers); // Copy all products from DB to static list
    }
    @Override
    public boolean login(String email, String passwordInput) {
        // First, check the local list of customers
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
        return "ID: " + id + "\nFirst Name: " + firstName + "\nLast Name: " + lastName +
               "\nEmail: " + email + "\nDate Registered: " + dateRegister;
    }

    // Delete customer by email (prompting via console for this example)
    public void deleteCustomer() {
        Scanner s = new Scanner(System.in);
        try {
            System.out.println("Enter Email of customer to delete: ");
            String emailToDelete = s.nextLine();
            // Verify admin password before deletion
            AdminExtends admin = new AdminExtends();
            if (admin.verifyPassword()) {
                CustomerData toRemove = null;
                for (CustomerData c : cd) {
                    if (c.email.equals(emailToDelete)) {
                        toRemove = c;
                        break;
                    }
                }
                if (toRemove != null) {
                    cd.remove(toRemove);
                    String result = DatabaseHandler.deleteCustomer(emailToDelete);
                    System.out.println(result);
                } else {
                    System.out.println("Customer not found.");
                }
            } else {
                System.out.println("Invalid Password! Customer was not deleted.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // Display all customers in a table GUI
    public void displayAllCustomers() {
        if (cd.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Customer Accounts!");
            return;
        }

        JFrame frame = new JFrame("Customer List");
        frame.setSize(1000, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        String[] columns = {"No", "ID", "First Name", "Last Name", "Password", "Email", "Date Registered"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        int i = 0;
        for (CustomerData c : cd) {
            Object[] row = {i, c.id, c.firstName, c.lastName, c.getPassword(), c.email, c.dateRegister};
            model.addRow(row);
            i++;
        }
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static int getTotalCustomer() {
        return cd.size();
    }

    @Override
    public String checkProfile(String email) {
        for (CustomerData c : cd) {
            if (c.email.equals(email)) {
                System.out.println("==== PROFILE ====");
                return c.toString();
            }
        }
        return "Not Found";
    }

    // Customer management GUI with various options
    public void customerManagement() {
        JFrame frame = new JFrame("Customer Management");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton exitButton = new JButton("0. Exit");
        JButton backButton = new JButton("1. Back to Previous Menu");
        JButton showAllButton = new JButton("2. Show all Customers");
        JButton searchButton = new JButton("3. Search Customers");
        JButton deleteButton = new JButton("4. Delete Customer");
        JButton modifyAccountButton = new JButton("5. Modify Account");
        JButton modifyPasswordButton = new JButton("6. Modify Password");
        JButton getReportButton = new JButton("7. Get Report");

        panel.add(exitButton);
        panel.add(backButton);
        panel.add(showAllButton);
        panel.add(searchButton);
        panel.add(deleteButton);
        panel.add(modifyAccountButton);
        panel.add(modifyPasswordButton);
        panel.add(getReportButton);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You have exited the program!");
                System.exit(0);
            }
        });

        backButton.addActionListener(e -> frame.dispose());

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
                    String profile = checkProfile(emailInput);
                    JOptionPane.showMessageDialog(frame, profile);
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
                    JOptionPane.showMessageDialog(frame, "Account modified successfully!");
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
                    JOptionPane.showMessageDialog(frame, "Password modified successfully!");
                }
            }
        });

        getReportButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Total Customers: " + getTotalCustomer()));

        frame.add(panel);
        frame.setVisible(true);
    }

    // Customer main menu GUI
    public void menuCustomer() {
        JFrame frame = new JFrame("Customer Menu");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton exitButton = new JButton("0. Exit");
        JButton loginButton = new JButton("1. Login");
        JButton registerButton = new JButton("2. Register");
        JButton backButton = new JButton("3. Back to Previous Menu");

        panel.add(exitButton);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(backButton);

        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Exiting...");
            System.exit(0);
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailInput = JOptionPane.showInputDialog(frame, "Enter email:");
                String passwordInput = JOptionPane.showInputDialog(frame, "Enter password:");
                if (emailInput != null && passwordInput != null) {
                    if (login(emailInput, passwordInput)) {
                        menu();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Login failed. Please try again.");
                    }
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstNameInput = JOptionPane.showInputDialog(frame, "Enter first name:");
                String lastNameInput = JOptionPane.showInputDialog(frame, "Enter last name:");
                String emailInput = JOptionPane.showInputDialog(frame, "Enter email:");
                String passwordInput = JOptionPane.showInputDialog(frame, "Enter password:");
                if (firstNameInput != null && lastNameInput != null && emailInput != null && passwordInput != null) {
                    register(firstNameInput, lastNameInput, emailInput, passwordInput);
                }
            }
        });

        backButton.addActionListener(e -> frame.dispose());
        frame.add(panel);
        frame.setVisible(true);
    }

    // Customer secondary menu after login
    public void menu() {
        JFrame frame = new JFrame("Customer Menu");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton exitButton = new JButton("0. Exit");
        JButton checkProfileButton = new JButton("1. Check Profile Account");
        JButton modifyAccountButton = new JButton("2. Modify Account");
        JButton modifyPasswordButton = new JButton("3. Modify Password");
        JButton shoppingButton = new JButton("4. Shopping");
        JButton logoutButton = new JButton("5. Log out");

        panel.add(exitButton);
        panel.add(checkProfileButton);
        panel.add(modifyAccountButton);
        panel.add(modifyPasswordButton);
        panel.add(shoppingButton);
        panel.add(logoutButton);

        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Exiting...");
            System.exit(0);
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
                cart.menuShopping();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AddToCart.CartisEmpty()) {
                    frame.dispose();
                } else {
                    int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "Warning! Your cart will not save when you log out of the account!\nAre you sure?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        AddToCart.logoutClearCart("yes");
                        JOptionPane.showMessageDialog(frame, "Your cart was cleared!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Your cart was not cleared!");
                    }
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}

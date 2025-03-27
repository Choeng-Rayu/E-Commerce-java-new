import java.util.ArrayList;
import java.util.List;
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
import javax.xml.crypto.Data;

public class SellerData extends UserData {
    public static int totalseller = 0;
    public static ArrayList<SellerData> sellers = new ArrayList<>();
    int id;
    public static String pushByName;

    // Default constructor
    public SellerData() {
    }

    // Constructor with parameters
    public SellerData(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.id = ++totalseller;
    }
    // Constructor to match the required parameters
    public SellerData(int id, String firstName, String lastName, String email, String password) {
        
        super(firstName, lastName, email, password);
        this.id = id;
        //this.dateRegistered = dateRegistered;
    }
    public static ArrayList<SellerData> getSellers(){
        return sellers;
    }
    public static void loadSellersIntoStaticList() {
        List<SellerData> allSellers = DatabaseHandler.loadAllSeller();
        sellers.clear(); // Clear existing entries (if any)
        sellers.addAll(allSellers); // Copy all products from DB to static list
    }
    @Override
    public void modifyAccount(String email, String passwordInputString) {
        // Iterate through the list of sellers to find the matching account
        for (SellerData seller : sellers) {
            if (seller.email.equals(email) && seller.getPassword().equals(passwordInputString)) {
                String newFirstName = JOptionPane.showInputDialog(null, "Enter new first name:");
                String newLastName = JOptionPane.showInputDialog(null, "Enter new last name:");
                String newEmail = JOptionPane.showInputDialog(null, "Enter new email:");
                String newPassword = JOptionPane.showInputDialog(null, "Enter new password:");
                if (newFirstName != null && newLastName != null && newEmail != null && newPassword != null) {
                    // Update seller details locally
                    seller.firstName = newFirstName;
                    seller.lastName = newLastName;
                    seller.email = newEmail;
                    seller.setPassword(newPassword);
                    setCurrentPw(newPassword);
                    pushByName = newFirstName + " " + newLastName;
                    // Update database record
                    String result = DatabaseHandler.updateSeller(seller);
                    JOptionPane.showMessageDialog(null, "Account was modified successfully!\n" + result);
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Modification canceled.");
                    return;
                }
            }
        }
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
                // Update in database
                String result = DatabaseHandler.updateSeller(seller);
                System.out.println("Password was changed successfully\n" + result);
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
                pushByName = seller.firstName + " " + seller.lastName;
                return true;
            }
        }
        System.out.println("Login failed. Incorrect email or password.");
        return false;
    }

    @Override
    public void register(String firstName, String lastName, String email, String password) {
        // Check if the email already exists
        for (SellerData seller : sellers) {
            if (seller.email.equals(email)) {
                JOptionPane.showMessageDialog(null, "Email already exists. Please use a different email.");
                return;
            }
        }
        SellerData newSeller = new SellerData(firstName, lastName, email, password);
        // Insert into the database
        String result = DatabaseHandler.insertSeller(newSeller);
        sellers.add(newSeller);
        JOptionPane.showMessageDialog(null, "Sign-up successful! Please login again for this account, " 
                                           + newSeller.firstName + " " + newSeller.lastName + "\n" + result);
    }
    @Override
    public String toString() {
        return "Seller ID: " + id + "\nFirst Name: " + firstName + "\nLast Name: " + lastName +
               "\nEmail: " + email + "\nRegistered date: " + dateRegister;
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

    // Seller menu for login and registration
    public void menuSeller() {
        JFrame frame = new JFrame("Seller Menu");
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
                        menuSellerAccount();
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

    // Seller account menu after login
    public void menuSellerAccount() {
        JFrame frame = new JFrame("Seller Account Menu");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        JButton exitButton = new JButton("0. Exit");
        JButton checkProfileButton = new JButton("1. Check Profile Account");
        JButton modifyAccountButton = new JButton("2. Modify Account");
        JButton modifyPasswordButton = new JButton("3. Modify Password");
        JButton productManagementButton = new JButton("4. Product Management");
        JButton logoutButton = new JButton("5. Log out");
    
        panel.add(exitButton);
        panel.add(checkProfileButton);
        panel.add(modifyAccountButton);
        panel.add(modifyPasswordButton);
        panel.add(productManagementButton);
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
    
        productManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product p = new Product(0, "", 0.0, 0, pushByName);
                p.productManagement();
            }
        });
    
        logoutButton.addActionListener(e -> frame.dispose());
    
        frame.add(panel);
        frame.setVisible(true);
    }

    // Delete seller with verification via admin credentials
    public void deleteSeller() {
        JFrame frame = new JFrame("Delete Seller");
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
                    String input = indexField.getText();
                    NumberOnlyException.validateNumber(input, "^[0-9]+$");
                    int i = Integer.parseInt(input);
                    // Verify admin credentials
                    AdminExtends ad = new AdminExtends();
                    if (ad.login(email, getPassword())) {
                        SellerData sellerToRemove = sellers.get(i);
                        sellers.remove(i);
                        String result = DatabaseHandler.deleteSeller(sellerToRemove.email);
                        JOptionPane.showMessageDialog(frame, "Account was deleted successfully!\n" + result);
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

        frame.add(panel);
        frame.setVisible(true);
    }

    // Display all sellers in a table GUI
    public void displayAllSeller() {
        if (sellers.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Seller Accounts!");
            return;
        }

        JFrame frame = new JFrame("Seller List");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        String[] columns = {"No", "ID", "First Name", "Last Name", "Password", "Email"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        int i = 0;
        for (SellerData s : sellers) {
            Object[] row = {i, s.id, s.firstName, s.lastName, s.getPassword(), s.email};
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

    public static void main(String[] args) {
        // Test sample data
        SellerData tester = new SellerData(null, null, null, null);
        SellerData newSeller = new SellerData("choeng", "rayu12356", "choengrayu@gmail.com", "123");
        SellerData newSeller1 = new SellerData("choeng1", "rayu", "email2", "1234");
        SellerData newSeller2 = new SellerData("choeng1", "rayu", "email3", "1236");
        sellers.add(newSeller);
        sellers.add(newSeller1);
        sellers.add(newSeller2);
        tester.displayAllSeller();
        tester.deleteSeller();
        tester.displayAllSeller();
    }

    // Seller management main menu GUI
    public void SellerManagement() {
        JFrame frame = new JFrame("Seller Management");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        JButton exitButton = new JButton("0. Exit");
        JButton backButton = new JButton("1. Back to Previous Menu");
        JButton showAllButton = new JButton("2. Show all Sellers");
        JButton searchButton = new JButton("3. Search Sellers");
        JButton deleteButton = new JButton("4. Delete Seller");
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
    
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "You have exited the program!");
            System.exit(0);
        });
    
        backButton.addActionListener(e -> frame.dispose());
    
        showAllButton.addActionListener(e -> {
            if (sellers.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No Seller Accounts!");
            } else {
                displayAllSeller();
            }
        });
    
        searchButton.addActionListener(e -> {
            if (sellers.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No Seller Accounts!");
            } else {
                String emailInput = JOptionPane.showInputDialog(frame, "Enter Email to search:");
                if (emailInput != null) {
                    String profile = checkProfile(emailInput);
                    JOptionPane.showMessageDialog(frame, profile);
                }
            }
        });
    
        deleteButton.addActionListener(e -> {
            if (sellers.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No Seller Accounts!");
            } else {
                displayAllSeller();
                deleteSeller();
            }
        });
    
        modifyAccountButton.addActionListener(e -> {
            if (sellers.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No Seller Accounts!");
            } else {
                String emailInput = JOptionPane.showInputDialog(frame, "Enter email:");
                String passwordInput = JOptionPane.showInputDialog(frame, "Enter password:");
                if (emailInput != null && passwordInput != null) {
                    modifyAccount(emailInput, passwordInput);
                }
            }
        });
    
        modifyPasswordButton.addActionListener(e -> {
            if (sellers.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No Seller Accounts!");
            } else {
                String emailInput = JOptionPane.showInputDialog(frame, "Enter email:");
                String passwordInput = JOptionPane.showInputDialog(frame, "Enter password:");
                if (emailInput != null && passwordInput != null) {
                    modifyPasswordString(emailInput, passwordInput);
                }
            }
        });
    
        getReportButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Total Sellers: " + getTotalSeller()));
    
        frame.add(panel);
        frame.setVisible(true);
    }
}

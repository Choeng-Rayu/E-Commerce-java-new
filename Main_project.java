import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main_project {
    public static void main(String[] args) {
        // Initialize user data
        UserData seller = new SellerData();
        UserData customer = new CustomerData();
        UserData admin = new AdminExtends();
        UserData[] users = {seller, customer, admin};

        // Create a JFrame for the main menu
        JFrame frame = new JFrame("E-Commerce System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Create a JPanel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create buttons for each option
        JButton exitButton = new JButton("0. Exit");
        JButton customerButton = new JButton("1. For Customer");
        JButton sellerButton = new JButton("2. For Seller");
        JButton adminButton = new JButton("3. For Admin");

        // Add buttons to the panel
        panel.add(exitButton);
        panel.add(customerButton);
        panel.add(sellerButton);
        panel.add(adminButton);

        // Add action listeners to buttons
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Exiting...");
                System.exit(0);
            }
        });

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CustomerData) users[1]).menuCustomer(); // Open the customer menu
            }
        });

        sellerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((SellerData) users[0]).menuSeller(); // Open the seller menu
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((AdminExtends) users[2]).adminLogIn(); // Open the admin login menu
            }
        });

        // Add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }
}
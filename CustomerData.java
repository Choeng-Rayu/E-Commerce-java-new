import java.util.ArrayList;
import java.util.Scanner;

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
        for (CustomerData c : cd) {
            if (c.email.equals(email)) {
                System.out.println("Sign-up failed. Email already exists.");
                return; // Exit the method if email already exists
            }
        }
        CustomerData newc = new CustomerData(firstName, lastName, email, password);
        cd.add(newc);
        System.out.println("Please login again for this new account " + firstName + " " + lastName + "!");
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
            System.out.println("No Customer Accounts!");
            return;
        }
        int i = 0;
        System.out.printf("%-5s %-9s %-16s %-13s %-13s %-13s %-20s\n", "No", "ID", "First Name", "Last Name", "Password", "Email", "Date of Registered");
        System.out.println("-----------------------------------------------------------------------");
        for (CustomerData c : cd) {
            System.out.printf("%-3d %-3s %-5d %-2s %-12s %-2s %-10s %-2s %-10s %-2s %-25s %-2s %-25s\n", i, "|", c.id, "|", c.firstName, "|", c.lastName, "|", c.getPassword(), "|", c.email, "|", c.dateRegister);
            i++;
        }
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
        Scanner sc = new Scanner(System.in);
        boolean customerManagement = true;
        while (customerManagement) {
            try {
                System.out.println("\n==== CUSTOMER MANAGEMENT ====");
                System.out.println("0. Exit");
                System.out.println("1. Back to Previous Menu");
                System.out.println("2. Show all Customers");
                System.out.println("3. Search Customers");
                System.out.println("4. Delete Customer");
                System.out.println("5. Modify account");
                System.out.println("6. Modify Password");
                System.out.println("7. Get report");
                System.out.print("Choose an option: ");
                String choice = sc.nextLine();
                NumberOnlyException.validateNumber(choice, "^[0-9]+$");
                int choice2 = Integer.parseInt(choice);
                switch (choice2) {
                    case 0 -> {
                        System.out.println("You were Exit the program!");
                        System.exit(0);
                    }
                    case 1 -> {
                        customerManagement = false;
                        break;
                    }
                    case 2 -> {
                        if (cd.isEmpty()) {
                            System.out.println("No Customer Accounts!");
                            break;
                        }
                        displayAllCustomers();
                        break;
                    }
                    case 3 -> {
                        if (cd.isEmpty()) {
                            System.out.println("No Customer Accounts!");
                            break;
                        }
                        System.out.println("Enter Email to verify: ");
                        String emailInput = sc.nextLine();
                        checkProfile(emailInput);
                        break;
                    }
                    case 4 -> {
                        if (cd.isEmpty()) {
                            System.out.println("No Customer Accounts!");
                            break;
                        }
                        displayAllCustomers();
                        deleteCustomer();
                        break;
                    }
                    case 5 -> {
                        if (cd.isEmpty()) {
                            System.out.println("No Customer Accounts!");
                            break;
                        }
                        System.out.print("Enter email: ");
                        String emailInput = sc.nextLine();
                        System.out.print("Enter password: ");
                        String passwordInput = sc.nextLine();
                        modifyAccount(emailInput, passwordInput);
                        break;
                    }
                    case 6 -> {
                        if (cd.isEmpty()) {
                            System.out.println("No Customer Accounts!");
                            break;
                        }
                        System.out.print("Enter email: ");
                        String emailInput = sc.nextLine();
                        System.out.print("Enter password: ");
                        String passwordInput = sc.nextLine();
                        modifyPasswordString(emailInput, passwordInput);
                        break;
                    }
                    case 7 -> {
                        System.out.println(getTotalCustomer());
                        break;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberOnlyException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        CustomerData c = new CustomerData("1", "1", "1", "1");
        c.register("1", "1", "1", "1");
        
    }

    public void menuCustomer() {
        boolean menuCustomer = true;
        Scanner scanner = new Scanner(System.in);
        while (menuCustomer) {
            try  {
                System.out.println("\n==== CUSTOMER ====");
                System.out.println("0. Exit");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Back to Previous Menu");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();
                NumberOnlyException.validateNumber(choice, "^[0-9]+$");
                int choice2 = Integer.parseInt(choice);
                switch (choice2) {
                    case 0 -> {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                    case 1 -> {
                        System.out.print("Enter email: ");
                        String emailInput = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String passwordInput = scanner.nextLine();
                        if (login(emailInput, passwordInput)) {
                            menu();
                        }
                        break;
                    }
                    case 2 -> {
                        System.out.print("Enter first name: ");
                        String firstNameInput = scanner.nextLine();
                        System.out.print("Enter last name: ");
                        String lastNameInput = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String emailInput = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String passwordInput = scanner.nextLine();
                        register(firstNameInput, lastNameInput, emailInput, passwordInput);
                        break;
                    }
                    case 3 -> {
                        menuCustomer = false;

                        break;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberOnlyException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void menu() {
        boolean menuRole = true;
        Scanner scanner = new Scanner(System.in);
        while (menuRole) {
            try {
                System.out.println("\n==== CUSTOMER ====");
                System.out.println("0. Exit");
                System.out.println("1. Check Profile Account");
                System.out.println("2. Modify Account");
                System.out.println("3. Modify Password");
                System.out.println("4. Shopping");
                System.out.println("5. Log out");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();
                NumberOnlyException.validateNumber(choice, "^[0-9]+$");
                int choice2 = Integer.parseInt(choice);
                switch (choice2) {
                    case 0 -> {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                    case 1 -> {
                        //System.out.print("Please enter email again to verify: ");
                        //String emailInput = scanner.nextLine();
                        //System.out.println(checkProfile(emailInput));
                        //System.out.println(toString());
                        System.out.print("Please enter email again to verify: ");
                        String emailInput = scanner.nextLine();
                        System.out.println(checkProfile(emailInput));
                        break;
                    }
                    case 2 -> {
                        System.out.print("Enter email: ");
                        String emailInput = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String passwordInput = scanner.nextLine();
                        modifyAccount(emailInput, passwordInput);
                        break;
                    }
                    case 3 -> {
                        System.out.print("Enter email: ");
                        String emailInput = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String passwordInput = scanner.nextLine();
                        modifyPasswordString(emailInput, passwordInput);
                        break;
                    }
                    case 4 -> {
                        //Product p = new Product(0, "", 0, 0, "");
                        AddToCart cart = new AddToCart(0, "", 0, 0, "");
                        cart.menuShopping();
                        break;
                    }
                    case 5 -> {
                        System.out.println("Warning! Your cart will not save when you lug out the account! ");
                        System.out.println("Are you sure?\nEnter (yes) to continoue: ");
                        Scanner s = new Scanner(System.in);
                        String input = s.nextLine();
                        if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")){
                            AddToCart.cartClear();
                            menuRole = false;
                        }else{
                            System.out.println("Your cart was not clear!");
                        }
                        
                        break;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberOnlyException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
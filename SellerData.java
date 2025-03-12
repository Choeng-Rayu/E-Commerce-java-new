// Purpose: This file contains the SellerData class which extends UserData and implements the authetication interface
import java.util.ArrayList;
import java.util.Scanner;

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
    public void modifyAccount(String email, String PasswordInputString) {
        for (SellerData seller : sellers) {
            if (seller.email.equals(email) && seller.getPassword().equals(PasswordInputString)) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter new first name: ");
                String newFirstName = scanner.nextLine();
                System.out.println("Enter new last name: ");
                String newLastName = scanner.nextLine();
                System.out.println("Enter new email: ");
                String newEmail = scanner.nextLine();
                System.out.println("Enter new password: ");
                String newPassword = scanner.nextLine();

                seller.firstName = newFirstName;
                seller.lastName = newLastName;
                seller.email = newEmail;
                seller.setPassword(newPassword);
                setCurrentPw(newPassword);
                pushByName = newFirstName + " " + newLastName;
                System.out.println("Account was modified successfully");
                return; // Exit the method after modifying the account
            }
        }
        System.out.println("Incorrect email or password");
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
                System.out.println("Sign-up failed. Email already exists.");
                return; // Exit the method if email already exists
            }
        }
        SellerData newSeller = new SellerData(firstName, lastName, email, password);
        sellers.add(newSeller);
        System.out.println("Sign-up successful! Please login again for this account, " + newSeller.firstName + " " + newSeller.lastName + "!");
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
        Scanner scanner = new Scanner(System.in);
        boolean menuSeller = true;
        while (menuSeller) {
            try{    
                System.out.println("\n==== SELLER ====");
                System.out.println("0. Exit");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Back to Previous Menu");
                System.out.print("Choose an option: ");
                String input = scanner.nextLine();
                NumberOnlyException.validateNumber(input, "^[0-9]+$");
                int choice2 = Integer.parseInt(input);
                //scanner.nextLine(); // Consume newline
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
                            menuSellerAccount();
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
                        menuSeller = false;
                        break;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }catch (NumberOnlyException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void menuSellerAccount() {
        Scanner scanner = new Scanner(System.in);
        boolean menuSellerAccount = true;
        while (menuSellerAccount) {
            try{
                System.out.println("\n==== SELLER ====");
                System.out.println("0. Exit");
                System.out.println("1. Check Profile Account");
                System.out.println("2. Modify Account");
                System.out.println("3. Modify Password");
                System.out.println("4. Product Management");
                System.out.println("5. Log out");
                System.out.print("Choose an option: ");
                String input = scanner.nextLine();
                NumberOnlyException.validateNumber(input, "^[0-9]+$");
                int choice2 = Integer.parseInt(input);
                //scanner.nextLine(); // Consume newline
                switch (choice2) {
                    case 0 -> {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                    case 1 -> {
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
                        Product p = new Product(0, "", 0.0, 0, pushByName);
                        p.productManagement();
                        break;
                    }
                    case 5 -> {
                        menuSellerAccount = false;
                        break;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
            catch (NumberOnlyException e){
                System.out.println(e.getMessage());
            }
        }
    }
    public void  deleteSeller(){
        SellerData s = new SellerData(firstName, lastName, email, email);
        try{    
            System.out.println("Select Index to Delete: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            NumberOnlyException.validateNumber(input, "^[0-9]+$");
            int i = Integer.parseInt(input);
        
            AdminExtends ad = new AdminExtends();
            if(ad.login(email, getPassword())){
                sellers.remove(i);
                System.out.println("Account was deleted Successfully!");
            }
            else{
                System.out.println("Invalid Password!, Account was not deleted");
            }
        }catch(NumberOnlyException e){
            System.out.println(e.getMessage()); 
        }catch(IndexOutOfBoundsException e){
            System.out.println("Invalid Index, Account was not deleted");
        }
    }
    public void displayAllSeller() {
        if (sellers.isEmpty()) {
            System.out.println("No Seller Accounts!");
            return;
        }
        int i = 0;
        System.out.printf("%-5s %-9s %-16s %-13s %-13s %-20s\n", "No", "ID", "First Name", "Last Name", "Password", "Email");
        System.out.println("-----------------------------------------------------------------------");
        for (SellerData s : sellers) {
            System.out.printf("%-3d %-3s %-5d %-2s %-12s %-2s %-10s %-2s %-10s %-2s %-30s\n", i, "|",  s.id,"|", s.firstName, "|", s.lastName, "|",s.getPassword(),"|", s.email);
            i++;
        }
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
    public void SellerManagement(){
        Scanner sc = new Scanner(System.in);
        //SellerData c = new SellerData("", "", "", "");
        boolean SellerManagement = true;
        while(SellerManagement){
            try{    
                System.out.println("\n==== Seller MANEGEMENT ====");
                System.out.println("0. Exit");
                System.out.println("1. Back to Previous Menu");
                System.out.println("2. Show all Sellers");
                System.out.println("3. Search Sellers");
                System.out.println("4. Delete Seller");
                System.out.println("5. Modify account");
                System.out.println("6. Modify Password");
                System.out.println("7. Get report");
                System.out.print("Choose an option: ");
                String input = sc.nextLine();
                NumberOnlyException.validateNumber(input, "^[0-9]+$");
                int choice2 = Integer.parseInt(input);
                switch (choice2){
                    case 0 ->{
                        System.out.println("You were Exit the program!");
                        System.exit(0);
                    }
                    case 1 ->{ // back previous menu
                        SellerManagement = false;
                        break;
                    }
                    case 2 ->{ //Sow all Sellers
                        //System.out.println("not yet implement");
                        if(sellers.isEmpty()){
                            System.out.println("No Seller Accounts!");
                            break;
                        }
                        displayAllSeller();
                        break;
                    }
                    case 3 ->{ // search
                        if(sellers.isEmpty()){
                            System.out.println("No Seller Accounts!");
                            break;
                        }
                        System.out.println("Enter Email to search: ");
                        String emailInput = sc.nextLine();
                        checkProfile(emailInput);
                        break;
                    }
                    case 4 ->{ // delete acc
                        if(sellers.isEmpty()){
                            System.out.println("No Seller Accounts!");
                            break;
                        }
                        displayAllSeller();
                        deleteSeller();
                        break;
                    }
                    case 5 ->{ // modify account
                        if(sellers.isEmpty()){
                            System.out.println("No Seller Accounts!");
                            break;
                        }
                        System.out.print("Enter email: ");
                        String emailInput = sc.nextLine();
                        System.out.print("Enter password: ");
                        String passwordInput = sc.nextLine();
                        modifyAccount(emailInput, passwordInput);
                        break;
                    }
                    case 6 ->{ // modify password
                        if(sellers.isEmpty()){
                            System.out.println("No Seller Accounts!");
                            break;
                        } 
                        System.out.print("Enter email: ");
                        String emailInput = sc.nextLine();
                        System.out.print("Enter password: ");
                        String passwordInput = sc.nextLine();
                        modifyPasswordString(emailInput, passwordInput);
                        break;
                    }
                    case 7 ->{
                        System.out.println(getTotalSeller());
                        break;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }catch(NumberOnlyException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
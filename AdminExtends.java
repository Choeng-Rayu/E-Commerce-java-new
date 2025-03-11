import java.util.Scanner;
public class AdminExtends extends UserData {
	public AdminExtends() {
        super("Choeng", "Rayu", "choengrayu@gmail.com","12345");
	}    
    public static void main(String[] args) {
        AdminExtends a = new AdminExtends();
        a.adminMenu();
    }

    public void adminMenu(){
        boolean adminMenu = true;
        AdminExtends ad = new AdminExtends();
        Scanner scanner = new Scanner(System.in);
        while(adminMenu){
            try{    
                System.out.println("======= Admin ======");
                System.out.println("0. Exit");
                System.out.println("1. Log out");
                System.out.println("2. Check Profile Account");
                System.out.println("3. Modify Account");
                System.out.println("4. Modify Password");
                System.out.println("5. Product Management");
                System.out.println("6. Seller Management");
                System.out.println("8. Customer Manangement");
                System.out.println("Enter your choise: ");
                String input = scanner.nextLine();
                NumberOnlyException.validateNumber(input, "^[0-9]*$");
                int choice = Integer.parseInt(input);
                //scanner.nextLine();
                switch (choice) {
                    case 0 -> adminMenu = false;
                    case 1 -> {
                        adminMenu = false;
                        break;
                    }
                    case 2 -> {
                        System.out.println("Enter Admin's email to verify: ");
                        String emailInput= scanner.nextLine();
                        ad.checkProfile(emailInput);
                        break;
                    }
                    case 3 -> {
                        // Modify Account logic
                        System.out.println("Enter Admin's email: " );
                        String emailInput = scanner.nextLine();
                        System.out.println("Enter Admin's Password: " );
                        String passwordInput = scanner.nextLine();
                        ad.modifyAccount(emailInput, passwordInput);
                        break;
                    }
                    case 4 -> {
                        // Modify Password or PIN logic
                        System.out.println("Enter Admin's email: " );
                        String emailInput = scanner.nextLine();
                        System.out.println("Enter Admin's Password: " );
                        String passwordInput = scanner.nextLine();
                        ad.modifyPasswordString(emailInput, passwordInput);
                        break;
                    }
                    case 5 -> {
                        // Product manage logic
                        //String pushBy = "Admin"; // or any appropriate value
                        Product p = new Product(0, "", 0, 0, "");
                        p.productManagement();
                    }
                    case 6 -> {
                        // Seller Management logic
                        SellerData sd = new SellerData(firstName, lastName, email, email);
                        sd.SellerManagement();
                    }
                    case 8 -> {
                        // Customer Management logic
                        CustomerData cd = new CustomerData("", "", "", "");
                        cd.customerManagement();
                        break;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }catch(NumberOnlyException e){
                System.out.println("Invalid input. Please try again.");
            }

        }
    }

    public void adminLogIn(){
        boolean adminLogIn = true;
        Scanner scanner = new Scanner(System.in);
        while (adminLogIn) { 
            try{
                System.out.println("\n==== ADMIN ====");
                System.out.println("0. Exit");
                System.out.println("1. back to previouse menu");
                System.out.println("2. Log In");
                System.out.print("Choose an option: ");
                String input1String = scanner.nextLine();
                NumberOnlyException.validateNumber(input1String, "^[0-9]*$");
                int choice3 = Integer.parseInt(input1String);
                switch (choice3) {
                    case 0 -> {
                        System.out.println("Exited successfully");
                        System.exit(0);
                    }
                    case 1 -> {
                        //System.out.print("Haven't implemented ye");
                        adminLogIn = false;
                        break;
                    }
                    case 2 -> {
                        //System.out.print("Haven't implemented yet");
                        System.err.print("Enter email: ");
                        String emailInputAdmin = scanner.nextLine();
                        System.err.print("Enter Password: ");
                        String passwordInput = scanner.nextLine();
                        if(login(emailInputAdmin, passwordInput)){
                            adminMenu();
                        }

                        break;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberOnlyException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
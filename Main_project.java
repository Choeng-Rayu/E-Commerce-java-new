import java.util.Scanner;

public class Main_project {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserData seller = new SellerData("rayu", "rayu", "choengrayu@gmail.com", "12345");
        seller.register("rayu", "rayu", "choengrayu@gmail.com", "12345");
        UserData customer = new CustomerData("", "", "", "");
        UserData amind = new AdminExtends();
        UserData[] users = {seller, customer, amind};
        // while (true) {
        //     try{    
        //         System.out.println("\n==== WELCOME TO OUR E-COMMERCE SYSTEM ====");
        //         System.out.println("0. Exit");
        //         System.out.println("1. For Customer");
        //         System.out.println("2. For Seller");
        //         System.out.println("3. For Admin");
        //         System.out.print("Choose an option: ");
        //         String input = scanner.nextLine();
        //         NumberOnlyException.validateNumber(input, "^[0-9]*$");
        //         int choice = Integer.parseInt(input);

        //         switch (choice) {
        //             case 0 -> {
        //                 System.out.println("Exited successfully");
        //                 scanner.close();
        //                 System.exit(0);
        //             }
        //             case 1 -> {
        //                 ((CustomerData) users[1]).menuCustomer();
        //                 break;
        //             }
        //             case 2 -> {
        //                 ((SellerData) users[0]).menuSeller();
        //                 break;
        //             }
        //             case 3 -> {
        //                 //((AdminExtends) amind).adminLogIn(); the same as below
        //                 ((AdminExtends) users[2]).adminLogIn();
        //                 break;
        //             }
        //             default -> System.out.println("Invalid choice. Try again.");
        //         }
        //     }catch(NumberOnlyException e){
        //         System.out.println(e.getMessage());
        //     }
        // }
        
        GUI.LoginGUI();


    }
}


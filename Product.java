import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Product {
    public int id;
    public String name;
    public double price;
    public int quantity;
    public String datePush;
    public String pushBy;
    public static int totalProductAddedCart = 0;
    public double totalMoneyForPay = 0;
    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<Product> cart = new ArrayList<>();
    SellerData seller = new SellerData("", "", "", "");

    // Constructor
    public Product(int id, String name, double price, int quantity, String pushBy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.pushBy = pushBy;
        this.datePush = getCurrentDateTimeInCambodia();
    }
    public static void cartClear(){
        cart.clear();
        totalProductAddedCart = 0;

        System.out.println("Your cart was cleared!");
    }
    public void DeleteProductByAdmin() {
        Scanner scanner1 = new Scanner(System.in);
        try {
            System.out.println("Select Index to Delete: ");
            String iString = scanner1.nextLine();
            NumberOnlyException.validateNumber(iString, "^[0-9]+");
            int i = Integer.parseInt(iString);
            AdminExtends ad = new AdminExtends();
            if (ad.verifyPassword()) {
                products.remove(i); //remove by index
                System.out.println("Account was deleted Successfully!");
            } else {
                System.out.println("Invalid Password!, Account was not deleted");
            }
        }catch(NumberOnlyException e ){
            System.out.println(e.getMessage());
        }
    }

    public void displayAllProduct() {
        if (products.isEmpty()) {
            System.out.println("No Product!");
            return;
        }
        int i = 0;
        System.out.printf("%-5s %-9s %-16s %-13s %-13s %-15s %-15s\n", "No", "ID", "Name", "Price$", "Quantity", "PushBy", "Date");
        System.out.println("-----------------------------------------------------------------------------------------------");
        for (Product p : products) {
            System.out.printf("%-3d %-3s %-5d %-2s %-12s %-2s %-10.2f %-2s %-10d %-3s %-10s %-1s %-10s\n", i, "|", p.id, "|", p.name, "|", p.price, "|", p.quantity, "|", p.pushBy, "|", p.datePush);
            i++;
        }
    }

    // Method to add a product
    public void addProduct(int id, String name, double price, int quantity, String pushBy) {
        Product p = new Product(id, name, price, quantity, pushBy);
        products.add(p);
    }

    public void modifiedProduct(int id) {
        Scanner scanner = new Scanner(System.in);
        for (Product product : products) {
            if (product.id == id) {
                try{
                    System.out.println("Enter new product name: ");
                    String newName = scanner.nextLine();
                    System.out.println("Enter new product price: ");
                    String input = scanner.nextLine();
                    NumberOnlyException.validateNumber(input, "^[0-9]+$"); // Call static method
                    double newPrice = Double.parseDouble(input);
                    System.out.println("Enter new product quantity: ");
                    String inputQuantity = scanner.nextLine();
                    NumberOnlyException.validateNumber(inputQuantity, "^[0-9]+$"); // Call static method

                    int newQuantity = Integer.parseInt(inputQuantity);
                    DuplicateArgumentException.validateQuantity(newQuantity);
                    //scanner.nextLine(); // Consume newline

                    product.name = newName;
                    product.price = newPrice;
                    product.quantity = newQuantity;
                    System.out.println("Product was modified successfully");
                    return; // Exit the method after modifying the product
                }catch(NumberOnlyException | DuplicateArgumentException e){
                    System.out.println("Error: "+ e.getMessage());
                }
            }
        }
        System.out.println("Product not found");
    }

    public String deleteProduct(int idDelete) {
        for (Product product : products) {
            if (product.id == idDelete) {
                if(SellerData.pushByName.equals(product.pushBy)){
                    Scanner s = new Scanner(System.in);
                    System.out.println("Enter password to Delete: ");
                    String passString = s.nextLine();
                    if (passString.equals(seller.getCurrentPw())) {
                        products.remove(product);
                        String result = "Product Name: " + product.name + " was deleted successfully";
                        return result;
                    }
                    return "Invalid password! Product was not deleted";
                }
                return "You Can only delete the product you pushed!";  
            }
        }
        return "Product not found";
    }

    @Override
    public String toString() {
        return "Product ID: " + id + "\nProduct Name: " + name + "\nProduct Price$: " + price + "\nProduct Quantity: " + quantity + "\nDate: " + datePush;
    }

    public static int getTotalProduct() {
        return products.size();
    }

    public String searchProductByID(int id) {
        for (Product product : products) {
            if (product.id == id) {
                return product.toString();
            }
        }
        return "Product not found";
    }

    public String searchProductByName(String name) {
        for (Product product : products) {
            if (product.name.equalsIgnoreCase(name)) {
                return product.toString();
            }
        }
        return "Product not found";
    }

    public String searchProductByPrice(double price) {
        for (Product product : products) {
            if (product.price == price) {
                return product.toString();
            }
        }
        return "Product not found";
    }
    public static int generateIDByProductSize() { 
        return products.size();
    }
    public void productManagement() {
        Scanner scanner = new Scanner(System.in);
        boolean productManagement = true;
        while (productManagement) {
            try{
                System.out.println("\n==== PRODUCTS MANAGEMENT SYSTEM ====");
                System.out.println("0. Exit");
                System.out.println("1. Add Products");
                System.out.println("2. Modify Products");
                System.out.println("3. Delete Products");
                System.out.println("4. Display Products");
                System.out.println("5. Search Products");
                System.out.println("6. Back to Main Menu");

                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();
             
                NumberOnlyException.validateNumber(choice, "^[0-9]*$");
                switch (Integer.parseInt(choice)) {
                    case 0 -> {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                    case 1 -> {
                        System.out.print("Enter product name: ");
                        String nameInput = scanner.nextLine();

                        System.out.print("Enter product price: ");
                        String priceInput = scanner.nextLine();
                        NumberOnlyException.validateNumber(priceInput, "^[0-9]+$"); // Call static method
                        double priceInputDouble = Double.parseDouble(priceInput);
                        
                        System.out.print("Enter product quantity: ");
                        String quantityInput = scanner.nextLine();
                     
                        NumberOnlyException.validateNumber(quantityInput, "^[0-9]+$"); // Call static method
                        int quantityInputInt = Integer.parseInt(quantityInput);
               
                        //SellerData s = new SellerData("", "", "", "");
                        addProduct(generateIDByProductSize(), nameInput, priceInputDouble, quantityInputInt, SellerData.pushByName);
                    }
                    case 2 -> {
                        if(products.isEmpty()){
                            System.out.println("No Product!");
                            return;
                        }
                        System.out.print("Enter product ID to Modify: ");
                        String idInput = scanner.nextLine();
                        NumberOnlyException.validateNumber(idInput, "^[0-9]+$");
                        int idInputInt = Integer.parseInt(idInput);
                        modifiedProduct(idInputInt);
                    }
                    case 3 -> {
                        if(products.isEmpty()){
                            System.out.println("No Product!");
                            return;
                        }
                        System.out.print("Enter product ID to Delete: ");
                        String idInput = scanner.nextLine();
                        NumberOnlyException.validateNumber(idInput, "^[0-9]+$");
                        int idInputInt = Integer.parseInt(idInput);
                        System.out.println(deleteProduct(idInputInt));
                    }
                    case 4 -> {
                        if(products.isEmpty()){
                            System.out.println("No Product!");
                            return;
                        }
                        displayAllProduct();
                        System.out.println("Total Products: " + Product.getTotalProduct());
                    }
                    case 5 -> {
                        if(products.isEmpty()){
                            System.out.println("No Product!");
                            return;
                        }
                        searchMenu();
                    }
                    case 6 -> {
                        productManagement = false;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberOnlyException | DuplicateArgumentException e) {
                System.out.println("Error: "+ e.getMessage());
            }
        }
    }

    public void searchMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean searchMenu = true;
        while (searchMenu) {
            try{
                System.out.println("\n==== SEARCH MENU ====");
                System.out.println("0. Exit");
                System.out.println("1. Search Product by ID");
                System.out.println("2. Search Product by Name");
                System.out.println("3. Search Product by Price");
                System.out.println("4. Back to Main Menu");

                System.out.print("Choose an option: ");
                String choiceString = scanner.nextLine();
                //scanner.nextLine(); // Consume newline
                NumberOnlyException.validateNumber(choiceString, "^[0-9]+$"); // Call static method
                int choice = Integer.parseInt(choiceString);
                switch (choice) {
                    case 0 -> {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                    case 1 -> {
                        System.out.print("Enter product ID: ");
                        String idInput = scanner.nextLine();
                        //scanner.nextLine(); // Consume newline
                        NumberOnlyException.validateNumber(idInput, "^[0-9]+$"); // Call static method
                        int idInputInt = Integer.parseInt(idInput);
                        System.out.println(searchProductByID(idInputInt));
                    }
                    case 2 -> {
                        System.out.print("Enter product name: ");
                        String nameInput = scanner.nextLine();
                        //scanner.nextLine(); // Consume newline
                        System.out.println(searchProductByName(nameInput));
                    }
                    case 3 -> {
                        System.out.print("Enter product price: ");
                        String priceInput = scanner.nextLine();
                        //scanner.nextLine(); // Consume newline
                        NumberOnlyException.validateNumber(priceInput, "^[0-9]+$"); // Call static method
                        double priceInputDouble = Double.parseDouble(priceInput);
                        System.out.println(searchProductByPrice(priceInputDouble));
                    }
                    case 4 -> {
                        searchMenu = false;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                    }
            }catch(NumberOnlyException e){
                System.out.println("Error: "+ e.getMessage());
            }
        }
    }

    public static String getCurrentDateTimeInCambodia() {
        ZoneId cambodiaZoneId = ZoneId.of("Asia/Phnom_Penh");
        LocalDateTime currentDateTime = LocalDateTime.now(cambodiaZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }

    public void addProductToCart(int id, int quantityAdded) {
        for (Product productAdded : products) {
            if (productAdded.id == id) {
                if (productAdded.quantity >= quantityAdded && quantityAdded > 0) {
                    Product cartProduct = new Product(productAdded.id, productAdded.name, productAdded.price, quantityAdded, productAdded.pushBy);
                    cart.add(cartProduct);
                    productAdded.quantity -= quantityAdded;
                    totalProductAddedCart += quantityAdded;
                    //System.out.println("Product: " + cartProduct.name + " " + cartProduct.price + " " + cartProduct.pushBy + " " + cartProduct.datePush);
                    System.out.println("Product added to cart successfully");
                    System.out.println("WARNING: Your cart will be remove after you logout!");    
                } else {
                    System.out.println("Insufficient quantity");
                }
                return;
            }
        }
        System.out.println("Product not found");
    }
    public void displayCart() {
        System.out.println("Cart:");
        System.out.printf("%-5s %-9s %-16s %-13s %-13s %-15s %-15s\n", "No", "ID", "Name", "Price$", "Quantity", "PushBy", "Date");
        System.out.println("-----------------------------------------------------------------------------------------------");
        int i = 0;
        totalMoneyForPay = 0;
        for (Product productAdded : cart) {
            System.out.printf("%-3d %-3s %-5d %-2s %-12s %-2s %-10.2f %-2s %-10d %-3s %-10s %-1s %-10s\n", i, "|", productAdded.id, "|", productAdded.name, "|", productAdded.price, "|", productAdded.quantity, "|", productAdded.pushBy, "|", productAdded.datePush);
            i++;
            totalMoneyForPay += productAdded.price * productAdded.quantity;
        }
        System.out.println("Total Quantity: " + totalProductAddedCart);
        System.out.println("Total Price: " + totalMoneyForPay + "$");
    }

    public void removeProductFromCart(int index) {
        for (Product productRemove : cart) {
            if (productRemove.id == index) {
                cart.remove(productRemove);
                totalProductAddedCart -= productRemove.quantity;
                System.out.println("Product removed from cart successfully");
                return;
            }
        }
        System.out.println("Product not found");
    }
    public void Payment(){
        displayCart();
        System.out.println("Please Enter Password for paying to Veryfy!");
        CustomerData c = new CustomerData("", "", "", "");
        if(c.verifyPassword()){
            //System.out.println("Change: " + (payment - totalMoenyForPay));
            cart.clear();
            totalProductAddedCart = 0;
                //System.out.println("Payment successfully");
            System.out.println("Payment successfully");
        } else {
            System.out.println("Invalid Password!, Payment was not successfully");
        }

    }
    public void menuShopping() {
        Scanner scanner = new Scanner(System.in);
        boolean menuShopping = true;

        while (menuShopping) {
            try {
                System.out.println("\n==== SHOPPING ====");
                System.out.println("0. Exit");
                System.out.println("1. Add to Cart");
                System.out.println("2. Remove from Cart");
                System.out.println("3. Check Out");
                System.out.println("4. Payment");
                System.out.println("5. Search Product");
                System.out.println("6. Back to Previous Menu");
                System.out.print("Choose an option: ");
                
                String input = scanner.nextLine();
                NumberOnlyException.validateNumber(input, "^[0-9]+$"); // Call static method
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 0 -> {
                        System.out.println("Exited");
                        System.exit(0);
                    }
                    case 1 -> {
                        if(products.isEmpty()){
                            System.out.println("No Product!");
                            return;
                        }
                        displayAllProduct();
                        System.out.print("Enter product ID To add Product Cart: ");
                        String idInputStr = scanner.nextLine();
                        NumberOnlyException.validateNumber(idInputStr, "^[0-9]+$");
                        int idInput = Integer.parseInt(idInputStr);

                        System.out.print("Enter quantity: ");
                        String quantityInputStr = scanner.nextLine();
                        NumberOnlyException.validateNumber(quantityInputStr, "^[0-9]+$");
                        int quantityInput = Integer.parseInt(quantityInputStr);

                        addProductToCart(idInput, quantityInput);
                    }
                    case 2 -> {
                        displayCart();
                        if(cart.isEmpty()){
                            System.out.println("No Product in Cart!");
                            return;
                        }
                        System.out.print("Enter product ID to remove: ");
                        String idInputStr = scanner.nextLine();
                        NumberOnlyException.validateNumber(idInputStr, "^[0-9]+$");
                        int idInput = Integer.parseInt(idInputStr);

                        removeProductFromCart(idInput);
                    }
                    case 3 -> {
                        displayCart(); 
                        if(cart.isEmpty()){
                        System.out.println("No Product in Cart!");
                        return;
                        }
                    }
                    case 4 -> {
                        if(cart.isEmpty()){
                            System.out.println("No Product in Cart!");
                            return;
                        }
                        Payment();
                    }
                    case 5 -> searchMenu();
                    case 6 -> menuShopping = false;
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberOnlyException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    public static void main(String[] args){
        Product c = new Product(1, "book", 123.56, 10, "rayu");
        Product newProduct2 = new Product(2, "pen", 234.56, 20, "rayu");
        Product newProduct3 = new Product(3, "phone", 345.67, 30, "rayu");
        products.add(newProduct2);
        products.add(newProduct3);
        products.add(c);
        //c.displayAllProduct();
        //c.addProductToCart(1, 2);
        //c.displayCart();
        c.productManagement();
        //c.menuShopping();
        //c.productManagement();  
        //c.searchMenu();
    }
}
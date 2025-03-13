
import java.util.ArrayList;
import java.util.Scanner;

public class AddToCart extends Product {
    public static int totalProductAdded = 0;
    public String currentDateAddToCart;
    public static ArrayList<Product> cart = new ArrayList<>();
    private static ArrayList<ProductSold> soldProducts = new ArrayList<>();

    public AddToCart(int id, String name, double price, int quantity, String pushBy) {
        super(id, name, price, quantity, pushBy);
        totalProductAdded += quantity;
    }
    public class ProductSold {
        public String buyerName;
        public String dateBought; // Changed to match constructor
        public int productId;
        public String productName;
        public double price;
        public int quantity;
        public String boughtName;

        public ProductSold(int productId, String productName, double price, int quantity, 
                         String boughtName, String buyerName, String dateBought) {
            this.productId = productId;
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
            this.boughtName = boughtName;
            this.buyerName = buyerName;
            this.dateBought = dateBought;
        }

        // Method to add a sold product to the list
        public void recordSale() {
            soldProducts.add(this);
        }

        // // Getter methods
        // public String getBuyerName() { return buyerName; }
        // public String getDateBought() { return dateBought; }
        // public int getProductId() { return productId; }
        // public double getPrice() { return price; }
        // public int getQuantity() { return quantity; }
    }

    // Method to display all sold products
    public void displaySoldProducts() {
        if (soldProducts.isEmpty()) {
            System.out.println("No products have been sold yet.");
            return;
        }
        System.out.println("\nSold Products:");
        System.out.printf("%-5s %-9s %-16s %-13s %-13s %-15s %-15s\n", 
                         "ID", "Name", "Price$", "Quantity", "SoldBy", "Buyer", "Date");
        System.out.println("--------------------------------------------------------------------------------");
        for (ProductSold sold : soldProducts) {
            System.out.printf("%-5d %-12s %-10.2f %-10d %-12s %-12s %-15s\n",
                            sold.productId, sold.productName, sold.price, sold.quantity,
                            sold.boughtName, sold.buyerName, sold.dateBought);
        }
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
    public void Payment1(){
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
    public void Payment() {
        displayCart();
        if (cart.isEmpty()) {
            System.out.println("No Product in Cart!");
            return;
        }
        
        System.out.print("Please Enter Password for paying to Verify: ");
        Scanner s = new Scanner(System.in);
        String pwInput = s.nextLine();
        CustomerData c = new CustomerData("", "", "", "");
        if (c.getCurrentPw().equals(pwInput)) {
            // Record each product in cart as sold
            for (Product p : cart) {
                ProductSold sold = new ProductSold(p.id, p.name, p.price, p.quantity, 
                    p.pushBy, CustomerData.CurrentNameLogin  , getCurrentDateTimeInCambodia()); // Using current date as example
                sold.recordSale();
            }
            cart.clear();
            totalProductAddedCart = 0;
            totalMoneyForPay = 0;
            System.out.println("Payment successfully processed");
        } else {
            System.out.println("Invalid Password! Payment was not successful");
        }
    }
    public static boolean CartisEmpty(){
        if(cart.isEmpty()){
            return true;
        }
        return false;
    }
    public static boolean logoutClearCart(String input){
        if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")){
            cart.clear();
            totalProductAddedCart = 0;
            //menuRole = false;
            return true;
        }
        return false;
                        
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
}

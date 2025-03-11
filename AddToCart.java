
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddToCart extends Product {
    public static int totalProductAdded = 0;
    public String currentDateAddToCart;
    static ArrayList<Product> cart = new ArrayList<>();
    public AddToCart(int id, String name, double price, int quantity, String pushBy) {
        super(id, name, price, quantity, pushBy);
        totalProductAdded += quantity;
    }
    public static String getCurrentDateTimeInCambodia() {
        ZoneId cambodiaZoneId = ZoneId.of("Asia/Phnom_Penh");
        LocalDateTime currentDateTime = LocalDateTime.now(cambodiaZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }
    @Override
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
        System.out.printf("%-5s %-9s %-16s %-13s %-13s %-20s\n", "No", "ID", "Name", "Price", "Quantity", "PushBy");
        System.out.println("--------------------------------------------------------------------");
        int i = 0;
        for (Product product : products) {
            if (product.quantity < 0) {
                System.out.printf("%-3d %-3s %-5d %-2s %-12s %-2s %-10.2f %-2s %-10d %-3s %-20s\n", i, "|", product.id, "|", product.name, "|", product.price, "|", product.quantity, "|", product.pushBy);
                i++;
            }
        }
        System.out.println("Total Quantity: " + totalProductAdded);
    }
    public void removeProductFromCart(int id, int quantity) {
        for (Product product : products) {
            if (product.id == id) {
                product.quantity += quantity;
                System.out.println("Product removed from cart successfully");
                return;
            }
        }
        System.out.println("Product not found");
    }
}

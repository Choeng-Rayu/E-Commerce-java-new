import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
// Class to store the information of the products that are bought and sold
public class BuySellProducts {
    public int id;
    public String name;
    public int price;
    public int quantity;
    public String nameBuyer;
    public String namePushBy;
    public String date;
    public int totalMoney;
    public int totalQuantity;
    public int totalProduct;

    public BuySellProducts(int id, String name, int price, int quantity, String nameBuyer, String namePushBy, String date, int totalMoney, int totalQuantity, int totalProduct) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.nameBuyer = nameBuyer;
        this.namePushBy = namePushBy;
        this.date = date;
        this.totalMoney = totalMoney;
        this.totalQuantity = totalQuantity;
        this.totalProduct = totalProduct;
    }
    // Function to get the current date and time in Cambodia
    public static String getCurrentDateTimeInCambodia() {
        ZoneId cambodiaZoneId = ZoneId.of("Asia/Phnom_Penh");
        LocalDateTime currentDateTime = LocalDateTime.now(cambodiaZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return currentDateTime.format(formatter);
    }
    public static void main(String[] args) {
        System.err.println(getCurrentDateTimeInCambodia());
    }

}

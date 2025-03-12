import java.util.ArrayList;

public class ProducctSold extends AddToCart {
	public String nameBuyer;
    public String dateSold;
	public ProducctSold(int id, String name, double price, int quantity, String description, String nameBuyer, String dateSold) {
		super(id, name, price, quantity, description);
        this.dateSold = dateSold;
        this.nameBuyer = nameBuyer;
	}
    public static ArrayList<Product> productSold = new ArrayList<>();
    public void productWasSold(){
        
    }
	
}

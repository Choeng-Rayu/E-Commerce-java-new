import java.util.List;

public class DuplicateArgumentException extends IllegalArgumentException {
    // Constructor with a custom message
    public DuplicateArgumentException(String message) {
        super(message);
    }
    public static void validateDuplicateIdProduct(int id, List<Product> products)throws DuplicateArgumentException{
        //Product product = new Product(0, "", 0, 0, "");
        for(Product product: products){
            if(product.id == id){
                throw new DuplicateArgumentException("Duplicate ID, Please enter a different ID");
            }
        }
    }
    public static void validateDuplicateEmailCustomer(String email, List<CustomerData> customers)throws DuplicateArgumentException{
        for(CustomerData customer: customers){
            if(customer.email.equals(email)){
                throw new DuplicateArgumentException("Duplicate Email, Please enter a different Email");
            }
        }
        
    }
    public static void validateDuplicateEmailSeller(String email, List<SellerData> sellers) throws DuplicateArgumentException{
        for(SellerData seller: sellers){
            if(seller.email.equals(email)){
                throw new DuplicateArgumentException("Duplicate Email, Please enter a different Email");
            }
        }
    }
    public static void validateQuantity(int quantity)throws DuplicateArgumentException{
        if(!(quantity > 0) || quantity == 0){
            throw new DuplicateArgumentException("Quantity must be greater than 0");
        }
    }
}

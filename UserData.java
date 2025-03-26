import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public abstract class UserData implements authetication {
    //int id;
    public  String firstName;
    public String lastName;
    public String email;
    private String password;
    String dateRegister;
    private static String passwordCurrentLogin;

    public UserData() {
        //defualt constructor
    }
    public UserData(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email; 
        this.password = password;
        this.dateRegister = getCurrentDateTimeInCambodia();
    }
    public void setCurrentPw(String password){
        UserData.passwordCurrentLogin = password;
        this.password = password;
    }
    public String getCurrentPw(){
        return passwordCurrentLogin;
    }
    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public boolean verifyPassword(){
        System.out.println("Enter The Password: ");
        Scanner s = new Scanner(System.in);
        String passwordVerify = s.nextLine();
        return passwordVerify.equals(getPassword());
    }
    @Override
    public void register(String firstName, String lastName, String email, String password) {
       //implement the method at sub class 
    }

    @Override
    public boolean login(String email, String password) {
       //implement the method at sub class
        return false;
    }
    @Override
    public String toString() {
        return "First Name: " + firstName + "\nLast Name: " + lastName + "\nEmail: " + email + "\nDate of Registered: " + dateRegister;
    }

    @Override
    public void modifyPasswordString(String email, String oldPassword) {
        //implement the method at sub class
    }

    public static String getCurrentDateTimeInCambodia() {
        ZoneId cambodiaZoneId = ZoneId.of("Asia/Phnom_Penh");
        LocalDateTime currentDateTime = LocalDateTime.now(cambodiaZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }
    @Override
    public void modifyAccount(String email, String PasswordInputString) {
        //implement the method at sub class
    }
    @Override
    public String checkProfile(String email) {
        return "Incorrect email";
    }

}
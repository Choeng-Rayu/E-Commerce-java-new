import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class UserData implements authetication {
    //int id;
    public  String firstName;
    public String lastName;
    public String email;
    private String password;
    String dateRegister;
    private String passwordCurrentLogin;
    // List to store registered users
    static ArrayList<UserData> users = new ArrayList<>();
    //UserData user = new UserData("", "", "", "", "");	
    // Constructor (Only initializes the object, does not add to the list)

    public UserData(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email; 
        this.password = password;
        this.dateRegister = getCurrentDateTimeInCambodia();
    }
    public void setCurrentPw(String password){
        this.passwordCurrentLogin = password;
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
        for (UserData user : users) {
            if (user.email.equals(email)) {
                System.out.println("Sign-up failed. Email already exists.");
                return; // Exit the method if email already exists
            }
        }
        UserData newUser = new CustomerData(firstName, lastName, email, password);
        users.add(newUser);
        System.out.println("Please login again for this new account " + firstName + " " + lastName + "!");
    }

    @Override
    public boolean login(String email, String password) {
        for (UserData user : users) {
            if (user.email.equals(email) && user.password.equals(password)) {
                System.out.println("Login Successful! Welcome, " + user.firstName + " " + user.lastName + "!");
                return true;
            }
        }
        System.out.println("Login failed. Incorrect email or password.");
        return false;
    }
    @Override
    public String toString() {
        return "First Name: " + firstName + "\nLast Name: " + lastName + "\nEmail: " + email + "Date of Registered: " + dateRegister;
    }

    @Override
    public void modifyPasswordString(String email, String oldPassword) {
        boolean isFound = false;
        for (UserData user : users) {
            if (user.email.equals(email) && user.password.equals(oldPassword)) {
                Scanner s = new Scanner(System.in);
                System.out.println("Enter new password: ");
                String newPassword = s.nextLine();
                user.setPassword(newPassword);
                System.out.println("Password was changed successfully");
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("Incorrect email or password");
        }
    }

    public static String getCurrentDateTimeInCambodia() {
        ZoneId cambodiaZoneId = ZoneId.of("Asia/Phnom_Penh");
        LocalDateTime currentDateTime = LocalDateTime.now(cambodiaZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }
    @Override
    public void modifyAccount(String email, String PasswordInputString) {
        boolean isFound = false;
        for (UserData user : users) {
            if (user.email.equals(email) && user.password.equals(PasswordInputString)) {
                Scanner s = new Scanner(System.in);
                System.out.println("Enter New First Name: ");
                String newFirstName = s.nextLine();
                System.out.println("Enter New Last Name: ");
                String newLastName = s.nextLine();
                System.out.println("Enter New Email: ");
                String newEmail = s.nextLine();
                user.firstName = newFirstName;
                user.lastName = newLastName;
                user.email = newEmail;
                System.out.println("Account was modified successfully");
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("Incorrect email or password");
        }
    }
    // public String deleteUser(String emailString){
    //     Scanner s = new Scanner(System.in);

    //     for (UserData user : users){
    //         if(user.email.equals(emailString)){
    //             String firstNameRe = user.firstName;
    //             String lastNameRe = user.lastName;
    //             System.out.print("Enter your password to delete: ");
    //             String passwordDelete = s.nextLine();
    //             if(user.getPassword().equals(passwordDelete)){
    //                 users.remove(user);
    //                 return "Name: " + firstNameRe  + " " + lastNameRe  + " was deleted!\n";
    //             }
    //             return "This Account: " + firstNameRe + " " + lastNameRe + " was not delete!\n" ;
    //         }
    //     }
    //     return "Not Found\n";
    // }
    @Override
    public String checkProfile(String email) {
        for (UserData user : users) {
            if (user.email.equals(email)) {
                return toString();
            }
        }
        return "Incorrect email";
    }
    public static int getTotalUsers() {
        return users.size();
    }

}
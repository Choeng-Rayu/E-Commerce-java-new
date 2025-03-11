
public interface authetication {
    boolean login(String email, String password);
    void register(String firstName, String lastName, String email, String password);
    boolean verifyPassword();
    void modifyAccount(String email, String PasswordInputString);
    void modifyPasswordString(String email, String oldPassword);
    String checkProfile(String email);
}
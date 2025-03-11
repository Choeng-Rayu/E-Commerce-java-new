import java.util.InputMismatchException;

public class NumberOnlyException extends InputMismatchException {
    // Constructor with a custom message
    public NumberOnlyException(String message) {
        super(message);
    }
    public static void validateNumber(String input, String formaString) throws NumberOnlyException {
        if (!input.matches(formaString)) {
            throw new NumberOnlyException("Invalid input: " + input);
        }
    }
}

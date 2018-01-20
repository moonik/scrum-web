package scrumweb.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super("User with user name: " + username + " already exists!");
    }
}

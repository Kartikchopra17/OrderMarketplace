package main.java.marketplace.exception;

public class UserExistsException extends Exception {
    public UserExistsException() {
        super("User with the same username or email already exists.");
    }

    public UserExistsException(String message) {
        super(message);
    }
}
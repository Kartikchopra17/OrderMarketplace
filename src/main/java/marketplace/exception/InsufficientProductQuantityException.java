package main.java.marketplace.exception;

public class InsufficientProductQuantityException extends Exception {
    public InsufficientProductQuantityException() {
        super("Insufficient product quantity available.");
    }

    public InsufficientProductQuantityException(String message) {
        super(message);
    }
}
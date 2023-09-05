package main.java.marketplace.exception;

public class ProductExistsException extends Exception {
    public ProductExistsException() {
        super("Product with the same ID already exists.");
    }

    public ProductExistsException(String message) {
        super(message);
    }
}
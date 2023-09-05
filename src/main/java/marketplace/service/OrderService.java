package main.java.marketplace.service;

import main.java.marketplace.model.CartItem;
import main.java.marketplace.model.Order;
import main.java.marketplace.model.ShoppingCart;
import main.java.marketplace.model.Product;
import main.java.marketplace.exception.ProductNotFoundException;
import main.java.marketplace.exception.InsufficientProductQuantityException;

import java.util.*;

public class OrderService {
    private Map<String, List<Order>> userOrders = new HashMap<>();
    private final Object lock = new Object(); // Concurrency control lock

    public void checkout(String userId, ShoppingCart cart) throws ProductNotFoundException, InsufficientProductQuantityException {
        synchronized (lock) {
            // Assuming ProductService is available to check product availability
            ProductService productService = new ProductService();

            // Check if products in the cart are available
            for (CartItem cartItem : cart.getItems()) {
                Product product = cartItem.getProduct();
                int quantity = cartItem.getQuantity();
                Product storedProduct = productService.getProduct(product.getProductId());

                if (storedProduct == null) {
                    throw new ProductNotFoundException("Product not found.");
                }

                // Check if the available quantity is sufficient
                if (storedProduct.getQuantity() < quantity) {
                    throw new InsufficientProductQuantityException("Product quantity not available.");
                }
            }

            // Create a new order for the user
            Order order = new Order(userId, userId, cart.getItems(), calculateTotalAmount(cart), new Date());

            // Deduct the purchased quantities from product inventory
            for (CartItem cartItem : cart.getItems()) {
                Product product = cartItem.getProduct();
                int quantity = cartItem.getQuantity();
                Product storedProduct = productService.getProduct(product.getProductId());

                if (storedProduct != null) {
                    storedProduct.setQuantity(storedProduct.getQuantity() - quantity);
                }
            }

            // Store the order for the user
            userOrders.computeIfAbsent(userId, k -> new ArrayList<>()).add(order);

            // Clear the user's shopping cart
            cart.clearCart();

            System.out.println("Order placed successfully.");
        }
    }

    public List<Order> getOrderHistory(String userId) {
        synchronized (lock) {
            return userOrders.getOrDefault(userId, new ArrayList<>());
        }
    }

    public double calculateTotalAmount(ShoppingCart cart) {
        List<CartItem> items = cart.getItems();
        double totalAmount = 0.0;

        for (CartItem item : items) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double itemPrice = product.getPrice() * quantity;
            totalAmount += itemPrice;
        }

        return totalAmount;
    }
}

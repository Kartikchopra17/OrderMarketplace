package main.java.marketplace.service;

import main.java.marketplace.model.CartItem;
import main.java.marketplace.model.Product;
import main.java.marketplace.model.ShoppingCart;
import main.java.marketplace.exception.ProductNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class CartService {
    private Map<String, ShoppingCart> userCarts = new HashMap<>();
    private final Object lock = new Object(); // Concurrency control lock

    public void addToCart(String userId, int productId, int quantity) throws ProductNotFoundException {
        synchronized (lock) {
            // Get the user's cart or create a new one if it doesn't exist
            ShoppingCart cart = userCarts.computeIfAbsent(userId, k -> new ShoppingCart());

            // Retrieve the product from ProductService (assuming you have ProductService)
            ProductService productService = new ProductService();
            Product product = productService.getProduct(productId);

            if (product == null) {
                throw new ProductNotFoundException("Product not found.");
            }

            // Add the product to the cart
            cart.addItem(new CartItem(product, quantity));
            System.out.println("Product added to cart successfully.");
        }
    }

    public ShoppingCart getCart(String userId) {
        synchronized (lock) {
            return userCarts.getOrDefault(userId, new ShoppingCart());
        }
    }

    public void clearCart(String userId) {
        synchronized (lock) {
            ShoppingCart cart = userCarts.get(userId);
            if (cart != null) {
                cart.clearCart();
                System.out.println("Cart cleared successfully.");
            }
        }
    }

    // You can add more cart-related methods here as needed
}
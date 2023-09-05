package main.java;

import main.java.marketplace.model.User;
import main.java.marketplace.model.Product;
import main.java.marketplace.model.ShoppingCart;
import main.java.marketplace.service.UserService;
import main.java.marketplace.service.ProductService;
import main.java.marketplace.service.CartService;
import main.java.marketplace.service.OrderService;
import main.java.marketplace.exception.UserExistsException;
import main.java.marketplace.exception.ProductExistsException;
import main.java.marketplace.exception.ProductNotFoundException;
import main.java.marketplace.exception.InsufficientProductQuantityException;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        ProductService productService = new ProductService();
        CartService cartService = new CartService();
        OrderService orderService = new OrderService();

        try {
            // Create some users
            User user1 = new User("user1", "user1@example.com", "password1", "pwd");
            User user2 = new User("user2", "user2@example.com", "password2", "pwd");

            userService.createUser(user1);
            userService.createUser(user2);

            // Create some products
            Product product1 = new Product(1, "Product 1", 10, 100.0);
            Product product2 = new Product(2, "Product 2", 20, 200.0);

            productService.addProduct(product1);
            productService.addProduct(product2);

            // Add products to the cart
            cartService.addToCart("user1", 1, 2);
            cartService.addToCart("user2", 2, 3);

            // Check out the cart and place orders
            ShoppingCart cart1 = cartService.getCart("user1");
            ShoppingCart cart2 = cartService.getCart("user2");

            orderService.checkout("user1", cart1);
            orderService.checkout("user2", cart2);

            // View order history
            System.out.println("User 1 Order History:");
            orderService.getOrderHistory("user1").forEach(System.out::println);

            System.out.println("User 2 Order History:");
            orderService.getOrderHistory("user2").forEach(System.out::println);
        } catch (UserExistsException | ProductExistsException | ProductNotFoundException |
                 InsufficientProductQuantityException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
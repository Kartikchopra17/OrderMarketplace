package main.java.marketplace.service;
import main.java.marketplace.model.Product;
import main.java.marketplace.exception.ProductExistsException;
import main.java.marketplace.exception.ProductNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class ProductService {
    private Map<Integer, Product> products = new HashMap<>();

    public void addProduct(Product product) throws ProductExistsException {
        if (products.containsKey(product.getProductId())) {
            throw new ProductExistsException("Product with the same ID already exists.");
        }
        products.put(product.getProductId(), product);
        System.out.println("Product added successfully.");
    }

    public Product getProduct(int productId) throws ProductNotFoundException {
        Product product = products.get(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found.");
        }
        return product;
    }

    public void updateProduct(Product updatedProduct) throws ProductNotFoundException {
        if (!products.containsKey(updatedProduct.getProductId())) {
            throw new ProductNotFoundException("Product not found.");
        }
        products.put(updatedProduct.getProductId(), updatedProduct);
        System.out.println("Product updated successfully.");
    }

    public void deleteProduct(int productId) throws ProductNotFoundException {
        if (products.remove(productId) == null) {
            throw new ProductNotFoundException("Product not found.");
        }
        System.out.println("Product deleted successfully.");
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}

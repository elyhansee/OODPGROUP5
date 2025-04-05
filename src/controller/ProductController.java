package controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Product;
import util.CSVExporter;
import util.Env;

// This class controls the manipulation of products
public class ProductController {
    private static final String filePath = Env.get("DATA_DIR") + "/products.csv";

    public void updateProduct(Product products) {
        CSVExporter.updateProducts(products, filePath);
    }

    public void addProduct(Product product) {
        CSVExporter.insertProducts(product, filePath);
    }

    public List<Product> getProductsBySeller(String sellerId, List<Product> products) {
        return products.stream()
                .filter(p -> p.getSellerID().equals(sellerId))
                .collect(Collectors.toList());
    }

    public List<Product> getActiveProducts(List<Product> products) {
        return products.stream()
                .filter(p -> p.isActive().equals("True"))
                .collect(Collectors.toList());
    }

    public List<Product> searchProducts(List<Product> products, String keyword) {
        String lowered = keyword.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowered)
                        || p.getDescription().toLowerCase().contains(lowered))
                .collect(Collectors.toList());
    }

    public Optional<Product> getProductById(List<Product> products, String id) {
        return products.stream()
                .filter(p -> p.getProductID().equals(id))
                .findFirst();
    }

}

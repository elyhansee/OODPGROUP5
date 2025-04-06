package controller;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.Menu;
import model.Product;
import model.Seller;
import util.CSVExporter;
import util.CSVImporter;
import util.Env;

// This class controls the manipulation of products
public class ProductController {
    private final List<Product> StoreProducts;
    private static final String filePath = Env.get("DATA_DIR") + "/products.csv";
    private static final String bundleFilePath = ".\\data\\bundles.csv"; //PLACEHOLDER 050425

    public ProductController(List<Product> storeProducts) {
        this.StoreProducts = storeProducts;
    }

    public List<Product> getActiveProducts() {
        return StoreProducts.stream()
                .filter(p -> p.isActive().equals("True"))
                .collect(Collectors.toList());
    }

    public List<Product> searchProducts(String keyword) {
        String lowered = keyword.toLowerCase();
        return StoreProducts.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowered)
                        || p.getDescription().toLowerCase().contains(lowered))
                .collect(Collectors.toList());
    }

    public Optional<Product> getProductById(String id) {
        return StoreProducts.stream()
                .filter(p -> p.getProductID().equals(id))
                .findFirst();
    }

    public List<String> getBundles() { //PLACEHOLDER 050425
        List<String> bundles = CSVImporter.importBundles(bundleFilePath);
        return bundles;
    }

    public Product sortProductsReco(String id) { //PLACEHOLDER 050425
        Product sortedProducts = StoreProducts.stream()
                .filter(products -> (products.getProductID().equals(id)))
                .findFirst()
                .orElse(null);
        return sortedProducts;
    }

    //    SELLER METHODS
    public void updateProduct(Product products) {
        CSVExporter.updateProducts(products, filePath);
    }

    public void addProduct(Product product) {
        CSVExporter.insertProducts(product, filePath);
    }

    public static void sellerWrite(Product product) {
        CSVExporter.updateProducts(product, filePath);
    }

    public void sellerNewProduct(Product product) {
        CSVExporter.insertProducts(product, filePath);
    }

    public List<Product> getProductsBySeller(String sellerId) {
        return StoreProducts.stream()
                .filter(p -> p.getSellerID().equals(sellerId))
                .collect(Collectors.toList());
    }

    // Sorts products based on the seller's ID
    public List<Product> sortProducts(Seller seller) {
        List<Product> sortedProducts = StoreProducts.stream()
                .filter(products -> (products.getSellerID().equals(seller.getUserID())))
                .collect(Collectors.toList());

        return sortedProducts;
    }


}

package model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import util.CSVExporter;
import util.CSVImporter;

// This class controls the manipulation of products
public class ProductController {

    private final static String filePath = ".\\data\\products.csv";
    private final static String bundleFilePath = ".\\data\\bundles.csv"; //PLACEHOLDER 050425

    public void sellerWrite(Product product) {
        CSVExporter.updateProducts(product, filePath);
    }
    public void sellerNewProduct(Product product) {
        CSVExporter.insertProducts(product, filePath);
    }

    // Sorts products based on the seller's ID
    public List<Product> sortProducts(Seller seller, List<Product> product) {
        List<Product> sortedProducts = product.stream()
                                            .filter(products -> (products.getSellerID().equals(seller.getUserID())))
                                            .collect(Collectors.toList());

        return sortedProducts;
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

    public List<String> getBundles() { //PLACEHOLDER 050425
        List<String> bundles = CSVImporter.importBundles(bundleFilePath);

        return bundles;
    }

    public Product sortProductsReco(String id, List<Product> product) { //PLACEHOLDER 050425
        Product sortedProducts = product.stream()
                                            .filter(products -> (products.getProductID().equals(id)))
                                            .findFirst()
                                            .orElse(null);

        return sortedProducts;
    }
}

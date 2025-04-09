package controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Product;
import util.CSVExporter;
import util.CSVImporter;
import util.Env;
import view.ProductView;

// This class controls the manipulation of products
public class ProductController {
    private final List<Product> StoreProducts;
    private final ProductView view;
    private static final String filePath = Env.get("DATA_DIR") + "/products.csv";
    private static final String bundleFilePath = Env.get("DATA_DIR") + "/bundles.csv";

    public ProductController(List<Product> storeProducts, ProductView view) {
        this.StoreProducts = storeProducts;
        this.view = view;
    }

    //    SHARED METHOD
    public void listProducts(List<Product> products) {
        view.displayProducts(products);
    }

    //Return a list of products
    public void productSearch() {
        String searchInput = view.productSearch();
        List<Product> searchResults = searchProducts(searchInput);
        view.displayProducts(searchResults);
    }

    public List<Product> getActiveProducts() {
        return StoreProducts.stream()
                .filter(p -> p.isActive().equals("True")).toList();
    }

    public List<Product> getStoreProducts(String ID) {
        return StoreProducts.stream().filter(p -> p.getSellerID().equals(ID)).toList();
    }

    private List<Product> searchProducts(String keyword) {
        String lowered = keyword.toLowerCase();
        return StoreProducts.stream()
                .filter(p -> (p.getName().toLowerCase().contains(lowered)
                        || p.getDescription().toLowerCase().contains(lowered))
                        && p.isActive().equals("True"))
                .collect(Collectors.toList());
    }

    public Optional<Product> getProductById(String id) {
        return StoreProducts.stream()
                .filter(p -> p.getProductID().equals(id))
                .findFirst();
    }

    public List<String> getBundles() {
        List<String> bundles = CSVImporter.importBundles(bundleFilePath);
        return bundles;
    }

    public Product sortProductsReco(String id) {
        Product sortedProducts = StoreProducts.stream()
                .filter(products -> (products.getProductID().equals(id)))
                .findFirst()
                .orElse(null);
        return sortedProducts;
    }

    public void decreaseStock(Product product, int quantity) {
        Product findProduct = StoreProducts.stream()
                .filter(p -> p.getProductID().equals(product.getProductID()))
                .findFirst().orElse(null);
        if (findProduct != null) {
            findProduct.setStock(findProduct.getStock() - quantity);
        }
//        CSVExporter.updateProducts(findProduct, filePath);
    }

    //    SELLER METHODS
    //Add product to catalogue during runtime
    public void addProduct(Product product) {
        CSVExporter.insertProducts(product, filePath);
        StoreProducts.add(product);
    }

    public void sellerWrite(Product product) {
        CSVExporter.updateProducts(product, filePath);
    }

    public void sellerNewProduct(Product product) {
        CSVExporter.insertProducts(product, filePath);
    }


}

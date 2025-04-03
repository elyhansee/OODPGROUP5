package model;

import java.util.List;
import java.util.stream.Collectors;

import util.CSVExporter;

// This class controls the manipulation of products
public class ProductController {

    private final static String filePath = ".\\data\\products.csv";

    public void sellerWrite(Product products) {
        CSVExporter.updateProducts(products, filePath);
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
}

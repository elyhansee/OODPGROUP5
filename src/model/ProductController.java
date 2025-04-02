package model;

import java.util.List;
import java.util.stream.Collectors;

import util.CSVExporter;

public class ProductController {

    private static String filePath = ".\\data\\products.csv";

    public void sellerWrite(Product products) {
        CSVExporter.updateProducts(products, filePath);
    }

    // Sorts products based on the seller's ID
    public List<Product> sortProducts(Seller seller, List<Product> product) {
        List<Product> sortedProducts = product.stream()
                                            .filter(products -> (products.getSellerID().equals(seller.getUserID())))
                                            .collect(Collectors.toList());

        return sortedProducts;
    }
}

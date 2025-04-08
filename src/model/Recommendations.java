package model;

import controller.CustomerController;
import controller.ProductController;
import util.CSVImporter;

import java.util.*;

public class Recommendations {

    public static void displayRecommendations(String currentProductID, List<Product> product, ProductController productController, CustomerController customerController) { // PLACEHOLDER
        Scanner scanner = new Scanner(System.in);
        List<String> bundles = productController.getBundles();
        List<Product> recommandedProducts = new ArrayList<>();

        String bundleIDs = "";
        for (String ids : bundles) {
            if (ids.contains(currentProductID)) {
                bundleIDs = ids;
                break;
            }
        }

        if (!bundleIDs.isEmpty()) {
            List<String> splitIDs = Arrays.asList(bundleIDs.split(","));

            for (String id : splitIDs) {
                if (!id.equals(currentProductID)) {
                    recommandedProducts.add(productController.sortProductsReco(id));
                }
            }

            System.out.println("\n== Recommanded Products ==");
            for (Product p : recommandedProducts) {
                System.out.println(p.toString());
            }

            customerController.invokeHandleCustomerActions();
        } else {
            return; // No Recommanded Products
        }
    }
}

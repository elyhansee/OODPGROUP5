package model;

import controller.CustomerController;
import controller.ProductController;
import util.CSVImporter;

import java.util.*;
import java.util.stream.Collectors;

public class Recommendations {

    public static void displayRecommendations(String currentProductID, List<Product> product, ProductController productController, CustomerController customerController) {
        List<String> bundles = productController.getBundles();
        List<Product> recommandedProducts = new ArrayList<>();
        Set<String> addedProductIDs = new HashSet<>();

        List<String> bundleIDs = new ArrayList<>();
        for (String ids : bundles) {
            if (ids.contains(currentProductID)) {
                bundleIDs.add(ids);
            }
        }

        if (!bundleIDs.isEmpty() && !bundleIDs.equals(null)) {
            String newBundleIDs = String.join(",", bundleIDs);
            List<String> splitIDs = Arrays.asList(newBundleIDs.split(","));

            for (String id : splitIDs) {
                if (!id.equals(currentProductID) && !addedProductIDs.contains(id)) {
                    Product reco = productController.sortProductsReco(id);
                    if (reco != null) {
                        recommandedProducts.add(reco);
                        addedProductIDs.add(id);
                    }
                }
            }

            if (!recommandedProducts.isEmpty() && !recommandedProducts.equals(null)) {
                System.out.println("\n== Recommended Products ==");
                for (Product p : recommandedProducts) { // help here i dont want it to print out repeat items
                    System.out.println(p.toString());
                }
    
                customerController.invokeHandleCustomerActions();
            }
            else {
                System.out.println("\n== No Recommended Products ==");
                return; // No Recommanded Products
            }

        } else {
            System.out.println("\n== No Recommended Products ==");
            return; // No Recommanded Products
        }
    }
}

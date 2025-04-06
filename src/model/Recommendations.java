package model;

import controller.ProductController;
import util.CSVImporter;

import java.util.*;

public class Recommendations {

    public static void displayRecommendations(String currentProductID, List<Product> product, Scanner scanner, ProductController productController, Customer customer) { // PLACEHOLDER

        List<String> bundles = CSVImporter.importBundles("src/data/bundles.csv");

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

            List<String> options = new ArrayList<>();

            System.out.println("== Recommanded Products ==");
            for (Product p : recommandedProducts) {
                options.add(p.getProductID() + " | " + p.getName() + " | $" + p.getPrice());
            }

            int choice = editMenu(options);

            if (choice != options.size()) {
                scanner.nextLine();
                System.out.println("Enter Quantity:");
                int quantity = Integer.parseInt(scanner.nextLine());


//                customer.getCart().addItem(recommandedProducts.get(choice - 1), quantity);
            }
        } else {
            return; // No Recommanded Products
        }


        // String category = currentProduct.getDescription(); //TODO: Add that into products

        // List<Product> recommandedProducts = product.stream()
        //                                         .filter(p -> !p.equals(currentProduct) && p.getDescription().equals(category))
        //                                         .collect(Collectors.toList());

        // if (recommandedProducts.isEmpty()) {
        //     System.out.println("No Recommendations for this product.");
        // }

        // else { // TBC, can use Menu class
        //     int count = 1;
        //     for (Product p : recommandedProducts) {
        //         System.out.println(count + ": " + p.getName());
        //     }
        // }
    }

    private static int editMenu(List<String> options) {
        options.add("Back");
        return Menu.selection(options);
    }
}

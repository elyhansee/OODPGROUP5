package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Seller extends User {

    private static ProductController productController = new ProductController();

    public Seller(String userID, String name, String email, String password, String contact, String address,boolean firstLogin) {
        super(userID, name, email, password, "Seller", contact, address,firstLogin);
    }

    @Override
    public void displayMenu() {
        System.out.println("Seller Menu:");
        System.out.println("1. View Profile");
        System.out.println("2. Add New Product Listing");
        System.out.println("3. Update Product Information");
        System.out.println("4. View Orders for My Products");
        System.out.println("5. Set Min-Max Price Ranges");
        System.out.println("6. Set Bundled Items for Product");
        System.out.println("7. Generate Sales Report");
        System.out.println("8. Logout");
    }

    public static void handleSellerMenu(Seller seller, Scanner scanner, List<Product> products) {
        int choice = 0;
        while (choice != 9) {
            seller.displayMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }
            switch(choice) {
                case 1:
                    seller.viewProfile();
                    break;
                case 2:
                    seller.addProduct(products, scanner);
                    break;
                case 3:
                    seller.updateProduct(products, scanner);
                    break;
                case 4:
                    // Stub: view orders related to seller’s products
                    System.out.println("Viewing orders for your products (stub).");
                    break;
                case 5:
                    // Stub: set min-max pricing
                    System.out.println("Setting min-max price ranges (stub).");
                    seller.setMinMaxPrice(products, scanner);
                    break;
                case 6:
                    // Stub: set bundled items recommendation
                    System.out.println("Setting bundled items for a product (stub).");
                    break;
                case 7:
                    // Stub: generate sales report
                    System.out.println("Generating sales report (stub).");
                    break;
                case 8:
                    seller.toggleListings(products);
                    break;
                case 9:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void viewProfile() {
        System.out.println("Seller Profile:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        // Additional seller-specific information
    }

    private void addProduct(List<Product> products, Scanner scanner) {
        System.out.println("Enter new product details:");
        System.out.print("Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Stock Quantity: ");
        int stock = Integer.parseInt(scanner.nextLine());

        // In a full implementation, product ID might be auto-generated.
        Product newProduct = new Product("P" + System.currentTimeMillis(), name, description, price, stock, this.userID, "False"); // Added default false 020425 Dehan
        products.add(newProduct);
        System.out.println("Product added: " + newProduct);
    }

    private void updateProduct(List<Product> products, Scanner scanner) {
        System.out.print("Enter Product ID to update: ");
        String productId = scanner.nextLine();
        Product target = null;
        for (Product p : products) {
            if (p.getProductID().equals(productId) && p.getSellerID().equals(this.userID)) {
                target = p;
                break;
            }
        }
        if (target == null) {
            System.out.println("Product not found or you are not authorized to update this product.");
            return;
        }
        System.out.print("Enter new price (or press Enter to skip): ");
        String priceStr = scanner.nextLine();
        if (!priceStr.isEmpty()) {
            double newPrice = Double.parseDouble(priceStr);
            target.setPrice(newPrice);
        }
        System.out.print("Enter new stock quantity (or press Enter to skip): ");
        String stockStr = scanner.nextLine();
        if (!stockStr.isEmpty()) {
            int newStock = Integer.parseInt(stockStr);
            target.setStock(newStock);
        }
        System.out.println("Product updated: " + target);
    }
    private void setMinMaxPrice(List<Product> products, Scanner scanner) {
        System.out.println("All your products:");
        for (Product p : products) {
            if (p.getSellerID().equals(this.userID)) {
                System.out.println(p);
            }
        }

        // Set Minimum Price
        System.out.print("Enter the Product ID to set a Minimum Price: ");
        String productIdMin = scanner.nextLine();
        Product minProduct = findMyProductById(products, productIdMin);
        if (minProduct != null) {
            System.out.print("Enter the new minimum price: ");
            double minPrice = Double.parseDouble(scanner.nextLine());
            minProduct.setMinPrice(minPrice);
            System.out.println("Minimum price set successfully.");
        } else {
            System.out.println("Product not found or unauthorized.");
        }

        // Set Maximum Price
        System.out.print("Enter the Product ID to set a Maximum Price: ");
        String productIdMax = scanner.nextLine();
        Product maxProduct = findMyProductById(products, productIdMax);
        if (maxProduct != null) {
            System.out.print("Enter the new maximum price: ");
            double maxPrice = Double.parseDouble(scanner.nextLine());
            maxProduct.setMaxPrice(maxPrice);
            System.out.println("Maximum price set successfully.");
        } else {
            System.out.println("Product not found or unauthorized.");
        }
    }

    private Product findMyProductById(List<Product> products, String productId) {
        for (Product p : products) {
            if (p.getProductID().equals(productId) && p.getSellerID().equals(this.userID)) {
                return p;
            }
        }
        return null;
    }

    // Toggles visibility
    private void toggleListings(List<Product> products) {

        List<String> options = new ArrayList<>();

        System.out.println("\nYour Products:");
        for (Product p : products) {
            System.out.println(p.toStringSeller());
            options.add(p.getProductID());
        }

        int sellerChoice = editMenu(options);

        if (sellerChoice != options.size() + 1) {
            products.get(sellerChoice - 1).toggleVisibility();

            productController.sellerWrite(products.get(sellerChoice - 1));
        }
    }
    
    // Used by: toggleListings, 
    private int editMenu(List<String> options){
        options.add("Back");
        return Menu.selection(options);
    }
}

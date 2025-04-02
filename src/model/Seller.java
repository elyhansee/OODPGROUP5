package model;

import java.util.List;
import java.util.Scanner;

public class Seller extends User {

    public Seller(String userID, String name, String email, String password, String contact, String address) {
        super(userID, name, email, password, "Seller", contact, address);
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
        System.out.println("8. Toggle Product Visibility");
        System.out.println("9. Logout");
    }

    public static void handleSellerMenu(Seller seller, Scanner scanner, List<Product> products) {
        int choice = 0;
        while (choice != 8) {
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

    // TODO: Try Catch
    private void addProduct(List<Product> products, Scanner scanner) {
        System.out.println("Enter new product details:");

        System.out.println("Product Name: ");
        String name = scanner.nextLine();

        System.out.println("Description: ");
        String description = scanner.nextLine();

        System.out.println("Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.println("Stock Quantity: ");
        int stock = Integer.parseInt(scanner.nextLine());

        // In a full implementation, product ID might be auto-generated.
        Product newProduct = new Product("P" + System.currentTimeMillis(), name, description, price, stock, this.userID);
        products.add(newProduct);

        productController.sellerNewProduct(newProduct);
    }

    // TODO: Menu is broken. Has to fixed that then test again
    private void updateProduct(List<Product> products, Scanner scanner) {
        List<String> options = new ArrayList<>();

        System.out.println("\nYour Products:");
        for (Product p : products) {
            System.out.println(p.toStringSeller());
            options.add(p.getProductID());
        }
        
        int sellerChoice = editMenu(options);
        String productID = "";

        if (sellerChoice != options.size()) {
            productID = products.get(sellerChoice - 1).getProductID();
        }

        Product target = null;
        for (Product p : products) {
            if (p.getProductID().equals(productID)) {
                target = p;
                break;
            }
        }

        // TODO: Try catch
        System.out.println("Enter new price (or press Enter to skip): ");
        String newPrice = scanner.nextLine();
        if (!newPrice.isEmpty()) {
            double newPriceDouble = Double.parseDouble(newPrice); //TODO: Format to 2 d.p.
            target.setPrice(newPriceDouble);
        }

        System.out.println("Enter new description (or press Enter to skip): ");
        String newDisc = scanner.nextLine();
        if (!newDisc.isEmpty()) {
            target.setDescription(newDisc);
        }

        System.out.println("Enter new stock quantity (or press Enter to skip): ");
        String newStock = scanner.nextLine();
        if (!newStock.isEmpty()) {
            int newStockInt = Integer.parseInt(newStock);
            target.setStock(newStockInt);
        }

        productController.sellerWrite(target);
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

}

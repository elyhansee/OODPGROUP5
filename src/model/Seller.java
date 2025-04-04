package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Seller extends User {

    private static ProductController productController = new ProductController();
    private static OrderController orderController = new OrderController();

    public Seller(String userID, String name, String email, String password, String contact, String address, boolean firstLogin) {
        super(userID, name, email, password, "Seller", contact, address, firstLogin);
    }

    @Override
    public void displayMenu() {
        System.out.println();
        System.out.println();
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

    public static void handleSellerMenu(Seller seller, Scanner scanner, List<Product> products, List<Order> orders) {

        products = productController.sortProducts(seller, products); // Sorts products to only show those for that seller 020425
        orders = orderController.sortOrders(seller, orders);

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
                    seller.generateSalesReport(products, orders, scanner);
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
        try {
            System.out.println("Enter new product details");

            System.out.println("Product Name: ");
            String name = scanner.nextLine();

            System.out.println("Description: ");
            String description = scanner.nextLine();

            double price = -1;
            while (price <= 0) {
                try {
                    System.out.println("Price: ");
                    price = Double.parseDouble(scanner.nextLine());
                }
                catch (NumberFormatException  e) {
                    System.out.println("Invalid Input! Please enter a valid price.");
                }
            }   

            int stock = -1;
            while (stock < 0) {
                try {
                    System.out.println("Stock Quantity: ");
                    stock = Integer.parseInt(scanner.nextLine());
                }
                catch (NumberFormatException  e) {
                    System.out.println("Invalid Input! Please enter a valid stock amount.");
                }
            }

            Product newProduct = new Product("P" + System.currentTimeMillis(), name, description, price, stock, this.userID, "False"); // Added default false 020425 Dehan
            products.add(newProduct);

            productController.sellerNewProduct(newProduct);
        }
        catch (Exception e) {
            System.out.println("An unexpected error has occurred. Please try again.");
        }
    }

    private void updateProduct(List<Product> products, Scanner scanner) {
        try {
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

            System.out.println("Enter new price (or press Enter to skip): ");
            String newPrice = scanner.nextLine();
            while (!newPrice.isEmpty()) {
                try {
                    double newPriceDouble = Double.parseDouble(newPrice);
                    if (newPriceDouble <= 0) {
                        System.out.println("Invalid Input! Please enter a valid price.");
                        System.out.println("Enter new price (or press Enter to skip): ");
                        newPrice = scanner.nextLine();
                    }
                    else {
                        DecimalFormat decFormat = new DecimalFormat("#.00");
                        String formattedPrice = decFormat.format(newPriceDouble);
    
                        target.setPrice(Double.parseDouble(formattedPrice));
                        break;
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid Input! Please enter a valid price.");
                    System.out.println("Enter new price (or press Enter to skip): ");
                    newPrice = scanner.nextLine();
                }
            }

            System.out.println("Enter new description (or press Enter to skip): ");
            String newDisc = scanner.nextLine();
            if (!newDisc.isEmpty()) {
                target.setDescription(newDisc);
            }

            System.out.println("Enter new stock quantity (or press Enter to skip): ");
            String newStock = scanner.nextLine();
            while (!newStock.isEmpty()) {
                try {
                    int newStockInt = Integer.parseInt(newStock);
                    if (newStockInt < 0) {
                        System.out.println("Invalid Input! Please enter a stock amount.");
                        System.out.println("Enter new stock quantity (or press Enter to skip): ");
                        newStock = scanner.nextLine();
                    }
                    else {
                        target.setStock(newStockInt);
                        break;
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid Input! Please enter a stock amount.");
                    System.out.println("Enter new stock quantity (or press Enter to skip): ");
                    newStock = scanner.nextLine();
                }
            }

            productController.sellerWrite(target);
        }
        catch (Exception e) {
            System.out.println("An unexpected error has occurred. Please try again.");
        }
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

    private void generateSalesReport(List<Product> products, List<Order> orders, Scanner scanner) {
        
        try {
            if (orders.isEmpty()) {
                System.out.println();
                System.out.println("== Your Sales Report ==");
                System.out.println("No one has ordered your products yet.");
                return;
            }
            
            System.out.println("Enter the start date <yyyy-mm-dd> (inclusive):");
            String startDate = scanner.nextLine();
            System.out.println("Enter the end date <yyyy-mm-dd> (inclusive):");
            String endDate = scanner.nextLine();

            orders = orderController.sortOrderByDate(orders, startDate, endDate);
            
            if (orders.isEmpty()) {
                System.out.println();
                System.out.println("== Your Sales Report ==");
                System.out.println("No one has ordered your products within the specified time.");
                return;
            }

            // Gets all the sales and groups them by product
            int totalSales = 0;
            HashMap<String, Integer> salesHash = new HashMap<String, Integer>();
            for (Order item : orders) {
                int count = salesHash.getOrDefault(item.getProductName(), 0);
                salesHash.put(item.getProductName(), count + 1);
                totalSales++;
            }

            // Gets a list of top sellers
            List<String> topSellerList = new ArrayList<>();
            int highestSales = 0;
            for (Map.Entry<String, Integer> entry : salesHash.entrySet()) {
                String item = entry.getKey();
                int count = entry.getValue();

                if (topSellerList.isEmpty()) {
                    topSellerList.add(item);
                    highestSales = count;
                }
                else if (count > highestSales) {
                    topSellerList.clear();
                    topSellerList.add(item);
                    highestSales = count;
                }
                else if (count == highestSales) {
                    topSellerList.add(item);
                }
            }

            // Calcualates total revenue
            double totalRevenue = 0;
            for (Map.Entry<String, Integer> entry : salesHash.entrySet()) {
                String product = entry.getKey();
                int count = entry.getValue();

                for (Product p : products) {
                    if (p.getName().equals(product)) {
                        totalRevenue += p.getPrice()*count;
                        break;
                    }
                }
            }

            System.out.println();
            System.out.println("== Your Sales Report ==");
            System.out.println("Total Sales: " + totalSales);
            System.out.printf("Total Revenue: $%.2f", totalRevenue);
            System.out.println("\n=======================================================");
            System.out.println("Your Top Seller(s):");
            System.out.println(String.join(", ", topSellerList) + " with " + highestSales + " sales each.");
            System.out.println("=======================================================");

            exitMenu();
        }
        catch (Exception e) {
            System.out.println("An unexpected error has occurred");
        }
    
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

        if (sellerChoice != options.size()) {
            products.get(sellerChoice - 1).toggleVisibility();

            productController.sellerWrite(products.get(sellerChoice - 1));
        }
    }
    
    // Used by: toggleListings, 
    private int editMenu(List<String> options){
        options.add("Back");
        return Menu.selection(options);
    }
    private void exitMenu() {
        Menu.singleSelection();
    }
}

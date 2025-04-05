package controller;

import model.Menu;
import model.Order;
import model.Product;
import model.Seller;
import view.SellerView;

import java.text.DecimalFormat;
import java.util.*;

public class SellerController {
    private final Seller seller;
    private final ProductController productController;
    private final OrderController orderController;
    private final SellerView view;

    public SellerController(Seller seller, ProductController productController, OrderController orderController, SellerView view) {
        this.seller = seller;
        this.productController = productController;
        this.orderController = orderController;
        this.view = view;
    }

    public void run() {
        int choice = -1;
        while (choice != 9) {
            view.displaySellerMenu();
            choice = view.getSellerMenuChoice();
            switch (choice) {
                case 1 -> view.viewSellerProfile(seller);
                case 2 -> {
//                    this.addProduct(products, scanner, productController);
//                    TODO:@Dehan Just need to update parameters and more code cleanup

                }
                case 3 -> {
//                    this.updateProduct(products, scanner, productController);
//                    TODO:@Dehan Just need to update parameters and more code cleanup

                }
                case 4 -> {
                    // Stub: view orders related to seller’s products
                    System.out.println("Viewing orders for your products (stub).");
                }
                case 5 -> {
                    // Stub: set min-max pricing
                    System.out.println("Setting min-max price ranges (stub).");
//                    this.setMinMaxPrice(products, scanner);
//                    TODO:@Dehan Just need to update parameters and more code cleanup
                }
                case 6 -> {
                    // Stub: set bundled items recommendation
                    System.out.println("Setting bundled items for a product (stub).");
//                    TODO:@Dehan Just need to update parameters and more code cleanup
                }
                case 7 -> {
//                    this.generateSalesReport(products, orders, scanner, orderController);
//                    TODO:@Dehan Just need to update parameters and more code cleanup
                }
                case 8 -> {
//                    this.toggleListings(products, productController);
//                    TODO:@Dehan Just need to update parameters and more code cleanup
                }
                case 9 -> {
                    System.out.println("Logging out...");
                }
                default -> {
                    System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }


    private void addProduct(List<Product> products, Scanner scanner, ProductController productController) {
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
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input! Please enter a valid price.");
                }
            }

            int stock = -1;
            while (stock < 0) {
                try {
                    System.out.println("Stock Quantity: ");
                    stock = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input! Please enter a valid stock amount.");
                }
            }

            Product newProduct = new Product("P" + System.currentTimeMillis(), name, description, price, stock, this.seller.getUserID(), "False"); // Added default false 020425 Dehan
            products.add(newProduct);

            productController.sellerNewProduct(newProduct);
        } catch (Exception e) {
            System.out.println("An unexpected error has occurred. Please try again.");
        }
    }

    private void updateProduct(List<Product> products, Scanner scanner, ProductController productController) {
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
                    } else {
                        DecimalFormat decFormat = new DecimalFormat("#.00");
                        String formattedPrice = decFormat.format(newPriceDouble);

                        target.setPrice(Double.parseDouble(formattedPrice));
                        break;
                    }
                } catch (NumberFormatException e) {
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
                    } else {
                        target.setStock(newStockInt);
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input! Please enter a stock amount.");
                    System.out.println("Enter new stock quantity (or press Enter to skip): ");
                    newStock = scanner.nextLine();
                }
            }

            productController.sellerWrite(target);
        } catch (Exception e) {
            System.out.println("An unexpected error has occurred. Please try again.");
        }
    }

    private void setMinMaxPrice(List<Product> products, Scanner scanner) {
        System.out.println("All your products:");
        for (Product p : products) {
            if (p.getSellerID().equals(this.seller.getUserID())) {
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
            if (p.getProductID().equals(productId) && p.getSellerID().equals(this.seller.getUserID())) {
                return p;
            }
        }
        return null;
    }

    private void generateSalesReport(List<Product> products, List<Order> orders, Scanner scanner, OrderController orderController) {

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
                } else if (count > highestSales) {
                    topSellerList.clear();
                    topSellerList.add(item);
                    highestSales = count;
                } else if (count == highestSales) {
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
                        totalRevenue += p.getPrice() * count;
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
        } catch (Exception e) {
            System.out.println("An unexpected error has occurred");
        }

    }

    // Toggles visibility
    private void toggleListings(List<Product> products, ProductController productController) {

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
    private int editMenu(List<String> options) {
        options.add("Back");
        return Menu.selection(options);
    }

    private void exitMenu() {
        Menu.singleSelection();
    }
}

package controller;

import model.Menu;
import model.Order;
import model.Product;
import model.Seller;
import util.CSVExporter;
import util.Env;
import view.SellerView;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    public void run(Scanner scanner) {
        int choice = -1;
        while (choice != 9) {
            seller.displayMenu();
            choice = view.getSellerMenuChoice();
            switch (choice) {
                case 1 -> view.viewSellerProfile(seller);
                case 2 -> addProduct(scanner);
                case 3 -> updateProduct(scanner);
                case 4 -> showOrders();
                case 5 -> setMinMaxPrice(scanner);
                case 6 -> setBundledItems(scanner);
                case 7 -> generateSalesReport(scanner);
                case 8 -> toggleListings();
                case 9 -> {
                    System.out.println("Logging out...");
                }
                default -> {
                    System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    private void showOrders() {
        List<Order> sellerOrders = orderController.getSellerOrders(seller.getUserID());
        orderController.listOrders(sellerOrders);
        if (!sellerOrders.isEmpty()) {
            handleSellerActions();
        }
    }

    private void updateOrderStatus() {
        String searchOrderID = view.enterOrderID();
        try {
            Order order = orderController.getSellerOrders(seller.getUserID())
                    .stream().filter(o -> o.getOrderID().equals(searchOrderID)).findFirst().orElseThrow();
            String newOrderStatus = view.selectOrderStatus();
            //Set new status to order
            order.setStatus(newOrderStatus);
            CSVExporter.updateOrder(order);
            System.out.println("Order Status Updated!");
        } catch (NoSuchElementException e) {
            System.out.println("Order not found");
        }

    }

    private void addProduct(Scanner scanner) {
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
            productController.addProduct(newProduct);
        } catch (Exception e) {
            System.out.println("An unexpected error has occurred. Please try again.");
        }
    }

    private void updateProduct(Scanner scanner) {
        try {
            //Get All seller products
            List<Product> products = productController.getStoreProducts(this.seller.getUserID());
            List<String> options = new ArrayList<>(products.stream().map(product -> String.format("%-14s | %-10s | $%-5.2f | %-20s | %d",
                                                                                        product.getProductID(),
                                                                                        product.getName(),
                                                                                        product.getPrice(),
                                                                                        product.getDescription(),
                                                                                        product.getStock()))
            .collect(Collectors.toList()));

            String selectedProductID = (view.selectProductID(options)).split(" ")[0];
            if (!selectedProductID.equalsIgnoreCase("Back")) {
                Product target = productController.getProductById(selectedProductID).orElseThrow();

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
            }

        } catch (NoSuchElementException nse) {
            System.out.println("Product Not found");
        } catch (Exception e) {
            System.out.println("An unexpected error has occurred. Please try again.");
        }
    }

    private void setMinMaxPrice(Scanner scanner) {
        List<Product> products = productController.getStoreProducts(this.seller.getUserID());
        productController.listProducts(products);

        // Set Minimum Price
        System.out.print("Enter the Product ID to set a Minimum Price: ");
        String productId = scanner.nextLine();
        Product minProduct = products.stream().filter(p -> p.getProductID().equals(productId)).findAny().orElse(null); //findMyProductById(products, productIdMin);
        if (minProduct != null) {
            double minPrice = -1;
            while(minPrice > minProduct.getPrice() || minPrice <= 0) {
                try {
                    System.out.print("Enter the new minimum price: ");
                    minPrice = Double.parseDouble(scanner.nextLine());
                    if (minPrice > minProduct.getPrice()){System.out.println("Minimum price cannot be higher than original price (" + minProduct.getPrice() + ").");}
                    else{minProduct.setMinPrice(minPrice);
                    productController.sellerWrite(minProduct);
                    System.out.println("Minimum price set successfully.");}
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid price entered. Please try again.");
                }
            }
        } else {
            System.out.println("Product not found or unauthorized.");
        }

        Product maxProduct = findMyProductById(products, productId);
        if (maxProduct != null) {
            double maxPrice = -1;
            while(maxPrice < maxProduct.getPrice() || maxPrice <= 0) {
                try {
                    System.out.print("Enter the new maximum price: ");
                    maxPrice = Double.parseDouble(scanner.nextLine());
                    if (maxPrice < maxProduct.getPrice()) {System.out.println("Maximum price cannot be lower than original price (" + maxProduct.getPrice() + ").");}
                    else{maxProduct.setMaxPrice(maxPrice);
                    productController.sellerWrite(maxProduct);
                    System.out.println("Maximum price set successfully.");}
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid price entered. Please try again.");
                }
            }
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

    private void generateSalesReport(Scanner scanner) {
        try {
            List<Order> orders = orderController.getSellerOrders(seller.getUserID());

            if (orders.isEmpty()) {
                System.out.println();
                System.out.println("== Your Sales Report ==");
                System.out.println("No one has ordered your products yet.");
                exitMenu();
                return;
            }

            System.out.println("Enter the start date <yyyy-mm-dd> (inclusive) [Press Enter for Today]:");
            String startDate = scanner.nextLine();
            if (startDate.isEmpty()) {
                startDate = LocalDate.now().toString();
            }

            System.out.println("Enter the end date <yyyy-mm-dd> (inclusive) [Press Enter for Today]:");
            String endDate = scanner.nextLine();
            if (endDate.isEmpty()) {
                endDate = LocalDate.now().toString();
            }

            orders = orderController.sortOrderByDate(orders, startDate, endDate);

            if (orders.isEmpty()) {
                System.out.println();
                System.out.println("== Your Sales Report ==");
                System.out.println("No one has ordered your products within the specified time.");
                exitMenu();
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
            for (Order o : orders) {
                    totalRevenue += o.getCost();
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
    private void toggleListings() {
        List<Product> products = productController.getStoreProducts(this.seller.getUserID());
        List<String> options = new ArrayList<>(products.stream().map(product -> String.format("%-14s | %-10s | $%-5.2f | %-20s | %d | %s",
                                                                                        product.getProductID(),
                                                                                        product.getName(),
                                                                                        product.getPrice(),
                                                                                        product.getDescription(),
                                                                                        product.getStock(),
                                                                                        product.isActive()))
        .collect(Collectors.toList()));

        String selectedProductID = (view.selectProductID(options)).split(" ")[0];
        if (!selectedProductID.equalsIgnoreCase("Back")) {
            Product target = productController.getProductById(selectedProductID).orElseThrow();
            target.toggleVisibility();

            productController.sellerWrite(target);
        }
    }

    private void exitMenu() {
        Menu.singleSelection();
    }

    public void setBundledItems(Scanner scanner) {
        List<Product> products=productController.getStoreProducts(seller.getUserID());
        List<String> productIds=products.stream().map(Product::getProductID).toList();
        view.displaySellerProducts(products);

        System.out.print("Enter Product ID to set recommendations for: ");
        String baseProductId=scanner.nextLine().trim();

        Product baseProduct=findMyProductById(products, baseProductId);
        if(baseProduct==null) {
            System.out.println("Invalid product ID.");
            return;
        }

        List<String> bundle=new ArrayList<>();
        bundle.add(baseProductId);

        while(true){
            System.out.print("Enter related Product ID to add (or 'done' to finish): ");
            String relatedId=scanner.nextLine().trim();

            if (relatedId.equalsIgnoreCase("done")) break;

            if (relatedId.equals(baseProductId)) {
                System.out.println("You cannot bundle the product with itself.");
            } else if (!productIds.contains(relatedId)) {
                System.out.println("That Product ID doesn't exist in your listings.");
            } else if (bundle.contains(relatedId)) {
                System.out.println("This product is already in the bundle.");
            } else {
                bundle.add(relatedId);
                System.out.println("Product added: " + relatedId);
            }
        }

        if (bundle.size()>1) {
//            CSVExporter.appendBundle(bundle, "./data/bundles.csv");
            CSVExporter.appendBundle(bundle, Env.get("DATA_DIR") + "/bundles.csv");
            System.out.println("Bundled recommendation saved.");
        } else {
            System.out.println("Bundle must include at least one additional product.");
        }
    }

    private void handleSellerActions() {
        List<String> options = List.of("Update Order Status", "Back");
        int action = view.sellerActions(options);
        switch (action) {
            case 1 -> updateOrderStatus();
            case 2 -> {
            }
            default -> System.out.println("Invalid Input. Try again.");
        }
    }
}

package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Customer extends User {

    private final Cart cart;

    public Customer(String userID, String name, String email, String password, String contact, String address, boolean firstLogin) {
        super(userID, name, email, password, "Customer", contact, address, firstLogin);
        this.cart = new Cart();
    }

    @Override
    public void displayMenu() {
        System.out.println();
        System.out.println("Customer Menu:");
        System.out.println("1. View Profile");
        System.out.println("2. Browse Products");
        System.out.println("3. Search Products");
        System.out.println("4. View Cart");
        System.out.println("5. Checkout");
        System.out.println("6. View Order Status");
        System.out.println("7. View Past Orders");
        System.out.println("8. Logout");
    }

    public void handleCustomerMenu(Scanner scanner, List<Product> products, List<Order> order,
                                   ProductController productController, OrderController orderController) {
        
        int choice = 0;
        while (choice != 8) {
            this.displayMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            switch (choice) {
                case 1 -> this.viewProfile();
                case 2 -> this.browseProducts(products, scanner, productController);
                case 3 -> this.searchProducts(products, scanner, productController);
                case 4 -> this.cart.viewCart();
                case 5 -> this.checkout(scanner);
                case 6 -> this.viewOrderStatus(order);
                case 7 -> this.viewPastOrders(order);
                case 8 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void viewProfile() {
        System.out.println("Profile Information:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Contact: " + contact);
        System.out.println("Address: " + address);
        // Provide option to update details (except role or userID)
    }

    private void browseProducts(List<Product> products, Scanner scanner, ProductController productController) {
        while (true) {
            List<Product> visibleProducts = productController.getActiveProducts(products);
            System.out.println("Available Products:");
            visibleProducts.forEach(System.out::println);
            System.out.println("--------------------");
            int user_action = productMenu();
            if (user_action == 1) {
                addToCart(products, scanner, productController);
            } else if (user_action == 2) {
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private void searchProducts(List<Product> products, Scanner scanner, ProductController productController) {
        scanner.nextLine();
        while (true) {
            String search = Menu.textInput("Enter search keyword: ");
            List<Product> results = productController.searchProducts(products, search);
            System.out.println("Search Results:");
            results.forEach(System.out::println);
            int option = productMenu();
            if (option == 1) {
                addToCart(products, scanner, productController);
            } else if (option == 2) {
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private int productMenu() {
        List<String> options = List.of("Add to cart", "Back");
        return Menu.selection(options);
    }

    private void addToCart(List<Product> products, Scanner scanner, ProductController productController) {
        String prod_ID = Menu.textInput("Enter Product ID (Case Sensitive). Type 'cancel' to cancel");

        if (prod_ID.equalsIgnoreCase("cancel")) {
            System.out.println("Cancelled");
            return;
        }

        Optional<Product> addProduct = productController.getProductById(products, prod_ID);
        if (addProduct.isEmpty()) {
            System.out.println("Product not found");
            return;
        }

        Product product = addProduct.get();
        while (true) {
            int prod_qty = Menu.numericInput("Enter product quantity. Type 0 to cancel");
            if (prod_qty == 0) return;

            if (prod_qty < 0 || prod_qty > product.getStock()) {
                System.out.println("Invalid Stock Amount");
            } else {
                System.out.println("Add to Cart:");
                System.out.println("ID: " + product.getProductID());
                System.out.println("Name: " + product.getName());
                System.out.println("Qty: " + prod_qty);
                System.out.println("--------------------");
                System.out.printf("Total Price:$%.2f %n", (prod_qty * product.getPrice()));

                String confirmation = Menu.textInput("Confirm Purchase? y/n");
                if (confirmation.equalsIgnoreCase("y")) {
                    cart.addItem(product, prod_qty);
                    System.out.println("ADDED TO CART");
                    break;
                } else if (confirmation.equalsIgnoreCase("n")) {
                    System.out.println("Purchase Cancelled");
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
        }
    }


    private void checkout(Scanner scanner) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        // Stub: simulate checkout process
        System.out.println("Checking out with the following items:");
        cart.displayCart();
        System.out.print("Enter shipping address (or press Enter to use your default): ");
        String shipAddr = scanner.nextLine();
        if (shipAddr.isEmpty()) {
            shipAddr = address;
        }
        // Choose shipping option
        System.out.println("Select Shipping Method: 1. Ship by Air  2. Express  3. Freight  4. Local");
        String shipOption = scanner.nextLine();
        // Payment simulation
        System.out.println("Payment authorized (simulation).");

        // @Ethan for your attention
        System.out.println("Date Purchased: " + LocalDate.now()); // Format in 2025-04-05 for LocalDate.now()

        // Generate order ID and clear cart (stub)
        System.out.println("Order placed successfully. Order ID: " + System.currentTimeMillis());
        cart.clearCart();
    }

    private void viewOrderStatus(List<Order> order) {
        if (order.isEmpty()) {
            System.out.println("You have no pending orders!");
            return;
        }

        else {
            System.out.println("\n== Recent Orders ==");
            for (Order o : order) {
                if (o.getCustomerID().equals(userID)) {
                    System.out.println(o.toString());
                }
            }
            exitMenu();
        }
    }

    private void viewPastOrders(List<Order> order) {
        if (order.isEmpty()) {
            System.out.println("You have not ordered anything before.");
            return;
        }

        else {
            System.out.println("\n== Past Orders ==");
            for (Order o : order) {
                if (o.getCustomerID().equals(userID) && o.getStatus().equals("Delivered")) {
                    System.out.println(o.toStringPast());
                }
            }
            exitMenu();
        }
    }

    private void exitMenu() {
        Menu.singleSelection();
    }

    public Cart getCart() {
        return cart;
    }
}

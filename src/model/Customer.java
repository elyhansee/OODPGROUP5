package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Customer extends User {
    private Cart cart;

    public Customer(String userID, String name, String email, String password, String contact, String address,boolean firstLogin) {
        super(userID, name, email, password, "Customer", contact, address,firstLogin);
        this.cart = new Cart();
    }

    @Override
    public void displayMenu() {
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

    public static void handleCustomerMenu(Customer customer, Scanner scanner, List<Product> products) {
        int choice = 0;
        while (choice != 8) {
            customer.displayMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    customer.viewProfile();
                    break;
                case 2:
                    customer.browseProducts(products);
                    break;
                case 3:
                    customer.searchProducts(products, scanner);
                    break;
                case 4:
                    customer.cart.displayCart();
                    break;
                case 5:
                    customer.checkout(scanner);
                    break;
                case 6:
                    // Stub: view order status
                    System.out.println("Viewing order status (stub).");
                    break;
                case 7:
                    // Stub: view past orders
                    System.out.println("Viewing past orders (stub).");
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
        System.out.println("Profile Information:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Contact: " + contact);
        System.out.println("Address: " + address);
        // Provide option to update details (except role or userID)
    }

    private void browseProducts(List<Product> products) {
        while (true) {
            System.out.println("Available Products:");
            for (Product p : products) {
                if (p.isActive()) {
                    System.out.println(p);
                }
            }
            System.out.println("--------------------");
            int user_action = productMenu();
            switch (user_action) {
                case 1:
                    addToCart(products);
                    continue;
                case 2:
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
            break;
        }
    }

    private void searchProducts(List<Product> products, Scanner scanner) {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine().toLowerCase();
        System.out.println("Search Results:");
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(keyword) || p.getDescription().toLowerCase().contains(keyword)) {
                System.out.println(p);
            }
        }
    }

    private int productMenu() {
        List<String> options = new ArrayList<>();
        options.add("Add to cart");
        options.add("Back");
        return Menu.selection(options);
    }

    private void addToCart(List<Product> products) {
        while(true){
            String prod_ID = Menu.textInput("Enter Product ID (Case Sensitive). Type 'cancel' to cancel");
            if (prod_ID.equalsIgnoreCase("cancel")) {
                break;
            } else {
                Optional<Product> checkProduct = products.stream()
                        .filter(p -> p.getProductID().equals(prod_ID)).findFirst();
                if (checkProduct.isPresent()) {
                    while (true) {
                        int prod_qty = Menu.numericInput("Enter product quantity. Type 0 to cancel");
                        if (prod_qty != 0) {
                            if (prod_qty < 0 || prod_qty > checkProduct.get().getStock()) {
                                System.out.println("Invalid Stock Amount");
                                continue;
                            } else {
                                System.out.println("Add to Cart:");
                                System.out.println("ID: " + checkProduct.get().getProductID());
                                System.out.println("Name: " + checkProduct.get().getName());
                                System.out.println("Qty: " + prod_qty);
                                System.out.println("--------------------");
                                System.out.printf("Total Price:$%.2f %n", (prod_qty * checkProduct.get().getPrice()));
                                while (true) {
                                    String confirmation = Menu.textInput("Confirm Purchase? y/n");
                                    if (confirmation.equalsIgnoreCase("y")) {
                                        System.out.println("ADDED TO CART");
                                        break;
                                    } else if (confirmation.equalsIgnoreCase("n")) {
                                        System.out.println("Purchase Cancelled");
                                    } else {
                                        System.out.println("Invalid input");
                                        continue;
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }
                } else {
                    System.out.println("Product not found");
                }
                break;
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
        // Generate order ID and clear cart (stub)
        System.out.println("Order placed successfully. Order ID: " + System.currentTimeMillis());
        cart.clear();
    }

    public Cart getCart() {
        return cart;
    }
}

package model;

import java.util.List;
import java.util.Scanner;

public class Customer extends User {
    private Cart cart;

    public Customer(String userID, String name, String email, String password, String contact, String address) {
        super(userID, name, email, password, "Customer", contact, address);
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

    public static void handleCustomerMenu(Customer customer, Scanner scanner, List<Product> products, List<OrderStatus> orderStatus) {
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

            switch(choice) {
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
                    customer.viewOrderStatus(orderStatus);
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
        System.out.println("Available Products:");
        for (Product p : products) {
            if (p.isActive()) {
                System.out.println(p);
            }
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

    private void viewOrderStatus(List<OrderStatus> orderStatus) {
        if (orderStatus.isEmpty()) {
            System.out.println("This customer has no pending orders!");
            return;
        }

        else {
            System.out.println("\nRecent Orders:");
            for (OrderStatus orderStat : orderStatus) {
                //if (orderStat.getCustomerID().equals(userID)) {
                    System.out.println(orderStat.toString());
                //}
            }
            System.out.println();
        }
    }

    public Cart getCart() {
        return cart;
    }
}

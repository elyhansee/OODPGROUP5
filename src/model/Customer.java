package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private static final List<CartItem> cartItems = new ArrayList<>();

    public Customer(String userID, String name, String email, String password, String contact, String address, boolean firstLogin) {
        super(userID, name, email, password, "Customer", contact, address, firstLogin);
    }

    @Override
    public void displayMenu() {
    }


    private void viewOrderStatus(List<Order> order) {
        if (order.isEmpty()) {
            System.out.println("You have no pending orders!");
            return;
        } else {
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
        } else {
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

    //    private void checkout(Scanner scanner) {
//        if (cartController.isEmpty()) {
//            System.out.println("Your cart is empty.");
//            return;
//        }
//        // Stub: simulate checkout process
//        System.out.println("Checking out with the following items:");
//        cartController.displayCart();
//        System.out.print("Enter shipping address (or press Enter to use your default): ");
//        String shipAddr = scanner.nextLine();
//        if (shipAddr.isEmpty()) {
//            shipAddr = address;
//        }
//        // Choose shipping option
//        System.out.println("Select Shipping Method: 1. Ship by Air  2. Express  3. Freight  4. Local");
//        String shipOption = scanner.nextLine();
//        // Payment simulation
//        System.out.println("Payment authorized (simulation).");
//
//        // @Ethan for your attention
//        System.out.println("Date Purchased: " + LocalDate.now()); // Format in 2025-04-05 for LocalDate.now()
//
//        // Generate order ID and clear cart (stub)
//        System.out.println("Order placed successfully. Order ID: " + System.currentTimeMillis());
//        cartController.clearCart();
//    }
}

package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    public List<CartItem> cartItems;

    public Customer(String userID, String name, String email, String password, String contact, String address, boolean firstLogin) {
        super(userID, name, email, password, "Customer", contact, address, firstLogin);
        this.cartItems = new ArrayList<>();
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
}

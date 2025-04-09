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
}

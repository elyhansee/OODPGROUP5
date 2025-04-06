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
}

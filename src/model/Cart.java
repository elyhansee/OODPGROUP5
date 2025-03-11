package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<OrderItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        // In a full implementation, check if the product already exists in the cart.
        items.add(new OrderItem(product, quantity));
    }

    public void removeItem(String productID) {
        items.removeIf(item -> item.getProduct().getProductID().equals(productID));
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Cart Contents:");
            for (OrderItem item : items) {
                System.out.println(item);
            }
        }
    }

    public List<OrderItem> getItems() {
        return items;
    }
}

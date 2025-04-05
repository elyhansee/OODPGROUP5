package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<OrderItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void viewCart() {
        displayCart();
        if (!items.isEmpty()) {
            List<String> options = List.of("Proceed to Checkout", "Update Cart", "Remove Item", "Clear Cart", "Back");
            int option = Menu.selection(options);
            switch (option) {
                case 1 -> checkout();
                case 2 -> updateCart();
                case 3 -> removeItem();
                case 4 -> clearCart();
                case 5 -> {
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public void checkout() {
//        TODO: BY ETHAN
    }

    //    READ
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

    //    CREATE
    public void addItem(Product product, int quantity) {
        // In a full implementation, check if the product already exists in the cart.
        items.add(new OrderItem(product, quantity));
    }

    //    UPDATE
    public void updateCart() {
//        TODO: BY ETHAN
    }

    //   DELETE
    public void removeItem() {
//        items.removeIf(item -> item.getProduct().getProductID().equals(productID));
    }


    public void clearCart() {
        items.clear();
        System.out.println("Cart cleared successfully!");
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<OrderItem> getItems() {
        return items;
    }
}

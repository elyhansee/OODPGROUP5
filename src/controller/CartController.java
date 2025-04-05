package controller;

import model.*;
import view.CartView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CartController {
    private final List<CartItem> shoppingCart;
    private final Scanner scanner = new Scanner(System.in);
    private final CartView view;

    public CartController(List<CartItem> shoppingCart, CartView view) {
        this.shoppingCart = shoppingCart;
        this.view = view;
    }

    public void run() {
        int choice = view.displayCartMenu();
        switch (choice) {
            case 1 -> updateCart();
            case 2 -> removeItem();
            case 3 -> clearCart();
            case 4 -> System.out.println("Going Back");
            default -> System.out.println("Invalid input. Please try again.");
        }
    }

    public void checkout(Customer customer, OrderController orderController) {
        if (shoppingCart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        // Stub: simulate checkout process
        view.displayCart(shoppingCart);
        System.out.print("Enter shipping address (or press Enter to use your default): ");
        String shipAddr = view.enterShippingAddress(scanner);
        if (shipAddr.isEmpty()) shipAddr = customer.getAddress();

        // Choose shipping option
        String shipOption = view.selectShippingMethod();
        // Payment simulation
        System.out.println("Payment authorized (simulation).");

        // @Ethan for your attention
        String purchaseDate = LocalDate.now().toString();
//        System.out.println("Date Purchased: " + purchaseDate); // Format in 2025-04-05 for LocalDate.now()
        String orderID = Long.toString(System.currentTimeMillis());
        for (CartItem item : shoppingCart) {
            orderController.newOrder(item, customer, orderID, purchaseDate, shipAddr, shipOption);
        }
        // Generate order ID and clear cart (stub)
        System.out.println("Order placed successfully. Order ID: " + orderID);
        this.clearCart();
    }


    //    CREATE
    public void addItem(Product product, int quantity) {
        // In a full implementation, check if the product already exists in the cart.
        String cartItemID = Long.toString(System.currentTimeMillis());
        shoppingCart.add(new CartItem(cartItemID, product, quantity));
    }

    //    READ
    public void getCartItems() {
        view.displayCart(shoppingCart);
        if (!shoppingCart.isEmpty()) {
            this.run();
        }
    }

    //    UPDATE
    public void updateCart() {
//        TODO: BY ETHAN

    }

    //   DELETE
    public void removeItem() {
        String remove_pid = view.removeItem();
        try {
            shoppingCart.removeIf(item -> item.getProduct().getProductID().equals(remove_pid));
        } catch (NullPointerException e) {
            System.out.println("Product not found");
        }
    }

    public void clearCart() {
        shoppingCart.clear();
        System.out.println("Cart cleared successfully!");
    }

}

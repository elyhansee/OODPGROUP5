package controller;

import model.*;
import util.CSVExporter;
import view.CartView;

import java.time.LocalDate;
import java.util.*;

public class CartController {
    private final List<CartItem> shoppingCart;
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

    public void addToCart(ProductController productController, CustomerController customerController) {
        String prod_ID = view.addToCart();
        if (prod_ID.equalsIgnoreCase("cancel") || prod_ID.isEmpty()) {
            return;
        }

        try {
            Product findProduct = productController.getProductById(prod_ID).orElseThrow();
            while (true) {
                int prod_qty = view.enterItemQuantity();
                if (prod_qty == 0) return;

                if (prod_qty < 0 || prod_qty > findProduct.getStock()) {
                    System.out.println("Invalid Stock Amount");
                } else {
                    String confirmation = view.addToCartConfirmation(findProduct, prod_qty);
                    if (confirmation.equalsIgnoreCase("y")) {
                        this.addItem(findProduct, prod_qty);
                        System.out.println("ADDED TO CART");

                        Recommendations.displayRecommendations(prod_ID, productController.getActiveProducts(), productController, customerController);

                        break;
                    } else if (confirmation.equalsIgnoreCase("n")) {
                        System.out.println("Cancelled");
                        break;
                    } else {
                        System.out.println("Invalid input");
                    }
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Product not found");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void checkout(Customer customer, OrderController orderController, ProductController productController) {
        if (shoppingCart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        // Stub: simulate checkout process
        view.displayCart(shoppingCart);
        String shipAddr = view.enterShippingAddress();
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
            item.getProduct().incrementSales();
            item.getProduct().applyDynamicPricing();
            productController.decreaseStock(item.getProduct(), item.getQuantity());
            CSVExporter.updateProducts(item.getProduct(), "src/data/products.csv"); // Save updated price
        }
        // Generate order ID and clear cart (stub)
        System.out.println("Order placed successfully. Order ID: " + orderID);
        this.clearCart();
    }

    public void displayCart() {
        view.displayCart(shoppingCart);
        if (!shoppingCart.isEmpty()) {
            run();
        }
    }

    //    CREATE
    private void addItem(Product product, int quantity) {
        // In a full implementation, check if the product already exists in the cart.
        String cartItemID = Long.toString(System.currentTimeMillis());
        shoppingCart.add(new CartItem(cartItemID, product, quantity));
    }

    //    UPDATE
    private void updateCart() {
        String cartItemID = view.enterCartItemId();
        if (!cartItemID.equalsIgnoreCase("cancel")) {
            try {
                CartItem item = shoppingCart.stream().filter(i -> i.getCartItemID().equalsIgnoreCase(cartItemID)).findAny().orElseThrow();
                int changeQuantity = view.enterItemQuantity();
                if (changeQuantity < 0 || changeQuantity == item.getQuantity()) {
                    System.out.println("Invalid Quantity or same quantity entered");
                } else if (changeQuantity > 0) {
                    String confirm = view.confirmQuantityChange();
                    if (confirm.toLowerCase().charAt(0) == 'y') {
                        item.setQuantity(changeQuantity);
                        System.out.println("Quantity Changed");
                    }
//                    shoppingCart.stream().filter(i -> i.getCartItemID().equalsIgnoreCase(cartItemID)).findAny().ifPresent(a -> a.set);
                }
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Unknown Error occured \n" + e.getMessage());
            }
        }
    }

    //   DELETE
    private void removeItem() {
        String cartItemId = view.enterCartItemId();
        if (!cartItemId.equalsIgnoreCase("cancel")) {
            try {
                shoppingCart.removeIf(item -> item.getCartItemID().equals(cartItemId));
                view.removeSuccess();
            } catch (NullPointerException e) {
                System.out.println("Product not found \n" + e.getMessage());
            } catch (UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void clearCart() {
        shoppingCart.clear();
        System.out.println("Cart cleared successfully!");
    }

}

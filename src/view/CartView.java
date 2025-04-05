package view;

import model.CartItem;
import model.Menu;
import model.OrderItem;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CartView {

    public int displayCartMenu() {
        List<String> options = List.of("Update Cart", "Remove Item", "Clear Cart", "Back");
        return Menu.selection(options);
    }

    public void displayCart(List<CartItem> cartItems) {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("\nYour Cart:");
        for (CartItem item : cartItems) {
            System.out.println(item);
        }
        System.out.println("---------------------------");
//        Calculate Grand total
        double total = cartItems.stream()
                .mapToDouble(
                        item ->
                                item.getProduct().getPrice() * item.getQuantity()).sum();

        System.out.printf("Total %.2f %n", total);
    }

    public String removeItem() {
        return Menu.textInput("Enter Product ID of Item to remove: ");
    }

    public String enterShippingAddress(Scanner scanner) {
        return scanner.nextLine();
    }

    public String selectShippingMethod() {
        System.out.println("Select Shipping Method: 1. Ship by Air  2. Express  3. Freight  4. Local");
        List<String> options = List.of("Air", "Express", "Freight", "Local");
        return switch (Menu.selection(options)) {
            case 1 -> "Air";
            case 2 -> "Express";
            case 3 -> "Freight";
            case 4 -> "Local";
            default -> "Local";
        };
    }
}

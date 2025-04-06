package view;

import model.CartItem;
import model.Menu;
import model.Product;

import java.util.List;

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
        //Calculate Grand total
        double total = cartItems.stream()
                .mapToDouble(
                        item ->
                                item.getProduct().getPrice() * item.getQuantity()).sum();

        System.out.printf("Total %.2f %n", total);
    }

    //Add to cart
    public String addToCart(){
        return Menu.textInput("Enter product ID (Case Sensitive). Type 'cancel' to cancel: ");
    }

    //OPEN: Remove from cart
    public void removeSuccess() {
        System.out.println("Successfully Removed Item");
    }
    //CLOSE: Remove from cart


    //Add to Cart Confirmation Details
    public String addToCartConfirmation(Product product, int qty) {
        System.out.println("Add to Cart:");
        System.out.println("ID: " + product.getProductID());
        System.out.println("Name: " + product.getName());
        System.out.println("Qty: " + qty);
        System.out.println("--------------------");
        System.out.printf("Total Price:$%.2f %n", (qty * product.getPrice()));

        return Menu.textInput("Add to Cart? y/n: ");
    }

    //OPEN: Checkout
    public String enterShippingAddress() {
        return Menu.textInput("Enter shipping address (or press Enter to use your default): ");
    }

    public String selectShippingMethod() {
        System.out.println("Select Shipping Method:");
        List<String> options = List.of("Air", "Express", "Freight", "Local");
        return switch (Menu.selection(options)) {
            case 1 -> "Air";
            case 2 -> "Express";
            case 3 -> "Freight";
            case 4 -> "Local";
            default -> "Local";
        };
    }
    //CLOSE: Checkout

    public String enterCartItemId() {
        return Menu.textInput("Enter Cart Item ID (Type 'cancel' to cancel): ");
    }

    public int enterItemQuantity() {
        return Menu.numericInput("Enter quantity (Type 0 to cancel): ");
    }

    public String confirmQuantityChange() {
        //TODO: YES OR NO
        return Menu.textInput("Confirm Quantity Change? y/n: ");
    }

}

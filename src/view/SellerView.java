package view;

import model.Menu;
import model.Order;
import model.Product;
import model.Seller;

import java.util.List;

public class SellerView {

    public void displaySellerMenu() {
        System.out.println();
        System.out.println("Seller Menu:");
        System.out.println("1. View Profile");
        System.out.println("2. Add New Product Listing");
        System.out.println("3. Update Product Information");
        System.out.println("4. View Orders for My Products");
        System.out.println("5. Set Min-Max Price Ranges");
        System.out.println("6. Set Bundled Items for Product");
        System.out.println("7. Generate Sales Report");
        System.out.println("8. Toggle Product Listing");
        System.out.println("9. Logout");
    }

    public int getSellerMenuChoice() {
        return Menu.numericInput("Enter your choice: ");
    }

    public int sellerActions(List<String> options) {
        return Menu.selection(options);
    }

    public void viewSellerProfile(Seller seller) {
        System.out.println("Seller Profile:");
        System.out.println("Name: " + seller.getName());
        System.out.println("Email: " + seller.getEmail());
    }



    public void displaySellerProducts(List<Product> products) {
        System.out.println("\nYour Products:");
        for (Product p : products) {
            System.out.println(p.toStringSeller());
        }
    }

    public String selectProductID(List<String> options) {
        options.add("Back");
        return options.get(Menu.selection(options) - 1);
    }

    public String enterOrderID() {
        return Menu.textInput("Enter Order ID: ");
    }

    public String selectOrderStatus() {
        System.out.println("Set new order status: ");
        List<String> orderStatus = List.of("Pending", "In Progress", "Shipped");
        return orderStatus.get(Menu.selection(orderStatus) - 1);
    }

}

package view;

import model.Menu;
import model.Seller;

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
        System.out.println("8. Logout");
    }

    public int getSellerMenuChoice() {
        return Menu.numericInput("Enter your choice: ");
    }

    public void viewSellerProfile(Seller seller) {
        System.out.println("Seller Profile:");
        System.out.println("Name: " + seller.getName());
        System.out.println("Email: " + seller.getEmail());
    }

}

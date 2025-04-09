package model;

import controller.OrderController;
import controller.ProductController;
import util.CSVExporter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Seller extends User {

    public Seller(String userID, String name, String email, String password, String contact, String address, boolean firstLogin) {
        super(userID, name, email, password, "Seller", contact, address, firstLogin);
    }

    @Override
    public void displayMenu() {
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
}

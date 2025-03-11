package model;

import java.util.List;
import java.util.Scanner;

public class Administrator extends User {

    public Administrator(String userID, String name, String email, String password, String contact, String address) {
        super(userID, name, email, password, "Administrator", contact, address);
    }

    @Override
    public void displayMenu() {
        System.out.println("Administrator Menu:");
        System.out.println("1. View Profile");
        System.out.println("2. Manage User Accounts");
        System.out.println("3. Manage Global Discounts / Campaigns");
        System.out.println("4. Generate Inventory and Order Insights");
        System.out.println("5. Logout");
    }

    public static void handleAdminMenu(Administrator admin, Scanner scanner, List<User> users, List<Product> products) {
        int choice = 0;
        while (choice != 5) {
            admin.displayMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }
            switch(choice) {
                case 1:
                    admin.viewProfile();
                    break;
                case 2:
                    // Stub: manage user accounts
                    System.out.println("Managing user accounts (stub).");
                    break;
                case 3:
                    // Stub: manage discounts or campaigns
                    System.out.println("Managing global discounts/campaigns (stub).");
                    break;
                case 4:
                    // Stub: generate overall inventory and order insights
                    System.out.println("Generating inventory and order insights (stub).");
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void viewProfile() {
        System.out.println("Administrator Profile:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        // Additional admin-specific details if needed
    }
}

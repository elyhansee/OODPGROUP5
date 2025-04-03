package model;

import util.CSVExporter;

import java.util.List;
import java.util.Scanner;

public class Administrator extends User {

    public Administrator(String userID, String name, String email, String password, String contact, String address) {
        super(userID, name, email, password, "Administrator", contact, address,false);
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
                    admin.manageUsers(users, scanner);
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

    private void manageUsers(List<User> users, Scanner scanner) {
        while (true) {
            System.out.println("Manage Users:");
            System.out.println("1. Add New User");
            System.out.println("2. Update User Contact/Address");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> {
                    registerNewUser(users, scanner);
                }
            }
        }
    }

    public static void registerNewUser(List<User> users, Scanner scanner) {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        String role = "";
        while (true) {
            System.out.print("Enter Role (Customer/Seller): ");
            role = scanner.nextLine().trim();
            if (role.equalsIgnoreCase("Customer") || role.equalsIgnoreCase("Seller")) {
                break;
            } else {
                System.out.println("Invalid role. Please enter 'Customer' or 'Seller'.");
            }
        }
        System.out.print("Enter Contact: ");
        String contact = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        String userIDPrefix = role.equalsIgnoreCase("Customer") ? "C" : "S";
        String userID = userIDPrefix + System.currentTimeMillis();
        String defaultPassword = generateRandomPassword();
        System.out.println("[Simulated Email] Your default password is: " + defaultPassword);

        User newUser;
        if (role.equalsIgnoreCase("Customer")) {
            newUser = new Customer(userID, name, email, defaultPassword, contact, address, true);
        } else if (role.equalsIgnoreCase("Seller")) {
            newUser = new Seller(userID, name, email, defaultPassword, contact, address, true);
        } else {
            System.out.println("Invalid role. Registration failed.");
            return;
        }

        users.add(newUser);
        CSVExporter.appendUserToCSV(newUser, "src/data/users.csv");
        System.out.println("Registration successful! Please log in using email and default password.");
    }

    public static String generateRandomPassword() {
        return "PW" + (int) (Math.random() * 10000);
    }
    private void viewProfile() {
        System.out.println("Administrator Profile:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        // Additional admin-specific details if needed
    }
}


import java.util.Scanner;

import model.User;
import model.Customer;
import model.OrderStatus;
import model.Seller;
import model.Administrator;
import util.CSVExporter;
import util.CSVImporter;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class Main {
    // In a complete system, these lists could be populated from CSV files.
    public static List<User> users = CSVImporter.importUsers("src/data/users.csv");
    public static List<Product> products = CSVImporter.importProducts("src/data/products.csv");
    public static List<OrderStatus> orders = CSVImporter.importOrders("src/data/orders.csv");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("˙⋆✮ Welcome to the E-Commerce Management System (ECMS) ✮⋆˙ ");
        while (true) {
            System.out.print("Are you a new user? (yes/no): ");
            String newUser = scanner.nextLine().toLowerCase();
            if (newUser.equals("y") || newUser.equals("yes")) {
                Administrator.registerNewUser(users, scanner);
                break;
            } else if (newUser.equals("n") || newUser.equals("no")) {
                break; // proceed to log in
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }

        // Login (for simplicity, we assume email and password login)
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User currentUser = authenticate(email, password);
        if (currentUser == null) {
            System.out.println("Invalid credentials. Exiting...");
            System.exit(0);
        }

        // Force change of default password on first login (stubbed)
        if (currentUser.isFirstLogin()) {
            System.out.print("Please change your default password: ");
            String newPassword = scanner.nextLine();
            currentUser.setPassword(newPassword);
            currentUser.setFirstLogin(false);
            // Save new pw here
            CSVExporter.updateUserPasswordByUID(currentUser.getUserID(), newPassword, "src/data/users.csv");
        }

        // Dispatch to user role menus
        switch (currentUser.getRole()) {
            case "Customer":
                List<OrderStatus> itemsOrdered = new ArrayList<>();
                for (OrderStatus ordered : orders) {
                    if (ordered.getCustomerID().equals(currentUser.getUserID())) {
                        itemsOrdered.add(ordered);
                    }
                }
                Customer.handleCustomerMenu((Customer) currentUser, scanner, products, itemsOrdered);
                break;
            case "Seller":
                Seller.handleSellerMenu((Seller) currentUser, scanner, products);
                break;
            case "Administrator":
                Administrator.handleAdminMenu((Administrator) currentUser, scanner, users, products);
                break;
            default:
                System.out.println("Unknown user role. Exiting...");
                break;
        }
        scanner.close();
    }

    private static User authenticate(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }



}

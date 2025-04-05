
import java.util.Scanner;

import controller.OrderController;
import controller.ProductController;
import model.User;
import model.Customer;
import model.Order;
import model.Seller;
import model.Administrator;
import util.CSVExporter;
import util.CSVImporter;
import model.Product;
import util.Env;

import java.util.ArrayList;
import java.util.List;

public class Main {
    // In a complete system, these lists could be populated from CSV files.
    public static List<User> users = CSVImporter.importUsers(Env.get("DATA_DIR") + "/users.csv");
    public static List<Product> products = CSVImporter.importProducts(Env.get("DATA_DIR") + "/products.csv");
    public static List<Order> orders = CSVImporter.importOrders(Env.get("DATA_DIR") + "/orders.csv");

    public static final ProductController productcontroller = new ProductController();
    public static final OrderController ordercontroller = new OrderController();

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
            case "Customer" -> {
                Customer customer = (Customer) currentUser;
                customer.handleCustomerMenu(scanner, products, getCustomerOrders(customer), productcontroller, ordercontroller);
            }
            case "Seller" -> {
                Seller.handleSellerMenu((Seller) currentUser, scanner, products, orders, productcontroller, ordercontroller);
                break;
            case "Administrator":
                Administrator.handleAdminMenu((Administrator) currentUser, scanner, users, products,orders);
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



    private static List<Order> getCustomerOrders(Customer customer) {
        List<Order> itemsOrdered = new ArrayList<>();
        for (Order ordered : orders) {
            if (ordered.getCustomerID().equals(customer.getUserID())) {
                itemsOrdered.add(ordered);
            }
        }
        return itemsOrdered;
    }

}

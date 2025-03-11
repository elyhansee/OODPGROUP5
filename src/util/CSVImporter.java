package util;

import java.util.ArrayList;
import java.util.List;
import model.User;
import model.Customer;
import model.Seller;
import model.Administrator;
import model.Product;

public class CSVImporter {
    public static List<User> importUsers(String filePath) {
        // Stub: In a full implementation, read the CSV file and create User objects.
        // For demonstration, we return a hard-coded list.
        List<User> users = new ArrayList<>();
        users.add(new Customer("C001", "Alice", "alice@example.com", "password", "12345678", "123 Street"));
        users.add(new Seller("S001", "Bob", "bob@example.com", "password", "87654321", "456 Avenue"));
        users.add(new Administrator("A001", "Charlie", "charlie@example.com", "password", "11223344", "789 Boulevard"));
        return users;
    }

    public static List<Product> importProducts(String filePath) {
        // Stub: In a full implementation, read the CSV file and create Product objects.
        // For demonstration, we return a hard-coded list.
        List<Product> products = new ArrayList<>();
        products.add(new Product("P001", "Keyboard", "Mechanical keyboard", 79.99, 50, "S001"));
        products.add(new Product("P002", "Mouse", "Wireless mouse", 39.99, 100, "S001"));
        return products;
    }
}

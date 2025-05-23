package model;

import util.CSVExporter;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Administrator extends User {

    public Administrator(String userID, String name, String email, String password, String contact, String address) {
        super(userID, name, email, password, "Administrator", contact, address, false);
    }

    @Override
    public void displayMenu() {
        System.out.println();
        System.out.println("Administrator Menu:");
        System.out.println("1. View Profile");
        System.out.println("2. Manage User Accounts");
        System.out.println("3. Manage Global Discounts / Campaigns");
        System.out.println("4. Generate Inventory and Order Insights");
        System.out.println("5. Logout");
    }

    public static void handleAdminMenu(Administrator admin, Scanner scanner, List<User> users, List<Product> products, List<Order> orders) {
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
            switch (choice) {
                case 1:
                    admin.viewProfile();
                    break;
                case 2:
                    System.out.println("Managing user accounts.");
                    admin.manageUsers(users, scanner);
                    break;
                case 3:
                    System.out.println("Managing global discounts/campaigns.");
                    admin.manageCampaigns(scanner, products);
                    break;
                case 4:
                    System.out.println("Generating inventory and order insights.");
                    admin.generatePlatformInsights(orders);
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
        System.out.println("Manage Users:");
        System.out.println("1. Add New User");
        System.out.println("2. Update User Information");
        System.out.println("3. Back");
        System.out.print("Enter choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> {
                registerNewUser(users, scanner);
            }
            case 2 -> {
                editUserInfo(users, scanner);
            }
        }
    }

    private void editUserInfo(List<User> users, Scanner scanner) {
        System.out.print("Enter the User ID to update: ");
        String userId = scanner.nextLine();

        System.out.println("Which field would you like to update?");
        System.out.println("1. User ID");
        System.out.println("2. Name");
        System.out.println("3. Email");
        System.out.println("4. Password");
        System.out.println("5. Contact");
        System.out.println("6. Address");
        System.out.print("Enter your choice: ");
        int field = Integer.parseInt(scanner.nextLine());
        String newValue;
        while (true) {
            System.out.print("Enter new value: ");
            newValue = scanner.nextLine();

            switch (field) {
                case 1:
                    if (!newValue.startsWith("C")&&!newValue.startsWith("S")&&!newValue.startsWith("A")) {
                        System.out.println("User ID must start with 'C', 'S', or 'A'.");
                        continue;
                    }
                    if (isValidAndUniqueUserID(newValue, users)) {
                        System.out.println("This User ID already exists.");
                        continue;
                    }

                    break;
                case 2:
                    if(!name.isEmpty()) {
                        break;
                    }else{System.out.println("Invalid input. Please try again.");continue;}
                case 6: // Address
                    if (newValue.isEmpty()) {
                        System.out.println("This field cannot be empty.");
                        continue;
                    }
                    break;

                case 3: // Email
                    if (!newValue.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                        System.out.println("Invalid email format.");
                        continue;
                    }
                    if (validEmail(newValue,users)) {
                        System.out.println("This email is already in use.");
                        continue;
                    }
                    break;
                case 4:
                    System.out.print("New password: ");
                    String newPassword = scanner.nextLine();
                    CSVExporter.updateUserPasswordByUID(userId, newPassword, "src/data/users.csv");
                    System.out.println("Password updated successfully.");
                    continue;
                case 5: // Contact
                    if (!newValue.matches("\\d{8}")) {
                        System.out.println("Contact number must be 8 digits.");
                        continue;
                    }
                    break;
                default:
                    System.out.println("Invalid field choice.");
                    return;
            }
            break;
        }
        CSVExporter.updateUserFieldByUID(userId, field - 1, newValue, "src/data/users.csv");
        for (User user : users) {
            if (user.getUserID().equals(userId)) {
                switch (field) {
                    case 1 -> user.setUserID(newValue);
                    case 2 -> user.setName(newValue);
                    case 3 -> user.setEmail(newValue);
                    case 5 -> user.setContact(newValue);
                    case 6 -> user.setAddress(newValue);
                }
                break; // Found and updated, exit loop
            }
        }
        System.out.println("User information updated successfully.");
    }


    public static void registerNewUser(List<User> users, Scanner scanner) {
        String name,email,role="",contact,address;
        while(true) {
            System.out.print("Enter Name: ");
            name = scanner.nextLine();
            if(!name.isEmpty()) {
                break;
            }else{System.out.println("Invalid input. Please try again.");}
        }
        while(true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine();
            if(!validEmail(email,users)) {
                System.out.println("Invalid Email, already exists.");
            }
            else {
                if (email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    break;}
                else {System.out.println("Invalid email format. Please try again.");}
            }
        }
        while (true) {
            System.out.print("Enter Role (Customer/Seller/C/S) non case sensative: ");
            role = scanner.nextLine().trim();
            if (role.equalsIgnoreCase("Customer")||role.equalsIgnoreCase("C")||role.equalsIgnoreCase("S")||role.equalsIgnoreCase("Seller")) {
                break;
            } else {
                System.out.println("Invalid role. Please enter 'Customer' or 'Seller'.");
            }
        }
        while(true) {
            System.out.print("Enter Contact No: ");
            contact = scanner.nextLine();
            if (contact.matches("\\d{8}")) {
                break;
            }
            System.out.println("Contact must be 8 digits.");
        }
        while(true) {
            System.out.print("Enter Address: ");
            address = scanner.nextLine();
            if(!address.isEmpty()) {break;}
            System.out.println("Invalid Address format. Please try again.");
        }
        String userIDPrefix = (role.equalsIgnoreCase("Customer")||role.equalsIgnoreCase("C"))?"C":"S";
        String userID = userIDPrefix + System.currentTimeMillis();
        String defaultPassword = generateRandomPassword();
        System.out.println("[Simulated Email] Your default password is: " + defaultPassword);

        User newUser;
        if (role.equalsIgnoreCase("Customer")||role.equalsIgnoreCase("C")) {
            newUser = new Customer(userID, name, email, defaultPassword, contact, address, true);
        } else if (role.equalsIgnoreCase("Seller")||role.equalsIgnoreCase("S")) {
            newUser = new Seller(userID, name, email, defaultPassword, contact, address, true);
        } else {
            System.out.println("Invalid role. Registration failed.");
            return;
        }

        users.add(newUser);
        CSVExporter.appendUserToCSV(newUser, "src/data/users.csv");
        System.out.println("Registration successful! Please log in using email and default password.");
    }
    public static boolean validEmail(String email,List<User> users) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                System.out.println("This email is already registered.");
                return false;
            }
        }
        return true;
    }

    public static String generateRandomPassword() {
        return "PW" + (int) (Math.random() * 10000);
    }

    private void viewProfile() {
        System.out.println("Administrator Profile:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
    }

    public static boolean isValidAndUniqueUserID(String userID, List<User> users) {
        // Check if it starts with S, C, or A
        if (!(userID.startsWith("S") || userID.startsWith("C") || userID.startsWith("A"))) {
            System.out.println("User ID must start with 'S', 'C', or 'A'.");
            return true;
        }

        // Check if it's unique
        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(userID)) {
                System.out.println("This User ID already exists.");
                return true;
            }
        }

        return false; // All good!
    }

    public void generatePlatformInsights(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("No orders available to generate insights.");
            return;
        }

        int totalOrders = orders.size();
        int airCount = 0, landCount = 0, seaCount = 0, localCount = 0;
        int shippingCount = 0, deliveredCount = 0, canceledCount = 0, otherCount = 0;

        System.out.println("˙⋆✮ Overall Inventory and Order Insights ✮⋆˙\n");
        System.out.println("Total Orders: " + totalOrders);

        for (Order order : orders) {
            String shipping = order.getShippingMethod().toLowerCase();
            if (shipping.contains("air")) airCount++;
            else if (shipping.contains("land")) landCount++;
            else if (shipping.contains("sea")) seaCount++;
            else if (shipping.contains("local")) localCount++;

            String status = order.getStatus().toLowerCase();
            if (status.contains("shipped")) shippingCount++;
            else if (status.contains("delivered")) deliveredCount++;
            else if (status.contains("cancel") || status.contains("sinkhole") || status.contains("drowned"))
                canceledCount++;
            else otherCount++;
        }

        System.out.println("\nShipping Methods:");
        System.out.printf("• Air: %d\n• Land: %d\n• Sea: %d\n• Local: %d\n", airCount, landCount, seaCount, localCount);

        System.out.println("\nOrder Status Breakdown:");
        System.out.printf("• In Shipping: %d\n• Delivered: %d\n• Canceled/Failed: %d\n• Other: %d\n", shippingCount, deliveredCount, canceledCount, otherCount);
    }

    public void manageCampaigns(Scanner scanner, List<Product> products) {
        System.out.println("\n--- Global Discount Campaigns ---");
        System.out.println("1. Apply discount to ALL products");
        System.out.println("2. Apply discount to a SPECIFIC product");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            applyGlobalDiscount(scanner, products);
        } else if (choice.equals("2")) {
            applySpecificDiscount(scanner, products);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    public void applyGlobalDiscount(Scanner scanner, List<Product> products) {
        System.out.println("\n--- ALL Products ---");
        double discountPercent;
        int days;
        while (true) {
            try {
                System.out.print("Enter discount percentage (e.g. 10 without percent sign): ");
                discountPercent = Double.parseDouble(scanner.nextLine());
                if (discountPercent > 0 && discountPercent < 100) {
                    System.out.println("Discount must be between 0 and 100.");
                    break;
                }
            } catch (Exception e) {
                System.out.println("You did not input an integer try again.");
            }
        }
        while (true) {
            try {
                System.out.print("Enter number of days (campaign duration): ");
                days = Integer.parseInt(scanner.nextLine());
                if (days >= 0) {break;}
                else{System.out.println("Invalid days.");}
            } catch (Exception e) {
                System.out.println("You did not input an integer try again.");
            }
        }

        System.out.println("Applying " + discountPercent + "% discount to all products...");
        LocalDate expiryDate = LocalDate.now().plusDays(days);
        for (Product p : products) {
            double originalPrice = p.getPrice();
            double discountedPrice = originalPrice * (1 - discountPercent / 100);
            p.setPrice(discountedPrice); // Apply new price
            p.setDiscountExpiry(expiryDate.toString());
            p.setDiscountPercentage(discountPercent);
            CSVExporter.updateProducts(p, "src/data/products.csv");
        }

        System.out.println("Discount applied successfully to all products!");
    }

    private void applySpecificDiscount(Scanner scanner, List<Product> products) {
        System.out.println("\n--- SPECIFIC Products ---");
        System.out.print("Enter the Product ID: ");
        String productId = scanner.nextLine();

        Product target = null;
        for (Product p : products) {
            if (p.getProductID().equals(productId)) {
                target = p;
                break;
            }
        }

        if (target == null) {
            System.out.println("Product not found.");
            return;
        }
        double discountPercent;
        int days;
        while (true) {
            try {
                System.out.print("Enter discount percentage (e.g. 10 without percent sign): ");
                discountPercent = Double.parseDouble(scanner.nextLine());
                if (discountPercent > 0 && discountPercent < 100) {
                    System.out.println("Discount must be between 0 and 100.");
                    break;
                }
            } catch (Exception e) {
                System.out.println("You did not input an integer try again.");
            }
        }
        while (true) {
            try {
                System.out.print("Enter number of days (campaign duration): ");
                days = Integer.parseInt(scanner.nextLine());
                if (days >= 0) {break;}
                else{System.out.println("Invalid days.");}
            } catch (Exception e) {
                System.out.println("You did not input an integer try again.");
            }
        }

        LocalDate expiryDate = LocalDate.now().plusDays(days);

        target.setDiscountPercentage(discountPercent);
        target.setDiscountExpiry(expiryDate.toString());
        target.setPrice(target.getPrice() * (1 - discountPercent / 100));
        CSVExporter.updateProducts(target, "src/data/products.csv");

        System.out.println("Discount applied to " + productId);
        System.out.println("Summary:");
        System.out.println("Discount: " + discountPercent + "%");
        System.out.println("Expiry: " + expiryDate);
    }
}

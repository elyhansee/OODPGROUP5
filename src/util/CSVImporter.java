package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.User;
import model.Customer;
import model.Order;
import model.Seller;
import model.Administrator;
import model.Product;

public class CSVImporter {
    public static List<User> importUsers(String filePath) {
        List<User> users = new ArrayList<>();

        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(filePath));
            
            while ((line = reader.readLine()) != null) {
                String[] readData = line.split(",");

                if (readData.length >= 7) { // 7 is the number of parameters needed
                    String userID = readData[0];
                    String name = readData[1];
                    String email = readData[2];
                    String password = readData[3];
                    String contact = readData[4];
                    String address = readData[5];
                    boolean firstLogin = readData[6].equals("1");

                    User userInfo = switch (userID.charAt(0)) {
                        case 'C' -> new Customer(userID, name, email, password, contact, address,firstLogin);
                        case 'S' -> new Seller(userID, name, email, password, contact, address,firstLogin);
                        case 'A' -> new Administrator(userID, name, email, password, contact, address);
                        default -> {
                            System.out.println("Invalid user type: " + userID.charAt(0));
                            yield null;
                        }
                    };
                    
                    if (userInfo != null) {
                        users.add(userInfo);
                    }
                }
            }
        }

        // Leaving it as general exception catching for now - Dehan 29/3
        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            try {
                reader.close();
            } 
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // users.add(new Customer("C001", "Alice", "alice@example.com", "password", "12345678", "123 Street"));
        // users.add(new Seller("S001", "Bob", "bob@example.com", "password", "87654321", "456 Avenue"));
        // users.add(new Administrator("A001", "Charlie", "charlie@example.com", "password", "11223344", "789 Boulevard"));
        return users;
    }

    public static List<Product> importProducts(String filePath) {
        List<Product> products = new ArrayList<>();

        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(filePath));
            
            while ((line = reader.readLine()) != null) {
                String[] readData = line.split(",");

                if (readData.length == 7) { // 6 is the number of parameters needed
                    String productID = readData[0];
                    String name = readData[1];
                    String description = readData[2];
                    double price = Double.parseDouble(readData[3]);
                    int stock = Integer.parseInt(readData[4]);
                    String sellerID = readData[5];
                    //boolean firstLogin = readData[6].equals("1");
                    String active = readData[6];//.equals("True");
                    double discount = Double.parseDouble(readData[7]);
                    String expiry = readData.length > 8 ? readData[8] : "";
                    Product newProduct = new Product(productID, name, description, price, stock, sellerID, active, discount, expiry);

                    if (newProduct != null) {
                        products.add(newProduct);
                    }
                }
            }
        }

        // Leaving it as general exception catching for now - Dehan 29/3
        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            try {
                reader.close();
            } 
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // products.add(new Product("P001", "Keyboard", "Mechanical keyboard", 79.99, 50, "S001"));
        // products.add(new Product("P002", "Mouse", "Wireless mouse", 39.99, 100, "S001"));
        return products;
    }

    // TO BE UPDATED 30/3 ===================================================================================================================
    public static List<Order> importOrders(String filePath) {
        List<Order> orders = new ArrayList<>();

        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(filePath));
            
            while ((line = reader.readLine()) != null) {
                String[] readData = line.split(",");

                //TODO: Update after esther done with checkout part
                if (readData.length == 9) { // 3 is the number of parameters needed
                    String customerID = readData[0];
                    String orderID = readData[1];
                    String productName = readData[2];
                    String shippingMethod = readData[3];
                    String shippingAddress = readData[4];
                    String status = readData[5];
                    String date = readData[6];
                    String sellerID = readData[7];
                    double cost = Double.parseDouble(readData[8]);

                    Order newOrder = new Order(customerID, orderID, productName, shippingMethod, shippingAddress, status, date, sellerID, cost);

                    if (newOrder != null) {
                        orders.add(newOrder);
                    }
                }
                //TODO: Probably add an ELSE statement if values are missing. Can do for the others too
            }
        }

        // Leaving it as general exception catching for now - Dehan 29/3
        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            try {
                reader.close();
            } 
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return orders;
    }

    // To get bundles
    public static List<String> importBundles(String filePath) { // PLACEHOLDER
        List<String> bundles = new ArrayList<>();

        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(filePath));
            
            while ((line = reader.readLine()) != null) {
                bundles.add(line);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } 
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return bundles;
    }
}

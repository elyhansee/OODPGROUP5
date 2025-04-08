package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
        catch (FileNotFoundException e) {
            System.out.println("The file " + filePath + " was not found.");
        }
        catch (IOException e) {
            System.out.println("Unable to load Users CSV.");
        }
        catch (Exception e) {
            System.out.println("An unexpected error has occurred.");
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } 
                catch (IOException e) {
                    System.out.println("Error closing reader.");
                }
            }
        }

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

                if (readData.length >= 11) {
                    String productID = readData[0];
                    String name = readData[1];
                    String description = readData[2];
                    double price = Double.parseDouble(readData[3]);
                    int stock = Integer.parseInt(readData[4]);
                    String sellerID = readData[5];
                    String active = readData[6];
                    double discount = Double.parseDouble(readData[7]);
                    String expiry = readData.length > 8 ? readData[8] : "";
                    double minPrice = Double.parseDouble(readData[9]);
                    double maxPrice = Double.parseDouble(readData[10]);
                    Product newProduct = new Product(productID, name, description, price, stock, sellerID, active, discount, expiry);
                    newProduct.setMinPrice(minPrice);
                    newProduct.setMaxPrice(maxPrice);
                    if (newProduct != null) {
                        products.add(newProduct);
                    }
                }
            }
        }

        catch (FileNotFoundException e) {
            System.out.println("The file " + filePath + " was not found.");
        }
        catch (IOException e) {
            System.out.println("Unable to load Products CSV.");
        }
        catch (Exception e) {
            System.out.println("An unexpected error has occurred.");
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } 
                catch (IOException e) {
                    System.out.println("Error closing reader.");
                }
            }
        }
        return products;
    }

    public static List<Order> importOrders(String filePath) {
        List<Order> orders = new ArrayList<>();

        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(filePath));
            
            while ((line = reader.readLine()) != null) {
                String[] readData = line.split(",");

                if (readData.length >= 9) {
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
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("The file " + filePath + " was not found.");
        }
        catch (IOException e) {
            System.out.println("Unable to load Orders CSV.");
        }
        catch (Exception e) {
            System.out.println("An unexpected error has occurred.");
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } 
                catch (IOException e) {
                    System.out.println("Error closing reader.");
                }
            }
        }

        return orders;
    }

    // To get bundles
    public static List<String> importBundles(String filePath) {
        List<String> bundles = new ArrayList<>();

        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(filePath));
            
            while ((line = reader.readLine()) != null) {
                bundles.add(line);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("The file " + filePath + " was not found.");
        }
        catch (IOException e) {
            System.out.println("Unable to load Bundles CSV.");
        }
        catch (Exception e) {
            System.out.println("An unexpected error has occurred.");
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } 
                catch (IOException e) {
                    System.out.println("Error closing reader.");
                }
            }
        }
        return bundles;
    }
}

package util;

import model.User;
import model.Product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVExporter {

    public static void appendUserToCSV(User user, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(user.getUserID()).append(",")
                    .append(user.getName()).append(",")
                    .append(user.getEmail()).append(",")
                    .append(user.getPassword()).append(",")
                    .append(user.getContact()).append(",")
                    .append(user.getAddress()).append(",")
                    .append(user.isFirstLogin() ? "1" : "0").append("\n");
        } catch (IOException e) {
            System.out.println("Error writing to users.csv: " + e.getMessage());
        }
    }
    public static void updateUserPasswordByUID(String userId, String newPassword, String filePath) {
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length >= 7 && data[0].equals(userId)) {
                    data[3] = newPassword;
                    data[6] = "0";
                    line = String.join(",", data);
                }

                lines.add(line);
            }
            reader.close();

            FileWriter writer = new FileWriter(filePath);
            for (String updatedLine : lines) {
                writer.write(updatedLine + "\n");
            }
            writer.close();

            System.out.println("Password updated for user ID: " + userId);
        } catch (IOException e) {
            System.out.println("Error updating password: " + e.getMessage());
        }
    }


    public static void updateUserFieldByUID(String userID, int fieldIndex, String newValue, String filePath) {
        if (fieldIndex == 3) {
            System.out.println("Use the updateUserPassword method for password updates.");
            return;
        }

        try {
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                // Match by user ID
                if (data.length >= 7 && data[0].equalsIgnoreCase(userID)) {
                    if (fieldIndex >= 0 && fieldIndex < data.length) {
                        data[fieldIndex] = newValue;

                        // If changing the role (e.g., Seller to Administrator), also update the userID prefix
                        if (fieldIndex == 0 && !newValue.equals(userID)) {
                            System.out.println("Note: You're replacing the entire User ID. Make sure it's intentional.");
                        } else if (fieldIndex == 6 && (newValue.equals("0") || newValue.equals("1"))) {
                            // ok to change login status
                        }

                        line = String.join(",", data);
                    }
                }

                lines.add(line); // Keep updated or original line
            }

            reader.close();

            // Write all lines back
            FileWriter writer = new FileWriter(filePath);
            for (String updatedLine : lines) {
                writer.write(updatedLine + "\n");
            }
            writer.close();

            System.out.println("User field updated successfully.");
        } catch (IOException e) {
            System.out.println("Error updating user field: " + e.getMessage());
        }
    }


    // General method to update Products CSV
    public static void updateProducts(Product product, String filePath) {
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                // Assumes productID is in the 1st column (index 0)
                if (data.length >= 9 && data[0].equals(product.getProductID())) {
                    data[0] = product.getProductID();
                    data[1] = product.getName();
                    data[2] = product.getDescription();
                    data[3] = String.valueOf(product.getPrice());
                    data[4] = String.valueOf(product.getStock());
                    data[5] = product.getSellerID();
                    data[6] = product.isActive();
                    data[7]=  String.valueOf(product.getDiscountPercentage());
                    data[8] =  product.getDiscountPercentage() == 0.0 ?"NULL": product.getDiscountExpiry();

                    line = String.join(",", data);
                }

                lines.add(line); // Keep updated or original line
            }
            reader.close();

            // Write all lines back to the same file
            FileWriter writer = new FileWriter(filePath);
            for (String updatedLine : lines) {
                writer.write(updatedLine + "\n");
            }
            writer.close();

            System.out.println("\nVisibility Updated Successfully for Product " + product.getProductID() + "\n");
        } 
        
        catch (IOException e) {
            System.out.println("Error updating visibility: " + e.getMessage());
        }
    }

        public static void insertProducts(Product product, String filePath) {
        try {
            String expiryStr = product.getDiscountExpiry().isEmpty() ? "NULL" : product.getDiscountExpiry();

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.append(product.getProductID()).append(",")
                    .append(product.getName()).append(",")
                    .append(product.getDescription()).append(",")
                    .append(String.valueOf(product.getPrice())).append(",")
                    .append(String.valueOf(product.getStock())).append(",")
                    .append(product.getSellerID()).append(",")
                    .append(product.isActive()).append(",")
                    .append(String.valueOf(product.getDiscountPercentage())).append(",")
                    .append(expiryStr).append("\n");

            writer.close();

            System.out.println("Product Successfully Added!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

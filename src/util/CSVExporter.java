package util;

import model.User;
import model.Customer;
import model.Product;
import model.Seller;
import model.Administrator;

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

    public static void updateUserPassword(String email, String newPassword, String filePath) {
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                // Assumes email is in the 3rd column (index 2)
                if (data.length >= 7 && data[2].equalsIgnoreCase(email)) {
                    // Replace the password (index 3)
                    data[3] = newPassword;
                    data[6] = "0";
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

            System.out.println("Password updated successfully for: " + email);
        } catch (IOException e) {
            System.out.println("Error updating password: " + e.getMessage());
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
                if (data.length >= 7 && data[0].equals(product.getProductID())) {
                    data[0] = product.getProductID();
                    data[1] = product.getName();
                    data[2] = product.getDescription();
                    data[3] = String.valueOf(product.getPrice());
                    data[4] = String.valueOf(product.getStock());
                    data[5] = product.getSellerID();
                    data[6] = product.isActive();

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
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.append(product.getProductID()).append(",")
                    .append(product.getName()).append(",")
                    .append(product.getDescription()).append(",")
                    .append(String.valueOf(product.getPrice())).append(",")
                    .append(String.valueOf(product.getStock())).append(",")
                    .append(product.getSellerID()).append(",")
                    .append(product.isActive()).append("\n");

            writer.close();

            System.out.println("Product Successfully Added!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

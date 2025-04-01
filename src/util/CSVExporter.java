package util;

import model.User;
import model.Customer;
import model.Seller;
import model.Administrator;

import java.io.BufferedReader;
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
                    .append(user.getRole()).append(",")
                    .append(user.getContact()).append(",")
                    .append(user.getAddress()).append("\n");
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
}

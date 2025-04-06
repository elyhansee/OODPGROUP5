package model;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);

    public static int selection(List<?> options) {
        int choice = -1;

        while (true) {
            // Display the options
            System.out.println("Options:");
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }

            System.out.print("Enter your choice: ");

            try {
                String input = scanner.nextLine();
                choice = Integer.parseInt(input);

                if (choice < 1 || choice > options.size()) {
                    System.out.println("Invalid option. Please enter a number between 1 and " + options.size() + ".");
                } else {
                    break; // Valid input
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return choice; // Return zero-based index
    }

    public static int numericInput(String caption) {
        System.out.print(caption);

        int choice;
        while (true) {
            try {
                String input = scanner.nextLine();
                choice = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return choice;
    }

    public static String textInput(String caption) {
        System.out.print(caption);

        String user_input;
        while (true) {
            try {
                user_input = scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please provide a valid entry.");
            }
        }
        return user_input;
    }

    // Used if the input doesnt matter
    public static void singleSelection() {
        System.out.println("Enter Any Button To Return");
        scanner.nextLine();
    }
}

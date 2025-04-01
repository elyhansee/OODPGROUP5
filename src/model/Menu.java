package model;

import java.util.List;
import java.util.Scanner;

public class Menu {
    public static int selection(List<String> options) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (true) {
            // Display the options
            System.out.println("Options:");
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }

            System.out.print("Enter the number of your choice: ");

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

//    public static int inputChoice(){
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//
//            try {
//                String input = scanner.nextLine();
//                choice = Integer.parseInt(input);
//
//                if (choice < 1 || choice > options.size()) {
//                    System.out.println("Invalid option. Please enter a number between 1 and " + options.size() + ".");
//                } else {
//                    break; // Valid input
//                }
//
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input. Please enter a valid integer.");
//            }
//        }
//        return choice;
//    }
}

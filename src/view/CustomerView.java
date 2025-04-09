package view;

import model.*;

import java.util.List;
import java.util.Scanner;

public class CustomerView {

    // public void displayMenu() {
    //     System.out.println();
    //     System.out.println("Customer Menu:");
    //     System.out.println("1. View Profile");
    //     System.out.println("2. Browse Products");
    //     System.out.println("3. Search Products");
    //     System.out.println("4. View Cart");
    //     System.out.println("5. Checkout");
    //     System.out.println("6. View Order Status");
    //     System.out.println("7. View Past Orders");
    //     System.out.println("8. Logout");
    // }

    public int getUserMenuChoice() {
        return Menu.numericInput("Enter your choice: ");
    }

    public void displayProfile(Customer customer) {
        System.out.println("\nProfile Information:");
        System.out.println("Name: " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Contact: " + customer.getContact());
        System.out.println("Address: " + customer.getAddress());
    }

    public int customerActions(List<String> options) {
        return Menu.selection(options);
    }

    public void displayOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            System.out.println("\nYour Orders:");
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }

    public void displayPastOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            System.out.println("Order History:");
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }


}

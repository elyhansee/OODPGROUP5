package view;

import model.Menu;
import model.Order;

import java.util.List;

public class OrderView {

    public void displayOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("You have no orders");
        } else {
            for (Order i : orders) {
                System.out.println(i.toString());
            }
        }
    }
}

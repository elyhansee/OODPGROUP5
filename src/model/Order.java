package model;

import java.util.List;

public class Order {
    private String orderID;
    private String customerID;
    private List<OrderItem> items;
    private String status; // e.g., Pending, Confirmed, Shipped, Delivered, Canceled
    private String shippingMethod;
    private String shippingAddress;

    public Order(String orderID, String customerID, List<OrderItem> items, String shippingMethod, String shippingAddress) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.items = items;
        this.status = "Pending";
        this.shippingMethod = shippingMethod;
        this.shippingAddress = shippingAddress;
    }

    // Getters and setters

    public String getOrderID() {
        return orderID;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("OrderID: %s | CustomerID: %s | Status: %s", orderID, customerID, status);
    }
}

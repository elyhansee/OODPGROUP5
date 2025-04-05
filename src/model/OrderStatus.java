package model;

public class OrderStatus {
    private String customerID;
    private String orderID;
    private String productName;
    private String shippingMethod;
    private String shippingAddress;
    private String status;

    public OrderStatus (String customerID, String orderID, String productName, String shippingMethod, String shippingAddress, String status) {
        this.customerID = customerID;        
        this.orderID = orderID;
        this.productName = productName;
        this.shippingMethod = shippingMethod;
        this.shippingAddress = shippingAddress;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("OrderID: %4s | Product: %-10s | Shipping Method: %-5s | Shipping To: %-15s | Status: %s", orderID, productName, shippingMethod, shippingAddress, status);
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public String getStatus() {
        return status;
    }

}

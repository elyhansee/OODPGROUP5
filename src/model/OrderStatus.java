package model;

public class OrderStatus {
    private String customerID;
    private String orderID;
    private String productName;
    private String shippingMethod;
    private String shippingAddress;
    private String status;
    private String sellerID;

    private String date;
    private String year;
    private String month;
    private String day;

    public OrderStatus (String customerID, String orderID, String productName, String shippingMethod, String shippingAddress, String status, String date, String sellerID) {
        this.customerID = customerID;        
        this.orderID = orderID;
        this.productName = productName;
        this.shippingMethod = shippingMethod;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.sellerID = sellerID;
        this.date = date;
        this.setYear();
        this.setMonth();
        this.setDay();
    }

    @Override
    public String toString() {
        return String.format("OrderID: %4s | Product: %-10s | Shipping Method: %-5s | Shipping To: %-15s | Status: %s", orderID, productName, shippingMethod, shippingAddress, status);
    }

    public String getCustomerID() {
        return customerID;
    }
    public String getSellerID() {
        return sellerID;
    }
    public String getProductName() {
        return productName;
    }

    public String getYear() {
        return year;
    }
    public void setYear() {
        this.year = this.date.substring(0, 4);
    }

    public String getMonth() {
        return month;
    }
    public void setMonth() {
        this.month = this.date.substring(5, 7);
    }
    
    public String getDay() {
        return day;
    }
    public void setDay() {
        this.day = this.date.substring(8, 10);
    }
}

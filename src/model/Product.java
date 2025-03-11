package model;

public class Product {
    private String productID;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String sellerID;
    private boolean active = true;

    // For dynamic pricing, you could add fields for min/max prices and an algorithm stub.
    private double minPrice;
    private double maxPrice;

    public Product(String productID, String name, String description, double price, int stock, String sellerID) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.sellerID = sellerID;
        // For simplicity, default min and max values are set equal to the base price.
        this.minPrice = price;
        this.maxPrice = price;
    }

    // Getters and setters
    public String getProductID() {
        return productID;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public int getStock() {
        return stock;
    }
    public String getSellerID() {
        return sellerID;
    }
    public boolean isActive() {
        return active;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void deactivate() {
        this.active = false;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Price: %.2f | Stock: %d | Description: %s",
                productID, name, price, stock, description);
    }
}

package model;

public class Product {
    private String productID;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String sellerID;
    private String active;

    // For dynamic pricing, you could add fields for min/max prices and an algorithm stub.
    private double minPrice;
    private double maxPrice;
    private int salesCount = 0;
    private int salesTarget = 10; // default target

    private double discountPercentage=0;
    private String discountExpiry;

    public Product(String productID, String name, String description, double price, int stock, String sellerID, String active,double discountPercentage,String discountExpiry) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price+salesCount;
        this.stock = stock;
        this.sellerID = sellerID;
        this.active = active;
        // For simplicity, default min and max values are set equal to the base price.
        this.minPrice = price;
        this.maxPrice = price;
        this.discountPercentage = discountPercentage;
        this.discountExpiry = discountExpiry;
    }
    public Product(String productID, String name, String description, double price, int stock, String sellerID, String active) {
        this(productID, name, description, price, stock, sellerID, active, 0.0, "NULL"); // Default discount: 0.0, no expiry
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
    public void setDescription(String description) {
        this.description = description;
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
    public String isActive() {
        return active;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void toggleVisibility() {
        if (this.active.equals("True"))
            this.active = "False";
        else
            this.active = "True";
    }
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double original() {
        double finalPrice = price;
        if (discountPercentage > 0.0) {
            finalPrice =( price /(100-discountPercentage))*100;
        }
        return finalPrice;
    }

    @Override
    public String toString() {
        if (discountPercentage > 0) {
            return String.format("ID: %s | Name: %s | Original Price: %.2f | Discounted Price: %.2f | Stock: %d | Description: %s",
                    productID, name, original(), price, stock, description);
        } else {
            return String.format("ID: %s | Name: %s | Price: %.2f | Stock: %d | Description: %s",
                    productID, name, price, stock, description);
        }
    }

    public String toStringSeller() {
        return String.format("ID: %s | Name: %s | Price: %.2f | Stock: %d | Description: %s | Visible: %s",
                productID, name, price, stock, description, active);
    }

    public void incrementSales() { salesCount++; }

    public void applyDynamicPricing() {
        if (salesCount < salesTarget) {
            price = Math.max(minPrice, price - 1);// Encourage sales
            if(price>maxPrice)
                price = maxPrice;
        } else if (salesCount >= salesTarget) {
            price = Math.min(maxPrice, price + 1); // Ride the trend
            if(price<minPrice)
                price = minPrice;
        }
    }

    public double getDiscountPercentage() { return discountPercentage; }
    public String getDiscountExpiry() { return discountExpiry; }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setDiscountExpiry(String discountExpiry) {
        this.discountExpiry = discountExpiry;
    }
}

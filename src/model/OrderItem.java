package model;

public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("Product: %s | Quantity: %d", product.getName(), quantity);
    }

    public String orderSummary(){
        double totalPrice = product.getPrice() * quantity;
        return String.format("Product: %s %n Quantity: %d %n Total: %.2f ", product.getName(), quantity, totalPrice);
    }
}

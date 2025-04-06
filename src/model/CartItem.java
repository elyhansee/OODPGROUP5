package model;

public class CartItem extends OrderItem {
    private String CartItemID;

    public CartItem(String cartItemID, Product product, int quantity) {
        super(product, quantity);
        this.CartItemID = cartItemID;
    }

    @Override
    public String toString() {
        return String.format("Cart Item ID: %s | Product: %s | Quantity: %d", this.getCartItemID(), super.getProduct().getName(), super.getQuantity());
    }

    public String getCartItemID() {
        return CartItemID;
    }

    public void setCartItemID(String cartItemID) {
        CartItemID = cartItemID;
    }
}

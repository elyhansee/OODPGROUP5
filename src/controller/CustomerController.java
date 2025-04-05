package controller;

import model.Customer;
import model.Menu;
import model.Product;
import view.CustomerView;

import java.util.List;
import java.util.Optional;

public class CustomerController {
    private final Customer customer;
    private final ProductController productController;
    private final OrderController orderController;
    private final CartController cartController;
    private final CustomerView view;

    public CustomerController(Customer customer, ProductController productController, OrderController orderController, CartController cartController, CustomerView view) {
        this.customer = customer;
        this.productController = productController;
        this.orderController = orderController;
        this.cartController = cartController;
        this.view = view;
    }

    public void run() {
        int choice = -1;
        while (choice != 8) {
            view.displayMenu();
            choice = view.getUserMenuChoice();
            switch (choice) {
                case 1 -> view.displayProfile(customer);
                case 2 -> {
                    List<Product> products = productController.getActiveProducts();
                    view.displayProducts(products);
                    List<String> options = List.of("Add to cart", "Back");
                    int option = Menu.selection(options);
                    switch (option) {
                        case 1 -> handleAddToCart();
                        case 2 -> {
                        }
                        default -> System.out.println("Invalid Input. Try again.");
                    }
                }
                case 3 -> {
                    String keyword = view.promptSearchKeyword();
                    List<Product> results = productController.searchProducts(keyword);
                    view.displayProducts(results);
                }
                case 4 -> {
                    cartController.getCartItems();
                }
                case 5 -> cartController.checkout(customer, orderController);
                case 6 -> {
//                    List<Order> orders = orderController.getCustomerOrders(customer.getUserID());
//                    view.displayOrders(orders);
                }
                case 7 -> view.displayPastOrders(); // Placeholder
                case 8 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid input. Try again.");
            }
        }
    }

    public void handleAddToCart() {
        String prod_ID = Menu.textInput("Enter Product ID (Case Sensitive). Type 'cancel' to cancel");

        if (prod_ID.equalsIgnoreCase("cancel")) {
            System.out.println("Cancelled");
            return;
        }

        Optional<Product> addProduct = productController.getProductById(prod_ID);
        if (addProduct.isEmpty()) {
            System.out.println("Product not found");
            return;
        }

        Product product = addProduct.get();
        while (true) {
            int prod_qty = Menu.numericInput("Enter product quantity. Type 0 to cancel");
            if (prod_qty == 0) return;

            if (prod_qty < 0 || prod_qty > product.getStock()) {
                System.out.println("Invalid Stock Amount");
            } else {
                System.out.println("Add to Cart:");
                System.out.println("ID: " + product.getProductID());
                System.out.println("Name: " + product.getName());
                System.out.println("Qty: " + prod_qty);
                System.out.println("--------------------");
                System.out.printf("Total Price:$%.2f %n", (prod_qty * product.getPrice()));

                String confirmation = Menu.textInput("Add to Cart? y/n");
                if (confirmation.equalsIgnoreCase("y")) {
                    cartController.addItem(product, prod_qty);
                    System.out.println("ADDED TO CART");
                    break;
                } else if (confirmation.equalsIgnoreCase("n")) {
                    System.out.println("Purchase Cancelled");
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
        }
    }


}

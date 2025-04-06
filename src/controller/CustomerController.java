package controller;

import model.Customer;
import model.Order;
import view.CartView;
import view.CustomerView;

import java.util.List;

public class CustomerController {
    private final Customer customer;
    private final ProductController productController;
    private final OrderController orderController;
    private final CartController cartController;
    private final CustomerView view;

    public CustomerController(Customer customer, ProductController productController, OrderController orderController, CustomerView view) {
        this.customer = customer;
        this.productController = productController;
        this.orderController = orderController;
        this.view = view;
        CartView cartView = new CartView();
        this.cartController = new CartController(customer.cartItems, cartView);
    }

    public void run() {
        int choice = -1;
        while (choice != 8) {
            view.displayMenu();
            choice = view.getUserMenuChoice();
            switch (choice) {
                case 1 -> view.displayProfile(customer);
                case 2 -> showProducts();
                case 3 -> productController.productSearch();
                case 4 -> cartController.displayCart();
                case 5 -> cartController.checkout(customer, orderController);
                case 6 -> showOrders();
                case 7 -> showPastOrders();
                case 8 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid input. Try again.");
            }
        }
    }

    private void showProducts() {
        productController.listProducts(productController.getActiveProducts());
        handleCustomerActions();
    }

    private void handleCustomerActions() {
        List<String> options = List.of("Add to cart", "Back");
        int action = view.customerActions(options);
        switch (action) {
            case 1 -> cartController.addToCart(productController);
            case 2 -> {
            }
            default -> System.out.println("Invalid Input. Try again.");
        }
    }

    private void showOrders() {
        List<Order> customerOrders = orderController.getCustomerOrders(customer.getUserID())
                .stream().filter(o -> !o.getStatus().equals("Delivered")).toList();
        view.displayOrders(customerOrders);
    }

    private void showPastOrders() {
        List<Order> pastOrders = orderController.getCustomerOrders(customer.getUserID())
                .stream().filter(o -> o.getStatus().equals("Delivered")).toList();
        view.displayPastOrders(pastOrders);
    }

}

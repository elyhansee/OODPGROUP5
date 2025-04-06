package controller;

import model.Customer;
import view.CustomerView;

import java.util.List;

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
                case 2 -> showProducts();
                case 3 -> productController.productSearch();
                case 4 -> cartController.displayCart();
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

    private void showProducts(){
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

//    private void viewOrderStatus(List<Order> order) {
//        if (order.isEmpty()) {
//            System.out.println("You have no pending orders!");
//            return;
//        } else {
//            System.out.println("\n== Recent Orders ==");
//            for (Order o : order) {
//                if (o.getCustomerID().equals(userID)) {
//                    System.out.println(o.toString());
//                }
//            }
//            exitMenu();
//        }
//    }
//
//    private void viewPastOrders(List<Order> order) {
//        if (order.isEmpty()) {
//            System.out.println("You have not ordered anything before.");
//            return;
//        } else {
//            System.out.println("\n== Past Orders ==");
//            for (Order o : order) {
//                if (o.getCustomerID().equals(userID) && o.getStatus().equals("Delivered")) {
//                    System.out.println(o.toStringPast());
//                }
//            }
//            exitMenu();
//        }
//    }


}

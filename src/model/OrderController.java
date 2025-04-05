package model;

import java.util.List;
import java.util.stream.Collectors;

public class OrderController {

    // Sorts orders based on the seller's ID
    public List<Order> sortOrders(Seller seller, List<Order> order) {
        List<Order> sortedOrders = order.stream()
                                            .filter(orders -> (orders.getSellerID().equals(seller.getUserID())))
                                            .collect(Collectors.toList());

        return sortedOrders;
    }

    // Maybe theres a way to make it generic
    // Sorts orders based on the customer's ID
    public List<Order> sortOrdersCustomer(Customer customer, List<Order> order) {
        List<Order> sortedOrders = order.stream()
                                            .filter(orders -> (orders.getCustomerID().equals(customer.getUserID())))
                                            .collect(Collectors.toList());

        return sortedOrders;
    }

    // Sorts order by date restrictions
    public List<Order> sortOrderByDate(List<Order> orders, String startDate, String endDate) {
        try {
            int startYear = Integer.parseInt(startDate.substring(0, 4));
            int startMonth = Integer.parseInt(startDate.substring(5, 7));
            int startDay = Integer.parseInt(startDate.substring(8, 10));

            int endYear = Integer.parseInt(endDate.substring(0, 4));
            int endMonth = Integer.parseInt(endDate.substring(5, 7));
            int endDay = Integer.parseInt(endDate.substring(8, 10));
            
            List<Order> sortedOrders = orders.stream()
                                                .filter(order -> {
                                                    int orderYear = Integer.parseInt(order.getYear());
                                                    int orderMonth = Integer.parseInt(order.getMonth());
                                                    int orderDay = Integer.parseInt(order.getDay());

                                                    boolean isAfter = ((orderYear > startYear || (orderYear == startYear && orderMonth > startMonth)
                                                                        || (orderYear == startYear && orderMonth == startMonth && orderDay >= startDay)));
                                                                        
                                                    boolean isBefore = ((orderYear < endYear || (orderYear == endYear && orderMonth < endMonth)
                                                                        || (orderYear == endYear && orderMonth == endMonth && orderDay <= endDay))); 
                                                    
                                                    return isAfter && isBefore;
                                                })
                                                .collect(Collectors.toList());

            return sortedOrders;
        }
        catch (NumberFormatException e) {
            System.out.println("Error converting date.");
            return List.of();
        }
        catch (Exception e) {
            System.out.println("An unexpected error has occurred.");
            return List.of();
        }
    }
}

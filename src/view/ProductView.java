package view;

import model.Menu;
import model.Product;

import java.util.List;

public class ProductView {
    public void displayProducts(List<Product> products) {
        System.out.println("Products:");
        for (Product p : products) {
            System.out.println(p);
        }
        System.out.println("---------------------------");
    }

    public String productSearch() {
        return Menu.textInput("Enter product keyword:\n");
    }
}

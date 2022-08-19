package tatsuya4lc.inventorysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tatsuya4lc.inventorysystem.models.InHouse;
import tatsuya4lc.inventorysystem.models.Inventory;
import tatsuya4lc.inventorysystem.models.Outsourced;
import tatsuya4lc.inventorysystem.models.Product;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 627, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Hello");

        InHouse part1 = new InHouse(1, "Pedal", 123.45, 10, 1, 15, 1);
        Outsourced part2 = new Outsourced(2, "Handle", 234.56, 9, 2, 16, "Test");
        InHouse part3 = new InHouse(3, "Seat", 345.67, 8, 3, 17, 3);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

        Product prod1 = new Product(1, "Bicycle", 123.45, 10, 1, 15);
        Product prod2 = new Product(2, "Motorcycle", 234.56, 9, 2, 16);
        Product prod3 = new Product(3, "Bike", 345.67, 8, 3, 17);

        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
        Inventory.addProduct(prod3);

        launch();
    }
}
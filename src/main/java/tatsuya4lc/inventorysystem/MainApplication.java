package tatsuya4lc.inventorysystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import tatsuya4lc.inventorysystem.controllers.MainController;
import tatsuya4lc.inventorysystem.controllers.PartController;
import tatsuya4lc.inventorysystem.controllers.ProductController;
import tatsuya4lc.inventorysystem.models.*;

import java.io.IOException;
import java.net.URL;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 627, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    public static void changeMenu(ActionEvent event, int x, int y, TableView tableView) {
        URL url = null;
        switch (x) {
            case 1 -> url = MainApplication.class.getResource("MainView.fxml");
            case 2 -> url = MainApplication.class.getResource("PartView.fxml");
            case 3 -> url = MainApplication.class.getResource("ProductView.fxml");
        }

        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader mainLoader = new FXMLLoader(url);
            Scene scene = new Scene(mainLoader.load());
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();

            switch (y) {
                case 1 -> {
                    PartController PaC = mainLoader.getController();
                    PaC.isModifyingPart(tableView.getSelectionModel().getSelectedIndex(), (Part) tableView.getSelectionModel().getSelectedItem());
                }
                case 2 -> {
                    ProductController PrC = mainLoader.getController();
                    PrC.isModifyingProduct(tableView.getSelectionModel().getSelectedIndex(), (Product) tableView.getSelectionModel().getSelectedItem());
                    PrC.newProduct();
                }
                case 3 -> {
                    ProductController PrC = mainLoader.getController();
                    PrC.newProduct();
                }
                case 4 -> {
                    MainController MaC = mainLoader.getController();
                    MaC.selectTabPart();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
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

        prod1.addAssociatedPart(part1);
        prod1.addAssociatedPart(part2);
        prod1.addAssociatedPart(part3);

        launch();
    }
}
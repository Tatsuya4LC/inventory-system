package tatsuya4lc.inventorysystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import tatsuya4lc.inventorysystem.controllers.MainController;
import tatsuya4lc.inventorysystem.controllers.PartController;
import tatsuya4lc.inventorysystem.controllers.ProductController;
import tatsuya4lc.inventorysystem.models.*;

import java.io.IOException;
import java.net.URL;

/**
 * This is the MainApplication class that creates the inventory management system application.
 * Application improvement suggestions:
 *      Context Menu: ability to interact with the fields in the TableView
 *      Dialog that informs Part is already associated
 *      Dialog that confirms to proceed with deletion and remove associated Part/s
 *      Search function needs to accommodate for number in name and show both matched id and name
 *
 * @author Tristan Lozano
 */
public class MainApplication extends Application {

    /**
     * This is an override method.
     * This override method prepares the first screen.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 636, 405);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the changeMenu method.
     * This method changes scene when called.
     *
     * @param event ActionEvent parameter used get the window for the stage
     * @param x leveraged to pick a fxml file for FXMLLoader to set scene
     * @param y leveraged to allow calling methods from another controller
     * @param table1 TableView parameter for modifying the Part Table
     * @param table2 TableView parameter for modifying the Product Table
     */
    public static void changeMenu(ActionEvent event, int x, int y, TableView<Part> table1, TableView<Product> table2) {
        URL url = null;
        //switch-case for choosing fxml file for FXMLLoader when changing scene
        switch (x) {
            case 1 -> url = MainApplication.class.getResource("MainView.fxml");
            case 2 -> url = MainApplication.class.getResource("PartView.fxml");
            case 3 -> url = MainApplication.class.getResource("ProductView.fxml");
        }

        //try-catch block that test for IOException throws runtime error when load() execute
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader mainLoader = new FXMLLoader(url);
            Scene scene = new Scene(mainLoader.load());
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();

            //switch case for optional behaviour when changing scene
            switch (y) {
                //when modifying part, accesses PartController and sends the selected item from the Part TableView
                case 1 -> {
                    PartController PaC = mainLoader.getController();
                    PaC.isModifyingPart(table1.getSelectionModel().getSelectedIndex(), table1.getSelectionModel().getSelectedItem());
                }
                //when modifying product, accesses ProductController and sends the selected item from the Product TableView
                //and initializes Part and Associated Part TableView columns in the adding product window
                case 2 -> {
                    ProductController PrC = mainLoader.getController();
                    PrC.isModifyingProduct(table2.getSelectionModel().getSelectedIndex(), table2.getSelectionModel().getSelectedItem());
                    PrC.addProductColumns();
                }
                //when adding product, initializes the Part TableView columns in the adding product window
                case 3 -> {
                    ProductController PrC = mainLoader.getController();
                    PrC.addProductColumns();
                }
                //accesses the MainController
                //when changing scene from adding/modifying part
                //selects the Part Tab in the tabPane
                case 4 -> {
                    MainController MaC = mainLoader.getController();
                    SingleSelectionModel<Tab> selectionModel = MaC.tabPane.getSelectionModel();
                    selectionModel.select(MaC.selectedParts);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method to generate ID for Product or Part.
     * checks if i already exist
     *
     * @param x leverages to generate either for Part or Product
     * @return an Integer i
     */
    public static int generateID(int x) {
        int i = 1;

        switch (x) {
            case 0 -> {
                for(Part part : Inventory.getAllParts()) {
                    if(part.getId() == i) {
                        i++;
                    }
                }
            }
            case 1 -> {
                for(Product product : Inventory.getAllProducts()) {
                    if(product.getId() == i) {
                        i++;
                    }
                }
            }
        }

        return i;
    }

    /**
     * This is the main method.
     * This is the first method that gets called when the application runs
     */
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

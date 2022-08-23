package tatsuya4lc.inventorysystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tatsuya4lc.inventorysystem.controllers.MainController;
import tatsuya4lc.inventorysystem.controllers.PartController;
import tatsuya4lc.inventorysystem.controllers.ProductController;
import tatsuya4lc.inventorysystem.models.*;

import java.io.IOException;
import java.net.URL;

/**
 * This is the MainApplication class that creates the inventory management system application.
 * <p>
 * FUTURE ENHANCEMENT
 * <p>
 *     1) Context Menu: enable user to interact with the fields in the TableView.
 *     <br>
 *     i.e. rename, copy, paste, execute edit or delete with a mouse
 * <p>
 *     2) Dialog that informs user part object is already associated.
 * <p>
 *     3) Dialog that confirms to proceed with the deletion even though associated parts exist.
 *     <br>
 *     This deletion would also remove the associated part from the product.
 * <p>
 *     4) Search function needs to accommodate for number in name and show both matched id and matched partial name.
 *     <br>
 *     This is to prevent a logical error when product/part name consist of a number that is also the same value as the ID.
 * <p>
 *     5) Ability to select multiple columns, instead of one at a time.
 * <p>
 * RUNTIME ERROR
 * <p>
 *     searchPart(), searchProduct
 * <p>
 * LOGICAL ERROR
 * <p>
 *     logicCheck()
 * <p>
 *     check comment above the method
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
     * checks if ID already in use
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
     * method for search button in the Part tabPane.
     * calls a method that searches for matching integer
     * or calls a method to search for the matching String
     * <p>
     * RUNTIME ERROR:
     * an error occurs because the invocation target is not a number format.
     * when user inputs anything that's not in a number format parseInt() method cannot execute
     * to prevent this the code is placed inside a try-catch block
     * when a user input anything but number format, it is thrown inside the catch block
     *
     * @param tableView the TableView to set
     * @param textField the TextField to set
     */
    public static void searchPart(TableView<Part> tableView, TextField textField) {
        tableView.setItems(Inventory.getAllParts());

        try {
            //parses searchBarParts from String to Integer
            int i = Integer.parseInt(textField.getText());
            ObservableList<Part> found = FXCollections.observableArrayList();

            if (Inventory.lookupPart(i) != null) {
                tableView.getSelectionModel().select(Inventory.lookupPart(i));
            } else if (Inventory.lookupPart(i) == null) {
                tableView.setItems(found);
            }
        } catch (NumberFormatException e) {
            tableView.getSelectionModel().clearSelection();
            tableView.setItems(Inventory.lookupPart(textField.getText()));
        }

        textField.clear();
    }

    /**
     * method for search button in the Product tabPane.
     * calls a method that searches for matching integer
     * or calls a method to search for the matching String
     * <p>
     * RUNTIME ERROR:
     * an error occurs because the invocation target is not a number format.
     * when user inputs anything that's not in a number format parseInt() method cannot execute
     * to prevent this the code is placed inside a try-catch block
     * when a user input anything but number format, it is thrown inside the catch block
     *
     * @param tableView the TableView to set
     * @param textField the TextField to set
     */
    public static void searchProduct(TableView<Product> tableView, TextField textField) {
        tableView.setItems(Inventory.getAllProducts());

        try {
            //parses searchBarProducts from String to Integer
            int i = Integer.parseInt(textField.getText());
            ObservableList<Product> found = FXCollections.observableArrayList();

            if (Inventory.lookupProduct(i) != null) {
                tableView.getSelectionModel().select(Inventory.lookupProduct(i));
            } else if (Inventory.lookupProduct(i) == null) {
                tableView.setItems(found);
            }
        } catch (NumberFormatException e) {
            tableView.getSelectionModel().clearSelection();
            tableView.setItems(Inventory.lookupProduct(textField.getText()));
        }

        textField.clear();
    }

    /**
     * method to check simple logical errors.
     * <p>
     * LOGICAL ERROR:
     * Stock cannot be outside the Minimum and Maximum range.
     * Minimum cannot be greater than Maximum
     * to prevent this there are 2 if statements
     * first statement checks if Minimum is greater than Maximum
     * second statement checks if Stock is less than Minimum or Stock is greater than Maximum
     * when the checks are true user is presented with an error dialog
     *
     * @param min the min to set
     * @param max the max to set
     * @param stock the stock to set
     * @return boolean
     */
    public static boolean logicCheck(int min, int max, int stock) {
        if (min > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Logical Error");
            alert.setHeaderText(null);
            alert.setContentText("Minimum cannot be greater than Maximum \nMinimum > Maximum");
            alert.showAndWait();

            return false;
        }

        if (stock < min || stock > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Logical Error");
            alert.setHeaderText(null);

            if (stock < min) {
                alert.setContentText("Stock is out of range \nStock < Minimum");
            } else {
                alert.setContentText("Stock is out of range \nStock > Maximum");
            }

            alert.showAndWait();
            return false;
        }

        return true;
    }

    /**
     * This is the main method.
     * This is the first method that gets called when the application runs
     */
    public static void main(String[] args) {
        launch();
    }
}

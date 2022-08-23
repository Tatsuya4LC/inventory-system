package tatsuya4lc.inventorysystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import tatsuya4lc.inventorysystem.MainApplication;
import tatsuya4lc.inventorysystem.models.Inventory;
import tatsuya4lc.inventorysystem.models.Part;
import tatsuya4lc.inventorysystem.models.Product;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller for the main window.
 * This class provides control logic for MainView.fxml
 * <p>
 *     RUNTIME ERROR
 *     <br>
 *     onModifyPart(), onModifyProduct(), onDeletePart(), onDeleteProduct()
 * <p>
 *     check comment inside the code, above the mentioned methods
 *
 * @author Tristan Lozano
 */
public class MainController implements Initializable {

    @FXML
    private TableColumn<Part, Integer> columnPartID;

    @FXML
    private TableColumn<Part, Integer> columnPartMax;

    @FXML
    private TableColumn<Part, Integer> columnPartMin;

    @FXML
    private TableColumn<Part, String> columnPartName;

    @FXML
    private TableColumn<Part, Double> columnPartPrice;

    @FXML
    private TableColumn<Part, Integer> columnPartStock;

    @FXML
    private TableColumn<Product, Integer> columnProductID;

    @FXML
    private TableColumn<Product, Integer> columnProductMax;

    @FXML
    private TableColumn<Product, Integer> columnProductMin;

    @FXML
    private TableColumn<Product, String> columnProductName;

    @FXML
    private TableColumn<Product, Double> columnProductPrice;

    @FXML
    private TableColumn<Product, Integer> columnProductStock;

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TextField searchBarParts;

    @FXML
    private TextField searchBarProducts;

    @FXML
    public Tab selectedParts;

    @FXML
    private Tab selectedProducts;

    @FXML
    public TabPane tabPane;

    /**
     * method for add button in the Part tabPane.
     * calls for a method from the MainApplication to change scene
     * and opens the menu for adding Part
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onAddPart(ActionEvent event) {
        MainApplication.changeMenu(event, 2, 0, null, null);
    }

    /**
     * method for add button in the Product tabPane.
     * calls for a method from the MainApplication to change scene
     * and opens the menu for adding Product
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onAddProduct(ActionEvent event) {
        MainApplication.changeMenu(event, 3, 3, null, null);
    }

    /**
     * exits the application from Part tabPane.
     */
    @FXML
    void onExitPart() {
        System.exit(0);
    }

    /**
     * exits the application form the Product tabPane.
     */
    @FXML
    void onExitProduct() {
        System.exit(0);
    }

    /**
     * method for modify button in the Part tabPane.
     * checks if an item is selected to prevent runtime error
     * then calls for a method from MainApplication to change scene
     * and opens the menu for modifying Part
     * <p>
     * RUNTIME ERROR:
     * an error occurs because the invocation target is a null pointer.
     * when a user activates the modify button without selecting an item the error occurs
     * to prevent this error, there is an if statement checking that the user selected an item in the TableView
     * and the selection is not null
     * if not, no code is run
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onModifyPart(ActionEvent event) {
        if (!partTable.getSelectionModel().isEmpty()) {
            MainApplication.changeMenu(event, 2, 1, partTable, null);
            partTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * method for modify button in the Product tabPane.
     * checks if an item is selected to prevent runtime error
     * then calls for a method from the MainApplication to change scene
     * and opens the menu for modifying Product
     * <p>
     * RUNTIME ERROR:
     * an error occurs because the invocation target is a null pointer.
     * when a user activates the modify button without selecting an item the error occurs
     * to prevent this error, there is an if statement checking that the user selected an item in the TableView
     * and the selection is not null
     * if not, no code is run
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onModifyProduct(ActionEvent event) {
        if (!productTable.getSelectionModel().isEmpty()) {
            MainApplication.changeMenu(event, 3, 2, null, productTable);
            productTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * method for delete button in the Part tabPane.
     * checks if an item is selected to prevent runtime error
     * checks if Part is associated to a Product
     * gives an error dialog and informs that Part is associated
     * gives a confirmation dialog and confirms before deletion if not associated
     * <p>
     * RUNTIME ERROR:
     * an error occurs because the invocation target is a null pointer.
     * when a user activates the modify button without selecting an item the error occurs
     * to prevent this error, there is an if statement checking that the user selected an item in the TableView
     * and the selection is not null
     * if not, no code is run
     */
    @FXML
    void onDeletePart() {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        boolean exist = false;

        //for loop checks for association
        if (selectedPart != null) {
            check:
            for (Product product : Inventory.getAllProducts()) {
                for (Part part : product.getAllAssociatedParts()) {
                    if (part == selectedPart) {
                        exist = true;
                        break check;
                    }
                }
            }

            if (exist) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Conflict");
                alert.setHeaderText(null);
                alert.setContentText("This Part is associated with a Product \ncannot be deleted");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("This will delete the Part \nWould you like to proceed?");
                Optional<ButtonType> result = alert.showAndWait();

                //checks for confirmation before deleting
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deletePart(selectedPart);
                    partTable.getSelectionModel().select(null);
                }
            }
            partTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * method for delete button in the Product tabPane.
     * checks if an item is selected to prevent runtime error
     * checks if Product has associated Part/s
     * gives an error dialog and informs Product has associated Part/s
     * gives a confirmation dialog and confirms deletion if no associated Part/s
     * <p>
     * RUNTIME ERROR:
     * an error occurs because the invocation target is a null pointer.
     * when a user activates the modify button without selecting an item the error occurs
     * to prevent this error, there is an if statement checking that the user selected an item in the TableView
     * and the selection is not null
     * if not, no code is run
     */
    @FXML
    void onDeleteProduct() {
        if (!productTable.getSelectionModel().isEmpty()) {
            //checks if Product has associated Part/s
            if (!productTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Conflict");
                alert.setHeaderText(null);
                alert.setContentText("Product cannot be deleted because of existing associated Part/s");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("This will delete the Product \nWould you like to proceed?");
                Optional<ButtonType> result = alert.showAndWait();

                //checks for confirmation before deleting
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
                    productTable.getSelectionModel().select(null);
                }
            }
            productTable.getSelectionModel().clearSelection();
        }

    }

    /**
     * calls a method from the MainApplication to do the search
     */
    @FXML
    void onSearchPart() {
        MainApplication.searchPart(partTable, searchBarParts);
    }

    /**
     * calls a method from the MainApplication to do the search
     */
    @FXML
    void onSearchProduct() {
        MainApplication.searchProduct(productTable, searchBarProducts);
    }

    /**
     * calls a method from the MainApplication to do the search
     *
     * @param event the event that button was pressed
     */
    @FXML
    void onEnterPart(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            MainApplication.searchPart(partTable, searchBarParts);
        }
    }

    /**
     * calls a method from the MainApplication to do the search
     *
     * @param event the event that button was pressed
     */
    @FXML
    void onEnterProduct(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            MainApplication.searchProduct(productTable, searchBarProducts);
        }
    }

    /**
     * method for initializing the Product/Part TableView and Product/Part TableColumn.
     */
    public void setMainView() {
        columnProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnProductStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnProductMin.setCellValueFactory(new PropertyValueFactory<>("min"));
        columnProductMax.setCellValueFactory(new PropertyValueFactory<>("max"));

        productTable.setItems(Inventory.getAllProducts());

        columnPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnPartMin.setCellValueFactory(new PropertyValueFactory<>("min"));
        columnPartMax.setCellValueFactory(new PropertyValueFactory<>("max"));

        partTable.setItems(Inventory.getAllParts());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMainView();
    }
}

package tatsuya4lc.inventorysystem.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 *     onModifyPart(), onModifyProduct(), onDeletePart(), onDeleteProduct(), onSearchPart(), onSearchProducts(),
 *     onEnterPart(), onEnterProduct(),
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
     * RUNTIME ERROR
     * an error occurs because the invocation target is a null pointer
     * when a user activates the modify button without selecting an item the error occurs
     * to prevent this error, there is an if statement checking that the user selected an item in the TableView
     * and the selection is not null
     * if not, no code is run
     * <p>
     * method for modify button in the Part tabPane.
     * checks if an item is selected to prevent runtime error
     * then calls for a method from MainApplication to change scene
     * and opens the menu for modifying Part
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
     * RUNTIME ERROR
     * an error occurs because the invocation target is a null pointer
     * when a user activates the modify button without selecting an item the error occurs
     * to prevent this error, there is an if statement checking that the user selected an item in the TableView
     * and the selection is not null
     * if not, no code is run
     * <p>
     * method for modify button in the Product tabPane.
     * checks if an item is selected to prevent runtime error
     * then calls for a method from the MainApplication to change scene
     * and opens the menu for modifying Product
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
     * RUNTIME ERROR
     * an error occurs because the invocation target is a null pointer
     * when a user activates the modify button without selecting an item the error occurs
     * to prevent this error, there is an if statement checking that the user selected an item in the TableView
     * and the selection is not null
     * if not, no code is run
     * <p>
     * method for delete button in the Part tabPane.
     * checks if an item is selected to prevent runtime error
     * checks if Part is associated to a Product
     * gives an error dialog and informs that Part is associated
     * gives a confirmation dialog and confirms before deletion if not associated
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
     * RUNTIME ERROR
     * an error occurs because the invocation target is a null pointer
     * when a user activates the modify button without selecting an item the error occurs
     * to prevent this error, there is an if statement checking that the user selected an item in the TableView
     * and the selection is not null
     * if not, no code is run
     * <p>
     * method for delete button in the Product tabPane.
     * checks if an item is selected to prevent runtime error
     * checks if Product has associated Part/s
     * gives an error dialog and informs Product has associated Part/s
     * gives a confirmation dialog and confirms deletion if no associated Part/s
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
     * RUNTIME ERROR
     * an error occurs because the invocation target is not a number format
     * when user inputs anything that's not in a number format parseInt() method cannot execute
     * to prevent this the code is placed inside a try-catch block
     * when a user input anything but number format, it is thrown inside the catch block
     * <p>
     * method for search button in the Part tabPane.
     * try-catch block to test for number format to prevent Runtime error
     * throws NumberFormatException to a method that accepts String
     * calls a method to search for the matching String from the text field searchBarParts
     */
    @FXML
    void onSearchPart() {
        partTable.setItems(Inventory.getAllParts());

        try {
            //parses searchBarParts from String to Integer
            int i = Integer.parseInt(searchBarParts.getText());
            ObservableList<Part> found = FXCollections.observableArrayList();

            if (Inventory.lookupPart(i) != null) {
                partTable.getSelectionModel().select(Inventory.lookupPart(i));
            } else if (Inventory.lookupPart(i) == null) {
                partTable.setItems(found);
            }
        } catch (NumberFormatException e) {
            partTable.getSelectionModel().clearSelection();
            partTable.setItems(Inventory.lookupPart(searchBarParts.getText()));
        }

        searchBarParts.clear();
    }

    /**
     * RUNTIME ERROR
     * an error occurs because the invocation target is not a number format
     * when user inputs anything that's not in a number format parseInt() method cannot execute
     * to prevent this the code is placed inside a try-catch block
     * when a user input anything but number format, it is thrown inside the catch block
     * <p>
     * method for search button in the Product tabPane.
     * try-catch block to test for number format to prevent Runtime error
     * throws NumberFormatException to a method that accepts String
     * calls a method to search for the matching String from the text field searchBarProducts
     */
    @FXML
    void onSearchProduct() {
        productTable.setItems(Inventory.getAllProducts());

        try {
            //parses searchBarProducts from String to Integer
            int i = Integer.parseInt(searchBarProducts.getText());
            ObservableList<Product> found = FXCollections.observableArrayList();

            if (Inventory.lookupProduct(i) != null) {
                productTable.getSelectionModel().select(Inventory.lookupProduct(i));
            } else if (Inventory.lookupProduct(i) == null) {
                productTable.setItems(found);
            }
        } catch (NumberFormatException e) {
            productTable.getSelectionModel().clearSelection();
            productTable.setItems(Inventory.lookupProduct(searchBarParts.getText()));
        }

        searchBarProducts.clear();
    }

    /**
     * RUNTIME ERROR
     * an error occurs because the invocation target is not a number format
     * when user inputs anything that's not in a number format parseInt() method cannot execute
     * to prevent this the code is placed inside a try-catch block
     * when a user input anything but number format, it is thrown inside the catch block
     * <p>
     * method for listening if Enter button was pressed.
     * calls a method to search for the matching String from the text field searchBarParts
     *
     * @param event the event that button was pressed
     */
    @FXML
    void onEnterPart(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            partTable.setItems(Inventory.getAllParts());

            try {
                //parses searchBarParts from String to Integer
                int i = Integer.parseInt(searchBarParts.getText());
                ObservableList<Part> found = FXCollections.observableArrayList();

                if (Inventory.lookupPart(i) != null) {
                    partTable.getSelectionModel().select(Inventory.lookupPart(i));
                } else if (Inventory.lookupPart(i) == null) {
                    partTable.setItems(found);
                }
            } catch (NumberFormatException e) {
                partTable.getSelectionModel().clearSelection();
                partTable.setItems(Inventory.lookupPart(searchBarProducts.getText()));
            }

            searchBarParts.clear();
        }
    }

    /**
     * RUNTIME ERROR
     * an error occurs because the invocation target is not a number format
     * when user inputs anything that's not in a number format parseInt() method cannot execute
     * to prevent this the code is placed inside a try-catch block
     * when a user input anything but number format, it is thrown inside the catch block
     * <p>
     * method for listening if Enter button was pressed.
     * calls a method to search for the matching String from the text field searchBarProducts
     *
     * @param event the event that button was pressed
     */
    @FXML
    void onEnterProduct(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            productTable.setItems(Inventory.getAllProducts());

            try {
                //parses searchBarProducts from String to Integer
                int i = Integer.parseInt(searchBarProducts.getText());
                ObservableList<Product> found = FXCollections.observableArrayList();

                if (Inventory.lookupProduct(i) != null) {
                    productTable.getSelectionModel().select(Inventory.lookupProduct(i));
                } else if (Inventory.lookupProduct(i) == null) {
                    productTable.setItems(found);
                }
            } catch (NumberFormatException e) {
                productTable.getSelectionModel().clearSelection();
                productTable.setItems(Inventory.lookupProduct(searchBarProducts.getText()));
            }

            searchBarProducts.clear();
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

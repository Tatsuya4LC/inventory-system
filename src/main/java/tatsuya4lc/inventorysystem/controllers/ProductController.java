package tatsuya4lc.inventorysystem.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import tatsuya4lc.inventorysystem.MainApplication;
import tatsuya4lc.inventorysystem.models.Inventory;
import tatsuya4lc.inventorysystem.models.Part;
import tatsuya4lc.inventorysystem.models.Product;

import java.util.Objects;
import java.util.Optional;

/**
 * Controller for the Product window.
 * This class provides logic for the ProductView.fxml
 *
 * @author Tristan Lozano
 */
public class ProductController {
    //class variable to hold ObservableList object
    private final ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    //class variable to hold Product object
    private Product productHolder;
    //class variable initializes as boolean with false value
    private boolean updateProduct = false;
    //class variable to hold an integer
    private int productToModifyIndex;

    @FXML
    private TableColumn<Part, Integer> columnAssociatedPartID;

    @FXML
    private TableColumn<Part, Integer> columnAssociatedPartMax;

    @FXML
    private TableColumn<Part, Integer> columnAssociatedPartMin;

    @FXML
    private TableColumn<Part, String> columnAssociatedPartName;

    @FXML
    private TableColumn<Part, Double> columnAssociatedPartPrice;

    @FXML
    private TableColumn<Part, Integer> columnAssociatedPartStock;

    @FXML
    private TableColumn<Part, Integer> columnProductPartID;

    @FXML
    private TableColumn<Part, Integer> columnProductPartMax;

    @FXML
    private TableColumn<Part, Integer> columnProductPartMin;

    @FXML
    private TableColumn<Part, String> columnProductPartName;

    @FXML
    private TableColumn<Part, Double> columnProductPartPrice;

    @FXML
    private TableColumn<Part, Integer> columnProductPartStock;

    @FXML
    private TextField onProductSearchPart;

    @FXML
    private TableView<Part> productAssociatedPartTable;

    @FXML
    private TableView<Part> productPartTable;

    @FXML
    private TextField textProductID;

    @FXML
    private TextField textProductMax;

    @FXML
    private TextField textProductMin;

    @FXML
    private TextField textProductName;

    @FXML
    private TextField textProductPrice;

    @FXML
    private TextField textProductStock;

    @FXML
    private Label windowHeaderProduct;

    /**
     * method for add button in the Product window.
     * checks if an item is selected to prevent runtime error
     * checks whether adding or modifying
     * adds Part/s to the TableView productAssociatedPartTable
     */
    @FXML
    void onAddAssociatedPart() {
        if (!productPartTable.getSelectionModel().isEmpty()) {
            Part selectedPart = productPartTable.getSelectionModel().getSelectedItem();
            boolean exist = false;

            //for loop checks if part is already associated
            if (updateProduct) {
                for (Part part : associatedPartsList) {
                    if (part.getId() == selectedPart.getId()) {
                        exist = true;
                        break;
                    }
                }
            } else if (!associatedPartsList.isEmpty()) {
                for (Part part : associatedPartsList) {
                    if (part.getId() == selectedPart.getId()) {
                        exist = true;
                        break;
                    }
                }
            }

            if (!exist) {
                associatedPartsList.add(selectedPart);
            }

            productAssociatedPartTable.setItems(associatedPartsList);
        }

        productPartTable.getSelectionModel().clearSelection();
    }

    /**
     * method for cancel button in the Product window.
     * calls for a method from the MainApplication to go back to the main window
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onProductCancel(ActionEvent event) {
        MainApplication.changeMenu(event, 1, 0, null, null);
    }

    /**
     * method for save button in the Product window.
     * calls for placeProduct() method
     * checks if placeProduct() is true
     * checks if adding or modifying
     * for loop adds associated Part/s to a Product
     * adds or updates Product to the observable list allProducts
     * calls for a method from the MainApplication to go back to the main window
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onProductSave(ActionEvent event) {
        if (placeProduct()) {
            if (updateProduct) {
                for (Part part : associatedPartsList) {
                    productHolder.addAssociatedPart(part);
                }

                Inventory.updateProduct(productToModifyIndex, productHolder);
            } else {
                for (Part part : associatedPartsList) {
                    productHolder.addAssociatedPart(part);
                }

                Inventory.addProduct(productHolder);
            }
            MainApplication.changeMenu(event, 1, 0, null, null);
        }
    }

    /**
     * method for search button in the Product window.
     * try-catch block to test for number format to prevent Runtime error
     * throws NumberFormatException to a method that accepts String
     * calls a method to search for the matching String from the text field onProductSearchPart
     */
    @FXML
    void onProductSearchAdd() {
        productPartTable.setItems(Inventory.getAllParts());

        try {
            //parses searchBarParts from String to Integer
            int i = Integer.parseInt(onProductSearchPart.getText());
            ObservableList<Part> found = FXCollections.observableArrayList();

            if (Inventory.lookupPart(i) != null) {
                productPartTable.getSelectionModel().select(Inventory.lookupPart(i));
            } else if (Inventory.lookupPart(i) == null) {
                productPartTable.setItems(found);
            }
        } catch (NumberFormatException e) {
            productPartTable.getSelectionModel().clearSelection();
            productPartTable.setItems(Inventory.lookupPart(onProductSearchPart.getText()));
        }

        onProductSearchPart.clear();
    }

    /**
     * method for listening if Enter button was pressed.
     * calls a method to search for the matching String from the text field onProductSearchPart
     *
     * @param event the event that button was pressed
     */
    @FXML
    void onEnterPartSearch(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            productPartTable.setItems(Inventory.getAllParts());

            try {
                //parses searchBarParts from String to Integer
                int i = Integer.parseInt(onProductSearchPart.getText());
                ObservableList<Part> found = FXCollections.observableArrayList();

                if (Inventory.lookupPart(i) != null) {
                    productPartTable.getSelectionModel().select(Inventory.lookupPart(i));
                } else if (Inventory.lookupPart(i) == null) {
                    productPartTable.setItems(found);
                }
            } catch (NumberFormatException e) {
                productPartTable.getSelectionModel().clearSelection();
                productPartTable.setItems(Inventory.lookupPart(onProductSearchPart.getText()));
            }

            onProductSearchPart.clear();
        }
    }

    /**
     * method for remove associated part button in the Product window.
     * checks if an item is selected to prevent runtime error
     * checks if Product is not null
     * gives a confirmation dialog asking user to confirm before removing if Product is not null
     * removes associated product from the TableView productAssociatedPartTable
     */
    @FXML
    void onRemoveAssociatedPart() {
        Part selectedPart = productAssociatedPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            if (productHolder != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Remove Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Remove the associated Part from the Product \nWould you like to proceed?");
                Optional<ButtonType> result = alert.showAndWait();

                //checks for confirmation before removing
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    associatedPartsList.remove(selectedPart);
                    productAssociatedPartTable.getSelectionModel().select(null);
                }
            } else {
                associatedPartsList.remove(selectedPart);
                productAssociatedPartTable.getSelectionModel().select(null);
            }
        }
    }

    /**
     * method to getText() from the text fields in the Product window.
     * checks for logical errors such as no input in the text field/s,
     * stock is outside min/max range and min is greater than max
     * try-catch block to test for number format to prevent runtime error
     * throws NumberFormatException and changes error to true
     * changes PromptText in the text fields to inform user of error
     * gives an error dialog and informs that there was an error if error variable changes to true
     * checks if adding or modifying Product when error is false
     * then creates a new Product object assigned to productHolder using the attributes from text fields and productID()
     *
     * @return boolean
     */
    private boolean placeProduct() {
        boolean error = false;
        String name = textProductName.getText();
        double price = 0.0;
        int stock = 0, min = 0, max = 0;

        if (Objects.equals(name, "")) {
            error = true;
            textProductName.setPromptText("! cannot be empty");
            textProductName.clear();
        }

        try {
            price = Double.parseDouble(textProductPrice.getText());
        } catch (NumberFormatException e) {
            error = true;
            textProductPrice.setPromptText("! Expects a number");
            textProductPrice.clear();
        }

        try {
            stock = Integer.parseInt(textProductStock.getText());
        } catch (NumberFormatException e) {
            error = true;
            textProductStock.setPromptText("! Expects a number");
            textProductStock.clear();
        }

        try {
            min = Integer.parseInt(textProductMin.getText());
        } catch (NumberFormatException e) {
            error = true;
            textProductMin.setPromptText("! Expects a number");
            textProductMin.clear();
        }

        try {
            max = Integer.parseInt(textProductMax.getText());
        } catch (NumberFormatException e) {
            error = true;
            textProductMax.setPromptText("! Expects a number");
            textProductMax.clear();
        }

        if (error) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Expected Input Mismatch");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid value for each field with \"!\" \ncannot be empty");
            alert.showAndWait();

            return false;

        } else {
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Logical Error");
                alert.setHeaderText(null);
                alert.setContentText("Minimum cannot be greater than Maximum \nMinimum > Maximum");
                alert.showAndWait();

                return false;

            } else if (stock < min || stock > max) {
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

            if (updateProduct) {
                productHolder = new Product(Integer.parseInt(textProductID.getText()), name, price, stock, min, max);
            } else {
                productHolder = new Product(MainApplication.generateID(1), name, price, stock, min, max);
            }

            return true;
        }
    }

    /**
     * method for modify button to call from the main window and send value.
     *
     * @param index the index to send from MainController
     * @param product the Product object to send from MainController
     */
    public void isModifyingProduct(int index, Product product) {
        associatedPartsList.clear();
        productToModifyIndex = index;
        productHolder = product;
        updateProduct = true;
        windowHeaderProduct.setText("Modify Product");
        productAssociatedPartTable.setItems(associatedPartsList);
        associatedPartsList.addAll(product.getAllAssociatedParts());

        textProductID.setText(String.valueOf(product.getId()));
        textProductName.setText(product.getName());
        textProductPrice.setText(String.valueOf(product.getPrice()));
        textProductStock.setText(String.valueOf(product.getStock()));
        textProductMin.setText(String.valueOf(product.getMin()));
        textProductMax.setText(String.valueOf(product.getMax()));
    }

    /**
     * method for initializing the Part and Associated Part TableView.
     */
    public void addProductColumns() {
        columnProductPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnProductPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnProductPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnProductPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnProductPartMin.setCellValueFactory(new PropertyValueFactory<>("min"));
        columnProductPartMax.setCellValueFactory(new PropertyValueFactory<>("max"));
        productPartTable.setItems(Inventory.getAllParts());

        columnAssociatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnAssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnAssociatedPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnAssociatedPartMin.setCellValueFactory(new PropertyValueFactory<>("min"));
        columnAssociatedPartMax.setCellValueFactory(new PropertyValueFactory<>("max"));
    }
}

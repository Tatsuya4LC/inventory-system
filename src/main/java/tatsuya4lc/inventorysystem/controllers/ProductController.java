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

import java.util.Optional;

public class ProductController {
    private final ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    Product productHolder;
    private boolean updateProduct = false, inputError, logicError;
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

    @FXML
    void onAddAssociatedPart(ActionEvent event) {
        if (!productPartTable.getSelectionModel().isEmpty()) {
            Part selectedPart = productPartTable.getSelectionModel().getSelectedItem();
            boolean exist = false;

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

        productPartTable.getSelectionModel().select(null);
    }


    @FXML
    void onProductCancel(ActionEvent event) {
        MainApplication.changeMenu(event, 1, 0, null);
    }

    @FXML
    void onProductSave(ActionEvent event) {
        placeProduct();

        if (inputError || logicError) {
            //catches error and does nothing
        } else {
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
            MainApplication.changeMenu(event, 1, 0, null);
        }
    }

    @FXML
    void onProductSearchAdd() {
        productPartTable.setItems(Inventory.getAllParts());

        try {
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

    @FXML
    void onEnterPartSearch(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            productPartTable.setItems(Inventory.getAllParts());

            try {
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

    @FXML
    void onRemoveAssociatedPart() {
        Part selectedPart = productAssociatedPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            if (productHolder != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Remove Confirmation");
                alert.setHeaderText("Remove associated Part from Product");
                alert.setContentText("Would you like to proceed?");
                Optional<ButtonType> result = alert.showAndWait();

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

    public int productID() {
        int i = 1;

        for(Product product : Inventory.getAllProducts()) {
            if(product.getId() == i) {
                i++;
            }
        }

        return i;
    }

    public void placeProduct() {
        inputError = false;
        logicError = false;
        String name = textProductName.getText();
        double price = 0.0;
        int stock = 0, min = 0, max = 0;

        try {
            price = Double.parseDouble(textProductPrice.getText());
        } catch (NumberFormatException e) {
            inputError = true;
            textProductPrice.setPromptText("! Expects a number");
        }

        try {
            stock = Integer.parseInt(textProductStock.getText());
        } catch (NumberFormatException e) {
            inputError = true;
            textProductStock.setPromptText("! Expects a number");
        }

        try {
            min = Integer.parseInt(textProductMin.getText());
        } catch (NumberFormatException e) {
            inputError = true;
            textProductMin.setPromptText("! Expects a number");
        }

        try {
            max = Integer.parseInt(textProductMax.getText());
        } catch (NumberFormatException e) {
            inputError = true;
            textProductMax.setPromptText("! Expects a number");
        }

        if (inputError) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Expected Input Mismatch");
            alert.setHeaderText("Incorrect Input Type");
            alert.setContentText("Please enter a valid value for each field with \"!\" \n cannot be empty");
            alert.showAndWait();
        } else {
            if (min > max) {
                logicError = true;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Logical Error");
                alert.setContentText("Minimum cannot be greater than Maximum \n Minimum > Maximum");
                alert.showAndWait();
            } else if (stock < min || stock > max) {
                logicError = true;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Logical Error");

                if (stock < min) {
                    alert.setContentText("Stock is out of range \n Stock < Minimum");
                } else {
                    alert.setContentText("Stock is out of range \n Stock > Maximum");
                }

                alert.showAndWait();
            }

            if (updateProduct) {
                productHolder = new Product(Integer.parseInt(textProductID.getText()), name, price, stock, min, max);
            } else {
                productHolder = new Product(productID(), name, price, stock, min, max);
            }
        }
    }

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

        columnAssociatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnAssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnAssociatedPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnAssociatedPartMin.setCellValueFactory(new PropertyValueFactory<>("min"));
        columnAssociatedPartMax.setCellValueFactory(new PropertyValueFactory<>("max"));
    }

    public void newProduct() {
        columnProductPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnProductPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnProductPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnProductPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnProductPartMin.setCellValueFactory(new PropertyValueFactory<>("min"));
        columnProductPartMax.setCellValueFactory(new PropertyValueFactory<>("max"));
        productPartTable.setItems(Inventory.getAllParts());
    }
}

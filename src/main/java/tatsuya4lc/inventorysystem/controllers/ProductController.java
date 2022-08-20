package tatsuya4lc.inventorysystem.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tatsuya4lc.inventorysystem.MainApplication;
import tatsuya4lc.inventorysystem.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    Product productHolder;
    private boolean updateProduct = false;
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
        Part selectedPart = productPartTable.getSelectionModel().getSelectedItem();
        boolean exist = false;

        if (updateProduct) {
            associatedPartsList = productHolder.getAllAssociatedParts();

            for (Part part : associatedPartsList) {
                if (part.getId() == selectedPart.getId()) {
                    exist = true;
                }
            }

        } else {
            if (associatedPartsList.isEmpty()) {
                associatedPartsList.add(selectedPart);
        }

            for (Part part : associatedPartsList) {
                if (part.getId() == selectedPart.getId()) {
                    exist = true;
                }
            }

        }
        if (!exist) {
            associatedPartsList.add(selectedPart);
        }
        productAssociatedPartTable.setItems(associatedPartsList);
    }

    @FXML
    void onProductCancel(ActionEvent event) throws IOException {
        mainMenu(event);
    }

    @FXML
    void onProductSave(ActionEvent event) throws IOException {
        if (placeProduct() && !updateProduct) {
            for(int i = 0; i < associatedPartsList.size(); i++) {
                productHolder.addAssociatedPart(associatedPartsList.get(i));
            }

            Inventory.addProduct(productHolder);
            mainMenu(event);
        } else if (updateProduct && placeProduct()) {
            for(int i = 0; i < associatedPartsList.size(); i++) {
                productHolder.addAssociatedPart(associatedPartsList.get(i));
            }

            Inventory.updateProduct(productToModifyIndex, productHolder);
            mainMenu(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incorrect Input Type");
            alert.setContentText("Please enter a valid value for each field with \"!\"");
            alert.showAndWait();
        }
    }

    @FXML
    void onProductSearchAdd(ActionEvent event) {
        try {
            int i = Integer.parseInt(onProductSearchPart.getText());
            ObservableList<Part> found = FXCollections.observableArrayList();

            if (Inventory.lookupPart(i) != null) {
                found.add(Inventory.lookupPart(i));
                productPartTable.setItems(found);
            } else if (Inventory.lookupProduct(i) == null) {
                productPartTable.setItems(found);
            }
        }

        catch (NumberFormatException e) {
            productPartTable.setItems(Inventory.lookupPart(onProductSearchPart.getText()));
        }

        onProductSearchPart.clear();
    }

    @FXML
    void onEnterPartSearch(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                int i = Integer.parseInt(onProductSearchPart.getText());
                ObservableList<Part> found = FXCollections.observableArrayList();

                if (Inventory.lookupPart(i) != null) {
                    found.add(Inventory.lookupPart(i));
                    productPartTable.setItems(found);
                } else if (Inventory.lookupProduct(i) == null) {
                    productPartTable.setItems(found);
                }
            }

            catch (NumberFormatException e) {
                productPartTable.setItems(Inventory.lookupPart(onProductSearchPart.getText()));
            }

            onProductSearchPart.clear();
        }
    }

    @FXML
    void onRemoveAssociatedPart(ActionEvent event) {
        Part selectedPart = productAssociatedPartTable.getSelectionModel().getSelectedItem();
        productHolder.deleteAssociatedPart(selectedPart);
        associatedPartsList = productHolder.getAllAssociatedParts();
        productAssociatedPartTable.setItems(productHolder.getAllAssociatedParts());
    }

    public void mainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();

        MainController MC = fxmlLoader.getController();
        MC.selectTabProduct();
    }

    public int productID(){
        int i = 1;

        for(Product product : Inventory.getAllProducts()) {
            if(product.getId() == i) {
                i++;
            }
        }
        return i;
    }

    public boolean placeProduct(){
        String name = textProductName.getText();
        boolean noError = true;
        double price = 0.0;
        int id = 0, stock = 0, min = 0, max = 0;

        try {
            price = Double.parseDouble(textProductPrice.getText());
        }
        catch (NumberFormatException e) {
            noError = false;
            textProductPrice.setPromptText("! Exception: " + e.getMessage());
        }

        try {
            stock = Integer.parseInt(textProductStock.getText());
        }
        catch (NumberFormatException e) {
            noError = false;
            textProductStock.setPromptText("! Exception: " + e.getMessage());
        }

        try {
            min = Integer.parseInt(textProductMin.getText());
        }
        catch (NumberFormatException e) {
            noError = false;
            textProductMin.setPromptText("! Exception: " + e.getMessage());
        }

        try {
            max = Integer.parseInt(textProductMax.getText());
        }
        catch (NumberFormatException e) {
            noError = false;
            textProductMax.setPromptText("! Exception: " + e.getMessage());
        }

        if (updateProduct) {
            productHolder = new Product(Integer.parseInt(textProductID.getText()), name, price, stock, min, max);
        } else {
            productHolder = new Product(productID(), name, price, stock, min, max);
        }

        return noError;
    }

    public void isModifyingProduct(int index, Product product) {
        productToModifyIndex = index;
        productHolder = product;
        updateProduct = true;
        windowHeaderProduct.setText("Modify Product");
        productAssociatedPartTable.setItems(product.getAllAssociatedParts());

        textProductID.setText(String.valueOf(product.getId()));
        textProductName.setText(product.getName());
        textProductPrice.setText(String.valueOf(product.getPrice()));
        textProductStock.setText(String.valueOf(product.getStock()));
        textProductMin.setText(String.valueOf(product.getMin()));
        textProductMax.setText(String.valueOf(product.getMax()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        System.out.println("Product Controller Initialized");
    }
}

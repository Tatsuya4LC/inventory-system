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
import tatsuya4lc.inventorysystem.models.Inventory;
import tatsuya4lc.inventorysystem.models.Part;
import tatsuya4lc.inventorysystem.models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    private Tab selectedParts;

    @FXML
    private Tab selectedProducts;

    @FXML
    void onAddPart(ActionEvent event) throws IOException {
        partMenu(event);
    }

    @FXML
    void onAddProduct(ActionEvent event) throws IOException {
        productMenu(event);
    }

    @FXML
    void onExitPart() {
        System.exit(0);
    }

    @FXML
    void onExitProduct() {
        System.exit(0);
    }

    @FXML
    void onModifyPart(ActionEvent event) throws IOException {
        if (partTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Item selection does not exist\nPlease select an item");
            alert.showAndWait();
        }

        else {
            FXMLLoader loader = partMenu(event);
            PartController PaC = loader.getController();
            PaC.isModifyingPart(partTable.getSelectionModel().getSelectedIndex(), partTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void onModifyProduct(ActionEvent event) throws IOException {
        if (productTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Item selection does not exist\nPlease select an item");
            alert.showAndWait();
        }

        else {
            FXMLLoader loader = productMenu(event);
            ProductController PaC = loader.getController();
            PaC.isModifyingProduct(productTable.getSelectionModel().getSelectedIndex(), productTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void onRemovePart() {
        Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
        partTable.setItems(Inventory.getAllParts());
        partTable.getSelectionModel().select(null);
    }

    @FXML
    void onRemoveProduct() {
        Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
        productTable.setItems(Inventory.getAllProducts());
        productTable.getSelectionModel().select(null);
    }

    @FXML
    void onSearchParts() {
        try {
            int i = Integer.parseInt(searchBarParts.getText());
            ObservableList<Part> found = FXCollections.observableArrayList();

            if (Inventory.lookupPart(i) != null) {
                found.add(Inventory.lookupPart(i));
                partTable.setItems(found);
            }

            else if (Inventory.lookupPart(i) == null) {
                partTable.setItems(found);
            }
        }

        catch (NumberFormatException e) {
            partTable.setItems(Inventory.lookupPart(searchBarParts.getText()));
        }

        searchBarParts.clear();
    }

    @FXML
    void onSearchProduct() {
        try {
            int i = Integer.parseInt(searchBarProducts.getText());
            ObservableList<Product> found = FXCollections.observableArrayList();

            if (Inventory.lookupProduct(i) != null) {
                found.add(Inventory.lookupProduct(i));
                productTable.setItems(found);
            }

            else if (Inventory.lookupProduct(i) == null) {
                productTable.setItems(found);
            }
        }

        catch (NumberFormatException e) {
            productTable.setItems(Inventory.lookupProduct(searchBarProducts.getText()));
        }

        searchBarProducts.clear();
    }

    @FXML
    void onEnterPart(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                int i = Integer.parseInt(searchBarParts.getText());
                ObservableList<Part> found = FXCollections.observableArrayList();

                if (Inventory.lookupPart(i) != null) {
                    found.add(Inventory.lookupPart(i));
                    partTable.setItems(found);
                }

                else if (Inventory.lookupPart(i) == null) {
                    partTable.setItems(found);
                }
            }

            catch (NumberFormatException e) {
                partTable.setItems(Inventory.lookupPart(searchBarParts.getText()));
            }

            searchBarParts.clear();
        }
    }

    @FXML
    void onEnterProduct(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                int i = Integer.parseInt(searchBarProducts.getText());
                ObservableList<Product> found = FXCollections.observableArrayList();

                if (Inventory.lookupProduct(i) != null) {
                    found.add(Inventory.lookupProduct(i));
                    productTable.setItems(found);
                }

                else if (Inventory.lookupProduct(i) == null) {
                    productTable.setItems(found);
                }
            }

            catch (NumberFormatException e) {
                productTable.setItems(Inventory.lookupProduct(searchBarProducts.getText()));
            }

            searchBarProducts.clear();
        }
    }

    public void selectTabPart() {
        SingleSelectionModel<Tab> selectionModel = selectedParts.getTabPane().getSelectionModel();
        selectionModel.select(selectedParts);
    }

    public FXMLLoader partMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("PartView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();

        return fxmlLoader;
    }
    public FXMLLoader productMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("ProductView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();

        return fxmlLoader;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
}
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
    private TableColumn<Part, Integer> colPartID;

    @FXML
    private TableColumn<Part, Integer> colPartMax;

    @FXML
    private TableColumn<Part, Integer> colPartMin;

    @FXML
    private TableColumn<Part, String> colPartName;

    @FXML
    private TableColumn<Part, Double> colPartPrice;

    @FXML
    private TableColumn<Part, Integer> colPartStock;

    @FXML
    private TableColumn<Product, Integer> colProductID;

    @FXML
    private TableColumn<Product, Integer> colProductMax;

    @FXML
    private TableColumn<Product, Integer> colProductMin;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private TableColumn<Product, Double> colProductPrice;

    @FXML
    private TableColumn<Product, Integer> colProductStock;

    @FXML
    private TableView<Part> partView;

    @FXML
    private TableView<Product> productView;

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
        partView(event, "PartView.fxml");
    }

    @FXML
    void onAddProduct(ActionEvent event) {

    }

    @FXML
    void onExitPart(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onExitProduct(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onModifyPart(ActionEvent event) throws IOException {
        if (partView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Item selection does not exist\nPlease select an item");
            alert.showAndWait();
        } else {
            FXMLLoader loader = partView(event, "PartView.fxml");
            PartController PrC = loader.getController();
            PrC.isModifyingPart(partView.getSelectionModel().getSelectedIndex(), partView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void onModifyProduct(ActionEvent event) {

    }

    @FXML
    void onRemovePart(ActionEvent event) {
        Inventory.deletePart(partView.getSelectionModel().getSelectedItem());
        partView.setItems(Inventory.getAllParts());
        partView.getSelectionModel().select(null);
    }

    @FXML
    void onRemoveProduct(ActionEvent event) {
        Inventory.deleteProduct(productView.getSelectionModel().getSelectedItem());
        productView.setItems(Inventory.getAllProducts());
        productView.getSelectionModel().select(null);
    }

    @FXML
    void onSearchParts(ActionEvent event) {
        try {
            int i = Integer.parseInt(searchBarParts.getText());
            ObservableList<Part> found = FXCollections.observableArrayList();

            if (Inventory.lookupPart(i) != null) {
                found.add(Inventory.lookupPart(i));
                partView.setItems(found);
            } else if (Inventory.lookupProduct(i) == null) {
                partView.setItems(found);
            }
        }

        catch (NumberFormatException e) {
            partView.setItems(Inventory.lookupPart(searchBarParts.getText()));
        }
    }

    @FXML
    void onSearchProduct(ActionEvent event) {
        try {
            int i = Integer.parseInt(searchBarProducts.getText());
            ObservableList<Product> found = FXCollections.observableArrayList();

            if (Inventory.lookupProduct(i) != null) {
                found.add(Inventory.lookupProduct(i));
                productView.setItems(found);
            } else if (Inventory.lookupProduct(i) == null) {
                productView.setItems(found);
            }
        }

        catch (NumberFormatException e) {
            productView.setItems(Inventory.lookupProduct(searchBarProducts.getText()));
        }

        searchBarProducts.clear();
    }

    public void selectTabPart() {
        SingleSelectionModel<Tab> selectionModel = selectedParts.getTabPane().getSelectionModel();
        selectionModel.select(selectedParts);
    }

    public void selectTabProduct() {
        SingleSelectionModel<Tab> selectionModel = selectedProducts.getTabPane().getSelectionModel();
        selectionModel.select(selectedProducts);
    }

    public FXMLLoader partView(ActionEvent event, String view) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(view));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();

        return fxmlLoader;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProductStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colProductMin.setCellValueFactory(new PropertyValueFactory<>("min"));
        colProductMax.setCellValueFactory(new PropertyValueFactory<>("max"));

        productView.setItems(Inventory.getAllProducts());

        colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartMin.setCellValueFactory(new PropertyValueFactory<>("min"));
        colPartMax.setCellValueFactory(new PropertyValueFactory<>("max"));

        partView.setItems(Inventory.getAllParts());




        System.out.println("Main Controller Initialized");
    }
}
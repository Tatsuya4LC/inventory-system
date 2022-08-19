package tatsuya4lc.inventorysystem.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tatsuya4lc.inventorysystem.models.Inventory;
import tatsuya4lc.inventorysystem.models.Part;
import tatsuya4lc.inventorysystem.models.Product;

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
    void onAddPart(ActionEvent event) {

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
    void onModifyPart(ActionEvent event) {

    }

    @FXML
    void onModifyProduct(ActionEvent event) {

    }

    @FXML
    void onRemovePart(ActionEvent event) {

    }

    @FXML
    void onRemoveProduct(ActionEvent event) {

    }

    @FXML
    void onSearchParts(ActionEvent event) {
        String search = searchBarParts.getText();
        partView.setItems(Inventory.lookupPart(search));
        searchBarParts.setText("");

        if (Inventory.lookupPart(search).isEmpty()) {
            try {
                int searchInt = Integer.parseInt(search);
                ObservableList<Part> found = FXCollections.observableArrayList();

                if (Inventory.lookupPart(searchInt) != null) {
                    found.add(Inventory.lookupPart(searchInt));
                    partView.setItems(found);
                }
            }

            catch (NumberFormatException e) {
                partView.setItems(Inventory.lookupPart(search));
            }
        }
    }

    @FXML
    void onSearchProduct(ActionEvent event) {
        String search = searchBarProducts.getText();
        productView.setItems(Inventory.lookupProduct(search));
        searchBarProducts.setText("");

        if(Inventory.lookupProduct(search).isEmpty()) {
            try {
                int searchInt = Integer.parseInt(search);
                ObservableList<Product> found = FXCollections.observableArrayList();

                if (Inventory.lookupProduct(searchInt) != null) {
                    found.add(Inventory.lookupProduct(searchInt));
                    productView.setItems(found);
                }
            }

            catch (NumberFormatException e) {
                productView.setItems(Inventory.lookupProduct(search));
            }
        }
    }

    @FXML
    void onSelectParts(ActionEvent event) {
        productView.setItems(Inventory.getAllProducts());
    }

    @FXML
    void onSelectProducts(ActionEvent event) {
        partView.setItems(Inventory.getAllParts());
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
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
    private TabPane tabPane;

    @FXML
    void onAddPart(ActionEvent event) {
        MainApplication.changeMenu(event, 2, 0, null);
    }

    @FXML
    void onAddProduct(ActionEvent event) {
        MainApplication.changeMenu(event, 3, 3, null);
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
    void onModifyPart(ActionEvent event) {
        if (!partTable.getSelectionModel().isEmpty()) {
            MainApplication.changeMenu(event, 2, 1, partTable);
        }
    }

    @FXML
    void onModifyProduct(ActionEvent event) {
        if (!productTable.getSelectionModel().isEmpty()) {
            MainApplication.changeMenu(event, 3, 2, productTable);
        }
    }

    @FXML
    void onDeletePart() {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        boolean exist = false;

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
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Conflict");
                alert2.setHeaderText("This Part is associated with a Product \n cannot be deleted");
                alert2.setContentText("Associated Part");
                alert2.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText("This will delete the Part");
                alert.setContentText("Would you like to proceed:");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deletePart(selectedPart);
                    partTable.getSelectionModel().select(null);
                }
            }
        }
    }

    @FXML
    void onDeleteProduct() {
        if (!productTable.getSelectionModel().isEmpty()) {
            if (!productTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Conflict");
                alert.setHeaderText("Product cannot be deleted because of existing associated Part/s");
                alert.setContentText("Associated Part/s exist");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText("This will delete the Product");
                alert.setContentText("Would you like to proceed:");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
                    productTable.getSelectionModel().select(null);
                }
            }
        }
    }

    @FXML
    void onSearchParts() {
        partTable.setItems(Inventory.getAllParts());

        try {
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

    @FXML
    void onSearchProduct() {
        productTable.setItems(Inventory.getAllProducts());

        try {
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

    @FXML
    void onEnterPart(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            partTable.setItems(Inventory.getAllParts());

            try {
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
    }

    @FXML
    void onEnterProduct(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            productTable.setItems(Inventory.getAllProducts());

            try {
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

    public void selectTabPart() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(selectedParts);
    }

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
        System.out.println("Initialization");
    }
}
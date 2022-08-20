package tatsuya4lc.inventorysystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tatsuya4lc.inventorysystem.MainApplication;
import tatsuya4lc.inventorysystem.models.InHouse;
import tatsuya4lc.inventorysystem.models.Inventory;
import tatsuya4lc.inventorysystem.models.Outsourced;
import tatsuya4lc.inventorysystem.models.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PartController implements Initializable {
    private int partToModifyIndex;
    private boolean updatePart = false;

    @FXML
    private RadioButton inHouse;

    @FXML
    private Label labelPartID;

    @FXML
    private Label labelPartInOut;

    @FXML
    private Label labelPartMax;

    @FXML
    private Label labelPartMin;

    @FXML
    private Label labelPartName;

    @FXML
    private Label labelPartPrice;

    @FXML
    private Label labelPartStock;

    @FXML
    private RadioButton outSourced;

    @FXML
    private ToggleGroup partSource;

    @FXML
    private TextField textPartID;

    @FXML
    private TextField textPartInOut;

    @FXML
    private TextField textPartMax;

    @FXML
    private TextField textPartMin;

    @FXML
    private TextField textPartName;

    @FXML
    private TextField textPartPrice;

    @FXML
    private TextField textPartStock;

    @FXML
    private Label windowHeader;

    @FXML
    void optionInHouse(ActionEvent event) {
        labelPartInOut.setText("Machine ID");;
        textPartInOut.setPromptText("Enter Machine ID");
    }

    @FXML
    void optionOutSourced(ActionEvent event) {
        labelPartInOut.setText("Company Name");
        textPartInOut.setPromptText("Enter Company Name");
    }

    @FXML
    void onSave(ActionEvent event) throws IOException {
        if (inHouse.isSelected()) {
            inHousePartSave(event);
        }

        if (outSourced.isSelected()) {
            outSourcedPartSave(event);
        }
    }

    @FXML
    void onCancel(ActionEvent event) throws IOException {
            mainMenu(event);
    }

    public void mainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();

        MainController MC = fxmlLoader.getController();
        MC.selectTabPart();
    }

    public int partID(){
        int i = 1;

        for(Part part : Inventory.getAllParts()) {
            if(part.getId() == i) {
                i++;
            }
        }
        return i;
    }

    public void inHousePartSave(ActionEvent event) throws IOException {
        String name = textPartName.getText();
        boolean error = false;
        double price = 0;
        int stock = 0, max = 0, min = 0, machineId = 0;

        try {
            price = Double.parseDouble(textPartPrice.getText());
        }
        catch (NumberFormatException e) {
            error = true;
            textPartPrice.setPromptText("! Exception: " + e.getMessage());
            textPartPrice.clear();
        }

        try {
            stock = Integer.parseInt(textPartStock.getText());
        }
        catch (NumberFormatException e) {
            error = true;
            textPartStock.setPromptText("! Exception: " + e.getMessage());
            textPartStock.clear();
        }

        try {
            max = Integer.parseInt(textPartMax.getText());
        }
        catch (NumberFormatException e) {
            error = true;
            textPartMax.setPromptText("! Exception: " + e.getMessage());
            textPartMax.clear();
        }

        try {
            min = Integer.parseInt(textPartMin.getText());
        }
        catch (NumberFormatException e) {
            error = true;
            textPartMin.setPromptText("! Exception: " + e.getMessage());
            textPartMin.clear();
        }

        try {
            machineId = Integer.parseInt(textPartInOut.getText());
        }
        catch (NumberFormatException e) {
            error = true;
            textPartInOut.setPromptText("! Exception: " + e.getMessage());
            textPartInOut.clear();
        }

        if(!error && !updatePart) {
            Inventory.addPart(new InHouse(partID(), name, price, stock, min, max, machineId));
            mainMenu(event);
        } else if (!error && updatePart)  {
            Inventory.updatePart(partToModifyIndex, new InHouse(Integer.parseInt(textPartID.getText()), name, price, stock, min, max, machineId));
            mainMenu(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incorrect Input Type");
            alert.setContentText("Please enter a valid value for each field with \"!\"");
            alert.showAndWait();
        }
    }

    public void outSourcedPartSave(ActionEvent event) throws IOException {
        String name = textPartName.getText();
        String machineId = textPartInOut.getText();
        boolean error = false;
        double price = 0;
        int stock = 0, max = 0, min = 0;

        try {
            price = Double.parseDouble(textPartPrice.getText());
        }
        catch (NumberFormatException e) {
            error = true;
            textPartPrice.setPromptText("! Exception: " + e.getMessage());
            textPartPrice.clear();
        }

        try {
            stock = Integer.parseInt(textPartStock.getText());
        }
        catch (NumberFormatException e) {
            error = true;
            textPartStock.setPromptText("! Exception: " + e.getMessage());
            textPartStock.clear();
        }

        try {
            max = Integer.parseInt(textPartMax.getText());
        }
        catch (NumberFormatException e) {
            error = true;
            textPartMax.setPromptText("! Exception: " + e.getMessage());
            textPartMax.clear();
        }

        try {
            min = Integer.parseInt(textPartMin.getText());
        }
        catch (NumberFormatException e) {
            error = true;
            textPartMin.setPromptText("! Exception: " + e.getMessage());
            textPartMin.clear();
        }

        if(!error && !updatePart) {
            Inventory.addPart(new Outsourced(partID(), name, price, stock, min, max, machineId));
            mainMenu(event);
        } else if (!error && updatePart)  {
            Inventory.updatePart(partToModifyIndex, new Outsourced(Integer.parseInt(textPartID.getText()), name, price, stock, min, max, machineId));
            mainMenu(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Incorrect Input Type");
            alert.setContentText("Please enter a valid value for each field with \"!\"");
            alert.showAndWait();
        }
    }

    public void isModifyingPart(int index, Part part) {
        partToModifyIndex = index;
        updatePart = true;
        windowHeader.setText("Modify Part");

        textPartID.setText(String.valueOf(part.getId()));
        textPartName.setText(part.getName());
        textPartPrice.setText(String.valueOf(part.getPrice()));
        textPartStock.setText(String.valueOf(part.getStock()));
        textPartMin.setText(String.valueOf(part.getMin()));
        textPartMax.setText(String.valueOf(part.getMax()));

        if(part instanceof InHouse) {
            textPartInOut.setText(String.valueOf(((InHouse) part).getMachineId()));
            inHouse.fire();
        }

        if(part instanceof Outsourced) {
            textPartInOut.setText(((Outsourced) part).getCompanyName());
            outSourced.fire();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Part Controller Initialized");
    }
}

package tatsuya4lc.inventorysystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tatsuya4lc.inventorysystem.MainApplication;
import tatsuya4lc.inventorysystem.models.InHouse;
import tatsuya4lc.inventorysystem.models.Inventory;
import tatsuya4lc.inventorysystem.models.Outsourced;
import tatsuya4lc.inventorysystem.models.Part;

import java.io.IOException;

public class PartController {
    Part partHolder;
    private int partToModifyIndex;
    private boolean updatePart = false;

    @FXML
    private RadioButton inHouse;

    @FXML
    private Label labelPartInOut;

    @FXML
    private RadioButton outSourced;

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
    private Label windowHeaderPart;

    @FXML
    private ToggleGroup partSource;

    @FXML
    void optionInHouse() {
        labelPartInOut.setText("Machine ID");
        textPartInOut.setPromptText("Enter Machine ID");
    }

    @FXML
    void optionOutSourced() {
        labelPartInOut.setText("Company Name");
        textPartInOut.setPromptText("Enter Company Name");
    }

    @FXML
    void onPartSave(ActionEvent event) throws IOException {
        if (inHouse.isSelected()) {
            if(partInHouse() && !updatePart) {
                Inventory.addPart(partHolder);
                mainMenu(event);
            }

            else if (updatePart && partInHouse())  {
                Inventory.updatePart(partToModifyIndex, partHolder);
                mainMenu(event);
            }

            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Incorrect Input Type");
                alert.setContentText("Please enter a valid value for each field with \"!\"");
                alert.showAndWait();
            }
        }

        if (outSourced.isSelected()) {
            if(partOutSourced() && !updatePart) {
                Inventory.addPart(partHolder);
                mainMenu(event);
            }

            else if (updatePart && partOutSourced())  {
                Inventory.updatePart(partToModifyIndex, partHolder);
                mainMenu(event);
            }

            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Incorrect Input Type");
                alert.setContentText("Please enter a valid value for each field with \"!\"");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void onPartCancel(ActionEvent event) throws IOException {
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

    public boolean partInHouse() {
        String name = textPartName.getText();
        boolean noError = true;
        double price = 0.0;
        int stock = 0, max = 0, min = 0, machineId = 0;

        try {
            price = Double.parseDouble(textPartPrice.getText());
        }

        catch (NumberFormatException e) {
            noError = false;
            textPartPrice.setPromptText("! Exception: " + e.getMessage());
            textPartPrice.clear();
        }

        try {
            stock = Integer.parseInt(textPartStock.getText());
        }

        catch (NumberFormatException e) {
            noError = false;
            textPartStock.setPromptText("! Exception: " + e.getMessage());
            textPartStock.clear();
        }

        try {
            max = Integer.parseInt(textPartMax.getText());
        }

        catch (NumberFormatException e) {
            noError = false;
            textPartMax.setPromptText("! Exception: " + e.getMessage());
            textPartMax.clear();
        }

        try {
            min = Integer.parseInt(textPartMin.getText());
        }

        catch (NumberFormatException e) {
            noError = false;
            textPartMin.setPromptText("! Exception: " + e.getMessage());
            textPartMin.clear();
        }

        try {
            machineId = Integer.parseInt(textPartInOut.getText());
        }

        catch (NumberFormatException e) {
            noError = false;
            textPartInOut.setPromptText("! Exception: " + e.getMessage());
            textPartInOut.clear();
        }

        if (updatePart) {
            partHolder = new InHouse(Integer.parseInt(textPartID.getText()), name, price, stock, min, max, machineId);
        }

        else {
            partHolder = new InHouse(partID(), name, price, stock, min, max, machineId);
        }

        return noError;
    }

    public boolean partOutSourced() {
        String name = textPartName.getText();
        String machineId = textPartInOut.getText();
        boolean noError = true;
        double price = 0.0;
        int stock = 0, max = 0, min = 0;

        try {
            price = Double.parseDouble(textPartPrice.getText());
        }

        catch (NumberFormatException e) {
            noError = false;
            textPartPrice.setPromptText("! Exception: " + e.getMessage());
            textPartPrice.clear();
        }

        try {
            stock = Integer.parseInt(textPartStock.getText());
        }

        catch (NumberFormatException e) {
            noError = false;
            textPartStock.setPromptText("! Exception: " + e.getMessage());
            textPartStock.clear();
        }

        try {
            max = Integer.parseInt(textPartMax.getText());
        }

        catch (NumberFormatException e) {
            noError = false;
            textPartMax.setPromptText("! Exception: " + e.getMessage());
            textPartMax.clear();
        }

        try {
            min = Integer.parseInt(textPartMin.getText());
        }

        catch (NumberFormatException e) {
            noError = false;
            textPartMin.setPromptText("! Exception: " + e.getMessage());
            textPartMin.clear();
        }

        if (updatePart) {
            partHolder = new Outsourced(Integer.parseInt(textPartID.getText()), name, price, stock, min, max, machineId);
        }

        else {
            partHolder = new Outsourced(partID(), name, price, stock, min, max, machineId);
        }

        return noError;
    }

    public void isModifyingPart(int index, Part part) {
        partToModifyIndex = index;
        updatePart = true;
        windowHeaderPart.setText("Modify Part");

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
}

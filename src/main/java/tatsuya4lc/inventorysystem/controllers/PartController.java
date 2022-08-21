package tatsuya4lc.inventorysystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tatsuya4lc.inventorysystem.MainApplication;
import tatsuya4lc.inventorysystem.models.InHouse;
import tatsuya4lc.inventorysystem.models.Inventory;
import tatsuya4lc.inventorysystem.models.Outsourced;
import tatsuya4lc.inventorysystem.models.Part;

public class PartController {
    Part partHolder;
    private int partToModifyIndex;
    private boolean updatePart = false;
    private boolean inputError, logicError;

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
    void onPartSave(ActionEvent event) {
        placePart();

        if (inputError || logicError) {
            //catches error and does nothing
        } else {
            if (updatePart) {
                Inventory.updatePart(partToModifyIndex, partHolder);
            } else {
                Inventory.addPart(partHolder);
            }
            MainApplication.changeMenu(event, 1, 4, null);
        }
    }

    @FXML
    void onPartCancel(ActionEvent event) {
        MainApplication.changeMenu(event, 1, 4, null);
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

    public void placePart() {
        inputError = false;
        logicError = false;
        String name = textPartName.getText();
        String companyName = textPartInOut.getText();
        double price = 0.0;
        int stock = 0, max = 0, min = 0, machineId =0;

        try {
            price = Double.parseDouble(textPartPrice.getText());
        } catch (NumberFormatException e) {
            inputError = true;
            textPartPrice.setPromptText("! Expects a number");
            textPartPrice.clear();
        }

        try {
            stock = Integer.parseInt(textPartStock.getText());
        } catch (NumberFormatException e) {
            inputError = true;
            textPartStock.setPromptText("! Expects a number");
            textPartStock.clear();
        }

        try {
            max = Integer.parseInt(textPartMax.getText());
        } catch (NumberFormatException e) {
            inputError = true;
            textPartMax.setPromptText("! Expects a number");
            textPartMax.clear();
        }

        try {
            min = Integer.parseInt(textPartMin.getText());
        } catch (NumberFormatException e) {
            inputError = true;
            textPartMin.setPromptText("! Expects a number");
            textPartMin.clear();
        }

        try {
            machineId = Integer.parseInt(textPartInOut.getText());
        } catch (NumberFormatException e) {
            inputError = true;
            textPartInOut.setPromptText("! Expects a number");
            textPartInOut.clear();
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

            if (inHouse.isSelected()) {
                if (updatePart) {
                    partHolder = new InHouse(Integer.parseInt(textPartID.getText()), name, price, stock, min, max, machineId);
                } else {
                    partHolder = new InHouse(partID(), name, price, stock, min, max, machineId);
                }
            } else {
                if (updatePart) {
                    partHolder = new Outsourced(Integer.parseInt(textPartID.getText()), name, price, stock, min, max, companyName);
                } else {
                    partHolder = new Outsourced(partID(), name, price, stock, min, max, companyName);
                }
            }
        }
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

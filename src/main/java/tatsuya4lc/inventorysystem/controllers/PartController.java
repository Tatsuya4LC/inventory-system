package tatsuya4lc.inventorysystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tatsuya4lc.inventorysystem.MainApplication;
import tatsuya4lc.inventorysystem.models.InHouse;
import tatsuya4lc.inventorysystem.models.Inventory;
import tatsuya4lc.inventorysystem.models.Outsourced;
import tatsuya4lc.inventorysystem.models.Part;

import java.util.Objects;

/**
 * Controller for the Part window.
 * This class provides logic for the PartView.fxml
 * <p>
 *     RUNTIME ERROR
 *     <br>
 *     placePart()
 * <p>
 *     LOGICAL ERROR
 *     <br>
 *     place()
 * <p>
 *     check comment inside the code, above the mentioned methods
 *
 * @author Tristan Lozano
 */
public class PartController {
    //class variable to hold Part object
    private Part partHolder;
    //class variable initializes as boolean with false value
    private boolean updatePart = false;
    //class variable to hold an integer
    private int partToModifyIndex;

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

    /**
     * method for In-House radio button in Part window.
     * changes label and text prompt when selected
     */
    @FXML
    void optionInHouse() {
        labelPartInOut.setText("Machine ID");
        textPartInOut.setPromptText("Enter Machine ID");
    }

    /**
     * method for OutSourced radio button in Part window.
     * changes label and text prompt when selected
     */
    @FXML
    void optionOutSourced() {
        labelPartInOut.setText("Company Name");
        textPartInOut.setPromptText("Enter Company Name");
    }

    /**
     * method for save button in Part window.
     * calls for placePart() method
     * checks if placePart() is true
     * checks if adding or modifying
     * adds or updates Part to the observable list allParts
     * calls for a method from the MainApplication to go back to the main window
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onPartSave(ActionEvent event) {
        if (placePart()) {
            if (updatePart) {
                Inventory.updatePart(partToModifyIndex, partHolder);
            } else {
                Inventory.addPart(partHolder);
            }
            MainApplication.changeMenu(event, 1, 4, null, null);
        }
    }

    /**
     * method for cancel button in Part window.
     * calls for a method from the MainApplication to go back to the main window
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onPartCancel(ActionEvent event) {
        MainApplication.changeMenu(event, 1, 4, null, null);
    }

    /**
     * RUNTIME ERROR
     * an error occurs because the invocation target is not a number format
     * when user inputs anything that's not in a number format parseInt() method cannot execute
     * to prevent this the code is placed inside a try-catch block
     * when a user input anything but number format, it is thrown inside the catch block
     * <p>
     * LOGICAL ERROR
     * Stock cannot be outside the Minimum and Maximum range<br>
     * Minimum cannot be greater than Maximum<br>
     * to prevent this there are 2 if statements
     * first statement checks if Minimum > Maximum
     * second statement checks if Stock < Minimum or Stock > Maximum
     * when the checks are true user is presented with an error dialog
     * <p>
     * method to getText() from the text fields in the Part window.
     * checks for logical errors such as no input in the text field/s,
     * stock is outside min/max range and min is greater than max
     * try-catch block to test for number format to prevent runtime error
     * throws NumberFormatException and changes error to true
     * changes PromptText in the text fields to inform user of error
     * gives an error dialog and informs that there was an error if error variable changes to true
     * checks if Part object is of InHouse or OutSourced class
     * checks if adding or modifying Part when error is false
     * then creates a new Part object assigned to partHolder using the attributes from text fields and partID()
     *
     * @return boolean
     */
    private boolean placePart() {
        boolean error = false;
        String name = textPartName.getText();
        String companyName = textPartInOut.getText();
        double price = 0.0;
        int stock = 0, max = 0, min = 0, machineId =0;

        if (Objects.equals(name, "")) {
            error = true;
            textPartName.setPromptText("! cannot be empty");
        }

        try {
            price = Double.parseDouble(textPartPrice.getText());
        } catch (NumberFormatException e) {
            error = true;
            textPartPrice.setPromptText("! Expects a number");
            textPartPrice.clear();
        }

        try {
            stock = Integer.parseInt(textPartStock.getText());
        } catch (NumberFormatException e) {
            error = true;
            textPartStock.setPromptText("! Expects a number");
            textPartStock.clear();
        }

        try {
            max = Integer.parseInt(textPartMax.getText());
        } catch (NumberFormatException e) {
            error = true;
            textPartMax.setPromptText("! Expects a number");
            textPartMax.clear();
        }

        try {
            min = Integer.parseInt(textPartMin.getText());
        } catch (NumberFormatException e) {
            error = true;
            textPartMin.setPromptText("! Expects a number");
            textPartMin.clear();
        }

        try {
            machineId = Integer.parseInt(textPartInOut.getText());
        } catch (NumberFormatException e) {
            error = true;
            textPartInOut.setPromptText("! Expects a number");
            textPartInOut.clear();
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
                alert.setHeaderText("null");

                if (stock < min) {
                    alert.setContentText("Stock is out of range \nStock < Minimum");
                } else {
                    alert.setContentText("Stock is out of range \nStock > Maximum");
                }

                alert.showAndWait();
                return false;
            }

            if (inHouse.isSelected()) {
                if (updatePart) {
                    partHolder = new InHouse(Integer.parseInt(textPartID.getText()), name, price, stock, min, max, machineId);
                } else {
                    partHolder = new InHouse(MainApplication.generateID(0), name, price, stock, min, max, machineId);
                }
            } else {
                if (updatePart) {
                    partHolder = new Outsourced(Integer.parseInt(textPartID.getText()), name, price, stock, min, max, companyName);
                } else {
                    partHolder = new Outsourced(MainApplication.generateID(0), name, price, stock, min, max, companyName);
                }
            }
        }

        return true;
    }

    /**
     * method for modify button to call from the main window to send value.
     * checks if Part object is of InHouse or OutSourced and display window appropriately
     *
     * @param index the index to send from MainController
     * @param part the Part object to send from the MainController
     */
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

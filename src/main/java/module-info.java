module tasuya4lc.inventorysystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens tatsuya4lc.inventorysystem to javafx.fxml;
    exports tatsuya4lc.inventorysystem;
    exports tatsuya4lc.inventorysystem.controllers;
    opens tatsuya4lc.inventorysystem.controllers to javafx.fxml;
    exports tatsuya4lc.inventorysystem.models;
    opens tatsuya4lc.inventorysystem.models to javafx.fxml;
}
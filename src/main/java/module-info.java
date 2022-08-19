module tasuya4lc.inventorysystem {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens tasuya4lc.inventorysystem to javafx.fxml;
    exports tasuya4lc.inventorysystem;
}
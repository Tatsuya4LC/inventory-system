package tatsuya4lc.inventorysystem.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> foundParts = FXCollections.observableArrayList();
    private static ObservableList<Product> foundProducts = FXCollections.observableArrayList();
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        for(Part part : getAllParts()) {
            if(part.getId() == partId) {
                return part;
            }
        } return null;
    }

    public static Product lookupProduct(int productId) {
        for(Product product : getAllProducts()) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        foundParts.clear();

        for(Part part : getAllParts()) {
            if(part.getName().contains(partName)) {
                foundParts.add(part);
            }
        }
            return foundParts;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        foundProducts.clear();

        for(Product product : getAllProducts()) {
            if(product.getName().contains(productName)) {
                foundProducts.add(product);
            }
        } return foundProducts;
    }

    public static void updatePart(int index, Part selectedPart) {
        getAllParts().set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        getAllProducts().set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {
       getAllParts().remove(selectedPart);
       return true;
    }

    public static boolean deleteProduct(Product selectedProduct) {
        getAllProducts().remove(selectedProduct);
        return true;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static  ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

package tatsuya4lc.inventorysystem.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        for(Part part : allParts) {
            if(part.getId() == partId) {
                return part;
            }
        }
        return null;
    }
    public static Product lookupProduct(int productId) {
        for(Product product : allProducts) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        for(Part part : allParts) {
            if(part.getName().contains(partName)) {
                return (ObservableList<Part>) part;
            }
        }
        return null;
    }
    public static ObservableList<Product> lookupProduct(String productName) {
        for(Product product : allProducts) {
            if(product.getName().contains(productName)) {
                return (ObservableList<Product>) product;
            }
        }
        return null;
    }

    public static void updatePart(int index, Part selectedPart) {
        getAllParts().set(index, selectedPart);
    }
    public static void updateProduct(int index, Product newProduct) {
        getAllProducts().set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {
       return getAllParts().remove(selectedPart);
    }
    public static boolean deleteProduct(Product selectedProduct) {
        return getAllProducts().remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static  ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

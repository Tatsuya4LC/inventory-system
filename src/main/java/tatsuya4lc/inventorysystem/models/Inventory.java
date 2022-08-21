package tatsuya4lc.inventorysystem.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is an inventory that holds all parts and products.
 * It contains methods that interacts with the observable list allParts and allProducts
 *
 * @author Tristan Lozano
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * method to adding Part object to the observable list allParts
     *
     * @param newPart the object to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * method to adding Product object to the observable list allProducts
     *
     * @param newProduct the object to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * method to search for matching attribute of a Part object
     * matched against observable list allParts
     *
     * @param partId the attribute to search
     * @return the matched object or null
     */
    public static Part lookupPart(int partId) {
        for(Part part : allParts) {
            if(part.getId() == partId) {
                return part;
            }
        } return null;
    }

    /**
     * method to search for matching attribute of a Product object
     * matched against observable list allProducts
     *
     * @param productId the attribute to search
     * @return the matched object or null
     */
    public static Product lookupProduct(int productId) {
        for(Product product : allProducts) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * method to search for matching String object
     * matched against observable list allParts
     *
     * @param partName the String to search
     * @return an observable list containing Part object/s with the matched String
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        foundParts.clear();

        for(Part part : allParts) {
            if(part.getName().contains(partName)) {
                foundParts.add(part);
            }
        }
            return foundParts;
    }

    /**
     * method to search for matching String object
     * match against observable list allProducts
     *
     * @param productName the String to search
     * @return an observable list containing Product object/s with the matched String
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        foundProducts.clear();

        for(Product product : allProducts) {
            if(product.getName().contains(productName)) {
                foundProducts.add(product);
            }
        } return foundProducts;
    }

    /**
     * method for updating Part object in the observable list allParts
     *
     * @param index the item index to update
     * @param selectedPart the Part object to update
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * method for updating Product object in the observable list allProducts
     *
     * @param index the item index to update
     * @param newProduct the Product object to update
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * method for deleting Part object from the observable list allParts
     *
     * @param selectedPart the Part object to delete
     * @return boolean
     */
    public static boolean deletePart(Part selectedPart) {
       return allParts.remove(selectedPart);
    }

    /**
     * method for deleting Product object from the observable list allProducts
     *
     * @param selectedProduct the Product object to delete
     * @return boolean
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * method to access observable list allParts
     * @return observable list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * method to access observable list allProducts
     * @return observable list
     */
    public static  ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

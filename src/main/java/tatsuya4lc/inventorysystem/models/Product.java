package tatsuya4lc.inventorysystem.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is for Product objects.
 * This contains the attributes for Product objects.
 * It also declares an observable list to associate any part/s object required by the product objects
 *
 * @author Tristan Lozano
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * A method for adding an object to the observable list
     * @param part the part object to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * A method for removing an object from the observable list
     * @param selectedAssociatedPart the part object to remove from the observable list
     * @return a boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * A method for getting all associated part object to the product object
     * @return associatedParts an observable list
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     *
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     *
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     *
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
}

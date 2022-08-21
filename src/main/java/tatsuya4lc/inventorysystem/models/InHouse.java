package tatsuya4lc.inventorysystem.models;

/**
 * The class for InHouse objects.
 * This class contains an attribute machineId for InHouse objects
 *
 * @author Tristan Lozano
 */
public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     *
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}

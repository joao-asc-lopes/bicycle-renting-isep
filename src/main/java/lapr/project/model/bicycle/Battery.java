package lapr.project.model.bicycle;

import java.util.Objects;

public class Battery {

    /**
     * The ID of the battery
     */
    private int id;
    /**
     * The maximum battery charge of the battery
     */
    private double maxCharge;
    /**
     * The current battery charge of the battery
     */
    private double currentCharge;
    /**
     * Variable that represents the weight of the battery.
     */
    private float weight;

    /**
     * Variable that represents the average voltage of a battery
     */
    private static final double VOLTAGE = 2;

    /**
     * Creates a new instance of the class Battery
     *
     * @param id            battery's id
     * @param maxCharge     battery's max charge
     * @param currentCharge battery's current charge
     * @param weight        weight of the battery
     */
    public Battery(int id, long  maxCharge, long currentCharge, float weight) {
        this.setId(id);
        this.maxCharge = maxCharge;
        this.setWeight(weight);
        this.setCurrentCharge(currentCharge);
    }


    /**
     * Method that creates a Battery without an ID to validate the data inserted and to be used to add
     * this battery to the DB
     * @param maxCharge Max Charge of the battery
     * @param currentCharge Current Charge of the battery
     * @param weight Weight of the battery
     */
    public Battery(long maxCharge, long currentCharge, float weight){
        this.setMaxCharge(maxCharge);
        this.setCurrentCharge(currentCharge);
        this.setWeight(weight);
    }


    /**
     * Returns the maximum battery charge of the Battery
     */
    public double getMaxCharge() {
        return maxCharge;
    }

    /**
     * Updates the value of the maximum battery charge of the battery
     *
     * @param maxCharge
     */
    public void setMaxCharge(long maxCharge) {
        if(maxCharge<=0){
            throw new IllegalArgumentException("Max Charge of a Battery can´t be 0 or negative");
        }
        this.maxCharge = maxCharge;
    }

    /**
     * Returns the current battery charge of the battery
     */
    public double getCurrentCharge() {
        return currentCharge;
    }

    /**
     * Updates the value of the current battery charge of the battery
     *
     * @param currentCharge
     */
    public void setCurrentCharge(long currentCharge) {
        if(currentCharge<0 || currentCharge>maxCharge){
            throw new IllegalArgumentException("Current Charge of a Battery can´t be negative or bigger than Max Charge");
        }
        this.currentCharge = currentCharge;
    }


    /**
     * Returns the ID of the battery
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the ID value of the battery
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Verifies if two batteries are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Battery battery = (Battery) o;
        return id == battery.id;
    }

    /**
     * Calculates and returns the Hash Code of the battery
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * The battery's weight.
     */
    public float getWeight() {
        return weight;
    }

    /**
     * This method allows the user to alter the weight of the bicycle with the new weight that he inputted.
     * @param weight
     */
    public void setWeight(float weight) {
        if(weight<0){
            throw new IllegalArgumentException("Weight of a battery can't be negative");
        }
        this.weight = weight;
    }

    /**
     * This method calculates the power of the battery of the bicycle.
     * @return The power of the battery of the bicycle . P = U * I .
     */
    public double getPowerBattery(){

        return this.maxCharge*VOLTAGE;
    }

    /**
     * Returns the voltage value
     */
    public double getVoltage(){
        return VOLTAGE;
    }
}

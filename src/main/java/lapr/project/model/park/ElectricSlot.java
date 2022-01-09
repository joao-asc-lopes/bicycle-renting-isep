package lapr.project.model.park;

import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.utils.InvalidDataException;

import java.util.Objects;


public class ElectricSlot implements Slot {

    /**
     * Variable that is representative of the slot Id.
     */
    private int slotId;
    /**
     * Variable that is representative of the maximum capacity.
     */

    private int maximumCapacity;

    /**
     * Variable that is representative of the number of free slots.
     */
    private int numberFreeSlots;

    private double chargeRate;
    private double intensity;

    /**
     * Constructor of an instance of electrical slots with all the atributes passed as parameter representative of its attributes.
     *
     * @param slotId          The id slot of slot.
     * @param maximumCapacity The maximum capacity of the instance of electrical slot.
     * @param numberFreeSlots The number of free slots of electrical slot.
     */

    public ElectricSlot(int slotId, int maximumCapacity, int numberFreeSlots, double chargeRate, double intensity) {
        this.slotId = slotId;
        this.setData(maximumCapacity, numberFreeSlots, chargeRate, intensity);
    }

    /**
     * Method that validates and updates the data passed as parameter in the constructor
     * @param maximumCapacity
     * @param numberFreeSlots
     */
    public void setData(int maximumCapacity, int numberFreeSlots,double chargeRate, double intensity){
        if(maximumCapacity <= 0){
            throw new InvalidDataException("Maximum capacity must be bigger than 0");
        }
        if(numberFreeSlots < 0){
            throw new InvalidDataException("Number of free slots must not be negative");
        }
        if(numberFreeSlots > maximumCapacity){
            throw new InvalidDataException("Number of free slots must not be bigger than maximum capacity");
        }
        if(chargeRate < 0){
            throw new InvalidDataException("Charge rate must be bigger than 0");
        }
        if(intensity <= 0){
            throw new InvalidDataException("Intensity must be bigger than 0");
        }
        this.maximumCapacity = maximumCapacity;
        this.numberFreeSlots = numberFreeSlots;
        this.chargeRate = chargeRate;
        this.intensity = intensity;
    }

    /**
     * Returns the slot If of this electrical slot.
     *
     * @return The slot id of this electrical slot.
     */

    public int getSlotId() {
        return slotId;
    }

    /**
     * Returns the maximum capacity of this electrical slot.
     *
     * @return The maximum capacity of this electrical slot.
     */

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    /**
     * Returns the ammount of free bikes of this electrical slot.
     * @return The amount of free bikes of this electrical slot.
     */


    /**
     * Returns the amount of free slots of this electrical slot.
     *
     * @return The amount of free slots of this electrical slot.
     */
    public int getNumberFreeSlots() {
        return numberFreeSlots;
    }

    /**
     * Variable that is representative of the charge rate. It is defined by the client as 220V
     */ /**
     * Method that returns the charge rate of the park.
     *
     * @return
     */
    public double getChargeRate() {
        return chargeRate;
    }

    /**
     * Equals of the Electrical Slots.
     *
     * @param o The other Object that is about to be compared with this instance of electrical slot.
     * @return True if both instances are the same, false if they different.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        ElectricSlot that = (ElectricSlot) o;
        return slotId == that.slotId;
    }

    /**
     * HashCode method for Electrical Slot.
     *
     * @return An int representative of the hashcode of this eletrical slot.
     */

    @Override
    public int hashCode() {
        return Objects.hash(slotId);
    }

    /**
     * This method returns the power associated with de bicycle slot. P = U*I
     *
     * @return A long representative of the power of the eletrical slot in a park. Should be always 3520 since both intensity and charge rate are constants.
     */
    public double getPower() {
        return getIntensity() * getChargeRate();
    }

    /**
     * This method  returns thea available charge per slot in the eletric slot.
     *
     * @param numberBikes The number of bikes that are charging at the moment.
     * @return The available power to charge
     */
    public double chargePerSlot(int numberBikes) {
        if (numberBikes <= 0) {
            return this.getPower(); // Since there are no bikes charging we should get the same power that the entire park has.
        }
        return this.getPower() / numberBikes;
    }

    /**
     * This is a method that allows the user to get the time that a bicycle is going to get charge in hours-
     *
     * @param eb The bicycle the user is checking.
     * @return A long representative of the time
     */

    public long calculateTimeToGetBatteryChargedInMinuts(ElectricBicycle eb, double chargePerSlot) {

        return Math.round((eb.getMissingCharge() / chargePerSlot) * 60);

    }

    /**
     * Returns the slot's intensity
     */
    public double getIntensity() {
        return intensity;
    }

}

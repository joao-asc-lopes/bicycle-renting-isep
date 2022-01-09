package lapr.project.model.park;

import lapr.project.utils.InvalidDataException;

import java.util.Objects;

public class NormalSlots implements Slot {

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

    /**
     * Constructor of Normal Slots with the attributes passed as a parameter.
     *
     * @param slotId          The slot id passed as a parameter.
     * @param maximumCapacity The maximum capacity passed as a parameter.
     * @param numberFreeSlots The number of free slots passed as a parameter.
     */

    public NormalSlots(int slotId, int maximumCapacity, int numberFreeSlots) {
        this.slotId = slotId;
        this.setData(maximumCapacity, numberFreeSlots);
    }

    /**
     * Method that validates and updates the data passed as parameter in the constructor
     * @param maximumCapacity
     * @param numberFreeSlots
     */
    public void setData(int maximumCapacity, int numberFreeSlots){
        if(maximumCapacity <= 0){
            throw new InvalidDataException("Maximum capacity must be bigger than 0");
        }
        if(numberFreeSlots < 0){
            throw new InvalidDataException("Number of free slots must not be negative");
        }
        if(numberFreeSlots > maximumCapacity){
            throw new InvalidDataException("Number of free slots must not be bigger than maximum capacity");
        }
        this.maximumCapacity = maximumCapacity;
        this.numberFreeSlots = numberFreeSlots;
    }

    /**
     * Returns the slot Id of this instance of Normal slot.
     *
     * @return The slot if of this instance of Normal slot.
     */
    public int getSlotId() {
        return slotId;
    }

    /**
     * Returns tha maximum capacity of this instance of normal slot.
     *
     * @return The maximum capacity of this instance of normal slot.
     */

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    /**
     * The amount of free bikes of this instance of normal slot.
     * @return the amounf of free bikes of this instance of normal slot.
     */


    /**
     * The amount of number of free slots in this instance of normal slot.
     *
     * @return The amount of number of free slots.
     */

    public int getNumberFreeSlots() {
        return numberFreeSlots;
    }

    /**
     * Equals of the Class Normal Slot.
     *
     * @param o The other object that is about to be comparable with the this instance of Park.
     * @return True of both the Normal Slots are equal, False if they are not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormalSlots that = (NormalSlots) o;
        return slotId == that.slotId;
    }

    /**
     * HashCode method of the Normal Slot.
     *
     * @return Returns an int that is indicative of the hascode of the instance of Normal Slot.
     */
    @Override
    public int hashCode() {
        return Objects.hash(slotId);
    }
}

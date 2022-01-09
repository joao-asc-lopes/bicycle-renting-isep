package lapr.project.model.park;

import lapr.project.model.bicycle.ElectricBicycle;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Park extends Location {

    /**
     * Representative of an instance of normal slot.
     */

    private NormalSlots normalSlots;
    /**
     * Representative of the slots of electrical bicycles of the park.
     */

    private ElectricSlot eletricalSlots;

    /**
     * Constructor of the class Park with the variables passed as a parameter.
     *
     * @param idLocation     The id park passed as a parameter.
     * @param name           The name of the park passed as a parameter.
     * @param normalSlots    The slot id of the park passed as a parameter.
     * @param eletricalSlots The electrical slot id of the park passed as a parameter.
     */

    public Park(int idLocation, String name, double latitude, double longitude, NormalSlots normalSlots, ElectricSlot eletricalSlots, double altitude) {
        super(idLocation, name, latitude, longitude, altitude, LocationType.PARK);
        this.normalSlots = normalSlots;
        this.eletricalSlots = eletricalSlots;
    }

    /**
     * Constructor of the class Park without ID that validates the data inserted and to be used
     * when adding a Park to the DB
     * @param name Name of the Park
     * @param latitude  Latitude of the Park
     * @param longitude Longitude of the Park
     * @param normalSlots   NormalSlots of the Park
     * @param electricSlots ElectricSlots of the Park
     * @param altitude  Altitude of the park
     */
    public Park(String name, double latitude, double longitude, NormalSlots normalSlots, ElectricSlot electricSlots, double altitude){
        super(name,latitude,longitude,altitude,LocationType.PARK);
        this.setNormalSlots(normalSlots);
        this.setEletricalSlots(electricSlots);
    }


    /**
     * This method returns the attribute Slot of the instance of Park.
     *
     * @return the attribute Slot of the instance of Park.
     */

    public NormalSlots getNormalSlots() {
        return normalSlots;
    }

    /**
     * This method returns the attribute slot of the electrical bicycles of the Park.
     *
     * @return The attribute Slot of the electrical bicycles of this instance of Park.
     */

    public ElectricSlot getEletricalSlots() {
        return eletricalSlots;
    }


    /**
     * Allows the user to alter its Slot with the new one passed as a parameter.
     *
     * @param normalSlots the other slot passed as a parameter.
     */

    public void setNormalSlots(NormalSlots normalSlots) {
        if(normalSlots==null){
            throw new IllegalArgumentException("Normal Slots can't be null");
        }
        this.normalSlots = normalSlots;
    }


    /**
     * Allows the user to alter its electrical Slot with the new one passed as a paramter.
     *
     * @param eletricalSlots The other slot passed as a parameter.
     */

    public void setEletricalSlots(ElectricSlot eletricalSlots) {
        if(eletricalSlots==null){
            throw new IllegalArgumentException("Electric Slots can't be null");
        }
        this.eletricalSlots = eletricalSlots;
    }

    /**
     * Returns an ArrayList of Strings.
     *
     * @param electricList The eletric Bicycle list passed as parameter.
     * @return ArrayList that is representative of the park report.
     */
    public ArrayList<String> showInfoOfPark(List<ElectricBicycle> electricList) {
        ArrayList<String> result = new ArrayList<>();
        double chargePerSlot = this.eletricalSlots.chargePerSlot(electricList.size());
        for (ElectricBicycle eb : electricList) {
            if (eb.getBattery().getPowerBattery() > this.eletricalSlots.getPower()) {
                String reportBike = "The Bike with the id :" + eb.getId() + " cannot be charged since the power of its battery is greater than what the slot offers!";
                result.add(reportBike);
            } else {
                String reportBike = "The bicycle is charged " + eb.percentageBattery() + "%, and to complete the charge we need is : " + LocalTime.MIN.plus(Duration.ofMinutes(this.eletricalSlots.calculateTimeToGetBatteryChargedInMinuts(eb, chargePerSlot))) + " hh/mm";
                result.add(reportBike);
            }
        }
        return result;
    }

    /**
     * Equals method.
     * @param o The other instance passed as a parameter.
     * @return True if both objects are the same, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Park park = (Park) o;
        return this.getIdLocation() == park.getIdLocation();
    }

    /**
     * Hashcode method.
     * @return An integer representative of the hash code of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getIdLocation());
    }
}


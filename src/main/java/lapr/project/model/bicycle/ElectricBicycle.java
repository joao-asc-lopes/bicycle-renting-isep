package lapr.project.model.bicycle;


public class ElectricBicycle extends Bicycle {
    /**
     * The Battery of the ElectricBicycle
     */
    private Battery battery;

    /**
     * Creates an instance of the class ElectricBicycle
     */
    public ElectricBicycle(String idBike, BicycleStatus status, Battery bat, float weight, double coefficient, double frontalArea) {
        super(idBike, status, BicycleType.ELECTRIC, weight,coefficient, frontalArea);
        this.setBattery(bat);
    }


    /**
     * Allows the user to get the battery of this instance of eletric bicycle.
     * @return This instance of eletric Bicycle.
     */
    public Battery getBattery() {
        return battery;
    }

    /**
     * Allows the user to set a new battery for the eletric bicycle.
     * @param battery The new battery of the eletric bicycle.
     */
    public void setBattery(Battery battery) {
        if(battery==null){
            throw new IllegalArgumentException("The battery can't be null when creating an Electric Bicycle");
        }
        this.battery = battery;
    }


    /**
     * Method that tells the percentage that a battery of an eletric bicycle is charged.
     * @return A double representative of the percentage of the bicycles rounded to 2 decimal houses.
     */
    public double percentageBattery (){
        double percentage =  this.battery.getCurrentCharge() / this.getBattery().getMaxCharge();

        return percentage*100;
    }

    /**
     * Method that returns the missing charge of an eletrical bicycle.
     * @return The missing charge of a bicycles battery.
     */
    public double getMissingCharge(){
        return this.battery.getMaxCharge() - this.battery.getCurrentCharge();
    }


}

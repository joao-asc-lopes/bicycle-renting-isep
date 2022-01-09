package lapr.project.controller.admin;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.BicycleFacade;

public class CreateBicycleController {
    /**
     * Variable that represents an instance of Bicycle Facade.
     */
    private BicycleFacade fc;
    

    /**
     * Constructor of create bicycle.
     */

    public CreateBicycleController() {
        this.fc = new BicycleFacade();
    }

    /**
     * Creates a new Electric bike and saves it in the DB.
     *
     * @param status               The bike's status
     * @param weight               The bike's weight
     * @param batteryMaxCharge     The bike's battery max charge value.
     * @param batteryCurrentCharge The bike's battery current charge.
     * @return true if the creation was successful, false if not.
     */
    public boolean createElectricBicycle(String id,Bicycle.BicycleStatus status, float weight, int batteryMaxCharge, int batteryCurrentCharge,int batteryWeight, double coefficient,double frontalArea) {
        return fc.createElectricBicycle(id, status, weight, batteryMaxCharge, batteryCurrentCharge,batteryWeight,coefficient, frontalArea);
    }

    /**
     * Creates a new Road Bicycle and saves it in the DB.
     *
     * @param status The bike's status.
     * @param weight The bike's weight.
     * @return true if the creation was successful, false if not.
     */
    public boolean createRoadBicycle(String id,Bicycle.BicycleStatus status, float weight, double coefficient,double frontalArea) {
        return this.fc.createRoadBicycle(id, status, weight, coefficient,frontalArea);
    }

    /**
     * Creates a new Mountain Bicycle and saves it in the DB.
     *
     * @param status The bike's status.
     * @param weight The bike's weight.
     * @return true if the registry was successful, false if not.
     */
    public boolean createMountainBicycle(String id, Bicycle.BicycleStatus status, float weight, double coefficient,double frontalArea) {
        return this.fc.createMountainBicycle(id, status, weight, coefficient,frontalArea);
    }




}

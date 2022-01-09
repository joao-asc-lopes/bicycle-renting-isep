package lapr.project.controller.admin;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.BicycleFacade;

import java.util.List;

public class UpdateBicycleController {
    /**
     * Instance of BicycleNetwork
     */
    private BicycleFacade bf;

    /**
     * Constructor of UpdateBicycleController
     */
    public UpdateBicycleController() {
        this.bf = new BicycleFacade();
    }

    /**
     * Method that returns all MountainBicycles from the Database
     */
    public List<Bicycle> getAllBicycles() {
        return bf.getAllBicycles();
    }

    /**
     * Method that updates a MountainBicycle
     */
    public boolean updateBicycle(String id_bike, Bicycle.BicycleStatus status, float weight, double coefficient) {
        return bf.updateNonElectricalBicycle(id_bike, status, weight, coefficient);
    }

    /**
     * Method that allows the update of an electric bicycle.
     * @param id_bike The id of the bike being updated.
     * @param status The status of the bike being updated.
     * @param weight The weight of the bike being updated.
     * @param id_bat The id of the battery of the bike being updated.
     * @return True if it was updated successfully , false if not.
     */

    public boolean updateElectricBicycle(String id_bike, Bicycle.BicycleStatus status,float weight,int id_bat, double coefficient){
        return bf.updateElectricBicycle(id_bike, status, weight,id_bat, coefficient);
    }
}

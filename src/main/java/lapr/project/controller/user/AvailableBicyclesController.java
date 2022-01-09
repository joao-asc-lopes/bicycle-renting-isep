/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lapr.project.controller.user;

import lapr.project.model.bicycle.*;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;

import java.util.List;


public class AvailableBicyclesController {
    /**
     * Variable that represents a variable of LocationFacade.
     */
    private LocationFacade fc;
    private BicycleFacade bf;


    /**
     * Constructor that opens up a connection to the private variable LocationFacade.
     */

    public AvailableBicyclesController() {

        this.fc = new LocationFacade();
        this.bf = new BicycleFacade();

    }

    /**
     * Method that returns the list of parks in the registry
     *
     * @return List of parks
     */
    public List<Park> getParkRegistry() {
        return this.fc.getParkList();
    }

    /**
     * Method that allows the user to get all the mountain bicycle in a specific park.
     * @param id The id of the specific park passed as a parameter.
     * @return A list with all the mountain bicycles of the said park.
     */
    public List<MountainBicycle> getMountainBicyclesInPark(int id){
        return this.fc.getMountainBicyclesPark(id);
    }

    /**
     * Method that allows the user to get all the road bicycles of a specific park.
     * @param id The id of the specific park passed as a parameter.
     * @return A list of all the road bicycles.
     */

    public List<RoadBicycle> getRoadBicyclesInPark(int id){
        return this.fc.getRoadBicyclesPark(id);
    }

    /**
     * Method that allows the user to get all the electric bicycles of a specific park.
     * @param id The if of the specific park passed as a parameter.
     * @return A list with all the electrical parks.
     */

    public List<ElectricBicycle> getElectricBicycleInPark(int id){
        return this.fc.getElectricBicyclesPark(id);
    }

    /**
     * Method that returns the Bicycles in the park received
     *
     * @param id ID of the park
     * @return List of Bicycles
     */
    public List<Bicycle> getBicyclesInPark(int id) {
        return this.fc.getParkedBicycles(id);
    }

    /**
     * Returns all the bicycles.
     * @return All the bicycles.
     */
    public List<Bicycle> getAllBicycles(){
        return this.bf.getAllBicycles();
    }
}

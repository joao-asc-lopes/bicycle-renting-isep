package lapr.project.controller.admin;

import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;

import java.util.List;

public class UpdateParkController {

    /**
     * Variable that represents an instance of parkFacade.
     */
    private LocationFacade pf;

    /**
     * Represents a constructor of updateParkController that opens up the private instance of LocationFacade.
     */

    public UpdateParkController() {
        this.pf = new LocationFacade();
    }


    /**
     * Returns a list with all parks
     */
    public List<Park> getParkList(){
        return pf.getParkList();
    }


    /**
     * Updates a park's data
     * @param idPark                        The id of the park to be updated
     * @param name                          The new park's name
     * @param normalBicycleCapacity             The new park's max capacity for mountain and road bicycles
     * @param numberFreeNormalSlots         The new park's number of free slots for mountain and road bicycles
     * @param electricBicycleCapacity         The new park's max capacity for electrical bicycles
     * @param numberFreeElectricalSlots     The new park's number of free slots for electrical bicycles
     */
    public boolean updatePark(int idPark, String name, int normalBicycleCapacity , int numberFreeNormalSlots, int electricBicycleCapacity, int numberFreeElectricalSlots){

        return pf.updatePark(idPark, name, normalBicycleCapacity, numberFreeNormalSlots, electricBicycleCapacity, numberFreeElectricalSlots);
    }
}

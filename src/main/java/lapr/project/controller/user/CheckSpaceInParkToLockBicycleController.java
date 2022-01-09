package lapr.project.controller.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;

import java.util.List;

public class CheckSpaceInParkToLockBicycleController {
    /**
     * Variable that represents the type of the bicycle.
     */

    private Bicycle.BicycleType type;
    /**
     * Variable that represents a private instance of Park Facade.
     */

    private LocationFacade bn;

    /**
     * Constructor that instanciates the private variable of the class LocationFacade.
     */

    public CheckSpaceInParkToLockBicycleController() {
        this.bn = new LocationFacade();
    }

    /**
     *  Allows the user to choose a certain type of bicycle that is passed as a parameter.
     * @param choice The choice the user chose given as a parameter.
     */

    public void printAllBicycleTypes(Bicycle.BicycleType choice) {
        this.type = choice;
    }

    /**
     *  Method that returns a list with all the parks that are in the DataBase.
     * @return Returns a list with all the parks that are in the database.
     */

    public List<Park> getAllParksList() {
        return bn.getParkList();
    }

    /**
     * Returns a location that is in the Database given its id as a parameter.
     * @param park_id id of the park given as a parameter.
     * @return Returns a location with its id the same as the id given as a parameter.
     */

    public Park getParkById(int park_id) {
        return bn.getParkById(park_id);
    }

    /**
     * Gets all the bicycle of a certain park given as a parameter and a type also given as a parameter.
     * @param park The park given as a parameter.
     * @return The number of slots depending on the type given as a parameter.
     */

    public int getSlotsByParkAndBicycleType(Park park) {


        if (type.toString().equals("Road") || (type.toString().equals("Mountain"))) {
            return park.getNormalSlots().getNumberFreeSlots();
        } else { // Else it is an eletric bicycle.
            return park.getEletricalSlots().getNumberFreeSlots();
        }

    }
}

package lapr.project.controller.user;

import lapr.project.model.park.Location;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;
import lapr.project.utils.PhysicsAlgorithms;

import java.util.List;

public class CalculateDistanceParksController {
    /**
     * Variable that represents the bicycle Network
     */
    private LocationFacade pf;

    /**
     * Constructor of the controller that simply opens the bicycle network and the Park Dao.
     */
    public CalculateDistanceParksController() {
        this.pf = new LocationFacade();
    }

    /**
     * Gets all the parks from the database.
     */
    public List<Park> getParkRegistry() {
        return this.pf.getParkList();
    }

    /**
     * Get the Park by is ID
     *
     * @param id ID of the park
     * @return Park with the same ID as received
     */
    public Location getLocationById(int id) {
        try {
            return this.pf.getParkById(id);
        } catch (InvalidDataException e) {
            return null;
        }
    }

    /**
     * Method that calculates the distance from a user to a chosen park.
     *
     * @param userLatitude  The latitude of the user.
     * @param userLongitude The longitude of the user.
     * @param p             The park that the user is checking the distance to.
     * @return A double representative of the distance.
     */
    public double calculateDistanceParkUser(double userLatitude, double userLongitude, Park p) {
        try {
            return PhysicsAlgorithms.distance(userLatitude, userLongitude, p.getLatitude(), p.getLongitude());
        } catch (InvalidDataException e) {
            return -1;
        }
    }


}

package lapr.project.controller.admin;

import lapr.project.model.park.InterestPoint;
import lapr.project.model.park.LocationFacade;

public class CreateInterestPointController {
    /**
     * Variable that represents the bicycleNetwork.
     */
    private LocationFacade pf;

    /**
     * Constructor of an isntance of Create Interest Point Controller.
     */
    public CreateInterestPointController() {
        this.pf = new LocationFacade();
    }

    /**
     * Adds an Interest Point to the DataBase.
     *
     * @param name       The name of the Interest point about to be registered.
     * @param latitude   The latitude of the Interest Point about to be registered.
     * @param longitude  The longitude of the Interest Point about to be registered.
     * @return True if it added the Interest Point, false if not.
     */

    public InterestPoint addInterestPoint(String name, double latitude, double longitude, double altitude) {
        return pf.registerInterestPoint(name,latitude,longitude,altitude);
    }
}

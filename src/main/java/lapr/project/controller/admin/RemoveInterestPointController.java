package lapr.project.controller.admin;

import lapr.project.model.park.LocationFacade;

public class RemoveInterestPointController {
    /**
     * Variable that represents the bicycle Network.
     */
    private LocationFacade pf;

    /**
     * Constructor that builds an instance of the bicycle Network.
     */
    public RemoveInterestPointController() {
        this.pf = new LocationFacade();
    }

    /**
     * Method that allows the user to remove an Interest Point with its id given as a parameter.
     *
     * @param idLocation The identifier of the location we want to remove.
     * @return True if the Interest Point was removed, false if not.
     */
    public boolean removeInterestPoint(int idLocation) {
        return this.pf.removeInterestPoint(idLocation);

    }
}

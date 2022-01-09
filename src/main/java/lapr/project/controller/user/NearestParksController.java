package lapr.project.controller.user;


import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;

import java.util.ArrayList;
import java.util.List;


public class NearestParksController {
    /**
     * method that represents the private variable bicycle Network.
     */
    private BicycleNetwork bn;
    /**
     * Method that represents the private variable LocationFacade.
     */

    private LocationFacade pf;

    /**
     * Method that opens up the controller.
     */
    public NearestParksController() {
        this.bn = new BicycleNetwork();
        this.pf = new LocationFacade();
    //    this.bn.loadData();
    }

    /**
     * Updates all the parks that are used by the DB.
     *
     * @return
     */
    public List<Park> getParkRegistry() {
        return this.pf.getParkList();
    }

    /**
     * This method returns the 5 closest parks from a user given its decimal coordinates.
     *
     * @param latitude  The latitude passed as a parameter.
     * @param longitude The longitude passed as a parameter.
     * @return An arrayList of Parks that contains the 5 closest parks from a user.
     */
    public ArrayList<Park> getClosestFiveParksFromUser(double latitude, double longitude) {
        return this.bn.shortestParksFromUser(latitude, longitude);

    }
}

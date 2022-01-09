package lapr.project.controller.user;

import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.bikenetwork.Route;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;

import java.util.List;

public class ShortestPathParkToParkController {
    /**
     * Variable that represents the location Facade.
     */
    private LocationFacade lf;
    /**
     * Variable that represents the bicycle Network.
     */

    private BicycleNetwork bn;

    /**
     * Constructor of the shortest Path park to park.
     */

    public ShortestPathParkToParkController() {
        this.bn = new BicycleNetwork();
        this.lf = new LocationFacade();
    }

    /**
     * Method that allows the user to get a list of all the parks.
     *
     * @return A list of all the parks.
     */

    public List<Park> getParkList() {

        return this.lf.getParkList();

    }

    /**
     * Method that allows the user to get the shortest path regarding the distance between 2 parks.
     *
     * @param initialPark The first park.
     * @param finalPark   The final park.
     * @return Iterable containing the shortest path regarding the distance between the 2 parks.
     */
    public List<Route> shortestPathParkToPark(Park initialPark, Park finalPark) {
        return this.bn.shortestPathByDistance(initialPark, finalPark);
    }
}

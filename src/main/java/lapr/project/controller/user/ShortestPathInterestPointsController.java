package lapr.project.controller.user;

import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.bikenetwork.Route;
import lapr.project.model.park.InterestPoint;
import lapr.project.model.park.Location;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.util.List;

public class ShortestPathInterestPointsController {
    /**
     * Variable that represents the Location Facade.
     */
    private LocationFacade lf;
    /**
     * Variable that represents the Bicycle Network.
     */

    private BicycleNetwork bn;

    /**
     * Constructor of this controller.
     */

    public ShortestPathInterestPointsController() {
        this.lf = new LocationFacade();
        this.bn = new BicycleNetwork();
        //  this.bn.loadData();
    }

    /**
     * Method that allows the user to get a list of parks.
     *
     * @return A list of parks.
     */

    public List<Park> getParkList() {
        return this.lf.getParkList();

    }

    /**
     * Method that allos the user to get a list of interest points.
     *
     * @return A list of interest points.
     */
    public List<InterestPoint> getInterestPointList() {
        return this.lf.getAllInterestPoints();
    }

    /**
     * Method that allows the user to get the shortest path between 2 parks.
     *
     * @param initialPark       The starting park.
     * @param finalPark         The final park.
     * @param interestPointList A list of interest points where the user wants to get through.
     * @return
     */

    public List<Route> getShortestPathIterable(Park initialPark, Park finalPark, List<Location> interestPointList) throws InvalidDataException {
        return this.bn.shortestPathByDistancePassingByInterestPoints(initialPark, finalPark, interestPointList);
    }
}

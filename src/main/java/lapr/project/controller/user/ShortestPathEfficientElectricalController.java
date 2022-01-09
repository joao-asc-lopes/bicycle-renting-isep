package lapr.project.controller.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.BicycleFacade;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.bikenetwork.Route;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;
import lapr.project.model.user.User;

import java.io.IOException;
import java.util.List;

public class ShortestPathEfficientElectricalController {
    /**
     * Variable that represents the location facade.
     */
    private LocationFacade lf;
    /**
     * Variable that represents the bicycle facade.
     */
    private BicycleFacade bf;

    /**
     * Variable that represents the bicycle network.
     */
    private BicycleNetwork bn;

    /**
     * Constructor of the controller that has the resposability of oppening up the connections.
     */

    public ShortestPathEfficientElectricalController() {
        this.bn = new BicycleNetwork();
        this.lf = new LocationFacade();
        this.bf = new BicycleFacade();
        // this.bn.loadData();

    }

    /**
     * Method that allows the user to return the list of parks.
     *
     * @return
     */
    public List<Park> getParkList() {
        return this.lf.getParkList();
    }

    /**
     * Method that allows the user to get all the electrical bicycles.
     *
     * @return All the electrical bicycles.
     */
    public List<ElectricBicycle> getElectricalBicycles() {
        return this.bf.getElectricBicyclesList();
    }

    /**
     * Method that allows the user to receive the shortest path taking in consideration the electrical.
     *
     * @param loggedUser    The currently logged user.
     * @param chosenBicycle The chosen electrical bicycle of the user.
     * @param firstPark     The starting park.
     * @param finalPark     The final park.
     * @return An iterable representative of the shortest path taking in consideration the electrical efficiency.
     * @throws IOException
     */
    public List<Route> shortestElectricalPath(User loggedUser, Bicycle chosenBicycle, Park firstPark, Park finalPark) {
        return this.bn.shortestPathByEnergeticEfficiency(loggedUser, chosenBicycle, firstPark, finalPark);
    }


}

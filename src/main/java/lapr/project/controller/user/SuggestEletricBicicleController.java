package lapr.project.controller.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.park.Park;
import lapr.project.model.user.User;

import java.io.IOException;

public class SuggestEletricBicicleController {

    /**
     *  Instance of BicycleNetwork
     */
    private BicycleNetwork bn;

    /**
     *  Instance of SessionController
     */
    private SessionController sc;

    /**
     *  Constructor of SuggestEletricBicicleController
     */
    public SuggestEletricBicicleController(){
        this.sc = new SessionController();
        this.bn = new BicycleNetwork();
    }

    /**
     * Method that allows a bike to be suggested for a simulated trip.
     * @param p1  The starting park of the simulated trip.
     * @param p2 The finishing park of the simulated trip.
     * @return The suggested bicycle.
     */
    public Bicycle suggestBicycle(Park p1, Park p2, User u) throws IOException {
        return this.bn.suggestBike(p1, p2, u);
    }
}

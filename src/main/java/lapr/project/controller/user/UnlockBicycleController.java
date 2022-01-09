package lapr.project.controller.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.BicycleFacade;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;
import lapr.project.model.user.User;
import lapr.project.model.user.UserFacade;
import lapr.project.utils.InvalidDataException;

import java.util.List;

public class UnlockBicycleController {
    /**
     * Instance of bicycle network in memory.
     */
    private BicycleFacade bicycleFacade;
    /**
     * Instance of Park Facade in memory.
     */
    private LocationFacade locationFacade;
    private SessionController currentSession;

    /**
     * Instance of User Facade in memory.
     */
    private UserFacade userFacade;
    /**
     * Type of bicycle chosen by the user.
     */
    private Bicycle.BicycleType chosenType;

    /**
     * Constructor of UnlockBicycleController that instanciates the connections.
     */

    public UnlockBicycleController() {
        this.bicycleFacade = new BicycleFacade();
        this.locationFacade = new LocationFacade();
        this.userFacade = new UserFacade();
        this.currentSession = new SessionController();
    }

    /**
     * Sets the current session, so that the currently logged in user is known.
     *
     * @param sc SessionController;
     */
    public void setCurrentSession(SessionController sc) {
        this.currentSession = sc;
    }

    /**
     * Returns the list of parks.
     *
     * @return list of parks.
     */
    public List<Park> getParkList() {
        return this.locationFacade.getParkList();
    }

    /**
     * Returns the list of available bicycles of a given type in a given park.
     *
     * @param parkId Id of the park.
     * @param type   Type of the bike.
     * @return List of bicycles of given type available in given park.
     */
    public List<Bicycle> getAvailableBicycles(int parkId, Bicycle.BicycleType type) {
        List<Bicycle> bikeList = this.locationFacade.getAvailableBicycles(parkId, type);
        if (bikeList.size() == 0) {
            throw new InvalidDataException("There are no available " + type + " bicycles for the selected park.");
        }
        this.chosenType = type;
        return bikeList;
    }


    /**
     * Unlocks a bicycle given bicycle for the loggedUser.
     *
     * @param bikeId The requested bicycle id.
     */
    public void unlockBicycle(String bikeId, User user) {
        Park park = this.locationFacade.getParkIdBicycleLocked(bikeId);

        this.bicycleFacade.unlockBicycle(bikeId, chosenType);
        this.userFacade.createRental(bikeId, park,user);
    }


}

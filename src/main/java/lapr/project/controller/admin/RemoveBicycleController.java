package lapr.project.controller.admin;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.BicycleFacade;

import java.util.List;

public class RemoveBicycleController {

    /**
     * Instance of BicycleNetwork
     */
    private BicycleFacade bf;

    /**
     * Constructor of RemoveBicycleController
     */
    public RemoveBicycleController() {
        this.bf = new BicycleFacade();
    }

    /**
     * Method that returns every single Bicycle ID in the database
     *
     * @return List containg all bicycle ID's
     */
    public List<Bicycle> getAllBicycles() {
        return bf.getAllBicycles();
    }

    /**
     * Method that removes Bicycle from the Database
     *
     * @param idBicycle ID of the bicycle to be removed
     * @return whether or not the operation worked
     */
    public boolean removeBicycle(int idBicycle) {
        return bf.removeBicycle(idBicycle);
    }

}

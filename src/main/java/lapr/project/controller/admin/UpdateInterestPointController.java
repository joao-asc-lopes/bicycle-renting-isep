package lapr.project.controller.admin;

import lapr.project.model.park.InterestPoint;
import lapr.project.model.park.LocationFacade;

import java.util.List;

public class UpdateInterestPointController {
    /**
     * Variable that represents the bicycle Network.
     */
    private LocationFacade pf;

    /**
     * Constructor of Update Interest Point Controller.
     */
    public UpdateInterestPointController() {
        this.pf = new LocationFacade();
    }

    /**
     * Returns a list of all InterestPoints in the database
     * @return a list of all InterestPoints in the database
     */
    public List<InterestPoint> getAllInterestPoints(){
        return this.pf.getAllInterestPoints();
    }

    /**
     * Updates an InterestPoint with id_point in the database
     * @return the success of the operation
     */
    public boolean updateInterestPoint(int id_point, String name){
        return this.pf.updateInterestPoint(id_point, name);
    }


}

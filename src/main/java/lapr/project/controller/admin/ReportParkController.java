package lapr.project.controller.admin;


import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;

import java.util.ArrayList;
import java.util.List;

public class ReportParkController {
    /**
     * Variable that it is representative of the park facade.
     */
    private LocationFacade pf;

    /**
     * Constructor of Report Park Controller that opens up a new Park Facade.
     */
    public ReportParkController(){
        this.pf = new LocationFacade();
    }
    /**
     * Method that return the parkRegistry in LocationFacade.
     *
     * @return List with parks
     */
    public List<Park> getParkRegistry() {
        return this.pf.getParkList();
    }

    /**
     * Method that allows me to get all the bicycles List of a park.
     * @param p The park we want to get all the bicycles
     * @return A list of all the electricBicycles passed as a parameter.
     */
    public List<ElectricBicycle> getEletricBikesPark(Park p ){
        return this.pf.getElectricBicyclesPark(p.getIdLocation());

    }

    /**
     * Method that allows us to get the report of the park.
     * @param p The park passed as a parameter.
     */
    public ArrayList<String> getReport (Park p, List<ElectricBicycle> eList){
     return p.showInfoOfPark(eList);
    }



}

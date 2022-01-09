package lapr.project.controller.admin;

import lapr.project.model.park.LocationFacade;

public class RemoveParkController {
    /**
     * Variable that it is representative of a connection to the DataBase.
     */
    private LocationFacade pf;

    /**
     * Constructor that builds a connection to the BicycleNetwork opening it.
     */
    public RemoveParkController() {
        this.pf = new LocationFacade();
    }

    /**
     * Method that allows to remove a park of the database, reallocating all its bicycles to other parks ( if possible ),
     *
     * @param idPark The id of the park about to be removed.
     * @return True if the park was correctly removed, false if not.
     */

    public boolean removePark(int idPark) {
        return this.pf.removePark(idPark);
    }
}

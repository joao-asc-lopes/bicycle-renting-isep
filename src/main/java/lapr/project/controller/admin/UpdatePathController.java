package lapr.project.controller.admin;

import lapr.project.model.bikenetwork.Path;
import lapr.project.model.bikenetwork.PathFacade;
import lapr.project.model.park.Location;

public class UpdatePathController {

    private PathFacade wf;


    public UpdatePathController() {
        this.wf = new PathFacade();
    }


    /**
     * Returns the wind information (instance of WindInformation) between two parks
     *
     * @param from The starting point park
     * @param to   The destination park
     * @return the instance of WindInformation between two parks
     */
    public Path getPath(Location from, Location to) {
        try {
            return wf.getPath(from, to);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     *  
     * update path information (instance of Path) between two parks
     *
     * @param initialParkId The staring point park
     * @param finalParkId   The destination park
     * @return true if path information is updated successfully; false if path information isn't updated
     */
    public boolean updatePath(int initialParkId, int finalParkId, double windSpeed, double windDirection, double kinectic) {
        return wf.updatePath(initialParkId, finalParkId, windSpeed, windDirection, kinectic);
    }


}
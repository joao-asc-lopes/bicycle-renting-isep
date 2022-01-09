package lapr.project.controller.admin;

import lapr.project.model.bikenetwork.Path;
import lapr.project.model.bikenetwork.PathFacade;
import lapr.project.model.park.Location;

public class AddPathController {

    private PathFacade wf;


    public AddPathController() {
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
     * Adds path information (instance of Path) between two parks
     *
     * @return true if path information is added successfully; false if path information isn't added
     */
    public boolean addPath(double lat1, double long1, double lat2, double long2, double windSpeed, double windDirection, double kinectic) {
        return wf.addPath(lat1, long1, lat2, long2, windSpeed, windDirection, kinectic);
    }


   

}

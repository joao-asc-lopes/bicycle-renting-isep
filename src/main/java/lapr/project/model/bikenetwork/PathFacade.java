/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model.bikenetwork;


import lapr.project.model.park.Location;

public class PathFacade {

    /**
     * Private variable representative of an instance of wind information service.
     */
    private PathService PathService;

    /**
     * Constructor of the Wind Information Facade class that opens an instance of Wind Information Service.
     */
    public PathFacade() {
        this.PathService = new PathService();
    }

    public Path getPath(Location from, Location to) {
        return this.PathService.getPath(from, to);
    }

    /**
     * Updates path information (instance of path) between two parks
     *
     * @param initialParkId The staring point park
     * @param finalParkId   The destination park
     * @return true if path information is updated successfully; false if wind information isn't updated
     */
    public boolean updatePath(int initialParkId, int finalParkId, double windSpeed, double windDirection, double kinectic) {
        return PathService.updatePath(initialParkId, finalParkId, windSpeed, windDirection, kinectic);
    }

    /**
     * Method that adds a Path to the database
     *
     * @param windSpeed
     * @param windDir
     * @param kineticFriction
     * @return
     */
    public boolean addPath(double lat1, double long1, double lat2, double long2, double windSpeed, double windDir, double kineticFriction) {
        return this.PathService.addPath(lat1, long1, lat2, long2, windSpeed, windDir, kineticFriction);
    }
}
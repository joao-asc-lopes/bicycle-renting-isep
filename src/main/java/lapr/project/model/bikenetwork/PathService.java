/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model.bikenetwork;


import lapr.project.data.LocationDao;
import lapr.project.data.PathDao;
import lapr.project.model.park.Location;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;


public class PathService {


    /**
     * Private variable representative of an instance of wind information dao.
     */
    private PathDao PathDao;

    private LocationDao locationDao;

    /**
     * Constructor of the Wind Information Service class that opens an instance of Wind Information Dao.
     */
    public PathService() {
        this.PathDao = new PathDao();
        this.locationDao = new LocationDao();
    }

    public Path getPath(Location from, Location to) {
        return this.PathDao.getPath(from, to);
    }

    /**
     * Updates path information (instance of path) between two parks
     *
     * @param initialParkId The staring point park
     * @param finalParkId   The destination park
     * @return true if path information is updated successfully; false if path information isn't updated
     */
    public boolean updatePath(int initialParkId, int finalParkId, double windSpeed, double windDirection, double kineticFriction) {
        return PathDao.updatePath(initialParkId, finalParkId, windSpeed, windDirection, kineticFriction);
    }
    
    /**
     * Updates wind information (instance of WindInformation) between two parks
     *
     * @param initialParkId The staring point park
     * @param finalParkId   The destination park
     * @return true if wind information is updated successfully; false if wind information isn't updated
     */
    public boolean updateWindInformation(int initialParkId, int finalParkId, double windSpeed, double windDirection) {
        return PathDao.updatePath(initialParkId, finalParkId, windSpeed, windDirection, 0.002);
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
        Path pa = new Path(windSpeed, windDir, kineticFriction);
        Park p1 = returnParkByCoordinates(lat1, long1);
        Park p2 = returnParkByCoordinates(lat2, long2);

        return this.PathDao.addPath(pa, p1, p2);
    }

    /**
     * Returns the Location with this pair of coordinates from the database
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public Park returnParkByCoordinates(double latitude, double longitude) {
        Location aux = this.locationDao.getLocationByCoordinates(latitude, longitude);
        if (aux instanceof Park) {
            return (Park) aux;
        }
        throw new InvalidDataException("No Park with these coordinates");
    }
}
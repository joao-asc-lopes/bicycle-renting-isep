package lapr.project.model.park;


import lapr.project.data.LocationDao;
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.bicycle.MountainBicycle;
import lapr.project.model.bicycle.RoadBicycle;
import lapr.project.utils.PhysicsAlgorithms;

import java.util.List;

public class LocationService {

    /**
     * Makes the connection between the services of park and the database.
     */
    private LocationDao locationDao;


    /**
     * The park we are trying to make use of its services.
     */
    private Park park;

    /**
     * Instance of the LocationService class, instanciating a new connection.
     */
    public LocationService() {
        this.locationDao = new LocationDao();
    }

    /**
     * Method that allows a user to get a location given its id as a parameter.
     *
     * @param idPark The id of the location given as a parameter.
     * @return Returns the location if it exists.
     */
    public Park getPark(int idPark) {
        return this.locationDao.getPark(idPark);
    }

    /**
     * Returns an interest point by its id.
     *
     * @param idInterestPoint id of an interest point.
     * @return an Interest point registry.
     */
    public InterestPoint getInterestPoint(int idInterestPoint) {
        return this.locationDao.getInterestPoint(idInterestPoint);
    }

    /**
     * Returns the Location with this pair of coordinates from the database
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public Location getLocationByCoordinates(double latitude, double longitude) {
        return this.locationDao.getLocationByCoordinates(latitude, longitude);
    }

    /**
     * Returns a park where a given bicycle is parked.
     *
     * @param idBicycle Id of a bicycle.
     * @return Instance of park where the bicycle is parked.
     */
    public Park getParkByIdBicycle(String idBicycle) {
        return this.locationDao.getParkIdBicycleLocked(idBicycle);
    }


    /**
     * Removes the park with the id given has a parameter.
     *
     * @param idPk The id of the park passed as a parameter.
     * @return True if it removed the park, false if not.
     */
    public boolean removePark(int idPk) {
        return this.locationDao.removePark(idPk);
    }

    /**
     * Removes an interest Point given the id has a parameter.
     *
     * @param idIp The id of the interest point passed as a parameter.
     * @return True if it was removed, false if not.
     * <p>
     * I can not merge this method with the removePark because the above has a special procedure when we erase it ( reallocates bikes).
     */
    public boolean removeInterestPoint(int idIp) {
        return this.locationDao.removeInterestPoint(idIp);
    }


    /**
     * Saves a created Interest Point in the database
     *
     * @param ip The created Interest Point.
     * @return True if saved, False if not.
     */
    private InterestPoint saveInterest(InterestPoint ip) {
        return this.locationDao.addInterestPoint(ip);
    }

    /**
     * Registers and saves in the database a new park.
     *
     * @param name                    The name of the new park.
     * @param latitude                The latitude of the new park.
     * @param longitude               The longitude of the new park.
     * @param normalBicycleCapacity   The capacity for normal (mountain and road) bicycles of the new park.
     * @param numberFreeNormalSlots   The number of free slots for normal bicycles of the new park.
     * @param electricBicycleCapacity The capacity for electrical bicycles of the new park.
     * @param numberFreeElectricSlots The number of free slots for electric bicycles of the new park.
     * @param altitude                The altitude of the new park
     * @return True if it saved. False if already exists and did not register.
     */
    public boolean addPark(String name, double latitude, double longitude, int normalBicycleCapacity, int numberFreeNormalSlots, int electricBicycleCapacity, int numberFreeElectricSlots, double chargeRate, double intensity, double altitude) {
        ElectricSlot eS = new ElectricSlot(0, electricBicycleCapacity, numberFreeElectricSlots, chargeRate, intensity);
        NormalSlots nS = new NormalSlots(0, normalBicycleCapacity, numberFreeNormalSlots);
        Park p = new Park(name, latitude, longitude, nS, eS, altitude);

        return this.locationDao.addPark(p);
    }

    /**
     * Registers and saves in the database a new interest Point.
     *
     * @param name      The name of the new interest point.
     * @param latitude  The latitude of the new interest point.
     * @param longitude The longitude of the new interest point.
     * @return True if it saved, false if not.
     */
    public InterestPoint addInterestPoint(String name, double latitude, double longitude,
                                    double altitude) {
        InterestPoint ip = new InterestPoint(name, latitude, longitude, altitude);
        return this.saveInterest(ip);
    }


    /**
     * Method that return the Bicycles in a Park
     *
     * @param parkId Park Id
     * @return Bicycles in the Park
     */
    public List<Bicycle> getParkedBicycles(int parkId) {
        return this.locationDao.getParkedBicycles(parkId);
    }

    /**
     * Method that returns all the Mountain Bicycles in a Park.
     *
     * @param parkId The park id we are trying to get the mountain bicycles.
     * @return An ArrayList with all the mountain bicycles of a given park.
     */
    public List<MountainBicycle> getMountainBicyclesPark(int parkId) {
        return this.locationDao.getMountainBicycles(parkId);
    }

    /**
     * Method that returns all the road bicycles in a Park.
     *
     * @param parkId The park id we are trying to get the road bicycles.
     * @return An ArrayList with all the road bicycles of a given park.
     */
    public List<RoadBicycle> getRoadBicyclesPark(int parkId) {
        return this.locationDao.getRoadBicycles(parkId);
    }

    /**
     * Method that returns all the eletrical bicycles in a Park.
     *
     * @param idPark The park id we are trying to get the eletrical bicycles.
     * @return An ArrayList with all the eletrical bicycles of a given park.
     */
    public List<ElectricBicycle> getEletricBicyclesPark(int idPark) {
        return this.locationDao.getElectricalBicycles(idPark);
    }

    /**
     * Returns the NormalSlot of the desired park
     *
     * @param idSlot The id of the NormalSlot (belonging to the park) we want to get
     * @return The desired park's NormalSlot
     */
    public NormalSlots getNormalSlot(int idSlot) {

        return this.locationDao.getNormalSlot(idSlot);
    }


    /**
     * Returns the NormalSlot of the desired park
     *
     * @param idSlot The id of the ElectricSlot (belonging to the park) we want to get
     * @return The desired park's ElectricSlot
     */
    public ElectricSlot getElectricalSlot(int idSlot) {

        return this.locationDao.getElectricalSlot(idSlot);
    }


    /**
     * Returns an ArrayList with all the parks in the database
     *
     * @return The ArrayList with all the parks in the database
     */
    public List<Park> getParkList() {

        return this.locationDao.getParkList();
    }


    /**
     * Method that allows the user to insert a bicycle into a park.
     *
     * @param bicycleId The bicycle we want to insert into the park.
     * @param parkId    The park where we want to insert a bicycle.
     * @return True if the park was inserted. False if not.
     */
    public boolean bikeIntoPark(String bicycleId, int parkId) {
        return locationDao.bikeIntoPark(bicycleId, parkId);
    }


    /**
     * Updates a park's data
     *
     * @param idPark                    The id of the park to be updated.
     * @param name                      The name of the park to be updated.
     * @param normalBicycleCapacity     The capacity for mountain and road bicycles of the park to be updated.
     * @param numberFreeNormalSlots     The number of free slots for mountain and road bicycles of the park to be updated.
     * @param electricBicycleCapacity   The capacity for electrical bicycles of the park to be updated.
     * @param numberFreeElectricalSlots The number of free slots for electrical bicycles of the park to be updated.
     */
    public boolean updatePark(int idPark, String name, int normalBicycleCapacity, int numberFreeNormalSlots,
                              int electricBicycleCapacity, int numberFreeElectricalSlots) {
        Park p = getPark(idPark);
        double latitude = p.getLatitude();
        double longitude = p.getLongitude();
        double altitude = p.getAltitude();
        p.getNormalSlots().setData(normalBicycleCapacity, numberFreeNormalSlots);
        p.getEletricalSlots().setData(electricBicycleCapacity, numberFreeElectricalSlots, p.getEletricalSlots().getChargeRate(), p.getEletricalSlots().getIntensity());
        Park newPark = new Park(idPark, name, latitude, longitude, p.getNormalSlots(), p.getEletricalSlots(), altitude);
        return locationDao.updatePark(newPark);
    }

    /**
     * Returns the distance between two Parks using physics algorithms, based on their coordinates.
     *
     * @param initial Initial location.
     * @param dest    Destination location.
     * @return The distance between them.
     */
    public double distanceBetweenLocations(Location initial, Location dest) {
        return PhysicsAlgorithms.distance(initial.getLatitude(), initial.getLongitude(), dest.getLatitude(), dest.getLongitude());
    }

    /**
     * Returns a list of all InterestPoints in the database
     *
     * @return a list of all InterestPoints in the database
     */
    public List<InterestPoint> getAllInterestPoints() {
        return this.locationDao.getAllInterestPointsList();
    }

    /**
     * Updates an InterestPoint with id_point in the database
     *
     * @return the success of the operation
     */
    public boolean updateInterestPoint(int idPoint, String name) {
        InterestPoint ip = getInterestPoint(idPoint);
        InterestPoint newIp = new InterestPoint(idPoint, name, ip.getLatitude(), ip.getLongitude(), ip.getAltitude());
        return this.locationDao.updateInterestPoint(newIp);
    }
}

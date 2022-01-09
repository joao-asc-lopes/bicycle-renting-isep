package lapr.project.model.park;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.bicycle.MountainBicycle;
import lapr.project.model.bicycle.RoadBicycle;

import java.util.ArrayList;
import java.util.List;

public class LocationFacade {
    /**
     * Variable representative of the park service.
     */

    private LocationService ps;

    /**
     * Constructor of the park Facade.
     */

    public LocationFacade() {
        this.ps = new LocationService();
    }



    /**
     * Returns all the bicycles parked in a certain park.
     *
     * @param parkId id of the park.
     * @return List of bicycles parked in given park.
     */
    public List<Bicycle> getParkedBicycles(int parkId) {
        return this.ps.getParkedBicycles(parkId);
    }

    /**
     * Return all the mountain bicycles in a certain park.
     * @param idPark Id of the park.
     * @return List of mountain bicycles in a given park.
     */
    public List<MountainBicycle> getMountainBicyclesPark(int idPark){
        return this.ps.getMountainBicyclesPark(idPark);
    }

    /**
     * Return all the road bicycles in a certain park.
     * @param idPark Id of the park.
     * @return List of road bicycles in a given park.
     */
    public List<RoadBicycle> getRoadBicyclesPark(int idPark){
        return this.ps.getRoadBicyclesPark(idPark);
    }

    /**
     * Return all the electric bicycles in a certain park.
     * @param idPark id of the park.
     * @return List of electrical bicycles in a given park.
     */

    public List<ElectricBicycle> getElectricBicyclesPark(int idPark){
        return this.ps.getEletricBicyclesPark(idPark);
    }

    public Park getParkIdBicycleLocked(String idBicycle){
        return this.ps.getParkByIdBicycle(idBicycle);
    }


    /**
     * Returns a list of all available bicycles for a given park.
     *
     * @param parkId id of the park.
     * @return All bicycles for a given park.
     */
    public List<Bicycle> getAvailableBicycles(int parkId, Bicycle.BicycleType type) {
        List<Bicycle> returnResult = new ArrayList<>();
        for (Bicycle b : this.ps.getParkedBicycles(parkId)) {
            if (b.getType() == type && b.getStatus().statusCode() == Bicycle.BicycleStatus.AVAILABLE.statusCode()) {
                returnResult.add(b);
            }
        }
        return returnResult;
    }

    /**
     * Method that allows a park to be removed from the DB given its parameter.
     *
     * @param idPark The id of the park about to be removed.
     * @return True if it removed the park, false if not.
     */
    public boolean removePark(int idPark) {
        return ps.removePark(idPark);
    }


    /**
     * Method that allows an interest point to be removed from the DB given its parameter.
     *
     * @param idLocation The id of the location we want to remove.
     * @return True if the interestPoint was removed, false if not.
     */
    public boolean removeInterestPoint(int idLocation) {

        return this.ps.removeInterestPoint(idLocation);
    }


    /**
     * Creates a new instance of park, saving it in the database.
     *
     * @param name           The new park's name.
     * @param latitude       The new park's latitude.
     * @param longitude      The new park's longitude.
     * @param normalBicycleCapacity     The new park's capacity for normal (mountain and road) bicycles.
     * @param numberFreeNormalSlots     The new park's number of free slots for normal bicycles
     * @param electricBicycleCapacity   The new park's capacity for electrical bicycles.
     * @param numberFreeElectricSlots   The new park's number of free slots for electric bicycles
     * @param altitude      The new park's altitude
     * @return True if the park was registered, false if not.
     */
    public boolean registerPark(String name, double latitude, double longitude, int  normalBicycleCapacity, int numberFreeNormalSlots, int electricBicycleCapacity, int numberFreeElectricSlots,double chargeRate,double intensity, double altitude) {
        return ps.addPark(name, latitude, longitude, normalBicycleCapacity, numberFreeNormalSlots, electricBicycleCapacity,numberFreeElectricSlots,chargeRate,intensity, altitude);
    }

    /**
     * Creates a new instance of an interest point.
     * @param name The name of the interest point.
     * @param latitude The latitude of the interest point.
     * @param longitude The longitude of the interest point.
     * @param altitude The altitude of the interest point.
     * @return True if the interest point was registered, false if not.
     */
    public InterestPoint registerInterestPoint(String name, double latitude, double longitude,double altitude){
        return ps.addInterestPoint(name,latitude,longitude,altitude);
    }

    /**
     * Returns a list of parks.
     *
     * @return All the parks that exist in the DB.
     */
    public List<Park> getParkList() {
        return ps.getParkList();
    }

    /**
     * Method that returns a park form the DB given its id.
     *
     * @param id The id of the wanted park passed as a parameter.
     * @return The park returned.
     */
    public Park getParkById(int id) {
        return ps.getPark(id);
    }

    public InterestPoint getInterestPointById(int id){
        return ps.getInterestPoint(id);
    }

    /**
     * Returns the Location with this pair of coordinates from the database
     * @param latitude
     * @param longitude
     * @return
     */
    public Location getLocationByCoordinates(double latitude, double longitude){
        return ps.getLocationByCoordinates(latitude,longitude);
    }




    /**
     * Method that allows the user to insert a given bike into a park given with its id.
     * @param bicycleId The bicycle that we want to store in the park.
     * @param parkId The park we want to put a bicycle in.
     * @return True if the bicycle was parked. False if not.
     */

    public boolean bikeIntoPark(String bicycleId, int parkId){
        return ps.bikeIntoPark(bicycleId, parkId);
    }

    /**
     * Updates the Park with id = idPark in the Database
     *
     * @param idPark         The new park's id.
     * @param name           The new park's name.
     * @param normalBicycleCapacity     The new park's capacity for normal (mountain and road) bicycles.
     * @param numberFreeNormalSlots     The new park's number of free slots for normal bicycles
     * @param electricBicycleCapacity   The new park's capacity for electrical bicycles.
     * @param numberFreeElectricalSlots   The new park's number of free slots for electric bicycles
     * @return True if the park was registered, false if not.
     */
    public boolean updatePark(int idPark, String name, int normalBicycleCapacity , int numberFreeNormalSlots, int electricBicycleCapacity, int numberFreeElectricalSlots){
        return ps.updatePark(idPark,name,normalBicycleCapacity,numberFreeNormalSlots,electricBicycleCapacity,numberFreeElectricalSlots);
    }

    /**
     * Returns a list of all InterestPoints in the database
     * @return a list of all InterestPoints in the database
     */
    public List<InterestPoint> getAllInterestPoints(){
        return this.ps.getAllInterestPoints();
    }

    /**
     * Updates an InterestPoint with idPoint in the database
     * @return the success of the operation
     */
    public boolean updateInterestPoint(int idPoint, String name){
        return ps.updateInterestPoint(idPoint,name);
    }
}

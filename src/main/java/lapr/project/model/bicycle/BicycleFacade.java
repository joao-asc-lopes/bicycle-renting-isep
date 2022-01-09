package lapr.project.model.bicycle;

import java.util.List;

public class BicycleFacade {

    /**
     *  Private variable representative of an instance of bicycle service.
     */


    private BicycleService bs;
    /**
     * Constructor of the bicycleFacade that opens an instance of Bicycle Service.
     */

    public BicycleFacade() {
        this.bs = new BicycleService();
    }

    /**
     * Method that returns every single Bicycle ID in the database
     *
     * @return List containing all bicycles.
     */
    public List<Bicycle> getAllBicycles() {
        return bs.getAllBicycles();
    }


    /**
     * Creates a new Mountain Bicycle and saves it in the DB.
     *
     * @param status The bike's status.
     * @param weight The bike's weight.
     * @return true if the registry was successful, false if not.
     */
    public boolean createMountainBicycle(String id,Bicycle.BicycleStatus status, float weight, double coefficient,double frontalArea) {
        return bs.createNonElectricBicycle(id,status, weight, Bicycle.BicycleType.MOUNTAIN, coefficient, frontalArea);
    }

    /**
     * Creates a new Road Bicycle and saves it in the DB.
     *
     * @param status The bike's status.
     * @param weight The bike's weight.
     * @return true if the creation was successful, false if not.
     */
    public boolean createRoadBicycle(String id,Bicycle.BicycleStatus status, float weight, double coefficient,double frontalArea) {
        return bs.createNonElectricBicycle(id,status, weight, Bicycle.BicycleType.ROAD, coefficient,frontalArea);
    }

    /**
     * Creates a new Electric bike and saves it in the DB.
     *
     * @param status               The bike's status
     * @param weight               The bike's weight
     * @param batteryMaxCharge     The bike's battery max charge value.
     * @param batteryCurrentCharge The bike's battery current charge.
     * @param batteryWeight        The bike's battery weight.
     * @return true if the creation was successful, false if not.
     */
    public boolean createElectricBicycle(String id,Bicycle.BicycleStatus status, float weight, double batteryMaxCharge, double batteryCurrentCharge, double batteryWeight, double coefficient, double frontalArea) {
        return bs.createElectricBicycle(id,status, weight, batteryMaxCharge, batteryCurrentCharge, batteryWeight,coefficient,frontalArea);
    }

    /**
     * Unlocks a given bicycle and creates a rental for the currently logged in user.
     *
     * @param bikeId id of the bike which will be unlocked.
     */
    public void unlockBicycle(String bikeId, Bicycle.BicycleType type) {
        this.bs.unlockBicycle(bikeId, type);
    }


    /**
     * Method that, given the id of a bike, erases it from the DB.
     *
     * @param idBike The id of the bike about to be removed from the db.
     * @return True if it removed a bicycle, False if not.
     */

    public boolean removeBicycle(int idBike) {
        return bs.delete(idBike);
    }

    /**
     * Updates a non electrical bicycle with the attributes passed as a paramter.
     *
     * @param idBike The id of the bike passed as a parameter.
     * @param status  The status of the bike passed as a parameter.
     * @param weight  The weight of the bike passed as a parameter.
     * @return True if the bike was updated, false if not.
     */
    public boolean updateNonElectricalBicycle(String idBike, Bicycle.BicycleStatus status, float weight, double coefficient) {
        return bs.updateBicycle(idBike, status, weight, coefficient);
    }

    /**
     * Updates all the electrical bicycles with all the attributes passed as a parameter.
     *
     * @param idBike    The id of the bicycle passed as a parameter.
     * @param status     The status of the bike passed as a parameter.
     * @param idBattery The if od the battery passed as  parameter.
     * @return True if it was updated, false if not.
     */
    public boolean updateElectricBicycle(String idBike, Bicycle.BicycleStatus status,float weight, int idBattery, double coefficient) {
        return bs.updateElectricBicycle(idBike, status, weight,idBattery,coefficient);
    }

    /**
     * Returns a list of all the electric bicycles in the database.
     *
     * @return list with all the electric bicycles in the database.
     */
    public List<ElectricBicycle> getElectricBicyclesList() {
        return this.bs.getElectricBicyclesList();
    }

    /**
     * Returns a list with all the mountain bicycles in the database.
     *
     * @return A list with all the mountain bicycles.
     */
    public List<MountainBicycle> getMountainBicycleList() {
        return this.bs.getMountainBicyclesList();
    }

    /**
     * Returns a list with all the road bicycles in the database.
     *
     * @return A list with all the road bicycles.
     */
    public List<RoadBicycle> getRoadBicycleList() {
        return this.bs.getRoadBicyclesList();
    }

    /**
     * Returns a bicycle from a user.
     * @param id The id of the bicycle.
     * @return The bicycle .
     */
    public Bicycle getBicycle(String id){
        return this.bs.getBicycle(id);
    }

}

package lapr.project.model.bicycle;

import lapr.project.data.BatteryDao;
import lapr.project.data.BicycleDao;
import lapr.project.utils.InvalidDataException;

import java.util.ArrayList;
import java.util.List;

public class BicycleService {
    /**
     * The Data Access Object for Bicycle.
     */
    private BicycleDao bicycleDao;

    private BatteryDao batteryDao;

    /**
     * Initializes the data access object.
     */
    public BicycleService() {
        this.bicycleDao = new BicycleDao();
        this.batteryDao = new BatteryDao();
    }

    /**
     *
     * @param id Id of the electric bicycle to be searched for.
     * @return  The registry of the electric bicycle.
     */
    public ElectricBicycle getElectricBicycle(String id) {
        return bicycleDao.getElectricBicycle(id);
    }

    /**
     *
     * @param id   Id of the mountain bicycle to be searched for.
     * @return  The register of the mountain bicycle.
     */
    public MountainBicycle getMountainBicycle(String id) {
        return bicycleDao.getMountainBicycle(id);
    }

    /**
     *
     * @param id    Id of the road bicycle to be searched for.
     * @return  The register of the road bicycle.
     */
    public RoadBicycle getRoadBicycle(String id) {
        return bicycleDao.getRoadBicycle(id);
    }

    public Bicycle getBicycle(String id){
        return bicycleDao.getBicycle(id);
    }

    /**
     * Adds a non electric bicycle to the database.
     *
     * @param status The bike's status.
     * @param weight The bike's weight.
     * @param type   The bike's type
     * @param coefficient The bike's coefficient
     * @return true if the operation was successful, false if not.
     */
    public boolean createNonElectricBicycle(String id,Bicycle.BicycleStatus status, float weight, Bicycle.BicycleType type, double coefficient, double frontalArea) {
        if(type.equals(Bicycle.BicycleType.MOUNTAIN)){
            MountainBicycle mb = new MountainBicycle(id,status,weight,coefficient,frontalArea);
            return this.bicycleDao.addNonElectricBicycle(mb);
        }
        else{
            RoadBicycle rb = new RoadBicycle(id,status,weight,coefficient,frontalArea);
            return this.bicycleDao.addNonElectricBicycle(rb);
        }
    }

    /**
     * @param status
     * @param weight
     * @param batteryMaxCharge
     * @param batteryCurrentCharge
     * @return
     */
    public boolean createElectricBicycle(String id,Bicycle.BicycleStatus status, float weight, double batteryMaxCharge, double batteryCurrentCharge, double batteryWeight, double coefficient, double frontalArea) {
        ElectricBicycle eb = new ElectricBicycle(id,status,new Battery(0,(long)batteryMaxCharge,(long)batteryCurrentCharge,(float)batteryWeight),weight,coefficient,frontalArea);
        return this.bicycleDao.addElectricBicycle(eb);
    }

    /**
     * Method that deletes a bicycle from the database
     *
     * @param idBike
     * @return
     */
    public boolean delete(int idBike) {
        return bicycleDao.removeBicycle(idBike);
    }

    /**
     * Method that updates a RoadBicycle or a Mountain Bicycle
     *
     * @param status Status of the bicycle.
     * @param weight Weight of the bicycle.
     * @return true if successful, false if not.
     */
    public boolean updateBicycle(String bikeId, Bicycle.BicycleStatus status, float weight, double coefficient) {
        Bicycle bicycle = getBicycle(bikeId);
        bicycle.setStatus(status);
        bicycle.setWeight(weight);
        bicycle.setBicycleAeroCoefficient(coefficient);
        return bicycleDao.updateNonElectricBicycle(bicycle);
    }

    /**
     * Method that updates a ElectricalBicycle
     *
     * @param idBike
     * @return
     */
    public boolean updateElectricBicycle(String idBike, Bicycle.BicycleStatus status, float weight, int idNewBattery, double coefficient) {
        ElectricBicycle bicycle = getElectricBicycle(idBike);
        bicycle.setStatus(status);
        bicycle.setWeight(weight);
        bicycle.setBicycleAeroCoefficient(coefficient);
        bicycle.setBattery(this.batteryDao.getBattery(idNewBattery));
        return bicycleDao.updateElectricBicycle(bicycle);
    }

    /**
     * Method that returns all Bicycles in the Database
     *
     * @return
     */
    public ArrayList<Bicycle> getAllBicycles() {
        return bicycleDao.getAllBicycles();
    }

    /**
     * Returns a list of all the electric bicycles in the database.
     *
     * @return list with all the electric bicycles in the database.
     */
    public List<ElectricBicycle> getElectricBicyclesList() {
        return this.bicycleDao.getElectricBicyclesList();
    }

    /**
     * Returns a list with all the mountain bicycles in the database.
     *
     * @return A list with all the mountain bicycles.
     */
    public List<MountainBicycle> getMountainBicyclesList() {
        return this.bicycleDao.getMountainBicyclesList();
    }

    /**
     * Returns a list with all the road bicycles in the database.
     *
     * @return A list with all the road bicycles.
     */
    public List<RoadBicycle> getRoadBicyclesList() {
        return this.bicycleDao.getRoadBicyclesList();
    }


    /**
     * Unlocks a bicycle from a park, so that a use can ride it.
     *
     * @param bikeId id of the bicycle to be unlocked.
     * @param type   type of the bicycle to be unlocked.
     */
    public void unlockBicycle(String bikeId, Bicycle.BicycleType type) {
        Bicycle b;
        //determines bike type.
        if (type.toString().equals(Bicycle.BicycleType.ELECTRIC.toString())) {
            b = this.bicycleDao.getElectricBicycle(bikeId);
        } else if (type.toString().equals(Bicycle.BicycleType.ROAD.toString())) {
            b = this.bicycleDao.getRoadBicycle(bikeId);
        } else {
            b = this.bicycleDao.getMountainBicycle(bikeId);
        }
        //verifies bike status.
        if (b.getStatus().statusCode() == Bicycle.BicycleStatus.AVAILABLE.statusCode()) {
            b.setStatus(Bicycle.BicycleStatus.IN_USE);
            this.bicycleDao.updateNonElectricBicycle(b);
        } else {
            throw new InvalidDataException("Bicycle is no longer available");
        }
    }
}

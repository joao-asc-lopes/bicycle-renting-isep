package lapr.project.controller.admin;

import lapr.project.model.park.Location;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

public class AddParkController {

    /**
     * Variable that represents the bicycleNetwork.
     */
    private LocationFacade lf;

    /**
     * Constructor to the Park Registration Controller that opens a path to the Bicycle Network.
     */
    public AddParkController() {
        this.lf = new LocationFacade();
    }

    /**
     * Registers a park in the dataBase.
     *
     * @param name                    The name of the new park to be registered in the database.
     * @param latitude                The latitude of the new park to be registered in the database.
     * @param longitude               The longitude of the new park to be registered in the database.
     * @param normalBicycleCapacity   The capacity for normal (mountain and road) bicycles of the park to be registered in the database.
     * @param numberFreeNormalSlots   The number of free slots for normal bicycles of the park to be registered in the databse
     * @param electricBicycleCapacity The capacity for electrical bicycles of the park to be registered in the database.
     * @param numberFreeElectricSlots The number of free slots for electric bicycles
     * @param elevation               The elevation of the park location
     * @return True if the park was correctly returned, False if not.
     */


    public boolean addPark(String name, double latitude, double longitude, int normalBicycleCapacity, int numberFreeNormalSlots, int electricBicycleCapacity, int numberFreeElectricSlots,double chargeRate,double intensity, double elevation) {
        return this.lf.registerPark(name, latitude, longitude, normalBicycleCapacity, numberFreeNormalSlots, electricBicycleCapacity, numberFreeElectricSlots,chargeRate,intensity, elevation);
    }

    /**
     *
     * @return
     */
    public Park getParkWithCoordinates(double latitude, double longitude){
        Location aux = this.lf.getLocationByCoordinates(latitude, longitude);
        if(aux instanceof Park) {
            return (Park)aux;
        }
        throw new InvalidDataException("No Park with these coordinates");
    }



}
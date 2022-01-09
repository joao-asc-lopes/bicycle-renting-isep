package lapr.project.model.bicycle;


public class MountainBicycle extends Bicycle {

    /**
     * Constructor of the Mountain Bicycle. Constructs an instance of MountainBicycles given all its attributes as a parameter.
     * @param id_bike The id of the Bike given as a parameter.
     * @param state The state of the Bike given as a parameter.
     * @param weight The weight of the bike given as a parameter.
     * @param  coefficient The coefficient of the bike.
     */

    public MountainBicycle(String id_bike, BicycleStatus state, float weight, double coefficient,double frontalArea) {
        super(id_bike, state, BicycleType.MOUNTAIN, weight,coefficient, frontalArea);
    }



}

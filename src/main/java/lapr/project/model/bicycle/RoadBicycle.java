package lapr.project.model.bicycle;


public class RoadBicycle extends Bicycle {

    /**
     * Constructs an instance of Road bicycle with all its attributes given as a parameter.
     * @param idBike The id of the bike given as a parameter.
     * @param state The state of the bike given as a parameter.
     * @param weight The weight of the bike given as a parameter.
     * @param  coefficient The coefficient of the bike.
     */

    public RoadBicycle(String idBike, BicycleStatus state, float weight, double coefficient,double frontalArea) {
        super(idBike, state, BicycleType.ROAD, weight, coefficient, frontalArea);
    }


}

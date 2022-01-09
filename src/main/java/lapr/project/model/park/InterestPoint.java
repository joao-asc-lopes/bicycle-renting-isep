package lapr.project.model.park;

public class InterestPoint extends Location {
    /**
     * Constructor for the instance of the InterestPoint.
     *
     * @param idLocation the idLocation of the instance of the interest point.
     * @param name       The name of the instance of the interest point.
     * @param latitude   The latitude of this instance of the interest point.
     * @param longitude  The longitude of this instance of the interest point.
     *                   This method also defines this instance as an Interest Point.
     */

    public InterestPoint (int idLocation, String name, double latitude, double longitude, double altitude){
        super(idLocation,name,latitude,longitude,altitude, LocationType.INTERESTPOINT);
    }

    /**
     * Constructor of an InterestPoint without ID that validates the data inserted and to be used when
     * adding a InterestPoint to the DB
     * @param name  Name of the InterestPoint
     * @param latitude  Latitude of the InterestPoint
     * @param longitude Longitude of the InterestPoint
     * @param altitude  Altitude of the InterestPoint
     */
    public InterestPoint(String name, double latitude, double longitude, double altitude){
        super(name,latitude,longitude,altitude,LocationType.INTERESTPOINT);
    }
}

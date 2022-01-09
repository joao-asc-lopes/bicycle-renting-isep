package lapr.project.model.park;

import java.util.Objects;

public abstract class Location {
    /**
     * Represents an enumerate with all the location types.
     */
    public enum LocationType {
        PARK {
            @Override
            public String toString() {
                return "Park";
            }
        },
        INTERESTPOINT {
            @Override
            public String toString() {
                return "Interest Point";
            }

        }

    }

    /**
     * Variable that represents the identifier of the location.
     */
    private int idLocation;
    /**
     * Variable that represents the name of the location.
     */
    private String name;

    /**
     * Variable that represents the latitude of the location.
     */

    private double latitude;
    /**
     * Variable that represents the longitude of the location.
     */

    private double longitude;

    private double altitude;
    /**
     * Variable that represents the type of the location.
     */
    private LocationType type;

    /**
     * Constructor that builds an instance of location with the attributes passed as a parameter.
     *
     * @param idLocation The id location of this instance of Location.
     * @param name       The name of this instance of Location.
     * @param latitude   The latitude of this instance of Location.
     * @param longitude  The longitude of this instance of Location.
     * @param type       The type of this instance of Location.
     */
    public Location(int idLocation, String name, double latitude, double longitude, double altitude, LocationType type) {
        this.idLocation = idLocation;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.type = type;
    }

    /**
     * Constructor that builds an instance of location without ID that validates the data inserted and must be used
     * to add this location to DB
     * @param name  Name of the location
     * @param latitude  Latitude of the location
     * @param longitude Longitude of the location
     * @param altitude  Altitude of the location
     * @param type  Type of the location
     */
    public Location(String name, double latitude, double longitude, double altitude, LocationType type){
         this.setName(name);
         this.setLatitude(latitude);
         this.setLongitude(longitude);
         this.setType(type);
         this.setAltitude(altitude);
    }

    /**
     * Returns the id location of this instance of idLocation;
     *
     * @return the Id Location of this instance of location.
     */
    public int getIdLocation() {
        return idLocation;
    }

    /**
     * Returns the name of this instance of location.
     *
     * @return The name of this instance of location.
     */

    public String getName() {
        return name;
    }


    /**
     * Returns the latitude of this instance of location.
     *
     * @return The instance of this instance of location.
     */

    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of this instance of location
     *
     * @return the longitude of this instance of location.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Variable that represents the altitude of the location.
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * Returns the type of this instance of location.
     *
     * @return The type of this instance of location.
     */
    public LocationType getType() {
        return this.type;
    }

    /**
     * Allows the user to alter the id location of this instance of location with the new one passed as a parameter.
     *
     * @param idLocation The new id location.
     */
    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    /**
     * Method that allows the user to alter the name of the location with the new one passed as a parameter.
     *
     * @param name The new name of the location.
     */
    public void setName(String name) {
        if(name==null||name.isEmpty()){
            throw new IllegalArgumentException("Name of the location can't be null or empty");
        }
        this.name = name;
    }

    /**
     * Allows the user to alter the altitude of the location with the new one passed as a parameter.
     *
     * @param latitude The new latitude of the location.
     */


    public void setLatitude(double latitude) {
        if(latitude>90||latitude<-90){
            throw new IllegalArgumentException("Latitude must be a value between -90 and 90");
        }
        this.latitude = latitude;
    }

    /**
     * Allows the user to alter the longitude of the location with the new one passed as a parameter.
     *
     * @param longitude The new longitude of the location.
     */

    public void setLongitude(double longitude) {
        if(longitude>180||longitude<-180){
            throw new IllegalArgumentException("Longitude must be a value between -180 and 180");
        }
        this.longitude = longitude;
    }

    public void setType(LocationType type) {
        if(type==null){
            throw new IllegalArgumentException("Type of location can´t be null");
        }
        this.type = type;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    /**
     * Equals method that compares two instances of Location.
     *
     * @param o The other instance passed as a parameter.
     * @return True if they are the same, false if they are not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Location location = (Location) o;
        return idLocation == location.idLocation;
    }

    /**
     * HashCode method of the location.
     *
     * @return Int Representative of the location.
     */

    @Override
    public int hashCode() {
        return Objects.hash(idLocation);
    }

    @Override
    public String toString() {
        return this.name;
    }
}

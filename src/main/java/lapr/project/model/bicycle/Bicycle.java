package lapr.project.model.bicycle;

import lapr.project.utils.InvalidDataException;

import java.util.Objects;

public abstract class Bicycle {

    /**
     * Reprents the possible types of bicycle.
     */
    public enum BicycleType {
        ELECTRIC {
            @Override
            public String toString() {
                return "Electric";
            }
        },
        MOUNTAIN {
            @Override
            public String toString() {
                return "Mountain";
            }
        },
        ROAD {
            @Override
            public String toString() {
                return "Road";
            }
        }
    }

    /**
     * Represents the possible states of a bicycle.
     * Available to be used (1), Already in use (2), or broken / malfunctioning (3).
     */
    public enum BicycleStatus {
        AVAILABLE(1), IN_USE(2), BROKEN(3);
        private final int statusCode;

        /**
         * @param statusCode
         */
        BicycleStatus(final int statusCode) {
            this.statusCode = statusCode;
        }

        /**
         * Returns the associated code with the enum.
         *
         * @return 1 if Available, 2 if in use, 3 if broken.
         */
        public int statusCode() {
            return statusCode;
        }

    }

    /**
     * Identifier of the bicycle in the system.
     */
    private String id;

    /**
     * The status of the bicycle.
     */
    private BicycleStatus bicycleStatus;
    /**
     * The type of the bicycle.
     */
    private BicycleType type;
    /**
     * The weight of the bicycle.
     */
    private float weight;

    /**
     * The bicycles aerodynamical coefficient.
     */
    private double bicycleAerodynamicalCoefficient;

    private double frontalArea;

    /**
     * Creates a bicycle without ID, that validates the data inserted, to be used to add this bicycle
     * to the DB
     * @param status Current status of the bike
     * @param type Type of the bike
     * @param weight Weight of the bike
     * @param bicycleAerodynamicalCoefficient AeroDynamical coefficient of the bicycle
     */
    public Bicycle(String id,BicycleStatus status, BicycleType type, float weight, double bicycleAerodynamicalCoefficient, double frontalArea){
        this.setId(id);
        this.setStatus(status);
        this.setWeight(weight);
        this.setType(type);
        this.setBicycleAeroCoefficient(bicycleAerodynamicalCoefficient);
        this.frontalArea = frontalArea;
    }

    /**
     * Returns the id of the bicycle.
     *
     * @return
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the id of the bicycle.
     *
     * @param id
     */
    public void setId(String id) {
        if(id == null) {
            throw new InvalidDataException("Bicycle ID cannot be null");
        }
        this.id = id;
    }

    /**
     * Gets the status of the bicycle.
     *
     * @return status of the bicycle.
     */
    public BicycleStatus getStatus() {
        return this.bicycleStatus;
    }

    /**
     *  Gets the aerodynamical coeffiecient of the bike.
     * @return The aerodynamical coefficient of the bike.
     */
    public double getBicycleAerodynamicalCoefficient() {
        return this.bicycleAerodynamicalCoefficient;
    }

    /**
     * Allows the user to set the bicycle aero coffiecient with the new one passed as a parameter.
     * @param newAeroCoeffiecient The new coefficient passed as a parameter.
     */
    public void setBicycleAeroCoefficient(double newAeroCoeffiecient){
        if(newAeroCoeffiecient<0){
            throw new IllegalArgumentException("Bicycle aero dynamical coefficient can't be null");
        }
        this.bicycleAerodynamicalCoefficient = newAeroCoeffiecient;
    }

    /**
     * Sets the status of the bicycle.
     *
     * @param bicycleStatus status of the bicycle.
     */
    public void setStatus(BicycleStatus bicycleStatus) {
        if(bicycleStatus==null){
            throw new IllegalArgumentException("Status of the bicycle can't be null");
        }
        this.bicycleStatus = bicycleStatus;
    }


    /**
     * Returns the type of bicycle.
     *
     * @return type of the bicycle.
     */
    public BicycleType getType() {
        return this.type;
    }

    public void setType(BicycleType type) {
        if(type==null){
            throw new IllegalArgumentException("Type of the bicycle can't be null");
        }
        this.type = type;
    }

    /**
     * Returns the weight of the bicycle.
     *
     * @return the weight of the bicycle.
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the bicycle.
     *
     * @param weight of the bicycle.
     */
    public void setWeight(float weight) {
        if(weight>0) {
            this.weight = weight;
        }
        else{
            throw new IllegalArgumentException("The weight of the bicycle can't be 0 or negative");
        }
    }

    /**
     * Compares a given object to the instance of bicycle.
     *
     * @param o some object.
     * @return true if they have the same id, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Bicycle bicycle = (Bicycle) o;
        return id.equals(bicycle.id);
    }

    /**
     * Returns the hashCode of the instance of bicycle based on their id.
     *
     * @return hashCode of the bicycle.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns the corresponding enum based on a code.
     *
     * @param code should be 1, 2 or 3;
     * @return The Bicycle status corresponding.
     */
    public static BicycleStatus statusByCode(int code) {
        if (code == 1) {
            return BicycleStatus.AVAILABLE;
        }
        if (code == 2) {
            return BicycleStatus.IN_USE;
        }
        if (code == 3) {
            return BicycleStatus.BROKEN;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Bike #"+id;
    }

    /**
     * The bicicles frontal area.
     */
    public double getFrontalArea() {
        return frontalArea;
    }


}

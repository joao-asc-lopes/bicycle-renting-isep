package lapr.project.model.bikenetwork;

import lapr.project.utils.InvalidDataException;

import java.util.Objects;

public class Path {
    /**
     * Variable that is representative of the speed of the wind
     */
    private double windSpeed;
    /**
     * Variable that is representative of the different directions of the wind
     */
    private double windDirection;

    /**
     * Contains the electric expense of the connection;
     */
    private double electricExpense;

    /**
     * Represents the aerodynamic coefficient between the two parks.
     */
    private double kineticFriction;
    /**
     * Default electric expense of the path, in case none is calculated.
     */
    private static final int ELECTRIC_EXPENSE_BY_DEFAULT = 0;

    /**
     * @param windSpeed
     * @param windDirection
     * @param electricExpense
     * @param kineticFriction
     */
    public Path(double windSpeed, double windDirection, double electricExpense, double kineticFriction) {
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.electricExpense = electricExpense;
        this.kineticFriction = kineticFriction;
    }

    /**
     * @param windSpeed
     * @param windDirection
     * @param kineticFriction
     */
    public Path(double windSpeed, double windDirection, double kineticFriction) {
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.kineticFriction = kineticFriction;
        this.electricExpense = ELECTRIC_EXPENSE_BY_DEFAULT;
    }

    /**
     * @return
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * @param windSpeed
     */
    public void setWindSpeed(double windSpeed) {
        if (windSpeed < 0.0) {
            throw new InvalidDataException("Wind speed must not be negative");
        }
        this.windSpeed = windSpeed;
    }

    /**
     * @return
     */
    public double getWindDirection() {
        return windDirection;
    }

    /**
     * @param windDirection
     */
    public void setWindDirection(double windDirection) {
        if (windDirection < -180 || windDirection > 360) {
            throw new InvalidDataException("Wind direction must be a value between -180 and 180 or 0 and 360");
        }
        this.windDirection = windDirection;
    }

    /**
     * @return
     */
    public double getElectricExpense() {
        return electricExpense;
    }

    /**
     * @param electricExpense
     */
    public void setElectricExpense(double electricExpense) {
        this.electricExpense = electricExpense;
    }

    /**
     * @return
     */
    public double getKineticFriction() {
        return kineticFriction;
    }

    /**
     * @param kineticFriction
     */
    public void setKineticFriction(double kineticFriction) {
        if (kineticFriction < 0.0) {
            throw new InvalidDataException("Kinetic Friction  must not be negative");
        }
        this.kineticFriction = kineticFriction;
    }

    /**
     * Equals for a path.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Path path = (Path) o;
        return Double.compare(path.electricExpense, electricExpense) == 0 &&
                Double.compare(path.kineticFriction, kineticFriction) == 0;

    }

    /**
     * Hashcode for a path.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(electricExpense, kineticFriction);
    }


}
package lapr.project.model.bikenetwork;

import lapr.project.model.park.Location;
import lapr.project.utils.InvalidDataException;

import java.util.LinkedList;
import java.util.Objects;

public class Route implements Comparable<Route> {
    /**
     * The locations that the route passes through.
     */
    private LinkedList<Location> locations;
    /**
     * Number of locations in the path.
     */
    private int numberLocations;

    /**
     * The total distance of the route.
     */
    private double totalDistance;
    /**
     * The total energy expense of the route.
     */
    private double totalEnergy;
    /**
     * Average elevation of the route [(final location - initial location) / 2]
     */
    private double elevation;
    /**
     * The value of distance, energy and elevation by default.
     */
    private static final double VALUES_BY_DEFAULT = 0;

    /**
     * Creates an instance of route and initializes the locations.
     */
    public Route() {
        this.totalDistance = VALUES_BY_DEFAULT;
        this.totalEnergy = VALUES_BY_DEFAULT;
        this.elevation = VALUES_BY_DEFAULT;
        this.numberLocations = 0;
        this.locations = new LinkedList<>();
    }

    /**
     * Instantiates a route with a given list of locations.
     *
     * @param list List of locations.
     */
    public Route(LinkedList<Location> list) {
        if (list == null) {
            throw new InvalidDataException("List is null");
        }
        this.locations = list;
        this.numberLocations += list.size();
    }

    /**
     * Adds a location to the route.
     *
     * @param location Adds a location to the route.
     */
    public void addLocation(Location location) {
        this.locations.addLast(location);
        this.numberLocations++;
    }

    /**
     * Sets the totalDistance of the route.
     *
     * @param totalDistance Weight of the route.
     */
    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    /**
     * Returns the total distance of the route.
     *
     * @return total distance of the route.
     */
    public double getTotalDistance() {
        return this.totalDistance;
    }

    /**
     * Returns the total energy expense in the route.
     *
     * @return the total energy expense in the route.
     */
    public double getTotalEnergy() {
        return totalEnergy;
    }

    /**
     * Returns the elevation of the route.
     *
     * @return elevation of the route.
     */
    public double getElevation() {
        return elevation;
    }

    /**
     * Sets a new elevation for the route.
     *
     * @param elevation new elevation.
     */
    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    /**
     * Returns the number of locations in the Route.
     *
     * @return Number of locations in route.
     */
    public int getNumberLocations() {
        return this.numberLocations;
    }

    /**
     * Sets a new total energy expense for the route.
     *
     * @param totalEnergy new total energy expense.
     */
    public void setTotalEnergy(double totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    /**
     * Returns the locations in the route in order of traversal.
     *
     * @return iterator with all the locations in the route in order of traversal.
     */
    public Iterable<Location> getPath() {
        return this.locations;
    }

    /**
     * Adds a list of locations to the locations of the route.
     *
     * @param list List with locations.
     */
    public void addLocations(LinkedList<Location> list) {
        if (list != null) {
            this.locations.addAll(list);
        }
    }

    /**
     * Compares this route's distance with the specified route for order.  Returns a
     * negative integer, zero, or a positive integer as this route's distance is less
     * than, equal to, or greater than the specified object.
     *
     * @param otherRoute the route to be compared.
     * @return a negative integer, zero, or a positive integer as this route's distance
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Route otherRoute) {
        return Double.compare(this.totalDistance, otherRoute.totalDistance);
    }

    /**
     * Compares this route's total energy with the specified route for order.  Returns a
     * negative integer, zero, or a positive integer as this route's distance is less
     * than, equal to, or greater than the specified object.
     *
     * @param otherRoute the route to be compared.
     * @return a negative integer, zero, or a positive integer as this route's distance
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    public int compareEnergy(Route otherRoute) {
        return Double.compare(this.totalEnergy, otherRoute.totalEnergy);
    }

    /**
     * Equals method.
     *
     * @param o Other object.
     * @return true if equal, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route route = (Route) o;
        return Double.compare(route.totalDistance, totalDistance) == 0 &&
                Double.compare(route.totalEnergy, totalEnergy) == 0 &&
                Double.compare(route.elevation, elevation) == 0 &&
                locations.equals(route.locations);
    }

    /**
     * Generates hashcode for the route.
     *
     * @return hashcode of route.
     */
    @Override
    public int hashCode() {
        return Objects.hash(locations, totalDistance, totalEnergy, elevation);
    }
}

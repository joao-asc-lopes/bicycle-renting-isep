/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller.user;

import lapr.project.model.bikenetwork.Path;
import lapr.project.model.bikenetwork.PathFacade;
import lapr.project.model.park.Location;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;
import lapr.project.utils.PhysicsAlgorithms;

import java.io.IOException;
import java.util.List;

public class BurntCaloriesController {

    /**
     * Variable
     */
    private LocationFacade pf;
    private PathFacade wf;

    public BurntCaloriesController() {
        this.pf = new LocationFacade();
        this.wf = new PathFacade();
    }

    /**
     * Method that return the parkRegistry in BicycleNetwork
     *
     * @return List with parks
     */
    public List<Park> getParkRegistry() {
        return this.pf.getParkList();
    }

    /**
     * Get the Park by is ID
     *
     * @param id ID of the park
     * @return Park with the same ID as received
     */
    public Park getParkById(int id) {
        try {
            return pf.getParkById(id);
        } catch (InvalidDataException e) {
            return null;
        }
    }

    /**
     * Method that returns the wind information.
     *
     * @param from The idPark.
     * @param to   The idPark that is the destination.
     * @return An instance of WindInformation.
     */
    public Path getPathBy2Parks(Location from, Location to) {
        return this.wf.getPath(from, to);
    }

    /***
     *  Method that allows the calculus of the burnt calories.
     * @param vm The average speed the user achieved.
     * @param bikeWeight The bike weight.
     * @param userWeight The user weight.
     * @param userHeight The user height.
     * @param latitude1 THe latitude of the park x.
     * @param longitude1 The longitude of the park x.
     * @param latitude2 The latitude of the park y.
     * @param longitude2 The longitude of the park y.
     * @param bikeAeroCoefficient The bike aerodynimcal Coefficient.
     * @return A double representative of the burnt calories in the simulated trip in cal.
     * @throws IOException
     */
    public double calculateBurntCalories(double vm, double bikeWeight, double frontArea, double userWeight, double userHeight, double latitude1, double longitude1, double latitude2, double longitude2, double bikeAeroCoefficient, double altitudeInitial, double altitudeFinal, double windDirection, double windSpeed) {
        try {

            return PhysicsAlgorithms.calculateCalories(vm, bikeWeight, frontArea, userWeight, userHeight, latitude1, longitude1, latitude2, longitude2, bikeAeroCoefficient, altitudeInitial, altitudeFinal, windSpeed, windDirection);
        } catch (InvalidDataException e) {
            return -1;
        }
    }
}

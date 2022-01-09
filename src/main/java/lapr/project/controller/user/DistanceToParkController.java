/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lapr.project.controller.user;

import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.park.Location;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.util.List;


public class DistanceToParkController {
    /**
     * Variable that represents a LocationFacade.
     */
    private LocationFacade pf;
    /**
     * Variable that represents a bicycle network.
     */
    private BicycleNetwork bikeNet;

    /**
     * Constructor that opens up a connection to both LocationFacade and BicycleNetwork.
     */

    public DistanceToParkController() {
        this.pf = new LocationFacade();
        this.bikeNet = new BicycleNetwork();
    }

    /**
     * Método que devolve os parks que estão no registo da BicycleNetwork
     *
     * @return Lista de parks na BicycleNetwork
     */
    public List<Park> getParkRegistry() {
        return this.pf.getParkList();
    }

    /**
     * Método que devolve a distância desde o park recebido com as coordenadas do
     * User
     *
     * @param parkId    Id do park
     * @param latitude  Latitude do User
     * @param longitude Longitude do User
     * @return Distância do User ao Park
     * @throws InvalidDataException Quando o ID do park não se encontra na BicycleNetwork
     */
    public double getDistanceToPark(int parkId, double latitude, double longitude) throws InvalidDataException {
        Location p = this.pf.getParkById(parkId);
        if (p == null) {
            throw new InvalidDataException();
        }
        return this.bikeNet.distanceUserPark(latitude, longitude, p);
    }
}

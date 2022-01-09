/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lapr.project.controller.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.BicycleFacade;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import lapr.project.model.user.Rental;
import lapr.project.model.user.RentalService;
import lapr.project.model.user.User;
import lapr.project.model.user.UserFacade;
import lapr.project.utils.EmailHandler;

import java.util.List;


public class LockBicycleController {
    /**
     * Variable that represents a private instance of rental service.
     */
    private RentalService rs;
    /**
     * Variable that represents a private instance of LocationFacade.
     */
    private LocationFacade pf;
    /**
     * Variable that represents a private instance of BicycleFacade.
     */
    private BicycleFacade bf;
    /**
     * Instance of EmailHandler
     */
    private EmailHandler ec;

    /**
     * Instance of UserFacade
     */
    private UserFacade uf;

    /**
     * Constructor. Instanciates the diverse connections of the private instances.
     */

    public LockBicycleController() {
        this.rs = new RentalService();
        this.uf = new UserFacade();
        this.ec = new EmailHandler();
        this.pf = new LocationFacade();
        this.bf = new BicycleFacade();
    }

    /**
     * Method that allows to give the users points based on his parking.
     *
     * @param thisPark The park where he parked the bicycle.
     * @param park     The old park.
     * @param user     The user that parked the bicycle.
     * @return True if awarded , False if an error occured.
     */

    public boolean awardUserPoints(Park thisPark, Park park, User user) {
        int points = new BicycleNetwork().calculatePoints(thisPark, park);
        return this.uf.awardUserPoints(user.getEmail(), points);
    }

    /**
     * Returns the current/last User to rent a specific Bicycle
     *
     * @param idBike
     * @return the User
     */
    public User getBicycleUser(String idBike) {
        return this.uf.getBicycleUser(idBike);
    }

    /**
     * Returns the last park where a specific Bicycle was parked
     *
     * @param idBike
     * @return the Park
     */
    public Park getStartingPark(String idBike) {
        return this.uf.getStartingPark(idBike);
    }

    /**
     * Método que envia um email para o utilizador a dizer que a bicicleta
     * foi corretamente trancada
     *
     * @param bicycleId Id da bicicleta
     * @param park      Park onde a bicicleta foi trancada
     * @return True se o método correr com sucesso
     */
    public boolean sendLockConfirmationEmail(String bicycleId, Park park) {
        Rental rental = rs.getActiveRental(bicycleId);
        String email = rental.getUser().getEmail();
        this.uf.updateRentalStatus(rental.getUser(), false);


        return ec.sendEmail(email, park.getName());
    }

    /**
     * Method that updates the park slots when a user locks his bicycle
     *
     * @param bicycleId BicycleId from the bicycle that was locked
     * @param parkId    Park where the user locked the bicycle
     * @return True if operation works with success, false otherwise
     */
    public boolean updateParkSlot(String bicycleId, int parkId) {
        Bicycle bike = getBicycle(bicycleId);
        Park park = pf.getParkById(parkId);
        if (bike != null && park != null) {
            if (bike instanceof ElectricBicycle) {
                ElectricSlot es = park.getEletricalSlots();
                int space = es.getNumberFreeSlots();
                if (space > 0) {
                    return pf.bikeIntoPark(bicycleId, park.getIdLocation());
                } else {
                    return false;
                }
            } else {
                NormalSlots ns = park.getNormalSlots();
                int space = ns.getNumberFreeSlots();
                if (space > 0) {
                    return pf.bikeIntoPark(bicycleId, park.getIdLocation());
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Method that searches for the Bicycle object with the same ID as the received
     *
     * @param bicycleId ID of the bicycle
     * @return Bicycle if ID found on the DB, null otherwise
     */
    private Bicycle getBicycle(String bicycleId) {
        List<Bicycle> array = bf.getAllBicycles();
        Bicycle bike = null;
        for (Bicycle b : array) {
            if (b.getId() == bicycleId) {
                bike = b;
            }
        }
        return bike;
    }

}

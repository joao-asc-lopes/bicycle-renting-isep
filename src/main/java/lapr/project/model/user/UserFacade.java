package lapr.project.model.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.util.List;

public class UserFacade {
    /**
     * Variable that is representative of the UserService.
     */
    private UserService us;
    /**
     * Variable that is representative of Rental Service.
     */
    private RentalService rs;

    /**
     * Constructor that opens up this connection.
     */

    public UserFacade() {
        this.us = new UserService();
        this.rs = new RentalService();
    }

    public void createRental(String bikeId, Park pickUp,User user) {
        this.us.rentBicycle(bikeId, pickUp,user);
    }

    /**
     * Creates a new instance of user, saving him in the database and extracting the initial payment from the given credit card.
     *
     * @param email    The new user's email.
     * @param password The new user's non-encrypted password.
     * @param name     The new user's name.
     * @param weight   The new user's weight.
     * @param height   The new user's height.
     * @param ccNumber The new user's credit card number.
     * @return true if user was registered, false if not.
     */
    public boolean registerUser(String username, String email, String password, String name, float weight, float height, long ccNumber,double averageSpeed) {
        return us.registerUser( username, email, password, name, weight, height, ccNumber,averageSpeed);
    }

    /**
     * Returns the current/last User to rent a specific Bicycle
     *
     * @param idBike
     * @return the User
     */
    public User getBicycleUser(String idBike) {
        return rs.getBicycleUser(idBike);
    }

    /**
     * Returns the last park where a specific Bicycle was parked
     *
     * @param idBike
     * @return the Park
     */
    public Park getStartingPark(String idBike) {
        return rs.getStartingPark(idBike);
    }

    /**
     * Updates the rental status of a given user.
     *
     * @param user   User to be updated.
     * @param status Rental status to be updated.
     * @return true if the operation was successful or false if not.
     */
    public boolean updateRentalStatus(User user, boolean status) {
        try {
            us.updateRentalStatus(user, status);
            return true;
        } catch (InvalidDataException e) {
            return false;
        }
    }

    /**
     * Method that awards a certain number of points to a User
     *
     * @param userMail
     * @param points
     * @return
     */
    public boolean awardUserPoints(String userMail, int points) {
        return this.us.awardUserPoints(userMail, points);
    }

    /**
     * Method that gets a user from the DB
     *
     * @param username email of the User
     * @return User object
     */
    public User getUser(String username) {
        return this.us.getUser(username);
    }

    /**
     * Method that creates a receipt
     *
     * @param value Value of the receipt
     * @param iva   Iva of the receipt
     * @return ID of the receipt created
     */
    public int createReceipt(double value, int iva) {
        return this.us.createReceipt(value, iva);
    }

    /**
     * Method that gets all unpaid rentals from the user
     *
     * @param username email of the user
     * @return List with the rental unpaid
     */
    public List<Rental> getUnpaidRentalsOfMonth(String username,int month) {
        return this.rs.getUnpaidRentalsOfMonth(username, month);
    }
    /**
     * Method that gets all unpaid rentals from the user
     *
     * @param username email of the user
     * @return List with the rental unpaid
     */
    public List<Rental> getUnpaidRentalsTotal(String username) {
        return this.rs.getUnpaidRentalsTotal(username);
    }

    /**
     * Method that associates a receipt with a rental in the DB
     *
     * @param idRental  ID of the rental
     * @param idReceipt ID of the receipt
     * @return True if works with success, false otherwise
     */
    public boolean insertReceiptRental(int idRental, int idReceipt) {
        return this.us.insertReceiptRental(idRental, idReceipt);
    }


    /**
     * Method that calculates the value from the sum of the length of the rentals received
     * @param lista List of rentals
     * @return The value user must pay
     */
    public double calculateValue(List<Rental> lista, User u){
        return this.rs.calculateValue(lista, u);
    }

    /**
     * Method that gets all rentals
     *
     * @return List with the rentals
     */
    public List<Rental> getAllRentals(){
        return this.rs.getAllRentals();
    }

    public Bicycle getUserActiveRentalBicycle(String username){
        return this.rs.getBicycleUserActiveRental(username);
    }
}

package lapr.project.model.user;

import lapr.project.data.BicycleDao;
import lapr.project.data.RentalDao;
import lapr.project.data.UserDao;
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.time.LocalDateTime;

public class UserService {

    /**
     * Makes the connection  between the service and the database.
     */
    private UserDao userDao;
    /**
     * Variable that is representative of rentaldAO.
     */

    private RentalDao rentalDao;

    private BicycleDao bicycleDao;

    /**
     * Instantiates the UserService class, creating a new UserDao instance.
     */
    public UserService() {
        this.userDao = new UserDao();
        this.rentalDao = new RentalDao();
        this.bicycleDao = new BicycleDao();
    }

    /**
     * Verifies and returns the registry of a user if he exists in the database, given his email.
     *
     * @param username email of the user to be found.
     * @return the User registry if he exists, null if not.
     */
    public User getUser(String username) {
        return userDao.getUser(username);
    }

    /**
     * Removes a user from the database.
     *
     * @param username from the user to be removed.
     * @return true if the user was removed, false if not.
     */
    public boolean removeUser(String username) {
        return this.userDao.removeUser(username);
    }

    /**
     * Saves a created user in the database.
     */
    private boolean save(User u) {
        try {
            getUser(u.getUsername());
            return false;
        } catch (IllegalArgumentException ex) {
            //If the record does not exist, save it
            this.userDao.addUser(u);
            return true;
        }
    }

    /**
     * Registers a user in the system.
     *
     * @param email    The new user's email.
     * @param password The new user's non-encrypted password.
     * @param name     The new user's name.
     * @param weight   The new user's weight.
     * @param height   The new user's height.
     * @param ccNumber The new user's credit card number.
     */
    public boolean registerUser(String username, String email, String password, String name, float weight, float height, long ccNumber,double averageSpeed) {
        User u = new User(username,email, password, name, weight, height, ccNumber, false,averageSpeed,0);
        return this.save(u);
    }


    /**
     * Rents a bicycle for the currently logged in user.
     *
     * @param bike bicycle that should be rented.
     */
    public void rentBicycle(String bike, Park pickUp, User user) {
        this.updateRentalStatus(user, true);
        Bicycle b = this.bicycleDao.getBicycle(bike);
        Rental r = new Rental(0, b, user,pickUp, LocalDateTime.now());
        this.rentalDao.addRental(r);
    }

    /**
     * @param user            User to be updated.
     * @param hasActiveRental Determines if the user has an active rental.
     */
    public void updateRentalStatus(User user, boolean hasActiveRental) {
        if (user.hasActiveRental()) {
            throw new InvalidDataException("User already has a rented bicycle.");
        }
        user.activateRental();
        this.userDao.updateRentalStatus(user.getUsername(), hasActiveRental);
    }

    /**
     * Method that awards a certain number of points to a User
     *
     * @param username
     * @param points
     * @return
     */
    public boolean awardUserPoints(String username, int points) {
        return this.userDao.awardUserPoints(username, points);
    }

    /**
     * Method that creates a receipt
     *
     * @param value Value of the receipt
     * @param iva   Iva of the receipt
     * @return ID of the receipt created
     */
    public int createReceipt(double value, int iva) {
        return this.userDao.createReceipt(value, iva);
    }



    /**
     * Method that associates a receipt with a rental in the DB
     *
     * @param idRental  ID of the rental
     * @param idReceipt ID of the receipt
     * @return True if works with success, false otherwise
     */
    public boolean insertReceiptRental(int idRental, int idReceipt) {
        return this.userDao.insertReceiptRental(idRental, idReceipt);
    }


}

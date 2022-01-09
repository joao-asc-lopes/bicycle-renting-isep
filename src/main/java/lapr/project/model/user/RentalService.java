package lapr.project.model.user;

import lapr.project.data.BicycleDao;
import lapr.project.data.RentalDao;
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.park.Park;

import java.time.LocalDateTime;
import java.util.List;


public class RentalService {
    /**
     * Variable representative of the parkDao.
     */
    private RentalDao rentalDao;

    private BicycleDao bicycleDao;


    /**
     * Instanciates the RentalService class, instanciating a new connection.
     */
    public RentalService() {
        this.rentalDao = new RentalDao();
        this.bicycleDao = new BicycleDao();
    }

    /**
     * Verifies and returns a Rental given his idRental from the database.
     *
     * @param id The id of the rental that we are trying to get.
     * @return The rental (if exists) with the id given has a parameter.
     */
    public Rental getRental(int id) {
        return rentalDao.getRental(id);
    }

    /**
     * Removes the rental with the id given has a parameter.
     *
     * @param idRental The id of the park passed as a parameter.
     * @return True if it removed the rental, false if not.
     */
    public boolean removeRental(int idRental) {
        return this.rentalDao.removeRental(idRental);
    }

    /**
     * Saves a created rental in the database.
     *
     * @return True if saved, False if not.
     */
    private boolean save(Rental r) {

        Rental returned = getRental(r.getId());
        if (returned == null) {
            return this.rentalDao.addRental(r);
        } else {
            return false;
        }
    }

    /**
     * Adds a rental to the DB
     *
     * @param id            id of the rental
     * @param idBike        if of the bike
     * @param user          user associated with the rental
     * @param unlockDate   Date of the unlock
     * @param lockDate     Date of the lock
     * @param state         State of the rental
     * @return True if operation can add
     */
    public boolean addRental(int id, String idBike, User user,Park pickUp, Park leftAt, LocalDateTime unlockDate, LocalDateTime lockDate, Rental.RentalStatus state) {
        Bicycle b = this.bicycleDao.getBicycle(idBike);
        Rental r = new Rental(id, b, user,pickUp,leftAt, unlockDate, lockDate, state);
        return this.save(r);
    }

    /**
     * Adds a rental to the DB
     *
     * @param id          id of the rental
     * @param idBike      if of the bike
     * @param user        user associated with the rental
     * @param unlockDate Date of the unlock
     * @return True if operation can add
     */
    public boolean addRental(int id, String idBike, User user,Park pickUp, LocalDateTime unlockDate) {
        Bicycle b = this.bicycleDao.getBicycle(idBike);
        Rental r = new Rental(id, b, user,pickUp, unlockDate);
        return this.save(r);
    }

    /**
     * Method that gets all unpaid rentals from the user
     *
     * @param username username of the user
     * @return List with the rental unpaid
     */
    public List<Rental> getUnpaidRentalsOfMonth(String username, int month) {
        return this.rentalDao.getUnpaidRentalsOfMonth(username, month);
    }

    /**
     * Method that gets all unpaid rentals from the user
     *
     * @param username username of the user
     * @return List with the rental unpaid
     */
    public List<Rental> getUnpaidRentalsTotal(String username) {
        return this.rentalDao.getUnpaidRentalsTotal(username);
    }

    /**
     * Gets a active rental from DB
     *
     * @param idBike id of the bike
     * @return Rental if exists, null otherwise
     */
    public Rental getActiveRental(String idBike) {
        try {
            return this.rentalDao.getActiveRental(idBike);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    /**
     * Returns the current/last User to rent a specific Bicycle
     * @param idBike
     * @return the User
     */
    public User getBicycleUser(String idBike){
        return rentalDao.getBicycleUser(idBike);
    }
    /**
     * Returns the last park where a specific Bicycle was parked
     * @param idBike
     * @return the Park
     */
    public Park getStartingPark(String idBike){
        return rentalDao.getStartingPark(idBike);
    }

    /**
     * Method that calculates the value from the sum of the length of the rentals received
     * @param lista List of rentals
     * @return The value user must pay
     */
    public double calculateValue(List<Rental> lista, User u){
        double ret = 0;
        for(Rental r : lista) {
            long length = r.getLength();
            if(length>3600){
                length-=3600;
                ret+=3*length/(double)3600;
            }
        }

        if (u.getPoints() >= 10 && ret >= 1.0){
            int aux = u.getPoints();
            while(aux >= 10 && ret-1>0){
                ret--;
                aux = aux - 10;
            }
            u.setPoints(aux);
        }

        return ret;
    }

    public long getTimeBicycleUnlocked(String idBicycle){
        return this.rentalDao.getTimeBicycleUnlocked(idBicycle);
    }

    public Bicycle getBicycleUserActiveRental(String username){
        return this.rentalDao.getBicycleUserActiveRental(username);
    }

    /**
     * Method that gets all rentals
     *
     * @return List with the rentals
     */
    public List<Rental> getAllRentals(){
        return this.rentalDao.getAllRentals();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.data;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.park.Location;
import lapr.project.model.park.Park;
import lapr.project.model.user.Rental;
import lapr.project.model.user.User;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RentalDao extends DataHandler {

    private static final String noRentals = "There are no Rentals in the database.";

    private UserDao ud;

    private BicycleDao bd;

    public RentalDao(){
        super();
        this.ud = new UserDao();
        this.bd = new BicycleDao();
    }

    /**
     * Gets a Rental from DB
     *
     * @param idRental id of the rental
     * @return Rental if exists
     * @throws IllegalArgumentException When the rental searched on the DB doesn't exist
     */
    public Rental getRental(int idRental) {

        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call get_rental(?) }");

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getUser".
            callStmt.setInt(2, idRental);

            // Executa a invocação da função "getUser".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            if (rSet.next()) {
                int rentalId = rSet.getInt(1);
                String bicycleId = rSet.getString(2);
                String userId = rSet.getString(3);
                LocationDao l = new LocationDao();
                Park pickUp = l.getPark(rSet.getInt(4));
                Park leftAt = l.getPark(rSet.getInt(5));
                LocalDateTime unlockDate = rSet.getTimestamp(6).toLocalDateTime();

                LocalDateTime lockDate;
                if (rSet.getTimestamp(7) != null) {
                    lockDate = rSet.getTimestamp(7).toLocalDateTime();
                } else {
                    lockDate = null;
                }
                int state = rSet.getInt(9);
                Rental.RentalStatus status;
                if (state == 1) {
                    status = Rental.RentalStatus.ACTIVE;
                } else {
                    status = Rental.RentalStatus.FINISHED;
                }
                Rental.RentalPayment payment;
                if (state == 1) {
                    payment = Rental.RentalPayment.NOT_PAID;
                } else {
                    payment = Rental.RentalPayment.PAID;
                }
                User user = this.ud.getUser(userId);
                Bicycle b = this.bd.getBicycle(bicycleId);
                return new Rental(rentalId, b, user,pickUp,leftAt, unlockDate, lockDate,status,payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Rental with the id: " + idRental);
    }

    /**
     * Adds a rental to DB
     *
     * @param r Rental to be added
     * @return True if method was able to add the rental to DB
     */
    public boolean addRental(Rental r) {
        if (r.getLockDate() == null && r.getLength() == 0) {
            return addRental(r.getBike(), r.getUser().getUsername(), r.getPickUpPark(),null,Timestamp.valueOf(r.getUnlockDate()),
                    null, r.getState().statusCode(),r.getRentalPayment().paymentCode());
        } else {
            return addRental(r.getBike(), r.getUser().getUsername(), r.getPickUpPark(),r.getLeftAtPark(),Timestamp.valueOf(r.getUnlockDate()),
                    Timestamp.valueOf(r.getLockDate()), r.getState().statusCode(),r.getRentalPayment().paymentCode());
        }
    }

    /**
     * Adds a rental to DB
     *
     * @param bicycle       Bike
     * @param userId        email of the user
     * @param unlockDate   Date of the unlock
     * @param lockDate     Date of the lock (if exists)
     * @param state         State of the rental
     * @return True if method was able to add the rental to DB
     */
    private boolean addRental(Bicycle bicycle, String userId, Park pickUp, Park leftAt, Timestamp unlockDate, Timestamp lockDate, int state, int payment) {
        try {
            openConnection();
            CallableStatement callStmt = getConnection().prepareCall("{ call add_rental(?,?,?,?,?,?,?,?) }");
            callStmt.setString(1, bicycle.getId());
            callStmt.setString(2, userId);
            callStmt.setInt(3,pickUp.getIdLocation());
            callStmt.setInt(4,leftAt.getIdLocation());
            callStmt.setTimestamp(5, unlockDate);
            callStmt.setTimestamp(6, lockDate);
            callStmt.setInt(7, state);
            callStmt.setInt(8,payment);
            callStmt.execute();
            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Method that gets all rentals
     *
     * @return List with the rentals
     */
    public List<Rental> getAllRentals() {
        CallableStatement callStmt = null;
        List<Rental> array = new ArrayList<>();
        try {
            callStmt = getConnection().prepareCall("{ ? = call get_all_rentals() }");
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            while (rSet.next()) {
                int idRental = rSet.getInt(1);
                String idBike = rSet.getString(2);
                String userEmail = rSet.getString(3);
                Park pickUp =  new LocationDao().getPark(rSet.getInt(4));
                Park leftAt =  new LocationDao().getPark(rSet.getInt(5));
                LocalDateTime unlock = rSet.getTimestamp(6).toLocalDateTime();
                LocalDateTime lock = rSet.getTimestamp(7).toLocalDateTime();
                int state = rSet.getInt(9);
                Rental.RentalStatus status;
                if (state == 1) {
                    status = Rental.RentalStatus.ACTIVE;
                } else {
                    status = Rental.RentalStatus.FINISHED;
                }
                User u = this.ud.getUser(userEmail);
                Bicycle b = this.bd.getBicycle(idBike);
                array.add(new Rental(idRental, b, u, pickUp,leftAt,unlock, lock, status));
            }
            return array;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(noRentals);
    }
    /**
     * Method that gets all unpaid rentals from the user received in a month
     *
     * @param username username of the user
     * @return List with the rentals that user didn't pay
     */
    public List<Rental> getUnpaidRentalsOfMonth(String username, int month) {
        CallableStatement callStmt = null;
        List<Rental> array = new ArrayList<>();
        try {
            callStmt = getConnection().prepareCall("{ ? = call get_all__rentals_month(?,?) }");

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parÃ¢metro de entrada da funÃ§Ã£o "getUser".
            callStmt.setString(2, username);
            callStmt.setInt(3, month);
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            while (rSet.next()) {
                int idRental = rSet.getInt(1);
                String idBike = rSet.getString(2);
                String userEmail = rSet.getString(3);
                Park pickUp =  new LocationDao().getPark(rSet.getInt(4));
                Park leftAt =  new LocationDao().getPark(rSet.getInt(5));
                LocalDateTime unlock = rSet.getTimestamp(6).toLocalDateTime();
                LocalDateTime lock = rSet.getTimestamp(7).toLocalDateTime();
                int state = rSet.getInt(9);
                Rental.RentalStatus status;
                if (state == 1) {
                    status = Rental.RentalStatus.ACTIVE;
                } else {
                    status = Rental.RentalStatus.FINISHED;
                }
                User u = this.ud.getUser(userEmail);
                Bicycle b = this.bd.getBicycle(idBike);
                array.add(new Rental(idRental, b, u, pickUp,leftAt,unlock, lock, status));
            }
            return array;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No User with email: " + username);
    }
    /**
     * Method that gets all unpaid rentals from the user received
     *
     * @param username username of the user
     * @return List with the rentals that user didn't pay
     */
    public List<Rental> getUnpaidRentalsTotal(String username) {
        CallableStatement callStmt = null;
        List<Rental> array = new ArrayList<>();
        try {
            callStmt = getConnection().prepareCall("{ ? = call get_all_unpaid_rentals(?) }");

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parÃ¢metro de entrada da funÃ§Ã£o "getUser".
            callStmt.setString(2, username);
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            while (rSet.next()) {
                int idRental = rSet.getInt(1);
                String idBike = rSet.getString(2);
                String userEmail = rSet.getString(3);
                Park pickUp =  new LocationDao().getPark(rSet.getInt(4));
                Park leftAt =  new LocationDao().getPark(rSet.getInt(5));
                LocalDateTime unlock = rSet.getTimestamp(6).toLocalDateTime();
                LocalDateTime lock = rSet.getTimestamp(7).toLocalDateTime();
                int state = rSet.getInt(9);
                Rental.RentalStatus status;
                if (state == 1) {
                    status = Rental.RentalStatus.ACTIVE;
                } else {
                    status = Rental.RentalStatus.FINISHED;
                }
                User u = this.ud.getUser(userEmail);
                Bicycle b = this.bd.getBicycle(idBike);
                array.add(new Rental(idRental, b, u, pickUp,leftAt,unlock, lock, status));
            }
            return array;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No User with email: " + username);
    }

    /**
     * Gets a active rental from DB
     *
     * @param id id of the bike
     * @return Rental if exists
     * @throws IllegalArgumentException Exception when no Rental was found on DB
     */
    public Rental getActiveRental(String id) {
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call get_active_rental(?) }");

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getUser".
            callStmt.setString(2, id);

            // Executa a invocação da função "getUser".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int rentalId = rSet.getInt(1);
                String bicycleId = rSet.getString(2);
                String userId = rSet.getString(3);
                Park pickUp = new LocationDao().getPark(rSet.getInt(4));
                LocalDateTime unlockDate = rSet.getTimestamp(5).toLocalDateTime();
                User user = this.ud.getUser(userId);
                Bicycle b = this.bd.getBicycle(bicycleId);
                return new Rental(rentalId, b, user,pickUp, unlockDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Rental with id: " + id);
    }

    /**
     * Removes a rental from DB
     *
     * @param idRental if of the rental
     * @return True if method was able to remove rental from DB
     */
    public boolean removeRental(int idRental) {

        try {
            openConnection();

            CallableStatement callStmt = getConnection().prepareCall("{ call remove_rental(?) }");

            callStmt.setInt(1, idRental);

            callStmt.execute();

            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Returns the current/last User to rent a specific Bicycle
     * @param idBike
     * @return the User
     */
    public User getBicycleUser(String idBike){
        try {
            CallableStatement callStmt = getConnection().prepareCall("{ ? = call get_bicycle_current_user(?) }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, idBike);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String idUser = rSet.getString(1);
                return this.ud.getUser(idUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Rental for the Bicycle with id: " + idBike);
    }

    /**
     * Returns the last park where a specific Bicycle was parked
     * @param idBike
     * @return the Park
     */
    public Park getStartingPark(String idBike) {
        try {
            CallableStatement callStmt = getConnection().prepareCall("{ ? = call get_starting_park(?) }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, idBike);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int idPark = rSet.getInt(1);
                Location p = new LocationDao().getPark(idPark);
                if(p instanceof Park){
                    return (Park)p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Rental for the bicycle with id: " + idBike);
    }

    public long getTimeBicycleUnlocked(String idBicycle){
        try {
            CallableStatement callStmt = getConnection().prepareCall("{ ? = call get_time_bicycle_unlocked(?) }");

            callStmt.registerOutParameter(1, OracleTypes.NUMBER);

            callStmt.setString(2, idBicycle);

            callStmt.execute();

            return callStmt.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Rental for the bicycle with id: " + idBicycle);
    }

    public Bicycle getBicycleUserActiveRental(String username){
        try {
            CallableStatement callStmt = getConnection().prepareCall("{ ? = call get_bicycle_user_active_rental(?) }");

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);

            callStmt.setString(2, username);

            callStmt.execute();

            String idBicycle = callStmt.getString(1);
            return new BicycleDao().getBicycle(idBicycle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Active Rental from the user: " + username);
    }

}

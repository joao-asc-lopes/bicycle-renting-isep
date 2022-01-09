package lapr.project.data;

import lapr.project.model.user.User;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserDao extends DataHandler {

    /**
     * Returns the specified registry in the table "Users".
     *
     * @param username the user's email.
     * @return the registry of the user in case he exists, null otherwise.
     */
    public User getUser(String username)  {
        CallableStatement callStmt;
        try {
            callStmt = getConnection().prepareCall("{ ? = call get_user(?) }");

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getUser".
            callStmt.setString(2, username);

            // Executa a invocação da função "getUser".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String usernameUser = rSet.getString(1);
                String userEmail = rSet.getString(2);
                String encryptedPassword = rSet.getString(3);
                String salt = rSet.getString(4);
                String userName = rSet.getString(5);
                float weight = rSet.getFloat(6);
                float height = rSet.getFloat(7);
                long ccNumber = rSet.getLong(8);
                boolean hasActiveRental = false;
                if (rSet.getInt(9) == 1) {
                    hasActiveRental = true;
                }
                double averageSpeed = rSet.getDouble(10);
                return new User(usernameUser,userEmail, encryptedPassword, salt, userName, weight, height, ccNumber, hasActiveRental,averageSpeed);
            }
            return null;
        } catch (SQLException e) {
            throw new IllegalArgumentException("No User with username "+username);
        }
    }

    /**
     * Adds a user to the database.
     *
     * @param user to be added.
     */
    public boolean addUser(User user) {
        return addUser(user.getUsername(), user.getEmail(), user.getEncryptedPassword(), user.getSalt(), user.getName(), user.getWeight(), user.getHeight(), user.getCCNumber(), user.hasActiveRental(), user.getAverageSpeed());
    }

    /**
     * Uses a stored procedure to add a user to the database.
     * Adds a User to the table "Users".
     *
     * @param email           The user's email.
     * @param password        The user's password.
     * @param salt            The salt used in the creation of the user's password, needed for login purposes.
     * @param name            The user's name.
     * @param weight          The user's weight.
     * @param height          The user's height.
     * @param ccNumber        The user's credit card number.
     * @param hasActiveRental If the user has an active bike rental.
     */
    private boolean addUser(String username, String email, String password, String salt, String name, float weight, float height, long ccNumber, boolean hasActiveRental, double averageSpeed) {
        try {
            openConnection();
            /*
             *  CREATE OR REPLACE PROCEDURE addUser(username VARCHAR, uemail VARCHAR, upassword VARCHAR, usalt VARCHAR, uname VARCHAR, uweight NUMBER, uheight NUMBER, ucc NUMBER, uActiveRental NUMBER)
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call add_user(?,?,?,?,?,?,?,?,?,?,?) }");
            callStmt.setString(1,username);
            callStmt.setString(2, email);
            callStmt.setString(3, password);
            callStmt.setString(4, salt);
            callStmt.setString(5, name);
            callStmt.setFloat(6, weight);
            callStmt.setFloat(7, height);
            callStmt.setLong(8, ccNumber);

            int activeRental = 0;
            if (hasActiveRental) {
                activeRental = 1;
            }

            callStmt.setInt(9, activeRental);
            callStmt.setInt(10, 0);
            callStmt.setDouble(11, averageSpeed);

            callStmt.execute();

            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes the user whose email is passed as parameter.
     *
     * @param username email of the user to be removed.
     * @return true if the user was removed, false if not.
     */
    public boolean removeUser(String username) {
        try {
            openConnection();
            /*
             *  PROCEDURE removeUser(uemail VARCHAR)
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call remove_user(?) }");

            callStmt.setString(1, username);

            callStmt.execute();

            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates the rental state of the user.
     *
     * @param username          The email of the user to be changed.
     * @param hasActiveRental true or false.
     */
    public boolean updateRentalStatus(String username, boolean hasActiveRental) {
        try {
            openConnection();
            CallableStatement callStmt = getConnection().prepareCall("{ call update_user_active_rental(?, ?) }");

            callStmt.setString(1, username);
            int state = 0;
            if (hasActiveRental) {
                state = 1;
            }
            callStmt.setInt(2, state);
            callStmt.execute();

            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method that awards a certain number of points to a User
     *
     * @param username
     * @param points
     * @return
     */
    public boolean awardUserPoints(String username, int points) {
        try {

            openConnection();
            CallableStatement callStmt = getConnection().prepareCall("{ call add_user_points(?,?) }");
            callStmt.setString(1, username);
            callStmt.setInt(2, points);
            callStmt.execute();
            closeAll();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method that creates a receipt in the DB
     *
     * @param value Value of the receipt
     * @param iva   Iva associated with tthe value
     * @return The ID created in the DB, -1 if fails
     */
    public int createReceipt(double value, int iva) {
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call create_receipt(?,?,?) }");
            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setDouble(2, value);
            callStmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            callStmt.setInt(4, iva);
            callStmt.execute();

            return callStmt.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Method that associates a receipt with a rental
     *
     * @param idRental  ID of the rental
     * @param idReceipt ID of the receipt
     * @return True if operation works with success, false otherwise
     */
    public boolean insertReceiptRental(int idRental, int idReceipt) {
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{call insert_receipt_rental(?,?) }");

            callStmt.setInt(1, idRental);
            callStmt.setInt(2, idReceipt);
            callStmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

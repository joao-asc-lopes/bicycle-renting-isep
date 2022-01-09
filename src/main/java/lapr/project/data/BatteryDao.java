package lapr.project.data;

import lapr.project.model.bicycle.Battery;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BatteryDao extends DataHandler {
    /**
     * Given an id, returns the corresponding battery.
     *
     * @param id id of the battery to be returned.
     * @return the Battery registry, or null if it doesn't exist.
     */
    public Battery getBattery(int id) {

        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call get_battery(?) }");
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, id);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int idBat = rSet.getInt(1);
                long maxCap = rSet.getLong(2);
                long curCap = rSet.getLong(3);
                float weight = rSet.getFloat(4);

                return new Battery(idBat, maxCap, curCap, weight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Battery with ID:" + id);
    }

    /**
     * Method to add battery to database
     *

     * @param batteryMaxCharge
     * @param batteryCurrentCharge
     * @param weight
     * @return
     */
    public int addBattery(double batteryMaxCharge, double batteryCurrentCharge, double weight) {
        return save(batteryMaxCharge, batteryCurrentCharge, weight);
    }

    /**
     * Saves a battery in the database and returns its generated id.
     *
     * @param batteryMaxCharge     The battery's max charge.
     * @param batteryCurrentCharge The battery's current charge.
     * @return The created battery's id.
     */
    private int save( double batteryMaxCharge, double batteryCurrentCharge, double batteryWeight) {
        try {
            openConnection();
            CallableStatement callStmt = getConnection().prepareCall("{? = call add_battery(?,?,?)}");
            callStmt.registerOutParameter(1,OracleTypes.INTEGER);
            callStmt.setDouble(2, batteryMaxCharge);
            callStmt.setDouble(3, batteryCurrentCharge);
            callStmt.setDouble(4, batteryWeight);

            callStmt.execute();
            int x = callStmt.getInt(1);
            closeAll();
            return x;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * Removes a battery from the database. This method should only be called when a bicycle is removed from the system.
     *
     * @param idBattery id of the bicycle to be removed.
     */
    public boolean removeBattery(int idBattery) {

        try {
            openConnection();

            CallableStatement callStmt = getConnection().prepareCall("{ call remove_battery(?) }");

            callStmt.setInt(1, idBattery);

            callStmt.execute();

            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

}

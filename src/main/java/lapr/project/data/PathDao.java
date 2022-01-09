/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.data;

import lapr.project.model.bikenetwork.Path;
import lapr.project.model.park.Location;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PathDao extends DataHandler {

    /**
     * Gets WindInformation from DB
     *
     * @param from id of the initial park
     * @param to   id of the final park
     * @return WindInformation if exists
     * @throws IllegalArgumentException When the WindInformation searched on the
     *                                  DB doesn't exist
     */
    public Path getPath(Location from, Location to) {

        try {
            openConnection();
            CallableStatement callStmt = getConnection().prepareCall("{ ? = call get_path_information(?,?) }");
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, from.getIdLocation());
            callStmt.setInt(3, to.getIdLocation());

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);


            while (rSet.next()) {

                double windSpeed = rSet.getDouble(3);
                double windDir = rSet.getDouble(4);
                double kinetic = rSet.getDouble(5);

                return new Path(windSpeed, windDir, kinetic);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method that adds a Path to the database
     *
     * @param pa
     * @return
     */
    public boolean addPath(Path pa, Location p1, Location p2) {
        try {
            openConnection();
            CallableStatement callStmt = getConnection().prepareCall("{ call add_path_information(?,?,?,?,?)}");
            callStmt.setDouble(1, p1.getIdLocation());
            callStmt.setDouble(2, p2.getIdLocation());
            callStmt.setDouble(3, pa.getWindSpeed());
            callStmt.setDouble(4, pa.getWindDirection());
            callStmt.setDouble(5, pa.getKineticFriction());
            callStmt.execute();
            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates wind information between 2 parks with the new data.
     *
     * @param initialParkId
     * @param finalParkId
     * @param windSpeed
     * @param direction
     * @return
     */
    public boolean updatePath(int initialParkId, int finalParkId, double windSpeed, double direction, double kinectic) {
        try {
            CallableStatement callSmt = getConnection().prepareCall("{ call update_path_information(?,?,?,?,?) }");
            callSmt.setInt(1, initialParkId);
            callSmt.setInt(2, finalParkId);
            callSmt.setDouble(3, windSpeed);
            callSmt.setDouble(4, direction);
            callSmt.setDouble(5, kinectic);
            callSmt.execute();
            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}

package lapr.project.data;

import lapr.project.model.bicycle.*;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BicycleDao extends DataHandler {


    /**
     * @param id if of the electrical bicycle to be found.
     * @return The registry of the electrical bicycle, or null if she doesn't exist.
     */
    public ElectricBicycle getElectricBicycle(String id) {
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call get_electric_bicycle(?) }");
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, id);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            return loadElectricBicycle(rSet);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("No Eletrical Bicycle with ID:" + id);
        }
    }

    /**
     * @param id id of the road bicycle to be found.
     * @return The registry of the bicycle found, or null if she doesn't exist.
     */
    public RoadBicycle getRoadBicycle(String id){
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call get_road_bicycle(?) }");
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, id);
            callStmt.execute();
            ResultSet rSet =  (ResultSet) callStmt.getObject(1);
            return loadRoadBicycle(rSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("No Road Bicycle with ID:" + id);
        }
    }

    /**
     * @param id id of the Mountain Bicycle to be found.
     * @return The registry of the bicycle or null if she doesn't exist.
     */
    public MountainBicycle getMountainBicycle(String id) {
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call get_mountain_bicycle(?) }");
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, id);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            return loadMountainBicycle(rSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("No Mountain Bicycle with ID:" + id);
        }
    }

    public Bicycle getBicycle(String id){
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call get_bicycle_type(?) }");
            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setString(2, id);
            callStmt.execute();
            int type = callStmt.getInt(1);
            switch(type){
                case 0: return getElectricBicycle(id);
                case 1: return getMountainBicycle(id);
                case 2: return getRoadBicycle(id);
                default: throw new IllegalArgumentException("No Bicycle with ID:" + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("No Bicycle with ID:" + id);
        }
    }


    /**
     * Given a result set it will try to create and return an electric bicycle.
     *
     * @param rSet ResultSet which should have the necessary data to create an electric bicycle.
     * @return The registry of an electric bicycle.
     * @throws SQLException
     */
    private ElectricBicycle loadElectricBicycle(ResultSet rSet) throws SQLException {
        if (rSet.next()) {
            String idBike = rSet.getString(1);
            int state = rSet.getInt(2);
            int idBattery = rSet.getInt(3);
            int weight = rSet.getInt(4);
            double coefficient = rSet.getDouble(5);
            double frontalArea = rSet.getDouble(6);
            closeAll();
            return new ElectricBicycle(idBike, Bicycle.statusByCode(state), new BatteryDao().getBattery(idBattery), weight,coefficient,frontalArea);
        }
        return null;
    }

    /**
     * Given a result set it will try to create and return a road bicycle.
     *
     * @param rSet ResultSet which should have the necessary data to create a road bicycle.
     * @return The registry of a road bicycle.
     * @throws SQLException
     */
    private RoadBicycle loadRoadBicycle(ResultSet rSet) throws SQLException {
        if (rSet.next()) {
            String idBike = rSet.getString(1);
            int state = rSet.getInt(2);
            int weight = rSet.getInt(3);
            double coefficient = rSet.getDouble(4);
            double frontalArea = rSet.getDouble(5);
            closeAll();
            return new RoadBicycle(idBike, Bicycle.statusByCode(state), weight,coefficient,frontalArea);
        }
        return null;
    }

    /**
     * Given a result set it will try to create and return a mountain bicycle.
     * @param rSet ResultSet which should have the necessary data to create a mountain bicycle.
     * @return  The register of the mountain bicycle or null if the result set was invalid.
     * @throws SQLException
     */
    private MountainBicycle loadMountainBicycle(ResultSet rSet) throws SQLException {
        if (rSet.next()) {
            String idBike = rSet.getString(1);
            int state = rSet.getInt(2);
            int weight = rSet.getInt(3);
            double coefficient = rSet.getDouble(4);
            double frontalArea = rSet.getDouble(5);
            closeAll();
            return new MountainBicycle(idBike, Bicycle.statusByCode(state), weight,coefficient,frontalArea);
        }
        return null;
    }


    /**
     * Saves a non-electric bike in the database.
     *
     * @param status Status of the bike.
     * @param weight Weight of the bike.
     * @param type   Type of the bike.
     * @return true if the operation was successful or false if not.
     */
    private boolean saveNonElectricBike(String id, Bicycle.BicycleStatus status, float weight, Bicycle.BicycleType type, double coefficient, double frontal) {
        try {
            openConnection();
            CallableStatement callStmt;
            if (type == Bicycle.BicycleType.ROAD) {
                callStmt = getConnection().prepareCall("{ call add_road_bicycle(?,?,?,?,?) }");
            } else if (type == Bicycle.BicycleType.MOUNTAIN) {
                callStmt = getConnection().prepareCall("{ call add_mountain_bicycle(?,?,?,?,?) }");
            } else {
                throw new IllegalArgumentException("Type not supported.");
            }
            callStmt.setString(1,id);
            callStmt.setInt(2, status.statusCode());
            callStmt.setFloat(3, weight);
            callStmt.setDouble(4,coefficient);
            callStmt.setDouble(5,frontal);
            callStmt.execute();
            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a bicycle and its battery to the database.
     *
     * @param status The bike's status.
     * @param weight The bike's weight
     * @return true if the operation was successful, false if not.
     */
    private boolean saveElectricBicycle(String id, Bicycle.BicycleStatus status, float weight, int batteryId, double coefficient,double frontal) {
        try {
            openConnection();
            CallableStatement callStmt = getConnection().prepareCall("{ call add_electric_bicycle(?,?,?,?,?,?) }");
            callStmt.setString(1,id);
            callStmt.setInt(2, status.statusCode());
            callStmt.setFloat(3, weight);
            callStmt.setInt(4, batteryId);
            callStmt.setDouble(5,coefficient);
            callStmt.setDouble(6,frontal);
            callStmt.execute();
            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Adds an electric bicycle to the database.
     *
     * @param eb ElectricBicycle to be added on the DB
     * @return true if the operation was successful, false if not.
     */
    public boolean addElectricBicycle(ElectricBicycle eb) {
        Battery bat = eb.getBattery();
        int idBattery = new BatteryDao().addBattery(bat.getMaxCharge(),bat.getCurrentCharge(), bat.getWeight());
        if(idBattery!=-1) {
            return this.saveElectricBicycle(eb.getId(),eb.getStatus(), eb.getWeight(), idBattery,
                    eb.getBicycleAerodynamicalCoefficient(),eb.getFrontalArea());
        }
        return false;
    }

    /**
     * Saves a non electric bicycle in the database.
     *
     * @param bike Bicycle to save on the DB
     * @return true if the operation was successful, false if not.
     */
    public boolean addNonElectricBicycle(Bicycle bike){
        return saveNonElectricBike(bike.getId(),bike.getStatus(), bike.getWeight(), bike.getType(),
                bike.getBicycleAerodynamicalCoefficient(),bike.getFrontalArea());
    }

    /**
     * Removes a bicycle from the database.
     *
     * @param idBike The id of the bicycle to be removed.
     * @return true if the removal was successful, false if not.
     */
    public boolean removeBicycle(int idBike) {
        try {
            openConnection();
            CallableStatement callStmt = getConnection().prepareCall("{ call remove_bicycle(?) }");
            callStmt.setInt(1, idBike);
            callStmt.execute();
            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return a list with all the bicycles in the database.
     */
    public ArrayList<Bicycle> getAllBicycles() {
        ArrayList<Bicycle> allBicycles = new ArrayList<>();
        allBicycles.addAll(getElectricBicyclesList());
        allBicycles.addAll(getMountainBicyclesList());
        allBicycles.addAll(getRoadBicyclesList());

        return allBicycles;
    }

    /**
     * Gets a list of all the road bicycles in the database.
     *
     * @return A list with all the road bicycles in the database.
     */
    public List<ElectricBicycle> getElectricBicyclesList() {
        List<ElectricBicycle> returnLst = new ArrayList<>();
        CallableStatement callStmt;
        try {
            callStmt = getConnection().prepareCall("{ ? = call get_all_electric_bicycles() }");
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            while (rSet.next()) {
                String idBike = rSet.getString(1);
                Bicycle.BicycleStatus status = Bicycle.statusByCode(rSet.getInt(2));
                Battery batttery = new BatteryDao().getBattery(rSet.getInt(3));
                float weight = rSet.getFloat(4);
                double coefficient = rSet.getDouble(5);
                double frontalArea = rSet.getDouble(6);
                returnLst.add(new ElectricBicycle(idBike,status,batttery,weight,coefficient,frontalArea));
            }

            return returnLst;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a list of all the road bicycles in the database.
     *
     * @return A list with all the road bicycles in the database.
     */
    public List<MountainBicycle> getMountainBicyclesList() {
        List<MountainBicycle> returnLst = new ArrayList<>();
        CallableStatement callStmt;
        try {
            openConnection();
            callStmt = getConnection().prepareCall("{ ? = call get_all_mountain_bicycles() }");
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            while (rSet.next()) {
                String id = rSet.getString(1);
                Bicycle.BicycleStatus status = Bicycle.statusByCode(rSet.getInt(2));
                float weight = rSet.getFloat(3);
                double coefficient = rSet.getDouble(4);
                double frontalArea = rSet.getDouble(5);
                returnLst.add(new MountainBicycle(id,status,weight,coefficient,frontalArea));
            }

            return returnLst;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a list of all the road bicycles in the database.
     *
     * @return A list with all the road bicycles in the database.
     */
    public List<RoadBicycle> getRoadBicyclesList() {
        List<RoadBicycle> returnLst = new ArrayList<>();
        CallableStatement callStmt;
        try {
            openConnection();
            callStmt = getConnection().prepareCall("{ ? = call get_all_road_bicycles() }");
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            while (rSet.next()) {
                String idBike = rSet.getString(1);
                Bicycle.BicycleStatus status = Bicycle.statusByCode(rSet.getInt(2));
                float weight = rSet.getFloat(3);
                double coefficient = rSet.getDouble(4);
                double frontalArea = rSet.getDouble(5);
                returnLst.add(new RoadBicycle(idBike,status,weight,coefficient,frontalArea));
            }

            return returnLst;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Updates a bicycle with the new status.
     *
     * @param bike Bicycle to be updated
     * @return
     */
    public boolean updateNonElectricBicycle(Bicycle bike) {
        try {
            openConnection();
            CallableStatement callSmt = getConnection().prepareCall("{ call update_bicycle(?,?,?) }");
            callSmt.setString(1, bike.getId());
            callSmt.setInt(2, bike.getStatus().statusCode());
            callSmt.setFloat(3, bike.getWeight());
            callSmt.execute();
            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method that allows the administrator to update an eletric bicycle with a new battery.
     * @param bicycle ElectricBicycle to be updated
     * @return True if updated, false if not.
     */
    public boolean updateElectricBicycle(ElectricBicycle bicycle) {
        try {
            openConnection();
            CallableStatement callSmt = getConnection().prepareCall("{ call update_electric_bicycle(?,?,?,?) }");
            callSmt.setString(1, bicycle.getId());
            callSmt.setInt(2, bicycle.getStatus().statusCode());
            callSmt.setFloat(3,bicycle.getWeight());
            callSmt.setInt(4, bicycle.getBattery().getId());
            callSmt.execute();
            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

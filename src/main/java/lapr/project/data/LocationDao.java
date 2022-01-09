package lapr.project.data;


import lapr.project.model.bicycle.*;
import lapr.project.model.park.*;
import lapr.project.utils.InvalidDataException;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LocationDao extends DataHandler {

    private static final String NOPARKMESSAGE = "No park with the id : ";
    private static final String INVALIDDATAMESSAGE = "Inserted Data is Invalid";
    /**
     * Method that allows to see if a location exists given its id as a parameter.
     * @param idPark The id of the park given as a parameter.
     * @return True if it exists, false if not.
     */

    public boolean locationExists(int idPark) {
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call get_location(?) }");
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, idPark);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            boolean result=false;
            if (rSet.next()) {
                result = true;
            }
            closeAll();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the park with the id passed as a parameter.
     *
     * @param idPark The id of the park we are trying to get.
     * @return The park that we tried to get.
     */

    public Park getPark(int idPark) {
        CallableStatement callStat = null;

        try {
            callStat = getConnection().prepareCall("{ ? = call get_park(?) }");

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStat.registerOutParameter(1, OracleTypes.CURSOR);

            callStat.setInt(2, idPark);

            // Executa a invocaÃ§Ã£o da funÃ§Ã£o "getPark".
            callStat.execute();

            ResultSet rSet = (ResultSet) callStat.getObject(1);
            if (rSet.next()) {
                int idParks = rSet.getInt(1);
                String namePark = rSet.getString(2);
                double altitude = rSet.getDouble(3);
                double latitude = rSet.getDouble(4);
                double longitude = rSet.getDouble(5);
                NormalSlots ns = this.getNormalSlot(rSet.getInt(6));
                ElectricSlot es = this.getElectricalSlot(rSet.getInt(7));



                return new Park(idParks, namePark, latitude, longitude, ns, es, altitude);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(NOPARKMESSAGE + idPark);
    }

    /**
     *
     * @param idInterestPoint
     * @return
     */
    public InterestPoint getInterestPoint(int idInterestPoint){
        CallableStatement callStat = null;

        try {
            callStat = getConnection().prepareCall("{ ? = call get_interest_point(?) }");

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStat.registerOutParameter(1, OracleTypes.CURSOR);

            callStat.setInt(2, idInterestPoint);

            callStat.execute();

            ResultSet rSet = (ResultSet) callStat.getObject(1);
            if (rSet.next()) {
                int idInterest = rSet.getInt(1);
                String namePark = rSet.getString(2);
                double altitude = rSet.getDouble(3);
                double latitude = rSet.getDouble(4);
                double longitude = rSet.getDouble(5);
                return new InterestPoint(idInterest,namePark,altitude,latitude,longitude);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(NOPARKMESSAGE + idInterestPoint);
    }

    public Location getLocationByCoordinates(double latitude, double longitude){
        CallableStatement callStat = null;
        try {
            callStat = getConnection().prepareCall("{ ? = call get_location_by_coordinates(?,?) }");

            callStat.registerOutParameter(1, OracleTypes.CURSOR);

            callStat.setDouble(2, latitude);

            callStat.setDouble(3,longitude);

            callStat.execute();

            ResultSet rSet = (ResultSet) callStat.getObject(1);
            if (rSet.next()) {
                int type = rSet.getInt(1);
                if(type==1){
                    int id = rSet.getInt(2);
                    String name = rSet.getString(3);
                    double altitude = rSet.getDouble(4);
                    NormalSlots ns = this.getNormalSlot(rSet.getInt(5));
                    ElectricSlot es = this.getElectricalSlot(rSet.getInt(6));
                    return new Park(id,name,latitude,longitude,ns,es,altitude);
                }
                else{
                    int id = rSet.getInt(2);
                    String name = rSet.getString(3);
                    double altitude = rSet.getDouble(4);
                    return new InterestPoint(id,name,latitude,longitude,altitude);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Location with the coordinates\nLatitude: " + latitude +"\nLongitude: "+longitude);
    }

    public Park getParkIdBicycleLocked(String idBicycle){
        CallableStatement callStat = null;

        try {
            callStat = getConnection().prepareCall("{ ? = call get_park_id_bicycle_locked(?) }");

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStat.registerOutParameter(1, OracleTypes.CURSOR);

            callStat.setString(2, idBicycle);

            callStat.execute();

            ResultSet rSet = (ResultSet) callStat.getObject(1);
            if (rSet.next()) {
                int idParks = rSet.getInt(1);
                String namePark = rSet.getString(2);
                double altitude = rSet.getDouble(3);
                double latitude = rSet.getDouble(4);
                double longitude = rSet.getDouble(5);
                NormalSlots ns = this.getNormalSlot(rSet.getInt(6));
                ElectricSlot es = this.getElectricalSlot(rSet.getInt(7));



                return new Park(idParks, namePark, latitude, longitude, ns, es, altitude);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No bicycle with the ID :" + idBicycle+" locked in a park");
    }


    /**
     * Method that allows an Interest point to be added to the database.
     *
     * @param ip The interest Point that is about to be added to the database.
     * @return True if it was added, false if not.
     */
    public InterestPoint addInterestPoint(InterestPoint ip) {
        return addInterestPoint(ip.getAltitude(), ip.getLatitude(), ip.getLongitude(), ip.getName());
    }

    private InterestPoint addInterestPoint(double altitude, double latitude, double longitude, String name) {

        try {
            openConnection();
            CallableStatement clbstm = getConnection().prepareCall("{ call add_interest_point(?,?,?,?) }");

            clbstm.setString(1, name);
            clbstm.setDouble(2, altitude);
            clbstm.setDouble(3, latitude);
            clbstm.setDouble(4, longitude);


            clbstm.execute();
            closeAll();

            return new InterestPoint(name,altitude,latitude,longitude);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidDataException(INVALIDDATAMESSAGE);
        }

    }

    /**
     * Registers and saves in the database a new park.
     *
     * @param p Park to be added on the DB
     * @return true if the operation was successful, false if not.
     */
    public boolean addPark(Park p) {

        try {
            openConnection();
            CallableStatement clbstm = getConnection().prepareCall("{ call add_park(?,?,?,?,?,?,?,?,?,?) }");

            clbstm.setString(1, p.getName());
            clbstm.setDouble(2, p.getLatitude());
            clbstm.setDouble(3, p.getLongitude());
            clbstm.setInt(4,p.getNormalSlots().getMaximumCapacity());
            clbstm.setInt(5, p.getNormalSlots().getNumberFreeSlots());
            clbstm.setInt(6, p.getEletricalSlots().getMaximumCapacity());
            clbstm.setInt(7, p.getEletricalSlots().getNumberFreeSlots());
            clbstm.setDouble(8, p.getEletricalSlots().getChargeRate());
            clbstm.setDouble(9,p.getEletricalSlots().getIntensity());
            clbstm.setDouble(10, p.getAltitude());
            clbstm.execute();
            closeAll();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidDataException(INVALIDDATAMESSAGE);
        }

    }

    /**
     * Method that removes a park from the database and restocks the bicycles to other parks if they can stock them.
     *
     * @param idPark The id of the park that is about to be restocked ( if possible) and removed.
     */
    public boolean removePark(int idPark) {
        try {
            openConnection();

            CallableStatement cStatRestoreBikesNormal = getConnection().prepareCall("{ call restock_bikes_normal(?) }");

            cStatRestoreBikesNormal.setInt(1, idPark);
            cStatRestoreBikesNormal.execute();


            CallableStatement cStatRestoreBikesElectrical = getConnection().prepareCall("{ call restock_bikes_electric(?) }");

            cStatRestoreBikesElectrical.setInt(1, idPark);
            cStatRestoreBikesElectrical.execute();


            CallableStatement cStat = getConnection().prepareCall("{ call remove_park(?) }");

            cStat.setInt(1, idPark);

            cStat.execute();
            closeAll();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidDataException(INVALIDDATAMESSAGE);
        }
    }

    /**
     * Method that allows an Interest Point to be removed.
     *
     * @param idLocation The id location of the interest Point.
     * @return True if it was removed, false if not.
     */
    public boolean removeInterestPoint(int idLocation) {
        try {
            openConnection();

            CallableStatement cStatRemoveLocation = getConnection().prepareCall("{ call remove_interest_points(?) }");
            cStatRemoveLocation.setInt(1, idLocation);
            cStatRemoveLocation.execute();
            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidDataException(INVALIDDATAMESSAGE);
        }
    }

    /**
     * Method that given an id of a Park as a parameter, gives all the parked bicycles of that park.
     * @param id The id of the park we want all the parked bicycles.
     * @return An ArrayList containing all the bicycles parked.
     */


    public List<Bicycle> getParkedBicycles(int id) {

        List<Bicycle> array = new ArrayList<>();
        CallableStatement callStat = null;
        try {
            callStat = getConnection().prepareCall("{ ? = call get_Parked_Road_Bicycles(?) }");

            callStat.registerOutParameter(1, OracleTypes.CURSOR);

            callStat.setInt(2, id);

            callStat.execute();
            ResultSet rSet = (ResultSet) callStat.getObject(1);
            while (rSet.next()) {
                String idBike = rSet.getString(1);
                int state = rSet.getInt(2);
                float weight = rSet.getFloat(3);
                double coefficient = rSet.getDouble(4);
                double frontalArea = rSet.getDouble(5);
                array.add(new RoadBicycle(idBike, Bicycle.statusByCode(state), weight,coefficient,frontalArea));
            }

            callStat = getConnection().prepareCall("{ ? = call get_Parked_Mountain_Bicycles(?) }");

            callStat.registerOutParameter(1, OracleTypes.CURSOR);

            callStat.setInt(2, id);

            callStat.execute();
            rSet = (ResultSet) callStat.getObject(1);
            while (rSet.next()) {
                String idBike = rSet.getString(1);
                int state = rSet.getInt(2);
                float weight = rSet.getFloat(3);
                double coefficient = rSet.getDouble(4);
                double frontalArea = rSet.getDouble(5);
                array.add(new MountainBicycle(idBike, Bicycle.statusByCode(state), weight,coefficient,frontalArea));
            }
            callStat = getConnection().prepareCall("{ ? = call get_Parked_Electric_Bicycles(?) }");
            callStat.registerOutParameter(1, OracleTypes.CURSOR);

            callStat.setInt(2, id);


            callStat.execute();
            rSet = (ResultSet) callStat.getObject(1);
            while (rSet.next()) {
                String idBike = rSet.getString(1); //ta
                int state = rSet.getInt(2); //ta
                float weight = rSet.getFloat(3); //ta
                double coefficient = rSet.getDouble(4);
                double frontalArea = rSet.getDouble(5);
                int batteryId = rSet.getInt(6);
                int maximumCharge = rSet.getInt(7);
                int currentCharge = rSet.getInt(8);
                float batWeight = rSet.getFloat(9);
                Battery b = new Battery(batteryId, maximumCharge, currentCharge,batWeight);
                array.add(new ElectricBicycle(idBike, Bicycle.statusByCode(state), b, weight,coefficient,frontalArea));
            }
            return array;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(NOPARKMESSAGE+id);

    }

    /**
     * Calls a function that returns an ArrayList with all the parks in the database
     *
     * @return An ArrayList with all the parks in the database
     */
    public List<Park> getParkList() {

        ArrayList<Park> parkList = new ArrayList<>();

        try {
            openConnection();
            CallableStatement callStat = getConnection().prepareCall("{ ? = call get_All_Parks() }");

            callStat.registerOutParameter(1, OracleTypes.CURSOR);

            callStat.execute();

            ResultSet result = (ResultSet) callStat.getObject(1);
            while (result.next()) {
                int id = result.getInt(1);
                double lat = result.getDouble(4);
                double longitude = result.getDouble(5);

                NormalSlots normalSlot = getNormalSlot(result.getInt(6));

                ElectricSlot eletricSlot = getElectricalSlot(result.getInt(7));

                String name = result.getString(2);


                double altitude = result.getDouble(3);

                parkList.add(new Park(id, name, lat, longitude, normalSlot, eletricSlot, altitude));
            }
            return parkList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidDataException(INVALIDDATAMESSAGE);
        }
    }

    /**
     * Calls a function that returns an ArrayList with all the parks in the database
     *
     * @return An ArrayList with all the parks in the database
     */
    public List<InterestPoint> getAllInterestPointsList() {

        ArrayList<InterestPoint> parkList = new ArrayList<>();

        try {
            openConnection();
            CallableStatement callStat = getConnection().prepareCall(" { ? = call get_All_Interest_Points() }");

            callStat.registerOutParameter(1, OracleTypes.CURSOR);

            callStat.execute();

            ResultSet result = (ResultSet) callStat.getObject(1);
            while (result.next()) {
                int id = result.getInt(1);
                double lat = result.getDouble(4);
                double longitude = result.getDouble(5);


                String name = result.getString(2);

                double altitude = result.getDouble(3);

                parkList.add(new InterestPoint(id, name, lat, longitude, altitude));
            }
            return parkList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new InvalidDataException(INVALIDDATAMESSAGE);
    }


    /**
     * Calls a procedure that returns the desired park's NormalSlot
     *
     * @param idSlot The park's NormalSlotId
     * @return The park's NormalSlot
     */
    public NormalSlots getNormalSlot(int idSlot){


        try {
            openConnection();
            CallableStatement callStatNew = getConnection().prepareCall("{ ? = call get_normal_slot(?) }");


            callStatNew.registerOutParameter(1, OracleTypes.CURSOR);
            callStatNew.setInt(2, idSlot);

            callStatNew.execute();


            ResultSet rSet = (ResultSet) callStatNew.getObject(1);

            if (rSet.next()) {

                int slotId = rSet.getInt(1);

                int maxCap = rSet.getInt(2);

                int nFreeSlots = rSet.getInt(3);


                return new NormalSlots(slotId, maxCap, nFreeSlots);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No slot with the id: "+ idSlot);
    }


    /**
     * Calls a procedure that returns the desired park's ElectricSlot
     *
     * @param idSlot The park's ElectricalSlotId
     * @return The park's ElectricSlot
     */
    public ElectricSlot getElectricalSlot(int idSlot){

        try {
            openConnection();
            CallableStatement callStat = getConnection().prepareCall("{ ? = call get_electric_slot(?) }");

            callStat.setInt(2, idSlot);

            callStat.registerOutParameter(1, OracleTypes.CURSOR);

            callStat.execute();

            ResultSet result = (ResultSet) callStat.getObject(1);

            while (result.next()) {

                int slotId = result.getInt(1);
                int maxCap = result.getInt(2);
                int nFreeSlots = result.getInt(3);
                double chargeRate = result.getDouble(4);
                double intensity = result.getDouble(5);

                return new ElectricSlot(slotId, maxCap, nFreeSlots, chargeRate, intensity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No slot with the id : " + idSlot);
    }

    /**
     * Updates the registry of interest point's name in the database
     *
     * @param ip InterestPoint to be update
     */
    public boolean updateInterestPoint(InterestPoint ip) {


        try {

            openConnection();

            CallableStatement callStmt = getConnection().prepareCall("{call update_interest_point(?,?) }");

            callStmt.setInt(1, ip.getIdLocation());
            callStmt.setString(2, ip.getName());

            callStmt.execute();

            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new InvalidDataException(INVALIDDATAMESSAGE);
    }

    /**
     * Updates a park's data
     * @param park Park to be updated
     */
    public boolean updatePark(Park park){

        try {

            openConnection();

            CallableStatement callStmt = getConnection().prepareCall("{call update_park(?,?,?,?,?,?) }");

            callStmt.setInt(1,park.getIdLocation());
            callStmt.setString(2,park.getName());
            callStmt.setInt(3,park.getNormalSlots().getMaximumCapacity());
            callStmt.setInt(4,park.getNormalSlots().getNumberFreeSlots());
            callStmt.setInt(5,park.getEletricalSlots().getMaximumCapacity());
            callStmt.setInt(6,park.getEletricalSlots().getNumberFreeSlots());

            callStmt.execute();

            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new InvalidDataException(INVALIDDATAMESSAGE);

    }


    /**
     * Method that inserts a bike into a park
     *
     * @param parkId    ID of the park
     * @param bicycleId ID of the bicycle
     * @return True if operation works with success, false otherwise
     */
    public boolean bikeIntoPark(String bicycleId, int parkId) {
        try {
            CallableStatement clbstm = getConnection().prepareCall("{ call bike_into_park(?,?) }");

            clbstm.setString(1, bicycleId);
            clbstm.setInt(2, parkId);
            clbstm.execute();
            closeAll();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new InvalidDataException(INVALIDDATAMESSAGE);
    }


    /**
     * Returns all the road bicycles of a park.
     *
     * @param idPark The id of the park we are trying to get the bicycles.
     * @return An ArrayList of Road Bicycles.
     */
    public List<RoadBicycle> getRoadBicycles(int idPark){

        ArrayList<RoadBicycle> array = new ArrayList<>();
        CallableStatement callStat = null;
        try {
            callStat = getConnection().prepareCall("{ ? = call get_Parked_Road_Bicycles(?) }");

            callStat.registerOutParameter(1, OracleTypes.CURSOR);

            callStat.setInt(2, idPark);

            callStat.execute();

            ResultSet rSet = (ResultSet) callStat.getObject(1);
            while (rSet.next()) {
                String idBike = rSet.getString(1);
                int state = rSet.getInt(2);
                float weight = rSet.getFloat(3);
                double coefficient = rSet.getDouble(4);
                double frontalArea = rSet.getDouble(5);
                array.add(new RoadBicycle(idBike, Bicycle.statusByCode(state), weight,coefficient,frontalArea));
            }
            closeAll();
            return array;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(NOPARKMESSAGE + idPark);
    }

    /**
     * This method returns all the mountain bicycles of a park regarding its park Id.
     *
     * @param idPark The id of the park we are trying to get all the mountain bicycles.
     * @return The id of the park we are getting the mountain bicycles.
     */

    public List<MountainBicycle> getMountainBicycles(int idPark){
        ArrayList<MountainBicycle> array = new ArrayList<>();
        CallableStatement callStat = null;
        try {

            callStat = getConnection().prepareCall("{ ? = call get_Parked_Mountain_Bicycles(?) }");

            callStat.registerOutParameter(1, OracleTypes.CURSOR);

            callStat.setInt(2, idPark);


            callStat.execute();

            ResultSet rSet = (ResultSet) callStat.getObject(1);

            while (rSet.next()) {
                String idBike = rSet.getString(1);
                int state = rSet.getInt(2);
                float weight = rSet.getFloat(3);
                double coefficient = rSet.getDouble(4);
                double frontalArea = rSet.getDouble(5);
                array.add(new MountainBicycle(idBike, Bicycle.statusByCode(state), weight,coefficient,frontalArea));
            }
            closeAll();
            return array;

        } catch (SQLException e){
            e.printStackTrace();
        }
        throw new IllegalArgumentException(NOPARKMESSAGE + idPark);


    }

    /**
     * Method that returns all the electric bicycles with the park id given as a parameter.
     * @param idPark The id of the park given as a parameter.
     * @return The array List of eletrical bicycles regarding the id of the park given as a parameter.
     */
    public List<ElectricBicycle> getElectricalBicycles(int idPark){
        try {
            ArrayList<ElectricBicycle> array = new ArrayList<>();
            CallableStatement callStat = null;
            callStat = getConnection().prepareCall("{ ? = call get_Parked_Electric_Bicycles(?) }");

            callStat.registerOutParameter(1, OracleTypes.CURSOR);

            callStat.setInt(2, idPark);

            callStat.execute();

            ResultSet rSet = (ResultSet) callStat.getObject(1);

            while (rSet.next()) {
                String idBike = rSet.getString(1);
                int batteryId = rSet.getInt(6);
                float weight = rSet.getFloat(3);
                double coefficient = rSet.getDouble(4);
                int maximumCharge = rSet.getInt(7);
                int currentCharge = rSet.getInt(8);
                float batteryWeight = rSet.getFloat(9);
                int state = rSet.getInt(2);

                double frontalArea = rSet.getDouble(5);
                Battery b = new Battery(batteryId, maximumCharge, currentCharge,batteryWeight);
                array.add(new ElectricBicycle(idBike, Bicycle.statusByCode(state), b, weight,coefficient,frontalArea));
            }
            closeAll();
            return array;


        } catch (SQLException e){
            e.printStackTrace();
        }
        throw new IllegalArgumentException(NOPARKMESSAGE + idPark);
    }
}

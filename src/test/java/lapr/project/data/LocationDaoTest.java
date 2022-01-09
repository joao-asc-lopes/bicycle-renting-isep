package lapr.project.data;

import lapr.project.model.bicycle.*;
import lapr.project.model.park.*;
import lapr.project.utils.InvalidDataException;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@RunWith(JUnitPlatform.class)
public class LocationDaoTest {
    private static LocationDao ld;
    private static IDatabaseTester databaseTester;
    private static FlatXmlDataSet dataSet;
    private static boolean changed;

    @BeforeAll
    protected static void init() throws Exception{
        Properties properties =
                new Properties(System.getProperties());
        InputStream input = new FileInputStream("target/classes/application.properties");
        properties.load(input);
        System.setProperties(properties);
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,"oracle.jdbc.OracleDriver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:oracle:thin://@vsrvbd1.dei.isep.ipp.pt:1521/pdborcl" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "LAPR3_G40" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "brogrammers" );
        databaseTester = new JdbcDatabaseTester("oracle.jdbc.OracleDriver",
                "jdbc:oracle:thin://@vsrvbd1.dei.isep.ipp.pt:1521/pdborcl", "LAPR3_G40", "brogrammers"){
            @Override
            public IDatabaseConnection getConnection() throws Exception{
                IDatabaseConnection connection = super.getConnection();
                connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Oracle10DataTypeFactory());
                return connection;
            }
        };
        changed=false;
        ld=new LocationDao();
        if(!DBTestAgent.backupDB(databaseTester)){
            throw new Exception();
        }
        FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
        dataSet = x.build(new FileInputStream("target/test-classes/locations.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
    }


    @AfterEach
    protected void tearDown() throws Exception
    {
        if(changed) {
            FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
            dataSet = x.build(new FileInputStream("target/test-classes/locations.xml"));
            DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
            changed=false;
        }
    }

    @AfterAll
    protected static void finish() throws Exception{
        if(!DBTestAgent.restoreDB(databaseTester)){
            throw new Exception();
        }
        databaseTester.getConnection().close();
    }

    protected static void emptyDB() throws Exception{
        FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
        dataSet = x.build(new FileInputStream("target/test-classes/locations.xml"));
        DatabaseOperation.DELETE.execute(databaseTester.getConnection(), dataSet);
    }



    @Test
    public void testLocationExistsWhenLocationNotExist(){
        boolean result = ld.locationExists(10003);
        assertTrue(!result);
    }

    @Test
    public void testLocationExistsWhenLocationExist(){
        boolean result = ld.locationExists(10001);
        assertTrue(result);
    }

    @Test
    public void testGetParkWhenLocationNotExist(){
        try {
            Location result = ld.getPark(10003);
            assertTrue(result == null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetParkWhenLocationExist(){
        NormalSlots ns = new NormalSlots(10001,10,10);
        ElectricSlot es = new ElectricSlot(10002,10,10,220,50);
        Park p = new Park(10001,"ISEP",42.3,-8,ns,es,20);
        try{
            Location result = ld.getPark(10001);
            assertTrue(result!=null);
            assertTrue(p.equals(result));
        }
        catch(IllegalArgumentException e){
            assertTrue(false);
        }
    }

    @Test
    public void testGetInterestPointWhenProcessFails(){
        try{
            InterestPoint result = ld.getInterestPoint(10010);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        try{
            InterestPoint result = ld.getInterestPoint(10001);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetInterestPointWhenProcessWorks(){
        InterestPoint expected = new InterestPoint(10000,"Trindade",42,-8,10);
        InterestPoint result = ld.getInterestPoint(10000);
        assertTrue(expected.equals(result));
    }

    @Test
    public void testGetLocationByCoordinatesWhenProcessFails(){
        try{
            Location l = ld.getLocationByCoordinates(30,14);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetLocationByCoordinatesWhenProcessWorks(){
        Location l = ld.getLocationByCoordinates(42,-8);
        assertTrue(l!=null);
        assertTrue(l instanceof  InterestPoint);
        Location p = ld.getLocationByCoordinates(42.3,-8);
        assertTrue(l!=null);
        assertTrue(p instanceof  Park);
    }

    @Test
    public void testGetParkIdBicycleLockedWhenProcessFails(){
        try{
            Park result = ld.getParkIdBicycleLocked("10004");
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        try{
            Park result = ld.getParkIdBicycleLocked("10003");
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetParkByIdBicycleLockedWhenProcessWorks(){
        Park expected = new Park(10002,"Casa da Música",42.1,-8.1,new NormalSlots(10002,10,10),new ElectricSlot(10003,10,10,40,30),20);
        Park result = ld.getParkIdBicycleLocked("10000");
        assertTrue(result!=null);
        assertTrue(expected.equals(result));
    }

    @Test
    public void testAddInterestPointWhenProcessFails(){
        InterestPoint ip = new InterestPoint(10000,"Trindade",-142,-8,10);
        InterestPoint result = ld.addInterestPoint(ip);
        assertEquals(ip, result);
    }

    @Test
    public void testAddInterestPointsWhenProcessWorks(){
        InterestPoint ip = new InterestPoint(10003,"Hospital S.JoÃ£o",42,-7.9,15);
        InterestPoint result = ld.addInterestPoint(ip);
        assertEquals(ip,result);
    }

    @Test
    public void testAddParkWhenProcessFails(){
        changed=true;
        NormalSlots ns = new NormalSlots(10000,10,10);
        ElectricSlot es = new ElectricSlot(10001,10,10,220,50);
        Park p = new Park(10001,"ISEP",-142.3,-8,ns,es,20);
        try {
            boolean result = ld.addPark(p);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
    }

    @Test
    public void testAddParkWhenProcessWorks(){
        changed=true;
        NormalSlots ns = new NormalSlots(10004,10,10);
        ElectricSlot es = new ElectricSlot(10005,10,10,220,50);
        Park p = new Park(10004,"Polo UniversitÃ¡rio",42.6,-7.6,ns,es,20);
        boolean result = ld.addPark(p);
        assertTrue(result);
    }

    @Test
    public void testRemoveParkWhenProcessFails(){
        changed=true;
        //When the ID is not in the DB
        try {
            boolean result = ld.removePark(10003);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
        //When the ID is in the DB but is a Interest Point
        try {
            boolean result = ld.removePark(10000);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
    }

    @Test
    public void testRemoveParkWhenProcessWorks(){
        changed=true;
        boolean result = ld.removePark(10001);
        assertTrue(result);
    }

    @Test
    public void testRemoveInterestPointWhenProcessFails(){
        changed=true;
        //When the ID is not in the DB
        try {
            boolean result = ld.removeInterestPoint(10003);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
        //When the ID is in the DB but is a Park
        try {
            boolean result = ld.removeInterestPoint(10001);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
    }

    @Test
    public void testRemoveInterestPointWhenProcessWorks(){
        changed=true;
        boolean result = ld.removeInterestPoint(10000);
        assertTrue(result);
    }

    @Test
    public void testGetParkedBicyclesWhenParkNotExist(){
        try {
            List<Bicycle> list = ld.getParkedBicycles(10003);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetParkedBicyclesWhenParkDontHaveBicycles(){
        List<Bicycle> list = ld.getParkedBicycles(10001);
        assertTrue(list!=null);
        assertTrue(list.isEmpty());
    }

    @Test
    public void testGetParkedBicyclesWhenParkHaveBicycles(){
        RoadBicycle rb = new RoadBicycle("10000", Bicycle.BicycleStatus.AVAILABLE,100f,10,10);
        MountainBicycle mb = new MountainBicycle("10002", Bicycle.BicycleStatus.AVAILABLE,130f,10,10);
        Battery bat = new Battery(10000,220,110,50f);
        ElectricBicycle eb = new ElectricBicycle("10001", Bicycle.BicycleStatus.AVAILABLE,bat,150f,10,10);
        List<Bicycle> list = ld.getParkedBicycles(10002);
        assertTrue(list!=null);
        assertTrue(!list.isEmpty());
        for(Bicycle b : list){
            if(b instanceof RoadBicycle){
                RoadBicycle result = (RoadBicycle)b;
                assertTrue(result.equals(rb));
            }
            if(b instanceof MountainBicycle){
                MountainBicycle result = (MountainBicycle)b;
                assertTrue(result.equals(mb));
            }
            if(b instanceof ElectricBicycle){
                ElectricBicycle result = (ElectricBicycle)b;
                assertTrue(result.equals(eb));
            }
        }
    }

    @Test
    public void testGetParkListWhenNoParksAreInTheDB() throws Exception{
        changed=true;
        emptyDB();
        List<Park> result = ld.getParkList();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetParkListWhenParksAreInTheDB(){
        NormalSlots ns1 = new NormalSlots(10000,10,10);
        ElectricSlot es1 = new ElectricSlot(10001,10,10,220,50);
        Park p1 = new Park(10001,"ISEP",42.3,-8,ns1,es1,20);
        NormalSlots ns2 = new NormalSlots(10002,10,10);
        ElectricSlot es2 = new ElectricSlot(10003,10,10,220,50);
        Park p2 = new Park(10002,"Casa da MÃºsica",42.1,-8.1,ns2,es2,15);

        List<Park> result = ld.getParkList();
        assertTrue(result!=null);
        assertTrue(!result.isEmpty()&&result.size()==2);
        assertTrue(result.get(0).equals(p1)||result.get(0).equals(p2));
        assertTrue(result.get(1).equals(p1)||result.get(1).equals(p2));
    }

    @Test
    public void testGetAllInterestPointsListWhenNoInterestPointsAreInTheDB()throws Exception{
        changed=true;
        emptyDB();
        List<InterestPoint> result = ld.getAllInterestPointsList();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllInterestPointsListWhenInterestPointsAreInTheDB(){
        InterestPoint ip = new InterestPoint(10000,"Trindade",42,-8,10);
        List<InterestPoint> result = ld.getAllInterestPointsList();
        assertTrue(result!=null);
        assertTrue(!result.isEmpty()&&result.size()==1);
        assertTrue(result.get(0).equals(ip));
    }

    @Test
    public void testGetNormalSlotWhenProcessFails(){
        try {
            NormalSlots result = ld.getNormalSlot(10004);
            assertTrue(result == null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetNormalSlotWhenProcessWorks(){
        NormalSlots expected = new NormalSlots(10000,10,10);
        try{
            NormalSlots result = ld.getNormalSlot(10000);
            assertTrue(result!=null);
            assertTrue(result.equals(expected));
        }
        catch(IllegalArgumentException e){
            assertTrue(false);
        }
    }

    @Test
    public void testGetElectricalSlotWhenProcessFails(){
        try{
            ElectricSlot result = ld.getElectricalSlot(10005);
            assertTrue(result==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetElectricalSlotWhenProcessWorks(){
        ElectricSlot expected = new ElectricSlot(10001,10,10,220,50);
        try{
            ElectricSlot result = ld.getElectricalSlot(10001);
            assertTrue(result!=null);
            assertTrue(result.equals(expected));
        }
        catch(IllegalArgumentException e){
            assertTrue(false);
        }
    }

    @Test
    public void testUpdateInterestPointWhenProcessFails(){
        changed=true;
        InterestPoint ip = new InterestPoint(10004,"Teste",10,10,1);
        try {
            boolean result = ld.updateInterestPoint(ip);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
        InterestPoint ip2 = new InterestPoint(10001,"Teste",10,10,1);
        try {
            boolean result = ld.updateInterestPoint(ip2);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
    }

    @Test
    public void testUpdateInterestPointWhenProcessWorks(){
        changed=true;
        InterestPoint ip = new InterestPoint(10000,"Teste",10,10,1);
        boolean result = ld.updateInterestPoint(ip);
        assertTrue(result);
    }

    @Test
    public void testUpdateParkWhenProcessFails(){
        changed=true;
        Park park = new Park(10004,"Teste",11,11,new NormalSlots(1,10,9),
                new ElectricSlot(2,10,9,25,9),10);
        try {
            boolean result = ld.updatePark(park);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
        Park park2 = new Park(10000,"Teste",11,11,new NormalSlots(1,10,9),
                new ElectricSlot(2,10,9,25,9),10);
        try {
            boolean result = ld.updatePark(park2);
            fail();
        }
        catch(InvalidDataException e){
             assertTrue(true);
        }
    }

    @Test
    public void testUpdateParkWhenProcessWorks(){
        changed=true;
        Park park = new Park(10001,"Teste",11,11,new NormalSlots(1,10,9),
                new ElectricSlot(2,10,9,25,9),10);
        boolean result = ld.updatePark(park);
        assertTrue(result);
    }

    @Test
    public void testBikeIntoParkWhenProcessFails(){
        changed=true;
        //Both IDs are wrong
        try {
            boolean result = ld.bikeIntoPark("10010", 10006);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
        //Park ID is wrong
        try {
            boolean result = ld.bikeIntoPark("10001", 10006);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
        //Bicycle ID is wrong
        try {
            boolean result = ld.bikeIntoPark("10010", 10000);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
    }

    @Test
    public void testBikeIntoParkWhenProcessWorks(){
        changed=true;
        boolean result = ld.bikeIntoPark("10003", 10002);
        assertTrue(result);
    }

    @Test
    public void testGetRoadBicyclesWhenProcessFails(){
        try {
            List<RoadBicycle> result = ld.getRoadBicycles(10004);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetRoadBicyclesWhenParkDontHaveRoadBicycles(){
        List<RoadBicycle> result = ld.getRoadBicycles(10001);
        assertTrue(result!=null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetRoadBicyclesWhenProcessWorks(){
        RoadBicycle expected = new RoadBicycle("10000", Bicycle.BicycleStatus.AVAILABLE,100f,10,10);
        List<RoadBicycle> result = ld.getRoadBicycles(10002);
        assertTrue(result!=null);
        assertTrue(!result.isEmpty()&&result.size()==1);
        assertTrue(result.get(0).equals(expected));
    }

    @Test
    public void testGetMountainBicyclesWhenProcessFails(){
        try {
            List<MountainBicycle> result = ld.getMountainBicycles(10004);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetMountainBicyclesWhenParkDontHaveMountainBicycles(){
        List<MountainBicycle> result = ld.getMountainBicycles(10001);
        assertTrue(result!=null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetMountainBicyclesWhenProcessWorks(){
        MountainBicycle expected = new MountainBicycle("10002", Bicycle.BicycleStatus.AVAILABLE,130f,10,10);
        List<MountainBicycle> result = ld.getMountainBicycles(10002);
        assertTrue(result!=null);
        assertTrue(!result.isEmpty()&&result.size()==1);
        assertTrue(result.get(0).equals(expected));
    }

    @Test
    public void testGetElectricalBicyclesWhenProcessFails(){
        try {
            List<ElectricBicycle> result = ld.getElectricalBicycles(10004);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetElectricalBicyclesWhenParkDontHaveElectricalBicycles(){
        List<ElectricBicycle> result = ld.getElectricalBicycles(10001);
        assertTrue(result!=null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetElectricalBicyclesWhenProcessWorks(){
        Battery bat = new Battery(10000,220,110,50f);
        ElectricBicycle expected = new ElectricBicycle("10001", Bicycle.BicycleStatus.AVAILABLE,bat,150f,10,10);
        List<ElectricBicycle> result = ld.getElectricalBicycles(10002);
        assertTrue(result!=null);
        assertTrue(!result.isEmpty()&&result.size()==1);
        assertTrue(result.get(0).equals(expected));
    }
}

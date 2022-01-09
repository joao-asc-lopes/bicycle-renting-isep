package lapr.project.data;

import lapr.project.model.bicycle.*;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@RunWith(JUnitPlatform.class)
public class BicycleDaoTest {
    private static BicycleDao bd;
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
        bd=new BicycleDao();
        if(!DBTestAgent.backupDB(databaseTester)){
            throw new Exception();
        }
        FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
        dataSet = x.build(new FileInputStream("target/test-classes/bicycle.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
    }


    @AfterEach
    protected void tearDown() throws Exception
    {
        if(changed) {
            FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
            dataSet = x.build(new FileInputStream("target/test-classes/bicycle.xml"));
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

    protected void emptyDB() throws Exception{
        FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
        dataSet = x.build(new FileInputStream("target/test-classes/bicycle.xml"));
        DatabaseOperation.DELETE.execute(databaseTester.getConnection(), dataSet);
    }

    @Test
    public void testGetElectricBicycleWhenProcessFails(){
        //An ID that isn´t in the DB
        try {
            ElectricBicycle result = bd.getElectricBicycle("10010");
            assertTrue(result==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //An ID of a mountain bike
        try {
            ElectricBicycle result = bd.getElectricBicycle("10000");
            assertTrue(result==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //An ID of a road bike
        try {
            ElectricBicycle result = bd.getElectricBicycle("10002");
            assertTrue(result==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetElectricBicycleWhenProcessWorks(){
        Battery bat = new Battery(10000,220,110,50f);
        ElectricBicycle expected = new ElectricBicycle("10004", Bicycle.BicycleStatus.AVAILABLE,bat,110f,10,10);
        try{
            ElectricBicycle result = bd.getElectricBicycle("10004");
            assertTrue(result!=null);
            assertTrue(result.equals(expected));
        }
        catch(IllegalArgumentException e){
            fail();
        }
    }

    @Test
    public void testGetRoadBicycleWhenProcessFails(){
        //An ID that isn´t in the DB
        try {
            RoadBicycle result = bd.getRoadBicycle("10010");
            assertTrue(result==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //An ID of a mountain bike
        try {
            RoadBicycle result = bd.getRoadBicycle("10000");
            assertTrue(result==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //An ID of a electric bike
        try {
            RoadBicycle result = bd.getRoadBicycle("10004");
            assertTrue(result==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetRoadBicycleWhenProcessWorks(){
        RoadBicycle expected = new RoadBicycle("10002", Bicycle.BicycleStatus.AVAILABLE,100f,10,10);
        try{
            RoadBicycle result = bd.getRoadBicycle("10002");
            assertTrue(result!=null);
            assertTrue(result.equals(expected));
        }
        catch(IllegalArgumentException e){
            fail();
        }
    }

    @Test
    public void testGetMountainBicycleWhenProcessFails(){
        //An ID that isn´t in the DB
        try {
            MountainBicycle result = bd.getMountainBicycle("10010");
            assertTrue(result==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //An ID of a road bike
        try {
            MountainBicycle result = bd.getMountainBicycle("10002");
            assertTrue(result==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //An ID of a electric bike
        try {
            MountainBicycle result = bd.getMountainBicycle("10004");
            assertTrue(result==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetMountainBicycleWhenProcessWorks(){
        MountainBicycle expected = new MountainBicycle("10000", Bicycle.BicycleStatus.AVAILABLE,150f,10,10);
        try{
            MountainBicycle result = bd.getMountainBicycle("10000");
            assertTrue(result!=null);
            assertTrue(result.equals(expected));
        }
        catch(IllegalArgumentException e){
            fail();
        }
    }

    @Test
    public void testGetBicycleWhenProcessFails(){
        try{
            Bicycle result = bd.getBicycle("10010");
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetBicycleWhenProcessWorks(){
        MountainBicycle expected = new MountainBicycle("10000", Bicycle.BicycleStatus.AVAILABLE,150f,10,10);
        Bicycle result = bd.getBicycle("10000");
        assertTrue(expected.equals(result));
    }


    @Test
    public void testAddElectricBicycle(){
        changed=true;
        ElectricBicycle ec = new ElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,new Battery(220,180,
                110,20),150f,50,10);
        boolean result = bd.addElectricBicycle(ec);
        assertTrue(result);
    }

    @Test
    public void testAddNonElectricBicycle(){
        changed=true;
        RoadBicycle rb = new RoadBicycle("10010",Bicycle.BicycleStatus.AVAILABLE,150f,20.0,10);
        boolean result = bd.addNonElectricBicycle(rb);
        assertTrue(result);
        MountainBicycle mb = new MountainBicycle("10011",Bicycle.BicycleStatus.AVAILABLE,150f,20.0,10);
        result = bd.addNonElectricBicycle(mb);
        assertTrue(result);
    }

    @Test
    public void testRemoveBicycleWhenProcessFails(){
        changed=true;

        boolean result = bd.removeBicycle(10010);
        assertTrue(!result);
    }

    @Test
    public void testRemoveBicycleWhenProcessWorks(){
        changed=true;

        boolean result =bd.removeBicycle(10000);
    }

    @Test
    public void testGetAllBicyclesWhenNoBicyclesAreInTheDB() throws Exception{
        changed=true;
        emptyDB();
        List<Bicycle> result = bd.getAllBicycles();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllBicyclesWhenBicyclesAreInTheDB(){
        List<Bicycle> expected = new ArrayList<>();
        expected.add(new MountainBicycle("10000", Bicycle.BicycleStatus.AVAILABLE,150f,10,10));
        expected.add(new MountainBicycle("10001", Bicycle.BicycleStatus.AVAILABLE,130f,10,10));
        expected.add(new RoadBicycle("10002", Bicycle.BicycleStatus.AVAILABLE,100f,10,10));
        expected.add(new RoadBicycle("10003", Bicycle.BicycleStatus.AVAILABLE,120f,10,10));
        Battery one = new Battery(10000,220,110,50f);
        Battery two = new Battery(10001,220,190,50f);
        expected.add(new ElectricBicycle("10004", Bicycle.BicycleStatus.AVAILABLE,one,110f,10,10));
        expected.add(new ElectricBicycle("10005", Bicycle.BicycleStatus.AVAILABLE,two,140f,10,10));

        List<Bicycle> result = bd.getAllBicycles();
        assertTrue(result!=null);
        assertTrue(result.size()==6);
        result.sort(Comparator.comparing(Bicycle::getId));
        assertTrue(result.equals(expected));
    }

    @Test
    public void testGetElectricBicyclesListNoBicyclesAreInTheDB() throws Exception{
        changed=true;
        emptyDB();
        List<ElectricBicycle> result = bd.getElectricBicyclesList();
        assertTrue(result.isEmpty());
    }

    //
    @Test
    public void testGetElectricBicyclesListBicyclesAreInTheDB(){
        List<ElectricBicycle> expected = new ArrayList<>();
        Battery one = new Battery(10000,220,110,50f);
        Battery two = new Battery(10001,220,190,50f);
        expected.add(new ElectricBicycle("10004", Bicycle.BicycleStatus.AVAILABLE,one,110f,10,10));
        expected.add(new ElectricBicycle("10005", Bicycle.BicycleStatus.AVAILABLE,two,140f,10,10));

        List<ElectricBicycle> result = bd.getElectricBicyclesList();
        assertTrue(result!=null);
        assertTrue(result.size()==2);
        assertTrue(result.equals(expected));
    }

    @Test
    public void testGetMountainBicyclesListWhenNoBicyclesAreInTheDB() throws Exception{
        changed=true;
        emptyDB();
        List<MountainBicycle> result = bd.getMountainBicyclesList();
        assertTrue(result.isEmpty());
    }

    //
    @Test
    public void testGetMountainBicyclesListWhenBicyclesAreInTheDB(){
        List<MountainBicycle> expected = new ArrayList<>();
        expected.add(new MountainBicycle("10000", Bicycle.BicycleStatus.AVAILABLE,150f,10,10));
        expected.add(new MountainBicycle("10001", Bicycle.BicycleStatus.AVAILABLE,130f,10,10));

        List<MountainBicycle> result = bd.getMountainBicyclesList();
        assertTrue(result!=null);
        assertTrue(result.size()==2);
        assertTrue(result.equals(expected));
    }

    @Test
    public void testGetRoadBicyclesListWhenNoBicyclesAreInTheDB() throws Exception{
        changed=true;
        emptyDB();
        List<RoadBicycle> result = bd.getRoadBicyclesList();
        assertTrue(result.isEmpty());
    }

    //
    @Test
    public void testGetRoadBicyclesListWhenBicyclesAreInTheDB(){
        List<RoadBicycle> expected = new ArrayList<>();
        expected.add(new RoadBicycle("10002", Bicycle.BicycleStatus.AVAILABLE,100f,10,10));
        expected.add(new RoadBicycle("10003", Bicycle.BicycleStatus.AVAILABLE,120f,10,10));

        List<RoadBicycle> result = bd.getRoadBicyclesList();
        assertTrue(result!=null);
        assertTrue(result.size()==2);
        assertTrue(result.equals(expected));
    }

    @Test
    public void testUpdateNonElectricBicycleWhenProcessFails(){
        changed=true;
        RoadBicycle bike = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,1.0,10);
        boolean result = bd.updateNonElectricBicycle(bike);
        assertTrue(!result);
        RoadBicycle bicycle = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,1.0,10);
        result = bd.updateNonElectricBicycle(bicycle);
        assertTrue(!result);
    }

    @Test
    public void testUpdateNonElectricBicycleWhenProcessWorks(){
        changed=true;
        RoadBicycle bicycle = new RoadBicycle("10003", Bicycle.BicycleStatus.AVAILABLE,130f,1.0,10);
        boolean result = bd.updateNonElectricBicycle(bicycle);
        assertTrue(result);
    }

    @Test
    public void testUpdateElectricBicycleWhenProcessFails(){
        changed=true;
        ElectricBicycle bicycle = new ElectricBicycle("10004", Bicycle.BicycleStatus.AVAILABLE,new Battery(10010,220,120,23f),150f,1.0,10);
        boolean result = bd.updateElectricBicycle(bicycle);
        assertTrue(!result);
    }

    @Test
    public void testUpdateElectricBicycleWhenProcessWorks(){
        changed=true;
        ElectricBicycle bicycle = new ElectricBicycle("10004", Bicycle.BicycleStatus.AVAILABLE,new Battery(10000,220,100,50f),150f,1.0,10);
        boolean result = bd.updateElectricBicycle(bicycle);
        assertTrue(result);
    }
}

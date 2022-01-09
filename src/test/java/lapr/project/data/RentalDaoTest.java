package lapr.project.data;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.RoadBicycle;
import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import lapr.project.model.user.Rental;
import lapr.project.model.user.User;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@RunWith(JUnitPlatform.class)
public class RentalDaoTest {
    private static RentalDao rd;
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
        rd=new RentalDao();
        if(!DBTestAgent.backupDB(databaseTester)){
            throw new Exception();
        }
        FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
        dataSet = x.build(new FileInputStream("target/test-classes/rental.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
    }


    @AfterEach
    protected void tearDown() throws Exception
    {
        if(changed) {
            FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
            dataSet = x.build(new FileInputStream("target/test-classes/rental.xml"));
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

    @Test
    public void testGetRentalWhenProcessFails(){
        try{
            Rental result = rd.getRental(10010);
            assertTrue(result==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    //
    @Test
    public void testGetRentalWhenProcessWorks(){
        try{
            Rental result = rd.getRental(10000);
        }
        catch(IllegalArgumentException e){
            fail();
        }
    }

    @Test
    public void testAddRentalWhenProcessFails(){
        changed=true;
        User u = new User("Daniel","teste@teste.com", "teste", "teste", "Teste",60f,1.5f,(long)111113333334444.0,false,15.0);
        NormalSlots ns1 = new NormalSlots(10000,10,10);
        ElectricSlot es1 = new ElectricSlot(10001,10,10,220,50);
        Park p1 = new Park(10000,"Trindade",42,-8,ns1,es1,15);
        NormalSlots ns2 = new NormalSlots(10002,10,10);
        ElectricSlot es2 = new ElectricSlot(10003,10,10,220,50);
        Park p2 = new Park(10000,"Trindade",42,-8,ns2,es2,15);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,10);
        Rental r = new Rental(10000,b,u,p1,p2,LocalDateTime.of(2018,12,30,13,0,0),
                LocalDateTime.of(2018,12,30,14,0,0));
        boolean result = rd.addRental(r);
        assertTrue(!result);
    }

    @Test
    public void testAddRentalWhenProcessWorks(){
        changed=true;
        User u = new User("TESTE", "teste@teste.com", "teste", "teste", "Teste",60f,1.5f,(long)111113333334444.0,false,15.0);
        NormalSlots ns1 = new NormalSlots(10000,10,10);
        ElectricSlot es1 = new ElectricSlot(10001,10,10,220,50);
        Park p1 = new Park(10000,"Trindade",42,-8,ns1,es1,15);
        NormalSlots ns2 = new NormalSlots(10002,10,10);
        ElectricSlot es2 = new ElectricSlot(10003,10,10,220,50);
        Park p2 = new Park(10000,"Trindade",42,-8,ns2,es2,15);
        Bicycle b = new RoadBicycle("10000", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,10);
        Rental r = new Rental(10004,b,u,p1,p2,LocalDateTime.of(2018,12,30,13,0,0),
                LocalDateTime.of(2018,12,30,14,0,0));
        boolean result = rd.addRental(r);
        assertTrue(result);
    }

    @Test
    public void testGetActiveRentalWhenProcessFails(){
        try {
            Rental result = rd.getActiveRental("10010");
            assertTrue(result==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetActiveRentalWhenProcessWorks(){
        try{
            Rental result = rd.getActiveRental("10000");
        }
        catch(IllegalArgumentException e){
            fail();
        }
    }

    @Test
    public void testRemoveRentalWhenProcessFails(){
        changed=true;
        boolean result = rd.removeRental(10010);
        assertTrue(!result);
    }

    @Test
    public void testRemoveRentalWhenProcessWorks(){
        changed=true;
        boolean result = rd.removeRental(10000);
        assertTrue(result);
    }

    @Test
    public void testGetBicycleUserWhenProcessFails(){
        try {
            User result = rd.getBicycleUser("10010");
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }

    }

    @Test
    public void testGetBicycleUserWhenProcessWorks(){
        try{
            User result = rd.getBicycleUser("10000");
            assertTrue(result!=null);
        }
        catch(IllegalArgumentException e){
            fail();
        }
    }

    @Test
    public void testGetStartingParkWhenProcessFails(){
        try{
            Park result = rd.getStartingPark("10010");
            assertTrue(result==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetStartingParkWhenProcessWorks(){
        try{
            Park result = rd.getStartingPark("10000");
            assertTrue(result!=null);
        }
        catch(IllegalArgumentException e){
            fail();
        }
    }

    @Test
    public void testGetAllUnpaidRentalsWhenProcessFails(){
        List<Rental> l = rd.getUnpaidRentalsTotal("FAIL");
        assertTrue(l.isEmpty());
    }

    @Test
    public void testGetAllUnpaidRentalsWhenUserDontHaveRentalsToPay(){
        List<Rental> l = rd.getUnpaidRentalsTotal("TESTE2");
        assertTrue(l!=null);
        assertTrue(l.isEmpty());
    }

    @Test
    public void testGetAllUnpaidRentalsWhenProcessWorks(){
        User u = new User("TESTE","teste@teste.com", "teste","teste","Teste",60f,1.5f,(long)1111314231242122.0,false,10.0);
        NormalSlots ns1 = new NormalSlots(10000,10,10);
        ElectricSlot es1 = new ElectricSlot(10001,10,10,220,50);
        Park p1 = new Park(10000,"10000",42,-8,ns1,es1,30);
        NormalSlots ns2 = new NormalSlots(10002,10,10);
        ElectricSlot es2 = new ElectricSlot(10003,10,10,220,50);
        Park p2 = new Park(10001,"10001",42,-9,ns2,es2,30);
        Bicycle b = new RoadBicycle("10000", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,10);
        Rental r = new Rental(10000,b,u,p1,p2, LocalDateTime.of(2018,12,30,14,0,0),
                LocalDateTime.of(2018,12,30,15,0,0));
        List<Rental> l = rd.getUnpaidRentalsTotal("TESTE");
        assertTrue(l!=null);
        assertTrue(!l.isEmpty());
        assertTrue(l.get(0).equals(r));
    }

    @Test
    public void getTimeBicycleUnlockedWhenProcessFails(){
        try{
            rd.getTimeBicycleUnlocked("10001");
            fail();
        }
        catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void getTimeBicycleUnlockedWhenProcessWorks(){
        long time = rd.getTimeBicycleUnlocked("10000");
        assertTrue(time>0);
    }

    @Test
    public void getBicycleUserActiveRentalWhenProcessFails(){
        try{
            rd.getBicycleUserActiveRental("FAIL");
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void getBicycleUserActiveRentalWhenProcessWorks(){
        RoadBicycle expected = new RoadBicycle("10000", Bicycle.BicycleStatus.AVAILABLE,150f,10,10);
        Bicycle result = rd.getBicycleUserActiveRental("TESTE");
        assertTrue(expected.equals(result));
    }

}

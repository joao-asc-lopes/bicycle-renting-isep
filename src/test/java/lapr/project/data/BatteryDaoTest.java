package lapr.project.data;

import lapr.project.model.bicycle.Battery;
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
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@RunWith(JUnitPlatform.class)
public class BatteryDaoTest {
    private static BatteryDao bd;
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
        bd=new BatteryDao();
        if(!DBTestAgent.backupDB(databaseTester)){
            throw new Exception();
        }
        FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
        dataSet = x.build(new FileInputStream("target/test-classes/battery.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
    }

    @AfterEach
    protected void tearDown() throws Exception
    {
        if(changed) {
            FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
            dataSet = x.build(new FileInputStream("target/test-classes/battery.xml"));
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
    public void testGetBatteryWhenProcessFails(){
        try {
            Battery bat = bd.getBattery(10003);
            assertTrue(bat==null);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetBatteryWhenProcessWorks(){
        try{
            Battery bat = bd.getBattery(10000);
            assertTrue(bat!=null);
        }
        catch(IllegalArgumentException e){
            assertTrue(false);
        }
    }

    @Test
    public void testAddBatteryWhenProcessFails(){
        changed=true;
        int id = bd.addBattery(-10,-10,-10);
        assertTrue(id==-1);

    }

    @Test
    public void testAddBatteryWhenProcessWorks(){
        changed=true;
        int id = bd.addBattery(260,250,15);
        assertTrue(id!=-1);
    }

    @Test
    public void testRemoveBatteryWhenProcessFails(){
        changed=true;
        boolean result = bd.removeBattery(10003);
        assertTrue(!result);
    }

    @Test
    public void testRemoveBatteryWhenProcessWorks(){
        changed=true;
        boolean result = bd.removeBattery(10000);
        assertTrue(result);
    }
}

package lapr.project.data;

import lapr.project.model.bikenetwork.Path;
import lapr.project.model.park.InterestPoint;
import lapr.project.model.park.Location;
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
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@RunWith(JUnitPlatform.class)
public class PathDaoTest {
    private static PathDao wd;
    private static IDatabaseTester databaseTester;
    private static FlatXmlDataSet dataSet;
    private static boolean changed;

    private Location location1 = new InterestPoint(10000, "Some name", 10, 10, 10);
    private Location location2 = new InterestPoint(10001, "Some name", 10, 10, 10);

    private Location location3 = new InterestPoint(10010, "Some name", 10, 10, 10);

    private Location location4 = new InterestPoint(10011, "Some name", 10, 10, 10);
    private Path path = new Path(10, 1, 10);

    @BeforeAll
    protected static void init() throws Exception {
        Properties properties =
                new Properties(System.getProperties());
        InputStream input = new FileInputStream("target/classes/application.properties");
        properties.load(input);
        System.setProperties(properties);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "oracle.jdbc.OracleDriver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:oracle:thin://@vsrvbd1.dei.isep.ipp.pt:1521/pdborcl");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "LAPR3_G40");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "brogrammers");
        databaseTester = new JdbcDatabaseTester("oracle.jdbc.OracleDriver",
                "jdbc:oracle:thin://@vsrvbd1.dei.isep.ipp.pt:1521/pdborcl", "LAPR3_G40", "brogrammers") {
            @Override
            public IDatabaseConnection getConnection() throws Exception {
                IDatabaseConnection connection = super.getConnection();
                connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Oracle10DataTypeFactory());
                return connection;
            }
        };
        changed = false;
        wd = new PathDao();
        if (!DBTestAgent.backupDB(databaseTester)) {
            throw new Exception();
        }
        FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
        dataSet = x.build(new FileInputStream("target/test-classes/pathInformation.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
    }


    @AfterEach
    protected void tearDown() throws Exception {
        if (changed) {
            FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
            dataSet = x.build(new FileInputStream("target/test-classes/pathInformation.xml"));
            DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
            changed = false;
        }
    }

    @AfterAll
    protected static void finish() throws Exception {
        if (!DBTestAgent.restoreDB(databaseTester)) {
            throw new Exception();
        }
        databaseTester.getConnection().close();
    }

    @Test
    public void testGetWindInformationWhenProcessFails() {
        try {
            Path result = wd.getPath(location3, location4);
            assertTrue(result == null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            Path result = wd.getPath(location1, location4);
            assertTrue(result == null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            Path result = wd.getPath(location3, location2);
            assertTrue(result == null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    //
    @Test
    public void testGetWindInformationWhenProcessWorks() {
        try {
            Path result = wd.getPath(location1, location2);
            assertTrue(result != null);
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testAddWindInformationWhenProcessFails() {
        changed = true;
        boolean result = wd.addPath(path, location1, location2);
        assertTrue(!result);
    }

    @Test
    public void testAddWindInformationWhenProcessWorks() {
        changed = true;
        location2.setIdLocation(10002);
        boolean result = wd.addPath(path, location1, location2);
        assertTrue(result);
    }

    @Test
    public void testUpdateWindInformationWhenProcessFails() {
        changed = true;
        boolean result = wd.updatePath(location1.getIdLocation(),location2.getIdLocation(),path.getWindSpeed(),2,path.getKineticFriction());
        assertTrue(!result);
    }

    @Test
    public void testUpdateWindInformationWhenProcessWorks() {
        changed = true;
        boolean result = wd.updatePath(location1.getIdLocation(),location2.getIdLocation(),path.getWindSpeed(),path.getWindDirection(),path.getKineticFriction());
        assertTrue(result);
    }
}

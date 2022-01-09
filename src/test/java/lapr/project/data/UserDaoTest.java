package lapr.project.data;

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
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@RunWith(JUnitPlatform.class)
public class UserDaoTest {
    private static UserDao ud;
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
        ud=new UserDao();
        if(!DBTestAgent.backupDB(databaseTester)){
            throw new Exception();
        }
        FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
        dataSet = x.build(new FileInputStream("target/test-classes/users.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
    }

    @AfterEach
    protected void tearDown() throws Exception
    {
        if(changed) {
            FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
            dataSet = x.build(new FileInputStream("target/test-classes/users.xml"));
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
    public void testUserWhenUserExists(){
        try {
            User u = ud.getUser("TESTE");
            assertTrue(u != null);
            assertTrue(u.getUsername().equals("TESTE"));
        }
        catch(IllegalArgumentException e){
            assertTrue(false);
        }
    }

    @Test
    public void testUserWhenUserNotExists(){
        try{
            User u = ud.getUser("WRONG");
            assertTrue(false);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void testAddUserWhenProcessFails(){
        changed=true;
        User u = new User("Daniel","TESTE","teste","teste","Teste",1,2,(long)111112222233333.0,false,15.0);
        boolean result = ud.addUser(u);
        assertTrue(!result);
    }

    @Test
    public void testAddUserWhenProcessWorks(){
        changed=true;
        User u = new User("Daniel","teste3@teste.com","exploit","exploit","Exploit",2f,4.5f,(long)9941222233334444.0,true,15.0);
        boolean result = ud.addUser(u);
        assertTrue(result);
    }

    @Test
    public void testRemoveUserWhenProcessFails(){
        changed=true;
        boolean result = ud.removeUser("WRONG");
        assertTrue(!result);
    }

    @Test
    public void testRemoveUserWhenProcessWorks(){
        changed=true;
        boolean result = ud.removeUser("TESTE");
        assertTrue(result);
    }

    @Test
    public void testUpdateRentalStatusWhenProcessFails(){
        changed=true;
        boolean result = ud.updateRentalStatus("WRONG", false);
        assertTrue(!result);
    }

    @Test
    public void testUpdateRentalStatusWhenProcessWorks(){
        changed=true;
        boolean result = ud.updateRentalStatus("TESTE", false);
        assertTrue(result);
    }

    @Test
    public void testAwardUserPointsWhenProcessFails(){
        changed=true;
        boolean result = ud.awardUserPoints("WRONG",10);
        assertTrue(!result);
    }

    @Test
    public void testAwardUserPointsWhenProcessWorks(){
        changed=true;
        boolean result = ud.awardUserPoints("TESTE",10);
        assertTrue(result);
    }

    @Test
    public void testCreateReceiptWhenProcessFails(){
        changed=true;
        int id = ud.createReceipt(-10,-10);
        assertTrue(id==-1);
    }

    @Test
    public void testCreateReceiptWhenProcessWorks(){
        changed=true;
        int id = ud.createReceipt(40,30);
        assertTrue(id!=-1);
    }



    @Test
    public void testInsertReceiptRentalWhenProcessFails(){
        changed=true;
        boolean result = ud.insertReceiptRental(10000,10000);
        assertTrue(!result);
    }

    @Test
    public void testInsertReceiptRentalWhenProcessWorks(){
        changed=true;
        boolean result = ud.insertReceiptRental(10000,10001);
        assertTrue(result);
    }
}

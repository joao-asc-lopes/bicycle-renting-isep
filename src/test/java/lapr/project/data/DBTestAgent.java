package lapr.project.data;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DBTestAgent {

    /**
     * Method that makes a full backup of the DB
     * @return True if operation works with sucess, false otherwise
     */
    public static boolean backupDB(IDatabaseTester databaseTester){
        try {
            QueryDataSet partialDataSet = new QueryDataSet(databaseTester.getConnection());
            partialDataSet.addTable("RECEIPTS", "SELECT * FROM RECEIPTS");
            partialDataSet.addTable("LOCATIONS", "SELECT * FROM LOCATIONS");
            partialDataSet.addTable("USERS", "SELECT * FROM USERS");
            partialDataSet.addTable("BATTERIES", "SELECT * FROM BATTERIES");
            partialDataSet.addTable("BICYCLES", "SELECT * FROM BICYCLES");
            partialDataSet.addTable("ELECTRIC_BICYCLES", "SELECT * FROM ELECTRIC_BICYCLES");
            partialDataSet.addTable("ROAD_BICYCLES", "SELECT * FROM ROAD_BICYCLES");

            partialDataSet.addTable("INTEREST_POINTS", "SELECT * FROM INTEREST_POINTS");
            partialDataSet.addTable("MOUNTAIN_BICYCLES", "SELECT * FROM MOUNTAIN_BICYCLES");
            partialDataSet.addTable("SLOTS", "SELECT * FROM SLOTS");
            partialDataSet.addTable("ELECTRIC_SLOTS", "SELECT * FROM ELECTRIC_SLOTS");
            partialDataSet.addTable("NORMAL_SLOTS", "SELECT * FROM NORMAL_SLOTS");
            partialDataSet.addTable("PARKS", "SELECT * FROM PARKS");
            partialDataSet.addTable("RENTALS", "SELECT * FROM RENTALS");
            partialDataSet.addTable("RECEIPT_RENTAL", "SELECT * FROM RECEIPT_RENTAL");
            partialDataSet.addTable("BICYCLE_SLOT", "SELECT * FROM BICYCLE_SLOT");
            partialDataSet.addTable("PATH_INFORMATION","SELECT * FROM PATH_INFORMATION");
            FlatXmlDataSet.write(partialDataSet, new FileOutputStream("target/test-classes/backup.xml"));

            DatabaseOperation.DELETE_ALL.execute(databaseTester.getConnection(), partialDataSet);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    /**
     * Method that makes a restore of a previous backup of the DB
     * @return True if operation works with sucess, false otherwise
     */
    public static boolean restoreDB(IDatabaseTester databaseTester){
        try {
            FlatXmlDataSetBuilder x = new FlatXmlDataSetBuilder();
            FlatXmlDataSet dataSet = x.build(new FileInputStream("target/test-classes/backup.xml"));
            DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }


}


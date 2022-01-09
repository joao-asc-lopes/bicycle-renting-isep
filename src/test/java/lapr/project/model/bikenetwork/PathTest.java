package lapr.project.model.bikenetwork;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class PathTest {

    private int initialPark;
    /**
     * Variable that is representative of the final Park id
     */
    private int finalPark;

    /**
     * Variable that is representative of the speed of the wind
     */
    private double windSpeed;
    /**
     * Variable that is representative of the different directions of the wind
     * >>>>>>> Stashed changes
     */
    private double windDirection;

    /**
     * Contains the electric expense of the connection;
     */
    private double electricExpense;

    /**
     * Represents the aerodynamic coefficient between the two parks.
     */
    private double kineticFriction;

    private Path path;
    /**
     * Average kinetic friction of the path, in case none is input.
     */

    @BeforeEach
    public void setUp(){
        this.initialPark = 1;
        this.finalPark = 2;
        this.electricExpense = 10;
        this.kineticFriction = 1;
        this.windDirection = 10;
        this.windSpeed = 10;
        this.path = new Path( this.windSpeed, this.windDirection, this.electricExpense);
    }


    @Test
    public void ensureGetWindSpeedWorks(){
        double expected = 10;
        double result = this.path.getWindSpeed();
        assertEquals(expected,result);
    }
    @Test
    public void ensureSetWindSpeedWorks(){
        try{
            this.path.setWindSpeed(-10);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        this.path.setWindSpeed(11);
        double expected = 11;
        double result = this.path.getWindSpeed();
        assertEquals(expected,result);
    }
    @Test
    public void ensureGetElectricExpenseWorks(){
        double expected = 0;
        double result = this.path.getElectricExpense();
        assertEquals(expected,result);
    }
    @Test
    public void ensureSetElectricExpenseWorks(){
        this.path.setElectricExpense(145);
        double expected = 145;
        double result = this.path.getElectricExpense();
        assertEquals(expected,result);
    }
    @Test
    public void ensureGetWindDirectionWorks(){
        double expected = 10;
        double result = this.path.getWindDirection();
        assertEquals(expected,result);
    }
    @Test
    public void ensureSetWindDirection(){
        try{
            this.path.setWindDirection(-181);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        try{
            this.path.setWindDirection(361);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        this.path.setWindDirection(124);
        double expected = 124;
        double result = this.path.getWindDirection();
        assertEquals(expected,result);
    }
    @Test
    public void ensureGetKinectic(){
        double expected = 10;
        double result = this.path.getKineticFriction();
        assertEquals(expected,result);
    }
    @Test
    public void ensureSetKinectic(){
        try{
            this.path.setKineticFriction(-1);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        this.path.setKineticFriction(34);
        double expected = 34;
        double result = this.path.getKineticFriction();
        assertEquals(expected,result);
    }

    @Test
    public void testEquals(){
        assertTrue(!path.equals(null));
        assertTrue(!path.equals(""));
        assertTrue(path.equals(path));
        Path p =  new Path( this.windSpeed, this.windDirection, this.electricExpense);
        assertTrue(p.equals(path));
    }

    @Test
    public void testHashCode(){
        assertTrue(path.hashCode()==1076102081);
    }

}
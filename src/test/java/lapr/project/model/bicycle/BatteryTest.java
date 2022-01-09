package lapr.project.model.bicycle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatteryTest {
    Battery bat;

    @BeforeEach
    public void setUp() {
        bat = new Battery(1,200, 200, 1);
    }

    @Test
    void testIfConstructorWithoutIDThrowExceptionWithWrongData(){
        //Max Charge with a value of 0 or less
        try{
            Battery bat = new Battery(1,0,14,14f);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Current Charge bigger than Max Charge
        try{
            Battery bat = new Battery(13,14,14f);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Current Charge less than 0
        try{
            Battery bat = new Battery(15,-1,14f);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Weight with value of 0 or less
        try{
            Battery bat = new Battery(120,90,-1);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    void testIfConstructorWithoutIDDontThrowExceptionWithCorrectData(){
        try{
            Battery bat = new Battery(120,90,30f);
            assertTrue(true);
        }
        catch(IllegalArgumentException e){
            fail();
        }
    }

    @Test
    void getMaximum_battery_charge() {
        double expected = 200;
        double result = bat.getMaxCharge();
        assertEquals(expected, result);
    }

    @Test
    void setMaximum_battery_charge() {
        bat.setMaxCharge(220);
        double expected = 220;
        double result = bat.getMaxCharge();
        assertEquals(expected, result);
    }

    @Test
    void getCurrent_battery_charge() {
        double expected = 200;
        double result = bat.getCurrentCharge();
        assertEquals(expected, result);
    }

    @Test
    void setCurrent_battery_charge() {
        bat.setCurrentCharge(100);
        double expected = 100;
        double result = bat.getCurrentCharge();
        assertEquals(expected, result);
    }

    @Test
    void getVoltage() {
        double expected = 2;
        double result = bat.getVoltage();
        assertEquals(expected, result);
    }

    @Test
    public void ensureCostructorWithWrongWeightThrowsException(){
        boolean expFlag = false;
        boolean flag = true;
        try {
            Battery bat3 = new Battery(1, 1, 1, -1);


        } catch (IllegalArgumentException e){
            flag = false;
        }
        assertEquals(expFlag,flag);

    }



    @Test
    void testIfEqualReturnsTrueIfObjectsAreEqual() {
        boolean expected = true;
        Battery bat2 = new Battery(1,200, 200, 1);
        boolean result = bat2.equals(bat);
        assertEquals(result, expected);
    }

    @Test
    void testIfHashCodeReturnsCorrectValue() {
        Battery bat2 = new Battery(1,200, 200, 1);
        int expected = bat2.hashCode();
        int result = bat.hashCode();
        assertEquals(result, expected);
    }

    @Test
    public void testIfDifferentHashFromDifferentValues() {
        Battery bat2 = new Battery(7,789, 180, 2);

        assertNotEquals(bat2.hashCode(), this.bat.hashCode());

    }

    @Test
    public void ensureDifferentObjectsAreNotEqualAndReturnFalse() {
        Battery bat2 = new Battery(4,911, 180, 1);
        boolean expFlag = false;
        boolean result = this.bat.equals(bat2);
        assertEquals(expFlag, result);
    }

    @Test
    public void ensureObjectsNullReturnsFalseEquals() {
        Object o = null;
        boolean expFlag = false;
        boolean result = this.bat.equals(o);
        assertEquals(expFlag, result);

    }

    @Test
    public void ensureDifferntKindOfObjectsReturnFalse() {
        String test = "test";
        boolean expFlag = false;
        boolean result = this.bat.equals(test);
        assertEquals(expFlag, result);
    }

    @Test
    public void ensureGetIdReturnsTheCorrectID(){
        Battery bat2 = new Battery(4,911, 180, 1);
        int expected = 4;
        int result = bat2.getId();
        assertEquals(expected,result);

    }

    @Test
    public void ensureGetPowerBikeWorks(){
        assertEquals(400.0, this.bat.getPowerBattery());
    }


}
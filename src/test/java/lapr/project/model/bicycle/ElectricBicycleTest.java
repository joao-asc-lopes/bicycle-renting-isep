package lapr.project.model.bicycle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElectricBicycleTest {
    ElectricBicycle bike;
    Battery bat;
    Battery bat2;
    ElectricBicycle bike2;
    Bicycle bike3;
    Bicycle bike4;

    @BeforeEach
    public void setUp() {
        bat = new Battery(1, 200, 200, 1);
        bat2 = new Battery(2, 500, 200, 1);
        bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), bat, 1,10,5.0);
        bike2 = new ElectricBicycle("ID2teste", Bicycle.statusByCode(1), bat, 1,10,5.0);

    }

    @Test
    void testIfConstructorWithoutIDThrowExceptionWithWrongData(){
        //Status null
        try{
            ElectricBicycle eb = new ElectricBicycle("IDteste",null,bat,150f,0.1,5.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Battery null
        try{
            ElectricBicycle eb = new ElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,null,150f,0.1,5.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Weight with value 0 or less
        try{
            ElectricBicycle eb = new ElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,bat,0f,0.1,5.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Coefficient less than 0
        try{
            ElectricBicycle eb = new ElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,bat,150f,-0.1,5.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    void testIfConstructorWithoutIDDontThrowExceptionWithCorrectData(){
        try{
            ElectricBicycle eb = new ElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,bat,150f,0.1,5.0);
            assertTrue(true);
        }
        catch(IllegalArgumentException e){
            fail();
        }
    }

    @Test
    void getId_bike() {
        String expected = "IDteste";
        String result = bike.getId();
        assertEquals(expected, result);
    }

    @Test
    void setId_bike() {
        bike.setId("IDteste");
        String expected = "IDteste";
        String result = bike.getId();
        assertEquals(expected, result);
    }

    @Test
    void getState() {
        int expected = 1;
        int result = bike.getStatus().statusCode();
        assertEquals(expected, result);
    }

    @Test
    void setState() {
        bike.setStatus(Bicycle.statusByCode(3));
        int expected = 3;
        int result = bike.getStatus().statusCode();
        assertEquals(expected, result);
    }

    @Test
    void setStateTwo() {
        bike.setStatus(Bicycle.statusByCode(2));
        int expected = 2;
        int result = bike.getStatus().statusCode();
        assertEquals(expected, result);
    }

    @Test
    void setStateOne() {
        bike.setStatus(Bicycle.statusByCode(1));
        int expected = 1;
        int result = bike.getStatus().statusCode();
        assertEquals(expected, result);
    }

    @Test
    void setStateNonExisting() {
        boolean flag = true;
        try {
            bike.setStatus(Bicycle.statusByCode(5));

            int result = bike.getStatus().statusCode();


        } catch (NullPointerException|IllegalArgumentException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);


    }

    @Test
    void getBattery() {
        Battery expected = bat;
        Battery result = bike.getBattery();
        assertEquals(expected, result);
    }

    @Test
    void setBattery() {
        bike.setBattery(bat2);
        Battery expected = bat2;
        Battery result = bike.getBattery();
        assertEquals(expected, result);
    }

    @Test
    void testHashCode() {
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), this.bat, 1,10,5.0);
        ElectricBicycle bike2 = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), this.bat, 1,10,5.0);
        int expected = bike2.hashCode();
        int result = bike.hashCode();
        assertEquals(expected, result);
    }

    @Test
    void testIfEqualsReturnsFalseIfObjectsHaveDifferentClasses() {
        RoadBicycle bike1 = new RoadBicycle("IDteste", Bicycle.statusByCode(2), 1,10,5.0);
        ElectricBicycle bike2 = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), this.bat, 1,10,5.0);
        boolean expected = false;
        boolean result = bike2.equals(bike1);
        assertEquals(expected, result);
    }

    @Test
    void testIfEqualsReturnsFalseIfObjectsHaveDifferentIDs() {
        ElectricBicycle bike1 = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), this.bat, 1,10,5.0);
        ElectricBicycle bike2 = new ElectricBicycle("IDteste2", Bicycle.statusByCode(1), this.bat2, 1,10,5.0);
        boolean expected = false;
        boolean result = bike2.equals(bike1);
        assertEquals(expected, result);
    }


    @Test
    void testIfEqualsReturnsTrueIfBicyclesAreEqual() {
        ElectricBicycle bike1 = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), this.bat, 1,10,5.0);
        ElectricBicycle bike2 = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), this.bat, 1,10,5.0);
        boolean expected = true;
        boolean result = bike2.equals(bike1);
        assertEquals(expected, result);
    }

    @Test
    public void testIfNullInEqualsReturnReturnsFalse() {
        Object o = null;
        ElectricBicycle bike1 = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), this.bat, 1,10,5.0);
        boolean expected = false;
        boolean result = bike1.equals(o);
        assertEquals(expected, result);
    }

    @Test
    void testDifferentHashCode() {
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), this.bat, 1,10,5.0);
        ElectricBicycle bike2 = new ElectricBicycle("IDteste2", Bicycle.statusByCode(1), this.bat, 1,10,5.0);
        int expected = bike2.hashCode();
        int result = bike.hashCode();
        assertNotEquals(expected, result);
    }

    @Test
    public void ensureGetMissingChargeWorks() {
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), this.bat2, 1,10,5.0);
        double expected = 300;
        double result = bike.getMissingCharge();
        assertEquals(expected, result);

    }

    @Test
    public void ensureGetMissingChargeWhenTheyAreEqualReturns0() {

        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), new Battery(10, 200, 200, 10), 1,10,5.0);
        double expected = 0;
        double result = bike.getMissingCharge();
        assertEquals(expected, result);
    }

    @Test
    public void ensureGetPercentageWorks() {
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), this.bat, 1,10,5.0);
        double expected = 100;
        double result = bike.percentageBattery();
        assertEquals(expected, result);
    }

    @Test
    public void ensureGetPercentageWorks2() {
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), this.bat2, 1,10,5.0);
        double expected = 40;


        double result = bike.percentageBattery();
        assertEquals(expected, result);

    }

    @Test
    public void ensureGetWeightWorks(){
        assertEquals(1,this.bike.getWeight());
    }

    @Test
    public void ensureSetWeightWortks(){
        this.bike.setWeight(2);
        assertEquals(2,this.bike.getWeight());
    }

    @Test
    public void ensureToStringWorks(){
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), this.bat, 1,10,5.0);
        String expected = "Bike #IDteste";
        String result = bike.toString();
        assertEquals(expected,result);

    }

    @Test
    public void ensureBikeWithNullIdCannotBeTested(){
        boolean flag = false;
        try{
            ElectricBicycle bike = new ElectricBicycle(null, Bicycle.statusByCode(2), this.bat, 1,10,5.0);
        } catch (IllegalArgumentException e){
            flag = true;
        }
        assertEquals(true,flag);

    }


    @Test
    public void ensureEqualsReturnsFalse(){
        boolean result = this.bike.equals(null);
        boolean expected = false;
        assertEquals(expected,result);
    }
    @Test
    public void ensureEqualsREturnsFalse(){
        String test = "test";
        boolean result = this.bike.equals(test);
        assertEquals(false,result);
    }
    @Test
    public void ensureEqualsReturnsFalseDifferentBikes(){
        boolean result = this.bike.equals(this.bike2);
        assertEquals(false,result);

    }
    @Test
    public void ensureEqualsWithSameBikeReturnsTrue(){
        boolean result = this.bike.equals(bike);
        assertEquals(true,result);
    }

    @Test
    public void ensureHashCodeWithDifferentIdReturnFalse(){
        assertNotEquals(this.bike2.hashCode(),this.bike.hashCode());
    }
    @Test
    public void ensureHashCodeReturnsTrue(){
        assertEquals(this.bike2.hashCode(), this.bike2.hashCode());
    }

}
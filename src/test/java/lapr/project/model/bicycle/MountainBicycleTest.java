package lapr.project.model.bicycle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MountainBicycleTest {
    MountainBicycle bike;
    MountainBicycle bike2;

    @BeforeEach
    public void setUp() {
        bike = new MountainBicycle("IDteste", Bicycle.statusByCode(1), 1,1,5.0);
        bike2 = new MountainBicycle("IDtest2e", Bicycle.statusByCode(1), 1,1,5.0);
    }

    @Test
    void testIfConstructorWithoutIDThrowExceptionWithWrongData(){
        //Null Status
        try{
            MountainBicycle mb = new MountainBicycle("IDteste",null,150f,0.2,5.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Weight with value 0 or less
        try{
            MountainBicycle mb = new MountainBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,0f,0.2,5.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Coefficient less than 0
        try{
            MountainBicycle mb = new MountainBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,150f,-0.1,5.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    void testIfConstructorWithoutIDDontThrowExceptionWithCorrectData(){
        try{
            MountainBicycle mb = new MountainBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
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
        bike.setId("IDteste2");
        String expected = "IDteste2";
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
        bike.setStatus(Bicycle.statusByCode(1));
        int expected = 1;
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
        bike.setStatus(Bicycle.statusByCode(3));
        int expected = 3;
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
    void testHashCode() {
        MountainBicycle bike = new MountainBicycle("IDteste", Bicycle.statusByCode(2), 1,1,5.0);
        MountainBicycle bike2 = new MountainBicycle("IDteste", Bicycle.statusByCode(3), 1,1,5.0);
        int expected = bike2.hashCode();
        int result = bike.hashCode();
        assertEquals(expected, result);
    }

    @Test
    void testIfEqualsReturnsFalseIfObjectsHaveDifferentClasses() {
        RoadBicycle bike1 = new RoadBicycle("IDteste", Bicycle.statusByCode(2), 1,1,5.0);
        MountainBicycle bike2 = new MountainBicycle("IDteste", Bicycle.statusByCode(1), 1,1,5.0);
        boolean expected = false;
        boolean result = bike2.equals(bike1);
        assertEquals(expected, result);
    }

    @Test
    void testIfEqualsReturnsFalseIfObjectsHaveDifferentIDs() {
        MountainBicycle bike1 = new MountainBicycle("IDteste", Bicycle.statusByCode(2), 1,1,5.0);
        MountainBicycle bike2 = new MountainBicycle("IDteste2", Bicycle.statusByCode(1), 1,1,5.0);
        boolean expected = false;
        boolean result = bike2.equals(bike1);
        assertEquals(expected, result);
    }

    @Test
    void testIfEqualsReturnsTrueIfBicyclesAreEqual() {
        MountainBicycle bike1 = new MountainBicycle("IDteste", Bicycle.statusByCode(1), 1,1,5.0);
        MountainBicycle bike2 = new MountainBicycle("IDteste", Bicycle.statusByCode(1), 1,1,5.0);
        boolean expected = true;
        boolean result = bike2.equals(bike1);
        assertEquals(expected, result);
    }

    @Test
    public void testIfEqualsWithDifferentMountainBicyclesReturnFalse() {
        boolean expFlag = false;
        Object o = null;
        MountainBicycle bike1 = new MountainBicycle("IDteste", Bicycle.statusByCode(1), 1,1,5.0);
        boolean result = bike1.equals(o);
        assertEquals(expFlag, result);


    }

    @Test
    void testDifferentHashCode() {
        MountainBicycle bike = new MountainBicycle("IDteste", Bicycle.statusByCode(2), 1,1,5.0);
        MountainBicycle bike2 = new MountainBicycle("IDteste2", Bicycle.statusByCode(3), 1,1,5.0);
        int expected = bike2.hashCode();
        int result = bike.hashCode();
        assertNotEquals(expected, result);
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
    public void ensureToStringBikeWorks(){
        MountainBicycle bike = new MountainBicycle("IDteste", Bicycle.statusByCode(2), 1,1,5.0);
        String expected = "Bike #IDteste";
        String result = bike.toString();
        assertEquals(expected,result);
    }
    @Test
    public void ensureBikeWithNullIdCannotBeTested(){
        boolean flag = false;
        try{
            MountainBicycle bike = new MountainBicycle(null, Bicycle.statusByCode(2),1,1,1);
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
    public void ensureHashCodeWithDifferenteBikes(){
        assertNotEquals(this.bike.hashCode(), this.bike2.hashCode());
    }
    @Test
    public void ensureHashCodeWithSameBikes(){
        assertEquals(this.bike.hashCode(), this.bike.hashCode());
    }

}
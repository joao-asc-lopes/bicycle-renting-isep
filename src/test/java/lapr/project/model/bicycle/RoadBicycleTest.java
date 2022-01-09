package lapr.project.model.bicycle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoadBicycleTest {
    RoadBicycle bike;
    RoadBicycle bike2;

    @BeforeEach
    public void setUp() {

        bike = new RoadBicycle("IDteste", Bicycle.statusByCode(1), 1,1,5.0);
        bike2 = new RoadBicycle("IDteste2", Bicycle.statusByCode(1), 1,1,5.0);
    }
    
    @Test
    void testIfConstructorWithoutIDThrowExceptionWithWrongData(){
        //Null Status
        try{
            RoadBicycle rb = new RoadBicycle("IDteste",null,150f,0.2,5.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Weight with value 0 or less
        try{
            RoadBicycle rb = new RoadBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,0f,0.2,5.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Coefficient less than 0
        try{
            RoadBicycle rb = new RoadBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,150f,-0.1,5.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    void testIfConstructorWithoutIDDontThrowExceptionWithCorrectData(){
        try{
            RoadBicycle rb = new RoadBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
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
    void testHashCode() {
        RoadBicycle bike = new RoadBicycle("IDteste", Bicycle.statusByCode(2), 1,1,5.0);
        RoadBicycle bike2 = new RoadBicycle("IDteste", Bicycle.statusByCode(3), 1,1,5.0);
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
        RoadBicycle bike1 = new RoadBicycle("IDteste", Bicycle.statusByCode(2), 1,1,5.0);
        RoadBicycle bike2 = new RoadBicycle("IDteste2", Bicycle.statusByCode(1), 1,1,5.0);
        boolean expected = false;
        boolean result = bike2.equals(bike1);
        assertEquals(expected, result);
    }


    @Test
    void testIfEqualsReturnsTrueIfBicyclesAreEqual() {
        RoadBicycle bike1 = new RoadBicycle("IDteste", Bicycle.statusByCode(1), 1,1,5.0);
        RoadBicycle bike2 = new RoadBicycle("IDteste", Bicycle.statusByCode(1), 1,1,5.0);
        boolean expected = true;
        boolean result = bike2.equals(bike1);
        assertEquals(expected, result);
    }

    @Test
    public void testIfObjectNullReturnsFalse() {
        boolean expFlag = false;
        RoadBicycle bike1 = new RoadBicycle("IDteste", Bicycle.statusByCode(1), 1,1,5.0);
        Object o = null;
        boolean result = bike1.equals(o);
        assertEquals(expFlag, result);
    }

    @Test
    void testDifferentHashCode() {
        RoadBicycle bike = new RoadBicycle("IDteste", Bicycle.statusByCode(2), 1,1,5.0);
        RoadBicycle bike2 = new RoadBicycle("IDteste2", Bicycle.statusByCode(3), 1,1,5.0);
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
    public void ensureToStringWorks(){
        RoadBicycle bike = new RoadBicycle("IDteste", Bicycle.statusByCode(2), 1,1,5.0);
        String expected = "Bike #IDteste";
        String result =  bike.toString();
        assertEquals(expected,result);
    }

    @Test
    public void ensureBikeWithNullIdCannotBeTested(){
        boolean flag = false;
        try{
            RoadBicycle bike = new RoadBicycle(null, Bicycle.statusByCode(2),1,1,1);
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
   public void ensureEqualsWithSameBike(){
       RoadBicycle x = new  RoadBicycle("IDteste", Bicycle.statusByCode(1), 1,1,5.0);
       assertTrue(bike.equals(x));
   }
   @Test
    public void ensureHashCodeWithSameBikeReturnsTrue(){
        assertNotEquals(this.bike.hashCode(), this.bike2.hashCode());
   }
   @Test
    public void ensureHashCodeWithDifferenteBikeReturnsEqualsHashCodes(){
        assertEquals(this.bike.hashCode(), this.bike.hashCode());
   }

    @Test
    public void testEquals(){
        Bicycle b = (Bicycle)bike;
        assertTrue(b.equals(b));
        assertTrue(!b.equals(null));
        assertTrue(!b.equals(""));
        RoadBicycle x = new  RoadBicycle("IDteste", Bicycle.statusByCode(1), 1,1,5.0);
        assertTrue(b.equals(x));
    }

    @Test
    public void testHashCodeBicycle(){
        Bicycle b = (Bicycle)bike;
        assertTrue(b.hashCode()==-1874674185);
    }


}
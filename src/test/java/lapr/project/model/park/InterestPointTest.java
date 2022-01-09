package lapr.project.model.park;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InterestPointTest {
    /**
     * Variable that represents a valid id location.
     */
    private int idLocation;
    /**
     * Variable that represents a valid name.
     */


    private String name;
    /**
     * Variable that represents a valid address.
     */

    private String address;
    /**
     * Variable that represents a valid latitude.
     */

    private double latitude;
    /**
     * Variable that represents a valid longitude.
     */

    private double longitude;
    /**
     * Variable that represents a valid instance of Interest Point.
     */

    private InterestPoint ip;


    @BeforeEach
    public void setUp() {
        this.idLocation = 1;
        this.name = "Monumento do Dani";
        this.address = "Rua do Dani";
        this.latitude = 11;
        this.longitude = 10;
        this.ip = new InterestPoint(idLocation, name, latitude, longitude, 1.0);
    }

    @Test
    void testIfConstructorWithoutIDThrowExceptionWithWrongData(){
        //Null name
        try{
            InterestPoint ip = new InterestPoint(null,42,-8,4);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Empty name
        try{
            InterestPoint ip = new InterestPoint("",42,-8,4);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Wrong latitude
        try{
            InterestPoint ip = new InterestPoint("Teste",-91,-8,4);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Wrong latitude
        try{
            InterestPoint ip = new InterestPoint("Teste",91,-8,4);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Wrong longitude
        try{
            InterestPoint ip = new InterestPoint("Teste",42,-181,4);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Wrong longitude
        try{
            InterestPoint ip = new InterestPoint("Teste",42,181,4);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    void testIfConstructorWithoutIDDontThrowExceptionWithCorrectData(){
        try{
            InterestPoint ip = new InterestPoint("Teste",42,-8,4);
            assertTrue(true);
        }
        catch(IllegalArgumentException e){
            fail();
        }
    }

    @Test
    public void ensureGetIdWorks() {
        System.out.println("Ensure Get id Interest Point works!");
        int expected = 1;
        int result = this.ip.getIdLocation();
        assertEquals(expected, result);
    }

    @Test
    public void ensureGetNameWorks() {
        System.out.println("Ensure Get Name works!");
        String expected = "Monumento do Dani";
        String result = this.ip.getName();
        assertEquals(expected, result);
    }



    @Test
    public void ensureGetLatitudeWorks() {
        System.out.println("Ensure Get Latitude Works!");
        double expected = 11;
        double result = this.ip.getLatitude();
        assertEquals(expected, result);
    }

    @Test
    public void ensureGetLongitudeWorks() {
        System.out.println("Ensure Get Longitude Works!");
        double expected = 10;
        double result = this.ip.getLongitude();
        assertEquals(expected, result);
    }

    @Test
    public void ensureSetIdWorks() {
        System.out.println("Ensure Set Id works!");
        this.ip.setIdLocation(2);
        int expected = 2;
        int result = this.ip.getIdLocation();
        assertEquals(expected, result);
    }

    @Test
    public void ensureSetNameWorks() {
        System.out.println("Ensure Set Name Works!");
        this.ip.setName("Monumento do Grande Daniel");
        String expected = "Monumento do Grande Daniel";
        String result = this.ip.getName();
        assertEquals(expected, result);
    }


    @Test
    public void ensureSetLatitudeWorks() {
        System.out.println("Ensure Set latitude Works!");
        this.ip.setLatitude(20);
        double expected = 20;
        double result = this.ip.getLatitude();
        assertEquals(expected, result);
    }

    @Test
    public void ensureSetLongitudeWorks() {
        System.out.println("Ensure Set Longitude Works");
        this.ip.setLongitude(21);
        double expected = 21;
        double result = this.ip.getLongitude();
        assertEquals(expected, result);
    }

    @Test
    public void ensureEqualsWithNullObjectReturnsFalse() {
        Object o = null;
        boolean expected = false;
        boolean result = this.ip.equals(o);
        assertEquals(expected, result);
    }

    @Test
    public void ensureEqualsReturnsFalseWithDifferentClassObjects() {
        String o = "Dani";
        boolean expected = false;
        boolean result = this.ip.equals(o);
        assertEquals(expected, result);
    }

    @Test
    public void ensureDifferentInterestPointsWithDifferentIdRetursFalse() {
        InterestPoint ip2 = new InterestPoint(2, "123", 10, 10, 1.0);
        boolean expected = false;
        boolean result = this.ip.equals(ip2);
        assertEquals(expected, result);
    }

    @Test
    public void ensureDifferentInterestWithSameIdReturnsTrue() {
        InterestPoint ip2 = new InterestPoint(1, "123", 10, 10, 1.0);
        boolean expected = true;
        boolean result = this.ip.equals(ip2);
        assertEquals(expected, result);

    }

    @Test
    public void ensureDifferentIdInterestHaveDifferentHashCodes() {
        InterestPoint ip2 = new InterestPoint(2, "123", 10, 10, 1.0);
        assertNotEquals(ip2.hashCode(), this.ip.hashCode());
    }

    @Test
    public void ensureSameIdDifferentInterestHaveSameHashCode() {
        InterestPoint ip2 = new InterestPoint(1, "123", 10, 10, 1.0);
        assertEquals(ip2.hashCode(), this.ip.hashCode());

    }

    @Test
    public void ensureGetTypeReturnsInterest() {
        String expected = "Interest Point";
        String result = this.ip.getType().toString();
        assertEquals(expected, result);
    }

    @Test
    public void ensureGetAltitudeReturnsCorrectValue() {
        double expected = 1.0;
        double result = this.ip.getAltitude();
        assertEquals(expected, result);
    }

    @Test
    public void ensureToStringWorks(){
        String expected = "Monumento do Dani";
        String result = this.ip.toString();
        assertEquals(expected,result);
    }

}

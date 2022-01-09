package lapr.project.model.park;


import lapr.project.model.bicycle.Battery;
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.ElectricBicycle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class ParkTest {

    /**
     * Variable representative of the id of the park.
     */
    private int idPark;
    /**
     * Variable representative of the latitude of the park.
     */

    private double latitude;
    /**
     * Variable representative of the longitude of the park.
     */

    private double longitude;
    /**
     * Variable representative of the normal slots of the park.
     */

    private NormalSlots normalSlotOfPark;
    /**
     * Variable representative of the electrical slots of the park.
     */

    private ElectricSlot electricSlotOfPark;
    /**
     * Variable representative of the name of the Park.
     */

    private String name;
    /**
     * Variable representative of the park.
     */
    private Park park;
    /**
     * Variable representative of a Normal Slot.
     */
    private NormalSlots nSlots;
    /**
     * Variable representative of an  Electrical Slot.
     */
    private ElectricSlot eSlots;
    /**
     * Variable that represents a battery.
     */
    private Battery bat;
    /**
     * Variable that represents a valid battery.
     */
    private Battery bat2;
    /**
     * Variable that represents a valid battery.
     */
    private Battery bat3;
    /**
     * Variable that represents a valid bike.
     */
    private ElectricBicycle bike;
    /**
     * Variable that represents a valid bike.
     */
    private ElectricBicycle bike2;
    /**
     * Variable that represents a valid bike.
     */
    private ElectricBicycle bike3;


    /**
     * Setup of the tests.
     */
    @BeforeEach
    public void setUp() {
        this.idPark = 1;
        this.latitude = 20.4;
        this.longitude = 30.5;
        this.normalSlotOfPark = new NormalSlots(1, 10, 5);
        this.electricSlotOfPark = new ElectricSlot(1, 40, 20,220,16);
        this.name = "Parque do Rossio";
        this.park = new Park(idPark, name, latitude, longitude, normalSlotOfPark, electricSlotOfPark, 1.0);
        this.nSlots = new NormalSlots(5, 5, 5);
        this.eSlots = new ElectricSlot(10, 10, 10,23 ,0.16);
       this.bat = new Battery(1, 1000, 500, 1);
        this.bat2 = new Battery(2, 4444444, 200, 1);
        this.bat3 = new Battery(2, 88, 0, 1);
        this.bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), bat, 1,10,5.0);
        this.bike2 = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), bat2, 1,10,5.0);
        this.bike3 = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), bat3, 1,10,5.0);
    }

    @Test
    void testIfConstructorWithoutIDThrowExceptionWithWrongData(){
        //Null name
        try{
            Park p = new Park(null,42,-8,normalSlotOfPark,electricSlotOfPark,1.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Empty name
        try{
            Park p = new Park("",42,-8,normalSlotOfPark,electricSlotOfPark,1.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Wrong latitude
        try{
            Park p = new Park("Teste",91,-8,normalSlotOfPark,electricSlotOfPark,1.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Wrong latitude
        try{
            Park p = new Park("Teste",-91,-8,normalSlotOfPark,electricSlotOfPark,1.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Wrong longitude
        try{
            Park p = new Park("Teste",42,-181,normalSlotOfPark,electricSlotOfPark,1.0);
            fail();
        }
        //Wrong longitude
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Wrong longitude
        try{
            Park p = new Park("Teste",42,181,normalSlotOfPark,electricSlotOfPark,1.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Null normalSlots
        try{
            Park p = new Park("Teste",42,-8,null,electricSlotOfPark,1.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        //Null electricSlots
        try{
            Park p = new Park("Teste",42,-8,normalSlotOfPark,null,1.0);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    void testIfConstructorWithoutIDDontThrowExceptionWithCorrectData(){
        try{
            Park p = new Park("Teste",42,-8,normalSlotOfPark,electricSlotOfPark,1.0);
            assertTrue(true);
        }
        catch(IllegalArgumentException e){
            fail();
        }
    }

    @Test
    public void ensureGetIdParkWorks() {
        System.out.println("Ensure get id of the park works!");
        int result = this.park.getIdLocation();
        int expResult = 1;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureGetLatitudeParkWorks() {
        System.out.println("Ensure get latitude of the park works!");
        double result = this.park.getLatitude();
        double expResult = 20.4;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureGetLongitudeParkWorks() {
        System.out.println("Ensure get longitude of the park works!");
        double result = this.park.getLongitude();
        double expResult = 30.5;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureGetNameParkWorks() {
        System.out.println("Ensure get name of the park works!");
        String result = this.park.getName();
        String expResult = "Parque do Rossio";
        assertEquals(expResult, result);
    }


    @Test
    public void ensureGetNormalSlotWorks() {
        System.out.println("Ensure get normal slots works!");
        NormalSlots result = this.park.getNormalSlots();
        NormalSlots expResult = this.normalSlotOfPark;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureGetElectricalSlotWorks() {
        System.out.println("Ensure get Electrical slots works");
        ElectricSlot result = this.park.getEletricalSlots();
        ElectricSlot expResult = this.electricSlotOfPark;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureSetIdWorks() {
        System.out.println("Ensure Set Id works");
        int newId = 2;
        this.park.setIdLocation(newId);
        int result = this.park.getIdLocation();
        int expResult = 2;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureSetLatitudeWorks() {
        System.out.println("Ensure Set Latitude Works!");
        double newLatitude = 30.6;
        this.park.setLatitude(newLatitude);
        double result = this.park.getLatitude();
        double expResult = 30.6;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureSetLongitudeWorks() {
        System.out.println("Ensure Set Longitude Works!");
        double newLongitude = 40.75;
        this.park.setLongitude(newLongitude);
        double result = this.park.getLongitude();
        double expResult = 40.75;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureSetNameWorks() {
        System.out.println("Ensure Set Name park works!");
        String newName = "Parque do Dani";
        this.park.setName(newName);
        String result = this.park.getName();
        String expResult = "Parque do Dani";
        assertEquals(expResult, result);
    }



    @Test
    public void ensureSetNormalSlotWorks() {
        System.out.println("Ensure Set Normal Slot works!");
        NormalSlots expResult = new NormalSlots(3, 10, 2);
        this.park.setNormalSlots(expResult);
        NormalSlots result = this.park.getNormalSlots();
        assertEquals(expResult, result);
    }

    @Test
    public void ensureSetElectricalSlotWorks() {
        System.out.println("Ensure Set Electrical Slot works!");
        ElectricSlot expResult = new ElectricSlot(10, 900, 800,220,50);
        this.park.setEletricalSlots(expResult);
        ElectricSlot result = this.park.getEletricalSlots();
        assertEquals(expResult, result);
    }

    @Test
    public void ensureEqualsWithNullReturnsFalse() {
        Object o = null;
        boolean result = this.park.equals(o);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureEqualsWithDifferentClassesReturnsFasle() {
        String t = "t";
        boolean result = this.park.equals(t);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureEqualsWithParksWithDifferentIdsReturnsFalse() {
        Park p = new Park(2, name, latitude, longitude, normalSlotOfPark, electricSlotOfPark, 1.0);
        boolean result = this.park.equals(p);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    /**
     * Should return true because the only distinguisher between them is the id.
     */
    @Test
    public void ensureEqualsWithParksWithDifferentNamesReturnsTrue() {
        Park p = new Park(idPark, "ISEP", latitude, longitude, normalSlotOfPark, electricSlotOfPark, 1.0);
        boolean result = this.park.equals(p);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Should return true because the only distinguisher between them is the id.
     */
    @Test
    public void ensureEqualsWithParksWithDifferentAddressesReturnsTrue() {
        Park p = new Park(idPark, name, latitude, longitude, normalSlotOfPark, electricSlotOfPark, 1.0);
        boolean result = this.park.equals(p);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Should return true because the only distinguisher between them is the id.
     */
    @Test
    public void ensureEqualsWithDifferentLatitudesReturnsTrue() {
        Park p = new Park(idPark, name, 900.758, longitude, normalSlotOfPark, electricSlotOfPark, 1.0);
        boolean result = this.park.equals(p);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Should return true because the only distinguisher between them is the ID.
     */
    @Test
    public void ensureEqualsWithDifferentLongitudesReturnsTrue() {
        Park p = new Park(idPark, name, latitude, 0.657, normalSlotOfPark, electricSlotOfPark, 1.0);
        boolean result = this.park.equals(p);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Should return true because the only distinguisher between them is the ID.
     */
    @Test
    public void ensureEqualsWithDifferentNormalSlotsReturnsTrue() {
        Park p = new Park(idPark, name, latitude, longitude, this.nSlots, electricSlotOfPark, 1.0);
        boolean result = this.park.equals(p);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Should return true because the only distinguisher between them is the ID.
     */
    @Test
    public void ensureEqualsWithDifferentElectricalSlotsReturnsTrue() {
        Park p = new Park(idPark, name, latitude, longitude, normalSlotOfPark, this.eSlots, 1.0);
        boolean result = this.park.equals(p);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureSameParkReturnsTrue() {
        Park p = new Park(idPark, name, latitude, longitude, normalSlotOfPark, electricSlotOfPark, 1.0);
        boolean result = this.park.equals(p);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureHashCodeForDifferentParkWithDifferentIdReturnDifferentHashCodes() {
        Park p2 = new Park(2, this.name, this.latitude, this.longitude, this.nSlots, this.eSlots, 1.0);
        assertNotEquals(this.park.hashCode(), p2.hashCode());
    }

    @Test
    public void ensureHashCodeForDifferentParksWithTheSameIdReturnsTheSameHashCode() {
        Park p2 = new Park(this.idPark, this.name, this.latitude, this.longitude, this.nSlots, this.eSlots, 1.0);
        assertEquals(p2.hashCode(), this.park.hashCode());
    }

    @Test
    public void ensureGetTypeReturnsPark() {
        String expected = "Park";
        String result = this.park.getType().toString();
        assertEquals(expected, result);
    }

    @Test
    public void ensureGetAltitudeReturnsCorrectValue() {
        double expected = 1.0;
        double result = this.park.getAltitude();
        assertEquals(expected, result);
    }

    @Test
    public void ensureShowInfoReturnsNothingWhenThereAreNoEletricalBicycles(){
        ArrayList<ElectricBicycle> eb = new ArrayList<>();
       ArrayList<String> result =  this.park.showInfoOfPark(eb);
       ArrayList<String> expected = new ArrayList<>();
       assertEquals(expected,result);
    }

    @Test
    public void ensureShowInfoWorks(){
        ArrayList<ElectricBicycle> eb = new ArrayList<>();
        eb.add(this.bike);
        eb.add(this.bike2);
        ArrayList<String> result =  this.park.showInfoOfPark(eb);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("The bicycle is charged 50.0%, and to complete the charge we need is : 00:17 hh/mm");
        expected.add("The Bike with the id :"+"IDteste" +" cannot be charged since the power of its battery is greater than what the slot offers!");
        assertEquals(expected,result);
    }





    @Test
    public void ensureShowInfo2Works(){
        ArrayList<ElectricBicycle> eb = new ArrayList<>();
        eb.add(this.bike);
        eb.add(this.bike);
        eb.add(this.bike2);
        eb.add(this.bike3);
        ArrayList<String> result =  this.park.showInfoOfPark(eb);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("The bicycle is charged 50.0%, and to complete the charge we need is : 00:34 hh/mm");
        expected.add("The bicycle is charged 50.0%, and to complete the charge we need is : 00:34 hh/mm");
        expected.add("The Bike with the id :"+"IDteste" +" cannot be charged since the power of its battery is greater than what the slot offers!");
        expected.add("The bicycle is charged 0.0%, and to complete the charge we need is : 00:06 hh/mm");
        assertEquals(expected,result);
    }





    @Test
    public void testToString(){
        String expected = "Parque do Rossio";
        String result = this.park.toString();
        assertEquals(expected,result);
    }



}








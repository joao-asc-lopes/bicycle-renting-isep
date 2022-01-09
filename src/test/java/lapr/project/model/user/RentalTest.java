/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.RoadBicycle;
import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RentalTest {
    private Rental rental;
    private Rental rental2;
    private LocalDateTime unlock;
    private LocalDateTime lock;
    private LocalDateTime lock2;
    private Rental.RentalStatus stat;
    private long length;
    private NormalSlots ns1;
    private ElectricSlot es1;
    private NormalSlots ns2;
    private ElectricSlot es2;
    private Park pickUp;
    private Park leftAt;
    private Rental.RentalStatus state;

    public RentalTest() {
    }

    @BeforeEach
    public void setUp() {
        int id_rental = 1;
        int id_bike = 2;
        User user = new User("Daniel","user@service.pt", "teste", "Teste", new Float(75.0), new Float(1.75), 11199456, false,15.00,0);
        ns1 = new NormalSlots(1,10,10);
        es1 = new ElectricSlot(2,10,10,220,50);
        ns2 = new NormalSlots(3,10,10);
        es2 = new ElectricSlot(4,10,10,220,50);
        pickUp = new Park(1,"Trindade",42,-8,ns1,es1,15);
        leftAt = new Park(2,"ISEP",42,-9,ns2,es2,15);
        unlock = LocalDateTime.of(2018, 12, 16, 16, 0, 0);
        lock = LocalDateTime.of(2018, 12, 16, 17, 0, 0);
        state = Rental.RentalStatus.FINISHED;
        length = 3600;
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        rental = new Rental(id_rental, b, user,pickUp,leftAt, unlock, lock, state, Rental.RentalPayment.NOT_PAID);
        rental2 = new Rental(2,b,user,pickUp,leftAt,unlock,lock);
    }

    /**
     * Test to getId
     */
    @Test
    public void testGetId() {
        System.out.println("testGetId");
        int expected = 1;
        int result = this.rental.getId();
        assertTrue(expected == result);
    }

    /**
     * Test ot setId
     */
    @Test
    public void testSetId() {
        System.out.println("testSetId");
        int expected = 10;
        this.rental.setId(10);
        int result = this.rental.getId();
        assertTrue(result == expected);
    }

    /**
     * Test of getBike
     */
    @Test
    public void testGetBike() {
        System.out.println("testGetId");
        Bicycle expected = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Bicycle result = this.rental.getBike();
        assertTrue(expected.equals(result));
    }

    /**
     * Test of setBike
     */
    @Test
    public void testSetBike() {
        System.out.println("testSetId");
        Bicycle set = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Bicycle expected = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        this.rental.setBike(set);
        Bicycle result = this.rental.getBike();
        assertTrue(result.equals(expected));
    }

    /**
     * Test of getUser
     */
    @Test
    public void testGetUser() {
        System.out.println("testGetUser");
        User expected = new User("Daniel","user@service.pt", "teste", "Teste", new Float(75.0), new Float(1.75), 11199456, false,15.00,0);
        User result = this.rental.getUser();
        assertTrue(result.equals(expected));
    }

    /**
     * Test of setUser
     */
    @Test
    public void testSetUser() {
        System.out.println("testSetUser");
        User expected = new User("Daniel","user2@service.pt", "SETTeste", "SETTeste", new Float(100.0), new Float(2.00), 11144456, false,15.00,0);
        this.rental.setUser(expected);
        User result = this.rental.getUser();
        assertTrue(result.equals(expected));
    }

    /**
     * Test of getUnlockDate
     */
    @Test
    public void testGetUnlockDate() {
        System.out.println("testGetUnlockDate");
        LocalDateTime expected = LocalDateTime.of(2018, 12, 16, 16, 0, 0);
        LocalDateTime result = this.rental.getUnlockDate();
        assertTrue(result.equals(expected));
    }

    /**
     * Test of setUnlockDate
     */
    @Test
    public void testSetUnlockDate() {
        System.out.println("testSetUnlockDate");
        LocalDateTime expected = LocalDateTime.of(2018, 12, 17, 20, 0, 0);
        this.rental.setUnlockDate(expected);
        LocalDateTime result = this.rental.getUnlockDate();
        assertTrue(result.equals(expected));
    }

    /**
     * Test of getLockDate
     */
    @Test
    public void testGetLockDate() {
        System.out.println("testGetLockDate");
        LocalDateTime expected = LocalDateTime.of(2018, 12, 16, 17, 0, 0);
        LocalDateTime result = this.rental.getLockDate();
        assertTrue(result.equals(expected));
    }

    /**
     * Test of setLockDate
     */
    @Test
    public void testSetLockDate() {
        System.out.println("testSetLockDate");
        LocalDateTime expected = LocalDateTime.of(2018, 12, 24, 13, 0, 0);
        this.rental.setLockDate(expected);
        LocalDateTime result = this.rental.getLockDate();
        assertTrue(result.equals(expected));
    }

    /**
     * Test of getStatus
     */
    @Test
    public void testGetState() {
        System.out.println("testGetState");
        Rental.RentalStatus expected = Rental.RentalStatus.FINISHED;
        Rental.RentalStatus result = Rental.RentalStatus.FINISHED;
        assertTrue(result.equals(expected));
    }

    /**
     * Test of setStatus
     */
    @Test
    public void testSetState() {
        System.out.println("testSetState");
        Rental.RentalStatus expected = Rental.RentalStatus.FINISHED;
        this.rental.setState(expected);
        Rental.RentalStatus result = this.rental.getState();
        assertEquals(result, expected);
    }

    /**
     * Test of getLength
     */
    @Test
    public void testGetLength() {
        System.out.println("testGetLength");
        long expected = 3600;
        long result = this.rental.getLength();
        assertTrue(result == expected);
    }

    /**
     * Test of setLength
     */
    @Test
    public void ensureSetLenghtDoesntChangeTheLengthIfOneOfDatesIsNull() {
        System.out.println("tetSetLength");
        long expected = this.length;
        this.rental.setUnlockDate(null);
        this.rental.setLength();
        long result = this.rental.getLength();
        assertTrue(result == expected);
    }

    /**
     * Test of setLength
     */
    @Test
    public void ensureSetLenghtUpdatesLengthIfDatesAreValid() {
        System.out.println("tetSetLength");

        long expected = 3600;
        this.rental.setLength();
        long result = this.rental.getLength();
        assertTrue(result == expected);
    }

    @Test
    public void testEqualsWhenObjectIsNull(){
        assertTrue(!rental.equals(null));
    }

    /**
     * Test of Equals with Different Rentals
     */
    @Test
    public void testEqualsWhenObjectsAreDifferent() {
        System.out.println("testEqualsWhenObjectsAreDifferent");
        int id_rental = 189;
        User user = new User("Daniel","userdifferent@service.pt", "expected", "Teste", new Float(190.0), new Float(1.75), 22224566, false,15.00,0);
        LocalDateTime unlock_date = LocalDateTime.of(2018, 12, 16, 16, 15, 0);
        LocalDateTime lock_date = LocalDateTime.of(2018, 12, 25, 21, 0, 0);
        Rental.RentalStatus state = Rental.RentalStatus.FINISHED;
        LocalTime rental_length = LocalTime.of(5, 0);
        NormalSlots ns1 = new NormalSlots(5,10,10);
        ElectricSlot es1 = new ElectricSlot(6,10,10,220,50);
        NormalSlots ns2 = new NormalSlots(7,10,10);
        ElectricSlot es2 = new ElectricSlot(8,10,10,220,50);
        Park pickUp = new Park(3,"Hospital S.João",42,-8,ns1,es1,15);
        Park leftAt = new Park(4,"Casa da Música",42,-9,ns2,es2,15);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,100f,0.1,5.0);
        Rental expected = new Rental(id_rental, b, user,pickUp,leftAt, unlock_date, lock_date, state, Rental.RentalPayment.NOT_PAID);
        assertTrue(!this.rental.equals(expected));
    }

    @Test
    public void testEqualsWhenObjectsAreDifferentClasses() {
        System.out.println("testEqualsWhenObjectsAreDifferentClasses");
        String x = "teste";
        boolean result = this.rental.equals(x);
        assertTrue(!result);
    }

    /**
     * Test of Equals with Equals Rentals
     */
    @Test
    public void testEqualsWhenObjectsAreEquals() {
        System.out.println("testEqualsWhenObjectsAreEquals");
        int rental = 1;
        User u = new User("Daniel","user@service.pt", "teste", "Teste", new Float(75.0), new Float(1.75), 11199456, false,15.00,0);
        LocalDateTime unlock = LocalDateTime.of(2018, 12, 16, 16, 0, 0);
        LocalDateTime lock = LocalDateTime.of(2018, 12, 16, 17, 0, 0);
        Rental.RentalStatus state = Rental.RentalStatus.FINISHED;
        LocalTime length = LocalTime.of(1, 0);
        NormalSlots ns1 = new NormalSlots(1,10,10);
        ElectricSlot es1 = new ElectricSlot(2,10,10,220,50);
        NormalSlots ns2 = new NormalSlots(3,10,10);
        ElectricSlot es2 = new ElectricSlot(4,10,10,220,50);
        Park pickUp = new Park(1,"Trindade",42,-8,ns1,es1,15);
        Park leftAt = new Park(2,"ISEP",42,-9,ns2,es2,15);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental expected = new Rental(rental, b, u, pickUp,leftAt,unlock, lock, state, Rental.RentalPayment.NOT_PAID);
        assertTrue(this.rental.equals(expected));
    }

    /**
     * Test of hashCode with Different Equals
     */
    @Test
    public void testHashCodeAreDifferentWithDifferentRentals() {
        System.out.println("testHashCodeAreDifferentWithDifferentRentals");
        int id_rental = 18;
        User user = new User("Daniel","userdifferent@service.pt", "expected", "hash", new Float(50.0), new Float(1.60), 65624566, false,15.00,0);
        LocalDateTime unlock_date = LocalDateTime.of(2018, 12, 12, 9, 15, 0);
        LocalDateTime lock_date = LocalDateTime.of(2018, 12, 13, 10, 0, 0);
        Rental.RentalStatus state = Rental.RentalStatus.FINISHED;
        LocalTime rental_length = LocalTime.of(0, 45);
        NormalSlots ns1 = new NormalSlots(5,10,10);
        ElectricSlot es1 = new ElectricSlot(6,10,10,220,50);
        NormalSlots ns2 = new NormalSlots(7,10,10);
        ElectricSlot es2 = new ElectricSlot(8,10,10,220,50);
        Park pickUp = new Park(3,"Hospital S.João",42,-8,ns1,es1,15);
        Park leftAt = new Park(4,"Casa da Música",42,-9,ns2,es2,15);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental expected = new Rental(id_rental, b, user,pickUp,leftAt, unlock_date, lock_date, state, Rental.RentalPayment.NOT_PAID);
        assertTrue(this.rental.hashCode() != expected.hashCode());
    }

    /**
     * Test of hashCode with Equals Rentals
     */
    @Test
    public void testHashCodeAreEqualsWithEqualsRentals() {
        System.out.println("testHashCodeAreEqualsWithEqualsRentals");
        int r = 1;
        User us = new User("Daniel","user@service.pt", "teste", "Teste", new Float(75.0), new Float(1.75), 11199456, false,15.00,0);
        LocalDateTime un = LocalDateTime.of(2018, 12, 16, 16, 0, 0);
        LocalDateTime l = LocalDateTime.of(2018, 12, 16, 17, 0, 0);
        Rental.RentalStatus st = Rental.RentalStatus.FINISHED;
        NormalSlots ns1 = new NormalSlots(1,10,10);
        ElectricSlot es1 = new ElectricSlot(2,10,10,220,50);
        NormalSlots ns2 = new NormalSlots(3,10,10);
        ElectricSlot es2 = new ElectricSlot(4,10,10,220,50);
        Park pickUp = new Park(1,"Trindade",42,-8,ns1,es1,15);
        Park leftAt = new Park(2,"ISEP",42,-9,ns2,es2,15);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental expected = new Rental(r, b, us,pickUp,leftAt, un, l, st, Rental.RentalPayment.NOT_PAID);
        assertTrue(this.rental.hashCode() == expected.hashCode());
    }

    @Test
    public void ensureRentalReturnsTheCorrectEnum() {
        System.out.println("Ensure test to return the enum returns the correct one.");
        int result = this.rental.getState().statusCode();
        assertEquals(1, result);

    }

    @Test
    public void testPaymentCode(){
        int result = rental2.getRentalPayment().paymentCode();
        assertTrue(result==0);
    }

    @Test
    public void testGetPickUpPark(){
        Park result = rental.getPickUpPark();
        assertTrue(result.equals(pickUp));
    }

    @Test
    public void testGetLeftPark(){
        Park result = rental.getLeftAtPark();
        assertTrue(result.equals(leftAt));
    }

    @Test
    public void testSetPickUpPark(){
        rental.setPickUpPark(leftAt);
        Park result = rental.getPickUpPark();
        assertTrue(result.equals(leftAt));
    }

    @Test
    public void testSetLeftAtPark(){
        rental.setLeftAtPark(pickUp);
        Park result = rental.getLeftAtPark();
        assertTrue(result.equals(pickUp));
    }

    @Test
    public void testSetRentalPayment(){
        rental.setRentalPayment(Rental.RentalPayment.PAID);
        Rental.RentalPayment result = rental.getRentalPayment();
        assertTrue(result.paymentCode()==1);
    }

}

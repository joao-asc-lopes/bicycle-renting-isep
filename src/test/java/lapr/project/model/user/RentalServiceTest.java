/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model.user;

import lapr.project.data.BicycleDao;
import lapr.project.data.RentalDao;
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.RoadBicycle;
import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RentalServiceTest {

    @Mock
    private RentalDao rentalDao;

    @Mock
    private BicycleDao bicycleDao;

    @InjectMocks
    private RentalService rentalService;

    private RentalService actualRentalService;

    private Rental r;

    private Park prk;
    private User user;
    private User user2;
    private LocalDateTime unlock;
    private LocalDateTime lock;
    private LocalDateTime lock2;
    private Rental.RentalStatus stat;
    private LocalTime length;
    private LocalTime length2;
    private NormalSlots ns1;
    private ElectricSlot es1;
    private NormalSlots ns2;
    private ElectricSlot es2;
    private Park pickUp;
    private Park leftAt;
    private Bicycle bicycle;
    public RentalServiceTest() {
    }

    @BeforeEach
    public void setUp() {
        prk = new Park(1,"Parque Teste",20.0,40.0, new NormalSlots(1,1,1), new ElectricSlot(1,1,1,220,50), 12);
        user = new User("Daniel","user@gmail.pt","user123", "User", 55,178, 15829478,true,15.00,0);
        user2 = new User("Daniel","user2@gmail.pt","user123", "User2", 75,185, 15829888,false,15.00,10);

        rentalDao = Mockito.mock(RentalDao.class);
        bicycleDao = Mockito.mock(BicycleDao.class);
        rentalService = new RentalService();
        actualRentalService = new RentalService();
        initMocks(this);
        unlock = LocalDateTime.of(2018, 12, 16, 16, 0, 0);
        lock = LocalDateTime.of(2018, 12, 16, 17, 0, 0);
        lock2 = LocalDateTime.of(2018, 12, 16, 18, 0, 0);
        stat = Rental.RentalStatus.FINISHED;
        length = LocalTime.of(1, 0);
        length2 = LocalTime.of(2, 0);
        ns1 = new NormalSlots(1,10,10);
        es1 = new ElectricSlot(2,10,10,220,50);
        ns2 = new NormalSlots(3,10,10);
        es2 = new ElectricSlot(4,10,10,220,50);
        pickUp = new Park(1,"Trindade",42,-8,ns1,es1,15);
        leftAt = new Park(2,"ISEP",42,-9,ns2,es2,15);
        bicycle = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        r = new Rental(1,bicycle,this.user,pickUp,leftAt, unlock,lock, Rental.RentalStatus.FINISHED);
    }


    @Test
    public void ensureGetTimeBicycleUnlockedReturnCorrectTime(){
        long expected = 1345;
        when(rentalDao.getTimeBicycleUnlocked("Test")).thenReturn(expected);
        long result = rentalService.getTimeBicycleUnlocked("Test");
        assertTrue(expected==result);
    }

    @Test
    public void ensureGetBicycleUserActiveRentalReturnCorrectUser(){
        Bicycle expected = new RoadBicycle("3", Bicycle.BicycleStatus.IN_USE,150f,1,5.0);
        when(rentalDao.getBicycleUserActiveRental("test")).thenReturn(expected);
        Bicycle result = rentalService.getBicycleUserActiveRental("test");
        assertTrue(expected.equals(result));
    }

    /**
     * Test that ensures that getRental returns null when rental id is wrong
     */
    @Test
    public void ensureGetRentalWhenIdIsWrongThrowsException() {
        when(rentalDao.getRental(1)).thenThrow(new InvalidDataException("No Rental with the id: " + 1));
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            Rental result = this.rentalService.getRental(1);
        });

        assertTrue(e.getMessage().equals("No Rental with the id: " + 1));
    }

    /**
     * Test that ensures that getRental returns  a rental when the id is correct
     */
    @Test
    public void ensureGetRentalWhenIdIsRightReturnObject() {
        when(rentalDao.getRental(1)).thenReturn(r);
        Rental result = rentalService.getRental(1);
        assertTrue(result.equals(r));
    }

    /**
     * Test that ensures that addRental return false when a rental with the same
     * id is trying to be saved
     */
    @Test
    public void ensureAddRentalWithSameIdRentalReturnsFalse() {
        boolean result;
        when(bicycleDao.getBicycle("IDteste")).thenReturn(bicycle);
        when(rentalDao.getRental(r.getId())).thenReturn(r);
        result = rentalService.addRental(r.getId(), r.getBike().getId(), r.getUser(),r.getPickUpPark(),r.getLeftAtPark(), r.getUnlockDate(), r.getLockDate()
                , r.getState());
        assertTrue(!result);
    }

    /**
     * Test that ensures that addRental return true when a rental with different id
     * is trying to be saved
     */
    @Test
    public void ensureAddRentalWithDifferentIdReturnTrue() {
        boolean result;
        when(bicycleDao.getBicycle("IDteste")).thenReturn(bicycle);
        when(rentalDao.addRental(r)).thenReturn(true);
        result = rentalService.addRental(r.getId(), r.getBike().getId(), r.getUser(), r.getPickUpPark(),r.getLeftAtPark(),r.getUnlockDate(), r.getLockDate()
                , r.getState());
        assertTrue(result);
    }

    /**
     * Test that ensures that removeRental returns false when a invalid id is passed
     */
    @Test
    public void ensureRemoveRentalWithInvalidIdReturnFalse() {
        when(rentalDao.removeRental(1)).thenReturn(false);
        boolean result = rentalService.removeRental(1);
        assertTrue(!result);
    }

    /**
     * Test that ensures that removeRentla returns true when a valid id is passed
     */
    @Test
    public void ensureRemoveRentalWithValidIdReturnTrue() {
        when(rentalDao.removeRental(1)).thenReturn(true);
        boolean result = rentalService.removeRental(1);
        assertTrue(result);
    }

    /**
     * Test that ensures that getActiveRental returns null when a invalid id_bike
     * is passed
     */
    @Test
    public void ensureGetActiveRentalWithInvalidIdReturnNull() {
        when(rentalDao.getActiveRental("2")).thenThrow(new IllegalArgumentException("No Rental with id: " + 2));
        Rental result = rentalService.getActiveRental("2");
        assertTrue(result == null);
    }

    /**
     * Test that ensures that getActiveRental returns a rental when a valid id_bike
     * is passed
     */
    @Test
    public void ensureGetActiveRentalWithValidIdReturnRental() {
        Rental another = new Rental(r.getId() + 1, r.getBike(), r.getUser(), r.getPickUpPark(),r.getUnlockDate());

        when(rentalDao.getActiveRental("2")).thenReturn(another);
        Rental result = rentalService.getActiveRental("2");
        assertTrue(result != null);
    }

    @Test
    public void ensureGetStartingParkReturnsCorrectParkIfBicycleExistsAndHasBeenRented(){
        Park expected = prk;
        when(this.rentalDao.getStartingPark("Teste")).thenReturn(prk);
        Park result = this.rentalService.getStartingPark("Teste");
        assertEquals(expected, result);
    }

    @Test
    public void ensureGetStartingParkThrowsExceptionIfThereAreNoRentalsForThisIdOrIdDoesntExist(){
        when(this.rentalDao.getStartingPark("Teste")).thenThrow(new IllegalArgumentException("No Rental for the bicycle with id: " + "Teste"));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
            this.rentalService.getStartingPark("Teste");
        });
        assertEquals("No Rental for the bicycle with id: " + "Teste", e1.getMessage());
    }


    @Test
    public void ensureGetBicycleUserReturnsCorrectUserIfBicycleExistsAndHasBeenRented(){
        User expected = this.user;
        when(this.rentalDao.getBicycleUser("Test")).thenReturn(this.user);
        User result = this.rentalService.getBicycleUser("Test");
        assertEquals(expected, result);
    }

    @Test
    public void ensureGetBicycleUserThrowsExceptionIfIdHasNeverBeenRentedOrIdDoesntExist(){
        when(this.rentalDao.getBicycleUser("Test")).thenThrow(new IllegalArgumentException("No Rental for the bicycle with id: " + "Test"));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
            this.rentalService.getBicycleUser("Test");
        });
        assertEquals("No Rental for the bicycle with id: " + "Test", e1.getMessage());
    }

    @Test
    public void testGetAllUnpaidRentalsWhenProcessFails() {
        when(this.rentalDao.getUnpaidRentalsTotal("teste@teste.com")).thenReturn(null);
        List<Rental> array = this.rentalService.getUnpaidRentalsTotal("teste@teste.com");
        assertTrue(array == null);
    }

    @Test
    public void testGetAllUnpaidRentalsWhenUserDontHaveUnpaidRentals() {
        when(this.rentalDao.getUnpaidRentalsTotal("teste@teste.com")).thenReturn(new ArrayList<>());
        List<Rental> array = this.rentalService.getUnpaidRentalsTotal("teste@teste.com");
        assertTrue(array.isEmpty());
    }

    @Test
    public void testGetAllUnpaidRentalsWhenUserHaveUnpaidRentals() {
        Rental ren = new Rental(1, r.getBike(), this.user, pickUp, leftAt, unlock, lock, Rental.RentalStatus.FINISHED);
        List<Rental> array = new ArrayList<>();
        array.add(ren);
        when(this.rentalDao.getUnpaidRentalsTotal("teste@teste.com")).thenReturn(array);
        List<Rental> result = this.rentalService.getUnpaidRentalsTotal("teste@teste.com");
        assertTrue(result.equals(array));
    }
        /**
         * Test calculateValue when user don't have to pay for the time and has not enough points to spend
         */
        @Test
        public void testCalculateValueWhenUserDontHaveToPayForTheTimeAndHasNotEnoughPoints () {
            Rental ren = new Rental(1, r.getBike(), this.user, pickUp, leftAt, unlock, lock, Rental.RentalStatus.FINISHED);
            List<Rental> array = new ArrayList<>();
            array.add(ren);
            double value = this.actualRentalService.calculateValue(array, this.user);
            assertTrue(value == 0.0);
        }

        /**
         * Test calculateValue when user have to pay for the time and has not enough points to spend
         */
        @Test
        public void testCalculateValueWhenUserHaveToPayForTheTimeAndHasNotEnoughPoints () {
            Rental ren = new Rental(1, r.getBike(), this.user, pickUp, leftAt, unlock, lock2, Rental.RentalStatus.FINISHED);
            List<Rental> array = new ArrayList<>();
            array.add(ren);
            double value = this.actualRentalService.calculateValue(array, this.user);
            assertTrue(value == 3.0);
        }

        /**
         * Test calculateValue when user don't have to pay for the time and has enough points to spend
         */
        @Test
        public void testCalculateValueWhenUserDontHaveToPayForTheTimeAndHasEnoughPoints () {
            Rental ren = new Rental(1, r.getBike(), this.user, pickUp, leftAt, unlock, lock, Rental.RentalStatus.FINISHED);
            List<Rental> array = new ArrayList<>();
            array.add(ren);
            double value = this.actualRentalService.calculateValue(array, this.user2);
            assertTrue(value == 0.0);
        }

        /**
         * Test calculateValue when user have to pay for the time and has enough points to spend
         */
        @Test
        public void testCalculateValueWhenUserHaveToPayForTheTimeAndHasEnoughPoints () {
            Rental ren = new Rental(1, r.getBike(), this.user, pickUp, leftAt, unlock, lock2, Rental.RentalStatus.FINISHED);
            List<Rental> array = new ArrayList<>();
            array.add(ren);
            double value = this.actualRentalService.calculateValue(array, this.user2);
            assertTrue(value == 2.0);
        }

    @Test
    public void ensureGetAllRentalsReturnsAllRentals(){
        List<Rental> allRentals = new ArrayList<>();
        User u = new User("Daniel","teste@teste.com","teste","teste","Teste",2f,2f,(long)1111222233334444.0,false,15.0);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,u, pickUp,leftAt,unlock,lock, Rental.RentalStatus.FINISHED,Rental.RentalPayment.NOT_PAID);
        allRentals.add(ren);
        when(this.rentalDao.getAllRentals()).thenReturn(allRentals);
        List<Rental> result = this.rentalService.getAllRentals();
        assertEquals(allRentals, result);
    }
    @Test
    public void ensureGetAllRentalsMonthReturnsAllRentals(){
        List<Rental> allRentalsJune = new ArrayList<>();
        User u = new User("Daniel","teste@teste.com","teste","teste","Teste",2f,2f,(long)1111222233334444.0,false,15.0);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,u, pickUp,leftAt,unlock,lock, Rental.RentalStatus.FINISHED,Rental.RentalPayment.NOT_PAID);
        allRentalsJune.add(ren);
        when(this.rentalDao.getUnpaidRentalsOfMonth("Daniel", 6)).thenReturn(allRentalsJune);
        List<Rental> result = this.rentalService.getUnpaidRentalsOfMonth("Daniel", 6);
        assertEquals(allRentalsJune, result);
    }

}

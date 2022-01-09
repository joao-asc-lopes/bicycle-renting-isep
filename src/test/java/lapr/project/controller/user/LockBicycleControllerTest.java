/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller.user;

import lapr.project.model.bicycle.*;
import lapr.project.model.bicycle.Bicycle.BicycleStatus;
import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import lapr.project.model.user.Rental;
import lapr.project.model.user.RentalService;
import lapr.project.model.user.User;
import lapr.project.model.user.UserFacade;
import lapr.project.utils.EmailHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LockBicycleControllerTest {

    @Mock
    private RentalService rentalService;

    @Mock
    private EmailHandler ec;

    @Mock
    private BicycleNetwork bikeN;

    @Mock
    private UserFacade userFacade;

    @Mock
    private LocationFacade locationFacade;

    @Mock
    private BicycleFacade bicycleFacade;
    @InjectMocks
    private LockBicycleController lbc;

    private Rental rent;
    private Park prk;
    private Park prk2;
    private User us;

    public LockBicycleControllerTest() {
    }

    @BeforeEach
    public void setUp() {
        bikeN = Mockito.mock(BicycleNetwork.class);

        prk2 = new Park(2, "Parque Teste2", 18.0, 45.0, new NormalSlots(3, 1, 1), new ElectricSlot(4, 1, 1,220,50), 120);
        prk = new Park(1, "Parque Teste", 20.0, 40.0, new NormalSlots(1, 1, 1), new ElectricSlot(2, 1, 1,220,50), 12);
        us = new User("Daniel","user@gmail.pt", "user123", "User", 55, 178, 15829478, true, 15.00,0);
        rentalService = Mockito.mock(RentalService.class);
        userFacade = Mockito.mock(UserFacade.class);
        ec = Mockito.mock(EmailHandler.class);
        locationFacade = Mockito.mock(LocationFacade.class);
        bicycleFacade = Mockito.mock(BicycleFacade.class);
        initMocks(this);
        User u = new User("Daniel","lapr3grupo40@gmail.com", "Lock", "Lock", new Float(80.0), new Float(1.80), 17199456, true, 15.00,0);
        NormalSlots ns1 = new NormalSlots(1,10,10);
        ElectricSlot es1 = new ElectricSlot(2,10,10,220,50);
        Park pickUp = new Park(1,"Trindade",42,-8,ns1,es1,15);
        Bicycle b = new RoadBicycle("IDteste",BicycleStatus.IN_USE,150f,0,5.0);
        rent = new Rental(1, b, u, pickUp,LocalDateTime.of(2018, 12, 17, 16, 0));
    }

    /**
     * Test of sendConfirmationEmail
     */
    @Test
    public void testConfirmationEmail() {

        NormalSlots ns = new NormalSlots(1, 1, 1);
        ElectricSlot es = new ElectricSlot(1, 2, 2,220,50);
        Park p = new Park(1, "Trindade", new Double(42), new Double(-8), ns, es, 150);
        when(rentalService.getActiveRental("2")).thenReturn(rent);
        when(ec.sendEmail(rent.getUser().getEmail(), p.getName())).thenReturn(true);
        boolean result = lbc.sendLockConfirmationEmail("2", p);
        assertTrue(result);


    }

    /**
     * Test of updateParkSlot when BicycleId is wrong
     */
    @Test
    public void testUpdateParkSlotWhenBicycleIdIsWrong() {
        ArrayList<Bicycle> array = new ArrayList<>();
        RoadBicycle road = new RoadBicycle("IDteste", BicycleStatus.AVAILABLE, 200.0f,10,5.0);
        array.add(road);
        Park p = new Park(1, "Trindade", 42, 8, new NormalSlots(1, 2, 2), new ElectricSlot(2, 2, 2,220,50), 150);
        when(bicycleFacade.getAllBicycles()).thenReturn(array);
        when(locationFacade.getParkById(1)).thenReturn(p);
        boolean result = lbc.updateParkSlot("IDteste", 1);
        assertTrue(!result);
    }

    /**
     * Test of updateParkSlot when ParkId is wrong
     */
    @Test
    public void testUpdateParkSlotWhenParkIdIsWrong() {
        ArrayList<Bicycle> array = new ArrayList<>();
        RoadBicycle road = new RoadBicycle("IDteste", BicycleStatus.AVAILABLE, 200.0f,10,5.0);
        array.add(road);

        Park p = new Park(1, "Trindade", 42, 8, new NormalSlots(1, 2, 2), new ElectricSlot(2, 2, 2,220,50), 150);
        when(bicycleFacade.getAllBicycles()).thenReturn(array);
        when(locationFacade.getParkById(1)).thenReturn(null);
        boolean result = lbc.updateParkSlot("IDteste", 2);
        assertTrue(!result);
    }

    /**
     * Test of updateParkSlot when Park doesn´t have space in normal slots
     */
    @Test
    public void testUpdateParkSlotWhenParkDontHaveSpaceNormalSlots() {
        ArrayList<Bicycle> array = new ArrayList<>();
        RoadBicycle road = new RoadBicycle("IDteste", BicycleStatus.AVAILABLE, 200.0f,10,5.0);
        array.add(road);
        Park p = new Park(1, "Trindade", 42, 8, new NormalSlots(1, 2, 0), new ElectricSlot(2, 2, 2,220,50), 150);
        when(bicycleFacade.getAllBicycles()).thenReturn(array);
        when(locationFacade.getParkById(1)).thenReturn(p);
        boolean result = lbc.updateParkSlot("IDteste", 1);
        assertTrue(!result);
    }

    /**
     * Test of updateParkSlot when Park doesn´t have space in electrical slots
     */
    @Test
    public void testUpdateParkSlotWhenParkDontHaveSpaceElectricalSlots() {
        ArrayList<Bicycle> array = new ArrayList<>();
        ElectricBicycle electrical = new ElectricBicycle("IDteste", BicycleStatus.AVAILABLE, new Battery(1, 200, 100, 10f), 200.0f,10,5.0);
        array.add(electrical);
        Park p = new Park(1, "Trindade", 42, 8, new NormalSlots(1, 2, 2), new ElectricSlot(2, 2, 0,220,50), 150);
        when(bicycleFacade.getAllBicycles()).thenReturn(array);
        when(locationFacade.getParkById(1)).thenReturn(p);
        boolean result = lbc.updateParkSlot("IDteste", 1);
        assertTrue(!result);
    }

    /**
     * Test of updateParkSlot with correctData to park a bicycle in Normal Slots
     */
    @Test
    public void testUpdateParkSlotWithCorrectDataNormalSlots() {
        ArrayList<Bicycle> array = new ArrayList<>();
        RoadBicycle road = new RoadBicycle("IDteste", BicycleStatus.AVAILABLE, 200.0f,10,5.0);
        array.add(road);

        Park p = new Park(1, "Trindade", 42, 8, new NormalSlots(1, 2, 2), new ElectricSlot(2, 2, 2,220,50), 150);

        when(bicycleFacade.getAllBicycles()).thenReturn(array);
        when(locationFacade.getParkById(1)).thenReturn(p);
        when(locationFacade.bikeIntoPark("IDteste", 1)).thenReturn(true);
        boolean result = lbc.updateParkSlot("IDteste", 1);
        assertTrue(result);
    }

    /**
     * Test of updateParkSlot with correctData to park a bicycle in Electrical Slots
     */
    @Test
    public void testUpdateParkSlotWithCorrectDataElectricalSlots() {
        ArrayList<Bicycle> array = new ArrayList<>();
        Battery bat = new Battery(1, 200, 100, 10f);
        ElectricBicycle ele = new ElectricBicycle("IDteste", BicycleStatus.AVAILABLE, bat, 200.0f,10,5.0);
        array.add(ele);

        Park p = new Park(1, "Trindade", 42, 8, new NormalSlots(1, 2, 2), new ElectricSlot(2, 2, 2,220,50), 150);

        when(bicycleFacade.getAllBicycles()).thenReturn(array);
        when(locationFacade.getParkById(1)).thenReturn(p);
        when(locationFacade.bikeIntoPark("IDteste", 1)).thenReturn(true);
        boolean result = lbc.updateParkSlot("IDteste", 1);
        assertTrue(result);
    }

    /**
     * Test of getStartingPark when Bicycle exists and has been rented
     */
    @Test
    public void ensureGetStartingParkReturnsCorrectParkIfBicycleExistsAndHasBeenRented() {
        Park expected = prk;
        when(this.userFacade.getStartingPark("Test")).thenReturn(prk);
        Park result = this.lbc.getStartingPark("Test");
        assertEquals(expected, result);
    }

    /**
     * Test of getStartingPark when there are no rentals for that id
     */
    @Test
    public void ensureGetStartingParkThrowsExceptionIfThereAreNoRentalsForThisIdOrIdDoesntExist() {
        int id = 1;
        when(this.userFacade.getStartingPark("Test")).thenThrow(new IllegalArgumentException("No Rental for the bicycle with id: " + id));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
            this.lbc.getStartingPark("Test");
        });
        assertEquals("No Rental for the bicycle with id: " + id, e1.getMessage());
    }


    /**
     * Test of getBicycle when Bicycle exists and has been rented
     */
    @Test
    public void ensureGetBicycleUserReturnsCorrectUserIfBicycleExistsAndHasBeenRented() {
        User expected = this.us;
        when(this.userFacade.getBicycleUser("Test")).thenReturn(this.us);
        User result = this.us;
        assertEquals(expected, result);
    }

    /**
     * Test of getBicycle when it has never been rented or ID doesn't exist
     */
    @Test
    public void ensureGetBicycleUserThrowsExceptionIfIdHasNeverBeenRentedOrIdDoesntExist() {
        when(this.userFacade.getBicycleUser("Test")).thenThrow(new IllegalArgumentException("No Rental for the bicycle with id: " + "Test"));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
            this.lbc.getBicycleUser("Test");
        });
        assertEquals("No Rental for the bicycle with id: " + "Test", e1.getMessage());
    }

    /**
     * Test of awardUserPoints when points is bigger than 0 and user exists
     */
    @Test
    public void ensureAwardUserPointsReturnsTrueIfPointsBiggerThan0AndUserExists() {
        boolean expected = true;
        when(this.userFacade.awardUserPoints(this.us.getEmail(), 15)).thenReturn(true);
        boolean result = this.lbc.awardUserPoints(this.prk2, this.prk, this.us);
        assertEquals(expected, result);
    }

    /**
     * Test of awardUserPoints when points equals 0
     */
    @Test
    public void ensureAwardUserPointsReturnsFalseIfPointsEqualZero() {
        boolean expected = false;
        when(this.userFacade.awardUserPoints(this.us.getEmail(), 0)).thenReturn(false);
        boolean result = this.lbc.awardUserPoints(this.prk, this.prk2, this.us);
        assertEquals(expected, result);
    }

    /**
     * Test of awardUserPoints when user doesn't exist
     */
    @Test
    public void ensureAwardUserPointsThrowsExceptionIfUserDoesntExist() {
        when(this.userFacade.awardUserPoints(this.us.getEmail(), 15)).thenThrow(new IllegalArgumentException("No User with email: " + this.us.getEmail()));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
            this.lbc.awardUserPoints(this.prk2, this.prk, this.us);
        });
        assertEquals("No User with email: " + this.us.getEmail(), e1.getMessage());
    }


}



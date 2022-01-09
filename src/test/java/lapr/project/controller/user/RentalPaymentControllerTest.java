package lapr.project.controller.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.RoadBicycle;
import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import lapr.project.model.user.Rental;
import lapr.project.model.user.User;
import lapr.project.model.user.UserFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class RentalPaymentControllerTest {
    @Mock
    private UserFacade userFacade;

    @InjectMocks
    private RentalPaymentController rpc;

    private User user;
    private User user2;
    private LocalDateTime unlock;
    private LocalDateTime lock;
    private LocalTime length;
    private NormalSlots ns1;
    private ElectricSlot es1;
    private NormalSlots ns2;
    private ElectricSlot es2;
    private Park pickUp;
    private Park leftAt;


    @BeforeEach
    public void setUp(){
        unlock = LocalDateTime.of(2019,1,1,13,0,0);
        lock = LocalDateTime.of(2019,1,1,15,0,0);
        length = LocalTime.of(2,0,0);
        ns1 = new NormalSlots(1,10,10);
        es1 = new ElectricSlot(2,10,10,220,50);
        ns2 = new NormalSlots(3,10,10);
        es2 = new ElectricSlot(4,10,10,220,50);
        pickUp = new Park(1,"Trindade",42,-8,ns1,es1,15);
        leftAt = new Park(2,"ISEP",42,-9,ns2,es2,15);
        user = new User("Leonardo","leonardo@outlook.com","bomDia123","Leonardo Estrela", 65, 177, 15974444, false, 15.00,30);
        user2 = new User("User","user2@gmail.pt","user123", "User2", 75,185, 15829888,false,15.00,10);

        userFacade = Mockito.mock(UserFacade.class);
        rpc = new RentalPaymentController();
        initMocks(this);
    }

    /**
     * Test generateReceipt when user don't exists
     */
    @Test
    public void testGenerateReceiptWhenUserNotExists() {
        when(userFacade.getUser("teste@teste.com")).thenThrow(new IllegalArgumentException());
        boolean result = this.rpc.generateReceipt("teste@teste.com");
        assertTrue(!result);
    }

    /**
     * Test generateReceipt when user don't have unpaid rentals
     */
    @Test
    public void testGenerateReceiptWhenUserDontHaveUnpaidRentals(){
        when(userFacade.getUser("teste@teste.com")).thenReturn(new User("Daniel","teste@teste.com","teste","teste","Teste",1f,2f,(long)1111222233334444.0,false,15.0));
        when(userFacade.getUnpaidRentalsTotal("teste@teste.com")).thenReturn(new ArrayList<>());
        boolean result = this.rpc.generateReceipt("teste@teste.com");
        assertTrue(!result);
    }

    /**
     * Test generateReceipt when insertReceiptRentalGivesError
     */
    @Test
    public void testGenerateReceiptWhenInsertReceiptRentalGivesError(){
        User u = new User("Daniel","teste@teste.com","teste","teste","Teste",2f,2f,(long)1111222233334444.0,false,15.0);
        when(userFacade.getUser("teste@teste.com")).thenReturn(u);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,u,pickUp,leftAt, unlock,lock, Rental.RentalStatus.FINISHED,Rental.RentalPayment.NOT_PAID);
        List<Rental> array = new ArrayList<>();
        when(userFacade.getUnpaidRentalsTotal("teste@teste.com")).thenReturn(array);
        when(userFacade.createReceipt(0,23)).thenReturn(1);
        when(userFacade.insertReceiptRental(1,1)).thenReturn(false);
        boolean result = this.rpc.generateReceipt("teste@teste.com");
        assertTrue(!result);
    }


    /**
     * Test generateReceipt with correct info
     */
    @Test
    public void testGenerateReceiptWithCorrectData(){
        User u = new User("Daniel","teste@teste.com","teste","teste","Teste",2f,2f,(long)1111222233334444.0,false,15.0);
        when(userFacade.getUser("teste@teste.com")).thenReturn(u);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,u, pickUp,leftAt,unlock,lock, Rental.RentalStatus.FINISHED,Rental.RentalPayment.NOT_PAID);
        List<Rental> array = new ArrayList<>();
        array.add(ren);
        when(userFacade.getUnpaidRentalsTotal("teste@teste.com")).thenReturn(array);
        when(userFacade.createReceipt(0,23)).thenReturn(1);
        when(userFacade.insertReceiptRental(1,1)).thenReturn(true);
        boolean result = this.rpc.generateReceipt("teste@teste.com");
        assertTrue(result);
    }

    /**
     * Test calculateValue when user don't have to pay for the time and has not enough points to spend
     */
    @Test
    public void testCalculateValueWhenUserDontHaveToPayForTheTimeAndHasNotEnoughPoints(){
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,this.user,pickUp,leftAt, unlock,lock, Rental.RentalStatus.FINISHED,Rental.RentalPayment.NOT_PAID);
        List<Rental> array = new ArrayList<>();
        array.add(ren);
        when(this.userFacade.calculateValue(array, this.user)).thenReturn(0.0);
        double value = this.rpc.calculateValue(array, this.user);
        assertTrue(value==0.0);
    }

    /**
     * Test calculateValue when user have to pay for the time and has not enough points to spend
     */
    @Test
    public void testCalculateValueWhenUserHaveToPayForTheTimeAndHasNotEnoughPoints(){
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,this.user, pickUp,leftAt,unlock,lock, Rental.RentalStatus.FINISHED,Rental.RentalPayment.NOT_PAID);
        List<Rental> array = new ArrayList<>();
        array.add(ren);
        when(this.userFacade.calculateValue(array, this.user)).thenReturn(3.0);
        double value = this.rpc.calculateValue(array, this.user);
        assertTrue(value==3.0);
    }

    /**
     * Test calculateValue when user don't have to pay for the time and has enough points to spend
     */
    @Test
    public void testCalculateValueWhenUserDontHaveToPayForTheTimeAndHasEnoughPoints(){
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,this.user2, pickUp,leftAt, unlock,lock, Rental.RentalStatus.FINISHED,Rental.RentalPayment.NOT_PAID);
        List<Rental> array = new ArrayList<>();
        array.add(ren);
        when(this.userFacade.calculateValue(array, this.user)).thenReturn(0.0);
        double value = this.rpc.calculateValue(array, this.user2);
        assertTrue(value==0.0);
    }

    /**
     * Test calculateValue when user have to pay for the time and has enough points to spend
     */
    @Test
    public void testCalculateValueWhenUserHaveToPayForTheTimeAndHasEnoughPoints(){
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,this.user, pickUp,leftAt,unlock,lock, Rental.RentalStatus.FINISHED);
        List<Rental> array = new ArrayList<>();
        array.add(ren);
        when(this.userFacade.calculateValue(array, this.user)).thenReturn(2.0);
        double value = this.rpc.calculateValue(array, this.user);
        assertTrue(value==2.0);
    }

    @Test
    public void testGetUserWhenUserDontExist() {
        when(userFacade.getUser("teste@teste.com")).thenThrow(new IllegalArgumentException());
        try {
            User u = rpc.getUser("teste@teste.com");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetUserWhenUserExist() {
        User expected = new User("Daniel","teste@teste.com", "teste", "teste", "Teste", 2f, 2f, (long) 1111222233334444.0, false,15.0);
        when(userFacade.getUser("teste@teste.com")).thenReturn(expected);
        try {
            User result = this.rpc.getUser("teste@teste.com");
            assertTrue(result.equals(expected));
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void ensureGetBicycleUserActiveRentalReturnCorrectUser(){
        Bicycle expected = new RoadBicycle("3", Bicycle.BicycleStatus.IN_USE,150f,1,5.0);
        when(this.userFacade.getUserActiveRentalBicycle("test")).thenReturn(expected);
        Bicycle result = this.rpc.getUserActiveRentalBicycle("test");
        assertTrue(expected.equals(result));
    }

    @Test
    public void ensureGetAllRentalsReturnsAllRentals(){
        List<Rental> allRentals = new ArrayList<>();
        User u = new User("Daniel","teste@teste.com","teste","teste","Teste",2f,2f,(long)1111222233334444.0,false,15.0);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,u, pickUp,leftAt,unlock,lock, Rental.RentalStatus.FINISHED,Rental.RentalPayment.NOT_PAID);
        allRentals.add(ren);
        when(this.userFacade.getAllRentals()).thenReturn(allRentals);
        List<Rental> result = this.rpc.getAllRentals();
        assertEquals(allRentals, result);
    }

    @Test
    public void returnUserUnpaidRentalsOfMonthReturnsTheCorrectValues(){
        List<Rental> allRentalsJune = new ArrayList<>();
        User u = new User("Daniel","teste@teste.com","teste","teste","Teste",2f,2f,(long)1111222233334444.0,false,15.0);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,u, pickUp,leftAt,unlock,lock, Rental.RentalStatus.FINISHED,Rental.RentalPayment.NOT_PAID);
        allRentalsJune.add(ren);
        when(this.userFacade.getUnpaidRentalsOfMonth("Daniel", 6)).thenReturn(allRentalsJune);
        List<Rental> result = this.rpc.returnUserUnpaidRentalsOfMonth("Daniel", 6);
        assertEquals(allRentalsJune, result);
    }


}
package lapr.project.model.user;

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

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserFacadeTest {

    @Mock
    private UserService us;

    @InjectMocks
    private UserFacade uf;

    private String email;
    @Mock
    private RentalService rs;
    private Park prk;
    private Park prk2;
    private User user;
    private User user2;
    private LocalDateTime unlock;
    private LocalDateTime lock;
    private LocalDateTime lock2;
    private Rental.RentalStatus stat;
    private LocalTime length;
    private NormalSlots ns1;
    private ElectricSlot es1;
    private NormalSlots ns2;
    private ElectricSlot es2;
    private Park pickUp;
    private Park leftAt;
    private User user3;

    @BeforeEach
    public void setUp() {
        prk2 = new Park(2, "Parque Teste2", 18.0, 45.0, new NormalSlots(3, 1, 1), new ElectricSlot(4, 1, 1,220,50), 120);
        prk = new Park(1, "Parque Teste", 20.0, 40.0, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1,220,50), 12);
        user = new User("Daniel","user@gmail.pt", "user123", "User", 55, 178, 15829478, true,15.00,0);
        user2 = new User("Daniel","user2@gmail.pt","user123", "User2", 75,185, 15829888,false,15.00,10);
        user3 = new User("daniel","dan@gmail.com","user123","User3",54,12,1231415,false,15,10);
        rs = Mockito.mock(RentalService.class);
        email = "user@service.pt";
        us = Mockito.mock(UserService.class);
        initMocks(this);
        unlock = LocalDateTime.of(2018, 12, 16, 16, 0, 0);
        lock = LocalDateTime.of(2018, 12, 16, 17, 0, 0);
        lock2 = LocalDateTime.of(2018, 12, 16, 18, 0, 0);
        stat = Rental.RentalStatus.FINISHED;
        length = LocalTime.of(1, 0);
        ns1 = new NormalSlots(1,10,10);
        es1 = new ElectricSlot(2,10,10,220,50);
        ns2 = new NormalSlots(3,10,10);
        es2 = new ElectricSlot(4,10,10,220,50);
        pickUp = new Park(1,"Trindade",42,-8,ns1,es1,15);
        leftAt = new Park(2,"ISEP",42,-9,ns2,es2,15);
    }

    @Test
    public void testRegisterUserWhenUserDoesNotExistsInDataBaseAndDataIsValid() throws SQLException {
        boolean expected = true;
        when(us.registerUser("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenReturn(true);
        boolean result = uf.registerUser("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        assertEquals(expected, result);
    }

    @Test
    public void testRegisterUserWhenUserHasEmptyPasswordThrowsException() throws SQLException {
        when(us.registerUser("Daniel",email, "", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenThrow(new InvalidDataException("The password is not valid."));
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            uf.registerUser("Daniel",email, "", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The password is not valid.", e.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasNullPasswordThrowsException() throws SQLException {
        when(us.registerUser("Daniel",email, null, "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenThrow(new InvalidDataException("The password is not valid."));
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            uf.registerUser("Daniel",email, null, "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The password is not valid.", e2.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasInvalidEmailThrowsException() throws SQLException {
        when(us.registerUser("Daniel","user@service", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenThrow(new InvalidDataException("The email syntax is not valid."));
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            uf.registerUser("Daniel","user@service", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The email syntax is not valid.", e.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasInvalidEmail2ThrowsException() throws SQLException {
        when(us.registerUser("Daniel","service.pt", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenThrow(new InvalidDataException("The email syntax is not valid."));
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            uf.registerUser("Daniel","service.pt", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The email syntax is not valid.", e2.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasEmptyStringEmailThrowsException() throws SQLException {
        when(us.registerUser("Daniel","", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenThrow(new InvalidDataException("The email syntax is not valid."));
        InvalidDataException e3 = assertThrows(InvalidDataException.class, () -> {
            uf.registerUser("Daniel","", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The email syntax is not valid.", e3.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasNullEmailThrowsException() throws SQLException {
        when(us.registerUser("Daniel",null, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenThrow(new InvalidDataException("The email syntax is not valid."));
        InvalidDataException e4 = assertThrows(InvalidDataException.class, () -> {
            uf.registerUser("Daniel",null, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The email syntax is not valid.", e4.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserEmailIsAlreadyRegisteredReturnsFalse() throws SQLException {
        when(us.registerUser("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenReturn(false);
        boolean result = uf.registerUser("Daniel",email, "somepassword", "John", (float) 65.0, (float) 1.82, (long) 1111222233335555.0,15.00);
        assertFalse(result);
    }


    @Test
    public void testRentBicycleThrowsExceptionIfUserAlreadyHasARentedBike() {
        User loggedUser = new User("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, true,15.00,0);
        NormalSlots ns = new NormalSlots(1,10,10);
        ElectricSlot es = new ElectricSlot(2,10,10,220,50);
        Park park = new Park(1,"Trindade",42,-8,ns,es,15);
        Mockito.doThrow(new InvalidDataException("User already has a rented bicycle.")).when(us).rentBicycle("IDteste",park, loggedUser);
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            uf.createRental("IDteste",park, loggedUser);
        });
        assertEquals("User already has a rented bicycle.", e.getMessage());
    }

    @Test
    public void testRentBicycleUpdatesLoggedUser() {
        User loggedUser = new User("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        NormalSlots ns = new NormalSlots(1,10,10);
        ElectricSlot es = new ElectricSlot(2,10,10,220,50);
        Park park = new Park(1,"Trindade",42,-8,ns,es,15);
        doNothing().when(us).rentBicycle("IDteste",park, loggedUser);
        loggedUser.activateRental();
        uf.createRental("IDteste",park, loggedUser);
        boolean result = loggedUser.hasActiveRental();
        assertTrue(result);
    }

    @Test
    public void ensureGetStartingParkReturnsCorrectParkIfBicycleExistsAndHasBeenRented() {
        Park expected = prk;
        when(this.rs.getStartingPark("Test")).thenReturn(prk);
        Park result = this.uf.getStartingPark("Test");
        assertEquals(expected, result);
    }

    @Test
    public void ensureGetStartingParkThrowsExceptionIfThereAreNoRentalsForThisIdOrIdDoesntExist() {
        when(this.rs.getStartingPark("Teste")).thenThrow(new IllegalArgumentException("No Rental for the bicycle with id: " + "Teste"));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
            this.uf.getStartingPark("Teste");
        });
        assertEquals("No Rental for the bicycle with id: " + "Teste", e1.getMessage());
    }


    @Test
    public void ensureGetBicycleUserReturnsCorrectUserIfBicycleExistsAndHasBeenRented() {
        User expected = this.user;
        when(this.rs.getBicycleUser("Test")).thenReturn(this.user);
        User result = this.uf.getBicycleUser("Test");
        assertEquals(expected, result);
    }

    @Test
    public void ensureGetBicycleUserThrowsExceptionIfIdHasNeverBeenRentedOrIdDoesntExist() {
        when(this.rs.getBicycleUser("Teste")).thenThrow(new IllegalArgumentException("No Rental for the bicycle with id: " + "Teste"));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
            this.uf.getBicycleUser("Teste");
        });
        assertEquals("No Rental for the bicycle with id: " + "Teste", e1.getMessage());

    }

    @Test
    public void ensureAwardUserPointsReturnsTrueIfPointsBiggerThan0AndUserExists() {
        boolean expected = true;
        when(this.us.awardUserPoints(this.user.getEmail(), 15)).thenReturn(true);
        boolean result = this.uf.awardUserPoints(this.user.getEmail(), 15);
        assertEquals(expected, result);
    }

    @Test
    public void ensureAwardUserPointsReturnsFalseIfPointsEqualZero() {
        boolean expected = false;
        when(this.us.awardUserPoints(this.user.getEmail(), 0)).thenReturn(false);
        boolean result = this.uf.awardUserPoints(this.user.getEmail(), 0);
        assertEquals(expected, result);
    }

    @Test
    public void ensureAwardUserPointsThrowsExceptionIfUserDoesntExist() {
        when(this.us.awardUserPoints(this.user.getEmail(), 15)).thenThrow(new IllegalArgumentException("No User with email: " + this.user.getEmail()));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
            this.uf.awardUserPoints(this.user.getEmail(), 15);
        });
        assertEquals("No User with email: " + this.user.getEmail(), e1.getMessage());
    }


    @Test
    public void testGetUserWhenUserDontExist() {
        when(us.getUser("teste@teste.com")).thenThrow(new IllegalArgumentException());
        try {
            User u = uf.getUser("teste@teste.com");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }


    @Test
    public void ensureGetBicycleUserActiveRentalReturnCorrectUser(){
        Bicycle expected = new RoadBicycle("3", Bicycle.BicycleStatus.IN_USE,150f,1,5.0);
        when(this.rs.getBicycleUserActiveRental("test")).thenReturn(expected);
        Bicycle result = this.uf.getUserActiveRentalBicycle("test");
        assertTrue(expected.equals(result));
    }

    @Test
    public void testGetUserWhenUserExist() {
        User expected = new User("Daniel","teste@teste.com", "teste", "teste", "Teste", 2f, 2f, (long) 1111222233334444.0, false,15.0);
        when(us.getUser("teste@teste.com")).thenReturn(expected);
        try {
            User result = uf.getUser("teste@teste.com");
            assertTrue(result.equals(expected));
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testCreateReceiptWhenProcessFails() {
        when(us.createReceipt(16, 23)).thenReturn(-1);
        int id = uf.createReceipt(16, 23);
        assertTrue(id == -1);
    }

    @Test
    public void testCreateReceiptWhenProcessWorks() {
        when(us.createReceipt(17, 23)).thenReturn(1);
        int id = uf.createReceipt(17, 23);
        assertTrue(id == 1);
    }

    @Test
    public void testGetAllUnpaidRentalsWhenProcessFails() {
        when(rs.getUnpaidRentalsTotal("teste@teste.com")).thenReturn(null);
        List<Rental> array = uf.getUnpaidRentalsTotal("teste@teste.com");
        assertTrue(array == null);
    }

    @Test
    public void testGetAllUnpaidRentalsWhenUserDontHaveUnpaidRentals() {
        when(rs.getUnpaidRentalsTotal("teste@teste.com")).thenReturn(new ArrayList<>());
        List<Rental> array = uf.getUnpaidRentalsTotal("teste@teste.com");
        assertTrue(array.isEmpty());
    }

    @Test
    public void testGetAllUnpaidRentalsWhenUserHaveUnpaidRentals() {
        User uexpected = new User("Daniel","teste@teste.com", "teste", "teste", "Teste", 2f, 2f, (long) 1111222233334444.0, false,15.0);
        LocalDateTime unlock = LocalDateTime.of(2019, 1, 1, 13, 0, 0);
        LocalDateTime lock = LocalDateTime.of(2019, 1, 1, 14, 0, 0);
        LocalTime length = LocalTime.of(1, 0, 0);
        NormalSlots ns1 = new NormalSlots(1,10,10);
        ElectricSlot es1 = new ElectricSlot(2,10,10,220,50);
        NormalSlots ns2 = new NormalSlots(3,10,10);
        ElectricSlot es2 = new ElectricSlot(4,10,10,220,50);
        Park pickUp = new Park(1,"Trindade",42,-8,ns1,es1,15);
        Park leftAt = new Park(2,"ISEP",42,-9,ns2,es2,15);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1, b, uexpected,pickUp,leftAt, unlock, lock, Rental.RentalStatus.FINISHED);
        List<Rental> array = new ArrayList<>();
        array.add(ren);
        when(rs.getUnpaidRentalsTotal("teste@teste.com")).thenReturn(array);
        List<Rental> result = uf.getUnpaidRentalsTotal("teste@teste.com");
        assertTrue(result.equals(array));
    }

    @Test
    public void testInsertReceiptRentalWhenProcessFails() {
        when(us.insertReceiptRental(1, 1)).thenReturn(false);
        boolean result = uf.insertReceiptRental(1, 1);
        assertTrue(!result);
    }

    @Test
    public void testInssertReceiptRentalWhenProcessWorks() {
        when(us.insertReceiptRental(1, 1)).thenReturn(true);
        boolean result = uf.insertReceiptRental(1, 1);
        assertTrue(result);
    }

    /**
     * Test calculateValue when user don't have to pay for the time and has not enough points to spend
     */
    @Test
    public void testCalculateValueWhenUserDontHaveToPayForTheTimeAndHasNotEnoughPoints(){
        LocalDateTime unlock = LocalDateTime.of(2019,1,1,13,0,0);
        LocalDateTime lock = LocalDateTime.of(2019,1,1,14,0,0);
        LocalTime length = LocalTime.of(1,0,0);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,this.user,pickUp,leftAt, unlock,lock, Rental.RentalStatus.FINISHED);
        List<Rental> array = new ArrayList<>();
        array.add(ren);
        when(this.rs.calculateValue(array, this.user)).thenReturn(0.0);
        double value = this.uf.calculateValue(array, this.user);
        assertTrue(value==0.0);
    }

    /**
     * Test calculateValue when user have to pay for the time and has not enough points to spend
     */
    @Test
    public void testCalculateValueWhenUserHaveToPayForTheTimeAndHasNotEnoughPoints(){
        LocalDateTime unlock = LocalDateTime.of(2019,1,1,13,0,0);
        LocalDateTime lock = LocalDateTime.of(2019,1,1,15,0,0);
        LocalTime length = LocalTime.of(2,0,0);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1, b, this.user, pickUp, leftAt, unlock, lock, Rental.RentalStatus.FINISHED);
        List<Rental> array = new ArrayList<>();
        array.add(ren);
        when(this.rs.calculateValue(array, this.user)).thenReturn(3.0);
        double value = this.uf.calculateValue(array, this.user);
        assertTrue(value==3.0);
    }

    /**
     * Test calculateValue when user don't have to pay for the time and has enough points to spend
     */
    @Test
    public void testCalculateValueWhenUserDontHaveToPayForTheTimeAndHasEnoughPoints(){
        LocalDateTime unlock = LocalDateTime.of(2019,1,1,13,0,0);
        LocalDateTime lock = LocalDateTime.of(2019,1,1,14,0,0);
        LocalTime length = LocalTime.of(1,0,0);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,this.user,pickUp,leftAt, unlock,lock, Rental.RentalStatus.FINISHED);
        List<Rental> array = new ArrayList<>();
        array.add(ren);
        when(this.rs.calculateValue(array, this.user)).thenReturn(0.0);
        double value = this.uf.calculateValue(array, this.user2);
        assertTrue(value==0.0);
    }

    /**
     * Test calculateValue when user have to pay for the time and has enough points to spend
     */
    @Test
    public void testCalculateValueWhenUserHaveToPayForTheTimeAndHasEnoughPoints(){
        LocalDateTime unlock = LocalDateTime.of(2019,1,1,13,0,0);
        LocalDateTime lock = LocalDateTime.of(2019,1,1,15,0,0);
        LocalTime length = LocalTime.of(2,0,0);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,this.user,pickUp,leftAt, unlock,lock, Rental.RentalStatus.FINISHED);        List<Rental> array = new ArrayList<>();
        array.add(ren);
        when(this.rs.calculateValue(array, this.user2)).thenReturn(2.0);
        double value = this.uf.calculateValue(array, this.user2);
        assertTrue(value==2.0);
    }

    @Test
    public void testUpdateRentalStatus(){
        Mockito.doThrow(new InvalidDataException()).when(us).updateRentalStatus(user,true);
        boolean result = this.uf.updateRentalStatus(user,true);
        assertTrue(!result);
        Mockito.doNothing().when(us).updateRentalStatus(user,true);
        boolean result2 = this.uf.updateRentalStatus(user,true);
        assertTrue(result2);
    }

    @Test
    public void ensureGetAllRentalsReturnsAllRentals(){
        List<Rental> allRentals = new ArrayList<>();
        User u = new User("Daniel","teste@teste.com","teste","teste","Teste",2f,2f,(long)1111222233334444.0,false,15.0);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,u, pickUp,leftAt,unlock,lock, Rental.RentalStatus.FINISHED,Rental.RentalPayment.NOT_PAID);
        allRentals.add(ren);
        when(this.rs.getAllRentals()).thenReturn(allRentals);
        List<Rental> result = this.uf.getAllRentals();
        assertEquals(allRentals, result);
    }

    @Test
    public void ensureGetAllRentalsMonthReturnsAllRentals(){
        List<Rental> allRentalsJune = new ArrayList<>();
        User u = new User("Daniel","teste@teste.com","teste","teste","Teste",2f,2f,(long)1111222233334444.0,false,15.0);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        Rental ren = new Rental(1,b,u, pickUp,leftAt,unlock,lock, Rental.RentalStatus.FINISHED,Rental.RentalPayment.NOT_PAID);
        allRentalsJune.add(ren);
        when(this.rs.getUnpaidRentalsOfMonth("Daniel", 6)).thenReturn(allRentalsJune);
        List<Rental> result = this.uf.getUnpaidRentalsOfMonth("Daniel", 6);
        assertEquals(allRentalsJune, result);
    }

}

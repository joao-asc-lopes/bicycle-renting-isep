package lapr.project.model.user;

import lapr.project.data.BicycleDao;
import lapr.project.data.RentalDao;
import lapr.project.data.UserDao;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    @InjectMocks
    private UserService us;

    @Mock
    private UserDao mockUserDao;

    @Mock
    private RentalDao mockRentalDao;

    @Mock
    private BicycleDao bicycleDao;


    private String email;

    private String nonExistentEmail;


    @BeforeEach
    public void setUp() {
        email = "user@service.pt";
        nonExistentEmail = "nonExistentUser@service.pt";
        mockUserDao = Mockito.mock(UserDao.class);
        mockRentalDao = Mockito.mock(RentalDao.class);
        bicycleDao = Mockito.mock(BicycleDao.class);
        initMocks(this);
    }

    @Test
    public void testGetUserReturnsCorrectInstanceOfUser() {
        User expected = new User("Daniel","user@service.pt", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        when(mockUserDao.getUser(email)).thenReturn(expected);
        User result = us.getUser(email);
        assertEquals(expected, result);
    }

    @Test
    public void testRemoveUserReturnsFalseUserDoesNotExist() {
        boolean expected = false;
        when(mockUserDao.removeUser(nonExistentEmail)).thenReturn(expected);
        boolean result = us.removeUser(nonExistentEmail);
        assertEquals(expected, result);
    }

    @Test
    public void testRemoveUserReturnsTrueUserExists() {
        boolean expected = true;
        when(mockUserDao.removeUser(email)).thenReturn(expected);
        boolean result = us.removeUser(email);
        assertEquals(expected, result);
    }

    @Test
    public void testAddUserReturnsTrueUserDoesNotExist() throws SQLException {
        User toBeAdded = new User("nonExistentName",nonExistentEmail, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        boolean expected = true;
        when(mockUserDao.addUser(toBeAdded)).thenReturn(expected);
        when(mockUserDao.getUser("nonExistentName")).thenThrow(new IllegalArgumentException());
        boolean result = us.registerUser("nonExistentName",nonExistentEmail, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        assertEquals(expected, result);
    }

    @Test
    public void testAddUserReturnsFalseUserAlreadyExists() throws SQLException {
        User toBeAdded = new User("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        boolean expected = false;
        when(mockUserDao.addUser(toBeAdded)).thenReturn(expected);
        when(mockUserDao.getUser(email)).thenReturn(toBeAdded);
        boolean result = us.registerUser("Daniel",nonExistentEmail, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        assertEquals(expected, result);
    }

    @Test
    public void testRegisterUserWhenUserDoesNotExistsInDataBaseAndDataIsValid() throws SQLException {
        boolean expected = true;
        User toBeAdded = new User("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        when(mockUserDao.getUser("Daniel")).thenThrow(new IllegalArgumentException());
        when(mockUserDao.addUser(toBeAdded)).thenReturn(true);
        boolean result = us.registerUser("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        assertEquals(expected, result);
    }


    @Test
    public void testRegisterUserWhenUserHasEmptyPasswordThrowsException() {
        when(mockUserDao.getUser(email)).thenThrow(new IllegalArgumentException());
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            us.registerUser("Daniel",email, "", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The password is not valid.", e.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasNullPasswordThrowsException() {
        when(mockUserDao.getUser(email)).thenThrow(new IllegalArgumentException());
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            us.registerUser("Daniel",email, null, "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The password is not valid.", e2.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasInvalidEmailThrowsException() {
        when(mockUserDao.getUser(email)).thenThrow(new IllegalArgumentException());
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            us.registerUser("Daniel","user@service", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The email syntax is not valid.", e.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasInvalidEmail2ThrowsException() {
        when(mockUserDao.getUser(email)).thenThrow(new IllegalArgumentException());
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            us.registerUser("Daniel","service.pt", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The email syntax is not valid.", e2.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasEmptyStringEmailThrowsException() {
        when(mockUserDao.getUser(email)).thenThrow(new IllegalArgumentException());
        InvalidDataException e3 = assertThrows(InvalidDataException.class, () -> {
            us.registerUser("Daniel","", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The email syntax is not valid.", e3.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasNullEmailThrowsException() {
        when(mockUserDao.getUser(email)).thenThrow(new IllegalArgumentException());
        InvalidDataException e4 = assertThrows(InvalidDataException.class, () -> {
            us.registerUser("Daniel",null, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The email syntax is not valid.", e4.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserEmailIsAlreadyRegisteredReturnsFalse() throws SQLException {
        when(mockUserDao.getUser(email)).thenReturn(new User("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0));
        boolean result = us.registerUser("Daniel",email, "somepassword", "John", (float) 65.0, (float) 1.82, (long) 1111222233335555.0,15.00);
        assertFalse(result);
    }


    @Test
    public void testRentBicycleThrowsExceptionIfUserAlreadyHasARentedBike() {
        NormalSlots ns = new NormalSlots(1,10,10);
        ElectricSlot es = new ElectricSlot(2,10,10,220,50);
        Park park = new Park(1,"Trindade",42,-8,ns,es,15);
        User loggedUser = new User("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, true,15.00,0);
        when(mockUserDao.getUser(email)).thenReturn(loggedUser);
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            us.rentBicycle("IDteste", park,loggedUser);
        });
        assertEquals("User already has a rented bicycle.", e.getMessage());
    }

    @Test
    public void testRentBicycleUpdatesLoggedUser() {
        int idRental = 189;
        int idBike = 222;
        LocalDateTime unlockDate = LocalDateTime.of(2018, 12, 16, 16, 15, 0);
        LocalDateTime lockDate = LocalDateTime.of(2018, 12, 25, 21, 0, 0);
        Rental.RentalStatus state = Rental.RentalStatus.FINISHED;
        LocalTime rentalLength = LocalTime.of(5, 0);
        NormalSlots ns1 = new NormalSlots(1,10,10);
        ElectricSlot es1 = new ElectricSlot(2,10,10,220,50);
        Park pickUp = new Park(1,"Trindade",42,-8,ns1,es1,15);
        Bicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f,0.1,5.0);
        User loggedUser = new User("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        when(mockUserDao.getUser(email)).thenReturn(loggedUser);
        when(bicycleDao.getBicycle("IDteste")).thenReturn(b);
        when(mockRentalDao.addRental(new Rental(idRental, b, loggedUser, pickUp,unlockDate))).thenReturn(true);
        us.rentBicycle("IDteste",pickUp, loggedUser);
        boolean result = loggedUser.hasActiveRental();
        assertTrue(result);
    }

    @Test
    public void ensureAwardUserPointsReturnsTrueIfPointsBiggerThan0AndUserExists() {
        boolean expected = true;
        when(this.mockUserDao.awardUserPoints(email, 15)).thenReturn(true);
        boolean result = this.us.awardUserPoints(email, 15);
        assertEquals(expected, result);
    }

    @Test
    public void ensureAwardUserPointsReturnsFalseIfPointsEqualZero() {
        boolean expected = false;
        when(this.mockUserDao.awardUserPoints(email, 0)).thenReturn(false);
        boolean result = this.us.awardUserPoints(email, 0);
        assertEquals(expected, result);
    }

    @Test
    public void ensureAwardUserPointsThrowsExceptionIfUserDoesntExist() {
        when(this.mockUserDao.awardUserPoints(email, 15)).thenThrow(new IllegalArgumentException("No User with email: " + email));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
            this.us.awardUserPoints(email, 15);
        });
        assertEquals("No User with email: " + email, e1.getMessage());
    }


    @Test
    public void testCreateReceiptWhenProcessFails() {
        when(mockUserDao.createReceipt(16, 23)).thenReturn(-1);
        int id = us.createReceipt(16, 23);
        assertTrue(id == -1);
    }

    @Test
    public void testCreateReceiptWhenProcessWorks() {
        when(mockUserDao.createReceipt(17, 23)).thenReturn(1);
        int id = us.createReceipt(17, 23);
        assertTrue(id == 1);
    }


    @Test
    public void testInsertReceiptRentalWhenProcessFails() {
        when(mockUserDao.insertReceiptRental(1, 1)).thenReturn(false);
        boolean result = us.insertReceiptRental(1, 1);
        assertTrue(!result);
    }

    @Test
    public void testInssertReceiptRentalWhenProcessWorks() {
        when(mockUserDao.insertReceiptRental(1, 1)).thenReturn(true);
        boolean result = us.insertReceiptRental(1, 1);
        assertTrue(result);
    }
}

package lapr.project.model.user;

import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    /**
     * Testing System.out.printlns
     */
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private User instance;
    @BeforeEach
    public void setUp(){
        instance = new User("Daniel","user@service.pt", "mynewpassword123", "somesalt", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.0);

    }

    @Test
    public void ensureCreateUserWithValidEmailAndPasswordDoesNotThrowException() {
        new User("Daniel","user@service.pt", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
    }

    @Test
    public void ensureCreateUserWithInvalidEmailThrowsException() {
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            new User("Daniel","user@service", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        });
        assertEquals("The email syntax is not valid.", e.getMessage());

        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            new User("Daniel","service.pt", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        });
        assertEquals("The email syntax is not valid.", e2.getMessage());

        InvalidDataException e3 = assertThrows(InvalidDataException.class, () -> {
            new User("Daniel","", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        });
        assertEquals("The email syntax is not valid.", e3.getMessage());

        InvalidDataException e4 = assertThrows(InvalidDataException.class, () -> {
            new User("Daniel",null, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        });
        assertEquals("The email syntax is not valid.", e4.getMessage());
    }

    @Test
    public void ensureCreateUserWithInvalidPasswordThrowsException() {
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            new User("Daniel","user@service.pt", "", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        });
        assertEquals("The password is not valid.", e.getMessage());

        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            new User("Daniel","user@service.pt", null, "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        });
        assertEquals("The password is not valid.", e2.getMessage());
    }

    @Test
    public void ensureEqualsReturnsFalseWhenOtherisNull() {
        boolean result = instance.equals(null);
        assertFalse(result);
    }

    @Test
    public void ensureEqualsReturnsFalseWhenOtherIsDifferentClass() {
        boolean result = instance.equals(new ArrayList<User>());
        assertFalse(result);
    }

    @Test
    public void ensureEqualsReturnsFalseWhenEmailIsDifferent() {
        User instance = new User("Daniel","user@service.pt", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        boolean result = instance.equals(new User("Dasdfiel","user2@service.pt", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0));
        assertFalse(result);
    }

    @Test
    public void ensureEqualsReturnsTrueWhenEmailIsSame() {
        boolean result = instance.equals(new User("Daniel","user@service.pt", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0));
        assertTrue(result);
    }

    @Test
    public void ensureEqualsReturnsTrueSameInstance() {
        boolean result = instance.equals(instance);
        assertTrue(result);
    }

    @Test
    public void ensureHashReturnsSameHashWithEqualEmail() {
        User instance2 = new User("Daniel","user@service.pt", "mynewpassword12", "John", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        assertEquals(instance.hashCode(), instance2.hashCode());
    }

    @Test
    public void ensureHashReturnsDifferentHashWithDifferentEmail() {
        User instance2 = new User("Daniel","user2@service.pt", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        assertNotEquals(instance.hashCode(), instance2.hashCode());
    }

    @Test
    public void ensureGetMethodsWorkAsIntended() {
        assertEquals(instance.getEmail(), "user@service.pt");
        assertEquals(instance.getEncryptedPassword(), "mynewpassword123");
        assertEquals(instance.getName(), "Steve");
        assertEquals(instance.getWeight(), (float) 70.0);
        assertEquals(instance.getHeight(), (float) 1.80);
        assertEquals(instance.hasActiveRental(), false);
        assertEquals(instance.getSalt(), "somesalt");
        assertEquals(instance.getCCNumber(), (long) 1111222233334444.0);
    }

    @Test
    public void ensureInitialPaymentDoesNotThrowException() {
        instance.performInitialPayment();
    }

    @Test
    public void testActivateRentalIsWorkingAsIntended() {
        instance.activateRental();
        boolean result = instance.hasActiveRental();
        assertTrue(result);
    }

    @Test
    public void testEndRentalIsWorkingAsIntended() {
        instance.activateRental();
        instance.endRental();
        boolean result = instance.hasActiveRental();
        assertFalse(result);
    }

    @Test
    public void ensurePaymentAPIWorksForTheUser() {
        System.setOut(new PrintStream(outContent));
        boolean result = instance.performInitialPayment();
        assertTrue(result);
    }

    @Test
    public void ensureSetPointsWorks(){
        int expected = 5;
        this.instance.setPoints(expected);
        assertEquals(this.instance.getPoints(), expected);
    }

    @Test
    public void ensureGetPointsWorks(){
        int expected = 0;
        assertEquals(expected, this.instance.getPoints());
    }

    @Test
    public void getAverageSpeed(){
        double expected = 15;
        double result = this.instance.getAverageSpeed();
        assertEquals(expected,result);
    }
    @Test
    public void ensureCreateUserWithSecondConstructor(){
        User user =new User ("123","teste@gmail.com","danny123","salt","Daniel",1,1,1,true,10);
        double expected = 0;
        double result = user.getPoints();
        assertEquals(expected,result);

    }


}

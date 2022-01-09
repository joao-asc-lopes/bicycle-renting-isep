package lapr.project.controller.user;

import lapr.project.model.user.UserFacade;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserRegistrationControllerTest {

    @InjectMocks
    private UserRegistrationController urc;

    @Mock
    private UserFacade uf;


    private String email;

    @BeforeEach
    public void setUp() {
        email = "user@service.pt";
        uf = mock(UserFacade.class);
        initMocks(this);
    }

    @Test
    public void testRegisterUserWhenUserDoesNotExistsInDataBaseAndDataIsValid() throws SQLException {
        boolean expected = true;
        when(uf.registerUser("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenReturn(true);
        boolean result = urc.registerUser("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        assertEquals(expected, result);
    }

    @Test
    public void testRegisterUserWhenUserHasEmptyPasswordThrowsException() throws SQLException{
        when(uf.registerUser("Daiel",email, "", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenThrow(new InvalidDataException("The password is not valid."));
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            urc.registerUser("Daiel",email, "", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The password is not valid.", e.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasNullPasswordThrowsException() throws SQLException{
        when(uf.registerUser("daniel",email, null, "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenThrow(new InvalidDataException("The password is not valid."));
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            urc.registerUser("daniel",email, null, "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The password is not valid.", e2.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasInvalidEmailThrowsException() throws SQLException{
        when(uf.registerUser("Daniel","user@service", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenThrow(new InvalidDataException("The email syntax is not valid."));
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            urc.registerUser("Daniel","user@service", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The email syntax is not valid.", e.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasInvalidEmail2ThrowsException() throws SQLException{
        when(uf.registerUser("Daniel","service.pt", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenThrow(new InvalidDataException("The email syntax is not valid."));
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            urc.registerUser("Daniel","service.pt", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The email syntax is not valid.", e2.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasEmptyStringEmailThrowsException() throws SQLException{
        when(uf.registerUser("Daniel","", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenThrow(new InvalidDataException("The email syntax is not valid."));
        InvalidDataException e3 = assertThrows(InvalidDataException.class, () -> {
            urc.registerUser("Daniel","", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The email syntax is not valid.", e3.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserHasNullEmailThrowsException() throws SQLException{
        when(uf.registerUser("Daniel",null, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenThrow(new InvalidDataException("The email syntax is not valid."));
        InvalidDataException e4 = assertThrows(InvalidDataException.class, () -> {
            urc.registerUser("Daniel",null, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00);
        });
        assertEquals("The email syntax is not valid.", e4.getMessage());
    }

    @Test
    public void testRegisterUserWhenUserEmailIsAlreadyRegisteredReturnsFalse() throws SQLException{
        when(uf.registerUser("Daniel",email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0,15.00)).thenReturn(false);
        boolean result = urc.registerUser("Daniel",email, "somepassword", "John", (float) 65.0, (float) 1.82, (long) 1111222233335555.0,15.00);
        assertFalse(result);
    }
}

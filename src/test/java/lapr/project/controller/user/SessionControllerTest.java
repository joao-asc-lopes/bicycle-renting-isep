package lapr.project.controller.user;

import lapr.project.model.user.User;
import lapr.project.model.user.UserService;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SessionControllerTest {

    @Mock
    private UserService us;

    @InjectMocks
    private SessionController sc;

    private String email;

    private String username;

    @BeforeEach
    public void setUp() {
        username ="userName";
        email = "user@service.pt";
        us = Mockito.mock(UserService.class);
        initMocks(this);
    }

    @Test
    public void testGetLoggedUserWhenNoUserIsLoggedReturnsNull() {
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            sc.getLoggedUser();
        });
        assertEquals("No user logged in.", e.getMessage());
    }

    @Test
    public void testGetLoggedUserReturnsLoggedUser() {
        User expected = new User(username,email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        when(us.getUser(username)).thenReturn(expected);
        sc.logUser(username, "mynewpassword123");
        User result = sc.getLoggedUser();
        assertEquals(expected, result);
    }

    @Test
    public void testLogUserWhenAUserIsAlreadyLogged() {
        User loggedUser = new User(username,email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        String u2Password = "mynewpassword";
        String u2Email = "someemail@service.pt";
        User expected = null;
        when(us.getUser(username)).thenReturn(loggedUser);
        sc.logUser(username, "mynewpassword123");
        User result = sc.logUser(u2Email, u2Password);
        assertEquals(expected, result);
    }

    @Test
    public void testLogUserWhenUserIsNotRegistered() {
        User expected = null;
        when(us.getUser(username)).thenReturn(null);
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            User result = sc.logUser(username, "mynewpassword123");
        });
        assertEquals("No user with given username.", e2.getMessage());
    }

    @Test
    public void testLogUserWhenUserPasswordIsIncorrect() {
        User expectedUser = new User(username, email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        User expected = null;
        when(us.getUser(username)).thenReturn(expectedUser);
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            User result = sc.logUser(username, "myoldpassword");
        });
        assertEquals("The password is incorrect.", e2.getMessage());
    }

    @Test
    public void testLogUserWhenCredentialsMatch() {
        User expectedUser = new User(username, email, "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        when(us.getUser(username)).thenReturn(expectedUser);
        User result = sc.logUser(username, "mynewpassword123");
        assertEquals(expectedUser, result);
    }
}

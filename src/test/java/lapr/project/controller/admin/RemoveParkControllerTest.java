package lapr.project.controller.admin;

import lapr.project.model.park.LocationFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RemoveParkControllerTest {

    @InjectMocks
    private RemoveParkController rpc;

    @Mock
    private LocationFacade fc;

    @BeforeEach
    public void setUp() {
        fc = mock(LocationFacade.class);
        initMocks(this);
    }

    @Test
    public void testIfRemoveParkReturnsTrueIfParkExists() {
        boolean expected = true;
        when(fc.removePark(1)).thenReturn(true);
        boolean result = rpc.removePark(1);
        assertEquals(expected, result);

    }

    @Test
    public void testIfRemoveParkReturnsFalseIfParkDoesNotExist() {
        boolean expected = false;
        when(fc.removePark(1)).thenReturn(false);
        boolean result = rpc.removePark(1);
        assertEquals(expected, result);
    }

}

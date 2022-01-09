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

public class RemoveInterestPointControllerTest {
    @InjectMocks
    private RemoveInterestPointController ripc;

    @Mock
    private LocationFacade bn;

    @BeforeEach
    public void setUp() {
        bn = mock(LocationFacade.class);
        initMocks(this);
    }

    @Test
    public void testIfRemoveInterestPointReturnsTrueIfPointExists() {
        boolean expected = true;
        when(bn.removeInterestPoint(1)).thenReturn(true);
        boolean result = this.ripc.removeInterestPoint(1);
        assertEquals(expected, result);
    }

    @Test
    public void testIfRemoveInterestPointReturnsFaleIfPointNotExists() {
        boolean expected = false;
        when(bn.removeInterestPoint(1)).thenReturn(false);
        boolean result = this.ripc.removeInterestPoint(1);
        assertEquals(expected, result);
    }
}

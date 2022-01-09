package lapr.project.controller.admin;

import lapr.project.model.park.InterestPoint;
import lapr.project.model.park.LocationFacade;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateInterestPointControllerTest {
    /**
     * Variable that represents the controller of the Interest Point.
     */
    @InjectMocks
    private CreateInterestPointController cipc;
    /**
     * Variable that represents the bicycle Network.
     */
    @Mock
    private LocationFacade bn;
    /**
     * Variable that represents the identifier of the Interest Point.
     */
    private int idIdentifier;

    @BeforeEach
    public void setUp() {
        this.idIdentifier = 1;
        this.bn = mock(LocationFacade.class);
        initMocks(this);

    }

    @Test
    public void ensureRegisterIdentifeierDoesNotExistReturnsTrue() throws IOException {

        InterestPoint expected = new InterestPoint("Daniel",40,-8,615.0);

        when(bn.registerInterestPoint(expected.getName(), expected.getLatitude(), expected.getLongitude(), expected.getAltitude())).thenReturn(expected);
        InterestPoint result = this.cipc.addInterestPoint("Daniel",40,-8,615.0);
        assertEquals(expected,result);
    }

    @Test
    public void ensureRegisterIdentifierAlreadyExistsThrowsException() throws IOException {

        InterestPoint expected = new InterestPoint("Daniel",40,-8,615.0);


        when(bn.registerInterestPoint("Daniel",40,-8,1.0)).thenThrow(InvalidDataException.class);
        Exception e1 = assertThrows(InvalidDataException.class, () -> {
            this.cipc.addInterestPoint("Daniel",40,-8,1.0);
        });
    }
}

package lapr.project.controller.admin;

import lapr.project.model.park.InterestPoint;
import lapr.project.model.park.LocationFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UpdateInterestPointControllerTest {
    /**
     * Variable that represents the controller of the Interest Point.
     */
    @InjectMocks
    private UpdateInterestPointController cipc;
    /**
     * Variable that represents the bicycle Network.
     */
    @Mock
    private LocationFacade bn;

    ArrayList<InterestPoint> listWithData;
    ArrayList<InterestPoint> listWithNoData;

    @BeforeEach
    public void setUp() {
        listWithData = new ArrayList<>();
        listWithNoData = new ArrayList<>();
        listWithData.add(new InterestPoint(1,"Bolhão",20,20,20));
        this.bn = mock(LocationFacade.class);
        initMocks(this);

    }

    @Test
    public void ensureRegisterIdentifeierDoesNotExistReturnsTrue() {
        boolean expFlag = true;
        when(bn.updateInterestPoint(1, "Daniel")).thenReturn(true);
        boolean result = this.cipc.updateInterestPoint(1, "Daniel");
        assertEquals(expFlag, result);


    }

    @Test
    public void ensureRegisterIdentifierAlreadyExistsReturnsFalse() {
        boolean expFlag = false;
        when(bn.updateInterestPoint(1, "Daniel")).thenReturn(false);
        boolean result = this.cipc.updateInterestPoint(2, "Das");
        assertEquals(expFlag, result);
    }

    @Test
    public void ensureGetAllInterestPointsReturnsEmptyArrayIfThereAreNoInterestPointsInDatabase(){
        ArrayList<InterestPoint> expected = new ArrayList<>();
        when(this.bn.getAllInterestPoints()).thenReturn(this.listWithNoData);
        List<InterestPoint> result = this.cipc.getAllInterestPoints();
        assertEquals(result.size(), expected.size());
        assertEquals(result.size(), 0);

    }

    @Test
    public void ensureGetAllInterestPointsReturnsArrayWithDataIfThereAreNoInterestPointsInDatabase(){
        List<InterestPoint> expected = new ArrayList<>();
        expected.add(new InterestPoint(1,"Bolhão",20,20,20));
        when(this.bn.getAllInterestPoints()).thenReturn(this.listWithData);
        List<InterestPoint> result = this.cipc.getAllInterestPoints();
        assertEquals(result.size(), expected.size());
        assertEquals(result.size(), 1);

    }
}

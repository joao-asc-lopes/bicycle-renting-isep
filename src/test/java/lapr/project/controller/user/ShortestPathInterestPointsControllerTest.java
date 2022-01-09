package lapr.project.controller.user;

import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.bikenetwork.Route;
import lapr.project.model.park.*;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ShortestPathInterestPointsControllerTest {

    @Mock
    private LocationFacade lf;

    @Mock
    private BicycleNetwork bn;

    @InjectMocks
    private ShortestPathInterestPointsController spic;


    private Park p1;
    private Park p2;

    /**
     * Variable that represents the park with the id 3.
     */
    private Park p3;
    /**
     * Variable that represents the park with the id 5.
     */
    private Park p5;
    /**
     * Variable that represents the park with the id 6.
     */
    private Park p6;
    /**
     * Variable that represents the park with the id 7.
     */
    private Park p7;
    /**
     * Variable that represents the park with the id 8.
     */
    private Park p8;


    /**
     * Variable that represents an interest point.
     */
    private InterestPoint ip1;
    /**
     * Variable that represents an interest point.
     */

    private InterestPoint ip2;


    @BeforeEach
    public void setUp() {
        lf = Mockito.mock(LocationFacade.class);
        bn = Mockito.mock(BicycleNetwork.class);

        initMocks(this);
        this.p1 = new Park(1, "Parque do Dani", 30, 40, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 220, 50), 1.0);
        this.p2 = new Park(2, "Parque do parque", 40, 51, new NormalSlots(3, 10, 10), new ElectricSlot(4, 100, 10, 220, 50), 100.0);
        this.p3 = new Park(3, "Parque da Bicicletas", 10, 25, new NormalSlots(5, 10, 10), new ElectricSlot(6, 200, 20, 220, 50), 1.0);
        this.p5 = new Park(5, "Parque das outras bicicletas", 10, 11, new NormalSlots(9, 100, 100), new ElectricSlot(10, 100, 10, 220, 50), 1.0);
        this.p6 = new Park(6, "Outro Parque", 11, 19, new NormalSlots(11, 10, 10), new ElectricSlot(12, 100, 10, 220, 50), 1.0);
        this.p7 = new Park(7, "Parquee das Bicicletas Portuenses", 1, 12, new NormalSlots(13, 10, 10), new ElectricSlot(14, 100, 10, 220, 50), 1.0);
        this.p8 = new Park(8, "Parque das Bicicletas Lisboetas", 3, 9, new NormalSlots(15, 10, 10), new ElectricSlot(16, 100, 10, 220, 50), 1.0);

        this.ip1 = new InterestPoint(9, "Dani", 10, 10, 10);
        this.ip2 = new InterestPoint(10, "Dani", 10, 10, 10);
    }

    @Test
    public void testGetParkRegistryWhenNoParksAreRegistered() {
        ArrayList<Park> expected = new ArrayList<>();
        when(this.lf.getParkList()).thenReturn(expected);
        List<Park> result = this.spic.getParkList();
        assertEquals(expected, result);

    }

    @Test
    public void testGetParkListWorksWithParksAreRegistered() {
        ArrayList<Park> expected = new ArrayList<>();
        expected.add(this.p3);
        when(this.lf.getParkList()).thenReturn(expected);
        List<Park> result = this.spic.getParkList();
        assertEquals(expected, result);

    }

    @Test
    public void testGetInterestPointsWhenNoInterestPointsRegistered() {
        ArrayList<InterestPoint> expected = new ArrayList<>();
        when(this.lf.getAllInterestPoints()).thenReturn(expected);
        List<InterestPoint> result = this.spic.getInterestPointList();
        assertEquals(expected, result);
    }

    @Test
    public void testGetInterestPointsWhenThereIsInterestPoints() {
        ArrayList<InterestPoint> expected = new ArrayList<>();
        expected.add(this.ip1);
        when(this.lf.getAllInterestPoints()).thenReturn(expected);
        List<InterestPoint> result = this.spic.getInterestPointList();
        assertEquals(expected, result);

    }

    @Test
    public void ensureShortestPathPassingThroughInterestPointsRThrowsExceptionWhenInitialParkIsNull() throws InvalidDataException {
        List<Location> expected = new ArrayList<>();
        expected.add(this.ip1);
        when(this.bn.shortestPathByDistancePassingByInterestPoints(null, p2, expected)).thenThrow(IllegalArgumentException.class);
        boolean flag = false;
        try {
            List<Route> result = this.spic.getShortestPathIterable(null, p2, expected);
        } catch (IllegalArgumentException e) {
            flag = true;
        }
        boolean expFlag = true;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureShortestPathPassingThroughInterestPointsRThrowsExceptionWhenDestinationParkIsNull() throws InvalidDataException {
        List<Location> expected = new ArrayList<>();
        expected.add(this.ip1);
        when(this.bn.shortestPathByDistancePassingByInterestPoints(p1, null, expected)).thenThrow(IllegalArgumentException.class);
        boolean flag = false;
        try {
            List<Route> result = this.spic.getShortestPathIterable(p1, null, expected);
        } catch (IllegalArgumentException e) {
            flag = true;
        }
        boolean expFlag = true;
        assertEquals(expFlag, flag);
    }


    @Test
    public void ensureShortestPathByDistancePassingThroughInterestPointsReturnsEdgeWhenListIsEmpty() throws InvalidDataException {
        List<Location> interestPoint = new ArrayList<>();
        interestPoint.add(this.ip1);
        LinkedList<Location> finalList = new LinkedList<>();
        List<Route> expected = new ArrayList<>();
        expected.add(new Route(finalList));
        when(this.spic.getShortestPathIterable(p1, p2, interestPoint)).thenReturn(expected);
        finalList.add(this.p1);
        finalList.add(this.ip1);
        finalList.add(this.p2);
        List<Route> result = this.spic.getShortestPathIterable(p1, p2, interestPoint);

        assertEquals(finalList, result.get(0).getPath());

    }


}

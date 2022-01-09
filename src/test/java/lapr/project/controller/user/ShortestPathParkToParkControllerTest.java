package lapr.project.controller.user;

import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.bikenetwork.Route;
import lapr.project.model.park.*;
import lapr.project.model.user.User;
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

public class ShortestPathParkToParkControllerTest {
    @Mock
    private LocationFacade pf;


    @Mock
    private BicycleNetwork bn;
    /**
     * Injects the mocks to the  Shortest Path Efficient Electrical Controller.
     */

    @InjectMocks
    private ShortestPathParkToParkController sppc;

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
     * Variable that represents the electrical bicycle.
     */


    private ElectricBicycle eb1;

    private User loggedUser;

    private ElectricBicycle chosenBike;


    @BeforeEach
    public void setUp() {
        pf = Mockito.mock(LocationFacade.class);
        bn = Mockito.mock(BicycleNetwork.class);

        initMocks(this);

        this.p1 = new Park(1, "Parque do Dani", 30, 40, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 1, 1), 1.0);
        this.p2 = new Park(2, "Parque do parque", 40, 51, new NormalSlots(3, 10, 10), new ElectricSlot(4, 100, 10, 1, 1), 100.0);
        this.p3 = new Park(3, "Parque da Bicicletas", 10, 25, new NormalSlots(5, 10, 10), new ElectricSlot(6, 200, 20, 1, 1), 1.0);
        this.p5 = new Park(5, "Parque das outras bicicletas", 10, 11, new NormalSlots(9, 100, 100), new ElectricSlot(10, 100, 10, 1, 1), 1.0);
        this.p6 = new Park(6, "Outro Parque", 11, 19, new NormalSlots(11, 10, 10), new ElectricSlot(12, 100, 10, 1, 1), 1.0);
        this.p7 = new Park(7, "Parquee das Bicicletas Portuenses", 1, 12, new NormalSlots(13, 10, 10), new ElectricSlot(14, 100, 10, 1, 1), 1.0);
        this.p8 = new Park(8, "Parque das Bicicletas Lisboetas", 3, 9, new NormalSlots(15, 10, 10), new ElectricSlot(16, 100, 10, 1, 1), 1.0);

    }

    @Test
    public void testGetParkRegistryWhenNoParksAreRegistered() {
        ArrayList<Park> expected = new ArrayList<>();
        when(this.pf.getParkList()).thenReturn(expected);
        List<Park> result = this.sppc.getParkList();
        assertEquals(expected, result);

    }

    @Test
    public void testGetParkListWorksWithParksAreRegistered() {
        ArrayList<Park> expected = new ArrayList<>();
        expected.add(this.p3);
        when(this.pf.getParkList()).thenReturn(expected);
        List<Park> result = this.sppc.getParkList();
        assertEquals(expected, result);

    }

    @Test
    public void ensureShortestPathByDistanceThrowsExceptionWhenInitialParkIsNull() throws InvalidDataException {
        when(this.bn.shortestPathByDistance(null, p2)).thenThrow(IllegalArgumentException.class);
        boolean flag = true;
        try {
            this.sppc.shortestPathParkToPark(null, p2);
        } catch (IllegalArgumentException e) {
            flag = false;
        }
        assertEquals(false, flag);
    }

    @Test
    public void ensureShortestPathByDistanceThrowsExceptionWhenFinalParkIsNull() throws InvalidDataException {
        when(this.bn.shortestPathByDistance(p1, null)).thenThrow(IllegalArgumentException.class);
        boolean flag = true;
        try {
            this.sppc.shortestPathParkToPark(p1, null);
        } catch (IllegalArgumentException e) {
            flag = false;
        }
        assertEquals(false, flag);
    }


    @Test
    public void ensureShortestPathByDistanceReturnsEdgeBetweenTheParksWhenTheyAreValid() throws InvalidDataException {
        LinkedList<Location> list = new LinkedList<>();
        list.add(this.p1);
        list.add(this.p2);
        Route expectedRoute = new Route(list);
        List<Route> expected = new ArrayList<>();
        expected.add(expectedRoute);
        when(this.bn.shortestPathByDistance(p1, p2)).thenReturn(expected);
        Iterable<Location> result = this.sppc.shortestPathParkToPark(p1, p2).get(0).getPath();

        assertEquals(list, result);
    }


}

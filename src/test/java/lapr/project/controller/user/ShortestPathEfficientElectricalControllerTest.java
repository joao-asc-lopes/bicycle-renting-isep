package lapr.project.controller.user;

import lapr.project.model.bicycle.Battery;
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.BicycleFacade;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.bikenetwork.Route;
import lapr.project.model.park.*;
import lapr.project.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class ShortestPathEfficientElectricalControllerTest {
    @Mock
    private LocationFacade pf;

    @Mock
    private BicycleFacade bf;

    @Mock
    private BicycleNetwork bn;
    /**
     * Injects the mocks to the  Shortest Path Efficient Electrical Controller.
     */

    @InjectMocks
    private ShortestPathEfficientElectricalController spec;

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
        this.bf = Mockito.mock(BicycleFacade.class);
        initMocks(this);

        this.p1 = new Park(1, "Parque do Dani", 30, 40, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 220, 50), 1.0);
        this.p2 = new Park(2, "Parque do parque", 40, 51, new NormalSlots(3, 10, 10), new ElectricSlot(4, 100, 10, 220, 50), 100.0);
        this.p3 = new Park(3, "Parque da Bicicletas", 10, 25, new NormalSlots(5, 10, 10), new ElectricSlot(6, 200, 20, 220, 50), 1.0);
        this.p5 = new Park(5, "Parque das outras bicicletas", 10, 11, new NormalSlots(9, 100, 100), new ElectricSlot(10, 100, 10, 220, 50), 1.0);
        this.p6 = new Park(6, "Outro Parque", 11, 19, new NormalSlots(11, 10, 10), new ElectricSlot(12, 100, 10, 220, 50), 1.0);
        this.p7 = new Park(7, "Parquee das Bicicletas Portuenses", 1, 12, new NormalSlots(13, 10, 10), new ElectricSlot(14, 100, 10, 220, 50), 1.0);
        this.p8 = new Park(8, "Parque das Bicicletas Lisboetas", 3, 9, new NormalSlots(15, 10, 10), new ElectricSlot(16, 100, 10, 220, 50), 1.0);

        this.eb1 = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), new Battery(10, 10, 10, 10), 10, 10, 5.0);


        this.loggedUser = new User("Daniel", "someemail@email.com", "password", "salt", 1, 1, 1, false, 15.00, 0);
        this.chosenBike = new ElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, new Battery(1, 1, 1, 1), 1, 10, 5.0);

    }

    @Test
    public void testGetParkRegistryWhenNoParksAreRegistered() {
        ArrayList<Park> expected = new ArrayList<>();
        when(this.pf.getParkList()).thenReturn(expected);
        List<Park> result = this.spec.getParkList();
        assertEquals(expected, result);

    }

    @Test
    public void testGetParkListWorksWithParksAreRegistered() {
        ArrayList<Park> expected = new ArrayList<>();
        expected.add(this.p3);
        when(this.pf.getParkList()).thenReturn(expected);
        List<Park> result = this.spec.getParkList();
        assertEquals(expected, result);

    }

    @Test
    public void testGetAllBikesAreNoBikesRegistered() {
        List<ElectricBicycle> expected = new ArrayList<>();
        when(this.bf.getElectricBicyclesList()).thenReturn(expected);
        List<ElectricBicycle> result = this.spec.getElectricalBicycles();
        assertEquals(expected, result);
    }

    @Test
    public void testGetElectricalBicyclesWorksWithBicyclesRegistered() {
        List<ElectricBicycle> expected = new ArrayList<>();
        expected.add(this.eb1);
        when(this.bf.getElectricBicyclesList()).thenReturn(expected);
        List<ElectricBicycle> result = this.spec.getElectricalBicycles();
        assertEquals(expected, result);

    }

    @Test
    public void ensureShortestPathByElectricExpenseThrowsExceptionWhenInitialParkIsNull() throws IOException {
        when(this.bn.shortestPathByEnergeticEfficiency(loggedUser, chosenBike, null, p2)).thenThrow(IllegalArgumentException.class);
        boolean flag = true;
        try {
            Iterable<Location> result = this.spec.shortestElectricalPath(loggedUser, chosenBike, null, p2).get(0).getPath();
        } catch (IllegalArgumentException e) {
            flag = false;
        }
        assertEquals(false, flag);

    }

    @Test
    public void ensureShortestPathByElectricExpenseThrowsExceptionWhenFinalParkIsNull() throws IOException {
        when(this.bn.shortestPathByEnergeticEfficiency(loggedUser, chosenBike, p1, null)).thenThrow(IllegalArgumentException.class);
        boolean flag = true;
        try {
            Iterable<Location> result = this.spec.shortestElectricalPath(loggedUser, chosenBike, p1, null).get(0).getPath();
        } catch (IllegalArgumentException e) {
            flag = false;
        }
        assertEquals(false, flag);

    }

    @Test
    public void ensureShortestPathByElectricExpenseThrowsExceptionWhenUserIsNull() {
        when(this.bn.shortestPathByEnergeticEfficiency(null, chosenBike, p1, p2)).thenThrow(IllegalArgumentException.class);
        boolean flag = true;
        try {
            Iterable<Location> result = this.spec.shortestElectricalPath(null, chosenBike, p1, p2).get(0).getPath();
            ;
        } catch (IllegalArgumentException e) {
            flag = false;
        }
        assertEquals(false, flag);

    }

    @Test
    public void ensureShortestPathByElectricExpenseThrowsExceptionWhenBikeIsNull() throws IOException {
        when(this.bn.shortestPathByEnergeticEfficiency(loggedUser, null, p1, p2)).thenThrow(IllegalArgumentException.class);
        boolean flag = true;
        try {
            Iterable<Location> result = this.spec.shortestElectricalPath(loggedUser, null, p1, p2).get(0).getPath();
        } catch (IllegalArgumentException e) {
            flag = false;
        }
        assertEquals(false, flag);
    }


    @Test
    public void ensureShortestPathByElectricExpenseReturnsCorrectPathBetweenTheParksWhenTheyAreValid() throws IOException {
        List<Park> parkList = new ArrayList<>();
        parkList.add(p1);
        parkList.add(p2);
        List<Route> expected = new ArrayList<>();
        Route r1 = new Route();
        r1.addLocation(p1);
        r1.addLocation(p2);
        when(pf.getParkList()).thenReturn(parkList);
        this.bn.loadData();
        expected.add(r1);
        when(this.bn.shortestPathByEnergeticEfficiency(loggedUser, chosenBike, p1, p2)).thenReturn(expected);
        List<Route> result = this.spec.shortestElectricalPath(loggedUser, chosenBike, p1, p2);
        assertEquals(expected, result);
    }


}

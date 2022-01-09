package lapr.project.controller.admin;

import lapr.project.model.bicycle.Battery;
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ReportParkControllerTest {

    @InjectMocks
    private ReportParkController rpc;

    @Mock
    private LocationFacade pf;
    private Battery bat;
    private Battery bat2;
    private ElectricBicycle bike;
    private ElectricBicycle bike2;

    @BeforeEach
    public void setUp() {
        pf = mock(LocationFacade.class);
        initMocks(this);
        this.bat = new Battery(1, 200, 200, 1);
        this.bat2 = new Battery(2, 500, 200, 1);
        this.bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), bat, 1,10,5.0);
        this.bike2 = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), bat2, 1,10,5.0);
    }

    @Test
    public void testGetParkRegistryWhenNoParksAreRegisted() {
        List<Park> array = rpc.getParkRegistry();
        assertTrue(array.isEmpty());
    }

    @Test
    public void testGetParkRegistryWhenParksAreRegisted() {
        List<Park> a = new ArrayList<>();
        a.add(new Park(1, "teste", 1, 1, null, null, 1.0));
        when(pf.getParkList()).thenReturn(a);
        List<Park> result = rpc.getParkRegistry();
        assertTrue(!result.isEmpty());
        assertTrue(result.size() == 1);

    }

    @Test
    public void testGetEletricalBikesWhenNoBikesAreThere() {
        Park p = new Park(1, "teste", 1, 1, null, null, 1.0);
        List<ElectricBicycle> ebc = rpc.getEletricBikesPark(p);
        assertTrue(ebc.isEmpty());
    }

    @Test
    public void testGetEletricBikesRegistryWhenBikesAreRegisted() {
        ArrayList<Park> a = new ArrayList<>();
        Park p = new Park(1, "teste", 1, 1, null, null, 1.0);
        a.add(p);
        List<ElectricBicycle> ebc = new ArrayList<>();
        ElectricBicycle eb = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), new Battery(1, 1, 1, 1), 1,10,5.0);
        ebc.add(eb);
        when(pf.getElectricBicyclesPark(1)).thenReturn(ebc);
        List<ElectricBicycle> result = rpc.getEletricBikesPark(p);
        assertTrue(!result.isEmpty());
        assertTrue(result.size() == 1);

    }

    @Test
    public void ensureShowInfoThrowsNullIfThereIsNotBikes() {
        Park p = new Park(1, "Parque do Rossio", 20.4, 30.5, new NormalSlots(1, 10, 5), new ElectricSlot(1, 40, 20,220,50), 1.0);
        ArrayList<ElectricBicycle> eb = new ArrayList<>();
        ArrayList<String> result = this.rpc.getReport(p, eb);
        ArrayList<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
}


package lapr.project.controller.user;

import lapr.project.model.bicycle.Bicycle;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CheckSpaceInParkToLockBicycleControllerTest {


    @InjectMocks
    private CheckSpaceInParkToLockBicycleController ctrl;
    @Mock
    private LocationFacade bn;


    @BeforeEach
    public void setUp() {
        bn = mock(LocationFacade.class);
        initMocks(this);
    }


    @Test
    public void ensurePrintAllBicycleTypesDoesNotThrowExceptionWithRoadType() {

        ctrl.printAllBicycleTypes(Bicycle.BicycleType.ROAD);

    }

    @Test
    public void ensurePrintAllBicycleTypesDoesNotThrowExceptionWithMountainType() {

        int choice = 2;

        ctrl.printAllBicycleTypes(Bicycle.BicycleType.MOUNTAIN);

    }

    @Test
    public void ensurePrintAllBicycleTypesDoesNotThrowExceptionWithElectricalType() {

        int choice = 3;

        ctrl.printAllBicycleTypes(Bicycle.BicycleType.ELECTRIC);

    }


    @Test
    public void ensureGetParkByIdReturnsCorrectPark() {

        Park expected = new Park(1, "Parque do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50),1.0);
        when(bn.getParkById(expected.getIdLocation())).thenReturn(expected);
        Park result = ctrl.getParkById(expected.getIdLocation());


        assertEquals(expected, result);
    }


    @Test
    public void ensureGetAllParksReturnsListWithAllParks() {

        ArrayList<Park> expected = new ArrayList<>();
        boolean flag = true;

        Park aux1 = new Park(1, "Parque do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50),1.0);
        Park aux2 = new Park(2, "Parque do Alex", 11, 11, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50),1.0);
        Park aux3 = new Park(3, "Parque do Leonardo", 12, 12, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50),1.0);

        expected.add(aux1);
        expected.add(aux2);
        expected.add(aux3);

        when(bn.getParkList()).thenReturn(expected);

        List<Park> result = ctrl.getAllParksList();

        assertEquals(expected, result);

    }

    @Test
    public void ensureGetSlotsByParkAndBicycleTypeReturnsCorrectNumberFreeNormalSlotsWithRoadType() {

        Park aux1 = new Park(1, "Parque do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50),1.0);

       Bicycle.BicycleType type = Bicycle.BicycleType.ROAD;

        ctrl.printAllBicycleTypes(type);

        int expected = aux1.getNormalSlots().getNumberFreeSlots();

        int result = ctrl.getSlotsByParkAndBicycleType(aux1);

        assertEquals(expected, result);

    }

    @Test
    public void ensureGetSlotsByParkAndBicycleTypeReturnsCorrectNumberNormalSlotsWithMountainType() {

        Park aux1 = new Park(1, "Parque do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50),1.0);

        Bicycle.BicycleType type = Bicycle.BicycleType.MOUNTAIN;

        ctrl.printAllBicycleTypes(type);

        int expected = aux1.getNormalSlots().getNumberFreeSlots();

        int result = ctrl.getSlotsByParkAndBicycleType(aux1);
        assertEquals(expected, result);

    }

    @Test
    public void ensureGetSlotsByParkAndBicycleTypeReturnsCorrectNumberFreeElectricalSlots() {

        Park aux1 = new Park(1, "Parque do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50),1.0);

        Bicycle.BicycleType type = Bicycle.BicycleType.ELECTRIC;

        ctrl.printAllBicycleTypes(type);

        int expected = aux1.getEletricalSlots().getNumberFreeSlots();

        int result = ctrl.getSlotsByParkAndBicycleType(aux1);

        assertEquals(expected, result);

    }
}
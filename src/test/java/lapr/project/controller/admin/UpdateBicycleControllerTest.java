package lapr.project.controller.admin;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.BicycleFacade;
import lapr.project.model.bicycle.MountainBicycle;
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

class UpdateBicycleControllerTest {

    @InjectMocks
    private UpdateBicycleController unebc;

    @Mock
    private BicycleFacade bikeFacade;
    private ArrayList<Bicycle> idBicycles;
    private ArrayList<Bicycle> idBicyclesNoData;
    private MountainBicycle bike1;
    private MountainBicycle bike2;
    private MountainBicycle bike3;

    @BeforeEach
    public void setUp() {
        idBicycles = new ArrayList<>();
        idBicyclesNoData = new ArrayList<>();
         bike1 = new MountainBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        bike2 = new MountainBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        bike3 = new MountainBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        idBicycles.add(bike1);
        idBicycles.add(bike2);
        idBicycles.add(bike3);
        bikeFacade = mock(BicycleFacade.class);
        initMocks(this);
    }

    @Test
    void testIfUpdateBicycleReturnsTrueIfBicycleDoesntExist() {
        boolean expected = true;
        when(bikeFacade.updateNonElectricalBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10)).thenReturn(true);
        boolean result = unebc.updateBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10);
        assertEquals(expected, result);
    }

    @Test
    void testIfUpdateBicycleReturnsFalseIfBicycleExists() {
        boolean expected = false;
        when(bikeFacade.updateNonElectricalBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10)).thenReturn(false);
        boolean result = unebc.updateBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10);
        assertEquals(expected, result);
    }

    @Test
    void testIfUpdateBicycleReturnsFalseIfWeightIsInvalid() {
        boolean expected = false;
        when(bikeFacade.updateNonElectricalBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, -5,10)).thenReturn(false);
        boolean result = unebc.updateBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, -5,10);
        assertEquals(expected, result);
    }

    @Test
    void testIfUpdateBicycleReturnsFalseIfIDIsInvalid() {
        boolean expected = false;
        when(bikeFacade.updateNonElectricalBicycle(null, Bicycle.BicycleStatus.AVAILABLE, 1,10)).thenReturn(false);
        boolean result = unebc.updateBicycle(null, Bicycle.BicycleStatus.AVAILABLE, 1,10);
        assertEquals(expected, result);
    }

    @Test
    void testIfGetAllBicyclesReturnsValuesIfDatabaseHasBicycles() {
        int expected = 3;
        when(unebc.getAllBicycles()).thenReturn(idBicycles);
        int result = idBicycles.size();
        assertEquals(result, expected);
    }

    @Test
    void testIfGetAllBicyclesReturnsNoValuesIfDatabaseIsEmpty() {
        int expected = 0;
        when(unebc.getAllBicycles()).thenReturn(idBicyclesNoData);
        int result = idBicyclesNoData.size();
        assertEquals(result, expected);
    }

    @Test
    void testIfUpdateElectricBicycleReturnsTrueIfBicycleDoesntExist() {
        boolean expected = true;
        when(bikeFacade.updateElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 150f,1,10)).thenReturn(true);
        boolean result = unebc.updateElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 150f,1,10);
        assertEquals(expected, result);
    }

    @Test
    void testIfUpdateElectricBicycleReturnsFalseIfBicycleExists() {
        boolean expected = false;
        when(bikeFacade.updateElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 150f,1,10)).thenReturn(false);
        boolean result = unebc.updateElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 150f,1,10);
        assertEquals(expected, result);
    }

    @Test
    void testIfUpdateElectricBicycleReturnsTrueIfWeightIsInvalid() {
        boolean expected = false;
        when(bikeFacade.updateElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f, -5,10)).thenReturn(false);
        boolean result = unebc.updateElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 150f,-5,10);
        assertEquals(expected, result);
    }

    @Test
    void testIfUpdateElectricBicycleReturnsFalseIfIDIsInvalid() {
        boolean expected = false;
        when(bikeFacade.updateElectricBicycle(null, Bicycle.BicycleStatus.AVAILABLE, 150f,1,10)).thenReturn(false);
        boolean result = unebc.updateElectricBicycle(null, Bicycle.BicycleStatus.AVAILABLE,150f, 1,10);
        assertEquals(expected, result);
    }

    @Test
    void testIfGetAllBicyclesReturnsNothing() {
        ArrayList<Bicycle> lbike = new ArrayList<>();
        when(this.bikeFacade.getAllBicycles()).thenReturn(this.idBicyclesNoData);
        List<Bicycle> result = this.unebc.getAllBicycles();
        assertEquals(lbike, result);
    }

    @Test
    void testIfGetAllBicyclesReturnsNoVaasdfy() {
        List<Bicycle> lbike = new ArrayList<>();
        idBicycles.add(bike1);
        idBicycles.add(bike2);
        idBicycles.add(bike3);
        when(this.bikeFacade.getAllBicycles()).thenReturn(this.idBicyclesNoData);
        List<Bicycle> result = this.unebc.getAllBicycles();
        assertEquals(lbike, result);
    }




}
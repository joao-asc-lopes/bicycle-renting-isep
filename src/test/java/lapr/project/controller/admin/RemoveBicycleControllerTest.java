package lapr.project.controller.admin;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.BicycleFacade;
import lapr.project.model.bicycle.RoadBicycle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class RemoveBicycleControllerTest {
    @InjectMocks
    private RemoveBicycleController rbc;

    @Mock
    private BicycleFacade bf;
    ArrayList<Bicycle> idBicycles;
    ArrayList<Bicycle> idBicyclesNoData;
    private RoadBicycle bike1;
    private RoadBicycle bike2;
    private RoadBicycle bike3;

    @BeforeEach
    public void setUp() {
        idBicycles = new ArrayList<>();
        idBicyclesNoData = new ArrayList<>();
       bike1 = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
       bike2 = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
         bike3 = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        idBicycles.add(bike1);
        idBicycles.add(bike2);
        idBicycles.add(bike3);
        bf = mock(BicycleFacade.class);
        initMocks(this);
    }

    @Test
    void testIfCreateBicycleReturnsTrueIfBicycleDoesntExist() {
        boolean expected = true;
        when(bf.removeBicycle(1)).thenReturn(true);
        boolean result = rbc.removeBicycle(1);
        assertEquals(expected, result);
    }

    @Test
    void testIfCreateBicycleReturnsFalseIfBicycleExists() {
        boolean expected = false;
        when(bf.removeBicycle(1)).thenReturn(false);
        boolean result = rbc.removeBicycle(1);
        assertEquals(expected, result);
    }

    @Test
    void testIfGetAllBicyclesReturnsValuesIfDatabaseHasBicycles() {
        int expected = 3;
        when(rbc.getAllBicycles()).thenReturn(idBicycles);
        int result = idBicycles.size();
        assertEquals(result, expected);
    }

    @Test
    void testIfGetAllBicyclesReturnsValuesIfDatabaseHasBicyclesComparingBicycles() {
        System.out.println("This is a test responsible to get the shortest parks given coordinates.");
        ArrayList<Bicycle> result = new ArrayList<>();
        result.add(this.bike1);
        result.add(bike2);
        result.add(bike3);

        when(this.rbc.getAllBicycles()).thenReturn(result);

        ArrayList<Bicycle> expResult = new ArrayList<>();
        expResult.add(this.bike1);
        expResult.add(bike2);
        expResult.add(bike3);
        assertEquals(expResult, result);

    }


    @Test
    void testIfGetAllBicyclesReturnsNoValuesIfDatabaseIsEmpty() {
        int expected = 0;
        when(rbc.getAllBicycles()).thenReturn(idBicyclesNoData);
        int result = idBicyclesNoData.size();
        assertEquals(result, expected);
    }
}
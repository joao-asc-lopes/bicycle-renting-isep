package lapr.project.controller.admin;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.BicycleFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class CreateBicycleControllerTest {

    @InjectMocks
    private CreateBicycleController cbc;

    @Mock
    private BicycleFacade fc;

    @BeforeEach
    public void setUp() {
        fc = mock(BicycleFacade.class);
        initMocks(this);
    }

    @Test
    void testIfCreateElectricBicycleReturnsTrueIfBicycleDoesntExist() {
        boolean expected = true;
        when(fc.createElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,  1, 1, 1,10,5.0,5.0)).thenReturn(true);
        boolean result = cbc.createElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,  1, 1, 1,10,5.0,5.0);
        assertEquals(expected, result);
    }

    @Test
    void testIfCreateElectricBicycleReturnsFalseIfBicycleExists() {
        boolean expected = false;
        when(fc.createElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,  1, 1, 1,10,5.0,5.0)).thenReturn(false);
        boolean result = cbc.createElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,  1, 1, 1,10,5.0,5.0);
        assertEquals(expected, result);
    }

    @Test
    void testIfCreateMountainBicycleReturnsTrueIfBicycleDoesntExist() {
        boolean expected = true;
        when(fc.createMountainBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0)).thenReturn(true);
        boolean result = cbc.createMountainBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        assertEquals(expected, result);
    }

    @Test
    void testIfCreateMountainBicycleReturnsFalseIfBicycleExists() {
        boolean expected = false;
        when(fc.createMountainBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0)).thenReturn(false);
        boolean result = cbc.createMountainBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        assertEquals(expected, result);
    }


    @Test
    void testIfCreateRoadBicycleReturnsTrueIfBicycleDoesntExist() {
        boolean expected = true;
        when(fc.createRoadBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0)).thenReturn(true);
        boolean result = cbc.createRoadBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        assertEquals(expected, result);
    }

    @Test
    void testIfCreateRoadBicycleReturnsFalseIfBicycleExists() {
        boolean expected = false;
        when(fc.createRoadBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0)).thenReturn(false);
        boolean result = cbc.createRoadBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        assertEquals(expected, result);
    }
}
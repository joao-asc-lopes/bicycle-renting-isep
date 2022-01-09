package lapr.project.model.bicycle;

import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class BicycleFacadeTest {

    private Battery bat;

    @InjectMocks
    private BicycleFacade bikeFac;
    @Mock
    private BicycleService bikeS;

    private ArrayList<Bicycle> allBikes;
    private ArrayList<RoadBicycle> roadBikes;
    private ArrayList<ElectricBicycle> electricBikes;
    private ArrayList<MountainBicycle> mountainBikes;
    private ArrayList<RoadBicycle> noRoadBikeData;
    private ArrayList<ElectricBicycle> noElectricBikeData;
    private ArrayList<MountainBicycle> noMountainBikeData;

    @BeforeEach
    public void setUp() {
        roadBikes = new ArrayList<>();
        electricBikes = new ArrayList<>();
        mountainBikes = new ArrayList<>();
        noRoadBikeData = new ArrayList<>();
        noElectricBikeData = new ArrayList<>();
        noMountainBikeData = new ArrayList<>();
        allBikes = new ArrayList<>();
        bat = new Battery(1, 200, 200, 1);

        RoadBicycle rBike1 = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        RoadBicycle rBike2 = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        RoadBicycle rBike3 = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        roadBikes.add(rBike1);
        roadBikes.add(rBike2);
        roadBikes.add(rBike3);

        MountainBicycle mBike1 = new MountainBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        MountainBicycle mBike2 = new MountainBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        MountainBicycle mBike3 = new MountainBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        mountainBikes.add(mBike1);
        mountainBikes.add(mBike2);
        mountainBikes.add(mBike3);

        ElectricBicycle eBike1 = new ElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, bat, 1,10,5.0);
        ElectricBicycle eBike2 = new ElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, bat, 1,10,5.0);
        ElectricBicycle eBike3 = new ElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, bat, 1,10,5.0);
        electricBikes.add(eBike1);
        electricBikes.add(eBike2);
        electricBikes.add(eBike3);


        bikeS = Mockito.mock(BicycleService.class);
        initMocks(this);
    }


    @Test
    void testIfRegisterRoadBicycleReturnsTrueIfBicycleDoesntExist() {
        boolean expected = true;
        RoadBicycle bike = new RoadBicycle("IDteste", Bicycle.statusByCode(2), 1,10,5.00);
        when(bikeS.createNonElectricBicycle("IDteste",Bicycle.BicycleStatus.IN_USE, 1, Bicycle.BicycleType.ROAD,10,5.0)).thenReturn(expected);
        boolean result = bikeFac.createRoadBicycle("IDteste",Bicycle.BicycleStatus.IN_USE, 1,10,5.0);
        assertEquals(result, expected);
    }

    @Test
    void testIfRegisterRoadBicycleReturnsFalseIfBicycleExists() {
        boolean expected = false;
        RoadBicycle bike = new RoadBicycle("IDteste", Bicycle.statusByCode(2), 1,10,5.0);
        when(bikeS.createNonElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1, Bicycle.BicycleType.ROAD,10,5.0)).thenReturn(expected);
        boolean result = bikeFac.createRoadBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        assertEquals(result, expected);
    }

    @Test
    void testIfRegisterMountainBicycleReturnsTrueIfBicycleDoesntExist() {
        boolean expected = true;
        MountainBicycle bike = new MountainBicycle("IDteste", Bicycle.statusByCode(2), 1,10,5.0);
        when(bikeS.createNonElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1, Bicycle.BicycleType.ROAD,10,5.0)).thenReturn(expected);
        boolean result = bikeFac.createRoadBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        assertEquals(result, expected);
    }

    @Test
    void testIfRegisterMountainBicycleReturnsFalseIfBicycleExists() {
        boolean expected = false;
        MountainBicycle bike = new MountainBicycle("IDteste", Bicycle.statusByCode(2), 1,10,5.0);
        when(bikeS.createNonElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1, Bicycle.BicycleType.MOUNTAIN,10,5.0)).thenReturn(expected);
        boolean result = bikeFac.createMountainBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        assertEquals(result, expected);
    }

    @Test
    void testIfRegisterEletricalBicycleReturnsTrueIfBicycleDoesntExist() {
        boolean expected = true;
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), bat, 1,10,5.0);
        when(bikeS.createElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,  1, 1, 1, 1,10,5.0)).thenReturn(expected);
        boolean result = bikeFac.createElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,  1, 1, 1, 1,10,5.0);
        assertEquals(result, expected);
    }

    @Test
    public void testGettingAllBicyclesReturnsCorrectValues() {
        when(bikeS.getAllBicycles()).thenReturn(allBikes);
        List<Bicycle> result = this.bikeFac.getAllBicycles();
        assertEquals(allBikes, result);
    }

    @Test
    void testIfRegisterEletricalBicycleReturnsFalseIfBicycleExists() {
        boolean expected = false;
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), bat, 1,10,5.0);
        when(bikeS.createElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,  1, 1, 1, 1,10,5.0)).thenReturn(expected);
        boolean result = bikeFac.createElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1, 1, 1, 1,10,5.0);
        assertEquals(result, expected);
    }

    @Test
    void testIfRemoveBicycleReturnsTrueIfBicycleExists() {
        boolean expected = true;
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), bat, 1,10,5.0);
        when(bikeS.delete(1)).thenReturn(expected);
        boolean result = bikeFac.removeBicycle(1);
        assertEquals(expected, result);
    }

    @Test
    void testIfRemoveBicycleReturnsFalseIfBicycleDoesntExists() {
        boolean expected = false;
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), bat, 1,10,5.0);
        when(bikeS.delete(1)).thenReturn(expected);
        boolean result = bikeFac.removeBicycle(1);
        assertEquals(expected, result);
    }

    @Test
    void testIfUpdateBicycleReturnsTrueIfBicycleExists() {
        boolean expected = true;
        RoadBicycle bike = new RoadBicycle("IDteste", Bicycle.statusByCode(2), 1,10,5.0);
        when(bikeS.updateBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10)).thenReturn(expected);
        boolean result = bikeFac.updateNonElectricalBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10);
        assertEquals(expected, result);
    }

    @Test
    void testIfUpdateBicycleReturnsFalseIfBicycleDoesntExists() {
        boolean expected = false;
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), bat, 1,10,5.0);
        when(bikeS.updateElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,150f, 1,10)).thenReturn(expected);
        boolean result = bikeFac.updateElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 150f,1,10);
        assertEquals(expected, result);
    }

    @Test
    void testIfGetAllRoadBicyclesReturnsValuesIfDatabaseHasBicycles() {
        int expected = 3;
        when(bikeS.getRoadBicyclesList()).thenReturn(roadBikes);
        int result = bikeFac.getRoadBicycleList().size();
        assertEquals(result, expected);
    }

    @Test
    void testIfGetAllRoadBicyclesReturnsNoValuesIfDatabaseIsEmpty() {
        int expected = 0;
        when(bikeS.getRoadBicyclesList()).thenReturn(noRoadBikeData);
        int result = bikeFac.getRoadBicycleList().size();
        assertEquals(result, expected);
    }

    @Test
    void testIfGetAllMountainBicyclesReturnsValuesIfDatabaseHasBicycles() {
        int expected = 3;
        when(bikeS.getMountainBicyclesList()).thenReturn(mountainBikes);
        int result = bikeFac.getMountainBicycleList().size();
        assertEquals(result, expected);
    }

    @Test
    void testIfGetAllMountainBicyclesReturnsNoValuesIfDatabaseIsEmpty() {
        int expected = 0;
        when(bikeS.getMountainBicyclesList()).thenReturn(noMountainBikeData);
        int result = bikeFac.getMountainBicycleList().size();
        assertEquals(result, expected);
    }

    @Test
    void testIfGetAllElectricalBicyclesReturnsValuesIfDatabaseHasBicycles() {
        int expected = 3;
        when(bikeS.getElectricBicyclesList()).thenReturn(electricBikes);
        int result = bikeFac.getElectricBicyclesList().size();
        assertEquals(result, expected);
    }

    @Test
    void testIfGetAllElectricalBicyclesReturnsNoValuesIfDatabaseIsEmpty() {
        int expected = 0;
        when(bikeS.getElectricBicyclesList()).thenReturn(noElectricBikeData);
        int result = bikeFac.getElectricBicyclesList().size();
        assertEquals(result, expected);
    }

    @Test
    public void ensureUnlockMountainBicycleThrowsExceptionWhenBicycleIsNotAvailable() {
        doThrow(new InvalidDataException("Bicycle is no longer available")).when(bikeS).unlockBicycle("IDteste", Bicycle.BicycleType.MOUNTAIN);
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            bikeFac.unlockBicycle("IDteste", Bicycle.BicycleType.MOUNTAIN);
        });
        assertEquals("Bicycle is no longer available", e.getMessage());
    }

    @Test
    public void ensureUnlockRoadBicycleThrowsExceptionWhenBicycleIsNotAvailable() {
        doThrow(new InvalidDataException("Bicycle is no longer available")).when(bikeS).unlockBicycle("IDteste", Bicycle.BicycleType.ROAD);
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            bikeFac.unlockBicycle("IDteste", Bicycle.BicycleType.ROAD);
        });
        assertEquals("Bicycle is no longer available", e.getMessage());
    }

    @Test
    public void ensureUnlockElectricBicycleThrowsExceptionWhenBicycleIsNotAvailable() {
        doThrow(new InvalidDataException("Bicycle is no longer available")).when(bikeS).unlockBicycle("IDteste", Bicycle.BicycleType.ELECTRIC);
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            bikeFac.unlockBicycle("IDteste", Bicycle.BicycleType.ELECTRIC);
        });
        assertEquals("Bicycle is no longer available", e.getMessage());
    }

    @Test
    public void ensureUnlockMountainBicycleDoesNotThrowExceptionWhenBicycleIsAvailable() {
        doNothing().when(bikeS).unlockBicycle("IDteste", Bicycle.BicycleType.MOUNTAIN);
        assertDoesNotThrow(() -> {
            bikeFac.unlockBicycle("IDteste", Bicycle.BicycleType.MOUNTAIN);
        });
    }

    @Test
    public void ensureUnlockRoadBicycleDoesNotThrowExceptionWhenBicycleIsAvailable() {
        doNothing().when(bikeS).unlockBicycle("IDteste", Bicycle.BicycleType.ROAD);
        assertDoesNotThrow(() -> {
            bikeFac.unlockBicycle("IDteste", Bicycle.BicycleType.ROAD);
        });
    }

    @Test
    public void ensureUnlockElectricBicycleDoesNotThrowExceptionWhenBicycleIsAvailable() {
        doNothing().when(bikeS).unlockBicycle("IDteste", Bicycle.BicycleType.ELECTRIC);
        assertDoesNotThrow(() -> {
            bikeFac.unlockBicycle("IDteste", Bicycle.BicycleType.ELECTRIC);
        });
    }

}

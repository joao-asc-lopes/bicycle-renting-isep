package lapr.project.model.bicycle;

import lapr.project.data.BatteryDao;
import lapr.project.data.BicycleDao;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class BicycleServiceTest {

    @InjectMocks
    private BicycleService bikeS;

    @Mock
    private BicycleDao mockBikeD;

    @Mock
    private BatteryDao batteryDao;

    private Battery bat;

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
        mockBikeD = Mockito.mock(BicycleDao.class);
        batteryDao = Mockito.mock(BatteryDao.class);
        initMocks(this);
    }


    @Test
    void testIfRegisterRoadBicycleReturnsTrueIfBicycleDoesntExist() {
        boolean expected = true;
        RoadBicycle bike = new RoadBicycle("IDteste", Bicycle.statusByCode(2), 1,10,5.0);
        //when(mockBikeD.bicycleExists(1)).thenReturn(false);
        when(mockBikeD.addNonElectricBicycle(new RoadBicycle("IDteste",Bicycle.BicycleStatus.IN_USE, 1,10,5.0))).thenReturn(expected);
        boolean result = bikeS.createNonElectricBicycle("IDteste",Bicycle.BicycleStatus.IN_USE, 1, Bicycle.BicycleType.ROAD,10,5.0);
        assertEquals(result, expected);
    }

    @Test
    void testIfRegisterRoadBicycleReturnsFalseIfBicycleExists() {
        boolean expected = false;
        RoadBicycle bike = new RoadBicycle("IDteste", Bicycle.statusByCode(2), 1,10,5.0);
        when(mockBikeD.addNonElectricBicycle(new RoadBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0))).thenReturn(expected);
        boolean result = bikeS.createNonElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1, Bicycle.BicycleType.ROAD,10,5.0);
        assertEquals(result, expected);
    }

    @Test
    void testIfRegisterMountainBicycleReturnsTrueIfBicycleDoesntExist() {
        boolean expected = true;
        MountainBicycle bike = new MountainBicycle("IDteste", Bicycle.statusByCode(2), 1,10,5.0);
        when(mockBikeD.addNonElectricBicycle(new RoadBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1, 10,5.0))).thenReturn(expected);
        boolean result = bikeS.createNonElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1, Bicycle.BicycleType.ROAD,10,5.0);
        assertEquals(result, expected);
    }

    @Test
    void testIfRegisterMountainBicycleReturnsFalseIfBicycleExists() {
        boolean expected = false;
        MountainBicycle bike = new MountainBicycle("IDteste", Bicycle.statusByCode(2), 1,10,5.0);
        when(mockBikeD.addNonElectricBicycle(new MountainBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1, 10,5.0))).thenReturn(expected);
        boolean result = bikeS.createNonElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1, Bicycle.BicycleType.MOUNTAIN,10,5.0);
        assertEquals(result, expected);
    }

    @Test
    void testIfRemoveBicycleReturnsTrueIfBicycleExists() {
        boolean expected = true;
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), bat, 1,10,5.0);
        when(mockBikeD.removeBicycle(1)).thenReturn(expected);
        boolean result = bikeS.delete(1);
        assertEquals(expected, result);
    }

    @Test
    void testIfRemoveBicycleReturnsFalseIfBicycleDoesntExists() {
        boolean expected = false;
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), bat, 1,10,5.0);
        when(mockBikeD.removeBicycle(1)).thenReturn(expected);
        boolean result = bikeS.delete(1);
        assertEquals(expected, result);
    }

    @Test
    void testIfUpdateBicycleReturnsTrueIfBicycleExists() {
        boolean expected = true;
        RoadBicycle road = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        when(mockBikeD.getBicycle("IDteste")).thenReturn(road);
        when(mockBikeD.updateNonElectricBicycle(road)).thenReturn(expected);
        boolean result = bikeS.updateBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10);
        assertEquals(expected, result);
    }

    @Test
    void testIfGetAllRoadBicyclesReturnsValuesIfDatabaseHasBicycles() {
        int expected = 3;
        when(mockBikeD.getRoadBicyclesList()).thenReturn(roadBikes);
        int result = bikeS.getRoadBicyclesList().size();
        assertEquals(result, expected);
    }

    @Test
    void testIfGetAllRoadBicyclesReturnsNoValuesIfDatabaseIsEmpty() {
        int expected = 0;
        when(mockBikeD.getRoadBicyclesList()).thenReturn(noRoadBikeData);
        int result = bikeS.getRoadBicyclesList().size();
        assertEquals(result, expected);
    }

    @Test
    void testIfGetAllMountainBicyclesReturnsValuesIfDatabaseHasBicycles() {
        int expected = 3;
        when(mockBikeD.getMountainBicyclesList()).thenReturn(mountainBikes);
        int result = bikeS.getMountainBicyclesList().size();
        assertEquals(result, expected);
    }

    @Test
    void testIfGetAllMountainBicyclesReturnsNoValuesIfDatabaseIsEmpty() {
        int expected = 0;
        when(mockBikeD.getMountainBicyclesList()).thenReturn(noMountainBikeData);
        int result = bikeS.getMountainBicyclesList().size();
        assertEquals(result, expected);
    }

    @Test
    void testIfGetAllElectricalBicyclesReturnsValuesIfDatabaseHasBicycles() {
        int expected = 3;
        when(mockBikeD.getElectricBicyclesList()).thenReturn(electricBikes);
        int result = bikeS.getElectricBicyclesList().size();
        assertEquals(result, expected);
    }

    @Test
    void testIfGetAllElectricalBicyclesReturnsNoValuesIfDatabaseIsEmpty() {
        int expected = 0;
        when(mockBikeD.getElectricBicyclesList()).thenReturn(noElectricBikeData);
        int result = bikeS.getElectricBicyclesList().size();
        assertEquals(result, expected);
    }

    @Test
    public void ensureUnlockMountainBicycleThrowsExceptionWhenBicycleIsNotAvailable() {
        MountainBicycle b = new MountainBicycle("IDteste", Bicycle.BicycleStatus.IN_USE, 1,10,5.0);
        when(mockBikeD.getMountainBicycle("IDteste")).thenReturn(b);
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            bikeS.unlockBicycle("IDteste", Bicycle.BicycleType.MOUNTAIN);
        });
        assertEquals("Bicycle is no longer available", e.getMessage());
    }

    @Test
    public void ensureUnlockRoadBicycleThrowsExceptionWhenBicycleIsNotAvailable() {
        RoadBicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.IN_USE, 1,1,5.0);
        when(mockBikeD.getRoadBicycle("IDteste")).thenReturn(b);
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            bikeS.unlockBicycle("IDteste", Bicycle.BicycleType.ROAD);
        });
        assertEquals("Bicycle is no longer available", e.getMessage());
    }

    @Test
    public void ensureUnlockElectricBicycleThrowsExceptionWhenBicycleIsNotAvailable() {
        ElectricBicycle b = new ElectricBicycle("IDteste", Bicycle.BicycleStatus.IN_USE, new Battery(1, 1, 1, 1), 1,10,5.0);
        when(mockBikeD.getElectricBicycle("IDteste")).thenReturn(b);
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            bikeS.unlockBicycle("IDteste", Bicycle.BicycleType.ELECTRIC);
        });
        assertEquals("Bicycle is no longer available", e.getMessage());
    }

    @Test
    public void ensureUnlockMountainBicycleDoesNotThrowExceptionWhenBicycleIsAvailable() {
        MountainBicycle b = new MountainBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        when(mockBikeD.getMountainBicycle("IDteste")).thenReturn(b);
        MountainBicycle c = new MountainBicycle("IDteste", Bicycle.BicycleStatus.IN_USE, 1,10,5.0);
        when(mockBikeD.updateNonElectricBicycle(c)).thenReturn(true);
        bikeS.unlockBicycle("IDteste", Bicycle.BicycleType.MOUNTAIN);
    }

    @Test
    public void ensureUnlockRoadBicycleDoesNotThrowExceptionWhenBicycleIsAvailable() {
        RoadBicycle b = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        when(mockBikeD.getRoadBicycle("IDteste")).thenReturn(b);
        RoadBicycle c = new RoadBicycle("IDteste", Bicycle.BicycleStatus.IN_USE, 1,10,5.0);
        when(mockBikeD.updateNonElectricBicycle(c)).thenReturn(true);
        bikeS.unlockBicycle("IDteste", Bicycle.BicycleType.ROAD);
    }

    @Test
    public void ensureUnlockElectricBicycleDoesNotThrowExceptionWhenBicycleIsAvailable() {
        ElectricBicycle b = new ElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, new Battery(1, 1, 1, 1), 1,10,5.0);
        when(mockBikeD.getElectricBicycle("IDteste")).thenReturn(b);
        ElectricBicycle c = new ElectricBicycle("IDteste", Bicycle.BicycleStatus.IN_USE, new Battery(1, 1, 1, 1), 1,10,5.0);
        when(mockBikeD.updateNonElectricBicycle(c)).thenReturn(true);
        bikeS.unlockBicycle("IDteste", Bicycle.BicycleType.ELECTRIC);
    }

    @Test
    void testIfRegisterEletricalBicycleReturnsTrueIfBicycleDoesntExist() {
        boolean expected = true;
        Battery bat = new Battery(1,1,1,1);
        when(mockBikeD.addElectricBicycle(new ElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, bat, 1,10,5.0))).thenReturn(expected);
        boolean result = bikeS.createElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, 1,  1, 1, 1,10,5.0);
        assertEquals(result, expected);
    }

    @Test
    public void testGettingAllBicyclesReturnsCorrectValues() {
        when(mockBikeD.getAllBicycles()).thenReturn(allBikes);
        List<Bicycle> result = this.bikeS.getAllBicycles();
        assertEquals(allBikes, result);
    }

    @Test
    void testIfUpdateBicycleReturnsFalseIfBicycleDoesntExists() {
        boolean expected = false;
        ElectricBicycle bike = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), bat, 1,10,5.0);
        when(mockBikeD.getElectricBicycle("IDteste")).thenReturn(bike);
        when(batteryDao.getBattery(1)).thenReturn(bat);
        when(mockBikeD.updateElectricBicycle(bike)).thenReturn(expected);
        boolean result = bikeS.updateElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 150f,1,10);
        assertEquals(expected, result);
    }

    @Test
    void testIfRegisterEletricalBicycleReturnsTrueIfBicycleExists() {
        boolean expected = true;
        Battery bat = new Battery(1,1,1,1);
        when(mockBikeD.addElectricBicycle(new ElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE, bat, 1,10,5.0))).thenReturn(expected);
        boolean result = bikeS.createElectricBicycle("IDteste",Bicycle.BicycleStatus.AVAILABLE,  1, 1, 1, 1,10,5.0);
        assertEquals(result, expected);
    }

    @Test
    public void testIfGetElectricBicycleReturnsCorrectBicycle() {
        ElectricBicycle expected = new ElectricBicycle("IDteste", Bicycle.statusByCode(2), bat, 1,10,5.0);
        when(mockBikeD.getElectricBicycle("IDteste")).thenReturn(expected);
        ElectricBicycle result = this.bikeS.getElectricBicycle("IDteste");
        assertEquals(expected, result);
    }

    @Test
    public void testIfGetRoadBicycleReturnsCorrectBicycle() {
        RoadBicycle expected = new RoadBicycle("IDteste", Bicycle.statusByCode(2), 1,10,5.0);
        when(mockBikeD.getRoadBicycle("IDteste")).thenReturn(expected);
        RoadBicycle result = this.bikeS.getRoadBicycle("IDteste");
        assertEquals(expected, result);
    }

    @Test
    public void testIfGetMountainBicycleReturnsCorrectBicycle() {
        MountainBicycle expected = new MountainBicycle("IDteste", Bicycle.statusByCode(2), 1,10,5.0);
        when(mockBikeD.getMountainBicycle("IDteste")).thenReturn(expected);
        MountainBicycle result = this.bikeS.getMountainBicycle("IDteste");
        assertEquals(expected, result);
    }


}
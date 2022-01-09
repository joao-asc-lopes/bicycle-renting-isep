package lapr.project.model.park;

import lapr.project.model.bicycle.*;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LocationFacadeTest {

    @InjectMocks
    private LocationFacade pf;
    @Mock
    private LocationService mockParkS;
    /**
     * Variable representatitve of an existing id park.
     */
    private int idPark;
    /**
     * Variable representative of an id park that just does not exist.
     */
    private int nonExistentIdPark;

    /**
     * Variable that represents a valid instance of MountainBicycle.
     */
    private MountainBicycle bike;
    /**
     * Variable that represents a valid isntance of RoadBicycle.
     */

    private RoadBicycle bike2;
    /**
     * Variable that represents a valid instance of ElectricBicycle.
     */

    private ElectricBicycle bike3;
    /**
     * Represents a vlalid instance of Battery.
     */
    private Battery bat;
    private InterestPoint ip2;


    @BeforeEach
    public void setUp() {
        this.idPark = 1;
        this.nonExistentIdPark = 10000;
        mockParkS = mock(LocationService.class);
        pf = new LocationFacade();
        initMocks(this);

        bike = new MountainBicycle("IDteste", Bicycle.statusByCode(1), 1,10,5.0);
        bike2 =  new RoadBicycle("IDteste", Bicycle.statusByCode(1), 1,10,5.0);
        bat = new Battery(1,200, 200, 1);



        bike3 = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), bat, 1,10,5.0);

         ip2 = new InterestPoint(2, "123", 10, 10, 1.0);


    }
    @Test
    void testIfGetAvailableBicyclesReturnsEmptyListIfThereAreNoParkedBicycles() {
        ArrayList<Bicycle> expected = new ArrayList<>();
        when(mockParkS.getParkedBicycles(1)).thenReturn(expected);
        List<Bicycle> result2 = pf.getAvailableBicycles(1, Bicycle.BicycleType.ROAD);
        assertEquals(expected, result2);
    }

    @Test
    void testIfGetAvailableBicyclesReturnsEmptyListIfThereAreNoParkedBicyclesOfThisType() {
        List<Bicycle> expected = new ArrayList<>();
        List<Bicycle> withBicycles = new ArrayList<>();
        withBicycles.add(new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0));
        when(pf.getAvailableBicycles(1, Bicycle.BicycleType.ELECTRIC)).thenReturn(withBicycles);
        when(mockParkS.getParkedBicycles(1)).thenReturn(new ArrayList<>());
        List<Bicycle> result = pf.getAvailableBicycles(1, Bicycle.BicycleType.ROAD);
        assertEquals(expected, result);
    }

    @Test
    void testIfGetAvailableBicyclesReturnsListWithBicyclesIfThereAreParkedBicyclesOfThisType() {
        List<Bicycle> expected = new ArrayList<>();

        ArrayList<Bicycle> input = new ArrayList<>();
        input.add(new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0));
        input.add(new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0));
        input.add(new RoadBicycle("IDteste", Bicycle.BicycleStatus.IN_USE, 1,10,5.0));
        input.add(new MountainBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0));
        expected.clear();
        expected.add(new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0));
        expected.add(new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0));
        when(mockParkS.getParkedBicycles(1)).thenReturn(input);
        List<Bicycle> result2 = pf.getAvailableBicycles(1, Bicycle.BicycleType.ROAD);
        assertEquals(expected, result2);
    }

    @Test
    public void ensureGetParkCorrectIdReturnsCorrectPark() {
        Park expected = new Park(1,"Rua do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);
        when(mockParkS.getPark(idPark)).thenReturn(expected);
        Park result = pf.getParkById(idPark);
        assertEquals(expected, result);
    }

    @Test
    public void ensureGetInterestPointIdReturnCorrectInterestPoint(){
        InterestPoint expected = new InterestPoint(2,"Rua do Dani",11,10,1.0);
        when(mockParkS.getInterestPoint(2)).thenReturn(expected);
        InterestPoint result = pf.getInterestPointById(2);
        assertEquals(expected,result);
    }

    @Test
    public void ensureGetLocationByCoordinatesReturnCorrectLocation(){
        Park expected = new Park(1,"Rua do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);
        when(mockParkS.getLocationByCoordinates(10,10)).thenReturn(expected);
        Location result = pf.getLocationByCoordinates(10,10);
        assertEquals(expected,result);
    }

    @Test
    public void testGetParkIdBicycleLocked(){
        Park expected = new Park(1,"Rua do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);
        when(mockParkS.getParkByIdBicycle("Teste")).thenReturn(expected);
        Park result = pf.getParkIdBicycleLocked("Teste");
        assertEquals(expected,result);
    }


    @Test
    public void ensureRemoveParkWithIncorrectIdReturnsFalse() {
        boolean expFlag = false;
        when(mockParkS.removePark(this.nonExistentIdPark)).thenReturn(false);
        boolean result = pf.removePark(nonExistentIdPark);
        assertEquals(expFlag, result);

    }

    @Test
    public void ensureRemoveInterestPointithCorrectIdReturnsTrue() {
        boolean expFlag = true;
        when(this.mockParkS.removeInterestPoint(1)).thenReturn(true);
        boolean result = pf.removeInterestPoint(1);
        assertEquals(expFlag, result);
    }

    @Test
    public void ensureRemoveInterestPointWithIncorrectIdReturnsFalse() {
        boolean expFlag = false;
        when(this.mockParkS.removeInterestPoint(100001)).thenReturn(false);
        boolean result = pf.removeInterestPoint(100001);
        assertEquals(expFlag, result);
    }

    @Test
    public void ensureRemoveParkWithCorrectIdReturnsTrue() {
        boolean expFlag = true;
        when(mockParkS.removePark(this.idPark)).thenReturn(expFlag);
        boolean result = pf.removePark(this.idPark);
        assertEquals(expFlag, result);
    }

    @Test
    public void ensureAddParkDoesNotExistReturnsTrue() {
        boolean expected = true;
        when(mockParkS.addPark("Parque do Dani",40,-8,10,10,10, 10,220,50,-4224.0)).thenReturn(expected);
        boolean result = pf.registerPark("Parque do Dani",40,-8,10,10,10, 10,220,50,-4224.0);
        assertEquals(expected, result);

    }

    @Test
    public void ensureRegisterParkReturnsFalseWithNullName() {

        boolean expFlag = false;
        when(mockParkS.addPark(null,40,-8,10,10,10, 10,220,50,615.0)).thenReturn(false);
        boolean result = this.pf.registerPark(null,40,-8,10,10,10, 10,220,50,615.0);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureRegisterParkReturnsFalseWithEmptyName() {

        boolean expFlag = false;
        when(mockParkS.addPark("",40,-8,10,10,10, 10,220,50,615.0)).thenReturn(false);
        boolean result = this.pf.registerPark("",40,-8,10,10,10, 10,220,50,615.0);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureRegisterParkReturnsFalseWithNullAddress() {

        boolean expFlag = false;
        when(mockParkS.addPark("Parque do Dani",40,-8,10,10,10, 10,220,50,615.0)).thenReturn(false);
        boolean result = this.pf.registerPark("Parque do Dani",40,-8,10,10,10, 10,220,50,615.0);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureRegisterParkReturnsFalseWithEmptyAddress() {

        boolean expFlag = false;
        when(mockParkS.addPark("Parque do Dani",40,-8,10,10,10, 10,220,50,615.0)).thenReturn(false);
        boolean result = this.pf.registerPark("Parque do Dani",40,-8,10,10,10, 10,220,50,615.0);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureRegisterParkReturnsFalseWithLowerThanPermittedLatitude() {
        InvalidDataException expFlag = new InvalidDataException();
        String result = "";
        when(mockParkS.addPark("Parque do Dani",-98,-8,10,10,10, 10,220,50,1.0)).thenThrow(expFlag);

        try {

            this.pf.registerPark("Parque do Dani", -98, -8, 10, 10, 10, 10,220,50,1.0);
        }
        catch(InvalidDataException e){
            result = e.getMessage();
        }
        assertEquals(expFlag.getMessage(),result);

    }

    @Test
    public void ensureRegisterParkReturnsFalseWithHigherThanPermittedLatitude() {
        InvalidDataException expFlag = new InvalidDataException();
        String result = "";
        when(mockParkS.addPark("Parque do Dani",98,-8,10,10,10, 10,220,50,1.0)).thenThrow(expFlag);

        try {

            this.pf.registerPark("Parque do Dani", 98, -8, 10, 10, 10, 10,220,50,1.0);
        }
        catch(InvalidDataException e){
            result = e.getMessage();
        }
        assertEquals(expFlag.getMessage(),result);

    }

    @Test
    public void ensureRegisterParkReturnsFalseWithLowerThanPermittedLongitude() {
        InvalidDataException expFlag = new InvalidDataException();
        String result = "";
        when(mockParkS.addPark("Parque do Dani",40,-190,10,10,10, 10,220,50,1.0)).thenThrow(expFlag);

        try {

            this.pf.registerPark("Parque do Dani", 40, -190, 10, 10, 10, 10,220,50,1.0);
        }
        catch(InvalidDataException e){
            result = e.getMessage();
        }
        assertEquals(expFlag.getMessage(),result);

    }

    @Test
    public void ensureRegisterParkReturnsFalseWithHigherThanPermittedLongitude() {
        InvalidDataException expFlag = new InvalidDataException();
        String result = "";
        when(mockParkS.addPark("Parque do Dani",40,190,10,10,10, 10,220,50,1.0)).thenThrow(expFlag);

        try {

            this.pf.registerPark("Parque do Dani", 40, 190, 10, 10, 10, 10,220,50,1.0);
        }
        catch(InvalidDataException e){
            result = e.getMessage();
        }
        assertEquals(expFlag.getMessage(),result);

    }
    @Test
    public void ensureSetDataThrowsExceptionWhenElectricMaximumCapacityIsZero(){
        when(this.mockParkS.addPark("Park 1",100.0,100.0, 2,2,0,5,220,50,75.0)).thenThrow(new InvalidDataException("Maximum capacity must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.pf.registerPark("Park 1",100.0,100.0, 2,2,0,5,220,50,75.0);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());

    }
    @Test
    public void ensureSetDataThrowsExceptionWhenElectricMaximumCapacityIsNegative(){
        when(this.mockParkS.addPark("Park 1",100.0,100.0, 2,2,-3,5,220,50,75.0)).thenThrow(new InvalidDataException("Maximum capacity must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.pf.registerPark("Park 1",100.0,100.0, 2,2,-3,5,220,50,75.0);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());

    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNormalMaximumCapacityIsNegative(){
        when(this.mockParkS.addPark("Park 1",100.0,100.0, -2,2,10,5,220,50,75.0)).thenThrow(new InvalidDataException("Maximum capacity must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.pf.registerPark("Park 1",100.0,100.0, -2,2,10,5,220,50,75.0);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());

    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNormalMaximumCapacityIsZero(){
        when(this.mockParkS.addPark("Park 1",100.0,100.0, 0,2,10,5,220,50,75.0)).thenThrow(new InvalidDataException("Maximum capacity must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.pf.registerPark("Park 1",100.0,100.0, 0,2,10,5,220,50,75.0);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNumberFreeSlotsIsNegative(){
        when(this.mockParkS.addPark("Park 1",100.0,100.0, 5,-2,10,5,220,50,75.0)).thenThrow(new InvalidDataException("Number of free slots must not be negative"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.pf.registerPark("Park 1",100.0,100.0, 5,-2,10,5,220,50,75.0);
        });
        assertEquals("Number of free slots must not be negative", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNumberFreeSlotsIsBiggerThanMaximum(){
        when(this.mockParkS.addPark("Park 1",100.0,100.0, 5,7,10,5,220,50,75.0)).thenThrow(new InvalidDataException("Number of free slots must not be bigger than maximum capacity"));
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.pf.registerPark("Park 1",100.0,100.0, 5,7,10,5,220,50,75.0);
        });
        assertEquals("Number of free slots must not be bigger than maximum capacity", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionIfChargeRateIsNegative(){
        when(this.mockParkS.addPark("Park 1",100.0,100.0, 5,2,10,5,-220,50,75.0)).thenThrow(new InvalidDataException("Charge rate must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.pf.registerPark("Park 1",100.0,100.0, 5,2,10,5,-220,50,75.0);
        });
        assertEquals("Charge rate must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionIfChargeRateIsZero(){
        when(this.mockParkS.addPark("Park 1",100.0,100.0, 5,2,10,5,0,50,75.0)).thenThrow(new InvalidDataException("Charge rate must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.pf.registerPark("Park 1",100.0,100.0, 5,2,10,5,0,50,75.0);
        });
        assertEquals("Charge rate must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionIfIntensityIsNegative(){
        when(this.mockParkS.addPark("Park 1",100.0,100.0, 5,2,10,5,220,-50,75.0)).thenThrow(new InvalidDataException("Intensity must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.pf.registerPark("Park 1",100.0,100.0, 5,2,10,5,220,-50,75.0);
        });
        assertEquals("Intensity must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionIfIntensityIsZero(){
        when(this.mockParkS.addPark("Park 1",100.0,100.0, 5,2,10,5,220,0,75.0)).thenThrow(new InvalidDataException("Intensity must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.pf.registerPark("Park 1",100.0,100.0, 5,2,10,5,220,0,75.0);

        });
        assertEquals("Intensity must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureGetParkedBicyclesReturnNullIfParkIdIsWrong() {
        when(mockParkS.getParkedBicycles(1)).thenReturn(null);
        List<Bicycle> result = pf.getParkedBicycles(1);
        assertTrue(result == null);
    }

    @Test
    public void ensureGetParkedBicyclesReturnEmptyIfParkIsEmpty() {
        when(mockParkS.getParkedBicycles(1)).thenReturn(new ArrayList<>());
        List<Bicycle> result = pf.getParkedBicycles(1);
        assertTrue(result.isEmpty());
    }

    @Test
    public void ensureGetParkedBicyclesReturnBikesIfParkIdIsRight() {
        ArrayList<Bicycle> array = new ArrayList<>();
        array.add(new RoadBicycle("IDteste", Bicycle.statusByCode(1), 1,10,5.0));
        array.add(new MountainBicycle("IDteste", Bicycle.statusByCode(1), 1,10,5.0));
        array.add(new ElectricBicycle("IDteste", Bicycle.statusByCode(1), new Battery(1,200, 40, 1), 1,10,5.0));
        when(mockParkS.getParkedBicycles(1)).thenReturn(array);
        List<Bicycle> result = pf.getParkedBicycles(1);
        assertTrue(!array.isEmpty());
        assertTrue(array.size() == 3);
    }

    @Test
    public void ensureGetAllParksReturnsListWithAllParks() {

        ArrayList<Park> expected = new ArrayList<>();
        boolean flag = true;

        Park aux1 = new Park(1, "Parque do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);
        Park aux2 = new Park(2, "Parque do Alex", 11, 11, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);
        Park aux3 = new Park(3, "Parque do Leonardo", 12, 12, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);

        when(mockParkS.addPark("Parque do Dani", 40, -8, 10, 10, 10, 10,220,50,1.0)).thenReturn(flag);
        when(mockParkS.addPark("Parque do Alex", 50, -8, 10, 10, 10, 10,220,50, 1.0)).thenReturn(flag);
        when(mockParkS.addPark("Parque do Leonardo", 60, -8, 10, 10, 10, 10,220,50, 1.0)).thenReturn(flag);

        expected.add(aux1);
        expected.add(aux2);
        expected.add(aux3);

        when(mockParkS.getParkList()).thenReturn(expected);

        List<Park> result = pf.getParkList();

        assertEquals(expected, result);

    }

    @Test
    public void ensureParksListThrowsExceptionWithInvalidData() {

        InvalidDataException expFlag = new InvalidDataException();
        String result ="";

        when(mockParkS.getParkList()).thenThrow(expFlag);

        try{

            pf.getParkList();
        }

        catch(InvalidDataException e){
            result = e.getMessage();
        }
        assertEquals(expFlag.getMessage(),result);
    }

    @Test
    public void testBikeIntoParkIfParkDontHaveSpace() {
        when(mockParkS.bikeIntoPark("IDteste", 1)).thenReturn(false);
        boolean result = pf.bikeIntoPark("IDteste", 1);
        assertTrue(!result);
    }

    @Test
    public void testBikeIntoParkIfParkHaveSpace() {
        when(mockParkS.bikeIntoPark("IDteste", 1)).thenReturn(true);
        boolean result = pf.bikeIntoPark("IDteste", 1);
        assertTrue(result);
    }

    @Test
    public void ensureAddInterestPointAlreadyExistsThrowsExcepttion(){
        InterestPoint ip = new InterestPoint(this.idPark,"Ponto do dani", 10,10,1.0);
        boolean expected = false;
        when(this.mockParkS.addInterestPoint(ip.getName(), ip.getLatitude(), ip.getLongitude(), ip.getAltitude())).thenThrow(InvalidDataException.class);
        Exception e1 = assertThrows(InvalidDataException.class, () -> {
            this.pf.registerInterestPoint(ip.getName(), ip.getLatitude(), ip.getLongitude(), ip.getAltitude());
        });
    }

    @Test
    public void ensureAddInterestPointDoestNotExistsReturnsTrue(){
        InterestPoint ip = new InterestPoint("Ponto do dani", 10,10,1.0);
        when(this.mockParkS.addInterestPoint(ip.getName(), ip.getLatitude(), ip.getLongitude(), ip.getAltitude())).thenReturn(ip);
        InterestPoint result = this.pf.registerInterestPoint("Ponto do dani", 10,10,1.0);
        assertEquals(ip,result);
    }





    @Test
    public void ensureUpdateParkUpdatesExistingPark() {
        boolean expFlag = true;
        when(mockParkS.updatePark(1,"Parque do Dani",20,10,20, 10)).thenReturn(true);
        boolean result = this.pf.updatePark(1,"Parque do Dani",20,10,20, 10);
        assertEquals(expFlag,result);

    }

    @Test
    public void ensureUpdateParkReturnsFalseIfParkDoesntExist() {
        boolean expFlag = false;
        when(mockParkS.updatePark(-21,"Parque do Dani",20,10,20, 10)).thenReturn(false);
        boolean result = this.pf.updatePark(-21,"Parque do Dani",20,10,20, 10);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureUpdateParkReturnsFalseWithNullName() {

        boolean expFlag = false;
        when(mockParkS.updatePark(1,"Parque do Dani",20,10,20, 10)).thenReturn(false);
        boolean result = this.pf.updatePark(1,null,20,10,20, 10);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureUpdateParkReturnsFalseWithEmptyName() {

        boolean expFlag = false;
        when(mockParkS.updatePark(1,"",20,10,20, 10)).thenReturn(false);
        boolean result = this.pf.updatePark(1,"",20,10,20, 10);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureUpdateParkReturnsFalseWithHigherNumberFreeNormalSlots() {
        boolean expFlag = false;
        when(mockParkS.updatePark(1,"Parque do Dani",20,21,20, 10)).thenReturn(false);
        boolean result = this.pf.updatePark(1,"Parque do Dani",20,21,20, 10);
        assertEquals(expFlag,result);

    }

    @Test
    public void ensureUpdateParkReturnsFalseWithInvalidNumberFreeNormalSlots() {
        boolean expFlag = false;
        when(mockParkS.updatePark(1,"Parque do Dani",20,-1,20, 21)).thenReturn(false);
        boolean result = this.pf.updatePark(1,"Parque do Dani",20,-1,20, 21);
        assertEquals(expFlag,result);

    }

    @Test
    public void ensureUpdateParkReturnsFalseWithInvalidNumberFreeElectricalSlots() {
        boolean expFlag = false;
        when(mockParkS.updatePark(1,"Parque do Dani",20,10,20, 10)).thenReturn(false);
        boolean result = this.pf.updatePark(1,"Parque do Dani",20,10,20, -1);
        assertEquals(expFlag,result);

    }

    @Test
    public void ensureUpdateParkReturnsFalseWithInvalidNormalBicycleCapacity() {
        boolean expFlag = false;
        when(mockParkS.updatePark(1,"Parque do Dani",-1,10,20, 10)).thenReturn(false);
        boolean result = this.pf.updatePark(1,"Parque do Dani",-1,10,20, 10);
        assertEquals(expFlag,result);

    }

    @Test
    public void ensureUpdateParkReturnsFalseWithInvalidNormalElectricalCapacity() {
        boolean expFlag = false;
        when(mockParkS.updatePark(1,"Parque do Dani",20,10,-1, 10)).thenReturn(false);
        boolean result = this.pf.updatePark(1,"Parque do Dani",20,10,-1, 10);
        assertEquals(expFlag,result);

    }


    @Test
    public void ensureGetMountainBicyclesWorks(){
       ArrayList<MountainBicycle> eb = new ArrayList<>();
        eb.add(this.bike);
        when(this.mockParkS.getMountainBicyclesPark(this.idPark)).thenReturn(eb);
       List<MountainBicycle> result = this.pf.getMountainBicyclesPark(this.idPark);
        assertEquals(eb,result);

    }

    @Test
    public void ensureGetMountainBikesWorksWithNoBikes(){
        ArrayList<MountainBicycle> eb = new ArrayList<>();
        when(this.mockParkS.getMountainBicyclesPark(this.idPark)).thenReturn(eb);
        List<MountainBicycle> result = this.pf.getMountainBicyclesPark(this.idPark);
        assertEquals(eb,result);

    }

    @Test
    public void ensureGetRoadBicyclesWOrks(){
       ArrayList<RoadBicycle> eb1 = new ArrayList<>();
        eb1.add(this.bike2);
        when(this.mockParkS.getRoadBicyclesPark(this.idPark)).thenReturn(eb1);
       List<RoadBicycle> result = this.pf.getRoadBicyclesPark(this.idPark);
        assertEquals(eb1,result);

    }

    @Test
    public void esnureGetRoadBikesWorksWithNoRoadBikes(){
        ArrayList<RoadBicycle> eb1 = new ArrayList<>();
        when(this.mockParkS.getRoadBicyclesPark(this.idPark)).thenReturn(eb1);
        List<RoadBicycle> result = this.pf.getRoadBicyclesPark(this.idPark);
        assertEquals(eb1,result);
    }

    @Test
    public void ensureGetEletricBicyclesWorks(){
        ArrayList<ElectricBicycle> eb1 = new ArrayList<>();
        eb1.add(this.bike3);
        when(this.mockParkS.getEletricBicyclesPark(this.idPark)).thenReturn(eb1);
       List<ElectricBicycle> result = this.pf.getElectricBicyclesPark(this.idPark);
        assertEquals(eb1,result);

    }

    @Test
    public void ensureGetEletricBicyclesWorksWithNoBikes(){
        ArrayList<ElectricBicycle> eb1 = new ArrayList<>();
        when(this.mockParkS.getEletricBicyclesPark(this.idPark)).thenReturn(eb1);
        List<ElectricBicycle> result = this.pf.getElectricBicyclesPark(this.idPark);
        assertEquals(eb1,result);

    }

    @Test
    public void ensureRegisterIdentifierExistsReturnsTrue() {
        boolean expFlag = true;
        when(mockParkS.updateInterestPoint(1, "Daniel")).thenReturn(true);
        boolean result = this.pf.updateInterestPoint(1, "Daniel");
        assertEquals(expFlag, result);


    }

    @Test
    public void ensureRegisterIdentifierDoesNotExistReturnsFalse() {
        boolean expFlag = false;
        when(mockParkS.updateInterestPoint(1, "Daniel")).thenReturn(false);
        boolean result = this.pf.updateInterestPoint(2, "Das");
        assertEquals(expFlag, result);
    }

    @Test
    public void ensureTestGetAllInterestPointsWorks(){
       ArrayList<InterestPoint> ipList = new ArrayList<>();
       ipList.add(this.ip2);
       when(this.mockParkS.getAllInterestPoints()).thenReturn(ipList);
       List<InterestPoint> result = this.pf.getAllInterestPoints();
       assertEquals(result,ipList);
    }

    @Test
    public void ensureTestGetAllInterestPointsWithNoInterestPoints(){
        ArrayList<InterestPoint> ipList = new ArrayList<>();
        when(this.mockParkS.getAllInterestPoints()).thenReturn(ipList);
        List<InterestPoint> result = this.pf.getAllInterestPoints();
        assertEquals(result,ipList);
    }


}

package lapr.project.model.park;


import lapr.project.data.LocationDao;
import lapr.project.model.bicycle.*;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LocationServiceTest {

    @InjectMocks
    private LocationService ps;

    /**
     * Connection to the database.
     */
    @Mock
    private LocationDao mockLocationDao;

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

    private Park park;

    private Park p2;

   private Park p3;

   private InterestPoint p1;




    @BeforeEach
    public void setUp() {
        this.idPark = 1;
        this.nonExistentIdPark = 10000;
        this.mockLocationDao = Mockito.mock(LocationDao.class);
        initMocks(this);
        bike = new MountainBicycle("IDteste", Bicycle.statusByCode(1), 1,10,5.0);
       bike2 =  new RoadBicycle("IDteste", Bicycle.statusByCode(1), 1,10,5.0);
        bat = new Battery(1, 200, 200, 1);


        p1 = new InterestPoint(34, "Dani", 12,12, 1.0);

        bike3 = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), bat, 1,10,5.0);

        p2 = new Park(2, "Dani", 10000000, 10, new NormalSlots(1,1,1), new ElectricSlot(1,1,1,220,50), 1.0);
        p2 = new Park(2, "Dani", 10, 10, new NormalSlots(1,1,1), new ElectricSlot(1,1,1,220,50), 1.0);
        park = new Park(idPark, "Dani", 10, 10, new NormalSlots(1,1,1), new ElectricSlot(1,1,1,220,50), 1.0);
    }


    @Test
    public void ensureGetParkCorrectIdReturnsCorrectPark() {
        Park expected = new Park(1, "Parque do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50),1.0);
        when(mockLocationDao.getPark(idPark)).thenReturn(expected);
        Park result = ps.getPark(idPark);
        assertEquals(expected, result);
    }

    @Test
    public void ensureGetInterestPointReturnCorrectInterestPoint(){
        InterestPoint expected = new InterestPoint(2,"Parque do Dani",10,10,1.0);
        when(mockLocationDao.getInterestPoint(2)).thenReturn(expected);
        InterestPoint result = ps.getInterestPoint(2);
        assertEquals(expected,result);
    }

    @Test
    public void ensureGetLocationByCoordinatesReturnCorrectLocation(){
        InterestPoint expected = new InterestPoint(2,"Parque do Dani",10,10,1.0);
        when(mockLocationDao.getLocationByCoordinates(10,10)).thenReturn(expected);
        Location result = ps.getLocationByCoordinates(10,10);
        assertEquals(expected,result);
    }

    @Test
    public void testGetParkIdBicycleLocked(){
        Park expected = new Park(1,"Rua do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);
        when(mockLocationDao.getParkIdBicycleLocked("Teste")).thenReturn(expected);
        Park result = ps.getParkByIdBicycle("Teste");
        assertEquals(expected,result);
    }

    @Test
    public void ensureRemoveParkWithIncorrectIdReturnsFalse() {
        boolean expFlag = false;
        when(mockLocationDao.removePark(this.nonExistentIdPark)).thenReturn(false);
        boolean result = ps.removePark(nonExistentIdPark);
        assertEquals(expFlag, result);

    }

    @Test
    public void ensureRemoveInterestPointithCorrectIdReturnsTrue() {
        boolean expFlag = true;
        when(this.mockLocationDao.removeInterestPoint(1)).thenReturn(true);
        boolean result = ps.removeInterestPoint(1);
        assertEquals(expFlag, result);
    }

    @Test
    public void ensureRemoveInterestPointWithIncorrectIdReturnsFalse() {
        boolean expFlag = false;
        when(this.mockLocationDao.removeInterestPoint(100001)).thenReturn(false);
        boolean result = ps.removeInterestPoint(100001);
        assertEquals(expFlag, result);
    }

    @Test
    public void ensureRemoveParkWithCorrectIdReturnsTrue() {
        boolean expFlag = true;
        when(mockLocationDao.removePark(this.idPark)).thenReturn(expFlag);
        boolean result = ps.removePark(this.idPark);
        assertEquals(expFlag, result);
    }

    @Test
    public void ensureAddParkDoesNotExistReturnsTrue() {
//        Park toBeAdded = new Park(this.nonExistentIdPark, "Parque do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 10),1.0);
        boolean expected = true;
        when(mockLocationDao.addPark(new Park("Parque do Dani",40,-8,new NormalSlots(0,10,10), new ElectricSlot(0,10,10,220,50),1.0))).thenReturn(expected);
//        when(mockLocationDao.getPark(this.nonExistentIdPark)).thenThrow(new IllegalArgumentException());
        boolean result = ps.addPark("Parque do Dani",40,-8,10,10,10, 10,1.0,220,50);
        assertEquals(expected, result);

    }

    @Test
    public void ensureRegisterParkReturnsFalseWithNullName() {
        try {
            boolean expFlag = false;
            when(mockLocationDao.addPark(new Park(null, 40, -8, new NormalSlots(0, 10, 10), new ElectricSlot(0, 10, 10,220,50), 615.0))).thenReturn(false);
            boolean result = this.ps.addPark(null, 40, -8, 10, 10, 10, 10, 615.0,220,50);
            assertEquals(expFlag, result);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void ensureRegisterParkReturnsFalseWithEmptyName() {
        try {
            boolean expFlag = false;
            when(mockLocationDao.addPark(new Park("", 40, -8, new NormalSlots(0, 10, 10), new ElectricSlot(0, 10, 10,220,50), 615.0))).thenReturn(false);
            boolean result = this.ps.addPark("", 40, -8, 10, 10, 10, 10, 615.0,220,50);
            assertEquals(expFlag, result);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }


    @Test
    public void ensureRegisterParkReturnsFalseWithLowerThanPermittedLatitude() {
        try {
            InvalidDataException expFlag = new InvalidDataException();
            String result = "";
            when(mockLocationDao.addPark(new Park("Parque do Dani", -98, -8, new NormalSlots(0, 10, 10), new ElectricSlot(0, 10, 10,220,50), 1.0))).thenThrow(expFlag);

            try {

                this.ps.addPark("Parque do Dani", -98, -8, 10, 10, 10, 10, 1.0,220,50);
            } catch (InvalidDataException e) {
                result = e.getMessage();
            }
            assertEquals(expFlag.getMessage(), result);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }

    }

    @Test
    public void ensureRegisterParkReturnsFalseWithHigherThanPermittedLatitude() {
        try {
            InvalidDataException expFlag = new InvalidDataException();
            String result = "";
            when(mockLocationDao.addPark(new Park("Parque do Dani", 98, -8, new NormalSlots(0, 10, 10), new ElectricSlot(0, 10, 10,220,50), 1.0))).thenThrow(expFlag);

            try {

                this.ps.addPark("Parque do Dani", 98, -8, 10, 10, 10, 10, 1.0,220,50);
            } catch (InvalidDataException e) {
                result = e.getMessage();
            }
            assertEquals(expFlag.getMessage(), result);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void ensureRegisterParkReturnsFalseWithLowerThanPermittedLongitude() {
        try {
            InvalidDataException expFlag = new InvalidDataException();
            String result = "";
            when(mockLocationDao.addPark(new Park("Parque do Dani", 40, -190, new NormalSlots(0, 10, 10), new ElectricSlot(0, 10, 10,220,50), 1.0))).thenThrow(expFlag);

            try {

                this.ps.addPark("Parque do Dani", 40, -190, 10, 10, 10, 10, 1.0,220,50);
            } catch (InvalidDataException e) {
                result = e.getMessage();
            }
            assertEquals(expFlag.getMessage(), result);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }

    }

    @Test
    public void ensureRegisterParkReturnsFalseWithHigherThanPermittedLongitude() {
        try {
            InvalidDataException expFlag = new InvalidDataException();
            String result = "";
            when(mockLocationDao.addPark(new Park("Parque do Dani", 40, 190, new NormalSlots(0, 10, 10), new ElectricSlot(0, 10, 10,220,50), 1.0))).thenThrow(expFlag);

            try {

                this.ps.addPark("Parque do Dani", 40, 190, 10, 10, 10, 10, 1.0,220,50);
            } catch (InvalidDataException e) {
                result = e.getMessage();
            }
            assertEquals(expFlag.getMessage(), result);
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }

    }

    @Test
    public void ensureSetDataThrowsExceptionWhenElectricMaximumCapacityIsZero(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.ps.addPark("Park 1",100.0,100.0, 2,2,0,5,220,50,75.0);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());

    }
    @Test
    public void ensureSetDataThrowsExceptionWhenElectricMaximumCapacityIsNegative(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.ps.addPark("Park 1",100.0,100.0, 2,2,-3,5,220,50,75.0);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());

    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNormalMaximumCapacityIsNegative(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.ps.addPark("Park 1",100.0,100.0, -2,2,10,5,220,50,75.0);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());

    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNormalMaximumCapacityIsZero(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.ps.addPark("Park 1",100.0,100.0, 0,2,10,5,220,50,75.0);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNumberFreeSlotsIsNegative(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.ps.addPark("Park 1",100.0,100.0, 5,-2,10,5,220,50,75.0);
        });
        assertEquals("Number of free slots must not be negative", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNumberFreeSlotsIsBiggerThanMaximum(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.ps.addPark("Park 1",100.0,100.0, 5,7,10,5,220,50,75.0);
        });
        assertEquals("Number of free slots must not be bigger than maximum capacity", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionIfChargeRateIsNegative(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.ps.addPark("Park 1",100.0,100.0, 5,2,10,5,-220,50,75.0);
        });
        assertEquals("Charge rate must be bigger than 0", e.getMessage());
    }

    @Test
    public void ensureSetDataThrowsExceptionIfIntensityIsNegative(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.ps.addPark("Park 1",100.0,100.0, 5,2,10,5,220,-50,75.0);
        });
        assertEquals("Intensity must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionIfIntensityIsZero(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            ElectricSlot nS = new ElectricSlot(2,5,4,5,0);
        });
        assertEquals("Intensity must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureGetParkedBicyclesReturnNullIfParkIdIsWrong() {
        when(mockLocationDao.getParkedBicycles(1)).thenReturn(null);
        List<Bicycle> result = ps.getParkedBicycles(1);
        assertTrue(result == null);
    }

    @Test
    public void ensureGetParkedBicyclesReturnEmptyIfParkIsEmpty() {
        when(mockLocationDao.getParkedBicycles(1)).thenReturn(new ArrayList<>());
        List<Bicycle> result = ps.getParkedBicycles(1);
        assertTrue(result.isEmpty());
    }

    @Test
    public void ensureGetParkedBicyclesReturnBikesIfParkIdIsRight() {
        ArrayList<Bicycle> array = new ArrayList<>();
        array.add(new RoadBicycle("IDteste", Bicycle.statusByCode(1), 1,10,5.0));
        array.add(new MountainBicycle("IDteste", Bicycle.statusByCode(1), 1,10,5.0));
        array.add(new ElectricBicycle("IDteste", Bicycle.statusByCode(1), new Battery(1, 40, 20, 1), 1,10,5.0));
        when(mockLocationDao.getParkedBicycles(1)).thenReturn(array);
        List<Bicycle> result = ps.getParkedBicycles(1);
        assertTrue(!array.isEmpty());
        assertTrue(array.size() == 3);
    }

    @Test
    public void ensureGetNormalSlotReturnsCorrectNormalSlot() {

        NormalSlots expected = new NormalSlots(1, 10, 10);


        Park aux = new Park(1, "Parque do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50),1.0);

        when(mockLocationDao.getNormalSlot(aux.getNormalSlots().getSlotId())).thenReturn(expected);


        NormalSlots result = ps.getNormalSlot(aux.getNormalSlots().getSlotId());


        assertEquals(expected, result);

    }

    @Test
    public void ensureGetElectricalSlotReturnsCorrectNormalSlot() {

        ElectricSlot expected = new ElectricSlot(2, 10, 10,220,50);


        Park aux = new Park(1, "Parque do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50),1.0);

        when(mockLocationDao.getElectricalSlot(aux.getEletricalSlots().getSlotId())).thenReturn(expected);


        ElectricSlot result = ps.getElectricalSlot(aux.getEletricalSlots().getSlotId());


        assertEquals(expected, result);

    }


    @Test
    public void ensureGetAllParksReturnsListWithAllParks() {

        List<Park> expected = new ArrayList<>();
        boolean flag = true;

        Park aux1 = new Park(1, "Parque do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50),1.0);
        Park aux2 = new Park(2, "Parque do Alex", 11, 11, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50),1.0);
        Park aux3 = new Park(3, "Parque do Leonardo", 12, 12, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50),1.0);

        when(mockLocationDao.addPark(new Park("Parque do Dani", 40, -8, new NormalSlots(0,10, 10),new ElectricSlot( 0,10, 10,220,50), 1.0))).thenReturn(flag);
        when(mockLocationDao.addPark(new Park("Parque do Alex", 50, -8, new NormalSlots(0,10, 10), new ElectricSlot(0,10, 10,220,50), 1.0))).thenReturn(flag);
        when(mockLocationDao.addPark(new Park("Parque do Leonardo", 60, -8,new NormalSlots( 0,10, 10),new ElectricSlot( 0,10, 10,220,50), 1.0))).thenReturn(flag);

        expected.add(aux1);
        expected.add(aux2);
        expected.add(aux3);

        when(mockLocationDao.getParkList()).thenReturn(expected);

        List<Park> result = ps.getParkList();

        assertEquals(expected, result);

    }

    @Test
    public void ensureParksListThrowsExceptionWithInvalidData() {

        InvalidDataException expFlag = new InvalidDataException();
        String result ="";

        when(mockLocationDao.getParkList()).thenThrow(expFlag);

        try{

            ps.getParkList();
        }

        catch(InvalidDataException e){
            result = e.getMessage();
        }
        assertEquals(expFlag.getMessage(),result);
    }


    @Test
    public void ensureAddInterestPointAlreadyExistsReturnsFalse(){
        InterestPoint ip = new InterestPoint(this.idPark,"Ponto do dani", 10,10,1.0);
        when(this.mockLocationDao.addInterestPoint(ip)).thenThrow(InvalidDataException.class);
        Exception e1 = assertThrows(InvalidDataException.class, () -> {
            this.mockLocationDao.addInterestPoint(ip);
        });

    }

    @Test
    public void ensureAddInterestPointDoestNotExistsReturnsTrue(){
        InterestPoint ip = new InterestPoint("Ponto do dani", 10,10,1.0);
        when(mockLocationDao.addInterestPoint(ip)).thenReturn(ip);
        InterestPoint result = ps.addInterestPoint("Ponto do dani", 10,10,1.0);
        assertEquals(ip,result);
    }


    @Test
    public void ensureUpdateParkUpdatesExistingPark() {
        boolean expFlag = true;
        Park p = new Park(1,"Parque do Dani",10,10,new NormalSlots(1,10,10), new ElectricSlot(2,10,10,220,50),30);
        when(mockLocationDao.getPark(1)).thenReturn(p);
        when(mockLocationDao.updatePark(p)).thenReturn(true);
        boolean result = this.ps.updatePark(1,"Parque do Dani",20,10,20, 10);
        assertEquals(expFlag,result);

    }

    @Test
    public void ensureUpdateParkReturnsFalseIfParkDoesntExist() {
        boolean expFlag = false;
        Park p = new Park(-21,"Parque do Dani",10,10,new NormalSlots(1,10,10), new ElectricSlot(2,10,10,220,50),30);
        when(mockLocationDao.getPark(-21)).thenThrow(new IllegalArgumentException("No park with the id: "+-21));
        when(mockLocationDao.updatePark(p)).thenReturn(false);
        try {
            boolean result = this.ps.updatePark(-21, "Parque do Dani", 20, 10, 20, 10);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void ensureUpdateParkReturnsFalseWithNullName() {

        boolean expFlag = false;
        Park p = new Park(1,null,10,10,new NormalSlots(1,10,10), new ElectricSlot(2,10,10,220,50),30);
        when(mockLocationDao.getPark(1)).thenReturn(p);
        when(mockLocationDao.updatePark(p)).thenReturn(false);
        boolean result = this.ps.updatePark(1,null,20,10,20, 10);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureUpdateParkReturnsFalseWithEmptyName() {

        boolean expFlag = false;
        Park p = new Park(1,"",10,10,new NormalSlots(1,10,10), new ElectricSlot(2,10,10,220,50),30);
        when(mockLocationDao.getPark(1)).thenReturn(p);
        when(mockLocationDao.updatePark(p)).thenReturn(false);
        boolean result = this.ps.updatePark(1,"",20,10,20, 10);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureUpdateParkReturnsFalseWithHigherNumberFreeNormalSlots() {
        boolean expFlag = false;
        Park p = new Park(1,"Parque do Dani",10,10,new NormalSlots(1,10,10), new ElectricSlot(2,10,10,220,50),30);
        when(mockLocationDao.getPark(1)).thenReturn(p);
        when(mockLocationDao.updatePark(p)).thenReturn(false);
        try {
            boolean result = this.ps.updatePark(1, "Parque do Dani", 20, 21, 20, 10);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
    }

    @Test
    public void ensureUpdateParkReturnsFalseWithInvalidNumberFreeNormalSlots() {
        boolean expFlag = false;
        Park p = new Park(1,"Parque do Dani",10,10,new NormalSlots(1,10,10), new ElectricSlot(2,10,10,220,50),30);
        when(mockLocationDao.getPark(1)).thenReturn(p);
        when(mockLocationDao.updatePark(p)).thenReturn(false);
        try {
            boolean result = this.ps.updatePark(1, "Parque do Dani", 20, -1, 20, 21);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
    }

    @Test
    public void ensureUpdateParkReturnsFalseWithInvalidNumberFreeElectricalSlots() {
        boolean expFlag = false;
        Park p = new Park(1,"Parque do Dani",10,10,new NormalSlots(1,10,10), new ElectricSlot(2,10,10,220,50),30);
        when(mockLocationDao.getPark(1)).thenReturn(p);
        when(mockLocationDao.updatePark(p)).thenReturn(false);
        try {
            boolean result = this.ps.updatePark(1, "Parque do Dani", 20, 10, 20, -1);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
    }

    @Test
    public void ensureUpdateParkReturnsFalseWithInvalidNormalBicycleCapacity() {
        boolean expFlag = false;
        Park p = new Park(1,"Parque do Dani",10,10,new NormalSlots(1,10,10), new ElectricSlot(2,10,10,220,50),30);
        when(mockLocationDao.getPark(1)).thenReturn(p);
        when(mockLocationDao.updatePark(p)).thenReturn(false);
        try {
            boolean result = this.ps.updatePark(1, "Parque do Dani", -1, 10, 20, 10);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
    }

    @Test
    public void ensureUpdateParkReturnsFalseWithInvalidNormalElectricalCapacity() {
        boolean expFlag = false;
        Park p = new Park(1,"Parque do Dani",10,10,new NormalSlots(1,10,10), new ElectricSlot(2,10,10,220,50),30);
        when(mockLocationDao.getPark(1)).thenReturn(p);
        when(mockLocationDao.updatePark(p)).thenReturn(false);
        try {
            boolean result = this.ps.updatePark(1, "Parque do Dani", 20, 10, -1, 10);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }

    }

    @Test
    public void testBikeIntoParkIfParkDontHaveSpace(){
        when(mockLocationDao.bikeIntoPark("IDteste", 1)).thenReturn(false);
        boolean result = ps.bikeIntoPark("IDteste", 1);
        assertTrue(!result);
    }
    
    @Test
    public void testBikeIntoParkIfParkHaveSpace(){
        when(mockLocationDao.bikeIntoPark("IDteste", 1)).thenReturn(true);
        boolean result = ps.bikeIntoPark("IDteste", 1);
        assertTrue(result);
    }

    @Test
    public void ensureGetMountainBicyclesWorks(){
        List<MountainBicycle> eb = new ArrayList<>();
        eb.add(this.bike);
        when(this.mockLocationDao.getMountainBicycles(this.idPark)).thenReturn(eb);
        List<MountainBicycle> result = this.ps.getMountainBicyclesPark(this.idPark);
        assertEquals(eb,result);

    }

    @Test
    public void ensureGetMountainBikesWorksWithNoBikes(){
        List<MountainBicycle> eb = new ArrayList<>();
        when(this.mockLocationDao.getMountainBicycles(this.idPark)).thenReturn(eb);
        List<MountainBicycle> result = this.ps.getMountainBicyclesPark(this.idPark);
        assertEquals(eb,result);

    }

    @Test
    public void ensureGetRoadBicyclesWOrks(){
        List<RoadBicycle> eb1 = new ArrayList<>();
        eb1.add(this.bike2);
        when(this.mockLocationDao.getRoadBicycles(this.idPark)).thenReturn(eb1);
        List<RoadBicycle> result = this.ps.getRoadBicyclesPark(this.idPark);
        assertEquals(eb1,result);

    }

    @Test
    public void esnureGetRoadBikesWorksWithNoRoadBikes(){
        List<RoadBicycle> eb1 = new ArrayList<>();
        when(this.mockLocationDao.getRoadBicycles(this.idPark)).thenReturn(eb1);
        List<RoadBicycle> result = this.ps.getRoadBicyclesPark(this.idPark);
        assertEquals(eb1,result);
    }

    @Test
    public void ensureGetEletricBicyclesWorks(){
        List<ElectricBicycle> eb1 = new ArrayList<>();
        eb1.add(this.bike3);
        when(this.mockLocationDao.getElectricalBicycles(this.idPark)).thenReturn(eb1);
        List<ElectricBicycle> result = this.ps.getEletricBicyclesPark(this.idPark);
        assertEquals(eb1,result);

    }

    @Test
    public void ensureGetEletricBicyclesWorksWithNoBikes(){
        List<ElectricBicycle> eb1 = new ArrayList<>();

        when(this.mockLocationDao.getElectricalBicycles(this.idPark)).thenReturn(eb1);
        List<ElectricBicycle> result = this.ps.getEletricBicyclesPark(this.idPark);
        assertEquals(eb1,result);

    }

    @Test
    public void ensureRegisterIdentifierExistsReturnsTrue() {
        boolean expFlag = true;
        InterestPoint ip = new InterestPoint(1,"Daniel",10,10,10);
        when(mockLocationDao.getInterestPoint(1)).thenReturn(ip);
        when(mockLocationDao.updateInterestPoint(ip)).thenReturn(true);
        boolean result = this.ps.updateInterestPoint(1, "Daniel");
        assertEquals(expFlag, result);


    }

    @Test
    public void ensureRegisterIdentifierDoesNotExistReturnsFalse() {
        boolean expFlag = false;
        InterestPoint ip = new InterestPoint(1,"Daniel",10,10,10);
        when(mockLocationDao.getInterestPoint(2)).thenThrow(new IllegalArgumentException("No interest point with the id: "+1));
        try {
            boolean result = this.ps.updateInterestPoint(2, "Das");
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void ensureCalculateDistanceWorks(){
        double distance = this.ps.distanceBetweenLocations(this.p2,this.park);
        double expected = 0;
        assertEquals(expected,distance);
    }


    @Test
    public void ensureGetArrayListWithAllInterestPoints(){
        List<InterestPoint> ipList = new ArrayList<>();
        ipList.add(this.p1);
        when(mockLocationDao.getAllInterestPointsList()).thenReturn(ipList);
        List<InterestPoint> result = this.ps.getAllInterestPoints();
        assertEquals(ipList,result);

    }

    @Test
    public void ensureGetArrayListNoPoints(){
        List<InterestPoint> ipList = new ArrayList<>();
        when(mockLocationDao.getAllInterestPointsList()).thenReturn(ipList);
        List<InterestPoint> result = this.ps.getAllInterestPoints();
        assertEquals(ipList,result);

    }


}

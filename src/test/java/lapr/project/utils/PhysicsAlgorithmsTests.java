package lapr.project.utils;

import lapr.project.model.bicycle.Battery;
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.bikenetwork.PathService;
import lapr.project.model.park.Park;
import lapr.project.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhysicsAlgorithmsTests {

    @Mock
    private PathService wis;

    @InjectMocks
    private PhysicsAlgorithms ph;

    /**
     * A variable representative of an incorrect type of latitude.
     */
    private double latitudeWrong1;
    /**
     * A variable representative of an incorrect type of latitude.
     */
    private double latitudeWrong2;
    /**
     * A variable representative of an incorrect type of longitude.
     */
    private double longitudeWrong1;
    /**
     * A variable representative of an incorrect type of longitude.
     */
    private double longitudeWrong2;
    /**
     * A variable representative of a correct type of latitude.
     */
    private double correctLatitude1;
    /**
     * A variable representative of a correct type of latitude.
     */
    private double correctLatitude2;
    /**
     * A variable representative of a correct type of longitude.
     */
    private double correctLongitude1;
    /**
     * A variable representative of a correct type of longitude.
     */
    private double correctLongitude2;
    /**
     * A variable representative of a correct type of latitude.
     */
    private double googleExampleLatitude1;
    /**
     * A variable representative of a correct type of latitude.
     */
    private double googleExampleLatitude2;
    /**
     * A variable representative of a correct type of longitude.
     */
    private double googleExampleLongitude1;
    /**
     * A variable representative of a correct type of longitude.
     */
    private double googleExampleLongitude2;

    @BeforeEach
    public void setUp() {

        this.latitudeWrong1 = -91;
        this.latitudeWrong2 = 91;
        this.longitudeWrong1 = -181;
        this.longitudeWrong2 = 182;
        this.correctLatitude1 = 47.6788206;
        this.correctLongitude1 = -122.3271205;
        this.correctLatitude2 = 47.6788206;
        this.correctLongitude2 = -122.5271205;
        this.googleExampleLatitude1 = 41.102189; //Latitude of my home.
        this.googleExampleLongitude1 = -8.647019; //Longitude of my home.
        this.googleExampleLatitude2 = 41.144294; //Estação São Bento.
        this.googleExampleLongitude2 = -8.610594; // Longitude Estação São bento.
        wis = Mockito.mock(PathService.class);
        initMocks(this);
    }

    @Test
    public void ensureCalculateDistanceWithLatitudeMinorThanNegative90ThrowsException() {
        boolean flag = false;

        try {
            double result = PhysicsAlgorithms.distance(this.latitudeWrong1, this.correctLongitude1, this.correctLatitude2, this.correctLongitude2);
        } catch (InvalidDataException e) {
            flag = true;
        }
        boolean expFlag = true;

        assertEquals(expFlag, flag);

    }

    @Test
    public void ensureCalculateDistanceWithLatitudeOver90DegreesThrowsException() {
        boolean flag = false;
        try {
            double result = PhysicsAlgorithms.distance(this.latitudeWrong2, this.correctLongitude1, this.correctLatitude2, this.correctLongitude2);
        } catch (InvalidDataException e) {
            flag = true;
        }
        boolean expFlag = true;

        assertEquals(expFlag, flag);

    }

    @Test
    public void ensureCalculateDistanceWithLongitudeUnderMinus180DegreesThrowsException() {
        boolean flag = false;
        try {
            double result = PhysicsAlgorithms.distance(this.correctLatitude1, this.longitudeWrong1, this.correctLatitude2, this.correctLongitude2);
        } catch (InvalidDataException e) {
            flag = true;
        }
        boolean expFlag = true;

        assertEquals(expFlag, flag);

    }

    @Test
    public void ensureCalculateDistanceWithLongitudeOver180DegreesThrowsException() {
        boolean flag = false;

        try {
            double result = PhysicsAlgorithms.distance(this.correctLatitude1, this.longitudeWrong2, this.correctLatitude2, this.correctLongitude2);
        } catch (InvalidDataException e) {
            flag = true;
        }
        boolean expFlag = true;

        assertEquals(expFlag, flag);

    }

    @Test
    public void ensureCalculateDistanceWithDecimalGeographicalCoordinatesWorks() {

        double result = PhysicsAlgorithms.distance(this.correctLatitude1, this.correctLongitude1, this.correctLatitude2, this.correctLongitude2);
        double expResult = 14973.190481586224;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureCalculateDistanceWithDecimalGeographicalCoordinates2Works() {

        double result = PhysicsAlgorithms.distance(this.googleExampleLatitude1, this.googleExampleLongitude1, this.googleExampleLatitude2, this.googleExampleLongitude2);
        double expResult = 5588.272889476131;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureCalculateDistanceWithLatitude90Works() {

        double result = PhysicsAlgorithms.distance(this.googleExampleLatitude1, this.googleExampleLongitude1, 90, this.googleExampleLongitude2);
        double expResult = 5437188.507224497;
        assertEquals(expResult, result,0.0001);
    }

    @Test
    public void ensureCalculateDistanceWithLatitudeMinus90Works() {

        double result = PhysicsAlgorithms.distance(this.googleExampleLatitude1, this.googleExampleLongitude1, -90, this.googleExampleLongitude2);
        double expResult = 14577898.288796077;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureCalcularDistanceWithLongitude180Works() {

        double result = PhysicsAlgorithms.distance(this.googleExampleLatitude1, this.googleExampleLongitude1, this.googleExampleLatitude2, 180);
        double expResult = 10828240.605060919;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureCalcularDistanceWithLongitudeMinus180Works() {

        double result = PhysicsAlgorithms.distance(this.googleExampleLatitude1, this.googleExampleLongitude1, this.googleExampleLatitude2, -180);
        double expResult = 10828240.605060919;
        assertEquals(expResult, result);

    }

    @Test
    public void ensureCalculateDistanceWithLatitude90InOtherParameterWorks() {

        double result = PhysicsAlgorithms.distance(90, this.googleExampleLongitude1, this.googleExampleLatitude2, this.googleExampleLongitude2);
        double expResult = 5432506.644838128;
        assertEquals(expResult, result,0.0001);
    }

    @Test
    public void ensureCalculateDistanceWithLatitudeMinus90InOtherParameterWorks() {

        double result = PhysicsAlgorithms.distance(-90, this.googleExampleLongitude1, this.googleExampleLatitude2, this.googleExampleLongitude2);
        double expResult = 14582580.151182443;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureCalculateDistanceWithLongitudeMinus180InOtherParameterWorks() {

        double result = PhysicsAlgorithms.distance(this.googleExampleLatitude1, -180, this.googleExampleLatitude2, this.googleExampleLongitude2);
        double expResult = 10828588.314576158;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureCalculateDistanceWithLongitude180InOtherParameterWorks() {

        double result = PhysicsAlgorithms.distance(this.googleExampleLatitude1, 180, this.googleExampleLatitude2, this.googleExampleLongitude2);
        double expResult = 10828588.314576158;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureCalculateCaloriesWithWrongDataThrowsException() {

        try {

            PhysicsAlgorithms.calculateCalories(-1, 10, 5, 10, 1, 10, 10, 10, 10, 10, 1, 1, 1, 1);

            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
        try {

            PhysicsAlgorithms.calculateCalories(-0, 10, 5, 10, 1, 10, 10, 10, 10, 10, 1, 1, 1, 1);

            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }

    }

    @Test
    public void ensureCalculateCaloriesWithCorrectDataReturnAValue() {
        Park park1 = new Park(1, "Trindade", 42, -8, null, null, 1.0);
        Park park2 = new Park(2, "S.João", 42, -9, null, null, 1.0);
        User user = new User("Daniel", "teste@teste.com", "teste", "teste", 50.0f, 1.76f, 111474938, false, 15.00, 0);

        double expected = 0;
        double result = PhysicsAlgorithms.calculateCalories(10, 1, 0.41, 50, 1.76, 0, 50, 1, 100, 0, 100, 1, 1, 1);

        assertEquals(expected, result);

    }

    @Test
    public void ensureCaloriesWorks() {
        double expected = 4.801573874706618E9;
        double result = PhysicsAlgorithms.calculateCalories(100, 10, 0.41, 1, 70, 1.76, 10, 81, 23, 40, 43, -41, 1, 10);

        assertEquals(expected, result,0.0001);
    }

    @Test
    public void ensureCalculateEnergyWithCorrectDataReturnAValueWhenWindIsTooStrong() {

        User u = new User("Daniel", "teste@teste.com", "teste", "teste", 55.0f, 1.76f, 111474938, false, 15.00, 0);
        Battery bat = new Battery(2, 50, 25, 1);
        ElectricBicycle eBike;

        Battery bat2 = new Battery(2, 50, 35, 1);
        eBike = new ElectricBicycle("IDteste", Bicycle.statusByCode(1), bat2, 11, 10, 5.0);

        Park park1 = new Park(1, "Trindade", 41.102189, -8.647019, null, null, 1.0);
        Park park2 = new Park(2, "S.Bento", 41.144294, -8.610594, null, null, 1.0);

        try {
            double vm = 10 / 3.6;
            double d = PhysicsAlgorithms.distance(park1.getLatitude(), park1.getLongitude(), park2.getLatitude(), park2.getLongitude());
            double result = PhysicsAlgorithms.calculateEnergySpentBetween2Points(0.41, 11, 1, 1, 20, 55, 1.76, 30, 40, 40, 51, 1, 1, 1);
            double expected = 0;

            assertEquals(expected, result);
        } catch (Exception e) {
            assertTrue(true);
        }

    }

    @Test
    public void ensureCalculateEnergyWorksWhenWithWorksWhenYouDontWalkAnyDistance() {
        double result = PhysicsAlgorithms.calculateEnergySpentBetween2Points(0.41, 10, 1, 10, 10, 10, 1.76, 10, 10, 10, 10, 1, 1, 20);
        double expResult = 0;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureCalculateEnergyWorksCorrectly() {
        double result = PhysicsAlgorithms.calculateEnergySpentBetween2Points(0.41, 10, 10, 1, 240, 1, 1, 80, 10, -10, -180, 1, 1, 10);
        double expResult = 1.7865380118902185E9;
        assertEquals(expResult, result,0.0001);
    }

    @Test
    public void ensureCalculateEnergyWithWrongDataThrowsException() {

        try {
            PhysicsAlgorithms.calculateDistanceCoveredByElectricalBicycle(-1, -1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }

        try {
            PhysicsAlgorithms.calculateDistanceCoveredByElectricalBicycle(1, -1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }

        try {
            PhysicsAlgorithms.calculateDistanceCoveredByElectricalBicycle(1, 1, 5, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void ensureCalculateEnergySpentBetween2PointsWithValidDataThrowsException() {
        double result = PhysicsAlgorithms.calculateEnergySpentBetween2Points(0.3, 11.0, 0.0, 11.0, 0.0, 60.0, 1.70, 39.099912, -94.581213, 38.627089, -90.200203, 1.10, 2.0, 13.0);
        double expResult = 1.484092724848919E7;
        assertEquals(result, expResult,0.0001);
    }

    @Test
    public void ensureCalculateEnergySpentBetween2PointsWithInvalidDataThrowsException1() {
        boolean flag = true;
        try {
            PhysicsAlgorithms.calculateEnergySpentBetween2Points(0.3, -1, 1.0, 11.0, 0.0, 60.0, 1.70, 39.099912, -94.581213, 38.627089, -90.200203, 1.10, 2.0, 13.0);
        } catch (InvalidDataException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureCalculateEnergySpentBetween2PointsWithInvalidDataThrowsException2() {
        boolean flag = true;
        try {
            PhysicsAlgorithms.calculateEnergySpentBetween2Points(0.3, -1, 0.0, 11.0, 0.0, 60.0, 1.70, 39.099912, -94.581213, 38.627089, -90.200203, 1.10, 2.0, 13.0);
        } catch (InvalidDataException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureCalculateEnergySpentBetween2PointsWithInvalidDataThrowsException3() {
        boolean flag = true;
        try {
            double result = PhysicsAlgorithms.calculateEnergySpentBetween2Points(0.3, 11.0, 0.0, 11.0, 0.0, -10, 1.70, 39.099912, -94.581213, 38.627089, -90.200203, 1.10, 2.0, 13.0);
        } catch (InvalidDataException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureCalculateEnergySpentBetween2PointsWithInvalidDataThrowsException4() {
        boolean flag = true;
        try {
            double result = PhysicsAlgorithms.calculateEnergySpentBetween2Points(0.3, 11.0, 0.0, -5, 0.0, 60.0, 1.70, 39.099912, -94.581213, 38.627089, -90.200203, 1.10, 2.0, 13.0);
        } catch (InvalidDataException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureCalculateBearingAngleWithLatitude1NegativeInvalidDataThrowsException() {

        boolean flag = true;
        try {
            PhysicsAlgorithms.calculateBearingAngle(this.latitudeWrong1, 1, 1, 1);
        } catch (InvalidDataException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureCalculateBearingAngleWithLatitude1PositiveInvalidDataThrowsException() {

        boolean flag = true;

        try {
            PhysicsAlgorithms.calculateBearingAngle(this.latitudeWrong2, 1, 1, 1);
        } catch (InvalidDataException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureCalculateBearingAngleWithLatitude2NegativeInvalidDataThrowsExcepton() {

        boolean flag = true;
        try {
            PhysicsAlgorithms.calculateBearingAngle(1, 1, this.latitudeWrong1, 1);
        } catch (InvalidDataException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureCalculateBearingAngleWithLatitude2PositiveThrowsInvalidDataException() {

        boolean flag = true;
        try {
            PhysicsAlgorithms.calculateBearingAngle(1, 1, this.latitudeWrong2, 1);
        } catch (InvalidDataException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureCalculateBearingAngleWithLongitude1NegativeThrowsInvalidDataExceptio() {

        boolean flag = true;
        try {
            PhysicsAlgorithms.calculateBearingAngle(1, this.longitudeWrong1, 1, 1);

        } catch (InvalidDataException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureCalculateBearingAngleWithLongitude1PositiveThrowsInvalidDataException() {

        boolean flag = true;
        try {
            PhysicsAlgorithms.calculateBearingAngle(1, this.longitudeWrong2, 1, 1);
        } catch (InvalidDataException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureCalculateBearingAngleWithLongitude2NegativeThrowsInvalidDataException() {

        boolean flag = true;
        try {
            PhysicsAlgorithms.calculateBearingAngle(1, 1, 1, this.longitudeWrong1);
        } catch (InvalidDataException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureCalculateBearingAngleWithLongitude2PositiveThrowsInvalidDataException() {

        boolean flag = true;
        try {
            PhysicsAlgorithms.calculateBearingAngle(1, 1, 1, this.longitudeWrong2);
        } catch (InvalidDataException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureCalculateBearingAngleWorks1() {

        double result = PhysicsAlgorithms.calculateBearingAngle(39.099912, -94.581213, 38.627089, -90.200203);
        double expResult = 96.5126242349995;
        assertEquals(result, expResult);
    }

    @Test
    public void ensureCalculateBearingAngleWorks2() {

        double result = PhysicsAlgorithms.calculateBearingAngle(8.46696, -17.03663, 65.35996, -17.03663);
        double expResult = 0;
        assertEquals(result, expResult);
    }

    @Test
    public void ensureCalculateBearingAngleWorks3() {

        double result = PhysicsAlgorithms.calculateBearingAngle(40.2041, -8.4014, 41.1538, -8.6211);
        double expResult = -9.879186447822658;
        assertEquals(result, expResult);
    }

    @Test
    public void ensureCalculateDirectionWindsIs0WhenAnglesAre90DegreesDifferent() {

        double result = PhysicsAlgorithms.calculateWindDirection(360, 270);
        double expResult = 0;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureCalculateWindDirectionIsMinus1When1WhenAnglesAreFromSecondQuadrant() {

        double result = PhysicsAlgorithms.calculateWindDirection(0, 91);
        double expResult = -1.0;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureCalculateWindDirectionIsMinus1WhenWhenAnglesAreFromThirdQuadrant() {

        double result = PhysicsAlgorithms.calculateWindDirection(0, 181);
        double expResult = -1.0;
        assertEquals(expResult, result);

    }

    @Test
    public void ensureCalculateWindDirectionIs1WhenWhenAnglesAreFromFirstQuadrant() {

        double result = PhysicsAlgorithms.calculateWindDirection(0, 1);
        double expResult = 1.0;
        assertEquals(expResult, result);

    }

    @Test
    public void ensureCalculateWindDirectionIs1WhenWhenAnglesAreFromFourthQuadrant() {

        double result = PhysicsAlgorithms.calculateWindDirection(0, 271);
        double expResult = 1.0;
        assertEquals(expResult, result);

    }

    @Test
    public void ensureBearingAngleWithNegativeBearingWorks() {
        double result = PhysicsAlgorithms.calculateWindDirection(-10, 1);
        double expResult = 1;
        assertEquals(result, expResult);
    }

    @Test
    public void ensureBearingAngleWithBearing0() {
        double result = PhysicsAlgorithms.calculateWindDirection(0, 1);
        double experesult = 1;
        assertEquals(experesult, result);
    }

    @Test
    public void ensureWindDirectionWithNegativeWorks() {
        double result = PhysicsAlgorithms.calculateWindDirection(1, -2);
        double expResult = 1;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureWindDirectionWith0Works() {
        double result = PhysicsAlgorithms.calculateWindDirection(2, 0);
        double expResult = 1;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureWhenTheyAre90DegreesReturns0() {
        double result = PhysicsAlgorithms.calculateWindDirection(90, 0);
        double expResult = 0;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureWhenTheyAre270DegreesWorks() {
        double result = PhysicsAlgorithms.calculateWindDirection(0, 270);
        double expREsult = 0;
        assertEquals(expREsult, result);
    }

    @Test
    public void ensureWhenTheyAre270NegativeWorks() {
        double result = PhysicsAlgorithms.calculateWindDirection(0, 270);
        double expResult = 0;
        assertEquals(expResult, result);
    }

    @Test
    public void testcalculateDistanceCoveredByElectricalBicycleWithWrongData(){
        try{
            PhysicsAlgorithms.calculateDistanceCoveredByElectricalBicycle(1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
        try{
            PhysicsAlgorithms.calculateDistanceCoveredByElectricalBicycle(1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
        try{
            PhysicsAlgorithms.calculateDistanceCoveredByElectricalBicycle(1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
        try{
            PhysicsAlgorithms.calculateDistanceCoveredByElectricalBicycle(1,1,1,-1,1,1,1,1,1,1,1,1,1,1,1,1);
            fail();
        }
        catch(InvalidDataException e){
            assertTrue(true);
        }
    }

    @Test
    public void testcalculateEnergySpentBetween2PointsWithWrongData(){
        try{
            PhysicsAlgorithms.calculateEnergySpentBetween2Points(1,0,0,1,1,1,1,1,1,1,1,1,1,1);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        try{
            PhysicsAlgorithms.calculateEnergySpentBetween2Points(1,1,1,1,1,0,1,1,1,1,1,1,1,1);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
        try{
            PhysicsAlgorithms.calculateEnergySpentBetween2Points(1,1,1,-1,1,1,1,1,1,1,1,1,1,1);
            fail();
        }
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }
}

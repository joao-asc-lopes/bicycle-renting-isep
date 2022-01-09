package lapr.project.controller.user;

import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.park.Location;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CalculateDistanceParksControllerTest {

    @Mock
    private BicycleNetwork bn;

    @Mock
    private LocationFacade pf;

    @InjectMocks
    private CalculateDistanceParksController cdc;

    public CalculateDistanceParksControllerTest() {
    }

    @BeforeEach
    public void setUp() {
        bn = Mockito.mock(BicycleNetwork.class);
        pf = Mockito.mock(LocationFacade.class);
        this.cdc = new CalculateDistanceParksController();
        initMocks(this);
    }

    @Test
    public void testGetParkRegistryWhenNoParksAreRegisted() {
        List<Park> array = cdc.getParkRegistry();
        assertTrue(array.isEmpty());
    }

    @Test
    public void testGetParkRegistryWhenParksAreRegisted() {
        List<Park> a = new ArrayList<>();
        a.add(new Park(1,"teste",1,1,null,null,1.0));
        when(pf.getParkList()).thenReturn(a);
        List<Park> result = cdc.getParkRegistry();
        assertTrue(!result.isEmpty());
        assertTrue(result.size() == 1);
    }

    @Test
    public void testGetParkByIdWhenIdIsWrong() {
        when(pf.getParkById(2)).thenThrow(new InvalidDataException());
        Location p = cdc.getLocationById(2);
        assertTrue(p == null);
    }

    @Test
    public void testGetParkByIdWhenIsIsCorrect(){
        Park x = new Park(1,"teste",1,1,null,null,1.0);
        when(pf.getParkById(1)).thenReturn(x);
        Location result = cdc.getLocationById(1);
        assertTrue(result != null);
    }


    @Test
    public void testCalculateDistanceBetweenUserAndParkWithValidData() {
        double expected = 14973.190481586224;
        double latitudeUser =47.6788206;
        double longitudeUser =   -122.3271205;
        Park park = new Park(1, "S.Joao", 47.6788206,-122.5271205,null,null,1.0);
        double result = this.cdc.calculateDistanceParkUser(latitudeUser,longitudeUser,park);
        assertEquals(result,expected);
    }

    @Test
    public void ensureCalculateDistanceWithUserLatitudeAbove90ReturnsMinusOne() {
        double expected = -1;
        double latitudeUser =91;
        double longitudeUser =   -122.3271205;
        Park park = new Park(1, "S.Joao", 47.6788206,-122.5271205,null,null,1.0);
        double result = this.cdc.calculateDistanceParkUser(latitudeUser,longitudeUser,park);
        assertTrue(result==expected);
    }

    @Test
    public void ensureCalculateDistanceWithUserLatitudeUnderMinus90ReturnsMinusOne() {
        double expected = -1;
        double latitudeUser =-91;
        double longitudeUser =   -122.3271205;
        Park park = new Park(1, "S.Joao", 47.6788206,-122.5271205,null,null,1.0);
        double result = this.cdc.calculateDistanceParkUser(latitudeUser,longitudeUser,park);
        assertTrue(result==expected);
    }

    @Test
    public void ensureCalculateDistanceWithUserLongitudeUnderMinus180ReturnsMinusOne() {
        double expected = -1;
        double latitudeUser =-10;
        double longitudeUser =   -200;
        Park park = new Park(1, "S.Joao", 47.6788206,-122.5271205,null,null,1.0);
        double result = this.cdc.calculateDistanceParkUser(latitudeUser,longitudeUser,park);
        assertTrue(result==expected);
    }

    @Test
    public void ensureCalculateDistanceWithUserLongitudeOver180ReturnsMinusOne() {
        double expected = -1;
        double latitudeUser =-10;
        double longitudeUser =   200;
        Park park = new Park(1, "S.Joao", 47.6788206,-122.5271205,null,null,1.0);
        double result = this.cdc.calculateDistanceParkUser(latitudeUser,longitudeUser,park);
        assertTrue(result==expected);
    }


}

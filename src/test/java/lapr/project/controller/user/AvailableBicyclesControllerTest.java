/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller.user;

import lapr.project.model.bicycle.*;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;
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


public class AvailableBicyclesControllerTest {
    @Mock
    private LocationFacade pf;

    @Mock
    private BicycleFacade bf;

    @InjectMocks
    private AvailableBicyclesController abc;

    private MountainBicycle test1;

    private RoadBicycle test2;

    private ElectricBicycle test3;

    private Battery bat1;

    public AvailableBicyclesControllerTest() {
    }

    @BeforeEach
    public void setUp() {
        bat1 = new Battery(1,150,110,50f);
        this.test1 = new MountainBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 10,10,5.0);
        this.test2 = new RoadBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,10,10,5.0);
        this.test3 = new ElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE,bat1,10,10,5.0);

        pf = Mockito.mock(LocationFacade.class);
        bf = Mockito.mock(BicycleFacade.class);
        abc = new AvailableBicyclesController();
        initMocks(this);
    }

    /**
     * Teste do getParkRegistry quando não há parks registados
     */
    @Test
    public void testGetParkRegistryWhenNoParksAreRegisted() {
        boolean expected = true;
        when(pf.getParkList()).thenReturn(new ArrayList<>());
        List<Park> parks = abc.getParkRegistry();
        assertTrue(parks.isEmpty());
    }

    /**
     * Teste do getParkRegistry quando há parks registados
     */
    @Test
    public void testGetParkRegistryWhenParksAreRegisted() {
        ArrayList<Park> expected = new ArrayList<>();
        Park p = new Park(1, "Trindade", 41, -8, null, null,1.0);
        expected.add(p);
        when(pf.getParkList()).thenReturn(expected);
        List<Park> parks = abc.getParkRegistry();
        assertTrue(!parks.isEmpty());
        assertTrue(parks.size() == 1);
    }

    @Test
    public void testGetBicyclesInParkWhenParkIdIsWrong() {
        when(pf.getParkedBicycles(1)).thenReturn(null);
        List<Bicycle> array = abc.getBicyclesInPark(1);
        assertTrue(array == null);
    }

    @Test
    public void testGetBicyclesInParkWhenNoBicyclesAreInThePark() {
        when(pf.getParkedBicycles(1)).thenReturn(new ArrayList<>());
        List<Bicycle> array = abc.getBicyclesInPark(1);
        assertTrue(array.isEmpty());
    }

    @Test
    public void testGetBicyclesInParkWhenBicyclesAreInThePark() {
        ArrayList<Bicycle> array = new ArrayList<>();
        array.add(new RoadBicycle("IDteste", Bicycle.statusByCode(1), 1,10,5.0));
        array.add(new MountainBicycle("IDteste", Bicycle.statusByCode(1), 1,10,5.0));
        array.add(new ElectricBicycle("IDteste", Bicycle.statusByCode(1), new Battery(1, 40, 20, 1), 1,10,5.0));
        when(pf.getParkedBicycles(1)).thenReturn(array);
        List<Bicycle> result = abc.getBicyclesInPark(1);
        assertTrue(!array.isEmpty());
        assertTrue(array.size() == 3);
    }

    @Test
    public void testGetMountainReturnsWhenNotEmpty(){
        ArrayList<MountainBicycle> expected = new ArrayList<>();
        expected.add(this.test1);
        when(this.pf.getMountainBicyclesPark(1)).thenReturn(expected);
        List<MountainBicycle> result = this.abc.getMountainBicyclesInPark(1);
        assertEquals(expected,result);

    }

    @Test
    public void testGetMountainReturnsWhenEmpty(){
        ArrayList<MountainBicycle> expected = new ArrayList<>();
        when(this.pf.getMountainBicyclesPark(1)).thenReturn(expected);
        List<MountainBicycle> result = this.abc.getMountainBicyclesInPark(1);
        assertEquals(expected,result);

    }

    @Test
    public void testGetRoadReturnsWhenNotEmpty(){
        ArrayList<RoadBicycle> expected = new ArrayList<>();
        expected.add(this.test2);
        when(this.pf.getRoadBicyclesPark(1)).thenReturn(expected);
        List<RoadBicycle> result = this.abc.getRoadBicyclesInPark(1);
        assertEquals(expected,result);

    }


    @Test
    public void testGetRoadReturnsWhenEmpty(){
        ArrayList<RoadBicycle> expected = new ArrayList<>();
        when(this.pf.getRoadBicyclesPark(1)).thenReturn(expected);
        List<RoadBicycle> result = this.abc.getRoadBicyclesInPark(1);
        assertEquals(expected,result);

    }

    @Test
    public void testGetElecReturnsWhenNotEmpty(){
        ArrayList<ElectricBicycle> expected = new ArrayList<>();
        expected.add(this.test3);
        when(this.pf.getElectricBicyclesPark(1)).thenReturn(expected);
        List<ElectricBicycle> result = this.abc.getElectricBicycleInPark(1);
        assertEquals(expected,result);

    }

    @Test
    public void testGetElecReturnsWhenEmpty(){
        ArrayList<ElectricBicycle> expected = new ArrayList<>();
        when(this.pf.getElectricBicyclesPark(1)).thenReturn(expected);
        List<ElectricBicycle> result = this.abc.getElectricBicycleInPark(1);
        assertEquals(expected,result);

    }

    @Test
    public void ensureAllBicyclesReturnsNullWhenThereAreNoBikesInTheDataBase(){
        List<Bicycle> expected = new ArrayList<>();
        when(this.bf.getAllBicycles()).thenReturn(expected);
        List<Bicycle> allBike = this.abc.getAllBicycles();
        assertEquals(expected,allBike);
    }
    @Test
    public void ensureAllBicyclesReturnCorrectBicyclesWhenReturnedFromDb(){
        List<Bicycle > expected = new ArrayList<>();
        expected.add(this.test1);
        expected.add(this.test2);
        when(this.bf.getAllBicycles()).thenReturn(expected);
        List<Bicycle> allBike = this.abc.getAllBicycles();
        assertEquals(expected,allBike);
    }





}

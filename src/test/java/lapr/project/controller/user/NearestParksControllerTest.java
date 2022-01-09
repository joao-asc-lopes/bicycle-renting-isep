package lapr.project.controller.user;

import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.NormalSlots;
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

public class NearestParksControllerTest {

    @Mock
    private LocationFacade pf;

    @Mock
    private BicycleNetwork bn;
    /**
     * Injects the mocks to the nearest Park controller.
     */

    @InjectMocks
    private NearestParksController npc;

    /**
     * Variable that represents the park with the id 3.
     */
    private Park p3;
    /**
     * Variable that represents the park with the id 5.
     */
    private Park p5;
    /**
     * Variable that represents the park with the id 6.
     */
    private Park p6;
    /**
     * Variable that represents the park with the id 7.
     */
    private Park p7;
    /**
     * Variable that represents the park with the id 8.
     */
    private Park p8;

    public NearestParksControllerTest() {

    }

    @BeforeEach
    public void setUp() {
        pf = Mockito.mock(LocationFacade.class);
        bn = Mockito.mock(BicycleNetwork.class);
        initMocks(this);


        this.p3 = new Park(3, "Parque da Bicicletas", 10, 25, new NormalSlots(5, 10, 10), new ElectricSlot(6, 200, 20,220,50),1.0);
        this.p5 = new Park(5, "Parque das outras bicicletas", 10, 11, new NormalSlots(9, 100, 100), new ElectricSlot(10, 100, 10,220,50),1.0);
        this.p6 = new Park(6, "Outro Parque", 11, 19, new NormalSlots(11, 10, 10), new ElectricSlot(12, 100, 10,220,50),1.0);
        this.p7 = new Park(7, "Parquee das Bicicletas Portuenses", 1, 12, new NormalSlots(13, 10, 10), new ElectricSlot(14, 100, 10,220,50),1.0);
        this.p8 = new Park(8, "Parque das Bicicletas Lisboetas", 3, 9, new NormalSlots(15, 10, 10), new ElectricSlot(16, 100, 10,220,50),1.0);
    }

    @Test
    public void testGetParkRegistryWhenNoParksAreRegisted() {
        List<Park> array = npc.getParkRegistry();
        assertTrue(array.isEmpty());
    }

    @Test
    public void testGetParkRegistryWhenParksAreRegisted() {
        List<Park> a = new ArrayList<>();
        a.add(new Park(1, "teste", 1, 1, null, null,1.0));
        when(pf.getParkList()).thenReturn(a);
        List<Park> result = npc.getParkRegistry();
        assertTrue(!result.isEmpty());
        assertTrue(result.size() == 1);

    }

    @Test
    public void ensureShortestPathWithLessThan5ExistingParksReturnsListWithLessThan5() {
        System.out.println("This is a test responsible to get the shortest parks given coordinates.");
        ArrayList<Park> expResult = new ArrayList<>();
        expResult.add(p5);
        expResult.add(p8);
        expResult.add(p6);
        expResult.add(p7);
        expResult.add(p3);
        when(this.npc.getClosestFiveParksFromUser(10, 10)).thenReturn(expResult);
        ArrayList<Park> result = this.npc.getClosestFiveParksFromUser(10,10);


        assertEquals(expResult, result);

    }

    @Test
    public void ensureShortestPathWithNothingReturnsNothing(){
        System.out.println("This is a test responsible to get the shortest parks given coordinates.");
        ArrayList<Park> expResult = new ArrayList<>();

        when(this.npc.getClosestFiveParksFromUser(10, 10)).thenReturn(expResult);
        ArrayList<Park> result = this.npc.getClosestFiveParksFromUser(10,10);


        assertEquals(expResult, result);


    }

    @Test
    public void ensureInvalidDataIsThrown(){
        ArrayList<Park> expResult = new ArrayList<>();
        boolean flag = false;
        when(this.npc.getClosestFiveParksFromUser(1220, 10)).thenThrow(new InvalidDataException());
        try {

        ArrayList<Park> result = this.npc.getClosestFiveParksFromUser(1220,10);
    } catch (InvalidDataException e){
            flag = true;
        }
        boolean expFlag = true;
        assertEquals(expFlag,flag);

}
}

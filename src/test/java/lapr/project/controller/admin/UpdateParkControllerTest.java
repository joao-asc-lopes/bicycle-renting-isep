package lapr.project.controller.admin;

import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UpdateParkControllerTest {

    @InjectMocks
    private UpdateParkController ctrl;
    @Mock
    private LocationFacade pf;


    @BeforeEach
    public void setUp() {
        pf = mock(LocationFacade.class);
        initMocks(this);
    }


    @Test
    public void ensureGetParkListReturnsListWithAllParks() {

        ArrayList<Park> expected = new ArrayList<>();
        boolean flag = true;

        Park aux1 = new Park(1, "Parque do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);
        Park aux2 = new Park(2, "Parque do Alex", 11, 11, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);
        Park aux3 = new Park(3, "Parque do Leonardo", 12, 12, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);

        when(pf.registerPark("Parque do Dani", 40, -8, 10, 10, 10, 10, 1.0,220,50)).thenReturn(flag);
        when(pf.registerPark("Parque do Alex", 50, -8, 10, 10, 10, 10, 1.0,220,50)).thenReturn(flag);
        when(pf.registerPark("Parque do Leonardo",60, -8, 10, 10, 10, 10, 1.0,220,50)).thenReturn(flag);

        expected.add(aux1);
        expected.add(aux2);
        expected.add(aux3);

        when(ctrl.getParkList()).thenReturn(expected);

        List<Park> result = pf.getParkList();

        assertEquals(expected, result);

    }

    @Test
    public void ensureParksListThrowsExceptionWithInvalidData() {

        InvalidDataException expFlag = new InvalidDataException();
        String result ="";

        when(pf.getParkList()).thenThrow(expFlag);

        try{

            ctrl.getParkList();
        }

        catch(InvalidDataException e){
            result = e.getMessage();
        }
        assertEquals(expFlag.getMessage(),result);
    }

    @Test
    public void ensureGetParkListThrowsExceptionWithInvalidData() {


    }


    @Test
    public void ensureUpdateParkUpdatesExistingPark() {
        boolean expFlag = true;
        when(pf.updatePark(1,"Parque do Dani",20,10,20, 10)).thenReturn(true);
        boolean result = this.ctrl.updatePark(1,"Parque do Dani",20,10,20, 10);
        assertEquals(expFlag,result);

    }

    @Test
    public void ensureUpdateParkReturnsFalseIfParkDoesntExist() {
        boolean expFlag = false;
        when(pf.updatePark(-21,"Parque do Dani",20,10,20, 10)).thenReturn(false);
        boolean result = this.ctrl.updatePark(-21,"Parque do Dani",20,10,20, 10);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureUpdateParkReturnsFalseWithNullName() {

        boolean expFlag = false;
        when(pf.updatePark(1,"Parque do Dani",20,10,20, 10)).thenReturn(false);
        boolean result = this.ctrl.updatePark(1,null,20,10,20, 10);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureUpdateParkReturnsFalseWithEmptyName() {

        boolean expFlag = false;
        when(pf.updatePark(1,"",20,10,20, 10)).thenReturn(false);
        boolean result = this.ctrl.updatePark(1,"",20,10,20, 10);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureUpdateParkReturnsFalseWithHigherNumberFreeNormalSlots() {
        boolean expFlag = false;
        when(pf.updatePark(1,"Parque do Dani",20,21,20, 10)).thenReturn(false);
        boolean result = this.ctrl.updatePark(1,"Parque do Dani",20,21,20, 10);
        assertEquals(expFlag,result);

    }

    @Test
    public void ensureUpdateParkReturnsFalseWithInvalidNumberFreeNormalSlots() {
        boolean expFlag = false;
        when(pf.updatePark(1,"Parque do Dani",20,-1,20, 21)).thenReturn(false);
        boolean result = this.ctrl.updatePark(1,"Parque do Dani",20,-1,20, 21);
        assertEquals(expFlag,result);

    }

    @Test
    public void ensureUpdateParkReturnsFalseWithInvalidNumberFreeElectricalSlots() {
        boolean expFlag = false;
        when(pf.updatePark(1,"Parque do Dani",20,10,20, 10)).thenReturn(false);
        boolean result = this.ctrl.updatePark(1,"Parque do Dani",20,10,20, -1);
        assertEquals(expFlag,result);

    }

    @Test
    public void ensureUpdateParkReturnsFalseWithInvalidNormalBicycleCapacity() {
        boolean expFlag = false;
        when(pf.updatePark(1,"Parque do Dani",-1,10,20, 10)).thenReturn(false);
        boolean result = this.ctrl.updatePark(1,"Parque do Dani",-1,10,20, 10);
        assertEquals(expFlag,result);

    }

    @Test
    public void ensureUpdateParkReturnsFalseWithInvalidNormalElectricalCapacity() {
        boolean expFlag = false;
        when(pf.updatePark(1,"Parque do Dani",20,10,-1, 10)).thenReturn(false);
        boolean result = this.ctrl.updatePark(1,"Parque do Dani",20,10,-1, 10);
        assertEquals(expFlag,result);

    }



    @Test
    public void testGetParkRegistryWhenNoParksAreRegisted() {
        List<Park> array = this.ctrl.getParkList();
        assertTrue(array.isEmpty());
    }

    @Test
    public void testGetParkRegistryWhenParksAreRegisted() {
        List<Park> a = new ArrayList<>();
        a.add(new Park(1, "teste", 1, 1, null, null, 1.0));
        when(pf.getParkList()).thenReturn(a);
        List<Park> result = this.ctrl.getParkList();
        assertTrue(!result.isEmpty());
        assertTrue(result.size() == 1);

    }

}

package lapr.project.controller.admin;

import lapr.project.model.bikenetwork.Path;
import lapr.project.model.bikenetwork.PathFacade;
import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UpdatePathControllerTest {

    /**
     * Where we are injecting everything of the mock. Variable representative of the Add Wind Information Controller.
     */
    @InjectMocks
    private UpdatePathController ctrl;

    /**
     * Mock. Variable representative of the BicycleNetwork.
     */
    @Mock
    private PathFacade wf;

    private Park p1;
    private Park p2;
    private Park p3;

    @BeforeEach
    public void setUp() {
        this.wf = mock(PathFacade.class);
        this.ctrl = new UpdatePathController();
        initMocks(this);
        p1 = new Park(1, "Urbano", 10000000, 10, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1, 220, 50), 1.0);
        p2 = new Park(2, "Cidade", 10000000, 10, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1, 220, 50), 1.0);
        p3 = new Park(3, "Rural", 10000000, 10, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1, 220, 50), 1.0);

    }


    
    @Test


    public void testUpdatePathInformationWithInvalidIds() {
        when(wf.updatePath(100, 200, 15.0, 1, 10)).thenReturn(false);
        try {
            boolean res = ctrl.updatePath(100, 200, 15.0, 1, 10);

            assertTrue(!res);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test


    public void testUpdatePathInformationWithValidIds() {
        when(wf.updatePath(100, 200, 15.0, 1, 10)).thenReturn(true);
        try {
            boolean res = ctrl.updatePath(100, 200, 15.0, 1, 10);


            assertTrue(res);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testGetPathWhenBothIdsAreWrong() {
        when(wf.getPath(p2, p3)).thenThrow(new IllegalArgumentException());
        try {
            Path wi = ctrl.getPath(p2, p3);
            assertTrue(wi == null);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testGetPathWhenInitialParkIdIsWrong() {
        when(wf.getPath(p2, p1)).thenThrow(new IllegalArgumentException());
        try {
            Path wi = ctrl.getPath(p2, p1);
            assertTrue(wi == null);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testGetPathWhenFinalParkIdIsWrong() {
        when(wf.getPath(p1, p3)).thenThrow(new IllegalArgumentException());
        try {
            Path wi = ctrl.getPath(p1, p3);
            assertTrue(wi == null);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test


    public void testGetPathWhenIdsAreCorrect() {
        when(wf.getPath(p1, p2)).thenReturn(new Path(15.0, 10, 10));
        try {
            Path wi = ctrl.getPath(p1, p2);
            assertTrue(wi != null);
        } catch (IllegalArgumentException e) {


            assertTrue(false);
        }
    }

    @Test
    public void testGetPathWithWrongData(){
        when(wf.getPath(p1,p2)).thenThrow(new IllegalArgumentException());
        Path result = ctrl.getPath(p1,p2);
        assertTrue(result==null);
    }

    @Test
    public void testGetPathWithCoorectData(){
        when(wf.getPath(p2,p1)).thenReturn(new Path(10,10,10,10));
        Path result = ctrl.getPath(p2,p1);
        assertTrue(result!=null);
    }
}

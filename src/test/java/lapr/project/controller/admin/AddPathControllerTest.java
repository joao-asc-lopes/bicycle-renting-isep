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

public class AddPathControllerTest {

    /**
     * Where we are injecting everything of the mock. Variable representative of the Add Wind Information Controller.
     */
    @InjectMocks
    private AddPathController ctrl;

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
        this.ctrl = new AddPathController();
        initMocks(this);
        p1 = new Park(1, "Urbano", 10000000, 10, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1, 220, 50), 1.0);
        p2 = new Park(2, "Cidade", 10000000, 10, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1, 220, 50), 1.0);
        p3 = new Park(3, "Rural", 10000000, 10, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1, 220, 50), 1.0);

    }


    @Test
    public void testGetWindInformationWhenBothIdsAreWrong() {
        when(wf.getPath(p2, p3)).thenThrow(new IllegalArgumentException());
        try {
            Path wi = ctrl.getPath(p2, p3);
            assertTrue(wi == null);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testGetWindInformationWhenInitialParkIdIsWrong() {
        when(wf.getPath(p2, p1)).thenThrow(new IllegalArgumentException());
        try {
            Path wi = ctrl.getPath(p2, p1);
            assertTrue(wi == null);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testGetWindInformationWhenFinalParkIdIsWrong() {
        when(wf.getPath(p1, p3)).thenThrow(new IllegalArgumentException());
        try {
            Path wi = ctrl.getPath(p1, p3);
            assertTrue(wi == null);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test


    public void testGetWindInformationWhenIdsAreCorrect() {
        when(wf.getPath(p1, p2)).thenReturn(new Path(15.0, 10, 10));
        try {
            Path wi = ctrl.getPath(p1, p2);
            assertTrue(wi != null);
        } catch (IllegalArgumentException e) {


            assertTrue(false);
        }
    }

    @Test


    public void testAddWindInformationWithInvalidIds() {
        when(wf.addPath(42, -8, 42, -9, 100, 1, 15.0)).thenReturn(false);
        try {
            boolean res = ctrl.addPath(42, -8, 42, -9, 100, 1, 0);


            assertTrue(!res);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test


    public void testAddWindInformationWithValidIds() {
        when(wf.addPath(42, -8, 42, -9, 15, 1, 10)).thenReturn(true);
        try {
            boolean res = ctrl.addPath(42, -8, 42, -9, 15, 1, 10);
            assertTrue(res);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }
}
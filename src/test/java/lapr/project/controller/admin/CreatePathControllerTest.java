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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreatePathControllerTest {
    @InjectMocks
    private CreatePathController cpc;

    @Mock
    private PathFacade pf;

    /**
     * Variable that represents the park with the id 1.
     */

    private Park p1;
    /**
     * Variable that represents the park with the id 2.
     */
    private Park p2;

    @BeforeEach
    public void setUp() {
        cpc = new CreatePathController();
        this.p1 = new Park(1, "Parque do Dani", 30, 40, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);
        this.p2 = new Park(2, "Parque do parque", 40, 51, new NormalSlots(2,220,10), new ElectricSlot(4, 100, 10,220,50), 100.0);
        Path path = new Path(1,1,1,1);
        initMocks(this);
    }

    @Test
    void ensureCreatePathReturnsFalseIfPathAlreadyExists() {
        when(this.pf.addPath(p1.getLatitude(),p1.getLongitude(),p2.getLatitude(), p2.getLongitude(), 1,1,1)).thenReturn(false);
        boolean result = this.cpc.createPath(p1.getLatitude(),p1.getLongitude(),p2.getLatitude(), p2.getLongitude(), 1,1,1);
        assertFalse(result);
    }

    @Test
    void ensureCreatePathReturnsTrueIfDataIsCorrect() {
        when(this.pf.addPath(p1.getLatitude(),p1.getLongitude(),p2.getLatitude(), p2.getLongitude(), 1,1,1)).thenReturn(true);
        boolean result = this.cpc.createPath(p1.getLatitude(),p1.getLongitude(),p2.getLatitude(), p2.getLongitude(), 1,1,1);
        assertTrue(result);
    }

}
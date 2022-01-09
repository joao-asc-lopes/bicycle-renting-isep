package lapr.project.model.bikenetwork;

import lapr.project.data.LocationDao;
import lapr.project.data.PathDao;
import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.InterestPoint;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PathServiceTest {
    @Mock
    private PathDao wid;

    @Mock
    private LocationDao ld;
    private Park p1;

    private Park p2;
    @InjectMocks
    private PathService wis;

    @BeforeEach
    public void setUp() {
        wid = Mockito.mock(PathDao.class);
        ld = Mockito.mock(LocationDao.class);
        wis = new PathService();
        initMocks(this);

        p1 = new Park(1, "Urbano", 10000000, 10, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1, 220, 50), 1.0);
        p2 = new Park(2, "Cidade", 10000000, 10, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1, 220, 50), 1.0);

    }


    @Test
    public void testAddWindInformationWithInvalidIds() {
        when(ld.getLocationByCoordinates(42, -8)).thenThrow(new IllegalArgumentException());
        try {
            boolean res = wis.addPath(42, -8, 42, -9, 10, 1, 10);


            assertTrue(!res);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddWindInformationWithValidIds() {
        Park p1 = new Park(10000, "Trindade", 42, -8, new NormalSlots(10000, 10, 10),
                new ElectricSlot(10001, 10, 10, 40, 30), 10);
        Park p2 = new Park(10006, "Casa da Música", 42, -9, new NormalSlots(10002, 10, 10),
                new ElectricSlot(10003, 10, 10, 40, 30), 10);
        Path p = new Path(10, 1, 10);
        when(ld.getLocationByCoordinates(42, -8)).thenReturn(p1);
        when(ld.getLocationByCoordinates(42, -9)).thenReturn(p2);
        when(wid.addPath(p, p1, p2)).thenReturn(true);
        try {
            boolean res = wis.addPath(42, -8, 42, -9, 10, 1, 10);


            assertTrue(true);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testUpdateWindInformationWithInvalidIds() {
        when(wid.updatePath(100, 200, 15.0, 1, 10)).thenReturn(false);
        try {
            boolean res = wis.updatePath(100, 200, 15.0, 1, 10);


            assertTrue(!res);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testUpdateWindInformationWithValidIds() {
        when(wid.updatePath(1, 2, 15.0, 1, 10)).thenReturn(true);
        try {
            boolean res = wis.updatePath(1, 2, 15.0, 1, 10);


            assertTrue(res);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void ensureAddPathReturnsTrue() {
        Park park = new Park(1, "Le Park", 10, 10, new NormalSlots(1, 1, 1), new ElectricSlot(2, 1, 1, 1, 1), 1);
        Park park2 = new Park(2, "The Pakr", 11, 11, new NormalSlots(3, 1, 1), new ElectricSlot(4, 1, 1, 1, 1), 2);
        Path pa = new Path(10, 10, 10);

        when(this.ld.getLocationByCoordinates(10, 10)).thenReturn(park);
        when(this.ld.getLocationByCoordinates(11, 11)).thenReturn(park2);
        when(this.wid.addPath(pa, park, park2)).thenReturn(false);
        boolean result = this.wis.addPath(10, 10, 11, 11, 10, 10, 10);
        assertEquals(false, result);
    }

    @Test
    public void ensureReturnParkByCoordinatesReturnsPark() {
        Park park = new Park(1, "Le Park", 10, 10, new NormalSlots(1, 1, 1), new ElectricSlot(2, 1, 1, 1, 1), 1);
        when(this.ld.getLocationByCoordinates(10, 10)).thenReturn(park);
        Park result = this.wis.returnParkByCoordinates(10, 10);
        assertEquals(park, result);

    }

    @Test
    public void ensureReturnParkThrowsException() {
        InterestPoint ip = new InterestPoint(2, "Dan", 10, 10, 1);
        when(this.ld.getLocationByCoordinates(10, 10)).thenThrow(InvalidDataException.class);
        boolean flag = false;
        try {
            Park result = this.wis.returnParkByCoordinates(10, 10);
        } catch (InvalidDataException e) {
            flag = true;
        }
        assertEquals(true, flag);
    }

    @Test
    public void ensureGetPathWorks() {
        Path path = new Path(10, 10, 10);
        when(this.wid.getPath(p1, p2)).thenReturn(path);
        Path pathResult = this.wis.getPath(p1, p2);
        assertEquals(path, pathResult);
    }
}

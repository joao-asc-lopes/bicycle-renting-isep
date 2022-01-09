package lapr.project.model.bikenetwork;

import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PathFacadeTest {

    @Mock
    private PathService wis;

    @InjectMocks
    private PathFacade wif;
    private Park p1;
    private Park p2;

    @BeforeEach
    public void setUp() {
        wis = Mockito.mock(PathService.class);
        wif = new PathFacade();
        initMocks(this);
        p1 = new Park(1, "Urbano",  10000000, 10, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1,220,50), 1.0);
        p2 = new Park(2, "Cidade",  10000000, 10, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1,220,50), 1.0);

    }


    @Test
    public void testAddWindInformationWithInvalidIds(){
        when(wis.addPath(42,-8,42,-9,100,1,15.0)).thenReturn(false);
        try{
            boolean res = wif.addPath(42,-8,42,-9,100,1,15.0);


        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testAddWindInformationWithValidIds(){
        when(wis.addPath(42,-8,42,-9,100,1,15.0)).thenReturn(true);
        try{
            boolean res = wif.addPath(42,-8,42,-9,100,1,15.0);


            assertTrue(res);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testUpdateWindInformationWithInvalidIds(){
        when(wis.updatePath(100,200,10,1,10)).thenReturn(false);
        try{
            boolean res = wif.updatePath(100,200,10,1,10);


            assertTrue(!res);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testUpdateWindInformationWithValidIds(){
        when(wis.updatePath(100,200,10,1,10)).thenReturn(true);
        try{
            boolean res = wif.updatePath(100,200,10,1,10);


            assertTrue(res);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }

    @Test
    public void ensureGetPathWorks(){
        Path path = new Path(10,10,10);
        when(this.wis.getPath(p1,p2)).thenReturn(path);
        Path result = this.wif.getPath(p1,p2);
        assertEquals (path,result);
    }

    @Test
    public void ensureAddPathReturnsTrue(){
        when(this.wis.addPath(10,10,10,10,1,1,1)).thenReturn(true);
        boolean result = this.wif.addPath(10,10,10,10,1,1,1);
        assertEquals(true,result);
    }
    @Test
    public void ensureAddPathReturnsFalse(){
        when(this.wis.addPath(10,10,10,10,1,1,1)).thenReturn(false);
        boolean result = this.wif.addPath(10,10,10,10,1,1,1);
        assertEquals(false,result);
    }
}


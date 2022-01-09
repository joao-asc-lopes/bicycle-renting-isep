package lapr.project.model.bikenetwork;

import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.Location;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class RouteTest {

    private Route emptyRoute;

    private Route route;

    private LinkedList<Location> lista;

    @BeforeEach
    public void setUp(){
        emptyRoute = new Route();
        lista = new LinkedList<>();
        lista.add(new Park(1,"Trindade", 42,-8,new NormalSlots(1,10,10),
                new ElectricSlot(2,10,10,40,30),30));
        lista.add(new Park(2,"Casa da Música", 42,-9,new NormalSlots(3,10,10),
                new ElectricSlot(4,10,10,40,30),30));
        route = new Route(lista);
    }


    @Test
    public void testAddLocation(){
        Park expected = new Park(1,"Trindade", 42,-8,new NormalSlots(1,10,10),
                new ElectricSlot(2,10,10,40,30),30);
        emptyRoute.addLocation(expected);
        Iterable<Location> it = emptyRoute.getPath();
        Location result = it.iterator().next();
        assertTrue(expected.equals(result));
    }

    @Test
    public void testTotalDistance(){
        emptyRoute.setTotalDistance(100);
        double distance = emptyRoute.getTotalDistance();
        assertTrue(Double.compare(100,distance)==0);
    }

    @Test
    public void testElevation(){
        route.setElevation(90);
        double elevation = route.getElevation();
        assertTrue(Double.compare(90,elevation)==0);
    }

    @Test
    public void testTotalEnergy(){
        route.setTotalEnergy(80);
        double energy = route.getTotalEnergy();
        assertTrue(Double.compare(80,energy)==0);
    }

    @Test
    public void testAddLocations(){
        emptyRoute.addLocations(lista);
        Iterator<Location> it = emptyRoute.getPath().iterator();
        Iterator<Location> exp = lista.iterator();
        while(it.hasNext()||exp.hasNext()){
            assertTrue(it.next().equals(exp.next()));
        }
        if(it.hasNext()||exp.hasNext()){
            fail();
        }
    }

    @Test
    public void testCompareTo(){
        route.setTotalDistance(100);
        emptyRoute.setTotalDistance(90);
        assertTrue(route.compareTo(emptyRoute)>0);
        emptyRoute.setTotalDistance(110);
        assertTrue(route.compareTo(emptyRoute)<0);
        emptyRoute.setTotalDistance(100);
        assertTrue(route.compareTo(emptyRoute)==0);
    }

    @Test
    public void testCompareEnergy(){
        route.setTotalEnergy(100);
        emptyRoute.setTotalEnergy(90);
        assertTrue(route.compareEnergy(emptyRoute)>0);
        emptyRoute.setTotalEnergy(110);
        assertTrue(route.compareEnergy(emptyRoute)<0);
        emptyRoute.setTotalEnergy(100);
        assertTrue(route.compareEnergy(emptyRoute)==0);
    }

    @Test
    public void testEquals(){
        assertTrue(emptyRoute.equals(emptyRoute));
        assertTrue(!emptyRoute.equals(null));
        assertTrue(!emptyRoute.equals(""));
        emptyRoute.setTotalDistance(route.getTotalDistance());
        emptyRoute.setTotalEnergy(route.getTotalEnergy());
        emptyRoute.setElevation(route.getElevation());
        emptyRoute.addLocations(lista);
        assertTrue(emptyRoute.equals(route));
    }

    @Test
    public void testHashCode(){
        assertTrue(emptyRoute.hashCode()==953312);
    }
}

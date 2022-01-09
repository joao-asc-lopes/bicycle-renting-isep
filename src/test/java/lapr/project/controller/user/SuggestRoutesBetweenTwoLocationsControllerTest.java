package lapr.project.controller.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.RoadBicycle;
import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.bikenetwork.Route;
import lapr.project.model.park.*;
import lapr.project.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class SuggestRoutesBetweenTwoLocationsControllerTest {

    @InjectMocks
    private SuggestRoutesBetweenTwoLocationsController srbc;
    @Mock
    private BicycleNetwork bn;
    private Park p1;
    private Park p2;
    private User u1;
    private Bicycle bike;


    @BeforeEach
    public void setUp() {
        bn = mock(BicycleNetwork.class);
        this.u1 = new User("DANIEL", "user@gmail.com", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false, 15.00, 0);
        this.bike = new RoadBicycle("1", Bicycle.BicycleStatus.AVAILABLE, 10, 0.1, 0.5);
        this.p1 = new Park(3, "Parque da Bicicletas", 10, 25, new NormalSlots(5, 10, 10), new ElectricSlot(6, 200, 20, 220, 50), 1.0);
        this.p2 = new Park(5, "Parque das outras bicicletas", 10, 11, new NormalSlots(9, 100, 100), new ElectricSlot(10, 100, 10, 220, 50), 1.0);


        initMocks(this);
    }

    @Test
    public void ensureSuggestRoutesReturnsThreeRoutesWhenListOnlyHas5InterestPoints() {
        List<Location> lstIps = new ArrayList<>();
        InterestPoint ip1 = new InterestPoint(1, "Piolho", 41.14692703030618, -8.61651062965393, 10);
        InterestPoint ip2 = new InterestPoint(2, "Hospital Santo Antonio", 41.14666042016164, -8.620244264602661, 10);
        lstIps.add(ip1);
        lstIps.add(ip2);
        Route expected = new Route();
        expected.addLocation(p1);
        expected.addLocation(ip1);
        expected.addLocation(ip2);
        expected.addLocation(p2);
        List<Route> expectedList = new ArrayList<>();
        expectedList.add(expected);
        when(bn.suggestRouteBetweenTwoLocations(p1, p2, bike, u1, 3, true, "energy", lstIps)).thenReturn(expectedList);
        List<Route> result = srbc.suggestRouteBetweenTwoLocations(p1, p2, bike, u1, 3, true, "energy", lstIps);
        assertEquals(expectedList.get(0).getPath(), result.get(0).getPath());
    }


}
   
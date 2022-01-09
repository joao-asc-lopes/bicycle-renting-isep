package lapr.project.controller.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.BicycleFacade;
import lapr.project.model.bicycle.MountainBicycle;
import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import lapr.project.model.user.User;
import lapr.project.model.user.UserFacade;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UnlockBicycleControllerTest {

    @Mock
    private LocationFacade pf;

    @Mock
    private UserFacade uf;

    @Mock
    private BicycleFacade bf;

    private User u;

    @InjectMocks
    private UnlockBicycleController ubc;

    @BeforeEach
    public void setUp() {
        u = new User("teste", "teste@gmail.com", "teste", "teste",0,0,0,false,0,0);
        pf = mock(LocationFacade.class);
        uf = mock(UserFacade.class);
        bf = mock(BicycleFacade.class);
        initMocks(this);
    }

    @Test
    public void ensureGetAllParksReturnsListWithAllParks() {
        Park aux1 = new Park(1, "Parque do Dani", 10, 10, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);
        Park aux2 = new Park(2, "Parque do Alex", 11, 11, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);
        Park aux3 = new Park(1, "Parque do Leonardo", 12, 12, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10,220,50), 1.0);

        ArrayList<Park> expResult = new ArrayList<>();
        expResult.add(aux1);
        expResult.add(aux2);
        expResult.add(aux3);

        when(pf.getParkList()).thenReturn(expResult);

        List<Park> result = ubc.getParkList();

        assertEquals(expResult, result);
    }

    @Test
    public void testGetAllAvailableBicyclesReturnsListWithAllAvailableBicycles() {
        Bicycle b = new MountainBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0);
        List<Bicycle> expected = new ArrayList<>();
        expected.add(b);
        when(pf.getAvailableBicycles(1, Bicycle.BicycleType.MOUNTAIN)).thenReturn(expected);
        List<Bicycle> result = ubc.getAvailableBicycles(1, Bicycle.BicycleType.MOUNTAIN);
        assertEquals(result, expected);
    }

    @Test
    public void ensureTestAvailableMountainBikesThrowsExceptionWhenSizeIsZero() {
        List<Bicycle> expected = new ArrayList<>();
        when(pf.getAvailableBicycles(1, Bicycle.BicycleType.MOUNTAIN)).thenReturn(expected);
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            ubc.getAvailableBicycles(1, Bicycle.BicycleType.MOUNTAIN);
        });
        assertEquals("There are no available Mountain bicycles for the selected park.", e2.getMessage());
    }

    @Test
    public void ensureTestAvailableRoadBikesThrowsExceptionWhenSizeIsZero() {
        List<Bicycle> expected = new ArrayList<>();
        when(pf.getAvailableBicycles(1, Bicycle.BicycleType.ROAD)).thenReturn(expected);
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            ubc.getAvailableBicycles(1, Bicycle.BicycleType.ROAD);
        });
        assertEquals("There are no available Road bicycles for the selected park.", e2.getMessage());
    }

    @Test
    public void ensureTestAvailableEletricBikesThrowsExceptionWhenSizeIsZero() {
        List<Bicycle> expected = new ArrayList<>();
        when(pf.getAvailableBicycles(1, Bicycle.BicycleType.ELECTRIC)).thenReturn(expected);
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            ubc.getAvailableBicycles(1, Bicycle.BicycleType.ELECTRIC);
        });
        assertEquals("There are no available Electric bicycles for the selected park.", e2.getMessage());
    }

    @Test
    public void ensureUnlockBicycleThrowsNoExceptionIfBikeIsValid() {
        User loggedUser = new User("Daniel","user@service.pt", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        NormalSlots ns = new NormalSlots(1,10,10);
        ElectricSlot es = new ElectricSlot(2,10,10,220,50);
        Park park = new Park(1,"Trindade",42,-8,ns,es,15);
        when(pf.getParkById(1)).thenReturn(park);
        doNothing().when(bf).unlockBicycle("IDteste", Bicycle.BicycleType.MOUNTAIN);
        doNothing().when(uf).createRental("IDteste", park,loggedUser);
        SessionController sc = Mockito.mock(SessionController.class);
        ubc.setCurrentSession(sc);
        when(sc.getLoggedUser()).thenReturn(loggedUser);
        ubc.unlockBicycle("IDteste",this.u);
    }

    @Test
    public void ensureUnlockBicycleThrowsExceptionIfBikeIsInvalid() {
        NormalSlots ns = new NormalSlots(1,10,10);
        ElectricSlot es = new ElectricSlot(2,10,10,220,50);
        Park park = new Park(1,"Trindade",42,-8,ns,es,15);
        Mockito.doThrow(new InvalidDataException("The bicycle is no longer available.")).when(bf).unlockBicycle("IDteste", Bicycle.BicycleType.MOUNTAIN);
        List<Bicycle> bikeList = new ArrayList<>();
        bikeList.add(new MountainBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, 1,10,5.0));
        when(pf.getParkById(1)).thenReturn(park);
        when(pf.getAvailableBicycles(1, Bicycle.BicycleType.MOUNTAIN)).thenReturn(bikeList);
        ubc.getAvailableBicycles(1, Bicycle.BicycleType.MOUNTAIN);
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            ubc.unlockBicycle("IDteste",this.u);
        });
        assertEquals("The bicycle is no longer available.", e2.getMessage());
    }

}

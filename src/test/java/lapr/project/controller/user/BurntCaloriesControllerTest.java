/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.MountainBicycle;
import lapr.project.model.bicycle.RoadBicycle;
import lapr.project.model.bikenetwork.Path;
import lapr.project.model.bikenetwork.PathFacade;
import lapr.project.model.park.*;
import lapr.project.model.user.User;
import lapr.project.utils.InvalidDataException;
import lapr.project.utils.PhysicsAlgorithms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BurntCaloriesControllerTest {
    @Mock
    private LocationFacade pf;

    @Mock
    private PathFacade pff;

    @Mock
    private PhysicsAlgorithms pa;

    @InjectMocks
    private BurntCaloriesController bnc;

    private Location p1;
    private Location p2;
    private Location p3;

    public BurntCaloriesControllerTest() {
    }

    @BeforeEach
    public void setUp() {
        pff = Mockito.mock(PathFacade.class);
        pf = Mockito.mock(LocationFacade.class);
        bnc = new BurntCaloriesController();
        initMocks(this);
        p1 = new Park(1, "Urbano", 10000000, 10, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1, 220, 50), 1.0);
        p2 = new Park(2, "Cidade", 10000000, 10, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1, 220, 50), 1.0);
        p3 = new Park(3, "Rural", 10000000, 10, new NormalSlots(1, 1, 1), new ElectricSlot(1, 1, 1, 220, 50), 1.0);
        pa = new PhysicsAlgorithms();
    }

    @Test
    public void testGetParkRegistryWhenNoParksAreRegisted() {
        List<Park> array = bnc.getParkRegistry();
        assertTrue(array.isEmpty());
    }

    @Test
    public void testGetParkRegistryWhenParksAreRegisted() {
        ArrayList<Park> a = new ArrayList<>();
        a.add(new Park(1, "teste", 1, 1, null, null, 1.0));
        when(pf.getParkList()).thenReturn(a);
        List<Park> result = bnc.getParkRegistry();
        assertTrue(!result.isEmpty());
        assertTrue(result.size() == 1);
    }

    @Test
    public void testGetParkByIdWhenIdIsWrong() {
        when(pf.getParkById(2)).thenThrow(new InvalidDataException());
        Park p = bnc.getParkById(2);
        assertTrue(p == null);
    }

    @Test
    public void testGetParkByIdWhenIsIsCorrect() {
        Park x = new Park(1, "teste", 1, 1, null, null, 1.0);
        when(pf.getParkById(1)).thenReturn(x);
        Park result = bnc.getParkById(1);
        assertTrue(result != null);
    }

    @Test
    public void testCalculateBurntCaloriesWhenParametersAreWithInvalidData() throws IOException {

        User user = new User("Daniel", "da@gmail.com", "test123", "Dani", 1, 1, 1, true, 15.00, 1);
        MountainBicycle bike1 = new MountainBicycle("IDteste", Bicycle.statusByCode(2), 1, 10, 5.0);

        double result = bnc.calculateBurntCalories(-1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

        double expResult = -1;
        assertEquals(expResult, result);


    }

    @Test
    public void testCalculateBurntCaloriesWhenParameteresAreWithValidData() throws IOException {


        double expected = 1366700.0355871227;


        double result = bnc.calculateBurntCalories(12, 10, 5, 50, 1.76, 10, 1, 11, 1, 1, 1, 1, 1, 1);

        assertEquals(expected, result);


    }

    @Test
    public void testCalculateBurntCaloriesWhenParameteresAreWithInvalidData() throws IOException {
        RoadBicycle bike2 = new RoadBicycle("IDteste", Bicycle.statusByCode(3), 1, 10, 5.0);
        double expected = -1;
        User u = new User("Daniel", "teste@teste.com", "teste", "teste", 50.0f, 1.75f, 1144556677, false, 15.00, 0);


        double result = this.bnc.calculateBurntCalories(-1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);


        assertTrue(result == expected);
    }

    @Test
    public void ensureGetsPathWith2Ids() {
        Path path = new Path(1, 1, 1);
        when(this.pff.getPath(p1, p2)).thenReturn(path);
        Path result = this.bnc.getPathBy2Parks(p1, p2);
        assertEquals(path, result);
    }

}

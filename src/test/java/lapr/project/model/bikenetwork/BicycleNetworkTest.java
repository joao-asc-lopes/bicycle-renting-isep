package lapr.project.model.bikenetwork;

import lapr.project.model.bicycle.Battery;
import lapr.project.model.bicycle.Bicycle.BicycleStatus;
import lapr.project.model.bicycle.BicycleService;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.park.*;
import lapr.project.model.user.User;
import lapr.project.model.user.UserService;
import lapr.project.utils.InvalidDataException;
import lapr.project.utils.PhysicsAlgorithms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class BicycleNetworkTest {
    @InjectMocks
    private PhysicsAlgorithms p;
    /**
     * Variable that represents an instance of Bicycle Network.
     */
    private BicycleNetwork bn;
    /**
     * Variable that represents the parkList that is in Bicycle Network
     */

    private ArrayList<Park> pList;
    /**
     * Variable that represents the park with the id 1.
     */

    private Park p1;
    /**
     * Variable that represents the park with the id 2.
     */
    private Park p2;
    /**
     * Variable that represents the park with the id 3.
     */
    private Park p3;
    /**
     * Variable that represents the park with the id 4.
     */
    private Park p4;
    /**
     * Variable that represents the park with the id 5.
     */
    private Park p5;
    /**
     * Variable that represents the park with the id 6.
     */
    private Park p6;
    /**
     * Variable that represents the park with the id 7.
     */
    private Park p7;
    /**
     * Variable that represents the park with the id 8.
     */
    private Park p8;


    /**
     * Variable that represents a valid park.
     */
    private Park p9;

    /**
     * Valid that represents a valid park.
     */
    private Park p10;
    private Path path;
    private User loggedUser;

    private ElectricBicycle chosenBike;

    private ElectricBicycle bike2;
    private ElectricBicycle bike3;
    private ElectricBicycle bike4;

    private ArrayList<ElectricBicycle> bikeList;

    private User u1;
    private User u2;


    /**
     * Variable that represents a correct latitude.
     */

    private double correctLatitude;
    /**
     * Variable that represents a correct longitude.
     */
    private double correctLongitude;
    /**
     * Variable that represents a correct type of latitude.
     */
    private double correctLatitude2;
    /**
     * Variable that represents a correct longitude.
     */
    private double correctLongitude2;

    @InjectMocks
    private BicycleNetwork bn2;

    @Spy
    private PhysicsAlgorithms pa;
    @Mock
    private UserService us;
    @Mock
    private BicycleService bikeS;
    @Mock
    private LocationService ps;
    @Mock
    private PathService wis;

    private String email;
    private Battery bat;

    private Park scenarioPark1;
    private Park scenarioPark2;
    private Park scenarioPark3;

    private InterestPoint scenarioInterestPoint1;
    private InterestPoint scenarioInterestPoint2;
    private InterestPoint scenarioInterestPoint3;
    private InterestPoint scenarioInterestPoint4;
    private Path emptyPath;

    @BeforeEach
    public void setUp() {
        emptyPath = new Path(0, 0, 0);
        scenarioPark1 = new Park(1, "Trindade", 41.15227, -8.60929, new NormalSlots(1, 10, 8), new ElectricSlot(2, 5, 3, 100, 10), 104);
        scenarioPark2 = new Park(2, "Cais da Ribeira", 41.14063, -8.61118, new NormalSlots(1, 10, 8), new ElectricSlot(2, 5, 3, 100, 10), 25);
        scenarioPark3 = new Park(3, "Castelo do Queijo", 41.16875, -8.68995, new NormalSlots(1, 10, 8), new ElectricSlot(2, 5, 3, 100, 10), 4);
        scenarioInterestPoint1 = new InterestPoint(4, "Clerigos", 41.14582, -8.61398, 87);
        scenarioInterestPoint2 = new InterestPoint(5, "Majestic", 41.14723, -8.60657, 91);
        scenarioInterestPoint3 = new InterestPoint(6, "Bolhao", 41.14871, -8.60746, 87);
        scenarioInterestPoint4 = new InterestPoint(7, "Sé", 41.14331, -8.60914, 82);
        bikeList = new ArrayList<>();
        bat = new Battery(1, 200, 200, 52);
        this.bn = new BicycleNetwork();
        this.path = new Path(10, 10, 0.1);
        this.p1 = new Park(1, "Parque do Dani", 30, 40, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 220, 50), 1.0);
        this.p2 = new Park(2, "Parque do parque", 40, 51, new NormalSlots(2, 220, 10), new ElectricSlot(4, 100, 10, 220, 50), 100.0);
        this.p3 = new Park(3, "Parque da Bicicletas", 10, 25, new NormalSlots(5, 10, 10), new ElectricSlot(6, 200, 20, 220, 50), 50.0);
        this.p4 = new Park(4, "Parque das mini-bicicletas", 22, 31, new NormalSlots(7, 10, 10), new ElectricSlot(8, 100, 10, 220, 50), 1.0);
        this.p5 = new Park(5, "Parque das outras bicicletas", 10, 11, new NormalSlots(9, 100, 100), new ElectricSlot(10, 100, 10, 220, 50), 5.0);
        this.p6 = new Park(6, "Outro Parque", 11, 19, new NormalSlots(11, 10, 10), new ElectricSlot(12, 100, 10, 220, 50), 25.0);
        this.p7 = new Park(7, "Parquee das Bicicletas Portuenses", 1, 12, new NormalSlots(13, 10, 10), new ElectricSlot(14, 100, 10, 220, 50), 1.0);

        this.p8 = new Park(8, "Parque das Bicicletas Lisboetas", 1.00001, 9, new NormalSlots(15, 10, 10), new ElectricSlot(16, 100, 10, 220, 50), 1.0);
        this.p9 = new Park(9, "Parque de Aveiro", 1, 9, new NormalSlots(17, 10, 10), new ElectricSlot(18, 100, 10, 220, 50), 1.0);


        pList = new ArrayList<>();
        pList.add(p1);
        pList.add(p2);
        pList.add(p3);
        pList.add(p4);
        pList.add(p5);
        pList.add(p6);
        pList.add(p7);
        pList.add(p8);

        bike2 = new ElectricBicycle("IDteste", BicycleStatus.AVAILABLE, new Battery(1, 200, 150, 50), 6, 0.01, 5.0);


        bikeList.add(bike2);

        this.u1 = new User("Daniel", "user@gmail.com", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false, 15.00, 0);
        this.u2 = new User("Daniel", "use2@gmail.com", "mynewpassword123", "Anna", (float) 0.0, (float) 1.80, (long) 1111222233334444.0, false, 15.00, 0);


        this.correctLatitude = 47.6788206;
        this.correctLongitude = -122.3271205;
        this.correctLatitude2 = 47.6788206;
        this.loggedUser = new User("Daniel", "someemail@email.com", "password", "salt", 1, 1, 1, false, 10, 0);
        this.correctLongitude2 = -122.5271205;
        this.chosenBike = new ElectricBicycle("1", BicycleStatus.AVAILABLE, bat, 1, 10, 10);

        email = "user@service.pt";
        pa = new PhysicsAlgorithms();
        bikeS = mock(BicycleService.class);
        us = mock(UserService.class);
        ps = mock(LocationService.class);
        pa = spy(PhysicsAlgorithms.class);
        wis = mock(PathService.class);
        initMocks(this);
        when(wis.getPath(p1, p2)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p1, p3)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p1, p4)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p1, p5)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p1, p6)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p1, p7)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p1, p8)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p1, p9)).thenReturn(new Path(10, 10, 1));

        when(wis.getPath(p2, p1)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p2, p3)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p2, p4)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p2, p5)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p2, p6)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p2, p7)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p2, p8)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p2, p9)).thenReturn(new Path(10, 10, 1));

        when(wis.getPath(p3, p1)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p3, p2)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p3, p4)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p3, p5)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p3, p6)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p3, p7)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p3, p8)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p3, p9)).thenReturn(new Path(10, 10, 1));

        when(wis.getPath(p4, p1)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p4, p2)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p4, p3)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p4, p5)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p4, p6)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p4, p7)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p4, p8)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p4, p9)).thenReturn(new Path(10, 10, 1));

        when(wis.getPath(p5, p1)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p5, p2)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p5, p3)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p5, p4)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p5, p6)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p5, p7)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p5, p8)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p5, p9)).thenReturn(new Path(10, 10, 1));

        when(wis.getPath(p6, p1)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p6, p2)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p6, p3)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p6, p4)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p6, p5)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p6, p7)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p6, p8)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p6, p9)).thenReturn(new Path(10, 10, 1));

        when(wis.getPath(p7, p1)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p7, p2)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p7, p3)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p7, p4)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p7, p5)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p7, p6)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p7, p8)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p7, p9)).thenReturn(new Path(10, 10, 1));

        when(wis.getPath(p8, p1)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p8, p2)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p8, p3)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p8, p4)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p8, p5)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p8, p6)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p8, p7)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p8, p9)).thenReturn(new Path(10, 10, 1));

        when(wis.getPath(p9, p1)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p9, p2)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p9, p3)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p9, p4)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p9, p5)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p9, p6)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p9, p7)).thenReturn(new Path(10, 10, 1));
        when(wis.getPath(p9, p8)).thenReturn(new Path(10, 10, 1));
    }

    @Test
    public void ensureReturnShortestPathsFromUserWorks() {
        System.out.println("This is a test responsible to get the shortest parks given coordinates.");
        this.bn2.addLocation(p1);
        this.bn2.addLocation(p2);
        this.bn2.addLocation(p3);
        this.bn2.addLocation(p4);
        this.bn2.addLocation(p5);
        this.bn2.addLocation(p6);
        this.bn2.addLocation(p7);
        this.bn2.addLocation(p8);
        ArrayList<Park> result = this.bn2.shortestParksFromUser(10, 10);// User with these coordinates.
        ArrayList<Park> expResult = new ArrayList<>();
        expResult.add(p5);
        expResult.add(p6);
        expResult.add(p8);
        expResult.add(p7);
        expResult.add(p3);
        assertEquals(expResult, result);
    }

    @Test
    public void ensureReturnsShortestPathWithLessThan5ExistingParksReturnsListWithLessThan5Elements() {
        System.out.println("Test to the shortest parks from user.");
        this.bn2.addLocation(p5);
        this.bn2.addLocation(p6);
        this.bn2.addLocation(p7);
        this.bn2.addLocation(p8);
        ArrayList<Park> result = this.bn2.shortestParksFromUser(10, 10);
        ArrayList<Park> expResult = new ArrayList<>();
        expResult.add(p5);
        expResult.add(p6);
        expResult.add(p8);
        expResult.add(p7);
        assertEquals(expResult, result);

    }

    @Test
    public void ensureThatReturnShortestParksWithNoExistingParksReturnsAnEmptyList() {
        System.out.println("Ensure that no existing parks in the system returns a list with no elements.");
        ArrayList<Park> result = this.bn.shortestParksFromUser(10, 10);
        ArrayList<Park> expResult = new ArrayList<>();
        assertEquals(expResult, result);
    }

    @Test
    public void ensureThatAParkWithAnIncorrectLongitudeThrowsException() {
        System.out.println("Ensure that trying to stick a park with an incorrect longitude throws an exception.");
        boolean flag = true;
        this.bn.addLocation(p9);
        try {
            ArrayList<Park> result = this.bn.shortestParksFromUser(10, 10000000);
            ArrayList<Park> expResult = new ArrayList<>();
            expResult.add(p9);
        } catch (InvalidDataException e) {
            flag = false;
        }
        boolean expFlag = false;
        assertEquals(expFlag, flag);
    }

    @Test
    public void ensureThatDistanceParkUserWorksCorrectly() { // More tests about this method in the Physics Algorithms.
        System.out.println("Ensure that the distance of the user to the park works correcly!");
        this.p1.setLatitude(this.correctLatitude2);
        this.p1.setLongitude(this.correctLongitude2);
        double result = this.bn.distanceUserPark(this.correctLatitude, this.correctLongitude, p1);
        double expectedResult = 14973.190481586224;
        assertEquals(expectedResult, result);

    }


    @Test
    public void ensureSuggestBikeThrowsExceptionIfThereAreNoElectricBicycles() throws IOException {
        when(this.bikeS.getElectricBicyclesList()).thenReturn(new ArrayList<>());
        IOException e2 = assertThrows(IOException.class, () -> {
            this.bn2.suggestBike(this.p1, this.p2, this.loggedUser);
        });
        assertEquals("There are no suitable bicycles for this scenario.", e2.getMessage());
    }

    @Test
    public void ensureSuggestBikeReturnsCorrectBicycleIfEverythingIsCorrect() throws IOException {
        ElectricBicycle expected = this.bike2;

        Path wind = new Path(1, 1, 1);
        doReturn(wind).when(this.wis).getPath(p8, p9);


        when(this.bikeS.getElectricBicyclesList()).thenReturn(this.bikeList);
        ElectricBicycle result = this.bn2.suggestBike(this.p8, this.p9, this.loggedUser);
        assertEquals(result, expected);
    }

    @Test
    public void ensureSameAltitudeReturns0Points8() {
        System.out.println("Ensure 2 parks with same altitude returns the user 0 points.");
        int expected = 0;
        int result = this.bn.calculatePoints(this.p1, this.p9);
        assertEquals(expected, result);
    }

    @Test
    public void ensureIfOtherParkWasHigherReturns0Points() {
        System.out.println("Ensure if the landed park is lower in altitude than the first park the user gets 0 points.");
        int expected = 0;
        int result = this.bn.calculatePoints(this.p1, this.p2);
        assertEquals(expected, result);
    }

    @Test
    public void ensureIfDifferenceOfParksIsExactly50Returns15Points() {
        System.out.println("Ensure if altitude difference is exactly 50 it returns 15 points to the user.");
        int expected = 15;
        int result = this.bn.calculatePoints(this.p2, this.p3);
        assertEquals(expected, result);
    }

    @Test
    public void ensureIfDifferenceIsHigherThan50MetersREturns15Points() {
        System.out.println("Ensure if altitude difference is higher than 50 meters it returns 15 points to the user.");
        int expected = 15;
        int result = this.bn.calculatePoints(this.p2, this.p1);
        assertEquals(expected, result);
    }

    @Test
    public void ensureIfDifferenceIsExactly25Returns5Points() {
        System.out.println("Ensure if altitude difference is exactly 25 it returns 5 points to the user.");
        int expected = 5;
        int result = this.bn.calculatePoints(this.p3, this.p6);
        assertEquals(expected, result);

    }

    @Test
    public void ensureIfDifferenceIsHigherThan25ButLowerThan50Returns5PointsToTheUser() {
        System.out.println("Ensure if altitude is higher than 25 but smaller than 50 returns 5 points.");
        int expected = 5;
        int result = this.bn.calculatePoints(this.p3, this.p4);
        assertEquals(expected, result);
    }

    @Test
    public void ensureSmallerThan25MetersReturns0Points() {
        int expected = 0;
        int result = this.bn.calculatePoints(this.p6, this.p7);
        assertEquals(expected, result);
    }

    @Test
    public void ensureShortestPathByDistanceThrowsExceptionWhenInitialParkIsNull() {
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByDistance(null, p2);
        });
        assertEquals("Locations are null.", e2.getMessage());
    }

    @Test
    public void ensureShortestPathByDistanceThrowsExceptionWhenFinalParkIsNull() {
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByDistance(p1, null);
        });
        assertEquals("Locations are null.", e2.getMessage());
    }

    @Test
    public void ensureShortestPathByDistanceThrowsExceptionWhenBikeGraphDoesNotContainInitialPark() {
        ArrayList<Park> list = new ArrayList<>();
        list.add(p2);
        when(ps.getParkList()).thenReturn(list);
        bn2.loadData();
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByDistance(p1, p2);
        });
        assertEquals("Initial location is not in the system.", e2.getMessage());
    }

    @Test
    public void ensureShortestPathByDistanceThrowsExceptionWhenBikeGraphDoesNotContainFinalPark() {
        ArrayList<Park> list = new ArrayList<>();
        list.add(p1);
        when(ps.getParkList()).thenReturn(list);
        bn2.loadData();
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByDistance(p1, p2);
        });
        assertEquals("Destination location is not in the system.", e2.getMessage());
    }

    @Test
    public void ensureShortestPathByDistanceReturnsEdgeBetweenTheParksWhenTheyAreValid() {
        List<Park> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(p2);
        Path path = new Path(1, 100, 10);
        bn2.addLocation(p1);
        bn2.addLocation(p2);
        bn2.addLocation(p3);
        bn2.addPath(p1, p2, path);
        bn2.addPath(p2, p1, path);
        bn2.addPath(p2, p3, path);
        bn2.addPath(p3, p2, path);
        bn2.addPath(p1, p3, path);
        bn2.addPath(p3, p1, path);
        List<Route> resultRoute = bn2.shortestPathByDistance(p1, p2);
        assertEquals(1, resultRoute.size());
        assertEquals(expected, resultRoute.get(0).getPath());
        assertEquals(1494319.0, resultRoute.get(0).getTotalDistance(), 0.01);
        assertEquals(0, resultRoute.get(0).getTotalEnergy());
        assertEquals(p1.getAltitude() - p2.getAltitude(), resultRoute.get(0).getElevation());
    }

    @Test
    public void ensureShortestPathByElectricExpenseThrowsExceptionWhenInitialParkIsNull() {
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByEnergeticEfficiency(loggedUser, chosenBike, null, p2);
        });
        assertEquals("Locations are null.", e2.getMessage());
    }

    @Test
    public void ensureShortestPathByElectricExpenseThrowsExceptionWhenFinalParkIsNull() {
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByEnergeticEfficiency(loggedUser, chosenBike, p1, null);
        });
        assertEquals("Locations are null.", e2.getMessage());
    }

    @Test
    public void ensureShortestPathByElectricExpenseThrowsExceptionWhenUserIsNull() {
        ArrayList<Park> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        when(ps.getParkList()).thenReturn(list);
        bn2.loadData();
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByEnergeticEfficiency(null, chosenBike, p1, p2);
        });
        assertEquals("No user logged in.", e2.getMessage());
    }

    @Test
    public void ensureShortestPathByElectricExpenseThrowsExceptionWhenBikeIsNull() {
        ArrayList<Park> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        when(ps.getParkList()).thenReturn(list);
        bn2.loadData();
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByEnergeticEfficiency(loggedUser, null, p1, p2);
        });
        assertEquals("No bicycle chosen.", e2.getMessage());
    }


    @Test
    public void ensureShortestPathByElectricExpenseThrowsExceptionWhenBikeGraphDoesNotContainInitialPark() {
        ArrayList<Park> list = new ArrayList<>();
        list.add(p2);
        when(ps.getParkList()).thenReturn(list);
        bn2.loadData();
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByEnergeticEfficiency(loggedUser, chosenBike, p1, p2);
        });
        assertEquals("Initial location is not in the system.", e2.getMessage());
    }

    @Test
    public void ensureShortestPathByElectricExpenseThrowsExceptionWhenBikeGraphDoesNotContainFinalPark() {
        ArrayList<Park> list = new ArrayList<>();
        list.add(p1);
        when(ps.getParkList()).thenReturn(list);
        bn2.loadData();
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByEnergeticEfficiency(loggedUser, chosenBike, p1, p2);
        });
        assertEquals("Destination location is not in the system.", e2.getMessage());
    }


    @Test
    public void ensureShortestPathByElectricExpenseReturnsInitialParkWhenParksAreEqual() throws IOException {
        List<Park> expected = new ArrayList<>();

        when(ps.getParkList()).thenReturn(pList);

        bn2.loadData();
        expected.add(p1);
        expected.add(p2);
        List<Route> result = bn2.shortestPathByEnergeticEfficiency(loggedUser, chosenBike, p1, p1);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getElevation());
        assertEquals(0, result.get(0).getTotalDistance());
        assertEquals(0, result.get(0).getTotalEnergy());
    }

    @Test
    public void ensureShortestPathByElectricExpenseReturnsOneRouteCorrectly() throws IOException {
        List<Park> expected = new ArrayList<>();

        when(ps.getParkList()).thenReturn(pList);

        bn2.loadData();
        expected.add(p1);
        expected.add(p2);
        List<Route> result = bn2.shortestPathByEnergeticEfficiency(loggedUser, chosenBike, p1, p2);
        assertEquals(1, result.size());
        assertEquals(-99, result.get(0).getElevation());
        assertEquals(1494319.0, result.get(0).getTotalDistance(), 0.01);
        assertEquals(0, result.get(0).getTotalEnergy());
    }

    @Test
    public void ensureShortestPathPassingThroughInterestPointsRThrowsExceptionWhenInitialParkIsNull() {
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByDistancePassingByInterestPoints(null, p2, new ArrayList<>());
        });
        assertEquals("Locations are null.", e2.getMessage());
    }

    @Test
    public void ensureShortestPathPassingThroughInterestPointsRThrowsExceptionWhenDestinationParkIsNull() {
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByDistancePassingByInterestPoints(p1, null, new ArrayList<>());
        });
        assertEquals("Locations are null.", e2.getMessage());
    }

    @Test
    public void ensureShortestPathByDistancePassingThroughInterestPointsThrowsExceptionWhenBikeGraphDoesNotContainInitialPark() {
        ArrayList<Park> list = new ArrayList<>();
        list.add(p2);
        when(ps.getParkList()).thenReturn(list);
        bn2.loadData();
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByDistancePassingByInterestPoints(p1, p2, new ArrayList<>());
        });
        assertEquals("Initial location is not in the system.", e2.getMessage());
    }

    @Test
    public void ensureShortestPathByDistancePassingThroughInterestPointsThrowsExceptionWhenBikeGraphDoesNotContainFinalPark() {
        ArrayList<Park> list = new ArrayList<>();
        list.add(p1);
        when(ps.getParkList()).thenReturn(list);
        bn2.loadData();
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByDistancePassingByInterestPoints(p1, p2, new ArrayList<>());
        });
        assertEquals("Destination location is not in the system.", e2.getMessage());
    }

    @Test
    public void ensureShortestPathByDistancePassingThroughInterestPointsThrowsExceptionWhenInterestPointListIsNull() {
        ArrayList<Park> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        when(ps.getParkList()).thenReturn(list);
        bn2.loadData();
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.shortestPathByDistancePassingByInterestPoints(p1, p2, null);
        });
        assertEquals("List of interest points is null.", e2.getMessage());
    }

    @Test
    public void ensureShortestPathByDistancePassingThroughInterestPointsReturnsEdgeWhenListIsEmpty() {
        when(ps.getParkList()).thenReturn(pList);
        bn2.loadData();
        List<Location> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(p2);
        List<Route> result = bn2.shortestPathByDistancePassingByInterestPoints(p1, p2, new ArrayList<>());
        assertEquals(expected, result.get(0).getPath());
    }

    @Test
    public void ensureShortestPathByDistancePassingThroughInterestPointsReturnsEdgeWhenListOnlyHasOneInterestPoint() {
        List<Location> lstIps = new ArrayList<>();
        InterestPoint ip1 = new InterestPoint(1, "Clérigos", 10, 10, 10);
        lstIps.add(ip1);
        bn2.addLocation(p1);
        bn2.addLocation(p2);
        bn2.addLocation(p3);
        bn2.addLocation(ip1);
        bn2.addPath(p1, p3, path);
        bn2.addPath(p1, ip1, path);
        bn2.addPath(p3, p1, path);
        bn2.addPath(p3, p2, path);
        bn2.addPath(p3, ip1, path);
        bn2.addPath(ip1, p3, path);
        List<Location> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(ip1);
        expected.add(p3);
        List<Route> result = bn2.shortestPathByDistancePassingByInterestPoints(p1, p3, lstIps);
        assertEquals(1, result.size());
        assertEquals(-49, result.get(0).getElevation());
        assertEquals(0, result.get(0).getTotalEnergy());
        assertEquals(5463978.0, result.get(0).getTotalDistance(), 0.01);
        assertEquals(expected, result.get(0).getPath());
    }

    @Test
    public void ensureShortestPathByDistancePassingThroughInterestPointsReturnsEdgeWhenListOnlyHasThreeInterestPoints() {
        List<Location> lstIps = new ArrayList<>();
        InterestPoint ip1 = new InterestPoint(1, "Hard Club", 10, 10, 10);
        InterestPoint ip2 = new InterestPoint(2, "Maus Hábitos", 15, 15, 10);
        InterestPoint ip3 = new InterestPoint(3, "Clérigos", 10.10, 10.10, 10);
        lstIps.add(ip1);
        lstIps.add(ip2);
        lstIps.add(ip3);
        bn2.addLocation(p1);
        bn2.addLocation(p2);
        bn2.addLocation(p3);
        bn2.addLocation(p4);
        bn2.addLocation(ip1);
        bn2.addLocation(ip2);
        bn2.addLocation(ip3);
        bn2.addPath(p1, p2, path);
        bn2.addPath(p1, p3, path);
        bn2.addPath(p1, ip3, path);
        bn2.addPath(ip3, ip1, path);
        bn2.addPath(ip1, ip2, path);
        bn2.addPath(ip2, p2, path);
        bn2.addPath(p2, p1, path);
        bn2.addPath(ip2, p4, path);


        List<Location> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(ip3);
        expected.add(ip1);
        expected.add(ip2);
        expected.add(p2);
        List<Route> result = bn2.shortestPathByDistancePassingByInterestPoints(p1, p2, lstIps);
        assertEquals(1, result.size());
        assertEquals(-99, result.get(0).getElevation());
        assertEquals(0, result.get(0).getTotalEnergy());
        assertEquals(9056693.0, result.get(0).getTotalDistance(), 0.01);
        assertEquals(expected, result.get(0).getPath());
    }

    @Test
    public void ensureShortestPathByDistancePassingThroughInterestPointsReturnsEdgeWhenListOnlyHas5InterestPoints() {
        List<Location> lstIps = new ArrayList<>();

        InterestPoint ip1 = new InterestPoint(1, "Piolho", 41.14692703030618, -8.61651062965393, 10);
        InterestPoint ip2 = new InterestPoint(2, "Hospital Santo Antonio", 41.14666042016164, -8.620244264602661, 10);
        InterestPoint ip3 = new InterestPoint(3, "Aliados", 41.147936907387304, -8.61103892326355, 10);
        InterestPoint ip4 = new InterestPoint(4, "Ribeira", 41.140932079766586, -8.60952615737915, 10);
        InterestPoint ip5 = new InterestPoint(5, "Maus Hábitos", 41.14670889481399, -8.605738878250122, 10);
        bn2.addLocation(p1);
        bn2.addLocation(p2);
        bn2.addLocation(p3);
        bn2.addLocation(p4);
        bn2.addLocation(ip1);
        bn2.addLocation(ip2);
        bn2.addLocation(ip3);
        bn2.addLocation(ip4);
        bn2.addLocation(ip5);
        bn2.addPath(p1, p2, path);
        bn2.addPath(p1, ip4, path);
        bn2.addPath(p1, ip2, path);
        bn2.addPath(p2, p1, path);
        bn2.addPath(p2, ip5, path);
        bn2.addPath(p2, ip4, path);
        bn2.addPath(ip4, p2, path);
        bn2.addPath(ip5, ip2, path);
        bn2.addPath(ip5, p2, path);
        bn2.addPath(ip2, ip3, path);
        bn2.addPath(ip2, ip1, path);
        bn2.addPath(ip2, p1, path);
        bn2.addPath(ip1, ip2, path);
        bn2.addPath(ip1, p3, path);
        bn2.addPath(ip3, p3, path);
        bn2.addPath(ip3, ip5, path);
        bn2.addPath(p3, ip1, path);
        bn2.addPath(ip1, p4, path);
        bn2.addPath(p4, ip1, path);
        lstIps.add(ip1);
        lstIps.add(ip2);
        lstIps.add(ip3);
        lstIps.add(ip4);
        lstIps.add(ip5);
        List<Location> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(ip2);
        expected.add(ip1);
        expected.add(ip2);
        expected.add(ip3);
        expected.add(ip5);
        expected.add(ip2);
        expected.add(p1);
        expected.add(ip4);
        expected.add(p2);
        List<Route> result = bn2.shortestPathByDistancePassingByInterestPoints(p1, p2, lstIps);
        assertEquals(6, result.size());
        assertEquals(-99, result.get(0).getElevation());
        assertEquals(0, result.get(0).getTotalEnergy());
        assertEquals(18456211.0, result.get(0).getTotalDistance(), 0.01);
        assertEquals(18456211.0, result.get(1).getTotalDistance(), 0.01);
        assertEquals(18456211.0, result.get(2).getTotalDistance(), 0.01);
        assertEquals(18456211.0, result.get(3).getTotalDistance(), 0.01);
        assertEquals(18456211.0, result.get(4).getTotalDistance(), 0.01);
        assertEquals(18456211.0, result.get(5).getTotalDistance(), 0.01);
        assertEquals(expected, result.get(0).getPath());
    }

    @Test
    public void ensureShortestPathByDistanceReturnsTwoRoutesCorrectly() {
        Park p1 = new Park(1, "Parque 1", 0.8997, 0.8997, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 220, 50), 1.0);
        Park p2 = new Park(2, "Parque 2", 2, 2, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 220, 50), 1.0);
        Park p3 = new Park(3, "Parque 3", 3.1007, 3.1007, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 220, 50), 1.0);
        Park p4 = new Park(4, "Parque 4", 4.20195, 4.20195, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 220, 50), 1.0);
        Park p5 = new Park(5, "Parque 5", 5, 5, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 220, 50), 1.0);
        Path path = new Path(10, 10, 0.01);
        this.bn2.addLocation(p1);
        this.bn2.addLocation(p2);
        this.bn2.addLocation(p3);
        this.bn2.addLocation(p4);
        this.bn2.addLocation(p5);
        this.bn2.addPath(p1, p2, path);
        this.bn2.addPath(p1, p4, path);
        this.bn2.addPath(p2, p3, path);
        this.bn2.addPath(p3, p4, path);
        this.bn2.addPath(p4, p5, path);

        LinkedList<Location> expected1 = new LinkedList<>();
        expected1.add(p1);
        expected1.add(p2);
        expected1.add(p3);
        expected1.add(p4);
        expected1.add(p5);

        LinkedList<Location> expected2 = new LinkedList<>();
        expected2.add(p1);
        expected2.add(p4);
        expected2.add(p5);

        List<Route> result = bn2.shortestPathByDistance(p1, p5);
        assertEquals(2, result.size());
        assertEquals(expected2, result.get(0).getPath());
        assertEquals(expected1, result.get(1).getPath());
    }

    @Test
    public void ensureShortestPathByEnergeticEfficiencyReturnsMultipleRoutes() {
        Park p1 = new Park(1, "Parque 1", 0.8997, 0.8997, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 220, 50), 1.0);
        Park p2 = new Park(2, "Parque 2", 2, 2, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 220, 50), 1.0);
        Park p3 = new Park(3, "Parque 3", 3.1007, 3.1007, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 220, 50), 1.0);
        Park p4 = new Park(4, "Parque 4", 4.20195, 4.20195, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 220, 50), 1.0);
        Park p5 = new Park(5, "Parque 5", 5, 5, new NormalSlots(1, 10, 10), new ElectricSlot(2, 10, 10, 220, 50), 1.0);
        Path path = new Path(10, 10, 600, 0.01);
        Path path2 = new Path(10, 10, 200, 0.01);
        this.bn2.addLocation(p1);
        this.bn2.addLocation(p2);
        this.bn2.addLocation(p3);
        this.bn2.addLocation(p4);
        this.bn2.addLocation(p5);
        this.bn2.addPath(p1, p2, path2);
        this.bn2.addPath(p1, p4, path);
        this.bn2.addPath(p2, p3, path2);
        this.bn2.addPath(p3, p4, path2);
        this.bn2.addPath(p4, p5, path2);

        LinkedList<Location> expected1 = new LinkedList<>();
        expected1.add(p1);
        expected1.add(p2);
        expected1.add(p3);
        expected1.add(p4);
        expected1.add(p5);

        LinkedList<Location> expected2 = new LinkedList<>();
        expected2.add(p1);
        expected2.add(p4);
        expected2.add(p5);

        List<Route> result = bn2.shortestPathByEnergeticEfficiency(loggedUser, chosenBike, p1, p5);
        assertEquals(2, result.size());
        assertEquals(expected2, result.get(0).getPath());
        assertEquals(expected1, result.get(1).getPath());
    }


    @Test
    public void ensureSuggestRoutesThrowsExceptionWhenInitialParkIsNull() {
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.suggestRouteBetweenTwoLocations(null, p2, chosenBike, loggedUser, 3, true, "energy", new ArrayList<>());
        });
        assertEquals("Locations are null.", e2.getMessage());
    }

    @Test
    public void ensureSuggestRoutesThrowsExceptionWhenDestinationParkIsNull() {
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.suggestRouteBetweenTwoLocations(p1, null, chosenBike, loggedUser, 3, true, "energy", new ArrayList<>());

        });
        assertEquals("Locations are null.", e2.getMessage());
    }

    @Test
    public void ensureSuggestRoutesThrowsExceptionWhenBikeGraphDoesNotContainInitialPark() {
        ArrayList<Park> list = new ArrayList<>();
        list.add(p2);
        when(ps.getParkList()).thenReturn(list);
        bn2.loadData();
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.suggestRouteBetweenTwoLocations(p1, p2, chosenBike, loggedUser, 3, true, "energy", new ArrayList<>());
        });
        assertEquals("Initial location is not in the system.", e2.getMessage());
    }

    @Test
    public void ensureSuggestRoutesThrowsExceptionWhenBikeGraphDoesNotContainFinalPark() {
        ArrayList<Park> list = new ArrayList<>();
        list.add(p1);
        when(ps.getParkList()).thenReturn(list);
        bn2.loadData();
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.suggestRouteBetweenTwoLocations(p1, p2, chosenBike, loggedUser, 3, true, "energy", new ArrayList<>());
        });
        assertEquals("Destination location is not in the system.", e2.getMessage());
    }

    @Test
    public void ensureSuggestRoutesThrowsExceptionWhenInterestPointListIsNull() {
        ArrayList<Park> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        when(ps.getParkList()).thenReturn(list);
        bn2.loadData();
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            bn2.suggestRouteBetweenTwoLocations(p1, p2, chosenBike, loggedUser, 3, true, "energy", null);
        });
        assertEquals("List of interest points is null.", e2.getMessage());
    }

    @Test
    public void ensureSuggestRoutesReturnsReturnsEdgeWhenListIsEmpty() {
        when(ps.getParkList()).thenReturn(pList);
        bn2.loadData();
        List<Location> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(p2);
        List<Route> result = bn2.suggestRouteBetweenTwoLocations(p1, p2, chosenBike, loggedUser, 3, true, "energy", new ArrayList<>());
        assertEquals(expected, result.get(0).getPath());
    }

    @Test
    public void ensureSuggestRoutesReturnsEdgeWhenListOnlyHasOneInterestPoint() {
        List<Location> lstIps = new ArrayList<>();
        InterestPoint ip1 = new InterestPoint(1, "Clérigos", 10, 10, 10);
        lstIps.add(ip1);
        bn2.addLocation(p1);
        bn2.addLocation(p2);
        bn2.addLocation(p3);
        bn2.addLocation(ip1);
        bn2.addPath(p1, p3, path);
        bn2.addPath(p1, ip1, path);
        bn2.addPath(p3, p1, path);
        bn2.addPath(p3, p2, path);
        bn2.addPath(p3, ip1, path);
        bn2.addPath(ip1, p3, path);
        List<Location> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(ip1);
        expected.add(p3);
        List<Route> result = bn2.suggestRouteBetweenTwoLocations(p1, p3, chosenBike, loggedUser, 3, true, "energy", lstIps);

        assertEquals(1, result.size());
        assertEquals(-49, result.get(0).getElevation());
        assertEquals(0, result.get(0).getTotalEnergy());
        assertEquals(5463978.0, result.get(0).getTotalDistance(), 0.01);
        assertEquals(expected, result.get(0).getPath());
    }

    @Test
    public void ensureSuggestRoutesReturnsReturnsEdgeWhenListOnlyHasThreeInterestPoints() {
        List<Location> lstIps = new ArrayList<>();
        InterestPoint ip1 = new InterestPoint(1, "Hard Club", 10, 10, 10);
        InterestPoint ip2 = new InterestPoint(2, "Maus Hábitos", 15, 15, 10);
        InterestPoint ip3 = new InterestPoint(3, "Clérigos", 10.10, 10.10, 10);
        lstIps.add(ip1);
        lstIps.add(ip2);
        lstIps.add(ip3);
        bn2.addLocation(p1);
        bn2.addLocation(p2);
        bn2.addLocation(p3);
        bn2.addLocation(p4);
        bn2.addLocation(ip1);
        bn2.addLocation(ip2);
        bn2.addLocation(ip3);
        bn2.addPath(p1, p2, path);
        bn2.addPath(p1, p3, path);
        bn2.addPath(p1, ip3, path);
        bn2.addPath(ip3, ip1, path);
        bn2.addPath(ip1, ip2, path);
        bn2.addPath(ip2, p2, path);
        bn2.addPath(p2, p1, path);
        bn2.addPath(ip2, p4, path);


        List<Location> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(ip3);
        expected.add(ip1);
        expected.add(ip2);
        expected.add(p2);
        List<Route> result = bn2.shortestPathByDistancePassingByInterestPoints(p1, p2, lstIps);
        assertEquals(1, result.size());
        assertEquals(-99, result.get(0).getElevation());
        assertEquals(0, result.get(0).getTotalEnergy());
        assertEquals(9056693.0, result.get(0).getTotalDistance(), 0.01);
        assertEquals(expected, result.get(0).getPath());
    }

    @Test
    public void ensureSuggestRoutesReturnsThreeRoutesWhenListOnlyHas5InterestPoints() {
        List<Location> lstIps = new ArrayList<>();
        InterestPoint ip1 = new InterestPoint(1, "Piolho", 41.14692703030618, -8.61651062965393, 10);
        InterestPoint ip2 = new InterestPoint(2, "Hospital Santo Antonio", 41.14666042016164, -8.620244264602661, 10);
        InterestPoint ip3 = new InterestPoint(3, "Aliados", 41.147936907387304, -8.61103892326355, 10);
        InterestPoint ip4 = new InterestPoint(4, "Ribeira", 41.140932079766586, -8.60952615737915, 10);
        InterestPoint ip5 = new InterestPoint(5, "Maus Hábitos", 41.14670889481399, -8.605738878250122, 10);
        bn2.addLocation(p1);
        bn2.addLocation(p2);
        bn2.addLocation(p3);
        bn2.addLocation(p4);
        bn2.addLocation(ip1);
        bn2.addLocation(ip2);
        bn2.addLocation(ip3);
        bn2.addLocation(ip4);
        bn2.addLocation(ip5);
        bn2.addPath(p1, p2, path);
        bn2.addPath(p1, ip4, path);
        bn2.addPath(p1, ip2, path);
        bn2.addPath(p2, p1, path);
        bn2.addPath(p2, ip5, path);
        bn2.addPath(p2, ip4, path);
        bn2.addPath(ip4, p2, path);
        bn2.addPath(ip5, ip2, path);
        bn2.addPath(ip5, p2, path);
        bn2.addPath(ip2, ip3, path);
        bn2.addPath(ip2, ip1, path);
        bn2.addPath(ip2, p1, path);
        bn2.addPath(ip1, ip2, path);
        bn2.addPath(ip1, p3, path);
        bn2.addPath(ip3, p3, path);
        bn2.addPath(ip3, ip5, path);
        bn2.addPath(p3, ip1, path);
        bn2.addPath(ip1, p4, path);
        bn2.addPath(p4, ip1, path);
        lstIps.add(ip1);
        lstIps.add(ip2);
        lstIps.add(ip3);
        lstIps.add(ip4);
        lstIps.add(ip5);
        List<Location> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(ip2);
        expected.add(ip1);
        expected.add(ip2);
        expected.add(ip3);
        expected.add(ip5);
        expected.add(ip2);
        expected.add(p1);
        expected.add(ip4);
        expected.add(p2);
        List<Route> result = bn2.suggestRouteBetweenTwoLocations(p1, p2, chosenBike, loggedUser, 3, true, "shortest_path", lstIps);
        assertEquals(3, result.size());
        assertEquals(-99, result.get(0).getElevation());
        assertEquals(0, result.get(0).getTotalEnergy());
        assertEquals(18456211.0, result.get(0).getTotalDistance(), 0.01);
        assertEquals(18456211.0, result.get(1).getTotalDistance(), 0.01);
        assertEquals(18456211.0, result.get(2).getTotalDistance(), 0.01);
        assertEquals(expected, result.get(0).getPath());
    }

    @Test
    public void testingScenario001() {
        bn2.addLocation(scenarioPark1);
        bn2.addLocation(scenarioPark2);
        bn2.addPath(scenarioPark1, scenarioPark2, emptyPath);
        bn2.addPath(scenarioPark2, scenarioPark1, emptyPath);

        List<Route> routesByDistance = bn2.shortestPathByDistance(scenarioPark1, scenarioPark2);
        ArrayList<Location> expected = new ArrayList<>();
        expected.add(scenarioPark1);
        expected.add(scenarioPark2);
        assertEquals(1, routesByDistance.size());
        assertEquals(expected, routesByDistance.get(0).getPath());
        assertEquals(1304, routesByDistance.get(0).getTotalDistance());
        assertEquals(0, routesByDistance.get(0).getTotalEnergy());
        assertEquals(79, routesByDistance.get(0).getElevation());
    }

    @Test
    public void ensureSceneario002Works(){
        bn2.addLocation(this.scenarioPark1);
        bn2.addLocation(this.scenarioPark2);
        bn2.addLocation(this.scenarioInterestPoint1); // clerigos
        bn2.addLocation(this.scenarioInterestPoint2); // majestic
        bn2.addLocation(this.scenarioInterestPoint3); // bolhao
        bn2.addPath(scenarioPark1,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint2,emptyPath);
        bn2.addPath(scenarioInterestPoint3,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint2,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint2,emptyPath);

        List<Route> routesByDistance = bn2.shortestPathByDistance(scenarioPark1, scenarioPark2);
        ArrayList<Location> expected = new ArrayList<>();
        expected.add(scenarioPark1);
        expected.add(this.scenarioInterestPoint1);
        expected.add(this.scenarioInterestPoint2);
        expected.add(scenarioPark2);
        assertEquals(1, routesByDistance.size());
        assertEquals(expected, routesByDistance.get(0).getPath());
        assertEquals(2287, routesByDistance.get(0).getTotalDistance());
        assertEquals(0, routesByDistance.get(0).getTotalEnergy());
        assertEquals(79, routesByDistance.get(0).getElevation());

    }
    @Test
    public void ensureScenario003Works(){
        bn2.addLocation(this.scenarioPark1);
        bn2.addLocation(this.scenarioPark2);
        bn2.addLocation(this.scenarioInterestPoint1); // clerigos
        bn2.addLocation(this.scenarioInterestPoint2); // majestic
        bn2.addLocation(this.scenarioInterestPoint3); // bolhao
        bn2.addPath(scenarioPark1,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioPark1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint3,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint2,emptyPath);
        bn2.addPath(scenarioInterestPoint3,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint2,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint2,emptyPath);

        List<Route> routesByDistance = bn2.shortestPathByDistance(scenarioPark1, scenarioPark2);
        ArrayList<Location> expected = new ArrayList<>();
        expected.add(scenarioPark1);
        expected.add(this.scenarioInterestPoint1);
        expected.add(this.scenarioInterestPoint2);
        expected.add(scenarioPark2);
        assertEquals(1, routesByDistance.size());
        assertEquals(expected, routesByDistance.get(0).getPath());
        assertEquals(2287, routesByDistance.get(0).getTotalDistance());
        assertEquals(0, routesByDistance.get(0).getTotalEnergy());
        assertEquals(79, routesByDistance.get(0).getElevation());

    }

    @Test
    public void ensureScenario004Works(){
        bn2.addLocation(this.scenarioPark1);
        bn2.addLocation(this.scenarioPark2);
        bn2.addLocation(this.scenarioInterestPoint1); // clerigos
        bn2.addLocation(this.scenarioInterestPoint2); // majestic
        bn2.addLocation(this.scenarioInterestPoint3); // bolhao
        bn2.addPath(scenarioPark1,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint3,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint2,emptyPath);
        bn2.addPath(scenarioInterestPoint3,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint2,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint2,emptyPath);

        List<Route> routesByDistance = bn2.shortestPathByDistance(scenarioPark1, scenarioPark2);
        ArrayList<Location> expected = new ArrayList<>();
        expected.add(scenarioPark1);
        expected.add(this.scenarioInterestPoint1);
        expected.add(this.scenarioInterestPoint2);
        expected.add(scenarioPark2);
        assertEquals(1, routesByDistance.size());
        assertEquals(expected, routesByDistance.get(0).getPath());
        assertEquals(2287, routesByDistance.get(0).getTotalDistance());
        assertEquals(0, routesByDistance.get(0).getTotalEnergy());
        assertEquals(79, routesByDistance.get(0).getElevation());

    }


    @Test
    public void ensureScenario005WOrks(){
        bn2.addLocation(this.scenarioPark1);
        bn2.addLocation(this.scenarioPark2);
        bn2.addLocation(this.scenarioInterestPoint1); // clerigos
        bn2.addLocation(this.scenarioInterestPoint2); // majestic
        bn2.addLocation(this.scenarioInterestPoint3); // bolhao
        bn2.addPath(scenarioPark1,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioPark1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint2,emptyPath);
        bn2.addPath(scenarioInterestPoint2,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint3,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint2,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint2,emptyPath);

        List<Route> routesByDistance = bn2.shortestPathByDistance(scenarioPark1, scenarioPark2);
        ArrayList<Location> expected = new ArrayList<>();
        expected.add(scenarioPark1);
        expected.add(this.scenarioInterestPoint1);
        expected.add(this.scenarioInterestPoint2);
        expected.add(scenarioPark2);
        assertEquals(1, routesByDistance.size());
        assertEquals(expected, routesByDistance.get(0).getPath());
        assertEquals(2287, routesByDistance.get(0).getTotalDistance());
        assertEquals(0, routesByDistance.get(0).getTotalEnergy());
        assertEquals(79, routesByDistance.get(0).getElevation());

    }
    @Test
    public void ensureElectricalEfficientRoute001(){
        bn2.addLocation(scenarioPark1);
        bn2.addLocation(scenarioPark2);
        bn2.addPath(scenarioPark1, scenarioPark2, emptyPath);
        bn2.addPath(scenarioPark2, scenarioPark1, emptyPath);

        List<Route> routesByDistance = bn2.shortestPathByEnergeticEfficiency(this.loggedUser,this.chosenBike,scenarioPark1, scenarioPark2);
        ArrayList<Location> expected = new ArrayList<>();
        expected.add(scenarioPark1);
        expected.add(scenarioPark2);
        assertEquals(1, routesByDistance.size());
        assertEquals(expected, routesByDistance.get(0).getPath());
        assertEquals(1304, routesByDistance.get(0).getTotalDistance());
        assertEquals(0, routesByDistance.get(0).getTotalEnergy());
        assertEquals(79, routesByDistance.get(0).getElevation());
    }

    @Test
    public void ensureScenarioByElectricalEfficiency002Works(){
        bn2.addLocation(this.scenarioPark1);
        bn2.addLocation(this.scenarioPark2);
        bn2.addLocation(this.scenarioInterestPoint1); // clerigos
        bn2.addLocation(this.scenarioInterestPoint2); // majestic
        bn2.addLocation(this.scenarioInterestPoint3); // bolhao
        bn2.addPath(scenarioPark1,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint2,emptyPath);
        bn2.addPath(scenarioInterestPoint3,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint2,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint2,emptyPath);

        List<Route> routesByDistance = bn2.shortestPathByEnergeticEfficiency(this.loggedUser,this.chosenBike,scenarioPark1, scenarioPark2);
        ArrayList<Location> expected = new ArrayList<>();
        expected.add(scenarioPark1);
        expected.add(this.scenarioInterestPoint1);
        expected.add(this.scenarioInterestPoint2);
        expected.add(scenarioPark2);
        assertEquals(1, routesByDistance.size());
        assertEquals(expected, routesByDistance.get(0).getPath());
        assertEquals(2287, routesByDistance.get(0).getTotalDistance());
        assertEquals(0, routesByDistance.get(0).getTotalEnergy());
        assertEquals(79, routesByDistance.get(0).getElevation());

    }
    @Test
    public void ensureElectricalEfficiencyWorks003(){
        bn2.addLocation(this.scenarioPark1);
        bn2.addLocation(this.scenarioPark2);
        bn2.addLocation(this.scenarioInterestPoint1); // clerigos
        bn2.addLocation(this.scenarioInterestPoint2); // majestic
        bn2.addLocation(this.scenarioInterestPoint3); // bolhao
        bn2.addPath(scenarioPark1,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioPark1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint3,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint2,emptyPath);
        bn2.addPath(scenarioInterestPoint3,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint2,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint2,emptyPath);

        List<Route> routesByDistance = bn2.shortestPathByEnergeticEfficiency(this.loggedUser,this.chosenBike,scenarioPark1, scenarioPark2);
        ArrayList<Location> expected = new ArrayList<>();
        expected.add(scenarioPark1);
        expected.add(this.scenarioInterestPoint1);
        expected.add(this.scenarioInterestPoint2);
        expected.add(scenarioPark2);
        assertEquals(1, routesByDistance.size());
        assertEquals(expected, routesByDistance.get(0).getPath());
        assertEquals(2287, routesByDistance.get(0).getTotalDistance());
        assertEquals(0, routesByDistance.get(0).getTotalEnergy());
        assertEquals(79, routesByDistance.get(0).getElevation());


    }

    @Test
    public void ensureElectricalScenario004Works(){
        bn2.addLocation(this.scenarioPark1);
        bn2.addLocation(this.scenarioPark2);
        bn2.addLocation(this.scenarioInterestPoint1); // clerigos
        bn2.addLocation(this.scenarioInterestPoint2); // majestic
        bn2.addLocation(this.scenarioInterestPoint3); // bolhao
        bn2.addPath(scenarioPark1,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint3,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint2,emptyPath);
        bn2.addPath(scenarioInterestPoint3,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint2,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint2,emptyPath);

        List<Route> routesByDistance = bn2.shortestPathByEnergeticEfficiency(this.loggedUser,this.chosenBike,scenarioPark1, scenarioPark2);
        ArrayList<Location> expected = new ArrayList<>();
        expected.add(scenarioPark1);
        expected.add(this.scenarioInterestPoint1);
        expected.add(this.scenarioInterestPoint2);
        expected.add(scenarioPark2);
        assertEquals(1, routesByDistance.size());
        assertEquals(expected, routesByDistance.get(0).getPath());
        assertEquals(2287, routesByDistance.get(0).getTotalDistance());
        assertEquals(0, routesByDistance.get(0).getTotalEnergy());
        assertEquals(79, routesByDistance.get(0).getElevation());

    }
    @Test
    public void ensureScenario005Works(){
        bn2.addLocation(this.scenarioPark1);
        bn2.addLocation(this.scenarioPark2);
        bn2.addLocation(this.scenarioInterestPoint1); // clerigos
        bn2.addLocation(this.scenarioInterestPoint2); // majestic
        bn2.addLocation(this.scenarioInterestPoint3); // bolhao
        bn2.addPath(scenarioPark1,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioPark1,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint1,scenarioInterestPoint2,emptyPath);
        bn2.addPath(scenarioInterestPoint2,scenarioInterestPoint1,emptyPath);
        bn2.addPath(scenarioInterestPoint3,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint3,emptyPath);
        bn2.addPath(scenarioInterestPoint2,scenarioPark2,emptyPath);
        bn2.addPath(scenarioPark2,scenarioInterestPoint2,emptyPath);

        List<Route> routesByDistance = bn2.shortestPathByEnergeticEfficiency(this.loggedUser,this.chosenBike,scenarioPark1, scenarioPark2);
        ArrayList<Location> expected = new ArrayList<>();
        expected.add(scenarioPark1);
        expected.add(this.scenarioInterestPoint1);
        expected.add(this.scenarioInterestPoint2);
        expected.add(scenarioPark2);
        assertEquals(1, routesByDistance.size());
        assertEquals(expected, routesByDistance.get(0).getPath());
        assertEquals(2287, routesByDistance.get(0).getTotalDistance());
        assertEquals(0, routesByDistance.get(0).getTotalEnergy());
        assertEquals(79, routesByDistance.get(0).getElevation());

    }

    @Test
    public void ensureRoutWithInterestPointsWorksScenario001(){
        bn2.addLocation(this.scenarioPark1);
        bn2.addLocation(this.scenarioPark2);

        bn2.addPath(scenarioPark1,scenarioPark2,emptyPath);


        List<Route> routesByDistance = bn2.shortestPathByDistance(scenarioPark1, scenarioPark2);
        ArrayList<Location> expected = new ArrayList<>();
        expected.add(scenarioPark1);
        expected.add(scenarioPark2);
        assertEquals(1, routesByDistance.size());
        assertEquals(expected, routesByDistance.get(0).getPath());
        assertEquals(1304, routesByDistance.get(0).getTotalDistance());
        assertEquals(0, routesByDistance.get(0).getTotalEnergy());
        assertEquals(79, routesByDistance.get(0).getElevation());

    }
    @Test
    public void ensureReturnsNullWhenGraphIsNull(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            List<Route> elR = this.bn.shortestPathByDistance(this.p1,this.p2);
        });
        assertEquals("Initial location is not in the system.", e.getMessage());


    }

}

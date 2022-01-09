/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller.user;

import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DistanceToParkControllerTest {

    @InjectMocks
    private DistanceToParkController dtpc;
    @Mock
    private LocationFacade pf;

    @Mock
    private BicycleNetwork bn;

    @BeforeEach
    public void setUp() {
        pf = mock(LocationFacade.class);
        bn = mock(BicycleNetwork.class);
        initMocks(this);
    }

    /**
     * Teste do getParkRegistry quando não há parks registados
     */
    @Test
    public void testGetParkRegistryWhenNoParksAreRegisted() {
        boolean expected = true;
        when(pf.getParkList()).thenReturn(new ArrayList<>());
        List<Park> parks = dtpc.getParkRegistry();
        assertTrue(parks.isEmpty());
    }

    /**
     * Teste do getParkRegistry quando há parks registados
     */
    @Test
    public void testGetParkRegistryWhenParksAreRegisted() {
        List<Park> expected = new ArrayList<>();
        Park p = new Park(1, "Trindade", 41, -8, null, null, 1.0);
        expected.add(p);
        when(pf.getParkList()).thenReturn(expected);
        List<Park> parks = dtpc.getParkRegistry();
        assertTrue(!parks.isEmpty());
        assertTrue(parks.size() == 1);
    }

    /**
     * Teste do getDistanceToPark quando o id do park é passado incorretamente
     */
    @Test
    public void testGetDistanceToParkWhenParkNotExistInBicycleNetwork() {
        List<Park> expected = new ArrayList<>();
        Park p = new Park(1, "S.Bento", 42, -8, null, null, 1.0);
        expected.add(p);
        when(pf.getParkList()).thenReturn(expected);
        try {
            when(pf.getParkById(2)).thenReturn(null);
            dtpc.getDistanceToPark(2, 42, 8);
            assertTrue(false);
        } catch (InvalidDataException e) {
            assertTrue(true);
        }
    }

    /**
     * Teste do getDistanceToPark quando o id do park é passado corretamente
     * mas as coordenadas do User estão incorretas
     */
    @Test
    public void testGetDistanceToParkWhenParkExistButCoordinatesAreWrong() {
        ArrayList<Park> expected = new ArrayList<>();
        Park p = new Park(1, "IPO", 42, -9, null, null, 1.0);
        expected.add(p);
        when(pf.getParkList()).thenReturn(expected);
        try {
            when(pf.getParkById(1)).thenReturn(p);
            when(bn.distanceUserPark(181, -91, p)).thenReturn(new Double(0));
            double result = dtpc.getDistanceToPark(1, 191, -91);
            assertTrue(result == 0);
        } catch (InvalidDataException e) {
            assertTrue(true);
        }
    }

    /**
     * Teste do getDistanceToPark quando o id do park e as coordenadas do User
     * são passados corretamente
     */
    @Test
    public void testGetDistanceToParkWhenParkExistAndCorrectCoordinates() {
        ArrayList<Park> expected = new ArrayList<>();
        Park p = new Park(1, "Casa da Música", 42, -8, null, null, 1.0);
        expected.add(p);
        when(pf.getParkList()).thenReturn(expected);
        try {
            double distance = 1402.4596559084723;
            when(pf.getParkById(1)).thenReturn(p);
            when(bn.distanceUserPark(42, -9, p)).thenReturn(1402.4596559084723);
            double result = dtpc.getDistanceToPark(1, 42, -9);
            assertTrue(result == distance);
        } catch (InvalidDataException e) {
            assertTrue(false);
        }
    }
}

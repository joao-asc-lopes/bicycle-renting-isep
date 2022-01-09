package lapr.project.controller.user;

import lapr.project.model.bicycle.Battery;
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.park.ElectricSlot;
import lapr.project.model.park.NormalSlots;
import lapr.project.model.park.Park;
import lapr.project.model.user.User;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class SuggestEletricBicicleControllerTest {

    @InjectMocks
    private SuggestEletricBicicleController sebc;
    @Mock
    private BicycleNetwork bn;

    private ElectricBicycle eBike;
    private Park p1;
    private Park p2;
    private User u1;
    private User u2;



    @BeforeEach
    public void setUp(){
        sebc = new SuggestEletricBicicleController();
        bn = mock(BicycleNetwork.class);
        this.u1 = new User("DANIEL","user@gmail.com", "mynewpassword123", "Steve", (float) 70.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);
        this.u2 = new User("Dani","use2@gmail.com", "mynewpassword123", "Anna", (float) 0.0, (float) 1.80, (long) 1111222233334444.0, false,15.00,0);



        this.p1 = new Park(3, "Parque da Bicicletas", 10, 25, new NormalSlots(5, 10, 10), new ElectricSlot(6, 200, 20,220,50),1.0);
        this.p2 = new Park(5, "Parque das outras bicicletas", 10, 11, new NormalSlots(9, 100, 100), new ElectricSlot(10, 100, 10,220,50),1.0);
        eBike = new ElectricBicycle("IDteste", Bicycle.BicycleStatus.AVAILABLE, new Battery(1,200,175,3),5,10,5.0);


        initMocks(this);
    }

    @Test
    void ensureSuggestBicycleSuggestsABicycleInCaseAllDataIsCorrect() throws IOException {
        Bicycle expected = this.eBike;
        when(this.bn.suggestBike(this.p1, this.p2, this.u1)).thenReturn(this.eBike);
        Bicycle result = this.sebc.suggestBicycle(this.p1, this.p2, this.u1);
        assertEquals(expected, result);
    }

    @Test
    void ensureSuggestBicycleThrowsExceptionIfOneOfTheParksIsNull() throws IOException {
        when(this.bn.suggestBike(null, this.p2, this.u1)).thenThrow(new InvalidDataException("The inserted data is invalid."));
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            this.sebc.suggestBicycle(null, this.p2, this.u1);
        });
        assertEquals("The inserted data is invalid.", e2.getMessage());
    }

    @Test
    void ensureSuggestBicycleThrowsExceptionIfUsersWeightIsInvalid() throws IOException {

        when(this.bn.suggestBike(this.p1, this.p2, this.u2)).thenThrow(new InvalidDataException("The inserted data is invalid."));
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            this.sebc.suggestBicycle(this.p1, this.p2, this.u2);
        });
        assertEquals("The inserted data is invalid.", e2.getMessage());
    }

    @Test
    void ensureSuggestBicycleThrowsExceptionIfThereAreNoSuitable() throws IOException {
        when(this.bn.suggestBike(this.p1, this.p2, this.u1)).thenThrow(new InvalidDataException("There are no suitable bicycles for this scenario."));
        InvalidDataException e2 = assertThrows(InvalidDataException.class, () -> {
            this.sebc.suggestBicycle(this.p1, this.p2, this.u1);
        });
        assertEquals("There are no suitable bicycles for this scenario.", e2.getMessage());
    }

}
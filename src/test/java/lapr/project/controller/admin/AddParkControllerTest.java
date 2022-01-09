package lapr.project.controller.admin;


import lapr.project.model.park.*;
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

public class AddParkControllerTest {

    /**
     * Where we are injecting everything of the mock. Variable representative of the Park Registration Controller.
     */
    @InjectMocks
    private AddParkController prc;
    /**
     * Mock. Variable representative of the BicycleNetwork.
     */
    @Mock
    private LocationFacade pf;
    /**
     * Variable representative of the id of the park.
     */
    private int idPark;

    @BeforeEach
    public void setUp() {
        this.idPark = 1;
        this.pf = mock(LocationFacade.class);
        initMocks(this);
    }

    @Test
    public void ensureRegisterParkDoesNotExistReturnsTrue() throws IOException {
        boolean expFlag = true;
        when(pf.registerPark("Parque do Dani",90,180,10,10,10, 10,220,50,-4224.0)).thenReturn(true);
        boolean result = this.prc.addPark("Parque do Dani",90,180,10,10,10, 10,220,50,-4224.0);
        assertEquals(expFlag,result);

    }


    @Test
    public void ensureRegisterParkReturnsFalseWithNullName() throws IOException {

        boolean expFlag = false;

        when(pf.registerPark(null,40,-8,10,10,10, 10,220,50,615.0)).thenReturn(false);
        boolean result = this.prc.addPark(null,40,-8,10,10,10, 10,220,50,615.0);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureRegisterParkReturnsFalseWithEmptyName() throws IOException {

        boolean expFlag = false;
        when(pf.registerPark("",40,-8,10,10,10, 10,220,50,615.0)).thenReturn(false);
        boolean result = this.prc.addPark("",40,-8,10,10,10, 10,220,50,615.0);
        assertEquals(expFlag,result);
    }

    @Test
    public void ensureRegisterParkReturnsFalseWithNullAddress() throws IOException {

        boolean expFlag = false;


        when(pf.registerPark("Parque do Dani",40,-8,10,10,10, 10,220,50,615.0)).thenReturn(false);
        boolean result = this.prc.addPark("Parque do Dani",40,-8,10,10,10, 10,220,50,615.0);


        assertEquals(expFlag,result);
    }

    @Test
    public void ensureRegisterParkReturnsFalseWithEmptyAddress() throws IOException {

        boolean expFlag = false;


        when(pf.registerPark("Parque do Dani",40,-8,10,10,10, 10,220,50,615.0)).thenReturn(false);
        boolean result = this.prc.addPark("Parque do Dani",40,-8,10,10,10, 10,220,50,1.0);


        assertEquals(expFlag,result);
    }

    @Test
    public void ensureRegisterParkReturnsFalseWithLowerThanPermittedLatitude() throws IOException {
        InvalidDataException expFlag = new InvalidDataException("Wrong Coordinates");
        String result = "";
        when(pf.registerPark("Parque do Dani",-98,-8,10,10,10, 10,220,50,1.0)).thenThrow(expFlag);

        try {

            this.prc.addPark("Parque do Dani", -98, -8, 10, 10, 10, 10,220,50,1.0);
        }
        catch(InvalidDataException e){
            result = e.getMessage();
            }
            assertEquals(expFlag.getMessage(),result);

    }

    @Test
    public void ensureRegisterParkReturnsFalseWithHigherThanPermittedLatitude() throws IOException {
        InvalidDataException expFlag = new InvalidDataException("Wrong Coordinates");
        String result = "";
        when(pf.registerPark("Parque do Dani",98,-8,10,10,10, 10,220,50,1.0)).thenThrow(expFlag);

        try {


            this.prc.addPark("Parque do Dani", 98, -8, 10, 10, 10, 10,220,50,1.0);
        }
        catch(InvalidDataException e){
            result = e.getMessage();
        }
        assertEquals(expFlag.getMessage(),result);

    }

    @Test
    public void ensureRegisterParkReturnsFalseWithLowerThanPermittedLongitude() throws IOException {
        InvalidDataException expFlag = new InvalidDataException("Wrong Coordinates");
        String result = "";
        when(pf.registerPark("Parque do Dani",40,-190,10,10,10, 10,220,50,1.0)).thenThrow(expFlag);

        try {


            this.prc.addPark("Parque do Dani", 40, -190, 10, 10, 10, 10,220,50,1.0);

        }
        catch(InvalidDataException e){
            result = e.getMessage();
        }
        assertEquals(expFlag.getMessage(),result);

    }

    @Test
    public void ensureRegisterParkReturnsFalseWithHigherThanPermittedLongitude() throws IOException {
        InvalidDataException expFlag = new InvalidDataException("Wrong Coordinates");
        String result = "";
        when(pf.registerPark("Parque do Dani",40,190,10,10,10, 10,220,50,1.0)).thenThrow(expFlag);

        try {



            this.prc.addPark("Parque do Dani", 40, 190, 10, 10, 10, 10,220,50,1.0);


        }
        catch(InvalidDataException e){
            result = e.getMessage();
        }
        assertEquals(expFlag.getMessage(),result);

    }

    @Test
    public void ensureSetDataThrowsExceptionWhenElectricMaximumCapacityIsZero(){
        when(this.pf.registerPark("Park 1",100.0,100.0, 2,2,0,5,220,50,75.0)).thenThrow(new InvalidDataException("Maximum capacity must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.pf.registerPark("Park 1",100.0,100.0, 2,2,0,5,220,50,75.0);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());

    }
    @Test
    public void ensureSetDataThrowsExceptionWhenElectricMaximumCapacityIsNegative(){
        when(this.pf.registerPark("Park 1",100.0,100.0, 2,2,-3,5,220,50,75.0)).thenThrow(new InvalidDataException("Maximum capacity must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.prc.addPark("Park 1",100.0,100.0, 2,2,-3,5,220,50,75.0);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());

    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNormalMaximumCapacityIsNegative(){
        when(this.pf.registerPark("Park 1",100.0,100.0, -2,2,10,5,220,50,75.0)).thenThrow(new InvalidDataException("Maximum capacity must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.prc.addPark("Park 1",100.0,100.0, -2,2,10,5,220,50,75.0);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());

    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNormalMaximumCapacityIsZero(){
        when(this.pf.registerPark("Park 1",100.0,100.0, 0,2,10,5,220,50,75.0)).thenThrow(new InvalidDataException("Maximum capacity must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.prc.addPark("Park 1",100.0,100.0, 0,2,10,5,220,50,75.0);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNumberFreeSlotsIsNegative(){
        when(this.pf.registerPark("Park 1",100.0,100.0, 5,-2,10,5,220,50,75.0)).thenThrow(new InvalidDataException("Number of free slots must not be negative"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.prc.addPark("Park 1",100.0,100.0, 5,-2,10,5,220,50,75.0);
        });
        assertEquals("Number of free slots must not be negative", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNumberFreeSlotsIsBiggerThanMaximum(){
        when(this.pf.registerPark("Park 1",100.0,100.0, 5,7,10,5,220,50,75.0)).thenThrow(new InvalidDataException("Number of free slots must not be bigger than maximum capacity"));
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.prc.addPark("Park 1",100.0,100.0, 5,7,10,5,220,50,75.0);
        });
        assertEquals("Number of free slots must not be bigger than maximum capacity", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionIfChargeRateIsNegative(){
        when(this.pf.registerPark("Park 1",100.0,100.0, 5,2,10,5,-220,50,75.0)).thenThrow(new InvalidDataException("Charge rate must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.prc.addPark("Park 1",100.0,100.0, 5,2,10,5,-220,50,75.0);
        });
        assertEquals("Charge rate must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionIfChargeRateIsZero(){
        when(this.pf.registerPark("Park 1",100.0,100.0, 5,2,10,5,0,50,75.0)).thenThrow(new InvalidDataException("Charge rate must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.prc.addPark("Park 1",100.0,100.0, 5,2,10,5,0,50,75.0);
        });
        assertEquals("Charge rate must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionIfIntensityIsNegative(){
        when(this.pf.registerPark("Park 1",100.0,100.0, 5,2,10,5,220,-50,75.0)).thenThrow(new InvalidDataException("Intensity must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.prc.addPark("Park 1",100.0,100.0, 5,2,10,5,220,-50,75.0);
        });
        assertEquals("Intensity must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionIfIntensityIsZero(){
        when(this.pf.registerPark("Park 1",100.0,100.0, 5,2,10,5,220,0,75.0)).thenThrow(new InvalidDataException("Intensity must be bigger than 0"));

        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.prc.addPark("Park 1",100.0,100.0, 5,2,10,5,220,0,75.0);

        });
        assertEquals("Intensity must be bigger than 0", e.getMessage());
    }

    @Test
    public void ensureGetParkByCoordinatesReturnsExpectedPark(){
    Park park = new Park(1,"Park",10,01,new NormalSlots(1,1,1), new ElectricSlot(1,1,1,1,1),1);
    when(this.pf.getLocationByCoordinates(10,10)).thenReturn(park);

    Park result = this.prc.getParkWithCoordinates(10,10);
    assertEquals(park,result);
}

    @Test
    public void ensureParkDoesNotWorkWhenDoesNotReturnRightParks() {
        when(this.pf.getLocationByCoordinates(10, 10)).thenThrow(new InvalidDataException("No Park With These Coordinates"));
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            this.prc.getParkWithCoordinates(10, 10);

        });
        assertEquals("No Park With These Coordinates", e.getMessage());
    }

    @Test
    public void ensuregetParkWithCoordinatesThrowsExceptionWhenThereAreNoLocationsWithCoordinates(){
        when(this.pf.getLocationByCoordinates(80.0,20.0)).thenThrow(InvalidDataException.class);
        InvalidDataException e1= assertThrows(InvalidDataException.class, () -> {
            this.prc.getParkWithCoordinates(80.0, 20.0);
        });
    }

    @Test
    public void ensuregetParkWithCoordinatesThrowsExceptionWhenThereAreNoParksWithCoordinates(){
        InterestPoint ip = new InterestPoint(1,"teste",80.0, 20.0,30.0);
        when(this.pf.getLocationByCoordinates(80.0,20.0)).thenReturn(ip);
        InvalidDataException e1= assertThrows(InvalidDataException.class, () -> {
            this.prc.getParkWithCoordinates(80.0, 20.0);
        });

        assertEquals("No Park with these coordinates",e1.getMessage());
    }

}

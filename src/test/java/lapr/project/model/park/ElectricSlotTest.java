package lapr.project.model.park;


import lapr.project.model.bicycle.Battery;
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ElectricSlotTest {
    /**
     * A variable representative of the id of the electrical slot.
     */
    private int slotId;
    /**
     * A variable representative of the maximum capacity of an electrical slot.
     */
    private int maximumCapacity;
    /**
     * A variable representative of the number of free slots of an electrical slot.
     */
    private int numberFreeSlots;
    /**
     * A variable representative of an instance of electrical slot.
     */
    private ElectricSlot electricSlot;

    /**
     * Setup of the tests.
     */
    @BeforeEach
    public void setUp() {
        this.slotId = 1;
        this.maximumCapacity = 20;
        this.numberFreeSlots = 15;
        this.electricSlot = new ElectricSlot(slotId, maximumCapacity, numberFreeSlots,220,50);
    }

    @Test
    public void ensureGetChargeRateWorks() {
        System.out.println("Test ensure get charge rate works!");
        double result = this.electricSlot.getChargeRate();
        double expResult = 220;
        assertEquals(expResult, result);

    }

    @Test
    public void ensureGetIdSlotWorks() {
        System.out.println("Test ensure get id electrical slot works!");
        int result = this.electricSlot.getSlotId();
        int expectedResult = 1;
        assertEquals(expectedResult, result);

    }

    @Test
    public void ensureGetMaximumCapacitySlotWorks() {
        System.out.println("Test ensure get Maximum Capacity works!");
        int result = this.electricSlot.getMaximumCapacity();
        int expResult = 20;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureGetNUmberFreeSlotsWorks() {
        System.out.println("Test ensure get Free Slots works!");
        int result = this.electricSlot.getNumberFreeSlots();
        int expResult = 15;
        assertEquals(expResult, result);
    }



    @Test
    public void ensureEqualsWithNullObjectsReturnsFalse() {
        Object o = null;
        boolean result = this.electricSlot.equals(o);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureEqualsWithDifferentClassObjectsReturnsFalse() {
        String s = "test";
        boolean result = this.electricSlot.equals(s);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureEqualsWithElectricalSlotsWithDifferentIdsToReturnFalse() {
        ElectricSlot e = new ElectricSlot(2, 20, 15,220,50);
        boolean result = this.electricSlot.equals(e);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    /**
     * Should return true because the distinguisher is the id.
     */
    @Test
    public void ensureEqualsWithDifferentCapacitiesRetursnTrue() {
        ElectricSlot e = new ElectricSlot(1, 21, 15,220,50);
        boolean result = this.electricSlot.equals(e);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Should return true since the only distinguisher is the identifier.
     */
    @Test
    public void ensureEqualsWithDifferentFreelotsReturnTrue() {
        ElectricSlot e = new ElectricSlot(1, 20, 16,220,50);
        boolean result = this.electricSlot.equals(e);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Should return true since the only distinguisher is the identifier.
     */
    @Test
    public void ensureEqualsWithDifferentChargesToReturnTrue() {
        ElectricSlot e = new ElectricSlot(1, 20, 15,220,50);
        boolean result = this.electricSlot.equals(e);
        boolean expResult = true;
        assertEquals(result, expResult);
    }

    /**
     * Should return true since the only distinguisher is the identifier.
     */
    @Test
    public void ensureEqualsWithSameElectricalSlotReturnsTrue() {
        ElectricSlot e = new ElectricSlot(1, 20, 15,220,50);
        boolean result = this.electricSlot.equals(e);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureHashCodesWithDifferentElectricalSlotsWithSameIdReturnSameHashCode() {
        ElectricSlot e1 = new ElectricSlot(1, 20, 20,220,50);
        assertEquals(e1.hashCode(), this.electricSlot.hashCode());
    }

    @Test
    public void ensureHashCodesWithDifferentElectricalSlotsWithDifferentIdReturnDifferentHashCode() {
        ElectricSlot e1 = new ElectricSlot(2, 20, 20,220,50);
        assertNotEquals(e1.hashCode(), this.electricSlot.hashCode());
    }
    @Test
    public void ensureGetPowerEletricSlotWorks(){
        assertEquals(11000.0,this.electricSlot.getPower());
    }

    @Test
    public void ensureGetChargerPerSlotWIthNegativeBikesReturnsAllPower(){
        assertEquals(11000.0, this.electricSlot.chargePerSlot(-1));
    }
    @Test
    public void ensureGetChargerPerSlotWIthZeroBikesReturnsAllPower(){
        assertEquals(11000.0, this.electricSlot.chargePerSlot(0));
    }

    @Test
    public void ensureGetChargerPerSlotWIthN4BikesReturnsFourthPower(){
        assertEquals(2750.0, this.electricSlot.chargePerSlot(4));
    }

    @Test
    public void ensureBikeTimeInMinutesWOrk(){
        ElectricBicycle eb = new ElectricBicycle("IDteste", Bicycle.statusByCode(2),new Battery(1,100,0,1),1,10,5.0);
        assertEquals(6000,this.electricSlot.calculateTimeToGetBatteryChargedInMinuts(eb,1));
    }

    @Test
    public void ensureSetDataThrowsExceptionWhenMaximumCapacityIsNegative(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            ElectricSlot nS = new ElectricSlot(2,-2,2,1,1);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());

    }
    @Test
    public void ensureSetDataThrowsExceptionWhenMaximumCapacityIsZero(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            ElectricSlot nS = new ElectricSlot(2,0,2,1,1);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNumberFreeSlotsIsNegative(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            ElectricSlot nS = new ElectricSlot(2,3,-2,1,1);
        });
        assertEquals("Number of free slots must not be negative", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNumberFreeSlotsIsBiggerThanMaximum(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            ElectricSlot nS = new ElectricSlot(2,3,4,1,1);
        });
        assertEquals("Number of free slots must not be bigger than maximum capacity", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionIfChargeRateIsNegative(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            ElectricSlot nS = new ElectricSlot(2,5,4,-6,1);
        });
        assertEquals("Charge rate must be bigger than 0", e.getMessage());
    }

    @Test
    public void ensureSetDataThrowsExceptionIfIntensityIsNegative(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            ElectricSlot nS = new ElectricSlot(2,5,4,6,0);
        });
        assertEquals("Intensity must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionIfIntensityIsZero(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            ElectricSlot nS = new ElectricSlot(2,5,4,5,0);
        });
        assertEquals("Intensity must be bigger than 0", e.getMessage());
    }


    @Test
    public void ensureSetDataUpdatesEverythingIfDataIsValid(){
        ElectricSlot expected =new ElectricSlot(2,5,2,5,5);
        ElectricSlot result = new ElectricSlot(2,1,1,9,9);
        result.setData(5,2,5,5);
        assertEquals(expected, result);
    }


}

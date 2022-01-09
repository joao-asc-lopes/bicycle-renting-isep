package lapr.project.model.park;

import lapr.project.utils.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class NormalSlotsTests {
    /**
     * Variable representative of the slot Id of this normal slots.
     */
    private int slotId;
    /**
     * Variable representative of the maximum capacity of this normal slot.
     */

    private int maximumCapacity;
    /**
     * Variable representatitve of the free slots existing in this normal slot.
     */

    private int freeSlots;
    /**
     * Variable representative of an instance of normalSlots.
     */

    private NormalSlots normalSlots;

    /**
     * Setup for the tests.
     */
    @BeforeEach
    public void setUp() {
        this.slotId = 1;
        this.freeSlots = 10;
        this.maximumCapacity = 20;
        this.normalSlots = new NormalSlots(slotId, maximumCapacity, freeSlots);

    }

    @Test
    public void ensureGetSlotIdWorks() {
        System.out.println("Ensure Get Slot Id works!");
        int result = this.normalSlots.getSlotId();
        int expResult = 1;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureGetFreeSlotsWorks() {
        System.out.println("Ensure Get Free Slots works!");
        int result = this.normalSlots.getNumberFreeSlots();
        int expResult = 10;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureGetMaximumCapacityWorks() {
        System.out.println("Ensure get Maximum Capacity Works");
        ;
        int result = this.normalSlots.getMaximumCapacity();
        int expResult = 20;
        assertEquals(expResult, result);
    }


    @Test
    public void ensureTestNormalSlotWithNullObject() {
        Object o = null;
        boolean result = this.normalSlots.equals(o);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureTestWithDifferentClassesReturnsFalse() {
        String test = "t";
        boolean result = this.normalSlots.equals(test);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureNormalSlotsWithDifferentIdsReturnsFalse() {
        NormalSlots e = new NormalSlots(2, 20, 10);
        boolean result = this.normalSlots.equals(e);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    /**
     * Should return true because their distinguisher is the identifier.
     */
    @Test
    public void ensureNormalSlotsWithDifferentCapacitiesReturnsTrue() {
        NormalSlots e = new NormalSlots(1, 21, 10);
        boolean result = this.normalSlots.equals(e);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Should return true because their distinguisher is the identifier.
     */
    @Test
    public void ensureNormalSlotsWithDifferentFreeSlotsReturnsTrue() {
        NormalSlots e = new NormalSlots(1, 20, 11);
        boolean result = this.normalSlots.equals(e);
        boolean expResult = true;
        assertEquals(result, expResult);
    }

    /**
     * Should return true because their distinguisher is the identifier.
     */
    @Test
    public void ensureNormalSlotsEqualRetursnTrue() {
        NormalSlots e = new NormalSlots(1, 20, 10);
        boolean result = this.normalSlots.equals(e);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    @Test
    public void ensureDifferentNormalSlotsWithTheSameIdentifierReturnTheSameHashCode() {
        NormalSlots n1 = new NormalSlots(1, 20, 20);
        assertEquals(n1.hashCode(), this.normalSlots.hashCode());
    }

    @Test
    public void ensureDifferentNormalSlotsWithDifferentIdentifiersReturnDifferentHashCodes() {
        NormalSlots n2 = new NormalSlots(2, 10, 10);
        assertNotEquals(n2.hashCode(), this.normalSlots.hashCode());
    }

    @Test
    public void ensureSetDataThrowsExceptionWhenMaximumCapacityIsNegative(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            NormalSlots nS = new NormalSlots(2,-2,2);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());

    }
    @Test
    public void ensureSetDataThrowsExceptionWhenMaximumCapacityIsZero(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            NormalSlots nS = new NormalSlots(2,0,2);
        });
        assertEquals("Maximum capacity must be bigger than 0", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNumberFreeSlotsIsNegative(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            NormalSlots nS = new NormalSlots(2,1,-2);
        });
        assertEquals("Number of free slots must not be negative", e.getMessage());
    }
    @Test
    public void ensureSetDataThrowsExceptionWhenNumberFreeSlotsIsBiggerThanMaximum(){
        InvalidDataException e = assertThrows(InvalidDataException.class, () -> {
            NormalSlots nS = new NormalSlots(2,2,3);
        });
        assertEquals("Number of free slots must not be bigger than maximum capacity", e.getMessage());
    }

    @Test
    public void ensureSetDataUpdatesEverythingIfDataIsValid(){
        NormalSlots expected =new NormalSlots(2,5,2);
        NormalSlots result = new NormalSlots(2,1,1);
        result.setData(5,2);
        assertEquals(expected, result);
    }
}

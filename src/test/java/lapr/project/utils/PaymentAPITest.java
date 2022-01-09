package lapr.project.utils;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentAPITest {
    /**
     * Testing System.out.printlns
     */
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /**
     * Testing the System.out.println
     */


    @Test
    public void ensurePaymentAPIWorks() {
        System.setOut(new PrintStream(outContent));
        boolean result = PaymentAPI.performInitalPayment(999999999);
        assertTrue(result);
    }

    @Test
    public void testPayReceipt(){
        boolean result = PaymentAPI.payReceipt("teste@teste.com",30.0);
        assertTrue(result);
    }

    @Test
    public void ensureApiWorks(){
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            PaymentAPI api = new PaymentAPI();
        });
        assertEquals("Utility class", e.getMessage());

    }
}

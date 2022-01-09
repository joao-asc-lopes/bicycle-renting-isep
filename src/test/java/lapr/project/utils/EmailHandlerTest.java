package lapr.project.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailHandlerTest {
    private EmailHandler ec;

    public EmailHandlerTest() {
    }

    @BeforeEach
    public void setUp() {
        this.ec = new EmailHandler("target/classes/application_test.properties");
    }


    /**
     * Test of sendEmail when Email is null
     */
    @Test
    public void testSendEmailWhenEmailIsNull() {
        System.out.println("testSendEmailWhenEmailIsNull");
        boolean result = ec.sendEmail(null, "");
        assertTrue(!result);
    }

    /**
     * Test of sendEmail when Subject is Null
     */
    @Test
    public void testSendEmailWhenSubjectInNull() {
        System.out.println("testSendEmailWhenSubjectInNull");
        boolean result = ec.sendEmail("lapr3grupo40@gmail.com", null);
        assertTrue(!result);
    }

    /**
     * Test of sendEmail when Email is Invalid
     */
    @Test
    public void testSendEmailWhenEmailIsInvalid() {
        System.out.println("testSendEmailWhenEmailIsInvalid");
        boolean result = ec.sendEmail("test", "subject");
        assertTrue(!result);
        result = ec.sendEmail("@test.com", "subject");
        assertTrue(!result);
        result = ec.sendEmail(".com", "subject");
        assertTrue(!result);
        result = ec.sendEmail("test@test", "subject");
        assertTrue(!result);
        result = ec.sendEmail("test.com", "subject");
        assertTrue(!result);
    }

    /**
     * Test of sendEmail when everything is null
     */
    @Test
    public void testSendEmailWhenEverythingIsNull() {
        System.out.println("Test Send email when everything is null!");
        boolean result = ec.sendEmail(null, null);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    @Test
    public void testSendEmailWhenFileNotFound() {
        this.ec = new EmailHandler("");
        System.out.println("Test Send email when file not found");
        boolean result = ec.sendEmail("lapr3grupo40@gmail.com", "testes");
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    @Test
    public void testSendEmailWhenFileIsEmptyOrDontHaveCredendials() {
        System.out.println("Test Send email when file not found");
        this.ec = new EmailHandler("target/test-classes/application_empty.properties");
        boolean result = ec.sendEmail("lapr3grupo40@gmail.com", "testes");
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    @Test
    public void testSendEmailWithFakeCredentials() {
        System.out.println("Test Send email when properties are incorrect");
        this.ec = new EmailHandler("target/test-classes/application_fake.properties");
        boolean result = ec.sendEmail("lapr3grupo40@gmail.com", "testes");
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    /**
     * Test of sendEmail with correct data
     */
    @Test
    public void testSendEmailWithCorrectData() {
        this.ec = new EmailHandler();
        boolean result = ec.sendEmail("lapr3grupo40@gmail.com", "testes");
        assertTrue(result);
    }

}


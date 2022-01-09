package lapr.project.utils;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public final class PaymentAPI {

    private static final Logger LOGGER = Logger.getLogger( PaymentAPI.class.getName() );
    /**
     * Private constructor to hide the public one, since this class only have static methods
     */
    public PaymentAPI() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * Debits the initial payment cost from the given credit card.
     */
    public static boolean performInitalPayment(long ccNumber) {
        LOGGER.log(INFO,"Payment with success with credit card "+ccNumber);
        return true;
    }

    /**
     * THIS METHOD SHOULD BE REPLACED BY A REAL PAYMENT SYSTEM
     *
     * Method that debits the value received in the user acoount
     * @param username Email of the user
     * @param value Value to be taken from the user
     * @return True if operation works with success, false otherwise
     */
    public static boolean payReceipt(String username, double value){
        LOGGER.log(INFO,"User "+username+"pay a receipt with the value of "+value);
        return true;
    }
}

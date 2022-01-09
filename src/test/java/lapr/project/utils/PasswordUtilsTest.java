package lapr.project.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilsTest {
    private String password;
    private String salt;
    private String encryptedPassword;

    @BeforeEach
    public void setUp() {

        this.password = "mynewpassword123";
        this.salt = PasswordUtils.getSalt();
        this.encryptedPassword = PasswordUtils.generateSecurePassword(password, salt);
    }

    @Test
    public void ensureGivingTwoEqualPasswordsReturnsTrue() {
        String instance = "mynewpassword123";
        boolean result = PasswordUtils.verifyUserPassword(instance, encryptedPassword, salt);
        assertTrue(result);
    }

    @Test
    public void ensureGivingTwoDifferentPasswordsReturnsFalse() {
        String instance = "mynewpassword124";
        boolean result = PasswordUtils.verifyUserPassword(instance, encryptedPassword, salt);
        assertFalse(result);
    }

    @Test
    public void ensureEncryptedPasswordIsDifferentWithDifferentSalt() {
        String instance = "mynewpassword123";
        String newSalt = PasswordUtils.getSalt();
        boolean result = PasswordUtils.verifyUserPassword(instance, encryptedPassword, newSalt);
        assertFalse(result);
    }

    @Test
    public void ensureEncryptedPasswordIsActuallyEncrypted() {
        boolean result = encryptedPassword.equals(password);
        assertFalse(result);
    }

    @Test
    public void ensureComparisonReturnsFalseIfAnyFieldIsNull() {
        boolean result = PasswordUtils.verifyUserPassword(null, encryptedPassword, salt);
        assertFalse(result);
        result = PasswordUtils.verifyUserPassword(password, null, salt);
        assertFalse(result);
        result = PasswordUtils.verifyUserPassword(password, encryptedPassword, null);
        assertFalse(result);
    }

    @Test
    public void ensureGeneratePasswordReturnsNullIfGivenPasswordIsNullOrEmptyField() {
        assertNull(PasswordUtils.generateSecurePassword("", salt));
        assertNull(PasswordUtils.generateSecurePassword(null, salt));
        assertNull(PasswordUtils.generateSecurePassword(password, null));
    }

    @Test
    public void ensureNullPasswordsReturnFalse() {
        assertFalse(PasswordUtils.verifyUserPassword(null, null, null));

    }

}

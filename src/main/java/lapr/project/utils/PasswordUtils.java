package lapr.project.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public final class PasswordUtils {
    /**
     * Variable representative of a random instanciation.
     */
    private static final Random RANDOM = new SecureRandom();
    /**
     * Variable that is representative of all the alphabet.
     */
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    /**
     * Variable representative of all the doable iterations.
     */
    private static final int ITERATIONS = 10000;
    /**
     * Variable representative of the length of the key.
     */
    private static final int KEY_LENGTH = 256;
    /**
     * Variable representative of the salt size.
     */
    private static final int SALT_SIZE = 10;

    /**
     * Private constructor to hide implicit one.
     */
    private PasswordUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Generates salt in order to securely save a password.
     *
     * @return the randomly generated salt.
     */
    public static String getSalt() {
        StringBuilder returnValue = new StringBuilder(SALT_SIZE);
        for (int i = 0; i < SALT_SIZE; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    /**
     * Creates an hash of the given password, using the generated salt.
     * Uses PBKDF2 (Password-Based Key Derivation Function 2).
     *
     * @param password Given non encrypted user password.
     * @param salt     generated salt.
     * @return hash to be encoded using Base64.
     */
    private static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing the password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    /**
     * Generates an encrypted password using the encoder Base64 and PBKDF2.
     *
     * @param password user entered password.
     * @param salt     generated salt.
     * @return The encrypted user password.
     */
    public static String generateSecurePassword(String password, String salt) {
        if (password == null || salt == null || "".equals(password)) {
            return null;
        }
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }

    /**
     * Given a non encrypted password and an encrypted password with its salt, verifies if the passwords match.
     *
     * @param providedPassword The password entered by the user.
     * @param securedPassword  The encrypted password saved in the database.
     * @param salt             The previously generated salt in the data-base.
     * @return true if the passwords match, false if not.
     */
    public static boolean verifyUserPassword(String providedPassword, String securedPassword, String salt) {
        if (providedPassword == null || securedPassword == null || salt == null || "".equals(providedPassword)) {
            return false;
        }
        // Generate New secure password with the same salt
        String newSecurePassword = generateSecurePassword(providedPassword, salt);
        if (newSecurePassword == null) {
            return false;
        }
        // Check if two passwords are equal
        return newSecurePassword.equalsIgnoreCase(securedPassword);
    }
}

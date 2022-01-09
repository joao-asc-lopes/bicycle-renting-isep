package lapr.project.model.user;

import lapr.project.utils.InvalidDataException;
import lapr.project.utils.PasswordUtils;
import lapr.project.utils.PaymentAPI;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a user in the Application.
 */
public class User {
    private String username;
    /**
     * The user's name.
     */
    private String name;
    /**
     * The user's email and to be used as the login credential.
     */
    private String email;
    /**
     * The user's encrypted password.
     */
    private String encryptedPassword;
    /**
     * The generated salt used to encrypt the password.
     */
    private String salt;
    /**
     * The user's credit card number, to be used for payments.
     */
    private long ccNumber;
    /**
     * The user's height.
     */
    private float height;
    /**
     * The user's weight.
     */
    private float weight;
    /**
     * true if the user is currently renting a bicycle, false if not.
     */
    private boolean hasActiveRental;
    /**
     * Average speed of the user.
     */
    private double averageSpeed;
    /**
     * Points of the user.
     */
    private int points;

    /**
     * Creates a new instance in memory of the user.
     *
     * @param email
     * @param password
     * @param name
     * @param weight
     * @param height
     * @param ccNumber
     * @param hasActiveRental
     */
    public User(String username, String email, String password, String name, float weight, float height, long ccNumber, boolean hasActiveRental, double averageSpeed, int points) {
        setData(username, email, password, name, weight, height, ccNumber, hasActiveRental, averageSpeed, points);
    }

    /**
     * Creates a non-validated instance of user. To be used when loading class from database.
     *
     * @param email           The new user's email.
     * @param password        The new user's non-encrypted password.
     * @param salt            The user's generated salt used to encrypt the password.
     * @param name            The new user's name.
     * @param weight          The new user's weight.
     * @param height          The new user's height.
     * @param ccNumber        The new user's credit card number.
     * @param hasActiveRental True if the user currently has an active rental. False if not.
     */
    public User(String username, String email, String password, String salt, String name, float weight, float height, long ccNumber, boolean hasActiveRental, double averageSpeed) {
        this.username = username;
        this.email = email;
        this.encryptedPassword = password;
        this.salt = salt;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.ccNumber = ccNumber;
        this.hasActiveRental = hasActiveRental;
        this.averageSpeed = averageSpeed;
        this.setPoints(0);
    }

    /**
     * Validates and updates the data of this User, making validations to the email and encrypting the password.
     *
     * @param username        The new user's username.
     * @param email           The new user's email.
     * @param password        The new user's non-encrypted password.
     * @param name            The new user's name.
     * @param weight          The new user's weight.
     * @param height          The new user's height.
     * @param ccNumber        The new user's credit card number.
     * @param hasActiveRental True if the user currently has an active rental. False if not.
     * @param points
     */
    private void setData(String username, String email, String password, String name, float weight, float height, long ccNumber, boolean hasActiveRental, double averageSpeed, int points) {
        if (username == null) {
            throw new InvalidDataException("The username is not valid!");
        }
        if (!validateEmail(email)) {
            throw new InvalidDataException("The email syntax is not valid.");
        }
        this.email = email;
        this.salt = PasswordUtils.getSalt();
        String tempPassword = PasswordUtils.generateSecurePassword(password, salt);
        if (tempPassword == null) {
            throw new InvalidDataException("The password is not valid.");
        }
        this.username = username;
        this.encryptedPassword = tempPassword;
        this.name = name;
        if(weight < 0 ){
            throw new InvalidDataException("The weight of the user is not valid.");
        }
        this.weight = weight;
        if(height< 0){
            throw new InvalidDataException("The height of the user is not valid!");
        }
        this.height = height;
        this.ccNumber = ccNumber;
        this.hasActiveRental = hasActiveRental;
        if(averageSpeed < 0){
            throw new InvalidDataException("The speed is not valid.");
        }
        this.averageSpeed = averageSpeed;
        this.setPoints(points);
    }

    /**
     * Debits the money associated with the first payment of the user from its credit card.
     */
    public boolean performInitialPayment() {
        return PaymentAPI.performInitalPayment(this.ccNumber);
    }

    /**
     * The user's username.
     */
    /**
     * Method that allows the user to get the username of this instance of user.
     *
     * @return Returns the username of user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method that allows the user to get the name of this instance of user.
     *
     * @return Returns the name of the user.
     */

    public String getName() {
        return name;
    }

    /**
     * Method that allows the user to get the email of this instance of user.
     *
     * @return The email of this instance of user.
     */

    public String getEmail() {
        return email;
    }

    /**
     * Method that allows the user to get the encrypted password of this instance of user.
     *
     * @return The encrypted password of this instance of user.
     */

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    /**
     * Method that allows the user to get the salt of this instance of user.
     *
     * @return The salt of this instance of user.
     */

    public String getSalt() {
        return salt;
    }

    /**
     * Method that allows the user to get the CC number of this instance of user.
     *
     * @return The CC number of this instance of number.
     */

    public long getCCNumber() {
        return ccNumber;
    }

    /**
     * Method that allows the user to receive the height of this instance of user..
     *
     * @return The height of this instance of user.
     */

    public float getHeight() {
        return height;
    }

    /**
     * Method that allows the user to receive the weight of this instance of user.
     *
     * @return The weight of this instance of user.
     */

    public float getWeight() {
        return weight;
    }

    /**
     * Method that allows the user to get if this instance of user has an active rental.
     *
     * @return True if it has, False if not.
     */


    public boolean hasActiveRental() {
        return hasActiveRental;
    }

    /**
     * Marks the user has currently having a rental.
     */
    public void activateRental() {
        this.hasActiveRental = true;
    }

    /**
     * Marks the user as currently not having a rental.
     */
    public void endRental() {
        this.hasActiveRental = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    /**
     * Hashcode method.
     *
     * @return Int representative of the hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    /**
     * Validates the email format.
     *
     * @param email to be validated
     */
    private static boolean validateEmail(String email) {
        //Validates if email is null.
        if (email == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    /**
     * The user's points
     */
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Average speed of the user
     */
    public double getAverageSpeed() {
        return averageSpeed;
    }
}

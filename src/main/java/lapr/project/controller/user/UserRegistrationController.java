package lapr.project.controller.user;

import lapr.project.model.user.UserFacade;


public class UserRegistrationController {
    /**
     * Private variable of an instance of UserFacade.
     */
    private UserFacade fc;

    /**
     * Constructor that instanciates the private variables.
     */

    public UserRegistrationController() {
        this.fc = new UserFacade();
    }

    /**
     * Creates a new instance of user, saving him in the database and extracting the initial payment from the given credit card.
     *
     * @param email    The new user's email.
     * @param password The new user's non-encrypted password.
     * @param name     The new user's name.
     * @param weight   The new user's weight.
     * @param height   The new user's height.
     * @param ccNumber The new user's credit card number.
     */
    public boolean registerUser(String username, String email, String password, String name, float weight, float height, long ccNumber,double averageSpeed) {
        return fc.registerUser(username, email, password, name, weight, height, ccNumber,averageSpeed);
    }
}

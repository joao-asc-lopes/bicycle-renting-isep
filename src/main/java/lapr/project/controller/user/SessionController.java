package lapr.project.controller.user;

import lapr.project.model.user.User;
import lapr.project.model.user.UserService;
import lapr.project.utils.InvalidDataException;
import lapr.project.utils.PasswordUtils;

public class SessionController {
    /**
     * The currently logged in user in the system.
     */
    private User loggedUser;
    /**
     * An instance of user service.
     */
    private UserService us;

    /**
     * Creates an instance of the current session in the system.
     */
    public SessionController() {
        this.loggedUser = null;
        this.us = new UserService();
    }

    /**
     * Returns the user who just logged in, given his email.
     *
     * @return The user currently logged in.
     */
    public User getLoggedUser() {
        if (loggedUser == null) {
            throw new InvalidDataException("No user logged in.");
        }
        return loggedUser;
    }


    /**
     * Logs a user to the system.
     *
     * @param username Username of the user who just logged in.
     */
    public User logUser(String username, String password) {
        if (loggedUser != null) {
            return null; //should be impossible for this to happen, but just in case.
        }
        this.loggedUser = checkCredentials(username, password);
        return loggedUser;
    }

    /**
     * Logs out the current user
     */
    public void logOutUser(){
        this.loggedUser = null;
    }

    /**
     * Verifies if the given credentials match a user.
     *
     * @param username    Username of the user trying to log in.
     * @param password Password of the user trying to log in.
     * @return true if the credentials match, false if not.
     */
    private User checkCredentials(String username, String password) {
        User user = us.getUser(username);
        if (user != null) {
            if (!PasswordUtils.verifyUserPassword(password, user.getEncryptedPassword(), user.getSalt())) {
                throw new InvalidDataException("The password is incorrect.");
            }
            return user;
        }
        throw new InvalidDataException("No user with given username.");
    }

}

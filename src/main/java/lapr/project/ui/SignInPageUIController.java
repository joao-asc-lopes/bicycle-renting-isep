/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lapr.project.controller.user.SessionController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author joaoLopes
 */
public class SignInPageUIController implements Initializable {

    @FXML
    private Button signInButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    /**
     * Instance of the the SessionController, which will keep track of which user is logged in.
     */
    private SessionController currentSession;

    private static final int WIDTH = 335;
    /**
     * Window's minimum height.
     */
    private static final int HEIGHT = 600;

    private static final String FAILED = "Sign In Failed";

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.currentSession = new SessionController();
    }

    /**
     * Handles the Create Account HyperLink in the GUI. Redirects the User to
     * the Account Creation scene.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleCreateAccountHyperLinkAction(ActionEvent event) throws IOException {
        //opens the user registration GUI.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserRegistrationScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage userRegistrationStage = new Stage();
        userRegistrationStage.initModality(Modality.APPLICATION_MODAL);
        userRegistrationStage.setTitle("Create Account");
        userRegistrationStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        userRegistrationStage.setMinHeight(userRegistrationStage.getHeight());
        userRegistrationStage.setMinWidth(userRegistrationStage.getWidth());
        userRegistrationStage.setResizable(false);
        userRegistrationStage.show();
    }

    @FXML
    private void handleSignInButtonAction(ActionEvent event) {
        String email = this.usernameField.getText().trim();
        String password = this.passwordField.getText().trim();
        if (email.isEmpty()) {
            UIControllerUtils.showAlert(Alert.AlertType.ERROR, "Email Invalid", FAILED, "Email field must not be empty.");
        } else if (password.isEmpty()) {
            UIControllerUtils.showAlert(Alert.AlertType.ERROR, "Password Invalid", FAILED, "Password field must not be empty.");
        } else {
            try {
                this.currentSession.logUser(email, password);

                openStartPageScene();
                UIControllerUtils.closeWindow(signInButton);
            } catch (Exception e) {
                UIControllerUtils.showAlert(Alert.AlertType.ERROR, "Unexpected Error", FAILED, e.getMessage());
            }
        }
    }

    /**
     * When the sign in is successful it opens the Application's start page.
     */

    private void openStartPageScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainPageScene.fxml"));
        Parent root = loader.load();
        //loads and initializes necessary information.
        MainPageUIController mainPgUIController = loader.getController();
        mainPgUIController.setCurrentSession(currentSession);

        Scene scene = new Scene(root);
        Stage startPageStage = new Stage();
        startPageStage.setTitle("Start Page");
        startPageStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.

        startPageStage.setMinHeight(HEIGHT);
        startPageStage.setMaxHeight(HEIGHT);
        startPageStage.setMinWidth(WIDTH);
        startPageStage.setMaxWidth(WIDTH);
        startPageStage.show();
    }


}

package lapr.project.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lapr.project.controller.user.SessionController;

import java.io.IOException;

public class MainPageUIController {
    /**
     * Represents the current session of a user in the system.
     * All users must be logged in to interact with the system.
     */
    private SessionController currentSession;

    /**
     * Variable that represents the button that allows the user to rent a bicycle.
     */
    @FXML
    private Button btnRent;
    /**
     * Variable that represents a button that allows to go back.
     */

    @FXML
    private Button btnLogOut;


    /**
     * Handle of the btnRent.
     *
     * @param event The click on the button rent transports the user to a rental of a bicycle.
     */
    @FXML
    void handleBtnRent(ActionEvent event) throws IOException {
        //opens the user registration GUI.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AvailableBicyclesScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);



        RentBicycleUIController rentBicycleUIController = loader.getController();
        rentBicycleUIController.initialize(this.currentSession);
        Stage userRegistrationStage = new Stage();
        userRegistrationStage.initModality(Modality.APPLICATION_MODAL);
        userRegistrationStage.setTitle("Rent a Bike!");
        userRegistrationStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        userRegistrationStage.setMinHeight(userRegistrationStage.getHeight());
        userRegistrationStage.setMinWidth(userRegistrationStage.getWidth());
        userRegistrationStage.setResizable(false);

        UIControllerUtils.closeWindow(this.btnRent);
        userRegistrationStage.show();

    }

    /**
     * Allows the user to go back.
     * @param event The click on btnLogout.
     */
    @FXML
    void handleCalculateDistance(ActionEvent event) {

    }


    /**
     * Sets the current session in memory.
     *
     * @param cs The current session.
     */
    void setCurrentSession(SessionController cs) {
        this.currentSession = cs;
    }
}

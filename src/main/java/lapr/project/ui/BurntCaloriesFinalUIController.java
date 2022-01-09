package lapr.project.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lapr.project.controller.user.BurntCaloriesController;
import lapr.project.controller.user.SessionController;
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bikenetwork.Path;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.io.IOException;


public class BurntCaloriesFinalUIController {
    /**
     * Variable that represents the starting park.
     */
    @FXML
    public TextField txtStartPark;
    /**
     * Variable that represents a button.
     */
    @FXML
    private Button btnCalories;


    @FXML
    private TextField txtAverageSpeed;
    /**
     * TextField non-editable that contains the name of the chosen Bicycle.
     */

    @FXML
    private TextField txtChosenBicycle;
    /**
     * TextField non-editable that contains the name of the starting park.
     */

    @FXML
    private TextField txtFinishingPark;
    /**
     * Variable that represents the chosen bicycle.
     */
    private Bicycle bicycle;
    /**
     * Variable that represents the current session user.
     */

    private SessionController currentSession;
    /**
     * Variable that represents the starting park.
     */
    private Park startingPark;
    /**
     * Variable that represents the finishing park.
     */

    private Park finishingPark;
    /**
     * Variable that represents the controller.
     */

    private BurntCaloriesController bcc;

    private Path wind;


    @FXML
    private Button btnBack;

    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);

    }

    /**
     * Initialize that represents the parkRegistry.
     */

    public void initialize(SessionController currentSession, Park startingPark, Park finishingPark, Bicycle chosenBicycle) {
        this.currentSession = currentSession;
        this.startingPark = startingPark;
        this.finishingPark = finishingPark;
        this.bicycle = chosenBicycle;
        this.bcc = new BurntCaloriesController();
        this.txtStartPark.setText(this.startingPark.getName());
        this.txtFinishingPark.setText(this.finishingPark.getName());
        this.txtChosenBicycle.setText(this.bicycle.toString());
        wind = this.bcc.getPathBy2Parks(this.startingPark, this.finishingPark);

    }

    @FXML
    void handleBtnCalories(ActionEvent event) throws IOException {
        try {
            if (this.txtAverageSpeed.getText().trim() == null) {
                throw new InvalidDataException("Please insert the average speed!");
            }

            double averageSpeed = Double.parseDouble(this.txtAverageSpeed.getText());
            if (averageSpeed < 0) {
                throw new InvalidDataException("Please insert a correct value for the average speed!");
            }

            UIControllerUtils.showAlert(Alert.AlertType.INFORMATION, "Information", "Burnt Calories in the trip!", bcc.calculateBurntCalories(averageSpeed, this.bicycle.getWeight(), this.bicycle.getFrontalArea(), this.currentSession.getLoggedUser().getWeight(), this.currentSession.getLoggedUser().getHeight(), this.startingPark.getLatitude(), this.startingPark.getLongitude(), this.finishingPark.getLatitude(), this.finishingPark.getLongitude(), this.bicycle.getBicycleAerodynamicalCoefficient(),this.startingPark.getAltitude(), this.finishingPark.getAltitude(), wind.getWindDirection(), wind.getWindSpeed()) + " joules");

        } catch (InvalidDataException e) {
            UIControllerUtils.showAlert(Alert.AlertType.ERROR, "ERROR!", "Error", e.getMessage());
        } catch (NumberFormatException nm) {
            UIControllerUtils.showAlert(Alert.AlertType.ERROR, "ERROR!", "Error", nm.getMessage());
        }

    }

}

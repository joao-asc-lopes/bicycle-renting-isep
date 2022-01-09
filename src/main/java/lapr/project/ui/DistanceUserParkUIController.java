package lapr.project.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lapr.project.utils.InvalidDataException;

import java.io.IOException;

public class DistanceUserParkUIController {
    /**
     * Variable of a button that allows to go back.
     */
    @FXML
    private Button btnBack;
    /**
     * Variable that represents the button that allows the user to choose a park.
     */
    @FXML
    private Button btnChoosePark;
    /**
     * Variable that represents the textField that allows the user to Introduce the latitude.
     */
    @FXML
    private TextField txtLatitude;
    /**
     * Variable that repreents the textField that allows the user to introduce the longitude.
     */

    @FXML
    private TextField txtLongitude;

    /**
     * Allows the user to go back.
     * @param event The click in btnBack.
     */
    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);

    }

    /**
     * Method that allows the user to choose the park only after introduced its coordinates.
     *
     * @param event The click in the button choosepark.
     */

    @FXML
    void handleBtnChoosePark(ActionEvent event) throws IOException {
        try {
            if (this.txtLatitude.getText().trim() == null || this.txtLongitude.getText().trim() == null) {
                throw new InvalidDataException("Introduce the required data!");
            }
            int latitude = Integer.parseInt(txtLatitude.getText().trim());
            int longitude = Integer.parseInt(txtLongitude.getText().trim());
            if(latitude > 90 || latitude < -90){
                throw new InvalidDataException("Please, insert a correct value for the latitude!");
            }
            if(longitude >180 || longitude <-180){
                throw new InvalidDataException("Please, insert a correct value for the longitude!");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseParkDistanceUserParkScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ChooseParkDistanceUserParkUIController dmuii = loader.getController();
            dmuii.initialize(latitude,longitude);
            Stage chooseParkDistanceUserParkStage = new Stage();
            chooseParkDistanceUserParkStage.initModality(Modality.APPLICATION_MODAL);
            chooseParkDistanceUserParkStage.setTitle("Distance User Park!");
            chooseParkDistanceUserParkStage.setScene(scene);
            //sets the size of the window to the one defined in JavaFX.
            chooseParkDistanceUserParkStage.setMinHeight(chooseParkDistanceUserParkStage.getHeight());
            chooseParkDistanceUserParkStage.setMinWidth(chooseParkDistanceUserParkStage.getWidth());
            chooseParkDistanceUserParkStage.setResizable(false);
            chooseParkDistanceUserParkStage.show();

        } catch (InvalidDataException e) {
            UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR","Error",e.getMessage());

        } catch (NumberFormatException nm) {
            UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR","Error",nm.getMessage());

        }
    }
}

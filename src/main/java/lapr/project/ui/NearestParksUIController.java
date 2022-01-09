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

public class NearestParksUIController {
    /**
     * Variable that represents the button that allows the user to see the 5 cloesest parks.
     */
    @FXML
    private Button btnCheckNearestParks;
    /**
     * Variable that represents the textField that contains the inputted latitude from the user.
     */

    @FXML
    private TextField txtLatitude;
    /**
     * Variable that represents the textField that contains the inputted longitude from the user.
     */

    @FXML
    private TextField txtLongitude;


    @FXML
    private Button btnBack;

    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);

    }

    /**
     * Method that allows the user to go to the scene where it shows the 5 closest parks.
     * @param event The click in the button check nearest parks.
     */

    @FXML
    void btnCheckNearParks(ActionEvent event) throws IOException {
        try{
            if(this.txtLatitude.getText().trim() == null || this.txtLongitude.getText().trim() == null){
                throw new InvalidDataException("Introduce the required data!");
            }
            int latitude = Integer.parseInt(this.txtLatitude.getText());
            int longitude = Integer.parseInt(this.txtLongitude.getText());

            if(latitude > 90 || latitude < -90){
                throw new InvalidDataException("Please, insert a correct value for the latitude!");
            }
            if(longitude > 180 || longitude < -180){
                throw new InvalidDataException("Please, insert a correct value for the longitude!");
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NearestParksListParksScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            NearestParksListParksUIController dmuii = loader.getController();
            dmuii.initialize(latitude,longitude);
            Stage nearestParksListParkStage = new Stage();
            nearestParksListParkStage.initModality(Modality.APPLICATION_MODAL);
            nearestParksListParkStage.setTitle("Distance User Park!");
            nearestParksListParkStage.setScene(scene);
            //sets the size of the window to the one defined in JavaFX.
            nearestParksListParkStage.setMinHeight(nearestParksListParkStage.getHeight());
            nearestParksListParkStage.setMinWidth(nearestParksListParkStage.getWidth());
            nearestParksListParkStage.setResizable(false);
            nearestParksListParkStage.show();



        } catch(InvalidDataException e){
            UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR","Error",e.getMessage());
        } catch( NumberFormatException nm){
            UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR","Error",nm.getMessage());
        }
    }


}

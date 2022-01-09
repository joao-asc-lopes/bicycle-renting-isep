package lapr.project.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lapr.project.controller.user.SessionController;
import lapr.project.controller.user.ShortestPathParkToParkController;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.io.IOException;
import java.util.List;

public class FirstParkBurntCalloriesUIController {
    /**
     * Represents a variable that represents a list view of parks.
     */
    @FXML
    private ListView<Park> lstViewParks;
    /**
     * Variable that represents a button.
     */
    @FXML
    private Button btnPark;

    /**
     * Variable that represents the current session.
     */
    private SessionController currentSession;
    @FXML
    private Button btnBack;

    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);

    }

    /**
     * Initialize that represents the parkRegistry.
     */

    public void initialize(SessionController currentSession) {
        this.currentSession = currentSession;
        ShortestPathParkToParkController spc = new ShortestPathParkToParkController();
        List<Park> parkList = spc.getParkList();
        ObservableList<Park> obsParks = FXCollections.observableArrayList();
        obsParks.addAll(parkList);
        this.lstViewParks.setItems(obsParks);


    }


    @FXML
    void handleBtnPark(ActionEvent event) throws IOException {
        Park park = this.lstViewParks.getSelectionModel().getSelectedItem();

        try{
            if(park == null){
                throw new InvalidDataException("Please select a park!");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BurntCaloriesFinishingPark.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            LastParkBurntCalloriesUIController dmuii = loader.getController();
            dmuii.initialize(currentSession,park);
            Stage lastParkBurntCaloriesStage = new Stage();
            lastParkBurntCaloriesStage.initModality(Modality.APPLICATION_MODAL);
            lastParkBurntCaloriesStage.setTitle("Burnt Calories!");
            lastParkBurntCaloriesStage.setScene(scene);
            //sets the size of the window to the one defined in JavaFX.
            lastParkBurntCaloriesStage.setMinHeight(lastParkBurntCaloriesStage.getHeight());
            lastParkBurntCaloriesStage.setMinWidth(lastParkBurntCaloriesStage.getWidth());
            lastParkBurntCaloriesStage.setResizable(false);
            lastParkBurntCaloriesStage.show();
        } catch (InvalidDataException e){
            UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR!","Error.",e.getMessage());
        }

    }
}

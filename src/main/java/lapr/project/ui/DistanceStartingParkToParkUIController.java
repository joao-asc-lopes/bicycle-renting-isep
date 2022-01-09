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
import lapr.project.controller.user.AvailableBicyclesController;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.io.IOException;
import java.util.List;

public class DistanceStartingParkToParkUIController {


    /**
     * Variable that represents the button that allows the user to choose finishing park.
     */
    @FXML
    private Button btnChooseOtherPark;
    /**
     * Variable that represents a listView of Parks.
     */
    @FXML
    private ListView<Park> lstStartingPark;

    @FXML
    private Button btnBack;

    /**
     * Allows the user to go back.
     * @param event
     */

    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);

    }

    /**
     * Initialize that represents the parkRegistry.
     */

    public void initialize() {
        AvailableBicyclesController abc = new AvailableBicyclesController();
        List<Park> parkList = abc.getParkRegistry();
        ObservableList<Park> obsParks = FXCollections.observableArrayList();
        obsParks.addAll(parkList);
        this.lstStartingPark.setItems(obsParks);


    }

    /**
     * Method that allows the user to choose the finishing park.
     * @param event The event of clicking btnChooseOtherPark.
     */
    @FXML
    void handleBtnChooseOtherPark(ActionEvent event) throws IOException {
        Park park = this.lstStartingPark.getSelectionModel().getSelectedItem();

        try{
            if(park == null){
                throw new InvalidDataException("Please select a park!");
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DistanceFinishingParkScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            DistanceFinishingParkUIController dmuii = loader.getController();
            dmuii.initialize(park.getLatitude(), park.getLongitude());
            Stage distanceParkParkStage = new Stage();
            distanceParkParkStage.initModality(Modality.APPLICATION_MODAL);
            distanceParkParkStage.setTitle("Distance User Park!");
            distanceParkParkStage.setScene(scene);
            //sets the size of the window to the one defined in JavaFX.
            distanceParkParkStage.setMinHeight(distanceParkParkStage.getHeight());
            distanceParkParkStage.setMinWidth(distanceParkParkStage.getWidth());
            distanceParkParkStage.setResizable(false);
            distanceParkParkStage.show();
        } catch( InvalidDataException e){
            UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR","Error",e.getMessage());
        }


    }
}

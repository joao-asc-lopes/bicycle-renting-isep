package lapr.project.ui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import lapr.project.controller.user.AvailableBicyclesController;
import lapr.project.controller.user.CalculateDistanceParksController;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.util.List;


public class ChooseParkDistanceUserParkUIController {

    /**
     * Variable representative of the listview of parks.
     */
    @FXML
    private ListView<Park> lstPArks;
    /**
     * Variable representative of the user latitude.
     */
    private int userLatitude;
    /**
     * Variable representative of the user longitude.
     */

    private int userLongitude;


    private CalculateDistanceParksController cdpc;

    /**
     * Variable representative od the button that allows the user to calculate the distance.
     */

    @FXML
    private Button btnCalculateDistance;
    /**
     * Variable that represents a button to go back.
     */
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
     * Method that allows the user to calculate the distance between an user and a park.
     * @param event The click in the button btnCalculateDistance.
     */
    /**
     * Initialize that represents the parkRegistry.
     */

    public void initialize(int latitude, int longitude) {
        AvailableBicyclesController abc = new AvailableBicyclesController();
        this.cdpc = new CalculateDistanceParksController();
          List<Park> parkList = abc.getParkRegistry();
        ObservableList<Park> obsParks = FXCollections.observableArrayList();
        obsParks.addAll(parkList);
        this.lstPArks.setItems(obsParks);
        this.userLatitude = latitude;
        this.userLongitude = longitude;

    }
    @FXML
    void handleCalculateDistance(ActionEvent event) {
        Park park = this.lstPArks.getSelectionModel().getSelectedItem();
        try{
            if(park == null){
                throw new InvalidDataException("Select a park please!");
            }
          UIControllerUtils.showAlert(Alert.AlertType.INFORMATION,"Info","Distance User-Park",String.valueOf(this.cdpc.calculateDistanceParkUser(this.userLatitude,this.userLongitude,park)+" km"));

        } catch(InvalidDataException e){
            UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR","Error",e.getMessage());
        }

    }
}

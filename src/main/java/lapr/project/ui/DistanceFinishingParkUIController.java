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


public class DistanceFinishingParkUIController {
    /**
     * Variable that represents a listView of parks.
     */
    @FXML
    private ListView<Park> lstparks;
    /**
     * Variable that represents the button that allows to calculate the distance.
     */
    @FXML
    private Button btnCalculateDistance;

    /**
     * Variable that represents the controller that calcuklates the distances.
     */
    private CalculateDistanceParksController cdpc;
    /**
     * Variable representative of the starting park latitude.
     */
    private double startingParkLatitude;
    /**
     * Variable representative of the starting park longitude.
     */
    private double startingParkLongitude;

    @FXML
    private Button btnBack;

    @FXML
    void handleBtnBack(ActionEvent event) {

        UIControllerUtils.closeWindow(this.btnBack);
    }

    /**
     * Initialize that represents the parkRegistry.
     */

    public void initialize(double parkLatitude, double parkLongitude) {
        AvailableBicyclesController abc = new AvailableBicyclesController();
        this.cdpc = new CalculateDistanceParksController();
        List<Park> parkList = abc.getParkRegistry();
        ObservableList<Park> obsParks = FXCollections.observableArrayList();
        obsParks.addAll(parkList);
        this.lstparks.setItems(obsParks);
        this.startingParkLatitude = parkLatitude;
        this.startingParkLongitude = parkLongitude;


    }


    /**
     * Method that allows the user to see the distance between 2 parks.
     * @param event The click on the button calculate distance.
     */
    @FXML
    void handleBtnCalculateDistance(ActionEvent event) {
        Park park = this.lstparks.getSelectionModel().getSelectedItem();
        try{
            if(park == null){
                throw new InvalidDataException("Please select a park!");
            }

            UIControllerUtils.showAlert(Alert.AlertType.INFORMATION,"Info","Info","The distance between the 2 parks is : "+this.cdpc.calculateDistanceParkUser(this.startingParkLatitude,this.startingParkLongitude,park)+ " km");
        } catch(InvalidDataException e){
            UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR","Error",e.getMessage());
        }

    }
}

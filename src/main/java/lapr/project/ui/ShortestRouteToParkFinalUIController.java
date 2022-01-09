package lapr.project.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import lapr.project.controller.user.ShortestPathParkToParkController;
import lapr.project.model.bikenetwork.Route;
import lapr.project.model.park.Location;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.util.ArrayList;
import java.util.List;


public class ShortestRouteToParkFinalUIController {
    /**
     * Variable that represents a list view of parks.
     */
    @FXML
    private ListView<String> lstViewParks;
    /**
     * Variable that represents the button that allows the user to go to the previous scene.
     */

    @FXML
    private Button btnBack;

    /**
     * Method that allows the user to go to the previous scene.
     *
     * @param event
     */

    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);
    }

    public void initialize(Park startingPark, Park finishingPark) {
        ShortestPathParkToParkController spc = new ShortestPathParkToParkController();
        try {
            List <Route> parkIterable = spc.shortestPathParkToPark(startingPark, finishingPark);

            for (Route r : parkIterable) {
                List<String> auxList = new ArrayList<>();
                for(Location l : r.getPath()){
                    auxList.add(l.getName());
                }
                this.lstViewParks.getItems().addAll(auxList);
                this.lstViewParks.getItems().add("=======================");
            }

        } catch (InvalidDataException e) {
            UIControllerUtils.showAlert(Alert.AlertType.ERROR, "No path found", "", e.getMessage());
        }


    }
}

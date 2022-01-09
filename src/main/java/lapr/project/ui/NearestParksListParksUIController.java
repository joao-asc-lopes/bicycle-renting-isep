package lapr.project.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import lapr.project.controller.user.NearestParksController;
import lapr.project.model.park.Park;

import java.util.List;

public class NearestParksListParksUIController {
    /**
     * Variable that represents a listview of parks.
     */
    @FXML
    private ListView<Park> lstViewNearParks;

    /**
     * Initialize that represents the parkRegistry.
     */

    @FXML
    private Button btnBack;

    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);

    }

    public void initialize(int latitude, int longitude) {
        NearestParksController npc = new NearestParksController();
        List<Park> parkList = npc.getClosestFiveParksFromUser(latitude,longitude);
        ObservableList<Park> obsParks = FXCollections.observableArrayList();
        obsParks.addAll(parkList);
        this.lstViewNearParks.setItems(obsParks);
    }
}

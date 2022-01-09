package lapr.project.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import lapr.project.controller.user.SessionController;
import lapr.project.controller.user.ShortestPathEfficientElectricalController;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.bikenetwork.Route;
import lapr.project.model.park.Location;
import lapr.project.model.park.Park;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShortestElectricalPathUIController {
    /**
     * Variable that represents a list view of parks.
     */
    @FXML
    private ListView<String> lstViewRoute;
    @FXML
    private Button btnBack;

    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);

    }

    public void initialize(Park startingPark, ElectricBicycle eleBike, Park finishingPark, SessionController currentSession) throws IOException {
        ShortestPathEfficientElectricalController speec = new ShortestPathEfficientElectricalController();
        List<Route> route = speec.shortestElectricalPath(currentSession.getLoggedUser(), eleBike, startingPark, finishingPark);
        for(Route r : route){
            ObservableList<Location> obsLocations = FXCollections.observableArrayList();
            List<String> auxList = new ArrayList<>();
            for(Location l : r.getPath()){
                auxList.add(l.getName());
            }
            this.lstViewRoute.getItems().addAll(auxList);
            this.lstViewRoute.getItems().addAll("========================");

        }

    }
}

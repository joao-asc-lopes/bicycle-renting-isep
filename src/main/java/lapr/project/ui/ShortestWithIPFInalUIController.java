package lapr.project.ui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import lapr.project.controller.user.ShortestPathInterestPointsController;
import lapr.project.model.bikenetwork.Route;
import lapr.project.model.park.InterestPoint;
import lapr.project.model.park.Location;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.util.ArrayList;
import java.util.List;


public class ShortestWithIPFInalUIController {
    /**
     * Variable that represnts a listView Of Locations.
     */
    @FXML
    private ListView<String> lstViewLocationsRoute;

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

    public void initialize(Park startingPark, Park finishingPark, List<Location> chosenInterestPoints) throws InvalidDataException {
        ShortestPathInterestPointsController spipc = new ShortestPathInterestPointsController();
        List<Route> interestPointList = spipc.getShortestPathIterable(startingPark, finishingPark, chosenInterestPoints);

        for(Route r : interestPointList){
            ObservableList<Location> obsLocations = FXCollections.observableArrayList();
            List<String> auxList = new ArrayList<>();
            for(Location l : r.getPath()){
                auxList.add(l.getName());
            }
            this.lstViewLocationsRoute.getItems().addAll(auxList);
            this.lstViewLocationsRoute.getItems().add("=======================");

        }


    }


}

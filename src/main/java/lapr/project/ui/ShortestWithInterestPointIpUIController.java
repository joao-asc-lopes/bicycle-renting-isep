package lapr.project.ui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import lapr.project.controller.user.ShortestPathInterestPointsController;
import lapr.project.model.park.InterestPoint;
import lapr.project.model.park.Location;
import lapr.project.utils.InvalidDataException;

import java.util.List;

public class ShortestWithInterestPointIpUIController {
    /**
     * Variable representative of the listView of InterestPoints.
     */
    @FXML
    private ListView<InterestPoint> lstViewInterestPoint;
    /**
     * Variable that represents the button that allows the user to choose an InterestPoint.
     */

    @FXML
    private Button btnInterestPoint;

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

    private ShortestWithInterestPointGeneralInfoUIController spgic;


    /**
     * Method that initializes the controller.
     */
    public void initialize(ShortestWithInterestPointGeneralInfoUIController spc, List<Location> alreadyChosenInterestPoints) {
        this.spgic = spc;
        ShortestPathInterestPointsController spipc = new ShortestPathInterestPointsController();
        List<InterestPoint> interestPointList = spipc.getInterestPointList();
        interestPointList.removeAll(alreadyChosenInterestPoints);
        ObservableList<InterestPoint> obsInterestPoints = FXCollections.observableArrayList();
        obsInterestPoints.addAll(interestPointList);
        this.lstViewInterestPoint.setItems(obsInterestPoints);


    }

    /**
     * Method that allows the user to go back to the genereal info scene.
     *
     * @param event
     */

    @FXML
    void handleBtnInterestPoint(ActionEvent event) {
        InterestPoint ip = this.lstViewInterestPoint.getSelectionModel().getSelectedItem();
        try {
            if (ip == null) {
                throw new InvalidDataException("Please select a Interest Point!");
            }
            this.spgic.updateListView(ip);
            Window window = this.btnInterestPoint.getScene().getWindow();
            window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));

        } catch (InvalidDataException e) {
            UIControllerUtils.showAlert(Alert.AlertType.ERROR, "ERROR", "Error", e.getMessage());
        }


    }
}

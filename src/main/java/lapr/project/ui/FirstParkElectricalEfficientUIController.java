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
import lapr.project.controller.user.SessionController;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.io.IOException;
import java.util.List;


public class FirstParkElectricalEfficientUIController {

    /**
     * Variable that represents a listView of Parks.
     */
    @FXML
    private ListView<Park> lstViewPark;

    /**
     * Variable that represents a button that allows the user to go to the previous scene.
     */
    @FXML
    private Button btnBack;

    /**
     * Variable that represents a button.
     */
    @FXML
    private Button btnChooseFinishingPark;

    /**
     * Variable that represents the current session.
     */
    private SessionController currenteSession;

    @FXML
    void handleBtnBack(ActionEvent event) {
    UIControllerUtils.closeWindow(this.btnBack);
    }

    public void initialize(SessionController currenteSession) {
        this.currenteSession = currenteSession;
        AvailableBicyclesController aic = new AvailableBicyclesController();
        List<Park> parkList = aic.getParkRegistry();
        ObservableList<Park> obsParks = FXCollections.observableArrayList();
        obsParks.addAll(parkList);
        this.lstViewPark.setItems(obsParks);

    }

    /**
     * Method that allows the user to choose the finishing park.
     * @param event The click on the button btnChooseFinishingPark.
     */
    @FXML
    void handleBtnChooseFinishingPark(ActionEvent event) throws IOException {
    Park park = this.lstViewPark.getSelectionModel().getSelectedItem();

    try{
        if(park == null){
            throw new InvalidDataException("Please select a park!");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShortestPathElectricalEfficiencyBicycleScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ShortestRouteElectricalBikekUIController dmuii = loader.getController();
        dmuii.initialize(park, this.currenteSession);
        Stage finishingParkElectricalEfficientStage = new Stage();
        finishingParkElectricalEfficientStage.initModality(Modality.APPLICATION_MODAL);
        finishingParkElectricalEfficientStage.setTitle("Shortest Route Park to Park w/ Interest Points!");
        finishingParkElectricalEfficientStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        finishingParkElectricalEfficientStage.setMinHeight(finishingParkElectricalEfficientStage.getHeight());
        finishingParkElectricalEfficientStage.setMinWidth(finishingParkElectricalEfficientStage.getWidth());
        finishingParkElectricalEfficientStage.setResizable(false);
        finishingParkElectricalEfficientStage.show();
    } catch (InvalidDataException e){
        UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR!","Error",e.getMessage());
    }


    }
}

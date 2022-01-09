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
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.io.IOException;
import java.util.List;

public class ShortestRouteElectricalBikekUIController {

    /**
     * Variable that represents a button.
     * 
     */
    @FXML
    private Button btnChooseBike;
    /**
     * Button that allows the user to go back.
     */
    @FXML
    private Button btnBack;
    /**
     * Variable that represents the list view of electric bicycles.
     */
    @FXML
    private ListView<ElectricBicycle> lstViewBikes;
    /**
     * Variable that represents the current session.
     */
    private SessionController currentSession;

    /**
     * Starting park.
     */
    private Park startingPark;

    /**
     * Method that allows the user to go to the previous scene.
     * @param event
     */
    @FXML
    void handleBtnBack(ActionEvent event) {
    UIControllerUtils.closeWindow(this.btnBack);

    }

    public void initialize(Park startingPark, SessionController currentSession) {
        this.currentSession = currentSession;
        this.startingPark = startingPark;
        AvailableBicyclesController abc = new AvailableBicyclesController();
        List<ElectricBicycle> parkList = abc.getElectricBicycleInPark(startingPark.getIdLocation());
        if(parkList.isEmpty()){
            throw new InvalidDataException("There are no electrical bicycles in this park!");
        }
        ObservableList<ElectricBicycle> obsElectricBicycles = FXCollections.observableArrayList();
        obsElectricBicycles.addAll(parkList);
        this.lstViewBikes.setItems(obsElectricBicycles);


    }

    @FXML
    void handleBtnChooseBike(ActionEvent event) throws IOException {
        ElectricBicycle eleBike = this.lstViewBikes.getSelectionModel().getSelectedItem();
        try {
            if (eleBike == null){
                throw new InvalidDataException("Please select an electrical bicycle!");
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FinishingParkElectricalEfficientScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            FinishingParkElectricalEfficientUIController dmuii = loader.getController();
            dmuii.initialize(this.startingPark,eleBike, this.currentSession);
            Stage finishingParkElectricalEfficientStage = new Stage();
            finishingParkElectricalEfficientStage.initModality(Modality.APPLICATION_MODAL);
            finishingParkElectricalEfficientStage.setTitle("Shortest Route Park Electrical Efficiency!");
            finishingParkElectricalEfficientStage.setScene(scene);
            //sets the size of the window to the one defined in JavaFX.
            finishingParkElectricalEfficientStage.setMinHeight(finishingParkElectricalEfficientStage.getHeight());
            finishingParkElectricalEfficientStage.setMinWidth(finishingParkElectricalEfficientStage.getWidth());
            finishingParkElectricalEfficientStage.setResizable(false);
            finishingParkElectricalEfficientStage.show();
        } catch ( InvalidDataException e){
            UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR","Error",e.getMessage());
        }

    }
}

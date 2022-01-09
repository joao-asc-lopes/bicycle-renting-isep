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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lapr.project.controller.user.AvailableBicyclesController;
import lapr.project.controller.user.SessionController;
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.bicycle.MountainBicycle;
import lapr.project.model.bicycle.RoadBicycle;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RentBicycleUIController {
    /**
     * Variable that represents a combo box with all the parks.
     */
    @FXML
    private ComboBox<Park> comboPark;
    /**
     * Variable that represents the current session.
     */

    private SessionController currentSession;
    /**
     * Variablçe that represents a combo box with all the bike types.
     */
    @FXML
    private ComboBox<Bicycle.BicycleType> comboBikeType;

    /**
     * Variable that represents the available bicycles.
     */
    private AvailableBicyclesController abc;

    /**
     * Variable that represents a listview with bicycles.
     */
    @FXML
    private ListView<Bicycle> listBikes;
    /**
     * Variable that represents a button.
     */

    @FXML
    private Button btnList;
    /**
     * Variable that represents a button.
     */

    @FXML
    private Button btnRentBicycle;

    @FXML
    private Button btnBack;

    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);
    }

    /**
     * Initialize that represents the parkRegistry.
     */

    public void initialize(SessionController currentSession) {
        this.currentSession = currentSession;
        abc = new AvailableBicyclesController();
        comboPark.getItems().addAll(this.abc.getParkRegistry());
        this.comboBikeType.getItems().clear();
        this.comboBikeType.getItems().addAll(Arrays.asList(Bicycle.BicycleType.values()));

    }


    @FXML
    void handleBtnBicycle(ActionEvent event) throws IOException {
        Bicycle bike = this.listBikes.getSelectionModel().getSelectedItem();
        try {
            if (bike == null) {
                throw new InvalidDataException("You need to select a bike!");
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuShortestDistances.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                MenuShortestDistancesUIController msduic = loader.getController();
                msduic.initialize(bike, this.currentSession);
                Stage menuDistanceShortestStage = new Stage();
                menuDistanceShortestStage.initModality(Modality.APPLICATION_MODAL);
                menuDistanceShortestStage.setTitle("Menu!");
                menuDistanceShortestStage.setScene(scene);
                //sets the size of the window to the one defined in JavaFX.
                menuDistanceShortestStage.setMinHeight(menuDistanceShortestStage.getHeight());
                menuDistanceShortestStage.setMinWidth(menuDistanceShortestStage.getWidth());
                menuDistanceShortestStage.setResizable(false);
                menuDistanceShortestStage.show();
            }

        } catch (InvalidDataException e) {
            UIControllerUtils.showAlert(Alert.AlertType.ERROR, "ERROR", " Select a bike!! ", e.getMessage());
        }
    }

    /**
     * Shows all the bicycles of a park. If none is chosen opens up an error message.
     *
     * @param event
     */

    @FXML
    void handleBtnList(ActionEvent event) {
        Park park = this.comboPark.getSelectionModel().getSelectedItem();
        Bicycle.BicycleType bikeType = this.comboBikeType.getSelectionModel().getSelectedItem();
        try {
            if (park == null) {
                throw new InvalidDataException("You need to select a park!");
            }
            if (bikeType == null) {
                throw new InvalidDataException("You need to select a bicycle type!");
            }
            if("Mountain".equals(bikeType.toString())){
                List<MountainBicycle> mountainBicycleList = this.abc.getMountainBicyclesInPark(park.getIdLocation());
                ObservableList<Bicycle> obsMountainBike = FXCollections.observableArrayList();
                obsMountainBike.addAll(mountainBicycleList);
                this.listBikes.setItems(obsMountainBike);
            } else if("Road".equals(bikeType.toString())){
                List<RoadBicycle> roadBicycleList = this.abc.getRoadBicyclesInPark(park.getIdLocation());
                ObservableList<Bicycle> obsRoadBike = FXCollections.observableArrayList();
                obsRoadBike.addAll(roadBicycleList);
                this.listBikes.setItems(obsRoadBike);
            } else {
                List<ElectricBicycle> elecBike = this.abc.getElectricBicycleInPark(park.getIdLocation());
                ObservableList<Bicycle>  obsElecBike = FXCollections.observableArrayList();
                obsElecBike.addAll(elecBike);
                this.listBikes.setItems(obsElecBike);
            }

        } catch (InvalidDataException e) {
            UIControllerUtils.showAlert(Alert.AlertType.ERROR, "ERROR", " ERROR!!!!! ", e.getMessage());
        }


    }


}


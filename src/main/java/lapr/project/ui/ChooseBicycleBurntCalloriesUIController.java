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
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.io.IOException;
import java.util.List;


public class ChooseBicycleBurntCalloriesUIController {

    /**
     * Variable that represents a list view of bicycles.
     */
    @FXML
    private ListView<Bicycle> lstViewBicycles;
    /**
     * Variable that represents a button.
     */

    @FXML
    private Button btnBicycle;

    /**
     * Variable that represents the current session.
     */
    private SessionController currentSession;
    /**
     * Variable that represents the starting park.
     */
    private Park startingPark;
    /**
     * Variable that represents the finishing park.
     */

    private Park finishingPark;

    @FXML
    private Button btnBack;

    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);

    }

    /**
     * Initialize that represents the parkRegistry.
     */
    public void initialize(SessionController currentSession, Park startingPark, Park finishingPark) {
        this.finishingPark = finishingPark;
        this.startingPark = startingPark;
        this.currentSession = currentSession;
        AvailableBicyclesController abc = new AvailableBicyclesController();
        List<Bicycle> listBikes = abc.getBicyclesInPark(this.startingPark.getIdLocation());
        if(listBikes.isEmpty()){
            throw new InvalidDataException("There are no Bikes in this park!");
        }
        ObservableList<Bicycle> obsBikes = FXCollections.observableArrayList();
        obsBikes.addAll(listBikes);
        this.lstViewBicycles.setItems(obsBikes);


    }

    /**
     * Method that  allows the user to choose a bicycle and proceed to the next scene.
     * @param event Click in btnBike.
     */

    @FXML
    void handleBtnBicycle(ActionEvent event) throws IOException {
        Bicycle bike = this.lstViewBicycles.getSelectionModel().getSelectedItem();
        try{
            if (bike == null){
                throw new InvalidDataException("Please select a bike!");
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BurntCaloriesFinalScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            BurntCaloriesFinalUIController dmuii = loader.getController();
            dmuii.initialize(currentSession,this.startingPark,this.finishingPark,bike);
            Stage lastParkBurntCaloriesStage = new Stage();
            lastParkBurntCaloriesStage.initModality(Modality.APPLICATION_MODAL);
            lastParkBurntCaloriesStage.setTitle("Burnt Calories!");
            lastParkBurntCaloriesStage.setScene(scene);
            //sets the size of the window to the one defined in JavaFX.
            lastParkBurntCaloriesStage.setMinHeight(lastParkBurntCaloriesStage.getHeight());
            lastParkBurntCaloriesStage.setMinWidth(lastParkBurntCaloriesStage.getWidth());
            lastParkBurntCaloriesStage.setResizable(false);
            lastParkBurntCaloriesStage.show();


        } catch (InvalidDataException e){
            UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR","Error!",e.getMessage());
        }

    }
}

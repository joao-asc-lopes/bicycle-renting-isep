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
import lapr.project.controller.user.SessionController;
import lapr.project.controller.user.ShortestPathParkToParkController;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.io.IOException;
import java.util.List;

public class LastParkBurntCalloriesUIController {
    /**
     * Variable that represents a list view of parks.
     */
    @FXML
    private ListView<Park> lstViewPark;
    /**
     * A variable that represents a button.
     */

    @FXML
    private Button btnChoosePark;

    /**
     * Variable that represents the current session.
     */
    private SessionController currentSession;
    /**
     * Variable that represents the starting park.
     */
    private Park startingPark;
    @FXML
    private Button btnBack;

    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);

    }


    /**
     * Initialize that represents the parkRegistry.
     */

    public void initialize(SessionController currentSession, Park startingPark) {
        this.startingPark = startingPark;
        this.currentSession = currentSession;
        ShortestPathParkToParkController spc = new ShortestPathParkToParkController();
        List<Park> parkList = spc.getParkList();
        parkList.remove(this.startingPark);
        ObservableList<Park> obsParks = FXCollections.observableArrayList();
        obsParks.addAll(parkList);
        this.lstViewPark.setItems(obsParks);


    }

    /**
     * Method that allows the user to choose a park and proceed to the next scene.
     * @param event The click in btnPark.
     */

    @FXML
    void handleBtnChoosePark(ActionEvent event) throws IOException {
        Park park = this.lstViewPark.getSelectionModel().getSelectedItem();
        try{
            if(park == null){
                throw new InvalidDataException("Please select a park!");
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BurntCaloriesChooseBike.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ChooseBicycleBurntCalloriesUIController dmuii = loader.getController();
            dmuii.initialize(currentSession,this.startingPark,park);
            Stage chooseBikeBurntCaloriesStage = new Stage();
            chooseBikeBurntCaloriesStage.initModality(Modality.APPLICATION_MODAL);
            chooseBikeBurntCaloriesStage.setTitle("Burnt Calories!");
            chooseBikeBurntCaloriesStage.setScene(scene);
            //sets the size of the window to the one defined in JavaFX.
            chooseBikeBurntCaloriesStage.setMinHeight(chooseBikeBurntCaloriesStage.getHeight());
            chooseBikeBurntCaloriesStage.setMinWidth(chooseBikeBurntCaloriesStage.getWidth());
            chooseBikeBurntCaloriesStage.setResizable(false);
            chooseBikeBurntCaloriesStage.show();
        } catch ( InvalidDataException e){
            UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR","Error!",e.getMessage());
        }

    }
}

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
import lapr.project.controller.user.ShortestPathInterestPointsController;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.io.IOException;
import java.util.List;

public class ShortestWithInterestPointStartingParkUIController {
    /**
     * Variable that represents the listView.
     */
    @FXML
    private ListView<Park> lstViewParks;
    /**
     * Variable that represents the button of choose park.
     */

    @FXML
    private Button btnChoosePark;
    /**
     * Variable that represents the shortesPathInterestPointsController.
     */
    private ShortestPathInterestPointsController spic;
    /**
     * Variable that represents a button that allows the user to go back to the previous window.
     */
    @FXML
    private Button btnBack;

    /**
     * Method that is responsible to go to the previous scene.
     * @param event
     */

    @FXML
    void handleBtnBack(ActionEvent event) {
    UIControllerUtils.closeWindow(this.btnBack);
    }


    /**
     * Method that initializes the controller.
     */
    public void initialize() {
        spic = new ShortestPathInterestPointsController();
        List<Park> parkList = this.spic.getParkList();
        ObservableList<Park> obsParks = FXCollections.observableArrayList();
        obsParks.addAll(parkList);
        this.lstViewParks.setItems(obsParks);


    }


    /**
     * Method that allows the user to choose the park and to proceed to choose the next one.
     * @param event The event of clicking in btnChoosePark.
     */

    @FXML
    void handleBtnChoosePark(ActionEvent event) throws IOException {
        Park park = this.lstViewParks.getSelectionModel().getSelectedItem();

        try{
            if(park == null){
                throw new InvalidDataException("Please select a park!");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShortestWithInterestPointFinishingParkScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ShortestWithInterestPointFinishingParkUIController dmuii = loader.getController();
            dmuii.initialize(park,this.spic);
            Stage shortestWithInterestingPointsFinishingParkStage = new Stage();
            shortestWithInterestingPointsFinishingParkStage.initModality(Modality.APPLICATION_MODAL);
            shortestWithInterestingPointsFinishingParkStage.setTitle("Shortest Route Park to Park w/ Interest Points!");
            shortestWithInterestingPointsFinishingParkStage.setScene(scene);
            //sets the size of the window to the one defined in JavaFX.
            shortestWithInterestingPointsFinishingParkStage.setMinHeight(shortestWithInterestingPointsFinishingParkStage.getHeight());
            shortestWithInterestingPointsFinishingParkStage.setMinWidth(shortestWithInterestingPointsFinishingParkStage.getWidth());
            shortestWithInterestingPointsFinishingParkStage.setResizable(false);
            shortestWithInterestingPointsFinishingParkStage.show();

        }catch(InvalidDataException e){
            UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR","Error!",e.getMessage());
        }


    }
}

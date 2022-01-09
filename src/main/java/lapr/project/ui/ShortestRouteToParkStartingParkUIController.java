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
import lapr.project.controller.user.ShortestPathParkToParkController;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.io.IOException;
import java.util.List;

public class ShortestRouteToParkStartingParkUIController {

    /**
     * Variable representative of a list view of parks.
     */
    @FXML
    private ListView<Park> lstViewParks;
    /**
     * Variable representative of a button.
     */

    @FXML
    private Button bntFinishingPark;
    @FXML
    private Button btnBack;

    @FXML
    void handleBtnBack(ActionEvent event) {
    UIControllerUtils.closeWindow(this.btnBack);
    }


    /**
     * Initialize that represents the parkRegistry.
     */

    public void initialize() {
        ShortestPathParkToParkController spc = new ShortestPathParkToParkController();
        List<Park> parkList = spc.getParkList();
        ObservableList<Park> obsParks = FXCollections.observableArrayList();
        obsParks.addAll(parkList);
       this.lstViewParks.setItems(obsParks);


    }



    /**
     * Method that allows the user to choose the finishing park.
     * @param event The click on the button btnFinishingPark
     */

    @FXML
    void handleBtnFinishingPark(ActionEvent event) throws IOException {
        Park park = this.lstViewParks.getSelectionModel().getSelectedItem();
        try{
            if(park == null){
                throw new InvalidDataException("Please select a park!");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShortestRouteToParkFinishingParkScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ShortestRouteToParkFinishingParkUIController dmuii = loader.getController();
            dmuii.initialize(park);
            Stage shortestRouteParkToParkLastParkStage = new Stage();
            shortestRouteParkToParkLastParkStage.initModality(Modality.APPLICATION_MODAL);
            shortestRouteParkToParkLastParkStage.setTitle("Shortest Route Park to Park!");
            shortestRouteParkToParkLastParkStage.setScene(scene);
            //sets the size of the window to the one defined in JavaFX.
            shortestRouteParkToParkLastParkStage.setMinHeight(shortestRouteParkToParkLastParkStage.getHeight());
            shortestRouteParkToParkLastParkStage.setMinWidth(shortestRouteParkToParkLastParkStage.getWidth());
            shortestRouteParkToParkLastParkStage.setResizable(false);
            shortestRouteParkToParkLastParkStage.show();

        } catch(InvalidDataException e){
            UIControllerUtils.showAlert(Alert.AlertType.ERROR,"ERROR","Error!",e.getMessage());
        }

    }
}

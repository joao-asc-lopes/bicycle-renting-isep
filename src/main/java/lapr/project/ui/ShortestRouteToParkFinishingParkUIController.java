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


public class ShortestRouteToParkFinishingParkUIController {

    /**
     * Variable thar represents a list view of parks.
     */
    @FXML
    private ListView<Park> listViewParks;
    /**
     * Variable that represents a button
     */

    @FXML
    private Button btnSeeRoute;

    @FXML
    private Button btnBack;

    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);
    }

    /**
     * Variable that represents the starting Park.
     */
    private Park startingPark;

    /**
     * Initialize that represents the parkRegistry.
     */

    public void initialize(Park startingPark) {
        this.startingPark = startingPark;
        ShortestPathParkToParkController spc = new ShortestPathParkToParkController();
        List<Park> parkList = spc.getParkList();
        parkList.remove(startingPark);
        ObservableList<Park> obsParks = FXCollections.observableArrayList();
        obsParks.addAll(parkList);
        this.listViewParks.setItems(obsParks);



    }


    /**
     * Method that allows the user to see the shortest Route.
     * @param event The click on button btnSeeRoute.
     */

    @FXML
    void handleBtnSeeRoute(ActionEvent event) throws IOException {
        Park park = this.listViewParks.getSelectionModel().getSelectedItem();

        try{
            if(park == null){
                throw new InvalidDataException("Please select a park!");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShortestRouteToParkFinalScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ShortestRouteToParkFinalUIController dmuii = loader.getController();
            dmuii.initialize(this.startingPark, park);
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

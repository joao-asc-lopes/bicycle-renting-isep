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

public class ShortestWithInterestPointFinishingParkUIController {

    /**
     * Variable that is representative of a listview of parks.
     */
    @FXML
    private ListView<Park> lstViewOParks;
    /**
     * Variable that represents a button of chooseing parks.
     */
    @FXML
    private Button btnChoosePark;
    /**
     * Variable that represents the starting park.
     */
    private Park startingPark;
    /**
     * Variable that represents a shortest Park controller.
     */
    private ShortestPathInterestPointsController spic;

    /**
     * Variable that is responsible to go to the previous scene.
     */
    @FXML
    private Button btnBack;

    /**
     * Method that allows the user to go to the previous scene.
     * @param event
     */
    @FXML
    void handleBtnBack(ActionEvent event) {
    UIControllerUtils.closeWindow(this.btnBack);
    }


    /**
     * Method that initializes the controller.
     */
    public void initialize(Park park, ShortestPathInterestPointsController spc) {
        this.startingPark = park;
        spic =spc;
        List<Park> parkList = this.spic.getParkList();
        parkList.remove(startingPark);
        ObservableList<Park> obsParks = FXCollections.observableArrayList();
        obsParks.addAll(parkList);
        this.lstViewOParks.setItems(obsParks);

    }


    /**
     * Method that allows the user to choose a park and proceed to next stage of the user story.
     * @param event
     */

    @FXML
    void handleBtnChoosePark(ActionEvent event) throws IOException {
       Park park = this.lstViewOParks.getSelectionModel().getSelectedItem();
       try{
           if(park == null){
               throw new InvalidDataException("Please select a park!");
           }

           FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShortestInterestPointGeneralInfo.fxml"));
           Parent root = loader.load();
           Scene scene = new Scene(root);
           ShortestWithInterestPointGeneralInfoUIController dmuii = loader.getController();
           dmuii.initialize(this.startingPark,park);
           Stage shortestRouteParkToParkGeneralInfoStage = new Stage();
           shortestRouteParkToParkGeneralInfoStage.initModality(Modality.APPLICATION_MODAL);
           shortestRouteParkToParkGeneralInfoStage.setTitle("Shortest Route Interest Points General Info!");
           shortestRouteParkToParkGeneralInfoStage.setScene(scene);
           //sets the size of the window to the one defined in JavaFX.
           shortestRouteParkToParkGeneralInfoStage.setMinHeight(shortestRouteParkToParkGeneralInfoStage.getHeight());
           shortestRouteParkToParkGeneralInfoStage.setMinWidth(shortestRouteParkToParkGeneralInfoStage.getWidth());
           shortestRouteParkToParkGeneralInfoStage.setResizable(false);
           shortestRouteParkToParkGeneralInfoStage.show();

       } catch(InvalidDataException e){
           UIControllerUtils.showAlert(Alert.AlertType.ERROR, "ERROR","Error",e.getMessage());
       }

    }
}

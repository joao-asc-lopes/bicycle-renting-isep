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

public class FinishingParkElectricalEfficientUIController {
    /**
     * Variable that represents a list view of parks.
     */
    @FXML
    private ListView<Park> lstViewPark;
    /**
     * Variable that represents a button.
     */
    @FXML
    private Button btnSeeRoute;
    /**
     * Starting park.
     */
    private Park startingPark;

    private ElectricBicycle electricBicycle ;

    private SessionController currentSession;

    @FXML
    private Button btnBack;

    @FXML
    void handleBtnBack(ActionEvent event) {
    UIControllerUtils.closeWindow(this.btnBack);
    }

    public void initialize(Park startingPark, ElectricBicycle eleBike, SessionController currentSession) {
        this.currentSession = currentSession;
        this.startingPark = startingPark;
        this.electricBicycle = eleBike;
        AvailableBicyclesController aic = new AvailableBicyclesController();
        List<Park> parkList = aic.getParkRegistry();
        parkList.remove(startingPark);
        ObservableList<Park> obsParks = FXCollections.observableArrayList();
        obsParks.addAll(parkList);
        this.lstViewPark.setItems(obsParks);


    }

    /**
     * Method that allows the user to see the most electrical efficient route between 2 chosen parks.
     * @param event The click on btnSeeRoute.
     */

    @FXML
    void handleBtnSeeRoute(ActionEvent event) throws IOException {

      Park park = this.lstViewPark.getSelectionModel().getSelectedItem();
      try{
          if(park == null){
              throw new InvalidDataException("Please select a park!");
          }

          FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShortestElectricalPathScene.fxml"));
          Parent root = loader.load();
          Scene scene = new Scene(root);
          ShortestElectricalPathUIController dmuii = loader.getController();
          dmuii.initialize(this.startingPark,this.electricBicycle,park, this.currentSession);
          Stage shortestElectricalPathStage = new Stage();
          shortestElectricalPathStage.initModality(Modality.APPLICATION_MODAL);
          shortestElectricalPathStage.setTitle("Shortest Route Park Electrical Efficiency!");
          shortestElectricalPathStage.setScene(scene);
          //sets the size of the window to the one defined in JavaFX.
          shortestElectricalPathStage.setMinHeight(shortestElectricalPathStage.getHeight());
          shortestElectricalPathStage.setMinWidth(shortestElectricalPathStage.getWidth());
          shortestElectricalPathStage.setResizable(false);
          shortestElectricalPathStage.show();
      } catch(InvalidDataException e){
          UIControllerUtils.showAlert(Alert.AlertType.ERROR,"Error!","Error",e.getMessage());
      }

    }
}

package lapr.project.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lapr.project.model.park.InterestPoint;
import lapr.project.model.park.Location;
import lapr.project.model.park.Park;

import java.io.IOException;
import java.util.List;

public class ShortestWithInterestPointGeneralInfoUIController {

    /**
     * Variable that represents the starting Park.
     */
    private Park startingPark;
    /**
     * Variable that represents the finishing park.
     */

    private Park finishingPark;

    /**
     * Variable that represents a non-editable textField that has the name of the starting park.
     */
    @FXML
    private TextField txtStartingPark;
    /**
     * Variable that represents a non-editable textField that has the name of the finishing park.
     */
    @FXML
    private TextField txtFinishingPark;
    /**
     * Variable that represents the button that allows the user to see the shortest route.
     */
    @FXML
    private Button btnSeeShortestRoute;
    /**
     * Variable that represents a listView of Parks.
     */

    @FXML
    private ListView<Location> lstViewInterestPoint;
    /**
     * Variable that represents a button that allows the user to add interest points.
     */

    @FXML
    private Button btnAddInterestPoint;

    /**
     * Variable that is responsible to go to the previous scene.
     */
    @FXML
    private Button btnBack;

    /**
     * Method that allows the user to go to the previous scene.
     *
     * @param event
     */
    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);
    }


    /**
     * Method that initializes the controller.
     */
    public void initialize(Park park, Park otherPark) {
        this.startingPark = park;
        this.finishingPark = otherPark;
        this.txtStartingPark.setText(this.startingPark.getName());
        this.txtFinishingPark.setText(this.finishingPark.getName());


    }

    /**
     * Method that allows the user to add  an interest Point that he wishes to go.
     *
     * @param event
     */
    @FXML
    void handleBtnAddInterestPoint(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShortestWithInterestPointIpScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ShortestWithInterestPointIpUIController dmuii = loader.getController();
        List<Location> alreadyChosenInterestPoints = this.lstViewInterestPoint.getItems();
        dmuii.initialize(this, alreadyChosenInterestPoints);
        Stage shortestRouteParkToParkGeneralInfoStage = new Stage();
        shortestRouteParkToParkGeneralInfoStage.initModality(Modality.APPLICATION_MODAL);
        shortestRouteParkToParkGeneralInfoStage.setTitle("Shortest Route Interest Points General Info!");
        shortestRouteParkToParkGeneralInfoStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        shortestRouteParkToParkGeneralInfoStage.setMinHeight(shortestRouteParkToParkGeneralInfoStage.getHeight());
        shortestRouteParkToParkGeneralInfoStage.setMinWidth(shortestRouteParkToParkGeneralInfoStage.getWidth());
        shortestRouteParkToParkGeneralInfoStage.setResizable(false);
        shortestRouteParkToParkGeneralInfoStage.show();

    }

    /**
     * Method that allows the user to see the shortest route.
     *
     * @param event
     */

    @FXML
    void handleBtnSeeShortestRoute(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShortestParkWithIPFinalScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ShortestWithIPFInalUIController dmuii = loader.getController();
        List<Location> chosenInterestPoints = this.lstViewInterestPoint.getItems();
        dmuii.initialize(this.startingPark, this.finishingPark, chosenInterestPoints);
        Stage shortestRouteParkFinalWithIPStage = new Stage();
        shortestRouteParkFinalWithIPStage.initModality(Modality.APPLICATION_MODAL);
        shortestRouteParkFinalWithIPStage.setTitle("Shortest Route Interest Points Final!");
        shortestRouteParkFinalWithIPStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        shortestRouteParkFinalWithIPStage.setMinHeight(shortestRouteParkFinalWithIPStage.getHeight());
        shortestRouteParkFinalWithIPStage.setMinWidth(shortestRouteParkFinalWithIPStage.getWidth());
        shortestRouteParkFinalWithIPStage.setResizable(false);
        shortestRouteParkFinalWithIPStage.show();
    }

    @FXML
    void handleTxtFinishingPark(ActionEvent event) {

    }

    @FXML
    void handleTxtStartingPark(ActionEvent event) {

    }

    protected void updateListView(InterestPoint ip) {
        this.lstViewInterestPoint.getItems().add(ip);
    }

}

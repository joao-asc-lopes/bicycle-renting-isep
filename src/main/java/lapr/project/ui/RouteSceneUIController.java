package lapr.project.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lapr.project.controller.user.SessionController;

import java.io.IOException;


public class RouteSceneUIController {

    @FXML
    private Button btnBack;
    /**
     * Button that allows the user to go to the Interest Points Route user story.
     */
    @FXML
    private Button btnInterestPoints;
    /**
     * Variable that represents the button that allows the user to see the park to park user story.
     */

    @FXML
    private Button btnParkToParkRoute;
    /**
     * Variable that represents the button that allows the user to see the most electrical efficient route between 2 parks.
     */

    @FXML
    private Button btnElectricalRoute;

    /**
     * Variable that represents the current session.
     */

    private SessionController currentSession;
    /**
     * Initialize that represents the parkRegistry.
     */

    public void initialize( SessionController currentSession){
        this.currentSession = currentSession;


    }


    /**
     * Method that allows the user to enter the most electrical efficient route user story.
     * @param event The event of clicking in the btnElectricalRoute.
     */
    @FXML
    void handleBtnElectricalRoute(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FirstParkEletricalEfficientScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        FirstParkElectricalEfficientUIController dmuii = loader.getController();
        dmuii.initialize(this.currentSession);
        Stage shortestWithInterestingPointsStartingParkStage = new Stage();
        shortestWithInterestingPointsStartingParkStage.initModality(Modality.APPLICATION_MODAL);
        shortestWithInterestingPointsStartingParkStage.setTitle("Shortest Route Park to Park w/ Interest Points!");
        shortestWithInterestingPointsStartingParkStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        shortestWithInterestingPointsStartingParkStage.setMinHeight(shortestWithInterestingPointsStartingParkStage.getHeight());
        shortestWithInterestingPointsStartingParkStage.setMinWidth(shortestWithInterestingPointsStartingParkStage.getWidth());
        shortestWithInterestingPointsStartingParkStage.setResizable(false);
        shortestWithInterestingPointsStartingParkStage.show();

    }

    /**
     * Method that allows the user to enter the interest point rout user story.
     * @param event The event of clicking in the btnInterestRoute.
     */

    @FXML
    void handleBtnInterestPoints(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShortestWithInterestPointStartingPark.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ShortestWithInterestPointStartingParkUIController dmuii = loader.getController();
        dmuii.initialize();
        Stage shortestWithInterestingPointsStartingParkStage = new Stage();
        shortestWithInterestingPointsStartingParkStage.initModality(Modality.APPLICATION_MODAL);
        shortestWithInterestingPointsStartingParkStage.setTitle("Shortest Route Park to Park w/ Interest Points!");
        shortestWithInterestingPointsStartingParkStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        shortestWithInterestingPointsStartingParkStage.setMinHeight(shortestWithInterestingPointsStartingParkStage.getHeight());
        shortestWithInterestingPointsStartingParkStage.setMinWidth(shortestWithInterestingPointsStartingParkStage.getWidth());
        shortestWithInterestingPointsStartingParkStage.setResizable(false);
        shortestWithInterestingPointsStartingParkStage.show();

    }

    /**
     * Method that allows the user to enter the park to park route user story.
     * @param event The event of clicking in the btnParkToParkRoute.
     */

    @FXML
    void handleBtnParkToParkRoute(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShortestRouteToParkStartingParkScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ShortestRouteToParkStartingParkUIController dmuii = loader.getController();
        dmuii.initialize();
        Stage shortestRouteParkToParkFirstParkStage = new Stage();
        shortestRouteParkToParkFirstParkStage.initModality(Modality.APPLICATION_MODAL);
        shortestRouteParkToParkFirstParkStage.setTitle("Shortest Route Park to Park!");
        shortestRouteParkToParkFirstParkStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        shortestRouteParkToParkFirstParkStage.setMinHeight(shortestRouteParkToParkFirstParkStage.getHeight());
        shortestRouteParkToParkFirstParkStage.setMinWidth(shortestRouteParkToParkFirstParkStage.getWidth());
        shortestRouteParkToParkFirstParkStage.setResizable(false);
        shortestRouteParkToParkFirstParkStage.show();

    }

    /**
     * Allows the user to go back to the previous window.
     * @param event The click in button back.
     */
    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);

    }


}




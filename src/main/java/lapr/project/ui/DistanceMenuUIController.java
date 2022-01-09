package lapr.project.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DistanceMenuUIController {

    @FXML
    private Button btnBack;
    /**
     * Variable that represents the button with the 5 nearest parks user story.
     */
    @FXML
    private Button btnNearestFiveParks;
    /**
     * Variable that represents the button that allows access to the user-park distance user story.
     */

    @FXML
    private Button btnUserParkDistance;
    /**
     * Variable that represents the button that allows access to the park-park distance user story.
     */

    @FXML
    private Button btnParkParkDistance;

    private static final String TITLE = "Distance User Park!";
    

    /**
     * Method that allows the user to enter the park-park distance user story.
     * @param event The click in the button btnParkParkDistance.
     */
    @FXML
    void handleBtnPrkParkDistance(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DistanceStartingParktoParkScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        DistanceStartingParkToParkUIController dmuii = loader.getController();
        dmuii.initialize();
        Stage distanceParkParkStage = new Stage();
        distanceParkParkStage.initModality(Modality.APPLICATION_MODAL);
        distanceParkParkStage.setTitle(TITLE);
        distanceParkParkStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        distanceParkParkStage.setMinHeight(distanceParkParkStage.getHeight());
        distanceParkParkStage.setMinWidth(distanceParkParkStage.getWidth());
        distanceParkParkStage.setResizable(false);
        distanceParkParkStage.show();
    }

    /**
     * Method that allows the user to enter the scene relative to the user-park distance user story.
     * @param event, The click in the button btnUserParkDistance.
     */

    @FXML
    void handleBtnUIserParkDistance(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DistanceUserParkScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage distanceUserParkStage = new Stage();
        distanceUserParkStage.initModality(Modality.APPLICATION_MODAL);
        distanceUserParkStage.setTitle(TITLE);
        distanceUserParkStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        distanceUserParkStage.setMinHeight(distanceUserParkStage.getHeight());
        distanceUserParkStage.setMinWidth(distanceUserParkStage.getWidth());
        distanceUserParkStage.setResizable(false);
        distanceUserParkStage.show();

    }

    /**
     * Method that allows the user to enter the scene relative to the 5 closest parks user story.
     * @param event The click in the button btnNearestFiveParks.
     */

    @FXML
    void handleNearest5Parks(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NearestParksScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage distanceParkParkStage = new Stage();
        distanceParkParkStage.initModality(Modality.APPLICATION_MODAL);
        distanceParkParkStage.setTitle(TITLE);
        distanceParkParkStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        distanceParkParkStage.setMinHeight(distanceParkParkStage.getHeight());
        distanceParkParkStage.setMinWidth(distanceParkParkStage.getWidth());
        distanceParkParkStage.setResizable(false);
        distanceParkParkStage.show();

    }

    /**
     * Allows the user to go back.
     * @param event The event of clicking in button back.
     */
    @FXML
    void handleBtnBack(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnBack);

    }


}

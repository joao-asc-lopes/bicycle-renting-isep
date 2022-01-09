package lapr.project.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lapr.project.controller.user.SessionController;
import lapr.project.model.bicycle.Bicycle;

import java.io.IOException;

public class MenuShortestDistancesUIController {

    /**
     * Variable that represents the button that allows the user to see the routes options.
     */
    @FXML
    private Button btnRoute;
    /**
     * Variable that represents the button that allows the user to see the distances options.
     */

    @FXML
    private Button btnDistance;
    /**
     * Variable that represents the current session.
     */
    private SessionController currentSession;
    /**
     * Variable that represents the textField that shows the current bicycle being used by the logged user.
     */

    @FXML
    private TextField txtBikeUsing;

    /**
     * Variable that represents the button.
     */
    @FXML
    private Button btnCalories;
    /**
     * Variable that represents a button that allows the user to log out.
     */

    @FXML
    private Button btnLogOut;

    /**
     * Method that allows the user to be redirected to the "menu" of all the distances.
     * @param event The click on the button Distance.
     */

    @FXML
    void handleBtnDistance(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DistancesMenuScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage distanceMenuStage = new Stage();
        distanceMenuStage.initModality(Modality.APPLICATION_MODAL);
        distanceMenuStage.setTitle("Menu!");
        distanceMenuStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        distanceMenuStage.setMinHeight(distanceMenuStage.getHeight());
        distanceMenuStage.setMinWidth(distanceMenuStage.getWidth());
        distanceMenuStage.setResizable(false);
        distanceMenuStage.show();

    }

    /**
     * Method that allows the user to be redirected to the "menu" of all the route options.
     * @param event The click on the button route.
     */

    @FXML
    void handleBtnRoute(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RouteMenuScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        RouteSceneUIController dmuii = loader.getController();
        dmuii.initialize(currentSession);
        Stage routeMenuStage = new Stage();
        routeMenuStage.initModality(Modality.APPLICATION_MODAL);
        routeMenuStage.setTitle("Menu!");
        routeMenuStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        routeMenuStage.setMinHeight(routeMenuStage.getHeight());
        routeMenuStage.setMinWidth(routeMenuStage.getWidth());
        routeMenuStage.setResizable(false);
        routeMenuStage.show();

    }

    /**
     * Initialize that represents the parkRegistry.
     */

    public void initialize(Bicycle bike, SessionController currentSession){
        this.currentSession = currentSession;
        this.txtBikeUsing.setText(bike.toString());

    }

    /**
     * Method that allows the user to check for the calories burnt in a simulated trip.
     * @param event The click in the button btnCalories.
     */

    @FXML
    void handleBtnCalories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BurntCaloriesStartingParkScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        FirstParkBurntCalloriesUIController dmuii = loader.getController();
        dmuii.initialize(currentSession);
        Stage firstParkBurntCaloriesStage = new Stage();
        firstParkBurntCaloriesStage.initModality(Modality.APPLICATION_MODAL);
        firstParkBurntCaloriesStage.setTitle("Burnt Calories!");
        firstParkBurntCaloriesStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        firstParkBurntCaloriesStage.setMinHeight(firstParkBurntCaloriesStage.getHeight());
        firstParkBurntCaloriesStage.setMinWidth(firstParkBurntCaloriesStage.getWidth());
        firstParkBurntCaloriesStage.setResizable(false);
        firstParkBurntCaloriesStage.show();

    }

    /**
     * Allows the user to go back.
     * @param event The click on button.
     */
    @FXML
    void handleBtnLogOut(ActionEvent event) {
        UIControllerUtils.closeWindow(this.btnLogOut);
    }

    /**
     * Method
     * @param event
     */
    @FXML
    void handleTxtBikeUsing(ActionEvent event) {


    }
}

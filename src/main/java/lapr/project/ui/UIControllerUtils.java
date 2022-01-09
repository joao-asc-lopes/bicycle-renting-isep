/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * Creates an alert for user interaction.
 *
 * @author joaoLopes
 */
public final class UIControllerUtils {

    private UIControllerUtils(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates and show a new alert.
     *
     * @param alertType Type of alert.
     * @param title Title of the alert, should reflect its Type.
     * @param header Header of the alert, should reflect its Type.
     * @param message Message given to the user, should be short and precise.
     */
    public static void showAlert(Alert.AlertType alertType, String title, String header,
            String message) {
        Alert newAlert = new Alert(alertType);

        newAlert.setTitle(title);
        newAlert.setHeaderText(header);
        newAlert.setContentText(message);

        newAlert.show();
    }

    /**
     * Closes the Window where the closeButton is located.
     *
     * @param closeButton
     */
    public static void closeWindow(Button closeButton) {
        Window window = closeButton.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * Returns an alert of the type passed through parameter.
     *
     * @param alertType
     * @param title
     * @param header
     * @param message
     * @return
     */
    public static Alert createAlert(Alert.AlertType alertType, String title, String header,
            String message) {
        Alert newAlert = new Alert(alertType);

        newAlert.setTitle(title);
        newAlert.setHeaderText(header);
        newAlert.setContentText(message);
        return newAlert;
    }
}

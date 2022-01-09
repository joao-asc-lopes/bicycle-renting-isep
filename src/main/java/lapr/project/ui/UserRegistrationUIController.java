package lapr.project.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lapr.project.controller.user.UserRegistrationController;
import lapr.project.utils.InvalidDataException;

import java.net.URL;
import java.util.ResourceBundle;

public class UserRegistrationUIController implements Initializable {

    private UserRegistrationController userRegController;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField ccNumberField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField weightField;

    @FXML
    private TextField heightField;

    /**
     * Initializes the controller class, used to load the background image from
     * the resources file.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.userRegController = new UserRegistrationController();
    }

    @FXML
    void handleRegisterButtonAction(ActionEvent event) {
        if (!this.passwordField.getText().equals(this.confirmPasswordField.getText())) {
            UIControllerUtils.showAlert(Alert.AlertType.ERROR, "Creating Account Failed", "Invalid Data", "Passwords do not match.");
        } else {
            try {
                registerUser();
                UIControllerUtils.showAlert(Alert.AlertType.INFORMATION, "Success", "Account Created Successfully", "");
                UIControllerUtils.closeWindow(registerButton);
            } catch (InvalidDataException e) {
                UIControllerUtils.showAlert(Alert.AlertType.ERROR, "Creating Account Failed",
                        "Invalid Data", e.getMessage());
            } catch (Exception e){

            }
        }
    }

    private void registerUser() {
        try {
            String username = this.usernameField.getText();
            String email = this.emailField.getText();
            String password = this.passwordField.getText();
            String name = this.nameField.getText();
            float weight = Float.parseFloat(this.weightField.getText());
            float height = Float.parseFloat(this.heightField.getText());
            long ccNumber = Long.parseLong(this.ccNumberField.getText());
            double average_speed = 15.0; //A SER ALTERADO
            this.userRegController.registerUser(username,email, password, name, weight, height, ccNumber, average_speed);
        }   catch (NumberFormatException e) {
            throw new InvalidDataException("Invalid data inserted. Please verify and try again.");
        }
    }

}

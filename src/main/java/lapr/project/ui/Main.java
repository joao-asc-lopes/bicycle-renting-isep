package lapr.project.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Nuno Bettencourt <nmb@isep.ipp.pt> on 24/05/16.
 */
public class Main extends Application {
    /**
     * Application main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //load database properties
        try {
            Properties properties =
                    new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);


        } catch (IOException e) {
            e.printStackTrace();
        }


        //Initial Database Setup
        //DataHandler dh = new DataHandler();
        //dh.scriptRunner("target/test-classes/SQLFunctionsProcedures.sql");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignInPageScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Bike Sharing");
        primaryStage.setScene(scene);
        //sets the size of the window to the one defined in JavaFX.
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}


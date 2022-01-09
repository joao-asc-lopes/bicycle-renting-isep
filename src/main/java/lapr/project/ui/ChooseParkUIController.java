package lapr.project.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import lapr.project.model.bicycle.Bicycle;

public class ChooseParkUIController {


    /**
     * Variable that represents a listview that contains certain bicycles.
     */
    @FXML
    private ListView<Bicycle> listBikes;
    /**
     * Variable that represents a button that allows the user to choose a bicycle.
     */

    @FXML
    private Button btnChoose;

    /**
     * Initialize method.
     */

    public void initialize(){
        throw new UnsupportedOperationException("Method is not implemented yet");
    }

}
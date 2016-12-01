package fr.deltastar.pigou.controller;

import fr.deltastar.pigou.constant.ListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Valentin
 */
public class SettingsArduinoComController extends BaseViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    @FXML
    protected void btnBackClick() {
        super.navigate(ListView.SETTINGS);
    }
    
}

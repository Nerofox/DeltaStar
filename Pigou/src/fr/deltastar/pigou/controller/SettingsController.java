package fr.deltastar.pigou.controller;

import fr.deltastar.pigou.constants.ListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author Valentin
 */
public class SettingsController extends BaseViewController implements Initializable {

    @FXML private Button btnSettingsArduino;
    @FXML private Button btnAutoConfig;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    @FXML
    private void btnSettingsArduinoClick() {
        super.navigate(ListView.SETTINGS_ARDUINO_COM);
    }
    
    @FXML
    private void btnAutoConfigClick() {
        super.navigate(ListView.SETTINGS_AUTO_CONFIG);
    }

    @Override
    @FXML
    protected void btnBackClick() {
        super.navigate(ListView.WELCOME);
    }
    
}

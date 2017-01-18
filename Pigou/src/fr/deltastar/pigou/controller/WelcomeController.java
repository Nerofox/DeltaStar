package fr.deltastar.pigou.controller;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.constant.ListView;
import fr.deltastar.pigou.service.ServicePigou;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author Valentin
 */
public class WelcomeController extends BaseViewController implements Initializable {
    
    @FXML private Button btnLaunch;
    @FXML private Button btnTestingPanel;
    @FXML private Button btnSettings;
    @FXML private Button btnExit;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!new File(Constants.FILENAME_CONFIG).exists()) {
            ServicePigou.getMessageService().displayInfo(Constants.FILENAME_NOTFOUND_CONFIG_MSG);
        }
    }
    
    @FXML
    private void btnTestingPanelClick() {
        super.navigate(ListView.TESTING_PANEL);
    }
    
    @FXML
    private void btnLaunchClick() {
        super.navigate(ListView.LAUNCH_MISSION);
    }
    
    @FXML
    private void btnExitClick() {
        ServicePigou.getComArduinoService().stop();
        System.exit(0);
    }
    
    @FXML
    private void btnSettingsClick() {
        super.navigate(ListView.SETTINGS);
    }

    @Override
    protected void btnBackClick() {}
}

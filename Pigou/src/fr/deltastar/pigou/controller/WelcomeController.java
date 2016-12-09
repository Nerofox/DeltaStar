package fr.deltastar.pigou.controller;

import fr.deltastar.pigou.constant.ListView;
import fr.deltastar.pigou.service.ServicePigou;
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
    
    @FXML private Button btnSettings;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ServicePigou.getComArduinoService().launch();
    }
    
    @FXML
    private void btnSettingsClick() {
        super.navigate(ListView.SETTINGS);
    }

    @Override
    protected void btnBackClick() {}
}

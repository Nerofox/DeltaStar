package fr.deltastar.pigou.controller;

import fr.deltastar.pigou.constant.ListView;
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

    @FXML private Button bSettings;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    private void bSettingsClick() {
        super.navigate(ListView.SETTINGS);
    }

    @Override
    protected void btnBackClick() {}
}

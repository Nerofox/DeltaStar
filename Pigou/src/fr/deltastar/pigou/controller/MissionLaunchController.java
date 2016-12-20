/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.deltastar.pigou.controller;

import fr.deltastar.pigou.constant.ListView;
import fr.deltastar.pigou.service.ServicePigou;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Valentin
 */
public class MissionLaunchController extends BaseViewController implements Initializable {

    @FXML private Button btnStop;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!ServicePigou.getComArduinoService().isFullConnected()) {
            ServicePigou.getComArduinoService().launch();
        }
        ServicePigou.getComArduinoService().startRefreshArduino();
    }    
    
    @FXML
    private void btnStopClick() {
        super.navigate(ListView.WELCOME);
    }
}

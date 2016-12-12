package fr.deltastar.pigou.controller;

import fr.deltastar.pigou.communication.ListenerComInterface;
import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.customcontrol.TreeTableViewPanel;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.service.ServicePigou;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * FXML Controller class
 *
 * @author Valentin
 */
public class SettingsAutoConfigController extends BaseViewController implements Initializable, ListenerComInterface {

    @FXML private TreeTableViewPanel ttvpOutput;
    
    @FXML private ProgressBar pbOutput;
    @FXML private Label lNbOutput;
    private int currentNbOutput;
    private int totalNbOutput;
    
    @FXML private Label lCurrentInput;
    
    @FXML private ProgressBar pbInput;
    @FXML private Label lNbInput;
    private int currentNbInput;
    private int totalNbInput;
    
    @FXML private ProgressBar pbTotal;
    @FXML private Label lNbTotal;
    private int currentNbTotal;
    private int totalNb;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.ttvpOutput.loadTree(ComponentConstants.OUTPUT);
        
        this.totalNbInput = DeltaStar.getNbComponent(ComponentConstants.INPUT);
        this.totalNb = DeltaStar.getNbComponent();
        this.totalNbOutput = DeltaStar.getNbComponent(ComponentConstants.OUTPUT);
        
        this.lNbOutput.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_OUTPUT, this.currentNbOutput, this.totalNbOutput));
        this.lNbInput.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_INPUT, this.currentNbInput, this.totalNbInput));
        this.lNbTotal.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_TOTAL, this.currentNbTotal, this.totalNb));
        
        if (!ServicePigou.getComArduinoService().isFullConnected()) {
            ServicePigou.getComArduinoService().launch(this);
        }
    }

    @Override
    public void onDataReceved(String data) {
        
    }

    @Override
    public void onConnectArduino(String arduinoId) {
        //une fois connecté on démarre le paramétrage
        if (ServicePigou.getComArduinoService().isFullConnected()) {
            //TODO a faire
        }
    }

    @FXML
    @Override
    protected void btnBackClick() {
        
    }
    
    
}

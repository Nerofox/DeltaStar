package fr.deltastar.pigou.controller;

import fr.deltastar.pigou.communication.ListenerComInterface;
import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.constant.ListView;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.constant.LcdSystemEngineConstants;
import fr.deltastar.pigou.model.constant.LcdSystemLifePackConstants;
import fr.deltastar.pigou.model.constant.LcdSystemPowerConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.system.EngineSystem;
import fr.deltastar.pigou.model.panel.system.LifePackSystem;
import fr.deltastar.pigou.model.panel.system.PowerSystem;
import fr.deltastar.pigou.service.ServicePigou;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * @author Valentin
 */
public class TestingPanelController extends BaseViewController implements Initializable, ListenerComInterface {

    @FXML private Label lCurrentInput;
    @FXML private ProgressBar pbProgressInput;
    
    @FXML private Button btnSwitchOn;
    @FXML private Button btnSwitchOff;
    @FXML private Button btnReset;
    
    private int totalNbInput;
    private int currentNbInput;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!ServicePigou.getComArduinoService().isFullConnected()) {
            ServicePigou.getComArduinoService().launch(this);
        } //else {
            this.onConnect(null);
        //}
        //ServicePigou.getComArduinoService().startRefreshArduino();
        
        this.totalNbInput = DeltaStar.getNbComponent(ComponentConstants.INPUT) + Constants.NB_SWITCH_INPUT;
        this.lCurrentInput.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_INPUT, this.currentNbInput, this.totalNbInput));
    }
    
    @FXML
    private void btnSwitchOnClick() {
        List<Component> components = DeltaStar.getListComponents(ComponentConstants.OUTPUT);
        for (Component c : components) {
            c.switchBlink();
        }
        ServicePigou.getComArduinoService().getArduinoA().sendOutput();
        ServicePigou.getComArduinoService().getArduinoB().sendOutput();
        ServicePigou.getComArduinoService().getArduinoC().sendOutput();
    }
    
    @FXML
    private void btnSwitchOffClick() {
        List<Component> components = DeltaStar.getListComponents(ComponentConstants.OUTPUT);
        for (Component c : components) {
            c.switchOff();
        }
        ServicePigou.getComArduinoService().getArduinoA().sendOutput();
        ServicePigou.getComArduinoService().getArduinoB().sendOutput();
        ServicePigou.getComArduinoService().getArduinoC().sendOutput();
    }

    @Override
    @FXML
    public void btnBackClick() {
        //ServicePigou.getComArduinoService().stopRefreshArduino();
        super.navigate(ListView.WELCOME);
    }
    
    @FXML
    private void btnResetClick() {
        this.currentNbInput = 0;
        this.lCurrentInput.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_INPUT, this.currentNbInput, this.totalNbInput));
        this.pbProgressInput.setProgress(0);
    }
    
    @Override
    public void onDataReceved(String data) {
        this.currentNbInput++;
        Platform.runLater(() -> {
            lCurrentInput.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_INPUT, currentNbInput, totalNbInput));
            pbProgressInput.setProgress((double)currentNbInput / (double)totalNbInput);
        });
    }

    @Override
    public void onConnect(String arduinoId) {
        if (ServicePigou.getComArduinoService().isFullConnected()) {
            this.pbProgressInput.setDisable(false);
            this.btnSwitchOff.setDisable(false);
            this.btnSwitchOn.setDisable(false);
            this.btnReset.setDisable(false);
            
            //display LCD mod
            PowerSystem ps = DeltaStar.getPowerSystem();
            EngineSystem es = DeltaStar.getEngineSystem();
            LifePackSystem lps = DeltaStar.getLifePackSystem();
            lps.getArduinoComLcd().setLcdMod(LcdSystemLifePackConstants.DISPLAY_O2N2_CELSIUS);
            lps.setArgOne(100);
            lps.setArgTwo(100);
            ps.getArduinoComLcd().setLcdMod(LcdSystemPowerConstants.DISPLAY_STATUS);
            ps.setArgOne(100);
            ps.setArgTwo(100);
            es.getArduinoComLcd().setLcdMod(LcdSystemEngineConstants.DISPLAY_FUEL);
            es.setArgOne(9600);
            es.setArgTwo(600);
        }
    }
    
    
}

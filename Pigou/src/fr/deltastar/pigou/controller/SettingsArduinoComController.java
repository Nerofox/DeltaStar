package fr.deltastar.pigou.controller;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.constant.ListView;
import fr.deltastar.pigou.model.constant.ArduinoPortConstants;
import fr.deltastar.pigou.service.ComArduinoService;
import fr.deltastar.pigou.service.ServicePigou;
import fr.deltastar.pigou.utils.FileManager;
import gnu.io.CommPortIdentifier;
import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * Gestion de la config des port COM associé a chaque Arduino
 * Effectue la sauvegarde sur le fichier prevu a cet effet
 * @author Valentin
 */
public class SettingsArduinoComController extends BaseViewController implements Initializable {

    @FXML private ComboBox<String> cbArduinoA;
    @FXML private ComboBox<String> cbArduinoB;
    @FXML private ComboBox<String> cbArduinoC;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //recuperation list des port com et insertion dans les listes
        Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) 
        {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            //seul les port série (type port COM pour windows) nous interesse
            if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                this.cbArduinoA.getItems().add(portIdentifier.getName());
                this.cbArduinoB.getItems().add(portIdentifier.getName());
                this.cbArduinoC.getItems().add(portIdentifier.getName());
            }
        }
        
        //si possible selection du port com actuel par listbox
        if (ServicePigou.getComArduinoService().getPortComA() != null) {
            this.cbArduinoA.setValue(ServicePigou.getComArduinoService().getPortComA());
        }
        if (ServicePigou.getComArduinoService().getPortComB() != null) {
            this.cbArduinoB.setValue(ServicePigou.getComArduinoService().getPortComB());
        }
        if (ServicePigou.getComArduinoService().getPortComC() != null) {
            this.cbArduinoC.setValue(ServicePigou.getComArduinoService().getPortComC());
        }
    }    

    @Override
    @FXML
    protected void btnBackClick() {
        super.navigate(ListView.SETTINGS);
    }
    
    @FXML
    private void cbArduinoAOnChange() {
        ServicePigou.getComArduinoService().setPortComA(this.cbArduinoA.getSelectionModel().getSelectedItem());
    }
    
    @FXML
    private void cbArduinoBOnChange() {
        ServicePigou.getComArduinoService().setPortComB(this.cbArduinoB.getSelectionModel().getSelectedItem());
    }
    
    @FXML
    private void cbArduinoCOnChange() {
        ServicePigou.getComArduinoService().setPortComC(this.cbArduinoC.getSelectionModel().getSelectedItem());
    }
    
    @FXML
    private void btnSaveClick() {
        ComArduinoService com = ServicePigou.getComArduinoService();
        //creation des 3 lignes de conf par port com
        String[] out = new String[3];
        out[0] = new String(ArduinoPortConstants.ARDUINO_A + Constants.FILENAME_DELIMITER + com.getPortComA());
        out[1] = new String(ArduinoPortConstants.ARDUINO_B + Constants.FILENAME_DELIMITER + com.getPortComB());
        out[2] = new String(ArduinoPortConstants.ARDUINO_C + Constants.FILENAME_DELIMITER + com.getPortComC());
        //sauvegarde
        File f = new File(Constants.FILENAME_CONFIG_PORT_COM_ARDUINO);
        if (f.exists())
            f.delete();
        FileManager.save(Constants.FILENAME_CONFIG_PORT_COM_ARDUINO, out);
        ServicePigou.getMessageService().displayInfo(Constants.AUTO_CONFIG_MSG_FINISH);
    }
}

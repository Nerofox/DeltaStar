package fr.deltastar.pigou.controller;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.constant.ListView;
import fr.deltastar.pigou.customcontrol.TreeTableViewPanel;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.service.ServicePigou;
import fr.deltastar.pigou.utils.FileManager;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author Valentin
 */
public class SettingsController extends BaseViewController implements Initializable {

    @FXML private Button btnSettingsArduino;
    @FXML private Button btnAutoConfig;
    
    @FXML private TreeTableViewPanel ttvpInput;
    @FXML private TreeTableViewPanel ttvpOutput;
    
    @FXML private TextField tfInput;
    @FXML private TextField tfOutput;
    @FXML private Button btnSaveInput;
    @FXML private Button btnSaveOutput;
    
    private Component currentComponent;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.ttvpInput.loadTree(ComponentConstants.INPUT);
        this.ttvpOutput.loadTree(ComponentConstants.OUTPUT);
    }
    
    @FXML
    private void btnSettingsArduinoClick() {
        super.navigate(ListView.SETTINGS_ARDUINO_COM);
    }
    
    @FXML
    private void btnAutoConfigClick() {
        super.navigate(ListView.SETTINGS_AUTO_CONFIG);
    }
    
    @FXML
    private void btnInputClick() {
        this.ttvpInput.setPosForSelectedComponent(Integer.parseInt(this.tfInput.getText()));
        SettingsController.saveConfig();
    }
    
    @FXML
    private void btnOutputClick() {
        this.ttvpOutput.setPosForSelectedComponent(Integer.parseInt(this.tfOutput.getText()));
        SettingsController.saveConfig();
    }

    @Override
    @FXML
    protected void btnBackClick() {
        super.navigate(ListView.WELCOME);
    }
    
    public static void saveConfig() {
        //finish, création du tableau de config
        List<Component> listAllComponents = DeltaStar.getListComponents();
        String[] componentsConf = new String[listAllComponents.size()];
        Component c;
        for (int i = 0; i < listAllComponents.size(); i++) {
            c = listAllComponents.get(i);
            componentsConf[i] = c.getComArduino().getArduinoId() + Constants.FILENAME_DELIMITER + c.getIdPos(); 
        }
        //sauvegarde, écrase le fichier de conf existant
        File f = new File(Constants.FILENAME_CONFIG);
        if (f.exists())
            f.delete();
        FileManager.save(Constants.FILENAME_CONFIG, componentsConf);
    }
    
}

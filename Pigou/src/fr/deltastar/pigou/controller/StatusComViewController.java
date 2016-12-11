package fr.deltastar.pigou.controller;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.constant.RessourcesConstants;
import fr.deltastar.pigou.model.constant.ArduinoPortConstants;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.service.ServicePigou;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Valentin
 */
public class StatusComViewController implements Initializable {

    private static StatusComViewController instance;
    
    @FXML private ProgressIndicator piProgressA;
    @FXML private ImageView ivStatusA;
    @FXML private Label lStatusErrorA;
    
    @FXML private ProgressIndicator piProgressB;
    @FXML private ImageView ivStatusB;
    @FXML private Label lStatusErrorB;
    
    @FXML private ProgressIndicator piProgressC;
    @FXML private ImageView ivStatusC;
    @FXML private Label lStatusErrorC;
    
    @FXML private ListView lvDataInput;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StatusComViewController.setInstance(this);
        ServicePigou.getComArduinoService().launch();
    }
    
    public void setStatusOk(String arduinoId) {
        if (arduinoId.equals(ArduinoPortConstants.ARDUINO_A)) {
            this.piProgressA.setVisible(false);
            this.ivStatusA.setVisible(true);
            this.ivStatusA.setImage(new Image(RessourcesConstants.OK));
        } else if (arduinoId.equals(ArduinoPortConstants.ARDUINO_B)) {
            this.piProgressB.setVisible(false);
            this.ivStatusB.setVisible(true);
            this.ivStatusB.setImage(new Image(RessourcesConstants.OK));
        } else if (arduinoId.equals(ArduinoPortConstants.ARDUINO_C)) {
            this.piProgressC.setVisible(false);
            this.ivStatusC.setVisible(true);
            this.ivStatusC.setImage(new Image(RessourcesConstants.OK));
        }
    }
    
    public void setStatusInProgress(String arduinoId) {
        if (arduinoId.equals(ArduinoPortConstants.ARDUINO_A)) {
            this.piProgressA.setVisible(true);
            this.ivStatusA.setVisible(false);
        } else if (arduinoId.equals(ArduinoPortConstants.ARDUINO_B)) {
            this.piProgressB.setVisible(true);
            this.ivStatusB.setVisible(false);
        } else if (arduinoId.equals(ArduinoPortConstants.ARDUINO_C)) {
            this.piProgressC.setVisible(true);
            this.ivStatusC.setVisible(false);
        }
    }
    
    public void setStatusKo(String arduinoId, String msg) {
        if (arduinoId.equals(ArduinoPortConstants.ARDUINO_A)) {
            this.piProgressA.setVisible(false);
            this.ivStatusA.setVisible(true);
            this.ivStatusA.setImage(new Image(RessourcesConstants.KO));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lStatusErrorA.setText(msg);
                }
            });
        } else if (arduinoId.equals(ArduinoPortConstants.ARDUINO_B)) {
            this.piProgressB.setVisible(false);
            this.ivStatusB.setVisible(true);
            this.ivStatusB.setImage(new Image(RessourcesConstants.KO));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lStatusErrorB.setText(msg);
                }
            });
        } else if (arduinoId.equals(ArduinoPortConstants.ARDUINO_C)) {
            this.piProgressC.setVisible(false);
            this.ivStatusC.setVisible(true);
            this.ivStatusC.setImage(new Image(RessourcesConstants.KO));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lStatusErrorC.setText(msg);
                }
            });
        }
    }
    
    public void addDataInput(String data) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lvDataInput.getItems().add(data);
            }
    });
        
    }
    
    public static StatusComViewController getInstance() {
        return instance;
    }

    public static void setInstance(StatusComViewController instance) {
        StatusComViewController.instance = instance;
    }
}

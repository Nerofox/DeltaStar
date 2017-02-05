package fr.deltastar.pigou.controller;

import fr.deltastar.pigou.communication.ComArduino;
import fr.deltastar.pigou.communication.ListenerComInterface;
import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.constant.ListView;
import fr.deltastar.pigou.customcontrol.TreeTableViewPanel;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.service.ServicePigou;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * FXML Controller class
 *
 * @author Valentin
 */
public class SettingsAutoConfigController extends BaseViewController implements Initializable, ListenerComInterface {

    private List<Component> listComponentsOutput;
    private List<Component> listComponentsInput;
    
    private int[] listInputAlreadyPressed;
    
    private List<ComArduino> listComArduino;
    
    //utiliser pour la boucle de paramétrage
    private int currentPosOutputArduino;
    //private int currentIdInputArduino;
    private Component currentComponentInput;
    private int currentArduino;

    @FXML private TreeTableViewPanel ttvpOutput;
    
    @FXML private ProgressBar pbOutput;
    @FXML private Label lNbOutput;
    private int currentNbOutputArduino;
    private int currentNbOutput;
    private int totalNbOutput;
    
    @FXML private Label lCurrentInput;
    
    @FXML private ProgressBar pbInput;
    @FXML private Label lNbInput;
    private int currentNbInput;
    private int totalNbInput;
    @FXML private Button btnNext;
    
    @FXML private ProgressBar pbTotal;
    @FXML private Label lNbTotal;
    private int currentNbTotal;
    private int totalNb;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.listComponentsInput = DeltaStar.getListComponents(ComponentConstants.INPUT);
        this.listComponentsOutput = DeltaStar.getListComponents(ComponentConstants.OUTPUT);
        this.ttvpOutput.loadTree(ComponentConstants.OUTPUT);
        
        this.totalNbInput = this.listComponentsInput.size();
        this.totalNb = this.listComponentsInput.size() + this.listComponentsOutput.size();
        this.totalNbOutput = this.listComponentsOutput.size();
        
        this.listInputAlreadyPressed = new int[this.totalNbInput];
        for (int i = 0; i < this.listInputAlreadyPressed.length; i++)
            this.listInputAlreadyPressed[i] = -1;
        
        this.lNbOutput.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_OUTPUT, this.currentNbOutputArduino, this.totalNbOutput));
        this.lNbInput.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_INPUT, this.currentNbInput, this.totalNbInput));
        this.lNbTotal.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_TOTAL, this.currentNbTotal, this.totalNb));
        
        if (!ServicePigou.getComArduinoService().isFullConnected())
            ServicePigou.getComArduinoService().launch(this);
    }
    
    /**
     * Cliqué en mode paramétrage output
     */
    @FXML
    public void btnNextClick() {
        //cas du premier clic
        if (this.btnNext.getText().equals("Start")) {
            this.btnNext.setText("Next");
            this.nextStep();
            return;
        }
        
        //recupere output selectionné
        Component cSelect = this.ttvpOutput.getSelectedComponent();
        if (cSelect != null) {
            //inscri le numéro et le composant arduino dans le composant choisi de la liste output
            cSelect.setComArduino(this.listComArduino.get(this.currentArduino));
            cSelect.setIdPos(this.currentPosOutputArduino - 1);
            this.ttvpOutput.refresh();
            
            if (this.currentPosOutputArduino > 0) {
                //remise a zero de l'output précédent
                this.listComArduino.get(this.currentArduino).setValueLed(this.currentPosOutputArduino - 1, ComponentConstants.OFF);
            }
            this.nextStep();
        } else {
            ServicePigou.getMessageService().displayInfo(Constants.AUTOCONFIG_MSG_CHOOSEOUTPUT);
        }
    }

    /**
     * Appellé en mode paramétrage input
     * @param data 
     */
    @Override
    public void onDataReceved(String data) {
        //empèche une étape suivante tant qu'on a pas finis le mode output
        if (this.totalNbOutput == this.currentNbOutput) {
            //si touche déja préssé on l'ignore
            int dataInt = Integer.parseInt(data);
            for (int i = 0; i < this.listInputAlreadyPressed.length; i++) {
                if (this.listInputAlreadyPressed[i] == dataInt) {
                    System.out.println("Input " + dataInt + " is already pressed");
                    return;
                }
            }
            this.listInputAlreadyPressed[this.currentNbInput - 1] = dataInt;
            
            //Arduino C est le seul a fournir les entrées
            this.currentComponentInput.setComArduino(ServicePigou.getComArduinoService().getArduinoC());
            this.currentComponentInput.setIdPos(dataInt);
            this.nextStep();
        }
    }
    
    /**
     * Effectue le paramétrage au fur et a mesure de la progression
     * commence par les output puis par les input
     */
    public void nextStep() {
        if (this.totalNbInput > this.currentNbInput) {
            if (this.totalNbOutput > this.currentNbOutput) {
                //PARAMETRAGE OUTPUT
                //si on atteint la limite pour l'envoi des outputs sur l'arduino en cours, on change
                if (this.currentPosOutputArduino > this.listComArduino.get(this.currentArduino).getNbOutputLed() - 1) {
                    this.currentArduino++;
                    this.listComArduino.get(this.currentArduino - 1).setValueLed(this.currentPosOutputArduino - 1, ComponentConstants.OFF);
                    this.listComArduino.get(this.currentArduino - 1).sendOutput();
                    this.currentPosOutputArduino = 0;
                }
                this.listComArduino.get(this.currentArduino).setValueLed(this.currentPosOutputArduino, ComponentConstants.ON);
                this.listComArduino.get(this.currentArduino).sendOutput();
                
                this.currentPosOutputArduino++;
                this.currentNbOutputArduino++;
                this.currentNbOutput++;
                this.currentNbTotal++;

                //fait évoluer la progressbar output et total
                Platform.runLater(() -> {
                    lNbOutput.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_OUTPUT, currentNbOutput, totalNbOutput));
                    lNbTotal.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_TOTAL, currentNbOutput + currentNbInput, totalNb));
                    pbOutput.setProgress((double)currentNbOutput / (double)totalNbOutput);
                    pbTotal.setProgress((double)currentNbTotal / (double)totalNb);
                });
                
            } else {
                //remise a zéro de la précédente led output avant paramétrage input
                if (this.currentNbOutputArduino > 0) {
                    this.listComArduino.get(this.currentArduino).setValueLed(this.currentPosOutputArduino - 1, ComponentConstants.OFF);
                    this.listComArduino.get(this.currentArduino).sendOutput();
                    this.currentNbOutputArduino = 0;
                    
                    //changement affichage
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            btnNext.setVisible(false);
                            pbInput.setVisible(true);
                            lNbInput.setVisible(true);
                        }
                    });
                    ServicePigou.getMessageService().displayInfo(Constants.AUTO_CONFIG_MSG_OUTPUTFINISH);
                    this.nextStep();
                    return;
                }
                //PARAMETRAGE INPUT
                //bascule sur le composant suivant et demande a l'utilisateur de le presser
                //dernière erreur java.lang.IndexOutOfBoundsException: Index: 34, Size: 34
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lNbInput.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_INPUT, currentNbInput + 1, totalNbInput));
                        lNbTotal.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_TOTAL, currentNbOutput + currentNbInput + 1, totalNb));
                        pbInput.setProgress(((double)(currentNbInput + 1)) / (double)totalNbInput);
                        pbTotal.setProgress((double)currentNbTotal / (double)totalNb);
                        lCurrentInput.setText(currentComponentInput.toString());
                        currentNbInput++;
                        currentNbTotal++;
                    }
                });
                this.currentComponentInput = this.listComponentsInput.get(this.currentNbInput);
            }
        } else {
            //FINISSION DU PARAMETRAGE
            SettingsController.saveConfig();
            ServicePigou.getMessageService().displayInfo(Constants.AUTO_CONFIG_MSG_FINISH);
        }
    }

    @Override
    public void onConnect(String arduinoId) {
        //une fois connecté on démarre le paramétrage
        if (ServicePigou.getComArduinoService().isFullConnected()) {
            this.listComArduino = ServicePigou.getComArduinoService().getAllArduino();
            ServicePigou.getMessageService().displayInfo(Constants.AUTO_CONFIG_MSG_OUTPUTPROGRESS);
            //this.nextStep();
        }
    }

    @FXML
    @Override
    protected void btnBackClick() {
        super.navigate(ListView.SETTINGS);
    }
    
}

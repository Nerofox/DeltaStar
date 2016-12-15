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
    
    private List<ComArduino> listComArduino;
    
    //utiliser pour la boucle de paramétrage
    private int currentPosOutputArduino;
    private int currentIdInputArduino;
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
        this.totalNbOutput = this.listComponentsOutput.size() + 1;
        
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
        //recupere output selectionné
        Component cSelect = this.ttvpOutput.getSelectedComponent();
        if (cSelect != null) {
            //inscri le numéro et le composant arduino dans le composant choisi de la liste output
            cSelect.setComArduino(this.listComArduino.get(this.currentArduino));
            cSelect.setIdPos(this.currentPosOutputArduino);
            
            if (this.currentPosOutputArduino > 0) {
                //remise a zero de l'output précédent
                this.listComArduino.get(this.currentArduino).setValueLed(this.currentPosOutputArduino - 1, ComponentConstants.OFF);
            }
            this.nextStep();
        } else {
            ServicePigou.getMessageService().displayInfo("Please choose a output before next");
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
            //TODO insérer le numéro 'data' dans le composant actuelle input de la liste 
            this.nextStep();
        }
    }
    
    /**
     * Effectue le paramétrage au fur et a mesure de la progression
     * commence par les output puis par les input
     */
    public void nextStep() {
        if (this.totalNb > this.currentNbTotal) {
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

                //TODO faire évoluer la progressbar output et total
                this.lNbOutput.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_OUTPUT, this.currentNbOutput, this.totalNbOutput));
                this.lNbTotal.setText(String.format(Constants.AUTOCONFIG_VIEW_NB_TOTAL, this.currentNbOutput + this.currentNbInput, this.totalNb));
                this.pbOutput.setProgress((double)this.currentNbOutput / (double)this.totalNbOutput);
                this.pbTotal.setProgress((double)this.currentNbTotal / (double)this.totalNb);
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
                            //TODO plantage nummpointer lol
                            btnNext.setVisible(false);
                            pbInput.setVisible(true);
                        }
                    });
                    this.nextStep();
                    return;
                }
                System.out.println("input");
                //PARAMETRAGE INPUT
                //TODO bascule sur le composant suivant et demande a l'utilisateur de le presser
                //TODO faire évoluer la progressbar input et total
                this.currentNbInput++;
            }
        } else {
            //FINISSION DU PARAMETRAGE
            System.out.println("finish !");
        }
    }

    @Override
    public void onConnectArduino(String arduinoId) {
        //une fois connecté on démarre le paramétrage
        if (ServicePigou.getComArduinoService().isFullConnected()) {
            this.listComArduino = ServicePigou.getComArduinoService().getAllArduino();
            ServicePigou.getMessageService().displayInfo("Configuration output in progress look at Deltastar and clic on the component on");
            this.nextStep();
        }
    }

    @FXML
    @Override
    protected void btnBackClick() {
        super.navigate(ListView.SETTINGS);
    }
    
    
}

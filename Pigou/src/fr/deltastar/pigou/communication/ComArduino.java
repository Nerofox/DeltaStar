package fr.deltastar.pigou.communication;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.model.constant.ArduinoPortConstants;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.panel.SystemLcdInterface;
import java.util.List;

/**
 * Effectue la communication logique avec l'arduino en input/output
 * @author Valentin
 */
public class ComArduino implements ListenerComInterface {
    
    private ComClientInterface com;
    private SystemLcdInterface sli;
    
    private char[] outputCodeLed;
    private int nbOutputLed;
    
    private char statusLcd;
    private String outputArgOneLcd;
    private int sizeOutputArgOneLcd;
    private String outputArgTwoLcd;
    private int sizeOutputArgTwoLcd;
    
    private String arduinoId;
    
    /**
     * Lance la communication avec un arduino
     * @param port numéro de port ou port com
     * @param modeInputOutput si l'arduino est en mode output ou input, être input correspond au deux modes
     * @param nbOutput nombre de led que dispose l'arduino
     * @param sizeArgOneLcd taille du premier argument envoyé a l'écran LCD
     * @param sizeArgTwoLcd
     * @param lci 
     * @param arduinoId
     */
    public void start(String port, int modeInputOutput, int nbOutput, int sizeArgOneLcd, int sizeArgTwoLcd, 
                      ListenerComInterface lci, String arduinoId) {
        this.statusLcd = '0';
        this.nbOutputLed = nbOutput;
        this.arduinoId = arduinoId;
       
        //initialisation des valeurs de l'argument 1 et 2 des LCD
        this.sizeOutputArgOneLcd = sizeArgOneLcd;
        this.outputArgOneLcd = "";
        for (int i = 0; i < this.sizeOutputArgOneLcd; i++)
            this.outputArgOneLcd += "0";
        
        this.sizeOutputArgTwoLcd = sizeArgTwoLcd;
        this.outputArgTwoLcd = "";
        for (int i = 0; i < this.sizeOutputArgTwoLcd; i++)
            this.outputArgTwoLcd += "0";
        
        //initialisation des led, par défaut 0 
        this.outputCodeLed = new char[nbOutput];
        for (int i = 0; i < nbOutput; i++) {
            this.outputCodeLed[i] = '0';
        }
        
        //selon le mode paramétré on utilise le panel virtuel ou réelle
        if (Constants.MODE_VIRTUAL) {
            this.com = new SocketClient();
        } else {
            this.com = new SerialClient();
        }
        this.com.connect(Constants.VIRTUAL_IP, port, lci, arduinoId);
        //on écoute les données entrante si souhaité
        if (modeInputOutput == ComponentConstants.INPUT && this.com.isConnect()) {
            this.com.listenInput();
        }
    }
    
    /**
     * Incris un état d'une led a une position donnée
     * @param pos
     * @param status 
     */
    public void setValueLed(int pos, int status) {
        this.outputCodeLed[pos] = Integer.toString(status).charAt(0);
    }
    
    /**
     * Inscris le mode dans lequel le LCD doit se mettre
     * @param statusLcd 
     */
    public void setLcdMod(int statusLcd) {
        this.statusLcd = Integer.toString(statusLcd).charAt(0);
    }
    
    /**
     * Envoi a l'arduino le code de sortie complet
     */
    public void sendOutput() {
        if (this.isConnect()) {
            //si on utilise un écran LCD, MAJ auto des valeurs de l'écran LCD
            if (this.sli != null) {
                this.setLcdArg(this.sli.getArgOne(), this.sli.getArgTwo());
            }
            StringBuilder sbOuput = new StringBuilder(String.valueOf(this.outputCodeLed));
            sbOuput.append(this.statusLcd).append(this.outputArgOneLcd).append(this.outputArgTwoLcd);
            this.com.sendData(sbOuput.toString());
        }
    }
    
    /**
     * Ferme l'ensemble de la connection
     */
    public void closeConnection() {
        if (this.com != null)
            this.com.closeConnection();
        this.com = null;
    }
    
    /**
     * Vérifie la connection a l'arduino
     * @return 
     */
    public Boolean isConnect() {
        if (this.com == null)
            return false;
        return this.com.isConnect();
    }

    /**
     * Déclenchée par défaut quand une donnée arrive sur l'arduino
     * execute le onAction correspondant au module lié a l'input
     * @param data 
     */
    @Override
    public void onDataReceved(String data) {
        System.out.println("Data in coming : " + data);
        int numberInput = 0;
        try {
            numberInput = Integer.parseInt(data);
        } catch (NumberFormatException e) {
            System.out.println("Data in not number");
            return;
        }
        List<Component> cs = DeltaStar.getAirlockSystem().getGearModule().getListComponents();
        for (BaseSystem baseSystem : DeltaStar.getListSystem()) {
            for (ModuleInterface module : baseSystem.getListModuleInterface()) {
                for (Component c : module.getListComponents()) {
                    if (c.getType()== ComponentConstants.INPUT && c.getIdPos() == numberInput) {
                        if (c.getStatus() == ComponentConstants.ON) {
                            c.setStatus(ComponentConstants.OFF);
                            module.onAction(false);
                        } else {
                            c.setStatus(ComponentConstants.ON);
                            module.onAction(true);
                        }
                    }
                }
            }
        }
    }

    public void setSli(SystemLcdInterface sli) {
        this.sli = sli;
    }
    
    public int getNbOutputLed() {
        return nbOutputLed;
    }

    public void setArduinoId(String arduinoId) {
        this.arduinoId = arduinoId;
    }
    
    public String getArduinoId() {
        return arduinoId;
    }

    @Override
    public void onConnect(String arduinoId) {}
    
    /**
     * Inscris les arguments pour l'écran LCD, varie selon le type d'affichage
     * @param argOne : inscrire -1 pour ignorer l'argument 1
     * @param argTwo : inscrire -1 pour ignorer l'argument 2
     */
    private void setLcdArg(int argOne, int argTwo) {
        StringBuilder sb = null;
        
        if (argOne != -1) {
            String argOneString = String.valueOf(argOne);
            int nbZeroArgOne = this.sizeOutputArgOneLcd - argOneString.length();

            sb = new StringBuilder("");
            for (int i = 0; i < nbZeroArgOne; i++)
                sb.append("0");
            sb.append(argOneString);
            this.outputArgOneLcd = sb.toString();
        }
        
        if (argTwo != -1) {
            String argTwoString = String.valueOf(argTwo);
            int nbZeroArgTwo = this.sizeOutputArgTwoLcd - argTwoString.length();

            sb = new StringBuilder("");
            for (int i = 0; i < nbZeroArgTwo; i++)
                sb.append("0");
            sb.append(argTwoString);
            this.outputArgTwoLcd = sb.toString();
        }
    }
}

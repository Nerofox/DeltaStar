package fr.deltastar.pigou.communication;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;

/**
 * Effectue la communication logique avec l'arduino en input/output
 * @author Valentin
 */
public class ComArduino implements ListenerComInterface {
    
    private ComClientInterface com;
    
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
        this.statusLcd = 0;
        this.nbOutputLed = nbOutput;
        this.arduinoId = arduinoId;
       
        this.sizeOutputArgOneLcd = sizeArgOneLcd;
        this.sizeOutputArgTwoLcd = sizeArgTwoLcd;
        
        //selon le mode paramétré on utilise le panel virtuel ou réelle
        if (Constants.MODE_VIRTUAL) {
            this.com = new SocketClient();
        } else {
            //TODO faire pour la commande série
        }
        this.com.connect(port, lci, arduinoId);
        //on écoute les données entrante si souhaité
        if (modeInputOutput == ComponentConstants.INPUT) {
            this.com.listenInput();
        }
        //initialisation des led, par défaut 0 
        this.outputCodeLed = new char[nbOutput];
        for (int i = 0; i < nbOutput; i++) {
            this.outputCodeLed[i] = '0';
        }
    }
    
    public void setValueLed(int pos, int status) {
        this.outputCodeLed[pos] = Integer.toString(status).charAt(0);
    }
    
    public void setLcdMod(int statusLcd) {
        this.statusLcd = (char)statusLcd;
    }

    public int getNbOutputLed() {
        return nbOutputLed;
    }
    
    public void setLcdArg(int argOne, int argTwo) {
        String argOneString = String.valueOf(argOne);
        int nbZeroArgOne = this.sizeOutputArgOneLcd - argOneString.length();
        
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < nbZeroArgOne; i++)
            sb.append("0");
        sb.append(argOneString);
        this.outputArgOneLcd = sb.toString();
        
        
        String argTwoString = String.valueOf(argTwo);
        int nbZeroArgTwo = this.sizeOutputArgTwoLcd - argTwoString.length();
        
        sb = new StringBuilder("");
        for (int i = 0; i < nbZeroArgTwo; i++)
            sb.append("0");
        sb.append(argTwoString);
        this.outputArgTwoLcd = sb.toString();
    }
    
    public void sendOutput() {
        StringBuilder sbOuput = new StringBuilder(String.valueOf(this.outputCodeLed));
        sbOuput.append(this.statusLcd).append(this.outputArgOneLcd).append(this.outputArgTwoLcd);
        this.com.sendData(sbOuput.toString());
    }
    
    public void closeConnection() {
        if (this.com != null)
            this.com.closeConnection();
        this.com = null;
    }
    
    public Boolean isConnect() {
        if (this.com == null)
            return false;
        return this.com.isConnect();
    }

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
        for (BaseSystem baseSystem : DeltaStar.getListSystem()) {
            for (ModuleInterface module : baseSystem.getListModuleInterface()) {
                for (Component c : module.getListComponents()) {
                    if (c.getStatus() == ComponentConstants.INPUT && c.getIdPos() == numberInput) {
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

    public String getArduinoId() {
        return arduinoId;
    }

    @Override
    public void onConnectArduino(String arduinoId) {}
}

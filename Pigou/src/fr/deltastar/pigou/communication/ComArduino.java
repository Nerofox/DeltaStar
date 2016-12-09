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
    
    private char statusLcd;
    private String outputArgOneLcd;
    private int sizeOutputArgOneLcd;
    private String outputArgTwoLcd;
    private int sizeOutputArgTwoLcd;
    
    public void start(String port, int modeInputOutput, int sizeArgOneLcd, int sizeArgTwoLcd) {
        this.statusLcd = 0;
       
        this.sizeOutputArgOneLcd = sizeArgOneLcd;
        this.sizeOutputArgTwoLcd = sizeArgTwoLcd;
        
        //selon le mode paramétré on utilise le panel virtuel ou réelle
        if (Constants.MODE_VIRTUAL) {
            this.com = new SocketClient();
        } else {
            //TODO faire pour la commande série
        }
        this.com.connect(port, this);
        //on écoute les données entrante si souhaité
        if (modeInputOutput == ComponentConstants.INPUT) {
            this.com.listenInput();
        }
    }
    
    public void setValueLed(int pos, int status) {
        this.outputCodeLed[pos] = (char)status;
    }
    
    public void setLcdMod(int statusLcd) {
        this.statusLcd = (char)statusLcd;
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
        
    }

    @Override
    public void onDataReceved(String data) {
        int numberInput = Integer.parseInt(data);
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
}

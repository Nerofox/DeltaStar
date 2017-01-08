package fr.deltastar.pigou.model.panel;

import fr.deltastar.pigou.communication.ComArduino;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe pour un composant
 * @author Valentin
 */
public class Component {
    /**
     * Numéro du champ input ou position du champ output dans le code arduino
     */
    protected int idPos;
    /**
     * Indique le type de composant en question, input ou output
     * utilise le ComponentConstants
     */
    protected int type;
    /**
     * Etat du composant, allumé ou éteint voir clignotant pour une led
     * utilise le ComponentConstants
     */
    protected int status;
    /**
     * Com arduino associé au composant
     */
    protected ComArduino comArduino;
    /**
     * Nom du composant
     */
    protected String title;

    public Component(int type, String title) {
        this.type = type;
        this.title = title;
    }
    
    /**
     * Effectue une transition du output de éteint a allumé
     * avec clignotement
     * @param delay : spécifie le délai de clignotement avant mise sur on
     */
    public void switchTransitionToOn(long delay) {
        if (this.type == ComponentConstants.OUTPUT) {
            this.switchBlink();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(delay);
                        switchOn();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Component.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        }
    }
    
    /**
     * Effectue une transition du output de allumé a éteint
     * avec clignotement
     * @param delay : spécifie le délai de clignotement avant mise sur off
     */
    public void switchTransitionToOff(long delay) {
        if (this.type == ComponentConstants.OUTPUT) {
            this.switchBlink();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(delay);
                        switchOff();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Component.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        }
    };
    
    /**
     * Bascule la sortie état allumé si possible
     */
    public void switchOn() {
        if (this.type == ComponentConstants.OUTPUT) {
            this.comArduino.setValueLed(this.idPos, ComponentConstants.ON);
            this.status = ComponentConstants.ON;
        }
    }
    
    /**
     * Bascule la sortie en état éteinte si possible
     */
    public void switchOff() {
        if (this.type == ComponentConstants.OUTPUT) {
            this.comArduino.setValueLed(this.idPos, ComponentConstants.OFF);
            this.status = ComponentConstants.OFF;
        }
    }
    
    /**
     * Bascule la sortie en état clignotant si possible
     */
    public void switchBlink() {
        if (this.type == ComponentConstants.OUTPUT) {
            this.comArduino.setValueLed(this.idPos, ComponentConstants.BLINK);
            this.status = ComponentConstants.BLINK;
        }
    }

    public int getIdPos() {
        return idPos;
    }

    public void setIdPos(int idPos) {
        this.idPos = idPos;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ComArduino getComArduino() {
        return comArduino;
    }

    public void setComArduino(ComArduino comArduino) {
        this.comArduino = comArduino;
    }

    @Override
    public String toString() {
        return this.title;
    }
}

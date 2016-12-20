package fr.deltastar.pigou.model.panel;

import fr.deltastar.pigou.communication.ComArduino;
import java.util.List;

/**
 *
 * @author Valentin
 */
public abstract class BaseSystem {
    /**
     * Com arduino pour la liaison avec l'écran lcd
     */
    protected ComArduino arduinoComLcd;
    /**
     * Etat du champ lcd
     */
    protected int statusLcd;
    protected int argOneLcd;
    protected int argTwoLcd;
    
    /**
     * Etat du système si celui est disponible ou pas
     */
    protected boolean isOnline;

    public ComArduino getArduinoComLcd() {
        return arduinoComLcd;
    }

    public void setArduinoComLcd(ComArduino arduinoComLcd) {
        this.arduinoComLcd = arduinoComLcd;
    }

    public int getStatusLcd() {
        return statusLcd;
    }

    public void setStatusLcd(int statusLcd) {
        this.statusLcd = statusLcd;
    }

    public int getArgOneLcd() {
        return argOneLcd;
    }

    public void setArgOneLcd(int argOneLcd) {
        this.argOneLcd = argOneLcd;
    }

    public int getArgTwoLcd() {
        return argTwoLcd;
    }

    public void setArgTwoLcd(int argTwoLcd) {
        this.argTwoLcd = argTwoLcd;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }
    
    /**
     * Récupère la liste des modules du system, uniquement les configurables
     * @return 
     */
    public abstract List<ModuleInterface> getListModuleInterface();
    /**
     * Ce qui se passe lors de l'activation du systeme
     */
    public void onActivateSystem() {
        this.isOnline = true;
    }
    /**
     * Ce qui se passe lors de la désactivation du systeme
     */
    public void onDeactivateSystem() {
        this.isOnline = false;
    }
}

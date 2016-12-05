package fr.deltastar.pigou.model.panel;

import java.util.List;

/**
 *
 * @author Valentin
 */
public abstract class BaseSystem {
    /**
     * Port arduino pour la liaison avec l'écran lcd
     */
    protected String arduinoComLcd;
    /**
     * Etat du champ lcd
     */
    protected int statusLcd;

    public String getArduinoComLcd() {
        return arduinoComLcd;
    }

    public void setArduinoComLcd(String arduinoComLcd) {
        this.arduinoComLcd = arduinoComLcd;
    }

    public int getStatusLcd() {
        return statusLcd;
    }

    public void setStatusLcd(int statusLcd) {
        this.statusLcd = statusLcd;
    }
    /**
     * Récupère la liste des modules du system, uniquement les configurables
     * @return 
     */
    public abstract List<ModuleInterface> getListModuleInterface();
    /**
     * Ce qui se passe lors de l'activation du systeme
     */
    public abstract void onActivateSystem();
    /**
     * Ce qui se passe lors de la désactivation du systeme
     */
    public abstract void onDeactivateSystem();
}

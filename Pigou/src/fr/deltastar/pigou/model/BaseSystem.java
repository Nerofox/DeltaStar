package fr.deltastar.pigou.model;

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
    
    /**
     * Charge l'ensemble des objets module du system
     */
    public abstract void loadModule();
    /**
     * Ce qui se passe lors de l'activation du systeme
     */
    public abstract void onActivateSystem();
    /**
     * Ce qui se passe lors de la désactivation du systeme
     */
    public abstract void onDeactivateSystem();
}

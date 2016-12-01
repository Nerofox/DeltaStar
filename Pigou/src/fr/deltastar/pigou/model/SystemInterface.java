package fr.deltastar.pigou.model;

/**
 *
 * @author Valentin
 */
public interface SystemInterface {
    /**
     * Charge l'ensemble des objets module du system
     */
    public void loadModule();
    /**
     * Ce qui se passe lors de l'activation du systeme
     */
    public void onActivateSystem();
    /**
     * Ce qui se passe lors de la désactivation du systeme
     */
    public void onDeactivateSystem();
}

package fr.deltastar.pigou.model;

/**
 *
 * @author Valentin
 */
public interface ComponentInputInterface {
    /**
     * Ce qui se passe si l'input est appuyé ou préssé sur on
     */
    public void onInputOn();
    /**
     * Ce qui se passe si l'input de type switch passe sur off
     */
    public void onInputOff();
}

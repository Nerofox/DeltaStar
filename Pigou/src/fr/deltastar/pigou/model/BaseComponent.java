package fr.deltastar.pigou.model;

/**
 * Classe de base pout l'ensemble des modules
 * @author Valentin
 */
public abstract class BaseComponent {
    /**
     * Numéro du champ input
     */
    protected int idPos;
    /**
     * Etat du composant, allumé ou éteint voir clignotant pour une led
     * utilise le ComponentConstants
     */
    protected int status;
    /**
     * Numéro de com arduino associé au composant
     * utilise le ArduinoPortConstants
     */
    protected String arduinoCom;

    public int getIdPos() {
        return idPos;
    }

    public void setIdPos(int idPos) {
        this.idPos = idPos;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getArduinoCom() {
        return arduinoCom;
    }

    public void setArduinoCom(String arduinoCom) {
        this.arduinoCom = arduinoCom;
    }
}

package fr.deltastar.pigou.model;

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

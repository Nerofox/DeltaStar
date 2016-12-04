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
    /**
     * Nom du composant
     */
    protected String title;

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

    public String getArduinoCom() {
        return arduinoCom;
    }

    public void setArduinoCom(String arduinoCom) {
        this.arduinoCom = arduinoCom;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

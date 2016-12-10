package fr.deltastar.pigou.model.panel;

import fr.deltastar.pigou.communication.ComArduino;

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

package fr.deltastar.pigou.model.panel;

/**
 *
 * @author Valentin
 */
public class ComponentConfig {
    private int idPos;
    private int status;
    private String arduinoCom;

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

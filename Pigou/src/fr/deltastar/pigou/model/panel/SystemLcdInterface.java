package fr.deltastar.pigou.model.panel;

/**
 * Utilisé par les systèmes qui utilise un écran LCD
 * @author Valentin
 */
public interface SystemLcdInterface {
    public int getArgOne();
    public void setArgOne(int argOne);
    public int getArgTwo();
    public void setArgTwo(int argTwo);
}

package fr.deltastar.pigou.communication;

/**
 *
 * @author valentin
 */
public interface ComClientInterface {
    public void connect(String port, ListenerComInterface listenerCom, String arduinoId);
    public void listenInput();
    public void sendData(String data);
    public void closeConnection();
    public boolean isConnect();
}

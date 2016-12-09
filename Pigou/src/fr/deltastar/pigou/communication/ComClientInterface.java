package fr.deltastar.pigou.communication;

/**
 *
 * @author valentin
 */
public interface ComClientInterface {
    public void connect(String port, ListenerComInterface listenerCom);
    public void listenInput();
    public void sendData(String data);
    public void closeConnection();
}

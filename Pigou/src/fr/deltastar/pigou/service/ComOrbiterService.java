package fr.deltastar.pigou.service;

import fr.deltastar.pigou.communication.ListenerComInterface;
import fr.deltastar.pigou.communication.SocketClient;
import fr.deltastar.pigou.constant.Constants;

/**
 * Service de gestion de la communication avec Orbiter via un script lua
 * @author Valentin
 */
public class ComOrbiterService implements ListenerComInterface {
    
    private SocketClient comOrbiter;

    public ComOrbiterService() {
        this.comOrbiter = new SocketClient();
    }
    
    public void start() {
        this.comOrbiter.connect(Constants.IP_ORBITER_SOCKET, Constants.PORT_ORBITER_SOCKET, this, "lua");
    }

    @Override
    public void onDataReceved(String data) {
        
    }

    @Override
    public void onConnect(String arduinoId) {
        
    }
}

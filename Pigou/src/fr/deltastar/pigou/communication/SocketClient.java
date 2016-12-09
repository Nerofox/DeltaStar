package fr.deltastar.pigou.communication;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.service.ServicePigou;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gestion simple d'un client socket
 * @author Valentin
 */
public class SocketClient implements ComClientInterface {
    private int port;
    
    private Thread listenerInput;
    private Socket socket;
    private BufferedReader in;
    
    private ListenerComInterface listenerCom;

    /**
     * initialise la connection
     * @param port
     * @param listenerCom 
     */
    @Override
    public void connect(String port, ListenerComInterface listenerCom) {
        this.port = Integer.parseInt(port);
        this.listenerCom = listenerCom;
        try {
            this.socket = new Socket(Constants.VIRTUAL_IP, this.port);
            System.out.println("Connection established on " + this.port);
        } catch (IOException ex) {
            ServicePigou.getMessageService().displayFatalError("Connection error on " + this.port);
        }
    }
    
    /**
     * Lance un thread qui va ecouter les données potentiel entrante
     * retourne les données si existantes dans le ListenerCom
     */
    @Override
    public void listenInput() {
        this.listenerInput = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String msg = "";
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while(true) {
                        msg = in.readLine();
                        if (msg != null && !"".equals(msg.trim())) {
                            listenerCom.onDataReceved(msg);
                            System.out.println("Data receved : " + msg);
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("Error listen input on " + port);
                }
            }
        });
        this.listenerInput.start();
    }
    
    /**
     * Envoi des données au serveur socket
     * @param data 
     */
    @Override
    public void sendData(String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PrintWriter out = null;
                try {
                    out = new PrintWriter(socket.getOutputStream());
                    out.write(data);
                    out.flush();
                } catch (IOException ex) {
                    System.out.println("Error send output on " + port);
                } finally {
                    out.close();
                }
            }
        }).start();
    }
    
    /**
     * Ferme la connection
     */
    @Override
    public void closeConnection() {
        try {
            this.listenerInput.stop();
            this.in.close();
            this.socket.close();
            System.out.println("Close connection on " + Constants.VIRTUAL_IP + ":" + this.port);
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package fr.deltastar.pigou.communication;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.controller.StatusComViewController;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gestion d'un client en mode série RS232
 * @author valentin
 */
public class SerialClient implements ComClientInterface {

    private int port;
    private String nameCom;
    
    private Thread listenerInput;
    private SerialPort serialPort;
    private CommPortIdentifier portIdentifier;
    private BufferedReader in;
    private PrintWriter out;
    
    private ListenerComInterface listenerCom;
    
    /**
     * initialise la connection
     * @param ip
     * @param port
     * @param listenerCom 
     * @param nameCom
     */
    @Override
    public void connect(String ip, String port, ListenerComInterface listenerCom, String nameCom) {
        this.port = Integer.parseInt(port);
        this.listenerCom = listenerCom;
        this.nameCom = nameCom;
        try {
            if (StatusComViewController.getInstance() != null)
                StatusComViewController.getInstance().setStatusInProgress(this.nameCom);
            
            //preparation du port com, si deja ouvert on s'arrète
            this.portIdentifier = CommPortIdentifier.getPortIdentifier(port);
            if (this.portIdentifier.isCurrentlyOwned()) {
                throw new Exception("Is already connect");
            }
            //connexion au port com et configuration
            this.serialPort = (SerialPort)this.portIdentifier.open(nameCom, 2000);
            this.serialPort.setSerialPortParams(Constants.SERIALCOM_DEBIT_COMMUNICATION, SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
            
            System.out.println("Connection established on " + this.port);
            if (StatusComViewController.getInstance() != null)
                StatusComViewController.getInstance().setStatusOk(this.nameCom);
            this.listenerCom.onConnect(this.nameCom);
        } catch (Exception ex) {
            System.out.println("Connection error on " + this.port + ", msg error : " + ex.getMessage());
            if (StatusComViewController.getInstance() != null)
                StatusComViewController.getInstance().setStatusKo(this.nameCom, "Connection error on " + this.port);
        }
    }

    /**
     * Lance un thread qui va ecouter les données potentiel entrante
     * retourne les données si existantes dans le ListenerCom
     */
    @Override
    public void listenInput() {
        if (this.serialPort == null)
            return;
        this.listenerInput = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String msg = "";
                    in = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                    while(true) {
                        msg = in.readLine();
                        if (msg != null && !"".equals(msg.trim())) {
                            listenerCom.onDataReceved(msg);
                            if (StatusComViewController.getInstance() != null)
                                StatusComViewController.getInstance().addDataInput(msg);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Error listen input on " + port);
                    if (StatusComViewController.getInstance() != null)
                        StatusComViewController.getInstance().setStatusKo(nameCom, "Connection error on " + port);
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
                try {
                    if (out == null)
                        out = new PrintWriter(serialPort.getOutputStream());
                    out.write(data);
                    System.out.println("Data send on " + nameCom + " : " + data);
                    //out.flush();
                } catch (Exception ex) {
                    System.out.println("Error send output on " + port);
                    if (StatusComViewController.getInstance() != null)
                        StatusComViewController.getInstance().setStatusKo(nameCom, "Send error on " + port);
                    if (out != null)
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
            if (this.listenerInput != null)
                this.listenerInput.stop();
            if (this.out != null)
                this.out.close();
            if (this.serialPort != null) {
                this.serialPort.close();
                this.serialPort = null;
            }
            if (this.in != null)
                this.in.close();
            System.out.println("Close connection on " + Constants.VIRTUAL_IP + ":" + this.port);
            if (StatusComViewController.getInstance() != null)
                StatusComViewController.getInstance().setStatusKo(this.nameCom, "Close connection on " + Constants.VIRTUAL_IP + ":" + this.port);
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isConnect() {
        if (this.portIdentifier == null || this.serialPort == null)
            return false;
        return this.portIdentifier.isCurrentlyOwned();
    }
}

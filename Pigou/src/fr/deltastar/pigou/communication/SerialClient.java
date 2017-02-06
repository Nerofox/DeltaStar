package fr.deltastar.pigou.communication;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.controller.StatusComViewController;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gestion d'un client en mode série RS232
 * @author valentin
 */
public class SerialClient implements ComClientInterface, SerialPortEventListener {

    private String port;
    private String nameCom;

    private SerialPort serialPort;
    private CommPortIdentifier portIdentifier;
    private BufferedReader in;
    private PrintWriter out;
    
    private long lastTimeInputPressed;
    
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
        this.port = port;
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
            this.lastTimeInputPressed = System.currentTimeMillis();
            this.serialPort.setSerialPortParams(Constants.SERIALCOM_DEBIT_COMMUNICATION, SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
            this.in = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            
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
    
    @Override
    public void listenInput() {
        try {
            //ajout evenement pour la reception de donnée
            this.serialPort.addEventListener(this);
            this.serialPort.notifyOnDataAvailable(true);
        } catch (TooManyListenersException ex) {
            Logger.getLogger(SerialClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public synchronized void serialEvent(SerialPortEvent spe) {
        if (spe.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                char[] result = new char[2];
                String input = "";
                //pour palier au probleme de un chiffre a la fois, on attend que
                //la liaison série emmet 2 caracteres renseigné ex: 02 ou 12
                do {
                    this.in.read(result);
                    input = input + String.valueOf(result[0]) + String.valueOf(result[1]);
                } while (input.trim().length() < 2);
                //filtrage des espaces
                char[] arrayInput = input.toCharArray();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < arrayInput.length; i++) {
                    if ((int)arrayInput[i] != 0)
                        sb.append(arrayInput[i]);
                }
                input = sb.toString();
                
                //pour pallier au problème de la carte qui envoi une trop grande quantité d'infos au départ
                //si la dernière action est trop rapide elle ne peut provenir d'un humain
                if ((System.currentTimeMillis() - this.lastTimeInputPressed) < 250) {
                    System.out.println("Stopping input too fast");
                    this.lastTimeInputPressed = System.currentTimeMillis();
                    return;
                }
                this.lastTimeInputPressed = System.currentTimeMillis();

                //envoi de l'infos
                System.out.println("Data receved : " + input);
                listenerCom.onDataReceved(input);
                if (StatusComViewController.getInstance() != null)
                    StatusComViewController.getInstance().addDataInput(input);
            } catch (Exception e) {
                if (e instanceof IOException) {
                    System.out.println("Data input ignored");
                    return;
                }
                e.printStackTrace();
                System.out.println("Error listen input on " + port);
                if (StatusComViewController.getInstance() != null)
                    StatusComViewController.getInstance().setStatusKo(nameCom, "Connection error on " + port);
            }
        }
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
                    out.flush();
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
            if (this.out != null)
                this.out.close();
            if (this.serialPort != null) {
                this.serialPort.removeEventListener();
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

package fr.deltastar.pigou.service;

import fr.deltastar.pigou.communication.ComArduino;
import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.constant.ArduinoPortConstants;
import fr.deltastar.pigou.communication.ListenerComInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de gestion de la communication avec arduino
 * @author Valentin
 */
public class ComArduinoService {
    
    private Thread backRunSynchroArduino;
    
    private ComArduino arduinoA;
    private ComArduino arduinoB;
    private ComArduino arduinoC;
    
    private String portComA;
    private String portComB;
    private String portComC;

    public ComArduinoService() {
        this.arduinoA = new ComArduino();
        this.arduinoB = new ComArduino();
        this.arduinoC = new ComArduino();
    }
    
    /**
     * Lance les communications avec l'arduino avec le mode
     * d'écoute standard pour l'arduino C
     */
    public void launch() {
        this.launch(this.arduinoC);
    }
    
    /**
     * Lance les communications avec l'arduino avec
     * un écouteur d'entré personnalisé
     * @param lci 
     */
    public void launch(ListenerComInterface lci) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String portA;
                String portB;
                String portC;
                if (Constants.MODE_VIRTUAL) {
                    portA = Constants.VIRTUAL_PORT_A;
                    portB = Constants.VIRTUAL_PORT_B;
                    portC = Constants.VIRTUAL_PORT_C;
                } else {
                    portA = portComA;
                    portB = portComB;
                    portC = portComC;
                }
                arduinoA.start(portA, ComponentConstants.OUTPUT, 13, 4, 3, lci, ArduinoPortConstants.ARDUINO_A);
                arduinoB.start(portB, ComponentConstants.OUTPUT, 15, 3, 3, lci, ArduinoPortConstants.ARDUINO_B);
                arduinoC.start(portC, ComponentConstants.INPUT, 14, 3, 3, lci, ArduinoPortConstants.ARDUINO_C);
            }
        }).start();
    }
    
    /**
     * Démarre la synchronisation entre les données PIGOU et les arduinos
     */
    public void startRefreshArduino() {
        if (this.backRunSynchroArduino == null) {
            this.backRunSynchroArduino = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            Thread.sleep(500);
                            if (arduinoA.isConnect())
                                arduinoA.sendOutput();
                            if (arduinoB.isConnect())
                                arduinoB.sendOutput();
                            if (arduinoC.isConnect())
                                arduinoC.sendOutput();
                        } catch (InterruptedException ex) {
                            System.err.println("Error synch");
                        }
                    }
                }
            });
        }
        this.backRunSynchroArduino.start();
    }
    
    /**
     * Stop la synchronisation entre les données PIGOU et les arduinos
     */
    public void stopRefreshArduino() {
        if (this.backRunSynchroArduino.isAlive())
            this.backRunSynchroArduino.stop();
    }
    
    /**
     * Retourne si les arduinos sont tous connectés
     */
    public boolean isFullConnected() {
        boolean result = false;
        if (this.arduinoA.isConnect() && this.arduinoB.isConnect() && this.arduinoC.isConnect())
            result = true;
        return result;
    }
    
    /**
     * Stop la communication avec les arduinos
     */
    public void stop() {
        this.arduinoA.closeConnection();
        this.arduinoB.closeConnection();
        this.arduinoC.closeConnection();
    }
    
    public List<ComArduino> getAllArduino() {
        List<ComArduino> l = new ArrayList<ComArduino>();
        l.add(this.getArduinoA());
        l.add(this.getArduinoB());
        l.add(this.getArduinoC());
        return l;
    }

    public ComArduino getArduinoA() {
        return arduinoA;
    }

    public ComArduino getArduinoB() {
        return arduinoB;
    }

    public ComArduino getArduinoC() {
        return arduinoC;
    }

    public String getPortComA() {
        return portComA;
    }

    public String getPortComB() {
        return portComB;
    }

    public String getPortComC() {
        return portComC;
    }

    public void setPortComA(String portComA) {
        this.portComA = portComA;
    }

    public void setPortComB(String portComB) {
        this.portComB = portComB;
    }

    public void setPortComC(String portComC) {
        this.portComC = portComC;
    }
}

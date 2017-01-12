package fr.deltastar.pigou.service;

import fr.deltastar.pigou.communication.ListenerComInterface;
import fr.deltastar.pigou.communication.SocketServer;
import fr.deltastar.pigou.constant.CmdOrbiterConstants;
import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.system.EngineSystem;
import fr.deltastar.pigou.utils.FileManager;
import java.io.File;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * Service de gestion de la communication avec Orbiter via un script lua
 * @author Valentin
 */
public class OrbiterService implements ListenerComInterface {
    
    private SocketServer serverSocket;
    
    /**
     * Altitude du vaisseau actuellement
     */
    private long altitude;

    public OrbiterService() {
        this.serverSocket = new SocketServer(this);
    }
    
    public boolean isOrbiterPathExist() {
        return new File(Constants.FILENAME_CONFIG_ORBITER).exists();
    }
    
    public void choosePathOrbiter(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        directoryChooser.setTitle(Constants.TITLE_ORBITER_DIRECTORY_CHOOSER);
        if(selectedDirectory != null){
            FileManager.save(Constants.FILENAME_CONFIG_ORBITER, new String[]{selectedDirectory.getAbsolutePath()});
            ServicePigou.getMessageService().displayInfo(Constants.MSG_CONFIG_ORBITER_SUCCESS);
        }
    }
    
    public void launch() {
        this.serverSocket.launch(Integer.parseInt(Constants.PORT_ORBITER_SOCKET));
    }
    
    public void stop() {
        this.serverSocket.close();
    }
    
    public void sendCmdToOrbiter(String mode, String option) {
        if (this.serverSocket != null)
            this.serverSocket.send(mode + Constants.DELIMITER_CMD_ORBITER + option);
    }
    
    /**
     * Retourne si le vaisseau est posé
     * on le considère comme atterit si il se trouve a moins de 200 mètres
     * @return 
     */
    public boolean isLanding() {
        return (this.altitude < Constants.ALTITUDE_MINIMAL_FORLANDING);
    }
    
    /**
     * Retourne vrai si le pilote peut survivre sans les supports de vie
     * cela dépend de l'altitude du vaisseau ou il se trouve et enfin
     * si les sas sont ouvert
     * @return 
     */
    public boolean isPossibleToLive() {
        return (this.altitude < Constants.ALTITUDE_MINIMAL_FORLIFE && DeltaStar.getAirlockSystem().getInnerDoorModule().isIsOpen()
                && DeltaStar.getAirlockSystem().getOuterDoorModule().isOpen());
    }

    @Override
    public void onDataReceved(String data) {
        //RECUPERATION VALEURS RECU PAR ORBITER
        String[] dataSplit = data.split(Constants.DELIMITER_CMD_ORBITER);
        this.altitude = Long.parseLong(dataSplit[0]);
        int fuelMain = Integer.parseInt(dataSplit[1]);
        int fuelRcs = Integer.parseInt(dataSplit[2]);
        
        //OPERATION AVEC MAJ DES VALEURS RECU PAR ORBITER
        
        //maj carburant si système en place
        EngineSystem es = DeltaStar.getEngineSystem();
        if (es.isOnline()) {
            es.setArgOne(fuelMain);
            es.setArgTwo(fuelRcs);
        }
        
    }

    @Override
    public void onConnect(String arduinoId) {
        this.serverSocket.listen();
        
        //CONFIG PAR DEFAUT AU DEMARRAGE DORBITER
        
        //les portes des sas sont ouverte par défaut
        DeltaStar.getAirlockSystem().getInnerDoorModule().getLedGreen().switchOn();
        DeltaStar.getAirlockSystem().getOuterDoorModule().getLedGreen().switchOn();
        //le train d'aterissage est ouvert par défaut
        DeltaStar.getAirlockSystem().getGearModule().getLedGreen().switchOn();
        
        //blocage carburant car les moteurs ne tourne pas !
        this.sendCmdToOrbiter(CmdOrbiterConstants.MODE_FUELLOCK, CmdOrbiterConstants.OPTION_MAIN);
        this.sendCmdToOrbiter(CmdOrbiterConstants.MODE_FUELLOCK, CmdOrbiterConstants.OPTION_RCS);
    }
}

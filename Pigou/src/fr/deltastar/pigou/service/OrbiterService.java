package fr.deltastar.pigou.service;

import fr.deltastar.pigou.communication.ListenerComInterface;
import fr.deltastar.pigou.communication.SocketServer;
import fr.deltastar.pigou.constant.Constants;
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
    private String pathOrbiter;

    public OrbiterService() {
        this.serverSocket = new SocketServer(this);
        //si fichier de conf présent on récupère le path d'Orbiter
        if (this.isOrbiterPathExist())
            this.pathOrbiter = FileManager.open(Constants.FILENAME_CONFIG_ORBITER)[0];
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
            this.pathOrbiter = selectedDirectory.getAbsolutePath();
            ServicePigou.getMessageService().displayInfo(Constants.MSG_CONFIG_ORBITER_SUCCESS);
        }
    }
    
    public void launch() {
        if (this.pathOrbiter == null) {
            ServicePigou.getMessageService().displayFatalError(Constants.MSG_CONFIG_ORBITER_BAD);
        } else {
            this.serverSocket.launch(Integer.parseInt(Constants.PORT_ORBITER_SOCKET));
            /*try {
                //lancement du scénario orbiter
                //plantage d'Orbiter en cas de lancement direct on passe par un .bat
                String[] bat = new String[2];
                bat[0] = "cd " + this.pathOrbiter;
                bat[1] = "orbiter.exe -s " + this.pathOrbiter + Constants.PATHS_ORBITER_SCENARIO_PIGOU + Constants.ORBITER_MISSION_ONE;
                FileManager.save(this.pathOrbiter + "\\startOrbiter.bat", bat);
                //lancement du bat
                Runtime rt = Runtime.getRuntime();
                Process pr = rt.exec(this.pathOrbiter + "\\startOrbiter");
            } catch (IOException ex) {
                Logger.getLogger(OrbiterService.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
    }
    
    public void stop() {
        this.serverSocket.close();
    }
    
    public void sendCmdToOrbiter(String mode, String option) {
        if (this.serverSocket != null)
            this.serverSocket.send(mode + Constants.DELIMITER_CMD_ORBITER + option);
    }

    @Override
    public void onDataReceved(String data) {
        
    }

    @Override
    public void onConnect(String arduinoId) {
        this.serverSocket.listen();
    }
}

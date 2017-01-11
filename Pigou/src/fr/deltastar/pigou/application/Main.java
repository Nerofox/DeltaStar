package fr.deltastar.pigou.application;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.constant.ListView;
import fr.deltastar.pigou.constant.RessourcesConstants;
import fr.deltastar.pigou.model.constant.ArduinoPortConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.service.ServicePigou;
import fr.deltastar.pigou.utils.FileManager;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Valentin
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(ListView.WELCOME));
        Scene scene = new Scene(root, Constants.SIZE_WIDTH_APPLICATION, Constants.SIZE_HEIGHT_APPLICATION);
        primaryStage.getIcons().add(new Image(RessourcesConstants.ICON));
        primaryStage.setTitle(Constants.TITLE_APPLICATION);
        primaryStage.setScene(scene);
        
        Parent rootStatusCom = FXMLLoader.load(getClass().getResource(ListView.STATUS_COM));
        Scene sceneStatusCom = new Scene(rootStatusCom);
        Stage stageStatusCom = new Stage();
        stageStatusCom.setTitle(Constants.TITLE_APPLICATION);
        stageStatusCom.setScene(sceneStatusCom);
        
        //mise en place de la fenêtre du status des connections en bas a droite de l'écran
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stageStatusCom.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 550);
        stageStatusCom.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 250);
        stageStatusCom.getIcons().add(new Image(RessourcesConstants.ICON));
        
        //chargement de la config du panneau DeltaStar
        if (new File(Constants.FILENAME_CONFIG).exists()) {
            List<Component> listAllComponents = DeltaStar.getListComponents();
            String[] conf = FileManager.open(Constants.FILENAME_CONFIG);
            String [] confSplit;
            for (int i = 0; i < conf.length; i++) {
                confSplit = conf[i].split(Constants.FILENAME_DELIMITER);
                if (confSplit[0].equals(ArduinoPortConstants.ARDUINO_A)) {
                    listAllComponents.get(i).setComArduino(ServicePigou.getComArduinoService().getArduinoA());
                    listAllComponents.get(i).getComArduino().setArduinoId(ArduinoPortConstants.ARDUINO_A);
                } else if (confSplit[0].equals(ArduinoPortConstants.ARDUINO_B)) {
                    listAllComponents.get(i).setComArduino(ServicePigou.getComArduinoService().getArduinoB());
                    listAllComponents.get(i).getComArduino().setArduinoId(ArduinoPortConstants.ARDUINO_B);
                } else if (confSplit[0].equals(ArduinoPortConstants.ARDUINO_C)) {
                    listAllComponents.get(i).setComArduino(ServicePigou.getComArduinoService().getArduinoC());
                    listAllComponents.get(i).getComArduino().setArduinoId(ArduinoPortConstants.ARDUINO_C);
                }
                listAllComponents.get(i).setIdPos(Integer.parseInt(confSplit[1]));
            }
        }
        
        primaryStage.show();
        stageStatusCom.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

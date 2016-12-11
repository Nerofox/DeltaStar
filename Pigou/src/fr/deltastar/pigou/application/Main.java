package fr.deltastar.pigou.application;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.constant.ListView;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        primaryStage.setTitle(Constants.TITLE_APPLICATION);
        primaryStage.setScene(scene);
        
        Parent rootStatusCom = FXMLLoader.load(getClass().getResource(ListView.STATUS_COM));
        Scene sceneStatusCom = new Scene(rootStatusCom);
        Stage stageStatusCom = new Stage();
        stageStatusCom.setTitle(Constants.TITLE_APPLICATION);
        stageStatusCom.setScene(sceneStatusCom);
        //mise en place de la fenêtre en bas a droite de l'écran
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stageStatusCom.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 550);
        stageStatusCom.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 250);
        
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

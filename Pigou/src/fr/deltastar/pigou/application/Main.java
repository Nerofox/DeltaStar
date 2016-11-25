package fr.deltastar.pigou.application;

import fr.deltastar.pigou.constants.Constants;
import fr.deltastar.pigou.constants.ListView;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
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
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

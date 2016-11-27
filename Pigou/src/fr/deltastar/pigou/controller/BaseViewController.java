package fr.deltastar.pigou.controller;

import fr.deltastar.pigou.constants.Constants;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Valentin
 */
public abstract class BaseViewController {
    @FXML private AnchorPane ap;
    @FXML protected Button btnBack;
    
    protected void navigate(String view) {
        try {
            Stage stage = (Stage)ap.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(view));
            Scene scene = new Scene(root, Constants.SIZE_WIDTH_APPLICATION, Constants.SIZE_HEIGHT_APPLICATION);
            stage.setTitle(Constants.TITLE_APPLICATION);
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(BaseViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected abstract void btnBackClick();
}

package fr.deltastar.pigou.service;

import fr.deltastar.pigou.constant.Constants;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Procède a l'affichage des messages de l'application sous forme de messagebox
 * @author valentin
 */
public class MessageService {
    
    private Boolean response;
    
    /**
        * Affiche un message d'érreur fatal et donc ferme l'application
        * @param msg 
        */
    public void displayFatalError(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //display msgBox
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error "+ Constants.TITLE_APPLICATION);
                alert.setHeaderText(null);
                alert.setContentText(msg);
                alert.showAndWait();
                //exit the program
                System.exit(8);
            }
        });
    }
    
    /**
        * Affiche un message de type information
        * @param msg
        */
    public void displayInfo(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //display msgBox
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(Constants.TITLE_APPLICATION);
                alert.setHeaderText(null);
                alert.setContentText(msg);
                alert.showAndWait();
            }
        });
    }
    
    /**
        * Non fonctionnel hors thread javafx appellant
        * Affiche un message de confirmation
        * @return bool vrai pour oui, faux pour non
        */
    public Boolean displayYesOrNo(String msg) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Constants.TITLE_APPLICATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        ButtonType buttonYes = new ButtonType("Oui");
        ButtonType buttonNo = new ButtonType("Non");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes) {
            return true;
        } else {
            return false;
        }
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }
}

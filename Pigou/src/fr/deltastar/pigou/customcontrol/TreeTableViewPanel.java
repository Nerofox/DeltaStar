package fr.deltastar.pigou.customcontrol;

import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

/**
 *
 * @author Valentin
 */
public class TreeTableViewPanel extends TreeTableView<String> {

    public TreeTableViewPanel() {
        //define column
        TreeTableColumn<String,String> column = new TreeTableColumn<>("List");
        
        TreeItem<String> root = new TreeItem<>("System");
        for (BaseSystem baseSystem : DeltaStar.getListSystem()) {
            TreeItem<String> rootSystem = new TreeItem<>(baseSystem.toString());
            for (ModuleInterface module : baseSystem.getListModuleInterface()) {
                for (Component component : module.getListComponents()) {
                    
                }
            }
            root.getChildren().add(rootSystem);
        }
        super.setShowRoot(true);
        super.setRoot(root);
    }
}

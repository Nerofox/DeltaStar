package fr.deltastar.pigou.customcontrol;

import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

/**
 *
 * @author Valentin
 */
public class TreeTableViewPanel extends TreeTableView<Object> {
    
    private Component currentComponentSeleted;
    
    public TreeTableViewPanel() {
        super.setPrefWidth(365);
        //création colonne
        TreeTableColumn<Object,String> columnList = new TreeTableColumn<>("List");
        TreeTableColumn<Object,String> columnId = new TreeTableColumn<>("Id");
        columnList.setPrefWidth(300);
        columnId.setPrefWidth(60);
        //type de retour
        columnList.setCellValueFactory((CellDataFeatures<Object, String> p) ->
            new ReadOnlyStringWrapper(p.getValue().getValue().toString()));
        columnId.setCellValueFactory(new Callback<CellDataFeatures<Object, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Object, String> param) {
                ReadOnlyStringWrapper readOnlyStringWrapper = null;
                if (param.getValue().getValue() instanceof Component) {
                    Component c = (Component)param.getValue().getValue();
                    readOnlyStringWrapper = new ReadOnlyStringWrapper(String.valueOf(c.getIdPos()));
                }
                return readOnlyStringWrapper;
            }
        });
        super.getColumns().addAll(columnList, columnId);
        
        //evenement quand on clic sur une ligne
        super.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection.getValue() instanceof Component)
                this.currentComponentSeleted = (Component)newSelection.getValue();
        });
    }
    
    /**
     * Charge la liste des systemes, modules et composants
     * @param componentType filtrer par type de composant INPUT ou OUTPUT
     *                      utilise le ComponentConstants
     */
    public void loadTree(int componentType) {
        //création du noeud principal
        TreeItem<Object> root = new TreeItem<>("DeltaStar");
        root.setExpanded(true);
        //ajout des systmèmes, module et composant
        for (BaseSystem baseSystem : DeltaStar.getListSystem()) {
            TreeItem<Object> rootSystem = new TreeItem<>(baseSystem);
            for (ModuleInterface module : baseSystem.getListModuleInterface()) {
                TreeItem<Object> rootModules = new TreeItem<>(module);
                for (Component component : module.getListComponents()) {
                    if (component.getType()== componentType)
                        rootModules.getChildren().add(new TreeItem<>(component));
                }
                rootSystem.getChildren().add(rootModules);
            }
            root.getChildren().add(rootSystem);
        }
        //configuration final du treeview
        super.setShowRoot(true);
        super.setRoot(root);
    }
    
    public Component getSelectedComponent() {
        return this.currentComponentSeleted;
    }
    
    public void setPosForSelectedComponent(int pos) {
        Component c = this.getSelectedComponent();
        c.setIdPos(pos);
        super.refresh();
    }
}

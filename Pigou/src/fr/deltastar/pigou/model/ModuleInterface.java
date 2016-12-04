package fr.deltastar.pigou.model;

import java.util.List;

/**
 *
 * @author Valentin
 */
public interface ModuleInterface {
    /**
     * Récupère la liste des composants du module
     * @return List
     */
    public List<Component> getListComponents();
    /**
     * Action exécutée si un input est activée ou désactivée
     * @param activate 
     */
    public void onAction(boolean activate);
}

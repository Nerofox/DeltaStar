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
}

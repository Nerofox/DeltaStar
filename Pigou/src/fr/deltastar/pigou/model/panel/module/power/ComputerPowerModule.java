package fr.deltastar.pigou.model.panel.module.power;

import fr.deltastar.pigou.model.Component;
import fr.deltastar.pigou.model.ModuleInterface;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class ComputerPowerModule implements ModuleInterface {

    @Override
    public List<Component> getListComponents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onAction(boolean activate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Computer";
    }
}

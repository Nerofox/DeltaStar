package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.panel.module.warning.WarningModule;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class WarningSystem extends BaseSystem {

    private WarningModule warningModule;
    
    public WarningSystem() {
        this.warningModule = new WarningModule();
    }

    public WarningModule getWarningModule() {
        return warningModule;
    }

    @Override
    public void onActivateSystem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDeactivateSystem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return "Warning";
    }

    @Override
    public List<ModuleInterface> getListModuleInterface() {
        List<ModuleInterface> mi = new ArrayList<>();
        mi.add(this.warningModule);
        return mi;
    }
}

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
        super.isOnline = true; //ce système est toujours disponible
    }

    public WarningModule getWarningModule() {
        return warningModule;
    }

    @Override
    public void onActivateSystem() {}

    @Override
    public void onDeactivateSystem() {}
    
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

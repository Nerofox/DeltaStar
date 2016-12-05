package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.panel.module.hud.HudModule;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class HudSystem extends BaseSystem {

    private HudModule hudModule;
    
    public HudSystem() {
        this.hudModule = new HudModule();
    }

    public HudModule getHudModule() {
        return hudModule;
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
        return "Hud";
    }

    @Override
    public List<ModuleInterface> getListModuleInterface() {
        List<ModuleInterface> mi = new ArrayList<>();
        mi.add(this.hudModule);
        return mi;
    }
}

package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.DeltaStar;
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
        super.setIsOnline(true);
        DeltaStar.getPowerSystem().onAuxSystem(true);
        DeltaStar.getPowerSystem().getHudPowerModule().getLedGreen().switchOn();
    }

    @Override
    public void onDeactivateSystem() {
        super.setIsOnline(false);
        DeltaStar.getPowerSystem().onAuxSystem(false);
        DeltaStar.getPowerSystem().getHudPowerModule().getLedGreen().switchOff();
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

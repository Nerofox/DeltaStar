package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.model.BaseSystem;
import fr.deltastar.pigou.model.ModuleInterface;
import fr.deltastar.pigou.model.panel.module.computer.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class ComputerSystem extends BaseSystem {

    private AutoPilotHoldAltitudeModule autoPilotHoldAltitudeModule;
    private AutoPilotKillRotModule autoPilotKillRotModule;
    private AutoPilotLhModule autoPilotLhModule;
    private AutoPilotOrbitMModule autoPilotOrbitMModule;
    private AutoPilotOrbitPModule autoPilotOrbitPModule;
    private AutoPilotProgradeModule autoPilotProgradeModule;
    private AutoPilotRetrogradeModule autoPilotRetrogradeModule;
    private MonitorModule monitorModule;
    private RcsCtrlModule rcsCtrlModule;

    public ComputerSystem() {
        this.autoPilotHoldAltitudeModule = new AutoPilotHoldAltitudeModule();
        this.autoPilotKillRotModule = new AutoPilotKillRotModule();
        this.autoPilotLhModule = new AutoPilotLhModule();
        this.autoPilotOrbitMModule = new AutoPilotOrbitMModule();
        this.autoPilotOrbitPModule = new AutoPilotOrbitPModule();
        this.autoPilotProgradeModule = new AutoPilotProgradeModule();
        this.autoPilotRetrogradeModule = new AutoPilotRetrogradeModule();
        this.monitorModule = new MonitorModule();
        this.rcsCtrlModule = new RcsCtrlModule();
    }

    public AutoPilotHoldAltitudeModule getAutoPilotHoldAltitudeModule() {
        return autoPilotHoldAltitudeModule;
    }

    public AutoPilotKillRotModule getAutoPilotKillRotModule() {
        return autoPilotKillRotModule;
    }

    public AutoPilotLhModule getAutoPilotLhModule() {
        return autoPilotLhModule;
    }

    public AutoPilotOrbitMModule getAutoPilotOrbitMModule() {
        return autoPilotOrbitMModule;
    }

    public AutoPilotOrbitPModule getAutoPilotOrbitPModule() {
        return autoPilotOrbitPModule;
    }

    public AutoPilotProgradeModule getAutoPilotProgradeModule() {
        return autoPilotProgradeModule;
    }

    public AutoPilotRetrogradeModule getAutoPilotRetrogradeModule() {
        return autoPilotRetrogradeModule;
    }

    public MonitorModule getMonitorModule() {
        return monitorModule;
    }

    public RcsCtrlModule getRcsCtrlModule() {
        return rcsCtrlModule;
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
        return "Computer";
    }

    @Override
    public List<ModuleInterface> getListModuleInterface() {
        List<ModuleInterface> mi = new ArrayList<>();
        mi.add(this.autoPilotHoldAltitudeModule);
        mi.add(this.autoPilotKillRotModule);
        mi.add(this.autoPilotLhModule);
        mi.add(this.autoPilotOrbitMModule);
        mi.add(this.autoPilotOrbitPModule);
        mi.add(this.autoPilotProgradeModule);
        mi.add(this.autoPilotRetrogradeModule);
        mi.add(this.rcsCtrlModule);
        return mi;
    }
    
}

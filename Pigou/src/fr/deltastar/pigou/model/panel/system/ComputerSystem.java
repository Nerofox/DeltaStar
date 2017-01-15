package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.constant.CmdOrbiterConstants;
import fr.deltastar.pigou.constant.SoundConstants;
import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.panel.module.computer.*;
import fr.deltastar.pigou.service.ServicePigou;
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
    
    /**
     * Contiens le dernié auto pilot choisi
     */
    private String lastApUse;
    /**
     * Dernière led de l'auto pilot choisi
     */
    private Component lastLed;

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
    
    /**
     * Démarre ou arrête un auto pilote et desactive le précédent AP si enclenché
     * Effectue un arrêt de kill rotation apres un temps donnée
     * @param nameAp 
     * @param cLed
     */
    public void changeAp(String nameAp, Component cLed) {
        if (this.isOnline) {
            if (nameAp.equals(this.lastApUse))
                this.stopLastAp();
            else {
                if (this.lastLed != null)
                    this.lastLed.switchOff();
                this.lastApUse = new String(nameAp);
                this.lastLed = cLed;
                ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_CMD, this.lastApUse);
                if (this.lastLed != null)
                    this.lastLed.switchOn();
                ServicePigou.getSoundService().play(SoundConstants.AUTOPILOT_ON);
            }
        }
    }
    
    @Override
    public void onActivateSystem() {
        super.setIsOnline(true);
        DeltaStar.getPowerSystem().onAuxSystem(true);
        DeltaStar.getPowerSystem().getComputerPowerModule().getLedGreen().switchOn();
    }

    @Override
    public void onDeactivateSystem() {
        super.setIsOnline(false);
        DeltaStar.getPowerSystem().onAuxSystem(false);
        DeltaStar.getPowerSystem().getComputerPowerModule().getLedGreen().switchOff();
        this.stopLastAp();
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
    
    /**
     * Coupe le dernié auto pilote utilisée
     */
    private void stopLastAp() {
        if (this.lastApUse != null) {
            if (this.lastLed != null)
                this.lastLed.switchOff();
            ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_CMD, this.lastApUse);
            ServicePigou.getSoundService().play(SoundConstants.AUTOPILOT_OFF);
            this.lastApUse = null;
            this.lastLed = null;
        }
    }
}

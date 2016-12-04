package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.model.BaseSystem;
import fr.deltastar.pigou.model.ModuleInterface;
import fr.deltastar.pigou.model.constant.ArduinoPortConstants;
import fr.deltastar.pigou.model.panel.module.power.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class PowerSystem extends BaseSystem {

    private AirlockPowerModule airlockPowerModule;
    private ApuModule apuModule;
    private ComputerPowerModule computerPowerModule;
    private EnginePowerModule enginePowerModule;
    private EpModule epModule;
    private EpuModule epuModule;
    private HudPowerModule hudPowerModule;
    private LifePackPowerModule lifePackPowerModule;
    private StarterModule starterModule;

    public PowerSystem() {
        this.airlockPowerModule = new AirlockPowerModule();
        this.apuModule = new ApuModule();
        this.computerPowerModule = new ComputerPowerModule();
        this.enginePowerModule = new EnginePowerModule();
        this.epModule = new EpModule();
        this.epuModule = new EpuModule();
        this.hudPowerModule = new HudPowerModule();
        this.lifePackPowerModule = new LifePackPowerModule();
        this.starterModule = new StarterModule();
        this.arduinoComLcd = ArduinoPortConstants.ARDUINO_B;
    }

    public AirlockPowerModule getAirlockPowerModule() {
        return airlockPowerModule;
    }

    public ApuModule getApuModule() {
        return apuModule;
    }

    public ComputerPowerModule getComputerPowerModule() {
        return computerPowerModule;
    }

    public EnginePowerModule getEnginePowerModule() {
        return enginePowerModule;
    }

    public EpModule getEpModule() {
        return epModule;
    }

    public EpuModule getEpuModule() {
        return epuModule;
    }

    public HudPowerModule getHudPowerModule() {
        return hudPowerModule;
    }

    public LifePackPowerModule getLifePackPowerModule() {
        return lifePackPowerModule;
    }

    public StarterModule getStarterModule() {
        return starterModule;
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
        return "Power";
    }

    @Override
    public List<ModuleInterface> getListModuleInterface() {
        List<ModuleInterface> mi = new ArrayList<>();
        mi.add(this.airlockPowerModule);
        mi.add(this.apuModule);
        mi.add(this.computerPowerModule);
        mi.add(this.enginePowerModule);
        mi.add(this.epModule);
        mi.add(this.epuModule);
        mi.add(this.hudPowerModule);
        mi.add(this.lifePackPowerModule);
        mi.add(this.starterModule);
        return mi;
    }
}

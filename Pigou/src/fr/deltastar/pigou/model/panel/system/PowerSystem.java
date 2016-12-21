package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.constant.ArduinoPortConstants;
import fr.deltastar.pigou.model.panel.SystemLcdInterface;
import fr.deltastar.pigou.model.panel.module.power.*;
import fr.deltastar.pigou.service.ServicePigou;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class PowerSystem extends BaseSystem implements SystemLcdInterface {

    private AirlockPowerModule airlockPowerModule;
    private ApuModule apuModule;
    private ComputerPowerModule computerPowerModule;
    private EnginePowerModule enginePowerModule;
    private EpModule epModule;
    private EpuModule epuModule;
    private HudPowerModule hudPowerModule;
    private LifePackPowerModule lifePackPowerModule;
    private StarterModule starterModule;
    
    /**
     * Taux en pourcentage de l'utilisation de la puissance electrique
     */
    private int usePower;
    /**
     * Taux en pourcentage de la quantité restante en énergie de l'APU
     */
    private int qtyPower;

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
        this.arduinoComLcd = ServicePigou.getComArduinoService().getArduinoB();;
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
    public int getArgOne() {
        return this.qtyPower;
    }

    @Override
    public void setArgOne(int argOne) {
        this.qtyPower = argOne;
    }

    @Override
    public int getArgTwo() {
        return this.usePower;
    }

    @Override
    public void setArgTwo(int argTwo) {
        this.usePower = argTwo;
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

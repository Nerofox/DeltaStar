package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.model.constant.LcdSystemPowerConstants;
import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.panel.SystemLcdInterface;
import fr.deltastar.pigou.model.panel.module.power.*;
import fr.deltastar.pigou.service.ServicePigou;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private Thread consoPower;
    
    /**
     * Taux en pourcentage de l'utilisation de la puissance electrique
     */
    private int usePower;
    /**
     * Taux en pourcentage de la quantité restante en énergie de l'APU
     */
    private int qtyPower;
    
    /**
     * Consommation suplémentaire du systèmes electrique, selon les systèmes
     * annexes
     */
    private int consoSupp;

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
        this.arduinoComLcd = ServicePigou.getComArduinoService().getArduinoB();
        this.arduinoComLcd.setSli(this);
        this.qtyPower = 100;
        this.usePower = 0;
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
        super.setIsOnline(true);
        this.getArduinoComLcd().setLcdMod(LcdSystemPowerConstants.DISPLAY_STATUS);
        //TODO penser a faire la boucle de décompte pour le courant électrique en fonction de la puissance
        this.consoPower = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(Constants.INTERVAL_POWER);
                        qtyPower = qtyPower - (Constants.NB_CONSOMMATION_BASE + consoSupp);
                        //plus de jus on coupe tout !
                        if (qtyPower <= 0) {
                            qtyPower = 0;
                            //TODO GENERER ALERTE
                            DeltaStar.getPowerSystem().onDeactivateSystem();
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PowerSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        this.consoPower.start();
    }
    
    /**
     * Appellé par les système annexes pour informer le systemes electriques l'activation/desactivation d'un systeme
     * @param activate 
     */
    public void onAuxSystem(boolean activate) {
        if (activate) {
            this.usePower += 20;
            this.consoSupp += Constants.CONSOMMATION_SYSTEM;
        } else {
            this.usePower -= 20;
            this.consoSupp -= Constants.CONSOMMATION_SYSTEM;
        }
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

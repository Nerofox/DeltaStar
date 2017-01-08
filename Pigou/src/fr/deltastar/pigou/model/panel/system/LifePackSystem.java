package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.panel.SystemLcdInterface;
import fr.deltastar.pigou.model.panel.module.lifepack.*;
import fr.deltastar.pigou.service.ServicePigou;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class LifePackSystem extends BaseSystem implements SystemLcdInterface {

    private HeaterModule heaterModule;
    private LifePackModule lifePackModule;
    private SupplyModule supplyModule;
    
    /**
     * Valeur en pourcentage de la quantité restante de O2/N2
     */
    private int valueO2N2;
    /**
     * Valeur sur 3 chiffres max de la température du circuit electronique
     */
    private int temperature;

    public LifePackSystem() {
        this.heaterModule = new HeaterModule();
        this.lifePackModule = new LifePackModule();
        this.supplyModule = new SupplyModule();
        this.arduinoComLcd = ServicePigou.getComArduinoService().getArduinoC();
        this.arduinoComLcd.setSli(this);
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getValueO2N2() {
        return valueO2N2;
    }

    public void setValueO2N2(int valueO2N2) {
        this.valueO2N2 = valueO2N2;
    }
    
    @Override
    public int getArgOne() {
        return valueO2N2;
    }

    @Override
    public void setArgOne(int argOne) {
        this.valueO2N2 = argOne;
    }

    @Override
    public int getArgTwo() {
        return this.temperature;
    }

    @Override
    public void setArgTwo(int argTwo) {
        this.temperature = argTwo;
    }
    
    @Override
    public void onActivateSystem() {
        super.setIsOnline(true);
        DeltaStar.getPowerSystem().onAuxSystem(true);
        DeltaStar.getPowerSystem().getLifePackPowerModule().getLedGreen().switchOn();
    }

    @Override
    public void onDeactivateSystem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return "Life-pack";
    }

    @Override
    public List<ModuleInterface> getListModuleInterface() {
        List<ModuleInterface> mi = new ArrayList<>();
        mi.add(this.heaterModule);
        mi.add(this.lifePackModule);
        mi.add(this.supplyModule);
        return mi;
    }
}

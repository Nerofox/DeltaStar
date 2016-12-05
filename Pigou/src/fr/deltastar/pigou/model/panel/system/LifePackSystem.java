package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.constant.ArduinoPortConstants;
import fr.deltastar.pigou.model.panel.module.lifepack.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class LifePackSystem extends BaseSystem {

    private HeaterModule heaterModule;
    private LifePackModule lifePackModule;
    private SupplyModule supplyModule;

    public LifePackSystem() {
        this.heaterModule = new HeaterModule();
        this.lifePackModule = new LifePackModule();
        this.supplyModule = new SupplyModule();
        this.arduinoComLcd = ArduinoPortConstants.ARDUINO_C;
    }

    public HeaterModule getHeaterModule() {
        return heaterModule;
    }

    public LifePackModule getLifePackModule() {
        return lifePackModule;
    }

    public SupplyModule getSupplyModule() {
        return supplyModule;
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

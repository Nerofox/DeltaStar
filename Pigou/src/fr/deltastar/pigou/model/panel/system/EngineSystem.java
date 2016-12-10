
package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.constant.ArduinoPortConstants;
import fr.deltastar.pigou.model.panel.module.engine.*;
import fr.deltastar.pigou.service.ServicePigou;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class EngineSystem extends BaseSystem {

    private MainDumpModule mainDumpModule;
    private MainValveModule mainValveModule;
    private RcsDumpModule rcsDumpModule;
    private SupplyModule supplyModule;
    private TransfertLeftModule transfertLeftModule;
    private TransfertRightModule transfertRightModule;

    public EngineSystem() {
        this.mainDumpModule = new MainDumpModule();
        this.mainValveModule = new MainValveModule();
        this.rcsDumpModule = new RcsDumpModule();
        this.supplyModule = new SupplyModule();
        this.transfertLeftModule = new TransfertLeftModule();
        this.transfertRightModule = new TransfertRightModule();
        this.arduinoComLcd = ServicePigou.getComArduinoService().getArduinoA();
    }

    public MainDumpModule getMainDumpModule() {
        return mainDumpModule;
    }

    public MainValveModule getMainValveModule() {
        return mainValveModule;
    }

    public RcsDumpModule getRcsDumpModule() {
        return rcsDumpModule;
    }

    public SupplyModule getSupplyModule() {
        return supplyModule;
    }

    public TransfertLeftModule getTransfertLeftModule() {
        return transfertLeftModule;
    }

    public TransfertRightModule getTransfertRightModule() {
        return transfertRightModule;
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
        return "Engine";
    }

    @Override
    public List<ModuleInterface> getListModuleInterface() {
        List<ModuleInterface> mi = new ArrayList<>();
        mi.add(this.mainDumpModule);
        mi.add(this.mainValveModule);
        mi.add(this.rcsDumpModule);
        mi.add(this.supplyModule);
        mi.add(this.transfertLeftModule);
        mi.add(this.transfertRightModule);
        return mi;
    }
    
}

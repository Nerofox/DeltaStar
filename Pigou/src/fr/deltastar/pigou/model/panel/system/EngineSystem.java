
package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.panel.SystemLcdInterface;
import fr.deltastar.pigou.model.panel.module.engine.*;
import fr.deltastar.pigou.service.ServicePigou;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class EngineSystem extends BaseSystem implements SystemLcdInterface {

    private MainDumpModule mainDumpModule;
    private MainValveModule mainValveModule;
    private RcsValveModule rcsValveModule;
    private RcsDumpModule rcsDumpModule;
    private SupplyModule supplyModule;
    private TransfertLeftModule transfertLeftModule;
    private TransfertRightModule transfertRightModule;
    
    /**
     * Réservoir restant pour le moteur principal
     */
    private int qtyMainFuel;
    /**
     * Réservoir restant pour les RCS
     */
    private int qtyRcsFuel;

    public EngineSystem() {
        this.mainDumpModule = new MainDumpModule();
        this.mainValveModule = new MainValveModule();
        this.rcsDumpModule = new RcsDumpModule();
        this.supplyModule = new SupplyModule();
        this.transfertLeftModule = new TransfertLeftModule();
        this.transfertRightModule = new TransfertRightModule();
        this.rcsValveModule = new RcsValveModule();
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
    public int getArgOne() {
        return this.qtyMainFuel;
    }

    @Override
    public void setArgOne(int argOne) {
        this.qtyMainFuel = argOne;
    }

    @Override
    public int getArgTwo() {
        return this.qtyRcsFuel;
    }

    @Override
    public void setArgTwo(int argTwo) {
        this.qtyRcsFuel = argTwo;
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
        mi.add(this.rcsValveModule);
        return mi;
    }
}


package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.constant.CmdOrbiterConstants;
import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.model.constant.LcdSystemEngineConstants;
import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.panel.SystemLcdInterface;
import fr.deltastar.pigou.model.panel.module.engine.*;
import fr.deltastar.pigou.service.ServicePigou;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private Thread consoEngine;
    
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
        this.arduinoComLcd.setSli(this);
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

    public RcsValveModule getRcsValveModule() {
        return rcsValveModule;
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
        super.setIsOnline(true);
        this.getArduinoComLcd().setLcdMod(LcdSystemEngineConstants.DISPLAY_FUEL);
        ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_FUELLOCK, CmdOrbiterConstants.OPTION_MAIN);
        ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_FUELLOCK, CmdOrbiterConstants.OPTION_RCS);
        DeltaStar.getPowerSystem().onAuxSystem(true);
        DeltaStar.getPowerSystem().getEnginePowerModule().getLedGreen().switchOn();
        this.consoEngine = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(Constants.TIME_DISPLAY_ALERT_ENGINE);
                        if (qtyMainFuel <= Constants.LIMIT_MAIN_FUEL_ALERT) {
                            DeltaStar.getEngineSystem().getArduinoComLcd().setLcdMod(LcdSystemEngineConstants.MAIN_FUEL_LOW);
                            Thread.sleep(Constants.TIME_DISPLAY_ALERT_ENGINE);
                            DeltaStar.getEngineSystem().getArduinoComLcd().setLcdMod(LcdSystemEngineConstants.DISPLAY_FUEL);
                        }
                        if (qtyRcsFuel <= Constants.LIMIT_RCS_FUEL_ALERT) {
                            DeltaStar.getEngineSystem().getArduinoComLcd().setLcdMod(LcdSystemEngineConstants.RCS_FUEL_LOW);
                            Thread.sleep(Constants.TIME_DISPLAY_ALERT_ENGINE);
                            DeltaStar.getEngineSystem().getArduinoComLcd().setLcdMod(LcdSystemEngineConstants.DISPLAY_FUEL);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(EngineSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        });
        this.consoEngine.start();
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

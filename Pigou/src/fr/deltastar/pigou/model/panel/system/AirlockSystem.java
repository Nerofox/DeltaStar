package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.constant.SoundConstants;
import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.panel.module.airlock.*;
import fr.deltastar.pigou.service.ServicePigou;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valentin
 */
public class AirlockSystem extends BaseSystem {

    private EmpAirModule empAirModule;
    private GearModule gearModule;
    private InnerDoorModule innerDoorModule;
    private NoseConeModule noseConeModule;
    private OuterDoorModule outerDoorModule;
    private UndockModule undockModule;

    public AirlockSystem() {
        this.empAirModule = new EmpAirModule();
        this.gearModule = new GearModule();
        this.innerDoorModule = new InnerDoorModule();
        this.noseConeModule = new NoseConeModule();
        this.outerDoorModule = new OuterDoorModule();
        this.undockModule = new UndockModule();
    }

    public EmpAirModule getEmpAirModule() {
        return empAirModule;
    }

    public GearModule getGearModule() {
        return gearModule;
    }

    public InnerDoorModule getInnerDoorModule() {
        return innerDoorModule;
    }

    public NoseConeModule getNoseConeModule() {
        return noseConeModule;
    }

    public OuterDoorModule getOuterDoorModule() {
        return outerDoorModule;
    }

    public UndockModule getUndockModule() {
        return undockModule;
    }
    
    /**
     * En fonction des états des portes
     * le systèmes indique si il est possible de faire des sorties
     * du module dans le vaisseau
     * @return 
     */
    public boolean isPossibleToExit() {
        return (this.outerDoorModule.isIsOpen() && this.noseConeModule.isIsOpen());
    }
    
    /**
     * En fonction des états des portes
     * le systèmes indique si il est possible de faire des entrées
     * du module dans le vaisseau
     * @return 
     */
    public boolean isPossibleToEnter() {
        return (this.innerDoorModule.isIsOpen());
    }

    @Override
    public void onActivateSystem() {
        super.setIsOnline(true);
        DeltaStar.getPowerSystem().onAuxSystem(true);
        DeltaStar.getPowerSystem().getAirlockPowerModule().getLedGreen().switchOn();
    }

    @Override
    public void onDeactivateSystem() {
        
    }

    @Override
    public String toString() {
        return "Airlock";
    }

    @Override
    public List<ModuleInterface> getListModuleInterface() {
        List<ModuleInterface> mi = new ArrayList<>();
        mi.add(this.empAirModule);
        mi.add(this.gearModule);
        mi.add(this.innerDoorModule);
        mi.add(this.noseConeModule);
        mi.add(this.outerDoorModule);
        mi.add(this.undockModule);
        return mi;
    }
}

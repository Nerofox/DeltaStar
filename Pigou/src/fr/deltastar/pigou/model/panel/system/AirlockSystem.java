package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.panel.module.airlock.*;
import java.util.ArrayList;
import java.util.List;

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

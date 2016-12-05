package fr.deltastar.pigou.model.panel;

import fr.deltastar.pigou.model.panel.system.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class regroupant l'ensemble des systèmes du panneau deltaStar
 * @author Valentin
 */
public class DeltaStar {
    private static AirlockSystem airlockSystem;
    private static ComputerSystem computerSystem;
    private static EngineSystem engineSystem;
    private static HudSystem hudSystem;
    private static LifePackSystem lifePackSystem;
    private static PowerSystem powerSystem;
    private static WarningSystem warningSystem;

    public static AirlockSystem getAirlockSystem() {
        if (airlockSystem == null)
            airlockSystem = new AirlockSystem();
        return airlockSystem;
    }

    public static ComputerSystem getComputerSystem() {
        if (computerSystem == null)
            computerSystem = new ComputerSystem();
        return computerSystem;
    }

    public static EngineSystem getEngineSystem() {
        if (engineSystem == null)
            engineSystem = new EngineSystem();
        return engineSystem;
    }

    public static HudSystem getHudSystem() {
        if (hudSystem == null)
            hudSystem = new HudSystem();
        return hudSystem;
    }

    public static LifePackSystem getLifePackSystem() {
        if (lifePackSystem == null)
            lifePackSystem = new LifePackSystem();
        return lifePackSystem;
    }

    public static PowerSystem getPowerSystem() {
        if (powerSystem == null)
            powerSystem = new PowerSystem();
        return powerSystem;
    }

    public static WarningSystem getWarningSystem() {
        if (warningSystem == null)
            warningSystem = new WarningSystem();
        return warningSystem;
    }
    
    public static List<BaseSystem> getListSystem() {
        List<BaseSystem> s = new ArrayList<>();
        s.add(DeltaStar.getAirlockSystem());
        s.add(DeltaStar.getComputerSystem());
        s.add(DeltaStar.getEngineSystem());
        s.add(DeltaStar.getHudSystem());
        s.add(DeltaStar.getLifePackSystem());
        s.add(DeltaStar.getPowerSystem());
        s.add(DeltaStar.getWarningSystem());
        return s;
    } 
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.deltastar.pigou.model.panel.module.hud;

import fr.deltastar.pigou.constant.CmdOrbiterConstants;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.service.ServicePigou;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class HudModule implements ModuleInterface {

    private Component button;

    public HudModule() {
        this.button = new Component(ComponentConstants.INPUT, "HUD - Button");
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.button);
        return c;
    }

    @Override
    public void onAction(boolean activate) {
        if (DeltaStar.getHudSystem().isOnline()) {
            ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_CMD, CmdOrbiterConstants.OPTION_HUDMODE);
        }
    }

    @Override
    public String toString() {
        return "HUD";
    }
}

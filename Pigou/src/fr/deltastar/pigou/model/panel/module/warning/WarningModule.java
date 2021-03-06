package fr.deltastar.pigou.model.panel.module.warning;

import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class WarningModule implements ModuleInterface {

    private Component ledRed1;
    private Component ledRed2;
    private Component ledRed3;
    private Component ledRed4;
    private Component ledRed5;
    private Component ledRed6;
    private Component ledRed7;
    private Component ledRed8;
    private Component ledRed9;
    private Component ledRed10;
    private Component buttonReset;

    public WarningModule() {
        this.ledRed1 = new Component(ComponentConstants.OUTPUT, "Led red 1");
        this.ledRed2 = new Component(ComponentConstants.OUTPUT, "Led red 2");
        this.ledRed3 = new Component(ComponentConstants.OUTPUT, "Led red 3");
        this.ledRed4 = new Component(ComponentConstants.OUTPUT, "Led red 4");
        this.ledRed5 = new Component(ComponentConstants.OUTPUT, "Led red 5");
        this.ledRed6 = new Component(ComponentConstants.OUTPUT, "Led red 6");
        this.ledRed7 = new Component(ComponentConstants.OUTPUT, "Led red 7");
        this.ledRed8 = new Component(ComponentConstants.OUTPUT, "Led red 8");
        this.ledRed9 = new Component(ComponentConstants.OUTPUT, "Led red 9");
        this.ledRed10 = new Component(ComponentConstants.OUTPUT, "Led red 10");
        this.buttonReset = new Component(ComponentConstants.INPUT, "Warning reset - Button");
    }

    public Component getLedRed1() {
        return ledRed1;
    }

    public Component getLedRed2() {
        return ledRed2;
    }

    public Component getLedRed3() {
        return ledRed3;
    }

    public Component getLedRed4() {
        return ledRed4;
    }

    public Component getLedRed5() {
        return ledRed5;
    }

    public Component getLedRed6() {
        return ledRed6;
    }

    public Component getLedRed7() {
        return ledRed7;
    }

    public Component getLedRed8() {
        return ledRed8;
    }

    public Component getLedRed9() {
        return ledRed9;
    }

    public Component getLedRed10() {
        return ledRed10;
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.buttonReset);
        c.add(this.ledRed1);
        c.add(this.ledRed2);
        c.add(this.ledRed3);
        c.add(this.ledRed4);
        c.add(this.ledRed5);
        c.add(this.ledRed6);
        c.add(this.ledRed7);
        c.add(this.ledRed8);
        c.add(this.ledRed9);
        c.add(this.ledRed10);
        return c;
    }

    @Override
    /**
     * Lancé par le bouton reset
     * Desactive tout les voyants
     */
    public void onAction(boolean activate) {
        DeltaStar.getWarningSystem().stopAlert();
    }

    @Override
    public String toString() {
        return "Warning";
    }
}

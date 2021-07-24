package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.Sped;
import me.alpha432.oyvey.features.modules.Module;

public class Obsidian extends Module {
    public Obsidian() {
        super("Obsidian", "placeholder", Category.COMBAT, false, false, false);
    }

    @Override
    public String getDisplayInfo() {
        if (Sped.moduleManager.getModuleByName("Surround").isEnabled()) {
            return "Surround";
        }
        if (Sped.moduleManager.getModuleByName("AutoTrap").isEnabled()) {
            return "AutoTrap";
        }
        if (Sped.moduleManager.getModuleByName("SelfFill").isEnabled()) {
            return "SelfFill";
        }
        return null;
    }
}

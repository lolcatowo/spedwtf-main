package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.Sped;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;

import java.awt.*;

public class EnchantColor extends Module {

    private final Setting<Integer> red = register(new Setting<>("Red", 255, 0, 255));
    private final Setting<Integer> green = register(new Setting<>("Green", 255, 0, 255));
    private final Setting<Integer> blue = register(new Setting<>("Blue", 255, 0, 255));
    public Setting<Boolean> rainbow = register(new Setting("Rainbow", false));

    public static EnchantColor INSTANCE;

    public EnchantColor() {
        super("EnchantColor", "Draws a skeleton inside the player.", Module.Category.RENDER, false, false, false);
        INSTANCE = this;
    }

    public int getColor() {
        return new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB();
    }

}
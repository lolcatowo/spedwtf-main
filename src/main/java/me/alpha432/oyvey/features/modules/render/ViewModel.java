package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;

public class ViewModel extends Module {
    public final Setting<Boolean> pauseOnEat = register(new Setting<>("Pause", true));

    public final Setting<Float> translateX = register(new Setting<>("X", 0F, -5F, 5F));
    public final Setting<Float> translateY = register(new Setting<>("Y", 0F, -5F, 5F));
    public final Setting<Float> translateZ = register(new Setting<>("Z", 0F, -5F, 5F));

    public final Setting<Float> scale = register(new Setting<>("Scale", 10f, 9f, 10f));

    public static ViewModel INSTANCE;

    public ViewModel() {
        super("Viewmodel", "Draws a skeleton inside the player.", Module.Category.RENDER, false, false, false);
        INSTANCE = this;
    }
}

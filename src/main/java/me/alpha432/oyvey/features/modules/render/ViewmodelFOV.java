package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent.FOVModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ViewmodelFOV extends Module {
    private final Setting<Integer> custom_fov = this.register(new Setting("CustomFov", 100, 90, 169));
    public Setting<Boolean> cancelEating = this.register(new Setting("CancelEating", true));
    private float fov;

    public ViewmodelFOV() {
        super("ViewModelFOV", "sdfsdfsdf", Module.Category.RENDER, true, false, false);
    }

    public void onEnable() {
        this.fov = mc.gameSettings.fovSetting;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        mc.gameSettings.fovSetting = this.fov;
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void onUpdate() {
        mc.gameSettings.fovSetting = (float)(Integer)this.custom_fov.getValue();
    }

    @SubscribeEvent
    public void fov_event(FOVModifier m) {
        m.setFOV((float)(Integer)this.custom_fov.getValue());
    }
}
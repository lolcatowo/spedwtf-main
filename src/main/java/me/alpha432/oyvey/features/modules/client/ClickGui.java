package me.alpha432.oyvey.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.alpha432.oyvey.Sped;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.event.events.ClientEvent;
import me.alpha432.oyvey.features.command.Command;
import me.alpha432.oyvey.features.gui.OyVeyGui;
import me.alpha432.oyvey.features.setting.Setting;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class ClickGui extends Module {
    public static ClickGui INSTANCE = new ClickGui();
    public Setting<String> prefix = register(new Setting<String>("Prefix", "."));
    public Setting<Boolean> customFov = register(new Setting<Boolean>("CustomFov", false));
    public Setting<Float> fov = register(new Setting<Float>("Fov", Float.valueOf(150.0f), Float.valueOf(-180.0f), Float.valueOf(180.0f)));
    public Setting<Integer> red = register(new Setting<Integer>("Red", 0, 0, 255));
    public Setting<Integer> green = register(new Setting<Integer>("Green", 0, 0, 255));
    public Setting<Integer> blue = register(new Setting<Integer>("Blue", 255, 0, 255));
    public Setting<Integer> hoverAlpha = register(new Setting<Integer>("Alpha", 180, 0, 255));
    public Setting<Integer> topRed = register(new Setting<Integer>("SecondRed", 0, 0, 255));
    public Setting<Integer> topGreen = register(new Setting<Integer>("SecondGreen", 0, 0, 255));
    public Setting<Integer> topBlue = register(new Setting<Integer>("SecondBlue", 150, 0, 255));
    public Setting<Integer> bgRed = register(new Setting<Integer>("BackgroundRed", 0, 0, 255));
    public Setting<Integer> bgGreen = register(new Setting<Integer>("BackgroundGreen", 0, 0, 255));
    public Setting<Integer> bgBlue = register(new Setting<Integer>("BackgroundBlue", 150, 0, 255));
    public Setting<Integer> alpha = register(new Setting<Integer>("HoverAlpha", 240, 0, 255));
    public Setting<Boolean> backgroundAlpha = this.register(new Setting<Boolean>("BackgroundAlpha", false));
    public Setting<Boolean> rainbow = register(new Setting<Boolean>("Rainbow", false));
    public Setting<rainbowMode> rainbowModeHud = register(new Setting<Object>("HRainbowMode", rainbowMode.Static, v -> rainbow.getValue()));
    public Setting<rainbowModeArray> rainbowModeA = register(new Setting<Object>("ARainbowMode", rainbowModeArray.Static, v -> rainbow.getValue()));
    public Setting<Integer> rainbowHue = register(new Setting<Object>("Delay", Integer.valueOf(240), Integer.valueOf(0), Integer.valueOf(600), v -> rainbow.getValue()));
    public Setting<Float> rainbowBrightness = register(new Setting<Object>("Brightness ", Float.valueOf(150.0f), Float.valueOf(1.0f), Float.valueOf(255.0f), v -> rainbow.getValue()));
    public Setting<Float> rainbowSaturation = register(new Setting<Object>("Saturation", Float.valueOf(150.0f), Float.valueOf(1.0f), Float.valueOf(255.0f), v -> rainbow.getValue()));
    private OyVeyGui click;
    public float hue;

    public ClickGui() {
        super("ClickGui", "Opens the ClickGui", Module.Category.CLIENT, true, false, false);
        setInstance();
    }

    public static ClickGui getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClickGui();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (customFov.getValue().booleanValue()) {
            ClickGui.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, fov.getValue().floatValue());
        }
    }

    public Color getCurrentColor() {
        if (this.rainbow.getValue().booleanValue()) {
            return Color.getHSBColor(this.hue, (float)this.rainbowSaturation.getValue().intValue() / 255.0f, (float)this.rainbowBrightness.getValue().intValue() / 255.0f);
        }
        return new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue());
    }

    @SubscribeEvent
    public void onSettingChange(ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(prefix)) {
                Sped.commandManager.setPrefix(prefix.getPlannedValue());
                Command.sendMessage("Prefix set to " + ChatFormatting.DARK_GRAY + Sped.commandManager.getPrefix());
            }
            Sped.colorManager.setColor(red.getPlannedValue(), green.getPlannedValue(), blue.getPlannedValue(), hoverAlpha.getPlannedValue());
        }
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(OyVeyGui.getClickGui());
    }


    @Override
    public void onLoad() {
        Sped.colorManager.setColor(red.getValue(), green.getValue(), blue.getValue(), hoverAlpha.getValue());
        Sped.commandManager.setPrefix(prefix.getValue());
    }

    @Override
    public void onTick() {
        if (!(ClickGui.mc.currentScreen instanceof OyVeyGui)) {
            disable();
        }
    }

    public enum rainbowModeArray {
        Static,
        Up

    }

    public enum rainbowMode {
        Static,
        Sideway

    }
}


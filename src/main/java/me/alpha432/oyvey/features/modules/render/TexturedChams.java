package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.Sped;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;

import java.awt.*;

public class TexturedChams extends Module {

    public static Setting<Integer> red;
    public static Setting<Integer> green;
    public static Setting<Integer> blue;
    public static Setting<Integer> alpha;
    public Setting<Boolean> rainbow = register(new Setting("Rainbow", false));

    public TexturedChams() {
        super("TextureChams", "hi yes", Category.RENDER, true, false, false);

        red = (Setting<Integer>) register(new Setting("Red", 168, 0, 255));
        green = (Setting<Integer>) register(new Setting("Green", 0, 0, 255));
        blue = (Setting<Integer>) register(new Setting("Blue", 232, 0, 255));
        alpha = (Setting<Integer>) register(new Setting("Alpha", 150, 0, 255));

    }

    public static Color getColor(long offset, float fade) {
        if (!Sped.moduleManager.getModuleT(TexturedChams.class).rainbow.getValue()) {
            return new Color(Sped.moduleManager.getModuleT(TexturedChams.class).red.getValue(), Sped.moduleManager.getModuleT(TexturedChams.class).green.getValue(), Sped.moduleManager.getModuleT(TexturedChams.class).blue.getValue());
        }
        float hue = (float) (System.nanoTime() + offset) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
        Color c = new Color((int) color);
        return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
    }

    @Override
        public void onUpdate() {
            if (rainbow.getValue()) {
                cycleRainbow();
            }
        }

        public void cycleRainbow() {
            float[] tick_color = {
                    (System.currentTimeMillis() % (360 * 32)) / (360f * 32)
            };
            int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8f, 0.8f);

            red.setValue((color_rgb_o >> 16) & 0xFF);
            green.setValue((color_rgb_o >> 8) & 0xFF);
            blue.setValue(color_rgb_o & 0xFF);
        }
    }




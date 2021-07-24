package me.alpha432.oyvey.util;

import me.alpha432.oyvey.Sped;
import net.minecraft.client.Minecraft;

public class FontUtils2 {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static float drawStringWithShadow(boolean customFont, String text, int x, int y, int color) {
        if (customFont) return Sped.fontRenderer.drawStringWithShadow(text, x, y, color);
        else return mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }

    public static float drawString(boolean customFont, String text, int x, int y, int color) {
        if (customFont) return Sped.fontRenderer.drawString(text, x, y, color);
        else return mc.fontRenderer.drawString(text, x, y, color);
    }

    public static int getStringWidth(boolean customFont, String str) {
        if (customFont) return Sped.fontRenderer.getStringWidth(str);
        else return mc.fontRenderer.getStringWidth(str);
    }


    public static int getFontHeight(boolean customFont) {
        if (customFont) return Sped.fontRenderer.getHeight();
        else return mc.fontRenderer.FONT_HEIGHT;
    }
}
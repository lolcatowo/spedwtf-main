package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.combat.AutoCrystal;
import me.alpha432.oyvey.util.EntityUtil;
import me.alpha432.oyvey.util.FontUtils;
import me.alpha432.oyvey.util.MathUtil;
import me.alpha432.oyvey.util.RenderUtil;
import me.alpha432.oyvey.features.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class TargetHUD extends Module {
    public Setting<Integer> x_ = register(new Setting<Integer>("X", 2, 0, 500));
    public Setting<Integer> y_ = register(new Setting<Integer>("Y", 2, 0, 500));


    public TargetHUD() {
        super("TargetHudTwo", "aurora", Category.CLIENT, true, false, true);
    }


    public void onRender2D() {
        int x = x_.getValue();
        int y = y_.getValue();
        int width = 120;
        int height = 40;
        EntityPlayer target = AutoCrystal.target;
        if (target != null) {
            RenderUtil.drawBorderedRect(x, y, x + width, y + height, 1.5, new Color(255, 255, 255, 100).getRGB(), new Color(255, 255, 255, 255).getRGB());
            final String playerName = target.getName();
            FontUtils.drawStringWithShadow(true, playerName, x + 36, y + 4, -1);
            FontUtils.drawStringWithShadow(true, "Ping : " + getPing(target), x + 36, y + 14, -1);
            FontUtils.drawStringWithShadow(true, "HP : " + EntityUtil.getHealth(target), x + 36, y + 24, -1);
            try {
                AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(playerName), playerName).loadTexture(Minecraft.getMinecraft().getResourceManager());
                Minecraft.getMinecraft().getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(playerName));
                GL11.glColor4f(1F, 1F, 1F, 1F);
                Gui.drawScaledCustomSizeModalRect(x + 4, y + 4, 8, 8, 8, 8, 28, 28, 64, 64);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public int getPing(EntityPlayer player) {
        int ping = 0;
        try {
            ping = (int) MathUtil.clamp((float) Objects.requireNonNull(mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime(), 1, 300.0f);
        }
        catch (NullPointerException ignored) {
        }
        return ping;
    }

}
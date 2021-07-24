package me.alpha432.oyvey.features.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;
import me.alpha432.oyvey.Sped;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import me.alpha432.oyvey.event.events.BlockCollisionBoundingBoxEvent;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;

public class AntiWeb extends Module
{
    public Setting<Boolean> disableBB;
    public Setting<Double> bbOffset;
    public Setting<Boolean> onGround;
    public Setting<Double> motionX;
    public Setting<Double> motionY;

    public AntiWeb() {
        super("AntiWeb", "Modifies movement in webs", Category.MOVEMENT, true, false, false);
        this.disableBB = (Setting<Boolean>)this.register(new Setting("AddBB", true));
        this.bbOffset = (Setting<Double>)this.register(new Setting("BoxOffset", 0.04, (-1.0), 1.0));
        this.onGround = (Setting<Boolean>)this.register(new Setting("OnGround", true));
        this.motionX = (Setting<Double>)this.register(new Setting("MotionX", 0.8, (-1.0), 5.0));
        this.motionY = (Setting<Double>)this.register(new Setting("MotionY", (-0.05), 0.0, 10.0));
    }

    @SubscribeEvent
    public void bbEvent(final BlockCollisionBoundingBoxEvent event) {
        if (nullCheck()) {
            return;
        }
        if (AntiWeb.mc.world.getBlockState(event.getPos()).getBlock() instanceof BlockWeb && this.disableBB.getValue()) {
            event.setCanceled(true);
            event.setBoundingBox(Block.FULL_BLOCK_AABB.contract(0.0, (double)this.bbOffset.getValue(), 0.0));
        }
    }

    @Override
    public void onUpdate() {
        if (AntiWeb.mc.player.isInWeb && !Sped.moduleManager.isModuleEnabled(Step.class)) {
            if (Keyboard.isKeyDown(AntiWeb.mc.gameSettings.keyBindSneak.keyCode)) {
                AntiWeb.mc.player.isInWeb = true;
                final EntityPlayerSP player = AntiWeb.mc.player;
                player.motionY *= this.motionY.getValue();
            }
            else if (this.onGround.getValue()) {
                AntiWeb.mc.player.onGround = false;
            }
            if (Keyboard.isKeyDown(AntiWeb.mc.gameSettings.keyBindForward.keyCode) || Keyboard.isKeyDown(AntiWeb.mc.gameSettings.keyBindBack.keyCode) || Keyboard.isKeyDown(AntiWeb.mc.gameSettings.keyBindLeft.keyCode) || Keyboard.isKeyDown(AntiWeb.mc.gameSettings.keyBindRight.keyCode)) {
                AntiWeb.mc.player.isInWeb = false;
                final EntityPlayerSP player2 = AntiWeb.mc.player;
                player2.motionX *= this.motionX.getValue();
                final EntityPlayerSP player3 = AntiWeb.mc.player;
                player3.motionZ *= this.motionX.getValue();
            }
        }
    }
}

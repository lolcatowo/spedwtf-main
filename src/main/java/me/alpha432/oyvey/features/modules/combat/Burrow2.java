package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.Sped;
import me.alpha432.oyvey.features.modules.movement.ReverseStep;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import me.alpha432.oyvey.features.command.Command;
import me.alpha432.oyvey.util.BurrowUtil;
import net.minecraft.block.BlockObsidian;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;

// from gondal.club

public class Burrow2 extends Module
{
    private final Setting<Integer> offset;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> Toggleac;
    private final Setting<Mode> mode;
    private BlockPos originalPos;
    private int oldSlot;
    Block returnBlock;

    public Burrow2() {
        super("SelfFill", "TPs you into a block", Category.COMBAT, true, false, false);
        this.offset = (Setting<Integer>)this.register(new Setting("Offset", 3, 0, 5));;
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", false));
        this.Toggleac = (Setting<Boolean>)this.register(new Setting("ToggleAC", true));
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", Mode.OBBY));
        this.oldSlot = -1;
        this.returnBlock = null;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (Toggleac.getValue()) {
            Sped.moduleManager.getModuleByName("AutoCrystal").isEnabled(); {
                AutoCrystal.getInstance().disable();
            }
        }
        this.originalPos = new BlockPos(Burrow2.mc.player.posX, Burrow2.mc.player.posY, Burrow2.mc.player.posZ);
        switch (this.mode.getValue()) {
            case OBBY: {
                this.returnBlock = Blocks.OBSIDIAN;
                break;
            }
            case ECHEST: {
                this.returnBlock = Blocks.ENDER_CHEST;
                break;
            }
            case CHEST: {
                this.returnBlock = Blocks.CHEST;
                break;
            }
        }
        if (Burrow2.mc.world.getBlockState(new BlockPos(Burrow2.mc.player.posX, Burrow2.mc.player.posY, Burrow2.mc.player.posZ)).getBlock().equals(this.returnBlock) || this.intersectsWithEntity(this.originalPos)) {
            this.toggle();
            return;
        }
        this.oldSlot = Burrow2.mc.player.inventory.currentItem;
    }

    @Override
    public void onUpdate() {
        switch (this.mode.getValue()) {
            case OBBY: {
                if (BurrowUtil.findHotbarBlock(BlockObsidian.class) == -1) {
                    Command.sendMessage("Can't find obby in hotbar!");
                    this.toggle();
                    break;
                }
                break;
            }
            case ECHEST: {
                if (BurrowUtil.findHotbarBlock(BlockEnderChest.class) == -1) {
                    Command.sendMessage("Can't find echest in hotbar!");
                    this.toggle();
                    break;
                }
                break;
            }
            case CHEST: {
                if (BurrowUtil.findHotbarBlock(BlockChest.class) == -1) {
                    Command.sendMessage("Can't find chest in hotbar!");
                    this.toggle();
                    break;
                }
                break;
            }
        }
        switch (this.mode.getValue()) {
            case OBBY: {
                BurrowUtil.switchToSlot(BurrowUtil.findHotbarBlock(BlockObsidian.class));
                break;
            }
            case ECHEST: {
                BurrowUtil.switchToSlot(BurrowUtil.findHotbarBlock(BlockEnderChest.class));
                break;
            }
            case CHEST: {
                BurrowUtil.switchToSlot(BurrowUtil.findHotbarBlock(BlockChest.class));
                break;
            }
        }
        mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.419, mc.player.posZ, true));
        mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.75319998, mc.player.posZ, true));
        mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00013597D, mc.player.posZ, true));
        mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610926D, mc.player.posZ, true));
        BurrowUtil.placeBlock(this.originalPos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
        Burrow2.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow2.mc.player.posX, Burrow2.mc.player.posY + this.offset.getValue(), Burrow2.mc.player.posZ, false));
        Burrow2.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Burrow2.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        Burrow2.mc.player.setSneaking(false);
        BurrowUtil.switchToSlot(this.oldSlot);
        disable();

    }

        private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : Burrow2.mc.world.loadedEntityList) {
            if (entity.equals((Object)Burrow2.mc.player)) {
                continue;
            }
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;

    }

     public enum Mode
    {
        OBBY,
        ECHEST,
        CHEST;
    }
}
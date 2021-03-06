package me.alpha432.oyvey.features.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.Sped;
import me.alpha432.oyvey.features.command.Command;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.UUID;

public class PearlNotify extends Module {
    private final HashMap<EntityPlayer, UUID> list;
    private Entity enderPearl;
    private boolean flag;

    public PearlNotify() {
        super("PearlResolver", "Notify pearl throws.", Category.MISC, true, false, false);
        list = new HashMap<EntityPlayer, UUID>();
    }

    @Override
    public void onEnable() {
        flag = true;
    }

    @Override
    public void onUpdate() {
        if (PearlNotify.mc.world == null || PearlNotify.mc.player == null) {
            return;
        }
        enderPearl = null;
        for (final Entity e : PearlNotify.mc.world.loadedEntityList) {
            if (e instanceof EntityEnderPearl) {
                enderPearl = e;
                break;
            }
        }
        if (enderPearl == null) {
            flag = true;
            return;
        }
        EntityPlayer closestPlayer = null;
        for (final EntityPlayer entity : PearlNotify.mc.world.playerEntities) {
            if (closestPlayer == null) {
                closestPlayer = entity;
            } else {
                if (closestPlayer.getDistance(enderPearl) <= entity.getDistance(enderPearl)) {
                    continue;
                }
                closestPlayer = entity;
            }
        }
        if (closestPlayer == PearlNotify.mc.player) {
            flag = false;
        }
        if (closestPlayer != null && flag) {
            String faceing = enderPearl.getHorizontalFacing().toString();
            if (faceing.equals("west")) {
                faceing = "east";
            } else if (faceing.equals("east")) {
                faceing = "west";
            }
            Command.sendSilentMessage(Sped.friendManager.isFriend(closestPlayer.getName()) ? (ChatFormatting.AQUA + closestPlayer.getName() + ChatFormatting.DARK_GRAY + " has just thrown a pearl heading " + faceing + "!") : (ChatFormatting.RED + closestPlayer.getName() + ChatFormatting.DARK_GRAY + " has just thrown a pearl heading " + faceing + "!"));
            flag = false;
        }
    }
}
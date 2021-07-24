package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.command.Command;
import net.minecraft.entity.passive.EntityDonkey;
import java.util.HashSet;
import net.minecraft.entity.Entity;
import java.util.Set;

import net.minecraft.init.SoundEvents;

public class DonkeyNotifier extends Module
{
    private static DonkeyNotifier instance;
    private Set<Entity> entities;

    public DonkeyNotifier() {
        super("DonkeyNotifier", "Notifies you when a donkey is discovered", Category.MISC, true, false, false);
        this.entities = new HashSet<Entity>();
        DonkeyNotifier.instance = this;
    }

    @Override
    public void onEnable() {
        this.entities.clear();
    }

    @Override
    public void onUpdate() {
        for (final Entity entity : DonkeyNotifier.mc.world.loadedEntityList) {
            if (entity instanceof EntityDonkey) {
                if (this.entities.contains(entity)) {
                    continue;
                }
                Command.sendMessage("donkey at: " + entity.posX + "x, " + entity.posY + "y, " + entity.posZ + "z.");
                this.entities.add(entity);
                GhastNotifier.mc.player.playSound(SoundEvents.BLOCK_ANVIL_USE , 1.0f, 1.0f);
            }
        }
    }
}
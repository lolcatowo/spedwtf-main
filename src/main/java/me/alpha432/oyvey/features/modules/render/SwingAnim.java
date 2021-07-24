package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.util.Wrapper;

public class SwingAnim
        extends Module {
    public SwingAnim() {
        super("ItemAnim", "idk makes the swing animation cool", Category.RENDER, true, false, true);
    }

    @Override
    public void onUpdate() {
        if (Wrapper.getPlayer() == null) return;

        if (Wrapper.mc.entityRenderer.itemRenderer.equippedProgressMainHand < 1)
            Wrapper.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1;

        if (Wrapper.mc.entityRenderer.itemRenderer.itemStackMainHand != Wrapper.getPlayer().getHeldItemMainhand())
            Wrapper.mc.entityRenderer.itemRenderer.itemStackMainHand = Wrapper.getPlayer().getHeldItemMainhand();

       // if (Wrapper.mc.entityRenderer.itemRenderer.equippedProgressOffHand < 1)
        //     Wrapper.mc.entityRenderer.itemRenderer.equippedProgressOffHand = 1;

       // if (Wrapper.mc.entityRenderer.itemRenderer.itemStackOffHand != Wrapper.getPlayer().getHeldItemOffhand())
        //     Wrapper.mc.entityRenderer.itemRenderer.itemStackOffHand = Wrapper.getPlayer().getHeldItemOffhand();

    }
}
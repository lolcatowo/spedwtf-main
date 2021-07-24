package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.Sped;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.features.setting.Setting;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatModifier extends Module {
    private static ChatModifier INSTANCE = new ChatModifier();
    public Setting<Boolean> clean = register(new Setting<Boolean>("NoChatBackground", Boolean.valueOf(false), "Cleans your chat"));
    public Setting<Boolean> noChatShadow = register(new Setting<Boolean>("NoChatShadow", Boolean.valueOf(false), "NoChatShadow"));
    public Setting<Boolean> customFont = register(new Setting<Boolean>("CustomFont", Boolean.valueOf(false), "Custom font for da chat"));
    public Setting<Boolean> infinite = register(new Setting<Boolean>("InfiniteChat", Boolean.valueOf(false), "Makes your chat infinite."));
    public boolean check;

    public ChatModifier() {
        super("Chat", "Modifies your chat", Module.Category.MISC, true, false, false);
        setInstance();
    }

    public static ChatModifier getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatModifier();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            String s = ((CPacketChatMessage) event.getPacket()).getMessage();
            check = !s.startsWith(Sped.commandManager.getPrefix());
        }
    }
}


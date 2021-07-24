package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.DiscordPresence;
import me.alpha432.oyvey.features.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class RPC extends Module
{
    public static RPC INSTANCE;
    public Setting<Boolean> showIP;
    public Setting<String> state;

    public RPC() {
        super("RPC", "Discord rich presence", Category.CLIENT, false, false, false);
        this.showIP = (Setting<Boolean>)this.register(new Setting("ShowIP", true, "Shows the server IP in your discord presence."));
        this.state = (Setting<String>)this.register(new Setting("Text", "death client", "Sets the state of the DiscordRPC."));
        RPC.INSTANCE = this;
    }

    @Override
    public void onEnable() {
        DiscordPresence.start();
    }

    @Override
    public void onDisable() {
        DiscordPresence.stop();
    }

    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if (isEnabled()){
            disable();
        }

    }

}

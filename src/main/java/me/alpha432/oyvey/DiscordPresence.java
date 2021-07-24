package me.alpha432.oyvey;

import me.alpha432.oyvey.features.modules.client.RPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class DiscordPresence {
    private static final DiscordRPC rpc;
    public static DiscordRichPresence presence;
    private static Thread thread;
    private static int index;

    static {
        index = 1;
        rpc = DiscordRPC.INSTANCE;
        presence = new DiscordRichPresence();
    }

    public static void start() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        rpc.Discord_Initialize("835836603670724609", handlers, true, "");
        DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordPresence.presence.details = Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu ? "main menu" : "playing " + (Minecraft.getMinecraft().currentServerData != null ? (RPC.INSTANCE.showIP.getValue().booleanValue() ? "on " + Minecraft.getMinecraft().currentServerData.serverIP + " " : " multiplayer") : " singleplayer");
        DiscordPresence.presence.state = RPC.INSTANCE.state.getValue();
        DiscordPresence.presence.largeImageKey = "sped";
        DiscordPresence.presence.smallImageKey = "sun";
        rpc.Discord_UpdatePresence(presence);
        thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                rpc.Discord_RunCallbacks();
                DiscordPresence.presence.details = Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu ? "main menu" : "playing " + (Minecraft.getMinecraft().currentServerData != null ? (RPC.INSTANCE.showIP.getValue().booleanValue() ? "on " + Minecraft.getMinecraft().currentServerData.serverIP + " " : " multiplayer") : " singleplayer");
                DiscordPresence.presence.state = RPC.INSTANCE.state.getValue();
                rpc.Discord_UpdatePresence(presence);
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException interruptedException) {}
            }
        }, "RPC-Callback-Handler");
        thread.start();
    }

    public static void stop() {
        if (thread != null && !thread.isInterrupted()) {
            thread.interrupt();
        }
        rpc.Discord_Shutdown();
    }
}
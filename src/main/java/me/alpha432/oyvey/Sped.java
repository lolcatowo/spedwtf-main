package me.alpha432.oyvey;

import me.alpha432.oyvey.features.gui.font.CustomFont;
import me.alpha432.oyvey.manager.*;
import me.alpha432.oyvey.util.Enemy;
import me.alpha432.oyvey.event.events.Render3DEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

@Mod(modid = "zori", name = "sped.wtf", version = "0.0.7")
public class Sped {
    public static final String MODID = "zori";
    public static final String MODNAME = "sped.wtf";
    public static final String MODVER = "0.0.7";
    public static final Logger LOGGER = LogManager.getLogger("spedwtf");
    public static TimerManager timerManager;
    public static CommandManager commandManager;
    public static FriendManager friendManager;
    public static ModuleManager moduleManager;
    public static PacketManager packetManager;
    public static ColorManager colorManager;
    public static HoleManager holeManager;
    public static InventoryManager inventoryManager;
    public static PotionManager potionManager;
    public static RotationManager rotationManager;
    public static PositionManager positionManager;
    public static SpeedManager speedManager;
    public static ReloadManager reloadManager;
    public static FileManager fileManager;
    public static ConfigManager configManager;
    public static ServerManager serverManager;
    public static EventManager eventManager;
    public static TextManager textManager;
    public static CustomFont fontRenderer;
    public static Render3DEvent render3DEvent;
    public static TotemPopManager totemPopManager;
    public static Enemy enemy;
    public static Enemy Mapping;
    @Mod.Instance
    public static Sped INSTANCE;
    private static boolean unloaded;

    static {
        unloaded = false;
    }

    public static void load() {
        LOGGER.info("\n\nLoading Sped.Wtf by Epiruku");
        unloaded = false;
        if (reloadManager != null) {
            reloadManager.unload();
            reloadManager = null;
        }
        textManager = new TextManager();
        commandManager = new CommandManager();
        friendManager = new FriendManager();
        moduleManager = new ModuleManager();
        rotationManager = new RotationManager();
        packetManager = new PacketManager();
        eventManager = new EventManager();
        speedManager = new SpeedManager();
        potionManager = new PotionManager();
        inventoryManager = new InventoryManager();
        serverManager = new ServerManager();
        fileManager = new FileManager();
        colorManager = new ColorManager();
        positionManager = new PositionManager();
        configManager = new ConfigManager();
        holeManager = new HoleManager();
        totemPopManager = new TotemPopManager();
        LOGGER.info("Managers loaded.");
        moduleManager.init();
        LOGGER.info("Modules loaded.");
        configManager.init();
        eventManager.init();
        LOGGER.info("EventManager loaded.");
        textManager.init(true);
        moduleManager.onLoad();
        LOGGER.info("Sped successfully loaded!\n");
    }

    public static void unload(boolean unload) {
        LOGGER.info("\n\nUnloading Sped.Wtf by Epiruku");
        if (unload) {
            reloadManager = new ReloadManager();
            reloadManager.init(commandManager != null ? commandManager.getPrefix() : ".");
        }
        Sped.onUnload();
        eventManager = null;
        friendManager = null;
        speedManager = null;
        holeManager = null;
        positionManager = null;
        rotationManager = null;
        configManager = null;
        commandManager = null;
        colorManager = null;
        serverManager = null;
        fileManager = null;
        potionManager = null;
        inventoryManager = null;
        moduleManager = null;
        textManager = null;
        LOGGER.info("Sped.Wtf unloaded!\n");
    }

    public static void reload() {
        Sped.unload(false);
        Sped.load();
    }

    public static void onUnload() {
        if (!unloaded) {
            eventManager.onUnload();
            moduleManager.onUnload();
            configManager.saveConfig(Sped.configManager.config.replaceFirst("oyvey/", ""));
            moduleManager.onUnloadPost();
            unloaded = true;
        }
    }

    public final FriendManager getFriendManager() {
        return friendManager;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("sped.wtf loaded or something i dunno");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Display.setTitle("𝙨𝙥𝙚𝙙.𝙬𝙩𝙛\n");
        Sped.load();
    }
}


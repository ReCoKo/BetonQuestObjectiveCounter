package me.recoko.betonquestthings;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.logging.MythicLogger;
import me.recoko.betonquestthings.questbook.QuestBookAddEvent;
import me.recoko.betonquestthings.questbook.QuestBookAddEventFactory;
import me.recoko.betonquestthings.questbook.QuestbookHandler;
import org.betonquest.betonquest.BetonQuest;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetonQuestThings extends JavaPlugin {
    private static BetonQuestThings instance;
    private MythicMobsBridge mythicBridge;
    @Override
    public void onEnable() {
        // Plugin startup logic
        BetonQuest.getInstance().registerObjectives("objective", ObjectiveCounter.class);
        BetonQuest.getInstance().registerNonStaticEvent("questbook", new QuestBookAddEventFactory());
        Bukkit.getPluginManager().registerEvents(new QuestbookHandler(), this);
        instance = this;
        if(this.getServer().getPluginManager().isPluginEnabled("MythicMobs")) {
            this.registerCompatibility("BetonQuest", () -> {
                this.mythicBridge = new MythicMobsBridge(MythicBukkit.inst());
            });
            this.getLogger().info("Added BetonQuest item support to MythicMobs");
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BetonQuestThings getInstance(){
        return instance;
    }


    private void registerCompatibility(String name, Runnable run) {
        try {
            if (Bukkit.getPluginManager().getPlugin(name) != null) {
                run.run();
                MythicLogger.log("Mythic " + name + " Support has been enabled!");
            }
        } catch (NoClassDefFoundError var4) {
            NoClassDefFoundError er = var4;
            MythicLogger.errorCompatibility(name, "Plugin not found/incompatible version");
            MythicLogger.handleMinorError(er);
        } catch (Exception var5) {
            Exception ex = var5;
            MythicLogger.error("Failed to enable support for " + name + ". Is it up to date?");
            ex.printStackTrace();
        }

    }
}

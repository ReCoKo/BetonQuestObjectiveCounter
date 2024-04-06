package me.recoko.betonquestobjectivecounter;

import org.betonquest.betonquest.BetonQuest;
import org.betonquest.betonquest.api.CountingObjective;
import org.betonquest.betonquest.api.Objective;
import org.betonquest.betonquest.api.logger.BetonQuestLogger;
import org.betonquest.betonquest.api.logger.BetonQuestLoggerFactory;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetonQuestObjectiveCounter extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        BetonQuest.getInstance().registerObjectives("objective", ObjectiveCounter.class);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

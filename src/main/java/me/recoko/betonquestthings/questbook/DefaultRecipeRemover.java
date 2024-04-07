package me.recoko.betonquestthings.questbook;

import me.recoko.betonquestthings.BetonQuestThings;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;

public class DefaultRecipeRemover implements Listener {

    public void removeRecipes(BetonQuestThings plugin){
        Bukkit.clearRecipes();
        BetonQuestThings.getInstance().getLogger().info("Removed recipes");
    }
}

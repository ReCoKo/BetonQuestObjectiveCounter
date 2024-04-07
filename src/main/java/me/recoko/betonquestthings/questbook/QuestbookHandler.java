package me.recoko.betonquestthings.questbook;

import com.destroystokyo.paper.event.player.PlayerRecipeBookClickEvent;
import me.recoko.betonquestthings.BetonQuestThings;
import org.betonquest.betonquest.exceptions.InstructionParseException;
import org.betonquest.betonquest.exceptions.ObjectNotFoundException;
import org.betonquest.betonquest.id.ItemID;
import org.betonquest.betonquest.item.QuestItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRecipeBookSettingsChangeEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class QuestbookHandler implements Listener {


    public static void addQuest(String name, String id) throws ObjectNotFoundException, InstructionParseException {
        addQuest(name, new ItemID(null, id));
    }

    public static void addQuest(String name, ItemID id) throws ObjectNotFoundException, InstructionParseException {
        if(id != null) {
            NamespacedKey key = new NamespacedKey("quest", "quest." + name);
            Recipe recipe = new ShapelessRecipe(key, new QuestItem(id).generate(1)).addIngredient(Material.AIR);
            Bukkit.addRecipe(recipe);
        }
    }

    public static void unsendQuest(String name, Player player){
        player.undiscoverRecipe(new NamespacedKey("quest", "quest." + name));
    }

    public static void sendQuest(String name, Player player){
        player.discoverRecipe(new NamespacedKey("quest", "quest." + name));
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e){
        for(NamespacedKey key : e.getPlayer().getDiscoveredRecipes()){
            if(!key.getKey().startsWith("quest.")){
                e.getPlayer().undiscoverRecipe(key);
            }
        }
    }

    @EventHandler
    public void recipeBookClick(PlayerRecipeBookClickEvent e){
        if(e.getRecipe().namespace().equals("quest")){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void purgeEventPlayer(PlayerCommandPreprocessEvent e){
        String command = e.getMessage();
        if(e.getPlayer().hasPermission("betonquest.admin")) {
            if (command.contains("bq purge") || command.contains("betonquest purge") || command.contains("betonquests purge")) {
                purge(command);
            }
        }


    }

    @EventHandler
    public void purgeEvent(ServerCommandEvent e){
        //Bukkit.getLogger().info("1");
        if(e.getCommand().contains("bq purge") || e.getCommand().contains("betonquest purge") || e.getCommand().contains("betonquests purge")){
            //Bukkit.getLogger().info("2");
            purge(e.getCommand());
        }
    }

    public void purge(String command){
        String[] split1 = command.split("purge ");
        if(split1.length > 1) {
            String player = split1[1].split(" ")[0];
            if (Bukkit.getOfflinePlayer(player).isOnline()) {
                //Bukkit.getLogger().info("3");

                Player p = Bukkit.getOfflinePlayer(player).getPlayer();
                assert p != null;
                for (NamespacedKey key : p.getDiscoveredRecipes()) {
                    //Bukkit.getLogger().info("4");

                    if (key.getNamespace().equalsIgnoreCase("quest")) {
                        //Bukkit.getLogger().info("5");

                        p.undiscoverRecipe(key);
                    }
                }
            }
        }
    }
}

package me.recoko.betonquestthings;

import io.lumine.mythic.api.adapters.AbstractItemStack;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.drops.DropMetadata;
import io.lumine.mythic.api.drops.IItemDrop;
import io.lumine.mythic.api.items.ItemSupplier;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.adapters.BukkitItemStack;
import io.lumine.mythic.bukkit.events.MythicDropLoadEvent;
import io.lumine.mythic.bukkit.utils.Events;
import io.lumine.mythic.bukkit.utils.numbers.Numbers;
import io.lumine.mythic.bukkit.utils.plugin.ReloadableModule;
import io.lumine.mythic.core.drops.droppables.ItemDrop;
import io.lumine.mythic.core.logging.MythicLogger;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;

import org.betonquest.betonquest.id.ItemID;
import org.betonquest.betonquest.item.QuestItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MythicMobsBridge extends ReloadableModule<MythicBukkit> implements ItemSupplier {
    public MythicMobsBridge(MythicBukkit plugin) {
        super(plugin);
    }

    public void load(MythicBukkit plugin) {
        Events.subscribe(MythicDropLoadEvent.class).handler((event) -> {
            if (event.getDropName().equalsIgnoreCase("beton") || event.getDropName().equalsIgnoreCase("betonquest")) {
                event.register(new MythicMobsBridge.BetonItemsDrop(event.getDropName(), event.getConfig()));
            }

        }).bindWith(this);
    }

    public void unload() {
    }

    public String getNamespace() {
        return "betonquest";
    }

    public ItemStack getItem(String name) {
        return this.getCustomItem(name);
    }

    public boolean isSimilar(String name, ItemStack itemStack) {
        return false;
    }

    public Collection<String> getAvailableItemNames() {
        return Collections.emptyList();
    }




    public ItemStack getCustomItem(String string) {
        try {
            QuestItem item = new QuestItem(new ItemID(null, string));
            return item.generate(1);
        } catch (Exception var6) {
            Exception exception = var6;
            MythicLogger.error("Could not load drop item: " + exception.getMessage());
            return new ItemStack(Material.AIR);
        }
    }





    public class BetonItemsDrop extends ItemDrop implements IItemDrop {
        //private Type type;
        private String id;
        private QuestItem dropItem;

        public BetonItemsDrop(String line, MythicLineConfig config) {
            super(line, config);


            String typeFormat = config.getString("item");
            try {
                dropItem = new QuestItem(new ItemID(null, typeFormat));
            } catch(Exception e) {
                Bukkit.getLogger().log(Level.WARNING, "Could not parse BetonQuest Item " + typeFormat + " for Mythicmobs");
            }

        }

        public AbstractItemStack getDrop(DropMetadata metadata, double amount) {
            if (this.dropItem == null) {
                return new BukkitItemStack(Material.AIR);
            } else {


                ItemStack itemStack = this.dropItem.generate(Numbers.round(amount));
                if (itemStack == null) {

                    MythicLogger.errorCompatibility("Betonquest", "Item type not found");
                    return new BukkitItemStack(Material.AIR);
                } else {
                    return BukkitAdapter.adapt(itemStack);
                }
            }
        }





        public QuestItem getDropItem() {
            return this.dropItem;
        }
    }
}


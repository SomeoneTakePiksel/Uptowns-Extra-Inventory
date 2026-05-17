package me.piksel.uptownsExtraInventory;

import me.piksel.uptownsExtraInventory.UptownsExtraInventory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class invMenager {
    private static final int size = UptownsExtraInventory.getInstance().getConfig().getInt("size") ;

    private static final String title = UptownsExtraInventory.getInstance().getConfig().getString("title");
    public static String getTitle(){return title;}

    public static Inventory getInvOff(UUID uuid, String name) {

        Map<UUID, Inventory> map = UptownsExtraInventory.getInstance().getInvs();

        return map.computeIfAbsent(uuid, id -> {

            Inventory inv = Bukkit.createInventory(
                    null,
                    size,
                    name + "'s Inventory"
            );

            loadInv(uuid, inv);

            return inv;
        });
    }


    public static Inventory getInv(Player player) {
        Map<UUID, Inventory> map = UptownsExtraInventory.getInstance().getInvs();

        return map.computeIfAbsent(player.getUniqueId(), uuid -> {
            Inventory inv = Bukkit.createInventory(player, size, title);

            loadInv(uuid, inv);
            return inv;
        });
    }
    public static Inventory getInvUUID(UUID player) {

        UptownsExtraInventory.getInstance().saveConfig();
        UptownsExtraInventory.getInstance().reloadConfig();

        Map<UUID, Inventory> map = UptownsExtraInventory.getInstance().getInvs();

        Player newPlayer = Bukkit.getPlayer(player);
        Inventory inv = Bukkit.createInventory(newPlayer, size, title);

        loadInv(player, inv);
        return inv;

    }


    public static void saveInv(UUID uuid, Inventory inv) {
        File invFolder = new File(UptownsExtraInventory.getInstance().getDataFolder(),"PlayersInventories");
        if (!invFolder.exists()) {
            invFolder.mkdirs();
        }
        File file = new File(invFolder, uuid + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("items", inv.getContents());

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UptownsExtraInventory.getInstance().saveConfig();
        UptownsExtraInventory.getInstance().reloadConfig();
    }

    public static void loadInv(UUID uuid, Inventory inv) {

        File invFolder = new File(UptownsExtraInventory.getInstance().getDataFolder(),"PlayersInventories");
        if (!invFolder.exists()) {
            invFolder.mkdirs();
        }
        File file = new File(invFolder, uuid + ".yml");

        if (!file.exists()) return;

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ItemStack[] contents = ((List<ItemStack>) config.get("items"))
                .toArray(new ItemStack[0]);

        inv.setContents(contents);
    }
}


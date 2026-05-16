package me.piksel.uptownsExtraInventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class UptownsExtraInventory extends JavaPlugin {
    private static UptownsExtraInventory instance;
    private final Map<UUID, Inventory> invs = new HashMap<>();
    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        registerCommand("inv",new invCommand());
        getServer().getPluginManager().registerEvents(new invListener(),this);
        getLogger().info("Uptowns Extra Inventory Plugin is working!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static UptownsExtraInventory getInstance() {
        return instance;
    }

    public Map<UUID, Inventory> getInvs() {
        return invs;
    }
}

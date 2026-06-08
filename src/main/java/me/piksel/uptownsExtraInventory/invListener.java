package me.piksel.uptownsExtraInventory;

import me.piksel.uptownsExtraInventory.UptownsExtraInventory;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class invListener implements Listener {

    private static final int size = UptownsExtraInventory.getInstance().getConfig().getInt("size") ;
    private static final int blocked = UptownsExtraInventory.getInstance().getConfig().getInt("blocked-amount") ;


    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals(invMenager.getTitle())) return;
        Player player = (Player) event.getPlayer();
        Inventory inv = event.getInventory();

        invMenager.saveInv(player.getUniqueId(), inv);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Inventory inv = UptownsExtraInventory.getInstance().getInvs().get(player.getUniqueId());
        invMenager.saveInv(player.getUniqueId(), inv);

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        player.sendRichMessage("<yellow>This server is using</yellow> <green>Uptowns Extra Inventory</green> \n to use the extra inventory: <light_purple>/inv</light_purple>");
    }
    private final String customInventoryTitle = invMenager.getTitle();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory != null && customInventoryTitle.equals(event.getView().getTitle())) {
            ItemStack item = event.getInventory().getItem(event.getSlot());
            if (item.getType() == Material.BLACK_STAINED_GLASS_PANE && item.getEnchantmentLevel(Enchantment.UNBREAKING) == 10 && item != null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (customInventoryTitle.equals(event.getView().getTitle())) {
            ItemStack item = event.getCursor();
            if (item.getType() == Material.BLACK_STAINED_GLASS_PANE && item.getEnchantmentLevel(Enchantment.UNBREAKING) == 10 && item != null) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getPlayer();
        Inventory inv = invMenager.getInv(player);
        for (ItemStack i : inv.getContents()) {
            if (i.getType() == Material.AIR && i == null) continue;
            player.getWorld().dropItem(player.getLocation(), i);
        }
        inv.clear();

        invMenager.saveInv(player.getUniqueId(),inv);
    }

}

package me.piksel.uptownsExtraInventory;

import me.piksel.uptownsExtraInventory.UptownsExtraInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class invListener implements Listener {
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
        if (inv != null) {
            invMenager.saveInv(player.getUniqueId(), inv);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.sendRichMessage("<yellow>This server is using</yellow> <green>Uptowns Extra Inventory</green> \n to use the extra inventory: <purple>/inv</purple>");
    }

}

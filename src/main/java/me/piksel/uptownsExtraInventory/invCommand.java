package me.piksel.uptownsExtraInventory;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class invCommand implements BasicCommand {
    private static final int size = UptownsExtraInventory.getInstance().getConfig().getInt("size") ;
    private static final int blocked = UptownsExtraInventory.getInstance().getConfig().getInt("blocked-amount") ;
    @Override
    public void execute(CommandSourceStack sender, String[] args){

        if (!(sender.getSender() instanceof Player player)) return;
        //player.sendRichMessage("<green>Openning inventory...");
        if (!player.hasPermission("uptown.inv")){
            player.sendRichMessage("<red>You don't have the permission to use that command </red> ask the server owner :shrug:");
            return;
        }

        if (args.length == 0) {
            Inventory inv = invMenager.getInv(player);
            ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
            item.addUnsafeEnchantment(Enchantment.UNBREAKING,10);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("BLOCKED");
            item.setItemMeta(meta);

            for (int i = size -1; i >= size - blocked; i--){
                inv.setItem(i,item);
            }
            player.openInventory(inv);
            return;
        }
        if (args.length == 1){
            if (!player.hasPermission("uptown.inv.admin")){
                player.sendRichMessage("<red>You don't have the permission to use that command </red> xdddd");
                return;
            }
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (target == null ){
                player.sendRichMessage("<yellow>You didn't chose anyone");
                return;
            }
            if (!target.hasPlayedBefore()) {
                player.sendMessage("§cPlayer never joined.");
                return;
            }
            // Load saved backpack
            Inventory stored = invMenager.getInvOff(
                    target.getUniqueId(),
                    target.getName()
            );

            // Create viewer inventory
            Inventory view = Bukkit.createInventory(
                    null,
                    size,
                    target.getName() + "'s Extra Inventory(can't edit)"
            );

            // Copy items
            view.setContents(stored.getContents());

            player.openInventory(view);
            player.sendRichMessage("<green>Opened </green>" + target.getName()+ "<yellow> extra inventory</yellow>");
            return;
        }
        return;
    }

    @Override
    public Collection<String> suggest(CommandSourceStack sender, String[] args) {
        if (!sender.getSender().hasPermission("uptown.inv.admin"))return Collections.emptyList();
        List<String> names = new ArrayList<>();
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            names.add(player.getName());
        }
        return names;
    }


}

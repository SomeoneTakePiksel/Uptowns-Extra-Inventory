package me.piksel.uptownsExtraInventory;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
public class invCommand implements BasicCommand {
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
                    54,
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
}

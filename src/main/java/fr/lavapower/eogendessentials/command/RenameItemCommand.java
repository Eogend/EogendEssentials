package fr.lavapower.eogendessentials.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RenameItemCommand implements CommandExecutor, TabCompleter
{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(!(commandSender instanceof Player player))
            commandSender.sendMessage("Cette commande n'est utilisable que par un joueur.");
        else
        {
            String name = String.join(" ", strings);
            ItemStack is = player.getInventory().getItemInMainHand();
            ItemMeta im = is.getItemMeta();
            if(im == null)
                player.sendMessage("Vous devez avoir un item en main");
            else
            {
                if(player.isOp())
                    im.setDisplayName(ChatColor.GRAY + name);
                else
                    im.setDisplayName(name);
                is.setItemMeta(im);
                player.sendMessage("Rename fait !");
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        return null;
    }
}

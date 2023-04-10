package fr.lavapower.eogendessentials.command;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class DataItemCommand implements CommandExecutor, TabCompleter
{

    @Override
    public boolean onCommand(
            @NotNull
            CommandSender sender,
            @NotNull
            Command command,
            @NotNull
            String label,
            @NotNull
            String[] args)
    {
        if(!(sender instanceof Player player))
        {
            sender.sendMessage("Cette commande n'est utilisable que par un joueur !");
            return true;
        }

        ItemStack is = player.getInventory().getItemInMainHand();
        ItemMeta im = is.getItemMeta();
        if(im == null)
        {
            player.sendMessage("Vous devez avoir un item en main");
            return true;
        }

        NBTItem nbtItem = new NBTItem(is);
        if(args.length >= 1)
        {
            switch(args[0])
            {
                case "list" ->
                {
                    player.sendMessage("Tags : " + String.join(", ", nbtItem.getKeys()));
                    return true;
                }
                case "get" ->
                {
                    if(args.length < 2)
                        player.sendMessage("Usage : /dataitem get <key>");
                    else
                        player.sendMessage("Clé : " + nbtItem.getString(args[1]));
                    return true;
                }
                case "modify" ->
                {
                    if(args.length < 3)
                        player.sendMessage("Usage : /dataitem modify <key> <value>");
                    else
                    {
                        nbtItem.setString(args[1], args[2]);
                        player.getInventory().setItemInMainHand(nbtItem.getItem());
                        player.sendMessage("Clé modifiée !");
                    }
                    return true;
                }
                case "delete" ->
                {
                    if(args.length < 2)
                        player.sendMessage("Usage : /dataitem delete <key>");
                    else
                    {
                        nbtItem.removeKey(args[1]);
                        player.getInventory().setItemInMainHand(nbtItem.getItem());
                        player.sendMessage("Clé supprimée !");
                    }
                    return true;
                }
                default ->
                {
                    return false;
                }
            }
        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(
            @NotNull
            CommandSender sender,
            @NotNull
            Command command,
            @NotNull
            String label,
            @NotNull
            String[] args)
    {
        if(args.length == 1)
            return Stream.of("list", "modify", "get", "delete").filter(x -> args[0].isEmpty() || x.toLowerCase().startsWith(args[0].toLowerCase())).toList();
        return null;
    }
}

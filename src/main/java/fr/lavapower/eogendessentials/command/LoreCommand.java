package fr.lavapower.eogendessentials.command;

import fr.lavapower.eogendessentials.command.parser.CommandParserUtils;
import fr.lavapower.eogendessentials.command.parser.Result;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class LoreCommand implements CommandExecutor, TabCompleter
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

        List<String> lore = im.getLore();
        if(lore == null)
            lore = new ArrayList<>();

        if(args.length > 1)
        {
            switch(args[0])
            {
                case "add" ->
                {
                    String text = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                    if(text.equals("<empty>"))
                        lore.add(" ");
                    else
                        lore.add(text);
                    im.setLore(lore);
                    is.setItemMeta(im);
                    player.sendMessage("Lore ajouté !");
                    return true;
                }
                case "addpos" ->
                {
                    Result<Integer> posResult = CommandParserUtils.GetIntegerFromString(args[1], 1);
                    String text2 = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                    if(!posResult.Valid)
                    {
                        player.sendMessage("Merci d'entrer un numéro de ligne valide !");
                        return true;
                    }

                    if(text2.equals("<empty>"))
                        lore.add(posResult.Value - 1, " ");
                    else
                        lore.add(posResult.Value - 1, text2);
                    im.setLore(lore);
                    is.setItemMeta(im);
                    player.sendMessage("Lore ajouté !");
                    return true;
                }
                case "remove" ->
                {
                    Result<Integer> posResult2 = CommandParserUtils.GetIntegerFromString(args[1], 1);
                    if(!posResult2.Valid)
                    {
                        player.sendMessage("Merci d'entrer un numéro de ligne valide !");
                        return true;
                    }

                    if(lore.size() >= posResult2.Value)
                    {
                        lore.remove(posResult2.Value - 1);
                        im.setLore(lore);
                        is.setItemMeta(im);
                        player.sendMessage("Lore supprimé !");
                    }
                    else
                        player.sendMessage(String.format("La ligne %d n'existe pas.", posResult2.Value));
                    return true;
                }
                case "edit" ->
                {
                    Result<Integer> posResult3 = CommandParserUtils.GetIntegerFromString(args[1], 1);
                    String text3 = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                    if(!posResult3.Valid)
                    {
                        player.sendMessage("Merci d'entrer un numéro de ligne valide !");
                        return true;
                    }

                    if(lore.size() >= posResult3.Value)
                    {
                        if(text3.equals("<empty>"))
                            lore.set(posResult3.Value - 1, " ");
                        else
                            lore.set(posResult3.Value - 1, text3);
                        im.setLore(lore);
                        is.setItemMeta(im);
                        player.sendMessage("Lore edité !");
                    }
                    else
                        player.sendMessage(String.format("La ligne %d n'existe pas.", posResult3.Value));
                    return true;
                }
            }
        }
        sender.sendMessage("Usage : /lore <add|addpos|edit|remove> [<position>] [<text>]");
        return true;
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
            return Stream.of("add", "addpos", "remove", "edit").filter(x -> args[0].isEmpty() || x.toLowerCase().startsWith(args[0].toLowerCase())).toList();
        return new ArrayList<>();
    }
}

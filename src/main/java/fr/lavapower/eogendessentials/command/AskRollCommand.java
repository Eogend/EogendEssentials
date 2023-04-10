package fr.lavapower.eogendessentials.command;

import fr.lavapower.eogendessentials.command.parser.CommandParserUtils;
import fr.lavapower.eogendessentials.command.parser.Result;
import fr.lavapower.eogendessentials.utils.ComponentUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AskRollCommand implements TabCompleter, CommandExecutor
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

        if(args.length >= 1) {
            Result<Player> playerResult = CommandParserUtils.GetPlayerFromString(args[0], null);
            if(!playerResult.Valid) {
                if(args[0].equals("range")) {
                    if( args.length < 2)
                    {
                        sender.sendMessage("Usage : /askroll range <distance> [<roll>]");
                        return true;
                    }

                    Result<Integer> rangeResult = CommandParserUtils.GetIntegerFromString(args[1], 1);
                    if(!rangeResult.Valid)
                    {
                        sender.sendMessage("Merci d'entrer une distance supérieur à 1");
                        return true;
                    }

                    for(Player p: player.getWorld().getPlayers())
                    {
                        if(p.getLocation().distance(player.getLocation()) <= rangeResult.Value)
                            askroll(sender, args, 2, p);
                    }

                    return true;
                }
                sender.sendMessage("Joueur inconnu !");
                return true;
            }

            askroll(sender, args, 1, playerResult.Value);
            return true;
        }
        sender.sendMessage("Usage : /askroll <player|range> [<distance>] [<roll>]");
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
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(x -> args[0].isEmpty() || x.toLowerCase().startsWith(args[0].toLowerCase())).toList();
        return null;
    }

    private void askroll(@NotNull CommandSender sender, @NotNull String[] args, int rollIndex, Player player)
    {
        int roll = 20;
        if(args.length >= rollIndex + 1)
        {
            Result<Integer> rollResult = CommandParserUtils.GetIntegerFromString(args[rollIndex], 1, 10000);
            if(!rollResult.Valid)
            {
                sender.sendMessage("Merci d'entrer un roll compris entre 1 et 10000");
                return;
            }
            roll = rollResult.Value;
        }

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(String.format("Roll %d", roll)));
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/roll %d", roll));
        player.spigot().sendMessage(ComponentUtils.createTextComponent("Un MJ vous a demandé un roll. Cliquez ici pour l'exécuter.", ChatColor.AQUA, hoverEvent, clickEvent));
        sender.sendMessage("Roll demandé !");
    }
}

package fr.lavapower.eogendessentials.command;

import fr.lavapower.eogendessentials.EogendEssentials;
import fr.lavapower.eogendessentials.command.parser.CommandParserUtils;
import fr.lavapower.eogendessentials.command.parser.Result;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VanishCommand implements CommandExecutor, TabCompleter
{
    EogendEssentials plugin;

    public VanishCommand(EogendEssentials plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(commandSender instanceof Player player) {
            Player target = player;
            if(strings.length > 0) {
                Result<Player> playerResult = CommandParserUtils.GetPlayerFromString(strings[0], player.getLocation());
                if(playerResult.Valid)
                    target = playerResult.Value;
                else {
                    commandSender.sendMessage("Entrer un joueur valide !");
                    return true;
                }
            }

            if(plugin.getVanishManager().vanish(target))
                commandSender.sendMessage("Vanish activé !");
            else
                commandSender.sendMessage("Vanish désactivé !");

        }
        else
            commandSender.sendMessage("Cette commande n'est utilisable que par un joueur !");
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(strings.length == 1)
            return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).filter(x -> strings[0].isEmpty() || x.toLowerCase().startsWith(strings[0].toLowerCase())).toList();
        return null;
    }
}

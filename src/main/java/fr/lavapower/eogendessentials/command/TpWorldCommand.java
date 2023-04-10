package fr.lavapower.eogendessentials.command;

import fr.lavapower.eogendessentials.command.parser.CommandParserUtils;
import fr.lavapower.eogendessentials.command.parser.Result;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TpWorldCommand implements CommandExecutor, TabCompleter
{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Location from;
        if(commandSender instanceof Player player)
            from = player.getLocation();
        else if(commandSender instanceof BlockCommandSender commandBlock)
            from = commandBlock.getBlock().getLocation();
        else {
            commandSender.sendMessage("Cette commande n'est utilisable que par un joueur ou un commande block !");
            return true;
        }

        if(strings.length == 5)
        {
            Result<Player> targetResult = CommandParserUtils.GetPlayerFromString(strings[0], from);
            if(!targetResult.Valid) {
                commandSender.sendMessage("Joueur inconnu !");
                return true;
            }

            Result<World> worldResult = CommandParserUtils.GetWorldFromString(strings[1]);
            if(!worldResult.Valid) {
                commandSender.sendMessage("Monde inconnu !");
                return true;
            }

            Result<Integer> xResult = CommandParserUtils.GetIntegerFromString(strings[2]);
            Result<Integer> yResult = CommandParserUtils.GetIntegerFromString(strings[3]);
            Result<Integer> zResult = CommandParserUtils.GetIntegerFromString(strings[4]);
            if(!(xResult.Valid && yResult.Valid && zResult.Valid)){
                commandSender.sendMessage("Position non valide !");
                return true;
            }

            targetResult.Value.teleport(new Location(worldResult.Value, xResult.Value, yResult.Value, zResult.Value), PlayerTeleportEvent.TeleportCause.COMMAND);
            commandSender.sendMessage("Téléportation effectuée !");
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(strings.length == 1)
            return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).filter(x -> strings[0].isEmpty() || x.toLowerCase().startsWith(strings[0].toLowerCase())).toList();
        if(strings.length == 2)
            return Bukkit.getWorlds().stream().map(WorldInfo::getName).filter(x -> strings[1].isEmpty() || x.toLowerCase().startsWith(strings[1].toLowerCase())).toList();
        return null;
    }
}

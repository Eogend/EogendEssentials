package fr.lavapower.eogendessentials.command;

import fr.lavapower.eogendessentials.command.parser.CommandParserUtils;
import fr.lavapower.eogendessentials.command.parser.Result;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WalkCommand implements CommandExecutor, TabCompleter
{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(commandSender instanceof Player player) {
            if(strings.length == 1) {
                Result<Float> valueResult = CommandParserUtils.GetFloatFromString(strings[0], 0, 1);
                if(valueResult.Valid) {
                    float value = valueResult.Value * 0.2f;
                    player.sendMessage("Vous marchez désormais plus lentement");
                    player.setWalkSpeed(value);
                }
                else {
                    commandSender.sendMessage("Entrer une vitesse entre 0 et 1 !");
                    return true;
                }
            }
            else {
                if(player.getWalkSpeed() != 0.2f)
                {
                    player.sendMessage("Vous marchez désormais normalement.");
                    player.setWalkSpeed(0.2f);
                }
                else
                {
                    player.sendMessage("Vous marchez désormais plus lentement");
                    player.setWalkSpeed(0.1f);
                }
            }
        }
        else
            commandSender.sendMessage("Cette commande n'est utilisable que par un joueur !");
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        return null;
    }
}

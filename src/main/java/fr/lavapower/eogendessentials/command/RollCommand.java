package fr.lavapower.eogendessentials.command;

import fr.lavapower.eogendessentials.EogendEssentials;
import fr.lavapower.eogendessentials.command.parser.CommandParserUtils;
import fr.lavapower.eogendessentials.command.parser.Result;
import fr.lavapower.eogendessentials.utils.ChatUtils;
import fr.lavapower.eogendessentials.utils.ComponentUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RollCommand implements CommandExecutor, TabCompleter
{
    EogendEssentials plugin;

    public RollCommand(EogendEssentials eogendPlugin2)
    {
        this.plugin = eogendPlugin2;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(commandSender instanceof Player player) {
            int max = 20;
            if(strings.length > 0) {
                Result<Integer> maxResult = CommandParserUtils.GetIntegerFromString(strings[0]);
                if(maxResult.Valid)
                    max = maxResult.Value;
                else {
                    commandSender.sendMessage("Entrer un roll valide !");
                    return true;
                }
            }

            int roll = getRoll(max);
            ArrayList<TextComponent> response = new ArrayList<>();
            response.add(ComponentUtils.createTextComponent(player.getDisplayName() + " a tiré " + roll + " sur son dé à " + max + " face(s)", ChatColor.LIGHT_PURPLE));
            ChatUtils.sendProximityMessage(plugin, player, response, 50);
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
    private static int getRoll(int max) {
        return 1 + (int)(Math.random() * max);
    }
}
package fr.lavapower.eogendessentials.command;

import fr.lavapower.eogendessentials.utils.ComponentUtils;
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

import java.util.ArrayList;
import java.util.List;

public class PlayersCommand implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        ArrayList<TextComponent> components = new ArrayList<>();
        components.add(new TextComponent("Liste des joueurs connectés : "));

        for(Player p : Bukkit.getOnlinePlayers()) {
            components.add(ComponentUtils.createTextComponent(p.getDisplayName(), new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(p.getName()))));
            components.add(new TextComponent(" "));
        }

        commandSender.spigot().sendMessage(components.toArray(new TextComponent[0]));
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        return null;
    }
}

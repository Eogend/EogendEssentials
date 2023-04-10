package fr.lavapower.eogendessentials.command;

import fr.lavapower.eogendessentials.EogendEssentials;
import fr.lavapower.eogendessentials.utils.ChatUtils;
import fr.lavapower.eogendessentials.utils.ComponentUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SendLinkCommand implements CommandExecutor, TabCompleter
{
    EogendEssentials plugin;

    public SendLinkCommand(EogendEssentials eogendPlugin2)
    {
        this.plugin = eogendPlugin2;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(commandSender instanceof Player player) {
            String link = String.join(" ", strings);
            List<TextComponent> comps = List.of(
                    new TextComponent("[LIEN] " + player.getDisplayName() + ChatColor.RESET + " : "),
                    ComponentUtils.createTextComponent("CLIQUEZ", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Cliquez pour ouvrir")), new ClickEvent(ClickEvent.Action.OPEN_URL, link))
            );
            ChatUtils.sendProximityMessage(plugin, player, comps, 50);
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

package fr.lavapower.eogendessentials.utils;

import fr.lavapower.eogendessentials.EogendEssentials;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatUtils
{
    public static void sendProximityMessage(EogendEssentials plugin, Player player, List<TextComponent> components, int distance)
    {
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(String.format("%s", player.getName())));
        components.forEach(x -> x.setHoverEvent(hoverEvent));

        Bukkit.getScheduler().runTask(plugin, () -> {
            for(Player p : Bukkit.getOnlinePlayers())
            {
                if(distance == 0 || (p.getWorld() == player.getWorld() && p.getLocation().distance(player.getLocation()) <= distance))
                {
                    p.spigot().sendMessage(components.toArray(new BaseComponent[0]));
                }
            }
        });
    }

}

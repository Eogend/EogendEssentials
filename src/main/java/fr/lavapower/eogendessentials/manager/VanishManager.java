package fr.lavapower.eogendessentials.manager;

import fr.lavapower.eogendessentials.EogendEssentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VanishManager
{
    private final List<Player> vanishedPlayers = new ArrayList<>();
    private final EogendEssentials plugin;

    public VanishManager(EogendEssentials eogendPlugin2) {
        this.plugin = eogendPlugin2;
    }

    public boolean vanish(Player player) {
        if(vanishedPlayers.contains(player)) {
            for(Player p: Bukkit.getOnlinePlayers())
                p.showPlayer(plugin, player);
            vanishedPlayers.remove(player);
            return false;
        }

        for(Player p: Bukkit.getOnlinePlayers())
            p.hidePlayer(plugin, player);
        vanishedPlayers.add(player);
        return true;
    }

    public void removeVanish(Player player) {
        vanishedPlayers.remove(player);
    }

    public List<Player> getVanishedPlayers() {
        return vanishedPlayers;
    }
}

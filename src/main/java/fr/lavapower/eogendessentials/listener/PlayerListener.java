package fr.lavapower.eogendessentials.listener;

import fr.lavapower.eogendessentials.EogendEssentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener {

    private final EogendEssentials plugin;

    public PlayerListener(EogendEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        plugin.getLogger().info(event.getEntity().getDisplayName() + " : DEATH - " + event.getEntity().getLastDamageCause().getCause());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            if(e.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL)
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if(plugin.getConfiguration().fly().contains(event.getPlayer().getUniqueId().toString()))
            event.getPlayer().setAllowFlight(true);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Player player = event.getPlayer();
            if(plugin.getConfiguration().fly().contains(player.getUniqueId().toString()))
                player.setAllowFlight(true);
        }, 10L);
    }

    @EventHandler
    public void onPlayerSwapHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if(player.isGliding())
        {
            if(player.getVelocity().length() < player.getLocation().getDirection().multiply(2).length())
                player.setVelocity(player.getLocation().getDirection().multiply(2));
        }
    }
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(plugin.getConfiguration().fly().contains(player.getUniqueId().toString()))
                player.setAllowFlight(true);

            for(Player p: plugin.getVanishManager().getVanishedPlayers())
                player.hidePlayer(plugin, p);
        }, 10L);
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        plugin.getVanishManager().removeVanish(event.getPlayer());
    }

}

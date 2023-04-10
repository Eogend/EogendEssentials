package fr.lavapower.eogendessentials.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class OtherListener implements Listener
{
    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        if(event.getBlockPlaced().getBlockData().getMaterial() == Material.TRIPWIRE_HOOK)
            event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasBlock()) {
            if(event.getClickedBlock().getBlockData().getMaterial().toString().contains("BED") && event.getClickedBlock().getBlockData().getMaterial() != Material.BEDROCK && !event.getPlayer().isSneaking())
            {
                event.getPlayer().sendMessage("[HRP] Fonctionnalité désactivée. Utilisez le /lay");
                event.setCancelled(true);
            }
        }
    }
}

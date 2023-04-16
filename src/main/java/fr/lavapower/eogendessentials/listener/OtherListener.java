package fr.lavapower.eogendessentials.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class OtherListener implements Listener
{
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Goat || event.getRightClicked() instanceof Cow ||
                event.getRightClicked() instanceof Sheep || event.getRightClicked() instanceof Chicken ||
                event.getRightClicked() instanceof Pig) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ride "+event.getPlayer().getName()+" mount "+event.getRightClicked().getUniqueId());
        }
    }

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

            if(event.getClickedBlock().getBlockData().getMaterial().toString().contains("SIGN"))
                event.getPlayer().openSign((Sign) event.getClickedBlock().getState());
        }
    }
}

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
import org.bukkit.inventory.ItemStack;

public class OtherListener implements Listener
{
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Entity rightClicked = event.getRightClicked();
        if(rightClicked instanceof Goat || rightClicked instanceof Cow ||
                rightClicked instanceof Sheep || rightClicked instanceof Chicken ||
                rightClicked instanceof Pig || rightClicked instanceof Bee) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ride "+event.getPlayer().getName()+" mount "+ rightClicked.getUniqueId());
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

            else if(event.getClickedBlock().getBlockData().getMaterial().toString().contains("SIGN") && !event.getPlayer().isSneaking())
                event.getPlayer().openSign((Sign) event.getClickedBlock().getState());

            else if(event.getClickedBlock().getBlockData().getMaterial() == Material.LANTERN && !event.hasItem())
            {
                event.getClickedBlock().setType(Material.AIR);
                event.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.LANTERN));
            }
        }
    }
}

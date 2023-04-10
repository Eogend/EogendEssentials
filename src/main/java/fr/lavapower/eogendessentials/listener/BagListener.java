package fr.lavapower.eogendessentials.listener;

import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BagListener implements Listener
{
    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
        {
            ItemStack backpack = event.getItem();
            if(backpack != null && backpack.getType() == Material.LEATHER && backpack.hasItemMeta() && backpack.getItemMeta().getDisplayName().contains("Sac"))
            {
                event.setCancelled(true);

                ItemStack[] stacks = new ItemStack[9];
                for(int i = 0; i < 9; i++)
                {
                    int finalI = i;
                    stacks[i] = NBT.get(backpack, nbt -> nbt.getItemStack("item" + finalI));
                }
                Inventory inventory = Bukkit.createInventory(null, 9, "Sac");
                inventory.setContents(stacks);
                event.getPlayer().openInventory(inventory);
            }
        }
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event)
    {
        if(!event.getView().getTitle().contains("Sac"))
            return;

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        ItemStack[] inventory = event.getInventory().getContents();
        NBT.modify(item, nbt -> {
            for(int i = 0; i < inventory.length; i++)
                nbt.setItemStack("item" + i, inventory[i]);
        });
        event.getPlayer().getInventory().setItemInMainHand(item);
    }
}

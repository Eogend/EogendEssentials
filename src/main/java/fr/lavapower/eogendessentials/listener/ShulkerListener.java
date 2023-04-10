package fr.lavapower.eogendessentials.listener;

import org.bukkit.Bukkit;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class ShulkerListener implements Listener
{
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR && event.getPlayer().isSneaking() && event.hasItem() && event.getItem().getType().toString().endsWith("SHULKER_BOX")) {
            event.setCancelled(true);
            ShulkerBox shulkerBox = (ShulkerBox)((BlockStateMeta) event.getItem().getItemMeta()).getBlockState();
            Inventory inventory = Bukkit.createInventory(null, InventoryType.SHULKER_BOX, event.getPlayer().getName() + " : " + formatShulkerPlaceholder("Shulker", event.getItem()));
            inventory.setContents(shulkerBox.getInventory().getContents());
            event.getPlayer().openInventory(inventory);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if(!event.getView().getTitle().contains(event.getWhoClicked().getName() + " : ")) return;

        ItemStack clickedItem = event.getCurrentItem();
        if(clickedItem != null && clickedItem.getType().toString().endsWith("SHULKER_BOX"))
            event.setCancelled(true);
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        if(!event.getView().getTitle().contains(event.getPlayer().getName() + " : ")) return;

        Inventory inventory = event.getInventory();
        Player player = (Player)event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        BlockStateMeta bsm = (BlockStateMeta) item.getItemMeta();
        ShulkerBox box = (ShulkerBox)bsm.getBlockState();
        box.getInventory().setContents(inventory.getContents());
        bsm.setBlockState(box);
        item.setItemMeta(bsm);
        player.getInventory().setItemInMainHand(item);
    }

    private static String getShulkerPlaceholderReplacement(ItemStack shulker) {
        if (shulker == null) return "invalid";
        if (shulker.getItemMeta() == null || !shulker.getItemMeta().hasDisplayName())
            return InventoryType.SHULKER_BOX.getDefaultTitle();
        return shulker.getItemMeta().getDisplayName();
    }

    private static String formatShulkerPlaceholder(String message, ItemStack shulker)
    {
        if(message.isEmpty())
            return message;
        if(!message.contains("%shulker_name%"))
            return message;
        return message.replace("%shulker_name%", getShulkerPlaceholderReplacement(shulker));
    }
}

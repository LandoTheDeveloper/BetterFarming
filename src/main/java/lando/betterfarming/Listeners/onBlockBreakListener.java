package lando.betterfarming.Listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class onBlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        Block block = e.getBlock();
        Player player = e.getPlayer();
        World world = e.getBlock().getWorld();
        Location blockLocation = e.getBlock().getLocation();

        // If player is holding a hoe, plugin activates
        if (player.getInventory().getItemInMainHand().getType().toString().contains("HOE")) {

            // Check if block can be aged (crops)
            if (block.getBlockData() instanceof Ageable) {
                Ageable ageable = (Ageable) block.getBlockData();

                // Check if crop is fully grown, if so, drop items
                if (ageable.getAge() >= ageable.getMaximumAge()) {

                    // Drop Items
                    for (ItemStack drop : block.getDrops()) {
                        block.getWorld().dropItemNaturally(block.getLocation(), drop);
                    }

                    Material seedMaterial = block.getType();
                    ItemStack seedItem = null;

                    if (seedMaterial == Material.WHEAT){
                        seedItem = new ItemStack(Material.WHEAT_SEEDS, 1);
                    } else if (seedMaterial == Material.POTATOES) {
                        seedItem = new ItemStack(Material.POTATO, 1);
                    } else {
                        seedItem = new ItemStack(Material.CARROT, 1);
                    }


                    if (player.getInventory().containsAtLeast(seedItem, 1)) {
                        player.getInventory().removeItem(seedItem);
                        // Replace the block with the corresponding crop type (e.g., wheat)
                        if (seedMaterial == Material.WHEAT) {
                            world.setType(blockLocation, Material.WHEAT);
                        } else if (seedMaterial == Material.CARROTS) {
                            world.setType(blockLocation, Material.CARROTS);
                        } else if (seedMaterial == Material.POTATOES) {
                            world.setType(blockLocation, Material.POTATOES);
                        }
                        // Cancel break if in inventory
                        e.setCancelled(true);
                    }



                    // Decrease durability by 1
                    short hoeDurability = player.getInventory().getItemInMainHand().getDurability();
                    player.getInventory().getItemInMainHand().setDurability((short) (hoeDurability + 1));

                } else
                    e.setCancelled(true); // Cancel break if not max age
            }
        }
    }
}

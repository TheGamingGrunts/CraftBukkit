package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityFireworks;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;

import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class CraftFirework extends CraftEntity implements Firework {
    private static final int FIREWORK_ITEM_INDEX = 8;

    private final CraftItemStack item;

    public CraftFirework(CraftServer server, EntityFireworks entity) {
        super(server, entity);

        ItemStack item = getHandle().getDataWatcher().f(FIREWORK_ITEM_INDEX);

        if (item == null) {
            item = new ItemStack(Item.FIREWORKS);
            getHandle().getDataWatcher().a(FIREWORK_ITEM_INDEX, item); // register
        }

        this.item = CraftItemStack.asCraftMirror(item);

        // Ensure the item is a firework...
        if (this.item.getType() != Material.FIREWORK) {
            this.item.setType(Material.FIREWORK);
        }
    }

    @Override
    public EntityFireworks getHandle() {
        return (EntityFireworks) entity;
    }

    @Override
    public String toString() {
        return "CraftFirework";
    }

    public EntityType getType() {
        return EntityType.FIREWORK;
    }

    public FireworkMeta getFireworkMeta() {
        return (FireworkMeta) item.getItemMeta();
    }

    public void setFireworkMeta(FireworkMeta meta) {
        item.setItemMeta(meta);

        // Copied from EntityFireworks constructor, update firework lifetime/power
        Random random = new Random();
        getHandle().b = 10 * meta.getPower() + random.nextInt(6) + random.nextInt(7);

        getHandle().getDataWatcher().h(FIREWORK_ITEM_INDEX); // Update
    }
}
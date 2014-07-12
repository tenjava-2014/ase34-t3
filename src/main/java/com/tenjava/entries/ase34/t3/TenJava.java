package com.tenjava.entries.ase34.t3;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import net.minecraft.server.v1_7_R3.Entity;
import net.minecraft.server.v1_7_R3.NBTTagCompound;
import net.minecraft.server.v1_7_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.util.concurrent.UncheckedExecutionException;
import com.tenjava.entries.ase34.t3.nms.BequeathingEntityChicken;
import com.tenjava.entries.ase34.t3.nms.BequeathingEntityCow;
import com.tenjava.entries.ase34.t3.nms.BequeathingEntityPig;
import com.tenjava.entries.ase34.t3.nms.BequeathingEntitySheep;
import com.tenjava.entries.ase34.t3.properties.GeneticProperties;

public class TenJava extends JavaPlugin implements Listener {

    private static Plugin instance;

    private HashMap<EntityType, Class> bequeathingEntities = new HashMap<>();

    public static Plugin getInstance() {
        return instance;
    }

    public void onEnable() {
        TenJava.instance = this;
        this.getServer().getPluginManager().registerEvents(this, this);
        try {
            EntityRegistrator.registerEntity("BequeathingPig", 90, BequeathingEntityPig.class);
            EntityRegistrator.registerEntity("BequeathingChicken", 93, BequeathingEntityChicken.class);
        } catch (Exception e) {
            throw new UncheckedExecutionException(e);
        }

        bequeathingEntities.put(EntityType.PIG, BequeathingEntityPig.class);
        bequeathingEntities.put(EntityType.CHICKEN, BequeathingEntityChicken.class);
        bequeathingEntities.put(EntityType.COW, BequeathingEntityCow.class);
        bequeathingEntities.put(EntityType.SHEEP, BequeathingEntitySheep.class);

        Bukkit.getWorlds().stream().flatMap(world -> world.getEntities().stream()).filter(entity -> {
            return bequeathingEntities.containsKey(entity.getType());
        }).forEach(entity -> injectGenetics(entity));
    }

    @Override
    public void onDisable() {
        Bukkit.getWorlds().stream().flatMap(world -> world.getEntities().stream()).filter(entity -> {
            return ((CraftEntity) entity).getHandle() instanceof GeneticEntity;
        }).forEach(entity -> ejectGenetics(((CraftEntity) entity).getHandle()));
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent ev) {
        if (((CraftEntity) ev.getEntity()).getHandle() instanceof GeneticEntity) {
            return;
        }

        if (!bequeathingEntities.keySet().contains(ev.getEntityType())) {
            return;
        }

        ev.setCancelled(true);
        System.out.println("event caught");

        World world = ((CraftWorld) ev.getLocation().getWorld()).getHandle();

        Entity entity;
        try {
            Constructor<Entity> ctor = bequeathingEntities.get(ev.getEntityType()).getConstructor(World.class);
            entity = ctor.newInstance(world);
        } catch (Exception e) {
            throw new UncheckedExecutionException(e);
        }

        entity.setLocation(ev.getLocation().getX(), ev.getLocation().getY(), ev.getLocation().getZ(), ev.getLocation()
                .getYaw(), ev.getLocation().getPitch());
        world.addEntity(entity);
    }

    public void injectGenetics(org.bukkit.entity.Entity oldEntity) {
        NBTTagCompound data = new NBTTagCompound();
        Location location = oldEntity.getLocation();
        ((CraftEntity) oldEntity).getHandle().e(data);

        oldEntity.remove();

        World world = ((CraftWorld) location.getWorld()).getHandle();

        Entity entity;
        try {
            Constructor<Entity> ctor = bequeathingEntities.get(oldEntity.getType()).getConstructor(World.class);
            entity = ctor.newInstance(world);
        } catch (Exception e) {
            throw new UncheckedExecutionException(e);
        }

        entity.f(data);
        entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        world.addEntity(entity);
    }

    public void ejectGenetics(Entity entity) {
        if (!(entity instanceof GeneticEntity)) {
            return;
        }

        NBTTagCompound data = new NBTTagCompound();
        entity.e(data);

        CraftEntity bukkitEntity = entity.getBukkitEntity();
        EntityType type = bukkitEntity.getType();
        entity.getBukkitEntity().remove();

        org.bukkit.entity.Entity newEntity = bukkitEntity.getWorld().spawnEntity(bukkitEntity.getLocation(), type);
        ((CraftEntity) newEntity).getHandle().f(data);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent ev) {
        ItemStack itemInHand = ev.getPlayer().getItemInHand();
        if (itemInHand.getType() == Material.STICK) {
            Entity handle = ((CraftEntity) ev.getRightClicked()).getHandle();

            if (handle instanceof GeneticEntity) {
                GeneticProperties properties = ((GeneticEntity) handle).getGeneticProperties();

                properties
                        .entrySet()
                        .stream()
                        .forEach(
                            entry -> {
                                double value = Math.round(entry.getValue().getValue() * 100 * 100) / 100D;

                                String formatted = String.format("%.2f", value);
                                ev.getPlayer().sendMessage(
                                    ChatColor.GOLD + "" + entry.getKey() + ChatColor.GRAY + ": " + formatted + "%");
                            });
            }
        }
    }
}

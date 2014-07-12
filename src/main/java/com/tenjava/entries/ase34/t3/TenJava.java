package com.tenjava.entries.ase34.t3;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.util.concurrent.UncheckedExecutionException;
import com.tenjava.entries.ase34.t3.nms.BequeathingEntityPig;

public class TenJava extends JavaPlugin implements Listener {

    private static Plugin instance;

    public static Plugin getInstance() {
        return instance;
    }

    public void onEnable() {
        TenJava.instance = this;
        this.getServer().getPluginManager().registerEvents(this, this);
        try {
            EntityRegistrator.registerEntity("BequeathingPig", 90, BequeathingEntityPig.class);
        } catch (Exception e) {
            throw new UncheckedExecutionException(e);
        }
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent ev) {
        Class<?> entityClass = ((CraftEntity) ev.getEntity()).getHandle().getClass();
        System.out.println(entityClass.toString());
        if (ev.getEntityType() == EntityType.PIG && entityClass != BequeathingEntityPig.class) {
            ev.setCancelled(true);
            System.out.println("event caught");
            BequeathingEntityPig pig = new BequeathingEntityPig(((CraftWorld) ev.getLocation()
                    .getWorld()).getHandle());
            pig.setLocation(ev.getLocation().getX(), ev.getLocation().getY(), ev.getLocation()
                    .getZ(), 0F, 0F);
            ((CraftWorld) ev.getLocation().getWorld()).getHandle().addEntity(pig);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent ev) {
        if (ev.getPlayer().getItemInHand().getType() == Material.STICK) {
            if ()
        }
    }
}

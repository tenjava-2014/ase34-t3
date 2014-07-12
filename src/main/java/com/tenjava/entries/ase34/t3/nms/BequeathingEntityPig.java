package com.tenjava.entries.ase34.t3.nms;

import net.minecraft.server.v1_7_R3.EntityAgeable;
import net.minecraft.server.v1_7_R3.EntityPig;
import net.minecraft.server.v1_7_R3.World;

import org.bukkit.Bukkit;

import com.tenjava.entries.ase34.t3.TenJava;

public class BequeathingEntityPig extends EntityPig {

    public BequeathingEntityPig(World world) {
        super(world);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable) {
        // TODO Auto-generated method stub
        System.out.println("breeeding!");
        EntityAgeable child = new BequeathingEntityPig(this.world);

        Bukkit.getScheduler().scheduleSyncDelayedTask(TenJava.getInstance(), new Runnable() {

            @Override
            public void run() {
                child.setAge(-60);
            }

        }, 1);
        return child;
    }

}

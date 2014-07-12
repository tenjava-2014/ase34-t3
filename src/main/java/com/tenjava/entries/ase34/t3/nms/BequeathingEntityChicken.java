package com.tenjava.entries.ase34.t3.nms;

import net.minecraft.server.v1_7_R3.EntityAgeable;
import net.minecraft.server.v1_7_R3.EntityChicken;
import net.minecraft.server.v1_7_R3.NBTTagCompound;
import net.minecraft.server.v1_7_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import com.tenjava.entries.ase34.t3.GeneticEntity;
import com.tenjava.entries.ase34.t3.TenJava;
import com.tenjava.entries.ase34.t3.properties.GeneticProperties;

public class BequeathingEntityChicken extends EntityChicken implements GeneticEntity {

    public BequeathingEntityChicken(World world) {
        super(world);
    }

    protected GeneticProperties geneticProperties = GeneticProperties.getDefault(EntityType.CHICKEN);

    @Override
    public EntityAgeable createChild(EntityAgeable otherparent) {
        BequeathingEntityChicken self = this;
        BequeathingEntityChicken child = new BequeathingEntityChicken(this.world);

        Bukkit.getScheduler().scheduleSyncDelayedTask(TenJava.getInstance(), new Runnable() {

            @Override
            public void run() {
                GeneticProperties.apply(self, (GeneticEntity) otherparent, child);
            }

        }, 1);

        return child;
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.getGeneticProperties().read(nbttagcompound);
    }

    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        this.getGeneticProperties().write(nbttagcompound);
    }

    @Override
    public GeneticProperties getGeneticProperties() {
        return geneticProperties;
    }

    @Override
    public void setGeneticProperties(GeneticProperties geneticProperties) {
        this.geneticProperties = (GeneticProperties) geneticProperties;
    }

    @Override
    protected void dropDeathLoot(boolean flag, int i) {
        int bonus = ((GeneticPropertySize) geneticProperties.get(GeneticProperties.Properties.SIZE))
                .generateDeatlootBonus();
        super.dropDeathLoot(flag, i + bonus);
    }

}

package com.tenjava.entries.ase34.t3.nms;

import net.minecraft.server.v1_7_R3.EntityAgeable;
import net.minecraft.server.v1_7_R3.EntityPig;
import net.minecraft.server.v1_7_R3.NBTTagCompound;
import net.minecraft.server.v1_7_R3.World;

import org.bukkit.Bukkit;

import com.tenjava.entries.ase34.t3.GeneticEntity;
import com.tenjava.entries.ase34.t3.TenJava;
import com.tenjava.entries.ase34.t3.properties.GeneticDeathDropProperties;
import com.tenjava.entries.ase34.t3.properties.GeneticProperties;

public class BequeathingEntityPig extends EntityPig implements GeneticEntity {

    public BequeathingEntityPig(World world) {
        super(world);
    }

    protected GeneticDeathDropProperties geneticProperties = new GeneticDeathDropProperties();

    @Override
    public EntityAgeable createChild(EntityAgeable otherparent) {
        BequeathingEntityPig self = this;
        BequeathingEntityPig child = new BequeathingEntityPig(this.world);

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
        // read
        super.a(nbttagcompound);
        this.getGeneticProperties().read(nbttagcompound);
    }

    @Override
    public void b(NBTTagCompound nbttagcompound) {
        // write
        super.b(nbttagcompound);
        this.getGeneticProperties().write(nbttagcompound);
    }

    @Override
    public GeneticProperties getGeneticProperties() {
        return geneticProperties;
    }

    @Override
    public void setGeneticProperties(GeneticProperties geneticProperties) {
        this.geneticProperties = (GeneticDeathDropProperties) geneticProperties;
    }

    @Override
    protected void dropDeathLoot(boolean flag, int i) {
        super.dropDeathLoot(flag, i + geneticProperties.generateDeatlootBonus());
    }
}

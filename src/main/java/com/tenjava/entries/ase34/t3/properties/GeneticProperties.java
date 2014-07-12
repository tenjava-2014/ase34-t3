package com.tenjava.entries.ase34.t3.properties;

import net.minecraft.server.v1_7_R3.EntityAgeable;
import net.minecraft.server.v1_7_R3.NBTTagCompound;

import com.tenjava.entries.ase34.t3.GeneticEntity;

public class GeneticProperties {

    public static final float DEFAULT_GROWTH = -24000;
    private static final int DEFAULT_FERTILITY = 6000;

    protected float growth = 1;
    protected float fertility = 1;

    public GeneticProperties() {
    }

    public GeneticProperties(GeneticProperties parent1, GeneticProperties parent2) {
        // TODO
    }

    /**
     * Reads from an {@link NBTTagCompound}.
     * 
     * @param nbttagcompound
     */
    public void read(NBTTagCompound nbttagcompound) {
        // TODO Auto-generated method stub
        NBTTagCompound compound = nbttagcompound.getCompound("gene");
        this.growth = compound.getFloat("growth");
    }

    /**
     * Writes to an {@link NBTTagCompound}.
     * 
     * @param nbttagcompound
     */
    public void write(NBTTagCompound nbttagcompound) {
        // TODO Auto-generated method stub
        NBTTagCompound compound = nbttagcompound.getCompound("gene");
        compound.setFloat("growth", this.growth);
    }

    /**
     * Applies this {@link GeneticProperties} to an {@link EntityAgeable}
     * 
     * @param child
     */
    public static void apply(GeneticEntity parent1, GeneticEntity parent2, GeneticEntity child) {
        GeneticProperties inherited = new GeneticProperties(parent1.getGeneticProperties(),
                parent2.getGeneticProperties());
        child.setGeneticProperties(inherited);

        child.setAge((int) (DEFAULT_GROWTH / inherited.growth));
        parent1.setAge((int) (DEFAULT_FERTILITY / parent1.getGeneticProperties().fertility));
        parent2.setAge((int) (DEFAULT_FERTILITY / parent2.getGeneticProperties().fertility));
    }

    public float getGrowth() {
        return growth;
    }

    public void setGrowth(float growth) {
        this.growth = growth;
    }

    public float getFertility() {
        return fertility;
    }

    public void setFertility(float fertility) {
        this.fertility = fertility;
    }

}

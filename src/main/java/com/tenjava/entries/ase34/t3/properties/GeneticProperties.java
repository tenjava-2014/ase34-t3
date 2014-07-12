package com.tenjava.entries.ase34.t3.properties;

import net.minecraft.server.v1_7_R3.NBTTagCompound;

import com.tenjava.entries.ase34.t3.GeneticEntity;

public class GeneticProperties {

    public static final float DEFAULT_GROWTH = -24000;
    private static final int DEFAULT_FERTILITY = 6000;

    protected double growth = 1;
    protected double fertility = 1;

    public GeneticProperties() {
    }

    public GeneticProperties(GeneticProperties parent1, GeneticProperties parent2) {
        this.growth = (parent1.growth + parent2.growth) / 2 + ((Math.random() * 0.2) - 0.5);
        this.fertility = (parent1.fertility + parent2.fertility) / 2
                + ((Math.random() * 0.2) - 0.5);
    }

    /**
     * Reads from an {@link NBTTagCompound}.
     * 
     * @param nbttagcompound
     */
    public void read(NBTTagCompound nbttagcompound) {
        NBTTagCompound compound = nbttagcompound.getCompound("gene");
        this.growth = compound.getDouble("growth");
        this.fertility = compound.getDouble("fertility");
    }

    /**
     * Writes to an {@link NBTTagCompound}.
     * 
     * @param nbttagcompound
     */
    public void write(NBTTagCompound nbttagcompound) {
        NBTTagCompound compound = nbttagcompound.getCompound("gene");
        compound.setDouble("growth", this.growth);
        compound.setDouble("fertility", this.fertility);
    }

    /**
     * Applies this {@link GeneticProperties} the parents and its newborn child. This should get
     * called one tick after the child has been spawned.
     * 
     * @param child
     */
    public static void apply(GeneticEntity parent1, GeneticEntity parent2, GeneticEntity child) {
        GeneticProperties inherited = new GeneticProperties(parent1.getGeneticProperties(),
                parent2.getGeneticProperties());
        child.setGeneticProperties(inherited);

        // System.out.println("child " + (int) (DEFAULT_GROWTH / inherited.growth));
        // System.out.println("parent1 "
        // + (int) (DEFAULT_FERTILITY / parent1.getGeneticProperties().fertility));
        // System.out.println("parent2 "
        // + (int) (DEFAULT_FERTILITY / parent2.getGeneticProperties().fertility));

        child.setAge((int) (DEFAULT_GROWTH / inherited.growth));
        parent1.setAge((int) (DEFAULT_FERTILITY / parent1.getGeneticProperties().fertility));
        parent2.setAge((int) (DEFAULT_FERTILITY / parent2.getGeneticProperties().fertility));
    }

    public double getGrowth() {
        return growth;
    }

    public void setGrowth(double growth) {
        this.growth = growth;
    }

    public double getFertility() {
        return fertility;
    }

    public void setFertility(double fertility) {
        this.fertility = fertility;
    }

    @Override
    public String toString() {
        return "GeneticProperties [growth=" + growth + ", fertility=" + fertility + "]";
    }

}

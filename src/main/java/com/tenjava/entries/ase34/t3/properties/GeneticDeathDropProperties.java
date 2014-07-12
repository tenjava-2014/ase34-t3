package com.tenjava.entries.ase34.t3.properties;

import net.minecraft.server.v1_7_R3.NBTTagCompound;

public class GeneticDeathDropProperties extends GeneticProperties {

    protected double deathlootOutput = 1;

    public double getDeathlootOutput() {
        return deathlootOutput;
    }

    public void setDeathlootOutput(double deathlootOutput) {
        this.deathlootOutput = deathlootOutput;
    }

    @Override
    public void read(NBTTagCompound nbttagcompound) {
        super.read(nbttagcompound);
        this.deathlootOutput = nbttagcompound.getCompound("gene").getDouble("deathlootOutput");
    }

    @Override
    public void write(NBTTagCompound nbttagcompound) {
        super.write(nbttagcompound);
        nbttagcompound.getCompound("gene").setDouble("deathlootOutput", this.deathlootOutput);
    }

    public int generateDeatlootBonus() {
        return (int) Math.round(this.deathlootOutput * (Math.random() * 0.5));
    }

    @Override
    public String toString() {
        return "GeneticDeathDropProperties [deathlootOutput=" + deathlootOutput + ", growth="
                + growth + ", fertility=" + fertility + "]";
    }
}

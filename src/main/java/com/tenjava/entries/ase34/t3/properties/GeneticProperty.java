package com.tenjava.entries.ase34.t3.properties;

import net.minecraft.server.v1_7_R3.NBTTagCompound;

public abstract class GeneticProperty {

    protected double value;

    public GeneticProperty() {
        this.value = 1;
    }

    public abstract String getNBTKey();

    public void read(NBTTagCompound nbttagcompound) {
        this.value = nbttagcompound.hasKey(getNBTKey()) ? nbttagcompound.getDouble(getNBTKey()) : this.value;
    }

    public void save(NBTTagCompound nbttagcompound) {
        nbttagcompound.setDouble(getNBTKey(), value);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "GeneticProperty [value=" + value + "]";
    }

}

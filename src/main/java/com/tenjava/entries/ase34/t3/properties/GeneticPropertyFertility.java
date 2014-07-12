package com.tenjava.entries.ase34.t3.properties;

public class GeneticPropertyFertility extends GeneticProperty {

    public int getParentAge() {
        return (int) Math.round(6000D / this.value);
    }

    @Override
    public String getNBTKey() {
        return "fertility";
    }
}

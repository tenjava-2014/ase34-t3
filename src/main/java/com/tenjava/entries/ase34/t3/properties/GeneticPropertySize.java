package com.tenjava.entries.ase34.t3.properties;

public class GeneticPropertySize extends GeneticProperty {

    @Override
    public String getNBTKey() {
        return "size";
    }

    public int generateDeatlootBonus() {
        return (int) Math.round(this.value + Math.random() * 0.4);
    }
}

package com.tenjava.entries.ase34.t3.properties;

public class GeneticPropertyWool extends GeneticProperty {

    @Override
    public String getNBTKey() {
        return "wool";
    }

    public int generateWoolBonus() {
        return (int) Math.round(1 - this.value + (Math.random() - 0.5) * 0.4);
    }

}

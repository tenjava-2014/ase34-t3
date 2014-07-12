package com.tenjava.entries.ase34.t3.properties;

public class GeneticPropertyGrowth extends GeneticProperty {

    @Override
    public String getNBTKey() {
	return "growth";
    }

    public int getChildAge() {
	return (int) Math.round(-24000D / this.value);
    }
}

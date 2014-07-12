package com.tenjava.entries.ase34.t3.properties;

import java.util.HashMap;

import net.minecraft.server.v1_7_R3.NBTTagCompound;

import org.bukkit.entity.EntityType;

import com.tenjava.entries.ase34.t3.GeneticEntity;
import com.tenjava.entries.ase34.t3.nms.BequeathingEntityPig;
import com.tenjava.entries.ase34.t3.nms.GeneticPropertySize;

@SuppressWarnings("serial")
public class GeneticProperties extends
	HashMap<GeneticProperties.Properties, GeneticProperty> {

    public enum Properties {
	GROWTH, FERTILITY, SIZE
    }

    protected GeneticProperties() {
	super();
    }

    public static HashMap<EntityType, GeneticProperties> defaults = new HashMap<>();

    public GeneticProperties(GeneticProperties geneticProperties,
	    GeneticProperties geneticProperties2) {
	// TODO
    }

    public static void apply(GeneticEntity parent1, GeneticEntity parent2,
	    BequeathingEntityPig child) {
	GeneticProperties inherited = new GeneticProperties(
		parent1.getGeneticProperties(), parent2.getGeneticProperties());
	child.setGeneticProperties(inherited);

	child.setAge(((GeneticPropertyGrowth) inherited
		.get(GeneticProperties.Properties.GROWTH)).getChildAge());
	parent1.setAge(((GeneticPropertyFertility) inherited
		.get(GeneticProperties.Properties.FERTILITY)).getParentAge());
	parent2.setAge(((GeneticPropertyFertility) inherited
		.get(GeneticProperties.Properties.FERTILITY)).getParentAge());
    }

    public void read(NBTTagCompound nbttagcompound) {
	NBTTagCompound geneCombound = nbttagcompound.getCompound("gene");
	for (GeneticProperty property : this.values()) {
	    property.read(geneCombound);
	}
    }

    public void write(NBTTagCompound nbttagcompound) {
	NBTTagCompound geneCombound = nbttagcompound.getCompound("gene");
	for (GeneticProperty property : this.values()) {
	    property.save(geneCombound);
	}
    }

    public static GeneticProperties getDefault(EntityType pig) {
	// TODO Auto-generated method stub
	return null;
    }

    static {
	defaults.put(EntityType.PIG, new GeneticProperties());
	defaults.get(EntityType.PIG).put(GeneticProperties.Properties.GROWTH,
		new GeneticPropertyGrowth());
	defaults.get(EntityType.PIG).put(
		GeneticProperties.Properties.FERTILITY,
		new GeneticPropertyFertility());
	defaults.get(EntityType.PIG).put(GeneticProperties.Properties.SIZE,
		new GeneticPropertySize());
    }
}

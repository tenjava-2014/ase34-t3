package com.tenjava.entries.ase34.t3.properties;

import java.util.HashMap;

import net.minecraft.server.v1_7_R3.NBTTagCompound;

import org.bukkit.entity.EntityType;

import com.google.common.util.concurrent.UncheckedExecutionException;
import com.tenjava.entries.ase34.t3.GeneticEntity;
import com.tenjava.entries.ase34.t3.nms.GeneticPropertySize;

@SuppressWarnings("serial")
public class GeneticProperties extends HashMap<GeneticProperties.Properties, GeneticProperty> {

    public enum Properties {
        GROWTH(GeneticPropertyGrowth.class), FERTILITY(GeneticPropertyFertility.class), SIZE(GeneticPropertySize.class);

        private Class clazz;

        Properties(Class clazz) {
            this.clazz = clazz;
        }

        public Class getClazz() {
            return clazz;
        }
    }

    protected GeneticProperties() {
        super();
    }

    public static HashMap<EntityType, GeneticProperties> defaults = new HashMap<>();

    public GeneticProperties(GeneticProperties geneticProperties1, GeneticProperties geneticProperties2) {
        geneticProperties1.keySet().stream().filter(key -> geneticProperties2.containsKey(key)).forEach(key -> {
            GeneticProperty property;
            try {
                property = (GeneticProperty) key.getClazz().getConstructor().newInstance();
            } catch (Exception e) {
                throw new UncheckedExecutionException(e);
            }

            double avg = (geneticProperties1.get(key).getValue() + geneticProperties2.get(key).getValue()) / 2;
            property.setValue(avg + (Math.random() - 0.5) * 0.1);

            this.put(key, property);
        });
    }

    public static void apply(GeneticEntity parent1, GeneticEntity parent2, GeneticEntity child) {
        GeneticProperties inherited = new GeneticProperties(parent1.getGeneticProperties(),
                parent2.getGeneticProperties());
        child.setGeneticProperties(inherited);

        child.setAge(((GeneticPropertyGrowth) inherited.get(GeneticProperties.Properties.GROWTH)).getChildAge());
        parent1.setAge(((GeneticPropertyFertility) inherited.get(GeneticProperties.Properties.FERTILITY))
                .getParentAge());
        parent2.setAge(((GeneticPropertyFertility) inherited.get(GeneticProperties.Properties.FERTILITY))
                .getParentAge());
    }

    public void read(NBTTagCompound nbttagcompound) {
        NBTTagCompound geneCombound = nbttagcompound.getCompound("gene");
        this.values().stream().forEach(prop -> prop.read(geneCombound));
    }

    public void write(NBTTagCompound nbttagcompound) {
        NBTTagCompound geneCombound = nbttagcompound.getCompound("gene");
        this.values().stream().forEach(prop -> prop.save(geneCombound));
    }

    public static GeneticProperties getDefault(EntityType type) {
        return defaults.get(type);
    }

    public static HashMap<EntityType, GeneticProperties> getDefaults() {
        return defaults;
    }

    static {
        defaults.put(EntityType.PIG, new GeneticProperties());
        defaults.get(EntityType.PIG).put(GeneticProperties.Properties.GROWTH, new GeneticPropertyGrowth());
        defaults.get(EntityType.PIG).put(GeneticProperties.Properties.FERTILITY, new GeneticPropertyFertility());
        defaults.get(EntityType.PIG).put(GeneticProperties.Properties.SIZE, new GeneticPropertySize());

        defaults.put(EntityType.CHICKEN, defaults.get(EntityType.PIG));
    }
}

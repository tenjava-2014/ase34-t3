package com.tenjava.entries.ase34.t3.properties;

import java.util.HashMap;

import net.minecraft.server.v1_7_R3.NBTTagCompound;

import org.bukkit.entity.EntityType;

import com.google.common.util.concurrent.UncheckedExecutionException;
import com.tenjava.entries.ase34.t3.GeneticEntity;

@SuppressWarnings("serial")
public class GeneticProperties extends HashMap<GeneticProperties.Properties, GeneticProperty> {

    public enum Properties {
        GROWTH(GeneticPropertyGrowth.class), FERTILITY(GeneticPropertyFertility.class), SIZE(GeneticPropertySize.class), EGG_DROP(
                GeneticPropertyEggDrop.class);

        private Class<? extends GeneticProperty> clazz;

        @SuppressWarnings("rawtypes")
        Properties(Class<? extends GeneticProperty> clazz) {
            this.clazz = clazz;
        }

        public Class<? extends GeneticProperty> getClazz() {
            return clazz;
        }

        public GeneticProperty getDefaultObject() {
            try {
                return (GeneticProperty) getClazz().getConstructor().newInstance();
            } catch (Exception e) {
                throw new UncheckedExecutionException(e);
            }
        }
    }

    protected GeneticProperties() {
        super();
    }

    public static HashMap<EntityType, GeneticProperties> defaults = new HashMap<>();

    public GeneticProperties(GeneticProperties geneticProperties1, GeneticProperties geneticProperties2) {
        geneticProperties1.keySet().stream().filter(key -> geneticProperties2.containsKey(key)).forEach(key -> {
            GeneticProperty property = key.getDefaultObject();

            double avg = (geneticProperties1.get(key).getValue() + geneticProperties2.get(key).getValue()) / 2;
            property.setValue(avg + (Math.random() - 0.5) * 0.1);

            this.put(key, property);
        });
    }

    public GeneticProperties(GeneticProperties geneticProperties) {
        super(geneticProperties);
    }

    public static void apply(GeneticEntity parent1, GeneticEntity parent2, GeneticEntity child) {
        GeneticProperties inherited = new GeneticProperties(parent1.getGeneticProperties(),
                parent2.getGeneticProperties());
        child.setGeneticProperties(inherited);

        child.setAge(((GeneticPropertyGrowth) inherited.get(Properties.GROWTH)).getChildAge());
        parent1.setAge(((GeneticPropertyFertility) inherited.get(Properties.FERTILITY)).getParentAge());
        parent2.setAge(((GeneticPropertyFertility) inherited.get(Properties.FERTILITY)).getParentAge());
    }

    public void read(NBTTagCompound nbttagcompound) {
        NBTTagCompound geneCombound = nbttagcompound.getCompound("gene");
        this.values().stream().forEach(prop -> prop.read(geneCombound));
    }

    public void write(NBTTagCompound nbttagcompound) {
        NBTTagCompound geneCombound = nbttagcompound.getCompound("gene");
        this.values().stream().forEach(prop -> prop.save(geneCombound));
    }

    public GeneticProperties addProperty(Properties prop) {
        this.put(prop, prop.getDefaultObject());
        return this;
    }

    public static GeneticProperties getDefault(EntityType type) {
        return defaults.get(type);
    }

    public static HashMap<EntityType, GeneticProperties> getDefaults() {
        return defaults;
    }

    static {
        defaults.put(EntityType.PIG, new GeneticProperties());
        defaults.get(EntityType.PIG).addProperty(Properties.GROWTH).addProperty(Properties.FERTILITY)
                .addProperty(Properties.SIZE);

        defaults.put(EntityType.CHICKEN, new GeneticProperties(defaults.get(EntityType.PIG)));
        defaults.get(EntityType.CHICKEN).addProperty(Properties.EGG_DROP);
    }
}

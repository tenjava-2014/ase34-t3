package com.tenjava.entries.ase34.t3;

import java.lang.reflect.Field;
import java.util.Map;

import net.minecraft.server.v1_7_R3.Entity;
import net.minecraft.server.v1_7_R3.EntityTypes;

public class EntityRegistrator {

    public static void registerEntity(String name, int id, Class<? extends Entity> clazz)
            throws Exception {
        Field f1 = EntityTypes.class.getDeclaredField("d");
        Field f2 = EntityTypes.class.getDeclaredField("f");

        f1.setAccessible(true);
        f2.setAccessible(true);

        ((Map<Class<? extends Entity>, String>) f1.get(null)).put(clazz, name);
        ((Map<Class<? extends Entity>, Integer>) f2.get(null)).put(clazz, id);
    }

}

package com.tenjava.entries.ase34.t3;

import net.minecraft.server.v1_7_R3.EntityAgeable;
import net.minecraft.server.v1_7_R3.EntityLiving;

public class ChildCreator {

    public EntityAgeable createChild(EntityAgeable parent1, EntityAgeable parent2) {
        return parent1.createChild(parent2);
    }

}

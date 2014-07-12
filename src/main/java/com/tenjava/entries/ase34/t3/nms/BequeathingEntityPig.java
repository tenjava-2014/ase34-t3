package com.tenjava.entries.ase34.t3.nms;

import net.minecraft.server.v1_7_R3.EntityAgeable;
import net.minecraft.server.v1_7_R3.EntityPig;
import net.minecraft.server.v1_7_R3.World;

public class BequeathingEntityPig extends EntityPig {

    public BequeathingEntityPig(World world) {
        super(world);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable) {
        // TODO Auto-generated method stub
        System.out.println("breeeding!");
        return super.createChild(entityageable);
    }

}

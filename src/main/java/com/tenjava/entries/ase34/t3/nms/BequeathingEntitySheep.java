package com.tenjava.entries.ase34.t3.nms;

import net.minecraft.server.v1_7_R3.Blocks;
import net.minecraft.server.v1_7_R3.EntityAgeable;
import net.minecraft.server.v1_7_R3.EntityHuman;
import net.minecraft.server.v1_7_R3.EntityItem;
import net.minecraft.server.v1_7_R3.EntitySheep;
import net.minecraft.server.v1_7_R3.Item;
import net.minecraft.server.v1_7_R3.ItemStack;
import net.minecraft.server.v1_7_R3.Items;
import net.minecraft.server.v1_7_R3.NBTTagCompound;
import net.minecraft.server.v1_7_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import com.tenjava.entries.ase34.t3.GeneticEntity;
import com.tenjava.entries.ase34.t3.TenJava;
import com.tenjava.entries.ase34.t3.properties.GeneticProperties;
import com.tenjava.entries.ase34.t3.properties.GeneticPropertyWool;

public class BequeathingEntitySheep extends EntitySheep implements GeneticEntity {

    public BequeathingEntitySheep(World world) {
        super(world);
    }

    protected GeneticProperties geneticProperties = GeneticProperties.getDefault(EntityType.SHEEP);

    @Override
    public EntityAgeable createChild(EntityAgeable otherparent) {
        BequeathingEntitySheep self = this;
        BequeathingEntitySheep child = new BequeathingEntitySheep(this.world);

        Bukkit.getScheduler().scheduleSyncDelayedTask(TenJava.getInstance(), new Runnable() {

            @Override
            public void run() {
                GeneticProperties.apply(self, (GeneticEntity) otherparent, child);
            }

        }, 1);

        return child;
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.getGeneticProperties().read(nbttagcompound);
    }

    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        this.getGeneticProperties().write(nbttagcompound);
    }

    @Override
    public GeneticProperties getGeneticProperties() {
        return geneticProperties;
    }

    @Override
    public void setGeneticProperties(GeneticProperties geneticProperties) {
        this.geneticProperties = (GeneticProperties) geneticProperties;
    }

    @Override
    public boolean a(EntityHuman entityhuman) {
        ItemStack itemstack = entityhuman.inventory.getItemInHand();

        if (itemstack != null && itemstack.getItem() == Items.SHEARS && !this.isSheared() && !this.isBaby()) {
            if (!this.world.isStatic) {
                this.setSheared(true);
                int i = 1
                        + this.random.nextInt(3)
                        + ((GeneticPropertyWool) getGeneticProperties().get(GeneticProperties.Properties.WOOL))
                                .generateWoolBonus();

                for (int j = 0; j < i; ++j) {
                    EntityItem entityitem = this
                            .a(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, this.getColor()), 1.0F);

                    entityitem.motY += (double) (this.random.nextFloat() * 0.05F);
                    entityitem.motX += (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                    entityitem.motZ += (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                }
            }

            itemstack.damage(1, entityhuman);
            this.makeSound("mob.sheep.shear", 1.0F, 1.0F);
        }
        return super.a(entityhuman);
    }
}

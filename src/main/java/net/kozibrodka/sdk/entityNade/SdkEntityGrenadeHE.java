package net.kozibrodka.sdk.entityNade;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.events.SdkConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class SdkEntityGrenadeHE extends SdkEntityGrenade{

    public SdkEntityGrenadeHE(World world)
    {
        super(world);
        stack = new ItemStack(ItemListener.itemGrenadeHe, 1, 0);
    }

    public SdkEntityGrenadeHE(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        stack = new ItemStack(ItemListener.itemGrenadeHe, 1, 0);
    }

    public SdkEntityGrenadeHE(World world, LivingEntity entityliving)
    {
        super(world, entityliving);
        stack = new ItemStack(ItemListener.itemGrenadeHe, 1, 0);
    }

    protected void explode()
    {
        if(!exploded)
        {
            exploded = true;
            Explosion explosion = new Explosion(world, null, x, (float)y, (float)z, 3F);
            explosion.explode();
            world.playSound(x, y, z, "random.explode", 4F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F);

            for(int i = 0; i < 32; i++)
            {
                world.addParticle("explode", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
                world.addParticle("smoke", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
            }

            dead = true;
        }
    }
}

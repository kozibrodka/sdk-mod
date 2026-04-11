package net.kozibrodka.sdk.entityNade;


import net.kozibrodka.sdk.events.ItemListener;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import java.util.List;

public class SdkEntityGrenadeIncendiary extends SdkEntityGrenade
{

    public SdkEntityGrenadeIncendiary(World world)
    {
        super(world);
        stack = new ItemStack(ItemListener.itemGrenadeIncendiaryLit, 1, 0);
    }

    public SdkEntityGrenadeIncendiary(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        stack = new ItemStack(ItemListener.itemGrenadeIncendiaryLit, 1, 0);
    }

    public SdkEntityGrenadeIncendiary(World world, LivingEntity entityliving)
    {
        super(world, entityliving);
        stack = new ItemStack(ItemListener.itemGrenadeIncendiaryLit, 1, 0);
    }

    public void tick()
    {
        super.tick();
        List list = world.getEntities(this, boundingBox);
        if(list.size() > 0)
        {
            Entity entity = (Entity)list.get(0);
            if(entity instanceof LivingEntity)
            {
                entity.fireTicks = 300;
                explode();
            }
        }
    }

    protected void handleBounce()
    {
        explode();
    }

    protected void explode()
    {
        if(!exploded)
        {
            exploded = true;
            world.playSound(this, Block.GLASS.soundGroup.getBreakSound(), (Block.GLASS.soundGroup.getVolume() + 1.0F) / 2.0F, Block.GLASS.soundGroup.getPitch() * 0.8F);
            int i = (int)Math.floor(x);
            int j = (int)Math.floor(y);
            int k = (int)Math.floor(z);
            for(int l = -2; l <= 2; l++)
            {
                for(int i1 = -2; i1 <= 2; i1++)
                {
                    for(int j1 = -2; j1 <= 2; j1++)
                    {
                        int k1 = Math.abs(l) + Math.abs(i1) + Math.abs(j1);
                        if(k1 <= 2 && world.isAir(i + l, j + i1, k + j1))
                        {
                            world.setBlock(i + l, j + i1, k + j1, Block.FIRE.id);
                        }
                    }

                }

            }

            markDead();
        }
    }

    private static final int RADIUS = 2;
}

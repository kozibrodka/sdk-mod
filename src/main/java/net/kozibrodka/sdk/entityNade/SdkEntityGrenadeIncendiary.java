package net.kozibrodka.sdk.entityNade;


import net.kozibrodka.sdk.events.ItemListener;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

import java.util.List;

public class SdkEntityGrenadeIncendiary extends SdkEntityGrenade
{

    public SdkEntityGrenadeIncendiary(Level world)
    {
        super(world);
        item = new ItemInstance(ItemListener.itemGrenadeIncendiaryLit, 1, 0);
    }

    public SdkEntityGrenadeIncendiary(Level world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        item = new ItemInstance(ItemListener.itemGrenadeIncendiaryLit, 1, 0);
    }

    public SdkEntityGrenadeIncendiary(Level world, Living entityliving)
    {
        super(world, entityliving);
        item = new ItemInstance(ItemListener.itemGrenadeIncendiaryLit, 1, 0);
    }

    public void tick()
    {
        super.tick();
        List list = level.getEntities(this, boundingBox);
        if(list.size() > 0)
        {
            EntityBase entity = (EntityBase)list.get(0);
            if(entity instanceof Living)
            {
                entity.fire = 300;
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
            level.playSound(this, BlockBase.GLASS.sounds.getBreakSound(), (BlockBase.GLASS.sounds.getVolume() + 1.0F) / 2.0F, BlockBase.GLASS.sounds.getPitch() * 0.8F);
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
                        if(k1 <= 2 && level.isAir(i + l, j + i1, k + j1))
                        {
                            level.setTile(i + l, j + i1, k + j1, BlockBase.FIRE.id);
                        }
                    }

                }

            }

            remove();
        }
    }

    private static final int RADIUS = 2;
}

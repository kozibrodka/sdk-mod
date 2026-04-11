package net.kozibrodka.sdk.entityNade;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkPoint3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import java.util.List;

public class SdkEntityGrenadeSticky extends SdkEntityGrenade
{

    public SdkEntityGrenadeSticky(World world)
    {
        super(world);
        stuckToBlock = false;
        stuckToEntity = null;
        stuckToEntityOffset = null;
        stack = new ItemStack(ItemListener.itemGrenadeSticky, 1, 0);
    }

    public SdkEntityGrenadeSticky(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        stuckToBlock = false;
        stuckToEntity = null;
        stuckToEntityOffset = null;
        stack = new ItemStack(ItemListener.itemGrenadeSticky, 1, 0);
    }

    public SdkEntityGrenadeSticky(World world, LivingEntity entityliving)
    {
        super(world, entityliving);
        stuckToBlock = false;
        stuckToEntity = null;
        stuckToEntityOffset = null;
        stack = new ItemStack(ItemListener.itemGrenadeSticky, 1, 0);
    }

    public void tick()
    {
        if(stuckToEntity != null && stuckToEntity.dead)
        {
            stuckToEntity = null;
        }
        if(!stuckToBlock && stuckToEntity == null)
        {
            super.tick();
        } else
        {
            handleExplode();
        }
        if(stuckToEntity == null)
        {
            List list = world.getEntities(this, boundingBox);
            if(list.size() > 0)
            {
                Entity entity = (Entity)list.get(0);
                if(entity instanceof LivingEntity)
                {
                    stuckToBlock = false;
                    stuckToEntity = entity;
                    stuckToEntityOffset = new SdkPoint3d(x - entity.x, y - entity.y, z - entity.z);
                }
            }
        } else
        {
            prevX = x;
            prevY = y;
            prevZ = z;
            x = stuckToEntity.x + (Double) stuckToEntityOffset.x;
            y = stuckToEntity.y + (Double) stuckToEntityOffset.y;
            z = stuckToEntity.z + (Double) stuckToEntityOffset.z;
        }
    }

    protected void handleBounce()
    {
        if(stuckToEntity == null)
        {
            stuckToBlock = true;
            velocityX = velocityY = velocityZ = 0.0D;
        }
    }

    protected boolean stuckToBlock;
    protected Entity stuckToEntity;
    protected SdkPoint3d stuckToEntityOffset;
}

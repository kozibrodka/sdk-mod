package net.kozibrodka.sdk.entityNade;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.itemNade.SdkItemGrenadeSmoke;
import net.kozibrodka.sdk.itemNade.SdkItemGrenadeSticky;
import net.kozibrodka.sdk_api.utils.SdkEntityGrenade;
import net.kozibrodka.sdk_api.utils.SdkExplosion;
import net.kozibrodka.sdk_api.utils.SdkPoint3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

public class SdkEntityGrenadeSticky extends SdkEntityGrenade implements EntitySpawnDataProvider
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

    @Override
    public void playServerSound(World world) {
        world.playSound(this, SdkItemGrenadeSticky.throwSound, 1.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
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

    @Override
    protected void handleBounce()
    {
        if(stuckToEntity == null)
        {
            stuckToBlock = true;
            velocityX = velocityY = velocityZ = 0.0D;
        }
    }

    @Override
    protected void explode()
    {
        if(!exploded)
        {
            exploded = true;
            boolean flagW = false;
            if(checkWaterCollisions()){
                flagW = true;
                for (int i = 0; i < 128; i++) {
                    world.addParticle("splash", x + world.random.nextDouble() - 0.5D, y + (world.random.nextDouble()*3.0D), z + world.random.nextDouble() - 0.5D, 1, 1, 1);
                    world.addParticle("bubble", x, y, z, (world.random.nextDouble() - 0.5D) * 3.0D, (world.random.nextDouble() - 0.5D) * 10.0D, (world.random.nextDouble() - 0.5D) * 3.0D);
                }
            }else {
                for (int i = 0; i < 32; i++) {
                    world.addParticle("explode", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
                    world.addParticle("smoke", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
                }
            }
            SdkExplosion explosion = new SdkExplosion(world, null, x,  y,  z, 3F, false, true, "random.explode", flagW);
            explosion.explodeA();
            explosion.explodeB(true);
            dead = true;
        }
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "NadeSticky");
    }

    protected boolean stuckToBlock;
    protected Entity stuckToEntity;
    protected SdkPoint3d stuckToEntityOffset;
}

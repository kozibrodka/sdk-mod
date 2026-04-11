package net.kozibrodka.sdk.entitySentry;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.SdkConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Monster;
import net.minecraft.entity.mob.PigZombieEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import java.lang.reflect.Method;

public class SdkEntityGuardians extends MobEntity
{

    public SdkEntityGuardians(World world)
    {
        super(world);
        range = 32F;
        attackStrength = 2;
        health = 20;
    }

    protected void initDataTracker()
    {
        super.initDataTracker();
        dataTracker.startTracking(17, "");
        dataTracker.startTracking(18, new Integer(health));
    }

    public String getOwner()
    {
        return dataTracker.getString(17);
    }

    public void setOwner(String s)
    {
        dataTracker.set(17, s);
    }

    public boolean damage(Entity entity1, int i)
    {
        if(super.damage(entity1, i))
        {
            if(passenger == entity1 || vehicle == entity1)
            {
                return true;
            }
            if(entity1 != this)
            {
                target = entity1;
            }
            return true;
        } else
        {
            return false;
        }
    }

    protected Entity getTargetInRange()
    {
        return getNearestEntityLivingInRange(this, range);
    }

    public LivingEntity getNearestEntityLivingInRange(Entity entity, double d)
    {
        return getNearestEntityLivingInRange(entity.x, entity.y, entity.z, d);
    }

    public LivingEntity getNearestEntityLivingInRange(double d, double d1, double d2, double d3)
    {
        double d4 = -1D;
        LivingEntity entityliving = null;
        for(int i = 0; i < world.entities.size(); i++)
        {
            Entity entity = (Entity)world.entities.get(i);
            if(!(entity instanceof LivingEntity) || !SdkConfig.sentriesKillAnimals && (!(entity instanceof Monster) || (entity instanceof PigZombieEntity)) || (entity instanceof PlayerEntity) || (entity instanceof WolfEntity) && ((WolfEntity)entity).isTamed() || !entity.isAlive())
            {
                continue;
            }
            double d5 = entity.getSquaredDistance(d, d1, d2);
            if((d3 < 0.0D || d5 < d3 * d3) && (d4 == -1D || d5 < d4) && canSee(entity) && okToAttack(entity))
            {
                d4 = d5;
                entityliving = (LivingEntity)entity;
            }
        }

        return entityliving;
    }

    protected void attack(Entity entity, float f)
    {
        if(okToAttack(entity) && (double)f < 2.5D && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            entity.damage(this, attackStrength);
            attackCooldown = 1;
        }
    }

    protected boolean okToAttack(Entity entity)
    {
        boolean flag = false;
        if(!aiManagerIsPetNotFound && (entity instanceof LivingEntity))
        {
            try
            {
                if(aiManagerIsPet == null)
                {
                    Class class1;
                    try
                    {
                        class1 = Class.forName("mod_AIManager");
                    }
                    catch(Exception exception1)
                    {
                        class1 = Class.forName("net.minecraft.src.mod_AIManager");
                    }
                    aiManagerIsPet = class1.getDeclaredMethod("isPet", new Class[] {
                            LivingEntity.class
                    });
                }
                flag = ((Boolean)aiManagerIsPet.invoke(null, new Object[] {
                    (LivingEntity)entity
                })).booleanValue();
            }
            catch(Exception exception)
            {
                System.out.println("mod_AIManager not found in SdkEntityGuardians. Ignore this exception if you do not have it installed.");
                aiManagerIsPetNotFound = true;
            }
        }
        return !flag && !(entity instanceof SdkEntityGuardians) && (!(entity instanceof PlayerEntity) || !((PlayerEntity)entity).name.equals(getOwner())) && (!(entity instanceof WolfEntity) || !((WolfEntity)entity).isTamed() || !((WolfEntity)entity).getOwnerName().equals(getOwner()));
    }

    protected float getPathfindingFavor(int i, int j, int k)
    {
        return 1.0F;
    }

    public void writeNbt(NbtCompound nbttagcompound)
    {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putString("Owner", getOwner() == null ? "" : getOwner());
    }

    public void readNbt(NbtCompound nbttagcompound)
    {
        super.readNbt(nbttagcompound);
        setOwner(nbttagcompound.getString("Owner"));
    }

    protected void tickLiving()
    {
        super.tickLiving();
        if(!world.isRemote)
        {
            dataTracker.set(18, Integer.valueOf(health));
        }
    }

    protected int attackStrength;
    protected float range;
    private static boolean aiManagerIsPetNotFound = false;
    private static Method aiManagerIsPet = null;

}

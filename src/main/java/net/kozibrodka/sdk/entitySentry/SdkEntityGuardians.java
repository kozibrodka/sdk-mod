package net.kozibrodka.sdk.entitySentry;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.SdkConfig;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.WalkingBase;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.entity.monster.MonsterEntityType;
import net.minecraft.entity.monster.ZombiePigman;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

import java.lang.reflect.Method;

public class SdkEntityGuardians extends WalkingBase
{

    public SdkEntityGuardians(Level world)
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
        dataTracker.setInt(17, s);
    }

    public boolean damage(EntityBase entity1, int i)
    {
        if(super.damage(entity1, i))
        {
            if(passenger == entity1 || vehicle == entity1)
            {
                return true;
            }
            if(entity1 != this)
            {
                entity = entity1;
            }
            return true;
        } else
        {
            return false;
        }
    }

    protected EntityBase getAttackTarget()
    {
        return getNearestEntityLivingInRange(this, range);
    }

    public Living getNearestEntityLivingInRange(EntityBase entity, double d)
    {
        return getNearestEntityLivingInRange(entity.x, entity.y, entity.z, d);
    }

    public Living getNearestEntityLivingInRange(double d, double d1, double d2, double d3)
    {
        double d4 = -1D;
        Living entityliving = null;
        for(int i = 0; i < level.entities.size(); i++)
        {
            EntityBase entity = (EntityBase)level.entities.get(i);
            if(!(entity instanceof Living) || !SdkConfig.sentriesKillAnimals && (!(entity instanceof MonsterEntityType) || (entity instanceof ZombiePigman)) || (entity instanceof PlayerBase) || (entity instanceof Wolf) && ((Wolf)entity).isTamed() || !entity.isAlive())
            {
                continue;
            }
            double d5 = entity.squaredDistanceTo(d, d1, d2);
            if((d3 < 0.0D || d5 < d3 * d3) && (d4 == -1D || d5 < d4) && method_928(entity) && okToAttack(entity))
            {
                d4 = d5;
                entityliving = (Living)entity;
            }
        }

        return entityliving;
    }

    protected void tryAttack(EntityBase entity, float f)
    {
        if(okToAttack(entity) && (double)f < 2.5D && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            entity.damage(this, attackStrength);
            attackTime = 1;
        }
    }

    protected boolean okToAttack(EntityBase entity)
    {
        boolean flag = false;
        if(!aiManagerIsPetNotFound && (entity instanceof Living))
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
                            Living.class
                    });
                }
                flag = ((Boolean)aiManagerIsPet.invoke(null, new Object[] {
                    (Living)entity
                })).booleanValue();
            }
            catch(Exception exception)
            {
                System.out.println("mod_AIManager not found in SdkEntityGuardians. Ignore this exception if you do not have it installed.");
                aiManagerIsPetNotFound = true;
            }
        }
        return !flag && !(entity instanceof SdkEntityGuardians) && (!(entity instanceof PlayerBase) || !((PlayerBase)entity).name.equals(getOwner())) && (!(entity instanceof Wolf) || !((Wolf)entity).isTamed() || !((Wolf)entity).getOwner().equals(getOwner()));
    }

    protected float getPathfindingFavour(int i, int j, int k)
    {
        return 1.0F;
    }

    public void writeCustomDataToTag(CompoundTag nbttagcompound)
    {
        super.writeCustomDataToTag(nbttagcompound);
        nbttagcompound.put("Owner", getOwner() == null ? "" : getOwner());
    }

    public void readCustomDataFromTag(CompoundTag nbttagcompound)
    {
        super.readCustomDataFromTag(nbttagcompound);
        setOwner(nbttagcompound.getString("Owner"));
    }

    protected void tickHandSwing()
    {
        super.tickHandSwing();
        if(!level.isServerSide)
        {
            dataTracker.setInt(18, Integer.valueOf(health));
        }
    }

    protected int attackStrength;
    protected float range;
    private static boolean aiManagerIsPetNotFound = false;
    private static Method aiManagerIsPet = null;

}

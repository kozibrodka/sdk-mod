package net.kozibrodka.sdk.entitySentry;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.monster.MonsterEntityType;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathHelper;

import java.util.*;

public abstract class SdkEntitySentry extends SdkEntityGuardians
    implements MonsterEntityType
{

    public SdkEntitySentry(Level world)
    {
        super(world);
        height = 1.5F;
        angerMap = new HashMap();
        texture = "/sdk/mobSentry.png";
        health = 20;
    }

    public SdkEntitySentry(Level world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
    }

    public boolean damage(EntityBase entity, int i)
    {
        if((entity instanceof Living) && okToAttack(entity))
        {
            List list = level.getEntities(this, boundingBox.expand(32D, 32D, 32D));
            for(int j = 0; j < list.size(); j++)
            {
                EntityBase entity1 = (EntityBase)list.get(j);
                if(!(entity1 instanceof SdkEntitySentry))
                {
                    continue;
                }
                SdkEntitySentry sdkentitysentry = (SdkEntitySentry)entity1;
                if(sdkentitysentry.getOwner() != null && sdkentitysentry.getOwner() != "" && sdkentitysentry.getOwner() == getOwner())
                {
                    sdkentitysentry.becomeAngryAt(entity);
                }
            }

            becomeAngryAt(entity);
        }
        return super.damage(entity, i);
    }

    private void becomeAngryAt(EntityBase entity)
    {
        if(angerMap.containsKey(entity))
        {
            angerMap.remove(entity);
        }
        angerMap.put(entity, Integer.valueOf(400 + rand.nextInt(400)));
    }

    public void tick()
    {
        baseTick();
        updateDespawnCounter();
    }

    protected void attackEntity(EntityBase entity, float f)
    {
        if(okToAttack(entity))
        {
            if(attackTime == 0 && level != null && gun != null && level.rand != null)
            {
                if(itemStack == null)
                {
                    itemStack = new ItemInstance(gun);
                }
                gun.useOryginal(itemStack, level, this, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                attackTime = ATTACK_DELAY;
            }
            field_663 = true;
        }
    }

    protected void tickHandSwing()
    {
        if(!removed)
        {
            if(level.getLevelTime() % 20L == 0L)
            {
                entity = getAttackTarget();
            }
            if(entity != null && method_928(entity))
            {
                restricted = false;
                method_924(entity, 10F, 10F);
                if(!restricted)
                {
                    attackEntity(entity, range);
                }
            } else
            {
                yaw++;
                pitch = 0.0F;
            }
        }
        for(Iterator iterator = angerMap.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            int i = ((Integer)entry.getValue()).intValue() - 1;
            if(i <= 0)
            {
                iterator.remove();
            } else
            {
                entry.setValue(Integer.valueOf(i));
            }
        }

    }

    protected EntityBase getAttackTarget()
    {
        Living entityliving = getNearestAnger(this);
        if(entityliving != null)
        {
            return entityliving;
        } else
        {
            return super.getAttackTarget();
        }
    }

    public Living getNearestAnger(EntityBase entity)
    {
        return getNearestAnger(entity.x, entity.y, entity.z);
    }

    public Living getNearestAnger(double d, double d1, double d2)
    {
        double d3 = -1D;
        Living entityliving = null;
        Iterator iterator = angerMap.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
            {
                break;
            }
            Map.Entry entry = (Map.Entry)iterator.next();
            EntityBase entity = (EntityBase)entry.getKey();
            if((entity instanceof Living) && entity.isAlive())
            {
                double d4 = entity.squaredDistanceTo(d, d1, d2);
                if(d3 == -1D || d4 < d3 && method_928(entity) && okToAttack(entity))
                {
                    d3 = d4;
                    entityliving = (Living)entity;
                }
            }
        } while(true);
        return entityliving;
    }

    public void method_924(EntityBase entity, float f, float f1)
    {
        if(!okToAttack(entity))
        {
            return;
        } else
        {
            double d = entity.x - x;
            double d1 = entity.z - z;
            double d2 = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2D - (y + (double)getEyeHeight());
            double d3 = MathHelper.sqrt(d * d + d1 * d1);
            float f2 = (float)((Math.atan2(d1, d) * 180D) / 3.1415927410125732D) - 90F;
            float f3 = (float)((Math.atan2(d2, d3) * 180D) / 3.1415927410125732D);
            pitch = -b(-pitch, f3, f1);
            yaw = b(yaw, f2, f);
            return;
        }
    }

    private float b(float f, float f1, float f2)
    {
        float f3;
        for(f3 = f1 - f; f3 < -180F; f3 += 360F) { }
        for(; f3 >= 180F; f3 -= 360F) { }
        if(f3 > f2)
        {
            restricted = true;
            f3 = f2;
        }
        if(f3 < -f2)
        {
            restricted = true;
            f3 = -f2;
        }
        return f + f3;
    }

    protected int getMobDrops()
    {
        return gun.requiredBullet.id;
    }

    protected String getHurtSound()
    {
        return "sdk:mechhurt";
    }

    protected String getDeathSound()
    {
        return null;
    }

    public void method_925(EntityBase entity, int i, double d, double d1) //knockback
    {
    }

    public void remove()
    {
        super.remove();
        entity = null;
        gun = null;
    }

    public boolean interact(PlayerBase entityplayer)
    {
        if(entityplayer.getHeldItem() != null && entityplayer.getHeldItem().itemId == ItemListener.itemWrench.id)
        {
            if(health > 0 && health < 20)
            {
                level.playSound(this, "sdk.wrench", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                health = Math.min(health + 2, 20);
                entityplayer.swingHand();
                entityplayer.getHeldItem().applyDamage(1, entityplayer);
                if(entityplayer.getHeldItem().getDamage() <= 0)
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedHotbarSlot] = null;
                }
            }
            return true;
        } else
        {
            return false;
        }
    }

    boolean restricted;
    float height;
    private static final float MAX_TURN_SPEED = 10F;
    private static final int MAX_HEALTH = 20;
    protected SdkItemGun gun;
    protected ItemInstance itemStack;
    protected int ATTACK_DELAY;
    private Map angerMap;
}

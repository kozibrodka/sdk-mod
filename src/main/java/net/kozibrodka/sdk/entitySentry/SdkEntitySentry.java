package net.kozibrodka.sdk.entitySentry;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import java.util.*;

public abstract class SdkEntitySentry extends SdkEntityGuardians
    implements Monster
{

    public SdkEntitySentry(World world)
    {
        super(world);
        height = 1.5F;
        angerMap = new HashMap();
        texture = "/sdk/mobSentry.png";
        health = 20;
    }

    public SdkEntitySentry(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
    }

    public boolean damage(Entity entity, int i)
    {
        if((entity instanceof LivingEntity) && okToAttack(entity))
        {
            List list = world.getEntities(this, boundingBox.expand(32D, 32D, 32D));
            for(int j = 0; j < list.size(); j++)
            {
                Entity entity1 = (Entity)list.get(j);
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

    private void becomeAngryAt(Entity entity)
    {
        if(angerMap.containsKey(entity))
        {
            angerMap.remove(entity);
        }
        angerMap.put(entity, Integer.valueOf(400 + random.nextInt(400)));
    }

    public void tick()
    {
        baseTick();
        tickMovement();
    }

    protected void attackEntity(Entity entity, float f)
    {
        if(okToAttack(entity))
        {
            if(attackCooldown == 0 && world != null && gun != null && world.random != null)
            {
                if(itemStack == null)
                {
                    itemStack = new ItemStack(gun);
                }
                gun.useOryginal(itemStack, world, this, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                attackCooldown = ATTACK_DELAY;
            }
            movementBlocked = true;
        }
    }

    protected void tickLiving()
    {
        if(!dead)
        {
            if(world.getTime() % 20L == 0L)
            {
                target = getTargetInRange();
            }
            if(target != null && canSee(target))
            {
                restricted = false;
                lookAt(target, 10F, 10F);
                if(!restricted)
                {
                    attackEntity(target, range);
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

    protected Entity getTargetInRange()
    {
        LivingEntity entityliving = getNearestAnger(this);
        if(entityliving != null)
        {
            return entityliving;
        } else
        {
            return super.getTargetInRange();
        }
    }

    public LivingEntity getNearestAnger(Entity entity)
    {
        return getNearestAnger(entity.x, entity.y, entity.z);
    }

    public LivingEntity getNearestAnger(double d, double d1, double d2)
    {
        double d3 = -1D;
        LivingEntity entityliving = null;
        Iterator iterator = angerMap.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
            {
                break;
            }
            Map.Entry entry = (Map.Entry)iterator.next();
            Entity entity = (Entity)entry.getKey();
            if((entity instanceof LivingEntity) && entity.isAlive())
            {
                double d4 = entity.getSquaredDistance(d, d1, d2);
                if(d3 == -1D || d4 < d3 && canSee(entity) && okToAttack(entity))
                {
                    d3 = d4;
                    entityliving = (LivingEntity)entity;
                }
            }
        } while(true);
        return entityliving;
    }

    public void lookAt(Entity entity, float f, float f1)
    {
        if(!okToAttack(entity))
        {
            return;
        } else
        {
            double d = entity.x - x;
            double d1 = entity.z - z;
            double d2 = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2D - (y + (double)getShadowRadius());
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

    protected int getDroppedItemId()
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

    public void applyKnockback(Entity entity, int i, double d, double d1) //knockback
    {
    }

    public void markDead()
    {
        super.markDead();
        target = null;
        gun = null;
    }

    public boolean interact(PlayerEntity entityplayer)
    {
        if(entityplayer.getHand() != null && entityplayer.getHand().itemId == ItemListener.itemWrench.id)
        {
            if(health > 0 && health < 20)
            {
                world.playSound(this, "sdk.wrench", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                health = Math.min(health + 2, 20);
                entityplayer.swingHand();
                entityplayer.getHand().damage(1, entityplayer);
                if(entityplayer.getHand().getDamage() <= 0)
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = null;
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
    protected ItemStack itemStack;
    protected int ATTACK_DELAY;
    private Map angerMap;
}

package net.kozibrodka.sdk.entityNade;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.ingame.mod_SdkFlasher;
import net.kozibrodka.sdk_api.ingame.mod_SdkGuns;
import net.kozibrodka.sdk_api.ingame.mod_SdkUtility;
import net.kozibrodka.sdk_api.utils.SdkPair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import java.util.ArrayList;

public class SdkEntityGrenadeStun extends SdkEntityGrenade
{

    public SdkEntityGrenadeStun(World world)
    {
        super(world);
        BOUNCE_SOUND = "sdk:stungrenadebounce";
        stack = new ItemStack(ItemListener.itemGrenadeStun, 1, 0);
    }

    public SdkEntityGrenadeStun(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        BOUNCE_SOUND = "sdk:stungrenadebounce";
        stack = new ItemStack(ItemListener.itemGrenadeStun, 1, 0);
    }

    public SdkEntityGrenadeStun(World world, LivingEntity entityliving)
    {
        super(world, entityliving);
        BOUNCE_SOUND = "sdk:stungrenadebounce";
        stack = new ItemStack(ItemListener.itemGrenadeStun, 1, 0);
    }

    protected void explode()
    {
        if(!exploded)
        {
            exploded = true;
            world.playSound(this, "sdk:stungrenade", 4F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
            mod_SdkFlasher.LightEntity(world, this, 15, 2);
            ArrayList arraylist = getEntityLivingsInRange(32D);
            for(int i = 0; i < arraylist.size(); i++)
            {
                LivingEntity entityliving = (LivingEntity)arraylist.get(i);
                if(!entityliving.canSee(this))
                {
                    continue;
                }
                double d = x - entityliving.x;
                double d1 = y - entityliving.y;
                double d2 = z - entityliving.z;
                float f = entityliving.pitch;
                float f1 = (float)(Math.atan(Math.sqrt(d * d + d2 * d2) / d1) * 57.295779513082323D);
                if(d1 >= 0.0D)
                {
                    f1 -= 90F;
                } else
                {
                    f1 += 90F;
                }
                float f2 = f - f1;
                float f3 = entityliving.yaw % 360F;
                if(f3 < -180F)
                {
                    f3 += 360F;
                }
                if(f3 < 0.0F)
                {
                    f3 *= -1F;
                } else
                if(f3 < 180F)
                {
                    f3 *= -1F;
                } else
                {
                    f3 = 360F - f3;
                }
                float f4;
                if(d >= 0.0D && d2 >= 0.0D)
                {
                    f4 = (float)(Math.atan(Math.abs(d / d2)) * 57.295779513082323D);
                } else
                if(d >= 0.0D && d2 <= 0.0D)
                {
                    f4 = 90F + (float)(Math.atan(Math.abs(d2 / d)) * 57.295779513082323D);
                } else
                if(d <= 0.0D && d2 >= 0.0D)
                {
                    f4 = -(90F - (float)(Math.atan(Math.abs(d2 / d)) * 57.295779513082323D));
                } else
                {
                    f4 = -(180F - (float)(Math.atan(Math.abs(d / d2)) * 57.295779513082323D));
                }
                float f5 = f4 - f3;
                if(f5 > 180F)
                {
                    f5 -= 360F;
                } else
                if(f5 < -180F)
                {
                    f5 += 360F;
                }
                f2 = Math.abs(f2);
                float f6;
                if(f2 < 15F)
                {
                    f6 = 1.0F;
                } else
                if(f2 > 180F)
                {
                    f6 = 0.0F;
                } else
                {
                    f6 = 1.0F - (f2 - 15F) / 165F;
                }
                f5 = Math.abs(f5);
                float f7;
                if(f5 < 15F)
                {
                    f7 = 1.0F;
                } else
                if(f5 > 180F)
                {
                    f7 = 0.0F;
                } else
                {
                    f7 = 1.0F - (f5 - 15F) / 165F;
                }
                float f8 = Math.min(f6, f7);
                float f9 = getDistance(entityliving);
                float f10;
                if((double)f9 < 8D)
                {
                    f10 = 1.0F;
                } else
                {
                    f10 = 1.0F - (float)(((double)f9 - 8D) / 24D);
                }
                int j;
                if(entityliving instanceof PlayerEntity)
                {
                    j = Math.round(1000F * f10 * f8);
                } else
                {
                    j = Math.round(200F * f10);
                }
                if(!mod_SdkUtility.flashTimes.containsKey(entityliving) || (Integer) ((SdkPair) mod_SdkUtility.flashTimes.get(entityliving)).getLeft() < j)
                {
                    mod_SdkUtility.flashTimes.put(entityliving, new SdkPair(j, entityliving.movementSpeed));
                }
                if(!(entityliving instanceof PlayerEntity))
                {
                    entityliving.movementSpeed = 0.0F;
                    entityliving.attackCooldown = j;
                }
            }

            dead = true;
        }
    }

    public ArrayList getEntityLivingsInRange(double d)
    {
        ArrayList arraylist = new ArrayList();
        for(int i = 0; i < world.entities.size(); i++)
        {
            Entity entity = (Entity)world.entities.get(i);
            if((entity instanceof LivingEntity) && entity.isAlive() && getSquaredDistance(entity) < d * d)
            {
                arraylist.add((LivingEntity)entity);
            }
        }

        return arraylist;
    }

    public ArrayList getPlayersInRange(double d)
    {
        ArrayList arraylist = new ArrayList();
        for(int i = 0; i < world.entities.size(); i++)
        {
            Entity entity = (Entity)world.entities.get(i);
            if((entity instanceof PlayerEntity) && entity.isAlive() && getSquaredDistance(entity) < d * d)
            {
                arraylist.add((PlayerEntity)entity);
            }
        }

        return arraylist;
    }

    protected String BOUNCE_SOUND;
    static final double MAX_DISTANCE = 32D;
    static final double MIN_DISTANCE = 8D;
    static final float MAX_ANGLE = 180F;
    static final float MIN_PITCH_ANGLE = 15F;
    static final float MIN_YAW_ANGLE = 15F;
    public static final int MAX_FLASH_TIME_PLAYER = 1000;
    public static final int MAX_FLASH_TIME_ENTITY = 200;
}

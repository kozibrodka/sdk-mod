
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.itemGun.SdkItemGunLaser;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

public class SdkEntityLaserWolf extends Wolf
{

    public SdkEntityLaserWolf(Level world)
    {
        super(world);
        laser = new ItemInstance(ItemListener.itemGunLaser);
        texture = "/assets/sdk/stationapi/textures/mob/mobLaserWolf.png";
    }

    protected void initDataTracker()
    {
        super.initDataTracker();
        dataTracker.startTracking(19, Byte.valueOf((byte)0));
        dataTracker.startTracking(20, Byte.valueOf((byte)0));
    }

    public boolean getHasLaser()
    {
        return dataTracker.getByte(19) > 0;
    }

    public void setHasLaser(boolean flag)
    {
        dataTracker.setInt(19, Byte.valueOf((byte)(flag ? 1 : 0)));
    }

    public boolean getHasArmour()
    {
        return dataTracker.getByte(20) > 0;
    }

    public void setHasArmour(boolean flag)
    {
        dataTracker.setInt(20, Byte.valueOf((byte)(flag ? 1 : 0)));
    }

    public String getTextured()
    {
        if(isTamed())
        {
            if(getHasLaser() && getHasArmour())
            {
                return "/assets/sdk/stationapi/textures/mob/mobLaserWolf_tame_laser_armour.png";
            }
            if(getHasLaser() && !getHasArmour())
            {
                return "/assets/sdk/stationapi/textures/mob/mobLaserWolf_tame_laser.png";
            }
            if(!getHasLaser() && getHasArmour())
            {
                return "/assets/sdk/stationapi/textures/mob/mobLaserWolf_tame_armour.png";
            } else
            {
                return "/assets/sdk/stationapi/textures/mob/mobLaserWolf_tame.png";
            }
        }
        if(isAngry())
        {
            return "/assets/sdk/stationapi/textures/mob/mobLaserWolf_angry.png";
        } else
        {
            return super.getTextured();
        }
    }

    public void writeCustomDataToTag(CompoundTag nbttagcompound)
    {
        super.writeCustomDataToTag(nbttagcompound);
        nbttagcompound.put("HasLaser", getHasLaser());
        nbttagcompound.put("HasArmour", getHasArmour());
    }

    public void readCustomDataFromTag(CompoundTag nbttagcompound)
    {
        super.readCustomDataFromTag(nbttagcompound);
        setHasLaser(nbttagcompound.getBoolean("HasLaser"));
        setHasArmour(nbttagcompound.getBoolean("HasArmour"));
    }

    protected void getDrops()
    {
        if(getHasLaser())
        {
            dropItem(ItemListener.itemGunLaser.id, 1);
        } else
        {
            super.getDrops();
        }
    }

    public boolean damage(EntityBase entity, int i)
    {
        return super.damage(entity, getHasArmour() ? i / 2 : i);
    }

    protected void tryAttack(EntityBase entity, float f)
    {
        if(getHasLaser())
        {
            if(f < 32F && method_928(entity))
            {
                float f1 = yaw;
                float f2 = pitch;
                method_924(entity, 10F, 10F);
                if(attackTime <= 0 && Math.abs(yaw - f1) < 10F && Math.abs(pitch - f2) < 10F)
                {
                    attackTime = 20;
                    ((SdkItemGun)ItemListener.itemGunLaser).useOryginal(laser, level, this, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                }
            }
        } else
        {
            super.tryAttack(entity, f);
        }
    }

    public boolean interact(PlayerBase entityplayer)
    {
        if(!level.isServerSide && isTamed() && entityplayer.name.equals(getOwner()))
        {
            ItemInstance itemstack = entityplayer.inventory.getHeldItem();
            if(itemstack != null)
            {
                if(!getHasLaser() && (itemstack.getType() instanceof SdkItemGunLaser))
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedHotbarSlot] = null;
                    setHasLaser(true);
                    return true;
                }
                if(!getHasArmour() && itemstack.itemId == ItemBase.diamondChestplate.id)
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedHotbarSlot] = null;
                    setHasArmour(true);
                    return true;
                }
                if(itemstack.itemId == ItemBase.redstoneDust.id && dataTracker.getInt(18) < 20)
                {
                    entityplayer.getHeldItem().count--;
                    if(entityplayer.getHeldItem().count == 0)
                    {
                        entityplayer.inventory.main[entityplayer.inventory.selectedHotbarSlot] = null;
                    }
                    addHealth(4);
                    return true;
                }
            }
        }
        return super.interact(entityplayer);
    }

    public ItemInstance laser;
}

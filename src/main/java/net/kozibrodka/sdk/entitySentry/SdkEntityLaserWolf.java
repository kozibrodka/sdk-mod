
package net.kozibrodka.sdk.entitySentry;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.itemGun.SdkItemGunLaser;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class SdkEntityLaserWolf extends WolfEntity
{

    public SdkEntityLaserWolf(World world)
    {
        super(world);
        laser = new ItemStack(ItemListener.itemGunLaser);
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
        dataTracker.set(19, Byte.valueOf((byte)(flag ? 1 : 0)));
    }

    public boolean getHasArmour()
    {
        return dataTracker.getByte(20) > 0;
    }

    public void setHasArmour(boolean flag)
    {
        dataTracker.set(20, Byte.valueOf((byte)(flag ? 1 : 0)));
    }

    public String getTexture()
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
            return super.getTexture();
        }
    }

    public void writeNbt(NbtCompound nbttagcompound)
    {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putBoolean("HasLaser", getHasLaser());
        nbttagcompound.putBoolean("HasArmour", getHasArmour());
    }

    public void readNbt(NbtCompound nbttagcompound)
    {
        super.readNbt(nbttagcompound);
        setHasLaser(nbttagcompound.getBoolean("HasLaser"));
        setHasArmour(nbttagcompound.getBoolean("HasArmour"));
    }

    protected void dropItems()
    {
        if(getHasLaser())
        {
            dropItem(ItemListener.itemGunLaser.id, 1);
        } else
        {
            super.dropItems();
        }
    }

    public boolean damage(Entity entity, int i)
    {
        return super.damage(entity, getHasArmour() ? i / 2 : i);
    }

    protected void attack(Entity entity, float f)
    {
        if(getHasLaser())
        {
            if(f < 32F && canSee(entity))
            {
                float f1 = yaw;
                float f2 = pitch;
                lookAt(entity, 10F, 10F);
                if(attackCooldown <= 0 && Math.abs(yaw - f1) < 10F && Math.abs(pitch - f2) < 10F)
                {
                    attackCooldown = 20;
                    ((SdkItemGun)ItemListener.itemGunLaser).useOryginal(laser, world, this, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                }
            }
        } else
        {
            super.attack(entity, f);
        }
    }

    public boolean interact(PlayerEntity entityplayer)
    {
        if(!world.isRemote && isTamed() && entityplayer.name.equals(getOwnerName()))
        {
            ItemStack itemstack = entityplayer.inventory.getSelectedItem();
            if(itemstack != null)
            {
                if(!getHasLaser() && (itemstack.getItem() instanceof SdkItemGunLaser))
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = null;
                    setHasLaser(true);
                    return true;
                }
                if(!getHasArmour() && itemstack.itemId == Item.DIAMOND_CHESTPLATE.id)
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = null;
                    setHasArmour(true);
                    return true;
                }
                if(itemstack.itemId == Item.REDSTONE.id && dataTracker.getInt(18) < 20)
                {
                    entityplayer.getHand().count--;
                    if(entityplayer.getHand().count == 0)
                    {
                        entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = null;
                    }
                    heal(4);
                    return true;
                }
            }
        }
        return super.interact(entityplayer);
    }

    public ItemStack laser;
}

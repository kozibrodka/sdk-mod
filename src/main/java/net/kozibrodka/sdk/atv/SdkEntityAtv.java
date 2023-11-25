package net.kozibrodka.sdk.atv;

import net.fabricmc.loader.api.FabricLoader;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemCustomUseDelay;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.kozibrodka.sdk_api.events.utils.SdkVehicle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.sortme.Explosion;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.DoubleTag;
import net.minecraft.util.io.FloatTag;
import net.minecraft.util.io.ListTag;

public class SdkEntityAtv extends SdkEntityLandVehicle
        implements InventoryBase, SdkVehicle
{

    public SdkEntityAtv(Level world)
    {
        super(world);
        gunA = null;
        gunB = null;
        deathTime = -13;
        DEATH_TIME_MAX = 100;
        soundLoopTime = 0;
        SOUND_RIDING = "sdk:atv";
        SOUND_LOOP_TIME_MAX = 3;
        setSize(1.0F, 1.0F); //setSize(1.0F, 1.0F);
        standingEyeHeight = 0.3F;
        field_1641 = 1.0F; //stepHeight
        field_1622 = true; //ignoreFrustumCheck
    }

    public SdkEntityAtv(Level world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1 + (double)standingEyeHeight, d2);
        velocityX = 0.0D;
        velocityY = 0.0D;
        velocityZ = 0.0D;
        prevX = d;
        prevY = d1;
        prevZ = d2;
    }

    public boolean method_1380() //canBePushe
    {
        return true;
    }

    public double getMountedHeightOffset()
    {
        return 0.29999999999999999D;
    }

    public float getStandingEyeHeight()
    {
        return 0.7F;
    }

    public void onHurt()
    {
        level.playSound(this, "ofensywa:mechhurt", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
    }

    public void onDeath()
    {
        if(deathTime == -13)
        {
            deathTime = DEATH_TIME_MAX;
        }
    }

    public void tick()
    {
        super.tick();
        if(rand.nextInt(MAX_HEALTH) > health * 2)
        {
            if(Math.random() < 0.75D)
            {
                spawnParticles("smoke", 4, false);
            } else
            {
                spawnParticles("largesmoke", 1, false);
            }
        }
        if(health > 0 && deathTime != -13)
        {
            deathTime = -13;
        }
        if(deathTime >= 0)
        {
            if(deathTime == 0)
            {
                Explosion explosion = new Explosion(level, null, x, (float)y, (float)z, 3F);
                explosion.kaboomPhase1();
                level.playSound(x, y, z, "random.explode", 4F, (1.0F + (level.rand.nextFloat() - level.rand.nextFloat()) * 0.2F) * 0.7F);
                spawnParticles("explode", 64, true);
                spawnParticles("smoke", 64, true);
                remove();
            } else
            if(rand.nextInt(DEATH_TIME_MAX) > deathTime)
            {
                spawnParticles("flame", 8, false);
            }
            deathTime--;
        }
        if(passenger != null)
        {
            if(soundLoopTime <= 0)
            {
                level.playSound(x + velocityX * 1.5D, y + (onGround ? 0.0D : velocityY) * 1.5D, z + velocityZ * 1.5D, SOUND_RIDING, 1.0F, 1.0F + (float)(getSpeed() / MAX_SPEED / 4D));
                soundLoopTime = SOUND_LOOP_TIME_MAX;
            }
            soundLoopTime--;
        } else
        {
            soundLoopTime = 0;
        }
    }

    public void spawnParticles(String s, int i, boolean flag)
    {
        for(int j = 0; j < i; j++)
        {
            double d = (x + rand.nextDouble() * (double)width * 1.5D) - (double)width * 0.75D;
            double d1 = ((y + rand.nextDouble() * (double)height) - (double)height * 0.5D) + 0.25D;
            double d2 = (z + rand.nextDouble() * (double)width) - (double)width * 0.5D;
            double d3 = flag ? rand.nextDouble() - 0.5D : 0.0D;
            double d4 = flag ? rand.nextDouble() - 0.5D : 0.0D;
            double d5 = flag ? rand.nextDouble() - 0.5D : 0.0D;
            if(Math.random() < 0.75D)
            {
                level.addParticle(s, d, d1, d2, d3, d4, d5);
            } else
            {
                level.addParticle(s, d, d1, d2, d3, d4, d5);
            }
        }

    }

    public boolean interact(PlayerBase entityplayer)
    {
        if(entityplayer.getHeldItem() != null && entityplayer.getHeldItem().itemId == ItemListener.itemWrench.id)
        {
            if(health > 0 && health < MAX_HEALTH)
            {
                level.playSound(this, "sdk:wrench", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                health = Math.min(health + 4, MAX_HEALTH);
                entityplayer.swingHand();
                entityplayer.getHeldItem().applyDamage(1, entityplayer);
                if(entityplayer.getHeldItem().getDamage() <= 0)
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedHotbarSlot] = null;
                }
            }
            return true;
        }
        if(passenger != null && (passenger instanceof PlayerBase) && passenger != entityplayer)
        {
            return true;
        }
        if(!level.isServerSide)
        {
            entityplayer.startRiding(this);
            SdkItemCustomUseDelay.doNotUseThisTick = level.getLevelTime();
        }
        return true;
    }

    public void fromTag(CompoundTag nbttagcompound)
    {
        ListTag nbttaglist = nbttagcompound.getListTag("Pos");
        ListTag nbttaglist1 = nbttagcompound.getListTag("Motion");
        ListTag nbttaglist2 = nbttagcompound.getListTag("Rotation");
        setPosition(0.0D, 0.0D, 0.0D);
        velocityX = ((DoubleTag)nbttaglist1.get(0)).data;
        velocityY = ((DoubleTag)nbttaglist1.get(1)).data;
        velocityZ = ((DoubleTag)nbttaglist1.get(2)).data;
        if(Math.abs(velocityX) > 10D)
        {
            velocityX = 0.0D;
        }
        if(Math.abs(velocityY) > 10D)
        {
            velocityY = 0.0D;
        }
        if(Math.abs(velocityZ) > 10D)
        {
            velocityZ = 0.0D;
        }
        prevX = prevRenderX = x = ((DoubleTag)nbttaglist.get(0)).data;
        prevY = prevRenderY = y = ((DoubleTag)nbttaglist.get(1)).data;
        prevZ = prevRenderZ = z = ((DoubleTag)nbttaglist.get(2)).data;
        prevYaw = yaw = ((FloatTag)nbttaglist2.get(0)).data;
        prevPitch = pitch = ((FloatTag)nbttaglist2.get(1)).data;
        fallDistance = nbttagcompound.getFloat("FallDistance");
        fire = nbttagcompound.getShort("Fire");
        air = nbttagcompound.getShort("Air");
        onGround = nbttagcompound.getBoolean("OnGround");
        setPosition(x, y, z);
        readCustomDataFromTag(nbttagcompound);
    }

    public void readCustomDataFromTag(CompoundTag nbttagcompound)
    {
        ListTag nbttaglist = nbttagcompound.getListTag("GunA");
        if(nbttaglist.size() > 0)
        {
            CompoundTag nbttagcompound1 = (CompoundTag)nbttaglist.get(0);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 == 0)
            {
                gunA = new ItemInstance(nbttagcompound1);
            }
        }
        ListTag nbttaglist1 = nbttagcompound.getListTag("GunB");
        if(nbttaglist1.size() > 0)
        {
            CompoundTag nbttagcompound2 = (CompoundTag)nbttaglist1.get(0);
            byte byte1 = nbttagcompound2.getByte("Slot");
            if(byte1 == 0)
            {
                gunB = new ItemInstance(nbttagcompound2);
            }
        }
        health = nbttagcompound.getInt("Health");
        deathTime = nbttagcompound.getInt("DeathTime");
    }

    public void writeCustomDataToTag(CompoundTag nbttagcompound)
    {
        ListTag nbttaglist = new ListTag();
        if(gunA != null)
        {
            CompoundTag nbttagcompound1 = new CompoundTag();
            nbttagcompound1.put("Slot", (byte)0);
            gunA.toTag(nbttagcompound1);
            nbttaglist.add(nbttagcompound1);
        }
        nbttagcompound.put("GunA", nbttaglist);
        ListTag nbttaglist1 = new ListTag();
        if(gunB != null)
        {
            CompoundTag nbttagcompound2 = new CompoundTag();
            nbttagcompound2.put("Slot", (byte)0);
            gunB.toTag(nbttagcompound2);
            nbttaglist1.add(nbttagcompound2);
        }
        nbttagcompound.put("GunB", nbttaglist1);
        nbttagcompound.put("Health", health);
        nbttagcompound.put("DeathTime", deathTime);
    }

    public void fireGuns()
    {
        if(gunA != null)
        {
            ((SdkItemGun)gunA.getType()).onItemRightClickEntity(gunA, level, this, -1.8F, 0.0F, 0.5625F, 90F, 0.0F);
        }
        if(gunB != null)
        {
            ((SdkItemGun)gunB.getType()).onItemRightClickEntity(gunB, level, this, -1.8F, 0.0F, -0.3125F, 90F, 0.0F);
        }
    }

    public int getInventorySize()
    {
        return 2;
    }

    public ItemInstance getInventoryItem(int i)
    {
        if(i == 0)
        {
            return gunA;
        }
        if(i == 1)
        {
            return gunB;
        } else
        {
            return null;
        }
    }

    public ItemInstance takeInventoryItem(int i, int j)
    {
        ItemInstance itemstack = null;
        if(i == 0 && gunA != null)
        {
            itemstack = gunA;
            gunA = null;
        } else
        if(i == 1 && gunB != null)
        {
            itemstack = gunB;
            gunB = null;
        }
        return itemstack;
    }

    public void setInventoryItem(int i, ItemInstance itemstack)
    {
        if(itemstack == null || (itemstack.getType() instanceof SdkItemGun))
        {
            if(i == 0)
            {
                gunA = itemstack;
            } else
            if(i == 1)
            {
                gunB = itemstack;
            }
        }
    }

    public String getContainerName()
    {
        return "ATV";
    }

    public int getMaxItemCount()
    {
        return 1;
    }

    public void markDirty()
    {
    }

    public boolean canPlayerUse(PlayerBase entityplayer)
    {
        return entityplayer.squaredDistanceTo(x, y, z) <= 64D;
    }

    public ItemInstance gunA;
    public ItemInstance gunB;
    public int deathTime;
    public int DEATH_TIME_MAX;
    public int soundLoopTime;
    public String SOUND_RIDING;
    public int SOUND_LOOP_TIME_MAX;

    @Override
    public void altFireKey(PlayerBase entityplayer) {
        fireGuns();
    }

    @Override
    public void inventoryAtvKey(Minecraft minecraft, PlayerBase entityplayer) {
        if(minecraft.currentScreen instanceof SdkGuiAtv){
            minecraft.openScreen(null);
        }else{
            minecraft.openScreen(new SdkGuiAtv(entityplayer.inventory, (SdkEntityAtv)entityplayer.vehicle));
        }
    }
}

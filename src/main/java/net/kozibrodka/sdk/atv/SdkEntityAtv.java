package net.kozibrodka.sdk.atv;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkItemCustomUseDelay;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.kozibrodka.sdk_api.utils.SdkVehicle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.TriState;

@HasTrackingParameters(trackingDistance = 160, updatePeriod = 2, sendVelocity = TriState.TRUE)
public class SdkEntityAtv extends SdkEntityLandVehicle
        implements Inventory, SdkVehicle, EntitySpawnDataProvider
{

    public SdkEntityAtv(World world)
    {
        super(world);
        gunA = null;
        gunB = null;
        deathTime = -13;
        DEATH_TIME_MAX = 100;
        soundLoopTime = 0;
        SOUND_RIDING = "sdk:atv";
        SOUND_LOOP_TIME_MAX = 3;
        setBoundingBoxSpacing(1.0F, 1.0F); //setSize(1.0F, 1.0F);
        standingEyeHeight = 0.3F;
        stepHeight = 1.0F; //stepHeight
        ignoreFrustumCull = true; //ignoreFrustumCheck
    }

    public SdkEntityAtv(World world, double d, double d1, double d2)
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


    @Override
    public boolean isPushable() //canBePushe
    {
        return true;
    }

    @Override
    public double getPassengerRidingHeight()
    {
        return 0.3D;
    }

    @Override
    public float getEyeHeight()
    {
        return 0.7F;
    }

    @Override
    public void onHurt()
    {
        world.playSound(this, "sdk:mechhurt", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    public void onDeath()
    {
        if(deathTime == -13)
        {
            deathTime = DEATH_TIME_MAX;
        }
    }

    @Override
    public void tick()
    {
        super.tick();
        if(clientFIRE){
            fireGuns();
        }
        if(random.nextInt(MAX_HEALTH) > health * 2)
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
                Explosion explosion = new Explosion(world, null, x, (float)y, (float)z, 3F);
                explosion.explode();
                world.playSound(x, y, z, "random.explode", 4F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F);
                spawnParticles("explode", 64, true);
                spawnParticles("smoke", 64, true);
                markDead();
            } else
            if(random.nextInt(DEATH_TIME_MAX) > deathTime)
            {
                spawnParticles("flame", 8, false);
            }
            deathTime--;
        }
        if(passenger != null)
        {
//            if(SdkEnvTool.isEnvServ() && passenger instanceof PlayerEntity playerP){
//                PacketHelper.sendTo(playerP, new GroundPacket(this.onGround));
//            }
            if(soundLoopTime <= 0)
            {
                world.playSound(x + velocityX * 1.5D, y + (onGround ? 0.0D : velocityY) * 1.5D, z + velocityZ * 1.5D, SOUND_RIDING, 1.0F, 1.0F + (float)(getSpeed() / MAX_SPEED / 4D));
                soundLoopTime = SOUND_LOOP_TIME_MAX;
            }
            soundLoopTime--;
        } else
        {
            soundLoopTime = 0;
        }
    }

    public void spawnParticles(String s, int i, boolean flag)
    { //TODO PARTICLE new
        for(int j = 0; j < i; j++)
        {
            double d = (x + random.nextDouble() * (double)width * 1.5D) - (double)width * 0.75D;
            double d1 = ((y + random.nextDouble() * (double)height) - (double)height * 0.5D) + 0.25D;
            double d2 = (z + random.nextDouble() * (double)width) - (double)width * 0.5D;
            double d3 = flag ? random.nextDouble() - 0.5D : 0.0D;
            double d4 = flag ? random.nextDouble() - 0.5D : 0.0D;
            double d5 = flag ? random.nextDouble() - 0.5D : 0.0D;
            if(Math.random() < 0.75D)
            {
                world.addParticle(s, d, d1, d2, d3, d4, d5);
            } else
            {
                world.addParticle(s, d, d1, d2, d3, d4, d5);
            }
        }

    }

    @Override
    public boolean interact(PlayerEntity entityplayer)
    {
        if(entityplayer.getHand() != null && entityplayer.getHand().itemId == ItemListener.itemWrench.id)
        {
            if(health > 0 && health < MAX_HEALTH)
            {
                world.playSound(this, "sdk:wrench", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                health = Math.min(health + 4, MAX_HEALTH);
                entityplayer.swingHand();
                entityplayer.getHand().damage(1, entityplayer);
                if(entityplayer.getHand().getDamage() <= 0)
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = null;
                }
            }
            return true;
        }
        if(passenger != null && (passenger instanceof PlayerEntity) && passenger != entityplayer)
        {
            return true;
        }
        if(!world.isRemote)
        {
            entityplayer.setVehicle(this);
            SdkItemCustomUseDelay.doNotUseThisTick = world.getTime();
        }
        return true;
    }

//    @Override
//    public void read(NbtCompound nbttagcompound)
//    {
//        NbtList nbttaglist = nbttagcompound.getList("Pos");
//        NbtList nbttaglist1 = nbttagcompound.getList("Motion");
//        NbtList nbttaglist2 = nbttagcompound.getList("Rotation");
//        setPosition(0.0D, 0.0D, 0.0D);
//        velocityX = ((NbtDouble)nbttaglist1.get(0)).value;
//        velocityY = ((NbtDouble)nbttaglist1.get(1)).value;
//        velocityZ = ((NbtDouble)nbttaglist1.get(2)).value;
//        if(Math.abs(velocityX) > 10D)
//        {
//            velocityX = 0.0D;
//        }
//        if(Math.abs(velocityY) > 10D)
//        {
//            velocityY = 0.0D;
//        }
//        if(Math.abs(velocityZ) > 10D)
//        {
//            velocityZ = 0.0D;
//        }
//        prevX = lastTickX = x = ((NbtDouble)nbttaglist.get(0)).value;
//        prevY = lastTickY = y = ((NbtDouble)nbttaglist.get(1)).value;
//        prevZ = lastTickZ = z = ((NbtDouble)nbttaglist.get(2)).value;
//        prevYaw = yaw = ((NbtFloat)nbttaglist2.get(0)).value;
//        prevPitch = pitch = ((NbtFloat)nbttaglist2.get(1)).value;
//        fallDistance = nbttagcompound.getFloat("FallDistance");
//        fireTicks = nbttagcompound.getShort("Fire");
//        air = nbttagcompound.getShort("Air");
//        onGround = nbttagcompound.getBoolean("OnGround");
//        setPosition(x, y, z);
//        readNbt(nbttagcompound);
//
//        System.out.println("READ");
//    }

    @Override
    public void readNbt(NbtCompound nbttagcompound)
    {
        NbtList nbttaglist = nbttagcompound.getList("GunA");
        if(nbttaglist.size() > 0)
        {
            NbtCompound nbttagcompound1 = (NbtCompound)nbttaglist.get(0);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 == 0)
            {
                gunA = new ItemStack(nbttagcompound1);
            }
        }
        NbtList nbttaglist1 = nbttagcompound.getList("GunB");
        if(nbttaglist1.size() > 0)
        {
            NbtCompound nbttagcompound2 = (NbtCompound)nbttaglist1.get(0);
            byte byte1 = nbttagcompound2.getByte("Slot");
            if(byte1 == 0)
            {
                gunB = new ItemStack(nbttagcompound2);
            }
        }
        health = nbttagcompound.getInt("Health");
        deathTime = nbttagcompound.getInt("DeathTime");
    }

    @Override
    public void writeNbt(NbtCompound nbttagcompound)
    {
        NbtList nbttaglist = new NbtList();
        if(gunA != null)
        {
            NbtCompound nbttagcompound1 = new NbtCompound();
            nbttagcompound1.putByte("Slot", (byte)0);
            gunA.writeNbt(nbttagcompound1);
            nbttaglist.add(nbttagcompound1);
        }
        nbttagcompound.put("GunA", nbttaglist);
        NbtList nbttaglist1 = new NbtList();
        if(gunB != null)
        {
            NbtCompound nbttagcompound2 = new NbtCompound();
            nbttagcompound2.putByte("Slot", (byte)0);
            gunB.writeNbt(nbttagcompound2);
            nbttaglist1.add(nbttagcompound2);
        }
        nbttagcompound.put("GunB", nbttaglist1);
        nbttagcompound.putInt("Health", health);
        nbttagcompound.putInt("DeathTime", deathTime);
    }

    public void fireGuns()
    {
        if(gunA != null)
        {
            ((SdkItemGun)gunA.getItem()).onItemRightClickEntity(gunA, world, this, -1.8F, 0.0F, 0.5625F, 90F, 0.0F,0);
        }
        if(gunB != null)
        {
            ((SdkItemGun)gunB.getItem()).onItemRightClickEntity(gunB, world, this, -1.8F, 0.0F, -0.3125F, 90F, 0.0F,1);
        }
    }

    @Override
    public int size()
    {
        return 2;
    }

    @Override
    public ItemStack getStack(int i)
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

    @Override
    public ItemStack removeStack(int i, int j)
    {
        ItemStack itemstack = null;
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

    @Override
    public void setStack(int i, ItemStack itemstack)
    {
        if(itemstack == null || (itemstack.getItem() instanceof SdkItemGun))
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

    @Override
    public String getName()
    {
        return "ATV";
    }

    @Override
    public int getMaxCountPerStack()
    {
        return 1;
    }

    @Override
    public void markDirty()
    {
    }

    @Override
    public boolean canPlayerUse(PlayerEntity entityplayer)
    {
        return entityplayer.getSquaredDistance(x, y, z) <= 64D;
    }

    public ItemStack gunA;
    public ItemStack gunB;
    public int deathTime;
    public int DEATH_TIME_MAX;
    public int soundLoopTime;
    public String SOUND_RIDING;
    public int SOUND_LOOP_TIME_MAX;

    @Override
    public void setControls(boolean forward, boolean back, boolean left, boolean right, boolean up, boolean down, boolean fire) {
        clientFORWARD = forward;
        clientBACK = back;
        clientLEFT= left;
        clientRIGHT= right;
        clientUP= up;
        clientDOWN= down;
        clientFIRE= fire;
    }

    @Override
    public void reloadKey() {
        /// null
    }

    @Override
    public void exitKey(PlayerEntity playerEntity) {
        playerEntity.setVehicle(null);
        this.passenger = null;
    }

    @Override
    public void inventoryKey(PlayerEntity playerEntity) {
        GuiHelper.openGUI(
                playerEntity,
                Identifier.of(Namespace.of("sdk"), "openAtv"),
                this,
                new SdkContainerAtv(playerEntity.inventory, this)
        );
    }

    @Override
    public void bombKey() {
        /// null
    }

    @Override
    public void rocketKey() {
        /// null
    }

    @Override
    public int getPercentHealth() {
        return (health/MAX_HEALTH)*100;
    }

    @Override
    public float getArmorFactor() {
        return 1.0F;
    }

    @Override
    public float getDmgReduce() {
        return 1.0F;
    }

    @Override
    public float getDmgBroken() {
        return 1.0F;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "Atv");
    }
}

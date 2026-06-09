package net.kozibrodka.sdk.atv;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.network.PassHeadRotPacket;
import net.kozibrodka.sdk.network.PassengerPacket;
import net.kozibrodka.sdk_api.ingame.mod_SdkBase;
import net.kozibrodka.sdk_api.particle.SdkFireSmokeParticle;
import net.kozibrodka.sdk_api.particle.SdkFlameParticle;
import net.kozibrodka.sdk_api.utils.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.TriState;

import java.util.Objects;

@HasTrackingParameters(trackingDistance = 160, updatePeriod = 2, sendVelocity = TriState.TRUE)
public class SdkEntityAtv extends SdkEntityLandVehicle
        implements Inventory, SdkVehicle, EntitySpawnDataProvider // EntitySpawnDataProvider
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
    public float getBrightnessAtEyes(float tickDelta) {
        if(mod_SdkBase.thermoVision){
            return 2.0F;
        }else{
            return super.getBrightnessAtEyes(tickDelta);
        }
    }

    @Override
    public boolean shouldRender(double distance) {
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
            world.broadcastEntityEvent(this, (byte)7);
            deathTime = DEATH_TIME_MAX;
        }
    }

    @Override
    public void tick()
    {
        super.tick();
        if(world.isRemote){
            clientTick();
            return;
            /// CLIENT TICK.
        }
        if(clientFIRE){
            fireGuns();
        }
        if(random.nextInt(MAX_HEALTH) > health * 2 && SdkEnvTool.isEnvClient())
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
                world.broadcastEntityEvent(this, (byte)8);
                boolean flagW = false;
                if(checkWaterCollisions()){
                    flagW = true;
                }
                SdkExplosion explosion1 = new SdkExplosion(world, null, x,  y,  z, 3F, false, false, "random.explode", flagW,0.3F);
                explosion1.explodeA();
                explosion1.explodeB(false);
                if(SdkEnvTool.isEnvClient()) {
                    spawnParticles("explode", 64, true);
                    spawnParticles("smoke", 64, true);
                }

                markDead();
            } else
            if(random.nextInt(DEATH_TIME_MAX) > deathTime && SdkEnvTool.isEnvClient())
            {
                spawnParticles("flame", 8, false);
            }
            deathTime--;
        }
        if(passenger != null)
        {
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

    public void clientTick(){
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
        if(deathTime >= 0)
        {
            if(random.nextInt(DEATH_TIME_MAX) > deathTime)
            {
                spawnParticles("flame", 8, false);
            }
            deathTime--;
        }
        if(passenger != null)
        {
            if(soundLoopTime <= 0)
            {
                world.playSound(x + clientVelocityX * 1.5D, y + (onGround ? 0.0D : clientVelocityY) * 1.5D, z + clientVelocityZ * 1.5D, SOUND_RIDING, 1.0F, 1.0F + (float)(getClientSpeed() / MAX_SPEED / 4D));
                soundLoopTime = SOUND_LOOP_TIME_MAX;
            }
            soundLoopTime--;
            /// Głowa Packet
            if(Objects.equals(SdkToolsRender.minecraft.player.name, ((PlayerEntity) passenger).name)){
                PacketHelper.send(new PassHeadRotPacket(passenger.yaw, passenger.pitch));
            }
        } else
        {
            soundLoopTime = 0;
        }
        ///
        if(gunIdA == 0){
            clientgunA = null;
        }else{
            clientgunA = new ItemStack(gunIdA, 1, 0);
        }
        if(gunIdB == 0){
            clientgunB = null;
        }else{
            clientgunB = new ItemStack(gunIdB, 1, 0);
        }
        ///
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void processServerEntityStatus(byte status) {
        if (status == 6) {
            world.playSound(this, "sdk:mechhurt", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        } else if (status == 7) {
            deathTime = DEATH_TIME_MAX;
            health = 0;
        } else if (status == 8) {
            boolean flagW = false;
            if(checkWaterCollisions()){
                flagW = true;
            }
            SdkExplosion explosion1 = new SdkExplosion(world, null, x,  y,  z, 3F, false, false, "random.explode", flagW,0.3F);
            explosion1.explodeA();
            explosion1.explodeB(false);
            spawnParticles("explode", 64, true);
            spawnParticles("smoke", 64, true);
        }  else if (status == 9){
            world.playSound(this, "sdk:wrench", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        } else if (status == 10){
            this.passenger = null;
        }else{
            super.processServerEntityStatus(status);
        }
    }

    @Environment(EnvType.CLIENT)
    public void spawnParticles(String s, int i, boolean flag)
    {
        for(int j = 0; j < i; j++)
        {
            double d = (x + random.nextDouble() * (double)width * 1.5D) - (double)width * 0.75D;
            double d1 = ((y + random.nextDouble() * (double)height) - (double)height * 0.5D) + 0.25D;
            double d2 = (z + random.nextDouble() * (double)width) - (double)width * 0.5D;
            double d3 = flag ? random.nextDouble() - 0.5D : 0.0D;
            double d4 = flag ? random.nextDouble() - 0.5D : 0.0D;
            double d5 = flag ? random.nextDouble() - 0.5D : 0.0D;

            if(Objects.equals(s, "largesmoke")){
                SdkFireSmokeParticle particl = new SdkFireSmokeParticle(world, d, d1, d2, d3, d4, d5, 2.5F);
                SdkToolsRender.minecraft.particleManager.addParticle(particl);
            }
            if(Objects.equals(s, "smoke")){
                SdkFireSmokeParticle particl = new SdkFireSmokeParticle(world, d, d1, d2, d3, d4, d5, 1.0F);
                SdkToolsRender.minecraft.particleManager.addParticle(particl);
            }
            if(Objects.equals(s, "flame")){
                SdkFlameParticle particl = new SdkFlameParticle(world, d, d1, d2, d3, d4, d5);
                SdkToolsRender.minecraft.particleManager.addParticle(particl);
            }
        }

    }

    @Override
    public boolean interact(PlayerEntity entityplayer)
    {
        if(world.isRemote){
            if(entityplayer.getHand() != null && entityplayer.getHand().itemId == ItemListener.itemWrench.id && health > 0 && health < MAX_HEALTH){
                entityplayer.swingHand();
                return true;
            }
            SdkItemCustomUseDelay.doNotUseThisTick = world.getTime();
            return true;
        }
        if(entityplayer.getHand() != null && entityplayer.getHand().itemId == ItemListener.itemWrench.id)
        {
            if(health > 0 && health < MAX_HEALTH)
            {
                world.broadcastEntityEvent(this, (byte)9);
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
            if(SdkEnvTool.isEnvServ()) {
                if(passenger instanceof PlayerEntity passPL){
                    PacketHelper.sendToAllTracking(this, new PassengerPacket(this.id, passPL.name));
                }else{
                    world.broadcastEntityEvent(this, (byte)10);
                }
            }
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
                dataTracker.set(18, gunA.itemId);
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
                dataTracker.set(19, gunB.itemId);
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
            if(!world.isRemote) {
                dataTracker.set(18, 0);
            }
        } else
        if(i == 1 && gunB != null)
        {
            itemstack = gunB;
            gunB = null;
            if(!world.isRemote) {
                dataTracker.set(19, 0);
            }
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
                if(!world.isRemote) {
                    dataTracker.set(18, gunA.itemId);
                }
            } else
            if(i == 1)
            {
                gunB = itemstack;
                if(!world.isRemote) {
                    dataTracker.set(19, gunB.itemId);
                }
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
    public ItemStack clientgunA;
    public ItemStack clientgunB;
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
        world.broadcastEntityEvent(this, (byte)10);
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
        return (int) (((double)health/(double)MAX_HEALTH)*100D);
    }

    @Override
    public float getArmorFactor() {
        return -1.0F;
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
    public String getAmmoName() {
        return "";
    }

    @Override
    public String getBombName() {
        return "";
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "Atv");
    }
}
